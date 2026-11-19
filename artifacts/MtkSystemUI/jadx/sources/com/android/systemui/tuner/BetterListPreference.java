package com.android.systemui.tuner;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class BetterListPreference extends ListPreference {
    private CharSequence mSummary;

    public BetterListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.support.v7.preference.ListPreference, android.support.v7.preference.Preference
    public void setSummary(CharSequence summary) {
        super.setSummary(summary);
        this.mSummary = summary;
    }

    @Override // android.support.v7.preference.ListPreference, android.support.v7.preference.Preference
    public CharSequence getSummary() {
        return this.mSummary;
    }
}
