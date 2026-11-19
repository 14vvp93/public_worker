package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.DateView;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class QuickStatusBarHeader extends RelativeLayout implements View.OnClickListener, NextAlarmController.NextAlarmChangeCallback, ZenModeController.Callback {
    private NextAlarmController mAlarmController;
    private final Runnable mAutoFadeOutTooltipRunnable;
    private Clock mClockView;
    private DateView mDateView;
    private boolean mExpanded;
    private final Handler mHandler;
    protected QuickQSPanel mHeaderQsPanel;
    private TouchAnimator mHeaderTextContainerAlphaAnimator;
    private View mHeaderTextContainerView;
    protected QSTileHost mHost;
    private StatusBarIconController.TintedIconManager mIconManager;
    private boolean mListening;
    private View mLongPressTooltipView;
    private AlarmManager.AlarmClockInfo mNextAlarm;
    private ImageView mNextAlarmIcon;
    private TextView mNextAlarmTextView;
    private boolean mQsDisabled;
    private QSPanel mQsPanel;
    private View mQuickQsStatusIcons;
    private int mRingerMode;
    private ImageView mRingerModeIcon;
    private TextView mRingerModeTextView;
    private final BroadcastReceiver mRingerReceiver;
    private int mShownCount;
    private View mStatusContainer;
    private TouchAnimator mStatusIconsAlphaAnimator;
    private View mStatusSeparator;
    private View mSystemIconsView;
    private ZenModeController mZenController;

    public QuickStatusBarHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHandler = new Handler();
        this.mRingerMode = 2;
        this.mRingerReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.QuickStatusBarHeader.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                QuickStatusBarHeader.this.mRingerMode = intent.getIntExtra("android.media.EXTRA_RINGER_MODE", -1);
                QuickStatusBarHeader.this.updateStatusText();
            }
        };
        this.mAutoFadeOutTooltipRunnable = new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QuickStatusBarHeader$FnPQlf4H1pC9aZZ4M1B32cjPajs
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.hideLongPressTooltip(false);
            }
        };
        this.mAlarmController = (NextAlarmController) Dependency.get(NextAlarmController.class);
        this.mZenController = (ZenModeController) Dependency.get(ZenModeController.class);
        this.mShownCount = getStoredShownCount();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderQsPanel = (QuickQSPanel) findViewById(R.id.quick_qs_panel);
        this.mSystemIconsView = findViewById(R.id.quick_status_bar_system_icons);
        this.mQuickQsStatusIcons = findViewById(R.id.quick_qs_status_icons);
        StatusIconContainer iconContainer = (StatusIconContainer) findViewById(R.id.statusIcons);
        iconContainer.setShouldRestrictIcons(false);
        this.mIconManager = new StatusBarIconController.TintedIconManager(iconContainer);
        this.mHeaderTextContainerView = findViewById(R.id.header_text_container);
        this.mLongPressTooltipView = findViewById(R.id.long_press_tooltip);
        this.mStatusContainer = findViewById(R.id.status_container);
        this.mStatusSeparator = findViewById(R.id.status_separator);
        this.mNextAlarmIcon = (ImageView) findViewById(R.id.next_alarm_icon);
        this.mNextAlarmTextView = (TextView) findViewById(R.id.next_alarm_text);
        this.mRingerModeIcon = (ImageView) findViewById(R.id.ringer_mode_icon);
        this.mRingerModeTextView = (TextView) findViewById(R.id.ringer_mode_text);
        updateResources();
        Rect tintArea = new Rect(0, 0, 0, 0);
        int colorForeground = Utils.getColorAttr(getContext(), android.R.attr.colorForeground);
        float intensity = getColorIntensity(colorForeground);
        int fillColor = fillColorForIntensity(intensity, getContext());
        applyDarkness(R.id.clock, tintArea, 0.0f, -1);
        this.mIconManager.setTint(fillColor);
        this.mClockView = (Clock) findViewById(R.id.clock);
        this.mClockView.setOnClickListener(this);
        this.mDateView = (DateView) findViewById(R.id.date);
        if (Settings.System.getInt(getContext().getContentResolver(), "key_change_sys_bar", 0) == 1) {
            resetHeaderBar();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatusText() {
        boolean changed = updateRingerStatus() || updateAlarmStatus();
        if (changed) {
            boolean alarmVisible = this.mNextAlarmTextView.getVisibility() == 0;
            boolean ringerVisible = this.mRingerModeTextView.getVisibility() == 0;
            this.mStatusSeparator.setVisibility((alarmVisible && ringerVisible) ? 0 : 8);
            updateTooltipShow();
        }
    }

    private boolean updateRingerStatus() {
        boolean isOriginalVisible = this.mRingerModeTextView.getVisibility() == 0;
        CharSequence originalRingerText = this.mRingerModeTextView.getText();
        boolean ringerVisible = false;
        if (!ZenModeConfig.isZenOverridingRinger(this.mZenController.getZen(), this.mZenController.getConfig())) {
            if (this.mRingerMode == 1) {
                this.mRingerModeIcon.setImageResource(R.drawable.stat_sys_ringer_vibrate);
                this.mRingerModeTextView.setText(R.string.qs_status_phone_vibrate);
                ringerVisible = true;
            } else if (this.mRingerMode == 0) {
                this.mRingerModeIcon.setImageResource(R.drawable.stat_sys_ringer_silent);
                this.mRingerModeTextView.setText(R.string.qs_status_phone_muted);
                ringerVisible = true;
            }
        }
        this.mRingerModeIcon.setVisibility(ringerVisible ? 0 : 8);
        this.mRingerModeTextView.setVisibility(ringerVisible ? 0 : 8);
        return (isOriginalVisible == ringerVisible && Objects.equals(originalRingerText, this.mRingerModeTextView.getText())) ? false : true;
    }

    private boolean updateAlarmStatus() {
        boolean isOriginalVisible = this.mNextAlarmTextView.getVisibility() == 0;
        CharSequence originalAlarmText = this.mNextAlarmTextView.getText();
        boolean alarmVisible = false;
        if (this.mNextAlarm != null) {
            alarmVisible = true;
            this.mNextAlarmTextView.setText(formatNextAlarm(this.mNextAlarm));
        }
        this.mNextAlarmIcon.setVisibility(alarmVisible ? 0 : 8);
        this.mNextAlarmTextView.setVisibility(alarmVisible ? 0 : 8);
        return (isOriginalVisible == alarmVisible && Objects.equals(originalAlarmText, this.mNextAlarmTextView.getText())) ? false : true;
    }

    private void applyDarkness(int id, Rect tintArea, float intensity, int color) {
        KeyEvent.Callback callbackFindViewById = findViewById(id);
        if (callbackFindViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) callbackFindViewById).onDarkChanged(tintArea, intensity, color);
        }
    }

    private int fillColorForIntensity(float intensity, Context context) {
        if (intensity == 0.0f) {
            return context.getColor(R.color.light_mode_icon_color_single_tone);
        }
        return context.getColor(R.color.dark_mode_icon_color_single_tone);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateResources();
        boolean shouldUseWallpaperTextColor = newConfig.orientation == 2;
        this.mClockView.useWallpaperTextColor(shouldUseWallpaperTextColor);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        updateResources();
    }

    private void updateResources() {
        Resources resources = this.mContext.getResources();
        this.mHeaderTextContainerView.getLayoutParams().height = resources.getDimensionPixelSize(R.dimen.qs_header_tooltip_height);
        this.mHeaderTextContainerView.setLayoutParams(this.mHeaderTextContainerView.getLayoutParams());
        ViewGroup.LayoutParams layoutParams = this.mSystemIconsView.getLayoutParams();
        int i = android.R.dimen.floating_toolbar_height;
        layoutParams.height = resources.getDimensionPixelSize(android.R.dimen.floating_toolbar_height);
        this.mSystemIconsView.setLayoutParams(this.mSystemIconsView.getLayoutParams());
        ViewGroup.LayoutParams layoutParams2 = getLayoutParams();
        if (!this.mQsDisabled) {
            i = android.R.dimen.floating_toolbar_horizontal_margin;
        }
        layoutParams2.height = resources.getDimensionPixelSize(i);
        setLayoutParams(getLayoutParams());
        updateStatusIconAlphaAnimator();
        updateHeaderTextContainerAlphaAnimator();
    }

    private void updateStatusIconAlphaAnimator() {
        this.mStatusIconsAlphaAnimator = new TouchAnimator.Builder().addFloat(this.mQuickQsStatusIcons, "alpha", 1.0f, 0.0f).build();
    }

    private void updateHeaderTextContainerAlphaAnimator() {
        this.mHeaderTextContainerAlphaAnimator = new TouchAnimator.Builder().addFloat(this.mHeaderTextContainerView, "alpha", 0.0f, 1.0f).setStartDelay(0.5f).build();
    }

    public void setExpanded(boolean expanded) {
        if (this.mExpanded == expanded) {
            return;
        }
        this.mExpanded = expanded;
        this.mHeaderQsPanel.setExpanded(expanded);
        updateEverything();
    }

    public void setExpansion(boolean isKeyguardShowing, float expansionFraction, float panelTranslationY) {
        float keyguardExpansionFraction = isKeyguardShowing ? 1.0f : expansionFraction;
        if (this.mStatusIconsAlphaAnimator != null) {
            this.mStatusIconsAlphaAnimator.setPosition(keyguardExpansionFraction);
        }
        if (isKeyguardShowing) {
            this.mHeaderTextContainerView.setTranslationY(panelTranslationY);
        } else {
            this.mHeaderTextContainerView.setTranslationY(0.0f);
        }
        if (this.mHeaderTextContainerAlphaAnimator != null) {
            this.mHeaderTextContainerAlphaAnimator.setPosition(keyguardExpansionFraction);
        }
    }

    private int getStoredShownCount() {
        return Prefs.getInt(this.mContext, "QsLongPressTooltipShownCount", 0);
    }

    public void disable(int state1, int state2, boolean animate) {
        boolean disabled = (state2 & 1) != 0;
        if (disabled == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = disabled;
        this.mHeaderQsPanel.setDisabledByPolicy(disabled);
        this.mHeaderTextContainerView.setVisibility(this.mQsDisabled ? 8 : 0);
        this.mQuickQsStatusIcons.setVisibility(this.mQsDisabled ? 8 : 0);
        updateResources();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).addIconGroup(this.mIconManager);
        requestApplyInsets();
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        Pair<Integer, Integer> padding = PhoneStatusBarView.cornerCutoutMargins(insets.getDisplayCutout(), getDisplay());
        if (padding != null) {
            this.mSystemIconsView.setPadding(((Integer) padding.first).intValue(), 0, ((Integer) padding.second).intValue(), 0);
        } else {
            this.mSystemIconsView.setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.status_bar_padding_start), 0, getResources().getDimensionPixelSize(R.dimen.status_bar_padding_end), 0);
        }
        return super.onApplyWindowInsets(insets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        setListening(false);
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).removeIconGroup(this.mIconManager);
        super.onDetachedFromWindow();
    }

    public void setListening(boolean listening) {
        if (listening == this.mListening) {
            return;
        }
        this.mHeaderQsPanel.setListening(listening);
        this.mListening = listening;
        if (listening) {
            this.mZenController.addCallback(this);
            this.mAlarmController.addCallback(this);
            this.mContext.registerReceiver(this.mRingerReceiver, new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"));
        } else {
            this.mZenController.removeCallback(this);
            this.mAlarmController.removeCallback(this);
            this.mContext.unregisterReceiver(this.mRingerReceiver);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.mClockView) {
            ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(new Intent("android.intent.action.SHOW_ALARMS"), 0);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
    public void onNextAlarmChanged(AlarmManager.AlarmClockInfo nextAlarm) {
        this.mNextAlarm = nextAlarm;
        updateStatusText();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onZenChanged(int zen) {
        updateStatusText();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onConfigChanged(ZenModeConfig config) {
        updateStatusText();
    }

    private void updateTooltipShow() {
        if (hasStatusText()) {
            hideLongPressTooltip(true);
        } else {
            hideStatusText();
        }
        updateHeaderTextContainerAlphaAnimator();
    }

    private boolean hasStatusText() {
        return this.mNextAlarmTextView.getVisibility() == 0 || this.mRingerModeTextView.getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLongPressTooltip(final boolean shouldShowStatusText) {
        this.mLongPressTooltipView.animate().cancel();
        if (this.mLongPressTooltipView.getVisibility() == 0 && this.mLongPressTooltipView.getAlpha() != 0.0f) {
            this.mHandler.removeCallbacks(this.mAutoFadeOutTooltipRunnable);
            this.mLongPressTooltipView.animate().alpha(0.0f).setDuration(300L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QuickStatusBarHeader.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    QuickStatusBarHeader.this.mLongPressTooltipView.setVisibility(4);
                    if (shouldShowStatusText) {
                        QuickStatusBarHeader.this.showStatus();
                    }
                }
            }).start();
        } else {
            this.mLongPressTooltipView.setVisibility(4);
            if (shouldShowStatusText) {
                showStatus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStatus() {
        this.mStatusContainer.setAlpha(0.0f);
        this.mStatusContainer.setVisibility(0);
        this.mStatusContainer.animate().alpha(1.0f).setDuration(300L).setListener(null).start();
    }

    private void hideStatusText() {
        if (this.mStatusContainer.getVisibility() == 0) {
            this.mStatusContainer.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QuickStatusBarHeader.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    QuickStatusBarHeader.this.mStatusContainer.setVisibility(4);
                    QuickStatusBarHeader.this.mStatusContainer.setAlpha(1.0f);
                }
            }).start();
        }
    }

    public void updateEverything() {
        post(new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QuickStatusBarHeader$94nU_9dHjWxAQAVsvLqnp7oGOsY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setClickable(false);
            }
        });
    }

    public void setQSPanel(QSPanel qsPanel) {
        this.mQsPanel = qsPanel;
        setupHost(qsPanel.getHost());
    }

    public void setupHost(QSTileHost host) {
        this.mHost = host;
        this.mHeaderQsPanel.setQSPanelAndHeader(this.mQsPanel, this);
        this.mHeaderQsPanel.setHost(host, null);
    }

    public void setCallback(QSDetail.Callback qsPanelCallback) {
        this.mHeaderQsPanel.setCallback(qsPanelCallback);
    }

    private String formatNextAlarm(AlarmManager.AlarmClockInfo info) {
        if (info == null) {
            return "";
        }
        String skeleton = DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser()) ? "EHm" : "Ehma";
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton);
        return DateFormat.format(pattern, info.getTriggerTime()).toString();
    }

    public static float getColorIntensity(int color) {
        return color == -1 ? 0.0f : 1.0f;
    }

    public void setMargins(int sideMargins) {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v != this.mSystemIconsView && v != this.mQuickQsStatusIcons && v != this.mHeaderQsPanel) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
                lp.leftMargin = sideMargins;
                lp.rightMargin = sideMargins;
            }
        }
    }

    public void resetHeaderBar() {
        LinearLayout mSystemParent;
        int count;
        if (this.mSystemIconsView != null && (count = (mSystemParent = (LinearLayout) this.mSystemIconsView).getChildCount()) == 5) {
            View[] childs = new View[count];
            for (int i = 0; i < count; i++) {
                childs[i] = mSystemParent.getChildAt(i);
            }
            mSystemParent.removeAllViews();
            for (int i2 = 0; i2 < count; i2++) {
                mSystemParent.addView(childs[(count - i2) - 1]);
            }
        }
    }
}
