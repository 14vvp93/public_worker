package com.android.systemui.recents;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IRecentsSystemUserCallbacks extends IInterface {
    void registerNonSystemUserCallbacks(IBinder iBinder, int i) throws RemoteException;

    void sendDockedFirstAnimationFrameEvent() throws RemoteException;

    void sendDockingTopTaskEvent(int i, Rect rect) throws RemoteException;

    void sendLaunchRecentsEvent() throws RemoteException;

    void sendRecentsDrawnEvent() throws RemoteException;

    void setWaitingForTransitionStartEvent(boolean z) throws RemoteException;

    void startScreenPinning(int i) throws RemoteException;

    void updateRecentsVisibility(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecentsSystemUserCallbacks {
        public Stub() {
            attachInterface(this, "com.android.systemui.recents.IRecentsSystemUserCallbacks");
        }

        public static IRecentsSystemUserCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
            if (iin != null && (iin instanceof IRecentsSystemUserCallbacks)) {
                return (IRecentsSystemUserCallbacks) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0;
            Rect _arg1;
            if (code == 1598968902) {
                reply.writeString("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    IBinder _arg2 = data.readStrongBinder();
                    int _arg3 = data.readInt();
                    registerNonSystemUserCallbacks(_arg2, _arg3);
                    return true;
                case 2:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    _arg0 = data.readInt() != 0;
                    updateRecentsVisibility(_arg0);
                    return true;
                case 3:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    startScreenPinning(data.readInt());
                    return true;
                case 4:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    sendRecentsDrawnEvent();
                    return true;
                case 5:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    int _arg4 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = (Rect) Rect.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    sendDockingTopTaskEvent(_arg4, _arg1);
                    return true;
                case 6:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    sendLaunchRecentsEvent();
                    return true;
                case 7:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    sendDockedFirstAnimationFrameEvent();
                    return true;
                case 8:
                    data.enforceInterface("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    _arg0 = data.readInt() != 0;
                    setWaitingForTransitionStartEvent(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecentsSystemUserCallbacks {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void registerNonSystemUserCallbacks(IBinder nonSystemUserCallbacks, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    _data.writeStrongBinder(nonSystemUserCallbacks);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void updateRecentsVisibility(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void startScreenPinning(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    _data.writeInt(taskId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void sendRecentsDrawnEvent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void sendDockingTopTaskEvent(int dragMode, Rect initialRect) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    _data.writeInt(dragMode);
                    if (initialRect != null) {
                        _data.writeInt(1);
                        initialRect.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void sendLaunchRecentsEvent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void sendDockedFirstAnimationFrameEvent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsSystemUserCallbacks
            public void setWaitingForTransitionStartEvent(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.recents.IRecentsSystemUserCallbacks");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
