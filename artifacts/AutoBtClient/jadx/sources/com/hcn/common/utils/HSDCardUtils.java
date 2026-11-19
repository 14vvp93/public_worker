package com.hcn.common.utils;

import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;
import com.autochips.bluetooth.skin.SkinID;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HSDCardUtils {
    private HSDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isSDCardEnableByEnvironment() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String getSDCardPathByEnvironment() {
        return isSDCardEnableByEnvironment() ? Environment.getExternalStorageDirectory().getAbsolutePath() : SkinID.SKIN_NONE;
    }

    public static List<SDCardInfo> getSDCardInfo() {
        ArrayList arrayList = new ArrayList();
        StorageManager storageManager = (StorageManager) HUtilsEx.getApp().getSystemService("storage");
        if (storageManager == null) {
            return arrayList;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            try {
                Method method = StorageVolume.class.getMethod("getPath", new Class[0]);
                for (StorageVolume storageVolume : storageVolumes) {
                    arrayList.add(new SDCardInfo((String) method.invoke(storageVolume, new Object[0]), storageVolume.getState(), storageVolume.isRemovable()));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class<?> cls = Class.forName("android.os.storage.StorageVolume");
                Method method2 = cls.getMethod("getPath", new Class[0]);
                Method method3 = cls.getMethod("isRemovable", new Class[0]);
                Method method4 = StorageManager.class.getMethod("getVolumeState", String.class);
                Object objInvoke = StorageManager.class.getMethod("getVolumeList", new Class[0]).invoke(storageManager, new Object[0]);
                int length = Array.getLength(objInvoke);
                for (int i = 0; i < length; i++) {
                    Object obj = Array.get(objInvoke, i);
                    String str = (String) method2.invoke(obj, new Object[0]);
                    arrayList.add(new SDCardInfo(str, (String) method4.invoke(storageManager, str), ((Boolean) method3.invoke(obj, new Object[0])).booleanValue()));
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public static List<String> getMountedSDCardPath() {
        ArrayList arrayList = new ArrayList();
        List<SDCardInfo> sDCardInfo = getSDCardInfo();
        if (sDCardInfo.isEmpty()) {
            return arrayList;
        }
        for (SDCardInfo sDCardInfo2 : sDCardInfo) {
            String str = sDCardInfo2.state;
            if (str != null && "mounted".equals(str.toLowerCase())) {
                arrayList.add(sDCardInfo2.path);
            }
        }
        return arrayList;
    }

    public static long getExternalTotalSize() {
        return HFileUtils.getFsTotalSize(getSDCardPathByEnvironment());
    }

    public static long getExternalAvailableSize() {
        return HFileUtils.getFsAvailableSize(getSDCardPathByEnvironment());
    }

    public static long getInternalTotalSize() {
        return HFileUtils.getFsTotalSize(Environment.getDataDirectory().getAbsolutePath());
    }

    public static long getInternalAvailableSize() {
        return HFileUtils.getFsAvailableSize(Environment.getDataDirectory().getAbsolutePath());
    }

    public static class SDCardInfo {
        private final long availableSize;
        private final boolean isRemovable;
        private final String path;
        private final String state;
        private final long totalSize;

        SDCardInfo(String str, String str2, boolean z) {
            this.path = str;
            this.state = str2;
            this.isRemovable = z;
            this.totalSize = HFileUtils.getFsTotalSize(str);
            this.availableSize = HFileUtils.getFsAvailableSize(str);
        }

        public String getPath() {
            return this.path;
        }

        public String getState() {
            return this.state;
        }

        public boolean isRemovable() {
            return this.isRemovable;
        }

        public long getTotalSize() {
            return this.totalSize;
        }

        public long getAvailableSize() {
            return this.availableSize;
        }

        public String toString() {
            return "SDCardInfo {path = " + this.path + ", state = " + this.state + ", isRemovable = " + this.isRemovable + ", totalSize = " + Formatter.formatFileSize(HUtilsEx.getApp(), this.totalSize) + ", availableSize = " + Formatter.formatFileSize(HUtilsEx.getApp(), this.availableSize) + '}';
        }
    }
}
