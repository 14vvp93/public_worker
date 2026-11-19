package com.android.systemui.tuner;

import android.R;
import android.content.Context;
import android.support.v7.preference.DropDownPreference;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.StatusBarIconController;

/* JADX INFO: loaded from: classes.dex */
public class ClockPreference extends DropDownPreference implements TunerService.Tunable {
    private ArraySet<String> mBlacklist;
    private final String mClock;
    private boolean mClockEnabled;
    private boolean mHasSeconds;
    private boolean mHasSetValue;
    private boolean mReceivedClock;
    private boolean mReceivedSeconds;

    public ClockPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mClock = context.getString(R.string.mediasize_iso_c0);
        setEntryValues(new CharSequence[]{"seconds", "default", "disabled"});
    }

    @Override // android.support.v7.preference.Preference
    public void onAttached() {
        super.onAttached();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist", "clock_seconds");
    }

    @Override // android.support.v7.preference.Preference
    public void onDetached() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        super.onDetached();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("icon_blacklist".equals(key)) {
            this.mReceivedClock = true;
            this.mBlacklist = StatusBarIconController.getIconBlacklist(newValue);
            this.mClockEnabled = !this.mBlacklist.contains(this.mClock);
        } else if ("clock_seconds".equals(key)) {
            this.mReceivedSeconds = true;
            this.mHasSeconds = (newValue == null || Integer.parseInt(newValue) == 0) ? false : true;
        }
        if (!this.mHasSetValue && this.mReceivedClock && this.mReceivedSeconds) {
            this.mHasSetValue = true;
            if (this.mClockEnabled && this.mHasSeconds) {
                setValue("seconds");
            } else if (this.mClockEnabled) {
                setValue("default");
            } else {
                setValue("disabled");
            }
        }
    }

    @Override // android.support.v7.preference.Preference
    protected boolean persistString(String value) {
        ((TunerService) Dependency.get(TunerService.class)).setValue("clock_seconds", "seconds".equals(value) ? 1 : 0);
        if ("disabled".equals(value)) {
            this.mBlacklist.add(this.mClock);
        } else {
            this.mBlacklist.remove(this.mClock);
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue("icon_blacklist", TextUtils.join(",", this.mBlacklist));
        return true;
    }
}
