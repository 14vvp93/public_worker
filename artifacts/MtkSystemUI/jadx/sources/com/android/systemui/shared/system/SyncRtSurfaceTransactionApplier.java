package com.android.systemui.shared.system;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

/* JADX INFO: loaded from: classes.dex */
public class SyncRtSurfaceTransactionApplier {
    private final Surface mTargetSurface;
    private final ViewRootImpl mTargetViewRootImpl;
    private final float[] mTmpFloat9 = new float[9];

    public SyncRtSurfaceTransactionApplier(View targetView) {
        this.mTargetViewRootImpl = targetView != null ? targetView.getViewRootImpl() : null;
        this.mTargetSurface = this.mTargetViewRootImpl != null ? this.mTargetViewRootImpl.mSurface : null;
    }

    public void scheduleApply(final SurfaceParams... params) {
        if (this.mTargetViewRootImpl == null) {
            return;
        }
        this.mTargetViewRootImpl.registerRtFrameCallback(new ThreadedRenderer.FrameDrawingCallback() { // from class: com.android.systemui.shared.system.-$$Lambda$SyncRtSurfaceTransactionApplier$odiZXJPwVQjUmyGhNuNxcMPZIOM
            public final void onFrameDraw(long j) {
                SyncRtSurfaceTransactionApplier.lambda$scheduleApply$0(this.f$0, params, j);
            }
        });
        this.mTargetViewRootImpl.getView().invalidate();
    }

    public static /* synthetic */ void lambda$scheduleApply$0(SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, SurfaceParams[] params, long frame) {
        if (syncRtSurfaceTransactionApplier.mTargetSurface == null || !syncRtSurfaceTransactionApplier.mTargetSurface.isValid()) {
            return;
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        for (int i = params.length - 1; i >= 0; i--) {
            SurfaceParams surfaceParams = params[i];
            SurfaceControl surface = surfaceParams.surface;
            t.deferTransactionUntilSurface(surface, syncRtSurfaceTransactionApplier.mTargetSurface, frame);
            applyParams(t, surfaceParams, syncRtSurfaceTransactionApplier.mTmpFloat9);
        }
        t.setEarlyWakeup();
        t.apply();
    }

    private static void applyParams(SurfaceControl.Transaction t, SurfaceParams params, float[] tmpFloat9) {
        t.setMatrix(params.surface, params.matrix, tmpFloat9);
        t.setWindowCrop(params.surface, params.windowCrop);
        t.setAlpha(params.surface, params.alpha);
        t.setLayer(params.surface, params.layer);
        t.show(params.surface);
    }

    public static class SurfaceParams {
        final float alpha;
        final int layer;
        final Matrix matrix;
        final SurfaceControl surface;
        final Rect windowCrop;

        public SurfaceParams(SurfaceControlCompat surface, float alpha, Matrix matrix, Rect windowCrop, int layer) {
            this.surface = surface.mSurfaceControl;
            this.alpha = alpha;
            this.matrix = new Matrix(matrix);
            this.windowCrop = new Rect(windowCrop);
            this.layer = layer;
        }
    }
}
