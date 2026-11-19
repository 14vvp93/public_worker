package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.systemui.statusbar.policy.SignalController.IconGroup;
import com.android.systemui.statusbar.policy.SignalController.State;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.io.PrintWriter;
import java.util.BitSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class SignalController<T extends State, I extends IconGroup> {
    protected static final boolean CHATTY;
    protected static final boolean DEBUG;
    private final CallbackHandler mCallbackHandler;
    protected final Context mContext;
    private int mHistoryIndex;
    protected final NetworkControllerImpl mNetworkController;
    protected final String mTag;
    protected final int mTransportType;
    protected final T mCurrentState = (T) cleanState();
    protected final T mLastState = (T) cleanState();
    private final State[] mHistory = new State[64];

    protected abstract T cleanState();

    public abstract void notifyListeners(NetworkController.SignalCallback signalCallback);

    static {
        boolean z = true;
        DEBUG = FeatureOptions.LOG_ENABLE || NetworkControllerImpl.DEBUG;
        if (!FeatureOptions.LOG_ENABLE && !NetworkControllerImpl.CHATTY) {
            z = false;
        }
        CHATTY = z;
    }

    public SignalController(String str, Context context, int i, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl) {
        this.mTag = "NetworkController." + str;
        this.mNetworkController = networkControllerImpl;
        this.mTransportType = i;
        this.mContext = context;
        this.mCallbackHandler = callbackHandler;
        for (int i2 = 0; i2 < 64; i2++) {
            this.mHistory[i2] = cleanState();
        }
        Log.d(this.mTag, "constructor");
    }

    public T getState() {
        return this.mCurrentState;
    }

    public void updateConnectivity(BitSet bitSet, BitSet bitSet2) {
        this.mCurrentState.inetCondition = bitSet2.get(this.mTransportType) ? 1 : 0;
        notifyListenersIfNecessary();
    }

    public void resetLastState() {
        this.mCurrentState.copyFrom(this.mLastState);
    }

    public boolean isDirty() {
        if (!this.mLastState.equals(this.mCurrentState)) {
            if (DEBUG) {
                Log.d(this.mTag, "[" + this + "]\nChange in state from: " + this.mLastState + "\n                  to: " + this.mCurrentState);
                return true;
            }
            return true;
        }
        return false;
    }

    public void saveLastState() {
        recordLastState();
        this.mCurrentState.time = System.currentTimeMillis();
        this.mLastState.copyFrom(this.mCurrentState);
    }

    public int getQsCurrentIconId() {
        if (this.mCurrentState.connected) {
            return getIcons().mQsIcons[this.mCurrentState.inetCondition][this.mCurrentState.level];
        }
        if (this.mCurrentState.enabled) {
            return getIcons().mQsDiscState;
        }
        return getIcons().mQsNullState;
    }

    public int getCurrentIconId() {
        if (this.mCurrentState.connected) {
            return getIcons().mSbIcons[this.mCurrentState.inetCondition][this.mCurrentState.level];
        }
        if (this.mCurrentState.enabled) {
            return getIcons().mSbDiscState;
        }
        return getIcons().mSbNullState;
    }

    public int getContentDescription() {
        if (this.mCurrentState.connected) {
            return getIcons().mContentDesc[this.mCurrentState.level];
        }
        return getIcons().mDiscContentDesc;
    }

    public void notifyListenersIfNecessary() {
        if (isDirty()) {
            saveLastState();
            notifyListeners();
        }
    }

    protected String getStringIfExists(int resId) {
        return resId != 0 ? this.mContext.getString(resId) : "";
    }

    protected I getIcons() {
        return (I) this.mCurrentState.iconGroup;
    }

    protected void recordLastState() {
        State[] stateArr = this.mHistory;
        int i = this.mHistoryIndex;
        this.mHistoryIndex = i + 1;
        stateArr[i & 63].copyFrom(this.mLastState);
    }

    public void dump(PrintWriter pw) {
        pw.println("  - " + this.mTag + " -----");
        StringBuilder sb = new StringBuilder();
        sb.append("  Current State: ");
        sb.append(this.mCurrentState);
        pw.println(sb.toString());
        int size = 0;
        for (int i = 0; i < 64; i++) {
            if (this.mHistory[i].time != 0) {
                size++;
            }
        }
        int i2 = this.mHistoryIndex;
        int i3 = i2 + 64;
        while (true) {
            i3--;
            if (i3 < (this.mHistoryIndex + 64) - size) {
                return;
            } else {
                pw.println("  Previous State(" + ((this.mHistoryIndex + 64) - i3) + "): " + this.mHistory[i3 & 63]);
            }
        }
    }

    public final void notifyListeners() {
        notifyListeners(this.mCallbackHandler);
    }

    static class IconGroup {
        final int[] mContentDesc;
        final int mDiscContentDesc;
        final String mName;
        final int mQsDiscState;
        final int[][] mQsIcons;
        final int mQsNullState;
        final int mSbDiscState;
        final int[][] mSbIcons;
        final int mSbNullState;

        public IconGroup(String name, int[][] sbIcons, int[][] qsIcons, int[] contentDesc, int sbNullState, int qsNullState, int sbDiscState, int qsDiscState, int discContentDesc) {
            this.mName = name;
            this.mSbIcons = sbIcons;
            this.mQsIcons = qsIcons;
            this.mContentDesc = contentDesc;
            this.mSbNullState = sbNullState;
            this.mQsNullState = qsNullState;
            this.mSbDiscState = sbDiscState;
            this.mQsDiscState = qsDiscState;
            this.mDiscContentDesc = discContentDesc;
        }

        public String toString() {
            return "IconGroup(" + this.mName + ")";
        }
    }

    static class State {
        boolean activityIn;
        boolean activityOut;
        boolean connected;
        boolean enabled;
        IconGroup iconGroup;
        int inetCondition;
        int level;
        int rssi;
        long time;

        State() {
        }

        public void copyFrom(State state) {
            this.connected = state.connected;
            this.enabled = state.enabled;
            this.level = state.level;
            this.iconGroup = state.iconGroup;
            this.inetCondition = state.inetCondition;
            this.activityIn = state.activityIn;
            this.activityOut = state.activityOut;
            this.rssi = state.rssi;
            this.time = state.time;
        }

        public String toString() {
            if (this.time != 0) {
                StringBuilder builder = new StringBuilder();
                toString(builder);
                return builder.toString();
            }
            return "Empty " + getClass().getSimpleName();
        }

        protected void toString(StringBuilder builder) {
            builder.append("this=");
            builder.append(hashCode());
            builder.append(',');
            builder.append("connected=");
            builder.append(this.connected);
            builder.append(',');
            builder.append("enabled=");
            builder.append(this.enabled);
            builder.append(',');
            builder.append("level=");
            builder.append(this.level);
            builder.append(',');
            builder.append("inetCondition=");
            builder.append(this.inetCondition);
            builder.append(',');
            builder.append("iconGroup=");
            builder.append(this.iconGroup);
            builder.append(',');
            builder.append("activityIn=");
            builder.append(this.activityIn);
            builder.append(',');
            builder.append("activityOut=");
            builder.append(this.activityOut);
            builder.append(',');
            builder.append("rssi=");
            builder.append(this.rssi);
            builder.append(',');
            builder.append("lastModified=");
            builder.append(DateFormat.format("MM-dd HH:mm:ss", this.time));
        }

        public boolean equals(Object o) {
            if (!o.getClass().equals(getClass())) {
                return false;
            }
            State other = (State) o;
            return other.connected == this.connected && other.enabled == this.enabled && other.level == this.level && other.inetCondition == this.inetCondition && other.iconGroup == this.iconGroup && other.activityIn == this.activityIn && other.activityOut == this.activityOut && other.rssi == this.rssi;
        }
    }
}
