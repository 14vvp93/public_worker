package com.android.systemui.tuner;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.Dependency;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.util.leak.LeakDetector;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class TunerServiceImpl extends TunerService {
    private static final String[] RESET_BLACKLIST = {"sysui_qs_tiles", "doze_always_on"};
    private ContentResolver mContentResolver;
    private final Context mContext;
    private int mCurrentUser;
    private final HashSet<TunerService.Tunable> mTunables;
    private CurrentUserTracker mUserTracker;
    private final Observer mObserver = new Observer();
    private final ArrayMap<Uri, String> mListeningUris = new ArrayMap<>();
    private final ConcurrentHashMap<String, Set<TunerService.Tunable>> mTunableLookup = new ConcurrentHashMap<>();

    public TunerServiceImpl(Context context) {
        this.mTunables = LeakDetector.ENABLED ? new HashSet<>() : null;
        this.mContext = context;
        this.mContentResolver = this.mContext.getContentResolver();
        for (UserInfo user : UserManager.get(this.mContext).getUsers()) {
            this.mCurrentUser = user.getUserHandle().getIdentifier();
            if (getValue("sysui_tuner_version", 0) != 4) {
                upgradeTuner(getValue("sysui_tuner_version", 0), 4);
            }
        }
        this.mCurrentUser = ActivityManager.getCurrentUser();
        this.mUserTracker = new CurrentUserTracker(this.mContext) { // from class: com.android.systemui.tuner.TunerServiceImpl.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int newUserId) {
                TunerServiceImpl.this.mCurrentUser = newUserId;
                TunerServiceImpl.this.reloadAll();
                TunerServiceImpl.this.reregisterAll();
            }
        };
        this.mUserTracker.startTracking();
    }

    private void upgradeTuner(int oldVersion, int newVersion) {
        String blacklistStr;
        if (oldVersion < 1 && (blacklistStr = getValue("icon_blacklist")) != null) {
            ArraySet<String> iconBlacklist = StatusBarIconController.getIconBlacklist(blacklistStr);
            iconBlacklist.add("rotate");
            iconBlacklist.add("headset");
            Settings.Secure.putStringForUser(this.mContentResolver, "icon_blacklist", TextUtils.join(",", iconBlacklist), this.mCurrentUser);
        }
        if (oldVersion < 2) {
            setTunerEnabled(this.mContext, false);
        }
        if (oldVersion < 4) {
            new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).postDelayed(new Runnable() { // from class: com.android.systemui.tuner.-$$Lambda$TunerServiceImpl$6-LaNwUHcRc1UGw8s8y1YCsTRbQ
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.clearAll();
                }
            }, 5000L);
        }
        setValue("sysui_tuner_version", newVersion);
    }

    @Override // com.android.systemui.tuner.TunerService
    public String getValue(String setting) {
        return Settings.Secure.getStringForUser(this.mContentResolver, setting, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public void setValue(String setting, String value) {
        Settings.Secure.putStringForUser(this.mContentResolver, setting, value, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public int getValue(String setting, int def) {
        return Settings.Secure.getIntForUser(this.mContentResolver, setting, def, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public String getValue(String setting, String def) {
        String ret = Settings.Secure.getStringForUser(this.mContentResolver, setting, this.mCurrentUser);
        return ret == null ? def : ret;
    }

    @Override // com.android.systemui.tuner.TunerService
    public void setValue(String setting, int value) {
        Settings.Secure.putIntForUser(this.mContentResolver, setting, value, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public void addTunable(TunerService.Tunable tunable, String... keys) {
        for (String key : keys) {
            addTunable(tunable, key);
        }
    }

    private void addTunable(TunerService.Tunable tunable, String key) {
        if (!this.mTunableLookup.containsKey(key)) {
            this.mTunableLookup.put(key, new ArraySet());
        }
        this.mTunableLookup.get(key).add(tunable);
        if (LeakDetector.ENABLED) {
            this.mTunables.add(tunable);
            ((LeakDetector) Dependency.get(LeakDetector.class)).trackCollection(this.mTunables, "TunerService.mTunables");
        }
        Uri uri = Settings.Secure.getUriFor(key);
        if (!this.mListeningUris.containsKey(uri)) {
            this.mListeningUris.put(uri, key);
            this.mContentResolver.registerContentObserver(uri, false, this.mObserver, this.mCurrentUser);
        }
        String value = Settings.Secure.getStringForUser(this.mContentResolver, key, this.mCurrentUser);
        tunable.onTuningChanged(key, value);
    }

    @Override // com.android.systemui.tuner.TunerService
    public void removeTunable(TunerService.Tunable tunable) {
        for (Set<TunerService.Tunable> list : this.mTunableLookup.values()) {
            list.remove(tunable);
        }
        if (LeakDetector.ENABLED) {
            this.mTunables.remove(tunable);
        }
    }

    protected void reregisterAll() {
        if (this.mListeningUris.size() == 0) {
            return;
        }
        this.mContentResolver.unregisterContentObserver(this.mObserver);
        for (Uri uri : this.mListeningUris.keySet()) {
            this.mContentResolver.registerContentObserver(uri, false, this.mObserver, this.mCurrentUser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadSetting(Uri uri) {
        String key = this.mListeningUris.get(uri);
        Set<TunerService.Tunable> tunables = this.mTunableLookup.get(key);
        if (tunables == null) {
            return;
        }
        String value = Settings.Secure.getStringForUser(this.mContentResolver, key, this.mCurrentUser);
        for (TunerService.Tunable tunable : tunables) {
            tunable.onTuningChanged(key, value);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadAll() {
        for (String key : this.mTunableLookup.keySet()) {
            String value = Settings.Secure.getStringForUser(this.mContentResolver, key, this.mCurrentUser);
            for (TunerService.Tunable tunable : this.mTunableLookup.get(key)) {
                tunable.onTuningChanged(key, value);
            }
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public void clearAll() {
        Settings.Global.putString(this.mContentResolver, "sysui_demo_allowed", null);
        Intent intent = new Intent("com.android.systemui.demo");
        intent.putExtra("command", "exit");
        this.mContext.sendBroadcast(intent);
        for (String key : this.mTunableLookup.keySet()) {
            if (!ArrayUtils.contains(RESET_BLACKLIST, key)) {
                Settings.Secure.putString(this.mContentResolver, key, null);
            }
        }
    }

    private class Observer extends ContentObserver {
        public Observer() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri, int userId) {
            if (userId == ActivityManager.getCurrentUser()) {
                TunerServiceImpl.this.reloadSetting(uri);
            }
        }
    }
}
