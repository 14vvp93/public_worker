package com.android.systemui.qs.customize;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TileAdapter extends RecyclerView.Adapter<Holder> implements TileQueryHelper.TileStateListener {
    private int mAccessibilityFromIndex;
    private final AccessibilityManager mAccessibilityManager;
    private List<TileQueryHelper.TileInfo> mAllTiles;
    private final Context mContext;
    private Holder mCurrentDrag;
    private List<String> mCurrentSpecs;
    private final RecyclerView.ItemDecoration mDecoration;
    private int mEditIndex;
    private QSTileHost mHost;
    private boolean mNeedsFocus;
    private List<TileQueryHelper.TileInfo> mOtherTiles;
    private int mTileDividerIndex;
    private final Handler mHandler = new Handler();
    private final List<TileQueryHelper.TileInfo> mTiles = new ArrayList();
    private int mAccessibilityAction = 0;
    private final GridLayoutManager.SpanSizeLookup mSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.qs.customize.TileAdapter.5
        @Override // android.support.v7.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int position) {
            int numColumns = TileAdapter.this.mContext.getResources().getInteger(R.integer.quick_settings_num_columns);
            if (SIMHelper.isWifiOnlyDevice()) {
                numColumns--;
            }
            int type = TileAdapter.this.getItemViewType(position);
            if (type == 1 || type == 4) {
                return numColumns;
            }
            return 1;
        }
    };
    private final ItemTouchHelper.Callback mCallbacks = new ItemTouchHelper.Callback() { // from class: com.android.systemui.qs.customize.TileAdapter.6
        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != 2) {
                viewHolder = null;
            }
            if (viewHolder == TileAdapter.this.mCurrentDrag) {
                return;
            }
            if (TileAdapter.this.mCurrentDrag != null) {
                int position = TileAdapter.this.mCurrentDrag.getAdapterPosition();
                TileQueryHelper.TileInfo info = (TileQueryHelper.TileInfo) TileAdapter.this.mTiles.get(position);
                TileAdapter.this.mCurrentDrag.mTileView.setShowAppLabel(position > TileAdapter.this.mEditIndex && !info.isSystem);
                TileAdapter.this.mCurrentDrag.stopDrag();
                TileAdapter.this.mCurrentDrag = null;
            }
            if (viewHolder != null) {
                TileAdapter.this.mCurrentDrag = (Holder) viewHolder;
                TileAdapter.this.mCurrentDrag.startDrag();
            }
            TileAdapter.this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.TileAdapter.6.1
                @Override // java.lang.Runnable
                public void run() {
                    TileAdapter.this.notifyItemChanged(TileAdapter.this.mEditIndex);
                }
            });
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
            if (TileAdapter.this.canRemoveTiles() || current.getAdapterPosition() >= TileAdapter.this.mEditIndex) {
                return target.getAdapterPosition() <= TileAdapter.this.mEditIndex + 1;
            }
            return target.getAdapterPosition() < TileAdapter.this.mEditIndex;
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 4) {
                int dragFlags = makeMovementFlags(0, 0);
                return dragFlags;
            }
            return makeMovementFlags(15, 0);
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            return TileAdapter.this.move(from, to, target.itemView);
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }
    };
    private final ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(this.mCallbacks);

    public TileAdapter(Context context) {
        this.mContext = context;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mDecoration = new TileItemDecoration(context);
    }

    public void setHost(QSTileHost host) {
        this.mHost = host;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return this.mItemTouchHelper;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return this.mDecoration;
    }

    public void saveSpecs(QSTileHost host) {
        List<String> newSpecs = new ArrayList<>();
        for (int i = 0; i < this.mTiles.size() && this.mTiles.get(i) != null; i++) {
            newSpecs.add(this.mTiles.get(i).spec);
        }
        host.changeTiles(this.mCurrentSpecs, newSpecs);
        this.mCurrentSpecs = newSpecs;
    }

    public void resetTileSpecs(QSTileHost host, List<String> specs) {
        host.changeTiles(this.mCurrentSpecs, specs);
        setTileSpecs(specs);
    }

    public void setTileSpecs(List<String> currentSpecs) {
        if (currentSpecs.equals(this.mCurrentSpecs)) {
            return;
        }
        this.mCurrentSpecs = currentSpecs;
        recalcSpecs();
    }

    @Override // com.android.systemui.qs.customize.TileQueryHelper.TileStateListener
    public void onTilesChanged(List<TileQueryHelper.TileInfo> tiles) {
        this.mAllTiles = tiles;
        recalcSpecs();
    }

    private void recalcSpecs() {
        if (this.mCurrentSpecs == null || this.mAllTiles == null) {
            return;
        }
        this.mOtherTiles = new ArrayList(this.mAllTiles);
        this.mTiles.clear();
        int i = 0;
        for (int i2 = 0; i2 < this.mCurrentSpecs.size(); i2++) {
            TileQueryHelper.TileInfo tile = getAndRemoveOther(this.mCurrentSpecs.get(i2));
            if (tile != null) {
                this.mTiles.add(tile);
            }
        }
        this.mTiles.add(null);
        while (i < this.mOtherTiles.size()) {
            TileQueryHelper.TileInfo tile2 = this.mOtherTiles.get(i);
            if (tile2.isSystem) {
                this.mOtherTiles.remove(i);
                this.mTiles.add(tile2);
                i--;
            }
            i++;
        }
        this.mTileDividerIndex = this.mTiles.size();
        this.mTiles.add(null);
        this.mTiles.addAll(this.mOtherTiles);
        updateDividerLocations();
        notifyDataSetChanged();
    }

    private TileQueryHelper.TileInfo getAndRemoveOther(String s) {
        for (int i = 0; i < this.mOtherTiles.size(); i++) {
            if (this.mOtherTiles.get(i).spec.equals(s)) {
                return this.mOtherTiles.remove(i);
            }
        }
        return null;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (this.mAccessibilityAction == 1 && position == this.mEditIndex - 1) {
            return 2;
        }
        if (position == this.mTileDividerIndex) {
            return 4;
        }
        return this.mTiles.get(position) == null ? 1 : 0;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 4) {
            return new Holder(inflater.inflate(R.layout.qs_customize_tile_divider, parent, false));
        }
        if (viewType != 1) {
            FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.qs_customize_tile_frame, parent, false);
            frame.addView(new CustomizeTileView(context, new QSIconViewImpl(context)));
            return new Holder(frame);
        }
        return new Holder(inflater.inflate(R.layout.qs_customize_divider, parent, false));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mTiles.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(Holder holder) {
        holder.clearDrag();
        return true;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(final Holder holder, int position) {
        int titleResId;
        if (holder.getItemViewType() == 4) {
            holder.itemView.setVisibility(this.mTileDividerIndex < this.mTiles.size() - 1 ? 0 : 4);
            return;
        }
        if (holder.getItemViewType() == 1) {
            if (this.mCurrentDrag == null) {
                titleResId = R.string.drag_to_add_tiles;
            } else if (!canRemoveTiles() && this.mCurrentDrag.getAdapterPosition() < this.mEditIndex) {
                titleResId = R.string.drag_to_remove_disabled;
            } else {
                titleResId = R.string.drag_to_remove_tiles;
            }
            ((TextView) holder.itemView.findViewById(android.R.id.title)).setText(titleResId);
            return;
        }
        int titleResId2 = holder.getItemViewType();
        if (titleResId2 != 2) {
            TileQueryHelper.TileInfo info = this.mTiles.get(position);
            if (position > this.mEditIndex) {
                info.state.contentDescription = this.mContext.getString(R.string.accessibility_qs_edit_add_tile_label, info.state.label);
            } else if (this.mAccessibilityAction != 0) {
                info.state.contentDescription = this.mContext.getString(R.string.accessibility_qs_edit_position_label, Integer.valueOf(position + 1));
            } else {
                info.state.contentDescription = this.mContext.getString(R.string.accessibility_qs_edit_tile_label, Integer.valueOf(position + 1), info.state.label);
            }
            holder.mTileView.handleStateChanged(info.state);
            holder.mTileView.setShowAppLabel(position > this.mEditIndex && !info.isSystem);
            if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
                boolean selectable = this.mAccessibilityAction == 0 || position < this.mEditIndex;
                holder.mTileView.setClickable(selectable);
                holder.mTileView.setFocusable(selectable);
                holder.mTileView.setImportantForAccessibility(selectable ? 1 : 4);
                if (!selectable) {
                    return;
                }
                holder.mTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        int position2 = holder.getAdapterPosition();
                        if (TileAdapter.this.mAccessibilityAction != 0) {
                            TileAdapter.this.selectPosition(position2, v);
                        } else if (position2 >= TileAdapter.this.mEditIndex || !TileAdapter.this.canRemoveTiles()) {
                            TileAdapter.this.startAccessibleAdd(position2);
                        } else {
                            TileAdapter.this.showAccessibilityDialog(position2, v);
                        }
                    }
                });
                return;
            }
            return;
        }
        holder.mTileView.setClickable(true);
        holder.mTileView.setFocusable(true);
        holder.mTileView.setFocusableInTouchMode(true);
        holder.mTileView.setVisibility(0);
        holder.mTileView.setImportantForAccessibility(1);
        holder.mTileView.setContentDescription(this.mContext.getString(R.string.accessibility_qs_edit_position_label, Integer.valueOf(position + 1)));
        holder.mTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TileAdapter.this.selectPosition(holder.getAdapterPosition(), v);
            }
        });
        if (!this.mNeedsFocus) {
            return;
        }
        holder.mTileView.requestLayout();
        holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.customize.TileAdapter.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                holder.mTileView.removeOnLayoutChangeListener(this);
                holder.mTileView.requestFocus();
            }
        });
        this.mNeedsFocus = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canRemoveTiles() {
        return this.mCurrentSpecs.size() > 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectPosition(int position, View v) {
        if (this.mAccessibilityAction == 1) {
            List<TileQueryHelper.TileInfo> list = this.mTiles;
            int i = this.mEditIndex;
            this.mEditIndex = i - 1;
            list.remove(i);
            notifyItemRemoved(this.mEditIndex);
            if (position == this.mEditIndex - 1) {
                position--;
            }
        }
        this.mAccessibilityAction = 0;
        move(this.mAccessibilityFromIndex, position, v);
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAccessibilityDialog(final int position, final View v) {
        final TileQueryHelper.TileInfo info = this.mTiles.get(position);
        CharSequence[] options = {this.mContext.getString(R.string.accessibility_qs_edit_move_tile, info.state.label), this.mContext.getString(R.string.accessibility_qs_edit_remove_tile, info.state.label)};
        AlertDialog dialog = new AlertDialog.Builder(this.mContext).setItems(options, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog2, int which) {
                if (which == 0) {
                    TileAdapter.this.startAccessibleMove(position);
                    return;
                }
                TileAdapter.this.move(position, info.isSystem ? TileAdapter.this.mEditIndex : TileAdapter.this.mTileDividerIndex, v);
                TileAdapter.this.notifyItemChanged(TileAdapter.this.mTileDividerIndex);
                TileAdapter.this.notifyDataSetChanged();
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        SystemUIDialog.setShowForAllUsers(dialog, true);
        SystemUIDialog.applyFlags(dialog);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAccessibleAdd(int position) {
        this.mAccessibilityFromIndex = position;
        this.mAccessibilityAction = 1;
        List<TileQueryHelper.TileInfo> list = this.mTiles;
        int i = this.mEditIndex;
        this.mEditIndex = i + 1;
        list.add(i, null);
        this.mNeedsFocus = true;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAccessibleMove(int position) {
        this.mAccessibilityFromIndex = position;
        this.mAccessibilityAction = 2;
        notifyDataSetChanged();
    }

    public GridLayoutManager.SpanSizeLookup getSizeLookup() {
        return this.mSizeLookup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean move(int from, int to, View v) {
        if (to == from) {
            return true;
        }
        CharSequence fromLabel = this.mTiles.get(from).state.label;
        move(from, to, this.mTiles);
        updateDividerLocations();
        if (to >= this.mEditIndex) {
            MetricsLogger.action(this.mContext, 360, strip(this.mTiles.get(to)));
            MetricsLogger.action(this.mContext, 361, from);
        } else if (from >= this.mEditIndex) {
            MetricsLogger.action(this.mContext, 362, strip(this.mTiles.get(to)));
            MetricsLogger.action(this.mContext, 363, to);
            v.announceForAccessibility(this.mContext.getString(R.string.accessibility_qs_edit_tile_added, fromLabel, Integer.valueOf(to + 1)));
        } else {
            MetricsLogger.action(this.mContext, 364, strip(this.mTiles.get(to)));
            MetricsLogger.action(this.mContext, 365, to);
            v.announceForAccessibility(this.mContext.getString(R.string.accessibility_qs_edit_tile_moved, fromLabel, Integer.valueOf(to + 1)));
        }
        saveSpecs(this.mHost);
        return true;
    }

    private void updateDividerLocations() {
        this.mEditIndex = -1;
        this.mTileDividerIndex = this.mTiles.size();
        for (int i = 0; i < this.mTiles.size(); i++) {
            if (this.mTiles.get(i) == null) {
                if (this.mEditIndex == -1) {
                    this.mEditIndex = i;
                } else {
                    this.mTileDividerIndex = i;
                }
            }
        }
        if (this.mTiles.size() - 1 == this.mTileDividerIndex) {
            notifyItemChanged(this.mTileDividerIndex);
        }
    }

    private static String strip(TileQueryHelper.TileInfo tileInfo) {
        String spec = tileInfo.spec;
        if (spec.startsWith("custom(")) {
            ComponentName component = CustomTile.getComponentFromSpec(spec);
            return component.getPackageName();
        }
        return spec;
    }

    private <T> void move(int from, int to, List<T> list) {
        list.add(to, list.remove(from));
        notifyItemMoved(from, to);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CustomizeTileView mTileView;

        public Holder(View itemView) {
            super(itemView);
            if (itemView instanceof FrameLayout) {
                this.mTileView = (CustomizeTileView) ((FrameLayout) itemView).getChildAt(0);
                this.mTileView.setBackground(null);
                this.mTileView.getIcon().disableAnimation();
            }
        }

        public void clearDrag() {
            this.itemView.clearAnimation();
            this.mTileView.findViewById(R.id.tile_label).clearAnimation();
            this.mTileView.findViewById(R.id.tile_label).setAlpha(1.0f);
            this.mTileView.getAppLabel().clearAnimation();
            this.mTileView.getAppLabel().setAlpha(0.6f);
        }

        public void startDrag() {
            this.itemView.animate().setDuration(100L).scaleX(1.2f).scaleY(1.2f);
            this.mTileView.findViewById(R.id.tile_label).animate().setDuration(100L).alpha(0.0f);
            this.mTileView.getAppLabel().animate().setDuration(100L).alpha(0.0f);
        }

        public void stopDrag() {
            this.itemView.animate().setDuration(100L).scaleX(1.0f).scaleY(1.0f);
            this.mTileView.findViewById(R.id.tile_label).animate().setDuration(100L).alpha(1.0f);
            this.mTileView.getAppLabel().animate().setDuration(100L).alpha(0.6f);
        }
    }

    private class TileItemDecoration extends RecyclerView.ItemDecoration {
        private final ColorDrawable mDrawable;

        private TileItemDecoration(Context context) {
            TypedArray ta = context.obtainStyledAttributes(new int[]{android.R.attr.colorSecondary});
            this.mDrawable = new ColorDrawable(ta.getColor(0, 0));
            ta.recycle();
        }

        @Override // android.support.v7.widget.RecyclerView.ItemDecoration
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int childCount = parent.getChildCount();
            int width = parent.getWidth();
            int bottom = parent.getBottom();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.ViewHolder holder = parent.getChildViewHolder(child);
                if (holder.getAdapterPosition() >= TileAdapter.this.mEditIndex || (child instanceof TextView)) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getTop() + params.topMargin + Math.round(ViewCompat.getTranslationY(child));
                    this.mDrawable.setBounds(0, top, width, bottom);
                    this.mDrawable.draw(c);
                    return;
                }
            }
        }
    }
}
