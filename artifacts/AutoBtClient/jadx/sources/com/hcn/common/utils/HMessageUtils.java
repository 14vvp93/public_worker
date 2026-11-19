package com.hcn.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.hcn.skin.support.utils.SkinListUtils;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class HMessageUtils implements Handler.Callback {
    private static final String TAG = "UiMessageUtils";
    private final List<HMessageCallback> mDefensiveCopyList;
    private final Handler mHandler;
    private final SparseArray<List<HMessageCallback>> mListenersSpecific;
    private final List<HMessageCallback> mListenersUniversal;
    private final UiMessage mMessage;
    private final String mName;
    private static final boolean DEBUG = HUtilsEx.isAppDebug();
    private static final ConcurrentHashMap<String, HMessageUtils> sPool = new ConcurrentHashMap<>();

    public interface HMessageCallback {
        void handleMessage(UiMessage uiMessage);
    }

    public static HMessageUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private HMessageUtils(String str) {
        this.mHandler = new Handler(Looper.getMainLooper(), this);
        this.mMessage = new UiMessage(null);
        this.mListenersSpecific = new SparseArray<>();
        this.mListenersUniversal = new ArrayList();
        this.mDefensiveCopyList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            throw new InvalidParameterException("HMessageUtils must have a name!");
        }
        this.mName = str;
    }

    public String name() {
        return this.mName;
    }

    public static final class Builder {
        String name = null;
        boolean supportGlobalUsage = false;

        public Builder setName(String str) {
            this.name = str;
            return this;
        }

        public Builder setSupportGlobalUsage(boolean z) {
            this.supportGlobalUsage = z;
            return this;
        }

        public HMessageUtils build() {
            if (TextUtils.isEmpty(this.name)) {
                throw new InvalidParameterException("HMessageUtils must have a name!");
            }
            HMessageUtils hMessageUtils = (HMessageUtils) HMessageUtils.sPool.get(this.name);
            if (hMessageUtils == null) {
                hMessageUtils = new HMessageUtils(this.name);
                if (this.supportGlobalUsage) {
                    HMessageUtils.sPool.put(hMessageUtils.name(), hMessageUtils);
                }
            }
            return hMessageUtils;
        }
    }

    public void release() {
        sPool.remove(this.mName);
    }

    public final void send(int i) {
        this.mHandler.sendEmptyMessage(i);
    }

    public final void sendUnique(int i) {
        this.mHandler.removeMessages(i);
        this.mHandler.sendEmptyMessage(i);
    }

    public final void send(int i, long j) {
        this.mHandler.sendEmptyMessageDelayed(i, j);
    }

    public final void sendUnique(int i, long j) {
        this.mHandler.removeMessages(i);
        this.mHandler.sendEmptyMessageDelayed(i, j);
    }

    public final void send(int i, Object obj) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(i, obj));
    }

    public final void sendUnique(int i, Object obj) {
        this.mHandler.removeMessages(i);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(i, obj));
    }

    public final void send(int i, Object obj, long j) {
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(i, obj), j);
    }

    public final void sendUnique(int i, Object obj, long j) {
        this.mHandler.removeMessages(i);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(i, obj), j);
    }

    public final void remove(int i) {
        this.mHandler.removeMessages(i);
    }

    public final void clearMessageQueue() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void addListener(int i, HMessageCallback hMessageCallback) {
        synchronized (this.mListenersSpecific) {
            List<HMessageCallback> arrayList = this.mListenersSpecific.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.mListenersSpecific.put(i, arrayList);
            }
            if (!arrayList.contains(hMessageCallback)) {
                arrayList.add(hMessageCallback);
            }
        }
    }

    public void addListener(HMessageCallback hMessageCallback) {
        synchronized (this.mListenersUniversal) {
            if (!this.mListenersUniversal.contains(hMessageCallback)) {
                this.mListenersUniversal.add(hMessageCallback);
            } else if (DEBUG) {
                Log.w(TAG, "Listener is already added. " + hMessageCallback.toString());
            }
        }
    }

    public void removeListener(HMessageCallback hMessageCallback) {
        synchronized (this.mListenersUniversal) {
            if (DEBUG && !this.mListenersUniversal.contains(hMessageCallback)) {
                Log.w(TAG, "Trying to remove a listener that is not registered. " + hMessageCallback.toString());
            }
            this.mListenersUniversal.remove(hMessageCallback);
        }
    }

    public void removeListeners(int i) {
        List<HMessageCallback> list;
        if (DEBUG && ((list = this.mListenersSpecific.get(i)) == null || list.size() == 0)) {
            Log.w(TAG, "Trying to remove specific listeners that are not registered. ID " + i);
        }
        synchronized (this.mListenersSpecific) {
            this.mListenersSpecific.delete(i);
        }
    }

    public void removeListener(int i, HMessageCallback hMessageCallback) {
        synchronized (this.mListenersSpecific) {
            List<HMessageCallback> list = this.mListenersSpecific.get(i);
            if (list != null && !list.isEmpty()) {
                if (DEBUG && !list.contains(hMessageCallback)) {
                    Log.w(TAG, "Trying to remove specific listener that is not registered. ID " + i + ", " + hMessageCallback);
                    return;
                }
                list.remove(hMessageCallback);
            } else if (DEBUG) {
                Log.w(TAG, "Trying to remove specific listener that is not registered. ID " + i + ", " + hMessageCallback);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        this.mMessage.setMessage(message);
        if (DEBUG) {
            logMessageHandling(this.mMessage);
        }
        synchronized (this.mListenersSpecific) {
            List<HMessageCallback> list = this.mListenersSpecific.get(message.what);
            if (list != null) {
                if (list.size() == 0) {
                    this.mListenersSpecific.remove(message.what);
                } else {
                    this.mDefensiveCopyList.addAll(list);
                    Iterator<HMessageCallback> it = this.mDefensiveCopyList.iterator();
                    while (it.hasNext()) {
                        it.next().handleMessage(this.mMessage);
                    }
                    this.mDefensiveCopyList.clear();
                }
            }
        }
        synchronized (this.mListenersUniversal) {
            if (this.mListenersUniversal.size() > 0) {
                this.mDefensiveCopyList.addAll(this.mListenersUniversal);
                Iterator<HMessageCallback> it2 = this.mDefensiveCopyList.iterator();
                while (it2.hasNext()) {
                    it2.next().handleMessage(this.mMessage);
                }
                this.mDefensiveCopyList.clear();
            }
        }
        this.mMessage.setMessage(null);
        return true;
    }

    private void logMessageHandling(UiMessage uiMessage) {
        List<HMessageCallback> list = this.mListenersSpecific.get(uiMessage.getId());
        if ((list == null || list.isEmpty()) && this.mListenersUniversal.size() == 0) {
            Log.w(TAG, "Delivering FAILED for message ID " + uiMessage.getId() + ". No listeners. " + uiMessage.toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Delivering message ID ");
        sb.append(uiMessage.getId());
        sb.append(", Specific listeners: ");
        if (list == null || list.size() == 0) {
            sb.append(0);
        } else {
            sb.append(list.size());
            sb.append(" [");
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getClass().getSimpleName());
                if (i < list.size() - 1) {
                    sb.append(SkinListUtils.DEFAULT_JOIN_SEPARATOR);
                }
            }
            sb.append("]");
        }
        sb.append(", Universal listeners: ");
        synchronized (this.mListenersUniversal) {
            if (this.mListenersUniversal.size() == 0) {
                sb.append(0);
            } else {
                sb.append(this.mListenersUniversal.size());
                sb.append(" [");
                for (int i2 = 0; i2 < this.mListenersUniversal.size(); i2++) {
                    sb.append(this.mListenersUniversal.get(i2).getClass().getSimpleName());
                    if (i2 < this.mListenersUniversal.size() - 1) {
                        sb.append(SkinListUtils.DEFAULT_JOIN_SEPARATOR);
                    }
                }
                sb.append("], Message: ");
            }
        }
        sb.append(uiMessage.toString());
        Log.v(TAG, sb.toString());
    }

    public static final class UiMessage {
        private Message mMessage;

        private UiMessage(Message message) {
            this.mMessage = message;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMessage(Message message) {
            this.mMessage = message;
        }

        public int getId() {
            return this.mMessage.what;
        }

        public Object getObject() {
            return this.mMessage.obj;
        }

        public String toString() {
            return "{ id=" + getId() + ", obj=" + getObject() + " }";
        }
    }

    private static final class LazyHolder {
        private static final HMessageUtils INSTANCE;

        private LazyHolder() {
        }

        static {
            HMessageUtils hMessageUtils = new HMessageUtils("LazyHolder");
            INSTANCE = hMessageUtils;
            HMessageUtils.sPool.put(hMessageUtils.name(), hMessageUtils);
        }
    }
}
