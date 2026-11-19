package kotlin;

import com.autochips.bluetooth.skin.SkinID;

/* JADX INFO: compiled from: HashCode.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"}, m62d2 = {"hashCode", SkinID.SKIN_NONE, SkinID.SKIN_NONE, "kotlin-stdlib"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class HashCodeKt {
    private static final int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }
}
