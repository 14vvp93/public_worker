package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class PagedTileLayout extends ViewPager implements QSPanel.QSTileLayout {
    private static final Interpolator SCROLL_CUBIC = new Interpolator() { // from class: com.android.systemui.qs.-$$Lambda$PagedTileLayout$fHkBmUM3ca-ZV4_eDd9ap-VT7Ho
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return PagedTileLayout.lambda$static$0(f);
        }
    };
    private final PagerAdapter mAdapter;
    private int mAnimatingToPage;
    private AnimatorSet mBounceAnimatorSet;
    private final Runnable mDistribute;
    private float mLastExpansion;
    private boolean mListening;
    private int mNumPages;
    private final ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PageIndicator mPageIndicator;
    private float mPageIndicatorPosition;
    private PageListener mPageListener;
    private final ArrayList<TilePage> mPages;
    private Scroller mScroller;
    private final ArrayList<QSPanel.TileRecord> mTiles;

    public interface PageListener {
        void onPageChanged(boolean z);
    }

    static /* synthetic */ float lambda$static$0(float t) {
        float t2 = t - 1.0f;
        return (t2 * t2 * t2) + 1.0f;
    }

    public PagedTileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTiles = new ArrayList<>();
        this.mPages = new ArrayList<>();
        this.mAnimatingToPage = -1;
        this.mDistribute = new Runnable() { // from class: com.android.systemui.qs.PagedTileLayout.1
            @Override // java.lang.Runnable
            public void run() {
                PagedTileLayout.this.distributeTiles();
            }
        };
        this.mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qs.PagedTileLayout.3
            @Override // android.support.v4.view.ViewPager.SimpleOnPageChangeListener, android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                PagedTileLayout.this.updateSelected();
                if (PagedTileLayout.this.mPageIndicator != null && PagedTileLayout.this.mPageListener != null) {
                    PageListener pageListener = PagedTileLayout.this.mPageListener;
                    boolean z = false;
                    if (!PagedTileLayout.this.isLayoutRtl() ? position == 0 : position == PagedTileLayout.this.mPages.size() - 1) {
                        z = true;
                    }
                    pageListener.onPageChanged(z);
                }
            }

            @Override // android.support.v4.view.ViewPager.SimpleOnPageChangeListener, android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (PagedTileLayout.this.mPageIndicator == null) {
                    return;
                }
                PagedTileLayout.this.mPageIndicatorPosition = position + positionOffset;
                PagedTileLayout.this.mPageIndicator.setLocation(PagedTileLayout.this.mPageIndicatorPosition);
                if (PagedTileLayout.this.mPageListener != null) {
                    PageListener pageListener = PagedTileLayout.this.mPageListener;
                    boolean z = true;
                    if (positionOffsetPixels != 0 || (!PagedTileLayout.this.isLayoutRtl() ? position != 0 : position != PagedTileLayout.this.mPages.size() - 1)) {
                        z = false;
                    }
                    pageListener.onPageChanged(z);
                }
            }
        };
        this.mAdapter = new PagerAdapter() { // from class: com.android.systemui.qs.PagedTileLayout.4
            @Override // android.support.v4.view.PagerAdapter
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                PagedTileLayout.this.updateListening();
            }

            @Override // android.support.v4.view.PagerAdapter
            public Object instantiateItem(ViewGroup container, int position) {
                if (PagedTileLayout.this.isLayoutRtl()) {
                    position = (PagedTileLayout.this.mPages.size() - 1) - position;
                }
                ViewGroup view = (ViewGroup) PagedTileLayout.this.mPages.get(position);
                try {
                    container.addView(view);
                } catch (IllegalStateException e) {
                    Log.e("PagedTileLayout", "Err when add " + view + " to " + position);
                }
                PagedTileLayout.this.updateListening();
                return view;
            }

            @Override // android.support.v4.view.PagerAdapter
            public int getCount() {
                return PagedTileLayout.this.mNumPages;
            }

            @Override // android.support.v4.view.PagerAdapter
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        this.mScroller = new Scroller(context, SCROLL_CUBIC);
        setAdapter(this.mAdapter);
        setOnPageChangeListener(this.mOnPageChangeListener);
        setCurrentItem(0, false);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        setAdapter(this.mAdapter);
        setCurrentItem(0, false);
    }

    @Override // android.support.v4.view.ViewPager
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (isLayoutRtl()) {
            item = (this.mPages.size() - 1) - item;
        }
        super.setCurrentItem(item, smoothScroll);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setListening(boolean listening) {
        if (this.mListening == listening) {
            return;
        }
        this.mListening = listening;
        updateListening();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateListening() {
        for (TilePage tilePage : this.mPages) {
            tilePage.setListening(tilePage.getParent() == null ? false : this.mListening);
        }
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mAnimatingToPage != -1) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mAnimatingToPage != -1) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            float pageFraction = getScrollX() / getWidth();
            int position = (int) pageFraction;
            float positionOffset = pageFraction - position;
            this.mOnPageChangeListener.onPageScrolled(position, positionOffset, getScrollX());
            postInvalidateOnAnimation();
            return;
        }
        if (this.mAnimatingToPage != -1) {
            setCurrentItem(this.mAnimatingToPage, true);
            this.mBounceAnimatorSet.start();
            setOffscreenPageLimit(1);
            this.mAnimatingToPage = -1;
        }
        super.computeScroll();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add((TilePage) LayoutInflater.from(getContext()).inflate(R.layout.qs_paged_page, (ViewGroup) this, false));
    }

    public void setPageIndicator(PageIndicator indicator) {
        this.mPageIndicator = indicator;
        this.mPageIndicator.setNumPages(this.mNumPages);
        this.mPageIndicator.setLocation(this.mPageIndicatorPosition);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getOffsetTop(QSPanel.TileRecord tile) {
        ViewGroup parent = (ViewGroup) tile.tileView.getParent();
        if (parent == null) {
            return 0;
        }
        return parent.getTop() + getTop();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void addTile(QSPanel.TileRecord tile) {
        this.mTiles.add(tile);
        postDistributeTiles();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanel.TileRecord tile) {
        if (this.mTiles.remove(tile)) {
            postDistributeTiles();
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setExpansion(float expansion) {
        this.mLastExpansion = expansion;
        updateSelected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelected() {
        if (this.mLastExpansion > 0.0f && this.mLastExpansion < 1.0f) {
            return;
        }
        boolean selected = this.mLastExpansion == 1.0f;
        setImportantForAccessibility(4);
        int i = 0;
        while (i < this.mPages.size()) {
            this.mPages.get(i).setSelected(i == getCurrentItem() ? selected : false);
            i++;
        }
        setImportantForAccessibility(0);
    }

    public void setPageListener(PageListener listener) {
        this.mPageListener = listener;
    }

    private void postDistributeTiles() {
        removeCallbacks(this.mDistribute);
        post(this.mDistribute);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void distributeTiles() {
        int NP = this.mPages.size();
        for (int i = 0; i < NP; i++) {
            this.mPages.get(i).removeAllViews();
        }
        int NT = this.mTiles.size();
        int index = 0;
        for (int index2 = 0; index2 < NT; index2++) {
            QSPanel.TileRecord tile = this.mTiles.get(index2);
            if (this.mPages.get(index).isFull() && (index = index + 1) == this.mPages.size()) {
                this.mPages.add((TilePage) LayoutInflater.from(getContext()).inflate(R.layout.qs_paged_page, (ViewGroup) this, false));
            }
            this.mPages.get(index).addTile(tile);
        }
        int i2 = this.mNumPages;
        if (i2 != index + 1) {
            this.mNumPages = index + 1;
            while (this.mPages.size() > this.mNumPages) {
                this.mPages.remove(this.mPages.size() - 1);
            }
            this.mPageIndicator.setNumPages(this.mNumPages);
            setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
            setCurrentItem(0, false);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean updateResources() {
        setPadding(0, 0, 0, getContext().getResources().getDimensionPixelSize(R.dimen.qs_paged_tile_layout_padding_bottom));
        boolean changed = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            changed |= this.mPages.get(i).updateResources();
        }
        if (changed) {
            distributeTiles();
        }
        return changed;
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxHeight = 0;
        int N = getChildCount();
        for (int i = 0; i < N; i++) {
            int height = getChildAt(i).getMeasuredHeight();
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        int i2 = getMeasuredWidth();
        setMeasuredDimension(i2, getPaddingBottom() + maxHeight);
    }

    public int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return this.mPages.get(0).mColumns;
    }

    public void startTileReveal(Set<String> tileSpecs, final Runnable postAnimation) {
        if (tileSpecs.isEmpty() || this.mPages.size() < 2 || getScrollX() != 0) {
            return;
        }
        int lastPageNumber = this.mPages.size() - 1;
        TilePage lastPage = this.mPages.get(lastPageNumber);
        ArrayList<Animator> bounceAnims = new ArrayList<>();
        for (QSPanel.TileRecord tr : lastPage.mRecords) {
            if (tileSpecs.contains(tr.tile.getTileSpec())) {
                bounceAnims.add(setupBounceAnimator(tr.tileView, bounceAnims.size()));
            }
        }
        if (bounceAnims.isEmpty()) {
            return;
        }
        this.mBounceAnimatorSet = new AnimatorSet();
        this.mBounceAnimatorSet.playTogether(bounceAnims);
        this.mBounceAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.PagedTileLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PagedTileLayout.this.mBounceAnimatorSet = null;
                postAnimation.run();
            }
        });
        this.mAnimatingToPage = lastPageNumber;
        setOffscreenPageLimit(this.mAnimatingToPage);
        this.mScroller.startScroll(getScrollX(), getScrollY(), this.mAnimatingToPage * getWidth(), 0, 750);
        postInvalidateOnAnimation();
    }

    private static Animator setupBounceAnimator(View view, int ordinal) {
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f));
        animator.setDuration(450L);
        animator.setStartDelay(ordinal * 85);
        animator.setInterpolator(new OvershootInterpolator(1.3f));
        return animator;
    }

    public static class TilePage extends TileLayout {
        private int mMaxRows;

        public TilePage(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mMaxRows = 3;
            updateResources();
        }

        @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.QSPanel.QSTileLayout
        public boolean updateResources() {
            int rows = getRows();
            boolean changed = rows != this.mMaxRows;
            if (changed) {
                this.mMaxRows = rows;
                requestLayout();
            }
            return super.updateResources() || changed;
        }

        private int getRows() {
            return Math.max(1, getResources().getInteger(R.integer.quick_settings_num_rows));
        }

        public boolean isFull() {
            return this.mRecords.size() >= this.mColumns * this.mMaxRows;
        }
    }
}
