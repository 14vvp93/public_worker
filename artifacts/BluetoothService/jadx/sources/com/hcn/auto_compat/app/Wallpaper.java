package com.hcn.auto_compat.app;

import android.text.TextUtils;
import com.hcn.common.lang.HPublicExecutor;
import com.hcn.common.lang.HTaskRunnable;
import com.hcn.common.lang.Listenable;
import com.hcn.common.lang.RunnableEx;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HFileUtils;
import com.hcn.common.utils.HUtilsEx;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Wallpaper {
    public static final String APP_WALLPAPER_PATH = "/apd/appWallpaper";
    public static final String ET_SAVE_PATH = "Save-Path";
    private static final String MODULE_AUTO_SETTINGS_PATH = "/apd/appWallpaper/settings";
    private static final String MODULE_BLUETOOTH_PATH = "/apd/appWallpaper/bluetooth";
    private static final String MODULE_EQ_PATH = "/apd/appWallpaper/heq";
    private static final String MODULE_LAUNCHER_PATH = "/apd/appWallpaper/launcher";
    private static final String MODULE_MEDIA_PATH = "/apd/appWallpaper/media";
    private static final String MODULE_RADIO_PATH = "/apd/appWallpaper/radio";
    public static final String ST_COMPLETED = "Completed";
    private static final String TAG = "Wallpaper";
    private static String mModulePath = "common";
    private boolean mInitialized;
    private final ArrayList<Info> mList;
    private final List<Listenable<String>> mListeners;

    private Wallpaper() {
        this.mInitialized = false;
        this.mList = new ArrayList<>();
        this.mListeners = new ArrayList();
        initConfig();
    }

    private static final class WallpaperHolder {
        static final Wallpaper sInstance = new Wallpaper();

        private WallpaperHolder() {
        }
    }

    public static Wallpaper instance() {
        return WallpaperHolder.sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFileSuffix(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return str.endsWith(str2);
    }

    public void register(Listenable<String> listenable) {
        if (this.mListeners.contains(listenable)) {
            return;
        }
        this.mListeners.add(listenable);
        if (this.mInitialized) {
            listenable.listen(ST_COMPLETED, null);
        }
    }

    private void initConfig() {
        String packageName = HUtilsEx.getApp().getPackageName();
        packageName.hashCode();
        switch (packageName) {
            case "com.hcn.AutoMediaPlayer":
                mModulePath = MODULE_MEDIA_PATH;
                break;
            case "com.android.launcher3":
                mModulePath = MODULE_LAUNCHER_PATH;
                break;
            case "com.hcn.autoradio":
                mModulePath = MODULE_RADIO_PATH;
                break;
            case "com.autochips.bluetooth":
                mModulePath = MODULE_BLUETOOTH_PATH;
                break;
            case "com.hcn.autosetting":
                mModulePath = MODULE_AUTO_SETTINGS_PATH;
                break;
            case "com.hcn.autoeq":
                mModulePath = MODULE_EQ_PATH;
                break;
            default:
                mModulePath = "common";
                break;
        }
        LogUtils.vTag(TAG, "init mModulePath: " + mModulePath);
    }

    public void unregister(Listenable<String> listenable) {
        if (Objects.isNull(listenable)) {
            return;
        }
        this.mListeners.remove(listenable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListener(String str, Object obj) {
        for (Listenable<String> listenable : this.mListeners) {
            if (listenable != null) {
                listenable.listen(str, obj);
            }
        }
    }

    public void initialize() {
        final int i = 20140109;
        HPublicExecutor.instance().submitTask(20140109, new loadWallpaperTask(), new HTaskRunnable.OnCompletionListener() { // from class: com.hcn.auto_compat.app.Wallpaper.1
            @Override // com.hcn.common.lang.HTaskRunnable.OnCompletionListener
            public void onCompletion(long j, Object obj) {
                if (j != i) {
                    return;
                }
                Wallpaper.this.mInitialized = true;
                if (HUtilsEx.isAppDebug()) {
                    Iterator it = Wallpaper.this.mList.iterator();
                    while (it.hasNext()) {
                        LogUtils.vTag(Wallpaper.TAG, ": " + ((Info) it.next()).toString());
                    }
                }
                Wallpaper.this.notifyListener(Wallpaper.ST_COMPLETED, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    class loadWallpaperTask extends RunnableEx {
        private loadWallpaperTask() {
        }

        @Override // com.hcn.common.lang.RunnableEx, java.lang.Runnable
        public void run() {
            Wallpaper.this.mList.clear();
            loadAndSortWallpaper();
        }

        private void loadAndSortWallpaper() {
            loadModuleWallpaper();
            sortWallpaper();
        }

        private void loadCommonWallpaper() {
            if (!HFileUtils.isFileExists(Wallpaper.APP_WALLPAPER_PATH)) {
                LogUtils.vTag(Wallpaper.TAG, "loadWallpaper: /apd/appWallpaper not exists");
                return;
            }
            for (File file : HFileUtils.listFilesInDir(Wallpaper.APP_WALLPAPER_PATH)) {
                if (!file.isDirectory()) {
                    String name = file.getName();
                    if (name.startsWith("thumbnail_") && (Wallpaper.this.isFileSuffix(name, ".jpg") || Wallpaper.this.isFileSuffix(name, ".png"))) {
                        Info info = new Info();
                        info.thumbnailPath = file.getAbsolutePath();
                        String str = Wallpaper.APP_WALLPAPER_PATH + File.separator + "wallpaper_" + name.substring(10, name.lastIndexOf("."));
                        String str2 = str + ".jpg";
                        if (HFileUtils.isFileExists(str2)) {
                            info.wallpaperPath = str2;
                        } else {
                            String str3 = str + ".png";
                            if (HFileUtils.isFileExists(str3)) {
                                info.wallpaperPath = str3;
                            }
                        }
                        Wallpaper.this.mList.add(info);
                    }
                }
            }
        }

        private void loadModuleWallpaper() {
            if (!Wallpaper.mModulePath.equals("common")) {
                if (!HFileUtils.isFileExists(Wallpaper.mModulePath)) {
                    LogUtils.vTag(Wallpaper.TAG, "loadModuleWallpaper: " + Wallpaper.mModulePath + " not exists");
                    loadCommonWallpaper();
                    return;
                }
                for (File file : HFileUtils.listFilesInDir(Wallpaper.mModulePath)) {
                    if (!file.isDirectory()) {
                        String name = file.getName();
                        if (name.startsWith("thumbnail_") && (Wallpaper.this.isFileSuffix(name, ".jpg") || Wallpaper.this.isFileSuffix(name, ".png"))) {
                            String str = Wallpaper.mModulePath + File.separator + name.replace("thumbnail_", "wallpaper_");
                            if (HFileUtils.isFileExists(str)) {
                                Info info = new Info();
                                info.wallpaperPath = str;
                                info.thumbnailPath = file.getAbsolutePath();
                                Wallpaper.this.mList.add(info);
                            }
                        }
                    }
                }
                return;
            }
            LogUtils.vTag(Wallpaper.TAG, "loadWallpaper common");
            loadCommonWallpaper();
        }

        private void sortWallpaper() {
            if (Wallpaper.this.mList.isEmpty()) {
                return;
            }
            Wallpaper.this.mList.sort(new Comparator() { // from class: com.hcn.auto_compat.app.Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Wallpaper.loadWallpaperTask.lambda$sortWallpaper$0((Wallpaper.Info) obj, (Wallpaper.Info) obj2);
                }
            });
        }

        static /* synthetic */ int lambda$sortWallpaper$0(Info info, Info info2) {
            if (info == null || info2 == null || info.thumbnailPath == null || info2.thumbnailPath == null) {
                return 0;
            }
            return info.thumbnailPath.compareTo(info2.thumbnailPath);
        }
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    public List<Info> getInfo() {
        if (this.mInitialized) {
            return this.mList;
        }
        return null;
    }

    public void saveWallpaperPath(String str) {
        if (this.mInitialized) {
            notifyListener(ET_SAVE_PATH, str);
        }
    }

    public void destroy() {
        this.mList.clear();
    }

    public static class Info {
        public String thumbnailPath;
        public String wallpaperPath;

        public String toString() {
            return "Info{thumbnailPath=" + this.thumbnailPath + ", wallpaperPath=" + this.wallpaperPath + "}";
        }
    }
}
