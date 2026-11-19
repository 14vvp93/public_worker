package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpStatusBarView extends AlphaOptimizedLinearLayout {
    private int mAbsoluteStartPadding;
    private List<Rect> mCutOutBounds;
    private int mCutOutInset;
    private Point mDisplaySize;
    private int mEndMargin;
    private boolean mFirstLayout;
    private Rect mIconDrawingRect;
    private View mIconPlaceholder;
    private Rect mLayoutedIconRect;
    private int mMaxWidth;
    private Runnable mOnDrawingRectChangedListener;
    private boolean mPublicMode;
    private NotificationData.Entry mShowingEntry;
    private int mSysWinInset;
    private TextView mTextView;
    private int[] mTmpPosition;

    public HeadsUpStatusBarView(Context context) {
        this(context, null);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mLayoutedIconRect = new Rect();
        this.mTmpPosition = new int[2];
        this.mFirstLayout = true;
        this.mIconDrawingRect = new Rect();
        Resources res = getResources();
        this.mAbsoluteStartPadding = res.getDimensionPixelSize(R.dimen.notification_side_paddings) + res.getDimensionPixelSize(android.R.dimen.conversation_compact_face_pile_size);
        this.mEndMargin = res.getDimensionPixelSize(android.R.dimen.conversation_compact_face_pile_protection_width);
        setPaddingRelative(this.mAbsoluteStartPadding, 0, this.mEndMargin, 0);
        updateMaxWidth();
    }

    private void updateMaxWidth() {
        int maxWidth = getResources().getDimensionPixelSize(R.dimen.qs_panel_width);
        if (maxWidth != this.mMaxWidth) {
            this.mMaxWidth = maxWidth;
            requestLayout();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mMaxWidth > 0) {
            int newSize = Math.min(View.MeasureSpec.getSize(widthMeasureSpec), this.mMaxWidth);
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(newSize, View.MeasureSpec.getMode(widthMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateMaxWidth();
    }

    @VisibleForTesting
    public HeadsUpStatusBarView(Context context, View iconPlaceholder, TextView textView) {
        this(context);
        this.mIconPlaceholder = iconPlaceholder;
        this.mTextView = textView;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mIconPlaceholder = findViewById(R.id.icon_placeholder);
        this.mTextView = (TextView) findViewById(R.id.text);
    }

    public void setEntry(NotificationData.Entry entry) {
        if (entry != null) {
            this.mShowingEntry = entry;
            CharSequence text = entry.headsUpStatusBarText;
            if (this.mPublicMode) {
                text = entry.headsUpStatusBarTextPublic;
            }
            this.mTextView.setText(text);
            return;
        }
        this.mShowingEntry = null;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mIconPlaceholder.getLocationOnScreen(this.mTmpPosition);
        int left = (int) (this.mTmpPosition[0] - getTranslationX());
        int top = this.mTmpPosition[1];
        int right = this.mIconPlaceholder.getWidth() + left;
        int bottom = this.mIconPlaceholder.getHeight() + top;
        this.mLayoutedIconRect.set(left, top, right, bottom);
        updateDrawingRect();
        int targetPadding = this.mAbsoluteStartPadding + this.mSysWinInset + this.mCutOutInset;
        boolean isRtl = isLayoutRtl();
        int start = isRtl ? this.mDisplaySize.x - right : left;
        if (start != targetPadding) {
            if (this.mCutOutBounds != null) {
                for (Rect cutOutRect : this.mCutOutBounds) {
                    int cutOutStart = isRtl ? this.mDisplaySize.x - cutOutRect.right : cutOutRect.left;
                    if (start > cutOutStart) {
                        start -= cutOutRect.width();
                        break;
                    }
                }
            }
            int newPadding = (targetPadding - start) + getPaddingStart();
            setPaddingRelative(newPadding, 0, this.mEndMargin, 0);
        }
        if (this.mFirstLayout) {
            setVisibility(8);
            this.mFirstLayout = false;
        }
    }

    public void setPanelTranslation(float translationX) {
        if (isLayoutRtl()) {
            setTranslationX(this.mCutOutInset + translationX);
        } else {
            setTranslationX(translationX - this.mCutOutInset);
        }
        updateDrawingRect();
    }

    private void updateDrawingRect() {
        float oldLeft = this.mIconDrawingRect.left;
        this.mIconDrawingRect.set(this.mLayoutedIconRect);
        this.mIconDrawingRect.offset((int) getTranslationX(), 0);
        if (oldLeft != this.mIconDrawingRect.left && this.mOnDrawingRectChangedListener != null) {
            this.mOnDrawingRectChangedListener.run();
        }
    }

    @Override // android.view.View
    protected boolean fitSystemWindows(Rect insets) {
        int safeInsetRight;
        boolean isRtl = isLayoutRtl();
        this.mSysWinInset = isRtl ? insets.right : insets.left;
        DisplayCutout displayCutout = getRootWindowInsets().getDisplayCutout();
        if (displayCutout != null) {
            safeInsetRight = isRtl ? displayCutout.getSafeInsetRight() : displayCutout.getSafeInsetLeft();
        } else {
            safeInsetRight = 0;
        }
        this.mCutOutInset = safeInsetRight;
        getDisplaySize();
        this.mCutOutBounds = null;
        if (displayCutout != null && displayCutout.getSafeInsetRight() == 0 && displayCutout.getSafeInsetLeft() == 0) {
            this.mCutOutBounds = displayCutout.getBoundingRects();
        }
        if (this.mSysWinInset != 0) {
            this.mCutOutInset = 0;
        }
        return super.fitSystemWindows(insets);
    }

    public NotificationData.Entry getShowingEntry() {
        return this.mShowingEntry;
    }

    public Rect getIconDrawingRect() {
        return this.mIconDrawingRect;
    }

    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        this.mTextView.setTextColor(DarkIconDispatcher.getTint(area, this, tint));
    }

    public void setPublicMode(boolean publicMode) {
        this.mPublicMode = publicMode;
    }

    public void setOnDrawingRectChangedListener(Runnable onDrawingRectChangedListener) {
        this.mOnDrawingRectChangedListener = onDrawingRectChangedListener;
    }

    private void getDisplaySize() {
        if (this.mDisplaySize == null) {
            this.mDisplaySize = new Point();
        }
        getDisplay().getRealSize(this.mDisplaySize);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getDisplaySize();
    }
}
