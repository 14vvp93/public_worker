package com.android.systemui;

import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecureSetting;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.tuner.TunablePadding;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.RotationUtils;

/* JADX INFO: loaded from: classes.dex */
public class ScreenDecorations extends SystemUI implements TunerService.Tunable {
    private static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
    private View mBottomOverlay;
    private float mDensity;
    private DisplayManager.DisplayListener mDisplayListener;
    private DisplayManager mDisplayManager;
    private View mOverlay;
    private int mRotation;
    private int mRoundedDefault;
    private int mRoundedDefaultBottom;
    private int mRoundedDefaultTop;
    private WindowManager mWindowManager;

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mWindowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        this.mRoundedDefault = this.mContext.getResources().getDimensionPixelSize(R.dimen.rounded_corner_radius);
        this.mRoundedDefaultTop = this.mContext.getResources().getDimensionPixelSize(R.dimen.rounded_corner_radius_top);
        this.mRoundedDefaultBottom = this.mContext.getResources().getDimensionPixelSize(R.dimen.rounded_corner_radius_bottom);
        if (hasRoundedCorners() || shouldDrawCutout()) {
            setupDecorations();
        }
        int padding = this.mContext.getResources().getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
        if (padding != 0) {
            setupPadding(padding);
        }
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.ScreenDecorations.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int displayId) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int displayId) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int displayId) {
                ScreenDecorations.this.updateOrientation();
            }
        };
        this.mRotation = -1;
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, null);
    }

    private void setupDecorations() {
        this.mOverlay = LayoutInflater.from(this.mContext).inflate(R.layout.rounded_corners, (ViewGroup) null);
        final DisplayCutoutView cutoutTop = new DisplayCutoutView(this.mContext, true, new Runnable() { // from class: com.android.systemui.-$$Lambda$ScreenDecorations$aq1MVJyy_LkZ11q5t5cPVZOqbG0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateWindowVisibilities();
            }
        });
        ((ViewGroup) this.mOverlay).addView(cutoutTop);
        this.mBottomOverlay = LayoutInflater.from(this.mContext).inflate(R.layout.rounded_corners, (ViewGroup) null);
        final DisplayCutoutView cutoutBottom = new DisplayCutoutView(this.mContext, false, new Runnable() { // from class: com.android.systemui.-$$Lambda$ScreenDecorations$aq1MVJyy_LkZ11q5t5cPVZOqbG0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateWindowVisibilities();
            }
        });
        ((ViewGroup) this.mBottomOverlay).addView(cutoutBottom);
        this.mOverlay.setSystemUiVisibility(256);
        this.mOverlay.setAlpha(0.0f);
        this.mBottomOverlay.setSystemUiVisibility(256);
        this.mBottomOverlay.setAlpha(0.0f);
        updateViews();
        this.mWindowManager.addView(this.mOverlay, getWindowLayoutParams());
        this.mWindowManager.addView(this.mBottomOverlay, getBottomLayoutParams());
        DisplayMetrics metrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getMetrics(metrics);
        this.mDensity = metrics.density;
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_rounded_size");
        SecureSetting setting = new SecureSetting(this.mContext, (Handler) Dependency.get(Dependency.MAIN_HANDLER), "accessibility_display_inversion_enabled") { // from class: com.android.systemui.ScreenDecorations.2
            @Override // com.android.systemui.qs.SecureSetting
            protected void handleValueChanged(int value, boolean observedChange) {
                int tint = value != 0 ? -1 : -16777216;
                ColorStateList tintList = ColorStateList.valueOf(tint);
                ((ImageView) ScreenDecorations.this.mOverlay.findViewById(R.id.left)).setImageTintList(tintList);
                ((ImageView) ScreenDecorations.this.mOverlay.findViewById(R.id.right)).setImageTintList(tintList);
                ((ImageView) ScreenDecorations.this.mBottomOverlay.findViewById(R.id.left)).setImageTintList(tintList);
                ((ImageView) ScreenDecorations.this.mBottomOverlay.findViewById(R.id.right)).setImageTintList(tintList);
                cutoutTop.setColor(tint);
                cutoutBottom.setColor(tint);
            }
        };
        setting.setListening(true);
        setting.onChange(false);
        this.mOverlay.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.ScreenDecorations.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ScreenDecorations.this.mOverlay.removeOnLayoutChangeListener(this);
                ScreenDecorations.this.mOverlay.animate().alpha(1.0f).setDuration(1000L).start();
                ScreenDecorations.this.mBottomOverlay.animate().alpha(1.0f).setDuration(1000L).start();
            }
        });
    }

    @Override // com.android.systemui.SystemUI
    protected void onConfigurationChanged(Configuration newConfig) {
        updateOrientation();
        if (shouldDrawCutout() && this.mOverlay == null) {
            setupDecorations();
        }
    }

    protected void updateOrientation() {
        int newRotation = RotationUtils.getExactRotation(this.mContext);
        if (newRotation != this.mRotation) {
            this.mRotation = newRotation;
            if (this.mOverlay != null) {
                updateLayoutParams();
                updateViews();
            }
        }
    }

    private void updateViews() {
        View topLeft = this.mOverlay.findViewById(R.id.left);
        View topRight = this.mOverlay.findViewById(R.id.right);
        View bottomLeft = this.mBottomOverlay.findViewById(R.id.left);
        View bottomRight = this.mBottomOverlay.findViewById(R.id.right);
        if (this.mRotation == 0) {
            updateView(topLeft, 51, 0);
            updateView(topRight, 53, 90);
            updateView(bottomLeft, 83, 270);
            updateView(bottomRight, 85, 180);
        } else if (this.mRotation == 1) {
            updateView(topLeft, 51, 0);
            updateView(topRight, 83, 270);
            updateView(bottomLeft, 53, 90);
            updateView(bottomRight, 85, 180);
        } else if (this.mRotation == 3) {
            updateView(topLeft, 83, 270);
            updateView(topRight, 85, 180);
            updateView(bottomLeft, 51, 0);
            updateView(bottomRight, 53, 90);
        } else if (this.mRotation == 2) {
            updateView(topLeft, 85, 180);
            updateView(topRight, 53, 90);
            updateView(bottomLeft, 83, 270);
            updateView(bottomRight, 51, 0);
        }
        updateWindowVisibilities();
    }

    private void updateView(View v, int gravity, int rotation) {
        ((FrameLayout.LayoutParams) v.getLayoutParams()).gravity = gravity;
        v.setRotation(rotation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindowVisibilities() {
        updateWindowVisibility(this.mOverlay);
        updateWindowVisibility(this.mBottomOverlay);
    }

    private void updateWindowVisibility(View overlay) {
        int i = 0;
        boolean visibleForCutout = shouldDrawCutout() && overlay.findViewById(R.id.display_cutout).getVisibility() == 0;
        boolean visibleForRoundedCorners = hasRoundedCorners();
        if (!visibleForCutout && !visibleForRoundedCorners) {
            i = 8;
        }
        overlay.setVisibility(i);
    }

    private boolean hasRoundedCorners() {
        return this.mRoundedDefault > 0 || this.mRoundedDefaultBottom > 0 || this.mRoundedDefaultTop > 0;
    }

    private boolean shouldDrawCutout() {
        return shouldDrawCutout(this.mContext);
    }

    static boolean shouldDrawCutout(Context context) {
        return context.getResources().getBoolean(android.R.^attr-private.initialActivityCount);
    }

    private void setupPadding(int padding) {
        StatusBar sb = (StatusBar) getComponent(StatusBar.class);
        View statusBar = sb != null ? sb.getStatusBarWindow() : null;
        if (statusBar != null) {
            TunablePadding.addTunablePadding(statusBar.findViewById(R.id.keyguard_header), "sysui_rounded_content_padding", padding, 2);
            FragmentHostManager fragmentHostManager = FragmentHostManager.get(statusBar);
            fragmentHostManager.addTagListener("CollapsedStatusBarFragment", new TunablePaddingTagListener(padding, R.id.status_bar));
            fragmentHostManager.addTagListener(QS.TAG, new TunablePaddingTagListener(padding, R.id.header));
        }
    }

    WindowManager.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -2, 2024, 545259816, -3);
        lp.privateFlags |= 80;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS) {
            lp.privateFlags |= 1048576;
        }
        lp.setTitle("ScreenDecorOverlay");
        if (this.mRotation == 2 || this.mRotation == 3) {
            lp.gravity = 85;
        } else {
            lp.gravity = 51;
        }
        lp.layoutInDisplayCutoutMode = 1;
        if (isLandscape(this.mRotation)) {
            lp.width = -2;
            lp.height = -1;
        }
        return lp;
    }

    private WindowManager.LayoutParams getBottomLayoutParams() {
        WindowManager.LayoutParams lp = getWindowLayoutParams();
        lp.setTitle("ScreenDecorOverlayBottom");
        if (this.mRotation == 2 || this.mRotation == 3) {
            lp.gravity = 51;
        } else {
            lp.gravity = 85;
        }
        return lp;
    }

    private void updateLayoutParams() {
        this.mWindowManager.updateViewLayout(this.mOverlay, getWindowLayoutParams());
        this.mWindowManager.updateViewLayout(this.mBottomOverlay, getBottomLayoutParams());
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (this.mOverlay != null && "sysui_rounded_size".equals(key)) {
            int size = this.mRoundedDefault;
            int sizeTop = this.mRoundedDefaultTop;
            int sizeBottom = this.mRoundedDefaultBottom;
            if (newValue != null) {
                try {
                    size = (int) (Integer.parseInt(newValue) * this.mDensity);
                } catch (Exception e) {
                }
            }
            if (sizeTop == 0) {
                sizeTop = size;
            }
            if (sizeBottom == 0) {
                sizeBottom = size;
            }
            setSize(this.mOverlay.findViewById(R.id.left), sizeTop);
            setSize(this.mOverlay.findViewById(R.id.right), sizeTop);
            setSize(this.mBottomOverlay.findViewById(R.id.left), sizeBottom);
            setSize(this.mBottomOverlay.findViewById(R.id.right), sizeBottom);
        }
    }

    private void setSize(View view, int pixelSize) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = pixelSize;
        params.height = pixelSize;
        view.setLayoutParams(params);
    }

    static class TunablePaddingTagListener implements FragmentHostManager.FragmentListener {
        private final int mId;
        private final int mPadding;
        private TunablePadding mTunablePadding;

        public TunablePaddingTagListener(int padding, int id) {
            this.mPadding = padding;
            this.mId = id;
        }

        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public void onFragmentViewCreated(String tag, Fragment fragment) {
            if (this.mTunablePadding != null) {
                this.mTunablePadding.destroy();
            }
            View view = fragment.getView();
            if (this.mId != 0) {
                view = view.findViewById(this.mId);
            }
            this.mTunablePadding = TunablePadding.addTunablePadding(view, "sysui_rounded_content_padding", this.mPadding, 3);
        }
    }

    public static class DisplayCutoutView extends View implements DisplayManager.DisplayListener, RegionInterceptingFrameLayout.RegionInterceptableView {
        private final Path mBoundingPath;
        private final Rect mBoundingRect;
        private final Region mBounds;
        private int mColor;
        private final DisplayInfo mInfo;
        private final int[] mLocation;
        private final Paint mPaint;
        private final boolean mStart;
        private final Runnable mVisibilityChangedListener;

        public DisplayCutoutView(Context context, boolean start, Runnable visibilityChangedListener) {
            super(context);
            this.mInfo = new DisplayInfo();
            this.mPaint = new Paint();
            this.mBounds = new Region();
            this.mBoundingRect = new Rect();
            this.mBoundingPath = new Path();
            this.mLocation = new int[2];
            this.mColor = -16777216;
            this.mStart = start;
            this.mVisibilityChangedListener = visibilityChangedListener;
            setId(R.id.display_cutout);
        }

        public void setColor(int color) {
            this.mColor = color;
            invalidate();
        }

        @Override // android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).registerDisplayListener(this, getHandler());
            update();
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).unregisterDisplayListener(this);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            getLocationOnScreen(this.mLocation);
            canvas.translate(-this.mLocation[0], -this.mLocation[1]);
            if (!this.mBoundingPath.isEmpty()) {
                this.mPaint.setColor(this.mColor);
                this.mPaint.setStyle(Paint.Style.FILL);
                this.mPaint.setAntiAlias(true);
                canvas.drawPath(this.mBoundingPath, this.mPaint);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int displayId) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int displayId) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int displayId) {
            if (displayId == getDisplay().getDisplayId()) {
                update();
            }
        }

        private void update() {
            int newVisible;
            requestLayout();
            getDisplay().getDisplayInfo(this.mInfo);
            this.mBounds.setEmpty();
            this.mBoundingRect.setEmpty();
            this.mBoundingPath.reset();
            if (ScreenDecorations.shouldDrawCutout(getContext()) && hasCutout()) {
                this.mBounds.set(this.mInfo.displayCutout.getBounds());
                localBounds(this.mBoundingRect);
                updateBoundingPath();
                invalidate();
                newVisible = 0;
            } else {
                newVisible = 8;
            }
            if (newVisible != getVisibility()) {
                setVisibility(newVisible);
                this.mVisibilityChangedListener.run();
            }
        }

        private void updateBoundingPath() {
            int lw = this.mInfo.logicalWidth;
            int lh = this.mInfo.logicalHeight;
            boolean z = true;
            if (this.mInfo.rotation != 1 && this.mInfo.rotation != 3) {
                z = false;
            }
            boolean flipped = z;
            int dw = flipped ? lh : lw;
            int dh = flipped ? lw : lh;
            this.mBoundingPath.set(DisplayCutout.pathFromResources(getResources(), dw, dh));
            Matrix m = new Matrix();
            transformPhysicalToLogicalCoordinates(this.mInfo.rotation, dw, dh, m);
            this.mBoundingPath.transform(m);
        }

        private static void transformPhysicalToLogicalCoordinates(int rotation, int physicalWidth, int physicalHeight, Matrix out) {
            switch (rotation) {
                case 0:
                    out.reset();
                    return;
                case 1:
                    out.setRotate(270.0f);
                    out.postTranslate(0.0f, physicalWidth);
                    return;
                case 2:
                    out.setRotate(180.0f);
                    out.postTranslate(physicalWidth, physicalHeight);
                    return;
                case 3:
                    out.setRotate(90.0f);
                    out.postTranslate(physicalHeight, 0.0f);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown rotation: " + rotation);
            }
        }

        private boolean hasCutout() {
            DisplayCutout displayCutout = this.mInfo.displayCutout;
            if (displayCutout == null) {
                return false;
            }
            if (this.mStart) {
                return displayCutout.getSafeInsetLeft() > 0 || displayCutout.getSafeInsetTop() > 0;
            }
            return displayCutout.getSafeInsetRight() > 0 || displayCutout.getSafeInsetBottom() > 0;
        }

        @Override // android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (this.mBounds.isEmpty()) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                setMeasuredDimension(resolveSizeAndState(this.mBoundingRect.width(), widthMeasureSpec, 0), resolveSizeAndState(this.mBoundingRect.height(), heightMeasureSpec, 0));
            }
        }

        public static void boundsFromDirection(DisplayCutout displayCutout, int gravity, Rect out) {
            Region bounds = displayCutout.getBounds();
            if (gravity == 3) {
                bounds.op(0, 0, displayCutout.getSafeInsetLeft(), Integer.MAX_VALUE, Region.Op.INTERSECT);
                out.set(bounds.getBounds());
            } else if (gravity == 5) {
                bounds.op(displayCutout.getSafeInsetLeft() + 1, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, Region.Op.INTERSECT);
                out.set(bounds.getBounds());
            } else if (gravity == 48) {
                bounds.op(0, 0, Integer.MAX_VALUE, displayCutout.getSafeInsetTop(), Region.Op.INTERSECT);
                out.set(bounds.getBounds());
            } else if (gravity == 80) {
                bounds.op(0, displayCutout.getSafeInsetTop() + 1, Integer.MAX_VALUE, Integer.MAX_VALUE, Region.Op.INTERSECT);
                out.set(bounds.getBounds());
            }
            bounds.recycle();
        }

        private void localBounds(Rect out) {
            DisplayCutout displayCutout = this.mInfo.displayCutout;
            if (this.mStart) {
                if (displayCutout.getSafeInsetLeft() > 0) {
                    boundsFromDirection(displayCutout, 3, out);
                    return;
                } else {
                    if (displayCutout.getSafeInsetTop() > 0) {
                        boundsFromDirection(displayCutout, 48, out);
                        return;
                    }
                    return;
                }
            }
            if (displayCutout.getSafeInsetRight() > 0) {
                boundsFromDirection(displayCutout, 5, out);
            } else if (displayCutout.getSafeInsetBottom() > 0) {
                boundsFromDirection(displayCutout, 80, out);
            }
        }

        @Override // com.android.systemui.RegionInterceptingFrameLayout.RegionInterceptableView
        public boolean shouldInterceptTouch() {
            return this.mInfo.displayCutout != null && getVisibility() == 0;
        }

        @Override // com.android.systemui.RegionInterceptingFrameLayout.RegionInterceptableView
        public Region getInterceptRegion() {
            if (this.mInfo.displayCutout == null) {
                return null;
            }
            View rootView = getRootView();
            Region cutoutBounds = this.mInfo.displayCutout.getBounds();
            rootView.getLocationOnScreen(this.mLocation);
            cutoutBounds.translate(-this.mLocation[0], -this.mLocation[1]);
            cutoutBounds.op(rootView.getLeft(), rootView.getTop(), rootView.getRight(), rootView.getBottom(), Region.Op.INTERSECT);
            return cutoutBounds;
        }
    }

    private boolean isLandscape(int rotation) {
        return rotation == 1 || rotation == 2;
    }
}
