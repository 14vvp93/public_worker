package com.android.systemui.tuner;

import android.app.DialogFragment;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;

/* JADX INFO: loaded from: classes.dex */
public abstract class TunerPreferenceFragment extends PreferenceFragment {
    @Override // android.support.v14.preference.PreferenceFragment, android.support.v7.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment f = null;
        if (preference instanceof CustomListPreference) {
            f = CustomListPreference.CustomListPreferenceDialogFragment.newInstance(preference.getKey());
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
        f.setTargetFragment(this, 0);
        f.show(getFragmentManager(), "dialog_preference");
    }
}
