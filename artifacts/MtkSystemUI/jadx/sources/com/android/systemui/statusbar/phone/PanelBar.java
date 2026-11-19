package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.auto.common.HConfig;

/* JADX INFO: loaded from: classes.dex */
public abstract class PanelBar extends FrameLayout {
    public static final String TAG = PanelBar.class.getSimpleName();
    private boolean mBouncerShowing;
    private boolean mExpanded;
    PanelView mPanel;
    private int mState;
    private boolean mTracking;

    public abstract void panelScrimMinFractionChanged(float f);

    public void go(int state) {
        this.mState = state;
    }

    public int getState() {
        return this.mState;
    }

    public PanelBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = 0;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setPanel(PanelView pv) {
        this.mPanel = pv;
        pv.setBar(this);
    }

    public void setBouncerShowing(boolean showing) {
        this.mBouncerShowing = showing;
        int important = showing ? 4 : 0;
        setImportantForAccessibility(important);
        updateVisibility();
        if (this.mPanel != null) {
            this.mPanel.setImportantForAccessibility(important);
        }
    }

    private void updateVisibility() {
        this.mPanel.setVisibility((this.mExpanded || this.mBouncerShowing) ? 0 : 4);
    }

    public boolean panelEnabled() {
        return !HConfig.isDisableDropdownPanelBar();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!panelEnabled()) {
            if (event.getAction() == 0) {
                Log.v(TAG, String.format("onTouch: all panels disabled, ignoring touch at (%d,%d)", Integer.valueOf((int) event.getX()), Integer.valueOf((int) event.getY())));
            }
            return false;
        }
        if (event.getAction() == 0) {
            PanelView panel = this.mPanel;
            if (panel == null) {
                Log.v(TAG, String.format("onTouch: no panel for touch at (%d,%d)", Integer.valueOf((int) event.getX()), Integer.valueOf((int) event.getY())));
                return true;
            }
            boolean enabled = panel.isEnabled();
            if (!enabled) {
                Log.v(TAG, String.format("onTouch: panel (%s) is disabled, ignoring touch at (%d,%d)", panel, Integer.valueOf((int) event.getX()), Integer.valueOf((int) event.getY())));
                return true;
            }
        }
        return this.mPanel == null || this.mPanel.onTouchEvent(event);
    }

    public void panelExpansionChanged(float frac, boolean expanded) {
        boolean fullyClosed = true;
        boolean fullyOpened = false;
        PanelView pv = this.mPanel;
        this.mExpanded = expanded;
        updateVisibility();
        if (expanded) {
            if (this.mState == 0) {
                go(1);
                onPanelPeeked();
            }
            fullyClosed = false;
            float thisFrac = pv.getExpandedFraction();
            fullyOpened = thisFrac >= 1.0f;
        }
        if (fullyOpened && !this.mTracking) {
            go(2);
            onPanelFullyOpened();
        } else if (fullyClosed && !this.mTracking && this.mState != 0) {
            go(0);
            onPanelCollapsed();
        }
    }

    public void collapsePanel(boolean animate, boolean delayed, float speedUpFactor) {
        boolean waiting = false;
        PanelView pv = this.mPanel;
        if (animate && !pv.isFullyCollapsed()) {
            pv.collapse(delayed, speedUpFactor);
            waiting = true;
        } else {
            pv.resetViews();
            pv.setExpandedFraction(0.0f);
            pv.cancelPeek();
        }
        if (!waiting && this.mState != 0) {
            go(0);
            onPanelCollapsed();
        }
    }

    public void onPanelPeeked() {
    }

    public boolean isClosed() {
        return this.mState == 0;
    }

    public void onPanelCollapsed() {
    }

    public void onPanelFullyOpened() {
    }

    public void onTrackingStarted() {
        this.mTracking = true;
    }

    public void onTrackingStopped(boolean expand) {
        this.mTracking = false;
    }

    public void onExpandingFinished() {
    }

    public void onClosingFinished() {
    }
}
