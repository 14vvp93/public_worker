package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    private String mAppName;
    private OnAppSettingsClickListener mAppSettingsClickListener;
    private int mAppUid;
    private CheckSaveListener mCheckSaveListener;
    private int mChosenImportance;
    private String mExitReason;
    private AnimatorSet mExpandAnimation;
    private NotificationGuts mGutsContainer;
    private INotificationManager mINotificationManager;
    private boolean mIsForBlockingHelper;
    private boolean mIsForeground;
    private boolean mIsNonblockable;
    private boolean mIsSingleDefaultChannel;
    private MetricsLogger mMetricsLogger;
    private boolean mNegativeUserSentiment;
    private int mNumUniqueChannelsInRow;
    private View.OnClickListener mOnKeepShowing;
    private OnSettingsClickListener mOnSettingsClickListener;
    private View.OnClickListener mOnStopOrMinimizeNotifications;
    private View.OnClickListener mOnUndo;
    private String mPackageName;
    private PackageManager mPm;
    private StatusBarNotification mSbn;
    private NotificationChannel mSingleNotificationChannel;
    private int mStartingUserImportance;

    public interface CheckSaveListener {
        void checkSave(Runnable runnable, StatusBarNotification statusBarNotification);
    }

    public interface OnAppSettingsClickListener {
        void onClick(View view, Intent intent);
    }

    public interface OnSettingsClickListener {
        void onClick(View view, NotificationChannel notificationChannel, int i);
    }

    public static /* synthetic */ void lambda$new$0(NotificationInfo notificationInfo, View v) {
        notificationInfo.mExitReason = "blocking_helper_keep_showing";
        notificationInfo.closeControls(v);
    }

    public static /* synthetic */ void lambda$new$1(NotificationInfo notificationInfo, View v) {
        notificationInfo.mExitReason = "blocking_helper_stop_notifications";
        notificationInfo.swapContent(false);
    }

    public static /* synthetic */ void lambda$new$2(NotificationInfo notificationInfo, View v) {
        notificationInfo.mExitReason = "blocking_helper_dismissed";
        notificationInfo.logBlockingHelperCounter("blocking_helper_undo");
        notificationInfo.swapContent(true);
    }

    public NotificationInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mExitReason = "blocking_helper_dismissed";
        this.mOnKeepShowing = new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$OE4i5uy2V1VNOIOCPk_l9LEjxGg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationInfo.lambda$new$0(this.f$0, view);
            }
        };
        this.mOnStopOrMinimizeNotifications = new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$-_FAMjCKcojvnymBpf3GU21SM4A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationInfo.lambda$new$1(this.f$0, view);
            }
        };
        this.mOnUndo = new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$7kxiIPS-E1y-_HRLaUJLceph_TY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationInfo.lambda$new$2(this.f$0, view);
            }
        };
    }

    @VisibleForTesting
    void bindNotification(PackageManager pm, INotificationManager iNotificationManager, String pkg, NotificationChannel notificationChannel, int numUniqueChannelsInRow, StatusBarNotification sbn, CheckSaveListener checkSaveListener, OnSettingsClickListener onSettingsClick, OnAppSettingsClickListener onAppSettingsClick, boolean isNonblockable) throws RemoteException {
        bindNotification(pm, iNotificationManager, pkg, notificationChannel, numUniqueChannelsInRow, sbn, checkSaveListener, onSettingsClick, onAppSettingsClick, isNonblockable, false, false);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0087  */
    public void bindNotification(PackageManager pm, INotificationManager iNotificationManager, String pkg, NotificationChannel notificationChannel, int numUniqueChannelsInRow, StatusBarNotification sbn, CheckSaveListener checkSaveListener, OnSettingsClickListener onSettingsClick, OnAppSettingsClickListener onAppSettingsClick, boolean isNonblockable, boolean isForBlockingHelper, boolean isUserSentimentNegative) throws RemoteException {
        boolean z;
        this.mINotificationManager = iNotificationManager;
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mPackageName = pkg;
        this.mNumUniqueChannelsInRow = numUniqueChannelsInRow;
        this.mSbn = sbn;
        this.mPm = pm;
        this.mAppSettingsClickListener = onAppSettingsClick;
        this.mAppName = this.mPackageName;
        this.mCheckSaveListener = checkSaveListener;
        this.mOnSettingsClickListener = onSettingsClick;
        this.mSingleNotificationChannel = notificationChannel;
        int importance = this.mSingleNotificationChannel.getImportance();
        this.mChosenImportance = importance;
        this.mStartingUserImportance = importance;
        this.mNegativeUserSentiment = isUserSentimentNegative;
        this.mIsNonblockable = isNonblockable;
        this.mIsForeground = (this.mSbn.getNotification().flags & 64) != 0;
        this.mIsForBlockingHelper = isForBlockingHelper;
        this.mAppUid = this.mSbn.getUid();
        int numTotalChannels = this.mINotificationManager.getNumNotificationChannelsForPackage(pkg, this.mAppUid, false);
        if (this.mNumUniqueChannelsInRow == 0) {
            throw new IllegalArgumentException("bindNotification requires at least one channel");
        }
        if (this.mNumUniqueChannelsInRow == 1 && this.mSingleNotificationChannel.getId().equals("miscellaneous")) {
            z = numTotalChannels == 1;
        }
        this.mIsSingleDefaultChannel = z;
        bindHeader();
        bindPrompt();
        bindButtons();
    }

    private void bindHeader() throws RemoteException {
        NotificationChannelGroup notificationChannelGroup;
        Drawable pkgicon = null;
        try {
            ApplicationInfo info = this.mPm.getApplicationInfo(this.mPackageName, 795136);
            if (info != null) {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(info));
                pkgicon = this.mPm.getApplicationIcon(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            pkgicon = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(R.id.pkgicon)).setImageDrawable(pkgicon);
        ((TextView) findViewById(R.id.pkgname)).setText(this.mAppName);
        CharSequence groupName = null;
        if (this.mSingleNotificationChannel != null && this.mSingleNotificationChannel.getGroup() != null && (notificationChannelGroup = this.mINotificationManager.getNotificationChannelGroupForPackage(this.mSingleNotificationChannel.getGroup(), this.mPackageName, this.mAppUid)) != null) {
            groupName = notificationChannelGroup.getName();
        }
        TextView groupNameView = (TextView) findViewById(R.id.group_name);
        TextView groupDividerView = (TextView) findViewById(R.id.pkg_group_divider);
        if (groupName != null) {
            groupNameView.setText(groupName);
            groupNameView.setVisibility(0);
            groupDividerView.setVisibility(0);
        } else {
            groupNameView.setVisibility(8);
            groupDividerView.setVisibility(8);
        }
        View settingsButton = findViewById(R.id.info);
        if (this.mAppUid >= 0 && this.mOnSettingsClickListener != null) {
            settingsButton.setVisibility(0);
            final int appUidF = this.mAppUid;
            settingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$pxVS_YLdCQFH6VceUnIC5mpFX7A
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationInfo.lambda$bindHeader$3(this.f$0, appUidF, view);
                }
            });
            return;
        }
        settingsButton.setVisibility(8);
    }

    public static /* synthetic */ void lambda$bindHeader$3(NotificationInfo notificationInfo, int appUidF, View view) {
        notificationInfo.logBlockingHelperCounter("blocking_helper_notif_settings");
        notificationInfo.mOnSettingsClickListener.onClick(view, notificationInfo.mNumUniqueChannelsInRow > 1 ? null : notificationInfo.mSingleNotificationChannel, appUidF);
    }

    private void bindPrompt() {
        TextView blockPrompt = (TextView) findViewById(R.id.block_prompt);
        bindName();
        if (this.mIsNonblockable) {
            blockPrompt.setText(R.string.notification_unblockable_desc);
            return;
        }
        if (this.mNegativeUserSentiment) {
            blockPrompt.setText(R.string.inline_blocking_helper);
        } else if (this.mIsSingleDefaultChannel || this.mNumUniqueChannelsInRow > 1) {
            blockPrompt.setText(R.string.inline_keep_showing_app);
        } else {
            blockPrompt.setText(R.string.inline_keep_showing);
        }
    }

    private void bindName() {
        TextView channelName = (TextView) findViewById(R.id.channel_name);
        if (this.mIsSingleDefaultChannel || this.mNumUniqueChannelsInRow > 1) {
            channelName.setVisibility(8);
        } else {
            channelName.setText(this.mSingleNotificationChannel.getName());
        }
    }

    @VisibleForTesting
    void logBlockingHelperCounter(String counterTag) {
        if (this.mIsForBlockingHelper) {
            this.mMetricsLogger.count(counterTag, 1);
        }
    }

    private boolean hasImportanceChanged() {
        return (this.mSingleNotificationChannel == null || this.mStartingUserImportance == this.mChosenImportance) ? false : true;
    }

    private void saveImportance() {
        if (!this.mIsNonblockable) {
            if (this.mCheckSaveListener != null && "blocking_helper_stop_notifications".equals(this.mExitReason)) {
                this.mCheckSaveListener.checkSave(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$FcHfx_eVn3zstWC-BeoEQcmo1DY
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.updateImportance();
                    }
                }, this.mSbn);
            } else {
                updateImportance();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateImportance() {
        MetricsLogger.action(this.mContext, 291, this.mChosenImportance - this.mStartingUserImportance);
        Handler bgHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
        bgHandler.post(new UpdateImportanceRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, this.mNumUniqueChannelsInRow == 1 ? this.mSingleNotificationChannel : null, this.mStartingUserImportance, this.mChosenImportance));
    }

    private void bindButtons() {
        View block = findViewById(R.id.block);
        TextView keep = (TextView) findViewById(R.id.keep);
        View minimize = findViewById(R.id.minimize);
        findViewById(R.id.undo).setOnClickListener(this.mOnUndo);
        block.setOnClickListener(this.mOnStopOrMinimizeNotifications);
        keep.setOnClickListener(this.mOnKeepShowing);
        minimize.setOnClickListener(this.mOnStopOrMinimizeNotifications);
        if (this.mIsNonblockable) {
            keep.setText(android.R.string.ok);
            block.setVisibility(8);
            minimize.setVisibility(8);
        } else if (this.mIsForeground) {
            block.setVisibility(8);
            minimize.setVisibility(0);
        } else if (!this.mIsForeground) {
            block.setVisibility(0);
            minimize.setVisibility(8);
        }
        TextView settingsLinkView = (TextView) findViewById(R.id.app_settings);
        final Intent settingsIntent = getAppSettingsIntent(this.mPm, this.mPackageName, this.mSingleNotificationChannel, this.mSbn.getId(), this.mSbn.getTag());
        if (!this.mIsForBlockingHelper && settingsIntent != null && !TextUtils.isEmpty(this.mSbn.getNotification().getSettingsText())) {
            settingsLinkView.setVisibility(0);
            settingsLinkView.setText(this.mContext.getString(R.string.notification_app_settings));
            settingsLinkView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationInfo$uIoadfdpLliB-jGM6g1VRlhmUaQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.mAppSettingsClickListener.onClick(view, settingsIntent);
                }
            });
            return;
        }
        settingsLinkView.setVisibility(8);
    }

    private void swapContent(final boolean showPrompt) {
        if (this.mExpandAnimation != null) {
            this.mExpandAnimation.cancel();
        }
        final View prompt = findViewById(R.id.prompt);
        final ViewGroup confirmation = (ViewGroup) findViewById(R.id.confirmation);
        TextView confirmationText = (TextView) findViewById(R.id.confirmation_text);
        View header = findViewById(R.id.header);
        if (showPrompt) {
            this.mChosenImportance = this.mStartingUserImportance;
        } else if (this.mIsForeground) {
            this.mChosenImportance = 1;
            confirmationText.setText(R.string.notification_channel_minimized);
        } else {
            this.mChosenImportance = 0;
            confirmationText.setText(R.string.notification_channel_disabled);
        }
        Property property = View.ALPHA;
        float[] fArr = new float[2];
        fArr[0] = prompt.getAlpha();
        fArr[1] = showPrompt ? 1.0f : 0.0f;
        ObjectAnimator promptAnim = ObjectAnimator.ofFloat(prompt, (Property<View, Float>) property, fArr);
        promptAnim.setInterpolator(showPrompt ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
        Property property2 = View.ALPHA;
        float[] fArr2 = new float[2];
        fArr2[0] = confirmation.getAlpha();
        fArr2[1] = showPrompt ? 0.0f : 1.0f;
        ObjectAnimator confirmAnim = ObjectAnimator.ofFloat(confirmation, (Property<ViewGroup, Float>) property2, fArr2);
        confirmAnim.setInterpolator(showPrompt ? Interpolators.ALPHA_OUT : Interpolators.ALPHA_IN);
        prompt.setVisibility(showPrompt ? 0 : 8);
        confirmation.setVisibility(showPrompt ? 8 : 0);
        header.setVisibility(showPrompt ? 0 : 8);
        this.mExpandAnimation = new AnimatorSet();
        this.mExpandAnimation.playTogether(promptAnim, confirmAnim);
        this.mExpandAnimation.setDuration(150L);
        this.mExpandAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.NotificationInfo.1
            boolean cancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (!this.cancelled) {
                    prompt.setVisibility(showPrompt ? 0 : 8);
                    confirmation.setVisibility(showPrompt ? 8 : 0);
                }
            }
        });
        this.mExpandAnimation.start();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (this.mGutsContainer != null && event.getEventType() == 32) {
            if (this.mGutsContainer.isExposed()) {
                event.getText().add(this.mContext.getString(R.string.notification_channel_controls_opened_accessibility, this.mAppName));
            } else {
                event.getText().add(this.mContext.getString(R.string.notification_channel_controls_closed_accessibility, this.mAppName));
            }
        }
    }

    private Intent getAppSettingsIntent(PackageManager pm, String packageName, NotificationChannel channel, int id, String tag) {
        Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.NOTIFICATION_PREFERENCES").setPackage(packageName);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 65536);
        if (resolveInfos == null || resolveInfos.size() == 0 || resolveInfos.get(0) == null) {
            return null;
        }
        ActivityInfo activityInfo = resolveInfos.get(0).activityInfo;
        intent.setClassName(activityInfo.packageName, activityInfo.name);
        if (channel != null) {
            intent.putExtra("android.intent.extra.CHANNEL_ID", channel.getId());
        }
        intent.putExtra("android.intent.extra.NOTIFICATION_ID", id);
        intent.putExtra("android.intent.extra.NOTIFICATION_TAG", tag);
        return intent;
    }

    @VisibleForTesting
    void closeControls(View v) {
        int[] parentLoc = new int[2];
        int[] targetLoc = new int[2];
        this.mGutsContainer.getLocationOnScreen(parentLoc);
        v.getLocationOnScreen(targetLoc);
        int centerX = v.getWidth() / 2;
        int centerY = v.getHeight() / 2;
        int x = (targetLoc[0] - parentLoc[0]) + centerX;
        int y = (targetLoc[1] - parentLoc[1]) + centerY;
        this.mGutsContainer.closeControls(x, y, true, false);
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public void setGutsParent(NotificationGuts guts) {
        this.mGutsContainer = guts;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean willBeRemoved() {
        return hasImportanceChanged();
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean shouldBeSaved() {
        return hasImportanceChanged();
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public View getContentView() {
        return this;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean handleCloseControls(boolean save, boolean force) {
        if (save) {
            saveImportance();
        }
        logBlockingHelperCounter(this.mExitReason);
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public int getActualHeight() {
        return getHeight();
    }

    private static class UpdateImportanceRunnable implements Runnable {
        private final int mAppUid;
        private final NotificationChannel mChannelToUpdate;
        private final int mCurrentImportance;
        private final INotificationManager mINotificationManager;
        private final int mNewImportance;
        private final String mPackageName;

        public UpdateImportanceRunnable(INotificationManager notificationManager, String packageName, int appUid, NotificationChannel channelToUpdate, int currentImportance, int newImportance) {
            this.mINotificationManager = notificationManager;
            this.mPackageName = packageName;
            this.mAppUid = appUid;
            this.mChannelToUpdate = channelToUpdate;
            this.mCurrentImportance = currentImportance;
            this.mNewImportance = newImportance;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.mChannelToUpdate != null) {
                    this.mChannelToUpdate.setImportance(this.mNewImportance);
                    this.mChannelToUpdate.lockFields(4);
                    this.mINotificationManager.updateNotificationChannelForPackage(this.mPackageName, this.mAppUid, this.mChannelToUpdate);
                } else {
                    this.mINotificationManager.setNotificationsEnabledWithImportanceLockForPackage(this.mPackageName, this.mAppUid, this.mNewImportance >= this.mCurrentImportance);
                }
            } catch (RemoteException e) {
                Log.e("InfoGuts", "Unable to update notification importance", e);
            }
        }
    }
}
