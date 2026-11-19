package com.android.systemui.tuner;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toolbar;
import com.android.settingslib.Utils;
import com.android.systemui.fragments.FragmentHostManager;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RadioListPreference extends CustomListPreference {
    private DialogInterface.OnClickListener mOnClickListener;
    private CharSequence mSummary;

    public RadioListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder, DialogInterface.OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    @Override // android.support.v7.preference.ListPreference, android.support.v7.preference.Preference
    public void setSummary(CharSequence summary) {
        super.setSummary(summary);
        this.mSummary = summary;
    }

    @Override // android.support.v7.preference.ListPreference, android.support.v7.preference.Preference
    public CharSequence getSummary() {
        if (this.mSummary == null || this.mSummary.toString().contains("%s")) {
            return super.getSummary();
        }
        return this.mSummary;
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    protected Dialog onDialogCreated(DialogFragment fragment, Dialog dialog) {
        final Dialog d = new Dialog(getContext(), R.style.Theme.DeviceDefault.Settings);
        Toolbar t = (Toolbar) d.findViewById(R.id.KEYCODE_TV_RADIO_SERVICE);
        View v = new View(getContext());
        v.setId(com.android.systemui.R.id.content);
        d.setContentView(v);
        t.setTitle(getTitle());
        t.setNavigationIcon(Utils.getDrawable(d.getContext(), R.attr.homeAsUpIndicator));
        t.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$RadioListPreference$4DEUOALD3KxT1NUXowELf-5ZJ2M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                d.dismiss();
            }
        });
        RadioFragment f = new RadioFragment();
        f.setPreference(this);
        FragmentHostManager.get(v).getFragmentManager().beginTransaction().add(R.id.content, f).commit();
        return d;
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    protected void onDialogStateRestored(DialogFragment fragment, Dialog dialog, Bundle savedInstanceState) {
        super.onDialogStateRestored(fragment, dialog, savedInstanceState);
        View view = dialog.findViewById(com.android.systemui.R.id.content);
        RadioFragment radioFragment = (RadioFragment) FragmentHostManager.get(view).getFragmentManager().findFragmentById(com.android.systemui.R.id.content);
        if (radioFragment != null) {
            radioFragment.setPreference(this);
        }
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }

    public static class RadioFragment extends TunerPreferenceFragment {
        private RadioListPreference mListPref;

        @Override // android.support.v14.preference.PreferenceFragment
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            Context context = getPreferenceManager().getContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
            setPreferenceScreen(screen);
            if (this.mListPref != null) {
                update();
            }
        }

        private void update() {
            Context context = getPreferenceManager().getContext();
            CharSequence[] entries = this.mListPref.getEntries();
            CharSequence[] values = this.mListPref.getEntryValues();
            CharSequence current = this.mListPref.getValue();
            for (int i = 0; i < entries.length; i++) {
                CharSequence entry = entries[i];
                SelectablePreference pref = new SelectablePreference(context);
                getPreferenceScreen().addPreference(pref);
                pref.setTitle(entry);
                pref.setChecked(Objects.equals(current, values[i]));
                pref.setKey(String.valueOf(i));
            }
        }

        @Override // android.support.v14.preference.PreferenceFragment, android.support.v7.preference.PreferenceManager.OnPreferenceTreeClickListener
        public boolean onPreferenceTreeClick(Preference preference) {
            this.mListPref.mOnClickListener.onClick(null, Integer.parseInt(preference.getKey()));
            return true;
        }

        public void setPreference(RadioListPreference radioListPreference) {
            this.mListPref = radioListPreference;
            if (getPreferenceManager() != null) {
                update();
            }
        }
    }
}
