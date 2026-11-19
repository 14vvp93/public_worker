package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.KeyEvent;
import android.view.View;
import com.android.systemui.Interpolators;
import com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ButtonDispatcher {
    private View.AccessibilityDelegate mAccessibilityDelegate;
    private Float mAlpha;
    private View.OnClickListener mClickListener;
    private View mCurrentView;
    private Float mDarkIntensity;
    private Boolean mDelayTouchFeedback;
    private ValueAnimator mFadeAnimator;
    private final int mId;
    private KeyButtonDrawable mImageDrawable;
    private View.OnLongClickListener mLongClickListener;
    private Boolean mLongClickable;
    private View.OnHoverListener mOnHoverListener;
    private View.OnTouchListener mTouchListener;
    private boolean mVertical;
    private final ArrayList<View> mViews = new ArrayList<>();
    private Integer mVisibility = -1;
    private final ValueAnimator.AnimatorUpdateListener mAlphaListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ButtonDispatcher$YQ5xchhAskLzgLUT3UrgvCxrRAQ
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f$0.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    };
    private final AnimatorListenerAdapter mFadeListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ButtonDispatcher.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            ButtonDispatcher.this.setVisibility(ButtonDispatcher.this.getAlpha() == 1.0f ? 0 : 4);
        }
    };

    public ButtonDispatcher(int id) {
        this.mId = id;
    }

    void clear() {
        this.mViews.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void addView(View view) {
        this.mViews.add(view);
        view.setOnClickListener(this.mClickListener);
        view.setOnTouchListener(this.mTouchListener);
        view.setOnLongClickListener(this.mLongClickListener);
        view.setOnHoverListener(this.mOnHoverListener);
        if (this.mLongClickable != null) {
            view.setLongClickable(this.mLongClickable.booleanValue());
        }
        if (this.mAlpha != null) {
            view.setAlpha(this.mAlpha.floatValue());
        }
        if (this.mVisibility != null && this.mVisibility.intValue() != -1) {
            view.setVisibility(this.mVisibility.intValue());
        }
        if (this.mAccessibilityDelegate != null) {
            view.setAccessibilityDelegate(this.mAccessibilityDelegate);
        }
        if (view instanceof NavBarButtonProvider.ButtonInterface) {
            NavBarButtonProvider.ButtonInterface button = (NavBarButtonProvider.ButtonInterface) view;
            if (this.mDarkIntensity != null) {
                button.setDarkIntensity(this.mDarkIntensity.floatValue());
            }
            if (this.mImageDrawable != null) {
                button.setImageDrawable(this.mImageDrawable);
            }
            if (this.mDelayTouchFeedback != null) {
                button.setDelayTouchFeedback(this.mDelayTouchFeedback.booleanValue());
            }
            button.setVertical(this.mVertical);
        }
    }

    public int getVisibility() {
        if (this.mVisibility != null) {
            return this.mVisibility.intValue();
        }
        return 0;
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public float getAlpha() {
        if (this.mAlpha != null) {
            return this.mAlpha.floatValue();
        }
        return 1.0f;
    }

    public KeyButtonDrawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public void setImageDrawable(KeyButtonDrawable drawable) {
        this.mImageDrawable = drawable;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            if (this.mViews.get(i) instanceof NavBarButtonProvider.ButtonInterface) {
                ((NavBarButtonProvider.ButtonInterface) this.mViews.get(i)).setImageDrawable(this.mImageDrawable);
            }
        }
    }

    public void setVisibility(int visibility) {
        if (this.mVisibility.intValue() == visibility) {
            return;
        }
        this.mVisibility = Integer.valueOf(visibility);
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setVisibility(this.mVisibility.intValue());
        }
    }

    public void abortCurrentGesture() {
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            if (this.mViews.get(i) instanceof NavBarButtonProvider.ButtonInterface) {
                ((NavBarButtonProvider.ButtonInterface) this.mViews.get(i)).abortCurrentGesture();
            }
        }
    }

    public void setAlpha(float alpha) {
        setAlpha(alpha, false);
    }

    public void setAlpha(float alpha, boolean animate) {
        if (animate) {
            if (this.mFadeAnimator != null) {
                this.mFadeAnimator.cancel();
            }
            this.mFadeAnimator = ValueAnimator.ofFloat(getAlpha(), alpha);
            this.mFadeAnimator.setDuration(getAlpha() < alpha ? 150L : 100L);
            this.mFadeAnimator.setInterpolator(getAlpha() < alpha ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
            this.mFadeAnimator.addListener(this.mFadeListener);
            this.mFadeAnimator.addUpdateListener(this.mAlphaListener);
            this.mFadeAnimator.start();
            setVisibility(0);
            return;
        }
        this.mAlpha = Float.valueOf(alpha);
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setAlpha(alpha);
        }
    }

    public void setDarkIntensity(float darkIntensity) {
        this.mDarkIntensity = Float.valueOf(darkIntensity);
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            if (this.mViews.get(i) instanceof NavBarButtonProvider.ButtonInterface) {
                ((NavBarButtonProvider.ButtonInterface) this.mViews.get(i)).setDarkIntensity(darkIntensity);
            }
        }
    }

    public void setDelayTouchFeedback(boolean delay) {
        this.mDelayTouchFeedback = Boolean.valueOf(delay);
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            if (this.mViews.get(i) instanceof NavBarButtonProvider.ButtonInterface) {
                ((NavBarButtonProvider.ButtonInterface) this.mViews.get(i)).setDelayTouchFeedback(delay);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setOnClickListener(this.mClickListener);
        }
    }

    public void setOnTouchListener(View.OnTouchListener touchListener) {
        this.mTouchListener = touchListener;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setOnTouchListener(this.mTouchListener);
        }
    }

    public void setLongClickable(boolean isLongClickable) {
        this.mLongClickable = Boolean.valueOf(isLongClickable);
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public void setOnHoverListener(View.OnHoverListener hoverListener) {
        this.mOnHoverListener = hoverListener;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public void setAccessibilityDelegate(View.AccessibilityDelegate delegate) {
        this.mAccessibilityDelegate = delegate;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            this.mViews.get(i).setAccessibilityDelegate(delegate);
        }
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public void setCurrentView(View currentView) {
        this.mCurrentView = currentView.findViewById(this.mId);
    }

    public void setVertical(boolean vertical) {
        this.mVertical = vertical;
        int N = this.mViews.size();
        for (int i = 0; i < N; i++) {
            KeyEvent.Callback callback = (View) this.mViews.get(i);
            if (callback instanceof NavBarButtonProvider.ButtonInterface) {
                ((NavBarButtonProvider.ButtonInterface) callback).setVertical(vertical);
            }
        }
    }
}
