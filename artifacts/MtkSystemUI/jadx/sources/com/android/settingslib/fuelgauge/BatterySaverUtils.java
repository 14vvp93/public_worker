package com.android.settingslib.fuelgauge;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;

/* JADX INFO: loaded from: classes.dex */
public class BatterySaverUtils {

    private static class Parameters {
        public final int endNth;
        private final Context mContext;
        public final int startNth;

        public Parameters(Context context) {
            this.mContext = context;
            String newValue = Settings.Global.getString(this.mContext.getContentResolver(), "low_power_mode_suggestion_params");
            KeyValueListParser parser = new KeyValueListParser(',');
            try {
                parser.setString(newValue);
            } catch (IllegalArgumentException e) {
                Slog.wtf("BatterySaverUtils", "Bad constants: " + newValue);
            }
            this.startNth = parser.getInt("start_nth", 4);
            this.endNth = parser.getInt("end_nth", 8);
        }
    }

    public static synchronized boolean setPowerSaveMode(Context context, boolean enable, boolean needFirstTimeWarning) {
        ContentResolver cr = context.getContentResolver();
        if (enable && needFirstTimeWarning && maybeShowBatterySaverConfirmation(context)) {
            return false;
        }
        if (enable && !needFirstTimeWarning) {
            setBatterySaverConfirmationAcknowledged(context);
        }
        if (!((PowerManager) context.getSystemService(PowerManager.class)).setPowerSaveMode(enable)) {
            return false;
        }
        if (enable) {
            int count = Settings.Secure.getInt(cr, "low_power_manual_activation_count", 0) + 1;
            Settings.Secure.putInt(cr, "low_power_manual_activation_count", count);
            Parameters parameters = new Parameters(context);
            if (count >= parameters.startNth && count <= parameters.endNth && Settings.Global.getInt(cr, "low_power_trigger_level", 0) == 0 && Settings.Secure.getInt(cr, "suppress_auto_battery_saver_suggestion", 0) == 0) {
                showAutoBatterySaverSuggestion(context);
            }
        }
        return true;
    }

    private static boolean maybeShowBatterySaverConfirmation(Context context) {
        if (Settings.Secure.getInt(context.getContentResolver(), "low_power_warning_acknowledged", 0) != 0) {
            return false;
        }
        context.sendBroadcast(getSystemUiBroadcast("PNW.startSaverConfirmation"));
        return true;
    }

    private static void showAutoBatterySaverSuggestion(Context context) {
        context.sendBroadcast(getSystemUiBroadcast("PNW.autoSaverSuggestion"));
    }

    private static Intent getSystemUiBroadcast(String action) {
        Intent i = new Intent(action);
        i.setFlags(268435456);
        i.setPackage("com.android.systemui");
        return i;
    }

    private static void setBatterySaverConfirmationAcknowledged(Context context) {
        Settings.Secure.putInt(context.getContentResolver(), "low_power_warning_acknowledged", 1);
    }

    public static void suppressAutoBatterySaver(Context context) {
        Settings.Secure.putInt(context.getContentResolver(), "suppress_auto_battery_saver_suggestion", 1);
    }

    public static void setAutoBatterySaverTriggerLevel(Context context, int level) {
        if (level > 0) {
            suppressAutoBatterySaver(context);
        }
        Settings.Global.putInt(context.getContentResolver(), "low_power_trigger_level", level);
    }

    public static void ensureAutoBatterySaver(Context context, int level) {
        if (Settings.Global.getInt(context.getContentResolver(), "low_power_trigger_level", 0) == 0) {
            setAutoBatterySaverTriggerLevel(context, level);
        }
    }
}
