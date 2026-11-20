package com.hcn.skinx_night;

import android.util.ArrayMap;
import android.view.View;
import com.hcn.common.misc.LogUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class NightModeManager {
    private static final String TAG = "NightModeManager";
    private static final ArrayMap<WeakReference<IUiModeDecoration>, NightModeHelper> mHelperMap = new ArrayMap<>();
    private static boolean mNightModeToggle = false;
    private static int mUiMode;

    public static void config(boolean z, int i) {
        mNightModeToggle = z;
        mUiMode = i & 48;
    }

    public static boolean supportNightDecorProtocol() {
        return mNightModeToggle;
    }

    public static void register(IUiModeDecoration iUiModeDecoration) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " register failed! the skin don't support!");
            return;
        }
        if (iUiModeDecoration == null) {
            LogUtils.wTag(TAG, "register failed! IUiModeDecoration is null");
        } else {
            if (getHelper(iUiModeDecoration) != null) {
                return;
            }
            mHelperMap.put(new WeakReference<>(iUiModeDecoration), new NightModeHelper(iUiModeDecoration));
            LogUtils.vTag(TAG, "register success : " + iUiModeDecoration.getClass().getSimpleName());
        }
    }

    public static void unregister(IUiModeDecoration iUiModeDecoration) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " unregister failed! the skin don't support!");
            return;
        }
        if (iUiModeDecoration == null) {
            return;
        }
        Iterator<WeakReference<IUiModeDecoration>> it = mHelperMap.keySet().iterator();
        while (it.hasNext()) {
            IUiModeDecoration iUiModeDecoration2 = it.next().get();
            if (iUiModeDecoration2 == null || iUiModeDecoration2 == iUiModeDecoration) {
                it.remove();
            }
        }
        LogUtils.vTag(TAG, "unregister success : " + iUiModeDecoration.getClass().getSimpleName());
    }

    public static void notifyNightModeChanged(int i) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " notifyNightModeChanged failed! the skin don't support!");
            return;
        }
        int i2 = i & 48;
        if (i2 == mUiMode) {
            return;
        }
        Iterator<WeakReference<IUiModeDecoration>> it = mHelperMap.keySet().iterator();
        while (it.hasNext()) {
            WeakReference<IUiModeDecoration> next = it.next();
            IUiModeDecoration iUiModeDecoration = next.get();
            NightModeHelper nightModeHelper = mHelperMap.get(next);
            if (nightModeHelper != null && iUiModeDecoration != null) {
                LogUtils.vTag(TAG, "notify --> " + iUiModeDecoration.getClass().getSimpleName());
                nightModeHelper.tryOnUiModeChanged(i);
            } else {
                it.remove();
            }
        }
        mUiMode = i2;
    }

    public static void initViewWithHelper(IUiModeDecoration iUiModeDecoration, View view) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " initViewWithHelper failed! the skin don't support!");
            return;
        }
        NightModeHelper helper = getHelper(iUiModeDecoration);
        if (helper != null) {
            helper.init(view);
        } else {
            LogUtils.wTag(TAG, "The " + iUiModeDecoration.getClass().getSimpleName() + " don't register helper!");
        }
    }

    public static void dispatchViewWithHelper(IUiModeDecoration iUiModeDecoration) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " dispatchViewWithHelper failed! the skin don't support!");
            return;
        }
        if (NightX.isNightMode()) {
            NightModeHelper helper = getHelper(iUiModeDecoration);
            if (helper != null) {
                helper.dispatchUpdateView(true);
            } else {
                LogUtils.wTag(TAG, "The " + iUiModeDecoration.getClass().getSimpleName() + " don't register helper!");
            }
        }
    }

    public static void updateViewWithHelper(IUiModeDecoration iUiModeDecoration, View view) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " updateViewWithHelper failed! the skin don't support!");
            return;
        }
        NightModeHelper helper = getHelper(iUiModeDecoration);
        if (helper != null) {
            helper.updateDecorationForView(view, NightX.isNightMode());
        } else {
            LogUtils.wTag(TAG, "The " + iUiModeDecoration.getClass().getSimpleName() + " don't register helper!");
        }
    }

    public static void setBindViewWithHelper(IUiModeDecoration iUiModeDecoration, View view) {
        if (!mNightModeToggle) {
            LogUtils.vTag(TAG, " setBindViewWithHelper failed! the skin don't support!");
            return;
        }
        NightModeHelper helper = getHelper(iUiModeDecoration);
        if (helper != null) {
            helper.setBindView(view);
        } else {
            LogUtils.wTag(TAG, "The " + iUiModeDecoration.getClass().getSimpleName() + " don't register helper!");
        }
    }

    private static NightModeHelper getHelper(IUiModeDecoration iUiModeDecoration) {
        ArrayList arrayList = new ArrayList();
        NightModeHelper nightModeHelperValueAt = null;
        int i = 0;
        while (true) {
            ArrayMap<WeakReference<IUiModeDecoration>, NightModeHelper> arrayMap = mHelperMap;
            if (i >= arrayMap.size()) {
                break;
            }
            WeakReference<IUiModeDecoration> weakReferenceKeyAt = arrayMap.keyAt(i);
            if (weakReferenceKeyAt.get() == null) {
                arrayList.add(weakReferenceKeyAt);
            } else if (weakReferenceKeyAt.get() == iUiModeDecoration) {
                nightModeHelperValueAt = arrayMap.valueAt(i);
            }
            i++;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            mHelperMap.remove((WeakReference) it.next());
        }
        return nightModeHelperValueAt;
    }
}
