package com.android.settingslib.net;

import android.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.INetworkStatsService;
import android.net.INetworkStatsSession;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkStatsHistory;
import android.net.NetworkTemplate;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Pair;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DataUsageController {
    private static final boolean DEBUG = Log.isLoggable("DataUsageController", 3);
    private static final StringBuilder PERIOD_BUILDER = new StringBuilder(50);
    private static final Formatter PERIOD_FORMATTER = new Formatter(PERIOD_BUILDER, Locale.getDefault());
    private Callback mCallback;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private NetworkNameProvider mNetworkController;
    private final NetworkPolicyManager mPolicyManager;
    private INetworkStatsSession mSession;
    private final INetworkStatsService mStatsService = INetworkStatsService.Stub.asInterface(ServiceManager.getService("netstats"));
    private final TelephonyManager mTelephonyManager;

    public interface Callback {
        void onMobileDataEnabled(boolean z);
    }

    public static class DataUsageInfo {
        public String carrier;
        public long cycleEnd;
        public long cycleStart;
        public long limitLevel;
        public String period;
        public long startDate;
        public long usageLevel;
        public long warningLevel;
    }

    public interface NetworkNameProvider {
        String getMobileDataNetworkName();
    }

    public DataUsageController(Context context) {
        this.mContext = context;
        this.mTelephonyManager = TelephonyManager.from(context);
        this.mConnectivityManager = ConnectivityManager.from(context);
        this.mPolicyManager = NetworkPolicyManager.from(this.mContext);
    }

    public void setNetworkController(NetworkNameProvider networkController) {
        this.mNetworkController = networkController;
    }

    public long getDefaultWarningLevel() {
        return 1048576 * ((long) this.mContext.getResources().getInteger(R.integer.config_motionPredictionOffsetNanos));
    }

    private INetworkStatsSession getSession() {
        if (this.mSession == null) {
            try {
                this.mSession = this.mStatsService.openSession();
            } catch (RemoteException e) {
                Log.w("DataUsageController", "Failed to open stats session", e);
            } catch (RuntimeException e2) {
                Log.w("DataUsageController", "Failed to open stats session", e2);
            }
        }
        return this.mSession;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    private DataUsageInfo warn(String msg) {
        Log.w("DataUsageController", "Failed to get data usage, " + msg);
        return null;
    }

    public DataUsageInfo getDataUsageInfo() {
        String subscriberId = getActiveSubscriberId(this.mContext);
        if (subscriberId == null) {
            return warn("no subscriber id");
        }
        NetworkTemplate template = NetworkTemplate.buildTemplateMobileAll(subscriberId);
        return getDataUsageInfo(NetworkTemplate.normalize(template, this.mTelephonyManager.getMergedSubscriberIds()));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public DataUsageInfo getDataUsageInfo(NetworkTemplate template) {
        long end;
        long start;
        long end2;
        DataUsageController dataUsageController = this;
        INetworkStatsSession session = getSession();
        if (session == null) {
            return dataUsageController.warn("no stats session");
        }
        NetworkPolicy policy = findNetworkPolicy(template);
        try {
            NetworkStatsHistory history = session.getHistoryForNetwork(template, 10);
            long now = System.currentTimeMillis();
            if (policy != null) {
                try {
                    boolean hasNext = NetworkPolicyManager.cycleIterator(policy).hasNext();
                    Log.i("DataUsageController", "getDataUsageInfo , policy != null hasNext = " + hasNext);
                    if (hasNext) {
                        Pair<ZonedDateTime, ZonedDateTime> cycle = (Pair) NetworkPolicyManager.cycleIterator(policy).next();
                        long start2 = ((ZonedDateTime) cycle.first).toInstant().toEpochMilli();
                        end = ((ZonedDateTime) cycle.second).toInstant().toEpochMilli();
                        start = start2;
                    } else {
                        end = now;
                        start = now - 2419200000L;
                    }
                    end2 = end;
                } catch (RemoteException e) {
                }
            } else {
                Log.i("DataUsageController", "getDataUsageInfo , policy = null");
                start = now - 2419200000L;
                end2 = now;
            }
            long end3 = start;
            long callStart = System.currentTimeMillis();
            long start3 = end2;
            long end4 = end2;
            try {
                NetworkStatsHistory.Entry entry = history.getValues(end3, start3, now, (NetworkStatsHistory.Entry) null);
                long callEnd = System.currentTimeMillis();
                if (DEBUG) {
                    try {
                        Log.d("DataUsageController", String.format("history call from %s to %s now=%s took %sms: %s", new Date(end3), new Date(end4), new Date(now), Long.valueOf(callEnd - callStart), historyEntryToString(entry)));
                    } catch (RemoteException e2) {
                        dataUsageController = this;
                    }
                }
                if (entry == null) {
                    dataUsageController = this;
                    try {
                        return dataUsageController.warn("no entry data");
                    } catch (RemoteException e3) {
                    }
                } else {
                    dataUsageController = this;
                    try {
                        long totalBytes = entry.rxBytes + entry.txBytes;
                        DataUsageInfo usage = new DataUsageInfo();
                        usage.startDate = end3;
                        usage.usageLevel = totalBytes;
                        usage.period = dataUsageController.formatDateRange(end3, end4);
                        usage.cycleStart = end3;
                        usage.cycleEnd = end4;
                        try {
                            if (policy != null) {
                                long start4 = policy.limitBytes;
                                usage.limitLevel = start4 > 0 ? policy.limitBytes : 0L;
                                usage.warningLevel = policy.warningBytes > 0 ? policy.warningBytes : 0L;
                            } else {
                                long start5 = getDefaultWarningLevel();
                                usage.warningLevel = start5;
                            }
                            if (dataUsageController.mNetworkController != null) {
                                usage.carrier = dataUsageController.mNetworkController.getMobileDataNetworkName();
                            }
                            return usage;
                        } catch (RemoteException e4) {
                        }
                    } catch (RemoteException e5) {
                    }
                }
            } catch (RemoteException e6) {
                dataUsageController = this;
            }
        } catch (RemoteException e7) {
        }
        return dataUsageController.warn("remote call failed");
    }

    private NetworkPolicy findNetworkPolicy(NetworkTemplate template) {
        NetworkPolicy[] policies;
        if (this.mPolicyManager == null || template == null || (policies = this.mPolicyManager.getNetworkPolicies()) == null) {
            return null;
        }
        for (NetworkPolicy policy : policies) {
            if (policy != null && template.equals(policy.template)) {
                return policy;
            }
        }
        return null;
    }

    private static String historyEntryToString(NetworkStatsHistory.Entry entry) {
        if (entry == null) {
            return null;
        }
        return "Entry[bucketDuration=" + entry.bucketDuration + ",bucketStart=" + entry.bucketStart + ",activeTime=" + entry.activeTime + ",rxBytes=" + entry.rxBytes + ",rxPackets=" + entry.rxPackets + ",txBytes=" + entry.txBytes + ",txPackets=" + entry.txPackets + ",operations=" + entry.operations + ']';
    }

    public void setMobileDataEnabled(boolean enabled) {
        Log.d("DataUsageController", "setMobileDataEnabled: enabled=" + enabled);
        this.mTelephonyManager.setDataEnabled(enabled);
        if (this.mCallback != null) {
            this.mCallback.onMobileDataEnabled(enabled);
        }
    }

    public boolean isMobileDataSupported() {
        return this.mConnectivityManager.isNetworkSupported(0) && this.mTelephonyManager.getSimState() == 5;
    }

    public boolean isMobileDataEnabled() {
        return this.mTelephonyManager.getDataEnabled();
    }

    private static String getActiveSubscriberId(Context context) {
        TelephonyManager tele = TelephonyManager.from(context);
        String actualSubscriberId = tele.getSubscriberId(SubscriptionManager.getDefaultDataSubscriptionId());
        return actualSubscriberId;
    }

    private String formatDateRange(long start, long end) throws Throwable {
        synchronized (PERIOD_BUILDER) {
            try {
                try {
                    PERIOD_BUILDER.setLength(0);
                    return DateUtils.formatDateRange(this.mContext, PERIOD_FORMATTER, start, end, 65552, null).toString();
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }
}
