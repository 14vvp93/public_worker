package com.android.systemui.recents;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IRecentsNonSystemUserCallbacks extends IInterface {
    void cancelPreloadingRecents() throws RemoteException;

    void hideRecents(boolean z, boolean z2) throws RemoteException;

    void onConfigurationChanged() throws RemoteException;

    void onDraggingInRecents(float f) throws RemoteException;

    void onDraggingInRecentsEnded(float f) throws RemoteException;

    void preloadRecents() throws RemoteException;

    void showCurrentUserToast(int i, int i2) throws RemoteException;

    void showRecents(boolean z, boolean z2, boolean z3, int i) throws RemoteException;

    void splitPrimaryTask(int i, int i2, int i3, Rect rect) throws RemoteException;

    void toggleRecents(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecentsNonSystemUserCallbacks {
        public Stub() {
            attachInterface(this, "com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
        }

        public static IRecentsNonSystemUserCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
            if (iin != null && (iin instanceof IRecentsNonSystemUserCallbacks)) {
                return (IRecentsNonSystemUserCallbacks) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg1;
            Rect _arg3;
            if (code == 1598968902) {
                reply.writeString("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    preloadRecents();
                    return true;
                case 2:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    cancelPreloadingRecents();
                    return true;
                case 3:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    boolean _arg0 = data.readInt() != 0;
                    boolean _arg2 = data.readInt() != 0;
                    _arg1 = data.readInt() != 0;
                    int _arg4 = data.readInt();
                    showRecents(_arg0, _arg2, _arg1, _arg4);
                    return true;
                case 4:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    boolean _arg5 = data.readInt() != 0;
                    _arg1 = data.readInt() != 0;
                    hideRecents(_arg5, _arg1);
                    return true;
                case 5:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    int _arg6 = data.readInt();
                    toggleRecents(_arg6);
                    return true;
                case 6:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    onConfigurationChanged();
                    return true;
                case 7:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    int _arg7 = data.readInt();
                    int _arg8 = data.readInt();
                    int _arg9 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg3 = (Rect) Rect.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    splitPrimaryTask(_arg7, _arg8, _arg9, _arg3);
                    return true;
                case 8:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    float _arg10 = data.readFloat();
                    onDraggingInRecents(_arg10);
                    return true;
                case 9:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    float _arg11 = data.readFloat();
                    onDraggingInRecentsEnded(_arg11);
                    return true;
                case 10:
                    data.enforceInterface("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    int _arg12 = data.readInt();
                    showCurrentUserToast(_arg12, data.readInt());
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecentsNonSystemUserCallbacks {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void preloadRecents() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void cancelPreloadingRecents() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void showRecents(boolean z, boolean z2, boolean z3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeInt(z3 ? 1 : 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void hideRecents(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void toggleRecents(int recentsGrowTarget) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    _data.writeInt(recentsGrowTarget);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void onConfigurationChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void splitPrimaryTask(int topTaskId, int dragMode, int stackCreateMode, Rect initialBounds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    _data.writeInt(topTaskId);
                    _data.writeInt(dragMode);
                    _data.writeInt(stackCreateMode);
                    if (initialBounds != null) {
                        _data.writeInt(1);
                        initialBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void onDraggingInRecents(float distanceFromTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    _data.writeFloat(distanceFromTop);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void onDraggingInRecentsEnded(float velocity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    _data.writeFloat(velocity);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.recents.IRecentsNonSystemUserCallbacks
            public void showCurrentUserToast(int msgResId, int msgLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.recents.IRecentsNonSystemUserCallbacks");
                    _data.writeInt(msgResId);
                    _data.writeInt(msgLength);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
