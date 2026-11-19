package com.hcn.widgets.dynamicrecyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseViewHolder;
import com.hcn.widgets.dynamicrecyclerview.decoration.AppIconItemDecoration;
import com.hcn.widgets.dynamicrecyclerview.model.AppIconData;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewDialog {
    private AppIconAdapter mAdapter;
    private int mColumnCount;
    private Context mContext;
    private AlertDialog mDialog;
    private Drawable mItemDivider;
    private int mItemHeight;
    private int mItemIconSize;
    private int mItemTextColor;
    private int mItemTextDrawablePadding;
    private int mItemTextSize;
    private int mItemWidth;
    private LinearLayout mLLayout;
    private int mLayoutType = 1;
    private int mOrientation = 1;
    private RecyclerView mRecyclerView;
    private int mRecyclerViewLeftMargin;
    private int mRecyclerViewTopMargin;
    private View mRoot;
    private int mRowCount;
    private TextView mTvTitle;
    private int mWindowHeight;
    private int mWindowWidth;

    public RecyclerViewDialog(Context context) {
        this.mContext = context;
        this.mRoot = LayoutInflater.from(context).inflate(R.layout.layout_rv_dialog, (ViewGroup) null);
        this.mRecyclerView = (RecyclerView) this.mRoot.findViewById(R.id.dialog_rv);
        this.mLLayout = (LinearLayout) this.mRoot.findViewById(R.id.root);
        this.mTvTitle = (TextView) this.mRoot.findViewById(R.id.tv_title);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogNoBg);
        builder.setView(this.mRoot);
        this.mDialog = builder.create();
        if (Build.VERSION.SDK_INT > 26) {
            this.mDialog.getWindow().setType(2038);
        } else {
            this.mDialog.getWindow().setType(2002);
        }
        this.mItemTextColor = this.mContext.getResources().getColor(R.color.white);
    }

    public View getRootView() {
        return this.mRoot;
    }

    public AlertDialog getDialog() {
        return this.mDialog;
    }

    public RecyclerViewDialog setDialogBackground(Drawable drawable) {
        this.mLLayout.setBackground(drawable);
        this.mWindowWidth = drawable.getIntrinsicWidth();
        this.mWindowHeight = drawable.getIntrinsicHeight();
        return this;
    }

    public RecyclerViewDialog setDialogWidth(int width) {
        this.mWindowWidth = width;
        return this;
    }

    public RecyclerViewDialog setDialogHeight(int height) {
        this.mWindowHeight = height;
        return this;
    }

    public RecyclerViewDialog setTitleColor(int color) {
        this.mTvTitle.setTextColor(color);
        return this;
    }

    public RecyclerViewDialog setItemWidth(int width) {
        this.mItemWidth = width;
        return this;
    }

    public RecyclerViewDialog setItemHeight(int height) {
        this.mItemHeight = height;
        return this;
    }

    public RecyclerViewDialog setItemTextSize(int textSize) {
        this.mItemTextSize = textSize;
        return this;
    }

    public RecyclerViewDialog setItemTextColor(int textColor) {
        this.mItemTextColor = textColor;
        return this;
    }

    public RecyclerViewDialog setItemTextDrawablePadding(int drawablePadding) {
        this.mItemTextDrawablePadding = drawablePadding;
        return this;
    }

    public RecyclerViewDialog setItemIconSize(int size) {
        this.mItemIconSize = size;
        return this;
    }

    public RecyclerViewDialog setLayoutType(int layoutType) {
        this.mLayoutType = layoutType;
        return this;
    }

    public RecyclerViewDialog setRowCount(int rowCount) {
        this.mRowCount = rowCount;
        return this;
    }

    public RecyclerViewDialog setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        return this;
    }

    public RecyclerViewDialog setRecyclerViewTopMargin(int topMargin) {
        this.mRecyclerViewTopMargin = topMargin;
        return this;
    }

    public void create() {
        if (this.mWindowWidth == 0) {
            this.mWindowWidth = (int) this.mContext.getResources().getDimension(R.dimen.layout_rv_dialog_width);
        }
        if (this.mWindowHeight == 0) {
            this.mWindowHeight = (int) this.mContext.getResources().getDimension(R.dimen.layout_rv_dialog_height);
        }
        if (this.mLLayout.getBackground() == null) {
            this.mLLayout.setBackgroundResource(R.drawable.dialog_bg);
        }
        if (this.mLayoutType == 0) {
            if (this.mItemWidth <= 0) {
                this.mItemWidth = (int) this.mContext.getResources().getDimension(R.dimen.item_dialog_linear_layout_width);
            }
            if (this.mItemHeight <= 0) {
                this.mItemHeight = (int) this.mContext.getResources().getDimension(R.dimen.item_dialog_linear_layout_height);
            }
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
            AppIconItemDecoration decoration = new AppIconItemDecoration(this.mContext, 1, 0, 1);
            if (this.mItemDivider == null) {
                this.mItemDivider = this.mContext.getResources().getDrawable(R.drawable.icon_divider);
            }
            decoration.setDrawable(this.mItemDivider);
            this.mRecyclerView.addItemDecoration(decoration);
            this.mAdapter = new AppIconAdapter(R.layout.item_dialog_linear);
        } else {
            if (this.mItemWidth <= 0) {
                this.mItemWidth = (int) this.mContext.getResources().getDimension(R.dimen.item_dialog_grid_layout_width);
            }
            if (this.mItemHeight <= 0) {
                this.mItemHeight = (int) this.mContext.getResources().getDimension(R.dimen.item_dialog_grid_layout_height);
            }
            if (this.mRowCount == 0) {
                this.mRowCount = 2;
            }
            if (this.mColumnCount == 0) {
                this.mColumnCount = 5;
            }
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mRecyclerView.getLayoutParams();
            lp.width = this.mItemWidth * this.mColumnCount;
            lp.height = this.mItemHeight * this.mRowCount;
            lp.topMargin = this.mRecyclerViewTopMargin;
            lp.leftMargin = this.mRecyclerViewLeftMargin;
            this.mRecyclerView.setLayoutParams(lp);
            int spanCount = this.mOrientation == 1 ? this.mColumnCount : this.mRowCount;
            this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, spanCount, this.mOrientation, false));
            this.mAdapter = new AppIconAdapter(R.layout.item_dialog_grid);
        }
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public RecyclerViewDialog setData(List<AppIconData> appIconDataList) {
        this.mAdapter.setData(appIconDataList);
        this.mAdapter.notifyDataSetChanged();
        return this;
    }

    public RecyclerViewDialog setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mAdapter.setOnItemClickListener(listener);
        return this;
    }

    public RecyclerViewDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mDialog.setOnDismissListener(listener);
        return this;
    }

    public RecyclerViewDialog show() {
        this.mDialog.show();
        this.mDialog.getWindow().setLayout(this.mWindowWidth, this.mWindowHeight);
        return this;
    }

    public void hide() {
        this.mDialog.dismiss();
    }

    private class AppIconAdapter extends BaseRecyclerViewAdapter<AppIconData> {
        protected AppIconAdapter(int layoutId) {
            super(layoutId);
        }

        @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter
        public void bindView(BaseViewHolder<AppIconData> holder, AppIconData data, int position) {
            ImageView appIcon = (ImageView) holder.getView(R.id.app_icon);
            TextView appName = (TextView) holder.getView(R.id.app_name);
            LinearLayout ll = (LinearLayout) appIcon.getParent();
            LinearLayout.LayoutParams appNameLp = (LinearLayout.LayoutParams) appName.getLayoutParams();
            if (RecyclerViewDialog.this.mLayoutType == 0) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll.getLayoutParams();
                lp.width = RecyclerViewDialog.this.mItemWidth;
                lp.height = RecyclerViewDialog.this.mItemHeight;
                ll.setLayoutParams(lp);
                if (RecyclerViewDialog.this.mItemTextDrawablePadding > 0) {
                    appNameLp.leftMargin = RecyclerViewDialog.this.mItemTextDrawablePadding;
                }
                appName.setMaxWidth(((RecyclerViewDialog.this.mItemWidth - RecyclerViewDialog.this.mItemIconSize) - RecyclerViewDialog.this.mItemTextDrawablePadding) - 20);
            } else {
                GridLayoutManager.LayoutParams lp2 = (GridLayoutManager.LayoutParams) ll.getLayoutParams();
                lp2.width = RecyclerViewDialog.this.mItemWidth;
                lp2.height = RecyclerViewDialog.this.mItemHeight;
                ll.setLayoutParams(lp2);
                if (RecyclerViewDialog.this.mItemTextDrawablePadding > 0) {
                    appNameLp.topMargin = RecyclerViewDialog.this.mItemTextDrawablePadding;
                }
                appName.setMaxWidth(RecyclerViewDialog.this.mItemWidth - 10);
            }
            appName.setLayoutParams(appNameLp);
            if (RecyclerViewDialog.this.mItemIconSize > 0) {
                LinearLayout.LayoutParams appIconLp = (LinearLayout.LayoutParams) appIcon.getLayoutParams();
                appIconLp.width = RecyclerViewDialog.this.mItemIconSize;
                appIconLp.height = RecyclerViewDialog.this.mItemIconSize;
                appIcon.setLayoutParams(appIconLp);
            }
            if (RecyclerViewDialog.this.mItemTextSize > 0) {
                appName.setTextSize(RecyclerViewDialog.this.mItemTextSize);
            }
            appName.setTextColor(RecyclerViewDialog.this.mItemTextColor);
            appIcon.setImageDrawable(data.getAppIcon());
            appName.setText(data.getAppName());
        }
    }
}
