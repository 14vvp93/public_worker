package com.hcn.widgets.dynamicrecyclerview;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.widgets.dynamicrecyclerview.adapter.AppIconAdapter;
import com.hcn.widgets.dynamicrecyclerview.model.APPItem;
import com.hcn.widgets.dynamicrecyclerview.model.AppIconData;
import com.hcn.widgets.dynamicrecyclerview.utils.SPHelper;
import com.hcn.widgets.dynamicrecyclerview.utils.Utils;
import com.hcn.widgets.dynamicrecyclerview.utils.XmlDeal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public class DynamicAppRecyclerView extends FrameLayout {
    public File configFile;
    Handler initHandler;
    private Drawable mAddIcon;
    private AlertDialog mAlertDialog;
    private AppIconAdapter mAppIconAdapter;
    private List<AppIconData> mAppIconDataList;
    private OnAppIconItemClickListener mClickListener;
    private int mColumnCount;
    public Map<String, String> mConfigMap;
    private Context mContext;
    private AppIconFilter mDefaultIconFilter;
    private AppIconLoader mDefaultIconLoader;
    private RecyclerViewDialog mDialog;
    private Set<ComponentName> mFilterSet;
    private OnAppIconItemLongClickListener mLongClickListener;
    private OnDialogStatusListener mOnDialogStatusListener;
    private int mOrientation;
    private RecyclerView mRecyclerView;
    private AdapterView.OnItemClickListener mRecyclerViewItemClickListener;
    AdapterView.OnItemLongClickListener mRecyclerViewItemLongClickListener;
    private String mSettingKey0;
    public File mSpFile;
    private SPHelper mSpHelper;
    private String save_file_name;

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

    public interface OnDialogStatusListener {
        void onDialogHide();

        void onDialogShow();
    }

    public void setAppIconLoader(AppIconLoader iconLoader) {
        this.mDefaultIconLoader = iconLoader;
    }

    public void setAppIconFilter(AppIconFilter iconFilter) {
        this.mDefaultIconFilter = iconFilter;
    }

    public DynamicAppRecyclerView(Context context) {
        this(context, null, 0, 0);
    }

    public DynamicAppRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DynamicAppRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DynamicAppRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mColumnCount = 0;
        this.mAppIconDataList = new ArrayList();
        this.mFilterSet = new ArraySet();
        this.initHandler = new Handler() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1000) {
                    Log.e("DynamicAppRecyclerView", "initHandler1111:");
                    DynamicAppRecyclerView.this.initConfigMap();
                    DynamicAppRecyclerView.this.initData(DynamicAppRecyclerView.this.save_file_name);
                    DynamicAppRecyclerView.this.RestoreData();
                    if (DynamicAppRecyclerView.this.mAppIconDataList.size() < 20) {
                        DynamicAppRecyclerView.this.putAddItemListTail();
                    }
                    DynamicAppRecyclerView.this.notifyDataSetChanged();
                    Log.e("DynamicAppRecyclerView", "initHandler2222:");
                }
                super.handleMessage(msg);
            }
        };
        this.mRecyclerViewItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    boolean isAddButtonClicked = ((AppIconData) DynamicAppRecyclerView.this.mAppIconDataList.get(position)).getComponentName() == null;
                    ComponentName component = ((AppIconData) DynamicAppRecyclerView.this.mAppIconDataList.get(position)).getComponentName();
                    if (DynamicAppRecyclerView.this.mClickListener == null || !DynamicAppRecyclerView.this.mClickListener.onItemClick(position, component, isAddButtonClicked)) {
                        if (isAddButtonClicked) {
                            DynamicAppRecyclerView.this.handleAddItemClick();
                        } else {
                            Utils.startApp(DynamicAppRecyclerView.this.mContext, component.getPackageName(), component.getClassName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mRecyclerViewItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.4
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isAddButtonClicked = ((AppIconData) DynamicAppRecyclerView.this.mAppIconDataList.get(position)).getComponentName() == null;
                ComponentName component = ((AppIconData) DynamicAppRecyclerView.this.mAppIconDataList.get(position)).getComponentName();
                if ((DynamicAppRecyclerView.this.mLongClickListener == null || !DynamicAppRecyclerView.this.mLongClickListener.onItemLongClick(position, component, isAddButtonClicked)) && !isAddButtonClicked) {
                    DynamicAppRecyclerView.this.removeDataWithNotify(position);
                }
                return true;
            }
        };
        this.mAppIconDataList = new ArrayList();
        this.mFilterSet = new ArraySet();
        this.mContext = context;
        this.mRecyclerView = new RecyclerView(this.mContext);
    }

    public void initAppIconRecycleView(AppIconAdapter adapter, Drawable addIcon, int orientation, String save_file_name) {
        this.mOrientation = orientation;
        this.mAddIcon = addIcon;
        if (this.mColumnCount > 0) {
            this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, this.mColumnCount));
        } else {
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, this.mOrientation, false));
        }
        if (orientation == 1) {
            addView(this.mRecyclerView, new FrameLayout.LayoutParams(-1, -1, 1));
        } else {
            addView(this.mRecyclerView, new FrameLayout.LayoutParams(-1, -1, 16));
        }
        this.mAppIconAdapter = adapter;
        this.mAppIconAdapter.setData(this.mAppIconDataList);
        this.mAppIconAdapter.setOnItemClickListener(this.mRecyclerViewItemClickListener);
        this.mAppIconAdapter.setOnItemLongClickListener(this.mRecyclerViewItemLongClickListener);
        this.mRecyclerView.setAdapter(this.mAppIconAdapter);
        this.save_file_name = save_file_name;
        this.mSettingKey0 = "persist.sys." + save_file_name;
        boolean initSuccess = initConfigMap();
        initData(save_file_name);
        RestoreData();
        if (this.mAppIconDataList.size() < 20) {
            putAddItemListTail();
        }
        this.initHandler.removeMessages(1000);
        if (!initSuccess) {
            this.mConfigMap = null;
            this.mAppIconDataList.clear();
            if (this.mSpFile.exists()) {
                this.mSpFile.delete();
            }
            this.initHandler.sendEmptyMessageDelayed(1000, 3000L);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("configMap==null:");
        sb.append(this.mConfigMap == null);
        Log.e("DynamicAppRecyclerView", sb.toString());
    }

    public DynamicAppRecyclerView setDialog(RecyclerViewDialog dialog) {
        this.mDialog = dialog;
        return this;
    }

    public void create() {
        if (this.mDialog == null) {
            this.mDialog = new RecyclerViewDialog(this.mContext);
            this.mDialog.create();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData(String save_file_name) {
        this.mSpFile = new File(this.mContext.getDataDir().getAbsolutePath() + "/shared_prefs/" + save_file_name + ".xml");
        String[] defaultData = null;
        if (!this.mSpFile.exists()) {
            String str = Settings.System.getString(this.mContext.getContentResolver(), save_file_name);
            Log.e("DynamicAppRecyclerView", "str:" + str);
            if (str != null) {
                defaultData = str.split(" ");
                Log.e("DynamicAppRecyclerView", "str:" + str + " -- defaultData.length: " + defaultData.length);
            } else {
                String str2 = Utils.getSystemProperty(this.mSettingKey0, null);
                if (str2 != null) {
                    defaultData = str2.split(" ");
                    Log.e("DynamicAppRecyclerView", "str:" + str2 + " -- defaultData.length: " + defaultData.length);
                }
            }
        }
        this.mSpHelper = new SPHelper(this.mContext, save_file_name);
        if (defaultData != null && defaultData.length > 0) {
            boolean bApply = false;
            for (int i = 0; i < defaultData.length; i++) {
                String value = mapToComponentString(defaultData[i]);
                Log.e("DynamicAppRecyclerView", "defaultData[i]:" + defaultData[i] + "--value:" + value);
                if (!TextUtils.isEmpty(value)) {
                    Log.e("DynamicAppRecyclerView", "i:" + i + "---value:" + value);
                    this.mSpHelper.put(String.valueOf(i), value);
                    bApply = true;
                }
            }
            if (bApply) {
                this.mSpHelper.apply();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initConfigMap() {
        boolean initSuccess = true;
        if (isDynamicAppListXmlExit()) {
            if (this.mConfigMap == null) {
                this.mConfigMap = new ArrayMap();
                InputStream is = null;
                XmlDeal xmldoc = null;
                try {
                    Log.e("DynamicAppRecyclerView", "Use extra config file:apd/dynamic_app_list.xml");
                    StringBuilder sb = new StringBuilder();
                    sb.append("mConfigMap==null:");
                    sb.append(this.mConfigMap == null);
                    Log.e("DynamicAppRecyclerView", sb.toString());
                    is = new FileInputStream(this.configFile);
                    try {
                        xmldoc = new XmlDeal(is);
                    } catch (Exception e) {
                        Log.e("DynamicAppRecyclerView", "parse the xmlfile is fail");
                        initSuccess = false;
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    Log.e("DynamicAppRecyclerView", "read the xmlfile is fail" + e2.getMessage());
                    initSuccess = false;
                }
                if (xmldoc != null) {
                    for (APPItem appItem : xmldoc.appItemList) {
                        this.mConfigMap.put(appItem.shortName, appItem.packageName + "/" + appItem.className);
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        } else {
            Log.e("DynamicAppRecyclerView", "configFile.exists()");
            if (this.mConfigMap == null) {
                this.mConfigMap = new ArrayMap();
                this.mConfigMap.put("music", "com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.MusicPlayerUiActivity");
                this.mConfigMap.put("video", "com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.VideoPlayerUiActivity");
                this.mConfigMap.put("radio", "com.hcn.autoradio/.RadioMain");
                this.mConfigMap.put("bluetooth", "com.autochips.bluetooth/.MainBluetoothActivity");
                this.mConfigMap.put("btmusic", "com.autochips.bluetooth/.BtMusicActivity");
                this.mConfigMap.put("chrome", "com.android.chrome/com.google.android.apps.chrome.Main");
                this.mConfigMap.put("googlemap", "com.google.android.apps.maps/com.google.android.maps.MapsActivity");
                this.mConfigMap.put("setting", "com.android.settings/.Settings");
                this.mConfigMap.put("playstore", "com.android.vending/.AssetBrowserActivity");
                this.mConfigMap.put("youtube", "com.google.android.youtube/.app.honeycomb.Shell$HomeActivity");
                this.mConfigMap.put("filemanager", "com.mediatek.filemanager/.FileManagerOperationActivity");
                this.mConfigMap.put("autosetting", "com.hcn.autosetting/.ui.SettingActivity");
                this.mConfigMap.put("avin", "com.auto.hcamera/.VideoInActivity");
                this.mConfigMap.put("eq", "com.hcn.autoeq/.EqUIMain");
                this.mConfigMap.put("heq", "com.hcn.autoeq/.MainActivity");
                this.mConfigMap.put("image", "com.hcn.gallery/.ui.ImageScanActivity");
                this.mConfigMap.put("carinfo", "com.hcn.autocan/.activity.CanActivity");
                this.mConfigMap.put("blind", "com.auto.hcamera/.HCameraActivity");
                this.mConfigMap.put("guide", "com.hcn.autouserguide/.UserGuideActivity");
                this.mConfigMap.put("spotify", "com.spotify.music/.libs.loginflow.LoginActivity");
                this.mConfigMap.put("browser", "com.android.browser/.BrowserActivity");
                this.mConfigMap.put("ov_easyconn", "net.easyconn/.ui.MainActivity");
                this.mConfigMap.put("easyconn", "net.easyconn/.ui.StandMainActivity");
                this.mConfigMap.put("zlink_carplay", "com.zjinnova.zlink/.main.view.SplashActivity");
                this.mConfigMap.put("kwmusic", "cn.kuwo.kwmusiccar/.WelcomeActivity");
                this.mConfigMap.put("kwtingshu", "cn.kuwo.tingshucar/.WelcomeActivity");
                this.mConfigMap.put("kaolafm", "com.edog.car/com.kaolafm.auto.home.HubActivity");
                this.mConfigMap.put("waze", "com.waze/.FreeMapAppActivity");
                this.mConfigMap.put("yandexgo", "ru.yandex.taxi/ru.yandex.taxi.activity.MainActivity");
                this.mConfigMap.put("yandex", "ru.yandex.yandexnavi/ru.yandex.yandexnavi.core.NavigatorActivity");
            }
        }
        return initSuccess;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void notifyDataSetChanged() {
        if (this.mAppIconAdapter != null) {
            this.mAppIconAdapter.notifyDataSetChanged();
        }
    }

    public void insertDataWithNotify(AppIconData data) {
        try {
            int pos = this.mAppIconDataList.size() - 1;
            addAppIconData(pos, data);
            this.mAppIconAdapter.notifyItemInserted(pos);
            this.mAppIconAdapter.notifyItemRangeChanged(pos, 1);
            saveDataToSP(pos, true);
            saveShortNameList(this.mAppIconDataList);
            if (this.mAppIconDataList.size() > 20) {
                removeDataWithNotify(this.mAppIconDataList.size() - 1);
            }
        } catch (Exception e) {
        }
    }

    private void saveShortNameList(List<AppIconData> appIconDataList) {
        final StringBuilder shortNameList = new StringBuilder();
        for (final AppIconData appIconData : appIconDataList) {
            StringBuilder sb = new StringBuilder();
            sb.append("appIconData.getComponentName::");
            sb.append(appIconData.getComponentName());
            sb.append("----mConfigMap=");
            sb.append(this.mConfigMap == null);
            Log.e("DynamicAppRecyclerView", sb.toString());
            this.mConfigMap.forEach(new BiConsumer() { // from class: com.hcn.widgets.dynamicrecyclerview.-$$Lambda$DynamicAppRecyclerView$irbLlZRxcBzstsXRFP8Q8Vqu9nA
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    DynamicAppRecyclerView.lambda$saveShortNameList$0(this.f$0, appIconData, shortNameList, (String) obj, (String) obj2);
                }
            });
        }
        Log.e("DynamicAppRecyclerView", shortNameList.toString().trim());
        Settings.System.putString(this.mContext.getContentResolver(), this.save_file_name, shortNameList.toString().trim());
        Utils.setSystemProperty(this.mSettingKey0, shortNameList.toString().trim());
    }

    public static /* synthetic */ void lambda$saveShortNameList$0(DynamicAppRecyclerView dynamicAppRecyclerView, AppIconData appIconData, StringBuilder shortNameList, String key, String value) {
        Log.e("DynamicAppRecyclerView", "key:value:" + key + "---" + value);
        if (dynamicAppRecyclerView.isSameApp(value, appIconData.getComponentName())) {
            Log.e("DynamicAppRecyclerView", "getComponentName::" + appIconData.getComponentName());
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

    public boolean isDynamicAppListXmlExit() {
        this.configFile = new File("apd/dynamic_app_list.xml");
        Log.e("DynamicAppRecyclerView", "configFile.exists()");
        StringBuilder sb = new StringBuilder();
        sb.append("configMap==null:");
        sb.append(this.mConfigMap == null);
        Log.e("DynamicAppRecyclerView", sb.toString());
        return this.configFile.exists();
    }

    public String mapToComponentString(String key) {
        return this.mConfigMap.get(key);
    }

    public void removeDataWithNotify(int pos) {
        if (this.mAppIconDataList.size() == 20) {
            putAddItemListTail();
        }
        removeAppIconData(pos);
        this.mAppIconAdapter.notifyItemRemoved(pos);
        if (pos != this.mAppIconDataList.size()) {
            this.mAppIconAdapter.notifyItemRangeChanged(pos, this.mAppIconDataList.size() - pos);
        }
        int count = this.mAppIconDataList.size() - 1;
        if (this.mAppIconDataList.size() == 20 && !containsAddItem()) {
            count = this.mAppIconDataList.size();
        }
        saveShortNameList(this.mAppIconDataList);
        saveDataToSP(pos, count);
    }

    private void saveDataToSP(int startPos, int count) {
        if (startPos == 0) {
            this.mSpHelper.clear();
        } else {
            this.mSpHelper.remove(String.valueOf(count));
        }
        for (int i = startPos; i < count; i++) {
            saveDataToSP(i, false);
        }
        this.mSpHelper.apply();
    }

    public void saveDataToSP(int pos, boolean withApply) {
        try {
            ComponentName componentName = this.mAppIconDataList.get(pos).getComponentName();
            if (componentName != null) {
                String pkg = componentName.getPackageName();
                String cls = componentName.getClassName().replaceFirst(pkg, "");
                this.mSpHelper.put(String.valueOf(pos), String.format("%s/%s", pkg, cls));
                if (withApply) {
                    this.mSpHelper.apply();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void RestoreData() {
        Map<String, ?> map = this.mSpHelper.getAll();
        boolean errorOccurred = false;
        for (int i = 0; i < map.size(); i++) {
            try {
                String s = (String) map.get(String.valueOf(i));
                String[] array = s.split("/");
                Log.e("DynamicAppRecyclerView", "array:" + array.length);
                if (array[0] != null) {
                    ArrayList<LauncherActivityInfo> infoList = Utils.getInstalledApps(this.mContext, array[0]);
                    if (infoList.size() > 0) {
                        for (LauncherActivityInfo info : infoList) {
                            if (info.getComponentName().getClassName().endsWith(array[1])) {
                                AppIconData data = new AppIconData(info.getLabel().toString(), info.getComponentName(), getIcon(info));
                                addAppIconData(data);
                                break;
                            }
                        }
                    } else {
                        errorOccurred = true;
                    }
                } else {
                    errorOccurred = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorOccurred = true;
            }
        }
        if (errorOccurred) {
            saveDataToSP(0, this.mAppIconDataList.size());
        }
    }

    public Drawable getIcon(LauncherActivityInfo info) {
        if (this.mDefaultIconLoader == null) {
            this.mDefaultIconLoader = new AppIconLoader() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.2
                @Override // com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.AppIconLoader
                public Drawable loadIcon(LauncherActivityInfo info2) {
                    return info2.getIcon(0);
                }
            };
        }
        return this.mDefaultIconLoader.loadIcon(info);
    }

    public boolean accept(LauncherActivityInfo info) {
        boolean result = false;
        if (info == null) {
            return false;
        }
        boolean result2 = !this.mFilterSet.contains(info.getComponentName());
        if (this.mDefaultIconFilter != null) {
            if (result2 && this.mDefaultIconFilter.accept(info)) {
                result = true;
            }
            return result;
        }
        return result2;
    }

    public boolean containsAddItem() {
        return this.mAppIconDataList.size() > 0 && this.mAppIconDataList.get(this.mAppIconDataList.size() - 1).getComponentName() == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putAddItemListTail() {
        if (!containsAddItem()) {
            AppIconData addIcon = new AppIconData("", null, this.mAddIcon);
            addAppIconData(addIcon);
        }
    }

    private void addAppIconData(AppIconData data) {
        addAppIconData(Math.max(this.mAppIconDataList.size(), 0), data);
    }

    private void addAppIconData(int index, AppIconData data) {
        this.mAppIconDataList.add(index, data);
        if (data.getComponentName() != null) {
            this.mFilterSet.add(data.getComponentName());
        }
    }

    private AppIconData removeAppIconData(int index) {
        AppIconData delData = this.mAppIconDataList.remove(index);
        this.mFilterSet.remove(delData.getComponentName());
        return delData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddItemClick() {
        ArrayList<LauncherActivityInfo> appInfoList = Utils.getInstalledApps(this.mContext, null);
        if (this.mDialog == null) {
            ArrayList<LauncherActivityInfo> dialogDataList = new ArrayList<>();
            ArrayList<CharSequence> dialogLabelList = new ArrayList<>();
            for (LauncherActivityInfo info : appInfoList) {
                if (accept(info)) {
                    dialogDataList.add(info);
                    dialogLabelList.add(info.getLabel());
                }
            }
            if (dialogDataList.isEmpty()) {
                Toast.makeText(this.mContext, R.string.no_more_app, 0).show();
                return;
            } else {
                CharSequence[] items = new CharSequence[dialogLabelList.size()];
                showDialogOnlyText(dialogDataList, (CharSequence[]) dialogLabelList.toArray(items));
                return;
            }
        }
        ArrayList<AppIconData> dialogList = new ArrayList<>();
        for (LauncherActivityInfo info2 : appInfoList) {
            if (accept(info2)) {
                AppIconData tmpInfo = new AppIconData(info2.getLabel().toString(), info2.getComponentName(), getIcon(info2));
                dialogList.add(tmpInfo);
            }
        }
        if (dialogList.isEmpty()) {
            Toast.makeText(this.mContext, R.string.no_more_app, 0).show();
        } else {
            showDialogWithIcon(dialogList);
        }
    }

    private void showDialogOnlyText(final List<LauncherActivityInfo> activityInfoList, CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, android.R.style.Theme.DeviceDefault.Dialog);
        try {
            builder.setTitle(R.string.dialog_title);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                LauncherActivityInfo info = (LauncherActivityInfo) activityInfoList.get(which);
                AppIconData tmpInfo = new AppIconData(info.getLabel().toString(), info.getComponentName(), DynamicAppRecyclerView.this.getIcon(info));
                DynamicAppRecyclerView.this.insertDataWithNotify(tmpInfo);
                DynamicAppRecyclerView.this.mRecyclerView.scrollToPosition(DynamicAppRecyclerView.this.mAppIconAdapter.getItemCount() - 1);
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                if (DynamicAppRecyclerView.this.mOnDialogStatusListener != null && DynamicAppRecyclerView.this.isAttachedToWindow()) {
                    DynamicAppRecyclerView.this.mOnDialogStatusListener.onDialogHide();
                }
            }
        });
        this.mAlertDialog = builder.create();
        this.mAlertDialog.getWindow().setType(2038);
        this.mAlertDialog.show();
        if (this.mOnDialogStatusListener != null) {
            this.mOnDialogStatusListener.onDialogShow();
        }
    }

    public void showDialogWithIcon(final List<AppIconData> appIconDataList) {
        this.mDialog.setData(appIconDataList).setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.8
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DynamicAppRecyclerView.this.insertDataWithNotify((AppIconData) appIconDataList.get(position));
                DynamicAppRecyclerView.this.mRecyclerView.scrollToPosition(DynamicAppRecyclerView.this.mAppIconAdapter.getItemCount() - 1);
                DynamicAppRecyclerView.this.mDialog.hide();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.7
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                if (DynamicAppRecyclerView.this.mOnDialogStatusListener != null && DynamicAppRecyclerView.this.isAttachedToWindow()) {
                    DynamicAppRecyclerView.this.mOnDialogStatusListener.onDialogHide();
                }
            }
        }).show();
        if (this.mOnDialogStatusListener != null) {
            this.mOnDialogStatusListener.onDialogShow();
        }
    }
}
