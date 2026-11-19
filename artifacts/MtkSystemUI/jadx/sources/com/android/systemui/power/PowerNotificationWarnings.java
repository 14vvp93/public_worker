package com.android.systemui.power;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.utils.PowerUtil;
import com.android.systemui.SystemUI;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.NotificationChannels;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PowerNotificationWarnings implements PowerUI.WarningsUI {
    private int mBatteryLevel;
    private int mBucket;
    private final Context mContext;
    private Estimate mEstimate;
    private SystemUIDialog mHighTempDialog;
    private boolean mHighTempWarning;
    private boolean mInvalidCharger;
    private long mLowWarningThreshold;
    private final NotificationManager mNoMan;
    private boolean mPlaySound;
    private final PowerManager mPowerMan;
    private SystemUIDialog mSaverConfirmation;
    private SystemUIDialog mSaverEnabledConfirmation;
    private long mScreenOffTime;
    private long mSevereWarningThreshold;
    private boolean mShowAutoSaverSuggestion;
    private int mShowing;
    private SystemUIDialog mThermalShutdownDialog;
    private boolean mWarning;
    private long mWarningTriggerTimeMs;
    private static final boolean DEBUG = PowerUI.DEBUG;
    private static final String[] SHOWING_STRINGS = {"SHOWING_NOTHING", "SHOWING_WARNING", "SHOWING_SAVER", "SHOWING_INVALID_CHARGER", "SHOWING_AUTO_SAVER_SUGGESTION"};
    private static final AudioAttributes AUDIO_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Receiver mReceiver = new Receiver();
    private final Intent mOpenBatterySettings = settings("android.intent.action.POWER_USAGE_SUMMARY");

    public PowerNotificationWarnings(Context context) {
        this.mContext = context;
        this.mNoMan = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        this.mPowerMan = (PowerManager) context.getSystemService("power");
        this.mReceiver.init();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dump(PrintWriter pw) {
        pw.print("mWarning=");
        pw.println(this.mWarning);
        pw.print("mPlaySound=");
        pw.println(this.mPlaySound);
        pw.print("mInvalidCharger=");
        pw.println(this.mInvalidCharger);
        pw.print("mShowing=");
        pw.println(SHOWING_STRINGS[this.mShowing]);
        pw.print("mSaverConfirmation=");
        pw.println(this.mSaverConfirmation != null ? "not null" : null);
        pw.print("mSaverEnabledConfirmation=");
        pw.println(this.mSaverEnabledConfirmation != null ? "not null" : null);
        pw.print("mHighTempWarning=");
        pw.println(this.mHighTempWarning);
        pw.print("mHighTempDialog=");
        pw.println(this.mHighTempDialog != null ? "not null" : null);
        pw.print("mThermalShutdownDialog=");
        pw.println(this.mThermalShutdownDialog != null ? "not null" : null);
    }

    private int getLowBatteryAutoTriggerDefaultLevel() {
        return this.mContext.getResources().getInteger(R.integer.config_defaultAnalogClockSecondsHandFps);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void update(int batteryLevel, int bucket, long screenOffTime) {
        this.mBatteryLevel = batteryLevel;
        if (bucket >= 0) {
            this.mWarningTriggerTimeMs = 0L;
        } else if (bucket < this.mBucket) {
            this.mWarningTriggerTimeMs = System.currentTimeMillis();
        }
        this.mBucket = bucket;
        this.mScreenOffTime = screenOffTime;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void updateEstimate(Estimate estimate) {
        this.mEstimate = estimate;
        if (estimate.estimateMillis <= this.mLowWarningThreshold) {
            this.mWarningTriggerTimeMs = System.currentTimeMillis();
        }
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void updateThresholds(long lowThreshold, long severeThreshold) {
        this.mLowWarningThreshold = lowThreshold;
        this.mSevereWarningThreshold = severeThreshold;
    }

    private void updateNotification() {
        if (DEBUG) {
            Slog.d("PowerUI.Notification", "updateNotification mWarning=" + this.mWarning + " mPlaySound=" + this.mPlaySound + " mInvalidCharger=" + this.mInvalidCharger);
        }
        if (this.mInvalidCharger) {
            showInvalidChargerNotification();
            this.mShowing = 3;
            return;
        }
        if (this.mWarning) {
            showWarningNotification();
            this.mShowing = 1;
        } else if (this.mShowAutoSaverSuggestion) {
            if (this.mShowing != 4) {
                showAutoSaverSuggestionNotification();
            }
            this.mShowing = 4;
        } else {
            this.mNoMan.cancelAsUser("low_battery", 2, UserHandle.ALL);
            this.mNoMan.cancelAsUser("low_battery", 3, UserHandle.ALL);
            this.mNoMan.cancelAsUser("auto_saver", 49, UserHandle.ALL);
            this.mShowing = 0;
        }
    }

    private void showInvalidChargerNotification() {
        Notification.Builder nb = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(com.android.systemui.R.drawable.ic_power_low).setWhen(0L).setShowWhen(false).setOngoing(true).setContentTitle(this.mContext.getString(com.android.systemui.R.string.invalid_charger_title)).setContentText(this.mContext.getString(com.android.systemui.R.string.invalid_charger_text)).setColor(this.mContext.getColor(R.color.car_colorPrimary));
        SystemUI.overrideNotificationAppName(this.mContext, nb, false);
        Notification n = nb.build();
        this.mNoMan.cancelAsUser("low_battery", 3, UserHandle.ALL);
        this.mNoMan.notifyAsUser("low_battery", 2, n, UserHandle.ALL);
    }

    protected void showWarningNotification() {
        String percentage = NumberFormat.getPercentInstance().format(((double) this.mBatteryLevel) / 100.0d);
        String title = this.mContext.getString(com.android.systemui.R.string.battery_low_title);
        String contentText = this.mContext.getString(com.android.systemui.R.string.battery_low_percent_format, percentage);
        if (this.mEstimate != null) {
            contentText = getHybridContentString(percentage);
        }
        Notification.Builder nb = new Notification.Builder(this.mContext, NotificationChannels.BATTERY).setSmallIcon(com.android.systemui.R.drawable.ic_power_low).setWhen(this.mWarningTriggerTimeMs).setShowWhen(false).setContentText(contentText).setContentTitle(title).setOnlyAlertOnce(true).setDeleteIntent(pendingBroadcast("PNW.dismissedWarning")).setStyle(new Notification.BigTextStyle().bigText(contentText)).setVisibility(1);
        if (hasBatterySettings()) {
            nb.setContentIntent(pendingBroadcast("PNW.batterySettings"));
        }
        if (this.mEstimate == null || this.mBucket < 0 || this.mEstimate.estimateMillis < this.mSevereWarningThreshold) {
            nb.setColor(Utils.getColorAttr(this.mContext, R.attr.colorError));
        }
        nb.addAction(0, this.mContext.getString(com.android.systemui.R.string.battery_saver_start_action), pendingBroadcast("PNW.startSaver"));
        nb.setOnlyAlertOnce(true ^ this.mPlaySound);
        this.mPlaySound = false;
        SystemUI.overrideNotificationAppName(this.mContext, nb, false);
        Notification n = nb.build();
        this.mNoMan.cancelAsUser("low_battery", 2, UserHandle.ALL);
        this.mNoMan.notifyAsUser("low_battery", 3, n, UserHandle.ALL);
    }

    private void showAutoSaverSuggestionNotification() {
        Notification.Builder nb = new Notification.Builder(this.mContext, NotificationChannels.HINTS).setSmallIcon(com.android.systemui.R.drawable.ic_power_saver).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(com.android.systemui.R.string.auto_saver_title)).setContentText(this.mContext.getString(com.android.systemui.R.string.auto_saver_text, Integer.valueOf(getLowBatteryAutoTriggerDefaultLevel())));
        nb.setContentIntent(pendingBroadcast("PNW.enableAutoSaver"));
        nb.setDeleteIntent(pendingBroadcast("PNW.dismissAutoSaverSuggestion"));
        nb.addAction(0, this.mContext.getString(com.android.systemui.R.string.no_auto_saver_action), pendingBroadcast("PNW.autoSaverNoThanks"));
        SystemUI.overrideNotificationAppName(this.mContext, nb, false);
        Notification n = nb.build();
        this.mNoMan.notifyAsUser("auto_saver", 49, n, UserHandle.ALL);
    }

    private String getHybridContentString(String percentage) {
        return PowerUtil.getBatteryRemainingStringFormatted(this.mContext, this.mEstimate.estimateMillis, percentage, this.mEstimate.isBasedOnUsage);
    }

    private PendingIntent pendingBroadcast(String action) {
        return PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent(action).setPackage(this.mContext.getPackageName()).setFlags(268435456), 0, UserHandle.CURRENT);
    }

    private static Intent settings(String action) {
        return new Intent(action).setFlags(1551892480);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public boolean isInvalidChargerWarningShowing() {
        return this.mInvalidCharger;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissHighTemperatureWarning() {
        if (!this.mHighTempWarning) {
            return;
        }
        this.mHighTempWarning = false;
        dismissHighTemperatureWarningInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissHighTemperatureWarningInternal() {
        this.mNoMan.cancelAsUser("high_temp", 4, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showHighTemperatureWarning() {
        if (this.mHighTempWarning) {
            return;
        }
        this.mHighTempWarning = true;
        Notification.Builder nb = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(com.android.systemui.R.drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(com.android.systemui.R.string.high_temp_title)).setContentText(this.mContext.getString(com.android.systemui.R.string.high_temp_notif_message)).setVisibility(1).setContentIntent(pendingBroadcast("PNW.clickedTempWarning")).setDeleteIntent(pendingBroadcast("PNW.dismissedTempWarning")).setColor(Utils.getColorAttr(this.mContext, R.attr.colorError));
        SystemUI.overrideNotificationAppName(this.mContext, nb, false);
        Notification n = nb.build();
        this.mNoMan.notifyAsUser("high_temp", 4, n, UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHighTemperatureDialog() {
        if (this.mHighTempDialog != null) {
            return;
        }
        SystemUIDialog d = new SystemUIDialog(this.mContext);
        d.setIconAttribute(R.attr.alertDialogIcon);
        d.setTitle(com.android.systemui.R.string.high_temp_title);
        d.setMessage(com.android.systemui.R.string.high_temp_dialog_message);
        d.setPositiveButton(R.string.ok, null);
        d.setShowForAllUsers(true);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$PU_JpsxNcz7jXGNa_DRkuMbEWwU
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.mHighTempDialog = null;
            }
        });
        d.show();
        this.mHighTempDialog = d;
    }

    void dismissThermalShutdownWarning() {
        this.mNoMan.cancelAsUser("high_temp", 39, UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showThermalShutdownDialog() {
        if (this.mThermalShutdownDialog != null) {
            return;
        }
        SystemUIDialog d = new SystemUIDialog(this.mContext);
        d.setIconAttribute(R.attr.alertDialogIcon);
        d.setTitle(com.android.systemui.R.string.thermal_shutdown_title);
        d.setMessage(com.android.systemui.R.string.thermal_shutdown_dialog_message);
        d.setPositiveButton(R.string.ok, null);
        d.setShowForAllUsers(true);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$O5nkGS5PG2ihQrXqunpOO_aZDms
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.mThermalShutdownDialog = null;
            }
        });
        d.show();
        this.mThermalShutdownDialog = d;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showThermalShutdownWarning() {
        Notification.Builder nb = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(com.android.systemui.R.drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(com.android.systemui.R.string.thermal_shutdown_title)).setContentText(this.mContext.getString(com.android.systemui.R.string.thermal_shutdown_message)).setVisibility(1).setContentIntent(pendingBroadcast("PNW.clickedThermalShutdownWarning")).setDeleteIntent(pendingBroadcast("PNW.dismissedThermalShutdownWarning")).setColor(Utils.getColorAttr(this.mContext, R.attr.colorError));
        SystemUI.overrideNotificationAppName(this.mContext, nb, false);
        Notification n = nb.build();
        this.mNoMan.notifyAsUser("high_temp", 39, n, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void updateLowBatteryWarning() {
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissLowBatteryWarning() {
        if (DEBUG) {
            Slog.d("PowerUI.Notification", "dismissing low battery warning: level=" + this.mBatteryLevel);
        }
        dismissLowBatteryNotification();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLowBatteryNotification() {
        if (this.mWarning) {
            Slog.i("PowerUI.Notification", "dismissing low battery notification");
        }
        this.mWarning = false;
        updateNotification();
    }

    private boolean hasBatterySettings() {
        return this.mOpenBatterySettings.resolveActivity(this.mContext.getPackageManager()) != null;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showLowBatteryWarning(boolean playSound) {
        Slog.i("PowerUI.Notification", "show low battery warning: level=" + this.mBatteryLevel + " [" + this.mBucket + "] playSound=" + playSound);
        this.mPlaySound = playSound;
        this.mWarning = true;
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissInvalidChargerWarning() {
        dismissInvalidChargerNotification();
    }

    private void dismissInvalidChargerNotification() {
        if (this.mInvalidCharger) {
            Slog.i("PowerUI.Notification", "dismissing invalid charger notification");
        }
        this.mInvalidCharger = false;
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showInvalidChargerWarning() {
        this.mInvalidCharger = true;
        updateNotification();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAutoSaverSuggestion() {
        this.mShowAutoSaverSuggestion = true;
        updateNotification();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissAutoSaverSuggestion() {
        this.mShowAutoSaverSuggestion = false;
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void userSwitched() {
        updateNotification();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStartSaverConfirmation() {
        if (this.mSaverConfirmation != null) {
            return;
        }
        SystemUIDialog d = new SystemUIDialog(this.mContext);
        d.setTitle(com.android.systemui.R.string.battery_saver_confirmation_title);
        d.setMessage(getBatterySaverDescription());
        if (isEnglishLocale()) {
            d.setMessageHyphenationFrequency(0);
        }
        d.setMessageMovementMethod(LinkMovementMethod.getInstance());
        d.setNegativeButton(R.string.cancel, null);
        d.setPositiveButton(com.android.systemui.R.string.battery_saver_confirmation_ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$PQ6TbFMdXvgpK7h9WuYVBt-fwlE
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.setSaverMode(true, false);
            }
        });
        d.setShowForAllUsers(true);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$xerpSCZ61JcOfwY_Falk7PImt6k
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.mSaverConfirmation = null;
            }
        });
        d.show();
        this.mSaverConfirmation = d;
    }

    private boolean isEnglishLocale() {
        return Objects.equals(Locale.getDefault().getLanguage(), Locale.ENGLISH.getLanguage());
    }

    private CharSequence getBatterySaverDescription() {
        String learnMoreUrl = this.mContext.getText(com.android.systemui.R.string.help_uri_battery_saver_learn_more_link_target).toString();
        if (TextUtils.isEmpty(learnMoreUrl)) {
            return this.mContext.getText(R.string.accessibility_autoclick_double_click);
        }
        CharSequence rawText = this.mContext.getText(R.string.accessibility_autoclick_drag);
        SpannableString message = new SpannableString(rawText);
        SpannableStringBuilder builder = new SpannableStringBuilder(message);
        for (Annotation annotation : (Annotation[]) message.getSpans(0, message.length(), Annotation.class)) {
            String key = annotation.getValue();
            if ("url".equals(key)) {
                int start = message.getSpanStart(annotation);
                int end = message.getSpanEnd(annotation);
                URLSpan urlSpan = new URLSpan(learnMoreUrl) { // from class: com.android.systemui.power.PowerNotificationWarnings.1
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }

                    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
                    public void onClick(View widget) {
                        if (PowerNotificationWarnings.this.mSaverConfirmation != null) {
                            PowerNotificationWarnings.this.mSaverConfirmation.dismiss();
                        }
                        PowerNotificationWarnings.this.mContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").setFlags(268435456));
                        Uri uri = Uri.parse(getURL());
                        Context context = widget.getContext();
                        Intent intent = new Intent("android.intent.action.VIEW", uri).setFlags(268435456);
                        try {
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.w("PowerUI.Notification", "Activity was not found for intent, " + intent.toString());
                        }
                    }
                };
                builder.setSpan(urlSpan, start, end, message.getSpanFlags(urlSpan));
            }
        }
        return builder;
    }

    private void showAutoSaverEnabledConfirmation() {
        if (this.mSaverEnabledConfirmation != null) {
            return;
        }
        final Intent actionBatterySaverSetting = new Intent("android.settings.BATTERY_SAVER_SETTINGS").setFlags(268435456);
        SystemUIDialog d = new SystemUIDialog(this.mContext);
        d.setTitle(com.android.systemui.R.string.auto_saver_enabled_title);
        d.setMessage(this.mContext.getString(com.android.systemui.R.string.auto_saver_enabled_text, Integer.valueOf(getLowBatteryAutoTriggerDefaultLevel())));
        d.setPositiveButton(com.android.systemui.R.string.auto_saver_okay_action, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$vDtwYNuUfBD9Wjct46I3oQZA9IU
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.onAutoSaverEnabledConfirmationClosed();
            }
        });
        d.setNeutralButton(com.android.systemui.R.string.open_saver_setting_action, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$AbQVXaj-x5Lsbd2FymR2u02w1z0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PowerNotificationWarnings.lambda$showAutoSaverEnabledConfirmation$5(this.f$0, actionBatterySaverSetting, dialogInterface, i);
            }
        });
        d.setShowForAllUsers(true);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.-$$Lambda$PowerNotificationWarnings$tYg3WGmOxz_w0_hCJQKvQn95Q7c
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.onAutoSaverEnabledConfirmationClosed();
            }
        });
        d.show();
        this.mSaverEnabledConfirmation = d;
    }

    public static /* synthetic */ void lambda$showAutoSaverEnabledConfirmation$5(PowerNotificationWarnings powerNotificationWarnings, Intent actionBatterySaverSetting, DialogInterface dialog, int which) {
        powerNotificationWarnings.mContext.startActivity(actionBatterySaverSetting);
        powerNotificationWarnings.onAutoSaverEnabledConfirmationClosed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAutoSaverEnabledConfirmationClosed() {
        this.mSaverEnabledConfirmation = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSaverMode(boolean mode, boolean needFirstTimeWarning) {
        BatterySaverUtils.setPowerSaveMode(this.mContext, mode, needFirstTimeWarning);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleAutoBatterySaver() {
        int autoTriggerThreshold = this.mContext.getResources().getInteger(R.integer.config_defaultBinderHeavyHitterWatcherBatchSize);
        if (autoTriggerThreshold == 0) {
            autoTriggerThreshold = 15;
        }
        BatterySaverUtils.ensureAutoBatterySaver(this.mContext, autoTriggerThreshold);
        showAutoSaverEnabledConfirmation();
    }

    private final class Receiver extends BroadcastReceiver {
        private Receiver() {
        }

        public void init() {
            IntentFilter filter = new IntentFilter();
            filter.addAction("PNW.batterySettings");
            filter.addAction("PNW.startSaver");
            filter.addAction("PNW.dismissedWarning");
            filter.addAction("PNW.clickedTempWarning");
            filter.addAction("PNW.dismissedTempWarning");
            filter.addAction("PNW.clickedThermalShutdownWarning");
            filter.addAction("PNW.dismissedThermalShutdownWarning");
            filter.addAction("PNW.startSaverConfirmation");
            filter.addAction("PNW.autoSaverSuggestion");
            filter.addAction("PNW.enableAutoSaver");
            filter.addAction("PNW.autoSaverNoThanks");
            filter.addAction("PNW.dismissAutoSaverSuggestion");
            PowerNotificationWarnings.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, filter, "android.permission.DEVICE_POWER", PowerNotificationWarnings.this.mHandler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("PowerUI.Notification", "Received " + action);
            if (action.equals("PNW.batterySettings")) {
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings.this.mContext.startActivityAsUser(PowerNotificationWarnings.this.mOpenBatterySettings, UserHandle.CURRENT);
                return;
            }
            if (action.equals("PNW.startSaver")) {
                PowerNotificationWarnings.this.setSaverMode(true, true);
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                return;
            }
            if (action.equals("PNW.startSaverConfirmation")) {
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings.this.showStartSaverConfirmation();
                return;
            }
            if (action.equals("PNW.dismissedWarning")) {
                PowerNotificationWarnings.this.dismissLowBatteryWarning();
                return;
            }
            if ("PNW.clickedTempWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
                PowerNotificationWarnings.this.showHighTemperatureDialog();
                return;
            }
            if ("PNW.dismissedTempWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
                return;
            }
            if ("PNW.clickedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                PowerNotificationWarnings.this.showThermalShutdownDialog();
                return;
            }
            if ("PNW.dismissedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                return;
            }
            if ("PNW.autoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings.this.showAutoSaverSuggestion();
                return;
            }
            if ("PNW.dismissAutoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
                return;
            }
            if ("PNW.enableAutoSaver".equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
                PowerNotificationWarnings.this.scheduleAutoBatterySaver();
            } else if ("PNW.autoSaverNoThanks".equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
                BatterySaverUtils.suppressAutoBatterySaver(context);
            }
        }
    }
}
