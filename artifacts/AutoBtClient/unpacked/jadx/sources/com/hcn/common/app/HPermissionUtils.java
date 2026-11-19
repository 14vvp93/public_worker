package com.hcn.common.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import androidx.core.content.ContextCompat;
import com.hcn.common.constant.HPermissionConstants;
import com.hcn.common.utils.HUtilsEx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class HPermissionUtils {
    private static HPermissionUtils sInstance;
    private static SimpleCallback sSimpleCallback4DrawOverlays;
    private static SimpleCallback sSimpleCallback4WriteSettings;
    private FullCallback mFullCallback;
    private OnExplainListener mOnExplainListener;
    private OnRationaleListener mOnRationaleListener;
    private Set<String> mPermissions;
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;
    private List<String> mPermissionsGranted;
    private String[] mPermissionsParam;
    private List<String> mPermissionsRequest;
    private SimpleCallback mSimpleCallback;
    private SingleCallback mSingleCallback;
    private ThemeCallback mThemeCallback;

    public interface FullCallback {
        void onDenied(List<String> list, List<String> list2);

        void onGranted(List<String> list);
    }

    public interface OnExplainListener {

        public interface ShouldRequest {
            void start(boolean z);
        }

        void explain(HUtilsTransActivity hUtilsTransActivity, List<String> list, ShouldRequest shouldRequest);
    }

    public interface OnRationaleListener {

        public interface ShouldRequest {
            void again(boolean z);
        }

        void rationale(HUtilsTransActivity hUtilsTransActivity, ShouldRequest shouldRequest);
    }

    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    public interface SingleCallback {
        void callback(boolean z, List<String> list, List<String> list2, List<String> list3);
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }

    public static List<String> getPermissions() {
        return getPermissions(HUtilsEx.getApp().getPackageName());
    }

    public static List<String> getPermissions(String str) {
        try {
            String[] strArr = HUtilsEx.getApp().getPackageManager().getPackageInfo(str, 4096).requestedPermissions;
            if (strArr == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(strArr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean isGranted(String... strArr) {
        Pair<List<String>, List<String>> requestAndDeniedPermissions = getRequestAndDeniedPermissions(strArr);
        if (!((List) requestAndDeniedPermissions.second).isEmpty()) {
            return false;
        }
        Iterator it = ((List) requestAndDeniedPermissions.first).iterator();
        while (it.hasNext()) {
            if (!isGranted((String) it.next())) {
                return false;
            }
        }
        return true;
    }

    private static Pair<List<String>, List<String>> getRequestAndDeniedPermissions(String... strArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<String> permissions = getPermissions();
        for (String str : strArr) {
            boolean z = false;
            for (String str2 : HPermissionConstants.getPermissions(str)) {
                if (permissions.contains(str2)) {
                    arrayList.add(str2);
                    z = true;
                }
            }
            if (!z) {
                arrayList2.add(str);
                Log.e("PermissionUtils", "U should add the permission of " + str + " in manifest.");
            }
        }
        return Pair.create(arrayList, arrayList2);
    }

    private static boolean isGranted(String str) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(HUtilsEx.getApp(), str) == 0;
    }

    public static boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(HUtilsEx.getApp());
    }

    public static void requestWriteSettings(SimpleCallback simpleCallback) {
        if (!isGrantedWriteSettings()) {
            sSimpleCallback4WriteSettings = simpleCallback;
            PermissionActivityImpl.start(2);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startWriteSettingsActivity(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + HUtilsEx.getApp().getPackageName()));
        if (!HIntentUtils.isIntentAvailable(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    public static boolean isGrantedDrawOverlays() {
        return Settings.canDrawOverlays(HUtilsEx.getApp());
    }

    public static void requestDrawOverlays(SimpleCallback simpleCallback) {
        if (!isGrantedDrawOverlays()) {
            sSimpleCallback4DrawOverlays = simpleCallback;
            PermissionActivityImpl.start(3);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startOverlayPermissionActivity(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + HUtilsEx.getApp().getPackageName()));
        if (!HIntentUtils.isIntentAvailable(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    public static void launchAppDetailsSettings() {
        Intent launchAppDetailsSettingsIntent = HIntentUtils.getLaunchAppDetailsSettingsIntent(HUtilsEx.getApp().getPackageName(), true);
        if (HIntentUtils.isIntentAvailable(launchAppDetailsSettingsIntent)) {
            HUtilsEx.getApp().startActivity(launchAppDetailsSettingsIntent);
        }
    }

    public static HPermissionUtils permissionGroup(String... strArr) {
        return permission(strArr);
    }

    public static HPermissionUtils permission(String... strArr) {
        return new HPermissionUtils(strArr);
    }

    private HPermissionUtils(String... strArr) {
        this.mPermissionsParam = strArr;
        sInstance = this;
    }

    public HPermissionUtils explain(OnExplainListener onExplainListener) {
        this.mOnExplainListener = onExplainListener;
        return this;
    }

    public HPermissionUtils rationale(OnRationaleListener onRationaleListener) {
        this.mOnRationaleListener = onRationaleListener;
        return this;
    }

    public HPermissionUtils callback(SingleCallback singleCallback) {
        this.mSingleCallback = singleCallback;
        return this;
    }

    public HPermissionUtils callback(SimpleCallback simpleCallback) {
        this.mSimpleCallback = simpleCallback;
        return this;
    }

    public HPermissionUtils callback(FullCallback fullCallback) {
        this.mFullCallback = fullCallback;
        return this;
    }

    public HPermissionUtils theme(ThemeCallback themeCallback) {
        this.mThemeCallback = themeCallback;
        return this;
    }

    public void request() {
        String[] strArr = this.mPermissionsParam;
        if (strArr == null || strArr.length <= 0) {
            Log.w("PermissionUtils", "No permissions to request.");
            return;
        }
        this.mPermissions = new LinkedHashSet();
        this.mPermissionsRequest = new ArrayList();
        this.mPermissionsGranted = new ArrayList();
        this.mPermissionsDenied = new ArrayList();
        this.mPermissionsDeniedForever = new ArrayList();
        Pair<List<String>, List<String>> requestAndDeniedPermissions = getRequestAndDeniedPermissions(this.mPermissionsParam);
        this.mPermissions.addAll((Collection) requestAndDeniedPermissions.first);
        this.mPermissionsDenied.addAll((Collection) requestAndDeniedPermissions.second);
        if (Build.VERSION.SDK_INT < 23) {
            this.mPermissionsGranted.addAll(this.mPermissions);
            requestCallback();
            return;
        }
        for (String str : this.mPermissions) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsRequest.add(str);
            }
        }
        if (this.mPermissionsRequest.isEmpty()) {
            requestCallback();
        } else {
            startPermissionActivity();
        }
    }

    private void startPermissionActivity() {
        PermissionActivityImpl.start(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldRationale(HUtilsTransActivity hUtilsTransActivity, Runnable runnable) {
        boolean z = false;
        if (this.mOnRationaleListener != null) {
            Iterator<String> it = this.mPermissionsRequest.iterator();
            while (it.hasNext()) {
                if (hUtilsTransActivity.shouldShowRequestPermissionRationale(it.next())) {
                    rationalInner(hUtilsTransActivity, runnable);
                    z = true;
                    break;
                }
            }
            this.mOnRationaleListener = null;
        }
        return z;
    }

    private void rationalInner(final HUtilsTransActivity hUtilsTransActivity, final Runnable runnable) {
        getPermissionsStatus(hUtilsTransActivity);
        this.mOnRationaleListener.rationale(hUtilsTransActivity, new OnRationaleListener.ShouldRequest() { // from class: com.hcn.common.app.HPermissionUtils.1
            @Override // com.hcn.common.app.HPermissionUtils.OnRationaleListener.ShouldRequest
            public void again(boolean z) {
                if (z) {
                    HPermissionUtils.this.mPermissionsDenied = new ArrayList();
                    HPermissionUtils.this.mPermissionsDeniedForever = new ArrayList();
                    runnable.run();
                    return;
                }
                hUtilsTransActivity.finish();
                HPermissionUtils.this.requestCallback();
            }
        });
    }

    private void getPermissionsStatus(Activity activity) {
        for (String str : this.mPermissionsRequest) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsDenied.add(str);
                if (!activity.shouldShowRequestPermissionRationale(str)) {
                    this.mPermissionsDeniedForever.add(str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCallback() {
        SingleCallback singleCallback = this.mSingleCallback;
        if (singleCallback != null) {
            singleCallback.callback(this.mPermissionsDenied.isEmpty(), this.mPermissionsGranted, this.mPermissionsDeniedForever, this.mPermissionsDenied);
            this.mSingleCallback = null;
        }
        if (this.mSimpleCallback != null) {
            if (this.mPermissionsDenied.isEmpty()) {
                this.mSimpleCallback.onGranted();
            } else {
                this.mSimpleCallback.onDenied();
            }
            this.mSimpleCallback = null;
        }
        if (this.mFullCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissionsGranted.size() > 0) {
                this.mFullCallback.onGranted(this.mPermissionsGranted);
            }
            if (!this.mPermissionsDenied.isEmpty()) {
                this.mFullCallback.onDenied(this.mPermissionsDeniedForever, this.mPermissionsDenied);
            }
            this.mFullCallback = null;
        }
        this.mOnRationaleListener = null;
        this.mThemeCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestPermissionsResult(Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    static final class PermissionActivityImpl extends HUtilsTransActivity.TransActivityDelegate {
        private static PermissionActivityImpl INSTANCE = new PermissionActivityImpl();
        private static final String TYPE = "TYPE";
        private static final int TYPE_DRAW_OVERLAYS = 3;
        private static final int TYPE_RUNTIME = 1;
        private static final int TYPE_WRITE_SETTINGS = 2;
        private static int currentRequestCode = -1;

        PermissionActivityImpl() {
        }

        public static void start(final int i) {
            HUtilsTransActivity.start(new HUtilsEx.Consumer<Intent>() { // from class: com.hcn.common.app.HPermissionUtils.PermissionActivityImpl.1
                @Override // com.hcn.common.utils.HUtilsEx.Consumer
                public void accept(Intent intent) {
                    intent.putExtra(PermissionActivityImpl.TYPE, i);
                }
            }, INSTANCE);
        }

        @Override // com.hcn.common.app.HUtilsTransActivity.TransActivityDelegate
        public void onCreated(final HUtilsTransActivity hUtilsTransActivity, Bundle bundle) {
            hUtilsTransActivity.getWindow().addFlags(262160);
            int intExtra = hUtilsTransActivity.getIntent().getIntExtra(TYPE, -1);
            if (intExtra != 1) {
                if (intExtra == 2) {
                    currentRequestCode = 2;
                    HPermissionUtils.startWriteSettingsActivity(hUtilsTransActivity, 2);
                    return;
                } else if (intExtra == 3) {
                    currentRequestCode = 3;
                    HPermissionUtils.startOverlayPermissionActivity(hUtilsTransActivity, 3);
                    return;
                } else {
                    hUtilsTransActivity.finish();
                    Log.e("PermissionUtils", "type is wrong.");
                    return;
                }
            }
            if (HPermissionUtils.sInstance == null) {
                Log.e("PermissionUtils", "sInstance is null.");
                hUtilsTransActivity.finish();
                return;
            }
            if (HPermissionUtils.sInstance.mPermissionsRequest == null) {
                Log.e("PermissionUtils", "mPermissionsRequest is null.");
                hUtilsTransActivity.finish();
                return;
            }
            if (HPermissionUtils.sInstance.mPermissionsRequest.size() <= 0) {
                Log.e("PermissionUtils", "mPermissionsRequest's size is no more than 0.");
                hUtilsTransActivity.finish();
                return;
            }
            if (HPermissionUtils.sInstance.mThemeCallback != null) {
                HPermissionUtils.sInstance.mThemeCallback.onActivityCreate(hUtilsTransActivity);
            }
            if (HPermissionUtils.sInstance.mOnExplainListener != null) {
                HPermissionUtils.sInstance.mOnExplainListener.explain(hUtilsTransActivity, HPermissionUtils.sInstance.mPermissionsRequest, new OnExplainListener.ShouldRequest() { // from class: com.hcn.common.app.HPermissionUtils.PermissionActivityImpl.2
                    @Override // com.hcn.common.app.HPermissionUtils.OnExplainListener.ShouldRequest
                    public void start(boolean z) {
                        if (!z) {
                            hUtilsTransActivity.finish();
                        } else {
                            PermissionActivityImpl.this.requestPermissions(hUtilsTransActivity);
                        }
                    }
                });
                HPermissionUtils.sInstance.mOnExplainListener = null;
            } else {
                requestPermissions(hUtilsTransActivity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestPermissions(final HUtilsTransActivity hUtilsTransActivity) {
            if (HPermissionUtils.sInstance.shouldRationale(hUtilsTransActivity, new Runnable() { // from class: com.hcn.common.app.HPermissionUtils.PermissionActivityImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    hUtilsTransActivity.requestPermissions((String[]) HPermissionUtils.sInstance.mPermissionsRequest.toArray(new String[0]), 1);
                }
            })) {
                return;
            }
            hUtilsTransActivity.requestPermissions((String[]) HPermissionUtils.sInstance.mPermissionsRequest.toArray(new String[0]), 1);
        }

        @Override // com.hcn.common.app.HUtilsTransActivity.TransActivityDelegate
        public void onRequestPermissionsResult(HUtilsTransActivity hUtilsTransActivity, int i, String[] strArr, int[] iArr) {
            hUtilsTransActivity.finish();
            if (HPermissionUtils.sInstance == null || HPermissionUtils.sInstance.mPermissionsRequest == null) {
                return;
            }
            HPermissionUtils.sInstance.onRequestPermissionsResult(hUtilsTransActivity);
        }

        @Override // com.hcn.common.app.HUtilsTransActivity.TransActivityDelegate
        public boolean dispatchTouchEvent(HUtilsTransActivity hUtilsTransActivity, MotionEvent motionEvent) {
            hUtilsTransActivity.finish();
            return true;
        }

        @Override // com.hcn.common.app.HUtilsTransActivity.TransActivityDelegate
        public void onDestroy(HUtilsTransActivity hUtilsTransActivity) {
            int i = currentRequestCode;
            if (i != -1) {
                checkRequestCallback(i);
                currentRequestCode = -1;
            }
            super.onDestroy(hUtilsTransActivity);
        }

        @Override // com.hcn.common.app.HUtilsTransActivity.TransActivityDelegate
        public void onActivityResult(HUtilsTransActivity hUtilsTransActivity, int i, int i2, Intent intent) {
            hUtilsTransActivity.finish();
        }

        private void checkRequestCallback(int i) {
            if (i == 2) {
                if (HPermissionUtils.sSimpleCallback4WriteSettings == null) {
                    return;
                }
                if (HPermissionUtils.isGrantedWriteSettings()) {
                    HPermissionUtils.sSimpleCallback4WriteSettings.onGranted();
                } else {
                    HPermissionUtils.sSimpleCallback4WriteSettings.onDenied();
                }
                HPermissionUtils.sSimpleCallback4WriteSettings = null;
                return;
            }
            if (i != 3 || HPermissionUtils.sSimpleCallback4DrawOverlays == null) {
                return;
            }
            if (HPermissionUtils.isGrantedDrawOverlays()) {
                HPermissionUtils.sSimpleCallback4DrawOverlays.onGranted();
            } else {
                HPermissionUtils.sSimpleCallback4DrawOverlays.onDenied();
            }
            HPermissionUtils.sSimpleCallback4DrawOverlays = null;
        }
    }
}
