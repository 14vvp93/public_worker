package com.android.systemui.qs;

import android.util.Log;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class QSAnimator implements View.OnAttachStateChangeListener, View.OnLayoutChangeListener, PagedTileLayout.PageListener, QSHost.Callback, TouchAnimator.Listener, TunerService.Tunable {
    private boolean mAllowFancy;
    private TouchAnimator mBrightnessAnimator;
    private TouchAnimator mFirstPageAnimator;
    private TouchAnimator mFirstPageDelayedAnimator;
    private boolean mFullRows;
    private QSTileHost mHost;
    private float mLastPosition;
    private TouchAnimator mNonfirstPageAnimator;
    private TouchAnimator mNonfirstPageDelayedAnimator;
    private int mNumQuickTiles;
    private boolean mOnKeyguard;
    private PagedTileLayout mPagedLayout;
    private final QS mQs;
    private final QSPanel mQsPanel;
    private final QuickQSPanel mQuickQsPanel;
    private TouchAnimator mTranslationXAnimator;
    private TouchAnimator mTranslationYAnimator;
    private TouchAnimator mVolumeAnimator;
    private final ArrayList<View> mAllViews = new ArrayList<>();
    private final ArrayList<View> mQuickQsViews = new ArrayList<>();
    private boolean mOnFirstPage = true;
    private final TouchAnimator.Listener mNonFirstPageListener = new TouchAnimator.ListenerAdapter() { // from class: com.android.systemui.qs.QSAnimator.1
        @Override // com.android.systemui.qs.TouchAnimator.ListenerAdapter, com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtEnd() {
            QSAnimator.this.mQuickQsPanel.setVisibility(4);
        }

        @Override // com.android.systemui.qs.TouchAnimator.ListenerAdapter, com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationStarted() {
            QSAnimator.this.mQuickQsPanel.setVisibility(0);
        }
    };
    private Runnable mUpdateAnimators = new Runnable() { // from class: com.android.systemui.qs.QSAnimator.2
        @Override // java.lang.Runnable
        public void run() {
            QSAnimator.this.updateAnimators();
            QSAnimator.this.setPosition(QSAnimator.this.mLastPosition);
        }
    };

    public QSAnimator(QS qs, QuickQSPanel quickPanel, QSPanel panel) {
        this.mQs = qs;
        this.mQuickQsPanel = quickPanel;
        this.mQsPanel = panel;
        this.mQsPanel.addOnAttachStateChangeListener(this);
        qs.getView().addOnLayoutChangeListener(this);
        if (this.mQsPanel.isAttachedToWindow()) {
            onViewAttachedToWindow(null);
        }
        QSPanel.QSTileLayout tileLayout = this.mQsPanel.getTileLayout();
        if (tileLayout instanceof PagedTileLayout) {
            this.mPagedLayout = (PagedTileLayout) tileLayout;
        } else {
            Log.w("QSAnimator", "QS Not using page layout");
        }
        panel.setPageListener(this);
    }

    public void onRtlChanged() {
        updateAnimators();
    }

    public void setOnKeyguard(boolean onKeyguard) {
        this.mOnKeyguard = onKeyguard;
        this.mQuickQsPanel.setVisibility(this.mOnKeyguard ? 4 : 0);
        if (this.mOnKeyguard) {
            clearAnimationState();
        }
    }

    public void setHost(QSTileHost qsh) {
        this.mHost = qsh;
        qsh.addCallback(this);
        updateAnimators();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View v) {
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_qs_fancy_anim", "sysui_qs_move_whole_rows", "sysui_qqs_count");
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View v) {
        if (this.mHost != null) {
            this.mHost.removeCallback(this);
        }
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        boolean z = true;
        if ("sysui_qs_fancy_anim".equals(key)) {
            if (newValue != null && Integer.parseInt(newValue) == 0) {
                z = false;
            }
            this.mAllowFancy = z;
            if (!this.mAllowFancy) {
                clearAnimationState();
            }
        } else if ("sysui_qs_move_whole_rows".equals(key)) {
            if (newValue != null && Integer.parseInt(newValue) == 0) {
                z = false;
            }
            this.mFullRows = z;
        } else if ("sysui_qqs_count".equals(key)) {
            QuickQSPanel quickQSPanel = this.mQuickQsPanel;
            this.mNumQuickTiles = QuickQSPanel.getNumQuickTiles(this.mQs.getContext());
            clearAnimationState();
        }
        updateAnimators();
    }

    @Override // com.android.systemui.qs.PagedTileLayout.PageListener
    public void onPageChanged(boolean isFirst) {
        if (this.mOnFirstPage == isFirst) {
            return;
        }
        if (!isFirst) {
            clearAnimationState();
        }
        this.mOnFirstPage = isFirst;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAnimators() {
        Object obj;
        Iterator<QSTile> it;
        int lastX;
        int height;
        Collection<QSTile> tiles;
        Object obj2;
        TouchAnimator.Builder firstPageBuilder;
        TouchAnimator.Builder firstPageBuilder2 = new TouchAnimator.Builder();
        TouchAnimator.Builder translationXBuilder = new TouchAnimator.Builder();
        TouchAnimator.Builder translationYBuilder = new TouchAnimator.Builder();
        if (this.mQsPanel.getHost() == null) {
            return;
        }
        Collection<QSTile> tiles2 = this.mQsPanel.getHost().getTiles();
        int[] loc1 = new int[2];
        int[] loc2 = new int[2];
        int lastX2 = 0;
        clearAnimationState();
        this.mAllViews.clear();
        this.mQuickQsViews.clear();
        Object tileLayout = this.mQsPanel.getTileLayout();
        this.mAllViews.add((View) tileLayout);
        int height2 = this.mQs.getView() != null ? this.mQs.getView().getMeasuredHeight() : 0;
        int heightDiff = (height2 - this.mQs.getHeader().getBottom()) + this.mQs.getHeader().getPaddingBottom();
        firstPageBuilder2.addFloat(tileLayout, "translationY", heightDiff, 0.0f);
        Iterator<QSTile> it2 = tiles2.iterator();
        int lastXDiff = 0;
        int lastXDiff2 = 0;
        while (it2.hasNext()) {
            QSTile tile = it2.next();
            QSTileView tileView = this.mQsPanel.getTileView(tile);
            if (tileView == null) {
                it = it2;
                lastX = lastX2;
                StringBuilder sb = new StringBuilder();
                height = height2;
                sb.append("tileView is null ");
                sb.append(tile.getTileSpec());
                Log.e("QSAnimator", sb.toString());
            } else {
                it = it2;
                lastX = lastX2;
                height = height2;
                View tileIcon = tileView.getIcon().getIconView();
                View view = this.mQs.getView();
                if (lastXDiff2 < this.mNumQuickTiles && this.mAllowFancy) {
                    QSTileView quickTileView = this.mQuickQsPanel.getTileView(tile);
                    if (quickTileView != null) {
                        lastX = loc1[0];
                        getRelativePosition(loc1, quickTileView.getIcon().getIconView(), view);
                        getRelativePosition(loc2, tileIcon, view);
                        int xDiff = loc2[0] - loc1[0];
                        tiles = tiles2;
                        int yDiff = loc2[1] - loc1[1];
                        int lastXDiff3 = loc1[0] - lastX;
                        obj2 = tileLayout;
                        translationXBuilder.addFloat(quickTileView, "translationX", 0.0f, xDiff);
                        translationYBuilder.addFloat(quickTileView, "translationY", 0.0f, yDiff);
                        translationXBuilder.addFloat(tileView, "translationX", -xDiff, 0.0f);
                        translationYBuilder.addFloat(tileView, "translationY", -yDiff, 0.0f);
                        this.mQuickQsViews.add(tileView.getIconWithBackground());
                        this.mAllViews.add(tileView.getIcon());
                        this.mAllViews.add(quickTileView);
                        lastXDiff = lastXDiff3;
                        firstPageBuilder = firstPageBuilder2;
                    }
                } else {
                    TouchAnimator.Builder firstPageBuilder3 = firstPageBuilder2;
                    tiles = tiles2;
                    obj2 = tileLayout;
                    if (this.mFullRows && isIconInAnimatedRow(lastXDiff2)) {
                        loc1[0] = loc1[0] + lastXDiff;
                        getRelativePosition(loc2, tileIcon, view);
                        int xDiff2 = loc2[0] - loc1[0];
                        int yDiff2 = loc2[1] - loc1[1];
                        firstPageBuilder = firstPageBuilder3;
                        firstPageBuilder.addFloat(tileView, "translationY", heightDiff, 0.0f);
                        translationXBuilder.addFloat(tileView, "translationX", -xDiff2, 0.0f);
                        translationYBuilder.addFloat(tileView, "translationY", -yDiff2, 0.0f);
                        translationYBuilder.addFloat(tileIcon, "translationY", -yDiff2, 0.0f);
                        this.mAllViews.add(tileIcon);
                    } else {
                        firstPageBuilder = firstPageBuilder3;
                        firstPageBuilder.addFloat(tileView, "alpha", 0.0f, 1.0f);
                        firstPageBuilder.addFloat(tileView, "translationY", -heightDiff, 0.0f);
                    }
                }
                this.mAllViews.add(tileView);
                lastXDiff2++;
                firstPageBuilder2 = firstPageBuilder;
                it2 = it;
                lastX2 = lastX;
                height2 = height;
                tiles2 = tiles;
                tileLayout = obj2;
                loc1 = loc1;
            }
            it2 = it;
            lastX2 = lastX;
            height2 = height;
        }
        Collection<QSTile> tiles3 = tiles2;
        Object obj3 = tileLayout;
        TouchAnimator.Builder firstPageBuilder4 = firstPageBuilder2;
        if (this.mAllowFancy) {
            View brightness = this.mQsPanel.getBrightnessView();
            if (brightness != null) {
                firstPageBuilder4.addFloat(brightness, "translationY", heightDiff, 0.0f);
                this.mBrightnessAnimator = new TouchAnimator.Builder().addFloat(brightness, "alpha", 0.0f, 1.0f).setStartDelay(0.5f).build();
                this.mAllViews.add(brightness);
            } else {
                this.mBrightnessAnimator = null;
            }
            View volume = this.mQsPanel.getVolumeView();
            if (volume != null) {
                firstPageBuilder4.addFloat(volume, "translationY", heightDiff, 0.0f);
                this.mVolumeAnimator = new TouchAnimator.Builder().addFloat(volume, "alpha", 0.0f, 1.0f).setStartDelay(0.5f).build();
                this.mAllViews.add(volume);
            } else {
                this.mVolumeAnimator = null;
            }
            this.mFirstPageAnimator = firstPageBuilder4.setListener(this).build();
            obj = obj3;
            this.mFirstPageDelayedAnimator = new TouchAnimator.Builder().setStartDelay(0.86f).addFloat(this.mQsPanel.getPageIndicator(), "alpha", 0.0f, 1.0f).addFloat(obj, "alpha", 0.0f, 1.0f).addFloat(this.mQsPanel.getDivider(), "alpha", 0.0f, 1.0f).addFloat(this.mQsPanel.getFooter().getView(), "alpha", 0.0f, 1.0f).build();
            this.mAllViews.add(this.mQsPanel.getPageIndicator());
            this.mAllViews.add(this.mQsPanel.getDivider());
            this.mAllViews.add(this.mQsPanel.getFooter().getView());
            float px = 0.0f;
            if (tiles3.size() <= 3) {
                px = 1.0f;
            } else if (tiles3.size() <= 6) {
                px = 0.4f;
            }
            PathInterpolatorBuilder interpolatorBuilder = new PathInterpolatorBuilder(0.0f, 0.0f, px, 1.0f);
            translationXBuilder.setInterpolator(interpolatorBuilder.getXInterpolator());
            translationYBuilder.setInterpolator(interpolatorBuilder.getYInterpolator());
            this.mTranslationXAnimator = translationXBuilder.build();
            this.mTranslationYAnimator = translationYBuilder.build();
        } else {
            obj = obj3;
        }
        this.mNonfirstPageAnimator = new TouchAnimator.Builder().addFloat(this.mQuickQsPanel, "alpha", 1.0f, 0.0f).addFloat(this.mQsPanel.getPageIndicator(), "alpha", 0.0f, 1.0f).addFloat(this.mQsPanel.getDivider(), "alpha", 0.0f, 1.0f).setListener(this.mNonFirstPageListener).setEndDelay(0.5f).build();
        this.mNonfirstPageDelayedAnimator = new TouchAnimator.Builder().setStartDelay(0.14f).addFloat(obj, "alpha", 0.0f, 1.0f).build();
    }

    private boolean isIconInAnimatedRow(int count) {
        if (this.mPagedLayout == null) {
            return false;
        }
        int columnCount = this.mPagedLayout.getColumnCount();
        return count < (((this.mNumQuickTiles + columnCount) - 1) / columnCount) * columnCount;
    }

    private void getRelativePosition(int[] loc1, View view, View parent) {
        loc1[0] = (view.getWidth() / 2) + 0;
        loc1[1] = 0;
        getRelativePositionInt(loc1, view, parent);
    }

    private void getRelativePositionInt(int[] loc1, View view, View parent) {
        if (view == parent || view == null) {
            return;
        }
        if (!(view instanceof PagedTileLayout.TilePage)) {
            loc1[0] = loc1[0] + view.getLeft();
            loc1[1] = loc1[1] + view.getTop();
        }
        getRelativePositionInt(loc1, (View) view.getParent(), parent);
    }

    public void setPosition(float position) {
        if (this.mFirstPageAnimator == null || this.mOnKeyguard) {
            return;
        }
        this.mLastPosition = position;
        if (this.mOnFirstPage && this.mAllowFancy) {
            this.mQuickQsPanel.setAlpha(1.0f);
            this.mFirstPageAnimator.setPosition(position);
            this.mFirstPageDelayedAnimator.setPosition(position);
            this.mTranslationXAnimator.setPosition(position);
            this.mTranslationYAnimator.setPosition(position);
            if (this.mBrightnessAnimator != null) {
                this.mBrightnessAnimator.setPosition(position);
            }
            if (this.mVolumeAnimator != null) {
                this.mVolumeAnimator.setPosition(position);
                return;
            }
            return;
        }
        this.mNonfirstPageAnimator.setPosition(position);
        this.mNonfirstPageDelayedAnimator.setPosition(position);
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationAtStart() {
        this.mQuickQsPanel.setVisibility(0);
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationAtEnd() {
        this.mQuickQsPanel.setVisibility(4);
        int N = this.mQuickQsViews.size();
        for (int i = 0; i < N; i++) {
            this.mQuickQsViews.get(i).setVisibility(0);
        }
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationStarted() {
        int i = 0;
        this.mQuickQsPanel.setVisibility(this.mOnKeyguard ? 4 : 0);
        if (this.mOnFirstPage) {
            int N = this.mQuickQsViews.size();
            while (true) {
                int i2 = i;
                if (i2 < N) {
                    this.mQuickQsViews.get(i2).setVisibility(4);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private void clearAnimationState() {
        int N = this.mAllViews.size();
        this.mQuickQsPanel.setAlpha(0.0f);
        for (int i = 0; i < N; i++) {
            View v = this.mAllViews.get(i);
            v.setAlpha(1.0f);
            v.setTranslationX(0.0f);
            v.setTranslationY(0.0f);
        }
        int N2 = this.mQuickQsViews.size();
        for (int i2 = 0; i2 < N2; i2++) {
            this.mQuickQsViews.get(i2).setVisibility(0);
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        this.mQsPanel.post(this.mUpdateAnimators);
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public void onTilesChanged() {
        this.mQsPanel.post(this.mUpdateAnimators);
    }
}
