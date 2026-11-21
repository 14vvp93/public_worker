package android.radio;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class RadioInfo implements Parcelable {
    public int mFreq;
    public int mIndexof;
    public boolean mIsLocal;
    public boolean mIsStereo;
    public String mPSname;
    public String mPTYtype;
    public String mRDSstate;
    public String mRTtype;
    public int mScanType;
    public String mUiBand;
    public static final Parcelable.Creator<RadioInfo> CREATOR = new Parcelable.Creator<RadioInfo>() { // from class: android.radio.RadioInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioInfo createFromParcel(Parcel in) {
            RadioInfo k = new RadioInfo();
            k.readFromParcel(in);
            return k;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioInfo[] newArray(int size) {
            return new RadioInfo[size];
        }
    };

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mFreq);
        out.writeInt(this.mScanType);
        out.writeBoolean(this.mIsStereo);
        out.writeBoolean(this.mIsLocal);
        out.writeString(this.mUiBand);
        out.writeInt(this.mIndexof);
        out.writeString(this.mPSname);
        out.writeString(this.mRTtype);
        out.writeString(this.mRDSstate);
        out.writeString(this.mPTYtype);
    }

    public String toString() {
        return "RadioInfo{mFreq=" + this.mFreq + ", mScanType=" + this.mScanType + ", mIsStereo=" + this.mIsStereo + ", mIsLocal=" + this.mIsLocal + ", mUiBand=" + this.mUiBand + ", mIndexof='" + this.mIndexof + "'}";
    }

    public void readFromParcel(Parcel in) {
        this.mFreq = in.readInt();
        this.mScanType = in.readInt();
        this.mIsStereo = in.readBoolean();
        this.mIsLocal = in.readBoolean();
        this.mUiBand = in.readString();
        this.mIndexof = in.readInt();
        this.mPSname = in.readString();
        this.mRTtype = in.readString();
        this.mRDSstate = in.readString();
        this.mPTYtype = in.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
