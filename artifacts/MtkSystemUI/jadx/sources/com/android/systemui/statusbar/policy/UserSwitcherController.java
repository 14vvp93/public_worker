package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.UserIcons;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.SystemUISecondaryUserService;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UserSwitcherController {
    private final ActivityStarter mActivityStarter;
    private Dialog mAddUserDialog;
    private boolean mAddUsersWhenLocked;
    protected final Context mContext;
    private Dialog mExitGuestDialog;
    protected final Handler mHandler;
    private final KeyguardMonitor mKeyguardMonitor;
    private boolean mPauseRefreshUsers;
    private Intent mSecondaryUserServiceIntent;
    private boolean mSimpleUserSwitcher;
    protected final UserManager mUserManager;
    private final ArrayList<WeakReference<BaseUserAdapter>> mAdapters = new ArrayList<>();
    private final GuestResumeSessionReceiver mGuestResumeSessionReceiver = new GuestResumeSessionReceiver();
    private ArrayList<UserRecord> mUsers = new ArrayList<>();
    private int mLastNonGuestUser = 0;
    private boolean mResumeUserOnGuestLogout = true;
    private int mSecondaryUser = -10000;
    private SparseBooleanArray mForcePictureLoadForUserId = new SparseBooleanArray(2);
    private final PhoneStateListener mPhoneStateListener = new PhoneStateListener() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.2
        private int mCallState;

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int state, String incomingNumber) {
            if (this.mCallState == state) {
                return;
            }
            this.mCallState = state;
            UserSwitcherController.this.refreshUsers(-10000);
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean unpauseRefreshUsers = false;
            int forcePictureLoadForId = -10000;
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                if (UserSwitcherController.this.mExitGuestDialog != null && UserSwitcherController.this.mExitGuestDialog.isShowing()) {
                    UserSwitcherController.this.mExitGuestDialog.cancel();
                    UserSwitcherController.this.mExitGuestDialog = null;
                }
                int currentId = intent.getIntExtra("android.intent.extra.user_handle", -1);
                UserInfo userInfo = UserSwitcherController.this.mUserManager.getUserInfo(currentId);
                int N = UserSwitcherController.this.mUsers.size();
                int i = 0;
                while (i < N) {
                    UserRecord record = (UserRecord) UserSwitcherController.this.mUsers.get(i);
                    if (record.info != null) {
                        boolean shouldBeCurrent = record.info.id == currentId;
                        if (record.isCurrent != shouldBeCurrent) {
                            UserSwitcherController.this.mUsers.set(i, record.copyWithIsCurrent(shouldBeCurrent));
                        }
                        if (shouldBeCurrent && !record.isGuest) {
                            UserSwitcherController.this.mLastNonGuestUser = record.info.id;
                        }
                        if ((userInfo == null || !userInfo.isAdmin()) && record.isRestricted) {
                            UserSwitcherController.this.mUsers.remove(i);
                            i--;
                        }
                    }
                    i++;
                }
                UserSwitcherController.this.notifyAdapters();
                if (UserSwitcherController.this.mSecondaryUser != -10000) {
                    context.stopServiceAsUser(UserSwitcherController.this.mSecondaryUserServiceIntent, UserHandle.of(UserSwitcherController.this.mSecondaryUser));
                    UserSwitcherController.this.mSecondaryUser = -10000;
                }
                if (userInfo != null && userInfo.id != 0) {
                    context.startServiceAsUser(UserSwitcherController.this.mSecondaryUserServiceIntent, UserHandle.of(userInfo.id));
                    UserSwitcherController.this.mSecondaryUser = userInfo.id;
                }
                unpauseRefreshUsers = true;
            } else if ("android.intent.action.USER_INFO_CHANGED".equals(intent.getAction())) {
                forcePictureLoadForId = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            } else if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                int userId = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (userId != 0) {
                    return;
                }
            }
            UserSwitcherController.this.refreshUsers(forcePictureLoadForId);
            if (unpauseRefreshUsers) {
                UserSwitcherController.this.mUnpauseRefreshUsers.run();
            }
        }
    };
    private final Runnable mUnpauseRefreshUsers = new Runnable() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.4
        @Override // java.lang.Runnable
        public void run() {
            UserSwitcherController.this.mHandler.removeCallbacks(this);
            UserSwitcherController.this.mPauseRefreshUsers = false;
            UserSwitcherController.this.refreshUsers(-10000);
        }
    };
    private final ContentObserver mSettingsObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.5
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            UserSwitcherController.this.mSimpleUserSwitcher = Settings.Global.getInt(UserSwitcherController.this.mContext.getContentResolver(), "lockscreenSimpleUserSwitcher", 0) != 0;
            UserSwitcherController.this.mAddUsersWhenLocked = Settings.Global.getInt(UserSwitcherController.this.mContext.getContentResolver(), "add_users_when_locked", 0) != 0;
            UserSwitcherController.this.refreshUsers(-10000);
        }
    };
    public final DetailAdapter userDetailAdapter = new DetailAdapter() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.6
        private final Intent USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return UserSwitcherController.this.mContext.getString(R.string.quick_settings_user_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View convertView, ViewGroup parent) {
            UserDetailView v;
            if (!(convertView instanceof UserDetailView)) {
                v = UserDetailView.inflate(context, parent, false);
                v.createAndSetAdapter(UserSwitcherController.this);
            } else {
                v = (UserDetailView) convertView;
            }
            v.refreshAdapter();
            return v;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return this.USER_SETTINGS_INTENT;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean state) {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return 125;
        }
    };
    private final KeyguardMonitor.Callback mCallback = new AnonymousClass7();

    public UserSwitcherController(Context context, KeyguardMonitor keyguardMonitor, Handler handler, ActivityStarter activityStarter) {
        this.mContext = context;
        this.mGuestResumeSessionReceiver.register(context);
        this.mKeyguardMonitor = keyguardMonitor;
        this.mHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mUserManager = UserManager.get(context);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.USER_ADDED");
        filter.addAction("android.intent.action.USER_REMOVED");
        filter.addAction("android.intent.action.USER_INFO_CHANGED");
        filter.addAction("android.intent.action.USER_SWITCHED");
        filter.addAction("android.intent.action.USER_STOPPED");
        filter.addAction("android.intent.action.USER_UNLOCKED");
        this.mContext.registerReceiverAsUser(this.mReceiver, UserHandle.SYSTEM, filter, null, null);
        this.mSecondaryUserServiceIntent = new Intent(context, (Class<?>) SystemUISecondaryUserService.class);
        this.mContext.registerReceiverAsUser(this.mReceiver, UserHandle.SYSTEM, new IntentFilter(), "com.android.systemui.permission.SELF", null);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("lockscreenSimpleUserSwitcher"), true, this.mSettingsObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("add_users_when_locked"), true, this.mSettingsObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("allow_user_switching_when_system_user_locked"), true, this.mSettingsObserver);
        this.mSettingsObserver.onChange(false);
        keyguardMonitor.addCallback(this.mCallback);
        listenForCallState();
        refreshUsers(-10000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.statusbar.policy.UserSwitcherController$1] */
    public void refreshUsers(int forcePictureLoadForId) {
        if (forcePictureLoadForId != -10000) {
            this.mForcePictureLoadForUserId.put(forcePictureLoadForId, true);
        }
        if (this.mPauseRefreshUsers) {
            return;
        }
        boolean forceAllUsers = this.mForcePictureLoadForUserId.get(-1);
        SparseArray<Bitmap> bitmaps = new SparseArray<>(this.mUsers.size());
        int N = this.mUsers.size();
        for (int i = 0; i < N; i++) {
            UserRecord r = this.mUsers.get(i);
            if (r != null && r.picture != null && r.info != null && !forceAllUsers && !this.mForcePictureLoadForUserId.get(r.info.id)) {
                bitmaps.put(r.info.id, r.picture);
            }
        }
        this.mForcePictureLoadForUserId.clear();
        final boolean addUsersWhenLocked = this.mAddUsersWhenLocked;
        new AsyncTask<SparseArray<Bitmap>, Void, ArrayList<UserRecord>>() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Code duplicated, block: B:56:0x0115  */
            /* JADX WARN: Code duplicated, block: B:62:0x0120  */
            /* JADX WARN: Code duplicated, block: B:78:0x014d A[DONT_INVERT] */
            /* JADX WARN: Code duplicated, block: B:79:0x014f A[DONT_INVERT] */
            /* JADX WARN: Code duplicated, block: B:80:0x0151  */
            /* JADX WARN: Code duplicated, block: B:81:0x016b  */
            /* JADX WARN: Code duplicated, block: B:83:0x0171  */
            /* JADX WARN: Code duplicated, block: B:84:0x0173  */
            /* JADX WARN: Code duplicated, block: B:86:0x017b  */
            @Override // android.os.AsyncTask
            public ArrayList<UserRecord> doInBackground(SparseArray<Bitmap>... params) {
                UserRecord guestRecord;
                boolean systemCanCreateUsers;
                boolean z;
                boolean z2;
                boolean canCreateGuest;
                boolean createIsRestricted;
                int index;
                UserRecord guestRecord2;
                Iterator<UserInfo> it;
                boolean z3 = false;
                SparseArray<Bitmap> bitmaps2 = params[0];
                List<UserInfo> infos = UserSwitcherController.this.mUserManager.getUsers(true);
                if (infos == null) {
                    return null;
                }
                ArrayList<UserRecord> records = new ArrayList<>(infos.size());
                int currentId = ActivityManager.getCurrentUser();
                boolean canSwitchUsers = UserSwitcherController.this.mUserManager.canSwitchUsers();
                Iterator<UserInfo> it2 = infos.iterator();
                UserInfo currentUserInfo = null;
                UserRecord guestRecord3 = null;
                while (it2.hasNext()) {
                    UserInfo info = it2.next();
                    boolean isCurrent = currentId == info.id ? true : z3;
                    UserInfo currentUserInfo2 = isCurrent ? info : currentUserInfo;
                    boolean switchToEnabled = (canSwitchUsers || isCurrent) ? true : z3;
                    if (info.isEnabled()) {
                        if (info.isGuest()) {
                            it = it2;
                            UserRecord guestRecord4 = new UserRecord(info, null, true, isCurrent, false, false, canSwitchUsers);
                            guestRecord3 = guestRecord4;
                        } else {
                            guestRecord2 = guestRecord3;
                            it = it2;
                            if (info.supportsSwitchToByUser()) {
                                Bitmap picture = bitmaps2.get(info.id);
                                if (picture == null && (picture = UserSwitcherController.this.mUserManager.getUserIcon(info.id)) != null) {
                                    int avatarSize = UserSwitcherController.this.mContext.getResources().getDimensionPixelSize(R.dimen.max_avatar_size);
                                    picture = Bitmap.createScaledBitmap(picture, avatarSize, avatarSize, true);
                                }
                                int index2 = isCurrent ? 0 : records.size();
                                records.add(index2, new UserRecord(info, picture, false, isCurrent, false, false, switchToEnabled));
                            }
                        }
                        currentUserInfo = currentUserInfo2;
                        it2 = it;
                        z3 = false;
                    } else {
                        guestRecord2 = guestRecord3;
                        it = it2;
                    }
                    guestRecord3 = guestRecord2;
                    currentUserInfo = currentUserInfo2;
                    it2 = it;
                    z3 = false;
                }
                UserRecord guestRecord5 = guestRecord3;
                if (records.size() > 1) {
                    guestRecord = guestRecord5;
                } else {
                    guestRecord = guestRecord5;
                    if (guestRecord != null) {
                    }
                    systemCanCreateUsers = !UserSwitcherController.this.mUserManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM);
                    if (currentUserInfo == null && ((currentUserInfo.isAdmin() || currentUserInfo.id == 0) && systemCanCreateUsers)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean currentUserCanCreateUsers = z;
                    if (systemCanCreateUsers || !addUsersWhenLocked) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    boolean anyoneCanCreateUsers = z2;
                    canCreateGuest = (!currentUserCanCreateUsers || anyoneCanCreateUsers) && guestRecord == null;
                    boolean canCreateUser = (!currentUserCanCreateUsers || anyoneCanCreateUsers) && UserSwitcherController.this.mUserManager.canAddMoreUsers();
                    createIsRestricted = !addUsersWhenLocked;
                    if (UserSwitcherController.this.mSimpleUserSwitcher) {
                        if (guestRecord == null) {
                            if (canCreateGuest) {
                                UserRecord guestRecord6 = new UserRecord(null, null, true, false, false, createIsRestricted, canSwitchUsers);
                                UserSwitcherController.this.checkIfAddUserDisallowedByAdminOnly(guestRecord6);
                                records.add(guestRecord6);
                            }
                        } else {
                            if (guestRecord.isCurrent) {
                                index = 0;
                            } else {
                                index = records.size();
                            }
                            records.add(index, guestRecord);
                        }
                    }
                    if (!UserSwitcherController.this.mSimpleUserSwitcher && canCreateUser) {
                        UserRecord addUserRecord = new UserRecord(null, null, false, false, true, createIsRestricted, canSwitchUsers);
                        UserSwitcherController.this.checkIfAddUserDisallowedByAdminOnly(addUserRecord);
                        records.add(addUserRecord);
                    }
                    return records;
                }
                Prefs.putBoolean(UserSwitcherController.this.mContext, "HasSeenMultiUser", true);
                systemCanCreateUsers = !UserSwitcherController.this.mUserManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM);
                if (currentUserInfo == null) {
                    z = false;
                } else {
                    z = false;
                }
                boolean currentUserCanCreateUsers2 = z;
                if (systemCanCreateUsers) {
                    z2 = false;
                } else {
                    z2 = false;
                }
                boolean anyoneCanCreateUsers2 = z2;
                canCreateGuest = (!currentUserCanCreateUsers2 || anyoneCanCreateUsers2) && guestRecord == null;
                boolean canCreateUser2 = (!currentUserCanCreateUsers2 || anyoneCanCreateUsers2) && UserSwitcherController.this.mUserManager.canAddMoreUsers();
                createIsRestricted = !addUsersWhenLocked;
                if (UserSwitcherController.this.mSimpleUserSwitcher) {
                    if (guestRecord == null) {
                        if (canCreateGuest) {
                            UserRecord guestRecord7 = new UserRecord(null, null, true, false, false, createIsRestricted, canSwitchUsers);
                            UserSwitcherController.this.checkIfAddUserDisallowedByAdminOnly(guestRecord7);
                            records.add(guestRecord7);
                        }
                    } else {
                        if (guestRecord.isCurrent) {
                            index = 0;
                        } else {
                            index = records.size();
                        }
                        records.add(index, guestRecord);
                    }
                }
                if (!UserSwitcherController.this.mSimpleUserSwitcher) {
                    UserRecord addUserRecord2 = new UserRecord(null, null, false, false, true, createIsRestricted, canSwitchUsers);
                    UserSwitcherController.this.checkIfAddUserDisallowedByAdminOnly(addUserRecord2);
                    records.add(addUserRecord2);
                }
                return records;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(ArrayList<UserRecord> userRecords) {
                if (userRecords != null) {
                    UserSwitcherController.this.mUsers = userRecords;
                    UserSwitcherController.this.notifyAdapters();
                }
            }
        }.execute(bitmaps);
    }

    private void pauseRefreshUsers() {
        if (!this.mPauseRefreshUsers) {
            this.mHandler.postDelayed(this.mUnpauseRefreshUsers, 3000L);
            this.mPauseRefreshUsers = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAdapters() {
        for (int i = this.mAdapters.size() - 1; i >= 0; i--) {
            BaseUserAdapter adapter = this.mAdapters.get(i).get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                this.mAdapters.remove(i);
            }
        }
    }

    public boolean isSimpleUserSwitcher() {
        return this.mSimpleUserSwitcher;
    }

    public boolean useFullscreenUserSwitcher() {
        int overrideUseFullscreenUserSwitcher = Settings.System.getInt(this.mContext.getContentResolver(), "enable_fullscreen_user_switcher", -1);
        if (overrideUseFullscreenUserSwitcher != -1) {
            return overrideUseFullscreenUserSwitcher != 0;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_enableFullscreenUserSwitcher);
    }

    public void switchTo(UserRecord record) {
        int id;
        UserInfo currUserInfo;
        if (record.isGuest && record.info == null) {
            UserInfo guest = this.mUserManager.createGuest(this.mContext, this.mContext.getString(R.string.guest_nickname));
            if (guest == null) {
                return;
            } else {
                id = guest.id;
            }
        } else {
            if (record.isAddUser) {
                showAddUserDialog();
                return;
            }
            id = record.info.id;
        }
        int currUserId = ActivityManager.getCurrentUser();
        if (currUserId == id) {
            if (record.isGuest) {
                showExitGuestDialog(id);
            }
        } else if (UserManager.isGuestUserEphemeral() && (currUserInfo = this.mUserManager.getUserInfo(currUserId)) != null && currUserInfo.isGuest()) {
            showExitGuestDialog(currUserId, record.resolveId());
        } else {
            switchToUserId(id);
        }
    }

    public int getSwitchableUserCount() {
        int count = 0;
        int N = this.mUsers.size();
        for (int i = 0; i < N; i++) {
            UserRecord record = this.mUsers.get(i);
            if (record.info != null && record.info.supportsSwitchTo()) {
                count++;
            }
        }
        return count;
    }

    protected void switchToUserId(int id) {
        try {
            pauseRefreshUsers();
            ActivityManager.getService().switchUser(id);
        } catch (RemoteException e) {
            Log.e("UserSwitcherController", "Couldn't switch user.", e);
        }
    }

    private void showExitGuestDialog(int id) {
        UserInfo info;
        int newId = 0;
        if (this.mResumeUserOnGuestLogout && this.mLastNonGuestUser != 0 && (info = this.mUserManager.getUserInfo(this.mLastNonGuestUser)) != null && info.isEnabled() && info.supportsSwitchToByUser()) {
            newId = info.id;
        }
        showExitGuestDialog(id, newId);
    }

    protected void showExitGuestDialog(int id, int targetId) {
        if (this.mExitGuestDialog != null && this.mExitGuestDialog.isShowing()) {
            this.mExitGuestDialog.cancel();
        }
        this.mExitGuestDialog = new ExitGuestDialog(this.mContext, id, targetId);
        this.mExitGuestDialog.show();
    }

    public void showAddUserDialog() {
        if (this.mAddUserDialog != null && this.mAddUserDialog.isShowing()) {
            this.mAddUserDialog.cancel();
        }
        this.mAddUserDialog = new AddUserDialog(this.mContext);
        this.mAddUserDialog.show();
    }

    protected void exitGuest(int id, int targetId) {
        switchToUserId(targetId);
        this.mUserManager.removeUser(id);
    }

    private void listenForCallState() {
        TelephonyManager.from(this.mContext).listen(this.mPhoneStateListener, 32);
    }

    public String getCurrentUserName(Context context) {
        UserRecord item;
        if (this.mUsers.isEmpty() || (item = this.mUsers.get(0)) == null || item.info == null) {
            return null;
        }
        return item.isGuest ? context.getString(R.string.guest_nickname) : item.info.name;
    }

    public void onDensityOrFontScaleChanged() {
        refreshUsers(-1);
    }

    @VisibleForTesting
    public void addAdapter(WeakReference<BaseUserAdapter> adapter) {
        this.mAdapters.add(adapter);
    }

    @VisibleForTesting
    public ArrayList<UserRecord> getUsers() {
        return this.mUsers;
    }

    public static abstract class BaseUserAdapter extends BaseAdapter {
        final UserSwitcherController mController;
        private final KeyguardMonitor mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);

        protected BaseUserAdapter(UserSwitcherController controller) {
            this.mController = controller;
            controller.addAdapter(new WeakReference<>(this));
        }

        @Override // android.widget.Adapter
        public int getCount() {
            boolean secureKeyguardShowing = this.mKeyguardMonitor.isShowing() && this.mKeyguardMonitor.isSecure() && !this.mKeyguardMonitor.canSkipBouncer();
            if (!secureKeyguardShowing) {
                return this.mController.getUsers().size();
            }
            int N = this.mController.getUsers().size();
            int count = 0;
            for (int i = 0; i < N && !this.mController.getUsers().get(i).isRestricted; i++) {
                count++;
            }
            return count;
        }

        @Override // android.widget.Adapter
        public UserRecord getItem(int position) {
            return this.mController.getUsers().get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        public void switchTo(UserRecord record) {
            this.mController.switchTo(record);
        }

        public String getName(Context context, UserRecord item) {
            if (item.isGuest) {
                if (item.isCurrent) {
                    return context.getString(R.string.guest_exit_guest);
                }
                return context.getString(item.info == null ? R.string.guest_new_guest : R.string.guest_nickname);
            }
            if (item.isAddUser) {
                return context.getString(R.string.user_add_user);
            }
            return item.info.name;
        }

        public Drawable getDrawable(Context context, UserRecord item) {
            if (item.isAddUser) {
                return context.getDrawable(R.drawable.ic_add_circle_qs);
            }
            Drawable icon = UserIcons.getDefaultUserIcon(context.getResources(), item.resolveId(), false);
            if (item.isGuest) {
                icon.setColorFilter(Utils.getColorAttr(context, android.R.attr.colorForeground), PorterDuff.Mode.SRC_IN);
            }
            return icon;
        }

        public void refresh() {
            this.mController.refreshUsers(-10000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIfAddUserDisallowedByAdminOnly(UserRecord record) {
        RestrictedLockUtils.EnforcedAdmin admin = RestrictedLockUtils.checkIfRestrictionEnforced(this.mContext, "no_add_user", ActivityManager.getCurrentUser());
        if (admin != null && !RestrictedLockUtils.hasBaseUserRestriction(this.mContext, "no_add_user", ActivityManager.getCurrentUser())) {
            record.isDisabledByAdmin = true;
            record.enforcedAdmin = admin;
        } else {
            record.isDisabledByAdmin = false;
            record.enforcedAdmin = null;
        }
    }

    public void startActivity(Intent intent) {
        this.mActivityStarter.startActivity(intent, true);
    }

    public static final class UserRecord {
        public RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        public final UserInfo info;
        public final boolean isAddUser;
        public final boolean isCurrent;
        public boolean isDisabledByAdmin;
        public final boolean isGuest;
        public final boolean isRestricted;
        public boolean isSwitchToEnabled;
        public final Bitmap picture;

        public UserRecord(UserInfo info, Bitmap picture, boolean isGuest, boolean isCurrent, boolean isAddUser, boolean isRestricted, boolean isSwitchToEnabled) {
            this.info = info;
            this.picture = picture;
            this.isGuest = isGuest;
            this.isCurrent = isCurrent;
            this.isAddUser = isAddUser;
            this.isRestricted = isRestricted;
            this.isSwitchToEnabled = isSwitchToEnabled;
        }

        public UserRecord copyWithIsCurrent(boolean _isCurrent) {
            return new UserRecord(this.info, this.picture, this.isGuest, _isCurrent, this.isAddUser, this.isRestricted, this.isSwitchToEnabled);
        }

        public int resolveId() {
            if (this.isGuest || this.info == null) {
                return -10000;
            }
            return this.info.id;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("UserRecord(");
            if (this.info != null) {
                sb.append("name=\"");
                sb.append(this.info.name);
                sb.append("\" id=");
                sb.append(this.info.id);
            } else if (this.isGuest) {
                sb.append("<add guest placeholder>");
            } else if (this.isAddUser) {
                sb.append("<add user placeholder>");
            }
            if (this.isGuest) {
                sb.append(" <isGuest>");
            }
            if (this.isAddUser) {
                sb.append(" <isAddUser>");
            }
            if (this.isCurrent) {
                sb.append(" <isCurrent>");
            }
            if (this.picture != null) {
                sb.append(" <hasPicture>");
            }
            if (this.isRestricted) {
                sb.append(" <isRestricted>");
            }
            if (this.isDisabledByAdmin) {
                sb.append(" <isDisabledByAdmin>");
                sb.append(" enforcedAdmin=");
                sb.append(this.enforcedAdmin);
            }
            if (this.isSwitchToEnabled) {
                sb.append(" <isSwitchToEnabled>");
            }
            sb.append(')');
            return sb.toString();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.policy.UserSwitcherController$7, reason: invalid class name */
    class AnonymousClass7 implements KeyguardMonitor.Callback {
        AnonymousClass7() {
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardMonitor.Callback
        public void onKeyguardShowingChanged() {
            if (UserSwitcherController.this.mKeyguardMonitor.isShowing()) {
                UserSwitcherController.this.notifyAdapters();
                return;
            }
            Handler handler = UserSwitcherController.this.mHandler;
            final UserSwitcherController userSwitcherController = UserSwitcherController.this;
            handler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$UserSwitcherController$7$pQr4FiWnaYmK1LUVjgYn-vNV4vI
                @Override // java.lang.Runnable
                public final void run() {
                    userSwitcherController.notifyAdapters();
                }
            });
        }
    }

    private final class ExitGuestDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        private final int mGuestId;
        private final int mTargetId;

        public ExitGuestDialog(Context context, int guestId, int targetId) {
            super(context);
            setTitle(R.string.guest_exit_guest_dialog_title);
            setMessage(context.getString(R.string.guest_exit_guest_dialog_message));
            setButton(-2, context.getString(android.R.string.cancel), this);
            setButton(-1, context.getString(R.string.guest_exit_guest_dialog_remove), this);
            SystemUIDialog.setWindowOnTop(this);
            setCanceledOnTouchOutside(false);
            this.mGuestId = guestId;
            this.mTargetId = targetId;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                cancel();
            } else {
                dismiss();
                UserSwitcherController.this.exitGuest(this.mGuestId, this.mTargetId);
            }
        }
    }

    private final class AddUserDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        public AddUserDialog(Context context) {
            super(context);
            setTitle(R.string.user_add_user_title);
            setMessage(context.getString(R.string.user_add_user_message_short));
            setButton(-2, context.getString(android.R.string.cancel), this);
            setButton(-1, context.getString(android.R.string.ok), this);
            SystemUIDialog.setWindowOnTop(this);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            UserInfo user;
            if (which == -2) {
                cancel();
                return;
            }
            dismiss();
            if (ActivityManager.isUserAMonkey() || (user = UserSwitcherController.this.mUserManager.createUser(UserSwitcherController.this.mContext.getString(R.string.user_new_user_name), 0)) == null) {
                return;
            }
            int id = user.id;
            Bitmap icon = UserIcons.convertToBitmap(UserIcons.getDefaultUserIcon(UserSwitcherController.this.mContext.getResources(), id, false));
            UserSwitcherController.this.mUserManager.setUserIcon(id, icon);
            UserSwitcherController.this.switchToUserId(id);
        }
    }
}
