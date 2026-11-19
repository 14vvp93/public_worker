package com.android.systemui;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class ForegroundServiceControllerImpl implements ForegroundServiceController {
    private final Context mContext;
    private final SparseArray<UserServices> mUserServices = new SparseArray<>();
    private final Object mMutex = new Object();

    public ForegroundServiceControllerImpl(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.ForegroundServiceController
    public boolean isDungeonNeededForUser(int userId) {
        synchronized (this.mMutex) {
            UserServices services = this.mUserServices.get(userId);
            if (services == null) {
                return false;
            }
            return services.isDungeonNeeded();
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public boolean isSystemAlertWarningNeeded(int userId, String pkg) {
        synchronized (this.mMutex) {
            UserServices services = this.mUserServices.get(userId);
            if (services == null) {
                return false;
            }
            return services.getStandardLayoutKey(pkg) == null;
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public String getStandardLayoutKey(int userId, String pkg) {
        synchronized (this.mMutex) {
            UserServices services = this.mUserServices.get(userId);
            if (services == null) {
                return null;
            }
            return services.getStandardLayoutKey(pkg);
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public ArraySet<Integer> getAppOps(int userId, String pkg) {
        synchronized (this.mMutex) {
            UserServices services = this.mUserServices.get(userId);
            if (services == null) {
                return null;
            }
            return services.getFeatures(pkg);
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public void onAppOpChanged(int code, int uid, String packageName, boolean active) {
        int userId = UserHandle.getUserId(uid);
        synchronized (this.mMutex) {
            UserServices userServices = this.mUserServices.get(userId);
            if (userServices == null) {
                userServices = new UserServices();
                this.mUserServices.put(userId, userServices);
            }
            if (active) {
                userServices.addOp(packageName, code);
            } else {
                userServices.removeOp(packageName, code);
            }
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public void addNotification(StatusBarNotification sbn, int importance) {
        updateNotification(sbn, importance);
    }

    @Override // com.android.systemui.ForegroundServiceController
    public boolean removeNotification(StatusBarNotification sbn) {
        synchronized (this.mMutex) {
            UserServices userServices = this.mUserServices.get(sbn.getUserId());
            if (userServices == null) {
                return false;
            }
            if (isDungeonNotification(sbn)) {
                userServices.setRunningServices(null, 0L);
                return true;
            }
            return userServices.removeNotification(sbn.getPackageName(), sbn.getKey());
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public void updateNotification(StatusBarNotification sbn, int newImportance) {
        synchronized (this.mMutex) {
            UserServices userServices = this.mUserServices.get(sbn.getUserId());
            if (userServices == null) {
                userServices = new UserServices();
                this.mUserServices.put(sbn.getUserId(), userServices);
            }
            if (isDungeonNotification(sbn)) {
                Bundle extras = sbn.getNotification().extras;
                if (extras != null) {
                    String[] svcs = extras.getStringArray("android.foregroundApps");
                    userServices.setRunningServices(svcs, sbn.getNotification().when);
                }
            } else {
                userServices.removeNotification(sbn.getPackageName(), sbn.getKey());
                if ((sbn.getNotification().flags & 64) != 0) {
                    if (newImportance > 1) {
                        userServices.addImportantNotification(sbn.getPackageName(), sbn.getKey());
                    }
                    Notification.Builder builder = Notification.Builder.recoverBuilder(this.mContext, sbn.getNotification());
                    if (builder.usesStandardHeader()) {
                        userServices.addStandardLayoutNotification(sbn.getPackageName(), sbn.getKey());
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.ForegroundServiceController
    public boolean isDungeonNotification(StatusBarNotification sbn) {
        return sbn.getId() == 40 && sbn.getTag() == null;
    }

    @Override // com.android.systemui.ForegroundServiceController
    public boolean isSystemAlertNotification(StatusBarNotification sbn) {
        return sbn.getPackageName().equals("android") && sbn.getTag() != null && sbn.getTag().contains("AlertWindowNotification");
    }

    private static class UserServices {
        private ArrayMap<String, ArraySet<Integer>> mAppOps;
        private ArrayMap<String, ArraySet<String>> mImportantNotifications;
        private String[] mRunning;
        private long mServiceStartTime;
        private ArrayMap<String, ArraySet<String>> mStandardLayoutNotifications;

        private UserServices() {
            this.mRunning = null;
            this.mServiceStartTime = 0L;
            this.mImportantNotifications = new ArrayMap<>(1);
            this.mStandardLayoutNotifications = new ArrayMap<>(1);
            this.mAppOps = new ArrayMap<>(1);
        }

        public void setRunningServices(String[] pkgs, long serviceStartTime) {
            this.mRunning = pkgs != null ? (String[]) Arrays.copyOf(pkgs, pkgs.length) : null;
            this.mServiceStartTime = serviceStartTime;
        }

        public void addOp(String pkg, int op) {
            if (this.mAppOps.get(pkg) == null) {
                this.mAppOps.put(pkg, new ArraySet<>(3));
            }
            this.mAppOps.get(pkg).add(Integer.valueOf(op));
        }

        public boolean removeOp(String pkg, int op) {
            ArraySet<Integer> keys = this.mAppOps.get(pkg);
            if (keys == null) {
                return false;
            }
            boolean found = keys.remove(Integer.valueOf(op));
            if (keys.size() == 0) {
                this.mAppOps.remove(pkg);
                return found;
            }
            return found;
        }

        public void addImportantNotification(String pkg, String key) {
            addNotification(this.mImportantNotifications, pkg, key);
        }

        public boolean removeImportantNotification(String pkg, String key) {
            return removeNotification(this.mImportantNotifications, pkg, key);
        }

        public void addStandardLayoutNotification(String pkg, String key) {
            addNotification(this.mStandardLayoutNotifications, pkg, key);
        }

        public boolean removeStandardLayoutNotification(String pkg, String key) {
            return removeNotification(this.mStandardLayoutNotifications, pkg, key);
        }

        public boolean removeNotification(String pkg, String key) {
            boolean removed = false | removeImportantNotification(pkg, key);
            return removed | removeStandardLayoutNotification(pkg, key);
        }

        public void addNotification(ArrayMap<String, ArraySet<String>> map, String pkg, String key) {
            if (map.get(pkg) == null) {
                map.put(pkg, new ArraySet<>());
            }
            map.get(pkg).add(key);
        }

        public boolean removeNotification(ArrayMap<String, ArraySet<String>> map, String pkg, String key) {
            ArraySet<String> keys = map.get(pkg);
            if (keys == null) {
                return false;
            }
            boolean found = keys.remove(key);
            if (keys.size() == 0) {
                map.remove(pkg);
                return found;
            }
            return found;
        }

        public boolean isDungeonNeeded() {
            if (this.mRunning != null && System.currentTimeMillis() - this.mServiceStartTime >= 5000) {
                for (String pkg : this.mRunning) {
                    ArraySet<String> set = this.mImportantNotifications.get(pkg);
                    if (set == null || set.size() == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        public ArraySet<Integer> getFeatures(String pkg) {
            return this.mAppOps.get(pkg);
        }

        public String getStandardLayoutKey(String pkg) {
            ArraySet<String> set = this.mStandardLayoutNotifications.get(pkg);
            if (set == null || set.size() == 0) {
                return null;
            }
            return set.valueAt(0);
        }

        public String toString() {
            return "UserServices{mRunning=" + Arrays.toString(this.mRunning) + ", mServiceStartTime=" + this.mServiceStartTime + ", mImportantNotifications=" + this.mImportantNotifications + ", mStandardLayoutNotifications=" + this.mStandardLayoutNotifications + '}';
        }
    }
}
