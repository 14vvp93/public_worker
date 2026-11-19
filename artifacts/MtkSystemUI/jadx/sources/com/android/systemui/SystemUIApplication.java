package com.android.systemui;

import android.Configures.HConfig;
import android.app.ActivityThread;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.TimingsTraceLog;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarWindowManager;
import com.android.systemui.util.NotificationChannels;
import com.hcn.auto.systemui.SkinManager;
import com.hcn.auto.theme.utils.ThemeUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SystemUIApplication extends Application implements SysUiServiceProvider {
    private boolean mBootCompleted;
    private final Map<Class<?>, Object> mComponents = new HashMap();
    private boolean mHomeReady;
    private SystemUI[] mServices;
    private boolean mServicesStarted;
    private static SystemUIApplication gInst = null;
    private static boolean mIsPortraitDevice = false;
    private static boolean mbSupportRotation = false;
    private static boolean mbSupportBotNavis = false;
    private static int mbSupportSingleNavis = 0;
    private static int mbNaviWidth = 0;
    public static final String CUSTOM_NAME = SystemProperties.get("persist.sys.customer", "");

    public static boolean isPortraitDevice() {
        return mIsPortraitDevice;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        gInst = this;
        SkinManager skinManager = SkinManager.getInstance(this);
        skinManager.loadSkin();
        int hwRotation = SystemProperties.getInt("sys.disp.force_direct", 1);
        if (2 == hwRotation) {
            mIsPortraitDevice = true;
        }
        if (HConfig.isRotationTypeConfigured(this)) {
            mbSupportRotation = true;
        }
        mbSupportSingleNavis = SystemProperties.getInt("sys.disp.navigationbarposition", 0);
        mbNaviWidth = SystemProperties.getInt("ro.sys.customer.naviwidth", 0);
        if (SystemProperties.getInt("ro.sys.customer.naviheight", 0) > 1) {
            mbSupportBotNavis = true;
        }
        setTheme(2131886644);
        SystemUIFactory.createFromConfig(this);
        ThemeUtils.init(this);
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            IntentFilter bootCompletedFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
            bootCompletedFilter.setPriority(1000);
            registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (SystemUIApplication.this.mBootCompleted) {
                        return;
                    }
                    SystemUIApplication.this.unregisterReceiver(this);
                    SystemUIApplication.this.mBootCompleted = true;
                    if (SystemUIApplication.this.mServicesStarted) {
                        int N = SystemUIApplication.this.mServices.length;
                        for (int i = 0; i < N; i++) {
                            SystemUIApplication.this.mServices[i].onBootCompleted();
                        }
                    }
                }
            }, bootCompletedFilter);
            if (SystemProperties.getBoolean("ro.atc.boot_time", false)) {
                IntentFilter homeReadyFilter = new IntentFilter("android.intent.action.HOME_READY");
                homeReadyFilter.setPriority(1000);
                registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.2
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        if (SystemUIApplication.this.mHomeReady) {
                            return;
                        }
                        SystemUIApplication.this.mHomeReady = true;
                        SystemUIApplication.this.unregisterReceiver(this);
                        SystemUIApplication.this.startOtherServicesAtc();
                    }
                }, homeReadyFilter);
            }
            IntentFilter localeChangedFilter = new IntentFilter("android.intent.action.LOCALE_CHANGED");
            registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction()) && SystemUIApplication.this.mBootCompleted) {
                        NotificationChannels.createAll(context);
                    }
                }
            }, localeChangedFilter);
            return;
        }
        String processName = ActivityThread.currentProcessName();
        ApplicationInfo info = getApplicationInfo();
        if (processName != null) {
            if (processName.startsWith(info.processName + ":")) {
                return;
            }
        }
        startSecondaryUserServicesIfNeeded();
    }

    public static boolean isPortraitWindowMode(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        boolean isRotationTypeDevice = HConfig.isRotationTypeConfigured(context);
        int v = Settings.System.getInt(context.getContentResolver(), "user_rotation", 0);
        boolean isRotationPortraitState = !mIsPortraitDevice && isRotationTypeDevice && v == 1 && configuration.orientation == 1;
        return (mIsPortraitDevice && v == 0) || isRotationPortraitState;
    }

    public static boolean hasBottomBar() {
        return mbSupportBotNavis && !mIsPortraitDevice;
    }

    public static int getNaviBarPosition() {
        return mbSupportSingleNavis;
    }

    public void startServicesIfNeeded() {
        String[] names;
        if (SystemProperties.getBoolean("ro.atc.boot_time", false)) {
            names = getResources().getStringArray(R.array.config_systemUIServiceComponents_atc_normal);
        } else {
            names = getResources().getStringArray(R.array.config_systemUIServiceComponents);
        }
        startServicesIfNeeded(names);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startOtherServicesAtc() {
        String[] names = getResources().getStringArray(R.array.config_systemUIServiceComponents_atc_later);
        startServicesIfNeeded(names, true);
    }

    void startSecondaryUserServicesIfNeeded() {
        String[] names = getResources().getStringArray(R.array.config_systemUIServiceComponentsPerUser);
        startServicesIfNeeded(names);
    }

    private void startServicesIfNeeded(String[] services) {
        startServicesIfNeeded(services, false);
    }

    private void startServicesIfNeeded(String[] services, boolean force) {
        if (this.mServicesStarted && !force) {
            return;
        }
        this.mServices = new SystemUI[services.length];
        if (!this.mBootCompleted && "1".equals(SystemProperties.get("sys.boot_completed"))) {
            this.mBootCompleted = true;
        }
        Log.v("SystemUIService", "Starting SystemUI services for user " + Process.myUserHandle().getIdentifier() + ".");
        TimingsTraceLog log = new TimingsTraceLog("SystemUIBootTiming", 4096L);
        log.traceBegin("StartServices");
        int N = services.length;
        for (int i = 0; i < N; i++) {
            String clsName = services[i];
            log.traceBegin("StartServices" + clsName);
            long ti = System.currentTimeMillis();
            try {
                Class<?> cls = Class.forName(clsName);
                this.mServices[i] = (SystemUI) cls.newInstance();
                this.mServices[i].mContext = this;
                this.mServices[i].mComponents = this.mComponents;
                this.mServices[i].start();
                log.traceEnd();
                long ti2 = System.currentTimeMillis() - ti;
                if (ti2 > 1000) {
                    Log.w("SystemUIService", "Initialization of " + cls.getName() + " took " + ti2 + " ms");
                }
                if (this.mBootCompleted) {
                    this.mServices[i].onBootCompleted();
                }
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2);
            } catch (InstantiationException ex3) {
                throw new RuntimeException(ex3);
            }
        }
        log.traceEnd();
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) new AnonymousClass4(), OverlayPlugin.class, true);
        this.mServicesStarted = true;
    }

    /* JADX INFO: renamed from: com.android.systemui.SystemUIApplication$4, reason: invalid class name */
    class AnonymousClass4 implements PluginListener<OverlayPlugin> {
        private ArraySet<OverlayPlugin> mOverlays;

        AnonymousClass4() {
        }

        @Override // com.android.systemui.plugins.PluginListener
        public void onPluginConnected(OverlayPlugin plugin, Context pluginContext) {
            StatusBar statusBar = (StatusBar) SystemUIApplication.this.getComponent(StatusBar.class);
            if (statusBar != null) {
                plugin.setup(statusBar.getStatusBarWindow(), statusBar.getNavigationBarView());
            }
            if (this.mOverlays == null) {
                this.mOverlays = new ArraySet<>();
            }
            if (plugin.holdStatusBarOpen()) {
                this.mOverlays.add(plugin);
                ((StatusBarWindowManager) Dependency.get(StatusBarWindowManager.class)).setStateListener(new StatusBarWindowManager.OtherwisedCollapsedListener() { // from class: com.android.systemui.-$$Lambda$SystemUIApplication$4$CvffmwwthiNtsxy5Qcfs4SCCXOk
                    @Override // com.android.systemui.statusbar.phone.StatusBarWindowManager.OtherwisedCollapsedListener
                    public final void setWouldOtherwiseCollapse(boolean z) {
                        this.f$0.mOverlays.forEach(new Consumer() { // from class: com.android.systemui.-$$Lambda$SystemUIApplication$4$szPUgQW-PICWudQc52pgcEp9wTM
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((OverlayPlugin) obj).setCollapseDesired(z);
                            }
                        });
                    }
                });
                ((StatusBarWindowManager) Dependency.get(StatusBarWindowManager.class)).setForcePluginOpen(this.mOverlays.size() != 0);
            }
        }

        @Override // com.android.systemui.plugins.PluginListener
        public void onPluginDisconnected(OverlayPlugin plugin) {
            this.mOverlays.remove(plugin);
            ((StatusBarWindowManager) Dependency.get(StatusBarWindowManager.class)).setForcePluginOpen(this.mOverlays.size() != 0);
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mServicesStarted) {
            int len = this.mServices.length;
            for (int i = 0; i < len; i++) {
                if (this.mServices[i] != null) {
                    this.mServices[i].onConfigurationChanged(newConfig);
                }
            }
        }
    }

    @Override // com.android.systemui.SysUiServiceProvider
    public <T> T getComponent(Class<T> cls) {
        return (T) this.mComponents.get(cls);
    }

    public SystemUI[] getServices() {
        return this.mServices;
    }
}
