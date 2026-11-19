package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.IConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.KeyChain;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.SecurityControllerImpl.CACertLoader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SecurityControllerImpl extends CurrentUserTracker implements SecurityController {
    private static final boolean DEBUG = Log.isLoggable("SecurityController", 3);
    private static final NetworkRequest REQUEST = new NetworkRequest.Builder().removeCapability(15).removeCapability(13).removeCapability(14).setUids(null).build();
    private final BroadcastReceiver mBroadcastReceiver;

    @GuardedBy("mCallbacks")
    private final ArrayList<SecurityController.SecurityControllerCallback> mCallbacks;
    private final ConnectivityManager mConnectivityManager;
    private final IConnectivityManager mConnectivityManagerService;
    private final Context mContext;
    private int mCurrentUserId;
    private SparseArray<VpnConfig> mCurrentVpns;
    private final DevicePolicyManager mDevicePolicyManager;
    private ArrayMap<Integer, Boolean> mHasCACerts;
    private final ConnectivityManager.NetworkCallback mNetworkCallback;
    private final PackageManager mPackageManager;
    private final UserManager mUserManager;
    private int mVpnUserId;

    public SecurityControllerImpl(Context context) {
        this(context, null);
    }

    public SecurityControllerImpl(Context context, SecurityController.SecurityControllerCallback callback) {
        super(context);
        this.mCallbacks = new ArrayList<>();
        this.mCurrentVpns = new SparseArray<>();
        this.mHasCACerts = new ArrayMap<>();
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onAvailable " + network.netId);
                }
                SecurityControllerImpl.this.updateState();
                SecurityControllerImpl.this.fireCallbacks();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onLost " + network.netId);
                }
                SecurityControllerImpl.this.updateState();
                SecurityControllerImpl.this.fireCallbacks();
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.security.action.TRUST_STORE_CHANGED".equals(intent.getAction())) {
                    SecurityControllerImpl.this.refreshCACerts();
                }
            }
        };
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mConnectivityManagerService = IConnectivityManager.Stub.asInterface(ServiceManager.getService("connectivity"));
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService("user");
        addCallback(callback);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.security.action.TRUST_STORE_CHANGED");
        context.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, filter, null, new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)));
        this.mConnectivityManager.registerNetworkCallback(REQUEST, this.mNetworkCallback);
        onUserSwitched(ActivityManager.getCurrentUser());
        startTracking();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("SecurityController state:");
        pw.print("  mCurrentVpns={");
        for (int i = 0; i < this.mCurrentVpns.size(); i++) {
            if (i > 0) {
                pw.print(", ");
            }
            pw.print(this.mCurrentVpns.keyAt(i));
            pw.print('=');
            pw.print(this.mCurrentVpns.valueAt(i).user);
        }
        pw.println("}");
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean isDeviceManaged() {
        return this.mDevicePolicyManager.isDeviceManaged();
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public CharSequence getDeviceOwnerOrganizationName() {
        return this.mDevicePolicyManager.getDeviceOwnerOrganizationName();
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public CharSequence getWorkProfileOrganizationName() {
        int profileId = getWorkProfileUserId(this.mCurrentUserId);
        if (profileId == -10000) {
            return null;
        }
        return this.mDevicePolicyManager.getOrganizationNameForUser(profileId);
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public String getPrimaryVpnName() {
        VpnConfig cfg = this.mCurrentVpns.get(this.mVpnUserId);
        if (cfg != null) {
            return getNameForVpnConfig(cfg, new UserHandle(this.mVpnUserId));
        }
        return null;
    }

    private int getWorkProfileUserId(int userId) {
        for (UserInfo userInfo : this.mUserManager.getProfiles(userId)) {
            if (userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean hasWorkProfile() {
        return getWorkProfileUserId(this.mCurrentUserId) != -10000;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public String getWorkProfileVpnName() {
        VpnConfig cfg;
        int profileId = getWorkProfileUserId(this.mVpnUserId);
        if (profileId == -10000 || (cfg = this.mCurrentVpns.get(profileId)) == null) {
            return null;
        }
        return getNameForVpnConfig(cfg, UserHandle.of(profileId));
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean isNetworkLoggingEnabled() {
        return this.mDevicePolicyManager.isNetworkLoggingEnabled(null);
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean isVpnEnabled() {
        for (int profileId : this.mUserManager.getProfileIdsWithDisabled(this.mVpnUserId)) {
            if (this.mCurrentVpns.get(profileId) != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean isVpnBranded() {
        String packageName;
        VpnConfig cfg = this.mCurrentVpns.get(this.mVpnUserId);
        if (cfg == null || (packageName = getPackageNameForVpnConfig(cfg)) == null) {
            return false;
        }
        return isVpnPackageBranded(packageName);
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean hasCACertInCurrentUser() {
        Boolean hasCACerts = this.mHasCACerts.get(Integer.valueOf(this.mCurrentUserId));
        return hasCACerts != null && hasCACerts.booleanValue();
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController
    public boolean hasCACertInWorkProfile() {
        Boolean hasCACerts;
        int userId = getWorkProfileUserId(this.mCurrentUserId);
        return (userId == -10000 || (hasCACerts = this.mHasCACerts.get(Integer.valueOf(userId))) == null || !hasCACerts.booleanValue()) ? false : true;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(SecurityController.SecurityControllerCallback callback) {
        synchronized (this.mCallbacks) {
            try {
                if (callback == null) {
                    return;
                }
                if (DEBUG) {
                    Log.d("SecurityController", "removeCallback " + callback);
                }
                this.mCallbacks.remove(callback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(SecurityController.SecurityControllerCallback callback) {
        synchronized (this.mCallbacks) {
            if (callback != null) {
                try {
                    if (!this.mCallbacks.contains(callback)) {
                        if (DEBUG) {
                            Log.d("SecurityController", "addCallback " + callback);
                        }
                        this.mCallbacks.add(callback);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.systemui.settings.CurrentUserTracker
    public void onUserSwitched(int newUserId) {
        this.mCurrentUserId = newUserId;
        UserInfo newUserInfo = this.mUserManager.getUserInfo(newUserId);
        if (newUserInfo.isRestricted()) {
            this.mVpnUserId = newUserInfo.restrictedProfileParentId;
        } else {
            this.mVpnUserId = this.mCurrentUserId;
        }
        refreshCACerts();
        fireCallbacks();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCACerts() {
        new CACertLoader().execute(Integer.valueOf(this.mCurrentUserId));
        int workProfileId = getWorkProfileUserId(this.mCurrentUserId);
        if (workProfileId != -10000) {
            new CACertLoader().execute(Integer.valueOf(workProfileId));
        }
    }

    private String getNameForVpnConfig(VpnConfig cfg, UserHandle user) {
        if (cfg.legacy) {
            return this.mContext.getString(R.string.legacy_vpn_name);
        }
        String vpnPackage = cfg.user;
        try {
            Context userContext = this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, user);
            return VpnConfig.getVpnLabel(userContext, vpnPackage).toString();
        } catch (PackageManager.NameNotFoundException nnfe) {
            Log.e("SecurityController", "Package " + vpnPackage + " is not present", nnfe);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireCallbacks() {
        synchronized (this.mCallbacks) {
            for (SecurityController.SecurityControllerCallback callback : this.mCallbacks) {
                callback.onStateChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateState() {
        LegacyVpnInfo legacyVpn;
        SparseArray<VpnConfig> vpns = new SparseArray<>();
        try {
            for (UserInfo user : this.mUserManager.getUsers()) {
                VpnConfig cfg = this.mConnectivityManagerService.getVpnConfig(user.id);
                if (cfg != null && (!cfg.legacy || ((legacyVpn = this.mConnectivityManagerService.getLegacyVpnInfo(user.id)) != null && legacyVpn.state == 3))) {
                    vpns.put(user.id, cfg);
                }
            }
            this.mCurrentVpns = vpns;
        } catch (RemoteException rme) {
            Log.e("SecurityController", "Unable to list active VPNs", rme);
        }
    }

    private String getPackageNameForVpnConfig(VpnConfig cfg) {
        if (cfg.legacy) {
            return null;
        }
        return cfg.user;
    }

    private boolean isVpnPackageBranded(String packageName) {
        try {
            ApplicationInfo info = this.mPackageManager.getApplicationInfo(packageName, 128);
            if (info != null && info.metaData != null && info.isSystemApp()) {
                boolean isBranded = info.metaData.getBoolean("com.android.systemui.IS_BRANDED", false);
                return isBranded;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected class CACertLoader extends AsyncTask<Integer, Void, Pair<Integer, Boolean>> {
        protected CACertLoader() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Code duplicated, block: B:15:0x0044 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:20:0x004f A[Catch: RemoteException | AssertionError | InterruptedException -> 0x0053, RemoteException | AssertionError | InterruptedException -> 0x0053, RemoteException | AssertionError | InterruptedException -> 0x0053, TryCatch #1 {RemoteException | AssertionError | InterruptedException -> 0x0053, blocks: (B:3:0x0002, B:6:0x0035, B:6:0x0035, B:6:0x0035, B:16:0x0046, B:16:0x0046, B:16:0x0046, B:20:0x004f, B:20:0x004f, B:20:0x004f, B:19:0x004b, B:19:0x004b, B:19:0x004b, B:21:0x0052, B:21:0x0052, B:21:0x0052), top: B:27:0x0002 }] */
        /* JADX WARN: Code duplicated, block: B:25:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:32:? A[Catch: RemoteException | AssertionError | InterruptedException -> 0x0053, RemoteException | AssertionError | InterruptedException -> 0x0053, RemoteException | AssertionError | InterruptedException -> 0x0053, SYNTHETIC, TRY_LEAVE, TryCatch #1 {RemoteException | AssertionError | InterruptedException -> 0x0053, blocks: (B:3:0x0002, B:6:0x0035, B:6:0x0035, B:6:0x0035, B:16:0x0046, B:16:0x0046, B:16:0x0046, B:20:0x004f, B:20:0x004f, B:20:0x004f, B:19:0x004b, B:19:0x004b, B:19:0x004b, B:21:0x0052, B:21:0x0052, B:21:0x0052), top: B:27:0x0002 }] */
        @Override // android.os.AsyncTask
        public Pair<Integer, Boolean> doInBackground(final Integer... userId) {
            Throwable th;
            try {
                KeyChain.KeyChainConnection conn = KeyChain.bindAsUser(SecurityControllerImpl.this.mContext, UserHandle.of(userId[0].intValue()));
                try {
                    boolean hasCACerts = !conn.getService().getUserCaAliases().getList().isEmpty();
                    Pair<Integer, Boolean> pair = new Pair<>(userId[0], Boolean.valueOf(hasCACerts));
                    if (conn != null) {
                        conn.close();
                    }
                    return pair;
                } catch (Throwable th2) {
                    th = th2;
                    th = null;
                    if (conn != null) {
                        throw th;
                    }
                    if (th == null) {
                        conn.close();
                        throw th;
                    }
                    conn.close();
                    throw th;
                }
            } catch (RemoteException | AssertionError | InterruptedException e) {
                Log.i("SecurityController", "CACertLoader doInBackground error : ", e);
                new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$SecurityControllerImpl$CACertLoader$xO5ELH-ynhsu1kwnRVzV4aHRUJ0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecurityControllerImpl.this.new CACertLoader().execute(userId[0]);
                    }
                }, 30000L);
                return new Pair<>(userId[0], null);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Pair<Integer, Boolean> result) {
            if (SecurityControllerImpl.DEBUG) {
                Log.d("SecurityController", "onPostExecute " + result);
            }
            if (result.second != null) {
                SecurityControllerImpl.this.mHasCACerts.put((Integer) result.first, (Boolean) result.second);
                SecurityControllerImpl.this.fireCallbacks();
            }
        }
    }
}
