package com.hcn.widgets.dynamicrecyclerview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseViewHolder;
import com.hcn.widgets.dynamicrecyclerview.model.AppIconData;
import com.hcn.widgets.dynamicrecyclerview.utils.SPUtils;
import com.hcn.widgets.dynamicrecyclerview.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class CheckBoxRvDialog implements View.OnClickListener {
    private static final String TAG = CheckBoxRvDialog.class.getCanonicalName();
    private String[] defaultData;
    private Drawable mCheckIcon;
    private int mColumnCount;
    public Map<String, String> mConfigMap;
    private Context mContext;
    private AppRecyclerView.AppIconFilter mDefaultIconFilter;
    private AppRecyclerView.AppIconLoader mDefaultIconLoader;
    private AlertDialog mDialog;
    private int mDrawableSize;
    private int mIconGravity;
    private int mItemHeight;
    private int mItemWidth;
    private LinearLayout mLLayout;
    private View.OnClickListener mOnConfirmClickListener;
    private View.OnClickListener mOnItemClickListener;
    private CheckBoxAdapter mSelectAdapter;
    private ArrayList<AppIconData> mSelectedApps;
    private RecyclerView mSelectedRv;
    private ArrayList<String> mSelectedSaveApps;
    private String mSettingKey;
    private String mSettingKey0;
    private String mSpFileName;
    private int mTextColor;
    private int mTextMarginTop;
    private int mTextOverflow;
    private int mTextSize;
    private CheckBoxAdapter mUnselectAdapter;
    private ArrayList<AppIconData> mUnselectedApps;
    private int mWindowHeight;
    private int mWindowWidth;
    private SPUtils spUtils;

    public String mapToComponentString(String key) {
        return this.mConfigMap.get(key);
    }

    public void updateSelectRVLayoutParams() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mSelectedRv.getLayoutParams();
        if (this.mSelectedApps == null || this.mSelectedApps.size() <= 0) {
            if (lp.height == 0) {
                return;
            } else {
                lp.height = 0;
            }
        } else if (lp.height == this.mItemHeight) {
            return;
        } else {
            lp.height = this.mItemHeight;
        }
        lp.width = this.mItemWidth * this.mColumnCount;
        this.mSelectedRv.setLayoutParams(lp);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_confirm) {
            saveSelectedApps(this.mSelectedApps);
            if (this.mOnConfirmClickListener != null) {
                this.mOnConfirmClickListener.onClick(v);
            }
            hideDialog();
            return;
        }
        if (id == R.id.btn_cancel) {
            hideDialog();
        }
    }

    public void saveSelectedApps(ArrayList<AppIconData> appList) {
        this.mSelectedSaveApps = new ArrayList<>();
        for (AppIconData info : appList) {
            if (info.getComponentName() != null) {
                this.mSelectedSaveApps.add(info.getComponentName().flattenToString());
            }
        }
        String apps = new Gson().toJson(this.mSelectedSaveApps);
        this.spUtils.put(this.mSpFileName, apps);
        saveShortNameList(appList);
        Log.d(TAG, "saveSelectedApps success");
        Log.d(TAG, this.mSpFileName + " size : " + this.mSelectedSaveApps.size());
        Log.d(TAG, this.mSpFileName + " : " + apps);
    }

    private void saveShortNameList(List<AppIconData> appIconDataList) {
        final StringBuilder shortNameList = new StringBuilder();
        for (final AppIconData appIconData : appIconDataList) {
            this.mConfigMap.forEach(new BiConsumer() { // from class: com.hcn.widgets.dynamicrecyclerview.-$$Lambda$CheckBoxRvDialog$7xHqjQPILKS9GfcCew_LVApHWWA
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CheckBoxRvDialog.lambda$saveShortNameList$0(this.f$0, appIconData, shortNameList, (String) obj, (String) obj2);
                }
            });
        }
        Log.d(TAG, this.mSettingKey + " : " + shortNameList.toString().trim());
        Settings.System.putString(this.mContext.getContentResolver(), this.mSettingKey, shortNameList.toString().trim());
        Utils.setSystemProperty(this.mSettingKey0, shortNameList.toString().trim());
    }

    public static /* synthetic */ void lambda$saveShortNameList$0(CheckBoxRvDialog checkBoxRvDialog, AppIconData appIconData, StringBuilder shortNameList, String key, String value) {
        Log.d(TAG, "key = " + key + ", value = " + value);
        if (checkBoxRvDialog.isSameApp(value, appIconData.getComponentName())) {
            shortNameList.append(key);
            shortNameList.append(" ");
        }
    }

    private boolean isSameApp(String str, ComponentName componentName) {
        String[] array = str.split("/");
        if (array.length != 2 || array[0] == null || array[0] == "" || componentName == null || !array[0].equals(componentName.getPackageName())) {
            return false;
        }
        if (!array[1].equals(componentName.getClassName()) && !array[1].contains(componentName.getClassName()) && !componentName.getClassName().contains(array[1])) {
            return false;
        }
        return true;
    }

    private void processData() {
        this.mLLayout.post(new Runnable() { // from class: com.hcn.widgets.dynamicrecyclerview.CheckBoxRvDialog.3
            @Override // java.lang.Runnable
            public void run() {
                ArrayList<LauncherActivityInfo> installedApps = Utils.getInstalledApps(CheckBoxRvDialog.this.mContext, null);
                CheckBoxRvDialog.this.mSelectedSaveApps = (ArrayList) new Gson().fromJson(CheckBoxRvDialog.this.spUtils.getString(CheckBoxRvDialog.this.mSpFileName, null), new TypeToken<List<String>>() { // from class: com.hcn.widgets.dynamicrecyclerview.CheckBoxRvDialog.3.1
                }.getType());
                if (CheckBoxRvDialog.this.mSelectedSaveApps == null && CheckBoxRvDialog.this.defaultData != null && CheckBoxRvDialog.this.defaultData.length > 0) {
                    CheckBoxRvDialog.this.mSelectedSaveApps = new ArrayList();
                    for (int i = 0; i < CheckBoxRvDialog.this.defaultData.length; i++) {
                        String key = CheckBoxRvDialog.this.defaultData[i];
                        String value = CheckBoxRvDialog.this.mapToComponentString(key);
                        Log.d(CheckBoxRvDialog.TAG, "key = " + key + ", value = " + value);
                        if (value != null) {
                            CheckBoxRvDialog.this.mSelectedSaveApps.add(CheckBoxRvDialog.this.mapToComponentString(CheckBoxRvDialog.this.defaultData[i]));
                        }
                    }
                }
                CheckBoxRvDialog.this.mSelectedApps = new ArrayList();
                CheckBoxRvDialog.this.mUnselectedApps = new ArrayList();
                for (LauncherActivityInfo appInfo : installedApps) {
                    if (CheckBoxRvDialog.this.accept(appInfo)) {
                        AppIconData tmpInfo = new AppIconData(appInfo.getLabel().toString(), appInfo.getComponentName(), CheckBoxRvDialog.this.getIcon(appInfo));
                        if (CheckBoxRvDialog.this.mSelectedSaveApps == null || !CheckBoxRvDialog.this.mSelectedSaveApps.contains(appInfo.getComponentName().flattenToString())) {
                            CheckBoxRvDialog.this.mUnselectedApps.add(tmpInfo);
                        } else {
                            CheckBoxRvDialog.this.mSelectedApps.add(tmpInfo);
                        }
                    }
                }
                CheckBoxRvDialog.this.mSelectAdapter.setList(CheckBoxRvDialog.this.mSelectedApps);
                CheckBoxRvDialog.this.mUnselectAdapter.setList(CheckBoxRvDialog.this.mUnselectedApps);
                CheckBoxRvDialog.this.updateSelectRVLayoutParams();
                Log.d(CheckBoxRvDialog.TAG, "installedApps size = " + installedApps.size());
                Log.d(CheckBoxRvDialog.TAG, "mUnselectedApps size = " + CheckBoxRvDialog.this.mUnselectedApps.size());
                String str = CheckBoxRvDialog.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("mSelectedSaveApps size = ");
                sb.append(CheckBoxRvDialog.this.mSelectedSaveApps == null ? 0 : CheckBoxRvDialog.this.mSelectedSaveApps.size());
                Log.d(str, sb.toString());
                String str2 = CheckBoxRvDialog.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("mSelectedApps size = ");
                sb2.append(CheckBoxRvDialog.this.mSelectedApps != null ? CheckBoxRvDialog.this.mSelectedApps.size() : 0);
                Log.d(str2, sb2.toString());
            }
        });
    }

    public void showDialog() {
        processData();
        this.mDialog.show();
        this.mDialog.getWindow().setLayout(this.mWindowWidth, this.mWindowHeight);
    }

    public void hideDialog() {
        this.mDialog.hide();
    }

    public Drawable getIcon(LauncherActivityInfo info) {
        if (this.mDefaultIconLoader == null) {
            this.mDefaultIconLoader = new AppRecyclerView.AppIconLoader() { // from class: com.hcn.widgets.dynamicrecyclerview.CheckBoxRvDialog.4
                @Override // com.hcn.widgets.dynamicrecyclerview.AppRecyclerView.AppIconLoader
                public Drawable loadIcon(LauncherActivityInfo info2) {
                    return info2.getIcon(0);
                }
            };
        }
        return this.mDefaultIconLoader.loadIcon(info);
    }

    public boolean accept(LauncherActivityInfo info) {
        boolean z = false;
        if (info == null) {
            return false;
        }
        if (this.mDefaultIconFilter == null) {
            return true;
        }
        if (1 != 0 && this.mDefaultIconFilter.accept(info)) {
            z = true;
        }
        boolean result = z;
        return result;
    }

    private class CheckBoxAdapter extends BaseRecyclerViewAdapter<AppIconData> {
        private boolean checked;
        final /* synthetic */ CheckBoxRvDialog this$0;

        public void setList(List<AppIconData> data) {
            setData(data);
            notifyDataSetChanged();
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        public void addData(AppIconData appIconData) {
            this.mData.add((T) appIconData);
            notifyDataSetChanged();
        }

        public void removeData(AppIconData data) {
            this.mData.remove(data);
            notifyDataSetChanged();
        }

        @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter
        public void bindView(BaseViewHolder<AppIconData> holder, final AppIconData appInfo, int position) {
            CustomCheckBox checkBox = (CustomCheckBox) holder.getView(R.id.checkbox);
            FrameLayout fl = (FrameLayout) checkBox.getParent();
            if (this.checked) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) fl.getLayoutParams();
                lp.width = this.this$0.mItemWidth > 0 ? this.this$0.mItemWidth : -2;
                lp.height = this.this$0.mItemHeight > 0 ? this.this$0.mItemHeight : -2;
                fl.setLayoutParams(lp);
            } else {
                GridLayoutManager.LayoutParams lp2 = (GridLayoutManager.LayoutParams) fl.getLayoutParams();
                lp2.width = this.this$0.mItemWidth > 0 ? this.this$0.mItemWidth : -2;
                lp2.height = this.this$0.mItemHeight > 0 ? this.this$0.mItemHeight : -2;
                fl.setLayoutParams(lp2);
            }
            if (this.this$0.mDrawableSize > 0) {
                checkBox.setDrawableSize(this.this$0.mDrawableSize);
            }
            checkBox.setDrawable(appInfo.getAppIcon());
            if (this.this$0.mCheckIcon != null) {
                checkBox.setCheckIcon(this.this$0.mCheckIcon);
            }
            checkBox.setIconGravity(this.this$0.mIconGravity);
            if (this.this$0.mTextSize > 0) {
                checkBox.setTextSize(this.this$0.mTextSize);
            }
            checkBox.setTextColor(this.this$0.mTextColor);
            checkBox.setTextMarginTop(this.this$0.mTextMarginTop);
            if (this.this$0.mTextOverflow > 0) {
                checkBox.setPadding(this.this$0.mTextOverflow);
            }
            checkBox.setText(appInfo.getAppName());
            checkBox.setChecked(this.checked);
            checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.CheckBoxRvDialog.CheckBoxAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (CheckBoxAdapter.this.checked) {
                        CheckBoxAdapter.this.this$0.mSelectAdapter.removeData(appInfo);
                        CheckBoxAdapter.this.this$0.mUnselectAdapter.addData(appInfo);
                    } else {
                        CheckBoxAdapter.this.this$0.mSelectAdapter.addData(appInfo);
                        CheckBoxAdapter.this.this$0.mSelectedRv.smoothScrollToPosition(CheckBoxAdapter.this.this$0.mSelectAdapter.getItemCount());
                        CheckBoxAdapter.this.this$0.mUnselectAdapter.removeData(appInfo);
                    }
                    CheckBoxAdapter.this.this$0.updateSelectRVLayoutParams();
                    if (CheckBoxAdapter.this.this$0.mOnItemClickListener != null) {
                        CheckBoxAdapter.this.this$0.mOnItemClickListener.onClick(v);
                    }
                }
            });
        }
    }
}
