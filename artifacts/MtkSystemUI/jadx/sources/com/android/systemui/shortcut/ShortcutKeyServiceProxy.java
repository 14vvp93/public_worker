package com.android.systemui.shortcut;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.policy.IShortcutService;

/* JADX INFO: loaded from: classes.dex */
public class ShortcutKeyServiceProxy extends IShortcutService.Stub {
    private Callbacks mCallbacks;
    private final Object mLock = new Object();
    private final Handler mHandler = new H();

    public interface Callbacks {
        void onShortcutKeyPressed(long j);
    }

    public ShortcutKeyServiceProxy(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public void notifyShortcutKeyPressed(long shortcutCode) throws RemoteException {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1, Long.valueOf(shortcutCode)).sendToTarget();
        }
    }

    private final class H extends Handler {
        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 1) {
                ShortcutKeyServiceProxy.this.mCallbacks.onShortcutKeyPressed(((Long) msg.obj).longValue());
            }
        }
    }
}
