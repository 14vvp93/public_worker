package com.android.systemui.recents;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.os.SomeArgs;

/* JADX INFO: loaded from: classes.dex */
public class RecentsImplProxy extends IRecentsNonSystemUserCallbacks.Stub {
    private final Handler mHandler = new Handler() { // from class: com.android.systemui.recents.RecentsImplProxy.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RecentsImplProxy.this.mImpl.preloadRecents();
                    break;
                case 2:
                    RecentsImplProxy.this.mImpl.cancelPreloadingRecents();
                    break;
                case 3:
                    SomeArgs args = (SomeArgs) msg.obj;
                    RecentsImplProxy.this.mImpl.showRecents(args.argi1 != 0, args.argi2 != 0, args.argi3 != 0, args.argi4);
                    break;
                case 4:
                    RecentsImplProxy.this.mImpl.hideRecents(msg.arg1 != 0, msg.arg2 != 0);
                    break;
                case 5:
                    RecentsImplProxy.this.mImpl.toggleRecents(((SomeArgs) msg.obj).argi1);
                    break;
                case 6:
                    RecentsImplProxy.this.mImpl.onConfigurationChanged();
                    break;
                case 7:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    RecentsImpl recentsImpl = RecentsImplProxy.this.mImpl;
                    int i = args2.argi1;
                    int i2 = args2.argi2;
                    args2.argi3 = 0;
                    recentsImpl.splitPrimaryTask(i, i2, 0, (Rect) args2.arg1);
                    break;
                case 8:
                    RecentsImplProxy.this.mImpl.onDraggingInRecents(((Float) msg.obj).floatValue());
                    break;
                case 9:
                    RecentsImplProxy.this.mImpl.onDraggingInRecentsEnded(((Float) msg.obj).floatValue());
                    break;
                case 10:
                    RecentsImplProxy.this.mImpl.onShowCurrentUserToast(msg.arg1, msg.arg2);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private RecentsImpl mImpl;

    public RecentsImplProxy(RecentsImpl recentsImpl) {
        this.mImpl = recentsImpl;
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void preloadRecents() throws RemoteException {
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void cancelPreloadingRecents() throws RemoteException {
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void showRecents(boolean z, boolean z2, boolean z3, int i) throws RemoteException {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = z ? 1 : 0;
        someArgsObtain.argi2 = z2 ? 1 : 0;
        someArgsObtain.argi3 = z3 ? 1 : 0;
        someArgsObtain.argi4 = i;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, someArgsObtain));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void hideRecents(boolean z, boolean z2) throws RemoteException {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, z ? 1 : 0, z2 ? 1 : 0));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void toggleRecents(int growTarget) throws RemoteException {
        SomeArgs args = SomeArgs.obtain();
        args.argi1 = growTarget;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, args));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void onConfigurationChanged() throws RemoteException {
        this.mHandler.sendEmptyMessage(6);
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void splitPrimaryTask(int topTaskId, int dragMode, int stackCreateMode, Rect initialBounds) throws RemoteException {
        SomeArgs args = SomeArgs.obtain();
        args.argi1 = topTaskId;
        args.argi2 = dragMode;
        args.argi3 = stackCreateMode;
        args.arg1 = initialBounds;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, args));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void onDraggingInRecents(float distanceFromTop) throws RemoteException {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(8, Float.valueOf(distanceFromTop)));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void onDraggingInRecentsEnded(float velocity) throws RemoteException {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, Float.valueOf(velocity)));
    }

    @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
    public void showCurrentUserToast(int msgResId, int msgLength) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10, msgResId, msgLength));
    }
}
