package android.sourceservice;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class MusicInfo implements Parcelable {
    public String className;
    public int duration;
    public String fileName;
    public int position;
    public String state;
    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() { // from class: android.sourceservice.MusicInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicInfo createFromParcel(Parcel in) {
            MusicInfo k = new MusicInfo();
            k.readFromParcel(in);
            return k;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.className);
        out.writeString(this.fileName);
        out.writeString(this.state);
        out.writeInt(this.duration);
        out.writeInt(this.position);
    }

    public String toString() {
        return "MusicInfo{className='" + this.className + "', fileName='" + this.fileName + "', state='" + this.state + "', duration=" + this.duration + ", position=" + this.position + '}';
    }

    public void readFromParcel(Parcel in) {
        this.className = in.readString();
        this.fileName = in.readString();
        this.state = in.readString();
        this.duration = in.readInt();
        this.position = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
