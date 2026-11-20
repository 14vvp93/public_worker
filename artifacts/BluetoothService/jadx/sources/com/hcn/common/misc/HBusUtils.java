package com.hcn.common.misc;

import android.util.Log;
import com.hcn.common.lang.HThreadUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skinx_night.NightX;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: classes.dex */
public class HBusUtils {
    private static final Object NULL = "NULl";
    private static final String TAG = "HBusUtils";
    private final Map<String, Set<Object>> mClassName_BusesMap;
    private final Map<String, Map<String, Object>> mClassName_Tag_Arg4StickyMap;
    private final Map<String, List<String>> mClassName_TagsMap;
    private final Map<String, List<HBusInfo>> mTag_BusInfoListMap;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface HBus {
        int priority() default 0;

        boolean sticky() default false;

        String tag();

        ThreadMode threadMode() default ThreadMode.POSTING;
    }

    public enum ThreadMode {
        MAIN,
        IO,
        CPU,
        CACHED,
        SINGLE,
        POSTING
    }

    private void init() {
    }

    private HBusUtils() {
        this.mTag_BusInfoListMap = new ConcurrentHashMap();
        this.mClassName_BusesMap = new ConcurrentHashMap();
        this.mClassName_TagsMap = new ConcurrentHashMap();
        this.mClassName_Tag_Arg4StickyMap = new ConcurrentHashMap();
        init();
    }

    private void registerBus(String str, String str2, String str3, String str4, String str5, boolean z, String str6) {
        registerBus(str, str2, str3, str4, str5, z, str6, 0);
    }

    private void registerBus(String str, String str2, String str3, String str4, String str5, boolean z, String str6, int i) {
        List<HBusInfo> copyOnWriteArrayList = this.mTag_BusInfoListMap.get(str);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.mTag_BusInfoListMap.put(str, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(new HBusInfo(str, str2, str3, str4, str5, z, str6, i));
    }

    public static void register(Object obj) {
        getInstance().registerInner(obj);
    }

    public static void unregister(Object obj) {
        getInstance().unregisterInner(obj);
    }

    public static void post(String str) {
        post(str, NULL);
    }

    public static void post(String str, Object obj) {
        getInstance().postInner(str, obj);
    }

    public static void postSticky(String str) {
        postSticky(str, NULL);
    }

    public static void postSticky(String str, Object obj) {
        getInstance().postStickyInner(str, obj);
    }

    public static void removeSticky(String str) {
        getInstance().removeStickyInner(str);
    }

    public String toString() {
        return "BusUtils: " + this.mTag_BusInfoListMap;
    }

    private static HBusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void registerInner(Object obj) {
        if (obj == null) {
            return;
        }
        Class<?> cls = obj.getClass();
        String name = cls.getName();
        boolean z = false;
        synchronized (this.mClassName_BusesMap) {
            Set<Object> copyOnWriteArraySet = this.mClassName_BusesMap.get(name);
            if (copyOnWriteArraySet == null) {
                copyOnWriteArraySet = new CopyOnWriteArraySet<>();
                this.mClassName_BusesMap.put(name, copyOnWriteArraySet);
                z = true;
            }
            if (copyOnWriteArraySet.contains(obj)) {
                Log.w(TAG, "The bus of <" + obj + "> already registered.");
                return;
            }
            copyOnWriteArraySet.add(obj);
            if (z) {
                recordTags(cls, name);
            }
            consumeStickyIfExist(obj);
        }
    }

    private void recordTags(Class<?> cls, String str) {
        if (this.mClassName_TagsMap.get(str) == null) {
            synchronized (this.mClassName_TagsMap) {
                if (this.mClassName_TagsMap.get(str) == null) {
                    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
                    for (Map.Entry<String, List<HBusInfo>> entry : this.mTag_BusInfoListMap.entrySet()) {
                        for (HBusInfo hBusInfo : entry.getValue()) {
                            try {
                                if (Class.forName(hBusInfo.className).isAssignableFrom(cls)) {
                                    copyOnWriteArrayList.add(entry.getKey());
                                    hBusInfo.subClassNames.add(str);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    this.mClassName_TagsMap.put(str, copyOnWriteArrayList);
                }
            }
        }
    }

    private void consumeStickyIfExist(Object obj) {
        Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(obj.getClass().getName());
        if (map == null) {
            return;
        }
        synchronized (this.mClassName_Tag_Arg4StickyMap) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                consumeSticky(obj, entry.getKey(), entry.getValue());
            }
        }
    }

    private void consumeSticky(Object obj, String str, Object obj2) {
        List<HBusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (HBusInfo hBusInfo : list) {
            if (hBusInfo.subClassNames.contains(obj.getClass().getName()) && hBusInfo.sticky) {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(hBusInfo.className);
                    if (map != null && map.containsKey(str)) {
                        invokeBus(obj, obj2, hBusInfo, true);
                    }
                }
            }
        }
    }

    private void unregisterInner(Object obj) {
        if (obj == null) {
            return;
        }
        String name = obj.getClass().getName();
        synchronized (this.mClassName_BusesMap) {
            Set<Object> set = this.mClassName_BusesMap.get(name);
            if (set != null && set.contains(obj)) {
                set.remove(obj);
                return;
            }
            if (HUtilsEx.isAppDebug()) {
                Log.e(TAG, "The bus of <" + obj + "> was not registered before.");
            }
        }
    }

    private void postInner(String str, Object obj) {
        postInner(str, obj, false);
    }

    private void postInner(String str, Object obj, boolean z) {
        List<HBusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            if (this.mTag_BusInfoListMap.isEmpty()) {
                Log.e(TAG, "Please check whether the bus plugin is applied.");
                return;
            }
            return;
        }
        Iterator<HBusInfo> it = list.iterator();
        while (it.hasNext()) {
            invokeBus(obj, it.next(), z);
        }
    }

    private void invokeBus(Object obj, HBusInfo hBusInfo, boolean z) {
        invokeBus(null, obj, hBusInfo, z);
    }

    private void invokeBus(Object obj, Object obj2, HBusInfo hBusInfo, boolean z) {
        if (hBusInfo.method == null) {
            Method methodByBusInfo = getMethodByBusInfo(hBusInfo);
            if (methodByBusInfo == null) {
                return;
            } else {
                hBusInfo.method = methodByBusInfo;
            }
        }
        invokeMethod(obj, obj2, hBusInfo, z);
    }

    private Method getMethodByBusInfo(HBusInfo hBusInfo) {
        try {
            return "".equals(hBusInfo.paramType) ? Class.forName(hBusInfo.className).getDeclaredMethod(hBusInfo.funName, new Class[0]) : Class.forName(hBusInfo.className).getDeclaredMethod(hBusInfo.funName, getClassName(hBusInfo.paramType));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Class<?> getClassName(String str) throws ClassNotFoundException {
        str.hashCode();
        switch (str) {
            case "double":
                return Double.TYPE;
            case "int":
                return Integer.TYPE;
            case "byte":
                return Byte.TYPE;
            case "char":
                return Character.TYPE;
            case "long":
                return Long.TYPE;
            case "boolean":
                return Boolean.TYPE;
            case "float":
                return Float.TYPE;
            case "short":
                return Short.TYPE;
            default:
                return Class.forName(str);
        }
    }

    private void invokeMethod(Object obj, HBusInfo hBusInfo, boolean z) {
        invokeMethod(null, obj, hBusInfo, z);
    }

    private void invokeMethod(final Object obj, final Object obj2, final HBusInfo hBusInfo, final boolean z) {
        Runnable runnable = new Runnable() { // from class: com.hcn.common.misc.HBusUtils.1
            @Override // java.lang.Runnable
            public void run() {
                HBusUtils.this.realInvokeMethod(obj, obj2, hBusInfo, z);
            }
        };
        String str = hBusInfo.threadMode;
        str.hashCode();
        switch (str) {
            case "SINGLE":
                HThreadUtils.getSinglePool().execute(runnable);
                break;
            case "IO":
                HThreadUtils.getIoPool().execute(runnable);
                break;
            case "CPU":
                HThreadUtils.getCpuPool().execute(runnable);
                break;
            case "MAIN":
                HThreadUtils.runOnUiThread(runnable);
                break;
            case "CACHED":
                HThreadUtils.getCachedPool().execute(runnable);
                break;
            default:
                runnable.run();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realInvokeMethod(Object obj, Object obj2, HBusInfo hBusInfo, boolean z) {
        Set<Object> hashSet = new HashSet<>();
        if (obj == null) {
            Iterator<String> it = hBusInfo.subClassNames.iterator();
            while (it.hasNext()) {
                Set<Object> set = this.mClassName_BusesMap.get(it.next());
                if (set != null && !set.isEmpty()) {
                    hashSet.addAll(set);
                }
            }
            if (hashSet.size() == 0) {
                if (z || !HUtilsEx.isAppDebug()) {
                    return;
                }
                Log.e(TAG, "The " + hBusInfo + " was not registered before.");
                return;
            }
        } else {
            hashSet.add(obj);
        }
        invokeBuses(obj2, hBusInfo, hashSet);
    }

    private void invokeBuses(Object obj, HBusInfo hBusInfo, Set<Object> set) {
        try {
            if (obj == NULL) {
                Iterator<Object> it = set.iterator();
                while (it.hasNext()) {
                    hBusInfo.method.invoke(it.next(), new Object[0]);
                }
            } else {
                Iterator<Object> it2 = set.iterator();
                while (it2.hasNext()) {
                    hBusInfo.method.invoke(it2.next(), obj);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        }
    }

    private void postStickyInner(String str, Object obj) {
        List<HBusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (HBusInfo hBusInfo : list) {
            if (!hBusInfo.sticky) {
                invokeBus(obj, hBusInfo, false);
            } else {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> concurrentHashMap = this.mClassName_Tag_Arg4StickyMap.get(hBusInfo.className);
                    if (concurrentHashMap == null) {
                        concurrentHashMap = new ConcurrentHashMap<>();
                        this.mClassName_Tag_Arg4StickyMap.put(hBusInfo.className, concurrentHashMap);
                    }
                    concurrentHashMap.put(str, obj);
                }
                invokeBus(obj, hBusInfo, true);
            }
        }
    }

    private void removeStickyInner(String str) {
        List<HBusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (HBusInfo hBusInfo : list) {
            if (hBusInfo.sticky) {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(hBusInfo.className);
                    if (map != null && map.containsKey(str)) {
                        map.remove(str);
                    }
                    return;
                }
            }
        }
    }

    static void registerBus4Test(String str, String str2, String str3, String str4, String str5, boolean z, String str6, int i) {
        getInstance().registerBus(str, str2, str3, str4, str5, z, str6, i);
    }

    private static final class HBusInfo {
        String className;
        String funName;
        Method method;
        String paramName;
        String paramType;
        int priority;
        boolean sticky;
        List<String> subClassNames = new CopyOnWriteArrayList();
        String tag;
        String threadMode;

        HBusInfo(String str, String str2, String str3, String str4, String str5, boolean z, String str6, int i) {
            this.tag = str;
            this.className = str2;
            this.funName = str3;
            this.paramType = str4;
            this.paramName = str5;
            this.sticky = z;
            this.threadMode = str6;
            this.priority = i;
        }

        public String toString() {
            return "BusInfo { tag : " + this.tag + ", desc: " + getDesc() + ", sticky: " + this.sticky + ", threadMode: " + this.threadMode + ", method: " + this.method + ", priority: " + this.priority + " }";
        }

        private String getDesc() {
            return this.className + NightX.DARK_TAG_SEGMENTS_SEPARATOR + this.funName + ("".equals(this.paramType) ? "()" : "(" + this.paramType + " " + this.paramName + ")");
        }
    }

    private static class LazyHolder {
        private static final HBusUtils INSTANCE = new HBusUtils();

        private LazyHolder() {
        }
    }
}
