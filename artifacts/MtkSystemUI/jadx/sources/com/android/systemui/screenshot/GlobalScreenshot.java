package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.util.NotificationChannels;

/* JADX INFO: loaded from: classes.dex */
class GlobalScreenshot {
    private ImageView mBackgroundView;
    private float mBgPadding;
    private float mBgPaddingScale;
    private MediaActionSound mCameraSound;
    private Context mContext;
    private Display mDisplay;
    private Matrix mDisplayMatrix;
    private DisplayMetrics mDisplayMetrics;
    private int mNotificationIconSize;
    private NotificationManager mNotificationManager;
    private final int mPreviewHeight;
    private final int mPreviewWidth;
    private AsyncTask<Void, Void, Void> mSaveInBgTask;
    private Bitmap mScreenBitmap;
    private AnimatorSet mScreenshotAnimation;
    private ImageView mScreenshotFlash;
    private View mScreenshotLayout;
    private ScreenshotSelectorView mScreenshotSelectorView;
    private ImageView mScreenshotView;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private WindowManager mWindowManager;

    public GlobalScreenshot(Context context) {
        Resources r = context.getResources();
        this.mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mDisplayMatrix = new Matrix();
        this.mScreenshotLayout = layoutInflater.inflate(R.layout.global_screenshot, (ViewGroup) null);
        this.mBackgroundView = (ImageView) this.mScreenshotLayout.findViewById(R.id.global_screenshot_background);
        this.mScreenshotView = (ImageView) this.mScreenshotLayout.findViewById(R.id.global_screenshot);
        this.mScreenshotFlash = (ImageView) this.mScreenshotLayout.findViewById(R.id.global_screenshot_flash);
        this.mScreenshotSelectorView = (ScreenshotSelectorView) this.mScreenshotLayout.findViewById(R.id.global_screenshot_selector);
        this.mScreenshotLayout.setFocusable(true);
        this.mScreenshotSelectorView.setFocusable(true);
        this.mScreenshotSelectorView.setFocusableInTouchMode(true);
        this.mScreenshotLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.GlobalScreenshot.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.mWindowLayoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 525568, -3);
        this.mWindowLayoutParams.setTitle("ScreenshotAnimation");
        this.mWindowLayoutParams.layoutInDisplayCutoutMode = 1;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
        this.mDisplay = this.mWindowManager.getDefaultDisplay();
        this.mDisplayMetrics = new DisplayMetrics();
        this.mDisplay.getRealMetrics(this.mDisplayMetrics);
        this.mNotificationIconSize = r.getDimensionPixelSize(android.R.dimen.notification_large_icon_height);
        this.mBgPadding = r.getDimensionPixelSize(R.dimen.global_screenshot_bg_padding);
        this.mBgPaddingScale = this.mBgPadding / this.mDisplayMetrics.widthPixels;
        int panelWidth = 0;
        try {
            panelWidth = r.getDimensionPixelSize(R.dimen.notification_panel_width);
        } catch (Resources.NotFoundException e) {
        }
        this.mPreviewWidth = panelWidth <= 0 ? this.mDisplayMetrics.widthPixels : panelWidth;
        this.mPreviewHeight = r.getDimensionPixelSize(R.dimen.notification_max_height);
        this.mCameraSound = new MediaActionSound();
        this.mCameraSound.load(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveScreenshotInWorkerThread(Runnable finisher) {
        SaveImageInBackgroundData data = new SaveImageInBackgroundData();
        data.context = this.mContext;
        data.image = this.mScreenBitmap;
        data.iconSize = this.mNotificationIconSize;
        data.finisher = finisher;
        data.previewWidth = this.mPreviewWidth;
        data.previewheight = this.mPreviewHeight;
        if (this.mSaveInBgTask != null) {
            this.mSaveInBgTask.cancel(false);
        }
        this.mSaveInBgTask = new SaveImageInBackgroundTask(this.mContext, data, this.mNotificationManager).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeScreenshot(Runnable finisher, boolean statusBarVisible, boolean navBarVisible, Rect crop) {
        int rot = this.mDisplay.getRotation();
        int width = crop.width();
        int height = crop.height();
        this.mScreenBitmap = SurfaceControl.screenshot(crop, width, height, rot);
        if (this.mScreenBitmap == null) {
            notifyScreenshotError(this.mContext, this.mNotificationManager, R.string.screenshot_failed_to_capture_text);
            finisher.run();
        } else {
            this.mScreenBitmap.setHasAlpha(false);
            this.mScreenBitmap.prepareToDraw();
            startAnimation(finisher, this.mDisplayMetrics.widthPixels, this.mDisplayMetrics.heightPixels, statusBarVisible, navBarVisible);
        }
    }

    void takeScreenshot(Runnable finisher, boolean statusBarVisible, boolean navBarVisible) {
        this.mDisplay.getRealMetrics(this.mDisplayMetrics);
        takeScreenshot(finisher, statusBarVisible, navBarVisible, new Rect(0, 0, this.mDisplayMetrics.widthPixels, this.mDisplayMetrics.heightPixels));
    }

    void takeScreenshotPartial(final Runnable finisher, final boolean statusBarVisible, final boolean navBarVisible) {
        this.mWindowManager.addView(this.mScreenshotLayout, this.mWindowLayoutParams);
        this.mScreenshotSelectorView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.GlobalScreenshot.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                ScreenshotSelectorView view = (ScreenshotSelectorView) v;
                switch (event.getAction()) {
                    case 0:
                        view.startSelection((int) event.getX(), (int) event.getY());
                        return true;
                    case 1:
                        view.setVisibility(8);
                        GlobalScreenshot.this.mWindowManager.removeView(GlobalScreenshot.this.mScreenshotLayout);
                        final Rect rect = view.getSelectionRect();
                        if (rect != null && rect.width() != 0 && rect.height() != 0) {
                            GlobalScreenshot.this.mScreenshotLayout.post(new Runnable() { // from class: com.android.systemui.screenshot.GlobalScreenshot.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    GlobalScreenshot.this.takeScreenshot(finisher, statusBarVisible, navBarVisible, rect);
                                }
                            });
                        }
                        view.stopSelection();
                        return true;
                    case 2:
                        view.updateSelection((int) event.getX(), (int) event.getY());
                        return true;
                    default:
                        return false;
                }
            }
        });
        this.mScreenshotLayout.post(new Runnable() { // from class: com.android.systemui.screenshot.GlobalScreenshot.3
            @Override // java.lang.Runnable
            public void run() {
                GlobalScreenshot.this.mScreenshotSelectorView.setVisibility(0);
                GlobalScreenshot.this.mScreenshotSelectorView.requestFocus();
            }
        });
    }

    void stopScreenshot() {
        if (this.mScreenshotSelectorView.getSelectionRect() != null) {
            this.mWindowManager.removeView(this.mScreenshotLayout);
            this.mScreenshotSelectorView.stopSelection();
        }
    }

    private void startAnimation(final Runnable finisher, int w, int h, boolean statusBarVisible, boolean navBarVisible) {
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        if (powerManager.isPowerSaveMode()) {
            Toast.makeText(this.mContext, R.string.screenshot_saved_title, 0).show();
        }
        this.mScreenshotView.setImageBitmap(this.mScreenBitmap);
        this.mScreenshotLayout.requestFocus();
        if (this.mScreenshotAnimation != null) {
            if (this.mScreenshotAnimation.isStarted()) {
                this.mScreenshotAnimation.end();
            }
            this.mScreenshotAnimation.removeAllListeners();
        }
        this.mWindowManager.addView(this.mScreenshotLayout, this.mWindowLayoutParams);
        ValueAnimator screenshotDropInAnim = createScreenshotDropInAnimation();
        ValueAnimator screenshotFadeOutAnim = createScreenshotDropOutAnimation(w, h, statusBarVisible, navBarVisible);
        this.mScreenshotAnimation = new AnimatorSet();
        this.mScreenshotAnimation.playSequentially(screenshotDropInAnim, screenshotFadeOutAnim);
        this.mScreenshotAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.GlobalScreenshot.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                GlobalScreenshot.this.saveScreenshotInWorkerThread(finisher);
                GlobalScreenshot.this.mWindowManager.removeView(GlobalScreenshot.this.mScreenshotLayout);
                GlobalScreenshot.this.mScreenBitmap = null;
                GlobalScreenshot.this.mScreenshotView.setImageBitmap(null);
            }
        });
        this.mScreenshotLayout.post(new Runnable() { // from class: com.android.systemui.screenshot.GlobalScreenshot.5
            @Override // java.lang.Runnable
            public void run() {
                GlobalScreenshot.this.mScreenshotView.setLayerType(2, null);
                GlobalScreenshot.this.mScreenshotView.buildLayer();
                GlobalScreenshot.this.mScreenshotAnimation.start();
            }
        });
    }

    private ValueAnimator createScreenshotDropInAnimation() {
        final Interpolator flashAlphaInterpolator = new Interpolator() { // from class: com.android.systemui.screenshot.GlobalScreenshot.6
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float x) {
                if (x <= 0.60465115f) {
                    return (float) Math.sin(3.141592653589793d * ((double) (x / 0.60465115f)));
                }
                return 0.0f;
            }
        };
        final Interpolator scaleInterpolator = new Interpolator() { // from class: com.android.systemui.screenshot.GlobalScreenshot.7
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float x) {
                if (x < 0.30232558f) {
                    return 0.0f;
                }
                return (x - 0.60465115f) / 0.39534885f;
            }
        };
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setDuration(430L);
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.GlobalScreenshot.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                GlobalScreenshot.this.mBackgroundView.setAlpha(0.0f);
                GlobalScreenshot.this.mBackgroundView.setVisibility(0);
                GlobalScreenshot.this.mScreenshotView.setAlpha(0.0f);
                GlobalScreenshot.this.mScreenshotView.setTranslationX(0.0f);
                GlobalScreenshot.this.mScreenshotView.setTranslationY(0.0f);
                GlobalScreenshot.this.mScreenshotView.setScaleX(GlobalScreenshot.this.mBgPaddingScale + 1.0f);
                GlobalScreenshot.this.mScreenshotView.setScaleY(1.0f + GlobalScreenshot.this.mBgPaddingScale);
                GlobalScreenshot.this.mScreenshotView.setVisibility(0);
                GlobalScreenshot.this.mScreenshotFlash.setAlpha(0.0f);
                GlobalScreenshot.this.mScreenshotFlash.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                GlobalScreenshot.this.mScreenshotFlash.setVisibility(8);
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.GlobalScreenshot.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = ((Float) animation.getAnimatedValue()).floatValue();
                float scaleT = (1.0f + GlobalScreenshot.this.mBgPaddingScale) - (scaleInterpolator.getInterpolation(t) * 0.27499998f);
                GlobalScreenshot.this.mBackgroundView.setAlpha(scaleInterpolator.getInterpolation(t) * 0.5f);
                GlobalScreenshot.this.mScreenshotView.setAlpha(t);
                GlobalScreenshot.this.mScreenshotView.setScaleX(scaleT);
                GlobalScreenshot.this.mScreenshotView.setScaleY(scaleT);
                GlobalScreenshot.this.mScreenshotFlash.setAlpha(flashAlphaInterpolator.getInterpolation(t));
            }
        });
        return anim;
    }

    private ValueAnimator createScreenshotDropOutAnimation(int w, int h, boolean statusBarVisible, boolean navBarVisible) {
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setStartDelay(500L);
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.GlobalScreenshot.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                GlobalScreenshot.this.mBackgroundView.setVisibility(8);
                GlobalScreenshot.this.mScreenshotView.setVisibility(8);
                GlobalScreenshot.this.mScreenshotView.setLayerType(0, null);
            }
        });
        if (!statusBarVisible || !navBarVisible) {
            anim.setDuration(320L);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.GlobalScreenshot.11
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    float t = ((Float) animation.getAnimatedValue()).floatValue();
                    float scaleT = (0.725f + GlobalScreenshot.this.mBgPaddingScale) - (0.125f * t);
                    GlobalScreenshot.this.mBackgroundView.setAlpha((1.0f - t) * 0.5f);
                    GlobalScreenshot.this.mScreenshotView.setAlpha(1.0f - t);
                    GlobalScreenshot.this.mScreenshotView.setScaleX(scaleT);
                    GlobalScreenshot.this.mScreenshotView.setScaleY(scaleT);
                }
            });
        } else {
            final Interpolator scaleInterpolator = new Interpolator() { // from class: com.android.systemui.screenshot.GlobalScreenshot.12
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float x) {
                    if (x < 0.8604651f) {
                        return (float) (1.0d - Math.pow(1.0f - (x / 0.8604651f), 2.0d));
                    }
                    return 1.0f;
                }
            };
            float halfScreenWidth = (w - (this.mBgPadding * 2.0f)) / 2.0f;
            float halfScreenHeight = (h - (this.mBgPadding * 2.0f)) / 2.0f;
            final PointF finalPos = new PointF((-halfScreenWidth) + (0.45f * halfScreenWidth), (-halfScreenHeight) + (0.45f * halfScreenHeight));
            anim.setDuration(430L);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.GlobalScreenshot.13
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    float t = ((Float) animation.getAnimatedValue()).floatValue();
                    float scaleT = (0.725f + GlobalScreenshot.this.mBgPaddingScale) - (scaleInterpolator.getInterpolation(t) * 0.27500004f);
                    GlobalScreenshot.this.mBackgroundView.setAlpha((1.0f - t) * 0.5f);
                    GlobalScreenshot.this.mScreenshotView.setAlpha(1.0f - scaleInterpolator.getInterpolation(t));
                    GlobalScreenshot.this.mScreenshotView.setScaleX(scaleT);
                    GlobalScreenshot.this.mScreenshotView.setScaleY(scaleT);
                    GlobalScreenshot.this.mScreenshotView.setTranslationX(finalPos.x * t);
                    GlobalScreenshot.this.mScreenshotView.setTranslationY(finalPos.y * t);
                }
            });
        }
        return anim;
    }

    static void notifyScreenshotError(Context context, NotificationManager nManager, int msgResId) {
        Resources r = context.getResources();
        String errorMsg = r.getString(msgResId);
        Notification.Builder b = new Notification.Builder(context, NotificationChannels.ALERTS).setTicker(r.getString(R.string.screenshot_failed_title)).setContentTitle(r.getString(R.string.screenshot_failed_title)).setContentText(errorMsg).setSmallIcon(R.drawable.stat_notify_image_error).setWhen(System.currentTimeMillis()).setVisibility(1).setCategory("err").setAutoCancel(true).setColor(context.getColor(android.R.color.car_colorPrimary));
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService("device_policy");
        Intent intent = dpm.createAdminSupportIntent("policy_disable_screen_capture");
        if (intent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, UserHandle.CURRENT);
            b.setContentIntent(pendingIntent);
        }
        SystemUI.overrideNotificationAppName(context, b, true);
        Notification n = new Notification.BigTextStyle(b).bigText(errorMsg).build();
        nManager.notify(1, n);
    }

    public static class ScreenshotActionReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                ActivityManager.getService().closeSystemDialogs("screenshot");
            } catch (RemoteException e) {
            }
            Intent actionIntent = (Intent) intent.getParcelableExtra("android:screenshot_sharing_intent");
            String editorPackage = context.getResources().getString(R.string.config_screenshotEditor);
            if (actionIntent.getAction() == "android.intent.action.EDIT" && editorPackage != null && editorPackage.length() > 0) {
                actionIntent.setComponent(ComponentName.unflattenFromString(editorPackage));
                NotificationManager nm = (NotificationManager) context.getSystemService("notification");
                nm.cancel(1);
            } else {
                PendingIntent chooseAction = PendingIntent.getBroadcast(context, 0, new Intent(context, (Class<?>) TargetChosenReceiver.class), 1342177280);
                actionIntent = Intent.createChooser(actionIntent, null, chooseAction.getIntentSender()).addFlags(268468224);
            }
            ActivityOptions opts = ActivityOptions.makeBasic();
            opts.setDisallowEnterPictureInPictureWhileLaunching(true);
            context.startActivityAsUser(actionIntent, opts.toBundle(), UserHandle.CURRENT);
        }
    }

    public static class TargetChosenReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
            nm.cancel(1);
        }
    }

    public static class DeleteScreenshotReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!intent.hasExtra("android:screenshot_uri_id")) {
                return;
            }
            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
            Uri uri = Uri.parse(intent.getStringExtra("android:screenshot_uri_id"));
            nm.cancel(1);
            new DeleteImageInBackgroundTask(context).execute(uri);
        }
    }
}
