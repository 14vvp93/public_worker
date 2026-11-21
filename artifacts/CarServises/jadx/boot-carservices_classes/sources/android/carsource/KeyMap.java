package android.carsource;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class KeyMap implements Parcelable {
    public int kadc;
    public int kcode;
    public int longcode;
    public static final Parcelable.Creator<KeyMap> CREATOR = new Parcelable.Creator<KeyMap>() { // from class: android.carsource.KeyMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMap createFromParcel(Parcel in) {
            KeyMap k = new KeyMap();
            k.readFromParcel(in);
            return k;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMap[] newArray(int size) {
            return new KeyMap[size];
        }
    };

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.kadc);
        out.writeInt(this.kcode);
        out.writeInt(this.longcode);
    }

    public void readFromParcel(Parcel in) {
        this.kadc = in.readInt();
        this.kcode = in.readInt();
        this.longcode = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
