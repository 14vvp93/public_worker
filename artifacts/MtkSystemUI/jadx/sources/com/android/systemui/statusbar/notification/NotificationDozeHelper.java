package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationDozeHelper {
    private final ColorMatrix mGrayscaleColorMatrix = new ColorMatrix();

    public void updateGrayscale(ImageView target, float darkAmount) {
        if (darkAmount > 0.0f) {
            updateGrayscaleMatrix(darkAmount);
            target.setColorFilter(new ColorMatrixColorFilter(this.mGrayscaleColorMatrix));
        } else {
            target.setColorFilter((ColorFilter) null);
        }
    }

    public void startIntensityAnimation(ValueAnimator.AnimatorUpdateListener updateListener, boolean dark, long delay, Animator.AnimatorListener listener) {
        float startIntensity = dark ? 0.0f : 1.0f;
        float endIntensity = dark ? 1.0f : 0.0f;
        ValueAnimator animator = ValueAnimator.ofFloat(startIntensity, endIntensity);
        animator.addUpdateListener(updateListener);
        animator.setDuration(700L);
        animator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        animator.setStartDelay(delay);
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    public void setIntensityDark(final Consumer<Float> listener, boolean dark, boolean animate, long delay, final View view) {
        if (animate) {
            startIntensityAnimation(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationDozeHelper$cAhupcRGr8viAUyxaXt90IAG2is
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    listener.accept((Float) valueAnimator.getAnimatedValue());
                }
            }, dark, delay, new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.NotificationDozeHelper.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    view.setTag(R.id.doze_intensity_tag, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    view.setTag(R.id.doze_intensity_tag, animation);
                }
            });
            return;
        }
        Animator animator = (Animator) view.getTag(R.id.doze_intensity_tag);
        if (animator != null) {
            animator.cancel();
        }
        listener.accept(Float.valueOf(dark ? 1.0f : 0.0f));
    }

    public void updateGrayscaleMatrix(float intensity) {
        this.mGrayscaleColorMatrix.setSaturation(1.0f - intensity);
    }
}
