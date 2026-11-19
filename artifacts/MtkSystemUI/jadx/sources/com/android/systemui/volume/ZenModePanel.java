package com.android.systemui.volume;

import android.animation.LayoutTransition;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ZenModePanel extends FrameLayout {
    private boolean mAttached;
    private int mAttachedZen;
    private View mAutoRule;
    private TextView mAutoTitle;
    private int mBucketIndex;
    private Callback mCallback;
    private final ConfigurableTexts mConfigurableTexts;
    private final Context mContext;
    private ZenModeController mController;
    private ViewGroup mEdit;
    private View mEmpty;
    private ImageView mEmptyIcon;
    private TextView mEmptyText;
    private Condition mExitCondition;
    private boolean mExpanded;
    private final Uri mForeverId;
    private final H mHandler;
    private boolean mHidden;
    protected final LayoutInflater mInflater;
    private final Interaction.Callback mInteractionCallback;
    private final ZenPrefs mPrefs;
    private Condition mSessionExitCondition;
    private int mSessionZen;
    private int mState;
    private String mTag;
    private final TransitionHelper mTransitionHelper;
    private boolean mVoiceCapable;
    private TextView mZenAlarmWarning;
    protected SegmentedButtons mZenButtons;
    protected final SegmentedButtons.Callback mZenButtonsCallback;
    private final ZenModeController.Callback mZenCallback;
    protected LinearLayout mZenConditions;
    private View mZenIntroduction;
    private View mZenIntroductionConfirm;
    private TextView mZenIntroductionCustomize;
    private TextView mZenIntroductionMessage;
    protected int mZenModeButtonLayoutId;
    protected int mZenModeConditionLayoutId;
    private RadioGroup mZenRadioGroup;
    private LinearLayout mZenRadioGroupContent;
    private static final boolean DEBUG = Log.isLoggable("ZenModePanel", 3);
    private static final int[] MINUTE_BUCKETS = ZenModeConfig.MINUTE_BUCKETS;
    private static final int MIN_BUCKET_MINUTES = MINUTE_BUCKETS[0];
    private static final int MAX_BUCKET_MINUTES = MINUTE_BUCKETS[MINUTE_BUCKETS.length - 1];
    private static final int DEFAULT_BUCKET_INDEX = Arrays.binarySearch(MINUTE_BUCKETS, 60);
    public static final Intent ZEN_SETTINGS = new Intent("android.settings.ZEN_MODE_SETTINGS");
    public static final Intent ZEN_PRIORITY_SETTINGS = new Intent("android.settings.ZEN_MODE_PRIORITY_SETTINGS");

    public interface Callback {
        void onExpanded(boolean z);

        void onInteraction();

        void onPrioritySettings();
    }

    public ZenModePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHandler = new H();
        this.mTransitionHelper = new TransitionHelper();
        this.mTag = "ZenModePanel/" + Integer.toHexString(System.identityHashCode(this));
        this.mBucketIndex = -1;
        this.mState = 0;
        this.mZenCallback = new ZenModeController.Callback() { // from class: com.android.systemui.volume.ZenModePanel.6
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public void onManualRuleChanged(ZenModeConfig.ZenRule rule) {
                ZenModePanel.this.mHandler.obtainMessage(2, rule).sendToTarget();
            }
        };
        this.mZenButtonsCallback = new SegmentedButtons.Callback() { // from class: com.android.systemui.volume.ZenModePanel.7
            @Override // com.android.systemui.volume.SegmentedButtons.Callback
            public void onSelected(Object value, boolean fromClick) {
                if (value != null && ZenModePanel.this.mZenButtons.isShown() && ZenModePanel.this.isAttachedToWindow()) {
                    final int zen = ((Integer) value).intValue();
                    if (fromClick) {
                        MetricsLogger.action(ZenModePanel.this.mContext, 165, zen);
                    }
                    if (ZenModePanel.DEBUG) {
                        Log.d(ZenModePanel.this.mTag, "mZenButtonsCallback selected=" + zen);
                    }
                    final Uri realConditionId = ZenModePanel.this.getRealConditionId(ZenModePanel.this.mSessionExitCondition);
                    AsyncTask.execute(new Runnable() { // from class: com.android.systemui.volume.ZenModePanel.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ZenModePanel.this.mController.setZen(zen, realConditionId, "ZenModePanel.selectZen");
                            if (zen != 0) {
                                Prefs.putInt(ZenModePanel.this.mContext, "DndFavoriteZen", zen);
                            }
                        }
                    });
                }
            }

            @Override // com.android.systemui.volume.Interaction.Callback
            public void onInteraction() {
                ZenModePanel.this.fireInteraction();
            }
        };
        this.mInteractionCallback = new Interaction.Callback() { // from class: com.android.systemui.volume.ZenModePanel.8
            @Override // com.android.systemui.volume.Interaction.Callback
            public void onInteraction() {
                ZenModePanel.this.fireInteraction();
            }
        };
        this.mContext = context;
        this.mPrefs = new ZenPrefs();
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mForeverId = Condition.newId(this.mContext).appendPath("forever").build();
        this.mConfigurableTexts = new ConfigurableTexts(this.mContext);
        this.mVoiceCapable = Util.isVoiceCapable(this.mContext);
        this.mZenModeConditionLayoutId = R.layout.zen_mode_condition;
        this.mZenModeButtonLayoutId = R.layout.zen_mode_button;
        if (DEBUG) {
            Log.d(this.mTag, "new ZenModePanel");
        }
    }

    protected void createZenButtons() {
        this.mZenButtons = (SegmentedButtons) findViewById(R.id.zen_buttons);
        this.mZenButtons.addButton(R.string.interruption_level_none_twoline, R.string.interruption_level_none_with_warning, 2);
        this.mZenButtons.addButton(R.string.interruption_level_alarms_twoline, R.string.interruption_level_alarms, 3);
        this.mZenButtons.addButton(R.string.interruption_level_priority_twoline, R.string.interruption_level_priority, 1);
        this.mZenButtons.setCallback(this.mZenButtonsCallback);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        createZenButtons();
        this.mZenIntroduction = findViewById(R.id.zen_introduction);
        this.mZenIntroductionMessage = (TextView) findViewById(R.id.zen_introduction_message);
        this.mZenIntroductionConfirm = findViewById(R.id.zen_introduction_confirm);
        this.mZenIntroductionConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$lbJ8lHqFYfMZus-ckwTZAx6gp_I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.confirmZenIntroduction();
            }
        });
        this.mZenIntroductionCustomize = (TextView) findViewById(R.id.zen_introduction_customize);
        this.mZenIntroductionCustomize.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$1BYa_z9Fn3nPDHjhUKHednhVOqQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ZenModePanel.lambda$onFinishInflate$1(this.f$0, view);
            }
        });
        this.mConfigurableTexts.add(this.mZenIntroductionCustomize, R.string.zen_priority_customize_button);
        this.mZenConditions = (LinearLayout) findViewById(R.id.zen_conditions);
        this.mZenAlarmWarning = (TextView) findViewById(R.id.zen_alarm_warning);
        this.mZenRadioGroup = (RadioGroup) findViewById(R.id.zen_radio_buttons);
        this.mZenRadioGroupContent = (LinearLayout) findViewById(R.id.zen_radio_buttons_content);
        this.mEdit = (ViewGroup) findViewById(R.id.edit_container);
        this.mEmpty = findViewById(android.R.id.empty);
        this.mEmpty.setVisibility(4);
        this.mEmptyText = (TextView) this.mEmpty.findViewById(android.R.id.title);
        this.mEmptyIcon = (ImageView) this.mEmpty.findViewById(android.R.id.icon);
        this.mAutoRule = findViewById(R.id.auto_rule);
        this.mAutoTitle = (TextView) this.mAutoRule.findViewById(android.R.id.title);
        this.mAutoRule.setVisibility(4);
    }

    public static /* synthetic */ void lambda$onFinishInflate$1(ZenModePanel zenModePanel, View v) {
        zenModePanel.confirmZenIntroduction();
        if (zenModePanel.mCallback != null) {
            zenModePanel.mCallback.onPrioritySettings();
        }
    }

    public void setEmptyState(final int icon, final int text) {
        this.mEmptyIcon.post(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$HiD6qQcUVG9hPBXBbXjbkowbyWE
            @Override // java.lang.Runnable
            public final void run() {
                ZenModePanel.lambda$setEmptyState$2(this.f$0, icon, text);
            }
        });
    }

    public static /* synthetic */ void lambda$setEmptyState$2(ZenModePanel zenModePanel, int icon, int text) {
        zenModePanel.mEmptyIcon.setImageResource(icon);
        zenModePanel.mEmptyText.setText(text);
    }

    public void setAutoText(final CharSequence text) {
        this.mAutoTitle.post(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$B3Y2r55PL6J4kgbiM4zXPpDTjiA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mAutoTitle.setText(text);
            }
        });
    }

    public void setState(int state) {
        if (this.mState == state) {
            return;
        }
        transitionFrom(getView(this.mState), getView(state));
        this.mState = state;
    }

    private void transitionFrom(final View from, final View to) {
        from.post(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$BhXvHb7L6APT_cYYehmMxR3OZv4
            @Override // java.lang.Runnable
            public final void run() {
                ZenModePanel.lambda$transitionFrom$5(to, from);
            }
        });
    }

    static /* synthetic */ void lambda$transitionFrom$5(View to, final View from) {
        to.setAlpha(0.0f);
        to.setVisibility(0);
        to.bringToFront();
        to.animate().cancel();
        to.animate().alpha(1.0f).setDuration(300L).withEndAction(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$ZenModePanel$YCGXBCe2GBC47ckivA_D9jTXkLc
            @Override // java.lang.Runnable
            public final void run() {
                from.setVisibility(4);
            }
        }).start();
    }

    private View getView(int state) {
        switch (state) {
            case 1:
                return this.mAutoRule;
            case 2:
                return this.mEmpty;
            default:
                return this.mEdit;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mConfigurableTexts.update();
        if (this.mZenButtons != null) {
            this.mZenButtons.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmZenIntroduction() {
        String prefKey = prefKeyForConfirmation(getSelectedZen(0));
        if (prefKey == null) {
            return;
        }
        if (DEBUG) {
            Log.d("ZenModePanel", "confirmZenIntroduction " + prefKey);
        }
        Prefs.putBoolean(this.mContext, prefKey, true);
        this.mHandler.sendEmptyMessage(3);
    }

    private static String prefKeyForConfirmation(int zen) {
        switch (zen) {
            case 1:
                return "DndConfirmedPriorityIntroduction";
            case 2:
                return "DndConfirmedSilenceIntroduction";
            case 3:
                return "DndConfirmedAlarmIntroduction";
            default:
                return null;
        }
    }

    private void onAttach() {
        setExpanded(true);
        this.mAttachedZen = this.mController.getZen();
        ZenModeConfig.ZenRule manualRule = this.mController.getManualRule();
        this.mExitCondition = manualRule != null ? manualRule.condition : null;
        if (DEBUG) {
            Log.d(this.mTag, "onAttach " + this.mAttachedZen + " " + manualRule);
        }
        handleUpdateManualRule(manualRule);
        this.mZenButtons.setSelectedValue(Integer.valueOf(this.mAttachedZen), false);
        this.mSessionZen = this.mAttachedZen;
        this.mTransitionHelper.clear();
        this.mController.addCallback(this.mZenCallback);
        setSessionExitCondition(copy(this.mExitCondition));
        updateWidgets();
        setAttached(true);
    }

    private void onDetach() {
        if (DEBUG) {
            Log.d(this.mTag, "onDetach");
        }
        setExpanded(false);
        checkForAttachedZenChange();
        setAttached(false);
        this.mAttachedZen = -1;
        this.mSessionZen = -1;
        this.mController.removeCallback(this.mZenCallback);
        setSessionExitCondition(null);
        this.mTransitionHelper.clear();
    }

    @VisibleForTesting
    void setAttached(boolean attached) {
        this.mAttached = attached;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible == this.mAttached) {
            return;
        }
        if (isVisible) {
            onAttach();
        } else {
            onDetach();
        }
    }

    private void setSessionExitCondition(Condition condition) {
        if (Objects.equals(condition, this.mSessionExitCondition)) {
            return;
        }
        if (DEBUG) {
            Log.d(this.mTag, "mSessionExitCondition=" + getConditionId(condition));
        }
        this.mSessionExitCondition = condition;
    }

    private void checkForAttachedZenChange() {
        int selectedZen = getSelectedZen(-1);
        if (DEBUG) {
            Log.d(this.mTag, "selectedZen=" + selectedZen);
        }
        if (selectedZen != this.mAttachedZen) {
            if (DEBUG) {
                Log.d(this.mTag, "attachedZen: " + this.mAttachedZen + " -> " + selectedZen);
            }
            if (selectedZen == 2) {
                this.mPrefs.trackNoneSelected();
            }
        }
    }

    private void setExpanded(boolean expanded) {
        if (expanded == this.mExpanded) {
            return;
        }
        if (DEBUG) {
            Log.d(this.mTag, "setExpanded " + expanded);
        }
        this.mExpanded = expanded;
        updateWidgets();
        fireExpanded();
    }

    protected void addZenConditions(int count) {
        for (int i = 0; i < count; i++) {
            View rb = this.mInflater.inflate(this.mZenModeButtonLayoutId, this.mEdit, false);
            rb.setId(i);
            this.mZenRadioGroup.addView(rb);
            View rbc = this.mInflater.inflate(this.mZenModeConditionLayoutId, this.mEdit, false);
            rbc.setId(i + count);
            this.mZenRadioGroupContent.addView(rbc);
        }
    }

    public void init(ZenModeController controller) {
        this.mController = controller;
        addZenConditions(3);
        this.mSessionZen = getSelectedZen(-1);
        handleUpdateManualRule(this.mController.getManualRule());
        if (DEBUG) {
            Log.d(this.mTag, "init mExitCondition=" + this.mExitCondition);
        }
        hideAllConditions();
    }

    private void setExitCondition(Condition exitCondition) {
        if (Objects.equals(this.mExitCondition, exitCondition)) {
            return;
        }
        this.mExitCondition = exitCondition;
        if (DEBUG) {
            Log.d(this.mTag, "mExitCondition=" + getConditionId(this.mExitCondition));
        }
        updateWidgets();
    }

    private static Uri getConditionId(Condition condition) {
        if (condition != null) {
            return condition.id;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Uri getRealConditionId(Condition condition) {
        if (isForever(condition)) {
            return null;
        }
        return getConditionId(condition);
    }

    private static Condition copy(Condition condition) {
        if (condition == null) {
            return null;
        }
        return condition.copy();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @VisibleForTesting
    void handleUpdateManualRule(ZenModeConfig.ZenRule rule) {
        Condition c;
        int zen = rule != null ? rule.zenMode : 0;
        handleUpdateZen(zen);
        if (rule == null) {
            c = null;
        } else {
            c = rule.condition != null ? rule.condition : createCondition(rule.conditionId);
        }
        handleUpdateConditions(c);
        setExitCondition(c);
    }

    private Condition createCondition(Uri conditionId) {
        if (ZenModeConfig.isValidCountdownToAlarmConditionId(conditionId)) {
            Condition c = ZenModeConfig.toNextAlarmCondition(this.mContext, ZenModeConfig.tryParseCountdownConditionId(conditionId), ActivityManager.getCurrentUser());
            return c;
        }
        if (ZenModeConfig.isValidCountdownConditionId(conditionId)) {
            long time = ZenModeConfig.tryParseCountdownConditionId(conditionId);
            int mins = (int) (((time - System.currentTimeMillis()) + 30000) / 60000);
            Condition c2 = ZenModeConfig.toTimeCondition(this.mContext, time, mins, ActivityManager.getCurrentUser(), false);
            return c2;
        }
        return forever();
    }

    private void handleUpdateZen(int zen) {
        if (this.mSessionZen != -1 && this.mSessionZen != zen) {
            this.mSessionZen = zen;
        }
        this.mZenButtons.setSelectedValue(Integer.valueOf(zen), false);
        updateWidgets();
    }

    @VisibleForTesting
    int getSelectedZen(int defValue) {
        Object zen = this.mZenButtons.getSelectedValue();
        return zen != null ? ((Integer) zen).intValue() : defValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWidgets() {
        boolean zenImportant;
        boolean zenNone;
        boolean zenAlarm;
        int message;
        if (this.mTransitionHelper.isTransitioning()) {
            this.mTransitionHelper.pendingUpdateWidgets();
            return;
        }
        int zen = getSelectedZen(0);
        boolean introduction = true;
        if (zen != 1) {
            zenImportant = false;
        } else {
            zenImportant = true;
        }
        if (zen != 2) {
            zenNone = false;
        } else {
            zenNone = true;
        }
        if (zen != 3) {
            zenAlarm = false;
        } else {
            zenAlarm = true;
        }
        if ((!zenImportant || this.mPrefs.mConfirmedPriorityIntroduction) && ((!zenNone || this.mPrefs.mConfirmedSilenceIntroduction) && (!zenAlarm || this.mPrefs.mConfirmedAlarmIntroduction))) {
            introduction = false;
        }
        this.mZenButtons.setVisibility(this.mHidden ? 8 : 0);
        this.mZenIntroduction.setVisibility(introduction ? 0 : 8);
        if (introduction) {
            if (zenImportant) {
                message = R.string.zen_priority_introduction;
            } else if (zenAlarm) {
                message = R.string.zen_alarms_introduction;
            } else if (this.mVoiceCapable) {
                message = R.string.zen_silence_introduction_voice;
            } else {
                message = R.string.zen_silence_introduction;
            }
            this.mConfigurableTexts.add(this.mZenIntroductionMessage, message);
            this.mConfigurableTexts.update();
            this.mZenIntroductionCustomize.setVisibility(zenImportant ? 0 : 8);
        }
        String warning = computeAlarmWarningText(zenNone);
        this.mZenAlarmWarning.setVisibility(warning == null ? 8 : 0);
        this.mZenAlarmWarning.setText(warning);
    }

    private String computeAlarmWarningText(boolean zenNone) {
        String skeleton;
        if (!zenNone) {
            return null;
        }
        long now = System.currentTimeMillis();
        long nextAlarm = this.mController.getNextAlarm();
        if (nextAlarm < now) {
            return null;
        }
        int warningRes = 0;
        if (this.mSessionExitCondition == null || isForever(this.mSessionExitCondition)) {
            warningRes = R.string.zen_alarm_warning_indef;
        } else {
            long time = ZenModeConfig.tryParseCountdownConditionId(this.mSessionExitCondition.id);
            if (time > now && nextAlarm < time) {
                warningRes = R.string.zen_alarm_warning;
            }
        }
        if (warningRes == 0) {
            return null;
        }
        boolean soon = nextAlarm - now < 86400000;
        boolean is24 = DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser());
        if (soon) {
            skeleton = is24 ? "Hm" : "hma";
        } else {
            skeleton = is24 ? "EEEHm" : "EEEhma";
        }
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton);
        CharSequence formattedTime = DateFormat.format(pattern, nextAlarm);
        int templateRes = soon ? R.string.alarm_template : R.string.alarm_template_far;
        String template = getResources().getString(templateRes, formattedTime);
        return getResources().getString(warningRes, template);
    }

    @VisibleForTesting
    void handleUpdateConditions(Condition c) {
        if (this.mTransitionHelper.isTransitioning()) {
            return;
        }
        bind(forever(), this.mZenRadioGroupContent.getChildAt(0), 0);
        if (c == null) {
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
        } else if (isForever(c)) {
            getConditionTagAt(0).rb.setChecked(true);
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
        } else if (isAlarm(c)) {
            bindGenericCountdown();
            bindNextAlarm(c);
            getConditionTagAt(2).rb.setChecked(true);
        } else if (isCountdown(c)) {
            bindNextAlarm(getTimeUntilNextAlarmCondition());
            bind(c, this.mZenRadioGroupContent.getChildAt(1), 1);
            getConditionTagAt(1).rb.setChecked(true);
        } else {
            Slog.wtf("ZenModePanel", "Invalid manual condition: " + c);
        }
        this.mZenConditions.setVisibility(this.mSessionZen == 0 ? 8 : 0);
    }

    private void bindGenericCountdown() {
        this.mBucketIndex = DEFAULT_BUCKET_INDEX;
        Condition countdown = ZenModeConfig.toTimeCondition(this.mContext, MINUTE_BUCKETS[this.mBucketIndex], ActivityManager.getCurrentUser());
        if (!this.mAttached || getConditionTagAt(1).condition == null) {
            bind(countdown, this.mZenRadioGroupContent.getChildAt(1), 1);
        }
    }

    private void bindNextAlarm(Condition c) {
        View alarmContent = this.mZenRadioGroupContent.getChildAt(2);
        ConditionTag tag = (ConditionTag) alarmContent.getTag();
        if (c != null && (!this.mAttached || tag == null || tag.condition == null)) {
            bind(c, alarmContent, 2);
        }
        ConditionTag tag2 = (ConditionTag) alarmContent.getTag();
        int i = 0;
        boolean showAlarm = (tag2 == null || tag2.condition == null) ? false : true;
        this.mZenRadioGroup.getChildAt(2).setVisibility(showAlarm ? 0 : 4);
        if (!showAlarm) {
            i = 4;
        }
        alarmContent.setVisibility(i);
    }

    private Condition forever() {
        return new Condition(this.mForeverId, foreverSummary(this.mContext), "", "", 0, 1, 0);
    }

    private static String foreverSummary(Context context) {
        return context.getString(android.R.string.orgTypeCustom);
    }

    private Condition getTimeUntilNextAlarmCondition() {
        GregorianCalendar weekRange = new GregorianCalendar();
        setToMidnight(weekRange);
        weekRange.add(5, 6);
        long nextAlarmMs = this.mController.getNextAlarm();
        if (nextAlarmMs > 0) {
            GregorianCalendar nextAlarm = new GregorianCalendar();
            nextAlarm.setTimeInMillis(nextAlarmMs);
            setToMidnight(nextAlarm);
            if (weekRange.compareTo((Calendar) nextAlarm) >= 0) {
                return ZenModeConfig.toNextAlarmCondition(this.mContext, nextAlarmMs, ActivityManager.getCurrentUser());
            }
            return null;
        }
        return null;
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }

    @VisibleForTesting
    ConditionTag getConditionTagAt(int index) {
        return (ConditionTag) this.mZenRadioGroupContent.getChildAt(index).getTag();
    }

    @VisibleForTesting
    int getVisibleConditions() {
        int N = this.mZenRadioGroupContent.getChildCount();
        int rt = 0;
        for (int rt2 = 0; rt2 < N; rt2++) {
            rt += this.mZenRadioGroupContent.getChildAt(rt2).getVisibility() == 0 ? 1 : 0;
        }
        return rt;
    }

    private void hideAllConditions() {
        int N = this.mZenRadioGroupContent.getChildCount();
        for (int i = 0; i < N; i++) {
            this.mZenRadioGroupContent.getChildAt(i).setVisibility(8);
        }
    }

    private static boolean isAlarm(Condition c) {
        return c != null && ZenModeConfig.isValidCountdownToAlarmConditionId(c.id);
    }

    private static boolean isCountdown(Condition c) {
        return c != null && ZenModeConfig.isValidCountdownConditionId(c.id);
    }

    private boolean isForever(Condition c) {
        return c != null && this.mForeverId.equals(c.id);
    }

    private void bind(Condition condition, final View row, final int rowId) {
        if (condition == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        boolean enabled = condition.state == 1;
        final ConditionTag tag = row.getTag() != null ? (ConditionTag) row.getTag() : new ConditionTag();
        row.setTag(tag);
        boolean first = tag.rb == null;
        if (tag.rb == null) {
            tag.rb = (RadioButton) this.mZenRadioGroup.getChildAt(rowId);
        }
        tag.condition = condition;
        final Uri conditionId = getConditionId(tag.condition);
        if (DEBUG) {
            Log.d(this.mTag, "bind i=" + this.mZenRadioGroupContent.indexOfChild(row) + " first=" + first + " condition=" + conditionId);
        }
        tag.rb.setEnabled(enabled);
        tag.rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.volume.ZenModePanel.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ZenModePanel.this.mExpanded && isChecked) {
                    tag.rb.setChecked(true);
                    if (ZenModePanel.DEBUG) {
                        Log.d(ZenModePanel.this.mTag, "onCheckedChanged " + conditionId);
                    }
                    MetricsLogger.action(ZenModePanel.this.mContext, 164);
                    ZenModePanel.this.select(tag.condition);
                    ZenModePanel.this.announceConditionSelection(tag);
                }
            }
        });
        if (tag.lines == null) {
            tag.lines = row.findViewById(android.R.id.content);
        }
        if (tag.line1 == null) {
            tag.line1 = (TextView) row.findViewById(android.R.id.text1);
            this.mConfigurableTexts.add(tag.line1);
        }
        if (tag.line2 == null) {
            tag.line2 = (TextView) row.findViewById(android.R.id.text2);
            this.mConfigurableTexts.add(tag.line2);
        }
        String line1 = !TextUtils.isEmpty(condition.line1) ? condition.line1 : condition.summary;
        String line2 = condition.line2;
        tag.line1.setText(line1);
        if (TextUtils.isEmpty(line2)) {
            tag.line2.setVisibility(8);
        } else {
            tag.line2.setVisibility(0);
            tag.line2.setText(line2);
        }
        tag.lines.setEnabled(enabled);
        tag.lines.setAlpha(enabled ? 1.0f : 0.4f);
        ImageView button1 = (ImageView) row.findViewById(android.R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.ZenModePanel.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ZenModePanel.this.onClickTimeButton(row, tag, false, rowId);
            }
        });
        ImageView button2 = (ImageView) row.findViewById(android.R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.ZenModePanel.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ZenModePanel.this.onClickTimeButton(row, tag, true, rowId);
            }
        });
        tag.lines.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.ZenModePanel.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                tag.rb.setChecked(true);
            }
        });
        long time = ZenModeConfig.tryParseCountdownConditionId(conditionId);
        if (rowId != 2 && time > 0) {
            button1.setVisibility(0);
            button2.setVisibility(0);
            if (this.mBucketIndex > -1) {
                button1.setEnabled(this.mBucketIndex > 0);
                button2.setEnabled(this.mBucketIndex < MINUTE_BUCKETS.length + (-1));
            } else {
                long span = time - System.currentTimeMillis();
                button1.setEnabled(span > ((long) (MIN_BUCKET_MINUTES * 60000)));
                Condition maxCondition = ZenModeConfig.toTimeCondition(this.mContext, MAX_BUCKET_MINUTES, ActivityManager.getCurrentUser());
                button2.setEnabled(!Objects.equals(condition.summary, maxCondition.summary));
            }
            button1.setAlpha(button1.isEnabled() ? 1.0f : 0.5f);
            button2.setAlpha(button2.isEnabled() ? 1.0f : 0.5f);
        } else {
            button1.setVisibility(8);
            button2.setVisibility(8);
        }
        if (first) {
            Interaction.register(tag.rb, this.mInteractionCallback);
            Interaction.register(tag.lines, this.mInteractionCallback);
            Interaction.register(button1, this.mInteractionCallback);
            Interaction.register(button2, this.mInteractionCallback);
        }
        row.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void announceConditionSelection(ConditionTag tag) {
        String modeText;
        int zen = getSelectedZen(0);
        switch (zen) {
            case 1:
                modeText = this.mContext.getString(R.string.interruption_level_priority);
                break;
            case 2:
                modeText = this.mContext.getString(R.string.interruption_level_none);
                break;
            case 3:
                modeText = this.mContext.getString(R.string.interruption_level_alarms);
                break;
            default:
                return;
        }
        announceForAccessibility(this.mContext.getString(R.string.zen_mode_and_condition, modeText, tag.line1.getText()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClickTimeButton(View row, ConditionTag tag, boolean up, int rowId) {
        MetricsLogger.action(this.mContext, 163, up);
        Condition newCondition = null;
        int N = MINUTE_BUCKETS.length;
        if (this.mBucketIndex == -1) {
            Uri conditionId = getConditionId(tag.condition);
            long time = ZenModeConfig.tryParseCountdownConditionId(conditionId);
            long now = System.currentTimeMillis();
            for (int i = 0; i < N; i++) {
                int j = up ? i : (N - 1) - i;
                int bucketMinutes = MINUTE_BUCKETS[j];
                long bucketTime = now + ((long) (60000 * bucketMinutes));
                if ((up && bucketTime > time) || (!up && bucketTime < time)) {
                    this.mBucketIndex = j;
                    newCondition = ZenModeConfig.toTimeCondition(this.mContext, bucketTime, bucketMinutes, ActivityManager.getCurrentUser(), false);
                    break;
                }
            }
            if (newCondition == null) {
                this.mBucketIndex = DEFAULT_BUCKET_INDEX;
                newCondition = ZenModeConfig.toTimeCondition(this.mContext, MINUTE_BUCKETS[this.mBucketIndex], ActivityManager.getCurrentUser());
            }
        } else {
            this.mBucketIndex = Math.max(0, Math.min(N - 1, this.mBucketIndex + (up ? 1 : -1)));
            newCondition = ZenModeConfig.toTimeCondition(this.mContext, MINUTE_BUCKETS[this.mBucketIndex], ActivityManager.getCurrentUser());
        }
        bind(newCondition, row, rowId);
        tag.rb.setChecked(true);
        select(newCondition);
        announceConditionSelection(tag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void select(Condition condition) {
        if (DEBUG) {
            Log.d(this.mTag, "select " + condition);
        }
        if (this.mSessionZen == -1 || this.mSessionZen == 0) {
            if (DEBUG) {
                Log.d(this.mTag, "Ignoring condition selection outside of manual zen");
                return;
            }
            return;
        }
        final Uri realConditionId = getRealConditionId(condition);
        if (this.mController != null) {
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.volume.ZenModePanel.5
                @Override // java.lang.Runnable
                public void run() {
                    ZenModePanel.this.mController.setZen(ZenModePanel.this.mSessionZen, realConditionId, "ZenModePanel.selectCondition");
                }
            });
        }
        setExitCondition(condition);
        if (realConditionId == null) {
            this.mPrefs.setMinuteIndex(-1);
        } else if ((isAlarm(condition) || isCountdown(condition)) && this.mBucketIndex != -1) {
            this.mPrefs.setMinuteIndex(this.mBucketIndex);
        }
        setSessionExitCondition(copy(condition));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireInteraction() {
        if (this.mCallback != null) {
            this.mCallback.onInteraction();
        }
    }

    private void fireExpanded() {
        if (this.mCallback != null) {
            this.mCallback.onExpanded(this.mExpanded);
        }
    }

    private final class H extends Handler {
        private H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    ZenModePanel.this.handleUpdateManualRule((ZenModeConfig.ZenRule) msg.obj);
                    break;
                case 3:
                    ZenModePanel.this.updateWidgets();
                    break;
            }
        }
    }

    @VisibleForTesting
    static class ConditionTag {
        Condition condition;
        TextView line1;
        TextView line2;
        View lines;
        RadioButton rb;

        ConditionTag() {
        }
    }

    private final class ZenPrefs implements SharedPreferences.OnSharedPreferenceChangeListener {
        private boolean mConfirmedAlarmIntroduction;
        private boolean mConfirmedPriorityIntroduction;
        private boolean mConfirmedSilenceIntroduction;
        private int mMinuteIndex;
        private final int mNoneDangerousThreshold;
        private int mNoneSelected;

        private ZenPrefs() {
            this.mNoneDangerousThreshold = ZenModePanel.this.mContext.getResources().getInteger(R.integer.zen_mode_alarm_warning_threshold);
            Prefs.registerListener(ZenModePanel.this.mContext, this);
            updateMinuteIndex();
            updateNoneSelected();
            updateConfirmedPriorityIntroduction();
            updateConfirmedSilenceIntroduction();
            updateConfirmedAlarmIntroduction();
        }

        public void trackNoneSelected() {
            this.mNoneSelected = clampNoneSelected(this.mNoneSelected + 1);
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Setting none selected: " + this.mNoneSelected + " threshold=" + this.mNoneDangerousThreshold);
            }
            Prefs.putInt(ZenModePanel.this.mContext, "DndNoneSelected", this.mNoneSelected);
        }

        public void setMinuteIndex(int minuteIndex) {
            int minuteIndex2 = clampIndex(minuteIndex);
            if (minuteIndex2 == this.mMinuteIndex) {
                return;
            }
            this.mMinuteIndex = clampIndex(minuteIndex2);
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Setting favorite minute index: " + this.mMinuteIndex);
            }
            Prefs.putInt(ZenModePanel.this.mContext, "DndCountdownMinuteIndex", this.mMinuteIndex);
        }

        @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            updateMinuteIndex();
            updateNoneSelected();
            updateConfirmedPriorityIntroduction();
            updateConfirmedSilenceIntroduction();
            updateConfirmedAlarmIntroduction();
        }

        private void updateMinuteIndex() {
            this.mMinuteIndex = clampIndex(Prefs.getInt(ZenModePanel.this.mContext, "DndCountdownMinuteIndex", ZenModePanel.DEFAULT_BUCKET_INDEX));
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Favorite minute index: " + this.mMinuteIndex);
            }
        }

        private int clampIndex(int index) {
            return MathUtils.constrain(index, -1, ZenModePanel.MINUTE_BUCKETS.length - 1);
        }

        private void updateNoneSelected() {
            this.mNoneSelected = clampNoneSelected(Prefs.getInt(ZenModePanel.this.mContext, "DndNoneSelected", 0));
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "None selected: " + this.mNoneSelected);
            }
        }

        private int clampNoneSelected(int noneSelected) {
            return MathUtils.constrain(noneSelected, 0, Integer.MAX_VALUE);
        }

        private void updateConfirmedPriorityIntroduction() {
            boolean confirmed = Prefs.getBoolean(ZenModePanel.this.mContext, "DndConfirmedPriorityIntroduction", false);
            if (confirmed == this.mConfirmedPriorityIntroduction) {
                return;
            }
            this.mConfirmedPriorityIntroduction = confirmed;
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Confirmed priority introduction: " + this.mConfirmedPriorityIntroduction);
            }
        }

        private void updateConfirmedSilenceIntroduction() {
            boolean confirmed = Prefs.getBoolean(ZenModePanel.this.mContext, "DndConfirmedSilenceIntroduction", false);
            if (confirmed == this.mConfirmedSilenceIntroduction) {
                return;
            }
            this.mConfirmedSilenceIntroduction = confirmed;
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Confirmed silence introduction: " + this.mConfirmedSilenceIntroduction);
            }
        }

        private void updateConfirmedAlarmIntroduction() {
            boolean confirmed = Prefs.getBoolean(ZenModePanel.this.mContext, "DndConfirmedAlarmIntroduction", false);
            if (confirmed == this.mConfirmedAlarmIntroduction) {
                return;
            }
            this.mConfirmedAlarmIntroduction = confirmed;
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "Confirmed alarm introduction: " + this.mConfirmedAlarmIntroduction);
            }
        }
    }

    private final class TransitionHelper implements LayoutTransition.TransitionListener, Runnable {
        private boolean mPendingUpdateWidgets;
        private boolean mTransitioning;
        private final ArraySet<View> mTransitioningViews;

        private TransitionHelper() {
            this.mTransitioningViews = new ArraySet<>();
        }

        public void clear() {
            this.mTransitioningViews.clear();
            this.mPendingUpdateWidgets = false;
        }

        public void pendingUpdateWidgets() {
            this.mPendingUpdateWidgets = true;
        }

        public boolean isTransitioning() {
            return !this.mTransitioningViews.isEmpty();
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            this.mTransitioningViews.add(view);
            updateTransitioning();
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            this.mTransitioningViews.remove(view);
            updateTransitioning();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "TransitionHelper run mPendingUpdateWidgets=" + this.mPendingUpdateWidgets);
            }
            if (this.mPendingUpdateWidgets) {
                ZenModePanel.this.updateWidgets();
            }
            this.mPendingUpdateWidgets = false;
        }

        private void updateTransitioning() {
            boolean transitioning = isTransitioning();
            if (this.mTransitioning == transitioning) {
                return;
            }
            this.mTransitioning = transitioning;
            if (ZenModePanel.DEBUG) {
                Log.d(ZenModePanel.this.mTag, "TransitionHelper mTransitioning=" + this.mTransitioning);
            }
            if (!this.mTransitioning) {
                if (this.mPendingUpdateWidgets) {
                    ZenModePanel.this.mHandler.post(this);
                } else {
                    this.mPendingUpdateWidgets = false;
                }
            }
        }
    }
}
