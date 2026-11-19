package com.android.systemui.volume;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioSystem;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class VolumeDialogImpl implements VolumeDialog {
    private static final String TAG = Util.logTag(VolumeDialogImpl.class);
    private int hasAddButtons;
    private int mActiveAlpha;
    private int mActiveStream;
    private ColorStateList mActiveTint;
    private final Context mContext;
    private CustomDialog mDialog;
    private ViewGroup mDialogRowsView;
    private ViewGroup mDialogView;
    private int mInactiveAlpha;
    private ColorStateList mInactiveTint;
    private final KeyguardManager mKeyguard;
    private int mPrevActiveStream;
    private ViewGroup mRinger;
    private ImageButton mRingerIcon;
    private SafetyWarningDialog mSafetyWarning;
    private ImageButton mSettingsIcon;
    private View mSettingsView;
    private boolean mShowA11yStream;
    private boolean mShowing;
    private VolumeDialogController.State mState;
    private Window mWindow;
    private FrameLayout mZenIcon;
    private final H mHandler = new H();
    private final List<VolumeRow> mRows = new ArrayList();
    private final SparseBooleanArray mDynamic = new SparseBooleanArray();
    private final Object mSafetyWarningLock = new Object();
    private final Accessibility mAccessibility = new Accessibility();
    private boolean mAutomute = true;
    private boolean mSilentMode = true;
    private boolean mHovering = false;
    private final boolean is_hicar_certificate = SystemProperties.getBoolean("ro.vendor.hicar.certificate", false);
    private Runnable mSinglePress = new Runnable() { // from class: com.android.systemui.volume.VolumeDialogImpl.3
        @Override // java.lang.Runnable
        public void run() {
            VolumeDialogImpl.this.mRingerIcon.setPressed(true);
            VolumeDialogImpl.this.mRingerIcon.postOnAnimationDelayed(VolumeDialogImpl.this.mSingleUnpress, 200L);
        }
    };
    private Runnable mSingleUnpress = new Runnable() { // from class: com.android.systemui.volume.VolumeDialogImpl.4
        @Override // java.lang.Runnable
        public void run() {
            VolumeDialogImpl.this.mRingerIcon.setPressed(false);
        }
    };
    private final VolumeDialogController.Callbacks mControllerCallbackH = new VolumeDialogController.Callbacks() { // from class: com.android.systemui.volume.VolumeDialogImpl.5
        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowRequested(int reason) {
            VolumeDialogImpl.this.showH(reason);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onDismissRequested(int reason) {
            VolumeDialogImpl.this.dismissH(reason);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onScreenOff() {
            VolumeDialogImpl.this.dismissH(4);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onStateChanged(VolumeDialogController.State state) {
            VolumeDialogImpl.this.onStateChangedH(state, 0);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onLayoutDirectionChanged(int layoutDirection) {
            VolumeDialogImpl.this.mDialogView.setLayoutDirection(layoutDirection);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onConfigurationChanged() {
            VolumeDialogImpl.this.mDialog.dismiss();
            VolumeDialogImpl.this.initDialog();
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowVibrateHint() {
            if (VolumeDialogImpl.this.mSilentMode) {
                VolumeDialogImpl.this.mController.setRingerMode(0, false);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowSilentHint() {
            if (VolumeDialogImpl.this.mSilentMode) {
                VolumeDialogImpl.this.mController.setRingerMode(2, false);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowSafetyWarning(int flags) {
            VolumeDialogImpl.this.showSafetyWarningH(flags);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onAccessibilityModeChanged(Boolean showA11yStream) {
            VolumeDialogImpl.this.mShowA11yStream = showA11yStream == null ? false : showA11yStream.booleanValue();
            VolumeRow activeRow = VolumeDialogImpl.this.getActiveRow();
            if (VolumeDialogImpl.this.mShowA11yStream || 10 != activeRow.stream) {
                VolumeDialogImpl.this.updateRowsH(activeRow);
            } else {
                VolumeDialogImpl.this.dismissH(7);
            }
        }
    };
    private final ContentObserver mAddButtonsSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.volume.VolumeDialogImpl.6
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            int curVal = Settings.System.getInt(VolumeDialogImpl.this.mContext.getContentResolver(), "key_add_buttons", 0);
            if (VolumeDialogImpl.this.hasAddButtons != curVal && VolumeDialogImpl.this.mWindow != null) {
                WindowManager.LayoutParams lp = VolumeDialogImpl.this.mWindow.getAttributes();
                if (curVal == 1) {
                    lp.y = 80;
                } else {
                    lp.y = 0;
                }
                VolumeDialogImpl.this.mWindow.setAttributes(lp);
                VolumeDialogImpl.this.hasAddButtons = curVal;
            }
        }
    };
    private final VolumeDialogController mController = (VolumeDialogController) Dependency.get(VolumeDialogController.class);
    private final AccessibilityManagerWrapper mAccessibilityMgr = (AccessibilityManagerWrapper) Dependency.get(AccessibilityManagerWrapper.class);
    private final DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);

    public VolumeDialogImpl(Context context) {
        this.mContext = new ContextThemeWrapper(context, R.style.qs_theme);
        this.mKeyguard = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.hasAddButtons = Settings.System.getInt(this.mContext.getContentResolver(), "key_add_buttons", 0);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("key_add_buttons"), true, this.mAddButtonsSettingsObserver, -1);
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public void init(int windowType, VolumeDialog.Callback callback) {
        initDialog();
        this.mAccessibility.init();
        this.mController.addCallback(this.mControllerCallbackH, this.mHandler);
        this.mController.getState();
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public void destroy() {
        this.mAccessibility.destroy();
        this.mController.removeCallback(this.mControllerCallbackH);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDialog() {
        Log.d(TAG, "initDialog");
        this.mDialog = new CustomDialog(this.mContext);
        this.mHovering = false;
        this.mShowing = false;
        this.mWindow = this.mDialog.getWindow();
        this.mWindow.requestFeature(1);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(65538);
        this.mWindow.addFlags(17563944);
        this.mWindow.setType(2026);
        this.mWindow.setWindowAnimations(android.R.style.Animation.Toast);
        WindowManager.LayoutParams lp = this.mWindow.getAttributes();
        lp.format = -3;
        lp.setTitle(VolumeDialogImpl.class.getSimpleName());
        lp.gravity = 49;
        if (this.hasAddButtons == 1) {
            lp.y = 80;
        }
        lp.windowAnimations = -1;
        this.mWindow.setAttributes(lp);
        this.mWindow.setLayout(-2, -2);
        this.mDialog.setCanceledOnTouchOutside(false);
        this.mDialog.setContentView(R.layout.volume_dialog);
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$8BZhTIdOE2rPYfFa5HbcUDCtXeM
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                VolumeDialogImpl.lambda$initDialog$1(this.f$0, dialogInterface);
            }
        });
        this.mDialogView = (ViewGroup) this.mDialog.findViewById(R.id.volume_dialog);
        this.mDialogView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$T52d0W13mYvykk6ORgbytqfZsps
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return VolumeDialogImpl.lambda$initDialog$2(this.f$0, view, motionEvent);
            }
        });
        this.mActiveTint = ColorStateList.valueOf(Utils.getColorAccent(this.mContext));
        this.mActiveAlpha = Color.alpha(this.mActiveTint.getDefaultColor());
        this.mInactiveTint = ColorStateList.valueOf(Utils.getColorAttr(this.mContext, android.R.attr.colorForeground));
        this.mInactiveAlpha = getAlphaAttr(android.R.attr.secondaryContentAlpha);
        this.mDialogRowsView = (ViewGroup) this.mDialog.findViewById(R.id.volume_dialog_rows);
        this.mRinger = (ViewGroup) this.mDialog.findViewById(R.id.ringer);
        this.mRingerIcon = (ImageButton) this.mRinger.findViewById(R.id.ringer_icon);
        this.mZenIcon = (FrameLayout) this.mRinger.findViewById(R.id.dnd_icon);
        this.mSettingsView = this.mDialog.findViewById(R.id.settings_container);
        this.mSettingsIcon = (ImageButton) this.mDialog.findViewById(R.id.settings);
        if (this.mRows.isEmpty()) {
            AudioSystem.isSingleVolume(this.mContext);
            addRow(3, R.drawable.ic_volume_media, R.drawable.ic_volume_media_mute, true, false);
            if (!AudioSystem.isSingleVolume(this.mContext)) {
                addRow(2, R.drawable.ic_volume_ringer, R.drawable.ic_volume_ringer_mute, true, false);
                addRow(0, R.drawable.ic_volume_voice, R.drawable.ic_volume_voice, false, false);
                addRow(5, R.drawable.ic_volume_gis, R.drawable.ic_volume_gis_mute, true, false);
            }
        } else {
            addExistingRows();
        }
        updateRowsH(getActiveRow());
        initRingerH();
        initSettingsH();
    }

    public static /* synthetic */ void lambda$initDialog$1(final VolumeDialogImpl volumeDialogImpl, DialogInterface dialog) {
        if (!volumeDialogImpl.isLandscape()) {
            volumeDialogImpl.mDialogView.setTranslationX(volumeDialogImpl.mDialogView.getWidth() / 2.0f);
        }
        volumeDialogImpl.mDialogView.setAlpha(0.0f);
        volumeDialogImpl.mDialogView.animate().alpha(1.0f).translationX(0.0f).setDuration(300L).setInterpolator(new SystemUIInterpolators.LogDecelerateInterpolator()).withEndAction(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$vBH_Cy2LsLvfluWDg0W4IzJ1dm8
            @Override // java.lang.Runnable
            public final void run() {
                VolumeDialogImpl.lambda$initDialog$0(this.f$0);
            }
        }).start();
    }

    public static /* synthetic */ void lambda$initDialog$0(VolumeDialogImpl volumeDialogImpl) {
        if (!Prefs.getBoolean(volumeDialogImpl.mContext, "TouchedRingerToggle", false)) {
            volumeDialogImpl.mRingerIcon.postOnAnimationDelayed(volumeDialogImpl.mSinglePress, 1500L);
        }
    }

    public static /* synthetic */ boolean lambda$initDialog$2(VolumeDialogImpl volumeDialogImpl, View v, MotionEvent event) {
        int action = event.getActionMasked();
        volumeDialogImpl.mHovering = action == 9 || action == 7;
        volumeDialogImpl.rescheduleTimeoutH();
        return true;
    }

    private int getAlphaAttr(int attr) {
        TypedArray ta = this.mContext.obtainStyledAttributes(new int[]{attr});
        float alpha = ta.getFloat(0, 0.0f);
        ta.recycle();
        return (int) (255.0f * alpha);
    }

    private boolean isLandscape() {
        return this.mContext.getResources().getConfiguration().orientation == 2;
    }

    public void setStreamImportant(int i, boolean z) {
        this.mHandler.obtainMessage(5, i, z ? 1 : 0).sendToTarget();
    }

    public void setAutomute(boolean automute) {
        if (this.mAutomute == automute) {
            return;
        }
        this.mAutomute = automute;
        this.mHandler.sendEmptyMessage(4);
    }

    public void setSilentMode(boolean silentMode) {
        if (this.mSilentMode == silentMode) {
            return;
        }
        this.mSilentMode = silentMode;
        this.mHandler.sendEmptyMessage(4);
    }

    private void addRow(int stream, int iconRes, int iconMuteRes, boolean important, boolean defaultStream) {
        addRow(stream, iconRes, iconMuteRes, important, defaultStream, false);
    }

    private void addRow(int stream, int iconRes, int iconMuteRes, boolean important, boolean defaultStream, boolean dynamic) {
        if (D.BUG) {
            Slog.d(TAG, "Adding row for stream " + stream);
        }
        VolumeRow row = new VolumeRow();
        initRow(row, stream, iconRes, iconMuteRes, important, defaultStream);
        this.mDialogRowsView.addView(row.view);
        this.mRows.add(row);
    }

    private void addExistingRows() {
        if (D.BUG) {
            Log.d(TAG, "addExistingRows");
        }
        int N = this.mRows.size();
        for (int i = 0; i < N; i++) {
            VolumeRow row = this.mRows.get(i);
            initRow(row, row.stream, row.iconRes, row.iconMuteRes, row.important, row.defaultStream);
            this.mDialogRowsView.addView(row.view);
            updateVolumeRowH(row);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VolumeRow getActiveRow() {
        for (VolumeRow row : this.mRows) {
            if (row.stream == this.mActiveStream) {
                return row;
            }
        }
        for (VolumeRow row2 : this.mRows) {
            if (row2.stream == this.mPrevActiveStream) {
                return row2;
            }
        }
        return this.mRows.get(0);
    }

    private VolumeRow findRow(int stream) {
        for (VolumeRow row : this.mRows) {
            if (row.stream == stream) {
                return row;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getImpliedLevel(SeekBar seekBar, int progress) {
        int m = seekBar.getMax();
        int n = (m / 100) - 1;
        if (progress == 0) {
            return 0;
        }
        if (progress == m) {
            int level = m / 100;
            return level;
        }
        int level2 = 1 + ((int) ((progress / m) * n));
        return level2;
    }

    @SuppressLint({"InflateParams"})
    private void initRow(final VolumeRow row, final int stream, int iconRes, int iconMuteRes, boolean important, boolean defaultStream) {
        row.stream = stream;
        row.iconRes = iconRes;
        row.iconMuteRes = iconMuteRes;
        row.important = important;
        row.defaultStream = defaultStream;
        row.view = this.mDialog.getLayoutInflater().inflate(R.layout.volume_dialog_row, (ViewGroup) null);
        row.view.setId(row.stream);
        row.view.setTag(row);
        row.header = (TextView) row.view.findViewById(R.id.volume_row_header);
        row.header.setId(20 * row.stream);
        if (stream == 10) {
            row.header.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        }
        row.dndIcon = (FrameLayout) row.view.findViewById(R.id.dnd_icon);
        row.slider = (SeekBar) row.view.findViewById(R.id.volume_row_slider);
        row.slider.setOnSeekBarChangeListener(new VolumeSeekBarChangeListener(row));
        row.anim = null;
        row.icon = (ImageButton) row.view.findViewById(R.id.volume_row_icon);
        row.icon.setImageResource(iconRes);
        if (row.stream != 10) {
            row.icon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$I-0sumSTzcnKtt5xn4YVlQQget8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VolumeDialogImpl.lambda$initRow$3(this.f$0, row, stream, view);
                }
            });
        } else {
            row.icon.setImportantForAccessibility(2);
        }
        row.lastSyncVolTime = 0L;
    }

    public static /* synthetic */ void lambda$initRow$3(VolumeDialogImpl volumeDialogImpl, VolumeRow row, int stream, View v) {
        Events.writeEvent(volumeDialogImpl.mContext, 7, Integer.valueOf(row.stream), Integer.valueOf(row.iconState));
        volumeDialogImpl.mController.setActiveStream(row.stream);
        if (row.stream == 2) {
            boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
            if (volumeDialogImpl.mState.ringerModeInternal == 2) {
                if (hasVibrator) {
                    volumeDialogImpl.mController.setRingerMode(1, false);
                } else {
                    boolean wasZero = row.ss.level == 0;
                    volumeDialogImpl.mController.setStreamVolume(stream, wasZero ? row.lastAudibleLevel : 0);
                }
            } else {
                volumeDialogImpl.mController.setRingerMode(2, false);
                if (row.ss.level == 0) {
                    volumeDialogImpl.mController.setStreamVolume(stream, 1);
                }
            }
        } else {
            volumeDialogImpl.mController.setStreamVolume(stream, ((row.ss.level == row.ss.levelMin || row.ss.muted) ? 1 : 0) != 0 ? row.lastAudibleLevel : row.ss.levelMin);
        }
        row.userAttempt = 0L;
    }

    public void initSettingsH() {
        this.mSettingsView.setVisibility(8);
        this.mSettingsIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$7RdQKc1FND8ZrjtxSEsHEKXSyeY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VolumeDialogImpl.lambda$initSettingsH$4(this.f$0, view);
            }
        });
    }

    public static /* synthetic */ void lambda$initSettingsH$4(VolumeDialogImpl volumeDialogImpl, View v) {
        Events.writeEvent(volumeDialogImpl.mContext, 8, new Object[0]);
        Intent intent = new Intent("android.settings.SOUND_SETTINGS");
        intent.setFlags(268435456);
        volumeDialogImpl.dismissH(5);
        ((ActivityStarter) Dependency.get(ActivityStarter.class)).startActivity(intent, true);
    }

    public void initRingerH() {
        this.mRingerIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$leUR0c6hrY1TNx5XUG-xhXI1EHk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VolumeDialogImpl.lambda$initRingerH$5(this.f$0, view);
            }
        });
        updateRingerH();
    }

    public static /* synthetic */ void lambda$initRingerH$5(VolumeDialogImpl volumeDialogImpl, View v) {
        int newRingerMode;
        Prefs.putBoolean(volumeDialogImpl.mContext, "TouchedRingerToggle", true);
        VolumeDialogController.StreamState ss = volumeDialogImpl.mState.states.get(2);
        if (ss == null) {
            return;
        }
        boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
        if (volumeDialogImpl.mState.ringerModeInternal == 2) {
            if (hasVibrator) {
                newRingerMode = 1;
            } else {
                newRingerMode = 0;
            }
        } else if (volumeDialogImpl.mState.ringerModeInternal == 1) {
            newRingerMode = 0;
        } else {
            if (ss.level == 0) {
                volumeDialogImpl.mController.setStreamVolume(2, 1);
            }
            newRingerMode = 2;
        }
        Events.writeEvent(volumeDialogImpl.mContext, 18, Integer.valueOf(newRingerMode));
        volumeDialogImpl.incrementManualToggleCount();
        volumeDialogImpl.updateRingerH();
        volumeDialogImpl.provideTouchFeedbackH(newRingerMode);
        volumeDialogImpl.mController.setRingerMode(newRingerMode, false);
        volumeDialogImpl.maybeShowToastH(newRingerMode);
    }

    private void incrementManualToggleCount() {
        ContentResolver cr = this.mContext.getContentResolver();
        int ringerCount = Settings.Secure.getInt(cr, "manual_ringer_toggle_count", 0);
        Settings.Secure.putInt(cr, "manual_ringer_toggle_count", ringerCount + 1);
    }

    private void provideTouchFeedbackH(int newRingerMode) {
        VibrationEffect effect = null;
        if (newRingerMode == 0) {
            effect = VibrationEffect.get(0);
        } else if (newRingerMode == 2) {
            this.mController.scheduleTouchFeedback();
        } else {
            effect = VibrationEffect.get(1);
        }
        if (effect != null) {
            this.mController.vibrate(effect);
        }
    }

    private void maybeShowToastH(int newRingerMode) {
        int seenToastCount = Prefs.getInt(this.mContext, "RingerGuidanceCount", 0);
        if (seenToastCount > 12) {
            return;
        }
        CharSequence toastText = null;
        if (newRingerMode == 0) {
            toastText = this.mContext.getString(android.R.string.mobile_provisioning_url);
        } else if (newRingerMode == 2) {
            VolumeDialogController.StreamState ss = this.mState.states.get(2);
            if (ss != null) {
                toastText = this.mContext.getString(R.string.volume_dialog_ringer_guidance_ring, Utils.formatPercentage(ss.level, ss.levelMax));
            }
        } else {
            toastText = this.mContext.getString(android.R.string.month_day_year);
        }
        Toast.makeText(this.mContext, toastText, 0).show();
        Prefs.putInt(this.mContext, "RingerGuidanceCount", seenToastCount + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showH(int reason) {
        if (D.BUG) {
            Log.d(TAG, "showH r=" + Events.DISMISS_REASONS[reason]);
        }
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        rescheduleTimeoutH();
        this.mShowing = true;
        initSettingsH();
        this.mDialog.show();
        Events.writeEvent(this.mContext, 0, Integer.valueOf(reason), Boolean.valueOf(this.mKeyguard.isKeyguardLocked()));
        this.mController.notifyVisible(true);
    }

    protected void rescheduleTimeoutH() {
        this.mHandler.removeMessages(2);
        int timeout = computeTimeoutH();
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, 3, 0), timeout);
        if (D.BUG) {
            Log.d(TAG, "rescheduleTimeout " + timeout + " " + Debug.getCaller());
        }
        this.mController.userActivity();
    }

    private int computeTimeoutH() {
        if (this.mAccessibility.mFeedbackEnabled) {
            return 20000;
        }
        if (this.mHovering) {
            return 16000;
        }
        return this.mSafetyWarning != null ? 5000 : 3000;
    }

    protected void dismissH(int reason) {
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(1);
        this.mDialogView.animate().cancel();
        this.mShowing = false;
        this.mDialogView.setTranslationX(0.0f);
        this.mDialogView.setAlpha(1.0f);
        ViewPropertyAnimator animator = this.mDialogView.animate().alpha(0.0f).setDuration(250L).setInterpolator(new SystemUIInterpolators.LogAccelerateInterpolator()).withEndAction(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$TeBm8mGAiDkt4iXRVJq9pjcoyzA
            @Override // java.lang.Runnable
            public final void run() {
                VolumeDialogImpl.lambda$dismissH$6(this.f$0);
            }
        });
        if (!isLandscape()) {
            animator.translationX(this.mDialogView.getWidth() / 2);
        }
        animator.start();
        Events.writeEvent(this.mContext, 1, Integer.valueOf(reason));
        this.mController.notifyVisible(false);
        synchronized (this.mSafetyWarningLock) {
            if (this.mSafetyWarning != null) {
                if (D.BUG) {
                    Log.d(TAG, "SafetyWarning dismissed");
                }
                this.mSafetyWarning.dismiss();
            }
        }
    }

    public static /* synthetic */ void lambda$dismissH$6(VolumeDialogImpl volumeDialogImpl) {
        if (D.BUG) {
            Log.d(TAG, "mDialog.dismiss()");
        }
        volumeDialogImpl.mDialog.dismiss();
    }

    private boolean shouldBeVisibleH(VolumeRow row, VolumeRow activeRow) {
        boolean isActive = row.stream == activeRow.stream;
        if (row.stream == 10) {
            return this.mShowA11yStream;
        }
        if ((activeRow.stream == 10 && row.stream == this.mPrevActiveStream) || isActive) {
            return true;
        }
        if (row.defaultStream) {
            return activeRow.stream == 2 || activeRow.stream == 4 || activeRow.stream == 0 || activeRow.stream == 10 || this.mDynamic.get(activeRow.stream);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRowsH(VolumeRow activeRow) {
        if (D.BUG) {
            Log.d(TAG, "updateRowsH");
        }
        if (!this.mShowing) {
            trimObsoleteH();
        }
        Iterator<VolumeRow> it = this.mRows.iterator();
        while (it.hasNext()) {
            VolumeRow row = it.next();
            boolean isActive = row == activeRow;
            boolean shouldBeVisible = shouldBeVisibleH(row, activeRow);
            Util.setVisOrGone(row.view, shouldBeVisible);
            if (row.view.isShown()) {
                updateVolumeRowTintH(row, isActive);
            }
        }
    }

    protected void updateRingerH() {
        VolumeDialogController.StreamState ss;
        if (this.mState == null || (ss = this.mState.states.get(2)) == null) {
        }
        boolean isZenMuted = this.mState.zenMode == 3 || this.mState.zenMode == 2 || (this.mState.zenMode == 1 && this.mState.disallowRinger);
        enableRingerViewsH(!isZenMuted);
        switch (this.mState.ringerModeInternal) {
            case 0:
                this.mRingerIcon.setImageResource(R.drawable.ic_volume_ringer_mute);
                this.mRingerIcon.setTag(2);
                addAccessibilityDescription(this.mRingerIcon, 0, this.mContext.getString(R.string.volume_ringer_hint_unmute));
                break;
            case 1:
                this.mRingerIcon.setImageResource(R.drawable.ic_volume_ringer_vibrate);
                addAccessibilityDescription(this.mRingerIcon, 1, this.mContext.getString(R.string.volume_ringer_hint_mute));
                this.mRingerIcon.setTag(3);
                break;
            default:
                boolean muted = (this.mAutomute && ss.level == 0) || ss.muted;
                if (!isZenMuted && muted) {
                    this.mRingerIcon.setImageResource(R.drawable.ic_volume_ringer_mute);
                    addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_unmute));
                    this.mRingerIcon.setTag(2);
                } else {
                    this.mRingerIcon.setImageResource(R.drawable.ic_volume_ringer);
                    if (this.mController.hasVibrator()) {
                        addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_vibrate));
                    } else {
                        addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_mute));
                    }
                    this.mRingerIcon.setTag(1);
                }
                break;
        }
    }

    private void addAccessibilityDescription(View view, int currState, final String hintLabel) {
        int currStateResId;
        switch (currState) {
            case 0:
                currStateResId = R.string.volume_ringer_status_silent;
                break;
            case 1:
                currStateResId = R.string.volume_ringer_status_vibrate;
                break;
            default:
                currStateResId = R.string.volume_ringer_status_normal;
                break;
        }
        view.setContentDescription(this.mContext.getString(currStateResId));
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.VolumeDialogImpl.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, hintLabel));
            }
        });
    }

    private void enableVolumeRowViewsH(VolumeRow row, boolean enable) {
        boolean showDndIcon = !enable;
        row.dndIcon.setVisibility(showDndIcon ? 0 : 8);
    }

    private void enableRingerViewsH(boolean enable) {
        this.mRingerIcon.setEnabled(enable);
        this.mZenIcon.setVisibility(enable ? 8 : 0);
    }

    private void trimObsoleteH() {
        if (D.BUG) {
            Log.d(TAG, "trimObsoleteH");
        }
        for (int i = this.mRows.size() - 1; i >= 0; i--) {
            VolumeRow row = this.mRows.get(i);
            if (row.ss != null && row.ss.dynamic && !this.mDynamic.get(row.stream)) {
                this.mRows.remove(i);
                this.mDialogRowsView.removeView(row.view);
            }
        }
    }

    protected void onStateChangedH(VolumeDialogController.State state, int reason) {
        if (D.BUG) {
            Log.d(TAG, "onStateChangedH, activeStream = " + state.activeStream + ", reason = " + reason);
        }
        if (state.activeStream == VolumeDialogController.State.NO_ACTIVE_STREAM) {
            return;
        }
        if (this.mState != null && state != null && this.mState.ringerModeInternal != state.ringerModeInternal && state.ringerModeInternal == 1) {
            this.mController.vibrate(VibrationEffect.get(5));
        }
        this.mState = state;
        this.mDynamic.clear();
        for (int i = 0; i < state.states.size(); i++) {
            int stream = state.states.keyAt(i);
            VolumeDialogController.StreamState ss = state.states.valueAt(i);
            if (ss.dynamic) {
                this.mDynamic.put(stream, true);
                if (findRow(stream) == null) {
                    addRow(stream, R.drawable.ic_volume_remote, R.drawable.ic_volume_remote_mute, true, false, true);
                }
            }
        }
        int i2 = this.mActiveStream;
        if (i2 != state.activeStream) {
            this.mPrevActiveStream = this.mActiveStream;
            this.mActiveStream = state.activeStream;
            VolumeRow activeRow = getActiveRow();
            updateRowsH(activeRow);
            rescheduleTimeoutH();
        }
        for (VolumeRow row : this.mRows) {
            updateVolumeRowH(row);
        }
        updateRingerH();
        this.mWindow.setTitle(composeWindowTitle());
    }

    CharSequence composeWindowTitle() {
        return this.mContext.getString(R.string.volume_dialog_title, getStreamLabelH(getActiveRow().ss));
    }

    private void updateVolumeRowH(VolumeRow row) {
        VolumeDialogController.StreamState ss;
        int iconRes;
        boolean z;
        if (D.BUG) {
            Log.d(TAG, "updateVolumeRowH s=" + row.stream);
        }
        if (this.mState == null || (ss = this.mState.states.get(row.stream)) == null) {
            return;
        }
        row.ss = ss;
        if (ss.level > 0) {
            row.lastAudibleLevel = ss.level;
        }
        if (ss.level == row.requestedLevel) {
            row.requestedLevel = -1;
        }
        boolean isA11yStream = row.stream == 10;
        boolean isRingStream = row.stream == 2;
        boolean isSystemStream = row.stream == 1;
        boolean isAlarmStream = row.stream == 4;
        boolean isMusicStream = row.stream == 3;
        boolean isRingVibrate = isRingStream && this.mState.ringerModeInternal == 1;
        boolean isRingSilent = isRingStream && this.mState.ringerModeInternal == 0;
        boolean isZenPriorityOnly = this.mState.zenMode == 1;
        boolean isZenAlarms = this.mState.zenMode == 3;
        boolean isZenNone = this.mState.zenMode == 2;
        boolean zenMuted = !isZenAlarms ? !isZenNone ? isZenPriorityOnly && ((isAlarmStream && this.mState.disallowAlarms) || ((isMusicStream && this.mState.disallowMedia) || ((isRingStream && this.mState.disallowRinger) || (isSystemStream && this.mState.disallowSystem)))) : isRingStream || isSystemStream || isAlarmStream || isMusicStream : !(isRingStream || isSystemStream);
        int max = ss.levelMax * 100;
        if (max != row.slider.getMax()) {
            row.slider.setMax(max);
        }
        int min = ss.levelMin * 100;
        if (min != row.slider.getMin()) {
            row.slider.setMin(min);
        }
        if (!row.tracking && !this.mHandler.hasMessages(8) && row.requestedLevel == -1) {
            Util.setText(row.header, String.valueOf(ss.level));
        }
        row.slider.setContentDescription(row.header.getText());
        boolean iconEnabled = (this.mAutomute || ss.muteSupported) && !zenMuted;
        row.icon.setEnabled(iconEnabled);
        row.icon.setAlpha(iconEnabled ? 1.0f : 0.5f);
        if (isRingVibrate) {
            iconRes = R.drawable.ic_volume_ringer_vibrate;
        } else if (isRingSilent || zenMuted) {
            iconRes = row.iconMuteRes;
        } else if (ss.routedToBluetooth) {
            iconRes = ss.muted ? R.drawable.ic_volume_media_bt_mute : R.drawable.ic_volume_media_bt;
        } else {
            iconRes = ((this.mAutomute && ss.level == 0) || ss.muted) ? row.iconMuteRes : row.iconRes;
        }
        int i = 3;
        if (row.stream == 3 && isHiCarCertificate() && isHiCarNaviAudioStart()) {
            if (iconRes == row.iconRes) {
                iconRes = R.drawable.ic_volume_gis;
            } else if (iconRes == row.iconMuteRes) {
                iconRes = R.drawable.ic_volume_gis_mute;
            }
        }
        row.icon.setImageResource(iconRes);
        if (iconRes != R.drawable.ic_volume_ringer_vibrate) {
            if (iconRes == R.drawable.ic_volume_media_bt_mute || iconRes == row.iconMuteRes) {
                i = 2;
            } else {
                i = (iconRes == R.drawable.ic_volume_media_bt || iconRes == row.iconRes) ? 1 : 0;
            }
        }
        row.iconState = i;
        if (zenMuted) {
            z = false;
            row.tracking = false;
        } else {
            z = false;
        }
        enableVolumeRowViewsH(row, !zenMuted ? true : z);
        boolean enableSlider = !zenMuted ? true : z;
        int vlevel = (!row.ss.muted || isRingStream || zenMuted) ? row.ss.level : 0;
        updateVolumeRowSliderH(row, enableSlider, vlevel);
    }

    private void updateVolumeRowTintH(VolumeRow row, boolean isActive) {
        if (isActive) {
            row.slider.requestFocus();
        }
        boolean useActiveColoring = isActive && row.slider.isEnabled();
        ColorStateList tint = useActiveColoring ? this.mActiveTint : this.mInactiveTint;
        int alpha = useActiveColoring ? this.mActiveAlpha : this.mInactiveAlpha;
        if (tint == row.cachedTint) {
            return;
        }
        row.slider.setAlpha(alpha / 255.0f);
        row.icon.setImageTintList(tint);
        row.icon.setImageAlpha(alpha);
        row.cachedTint = tint;
    }

    private void updateVolumeRowSliderH(VolumeRow row, boolean enable, int vlevel) {
        int newProgress;
        row.slider.setEnabled(enable);
        updateVolumeRowTintH(row, row.stream == this.mActiveStream);
        if (row.tracking) {
            return;
        }
        int progress = row.slider.getProgress();
        int level = getImpliedLevel(row.slider, progress);
        boolean rowVisible = row.view.getVisibility() == 0;
        boolean inGracePeriod = SystemClock.uptimeMillis() - row.userAttempt < 1000;
        this.mHandler.removeMessages(3, row);
        if (this.mShowing && rowVisible && inGracePeriod) {
            if (D.BUG) {
                Log.d(TAG, "inGracePeriod");
            }
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(3, row), row.userAttempt + 1000);
            return;
        }
        if ((vlevel != level || !this.mShowing || !rowVisible) && progress != (newProgress = vlevel * 100)) {
            if (!this.mShowing || !rowVisible) {
                if (row.anim != null) {
                    row.anim.cancel();
                }
                row.slider.setProgress(newProgress, true);
            } else {
                if (row.anim != null && row.anim.isRunning() && row.animTargetProgress == newProgress) {
                    return;
                }
                if (row.anim == null) {
                    row.anim = ObjectAnimator.ofInt(row.slider, "progress", progress, newProgress);
                    row.anim.setInterpolator(new DecelerateInterpolator());
                } else {
                    row.anim.cancel();
                    row.anim.setIntValues(progress, newProgress);
                }
                row.animTargetProgress = newProgress;
                row.anim.setDuration(80L);
                row.anim.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recheckH(VolumeRow row) {
        if (row == null) {
            if (D.BUG) {
                Log.d(TAG, "recheckH ALL");
            }
            trimObsoleteH();
            for (VolumeRow r : this.mRows) {
                updateVolumeRowH(r);
            }
            return;
        }
        if (D.BUG) {
            Log.d(TAG, "recheckH " + row.stream);
        }
        updateVolumeRowH(row);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStreamImportantH(int stream, boolean important) {
        for (VolumeRow row : this.mRows) {
            if (row.stream == stream) {
                row.important = important;
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSafetyWarningH(int flags) {
        if ((flags & 1025) != 0 || this.mShowing) {
            synchronized (this.mSafetyWarningLock) {
                if (this.mSafetyWarning != null) {
                    return;
                }
                this.mSafetyWarning = new SafetyWarningDialog(this.mContext, this.mController.getAudioManager()) { // from class: com.android.systemui.volume.VolumeDialogImpl.2
                    @Override // com.android.systemui.volume.SafetyWarningDialog
                    protected void cleanUp() {
                        synchronized (VolumeDialogImpl.this.mSafetyWarningLock) {
                            VolumeDialogImpl.this.mSafetyWarning = null;
                        }
                        VolumeDialogImpl.this.recheckH(null);
                    }
                };
                this.mSafetyWarning.show();
                recheckH(null);
            }
        }
        rescheduleTimeoutH();
    }

    private String getStreamLabelH(VolumeDialogController.StreamState ss) {
        if (ss == null) {
            return "";
        }
        if (ss.remoteLabel != null) {
            return ss.remoteLabel;
        }
        try {
            return this.mContext.getResources().getString(ss.name);
        } catch (Resources.NotFoundException e) {
            Slog.e(TAG, "Can't find translation for stream " + ss);
            return "";
        }
    }

    private final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    VolumeDialogImpl.this.showH(msg.arg1);
                    break;
                case 2:
                    VolumeDialogImpl.this.dismissH(msg.arg1);
                    break;
                case 3:
                    VolumeDialogImpl.this.recheckH((VolumeRow) msg.obj);
                    break;
                case 4:
                    VolumeDialogImpl.this.recheckH(null);
                    break;
                case 5:
                    VolumeDialogImpl.this.setStreamImportantH(msg.arg1, msg.arg2 != 0);
                    break;
                case 6:
                    VolumeDialogImpl.this.rescheduleTimeoutH();
                    break;
                case 7:
                    VolumeDialogImpl.this.onStateChangedH(VolumeDialogImpl.this.mState, 1);
                    break;
                case 8:
                    VolumeDialogImpl.this.onSetStreamVolume((VolumeRow) msg.obj, msg.arg1);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CustomDialog extends Dialog implements DialogInterface {
        public CustomDialog(Context context) {
            super(context, R.style.qs_theme);
        }

        @Override // android.app.Dialog, android.view.Window.Callback
        public boolean dispatchTouchEvent(MotionEvent ev) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.dispatchTouchEvent(ev);
        }

        @Override // android.app.Dialog
        protected void onStart() {
            super.setCanceledOnTouchOutside(false);
            super.onStart();
        }

        @Override // android.app.Dialog
        protected void onStop() {
            super.onStop();
            VolumeDialogImpl.this.mHandler.sendEmptyMessage(4);
        }

        @Override // android.app.Dialog
        public boolean onTouchEvent(MotionEvent event) {
            if (!isShowing() || event.getAction() != 4) {
                return false;
            }
            VolumeDialogImpl.this.mHandler.removeMessages(2);
            VolumeDialogImpl.this.mHandler.sendMessageDelayed(VolumeDialogImpl.this.mHandler.obtainMessage(2, 1, 0), 400L);
            return true;
        }
    }

    private final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private final VolumeRow mRow;

        private VolumeSeekBarChangeListener(VolumeRow row) {
            this.mRow = row;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int minProgress;
            if (this.mRow.ss != null && fromUser) {
                if (this.mRow.ss.levelMin > 0 && progress < (minProgress = this.mRow.ss.levelMin * 100)) {
                    seekBar.setProgress(minProgress);
                    progress = minProgress;
                }
                int userLevel = VolumeDialogImpl.getImpliedLevel(seekBar, progress);
                if (this.mRow.ss.level == userLevel && (!this.mRow.ss.muted || userLevel <= 0)) {
                    return;
                }
                boolean isIdle = SystemClock.uptimeMillis() - this.mRow.lastSyncVolTime > 500;
                this.mRow.userAttempt = SystemClock.uptimeMillis();
                if (this.mRow.requestedLevel != userLevel) {
                    if (D.BUG) {
                        Log.d(VolumeDialogImpl.TAG, AudioSystem.streamToString(this.mRow.stream) + " onProgressChanged: userLevel = " + userLevel + " fromUser = " + fromUser);
                    }
                    Util.setText(this.mRow.header, String.valueOf(userLevel));
                    VolumeDialogImpl.this.mHandler.removeMessages(8);
                    if ((!this.mRow.ss.muted || userLevel <= 0) && !isIdle) {
                        VolumeDialogImpl.this.mHandler.sendMessageDelayed(VolumeDialogImpl.this.mHandler.obtainMessage(8, userLevel, 0, this.mRow), 250L);
                        return;
                    }
                    this.mRow.requestedLevel = userLevel;
                    this.mRow.lastSyncVolTime = SystemClock.uptimeMillis();
                    VolumeDialogImpl.this.mController.setStreamVolume(this.mRow.stream, userLevel);
                    if (3 == this.mRow.stream) {
                        VolumeDialogImpl.this.mController.setStreamVolume(4, userLevel);
                    }
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (D.BUG) {
                Log.d(VolumeDialogImpl.TAG, "onStartTrackingTouch " + this.mRow.stream);
            }
            VolumeDialogImpl.this.mController.setActiveStream(this.mRow.stream);
            this.mRow.tracking = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (D.BUG) {
                Log.d(VolumeDialogImpl.TAG, "onStopTrackingTouch " + this.mRow.stream);
            }
            this.mRow.tracking = false;
            this.mRow.userAttempt = SystemClock.uptimeMillis();
            int userLevel = VolumeDialogImpl.getImpliedLevel(seekBar, seekBar.getProgress());
            if (this.mRow.ss != null && this.mRow.ss.level != userLevel) {
                VolumeDialogImpl.this.mHandler.sendMessageDelayed(VolumeDialogImpl.this.mHandler.obtainMessage(3, this.mRow), 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSetStreamVolume(VolumeRow row, int userLevel) {
        row.requestedLevel = userLevel;
        row.lastSyncVolTime = SystemClock.uptimeMillis();
        this.mController.setStreamVolume(row.stream, userLevel);
        if (3 == row.stream) {
            this.mController.setStreamVolume(4, userLevel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class Accessibility extends View.AccessibilityDelegate {
        private boolean mFeedbackEnabled;
        private final AccessibilityManager.AccessibilityServicesStateChangeListener mListener;

        private Accessibility() {
            this.mListener = new AccessibilityManager.AccessibilityServicesStateChangeListener() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogImpl$Accessibility$kxl9eO7EXO-qLbFSXepX3MznHcY
                @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
                public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
                    this.f$0.updateFeedbackEnabled();
                }
            };
        }

        public void init() {
            VolumeDialogImpl.this.mDialogView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.volume.VolumeDialogImpl.Accessibility.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                    if (D.BUG) {
                        Log.d(VolumeDialogImpl.TAG, "onViewDetachedFromWindow");
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                    if (D.BUG) {
                        Log.d(VolumeDialogImpl.TAG, "onViewAttachedToWindow");
                    }
                    Accessibility.this.updateFeedbackEnabled();
                }
            });
            VolumeDialogImpl.this.mDialogView.setAccessibilityDelegate(this);
            VolumeDialogImpl.this.mAccessibilityMgr.addCallback(this.mListener);
            updateFeedbackEnabled();
        }

        public void destroy() {
            VolumeDialogImpl.this.mAccessibilityMgr.removeCallback(this.mListener);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            event.getText().add(VolumeDialogImpl.this.composeWindowTitle());
            return true;
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.onRequestSendAccessibilityEvent(host, child, event);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateFeedbackEnabled() {
            this.mFeedbackEnabled = computeFeedbackEnabled();
        }

        private boolean computeFeedbackEnabled() {
            List<AccessibilityServiceInfo> services = VolumeDialogImpl.this.mAccessibilityMgr.getEnabledAccessibilityServiceList(-1);
            for (AccessibilityServiceInfo asi : services) {
                if (asi.feedbackType != 0 && asi.feedbackType != 16) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VolumeRow {
        private ObjectAnimator anim;
        private int animTargetProgress;
        private ColorStateList cachedTint;
        private boolean defaultStream;
        private FrameLayout dndIcon;
        private TextView header;
        private ImageButton icon;
        private int iconMuteRes;
        private int iconRes;
        private int iconState;
        private boolean important;
        private int lastAudibleLevel;
        private long lastSyncVolTime;
        private int requestedLevel;
        private SeekBar slider;
        private VolumeDialogController.StreamState ss;
        private int stream;
        private boolean tracking;
        private long userAttempt;
        private View view;

        private VolumeRow() {
            this.requestedLevel = -1;
            this.lastAudibleLevel = 1;
        }
    }

    private boolean isHiCarNaviAudioStart() {
        return AudioSystem.isStreamActive(5, 0) || AudioSystem.isStreamActiveRemotely(5, 0);
    }

    private boolean isHiCarCertificate() {
        return this.is_hicar_certificate;
    }
}
