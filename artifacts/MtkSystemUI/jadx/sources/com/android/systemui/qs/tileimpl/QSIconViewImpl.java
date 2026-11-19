package com.android.systemui.qs.tileimpl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.AlphaControlledSignalTileView;
import com.android.systemui.util.ExternalParameterManager;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class QSIconViewImpl extends QSIconView {
    private boolean mAnimationEnabled;
    protected final View mIcon;
    protected final int mIconSizePx;
    private int mState;
    protected final int mTilePaddingBelowIconPx;
    private int mTint;

    public QSIconViewImpl(Context context) {
        super(context);
        this.mAnimationEnabled = true;
        this.mState = -1;
        Resources res = context.getResources();
        this.mIconSizePx = res.getDimensionPixelSize(R.dimen.qs_tile_icon_size);
        this.mTilePaddingBelowIconPx = res.getDimensionPixelSize(R.dimen.qs_tile_padding_below_icon);
        this.mIcon = createIcon();
        addView(this.mIcon);
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public void disableAnimation() {
        this.mAnimationEnabled = false;
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public View getIconView() {
        return this.mIcon;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = View.MeasureSpec.getSize(widthMeasureSpec);
        int iconSpec = exactly(this.mIconSizePx);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(w, getIconMeasureMode()), iconSpec);
        setMeasuredDimension(w, this.mIcon.getMeasuredHeight() + this.mTilePaddingBelowIconPx);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int w = getMeasuredWidth();
        int iconLeft = (w - this.mIcon.getMeasuredWidth()) / 2;
        layout(this.mIcon, iconLeft, 0);
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public void setIcon(QSTile.State state) {
        setIcon((ImageView) this.mIcon, state);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateIcon(ImageView iv, QSTile.State state) {
        Drawable d;
        QSTile.Icon icon = state.iconSupplier != null ? state.iconSupplier.get() : state.icon;
        if (!Objects.equals(icon, iv.getTag(R.id.qs_icon_tag)) || !Objects.equals(state.slash, iv.getTag(R.id.qs_slash_tag))) {
            boolean shouldAnimate = iv.isShown() && this.mAnimationEnabled && iv.getDrawable() != null;
            if (icon == null) {
                d = null;
            } else {
                d = shouldAnimate ? icon.getDrawable(this.mContext) : icon.getInvisibleDrawable(this.mContext);
            }
            int padding = icon != null ? icon.getPadding() : 0;
            if (d != null) {
                d.setAutoMirrored(false);
                d.setLayoutDirection(getLayoutDirection());
            }
            if (iv instanceof SlashImageView) {
                ((SlashImageView) iv).setAnimationEnabled(shouldAnimate);
                ((SlashImageView) iv).setState(null, d);
            } else {
                iv.setImageDrawable(d);
            }
            iv.setTag(R.id.qs_icon_tag, icon);
            iv.setTag(R.id.qs_slash_tag, state.slash);
            iv.setPadding(0, padding, 0, padding);
            if (d instanceof Animatable2) {
                final Animatable2 a = (Animatable2) d;
                a.start();
                if (state.isTransient) {
                    a.registerAnimationCallback(new Animatable2.AnimationCallback() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.1
                        @Override // android.graphics.drawable.Animatable2.AnimationCallback
                        public void onAnimationEnd(Drawable drawable) {
                            a.start();
                        }
                    });
                }
            }
        }
    }

    protected void setIcon(final ImageView iv, final QSTile.State state) {
        if (state.disabledByPolicy) {
            iv.setColorFilter(getContext().getColor(R.color.qs_tile_disabled_color));
        } else {
            iv.clearColorFilter();
        }
        if (state.state != this.mState) {
            int color = getColor(state.state);
            this.mState = state.state;
            if (iv.isShown() && this.mTint != 0) {
                animateGrayScale(this.mTint, color, iv, new Runnable() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSIconViewImpl$J1UpdvvSiFAmzZuy0PTr_V3YTn0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.updateIcon(iv, state);
                    }
                });
                this.mTint = color;
                return;
            }
            if (iv instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
                ((AlphaControlledSignalTileView.AlphaControlledSlashImageView) iv).setFinalImageTintList(ColorStateList.valueOf(color));
            } else {
                setTint(iv, color);
            }
            this.mTint = color;
            updateIcon(iv, state);
            return;
        }
        updateIcon(iv, state);
    }

    protected int getColor(int state) {
        return QSTileImpl.getColorForState(getContext(), state);
    }

    private void animateGrayScale(int fromColor, int toColor, final ImageView iv, final Runnable endRunnable) {
        if (iv instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
            ((AlphaControlledSignalTileView.AlphaControlledSlashImageView) iv).setFinalImageTintList(ColorStateList.valueOf(toColor));
        }
        if (this.mAnimationEnabled && ValueAnimator.areAnimatorsEnabled()) {
            final float fromAlpha = Color.alpha(fromColor);
            final float toAlpha = Color.alpha(toColor);
            final float fromChannel = Color.red(fromColor);
            final float toChannel = Color.red(toColor);
            ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
            anim.setDuration(350L);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSIconViewImpl$CeqSBPdIhNYTow_6QM6a9ZwQyb8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    QSIconViewImpl.lambda$animateGrayScale$1(fromAlpha, toAlpha, fromChannel, toChannel, iv, valueAnimator);
                }
            });
            anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    endRunnable.run();
                }
            });
            anim.start();
            return;
        }
        setTint(iv, toColor);
        endRunnable.run();
    }

    static /* synthetic */ void lambda$animateGrayScale$1(float fromAlpha, float toAlpha, float fromChannel, float toChannel, ImageView iv, ValueAnimator animation) {
        float fraction = animation.getAnimatedFraction();
        int alpha = (int) (((toAlpha - fromAlpha) * fraction) + fromAlpha);
        int channel = (int) (((toChannel - fromChannel) * fraction) + fromChannel);
        setTint(iv, Color.argb(alpha, channel, channel, channel));
    }

    public static void setTint(ImageView iv, int color) {
        if ((ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_16 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB)) && ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB) {
            iv.setImageTintList(ColorStateList.valueOf(color));
        }
    }

    protected int getIconMeasureMode() {
        return 1073741824;
    }

    protected View createIcon() {
        ImageView icon = new SlashImageView(this.mContext);
        icon.setId(android.R.id.icon);
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return icon;
    }

    protected final int exactly(int size) {
        return View.MeasureSpec.makeMeasureSpec(size, 1073741824);
    }

    protected final void layout(View child, int left, int top) {
        child.layout(left, top, child.getMeasuredWidth() + left, child.getMeasuredHeight() + top);
    }
}
