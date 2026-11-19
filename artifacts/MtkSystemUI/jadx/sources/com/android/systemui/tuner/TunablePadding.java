package com.android.systemui.tuner;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.Dependency;

/* JADX INFO: loaded from: classes.dex */
public class TunablePadding implements TunerService.Tunable {
    private final int mDefaultSize;
    private final float mDensity;
    private final int mFlags;
    private final View mView;

    private TunablePadding(String key, int def, int flags, View view) {
        this.mDefaultSize = def;
        this.mFlags = flags;
        this.mView = view;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) view.getContext().getSystemService(WindowManager.class)).getDefaultDisplay().getMetrics(metrics);
        this.mDensity = metrics.density;
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, key);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        int dimen = this.mDefaultSize;
        if (newValue != null) {
            dimen = (int) (Integer.parseInt(newValue) * this.mDensity);
        }
        int left = this.mView.isLayoutRtl() ? 2 : 1;
        int right = this.mView.isLayoutRtl() ? 1 : 2;
        this.mView.setPadding(getPadding(dimen, left), getPadding(dimen, 4), getPadding(dimen, right), getPadding(dimen, 8));
    }

    private int getPadding(int dimen, int flag) {
        if ((this.mFlags & flag) != 0) {
            return dimen;
        }
        return 0;
    }

    public void destroy() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
    }

    public static class TunablePaddingService {
        public TunablePadding add(View view, String key, int defaultSize, int flags) {
            if (view == null) {
                throw new IllegalArgumentException();
            }
            return new TunablePadding(key, defaultSize, flags, view);
        }
    }

    public static TunablePadding addTunablePadding(View view, String key, int defaultSize, int flags) {
        return ((TunablePaddingService) Dependency.get(TunablePaddingService.class)).add(view, key, defaultSize, flags);
    }
}
