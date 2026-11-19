package androidx.versionedparcelable;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ParcelUtils {
    public static Parcelable toParcelable(VersionedParcelable obj) {
        return new ParcelImpl(obj);
    }

    public static <T extends VersionedParcelable> T fromParcelable(Parcelable parcelable) {
        if (!(parcelable instanceof ParcelImpl)) {
            throw new IllegalArgumentException("Invalid parcel");
        }
        return (T) ((ParcelImpl) parcelable).getVersionedParcel();
    }
}
