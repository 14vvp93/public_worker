package com.android.systemui.plugins;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import dalvik.system.PathClassLoader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PluginManagerImpl extends BroadcastReceiver implements PluginManager {
    private final boolean isDebuggable;
    private final Map<String, ClassLoader> mClassLoaders;
    private final Context mContext;
    private final PluginInstanceManagerFactory mFactory;
    private boolean mHasOneShot;
    private boolean mListening;
    private Looper mLooper;
    private final ArraySet<String> mOneShotPackages;
    private ClassLoaderFilter mParentClassLoader;
    private final ArrayMap<PluginListener<?>, PluginInstanceManager> mPluginMap;
    private final PluginPrefs mPluginPrefs;
    private boolean mWtfsSet;

    public PluginManagerImpl(Context context) {
        this(context, new PluginInstanceManagerFactory(), Build.IS_DEBUGGABLE, Thread.getUncaughtExceptionPreHandler());
    }

    @VisibleForTesting
    PluginManagerImpl(Context context, PluginInstanceManagerFactory factory, boolean debuggable, Thread.UncaughtExceptionHandler defaultHandler) {
        this.mPluginMap = new ArrayMap<>();
        this.mClassLoaders = new ArrayMap();
        this.mOneShotPackages = new ArraySet<>();
        this.mContext = context;
        this.mFactory = factory;
        this.mLooper = (Looper) Dependency.get(Dependency.BG_LOOPER);
        this.isDebuggable = debuggable;
        this.mPluginPrefs = new PluginPrefs(this.mContext);
        PluginExceptionHandler uncaughtExceptionHandler = new PluginExceptionHandler(defaultHandler);
        Thread.setUncaughtExceptionPreHandler(uncaughtExceptionHandler);
        if (this.isDebuggable) {
            new Handler(this.mLooper).post(new Runnable() { // from class: com.android.systemui.plugins.-$$Lambda$PluginManagerImpl$DDmEEnITAENQNECHR1R9V5n2bfQ
                @Override // java.lang.Runnable
                public final void run() {
                    ((PluginDependencyProvider) Dependency.get(PluginDependencyProvider.class)).allowPluginDependency(ActivityStarter.class);
                }
            });
        }
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> T getOneShotPlugin(Class<T> cls) {
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface == null) {
            throw new RuntimeException(cls + " doesn't provide an interface");
        }
        if (TextUtils.isEmpty(providesInterface.action())) {
            throw new RuntimeException(cls + " doesn't provide an action");
        }
        return (T) getOneShotPlugin(providesInterface.action(), cls);
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> T getOneShotPlugin(String action, Class<?> cls) {
        if (!this.isDebuggable) {
            return null;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Must be called from UI thread");
        }
        PluginInstanceManager<T> p = this.mFactory.createPluginInstanceManager(this.mContext, action, null, false, this.mLooper, cls, this);
        this.mPluginPrefs.addAction(action);
        PluginInstanceManager.PluginInfo<T> info = p.getPlugin();
        if (info == null) {
            return null;
        }
        this.mOneShotPackages.add(info.mPackage);
        this.mHasOneShot = true;
        startListening();
        return info.mPlugin;
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> void addPluginListener(PluginListener<T> listener, Class<?> cls) {
        addPluginListener((PluginListener) listener, cls, false);
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> void addPluginListener(PluginListener<T> listener, Class<?> cls, boolean allowMultiple) {
        addPluginListener(PluginManager.getAction(cls), listener, cls, allowMultiple);
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> void addPluginListener(String action, PluginListener<T> listener, Class<?> cls) {
        addPluginListener(action, listener, cls, false);
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T extends Plugin> void addPluginListener(String action, PluginListener<T> listener, Class cls, boolean allowMultiple) {
        if (!this.isDebuggable) {
            return;
        }
        this.mPluginPrefs.addAction(action);
        PluginInstanceManager p = this.mFactory.createPluginInstanceManager(this.mContext, action, listener, allowMultiple, this.mLooper, cls, this);
        p.loadAll();
        this.mPluginMap.put(listener, p);
        startListening();
    }

    @Override // com.android.systemui.plugins.PluginManager
    public void removePluginListener(PluginListener<?> listener) {
        if (this.isDebuggable && this.mPluginMap.containsKey(listener)) {
            this.mPluginMap.remove(listener).destroy();
            if (this.mPluginMap.size() == 0) {
                stopListening();
            }
        }
    }

    private void startListening() {
        if (this.mListening) {
            return;
        }
        this.mListening = true;
        IntentFilter filter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_CHANGED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction(PluginManager.PLUGIN_CHANGED);
        filter.addAction("com.android.systemui.action.DISABLE_PLUGIN");
        filter.addDataScheme("package");
        this.mContext.registerReceiver(this, filter);
        this.mContext.registerReceiver(this, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    private void stopListening() {
        if (!this.mListening || this.mHasOneShot) {
            return;
        }
        this.mListening = false;
        this.mContext.unregisterReceiver(this);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
            for (PluginInstanceManager manager : this.mPluginMap.values()) {
                manager.loadAll();
            }
            return;
        }
        if ("com.android.systemui.action.DISABLE_PLUGIN".equals(intent.getAction())) {
            Uri uri = intent.getData();
            ComponentName component = ComponentName.unflattenFromString(uri.toString().substring(10));
            this.mContext.getPackageManager().setComponentEnabledSetting(component, 2, 1);
            ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).cancel(component.getClassName(), 6);
            return;
        }
        Uri data = intent.getData();
        String pkg = data.getEncodedSchemeSpecificPart();
        if (this.mOneShotPackages.contains(pkg)) {
            int icon = this.mContext.getResources().getIdentifier("tuner", "drawable", this.mContext.getPackageName());
            int color = Resources.getSystem().getIdentifier("system_notification_accent_color", "color", "android");
            String label = pkg;
            try {
                PackageManager pm = this.mContext.getPackageManager();
                label = pm.getApplicationInfo(pkg, 0).loadLabel(pm).toString();
            } catch (PackageManager.NameNotFoundException e) {
            }
            Notification.Builder nb = new Notification.Builder(this.mContext, PluginManager.NOTIFICATION_CHANNEL_ID).setSmallIcon(icon).setWhen(0L).setShowWhen(false).setPriority(2).setVisibility(1).setColor(this.mContext.getColor(color)).setContentTitle("Plugin \"" + label + "\" has updated").setContentText("Restart SysUI for changes to take effect.");
            Intent i = new Intent("com.android.systemui.action.RESTART").setData(Uri.parse("package://" + pkg));
            PendingIntent pi = PendingIntent.getBroadcast(this.mContext, 0, i, 0);
            nb.addAction(new Notification.Action.Builder((Icon) null, "Restart SysUI", pi).build());
            ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).notifyAsUser(pkg, 6, nb.build(), UserHandle.ALL);
        }
        if (clearClassLoader(pkg)) {
            Toast.makeText(this.mContext, "Reloading " + pkg, 1).show();
        }
        if (!"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
            for (PluginInstanceManager manager2 : this.mPluginMap.values()) {
                manager2.onPackageChange(pkg);
            }
            return;
        }
        for (PluginInstanceManager manager3 : this.mPluginMap.values()) {
            manager3.onPackageRemoved(pkg);
        }
    }

    public ClassLoader getClassLoader(String sourceDir, String pkg) {
        if (this.mClassLoaders.containsKey(pkg)) {
            return this.mClassLoaders.get(pkg);
        }
        ClassLoader classLoader = new PathClassLoader(sourceDir, getParentClassLoader());
        this.mClassLoaders.put(pkg, classLoader);
        return classLoader;
    }

    private boolean clearClassLoader(String pkg) {
        return this.mClassLoaders.remove(pkg) != null;
    }

    ClassLoader getParentClassLoader() {
        if (this.mParentClassLoader == null) {
            this.mParentClassLoader = new ClassLoaderFilter(getClass().getClassLoader(), "com.android.systemui.plugin");
        }
        return this.mParentClassLoader;
    }

    public Context getContext(ApplicationInfo info, String pkg) throws PackageManager.NameNotFoundException {
        ClassLoader classLoader = getClassLoader(info.sourceDir, pkg);
        return new PluginInstanceManager.PluginContextWrapper(this.mContext.createApplicationContext(info, 0), classLoader);
    }

    @Override // com.android.systemui.plugins.PluginManager
    public <T> boolean dependsOn(Plugin p, Class<T> cls) {
        for (int i = 0; i < this.mPluginMap.size(); i++) {
            if (this.mPluginMap.valueAt(i).dependsOn(p, cls)) {
                return true;
            }
        }
        return false;
    }

    public void handleWtfs() {
        if (!this.mWtfsSet) {
            this.mWtfsSet = true;
            Log.setWtfHandler(new Log.TerribleFailureHandler() { // from class: com.android.systemui.plugins.-$$Lambda$PluginManagerImpl$x7_zsW6bYkksPPNk4f5aNW7Etqo
                public final void onTerribleFailure(String str, Log.TerribleFailure terribleFailure, boolean z) {
                    PluginManagerImpl.lambda$handleWtfs$1(this.f$0, str, terribleFailure, z);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$handleWtfs$1(PluginManagerImpl pluginManagerImpl, String tag, Log.TerribleFailure what, boolean system) {
        throw pluginManagerImpl.new CrashWhilePluginActiveException(what);
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println(String.format("  plugin map (%d):", Integer.valueOf(this.mPluginMap.size())));
        for (PluginListener<?> pluginListener : this.mPluginMap.keySet()) {
            pw.println(String.format("    %s -> %s", pluginListener, this.mPluginMap.get(pluginListener)));
        }
    }

    @VisibleForTesting
    public static class PluginInstanceManagerFactory {
        public <T extends Plugin> PluginInstanceManager createPluginInstanceManager(Context context, String action, PluginListener<T> listener, boolean allowMultiple, Looper looper, Class<?> cls, PluginManagerImpl manager) {
            return new PluginInstanceManager(context, action, listener, allowMultiple, looper, new VersionInfo().addClass(cls), manager);
        }
    }

    private static class ClassLoaderFilter extends ClassLoader {
        private final ClassLoader mBase;
        private final String mPackage;

        public ClassLoaderFilter(ClassLoader base, String pkg) {
            super(ClassLoader.getSystemClassLoader());
            this.mBase = base;
            this.mPackage = pkg;
        }

        @Override // java.lang.ClassLoader
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (!name.startsWith(this.mPackage)) {
                super.loadClass(name, resolve);
            }
            return this.mBase.loadClass(name);
        }
    }

    private class PluginExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler mHandler;

        private PluginExceptionHandler(Thread.UncaughtExceptionHandler handler) {
            this.mHandler = handler;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable throwable) {
            if (SystemProperties.getBoolean("plugin.debugging", false)) {
                this.mHandler.uncaughtException(thread, throwable);
                return;
            }
            boolean disabledAny = checkStack(throwable);
            if (!disabledAny) {
                for (PluginInstanceManager manager : PluginManagerImpl.this.mPluginMap.values()) {
                    disabledAny |= manager.disableAll();
                }
            }
            if (disabledAny) {
                throwable = PluginManagerImpl.this.new CrashWhilePluginActiveException(throwable);
            }
            this.mHandler.uncaughtException(thread, throwable);
        }

        private boolean checkStack(Throwable throwable) {
            if (throwable == null) {
                return false;
            }
            boolean disabledAny = false;
            for (StackTraceElement element : throwable.getStackTrace()) {
                for (PluginInstanceManager manager : PluginManagerImpl.this.mPluginMap.values()) {
                    disabledAny |= manager.checkAndDisable(element.getClassName());
                }
            }
            return checkStack(throwable.getCause()) | disabledAny;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CrashWhilePluginActiveException extends RuntimeException {
        public CrashWhilePluginActiveException(Throwable throwable) {
            super(throwable);
        }
    }
}
