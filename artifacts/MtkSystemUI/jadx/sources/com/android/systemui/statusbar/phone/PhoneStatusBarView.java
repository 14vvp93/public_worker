package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.util.ExternalParameterManager;
import com.auto.common.HConfig;
import java.io.File;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PhoneStatusBarView extends PanelBar {
    private static final boolean DEBUG = StatusBar.DEBUG;
    private final String BACKCAR_PROPERTIES;
    private final String BACKCAR_STATUS_NONE;
    private final String BACKCAR_STATUS_START;
    private final String BACKCAR_STATUS_STOP;
    private ImageView mBack;
    StatusBar mBar;
    private final PhoneStatusBarTransitions mBarTransitions;
    private Context mContext;
    private int mCutoutSideNudge;
    private View mCutoutSpace;
    private DisplayCutout mDisplayCutout;
    private ImageView mFyScreen;
    private final Runnable mHideExpandedRunnable;
    private ImageView mHome;
    boolean mIsFullyOpenedPanel;
    private int mLastOrientation;
    private float mMinFraction;
    private float mPanelFraction;
    private ImageView mRecent;
    private ImageView mRotation;
    private ImageView mScreenOff;
    private ScrimController mScrimController;
    private ImageView mSplit;
    private ImageView mVolumeDown;
    private ImageView mVolumeMute;
    private ImageView mVolumeUp;

    public PhoneStatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.BACKCAR_PROPERTIES = "runtime.backcar.status";
        this.BACKCAR_STATUS_NONE = "none";
        this.BACKCAR_STATUS_START = "start";
        this.BACKCAR_STATUS_STOP = "stop";
        this.mIsFullyOpenedPanel = false;
        this.mHideExpandedRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarView.1
            @Override // java.lang.Runnable
            public void run() {
                if (PhoneStatusBarView.this.mPanelFraction == 0.0f) {
                    PhoneStatusBarView.this.mBar.makeExpandedInvisible();
                }
            }
        };
        this.mCutoutSideNudge = 0;
        this.mContext = context;
        this.mBarTransitions = new PhoneStatusBarTransitions(this);
    }

    public BarTransitions getBarTransitions() {
        return this.mBarTransitions;
    }

    public void setBar(StatusBar bar) {
        this.mBar = bar;
    }

    public void setScrimController(ScrimController scrimController) {
        this.mScrimController = scrimController;
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar, android.view.View
    public void onFinishInflate() {
        this.mBarTransitions.init();
        this.mCutoutSpace = findViewById(R.id.cutout_space_view);
        updateResources();
        this.mHome = (ImageView) findViewById(R.id.home);
        this.mBack = (ImageView) findViewById(R.id.back);
        this.mRecent = (ImageView) findViewById(R.id.recent_apps007);
        this.mFyScreen = (ImageView) findViewById(R.id.off_screen);
        this.mVolumeUp = (ImageView) findViewById(R.id.volume_up);
        this.mVolumeDown = (ImageView) findViewById(R.id.volume_down);
        this.mVolumeMute = (ImageView) findViewById(R.id.volume_mute);
        this.mRotation = (ImageView) findViewById(R.id.rotation);
        this.mSplit = (ImageView) findViewById(R.id.split);
        this.mScreenOff = (ImageView) findViewById(R.id.screenoff);
        if (setTradeMark("/apd/trademark.png")) {
            Log.i("PhoneStatusBarView", "Set StatusBar TradeMark by Customer.");
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (updateOrientationAndCutout(getResources().getConfiguration().orientation)) {
            updateLayoutForCutout();
        }
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mHome);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mBack);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mRecent);
        if (this.mRotation != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mRotation);
        }
        if (this.mSplit != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mSplit);
        }
        if ((ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB)) && this.mFyScreen != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mFyScreen);
        }
        if (this.mScreenOff != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mScreenOff);
        }
        if (this.mVolumeUp != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mVolumeUp);
        }
        if (this.mVolumeDown != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mVolumeDown);
        }
        if (this.mVolumeMute != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mVolumeMute);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDisplayCutout = null;
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mHome);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mBack);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mRecent);
        if (this.mRotation != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mRotation);
        }
        if (this.mSplit != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mSplit);
        }
        if (this.mScreenOff != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mScreenOff);
        }
        if (this.mVolumeUp != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mVolumeUp);
        }
        if (this.mVolumeDown != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mVolumeDown);
        }
        if (this.mVolumeMute != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mVolumeMute);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (updateOrientationAndCutout(newConfig.orientation)) {
            updateLayoutForCutout();
            requestLayout();
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (updateOrientationAndCutout(this.mLastOrientation)) {
            updateLayoutForCutout();
            requestLayout();
        }
        return super.onApplyWindowInsets(insets);
    }

    private boolean updateOrientationAndCutout(int newOrientation) {
        boolean changed = false;
        if (newOrientation != Integer.MIN_VALUE && this.mLastOrientation != newOrientation) {
            changed = true;
            this.mLastOrientation = newOrientation;
        }
        if (!Objects.equals(getRootWindowInsets().getDisplayCutout(), this.mDisplayCutout)) {
            this.mDisplayCutout = getRootWindowInsets().getDisplayCutout();
            return true;
        }
        return changed;
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public boolean panelEnabled() {
        return this.mBar.panelsEnabled();
    }

    public boolean onRequestSendAccessibilityEventInternal(View child, AccessibilityEvent event) {
        if (super.onRequestSendAccessibilityEventInternal(child, event)) {
            AccessibilityEvent record = AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(record);
            dispatchPopulateAccessibilityEvent(record);
            event.appendRecord(record);
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onPanelPeeked() {
        super.onPanelPeeked();
        this.mBar.makeExpandedVisible(false);
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onPanelCollapsed() {
        super.onPanelCollapsed();
        post(this.mHideExpandedRunnable);
        this.mIsFullyOpenedPanel = false;
    }

    public void removePendingHideExpandedRunnables() {
        removeCallbacks(this.mHideExpandedRunnable);
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onPanelFullyOpened() {
        super.onPanelFullyOpened();
        if (!this.mIsFullyOpenedPanel) {
            this.mPanel.sendAccessibilityEvent(32);
        }
        this.mIsFullyOpenedPanel = true;
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        String backcarStatus = SystemProperties.get("runtime.backcar.status", "none");
        if (backcarStatus.equals("start")) {
            return false;
        }
        boolean barConsumedEvent = this.mBar.interceptTouchEvent(event);
        return barConsumedEvent || super.onTouchEvent(event);
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onTrackingStarted() {
        super.onTrackingStarted();
        this.mBar.onTrackingStarted();
        this.mScrimController.onTrackingStarted();
        removePendingHideExpandedRunnables();
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onClosingFinished() {
        super.onClosingFinished();
        this.mBar.onClosingFinished();
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onTrackingStopped(boolean expand) {
        super.onTrackingStopped(expand);
        this.mBar.onTrackingStopped(expand);
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void onExpandingFinished() {
        super.onExpandingFinished();
        this.mScrimController.onExpandingFinished();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (HConfig.isChangedAppBlockingDropdownPanelBar()) {
            return true;
        }
        String backcarStatus = SystemProperties.get("runtime.backcar.status", "none");
        if (backcarStatus.equals("start")) {
            return false;
        }
        return this.mBar.interceptTouchEvent(event) || super.onInterceptTouchEvent(event);
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void panelScrimMinFractionChanged(float minFraction) {
        if (this.mMinFraction != minFraction) {
            this.mMinFraction = minFraction;
            updateScrimFraction();
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelBar
    public void panelExpansionChanged(float frac, boolean expanded) {
        super.panelExpansionChanged(frac, expanded);
        this.mPanelFraction = frac;
        updateScrimFraction();
        if ((frac == 0.0f || frac == 1.0f) && this.mBar.getNavigationBarView() != null) {
            this.mBar.getNavigationBarView().onPanelExpandedChange(expanded);
        }
    }

    private void updateScrimFraction() {
        float scrimFraction = this.mPanelFraction;
        if (this.mMinFraction < 1.0f) {
            scrimFraction = Math.max((this.mPanelFraction - this.mMinFraction) / (1.0f - this.mMinFraction), 0.0f);
        }
        this.mScrimController.setPanelExpansion(scrimFraction);
    }

    public void updateResources() {
        this.mCutoutSideNudge = getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        setLayoutParams(layoutParams);
    }

    private void updateLayoutForCutout() {
        Pair<Integer, Integer> cornerCutoutMargins = cornerCutoutMargins(this.mDisplayCutout, getDisplay());
        updateCutoutLocation(cornerCutoutMargins);
        updateSafeInsets(cornerCutoutMargins);
    }

    private void updateCutoutLocation(Pair<Integer, Integer> cornerCutoutMargins) {
        if (this.mCutoutSpace == null) {
            return;
        }
        if (this.mDisplayCutout == null || this.mDisplayCutout.isEmpty() || this.mLastOrientation != 1 || cornerCutoutMargins != null) {
            this.mCutoutSpace.setVisibility(8);
            return;
        }
        this.mCutoutSpace.setVisibility(0);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mCutoutSpace.getLayoutParams();
        Rect bounds = new Rect();
        ScreenDecorations.DisplayCutoutView.boundsFromDirection(this.mDisplayCutout, 48, bounds);
        bounds.left += this.mCutoutSideNudge;
        bounds.right -= this.mCutoutSideNudge;
        lp.width = bounds.width();
        lp.height = bounds.height();
    }

    private void updateSafeInsets(Pair<Integer, Integer> cornerCutoutMargins) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
        if (this.mDisplayCutout == null) {
            lp.leftMargin = 0;
            lp.rightMargin = 0;
            return;
        }
        lp.leftMargin = this.mDisplayCutout.getSafeInsetLeft();
        lp.rightMargin = this.mDisplayCutout.getSafeInsetRight();
        if (cornerCutoutMargins != null) {
            lp.leftMargin = Math.max(lp.leftMargin, ((Integer) cornerCutoutMargins.first).intValue());
            lp.rightMargin = Math.max(lp.rightMargin, ((Integer) cornerCutoutMargins.second).intValue());
            WindowInsets insets = getRootWindowInsets();
            int leftInset = insets.getSystemWindowInsetLeft();
            int rightInset = insets.getSystemWindowInsetRight();
            if (lp.leftMargin <= leftInset) {
                lp.leftMargin = 0;
            }
            if (lp.rightMargin <= rightInset) {
                lp.rightMargin = 0;
            }
        }
    }

    public static Pair<Integer, Integer> cornerCutoutMargins(DisplayCutout cutout, Display display) {
        if (cutout == null) {
            return null;
        }
        Point size = new Point();
        display.getRealSize(size);
        Rect bounds = new Rect();
        ScreenDecorations.DisplayCutoutView.boundsFromDirection(cutout, 48, bounds);
        if (bounds.left <= 0) {
            return new Pair<>(Integer.valueOf(bounds.right), 0);
        }
        if (bounds.right < size.x) {
            return null;
        }
        return new Pair<>(0, Integer.valueOf(size.x - bounds.left));
    }

    private boolean setTradeMark(String markFile) {
        File mTrade = new File(markFile);
        if (!mTrade.exists()) {
            return false;
        }
        Bitmap mTradeBitMap = BitmapFactory.decodeFile(markFile);
        setBackground(new BitmapDrawable(mTradeBitMap));
        return true;
    }
}
