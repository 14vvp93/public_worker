package com.mt8163tools.hooks

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

class XposedEntry : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != SYSTEM_SERVER_PACKAGE) return

        try {
            VoltageHook().install(lpparam.classLoader)
        } catch (throwable: Throwable) {
            XposedBridge.log("$LOG_TAG: failed to initialize Voltage hook: $throwable")
        }

        try {
            AccessoryHook().install(lpparam.classLoader)
        } catch (throwable: Throwable) {
            XposedBridge.log("$LOG_TAG: failed to initialize Accessory hook: $throwable")
        }
    }

    private companion object {
        const val SYSTEM_SERVER_PACKAGE = "android"
        const val LOG_TAG = "MT8163Tools"
    }
}
