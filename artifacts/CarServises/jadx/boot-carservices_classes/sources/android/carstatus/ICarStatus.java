package android.carstatus;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public interface ICarStatus extends IInterface {
    int getAccStatus() throws RemoteException;

    boolean getHeadLightStatus() throws RemoteException;

    int getParkingStatus() throws RemoteException;

    int getReversing() throws RemoteException;

    int getTelMuteSatus() throws RemoteException;

    void setDashboardBrightness(int i) throws RemoteException;

    void setLed(int i, int i2, int i3, int i4) throws RemoteException;

    void setParkingStatus(int i) throws RemoteException;

    void setReversing(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarStatus {
        private static final String DESCRIPTOR = "android.carstatus.ICarStatus";
        static final int TRANSACTION_getAccStatus = 3;
        static final int TRANSACTION_getHeadLightStatus = 7;
        static final int TRANSACTION_getParkingStatus = 1;
        static final int TRANSACTION_getReversing = 2;
        static final int TRANSACTION_getTelMuteSatus = 9;
        static final int TRANSACTION_setDashboardBrightness = 6;
        static final int TRANSACTION_setLed = 8;
        static final int TRANSACTION_setParkingStatus = 5;
        static final int TRANSACTION_setReversing = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarStatus asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICarStatus)) {
                return (ICarStatus) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    int parkingStatus = getParkingStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(parkingStatus);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int reversing = getReversing();
                    parcel2.writeNoException();
                    parcel2.writeInt(reversing);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int accStatus = getAccStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(accStatus);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    setReversing(parcel.readInt());
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    setParkingStatus(parcel.readInt());
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    setDashboardBrightness(parcel.readInt());
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean headLightStatus = getHeadLightStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(headLightStatus ? 1 : 0);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    setLed(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    int telMuteSatus = getTelMuteSatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(telMuteSatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICarStatus {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.carstatus.ICarStatus
            public int getParkingStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public int getReversing() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public int getAccStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public void setReversing(int revsersing) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(revsersing);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public void setParkingStatus(int park) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(park);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public void setDashboardBrightness(int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public boolean getHeadLightStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public void setLed(int index, int red, int green, int blue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(index);
                    _data.writeInt(red);
                    _data.writeInt(green);
                    _data.writeInt(blue);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carstatus.ICarStatus
            public int getTelMuteSatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
