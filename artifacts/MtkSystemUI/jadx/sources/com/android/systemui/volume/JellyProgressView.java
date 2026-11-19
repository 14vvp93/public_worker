package com.android.systemui.volume;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.android.systemui.R;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class JellyProgressView extends View {
    private AudioManager audioManager;
    private BitmapShader bitmapShader;
    private float currentProgress;
    private String currentSkinName;
    private int maxVolume;
    private float progress;
    private ValueAnimator progressAnimator;
    private Bitmap progressBitmap;
    private Paint progressPaint;
    private RectF progressRect;
    private int streamType;
    private boolean useClockwiseDirection;
    protected BroadcastReceiver volumeReceiver;

    public JellyProgressView(Context context) {
        super(context);
        this.progress = 50.0f;
        this.currentProgress = 50.0f;
        this.streamType = 3;
        this.useClockwiseDirection = false;
        init();
    }

    public JellyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.progress = 50.0f;
        this.currentProgress = 50.0f;
        this.streamType = 3;
        this.useClockwiseDirection = false;
        init();
    }

    public JellyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.progress = 50.0f;
        this.currentProgress = 50.0f;
        this.streamType = 3;
        this.useClockwiseDirection = false;
        init();
    }

    private void init() {
        this.progressPaint = new Paint(1);
        this.progressPaint.setStyle(Paint.Style.FILL);
        this.progressRect = new RectF();
        setupProgressAnimation();
        loadProgressBitmap();
        this.audioManager = (AudioManager) getContext().getSystemService("audio");
        this.maxVolume = this.audioManager.getStreamMaxVolume(this.streamType);
        updateProgressFromVolume();
    }

    private void registerVolumeChangedReceiver() {
        if (this.volumeReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            this.volumeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.volume.JellyProgressView.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                    int streamValue = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1);
                    Log.d("JellyProgressView", "onReceive streamType : " + streamType + ", streamValue : " + streamValue);
                    if (streamType == JellyProgressView.this.streamType) {
                        float newProgress = (streamValue * 100.0f) / JellyProgressView.this.maxVolume;
                        JellyProgressView.this.setProgress(newProgress);
                    }
                }
            };
            Log.d("JellyProgressView", "registerVolumeChangedReceiver");
            getContext().registerReceiver(this.volumeReceiver, intentFilter);
        }
    }

    private void loadProgressBitmap() {
        int resourceId = 0;
        this.currentSkinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
        this.useClockwiseDirection = !"p38".equals(this.currentSkinName);
        if (this.currentSkinName != null && !this.currentSkinName.isEmpty()) {
            resourceId = R.drawable.xt540_h_volume_p;
        }
        if (resourceId == 0) {
            resourceId = R.drawable.p38_volume_p;
            this.useClockwiseDirection = false;
        }
        this.progressBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        if (this.progressBitmap != null) {
            this.bitmapShader = new BitmapShader(this.progressBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            this.progressPaint.setShader(this.bitmapShader);
        } else {
            Log.e("ProgressBar", "Failed to load progress bitmap");
        }
    }

    private void setupProgressAnimation() {
        this.progressAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.progressAnimator.setInterpolator(new DecelerateInterpolator());
        this.progressAnimator.setDuration(300L);
        this.progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.JellyProgressView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                JellyProgressView.this.currentProgress += (JellyProgressView.this.progress - JellyProgressView.this.currentProgress) * fraction;
                JellyProgressView.this.invalidate();
            }
        });
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!this.useClockwiseDirection) {
            this.progressRect.set((-h) / 2, 0.0f, h / 2, h);
        } else {
            float centerX = w / 2.0f;
            float centerY = h;
            float radius = h;
            this.progressRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        }
        updateBitmapShader();
    }

    private void updateBitmapShader() {
        if (this.progressBitmap != null) {
            this.bitmapShader = new BitmapShader(this.progressBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            this.progressPaint.setShader(this.bitmapShader);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float startAngle;
        super.onDraw(canvas);
        if (this.currentProgress <= 0.1f) {
            return;
        }
        float sweepAngle = 180.0f * (this.currentProgress / 100.0f);
        if (this.useClockwiseDirection) {
            startAngle = 180.0f;
            canvas.drawArc(this.progressRect, 180.0f, sweepAngle, false, this.progressPaint);
        } else {
            startAngle = 90.0f;
            sweepAngle = -sweepAngle;
        }
        canvas.drawArc(this.progressRect, startAngle, sweepAngle, true, this.progressPaint);
    }

    public void setProgress(float progress) {
        float newProgress = Math.max(0.0f, Math.min(100.0f, progress));
        if (this.progress != newProgress) {
            this.progress = newProgress;
            if (this.progressAnimator != null) {
                if (this.progressAnimator.isRunning()) {
                    this.progressAnimator.cancel();
                }
                this.progressAnimator.start();
            } else {
                this.currentProgress = progress;
                invalidate();
            }
        }
    }

    private void updateProgressFromVolume() {
        int currentVolume = this.audioManager.getStreamVolume(this.streamType);
        this.progress = (currentVolume * 100.0f) / this.maxVolume;
        this.currentProgress = this.progress;
        invalidate();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerVolumeChangedReceiver();
        updateProgressFromVolume();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.progressAnimator != null) {
            this.progressAnimator.cancel();
        }
        if (this.volumeReceiver != null) {
            try {
                getContext().unregisterReceiver(this.volumeReceiver);
                this.volumeReceiver = null;
                Log.d("JellyProgressView", "unregisterVolumeChangedReceiver");
            } catch (Exception e) {
                Log.e("JellyProgressView", "Error unregistering volume receiver", e);
            }
        }
        if (this.progressBitmap != null) {
            this.progressBitmap.recycle();
            this.progressBitmap = null;
        }
    }

    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Method getter = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
        }
    }
}
