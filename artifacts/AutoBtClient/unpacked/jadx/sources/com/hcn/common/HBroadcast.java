package com.hcn.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.security.InvalidParameterException;

/* JADX INFO: loaded from: classes.dex */
public class HBroadcast extends BroadcastReceiver {
    public static String DEBUG_CONFIG_ACTION = "com.hcn.$(module).config.action";
    public static final String DEBUG_CONFIG_EXTRA_INFO_LOGGER = "Logger";
    public static final String DEBUG_CONFIG_EXTRA_KEY = "config.extra.key";

    public static void init(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new InvalidParameterException("HBroadcast init: 'moduleKey' is empty!");
        }
        DEBUG_CONFIG_ACTION = DEBUG_CONFIG_ACTION.replace("$(module)", str);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && DEBUG_CONFIG_ACTION.equals(action)) {
            String stringExtra = intent.getStringExtra(DEBUG_CONFIG_EXTRA_KEY);
            if (!TextUtils.isEmpty(stringExtra) && DEBUG_CONFIG_EXTRA_INFO_LOGGER.equals(stringExtra)) {
                HConfig.loggerConfig(false);
            }
        }
    }
}
