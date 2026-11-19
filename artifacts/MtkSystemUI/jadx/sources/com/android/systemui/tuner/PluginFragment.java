package com.android.systemui.tuner;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.plugins.PluginInstanceManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.PluginPrefs;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class PluginFragment extends PreferenceFragment {
    private PluginPrefs mPluginPrefs;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.tuner.PluginFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            PluginFragment.this.loadPrefs();
        }
    };

    @Override // android.support.v14.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_CHANGED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        getContext().registerReceiver(this.mReceiver, filter);
        getContext().registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(this.mReceiver);
    }

    @Override // android.support.v14.preference.PreferenceFragment
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        loadPrefs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadPrefs() {
        final PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getContext());
        screen.setOrderingAsAdded(false);
        final Context prefContext = getPreferenceManager().getContext();
        this.mPluginPrefs = new PluginPrefs(getContext());
        PackageManager pm = getContext().getPackageManager();
        Set<String> pluginActions = this.mPluginPrefs.getPluginList();
        final ArrayMap<String, ArraySet<String>> plugins = new ArrayMap<>();
        for (String action : pluginActions) {
            String name = toName(action);
            List<ResolveInfo> result = pm.queryIntentServices(new Intent(action), 512);
            for (ResolveInfo info : result) {
                String packageName = info.serviceInfo.packageName;
                if (!plugins.containsKey(packageName)) {
                    plugins.put(packageName, new ArraySet<>());
                }
                plugins.get(packageName).add(name);
            }
        }
        List<PackageInfo> apps = pm.getPackagesHoldingPermissions(new String[]{PluginInstanceManager.PLUGIN_PERMISSION}, 516);
        apps.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$PluginFragment$-gWfSjbBwCV0_TE_pdcQVko9wUY
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PluginFragment.lambda$loadPrefs$0(this.f$0, plugins, prefContext, screen, (PackageInfo) obj);
            }
        });
        setPreferenceScreen(screen);
    }

    public static /* synthetic */ void lambda$loadPrefs$0(PluginFragment pluginFragment, ArrayMap plugins, Context prefContext, PreferenceScreen screen, PackageInfo app) {
        if (plugins.containsKey(app.packageName)) {
            SwitchPreference pref = new PluginPreference(prefContext, app);
            pref.setSummary("Plugins: " + pluginFragment.toString((ArraySet) plugins.get(app.packageName)));
            screen.addPreference(pref);
        }
    }

    private String toString(ArraySet<String> plugins) {
        StringBuilder b = new StringBuilder();
        for (String string : plugins) {
            if (b.length() != 0) {
                b.append(", ");
            }
            b.append(string);
        }
        return b.toString();
    }

    private String toName(String action) {
        String str = action.replace("com.android.systemui.action.PLUGIN_", "");
        StringBuilder b = new StringBuilder();
        for (String s : str.split("_")) {
            if (b.length() != 0) {
                b.append(' ');
            }
            b.append(s.substring(0, 1));
            b.append(s.substring(1).toLowerCase());
        }
        return b.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PluginPreference extends SwitchPreference {
        private final boolean mHasSettings;
        private final PackageInfo mInfo;
        private final PackageManager mPm;

        public PluginPreference(Context prefContext, PackageInfo info) {
            super(prefContext);
            this.mPm = prefContext.getPackageManager();
            this.mHasSettings = this.mPm.resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(info.packageName), 0) != null;
            this.mInfo = info;
            setTitle(info.applicationInfo.loadLabel(this.mPm));
            setChecked(isPluginEnabled());
            setWidgetLayoutResource(R.layout.tuner_widget_settings_switch);
        }

        private boolean isPluginEnabled() {
            for (int i = 0; i < this.mInfo.services.length; i++) {
                ComponentName componentName = new ComponentName(this.mInfo.packageName, this.mInfo.services[i].name);
                if (this.mPm.getComponentEnabledSetting(componentName) == 2) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.support.v7.preference.Preference
        protected boolean persistBoolean(boolean value) {
            int desiredState = value ? 1 : 2;
            boolean shouldSendBroadcast = false;
            for (int i = 0; i < this.mInfo.services.length; i++) {
                ComponentName componentName = new ComponentName(this.mInfo.packageName, this.mInfo.services[i].name);
                if (this.mPm.getComponentEnabledSetting(componentName) != desiredState) {
                    this.mPm.setComponentEnabledSetting(componentName, desiredState, 1);
                    shouldSendBroadcast = true;
                }
            }
            if (shouldSendBroadcast) {
                String pkg = this.mInfo.packageName;
                Intent intent = new Intent(PluginManager.PLUGIN_CHANGED, pkg != null ? Uri.fromParts("package", pkg, null) : null);
                getContext().sendBroadcast(intent);
            }
            return true;
        }

        @Override // android.support.v14.preference.SwitchPreference, android.support.v7.preference.Preference
        public void onBindViewHolder(PreferenceViewHolder holder) {
            super.onBindViewHolder(holder);
            holder.findViewById(R.id.settings).setVisibility(this.mHasSettings ? 0 : 8);
            holder.findViewById(R.id.divider).setVisibility(this.mHasSettings ? 0 : 8);
            holder.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$PluginFragment$PluginPreference$Xt_y65tw1Tc7XykRWrNNbIDklTs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PluginFragment.PluginPreference.lambda$onBindViewHolder$0(this.f$0, view);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$PluginFragment$PluginPreference$hyhKFHxbkbEXGxqXV7_N3Il_7XE
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return PluginFragment.PluginPreference.lambda$onBindViewHolder$1(this.f$0, view);
                }
            });
        }

        public static /* synthetic */ void lambda$onBindViewHolder$0(PluginPreference pluginPreference, View v) {
            ResolveInfo result = v.getContext().getPackageManager().resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(pluginPreference.mInfo.packageName), 0);
            if (result != null) {
                v.getContext().startActivity(new Intent().setComponent(new ComponentName(result.activityInfo.packageName, result.activityInfo.name)));
            }
        }

        public static /* synthetic */ boolean lambda$onBindViewHolder$1(PluginPreference pluginPreference, View v) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", pluginPreference.mInfo.packageName, null));
            pluginPreference.getContext().startActivity(intent);
            return true;
        }
    }
}
