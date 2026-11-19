package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.NavigationBarInflaterView;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NavBarTuner extends TunerPreferenceFragment {
    private static final int[][] ICONS = {new int[]{R.drawable.ic_qs_circle, R.string.tuner_circle}, new int[]{R.drawable.ic_add, R.string.tuner_plus}, new int[]{R.drawable.ic_remove, R.string.tuner_minus}, new int[]{R.drawable.ic_left, R.string.tuner_left}, new int[]{R.drawable.ic_right, R.string.tuner_right}, new int[]{R.drawable.ic_menu, R.string.tuner_menu}};
    private Handler mHandler;
    private final ArrayList<TunerService.Tunable> mTunables = new ArrayList<>();

    @Override // android.support.v14.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        this.mHandler = new Handler();
        super.onCreate(savedInstanceState);
    }

    @Override // android.support.v14.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override // android.support.v14.preference.PreferenceFragment
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.nav_bar_tuner);
        bindLayout((ListPreference) findPreference("layout"));
        bindButton("sysui_nav_bar_left", "space", "left");
        bindButton("sysui_nav_bar_right", "menu_ime", "right");
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$tsKQ8HfwaDSvc3iDCsgHsW954hc
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TunerService) Dependency.get(TunerService.class)).removeTunable((TunerService.Tunable) obj);
            }
        });
    }

    private void addTunable(TunerService.Tunable tunable, String... keys) {
        this.mTunables.add(tunable);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(tunable, keys);
    }

    private void bindLayout(final ListPreference preference) {
        addTunable(new TunerService.Tunable() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$S2jLo9__CXt1xH-Y0l__zpA7S6Y
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                this.f$0.mHandler.post(new Runnable() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$4yjdjEjuBBM9Uve2djbFEJblmmM
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarTuner.lambda$bindLayout$1(str2, listPreference);
                    }
                });
            }
        }, "sysui_nav_bar");
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$xKs-3UbChwmRyuUfQ6nrgLk_wz4
            @Override // android.support.v7.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference2, Object obj) {
                return NavBarTuner.lambda$bindLayout$3(preference2, obj);
            }
        });
    }

    static /* synthetic */ void lambda$bindLayout$1(String newValue, ListPreference preference) {
        String val = newValue;
        if (val == null) {
            val = "default";
        }
        preference.setValue(val);
    }

    static /* synthetic */ boolean lambda$bindLayout$3(Preference preference1, Object newValue) {
        String val = (String) newValue;
        if ("default".equals(val)) {
            val = null;
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue("sysui_nav_bar", val);
        return true;
    }

    private void bindButton(final String setting, final String def, String k) {
        final ListPreference type = (ListPreference) findPreference("type_" + k);
        final Preference keycode = findPreference("keycode_" + k);
        final ListPreference icon = (ListPreference) findPreference("icon_" + k);
        setupIcons(icon);
        addTunable(new TunerService.Tunable() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$OO9apf0OQ_k5f52-B3z0sPWxq4Y
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                NavBarTuner navBarTuner = this.f$0;
                navBarTuner.mHandler.post(new Runnable() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$SHMBahM6FQXBhKLgycxQEP9MKcg
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarTuner.lambda$bindButton$4(navBarTuner, str2, str, listPreference, listPreference, preference);
                    }
                });
            }
        }, setting);
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$addObFvLTzuE04K9maj0keVenxg
            @Override // android.support.v7.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference, Object obj) {
                return NavBarTuner.lambda$bindButton$7(this.f$0, setting, type, keycode, icon, preference, obj);
            }
        };
        type.setOnPreferenceChangeListener(listener);
        icon.setOnPreferenceChangeListener(listener);
        keycode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$L8cT3y_Xey3yV0gzNMgaCqxduDs
            @Override // android.support.v7.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                return NavBarTuner.lambda$bindButton$9(this.f$0, keycode, setting, type, icon, preference);
            }
        });
    }

    public static /* synthetic */ void lambda$bindButton$4(NavBarTuner navBarTuner, String newValue, String def, ListPreference type, ListPreference icon, Preference keycode) {
        String val = newValue;
        if (val == null) {
            val = def;
        }
        String button = NavigationBarInflaterView.extractButton(val);
        if (button.startsWith("key")) {
            type.setValue("key");
            String uri = NavigationBarInflaterView.extractImage(button);
            int code = NavigationBarInflaterView.extractKeycode(button);
            icon.setValue(uri);
            navBarTuner.updateSummary(icon);
            keycode.setSummary(code + "");
            keycode.setVisible(true);
            icon.setVisible(true);
            return;
        }
        type.setValue(button);
        keycode.setVisible(false);
        icon.setVisible(false);
    }

    public static /* synthetic */ boolean lambda$bindButton$7(final NavBarTuner navBarTuner, final String setting, final ListPreference type, final Preference keycode, final ListPreference icon, Preference preference, Object newValue) {
        navBarTuner.mHandler.post(new Runnable() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$LY1SqGQukLZwVkxUGh5npeYUH_E
            @Override // java.lang.Runnable
            public final void run() {
                NavBarTuner.lambda$bindButton$6(this.f$0, setting, type, keycode, icon);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$bindButton$6(NavBarTuner navBarTuner, String setting, ListPreference type, Preference keycode, ListPreference icon) {
        navBarTuner.setValue(setting, type, keycode, icon);
        navBarTuner.updateSummary(icon);
    }

    public static /* synthetic */ boolean lambda$bindButton$9(final NavBarTuner navBarTuner, final Preference keycode, final String setting, final ListPreference type, final ListPreference icon, Preference preference) {
        final EditText editText = new EditText(navBarTuner.getContext());
        new AlertDialog.Builder(navBarTuner.getContext()).setTitle(preference.getTitle()).setView(editText).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$NavBarTuner$f5iTNvW7hFZFMmuMznH0p1eCwlI
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                NavBarTuner.lambda$bindButton$8(this.f$0, editText, keycode, setting, type, icon, dialogInterface, i);
            }
        }).show();
        return true;
    }

    public static /* synthetic */ void lambda$bindButton$8(NavBarTuner navBarTuner, EditText editText, Preference keycode, String setting, ListPreference type, ListPreference icon, DialogInterface dialog, int which) {
        int code = 66;
        try {
            code = Integer.parseInt(editText.getText().toString());
        } catch (Exception e) {
        }
        keycode.setSummary(code + "");
        navBarTuner.setValue(setting, type, keycode, icon);
    }

    private void updateSummary(ListPreference icon) {
        try {
            int size = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
            String pkg = icon.getValue().split("/")[0];
            int id = Integer.parseInt(icon.getValue().split("/")[1]);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            Drawable d = Icon.createWithResource(pkg, id).loadDrawable(getContext());
            d.setTint(-16777216);
            d.setBounds(0, 0, size, size);
            ImageSpan span = new ImageSpan(d, 1);
            builder.append("  ", span, 0);
            builder.append((CharSequence) " ");
            for (int i = 0; i < ICONS.length; i++) {
                if (ICONS[i][0] == id) {
                    builder.append((CharSequence) getString(ICONS[i][1]));
                }
            }
            icon.setSummary(builder);
        } catch (Exception e) {
            Log.d("NavButton", "Problem with summary", e);
            icon.setSummary((CharSequence) null);
        }
    }

    private void setValue(String setting, ListPreference type, Preference keycode, ListPreference icon) {
        String button = type.getValue();
        if ("key".equals(button)) {
            String uri = icon.getValue();
            int code = 66;
            try {
                code = Integer.parseInt(keycode.getSummary().toString());
            } catch (Exception e) {
            }
            button = button + "(" + code + ":" + uri + ")";
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue(setting, button);
    }

    private void setupIcons(ListPreference icon) {
        CharSequence[] labels = new CharSequence[ICONS.length];
        CharSequence[] values = new CharSequence[ICONS.length];
        int size = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
        for (int i = 0; i < ICONS.length; i++) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            Drawable d = Icon.createWithResource(getContext().getPackageName(), ICONS[i][0]).loadDrawable(getContext());
            d.setTint(-16777216);
            d.setBounds(0, 0, size, size);
            ImageSpan span = new ImageSpan(d, 1);
            builder.append("  ", span, 0);
            builder.append((CharSequence) " ");
            builder.append((CharSequence) getString(ICONS[i][1]));
            labels[i] = builder;
            values[i] = getContext().getPackageName() + "/" + ICONS[i][0];
        }
        icon.setEntries(labels);
        icon.setEntryValues(values);
    }
}
