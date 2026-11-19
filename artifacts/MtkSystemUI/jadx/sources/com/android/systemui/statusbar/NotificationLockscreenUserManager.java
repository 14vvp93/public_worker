package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class NotificationLockscreenUserManager implements Dumpable {
    private boolean mAllowLockscreenRemoteInput;
    protected final Context mContext;
    protected int mCurrentUserId;
    private final DevicePolicyManager mDevicePolicyManager;
    protected NotificationEntryManager mEntryManager;
    protected ContentObserver mLockscreenSettingsObserver;
    protected NotificationPresenter mPresenter;
    protected ContentObserver mSettingsObserver;
    private boolean mShowLockscreenNotifications;
    private final UserManager mUserManager;
    private final SparseBooleanArray mLockscreenPublicMode = new SparseBooleanArray();
    private final SparseBooleanArray mUsersAllowingPrivateNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersAllowingNotifications = new SparseBooleanArray();
    private final DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    protected final BroadcastReceiver mAllUsersReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int userId = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action) && NotificationLockscreenUserManager.this.isCurrentProfile(getSendingUserId())) {
                NotificationLockscreenUserManager.this.mUsersAllowingPrivateNotifications.clear();
                NotificationLockscreenUserManager.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManager.this.mEntryManager.updateNotifications();
            } else if ("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action) && userId != NotificationLockscreenUserManager.this.mCurrentUserId && NotificationLockscreenUserManager.this.isCurrentProfile(userId)) {
                NotificationLockscreenUserManager.this.mPresenter.onWorkChallengeChanged();
            }
        }
    };
    protected final BroadcastReceiver mBaseBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                NotificationLockscreenUserManager.this.mCurrentUserId = intent.getIntExtra("android.intent.extra.user_handle", -1);
                NotificationLockscreenUserManager.this.updateCurrentProfilesCache();
                Log.v("LockscreenUserManager", "userId " + NotificationLockscreenUserManager.this.mCurrentUserId + " is in the house");
                NotificationLockscreenUserManager.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManager.this.mPresenter.onUserSwitched(NotificationLockscreenUserManager.this.mCurrentUserId);
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action)) {
                NotificationLockscreenUserManager.this.updateCurrentProfilesCache();
                return;
            }
            if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                ((OverviewProxyService) Dependency.get(OverviewProxyService.class)).startConnectionToCurrentUser();
                return;
            }
            if ("android.intent.action.USER_PRESENT".equals(action)) {
                try {
                    int lastResumedActivityUserId = ActivityManager.getService().getLastResumedActivityUserId();
                    if (NotificationLockscreenUserManager.this.mUserManager.isManagedProfile(lastResumedActivityUserId)) {
                        NotificationLockscreenUserManager.this.showForegroundManagedProfileActivityToast();
                        return;
                    }
                    return;
                } catch (RemoteException e) {
                    return;
                }
            }
            if ("com.android.systemui.statusbar.work_challenge_unlocked_notification_action".equals(action)) {
                IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT");
                String notificationKey = intent.getStringExtra("android.intent.extra.INDEX");
                if (intentSender != null) {
                    try {
                        NotificationLockscreenUserManager.this.mContext.startIntentSender(intentSender, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e2) {
                    }
                }
                if (notificationKey != null) {
                    int count = NotificationLockscreenUserManager.this.mEntryManager.getNotificationData().getActiveNotifications().size();
                    int rank = NotificationLockscreenUserManager.this.mEntryManager.getNotificationData().getRank(notificationKey);
                    NotificationVisibility nv = NotificationVisibility.obtain(notificationKey, rank, count, true);
                    try {
                        NotificationLockscreenUserManager.this.mBarService.onNotificationClick(notificationKey, nv);
                    } catch (RemoteException e3) {
                    }
                }
            }
        }
    };
    protected final SparseArray<UserInfo> mCurrentProfiles = new SparseArray<>();
    private final IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    public NotificationLockscreenUserManager(Context context) {
        this.mCurrentUserId = 0;
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mCurrentUserId = ActivityManager.getCurrentUser();
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        this.mLockscreenSettingsObserver = new ContentObserver(this.mPresenter.getHandler()) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManager.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                NotificationLockscreenUserManager.this.mUsersAllowingPrivateNotifications.clear();
                NotificationLockscreenUserManager.this.mUsersAllowingNotifications.clear();
                NotificationLockscreenUserManager.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManager.this.mEntryManager.updateNotifications();
            }
        };
        this.mSettingsObserver = new ContentObserver(this.mPresenter.getHandler()) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManager.4
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                NotificationLockscreenUserManager.this.updateLockscreenNotificationSetting();
                if (NotificationLockscreenUserManager.this.mDeviceProvisionedController.isDeviceProvisioned()) {
                    NotificationLockscreenUserManager.this.mEntryManager.updateNotifications();
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_notifications"), false, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_allow_private_notifications"), true, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this.mSettingsObserver);
        IntentFilter allUsersFilter = new IntentFilter();
        allUsersFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        allUsersFilter.addAction("android.intent.action.DEVICE_LOCKED_CHANGED");
        this.mContext.registerReceiverAsUser(this.mAllUsersReceiver, UserHandle.ALL, allUsersFilter, null, null);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.USER_SWITCHED");
        filter.addAction("android.intent.action.USER_ADDED");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction("android.intent.action.USER_UNLOCKED");
        this.mContext.registerReceiver(this.mBaseBroadcastReceiver, filter);
        IntentFilter internalFilter = new IntentFilter();
        internalFilter.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        this.mContext.registerReceiver(this.mBaseBroadcastReceiver, internalFilter, "com.android.systemui.permission.SELF", null);
        updateCurrentProfilesCache();
        this.mSettingsObserver.onChange(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showForegroundManagedProfileActivityToast() {
        Toast toast = Toast.makeText(this.mContext, R.string.managed_profile_foreground_toast, 0);
        TextView text = (TextView) toast.getView().findViewById(android.R.id.message);
        text.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.stat_sys_managed_profile_status, 0, 0, 0);
        int paddingPx = this.mContext.getResources().getDimensionPixelSize(R.dimen.managed_profile_toast_padding);
        text.setCompoundDrawablePadding(paddingPx);
        toast.show();
    }

    public boolean shouldShowLockscreenNotifications() {
        return this.mShowLockscreenNotifications;
    }

    public boolean shouldAllowLockscreenRemoteInput() {
        return this.mAllowLockscreenRemoteInput;
    }

    public boolean isCurrentProfile(int userId) {
        boolean z;
        synchronized (this.mCurrentProfiles) {
            if (userId != -1) {
                try {
                    z = this.mCurrentProfiles.get(userId) != null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return z;
    }

    private boolean shouldTemporarilyHideNotifications(int userId) {
        if (userId == -1) {
            userId = this.mCurrentUserId;
        }
        return KeyguardUpdateMonitor.getInstance(this.mContext).isUserInLockdown(userId);
    }

    public boolean shouldHideNotifications(int userId) {
        return (isLockscreenPublicMode(userId) && !userAllowsNotificationsInPublic(userId)) || (userId != this.mCurrentUserId && shouldHideNotifications(this.mCurrentUserId)) || shouldTemporarilyHideNotifications(userId);
    }

    public boolean shouldHideNotifications(String key) {
        if (this.mEntryManager != null) {
            return isLockscreenPublicMode(this.mCurrentUserId) && this.mEntryManager.getNotificationData().getVisibilityOverride(key) == -1;
        }
        Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
        return true;
    }

    public boolean shouldShowOnKeyguard(StatusBarNotification sbn) {
        if (this.mEntryManager != null) {
            return this.mShowLockscreenNotifications && !this.mEntryManager.getNotificationData().isAmbient(sbn.getKey());
        }
        Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
        return false;
    }

    private void setShowLockscreenNotifications(boolean show) {
        this.mShowLockscreenNotifications = show;
    }

    private void setLockscreenAllowRemoteInput(boolean allowLockscreenRemoteInput) {
        this.mAllowLockscreenRemoteInput = allowLockscreenRemoteInput;
    }

    protected void updateLockscreenNotificationSetting() {
        boolean show = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_show_notifications", 1, this.mCurrentUserId) != 0;
        int dpmFlags = this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, this.mCurrentUserId);
        boolean allowedByDpm = (dpmFlags & 4) == 0;
        setShowLockscreenNotifications(show && allowedByDpm);
        setLockscreenAllowRemoteInput(false);
    }

    public boolean userAllowsPrivateNotificationsInPublic(int userHandle) {
        boolean allowedByUser;
        boolean allowed = true;
        if (userHandle == -1) {
            return true;
        }
        if (this.mUsersAllowingPrivateNotifications.indexOfKey(userHandle) < 0) {
            if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 0, userHandle) != 0) {
                allowedByUser = true;
            } else {
                allowedByUser = false;
            }
            boolean allowedByDpm = adminAllowsKeyguardFeature(userHandle, 8);
            if (!allowedByUser || !allowedByDpm) {
                allowed = false;
            }
            this.mUsersAllowingPrivateNotifications.append(userHandle, allowed);
            return allowed;
        }
        return this.mUsersAllowingPrivateNotifications.get(userHandle);
    }

    private boolean adminAllowsKeyguardFeature(int userHandle, int feature) {
        if (userHandle == -1) {
            return true;
        }
        int dpmFlags = this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, userHandle);
        return (dpmFlags & feature) == 0;
    }

    public void setLockscreenPublicMode(boolean publicMode, int userId) {
        this.mLockscreenPublicMode.put(userId, publicMode);
    }

    public boolean isLockscreenPublicMode(int userId) {
        return userId == -1 ? this.mLockscreenPublicMode.get(this.mCurrentUserId, false) : this.mLockscreenPublicMode.get(userId, false);
    }

    private boolean userAllowsNotificationsInPublic(int userHandle) {
        if (isCurrentProfile(userHandle) && userHandle != this.mCurrentUserId) {
            return true;
        }
        if (this.mUsersAllowingNotifications.indexOfKey(userHandle) < 0) {
            boolean allowedByUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_show_notifications", 0, userHandle) != 0;
            boolean allowedByDpm = adminAllowsKeyguardFeature(userHandle, 4);
            boolean allowed = allowedByUser && allowedByDpm;
            this.mUsersAllowingNotifications.append(userHandle, allowed);
            return allowed;
        }
        return this.mUsersAllowingNotifications.get(userHandle);
    }

    public boolean needsRedaction(NotificationData.Entry ent) {
        int userId = ent.notification.getUserId();
        boolean currentUserWantsRedaction = !userAllowsPrivateNotificationsInPublic(this.mCurrentUserId);
        boolean notiUserWantsRedaction = !userAllowsPrivateNotificationsInPublic(userId);
        boolean redactedLockscreen = currentUserWantsRedaction || notiUserWantsRedaction;
        boolean notificationRequestsRedaction = ent.notification.getNotification().visibility == 0;
        boolean userForcesRedaction = packageHasVisibilityOverride(ent.notification.getKey());
        if (userForcesRedaction) {
            return true;
        }
        return notificationRequestsRedaction && redactedLockscreen;
    }

    private boolean packageHasVisibilityOverride(String key) {
        if (this.mEntryManager != null) {
            return this.mEntryManager.getNotificationData().getVisibilityOverride(key) == 0;
        }
        Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentProfilesCache() {
        synchronized (this.mCurrentProfiles) {
            this.mCurrentProfiles.clear();
            if (this.mUserManager != null) {
                for (UserInfo user : this.mUserManager.getProfiles(this.mCurrentUserId)) {
                    this.mCurrentProfiles.put(user.id, user);
                }
            }
        }
    }

    public boolean isAnyProfilePublicMode() {
        for (int i = this.mCurrentProfiles.size() - 1; i >= 0; i--) {
            if (isLockscreenPublicMode(this.mCurrentProfiles.valueAt(i).id)) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentUserId() {
        return this.mCurrentUserId;
    }

    public SparseArray<UserInfo> getCurrentProfiles() {
        return this.mCurrentProfiles;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NotificationLockscreenUserManager state:");
        pw.print("  mCurrentUserId=");
        pw.println(this.mCurrentUserId);
        pw.print("  mShowLockscreenNotifications=");
        pw.println(this.mShowLockscreenNotifications);
        pw.print("  mAllowLockscreenRemoteInput=");
        pw.println(this.mAllowLockscreenRemoteInput);
        pw.print("  mCurrentProfiles=");
        for (int i = this.mCurrentProfiles.size() - 1; i >= 0; i += -1) {
            int userId = this.mCurrentProfiles.valueAt(i).id;
            pw.print("" + userId + " ");
        }
        pw.println();
    }
}
