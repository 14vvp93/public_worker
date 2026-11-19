package com.android.settingslib.users;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.util.UserIcons;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class UserManagerHelper {
    private final ActivityManager mActivityManager;
    private final Context mContext;
    private OnUsersUpdateListener mUpdateListener;
    private final BroadcastReceiver mUserChangeReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.users.UserManagerHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            UserManagerHelper.this.mUpdateListener.onUsersUpdate();
        }
    };
    private final UserManager mUserManager;

    public interface OnUsersUpdateListener {
        void onUsersUpdate();
    }

    public UserManagerHelper(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
    }

    public void registerOnUsersUpdateListener(OnUsersUpdateListener listener) {
        this.mUpdateListener = listener;
        registerReceiver();
    }

    public void unregisterOnUsersUpdateListener() {
        unregisterReceiver();
    }

    public boolean isHeadlessSystemUser() {
        return SystemProperties.getBoolean("android.car.systemuser.headless", false);
    }

    public UserInfo getForegroundUserInfo() {
        return this.mUserManager.getUserInfo(getForegroundUserId());
    }

    public int getForegroundUserId() {
        ActivityManager activityManager = this.mActivityManager;
        return ActivityManager.getCurrentUser();
    }

    public List<UserInfo> getAllUsersExcludesSystemUser() {
        return getAllUsersExceptUser(0);
    }

    public List<UserInfo> getAllUsersExceptUser(int userId) {
        List<UserInfo> others = this.mUserManager.getUsers(true);
        Iterator<UserInfo> iterator = others.iterator();
        while (iterator.hasNext()) {
            UserInfo userInfo = iterator.next();
            if (userInfo.id == userId) {
                iterator.remove();
            }
        }
        return others;
    }

    public List<UserInfo> getAllUsers() {
        if (isHeadlessSystemUser()) {
            return getAllUsersExcludesSystemUser();
        }
        return this.mUserManager.getUsers(true);
    }

    public boolean userIsSystemUser(UserInfo userInfo) {
        return userInfo.id == 0;
    }

    public boolean foregroundUserIsGuestUser() {
        return getForegroundUserInfo().isGuest();
    }

    public boolean foregroundUserHasUserRestriction(String restriction) {
        return this.mUserManager.hasUserRestriction(restriction, getForegroundUserInfo().getUserHandle());
    }

    public boolean foregroundUserCanAddUsers() {
        return !foregroundUserHasUserRestriction("no_add_user");
    }

    public UserInfo createNewUser(String userName) {
        UserInfo user = this.mUserManager.createUser(userName, 0);
        if (user == null) {
            Log.w("UserManagerHelper", "can't create user.");
            return null;
        }
        assignDefaultIcon(user);
        return user;
    }

    public void switchToUser(UserInfo userInfo) {
        if (userInfo.id == getForegroundUserId()) {
            return;
        }
        switchToUserId(userInfo.id);
    }

    public void startNewGuestSession(String guestName) {
        UserInfo guest = this.mUserManager.createGuest(this.mContext, guestName);
        if (guest == null) {
            Log.w("UserManagerHelper", "can't create user.");
        } else {
            assignDefaultIcon(guest);
            switchToUserId(guest.id);
        }
    }

    public Bitmap getUserIcon(UserInfo userInfo) {
        Bitmap picture = this.mUserManager.getUserIcon(userInfo.id);
        if (picture == null) {
            return assignDefaultIcon(userInfo);
        }
        return picture;
    }

    public Bitmap getUserDefaultIcon(UserInfo userInfo) {
        return UserIcons.convertToBitmap(UserIcons.getDefaultUserIcon(this.mContext.getResources(), userInfo.id, false));
    }

    public Bitmap getGuestDefaultIcon() {
        return UserIcons.convertToBitmap(UserIcons.getDefaultUserIcon(this.mContext.getResources(), -10000, false));
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.USER_REMOVED");
        filter.addAction("android.intent.action.USER_ADDED");
        filter.addAction("android.intent.action.USER_INFO_CHANGED");
        filter.addAction("android.intent.action.USER_SWITCHED");
        filter.addAction("android.intent.action.USER_STOPPED");
        filter.addAction("android.intent.action.USER_UNLOCKED");
        this.mContext.registerReceiverAsUser(this.mUserChangeReceiver, UserHandle.ALL, filter, null, null);
    }

    private Bitmap assignDefaultIcon(UserInfo userInfo) {
        Bitmap bitmap = userInfo.isGuest() ? getGuestDefaultIcon() : getUserDefaultIcon(userInfo);
        this.mUserManager.setUserIcon(userInfo.id, bitmap);
        return bitmap;
    }

    private void switchToUserId(int id) {
        try {
            this.mActivityManager.switchUser(id);
        } catch (Exception e) {
            Log.e("UserManagerHelper", "Couldn't switch user.", e);
        }
    }

    private void unregisterReceiver() {
        this.mContext.unregisterReceiver(this.mUserChangeReceiver);
    }
}
