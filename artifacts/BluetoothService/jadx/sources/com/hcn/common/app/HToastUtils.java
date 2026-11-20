package com.hcn.common.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.hcn.common.R;
import com.hcn.common.lang.HThreadUtils;
import com.hcn.common.utils.HConvertUtils;
import com.hcn.common.utils.HImageUtils;
import com.hcn.common.utils.HScreenUtils;
import com.hcn.common.utils.HStringUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.common.widget.HBarUtils;
import com.hcn.common.widget.HViewUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class HToastUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final HToastUtils DEFAULT_MAKER = make();
    private static final String NOTHING = "toast nothing";
    private static final String NULL = "toast null";
    private static final String TAG_TOAST = "TAG_TOAST";
    private static WeakReference<IToast> sWeakToast;
    private String mMode;
    private int mGravity = -1;
    private int mXOffset = -1;
    private int mYOffset = -1;
    private int mBgColor = COLOR_DEFAULT;
    private int mBgResource = -1;
    private int mTextColor = COLOR_DEFAULT;
    private int mTextSize = -1;
    private boolean isLong = false;
    private Drawable[] mIcons = new Drawable[4];
    private boolean isNotUseSystemToast = false;

    interface IToast {
        void cancel();

        void setToastView(View view);

        void setToastView(CharSequence charSequence);

        void show(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MODE {
        public static final String DARK = "dark";
        public static final String LIGHT = "light";
    }

    public static HToastUtils make() {
        return new HToastUtils();
    }

    public final HToastUtils setMode(String str) {
        this.mMode = str;
        return this;
    }

    public final HToastUtils setGravity(int i, int i2, int i3) {
        this.mGravity = i;
        this.mXOffset = i2;
        this.mYOffset = i3;
        return this;
    }

    public final HToastUtils setBgColor(int i) {
        this.mBgColor = i;
        return this;
    }

    public final HToastUtils setBgResource(int i) {
        this.mBgResource = i;
        return this;
    }

    public final HToastUtils setTextColor(int i) {
        this.mTextColor = i;
        return this;
    }

    public final HToastUtils setTextSize(int i) {
        this.mTextSize = i;
        return this;
    }

    public final HToastUtils setDurationIsLong(boolean z) {
        this.isLong = z;
        return this;
    }

    public final HToastUtils setLeftIcon(int i) {
        return setLeftIcon(ContextCompat.getDrawable(HUtilsEx.getApp(), i));
    }

    public final HToastUtils setLeftIcon(Drawable drawable) {
        this.mIcons[0] = drawable;
        return this;
    }

    public final HToastUtils setTopIcon(int i) {
        return setTopIcon(ContextCompat.getDrawable(HUtilsEx.getApp(), i));
    }

    public final HToastUtils setTopIcon(Drawable drawable) {
        this.mIcons[1] = drawable;
        return this;
    }

    public final HToastUtils setRightIcon(int i) {
        return setRightIcon(ContextCompat.getDrawable(HUtilsEx.getApp(), i));
    }

    public final HToastUtils setRightIcon(Drawable drawable) {
        this.mIcons[2] = drawable;
        return this;
    }

    public final HToastUtils setBottomIcon(int i) {
        return setBottomIcon(ContextCompat.getDrawable(HUtilsEx.getApp(), i));
    }

    public final HToastUtils setBottomIcon(Drawable drawable) {
        this.mIcons[3] = drawable;
        return this;
    }

    public final HToastUtils setNotUseSystemToast() {
        this.isNotUseSystemToast = true;
        return this;
    }

    public static HToastUtils getDefaultMaker() {
        return DEFAULT_MAKER;
    }

    public final void show(CharSequence charSequence) {
        show(charSequence, getDuration(), this);
    }

    public final void show(int i) {
        show(HStringUtils.getString(i), getDuration(), this);
    }

    public final void show(int i, Object... objArr) {
        show(HStringUtils.getString(i, objArr), getDuration(), this);
    }

    public final void show(String str, Object... objArr) {
        show(HStringUtils.format(str, objArr), getDuration(), this);
    }

    public final void show(View view) {
        show(view, getDuration(), this);
    }

    private int getDuration() {
        return this.isLong ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View tryApplyUtilsToastView(CharSequence charSequence) {
        if (!MODE.DARK.equals(this.mMode) && !MODE.LIGHT.equals(this.mMode)) {
            Drawable[] drawableArr = this.mIcons;
            if (drawableArr[0] == null && drawableArr[1] == null && drawableArr[2] == null && drawableArr[3] == null) {
                return null;
            }
        }
        View viewLayoutId2View = HViewUtils.layoutId2View(R.layout.utils_toast_view);
        TextView textView = (TextView) viewLayoutId2View.findViewById(android.R.id.message);
        if (MODE.DARK.equals(this.mMode)) {
            ((GradientDrawable) viewLayoutId2View.getBackground().mutate()).setColor(Color.parseColor("#BB000000"));
            textView.setTextColor(-1);
        }
        textView.setText(charSequence);
        if (this.mIcons[0] != null) {
            View viewFindViewById = viewLayoutId2View.findViewById(R.id.utvLeftIconView);
            ViewCompat.setBackground(viewFindViewById, this.mIcons[0]);
            viewFindViewById.setVisibility(0);
        }
        if (this.mIcons[1] != null) {
            View viewFindViewById2 = viewLayoutId2View.findViewById(R.id.utvTopIconView);
            ViewCompat.setBackground(viewFindViewById2, this.mIcons[1]);
            viewFindViewById2.setVisibility(0);
        }
        if (this.mIcons[2] != null) {
            View viewFindViewById3 = viewLayoutId2View.findViewById(R.id.utvRightIconView);
            ViewCompat.setBackground(viewFindViewById3, this.mIcons[2]);
            viewFindViewById3.setVisibility(0);
        }
        if (this.mIcons[3] != null) {
            View viewFindViewById4 = viewLayoutId2View.findViewById(R.id.utvBottomIconView);
            ViewCompat.setBackground(viewFindViewById4, this.mIcons[3]);
            viewFindViewById4.setVisibility(0);
        }
        return viewLayoutId2View;
    }

    public static void showShort(CharSequence charSequence) {
        show(charSequence, 0, DEFAULT_MAKER);
    }

    public static void showShort(int i) {
        show(HStringUtils.getString(i), 0, DEFAULT_MAKER);
    }

    public static void showShort(int i, Object... objArr) {
        show(HStringUtils.getString(i, objArr), 0, DEFAULT_MAKER);
    }

    public static void showShort(String str, Object... objArr) {
        show(HStringUtils.format(str, objArr), 0, DEFAULT_MAKER);
    }

    public static void showLong(CharSequence charSequence) {
        show(charSequence, 1, DEFAULT_MAKER);
    }

    public static void showLong(int i) {
        show(HStringUtils.getString(i), 1, DEFAULT_MAKER);
    }

    public static void showLong(int i, Object... objArr) {
        show(HStringUtils.getString(i, objArr), 1, DEFAULT_MAKER);
    }

    public static void showLong(String str, Object... objArr) {
        show(HStringUtils.format(str, objArr), 1, DEFAULT_MAKER);
    }

    public static void cancel() {
        HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.app.HToastUtils.1
            @Override // java.lang.Runnable
            public void run() {
                if (HToastUtils.sWeakToast != null) {
                    IToast iToast = (IToast) HToastUtils.sWeakToast.get();
                    if (iToast != null) {
                        iToast.cancel();
                    }
                    HToastUtils.sWeakToast = null;
                }
            }
        });
    }

    private static void show(CharSequence charSequence, int i, HToastUtils hToastUtils) {
        show(null, getToastFriendlyText(charSequence), i, hToastUtils);
    }

    private static void show(View view, int i, HToastUtils hToastUtils) {
        show(view, null, i, hToastUtils);
    }

    private static void show(final View view, final CharSequence charSequence, final int i, HToastUtils hToastUtils) {
        HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.app.HToastUtils.2
            @Override // java.lang.Runnable
            public void run() {
                HToastUtils.cancel();
                IToast iToastNewToast = HToastUtils.newToast(HToastUtils.this);
                HToastUtils.sWeakToast = new WeakReference(iToastNewToast);
                View view2 = view;
                if (view2 != null) {
                    iToastNewToast.setToastView(view2);
                } else {
                    iToastNewToast.setToastView(charSequence);
                }
                iToastNewToast.show(i);
            }
        });
    }

    private static CharSequence getToastFriendlyText(CharSequence charSequence) {
        if (charSequence == null) {
            return NULL;
        }
        return charSequence.length() == 0 ? NOTHING : charSequence;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IToast newToast(HToastUtils hToastUtils) {
        if (!hToastUtils.isNotUseSystemToast && NotificationManagerCompat.from(HUtilsEx.getApp()).areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT < 23) {
                return new SystemToast(hToastUtils);
            }
            if (!HPermissionUtils.isGrantedDrawOverlays()) {
                return new SystemToast(hToastUtils);
            }
        }
        if (Build.VERSION.SDK_INT < 25) {
            return new WindowManagerToast(hToastUtils, 2005);
        }
        if (HPermissionUtils.isGrantedDrawOverlays()) {
            if (Build.VERSION.SDK_INT >= 26) {
                return new WindowManagerToast(hToastUtils, 2038);
            }
            return new WindowManagerToast(hToastUtils, 2002);
        }
        return new ActivityToast(hToastUtils);
    }

    static final class SystemToast extends AbsToast {
        SystemToast(HToastUtils hToastUtils) {
            super(hToastUtils);
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    Field declaredField = Toast.class.getDeclaredField("mTN");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(this.mToast);
                    Field declaredField2 = declaredField.getType().getDeclaredField("mHandler");
                    declaredField2.setAccessible(true);
                    declaredField2.set(obj, new SafeHandler((Handler) declaredField2.get(obj)));
                } catch (Exception unused) {
                }
            }
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void show(int i) {
            if (this.mToast == null) {
                return;
            }
            this.mToast.setDuration(i);
            this.mToast.show();
        }

        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler handler) {
                this.impl = handler;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                this.impl.handleMessage(message);
            }

            @Override // android.os.Handler
            public void dispatchMessage(Message message) {
                try {
                    this.impl.dispatchMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static final class WindowManagerToast extends AbsToast {
        private WindowManager.LayoutParams mParams;
        private WindowManager mWM;

        WindowManagerToast(HToastUtils hToastUtils, int i) {
            super(hToastUtils);
            this.mParams = new WindowManager.LayoutParams();
            this.mWM = (WindowManager) HUtilsEx.getApp().getSystemService("window");
            this.mParams.type = i;
        }

        WindowManagerToast(HToastUtils hToastUtils, WindowManager windowManager, int i) {
            super(hToastUtils);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mWM = windowManager;
            layoutParams.type = i;
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void show(int i) {
            if (this.mToast == null) {
                return;
            }
            this.mParams.height = -2;
            this.mParams.width = -2;
            this.mParams.format = -3;
            this.mParams.windowAnimations = android.R.style.Animation.Toast;
            this.mParams.setTitle("ToastWithoutNotification");
            this.mParams.flags = 152;
            this.mParams.packageName = HUtilsEx.getApp().getPackageName();
            this.mParams.gravity = this.mToast.getGravity();
            if ((this.mParams.gravity & 7) == 7) {
                this.mParams.horizontalWeight = 1.0f;
            }
            if ((this.mParams.gravity & 112) == 112) {
                this.mParams.verticalWeight = 1.0f;
            }
            this.mParams.x = this.mToast.getXOffset();
            this.mParams.y = this.mToast.getYOffset();
            this.mParams.horizontalMargin = this.mToast.getHorizontalMargin();
            this.mParams.verticalMargin = this.mToast.getVerticalMargin();
            try {
                WindowManager windowManager = this.mWM;
                if (windowManager != null) {
                    windowManager.addView(this.mToastView, this.mParams);
                }
            } catch (Exception unused) {
            }
            HThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.hcn.common.app.HToastUtils.WindowManagerToast.1
                @Override // java.lang.Runnable
                public void run() {
                    WindowManagerToast.this.cancel();
                }
            }, i == 0 ? 2000L : 3500L);
        }

        @Override // com.hcn.common.app.HToastUtils.AbsToast, com.hcn.common.app.HToastUtils.IToast
        public void cancel() {
            try {
                WindowManager windowManager = this.mWM;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(this.mToastView);
                    this.mWM = null;
                }
            } catch (Exception unused) {
            }
            super.cancel();
        }
    }

    static final class ActivityToast extends AbsToast {
        private static int sShowingIndex;
        private IToast iToast;
        private HActivityUtils.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

        ActivityToast(HToastUtils hToastUtils) {
            super(hToastUtils);
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void show(int i) {
            if (this.mToast == null) {
                return;
            }
            if (!HAppUtils.isAppForeground()) {
                this.iToast = showSystemToast(i);
                return;
            }
            boolean z = false;
            for (Activity activity : HUtilsActivityLifecycleImpl.INSTANCE.getActivityList()) {
                if (HActivityUtils.isActivityAlive(activity)) {
                    if (!z) {
                        this.iToast = showWithActivityWindow(activity, i);
                        z = true;
                    } else {
                        showWithActivityView(activity, sShowingIndex, true);
                    }
                }
            }
            if (z) {
                registerLifecycleCallback();
                HThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.hcn.common.app.HToastUtils.ActivityToast.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ActivityToast.this.cancel();
                    }
                }, i == 0 ? 2000L : 3500L);
                sShowingIndex++;
                return;
            }
            this.iToast = showSystemToast(i);
        }

        @Override // com.hcn.common.app.HToastUtils.AbsToast, com.hcn.common.app.HToastUtils.IToast
        public void cancel() {
            Window window;
            ViewGroup viewGroup;
            View viewFindViewWithTag;
            if (isShowing()) {
                unregisterLifecycleCallback();
                for (Activity activity : HUtilsActivityLifecycleImpl.INSTANCE.getActivityList()) {
                    if (HActivityUtils.isActivityAlive(activity) && (window = activity.getWindow()) != null && (viewFindViewWithTag = (viewGroup = (ViewGroup) window.getDecorView()).findViewWithTag(HToastUtils.TAG_TOAST + (sShowingIndex - 1))) != null) {
                        try {
                            viewGroup.removeView(viewFindViewWithTag);
                        } catch (Exception unused) {
                        }
                    }
                }
            }
            IToast iToast = this.iToast;
            if (iToast != null) {
                iToast.cancel();
                this.iToast = null;
            }
            super.cancel();
        }

        private IToast showSystemToast(int i) {
            SystemToast systemToast = new SystemToast(this.mToastUtils);
            systemToast.mToast = this.mToast;
            systemToast.show(i);
            return systemToast;
        }

        private IToast showWithActivityWindow(Activity activity, int i) {
            WindowManagerToast windowManagerToast = new WindowManagerToast(this.mToastUtils, activity.getWindowManager(), 99);
            windowManagerToast.mToastView = getToastViewSnapshot(-1);
            windowManagerToast.mToast = this.mToast;
            windowManagerToast.show(i);
            return windowManagerToast;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showWithActivityView(Activity activity, int i, boolean z) {
            Window window = activity.getWindow();
            if (window != null) {
                ViewGroup viewGroup = (ViewGroup) window.getDecorView();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                layoutParams.gravity = this.mToast.getGravity();
                layoutParams.bottomMargin = this.mToast.getYOffset() + HBarUtils.getNavBarHeight();
                layoutParams.topMargin = this.mToast.getYOffset() + HBarUtils.getStatusBarHeight();
                layoutParams.leftMargin = this.mToast.getXOffset();
                View toastViewSnapshot = getToastViewSnapshot(i);
                if (z) {
                    toastViewSnapshot.setAlpha(0.0f);
                    toastViewSnapshot.animate().alpha(1.0f).setDuration(200L).start();
                }
                viewGroup.addView(toastViewSnapshot, layoutParams);
            }
        }

        private void registerLifecycleCallback() {
            final int i = sShowingIndex;
            HActivityUtils.ActivityLifecycleCallbacks activityLifecycleCallbacks = new HActivityUtils.ActivityLifecycleCallbacks() { // from class: com.hcn.common.app.HToastUtils.ActivityToast.2
                @Override // com.hcn.common.app.HActivityUtils.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity) {
                    if (ActivityToast.this.isShowing()) {
                        ActivityToast.this.showWithActivityView(activity, i, false);
                    }
                }
            };
            this.mActivityLifecycleCallbacks = activityLifecycleCallbacks;
            HActivityUtils.addActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }

        private void unregisterLifecycleCallback() {
            HActivityUtils.removeActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
            this.mActivityLifecycleCallbacks = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isShowing() {
            return this.mActivityLifecycleCallbacks != null;
        }
    }

    static abstract class AbsToast implements IToast {
        protected Toast mToast = new Toast(HUtilsEx.getApp());
        protected HToastUtils mToastUtils;
        protected View mToastView;

        AbsToast(HToastUtils hToastUtils) {
            this.mToastUtils = hToastUtils;
            if (hToastUtils.mGravity == -1 && this.mToastUtils.mXOffset == -1 && this.mToastUtils.mYOffset == -1) {
                return;
            }
            this.mToast.setGravity(this.mToastUtils.mGravity, this.mToastUtils.mXOffset, this.mToastUtils.mYOffset);
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void setToastView(View view) {
            this.mToastView = view;
            this.mToast.setView(view);
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void setToastView(CharSequence charSequence) {
            View viewTryApplyUtilsToastView = this.mToastUtils.tryApplyUtilsToastView(charSequence);
            if (viewTryApplyUtilsToastView != null) {
                setToastView(viewTryApplyUtilsToastView);
                processRtlIfNeed();
                return;
            }
            View view = this.mToast.getView();
            this.mToastView = view;
            if (view == null || view.findViewById(android.R.id.message) == null) {
                setToastView(HViewUtils.layoutId2View(R.layout.utils_toast_view));
            }
            TextView textView = (TextView) this.mToastView.findViewById(android.R.id.message);
            textView.setText(charSequence);
            if (this.mToastUtils.mTextColor != HToastUtils.COLOR_DEFAULT) {
                textView.setTextColor(this.mToastUtils.mTextColor);
            }
            if (this.mToastUtils.mTextSize != -1) {
                textView.setTextSize(this.mToastUtils.mTextSize);
            }
            setBg(textView);
            processRtlIfNeed();
        }

        private void processRtlIfNeed() {
            if (HViewUtils.isLayoutRtl()) {
                setToastView(getToastViewSnapshot(-1));
            }
        }

        private void setBg(TextView textView) {
            if (this.mToastUtils.mBgResource != -1) {
                this.mToastView.setBackgroundResource(this.mToastUtils.mBgResource);
                textView.setBackgroundColor(0);
                return;
            }
            if (this.mToastUtils.mBgColor != HToastUtils.COLOR_DEFAULT) {
                Drawable background = this.mToastView.getBackground();
                Drawable background2 = textView.getBackground();
                if (background != null && background2 != null) {
                    background.mutate().setColorFilter(new PorterDuffColorFilter(this.mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                    textView.setBackgroundColor(0);
                } else if (background != null) {
                    background.mutate().setColorFilter(new PorterDuffColorFilter(this.mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else if (background2 != null) {
                    background2.mutate().setColorFilter(new PorterDuffColorFilter(this.mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    this.mToastView.setBackgroundColor(this.mToastUtils.mBgColor);
                }
            }
        }

        @Override // com.hcn.common.app.HToastUtils.IToast
        public void cancel() {
            Toast toast = this.mToast;
            if (toast != null) {
                toast.cancel();
            }
            this.mToast = null;
            this.mToastView = null;
        }

        View getToastViewSnapshot(int i) {
            Bitmap bitmapView2Bitmap = HImageUtils.view2Bitmap(this.mToastView);
            ImageView imageView = new ImageView(HUtilsEx.getApp());
            imageView.setTag(HToastUtils.TAG_TOAST + i);
            imageView.setImageBitmap(bitmapView2Bitmap);
            return imageView;
        }
    }

    public static final class UtilsMaxWidthRelativeLayout extends RelativeLayout {
        private static final int SPACING = HConvertUtils.dp2px(80.0f);

        public UtilsMaxWidthRelativeLayout(Context context) {
            super(context);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        @Override // android.widget.RelativeLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(HScreenUtils.getAppScreenWidth() - SPACING, Integer.MIN_VALUE), i2);
        }
    }
}
