package com.android.systemui.tuner;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class OtherPrefs extends PreferenceFragment {
    @Override // android.support.v14.preference.PreferenceFragment
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.other_settings);
    }
}
