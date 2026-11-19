package com.mediatek.systemui.statusbar.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import com.mediatek.internal.telephony.MtkIccCardConstants;
import com.mediatek.internal.telephony.MtkSubscriptionManager;
import com.mediatek.telephony.MtkTelephonyManagerEx;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SIMHelper {
    private static final String[] CT_NUMERIC = {"45502", "45507", "46003", "46011", "46012", "46013"};
    public static Context sContext;
    private static List<SubscriptionInfo> sSimInfos;

    public static void updateSIMInfos(Context context) {
        sSimInfos = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
    }

    public static int getFirstSubInSlot(int slotId) {
        int[] subIds = SubscriptionManager.getSubId(slotId);
        if (subIds != null && subIds.length > 0) {
            return subIds[0];
        }
        Log.d("SIMHelper", "Cannot get first sub in slot: " + slotId);
        return -1;
    }

    public static int getSlotCount() {
        return TelephonyManager.getDefault().getPhoneCount();
    }

    public static void setContext(Context context) {
        sContext = context;
    }

    public static boolean isWifiOnlyDevice() {
        Context context = sContext;
        Context context2 = sContext;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        return !cm.isNetworkSupported(0);
    }

    public static boolean isRadioOn(int subId) {
        ITelephony telephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (telephony != null) {
            try {
                return telephony.isRadioOnForSubscriber(subId, sContext.getPackageName());
            } catch (RemoteException e) {
                Log.e("SIMHelper", "mTelephony exception");
                return false;
            }
        }
        return false;
    }

    public static int getMainPhoneId() {
        int mainPhoneId = -1;
        String curr3GSim = SystemProperties.get("persist.vendor.radio.simswitch", "1");
        if (!TextUtils.isEmpty(curr3GSim)) {
            int curr3GPhoneId = Integer.parseInt(curr3GSim);
            mainPhoneId = curr3GPhoneId - 1;
        }
        Log.d("@M_SIMHelper", "getMainPhoneId, mainPhoneId = " + mainPhoneId);
        return mainPhoneId;
    }

    public static boolean isSecondaryCSIMForMixedVolte(int subId) {
        boolean ret = false;
        int mainPhoneId = getMainPhoneId();
        int mainPhoneSubId = MtkSubscriptionManager.getSubIdUsingPhoneId(mainPhoneId);
        int currentSoltId = SubscriptionManager.getSlotIndex(subId);
        if (mainPhoneId != currentSoltId && ((isCTCardType(mainPhoneId) || isCtSim(mainPhoneSubId)) && (isCTCardType(currentSoltId) || isCtSim(subId)))) {
            ret = true;
        }
        if (FeatureOptions.LOG_ENABLE) {
            Log.d("@M_SIMHelper", "isSecondaryCSIMForMixedVolte, ret = " + ret + ", subId = " + subId);
        }
        return ret;
    }

    public static boolean isCtSim(int subId) {
        boolean ctSim = false;
        String numeric = TelephonyManager.getDefault().getSimOperator(subId);
        for (String ct : CT_NUMERIC) {
            if (ct.equals(numeric)) {
                ctSim = true;
                break;
            }
        }
        if (FeatureOptions.LOG_ENABLE) {
            Log.d("@M_SIMHelper", "isCtSim, ctSim = " + ctSim + ", subId = " + subId);
        }
        return ctSim;
    }

    public static boolean isCTCardType(int slotId) {
        boolean ctCard = false;
        MtkIccCardConstants.CardType cardType = MtkTelephonyManagerEx.getDefault().getCdmaCardType(slotId);
        if (MtkIccCardConstants.CardType.CT_4G_UICC_CARD.equals(cardType) || MtkIccCardConstants.CardType.CT_3G_UIM_CARD.equals(cardType) || MtkIccCardConstants.CardType.CT_UIM_SIM_CARD.equals(cardType)) {
            ctCard = true;
        }
        if (FeatureOptions.LOG_ENABLE) {
            Log.d("@M_SIMHelper", "isCTCardType, ctCard = " + ctCard + ", soltId = " + slotId);
        }
        return ctCard;
    }
}
