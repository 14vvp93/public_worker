package com.hcn.widgets.dynamicrecyclerview;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.widgets.dynamicrecyclerview.model.AppIconData;
import com.hcn.widgets.dynamicrecyclerview.utils.Utils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AppRecyclerView extends FrameLayout {
    private ArrayList<AppIconData> mAppIconDataList;
    private OnAppIconItemClickListener mClickListener;
    private int mColumnCount;
    private Context mContext;
    private CheckBoxRvDialog mDialog;
    private Handler mHandler;
    private OnAppIconItemLongClickListener mLongClickListener;
    private int mOrientation;
    private ArrayList<AppIconData> mPresetDataList;
    private RecyclerView mRecyclerView;
    private AdapterView.OnItemClickListener mRecyclerViewItemClickListener;
    AdapterView.OnItemLongClickListener mRecyclerViewItemLongClickListener;

    public interface AppIconFilter {
        boolean accept(LauncherActivityInfo launcherActivityInfo);
    }

    public interface AppIconLoader {
        Drawable loadIcon(LauncherActivityInfo launcherActivityInfo);
    }

    public interface OnAppIconItemClickListener {
        boolean onItemClick(int i, ComponentName componentName, boolean z);
    }

    public interface OnAppIconItemLongClickListener {
        boolean onItemLongClick(int i, ComponentName componentName, boolean z);
    }

    public AppRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mColumnCount = 0;
        this.mOrientation = 0;
        this.mAppIconDataList = new ArrayList<>();
        this.mPresetDataList = new ArrayList<>();
        this.mHandler = new Handler();
        this.mRecyclerViewItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.AppRecyclerView.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    boolean isAddButtonClicked = ((AppIconData) AppRecyclerView.this.mAppIconDataList.get(position)).getComponentName() == null;
                    ComponentName component = ((AppIconData) AppRecyclerView.this.mAppIconDataList.get(position)).getComponentName();
                    if (isAddButtonClicked) {
                        AppRecyclerView.this.mDialog.showDialog();
                    } else if (AppRecyclerView.this.mClickListener != null) {
                        AppRecyclerView.this.mClickListener.onItemClick(position, component, false);
                    } else {
                        Utils.startApp(AppRecyclerView.this.mContext, component.getPackageName(), component.getClassName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mRecyclerViewItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.AppRecyclerView.4
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isAddButtonClicked = ((AppIconData) AppRecyclerView.this.mAppIconDataList.get(position)).getComponentName() == null;
                ComponentName component = ((AppIconData) AppRecyclerView.this.mAppIconDataList.get(position)).getComponentName();
                AppRecyclerView.this.mDialog.showDialog();
                if (AppRecyclerView.this.mLongClickListener != null) {
                    AppRecyclerView.this.mLongClickListener.onItemLongClick(position, component, isAddButtonClicked);
                }
                return true;
            }
        };
        this.mContext = context;
        this.mRecyclerView = new RecyclerView(this.mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
