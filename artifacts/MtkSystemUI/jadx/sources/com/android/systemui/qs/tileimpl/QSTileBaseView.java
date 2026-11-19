package com.android.systemui.qs.tileimpl;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.util.ExternalParameterManager;

/* JADX INFO: loaded from: classes.dex */
public class QSTileBaseView extends com.android.systemui.plugins.qs.QSTileView {
    private String mAccessibilityClass;
    private final ImageView mBg;
    private int mCircleColor;
    private boolean mClicked;
    private boolean mCollapsedView;
    private final int mColorActive;
    private final int mColorDisabled;
    private final int mColorInactive;
    private final H mHandler;
    protected QSIconView mIcon;
    private final FrameLayout mIconFrame;
    protected RippleDrawable mRipple;
    private Drawable mTileBackground;
    private boolean mTileState;

    public QSTileBaseView(Context context, QSIconView icon, boolean collapsedView) {
        super(context);
        this.mHandler = new H();
        int padding = context.getResources().getDimensionPixelSize(R.dimen.qs_quick_tile_padding);
        this.mIconFrame = new FrameLayout(context);
        this.mIconFrame.setForegroundGravity(17);
        int size = context.getResources().getDimensionPixelSize(R.dimen.qs_quick_tile_size);
        addView(this.mIconFrame, new LinearLayout.LayoutParams(size, size));
        this.mBg = new ImageView(getContext());
        this.mBg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mBg.setImageResource(R.drawable.ic_qs_circle);
        if (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_16 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB)) {
            this.mIconFrame.addView(this.mBg);
        }
        this.mIcon = icon;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.setMargins(0, padding, 0, padding);
        this.mIconFrame.addView(this.mIcon, params);
        this.mIconFrame.setClipChildren(false);
        this.mIconFrame.setClipToPadding(false);
        this.mTileBackground = newTileBackground();
        if ((this.mTileBackground instanceof RippleDrawable) && (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_16 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB))) {
            setRipple((RippleDrawable) this.mTileBackground);
        }
        setImportantForAccessibility(1);
        if (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_16 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB)) {
            setBackground(this.mTileBackground);
        }
        this.mColorActive = Utils.getColorAttr(context, android.R.attr.colorAccent);
        this.mColorDisabled = Utils.getDisabled(context, Utils.getColorAttr(context, android.R.attr.textColorTertiary));
        this.mColorInactive = Utils.getColorAttr(context, android.R.attr.textColorSecondary);
        setPadding(0, 0, 0, 0);
        setClipChildren(false);
        setClipToPadding(false);
        this.mCollapsedView = collapsedView;
        setFocusable(true);
    }

    protected Drawable newTileBackground() {
        int[] attrs = {android.R.attr.selectableItemBackgroundBorderless};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        Drawable d = ta.getDrawable(0);
        ta.recycle();
        return d;
    }

    private void setRipple(RippleDrawable tileBackground) {
        this.mRipple = tileBackground;
        if (getWidth() != 0) {
            updateRippleSize();
        }
    }

    private void updateRippleSize() {
        int cx = (this.mIconFrame.getMeasuredWidth() / 2) + this.mIconFrame.getLeft();
        int cy = (this.mIconFrame.getMeasuredHeight() / 2) + this.mIconFrame.getTop();
        int rad = (int) (this.mIcon.getHeight() * 0.85f);
        this.mRipple.setHotspotBounds(cx - rad, cy - rad, cx + rad, cy + rad);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public void init(final QSTile tile) {
        init(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSTileBaseView$aVxKNvlJE7IFS8nVmOyLdAcByFA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                tile.click();
            }
        }, new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSTileBaseView$W9w1scJAVZm5V6Q1VB4ZO5o3C8A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                tile.secondaryClick();
            }
        }, new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSTileBaseView$STEfvGmwtIL_pMrVYwBQuK3x1jo
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return QSTileBaseView.lambda$init$2(tile, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$init$2(QSTile tile, View view) {
        tile.longClick();
        return true;
    }

    public void init(View.OnClickListener click, View.OnClickListener secondaryClick, View.OnLongClickListener longClick) {
        setOnClickListener(click);
        setOnLongClickListener(longClick);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mRipple != null) {
            updateRippleSize();
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View updateAccessibilityOrder(View previousView) {
        setAccessibilityTraversalAfter(previousView.getId());
        return this;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public void onStateChanged(QSTile.State state) {
        this.mHandler.obtainMessage(1, state).sendToTarget();
    }

    protected void handleStateChanged(QSTile.State state) {
        boolean newState;
        int circleColor = getCircleColor(state.state);
        if (circleColor != this.mCircleColor) {
            if (this.mBg.isShown() && animationsEnabled()) {
                ValueAnimator animator = ValueAnimator.ofArgb(this.mCircleColor, circleColor).setDuration(350L);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.-$$Lambda$QSTileBaseView$R4RxHhlQ5aUQCBgq0kdDEHJXn14
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.mBg.setImageTintList(ColorStateList.valueOf(((Integer) valueAnimator.getAnimatedValue()).intValue()));
                    }
                });
                animator.start();
            } else {
                QSIconViewImpl.setTint(this.mBg, circleColor);
            }
            this.mCircleColor = circleColor;
        }
        setClickable(state.state != 0);
        this.mIcon.setIcon(state);
        setContentDescription(state.contentDescription);
        this.mAccessibilityClass = state.expandedAccessibilityClassName;
        if ((state instanceof QSTile.BooleanState) && this.mTileState != (newState = ((QSTile.BooleanState) state).value)) {
            this.mClicked = false;
            this.mTileState = newState;
        }
    }

    protected boolean animationsEnabled() {
        return true;
    }

    private int getCircleColor(int state) {
        switch (state) {
            case 0:
            case 1:
                return this.mColorDisabled;
            case 2:
                return this.mColorActive;
            default:
                Log.e("QSTileBaseView", "Invalid state " + state);
                return 0;
        }
    }

    @Override // android.view.View
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        setBackground(clickable ? this.mRipple : null);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public int getDetailY() {
        return getTop() + (getHeight() / 2);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public QSIconView getIcon() {
        return this.mIcon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getIconWithBackground() {
        return this.mIconFrame;
    }

    @Override // android.view.View
    public boolean performClick() {
        this.mClicked = true;
        return super.performClick();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        boolean b;
        super.onInitializeAccessibilityEvent(event);
        if (!TextUtils.isEmpty(this.mAccessibilityClass)) {
            event.setClassName(this.mAccessibilityClass);
            if (Switch.class.getName().equals(this.mAccessibilityClass)) {
                if (this.mClicked) {
                    b = !this.mTileState;
                } else {
                    b = this.mTileState;
                }
                String label = getResources().getString(b ? R.string.switch_bar_on : R.string.switch_bar_off);
                event.setContentDescription(label);
                event.setChecked(b);
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        boolean b;
        super.onInitializeAccessibilityNodeInfo(info);
        if (!TextUtils.isEmpty(this.mAccessibilityClass)) {
            info.setClassName(this.mAccessibilityClass);
            if (Switch.class.getName().equals(this.mAccessibilityClass)) {
                if (this.mClicked) {
                    b = !this.mTileState;
                } else {
                    b = this.mTileState;
                }
                String label = getResources().getString(b ? R.string.switch_bar_on : R.string.switch_bar_off);
                info.setText(label);
                info.setChecked(b);
                info.setCheckable(true);
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(R.string.accessibility_long_click_tile)));
            }
        }
    }

    private class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                QSTileBaseView.this.handleStateChanged((QSTile.State) msg.obj);
            }
        }
    }
}
