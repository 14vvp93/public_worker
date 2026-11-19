package com.android.systemui.qs;

import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TouchAnimator {
    private static final FloatProperty<TouchAnimator> POSITION = new FloatProperty<TouchAnimator>("position") { // from class: com.android.systemui.qs.TouchAnimator.1
        @Override // android.util.FloatProperty
        public void setValue(TouchAnimator touchAnimator, float value) {
            touchAnimator.setPosition(value);
        }

        @Override // android.util.Property
        public Float get(TouchAnimator touchAnimator) {
            return Float.valueOf(touchAnimator.mLastT);
        }
    };
    private final float mEndDelay;
    private final Interpolator mInterpolator;
    private final KeyframeSet[] mKeyframeSets;
    private float mLastT;
    private final Listener mListener;
    private final float mSpan;
    private final float mStartDelay;
    private final Object[] mTargets;

    public interface Listener {
        void onAnimationAtEnd();

        void onAnimationAtStart();

        void onAnimationStarted();
    }

    private TouchAnimator(Object[] targets, KeyframeSet[] keyframeSets, float startDelay, float endDelay, Interpolator interpolator, Listener listener) {
        this.mLastT = -1.0f;
        this.mTargets = targets;
        this.mKeyframeSets = keyframeSets;
        this.mStartDelay = startDelay;
        this.mEndDelay = endDelay;
        this.mSpan = (1.0f - this.mEndDelay) - this.mStartDelay;
        this.mInterpolator = interpolator;
        this.mListener = listener;
    }

    public void setPosition(float fraction) {
        float t = MathUtils.constrain((fraction - this.mStartDelay) / this.mSpan, 0.0f, 1.0f);
        if (this.mInterpolator != null) {
            t = this.mInterpolator.getInterpolation(t);
        }
        if (t == this.mLastT) {
            return;
        }
        if (this.mListener != null) {
            if (t == 1.0f) {
                this.mListener.onAnimationAtEnd();
            } else if (t == 0.0f) {
                this.mListener.onAnimationAtStart();
            } else if (this.mLastT <= 0.0f || this.mLastT == 1.0f) {
                this.mListener.onAnimationStarted();
            }
            this.mLastT = t;
        }
        for (int i = 0; i < this.mTargets.length; i++) {
            this.mKeyframeSets[i].setValue(t, this.mTargets[i]);
        }
    }

    public static class ListenerAdapter implements Listener {
        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtStart() {
        }

        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtEnd() {
        }

        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationStarted() {
        }
    }

    public static class Builder {
        private float mEndDelay;
        private Interpolator mInterpolator;
        private Listener mListener;
        private float mStartDelay;
        private List<Object> mTargets = new ArrayList();
        private List<KeyframeSet> mValues = new ArrayList();

        public Builder addFloat(Object target, String property, float... values) {
            add(target, KeyframeSet.ofFloat(getProperty(target, property, Float.TYPE), values));
            return this;
        }

        private void add(Object target, KeyframeSet keyframeSet) {
            this.mTargets.add(target);
            this.mValues.add(keyframeSet);
        }

        private static Property getProperty(Object target, String property, Class<?> cls) {
            if (target instanceof View) {
                switch (property) {
                    case "translationX":
                        return View.TRANSLATION_X;
                    case "translationY":
                        return View.TRANSLATION_Y;
                    case "translationZ":
                        return View.TRANSLATION_Z;
                    case "alpha":
                        return View.ALPHA;
                    case "rotation":
                        return View.ROTATION;
                    case "x":
                        return View.X;
                    case "y":
                        return View.Y;
                    case "scaleX":
                        return View.SCALE_X;
                    case "scaleY":
                        return View.SCALE_Y;
                }
            }
            if ((target instanceof TouchAnimator) && "position".equals(property)) {
                return TouchAnimator.POSITION;
            }
            return Property.of(target.getClass(), cls, property);
        }

        public Builder setStartDelay(float startDelay) {
            this.mStartDelay = startDelay;
            return this;
        }

        public Builder setEndDelay(float endDelay) {
            this.mEndDelay = endDelay;
            return this;
        }

        public Builder setInterpolator(Interpolator intepolator) {
            this.mInterpolator = intepolator;
            return this;
        }

        public Builder setListener(Listener listener) {
            this.mListener = listener;
            return this;
        }

        public TouchAnimator build() {
            return new TouchAnimator(this.mTargets.toArray(new Object[this.mTargets.size()]), (KeyframeSet[]) this.mValues.toArray(new KeyframeSet[this.mValues.size()]), this.mStartDelay, this.mEndDelay, this.mInterpolator, this.mListener);
        }
    }

    private static abstract class KeyframeSet {
        private final float mFrameWidth;
        private final int mSize;

        protected abstract void interpolate(int i, float f, Object obj);

        public KeyframeSet(int size) {
            this.mSize = size;
            this.mFrameWidth = 1.0f / (size - 1);
        }

        void setValue(float fraction, Object target) {
            int i = MathUtils.constrain((int) Math.ceil(fraction / this.mFrameWidth), 1, this.mSize - 1);
            float amount = (fraction - (this.mFrameWidth * (i - 1))) / this.mFrameWidth;
            interpolate(i, amount, target);
        }

        public static KeyframeSet ofFloat(Property property, float... values) {
            return new FloatKeyframeSet(property, values);
        }
    }

    private static class FloatKeyframeSet<T> extends KeyframeSet {
        private final Property<T, Float> mProperty;
        private final float[] mValues;

        public FloatKeyframeSet(Property<T, Float> property, float[] values) {
            super(values.length);
            this.mProperty = property;
            this.mValues = values;
        }

        @Override // com.android.systemui.qs.TouchAnimator.KeyframeSet
        protected void interpolate(int index, float amount, Object target) {
            float firstFloat = this.mValues[index - 1];
            float secondFloat = this.mValues[index];
            this.mProperty.set(target, Float.valueOf(((secondFloat - firstFloat) * amount) + firstFloat));
        }
    }
}
