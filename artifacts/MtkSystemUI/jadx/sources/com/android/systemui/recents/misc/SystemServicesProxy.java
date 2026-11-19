package com.android.systemui.recents.misc;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.dreams.IDreamManager;
import android.util.Log;
import android.util.MutableBoolean;
import android.view.Display;
import android.view.IDockedStackListener;
import android.view.IWindowManager;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.app.AssistUtils;
import com.android.internal.os.BackgroundThread;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.recents.Recents;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SystemServicesProxy {
    static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private static SystemServicesProxy sSystemServicesProxy;
    AccessibilityManager mAccm;
    ActivityManager mAm;
    AssistUtils mAssistUtils;
    Canvas mBgProtectionCanvas;
    Paint mBgProtectionPaint;
    private final Context mContext;
    private int mCurrentUserId;
    Display mDisplay;
    int mDummyThumbnailHeight;
    int mDummyThumbnailWidth;
    boolean mIsSafeMode;
    PackageManager mPm;
    String mRecentsPackage;
    UserManager mUm;
    WindowManager mWm;
    private final Runnable mGcRunnable = new Runnable() { // from class: com.android.systemui.recents.misc.SystemServicesProxy.1
        @Override // java.lang.Runnable
        public void run() {
            System.gc();
            System.runFinalization();
        }
    };
    private final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    private final UserInfoController.OnUserInfoChangedListener mOnUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.recents.misc.-$$Lambda$SystemServicesProxy$14WNoAPwhU0GwlQXHqE_l3lK1kI
        @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
        public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
            SystemServicesProxy.lambda$new$0(this.f$0, str, drawable, str2);
        }
    };
    IActivityManager mIam = ActivityManager.getService();
    IPackageManager mIpm = AppGlobals.getPackageManager();
    IWindowManager mIwm = WindowManagerGlobal.getWindowManagerService();
    private final IDreamManager mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.checkService("dreams"));

    static {
        sBitmapOptions.inMutable = true;
        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public static /* synthetic */ void lambda$new$0(SystemServicesProxy systemServicesProxy, String name, Drawable picture, String userAccount) {
        ActivityManager activityManager = systemServicesProxy.mAm;
        systemServicesProxy.mCurrentUserId = ActivityManager.getCurrentUser();
    }

    private SystemServicesProxy(Context context) {
        this.mContext = context.getApplicationContext();
        this.mAccm = AccessibilityManager.getInstance(context);
        this.mAm = (ActivityManager) context.getSystemService("activity");
        this.mPm = context.getPackageManager();
        this.mAssistUtils = new AssistUtils(context);
        this.mWm = (WindowManager) context.getSystemService("window");
        this.mUm = UserManager.get(context);
        this.mDisplay = this.mWm.getDefaultDisplay();
        this.mRecentsPackage = context.getPackageName();
        this.mIsSafeMode = this.mPm.isSafeMode();
        ActivityManager activityManager = this.mAm;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        Resources res = context.getResources();
        this.mDummyThumbnailWidth = res.getDimensionPixelSize(R.dimen.thumbnail_width);
        this.mDummyThumbnailHeight = res.getDimensionPixelSize(R.dimen.thumbnail_height);
        this.mBgProtectionPaint = new Paint();
        this.mBgProtectionPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        this.mBgProtectionPaint.setColor(-1);
        this.mBgProtectionCanvas = new Canvas();
        UserInfoController userInfoController = (UserInfoController) Dependency.get(UserInfoController.class);
        userInfoController.addCallback(this.mOnUserInfoChangedListener);
    }

    public static synchronized SystemServicesProxy getInstance(Context context) {
        if (sSystemServicesProxy == null) {
            sSystemServicesProxy = new SystemServicesProxy(context);
        }
        return sSystemServicesProxy;
    }

    public void gc() {
        BackgroundThread.getHandler().post(this.mGcRunnable);
    }

    public boolean isRecentsActivityVisible() {
        return isRecentsActivityVisible(null);
    }

    public boolean isRecentsActivityVisible(MutableBoolean isHomeStackVisible) {
        if (this.mIam == null) {
            return false;
        }
        try {
            List<ActivityManager.StackInfo> stackInfos = this.mIam.getAllStackInfos();
            ActivityManager.StackInfo recentsStackInfo = null;
            ActivityManager.StackInfo recentsStackInfo2 = null;
            ActivityManager.StackInfo homeStackInfo = null;
            for (int i = 0; i < stackInfos.size(); i++) {
                ActivityManager.StackInfo stackInfo = stackInfos.get(i);
                WindowConfiguration winConfig = stackInfo.configuration.windowConfiguration;
                int activityType = winConfig.getActivityType();
                int windowingMode = winConfig.getWindowingMode();
                if (homeStackInfo == null && activityType == 2) {
                    homeStackInfo = stackInfo;
                } else if (recentsStackInfo2 == null && activityType == 1 && (windowingMode == 1 || windowingMode == 4)) {
                    recentsStackInfo2 = stackInfo;
                } else if (recentsStackInfo == null && activityType == 3) {
                    recentsStackInfo = stackInfo;
                }
            }
            boolean homeStackVisibleNotOccluded = isStackNotOccluded(homeStackInfo, recentsStackInfo2);
            boolean recentsStackVisibleNotOccluded = isStackNotOccluded(recentsStackInfo, recentsStackInfo2);
            if (isHomeStackVisible != null) {
                isHomeStackVisible.value = homeStackVisibleNotOccluded;
            }
            ComponentName topActivity = recentsStackInfo != null ? recentsStackInfo.topActivity : null;
            return recentsStackVisibleNotOccluded && topActivity != null && topActivity.getPackageName().equals("com.android.systemui") && Recents.RECENTS_ACTIVITIES.contains(topActivity.getClassName());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isStackNotOccluded(ActivityManager.StackInfo stackInfo, ActivityManager.StackInfo fullscreenStackInfo) {
        boolean stackVisibleNotOccluded = stackInfo == null || stackInfo.visible;
        if (fullscreenStackInfo != null && stackInfo != null) {
            boolean isFullscreenStackOccludingg = fullscreenStackInfo.visible && fullscreenStackInfo.position > stackInfo.position;
            return stackVisibleNotOccluded & (isFullscreenStackOccludingg ? false : true);
        }
        return stackVisibleNotOccluded;
    }

    public boolean isInSafeMode() {
        return this.mIsSafeMode;
    }

    public boolean setTaskWindowingModeSplitScreenPrimary(int taskId, int createMode, Rect initialBounds) {
        if (this.mIam == null) {
            return false;
        }
        try {
            return this.mIam.setTaskWindowingModeSplitScreenPrimary(taskId, createMode, true, false, initialBounds, true);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ActivityManager.StackInfo getSplitScreenPrimaryStack() {
        try {
            return this.mIam.getStackInfo(3, 0);
        } catch (RemoteException e) {
            return null;
        }
    }

    public boolean hasDockedTask() {
        ActivityManager.StackInfo stackInfo;
        if (this.mIam == null || (stackInfo = getSplitScreenPrimaryStack()) == null) {
            return false;
        }
        int userId = getCurrentUser();
        boolean hasUserTask = false;
        for (int i = stackInfo.taskUserIds.length - 1; i >= 0 && !hasUserTask; i--) {
            hasUserTask = stackInfo.taskUserIds[i] == userId;
        }
        return hasUserTask;
    }

    public boolean hasSoftNavigationBar() {
        try {
            return this.mIwm.hasNavigationBar();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasTransposedNavigationBar() {
        Rect insets = new Rect();
        getStableInsets(insets);
        return insets.right > 0;
    }

    public boolean isSystemUser(int userId) {
        return userId == 0;
    }

    public int getCurrentUser() {
        return this.mCurrentUserId;
    }

    public int getProcessUser() {
        if (this.mUm == null) {
            return 0;
        }
        return this.mUm.getUserHandle();
    }

    public boolean isTouchExplorationEnabled() {
        return this.mAccm != null && this.mAccm.isEnabled() && this.mAccm.isTouchExplorationEnabled();
    }

    public int getDeviceSmallestWidth() {
        if (this.mDisplay == null) {
            return 0;
        }
        Point smallestSizeRange = new Point();
        Point largestSizeRange = new Point();
        this.mDisplay.getCurrentSizeRange(smallestSizeRange, largestSizeRange);
        return smallestSizeRange.x;
    }

    public Rect getDisplayRect() {
        Rect displayRect = new Rect();
        if (this.mDisplay == null) {
            return displayRect;
        }
        Point p = new Point();
        this.mDisplay.getRealSize(p);
        displayRect.set(0, 0, p.x, p.y);
        return displayRect;
    }

    public Rect getWindowRect() {
        Rect windowRect = new Rect();
        try {
            if (this.mIam == null) {
                return windowRect;
            }
            try {
                ActivityManager.StackInfo stackInfo = this.mIam.getStackInfo(0, 3);
                if (stackInfo == null) {
                    stackInfo = this.mIam.getStackInfo(1, 1);
                }
                if (stackInfo != null) {
                    windowRect.set(stackInfo.bounds);
                }
                return windowRect;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
        }
        return windowRect;
    }

    public void startActivityAsUserAsync(final Intent intent, final ActivityOptions opts) {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.recents.misc.-$$Lambda$SystemServicesProxy$N7nq4D_yvcF7wooCA6t2HP24UJI
            @Override // java.lang.Runnable
            public final void run() {
                SystemServicesProxy systemServicesProxy = this.f$0;
                Intent intent2 = intent;
                ActivityOptions activityOptions = opts;
                systemServicesProxy.mContext.startActivityAsUser(intent2, activityOptions != null ? activityOptions.toBundle() : null, UserHandle.CURRENT);
            }
        });
    }

    public void startInPlaceAnimationOnFrontMostApplication(ActivityOptions opts) {
        if (this.mIam == null) {
            return;
        }
        try {
            this.mIam.startInPlaceAnimationOnFrontMostApplication(opts == null ? null : opts.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerDockedStackListener(IDockedStackListener listener) {
        if (this.mWm == null) {
            return;
        }
        try {
            this.mIwm.registerDockedStackListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDockedDividerSize(Context context) {
        Resources res = context.getResources();
        int dividerWindowWidth = res.getDimensionPixelSize(R.dimen.car_button_height);
        int dividerInsets = res.getDimensionPixelSize(R.dimen.car_borderless_button_horizontal_padding);
        return dividerWindowWidth - (2 * dividerInsets);
    }

    public void requestKeyboardShortcuts(Context context, WindowManager.KeyboardShortcutsReceiver receiver, int deviceId) {
        this.mWm.requestAppKeyboardShortcuts(receiver, deviceId);
    }

    public void getStableInsets(Rect outStableInsets) {
        if (this.mWm == null) {
            return;
        }
        try {
            this.mIwm.getStableInsets(0, outStableInsets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRecentsVisibility(final boolean visible) {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.recents.misc.-$$Lambda$SystemServicesProxy$ve6L74feVQWkpga-S7KU2FyhUuE
            @Override // java.lang.Runnable
            public final void run() {
                SystemServicesProxy.lambda$setRecentsVisibility$2(this.f$0, visible);
            }
        });
    }

    public static /* synthetic */ void lambda$setRecentsVisibility$2(SystemServicesProxy systemServicesProxy, boolean visible) {
        try {
            systemServicesProxy.mIwm.setRecentsVisibility(visible);
        } catch (RemoteException e) {
            Log.e("SystemServicesProxy", "Unable to reach window manager", e);
        }
    }

    public void setPipVisibility(final boolean visible) {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.recents.misc.-$$Lambda$SystemServicesProxy$yx6uKMR_Ve4h8p3CZXLJmEmcWpI
            @Override // java.lang.Runnable
            public final void run() {
                SystemServicesProxy.lambda$setPipVisibility$3(this.f$0, visible);
            }
        });
    }

    public static /* synthetic */ void lambda$setPipVisibility$3(SystemServicesProxy systemServicesProxy, boolean visible) {
        try {
            systemServicesProxy.mIwm.setPipVisibility(visible);
        } catch (RemoteException e) {
            Log.e("SystemServicesProxy", "Unable to reach window manager", e);
        }
    }

    public boolean isDreaming() {
        try {
            return this.mDreamManager.isDreaming();
        } catch (RemoteException e) {
            Log.e("SystemServicesProxy", "Failed to query dream manager.", e);
            return false;
        }
    }

    public void awakenDreamsAsync() {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.recents.misc.-$$Lambda$SystemServicesProxy$qne41-2ZLAnF5q0szRXUTgxl7BA
            @Override // java.lang.Runnable
            public final void run() {
                SystemServicesProxy.lambda$awakenDreamsAsync$4(this.f$0);
            }
        });
    }

    public static /* synthetic */ void lambda$awakenDreamsAsync$4(SystemServicesProxy systemServicesProxy) {
        try {
            systemServicesProxy.mDreamManager.awaken();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
