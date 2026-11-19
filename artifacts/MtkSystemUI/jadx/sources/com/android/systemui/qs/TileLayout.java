package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TileLayout extends ViewGroup implements QSPanel.QSTileLayout {
    protected int mCellHeight;
    protected int mCellMarginHorizontal;
    private int mCellMarginTop;
    protected int mCellMarginVertical;
    protected int mCellWidth;
    protected int mColumns;
    private boolean mListening;
    protected final ArrayList<QSPanel.TileRecord> mRecords;
    protected int mSidePadding;

    public TileLayout(Context context) {
        this(context, null);
    }

    public TileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRecords = new ArrayList<>();
        setFocusableInTouchMode(true);
        updateResources();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getOffsetTop(QSPanel.TileRecord tile) {
        return getTop();
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
        this.mRecords.add(tile);
        tile.tile.setListening(this, this.mListening);
        addView(tile.tileView);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanel.TileRecord tile) {
        this.mRecords.remove(tile);
        tile.tile.setListening(this, false);
        removeView(tile.tileView);
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        for (QSPanel.TileRecord record : this.mRecords) {
            record.tile.setListening(this, false);
        }
        this.mRecords.clear();
        super.removeAllViews();
    }

    public boolean updateResources() {
        Resources res = this.mContext.getResources();
        int numColumns = res.getInteger(R.integer.quick_settings_num_columns);
        if (SIMHelper.isWifiOnlyDevice()) {
            numColumns--;
        }
        int columns = Math.max(1, numColumns);
        this.mCellHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_tile_height);
        this.mCellMarginHorizontal = res.getDimensionPixelSize(R.dimen.qs_tile_margin_horizontal);
        this.mCellMarginVertical = res.getDimensionPixelSize(R.dimen.qs_tile_margin_vertical);
        this.mCellMarginTop = res.getDimensionPixelSize(R.dimen.qs_tile_margin_top);
        this.mSidePadding = res.getDimensionPixelOffset(R.dimen.qs_tile_layout_margin_side);
        if (this.mColumns != columns) {
            this.mColumns = columns;
            requestLayout();
            return true;
        }
        return false;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int numTiles = this.mRecords.size();
        int width = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart()) - getPaddingEnd();
        int numRows = ((this.mColumns + numTiles) - 1) / this.mColumns;
        this.mCellWidth = ((width - (this.mSidePadding * 2)) - (this.mCellMarginHorizontal * this.mColumns)) / this.mColumns;
        View previousView = this;
        for (QSPanel.TileRecord record : this.mRecords) {
            if (record.tileView.getVisibility() != 8) {
                record.tileView.measure(exactly(this.mCellWidth), exactly(this.mCellHeight));
                previousView = record.tileView.updateAccessibilityOrder(previousView);
            }
        }
        int height = ((this.mCellHeight + this.mCellMarginVertical) * numRows) + (numRows != 0 ? this.mCellMarginTop - this.mCellMarginVertical : 0);
        if (height < 0) {
            height = 0;
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    private static int exactly(int size) {
        return View.MeasureSpec.makeMeasureSpec(size, 1073741824);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getWidth();
        int i = 0;
        boolean isRtl = getLayoutDirection() == 1;
        int row = 0;
        int column = 0;
        while (i < this.mRecords.size()) {
            if (column == this.mColumns) {
                column = 0;
                row++;
            }
            QSPanel.TileRecord record = this.mRecords.get(i);
            int top = getRowTop(row);
            int left = getColumnStart(isRtl ? (this.mColumns - column) - 1 : column);
            int right = this.mCellWidth + left;
            record.tileView.layout(left, top, right, record.tileView.getMeasuredHeight() + top);
            i++;
            column++;
        }
    }

    private int getRowTop(int row) {
        return ((this.mCellHeight + this.mCellMarginVertical) * row) + this.mCellMarginTop;
    }

    private int getColumnStart(int column) {
        return getPaddingStart() + this.mSidePadding + (this.mCellMarginHorizontal / 2) + ((this.mCellWidth + this.mCellMarginHorizontal) * column);
    }
}
