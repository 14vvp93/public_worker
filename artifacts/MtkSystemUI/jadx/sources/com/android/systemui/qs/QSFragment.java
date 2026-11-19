package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.util.ExternalParameterManager;

/* JADX INFO: loaded from: classes.dex */
public class QSFragment extends Fragment implements QS, CommandQueue.Callbacks {
    private QSContainerImpl mContainer;
    private long mDelay;
    private QSFooter mFooter;
    protected QuickStatusBarHeader mHeader;
    private boolean mHeaderAnimating;
    private boolean mKeyguardShowing;
    private int mLayoutDirection;
    private boolean mListening;
    private QS.HeightListener mPanelView;
    private QSAnimator mQSAnimator;
    private QSCustomizer mQSCustomizer;
    private QSDetail mQSDetail;
    protected QSPanel mQSPanel;
    private boolean mQsDisabled;
    private boolean mQsExpanded;
    private boolean mStackScrollerOverscrolling;
    private final Rect mQsBounds = new Rect();
    private float mLastQSExpansion = -1.0f;
    private RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler = (RemoteInputQuickSettingsDisabler) Dependency.get(RemoteInputQuickSettingsDisabler.class);
    private final ViewTreeObserver.OnPreDrawListener mStartHeaderSlidingIn = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.qs.QSFragment.2
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            QSFragment.this.getView().getViewTreeObserver().removeOnPreDrawListener(this);
            QSFragment.this.getView().animate().translationY(0.0f).setStartDelay(QSFragment.this.mDelay).setDuration(448L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(QSFragment.this.mAnimateHeaderSlidingInListener).start();
            QSFragment.this.getView().setY(-QSFragment.this.mHeader.getHeight());
            return true;
        }
    };
    private final Animator.AnimatorListener mAnimateHeaderSlidingInListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.3
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            QSFragment.this.mHeaderAnimating = false;
            QSFragment.this.updateQsState();
        }
    };

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.cloneInContext(new ContextThemeWrapper(getContext(), R.style.qs_theme)).inflate(R.layout.qs_panel, container, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mQSPanel = (QSPanel) view.findViewById(R.id.quick_settings_panel);
        this.mQSDetail = (QSDetail) view.findViewById(R.id.qs_detail);
        this.mHeader = (QuickStatusBarHeader) view.findViewById(R.id.header);
        this.mFooter = (QSFooter) view.findViewById(R.id.qs_footer);
        this.mContainer = (QSContainerImpl) view.findViewById(R.id.quick_settings_container);
        this.mQSDetail.setQsPanel(this.mQSPanel, this.mHeader, (View) this.mFooter);
        this.mQSAnimator = new QSAnimator(this, (QuickQSPanel) this.mHeader.findViewById(R.id.quick_qs_panel), this.mQSPanel);
        this.mQSCustomizer = (QSCustomizer) view.findViewById(R.id.qs_customize);
        this.mQSCustomizer.setQs(this);
        if (savedInstanceState != null) {
            setExpanded(savedInstanceState.getBoolean("expanded"));
            setListening(savedInstanceState.getBoolean("listening"));
            int[] loc = new int[2];
            View edit = view.findViewById(android.R.id.edit);
            edit.getLocationInWindow(loc);
            int x = loc[0] + (edit.getWidth() / 2);
            int y = loc[1] + (edit.getHeight() / 2);
            this.mQSCustomizer.setEditLocation(x, y);
            this.mQSCustomizer.restoreInstanceState(savedInstanceState);
        }
        ((CommandQueue) SysUiServiceProvider.getComponent(getContext(), CommandQueue.class)).addCallbacks(this);
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        ((CommandQueue) SysUiServiceProvider.getComponent(getContext(), CommandQueue.class)).removeCallbacks(this);
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mListening) {
            setListening(false);
        }
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("expanded", this.mQsExpanded);
        outState.putBoolean("listening", this.mListening);
        this.mQSCustomizer.saveInstanceState(outState);
    }

    boolean isListening() {
        return this.mListening;
    }

    boolean isExpanded() {
        return this.mQsExpanded;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public View getHeader() {
        return this.mHeader;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHasNotifications(boolean hasNotifications) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setPanelView(QS.HeightListener panelView) {
        this.mPanelView = panelView;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.getLayoutDirection() != this.mLayoutDirection) {
            this.mLayoutDirection = newConfig.getLayoutDirection();
            if (this.mQSAnimator != null) {
                this.mQSAnimator.onRtlChanged();
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setContainer(ViewGroup container) {
        if (container instanceof NotificationsQuickSettingsContainer) {
            this.mQSCustomizer.setContainer((NotificationsQuickSettingsContainer) container);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isCustomizing() {
        return this.mQSCustomizer.isCustomizing();
    }

    public void setHost(QSTileHost qsh) {
        this.mQSPanel.setHost(qsh, this.mQSCustomizer);
        this.mHeader.setQSPanel(this.mQSPanel);
        this.mFooter.setQSPanel(this.mQSPanel);
        this.mQSDetail.setHost(qsh);
        if (this.mQSAnimator != null) {
            this.mQSAnimator.setHost(qsh);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void disable(int state1, int state2, boolean animate) {
        int state3 = this.mRemoteInputQuickSettingsDisabler.adjustDisableFlags(state2);
        boolean disabled = (state3 & 1) != 0;
        if (disabled == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = disabled;
        this.mContainer.disable(state1, state3, animate);
        this.mHeader.disable(state1, state3, animate);
        this.mFooter.disable(state1, state3, animate);
        updateQsState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateQsState() {
        boolean z = true;
        boolean expandVisually = this.mQsExpanded || this.mStackScrollerOverscrolling || this.mHeaderAnimating;
        this.mQSPanel.setExpanded(this.mQsExpanded);
        this.mQSDetail.setExpanded(this.mQsExpanded);
        this.mHeader.setVisibility((this.mQsExpanded || !this.mKeyguardShowing || this.mHeaderAnimating) ? 0 : 4);
        this.mHeader.setExpanded((this.mKeyguardShowing && !this.mHeaderAnimating) || (this.mQsExpanded && !this.mStackScrollerOverscrolling));
        if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && (ExternalParameterManager.ETHEME_SUB_16 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_22 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_37 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB)) {
            this.mFooter.setVisibility(8);
        } else {
            this.mFooter.setVisibility((this.mQsDisabled || !(this.mQsExpanded || !this.mKeyguardShowing || this.mHeaderAnimating)) ? 4 : 0);
        }
        QSFooter qSFooter = this.mFooter;
        if ((!this.mKeyguardShowing || this.mHeaderAnimating) && (!this.mQsExpanded || this.mStackScrollerOverscrolling)) {
            z = false;
        }
        qSFooter.setExpanded(z);
        this.mQSPanel.setVisibility((this.mQsDisabled || !expandVisually) ? 4 : 0);
    }

    public QSPanel getQsPanel() {
        return this.mQSPanel;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isShowingDetail() {
        return this.mQSPanel.isShowingCustomize() || this.mQSDetail.isShowingDetail();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isCustomizing();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderClickable(boolean clickable) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setExpanded(boolean expanded) {
        this.mQsExpanded = expanded;
        this.mQSPanel.setListening(this.mListening && this.mQsExpanded);
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setKeyguardShowing(boolean keyguardShowing) {
        this.mKeyguardShowing = keyguardShowing;
        this.mLastQSExpansion = -1.0f;
        if (this.mQSAnimator != null) {
            this.mQSAnimator.setOnKeyguard(keyguardShowing);
        }
        this.mFooter.setKeyguardShowing(keyguardShowing);
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setOverscrolling(boolean stackScrollerOverscrolling) {
        this.mStackScrollerOverscrolling = stackScrollerOverscrolling;
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setListening(boolean listening) {
        this.mListening = listening;
        this.mHeader.setListening(listening);
        this.mFooter.setListening(listening);
        this.mQSPanel.setListening(this.mListening && this.mQsExpanded);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderListening(boolean listening) {
        this.mHeader.setListening(listening);
        this.mFooter.setListening(listening);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setQsExpansion(float expansion, float headerTranslation) {
        float height;
        this.mContainer.setExpansion(expansion);
        float translationScaleY = expansion - 1.0f;
        if (!this.mHeaderAnimating) {
            View view = getView();
            if (!this.mKeyguardShowing) {
                height = headerTranslation;
            } else {
                height = this.mHeader.getHeight() * translationScaleY;
            }
            view.setTranslationY(height);
        }
        if (expansion == this.mLastQSExpansion) {
            return;
        }
        this.mLastQSExpansion = expansion;
        boolean fullyExpanded = expansion == 1.0f;
        int heightDiff = (this.mQSPanel.getBottom() - this.mHeader.getBottom()) + this.mHeader.getPaddingBottom() + this.mFooter.getHeight();
        float panelTranslationY = heightDiff * translationScaleY;
        this.mHeader.setExpansion(this.mKeyguardShowing, expansion, panelTranslationY);
        this.mFooter.setExpansion(this.mKeyguardShowing ? 1.0f : expansion);
        this.mQSPanel.getQsTileRevealController().setExpansion(expansion);
        this.mQSPanel.getTileLayout().setExpansion(expansion);
        this.mQSPanel.setTranslationY(heightDiff * translationScaleY);
        this.mQSDetail.setFullyExpanded(fullyExpanded);
        if (fullyExpanded) {
            this.mQSPanel.setClipBounds(null);
        } else {
            this.mQsBounds.top = (int) (-this.mQSPanel.getTranslationY());
            this.mQsBounds.right = this.mQSPanel.getWidth();
            this.mQsBounds.bottom = this.mQSPanel.getHeight();
            this.mQSPanel.setClipBounds(this.mQsBounds);
        }
        if (this.mQSAnimator != null) {
            this.mQSAnimator.setPosition(expansion);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void animateHeaderSlidingIn(long delay) {
        if (!this.mQsExpanded) {
            this.mHeaderAnimating = true;
            this.mDelay = delay;
            getView().getViewTreeObserver().addOnPreDrawListener(this.mStartHeaderSlidingIn);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void animateHeaderSlidingOut() {
        this.mHeaderAnimating = true;
        getView().animate().y(-this.mHeader.getHeight()).setStartDelay(0L).setDuration(360L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (QSFragment.this.getView() == null) {
                    Log.e(QS.TAG, "current view is null, return");
                    return;
                }
                QSFragment.this.getView().animate().setListener(null);
                QSFragment.this.mHeaderAnimating = false;
                QSFragment.this.updateQsState();
            }
        }).start();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setExpandClickListener(View.OnClickListener onClickListener) {
        this.mFooter.setExpandClickListener(onClickListener);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void closeDetail() {
        this.mQSPanel.closeDetail();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void notifyCustomizeChanged() {
        this.mContainer.updateExpansion();
        this.mQSPanel.setVisibility(!this.mQSCustomizer.isCustomizing() ? 0 : 4);
        if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && (ExternalParameterManager.ETHEME_SUB_16 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_22 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_37 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB)) {
            this.mFooter.setVisibility(8);
        } else {
            this.mFooter.setVisibility(this.mQSCustomizer.isCustomizing() ? 4 : 0);
        }
        this.mPanelView.onQsHeightChanged();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getDesiredHeight() {
        if (this.mQSCustomizer.isCustomizing()) {
            return getView().getHeight();
        }
        if (this.mQSDetail.isClosingDetail()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mQSPanel.getLayoutParams();
            int panelHeight = layoutParams.topMargin + layoutParams.bottomMargin + this.mQSPanel.getMeasuredHeight();
            return getView().getPaddingBottom() + panelHeight;
        }
        return getView().getMeasuredHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeightOverride(int desiredHeight) {
        this.mContainer.setHeightOverride(desiredHeight);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getQsMinExpansionHeight() {
        return this.mHeader.getHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void hideImmediately() {
        getView().animate().cancel();
        getView().setY(-this.mHeader.getHeight());
    }
}
