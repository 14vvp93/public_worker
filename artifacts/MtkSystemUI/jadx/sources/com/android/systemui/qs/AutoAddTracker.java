package com.android.systemui.qs;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Prefs;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class AutoAddTracker {
    private static final String[][] CONVERT_PREFS = {new String[]{"QsHotspotAdded", "hotspot"}, new String[]{"QsDataSaverAdded", "saver"}, new String[]{"QsInvertColorsAdded", "inversion"}, new String[]{"QsWorkAdded", "work"}, new String[]{"QsNightDisplayAdded", "night"}};
    private final Context mContext;

    @VisibleForTesting
    protected final ContentObserver mObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.qs.AutoAddTracker.1
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            AutoAddTracker.this.mAutoAdded.addAll(AutoAddTracker.this.getAdded());
        }
    };
    private final ArraySet<String> mAutoAdded = new ArraySet<>(getAdded());

    public AutoAddTracker(Context context) {
        this.mContext = context;
        for (String[] convertPref : CONVERT_PREFS) {
            if (Prefs.getBoolean(context, convertPref[0], false)) {
                setTileAdded(convertPref[1]);
                Prefs.remove(context, convertPref[0]);
            }
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("qs_auto_tiles"), false, this.mObserver);
    }

    public boolean isAdded(String tile) {
        return this.mAutoAdded.contains(tile);
    }

    public void setTileAdded(String tile) {
        if (this.mAutoAdded.add(tile)) {
            saveTiles();
        }
    }

    private void saveTiles() {
        Settings.Secure.putString(this.mContext.getContentResolver(), "qs_auto_tiles", TextUtils.join(",", this.mAutoAdded));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection<String> getAdded() {
        String current = Settings.Secure.getString(this.mContext.getContentResolver(), "qs_auto_tiles");
        if (current == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(current.split(","));
    }
}
