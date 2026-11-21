package android.sourceservice;

import android.Configures.HConfig;
import android.R;
import android.carstatus.CarStatusService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.link.CarletterInterface;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.android.server.LocalServices;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class ParkingObserver {
    private ContentResolver mContentResolver;
    private Context mContext;
    private ParkingPreventView mParkingPreventView;
    private WindowManager mWM;
    private final String TAG = "ParkingObserver";
    private final String ACTIVE_YOUTUBE = SourceService.ACTIVE_YOUTUBE;
    private final int SHOWDELAY = 500;
    private final int MSG_SHOW_PARKINGVIEW = 1;
    private Handler mParkingHandler = new Handler() { // from class: android.sourceservice.ParkingObserver.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ParkingObserver.this.showParkingView(msg.arg1 != 0);
            }
        }
    };

    private class ParkingPreventView extends View {
        private final int appHeight;
        private final int appWidth;
        private final float fontScale;
        private final int fontsize_sp;
        private final Paint paint;
        private final String parkingTitle;

        public ParkingPreventView(Context context) {
            super(context);
            this.fontsize_sp = 40;
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            this.appWidth = dm.widthPixels;
            if (SystemProperties.getInt("sys.disp.forceshownavbar", 0) > 0) {
                this.appHeight = dm.heightPixels - SystemProperties.getInt("ro.sys.customer.naviheight", 0);
            } else {
                this.appHeight = dm.heightPixels;
            }
            this.fontScale = dm.scaledDensity;
            String title = "Driving,please pay attention to safety!";
            try {
                try {
                    title = resources.getString(R.string.mime_type_audio_ext);
                } catch (Resources.NotFoundException e) {
                    Log.w("ParkingObserver", "Can not find parking title form system?");
                }
                this.parkingTitle = title;
                this.paint = new Paint();
                this.paint.setColor(-1);
                this.paint.setStrokeWidth(2.0f);
                this.paint.setDither(true);
                this.paint.setAntiAlias(true);
                this.paint.setTextSize(40.0f * this.fontScale);
                this.paint.setTextAlign(Paint.Align.CENTER);
            } catch (Throwable th) {
                this.parkingTitle = "Driving,please pay attention to safety!";
                throw th;
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(-16777216);
            canvas.drawText(this.parkingTitle, this.appWidth / 2.0f, this.appHeight / 2.0f, this.paint);
        }

        @Override // android.view.View
        public boolean dispatchTouchEvent(MotionEvent event) {
            return true;
        }

        @Override // android.view.View
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == 4) {
                return true;
            }
            return super.dispatchKeyEvent(event);
        }
    }

    public ParkingObserver(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mWM = (WindowManager) context.getSystemService("window");
        Log.v("ParkingObserver", "create parkingObserver...");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showParkingView(boolean enable) {
        if (this.mParkingPreventView == null && enable) {
            this.mParkingPreventView = new ParkingPreventView(this.mContext);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
            lp.type = 2003;
            lp.flags = CarletterInterface.KEYCODE_KNOB_LEFT;
            lp.setTitle("ParkingPreventTitle");
            this.mWM.addView(this.mParkingPreventView, lp);
        } else if (this.mParkingPreventView != null && !enable) {
            this.mWM.removeView(this.mParkingPreventView);
            this.mParkingPreventView = null;
        }
        Log.v("ParkingObserver", "parking prevent view show:" + enable);
    }

    private boolean checkCanWatchVideo() {
        boolean isLowSpeedEnable = false;
        boolean isWatchVideoEnable = Settings.System.getInt(this.mContentResolver, HConfig.driving_disable_video, 0) == 1;
        if (!isWatchVideoEnable) {
            isLowSpeedEnable = Settings.System.getInt(this.mContentResolver, HConfig.VEHICLE_SPEED_VIDEO_STATE, 1) == 0;
        }
        CarStatusService carStatus = (CarStatusService) LocalServices.getService(CarStatusService.class);
        boolean isParkingEnable = carStatus.getParkingStatus() != 0;
        Log.v("ParkingObserver", "check can watch video:" + isWatchVideoEnable + "|" + isLowSpeedEnable + "|" + isParkingEnable);
        return isWatchVideoEnable || isLowSpeedEnable || isParkingEnable;
    }

    public void checkParkingView(String topPackage) {
        int isParkingViewShow = Settings.System.getInt(this.mContentResolver, HConfig.PARKINGVIEW_THIRDAPP_ENABLE, 0);
        if (isParkingViewShow == 0 || topPackage == null) {
            return;
        }
        boolean canWatchVideo = true;
        if (topPackage.startsWith(SourceService.ACTIVE_YOUTUBE)) {
            canWatchVideo = checkCanWatchVideo();
        }
        this.mParkingHandler.removeMessages(1);
        this.mParkingHandler.sendMessageDelayed(this.mParkingHandler.obtainMessage(1, canWatchVideo ? 0 : 1, 0), 500L);
    }
}
