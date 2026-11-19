package com.android.keyguard;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.mediatek.internal.telephony.MtkSubscriptionManager;
import com.mediatek.telephony.MtkTelephonyManagerEx;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardUtils {
    private SubscriptionManager mSubscriptionManager;
    private KeyguardUpdateMonitor mUpdateMonitor;
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private static final boolean mIsPrivacyProtectionLockSupport = SystemProperties.get("ro.vendor.mtk_privacy_protection_lock").equals("1");
    private static KeyguardUtils sInstance = new KeyguardUtils();
    private static boolean mIsMediatekSimMeLockSupport = SystemProperties.get("ro.vendor.sim_me_lock_mode", "0").equals("0");
    private static int sPhoneCount = 0;
    private static boolean mIsDismissEnabled = false;
    private static boolean mIsSimmePolicyEnabled = false;
    private static int mValidPhoneIds = -1;
    private static boolean mIsDismissSimMeLockSupport = false;

    public KeyguardUtils(Context context) {
        this.mUpdateMonitor = KeyguardUpdateMonitor.getInstance(context);
        this.mSubscriptionManager = SubscriptionManager.from(context);
    }

    private KeyguardUtils() {
    }

    public static KeyguardUtils getDefault() {
        return sInstance;
    }

    public String getOptrNameUsingPhoneId(int phoneId, Context context) {
        int subId = getSubIdUsingPhoneId(phoneId);
        SubscriptionInfo info = this.mSubscriptionManager.getActiveSubscriptionInfo(subId);
        if (info == null) {
            if (DEBUG) {
                Log.d("KeyguardUtils", "getOptrNameUsingPhoneId, return null");
                return null;
            }
            return null;
        }
        if (DEBUG) {
            Log.d("KeyguardUtils", "getOptrNameUsingPhoneId mDisplayName=" + ((Object) info.getDisplayName()));
        }
        if (info.getDisplayName() != null) {
            return info.getDisplayName().toString();
        }
        return null;
    }

    public Bitmap getOptrBitmapUsingPhoneId(int phoneId, Context context) {
        int subId = getSubIdUsingPhoneId(phoneId);
        SubscriptionInfo info = this.mSubscriptionManager.getActiveSubscriptionInfo(subId);
        if (info == null) {
            if (!DEBUG) {
                return null;
            }
            Log.d("KeyguardUtils", "getOptrBitmapUsingPhoneId, return null");
            return null;
        }
        Bitmap bgBitmap = info.createIconBitmap(context);
        return bgBitmap;
    }

    public static final boolean isMediatekSimMeLockSupport() {
        return mIsMediatekSimMeLockSupport;
    }

    public static final boolean isDismissSimMeLockSupport() {
        return mIsDismissSimMeLockSupport;
    }

    public static boolean isFlightModePowerOffMd() {
        boolean powerOffMd = SystemProperties.get("ro.vendor.mtk_flight_mode_power_off_md").equals("1");
        Log.d("KeyguardUtils", "powerOffMd = " + powerOffMd);
        return powerOffMd;
    }

    public static int getNumOfPhone() {
        if (sPhoneCount == 0) {
            sPhoneCount = TelephonyManager.getDefault().getPhoneCount();
            sPhoneCount = sPhoneCount <= 4 ? sPhoneCount : 4;
        }
        return sPhoneCount;
    }

    public static boolean isValidPhoneId(int phoneId) {
        return phoneId != Integer.MAX_VALUE && phoneId >= 0 && phoneId < getNumOfPhone();
    }

    public static int getPhoneIdUsingSubId(int subId) {
        Log.e("KeyguardUtils", "getPhoneIdUsingSubId: subId = " + subId);
        int phoneId = SubscriptionManager.getPhoneId(subId);
        if (phoneId < 0 || phoneId >= getNumOfPhone()) {
            Log.e("KeyguardUtils", "getPhoneIdUsingSubId: invalid phonId = " + phoneId);
        } else {
            Log.e("KeyguardUtils", "getPhoneIdUsingSubId: get phone ID = " + phoneId);
        }
        return phoneId;
    }

    public static int getSubIdUsingPhoneId(int phoneId) {
        int subId = MtkSubscriptionManager.getSubIdUsingPhoneId(phoneId);
        return subId;
    }

    public static void requestImeStatusRefresh(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        if (imm != null && DEBUG) {
            Log.d("KeyguardUtils", "call imm.requestImeStatusRefresh()");
        }
    }

    public static boolean isSystemEncrypted() {
        boolean bRet = true;
        String cryptoType = SystemProperties.get("ro.crypto.type");
        String state = SystemProperties.get("ro.crypto.state");
        String decrypt = SystemProperties.get("vold.decrypt");
        if ("unsupported".equals(state)) {
            return false;
        }
        if ("unencrypted".equals(state)) {
            if ("".equals(decrypt)) {
                bRet = false;
            }
        } else if (!"".equals(state) && "encrypted".equals(state)) {
            if ("file".equals(cryptoType)) {
                bRet = false;
            } else if ("block".equals(cryptoType) && "trigger_restart_framework".equals(decrypt)) {
                bRet = false;
            }
        }
        if (DEBUG) {
            Log.d("KeyguardUtils", "cryptoType=" + cryptoType + " ro.crypto.state=" + state + " vold.decrypt=" + decrypt + " sysEncrypted=" + bRet);
        }
        return bRet;
    }

    public static final boolean isPrivacyProtectionLockSupport() {
        return mIsPrivacyProtectionLockSupport;
    }

    public static final boolean isSimMeLockValid(int phoneId) {
        if (mValidPhoneIds == -1) {
            return true;
        }
        int validMask = mValidPhoneIds & (1 << phoneId);
        Log.d("KeyguardUtils", "isSimMeLockValid phoneId=" + phoneId + ", result=" + validMask);
        return (1 << phoneId) == validMask;
    }

    public final void initSimmePolicy(Context context) {
        if (mIsMediatekSimMeLockSupport) {
            return;
        }
        if ("1".equals(context.getResources().getString(com.android.systemui.R.string.simme_dependency_enabled))) {
            Log.d("KeyguardUtils", "config enabled for simme dependency");
            mIsSimmePolicyEnabled = true;
        } else {
            File simmeOption = new File(Environment.getExternalStorageDirectory(), "SimmeLock");
            mIsSimmePolicyEnabled = simmeOption.exists();
        }
        if ("1".equals(context.getResources().getString(com.android.systemui.R.string.dismiss_button_enabled))) {
            Log.d("KeyguardUtils", "config enabled for simme dismiss");
            mIsDismissEnabled = true;
        } else {
            File dismissOption = new File(Environment.getExternalStorageDirectory(), "SimmeDismiss");
            mIsDismissEnabled = dismissOption.exists();
        }
        int lockPolicy = MtkTelephonyManagerEx.getDefault().getSimLockPolicy();
        mIsMediatekSimMeLockSupport = lockPolicy > 0;
        if (lockPolicy <= 0) {
            return;
        }
        if (mIsSimmePolicyEnabled) {
            mValidPhoneIds = parseValueFromConfig(context, "simme_lock_with_request_", lockPolicy);
            if (mValidPhoneIds > 15) {
                mValidPhoneIds = -1;
            }
        }
        if (mIsDismissEnabled) {
            mIsDismissSimMeLockSupport = true;
            if (parseValueFromConfig(context, "dismiss_button_with_request_", lockPolicy) == 0) {
                mIsDismissSimMeLockSupport = false;
            }
        }
    }

    private final int parseValueFromConfig(Context context, String configName, int lockPolicy) {
        int resourceId = context.getResources().getIdentifier(configName + lockPolicy, "string", context.getPackageName());
        if (resourceId == 0) {
            Log.w("KeyguardUtils", "Cannot get valid source id for " + configName + lockPolicy);
            return -1;
        }
        String configValueString = context.getResources().getString(resourceId);
        Log.d("KeyguardUtils", "Get config for " + configName + lockPolicy + ", value=" + configValueString);
        if (configValueString == null || configValueString.isEmpty()) {
            return -1;
        }
        try {
            int configInt = Integer.parseInt(configValueString);
            return configInt;
        } catch (Exception e) {
            Log.e("KeyguardUtils", "Exception happened: " + e);
            return -1;
        }
    }
}
