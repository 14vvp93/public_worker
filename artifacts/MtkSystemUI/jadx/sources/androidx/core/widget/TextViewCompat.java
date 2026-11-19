package androidx.core.widget;

import android.os.Build;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public final class TextViewCompat {
    public static void setTextAppearance(TextView textView, int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(resId);
        } else {
            textView.setTextAppearance(textView.getContext(), resId);
        }
    }
}
