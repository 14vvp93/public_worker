package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceViewHolder;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ShortcutPicker extends PreferenceFragment implements TunerService.Tunable {
    private String mKey;
    private SelectablePreference mNonePreference;
    private final ArrayList<SelectablePreference> mSelectablePreferences = new ArrayList<>();
    private TunerService mTunerService;

    @Override // android.support.v14.preference.PreferenceFragment
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        final Context context = getPreferenceManager().getContext();
        final PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        screen.setOrderingAsAdded(true);
        final PreferenceCategory otherApps = new PreferenceCategory(context);
        otherApps.setTitle(R.string.tuner_other_apps);
        this.mNonePreference = new SelectablePreference(context);
        this.mSelectablePreferences.add(this.mNonePreference);
        this.mNonePreference.setTitle(R.string.lockscreen_none);
        this.mNonePreference.setIcon(R.drawable.ic_remove_circle);
        screen.addPreference(this.mNonePreference);
        LauncherApps apps = (LauncherApps) getContext().getSystemService(LauncherApps.class);
        List<LauncherActivityInfo> activities = apps.getActivityList(null, Process.myUserHandle());
        screen.addPreference(otherApps);
        activities.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$ShortcutPicker$kjubRY0RERFi5q4FUGuCDMvPtEc
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ShortcutPicker.lambda$onCreatePreferences$1(this.f$0, context, screen, otherApps, (LauncherActivityInfo) obj);
            }
        });
        screen.removePreference(otherApps);
        for (int i = 0; i < otherApps.getPreferenceCount(); i++) {
            Preference p = otherApps.getPreference(0);
            otherApps.removePreference(p);
            p.setOrder(Integer.MAX_VALUE);
            screen.addPreference(p);
        }
        setPreferenceScreen(screen);
        this.mKey = getArguments().getString("android.support.v7.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        this.mTunerService = (TunerService) Dependency.get(TunerService.class);
        this.mTunerService.addTunable(this, this.mKey);
    }

    public static /* synthetic */ void lambda$onCreatePreferences$1(final ShortcutPicker shortcutPicker, final Context context, final PreferenceScreen screen, PreferenceCategory otherApps, final LauncherActivityInfo info) {
        try {
            List<ShortcutParser.Shortcut> shortcuts = new ShortcutParser(shortcutPicker.getContext(), info.getComponentName()).getShortcuts();
            AppPreference appPreference = new AppPreference(context, info);
            shortcutPicker.mSelectablePreferences.add(appPreference);
            if (shortcuts.size() != 0) {
                screen.addPreference(appPreference);
                shortcuts.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$ShortcutPicker$KhrCRM8tSZs7Fj3ZW16pUQ2_D54
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ShortcutPicker.lambda$onCreatePreferences$0(this.f$0, context, info, screen, (ShortcutParser.Shortcut) obj);
                    }
                });
            } else {
                otherApps.addPreference(appPreference);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public static /* synthetic */ void lambda$onCreatePreferences$0(ShortcutPicker shortcutPicker, Context context, LauncherActivityInfo info, PreferenceScreen screen, ShortcutParser.Shortcut shortcut) {
        ShortcutPreference shortcutPref = new ShortcutPreference(context, shortcut, info.getLabel());
        shortcutPicker.mSelectablePreferences.add(shortcutPref);
        screen.addPreference(shortcutPref);
    }

    @Override // android.support.v14.preference.PreferenceFragment, android.support.v7.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        this.mTunerService.setValue(this.mKey, preference.toString());
        getActivity().onBackPressed();
        return true;
    }

    @Override // android.support.v14.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ("sysui_keyguard_left".equals(this.mKey)) {
            getActivity().setTitle(R.string.lockscreen_shortcut_left);
        } else {
            getActivity().setTitle(R.string.lockscreen_shortcut_right);
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mTunerService.removeTunable(this);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        final String v = newValue != null ? newValue : "";
        this.mSelectablePreferences.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$ShortcutPicker$i1fIZ726bN-ySXwulncRN12T1Qg
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SelectablePreference selectablePreference = (SelectablePreference) obj;
                selectablePreference.setChecked(v.equals(selectablePreference.toString()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AppPreference extends SelectablePreference {
        private boolean mBinding;
        private final LauncherActivityInfo mInfo;

        public AppPreference(Context context, LauncherActivityInfo info) {
            super(context);
            this.mInfo = info;
            setTitle(context.getString(R.string.tuner_launch_app, info.getLabel()));
            setSummary(context.getString(R.string.tuner_app, info.getLabel()));
        }

        @Override // android.support.v7.preference.CheckBoxPreference, android.support.v7.preference.Preference
        public void onBindViewHolder(PreferenceViewHolder holder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mInfo.getBadgedIcon(getContext().getResources().getConfiguration().densityDpi));
            }
            this.mBinding = false;
            super.onBindViewHolder(holder);
        }

        @Override // android.support.v7.preference.Preference
        protected void notifyChanged() {
            if (this.mBinding) {
                return;
            }
            super.notifyChanged();
        }

        @Override // com.android.systemui.tuner.SelectablePreference, android.support.v7.preference.Preference
        public String toString() {
            return this.mInfo.getComponentName().flattenToString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ShortcutPreference extends SelectablePreference {
        private boolean mBinding;
        private final ShortcutParser.Shortcut mShortcut;

        public ShortcutPreference(Context context, ShortcutParser.Shortcut shortcut, CharSequence appLabel) {
            super(context);
            this.mShortcut = shortcut;
            setTitle(shortcut.label);
            setSummary(context.getString(R.string.tuner_app, appLabel));
        }

        @Override // android.support.v7.preference.CheckBoxPreference, android.support.v7.preference.Preference
        public void onBindViewHolder(PreferenceViewHolder holder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mShortcut.icon.loadDrawable(getContext()));
            }
            this.mBinding = false;
            super.onBindViewHolder(holder);
        }

        @Override // android.support.v7.preference.Preference
        protected void notifyChanged() {
            if (this.mBinding) {
                return;
            }
            super.notifyChanged();
        }

        @Override // com.android.systemui.tuner.SelectablePreference, android.support.v7.preference.Preference
        public String toString() {
            return this.mShortcut.toString();
        }
    }
}
