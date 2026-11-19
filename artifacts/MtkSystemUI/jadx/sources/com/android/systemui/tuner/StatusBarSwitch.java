package com.android.systemui.tuner;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarSwitch extends SwitchPreference implements TunerService.Tunable {
    private Set<String> mBlacklist;

    public StatusBarSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.support.v7.preference.Preference
    public void onAttached() {
        super.onAttached();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist");
    }

    @Override // android.support.v7.preference.Preference
    public void onDetached() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        super.onDetached();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (!"icon_blacklist".equals(key)) {
            return;
        }
        this.mBlacklist = StatusBarIconController.getIconBlacklist(newValue);
        setChecked(!this.mBlacklist.contains(getKey()));
    }

    @Override // android.support.v7.preference.Preference
    protected boolean persistBoolean(boolean value) {
        if (!value) {
            if (!this.mBlacklist.contains(getKey())) {
                MetricsLogger.action(getContext(), 234, getKey());
                this.mBlacklist.add(getKey());
                setList(this.mBlacklist);
                return true;
            }
            return true;
        }
        if (this.mBlacklist.remove(getKey())) {
            MetricsLogger.action(getContext(), 233, getKey());
            setList(this.mBlacklist);
            return true;
        }
        return true;
    }

    private void setList(Set<String> blacklist) {
        ContentResolver contentResolver = getContext().getContentResolver();
        Settings.Secure.putStringForUser(contentResolver, "icon_blacklist", TextUtils.join(",", blacklist), ActivityManager.getCurrentUser());
    }
}
