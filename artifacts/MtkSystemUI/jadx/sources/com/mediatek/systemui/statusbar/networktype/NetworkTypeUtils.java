package com.mediatek.systemui.statusbar.networktype;

import android.telephony.ServiceState;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.NetworkControllerImpl;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkTypeUtils {
    static final Map<Integer, Integer> sNetworkTypeIcons = new HashMap<Integer, Integer>() { // from class: com.mediatek.systemui.statusbar.networktype.NetworkTypeUtils.1
        {
            put(5, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(6, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(12, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(14, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(4, Integer.valueOf(R.drawable.stat_sys_network_type_1x));
            put(7, Integer.valueOf(R.drawable.stat_sys_network_type_1x));
            put(2, Integer.valueOf(R.drawable.stat_sys_network_type_e));
            put(3, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(13, Integer.valueOf(R.drawable.stat_sys_network_type_4g));
            put(8, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(9, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(10, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(15, Integer.valueOf(R.drawable.stat_sys_network_type_3g));
            put(18, 0);
        }
    };

    public static int getNetworkTypeIcon(ServiceState serviceState, NetworkControllerImpl.Config config, boolean hasService) {
        int i = 0;
        if (!hasService) {
            return 0;
        }
        int tempNetworkType = getNetworkType(serviceState);
        Integer iconId = sNetworkTypeIcons.get(Integer.valueOf(tempNetworkType));
        if (iconId == null) {
            if (tempNetworkType != 0) {
                i = config.showAtLeast3G ? R.drawable.stat_sys_network_type_3g : R.drawable.stat_sys_network_type_g;
            }
            iconId = Integer.valueOf(i);
        }
        return iconId.intValue();
    }

    private static int getNetworkType(ServiceState serviceState) {
        if (serviceState == null) {
            return 0;
        }
        int type = serviceState.getDataNetworkType() != 0 ? serviceState.getDataNetworkType() : serviceState.getVoiceNetworkType();
        return type;
    }
}
