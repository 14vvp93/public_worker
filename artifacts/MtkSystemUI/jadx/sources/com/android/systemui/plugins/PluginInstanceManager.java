package com.android.systemui.plugins;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.plugins.Plugin;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PluginInstanceManager<T extends Plugin> {
    public static final String PLUGIN_PERMISSION = "com.android.systemui.permission.PLUGIN";
    private final boolean isDebuggable;
    private final String mAction;
    private final boolean mAllowMultiple;
    private final Context mContext;
    private final PluginListener<T> mListener;

    @VisibleForTesting
    final PluginInstanceManager<T>.MainHandler mMainHandler;
    private final PluginManagerImpl mManager;

    @VisibleForTesting
    final PluginInstanceManager<T>.PluginHandler mPluginHandler;
    private final PackageManager mPm;
    private final VersionInfo mVersion;

    PluginInstanceManager(Context context, String action, PluginListener<T> listener, boolean allowMultiple, Looper looper, VersionInfo version, PluginManagerImpl manager) {
        this(context, context.getPackageManager(), action, listener, allowMultiple, looper, version, manager, Build.IS_DEBUGGABLE);
    }

    @VisibleForTesting
    PluginInstanceManager(Context context, PackageManager pm, String action, PluginListener<T> listener, boolean allowMultiple, Looper looper, VersionInfo version, PluginManagerImpl manager, boolean debuggable) {
        this.mMainHandler = new MainHandler(Looper.getMainLooper());
        this.mPluginHandler = new PluginHandler(looper);
        this.mManager = manager;
        this.mContext = context;
        this.mPm = pm;
        this.mAction = action;
        this.mListener = listener;
        this.mAllowMultiple = allowMultiple;
        this.mVersion = version;
        this.isDebuggable = debuggable;
    }

    public PluginInfo<T> getPlugin() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Must be called from UI thread");
        }
        this.mPluginHandler.handleQueryPlugins(null);
        if (((PluginHandler) this.mPluginHandler).mPlugins.size() <= 0) {
            return null;
        }
        this.mMainHandler.removeMessages(1);
        PluginInfo<T> info = (PluginInfo) ((PluginHandler) this.mPluginHandler).mPlugins.get(0);
        PluginPrefs.setHasPlugins(this.mContext);
        info.mPlugin.onCreate(this.mContext, ((PluginInfo) info).mPluginContext);
        return info;
    }

    public void loadAll() {
        this.mPluginHandler.sendEmptyMessage(1);
    }

    public void destroy() {
        ArrayList<PluginInfo> plugins = new ArrayList<>(((PluginHandler) this.mPluginHandler).mPlugins);
        for (PluginInfo plugin : plugins) {
            this.mMainHandler.obtainMessage(2, plugin.mPlugin).sendToTarget();
        }
    }

    public void onPackageRemoved(String pkg) {
        this.mPluginHandler.obtainMessage(3, pkg).sendToTarget();
    }

    public void onPackageChange(String pkg) {
        this.mPluginHandler.obtainMessage(3, pkg).sendToTarget();
        this.mPluginHandler.obtainMessage(2, pkg).sendToTarget();
    }

    public boolean checkAndDisable(String className) {
        boolean disableAny = false;
        ArrayList<PluginInfo> plugins = new ArrayList<>(((PluginHandler) this.mPluginHandler).mPlugins);
        for (PluginInfo info : plugins) {
            if (className.startsWith(info.mPackage)) {
                disable(info);
                disableAny = true;
            }
        }
        return disableAny;
    }

    public boolean disableAll() {
        ArrayList<PluginInfo> plugins = new ArrayList<>(((PluginHandler) this.mPluginHandler).mPlugins);
        for (int i = 0; i < plugins.size(); i++) {
            disable(plugins.get(i));
        }
        int i2 = plugins.size();
        return i2 != 0;
    }

    private void disable(PluginInfo info) {
        Log.w("PluginInstanceManager", "Disabling plugin " + info.mPackage + "/" + info.mClass);
        this.mPm.setComponentEnabledSetting(new ComponentName(info.mPackage, info.mClass), 2, 1);
    }

    public <T> boolean dependsOn(Plugin p, Class<T> cls) {
        ArrayList<PluginInfo> plugins = new ArrayList<>(((PluginHandler) this.mPluginHandler).mPlugins);
        for (PluginInfo info : plugins) {
            if (info.mPlugin.getClass().getName().equals(p.getClass().getName())) {
                return info.mVersion != null && info.mVersion.hasClass(cls);
            }
        }
        return false;
    }

    public String toString() {
        return String.format("%s@%s (action=%s)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this.mAction);
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    PluginPrefs.setHasPlugins(PluginInstanceManager.this.mContext);
                    PluginInfo pluginInfo = (PluginInfo) message.obj;
                    PluginInstanceManager.this.mManager.handleWtfs();
                    if (!(message.obj instanceof PluginFragment)) {
                        ((T) pluginInfo.mPlugin).onCreate(PluginInstanceManager.this.mContext, pluginInfo.mPluginContext);
                    }
                    PluginInstanceManager.this.mListener.onPluginConnected((T) pluginInfo.mPlugin, pluginInfo.mPluginContext);
                    break;
                case 2:
                    PluginInstanceManager.this.mListener.onPluginDisconnected((Plugin) message.obj);
                    if (!(message.obj instanceof PluginFragment)) {
                        ((Plugin) message.obj).onDestroy();
                    }
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    private class PluginHandler extends Handler {
        private final ArrayList<PluginInfo<T>> mPlugins;

        public PluginHandler(Looper looper) {
            super(looper);
            this.mPlugins = new ArrayList<>();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = this.mPlugins.size() - 1; i >= 0; i--) {
                        PluginInfo<T> plugin = this.mPlugins.get(i);
                        PluginInstanceManager.this.mListener.onPluginDisconnected(plugin.mPlugin);
                        if (!(plugin.mPlugin instanceof PluginFragment)) {
                            plugin.mPlugin.onDestroy();
                        }
                    }
                    this.mPlugins.clear();
                    handleQueryPlugins(null);
                    break;
                case 2:
                    String p = (String) msg.obj;
                    if (PluginInstanceManager.this.mAllowMultiple || this.mPlugins.size() == 0) {
                        handleQueryPlugins(p);
                    }
                    break;
                case 3:
                    String pkg = (String) msg.obj;
                    for (int i2 = this.mPlugins.size() - 1; i2 >= 0; i2--) {
                        PluginInfo<T> plugin2 = this.mPlugins.get(i2);
                        if (plugin2.mPackage.equals(pkg)) {
                            PluginInstanceManager.this.mMainHandler.obtainMessage(2, plugin2.mPlugin).sendToTarget();
                            this.mPlugins.remove(i2);
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleQueryPlugins(String pkgName) {
            Intent intent = new Intent(PluginInstanceManager.this.mAction);
            if (pkgName != null) {
                intent.setPackage(pkgName);
            }
            List<ResolveInfo> result = PluginInstanceManager.this.mPm.queryIntentServices(intent, 0);
            if (result.size() > 1 && !PluginInstanceManager.this.mAllowMultiple) {
                Log.w("PluginInstanceManager", "Multiple plugins found for " + PluginInstanceManager.this.mAction);
                return;
            }
            for (ResolveInfo info : result) {
                ComponentName name = new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name);
                PluginInfo<T> t = handleLoadPlugin(name);
                if (t != null) {
                    PluginInstanceManager.this.mMainHandler.obtainMessage(1, t).sendToTarget();
                    this.mPlugins.add(t);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected PluginInfo<T> handleLoadPlugin(ComponentName component) {
            String pkg;
            if (!PluginInstanceManager.this.isDebuggable) {
                Log.d("PluginInstanceManager", "Somehow hit second debuggable check");
                return null;
            }
            String pkg2 = component.getPackageName();
            String cls = component.getClassName();
            try {
                ApplicationInfo info = PluginInstanceManager.this.mPm.getApplicationInfo(pkg2, 0);
                try {
                    if (PluginInstanceManager.this.mPm.checkPermission(PluginInstanceManager.PLUGIN_PERMISSION, pkg2) == 0) {
                        ClassLoader classLoader = PluginInstanceManager.this.mManager.getClassLoader(info.sourceDir, info.packageName);
                        Context pluginContext = new PluginContextWrapper(PluginInstanceManager.this.mContext.createApplicationContext(info, 0), classLoader);
                        Class<?> pluginClass = Class.forName(cls, true, classLoader);
                        Plugin plugin = (Plugin) pluginClass.newInstance();
                        try {
                            VersionInfo version = checkVersion(pluginClass, plugin, PluginInstanceManager.this.mVersion);
                            try {
                                return new PluginInfo<>(pkg2, cls, plugin, pluginContext, version);
                            } catch (VersionInfo.InvalidVersionException e) {
                                e = e;
                                VersionInfo.InvalidVersionException e2 = e;
                                int icon = PluginInstanceManager.this.mContext.getResources().getIdentifier("tuner", "drawable", PluginInstanceManager.this.mContext.getPackageName());
                                int color = Resources.getSystem().getIdentifier("system_notification_accent_color", "color", "android");
                                pkg = pkg2;
                                try {
                                    Notification.Builder nb = new Notification.Builder(PluginInstanceManager.this.mContext, PluginManager.NOTIFICATION_CHANNEL_ID).setStyle(new Notification.BigTextStyle()).setSmallIcon(icon).setWhen(0L).setShowWhen(false).setVisibility(1).setColor(PluginInstanceManager.this.mContext.getColor(color));
                                    String label = cls;
                                    try {
                                        try {
                                            try {
                                                label = PluginInstanceManager.this.mPm.getServiceInfo(component, 0).loadLabel(PluginInstanceManager.this.mPm).toString();
                                            } catch (Throwable th) {
                                                e = th;
                                            }
                                        } catch (PackageManager.NameNotFoundException e3) {
                                        }
                                    } catch (PackageManager.NameNotFoundException e4) {
                                    }
                                    if (!e2.isTooNew()) {
                                        Notification.Builder contentTitle = nb.setContentTitle("Plugin \"" + label + "\" is too old");
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Contact plugin developer to get an updated version.\n");
                                        sb.append(e2.getMessage());
                                        contentTitle.setContentText(sb.toString());
                                    } else {
                                        Notification.Builder contentTitle2 = nb.setContentTitle("Plugin \"" + label + "\" is too new");
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append("Check to see if an OTA is available.\n");
                                        sb2.append(e2.getMessage());
                                        contentTitle2.setContentText(sb2.toString());
                                    }
                                    Intent i = new Intent("com.android.systemui.action.DISABLE_PLUGIN").setData(Uri.parse("package://" + component.flattenToString()));
                                    PendingIntent pi = PendingIntent.getBroadcast(PluginInstanceManager.this.mContext, 0, i, 0);
                                    nb.addAction(new Notification.Action.Builder((Icon) null, "Disable plugin", pi).build());
                                    ((NotificationManager) PluginInstanceManager.this.mContext.getSystemService(NotificationManager.class)).notifyAsUser(cls, 6, nb.build(), UserHandle.ALL);
                                    Log.w("PluginInstanceManager", "Plugin has invalid interface version " + plugin.getVersion() + ", expected " + PluginInstanceManager.this.mVersion);
                                    return null;
                                } catch (Throwable th2) {
                                    e = th2;
                                }
                            }
                        } catch (VersionInfo.InvalidVersionException e5) {
                            e = e5;
                        }
                    } else {
                        Log.d("PluginInstanceManager", "Plugin doesn't have permission: " + pkg2);
                        return null;
                    }
                } catch (Throwable th3) {
                    e = th3;
                    pkg = pkg2;
                }
            } catch (Throwable th4) {
                e = th4;
                pkg = pkg2;
            }
            Log.w("PluginInstanceManager", "Couldn't load plugin: " + pkg, e);
            return null;
        }

        private VersionInfo checkVersion(Class<?> pluginClass, T plugin, VersionInfo version) throws VersionInfo.InvalidVersionException {
            VersionInfo pv = new VersionInfo().addClass(pluginClass);
            if (pv.hasVersionInfo()) {
                version.checkVersion(pv);
                return pv;
            }
            int fallbackVersion = plugin.getVersion();
            if (fallbackVersion != version.getDefaultVersion()) {
                throw new VersionInfo.InvalidVersionException("Invalid legacy version", false);
            }
            return null;
        }
    }

    public static class PluginContextWrapper extends ContextWrapper {
        private final ClassLoader mClassLoader;
        private LayoutInflater mInflater;

        public PluginContextWrapper(Context base, ClassLoader classLoader) {
            super(base);
            this.mClassLoader = classLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public ClassLoader getClassLoader() {
            return this.mClassLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Object getSystemService(String name) {
            if ("layout_inflater".equals(name)) {
                if (this.mInflater == null) {
                    this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
                }
                return this.mInflater;
            }
            return getBaseContext().getSystemService(name);
        }
    }

    static class PluginInfo<T> {
        private String mClass;
        String mPackage;
        T mPlugin;
        private final Context mPluginContext;
        private final VersionInfo mVersion;

        public PluginInfo(String pkg, String cls, T plugin, Context pluginContext, VersionInfo info) {
            this.mPlugin = plugin;
            this.mClass = cls;
            this.mPackage = pkg;
            this.mPluginContext = pluginContext;
            this.mVersion = info;
        }
    }
}
