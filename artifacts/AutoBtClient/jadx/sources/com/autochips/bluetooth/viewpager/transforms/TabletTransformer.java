package com.autochips.bluetooth.viewpager.transforms;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class TabletTransformer extends ABaseTransformer {
    private static final Matrix OFFSET_MATRIX = new Matrix();
    private static final Camera OFFSET_CAMERA = new Camera();
    private static final float[] OFFSET_TEMP_FLOAT = new float[2];

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        float fAbs = (f < 0.0f ? 30.0f : -30.0f) * Math.abs(f);
        view.setTranslationX(getOffsetXForRotation(fAbs, view.getWidth(), view.getHeight()));
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(0.0f);
        view.setRotationY(fAbs);
    }

    protected static final float getOffsetXForRotation(float f, int i, int i2) {
        Matrix matrix = OFFSET_MATRIX;
        matrix.reset();
        Camera camera = OFFSET_CAMERA;
        camera.save();
        camera.rotateY(Math.abs(f));
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate((-i) * 0.5f, (-i2) * 0.5f);
        float f2 = i;
        float f3 = i2;
        matrix.postTranslate(f2 * 0.5f, 0.5f * f3);
        float[] fArr = OFFSET_TEMP_FLOAT;
        fArr[0] = f2;
        fArr[1] = f3;
        matrix.mapPoints(fArr);
        return (f2 - fArr[0]) * (f > 0.0f ? 1.0f : -1.0f);
    }
}
