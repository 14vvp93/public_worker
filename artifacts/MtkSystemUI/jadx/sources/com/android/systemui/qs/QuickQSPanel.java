package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class QuickQSPanel extends QSPanel {
    private boolean mDisabledByPolicy;
    protected QSPanel mFullPanel;
    private int mMaxTiles;
    private final TunerService.Tunable mNumTiles;

    public QuickQSPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNumTiles = new TunerService.Tunable() { // from class: com.android.systemui.qs.QuickQSPanel.1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public void onTuningChanged(String key, String newValue) {
                QuickQSPanel.this.setMaxTiles(QuickQSPanel.getNumQuickTiles(QuickQSPanel.this.mContext));
            }
        };
        if (this.mFooter != null) {
            removeView(this.mFooter.getView());
        }
        if (this.mTileLayout != null) {
            for (int i = 0; i < this.mRecords.size(); i++) {
                this.mTileLayout.removeTile(this.mRecords.get(i));
            }
            removeView((View) this.mTileLayout);
        }
        this.mTileLayout = new HeaderTileLayout(context);
        this.mTileLayout.setListening(this.mListening);
        addView((View) this.mTileLayout, 0);
        super.setPadding(0, 0, 0, 0);
    }

    @Override // android.view.View
    public void setPadding(int left, int top, int right, int bottom) {
    }

    @Override // com.android.systemui.qs.QSPanel
    protected void addDivider() {
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this.mNumTiles, "sysui_qqs_count");
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this.mNumTiles);
    }

    public void setQSPanelAndHeader(QSPanel fullPanel, View header) {
        this.mFullPanel = fullPanel;
    }

    @Override // com.android.systemui.qs.QSPanel
    protected boolean shouldShowDetail() {
        return !this.mExpanded;
    }

    @Override // com.android.systemui.qs.QSPanel
    protected void drawTile(QSPanel.TileRecord r, QSTile.State state) {
        if (state instanceof QSTile.SignalState) {
            QSTile.SignalState copy = new QSTile.SignalState();
            state.copyTo(copy);
            copy.activityIn = false;
            copy.activityOut = false;
            state = copy;
        }
        super.drawTile(r, state);
    }

    @Override // com.android.systemui.qs.QSPanel
    public void setHost(QSTileHost host, QSCustomizer customizer) {
        super.setHost(host, customizer);
        setTiles(this.mHost.getTiles());
    }

    public void setMaxTiles(int maxTiles) {
        this.mMaxTiles = maxTiles;
        if (this.mHost != null) {
            setTiles(this.mHost.getTiles());
        }
    }

    @Override // com.android.systemui.qs.QSPanel, com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("qs_show_brightness".equals(key)) {
            super.onTuningChanged(key, "0");
        }
    }

    @Override // com.android.systemui.qs.QSPanel
    public void setTiles(Collection<QSTile> tiles) {
        ArrayList<QSTile> quickTiles = new ArrayList<>();
        for (QSTile tile : tiles) {
            quickTiles.add(tile);
            if (quickTiles.size() == this.mMaxTiles) {
                break;
            }
        }
        super.setTiles(quickTiles, true);
    }

    public static int getNumQuickTiles(Context context) {
        return ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_qqs_count", 6);
    }

    void setDisabledByPolicy(boolean disabled) {
        if (disabled != this.mDisabledByPolicy) {
            this.mDisabledByPolicy = disabled;
            setVisibility(disabled ? 8 : 0);
        }
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        if (this.mDisabledByPolicy) {
            if (getVisibility() == 8) {
                return;
            } else {
                visibility = 8;
            }
        }
        super.setVisibility(visibility);
    }

    private static class HeaderTileLayout extends LinearLayout implements QSPanel.QSTileLayout {
        private boolean mListening;
        protected final ArrayList<QSPanel.TileRecord> mRecords;
        private int mTileDimensionSize;

        public HeaderTileLayout(Context context) {
            super(context);
            this.mRecords = new ArrayList<>();
            setClipChildren(false);
            setClipToPadding(false);
            this.mTileDimensionSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_quick_tile_size);
            setGravity(17);
            setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            setGravity(17);
            LinearLayout.LayoutParams staticSpaceLayoutParams = generateSpaceLayoutParams(this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_quick_tile_space_width));
            int childViewCount = getChildCount();
            for (int i = 0; i < childViewCount; i++) {
                View childView = getChildAt(i);
                if (childView instanceof Space) {
                    childView.setLayoutParams(staticSpaceLayoutParams);
                }
            }
        }

        private LinearLayout.LayoutParams generateSpaceLayoutParams(int spaceWidth) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(spaceWidth, this.mTileDimensionSize);
            if (spaceWidth == 0) {
                lp.weight = 1.0f;
            }
            lp.gravity = 17;
            return lp;
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public void setListening(boolean listening) {
            if (this.mListening == listening) {
                return;
            }
            this.mListening = listening;
            for (QSPanel.TileRecord record : this.mRecords) {
                record.tile.setListening(this, this.mListening);
            }
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public void addTile(QSPanel.TileRecord tile) {
            if (getChildCount() != 0) {
                LinearLayout.LayoutParams spaceLayoutParams = generateSpaceLayoutParams(this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_quick_tile_space_width));
                addView(new Space(this.mContext), getChildCount(), spaceLayoutParams);
            }
            addView(tile.tileView, getChildCount(), generateTileLayoutParams());
            this.mRecords.add(tile);
            tile.tile.setListening(this, this.mListening);
        }

        private LinearLayout.LayoutParams generateTileLayoutParams() {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(this.mTileDimensionSize, this.mTileDimensionSize);
            lp.gravity = 17;
            return lp;
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public void removeTile(QSPanel.TileRecord tile) {
            int childIndex = getChildIndex(tile.tileView);
            removeViewAt(childIndex);
            if (getChildCount() != 0) {
                removeViewAt(childIndex);
            }
            this.mRecords.remove(tile);
            tile.tile.setListening(this, false);
        }

        private int getChildIndex(QSTileView tileView) {
            int childViewCount = getChildCount();
            for (int i = 0; i < childViewCount; i++) {
                if (getChildAt(i) == tileView) {
                    return i;
                }
            }
            return -1;
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public int getOffsetTop(QSPanel.TileRecord tile) {
            return 0;
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public boolean updateResources() {
            return false;
        }

        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.mRecords != null && this.mRecords.size() > 0) {
                View previousView = this;
                for (QSPanel.TileRecord record : this.mRecords) {
                    if (record.tileView.getVisibility() != 8) {
                        previousView = record.tileView.updateAccessibilityOrder(previousView);
                    }
                }
                this.mRecords.get(0).tileView.setAccessibilityTraversalAfter(R.id.alarm_status_collapsed);
                this.mRecords.get(this.mRecords.size() - 1).tileView.setAccessibilityTraversalBefore(R.id.expand_indicator);
            }
        }
    }
}
