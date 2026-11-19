package com.android.systemui.shared.recents;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public interface IOverviewProxy extends IInterface {
    void onBind(ISystemUiProxy iSystemUiProxy) throws RemoteException;

    void onMotionEvent(MotionEvent motionEvent) throws RemoteException;

    void onOverviewHidden(boolean z, boolean z2) throws RemoteException;

    void onOverviewShown(boolean z) throws RemoteException;

    void onOverviewToggle() throws RemoteException;

    void onPreMotionEvent(int i) throws RemoteException;

    void onQuickScrubEnd() throws RemoteException;

    void onQuickScrubProgress(float f) throws RemoteException;

    void onQuickScrubStart() throws RemoteException;

    void onQuickStep(MotionEvent motionEvent) throws RemoteException;

    void onTip(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IOverviewProxy {
        public static IOverviewProxy asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.systemui.shared.recents.IOverviewProxy");
            if (iin != null && (iin instanceof IOverviewProxy)) {
                return (IOverviewProxy) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            MotionEvent _arg0;
            boolean _arg1;
            if (code == 1598968902) {
                reply.writeString("com.android.systemui.shared.recents.IOverviewProxy");
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onBind(ISystemUiProxy.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onPreMotionEvent(data.readInt());
                    return true;
                case 3:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    _arg0 = data.readInt() != 0 ? (MotionEvent) MotionEvent.CREATOR.createFromParcel(data) : null;
                    onMotionEvent(_arg0);
                    return true;
                case 4:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onQuickScrubStart();
                    return true;
                case 5:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onQuickScrubEnd();
                    return true;
                case 6:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onQuickScrubProgress(data.readFloat());
                    return true;
                case 7:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onOverviewToggle();
                    return true;
                case 8:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    _arg1 = data.readInt() != 0;
                    onOverviewShown(_arg1);
                    return true;
                case 9:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    boolean _arg2 = data.readInt() != 0;
                    _arg1 = data.readInt() != 0;
                    onOverviewHidden(_arg2, _arg1);
                    return true;
                case 10:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    _arg0 = data.readInt() != 0 ? (MotionEvent) MotionEvent.CREATOR.createFromParcel(data) : null;
                    onQuickStep(_arg0);
                    return true;
                case 11:
                    data.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    onTip(data.readInt(), data.readInt());
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOverviewProxy {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onBind(ISystemUiProxy sysUiProxy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    _data.writeStrongBinder(sysUiProxy != null ? sysUiProxy.asBinder() : null);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onPreMotionEvent(int downHitTarget) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    _data.writeInt(downHitTarget);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onMotionEvent(MotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onQuickScrubStart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onQuickScrubEnd() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onQuickScrubProgress(float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    _data.writeFloat(progress);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onOverviewToggle() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onOverviewShown(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onOverviewHidden(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onQuickStep(MotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public void onTip(int actionType, int viewType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    _data.writeInt(actionType);
                    _data.writeInt(viewType);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
