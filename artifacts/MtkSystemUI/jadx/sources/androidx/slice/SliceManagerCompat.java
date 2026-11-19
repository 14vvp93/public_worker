package androidx.slice;

import android.content.Context;
import android.net.Uri;
import androidx.slice.compat.SliceProviderCompat;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SliceManagerCompat extends SliceManager {
    private final Context mContext;

    SliceManagerCompat(Context context) {
        this.mContext = context;
    }

    @Override // androidx.slice.SliceManager
    public List<Uri> getPinnedSlices() {
        return SliceProviderCompat.getPinnedSlices(this.mContext);
    }
}
