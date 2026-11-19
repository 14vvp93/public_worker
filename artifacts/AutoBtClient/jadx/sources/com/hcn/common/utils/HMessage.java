package com.hcn.common.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class HMessage implements Parcelable {
    static final int FLAGS_TO_CLEAR_ON_COPY_FROM = 1;
    static final int FLAG_ASYNCHRONOUS = 2;
    static final int FLAG_IN_USE = 1;
    private static final int MAX_POOL_SIZE = 20;
    private static boolean gCheckRecycle = true;
    private static HMessage sPool;
    private static int sPoolSize;
    public int arg0;
    public int arg1;
    int flags;
    HMessage next;
    public Object obj0;
    public int what;
    public static final Object sPoolSync = new Object();
    public static final Parcelable.Creator<HMessage> CREATOR = new Parcelable.Creator<HMessage>() { // from class: com.hcn.common.utils.HMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HMessage createFromParcel(Parcel parcel) {
            HMessage hMessageObtain = HMessage.obtain();
            hMessageObtain.readFromParcel(parcel);
            return hMessageObtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HMessage[] newArray(int i) {
            return new HMessage[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HMessage() {
        this(-1);
    }

    public HMessage(int i) {
        this(i, -1, -1);
    }

    public HMessage(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public HMessage(int i, int i2, int i3, Object obj) {
        this.what = i;
        this.arg0 = i2;
        this.arg1 = i3;
        this.obj0 = obj;
    }

    public boolean isAsynchronous() {
        return (this.flags & 2) != 0;
    }

    public void setAsynchronous(boolean z) {
        if (z) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    boolean isInUse() {
        return (this.flags & 1) == 1;
    }

    void markInUse() {
        this.flags |= 1;
    }

    public static HMessage obtain() {
        synchronized (sPoolSync) {
            HMessage hMessage = sPool;
            if (hMessage != null) {
                sPool = hMessage.next;
                hMessage.next = null;
                hMessage.flags = 0;
                sPoolSize--;
                return hMessage;
            }
            return new HMessage();
        }
    }

    public static HMessage obtain(int i) {
        return obtain(i, -1, -1, null);
    }

    public static HMessage obtain(Object obj) {
        return obtain(-1, -1, -1, obj);
    }

    public static HMessage obtain(int i, int i2) {
        return obtain(i, i2, -1, null);
    }

    public static HMessage obtain(int i, Object obj) {
        return obtain(i, -1, -1, obj);
    }

    public static HMessage obtain(int i, int i2, int i3) {
        return obtain(i, i2, i3, null);
    }

    public static HMessage obtain(int i, int i2, Object obj) {
        return obtain(i, i2, -1, obj);
    }

    public static HMessage obtain(int i, int i2, int i3, Object obj) {
        HMessage hMessageObtain = obtain();
        hMessageObtain.what = i;
        hMessageObtain.arg0 = i2;
        hMessageObtain.arg1 = i3;
        hMessageObtain.obj0 = obj;
        return hMessageObtain;
    }

    public static HMessage obtain(HMessage hMessage) {
        HMessage hMessageObtain = obtain();
        hMessageObtain.what = hMessage.what;
        hMessageObtain.arg0 = hMessage.arg0;
        hMessageObtain.arg1 = hMessage.arg1;
        hMessageObtain.obj0 = hMessage.obj0;
        return hMessageObtain;
    }

    public HMessage setWhat(int i) {
        this.what = i;
        return this;
    }

    public void copyFrom(HMessage hMessage) {
        this.what = hMessage.what;
        this.arg0 = hMessage.arg0;
        this.arg1 = hMessage.arg1;
        this.obj0 = hMessage.obj0;
        this.flags = hMessage.flags & (-2);
    }

    public static void updateCheckRecycle(int i) {
        if (i < 21) {
            gCheckRecycle = false;
        }
    }

    public void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new IllegalStateException("This message cannot be recycled because it is still in use.");
            }
        } else {
            recycleUnchecked();
        }
    }

    void recycleUnchecked() {
        this.flags = 1;
        this.what = 0;
        this.arg0 = 0;
        this.arg1 = 0;
        this.obj0 = null;
        synchronized (sPoolSync) {
            int i = sPoolSize;
            if (i < 20) {
                this.next = sPool;
                sPool = this;
                sPoolSize = i + 1;
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.what);
        parcel.writeInt(this.arg0);
        parcel.writeInt(this.arg1);
        Object obj = this.obj0;
        if (obj != null) {
            try {
                parcel.writeInt(1);
                parcel.writeParcelable((Parcelable) obj, i);
                return;
            } catch (ClassCastException unused) {
                throw new RuntimeException("Can't marshal non-Parcelable objects across processes.");
            }
        }
        parcel.writeInt(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readFromParcel(Parcel parcel) {
        this.what = parcel.readInt();
        this.arg0 = parcel.readInt();
        this.arg1 = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.obj0 = parcel.readParcelable(getClass().getClassLoader());
        }
    }
}
