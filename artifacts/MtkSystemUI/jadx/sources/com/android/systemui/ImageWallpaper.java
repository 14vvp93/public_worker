package com.android.systemui;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Process;
import android.os.Trace;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class ImageWallpaper extends WallpaperService {
    private Context mContext;
    private DrawableEngine mEngine;
    private boolean mIsLowMemoryDevice = false;
    private boolean mIstablet;
    private WallpaperManager mWallpaperManager;

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        this.mWallpaperManager = (WallpaperManager) getSystemService(WallpaperManager.class);
        this.mIstablet = false;
        long totalMemory = Process.getTotalMemory() / 1048576;
        if (totalMemory <= 1024) {
            this.mIsLowMemoryDevice = true;
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        if (this.mIsLowMemoryDevice && this.mEngine != null) {
            this.mEngine.trimMemory(level);
        }
    }

    @Override // android.service.wallpaper.WallpaperService
    public WallpaperService.Engine onCreateEngine() {
        this.mEngine = new DrawableEngine();
        return this.mEngine;
    }

    class DrawableEngine extends WallpaperService.Engine {
        boolean loadWallpaperPending;
        Bitmap mBackground;
        int mBackgroundHeight;
        int mBackgroundWidth;
        private Display mDefaultDisplay;
        private int mDisplayHeightAtLastSurfaceSizeUpdate;
        private int mDisplayWidthAtLastSurfaceSizeUpdate;
        private int mLastRequestedHeight;
        private int mLastRequestedWidth;
        int mLastRotation;
        int mLastSurfaceHeight;
        int mLastSurfaceWidth;
        int mLastXTranslation;
        int mLastYTranslation;
        private AsyncTask<Void, Void, Bitmap> mLoader;
        private boolean mNeedsDrawAfterLoadingWallpaper;
        boolean mOffsetsChanged;
        private int mRotationAtLastSurfaceSizeUpdate;
        float mScale;
        private boolean mSurfaceRedrawNeeded;
        private boolean mSurfaceValid;
        private final DisplayInfo mTmpDisplayInfo;
        private final Runnable mUnloadWallpaperCallback;
        boolean mVisible;
        float mXOffset;
        float mYOffset;

        DrawableEngine() {
            super(ImageWallpaper.this);
            this.mUnloadWallpaperCallback = new Runnable() { // from class: com.android.systemui.-$$Lambda$ImageWallpaper$DrawableEngine$NJnw285KGKe6wEAiXHs5XJx5qSc
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.unloadWallpaper(false);
                }
            };
            this.mBackgroundWidth = -1;
            this.mBackgroundHeight = -1;
            this.mLastSurfaceWidth = -1;
            this.mLastSurfaceHeight = -1;
            this.mLastRotation = -1;
            this.mXOffset = 0.0f;
            this.mYOffset = 0.0f;
            this.mScale = 1.0f;
            this.mTmpDisplayInfo = new DisplayInfo();
            this.mVisible = true;
            this.mRotationAtLastSurfaceSizeUpdate = -1;
            this.mDisplayWidthAtLastSurfaceSizeUpdate = -1;
            this.mDisplayHeightAtLastSurfaceSizeUpdate = -1;
            this.mLastRequestedWidth = -1;
            this.mLastRequestedHeight = -1;
            this.loadWallpaperPending = false;
            setFixedSizeAllowed(true);
        }

        void trimMemory(int level) {
            if (level >= 10 && level <= 15 && this.mBackground != null) {
                unloadWallpaper(true);
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.mDefaultDisplay = ((WindowManager) ImageWallpaper.this.getSystemService(WindowManager.class)).getDefaultDisplay();
            setOffsetNotificationsEnabled(false);
            updateSurfaceSize(surfaceHolder, getDefaultDisplayInfo(), false);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onDestroy() {
            super.onDestroy();
            this.mBackground = null;
            unloadWallpaper(true);
        }

        boolean updateSurfaceSize(SurfaceHolder surfaceHolder, DisplayInfo displayInfo, boolean forDraw) {
            boolean hasWallpaper = true;
            if (this.mBackgroundWidth <= 0 || this.mBackgroundHeight <= 0) {
                loadWallpaper(forDraw);
                hasWallpaper = false;
            }
            int surfaceWidth = Math.max(displayInfo.logicalWidth, this.mBackgroundWidth);
            int surfaceHeight = Math.max(displayInfo.logicalHeight, this.mBackgroundHeight);
            if (!ImageWallpaper.this.mIstablet) {
                if (this.mBackgroundWidth * displayInfo.logicalHeight < this.mBackgroundHeight * displayInfo.logicalWidth) {
                    surfaceWidth = displayInfo.logicalWidth;
                    surfaceHeight = (this.mBackgroundHeight * displayInfo.logicalWidth) / this.mBackgroundWidth;
                } else {
                    surfaceWidth = (this.mBackgroundWidth * displayInfo.logicalHeight) / this.mBackgroundHeight;
                    surfaceHeight = displayInfo.logicalHeight;
                }
            }
            surfaceHolder.setFixedSize(surfaceWidth, surfaceHeight);
            this.mLastRequestedWidth = surfaceWidth;
            this.mLastRequestedHeight = surfaceHeight;
            return hasWallpaper;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onVisibilityChanged(boolean visible) {
            if (this.mVisible != visible) {
                this.mVisible = visible;
                if (visible) {
                    drawFrame();
                }
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixels, int yPixels) {
            if (this.mXOffset != xOffset || this.mYOffset != yOffset) {
                this.mXOffset = xOffset;
                this.mYOffset = yOffset;
                this.mOffsetsChanged = true;
            }
            drawFrame();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawFrame();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.mLastSurfaceHeight = -1;
            this.mLastSurfaceWidth = -1;
            this.mSurfaceValid = false;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this.mLastSurfaceHeight = -1;
            this.mLastSurfaceWidth = -1;
            this.mSurfaceValid = true;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            super.onSurfaceRedrawNeeded(holder);
            if (this.mBackground == null) {
                try {
                    updateBitmap(ImageWallpaper.this.mWallpaperManager.getBitmap(true));
                } catch (Exception e) {
                    Log.e("ImageWallpaper", "getBitmap failed.", e);
                }
            }
            this.mSurfaceRedrawNeeded = true;
            drawFrame();
        }

        private DisplayInfo getDefaultDisplayInfo() {
            this.mDefaultDisplay.getDisplayInfo(this.mTmpDisplayInfo);
            return this.mTmpDisplayInfo;
        }

        void drawFrame() {
            if (this.mSurfaceValid) {
                try {
                    Trace.traceBegin(8L, "drawWallpaper");
                    DisplayInfo displayInfo = getDefaultDisplayInfo();
                    int newRotation = displayInfo.rotation;
                    if (newRotation != this.mLastRotation) {
                        if (!updateSurfaceSize(getSurfaceHolder(), displayInfo, true)) {
                            Trace.traceEnd(8L);
                            return;
                        } else {
                            this.mRotationAtLastSurfaceSizeUpdate = newRotation;
                            this.mDisplayWidthAtLastSurfaceSizeUpdate = displayInfo.logicalWidth;
                            this.mDisplayHeightAtLastSurfaceSizeUpdate = displayInfo.logicalHeight;
                        }
                    }
                    SurfaceHolder sh = getSurfaceHolder();
                    Rect frame = sh.getSurfaceFrame();
                    int dw = frame.width();
                    int dh = frame.height();
                    boolean surfaceDimensionsChanged = (dw == this.mLastSurfaceWidth && dh == this.mLastSurfaceHeight) ? false : true;
                    boolean redrawNeeded = surfaceDimensionsChanged || newRotation != this.mLastRotation || this.mSurfaceRedrawNeeded;
                    if (!redrawNeeded && !this.mOffsetsChanged) {
                        Trace.traceEnd(8L);
                        return;
                    }
                    this.mLastRotation = newRotation;
                    this.mSurfaceRedrawNeeded = false;
                    if (this.mBackground == null) {
                        loadWallpaper(true);
                        Trace.traceEnd(8L);
                        return;
                    }
                    this.mScale = Math.max(1.0f, Math.max(dw / this.mBackground.getWidth(), dh / this.mBackground.getHeight()));
                    if (!ImageWallpaper.this.mIstablet) {
                        this.mScale = dw / this.mBackground.getWidth();
                    }
                    int availw = dw - ((int) (this.mBackground.getWidth() * this.mScale));
                    int availh = dh - ((int) (this.mBackground.getHeight() * this.mScale));
                    int xPixels = availw / 2;
                    int yPixels = availh / 2;
                    if (ImageWallpaper.this.mIstablet) {
                        int availwUnscaled = dw - this.mBackground.getWidth();
                        int availhUnscaled = dh - this.mBackground.getHeight();
                        if (availwUnscaled < 0) {
                            xPixels += (int) ((availwUnscaled * (this.mXOffset - 0.5f)) + 0.5f);
                        }
                        if (availhUnscaled < 0) {
                            yPixels += (int) ((availhUnscaled * (this.mYOffset - 0.5f)) + 0.5f);
                        }
                    } else {
                        int availwUnscaled2 = ((int) (dw / this.mScale)) - this.mBackground.getWidth();
                        int availhUnscaled2 = ((int) (dh / this.mScale)) - this.mBackground.getHeight();
                        if (availwUnscaled2 < 0) {
                            xPixels += (int) ((availwUnscaled2 * (this.mXOffset - 0.5f) * this.mScale) + 0.5f);
                        }
                        if (availhUnscaled2 < 0) {
                            yPixels += (int) ((availhUnscaled2 * (this.mYOffset - 0.5f) * this.mScale) + 0.5f);
                        }
                    }
                    int xPixels2 = xPixels;
                    int yPixels2 = yPixels;
                    this.mOffsetsChanged = false;
                    if (surfaceDimensionsChanged) {
                        this.mLastSurfaceWidth = dw;
                        this.mLastSurfaceHeight = dh;
                    }
                    if (!redrawNeeded && xPixels2 == this.mLastXTranslation && yPixels2 == this.mLastYTranslation) {
                        Trace.traceEnd(8L);
                        return;
                    }
                    this.mLastXTranslation = xPixels2;
                    this.mLastYTranslation = yPixels2;
                    drawWallpaperWithCanvas(sh, availw, availh, xPixels2, yPixels2);
                    scheduleUnloadWallpaper();
                    Trace.traceEnd(8L);
                } catch (Throwable th) {
                    Trace.traceEnd(8L);
                    throw th;
                }
            }
        }

        private void loadWallpaper(boolean needsDraw) {
            Log.w("ImageWallpaper", "#loadWallpaper [Sync Version]");
            this.mNeedsDrawAfterLoadingWallpaper |= needsDraw;
            Bitmap bt = null;
            if (!this.loadWallpaperPending) {
                this.loadWallpaperPending = true;
                Throwable exception = null;
                try {
                    bt = ImageWallpaper.this.mWallpaperManager.getBitmap(true);
                } catch (OutOfMemoryError | RuntimeException e) {
                    exception = e;
                }
                if (exception != null) {
                    Log.w("ImageWallpaper", "Unable to load wallpaper!", exception);
                    try {
                        ImageWallpaper.this.mWallpaperManager.clear();
                    } catch (IOException ex) {
                        Log.w("ImageWallpaper", "Unable reset to default wallpaper!", ex);
                    }
                    try {
                        bt = ImageWallpaper.this.mWallpaperManager.getBitmap(true);
                    } catch (OutOfMemoryError | RuntimeException e2) {
                        Log.w("ImageWallpaper", "Unable to load default wallpaper!", e2);
                    }
                }
                if (bt != null) {
                    updateBitmap(bt);
                    if (this.mNeedsDrawAfterLoadingWallpaper) {
                        drawFrame();
                    }
                    this.mNeedsDrawAfterLoadingWallpaper = false;
                }
                this.loadWallpaperPending = false;
            }
        }

        private void updateBitmap(Bitmap bitmap) {
            this.mBackground = null;
            this.mBackgroundWidth = -1;
            this.mBackgroundHeight = -1;
            if (bitmap != null) {
                this.mBackground = bitmap;
                this.mBackgroundWidth = this.mBackground.getWidth();
                this.mBackgroundHeight = this.mBackground.getHeight();
            }
            updateSurfaceSize(getSurfaceHolder(), getDefaultDisplayInfo(), false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unloadWallpaper(boolean forgetSize) {
            if (this.mLoader != null) {
                this.mLoader.cancel(false);
                this.mLoader = null;
            }
            this.mBackground = null;
            if (forgetSize) {
                this.mBackgroundWidth = -1;
                this.mBackgroundHeight = -1;
            }
            Surface surface = getSurfaceHolder().getSurface();
            surface.hwuiDestroy();
            ImageWallpaper.this.mWallpaperManager.forgetLoadedWallpaper();
        }

        private void scheduleUnloadWallpaper() {
            Handler handler = ImageWallpaper.this.getMainThreadHandler();
            handler.removeCallbacks(this.mUnloadWallpaperCallback);
            handler.postDelayed(this.mUnloadWallpaperCallback, 5000L);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        protected void dump(String prefix, FileDescriptor fd, PrintWriter out, String[] args) {
            super.dump(prefix, fd, out, args);
            out.print(prefix);
            out.println("ImageWallpaper.DrawableEngine:");
            out.print(prefix);
            out.print(" mBackground=");
            out.print(this.mBackground);
            out.print(" mBackgroundWidth=");
            out.print(this.mBackgroundWidth);
            out.print(" mBackgroundHeight=");
            out.println(this.mBackgroundHeight);
            out.print(prefix);
            out.print(" mLastRotation=");
            out.print(this.mLastRotation);
            out.print(" mLastSurfaceWidth=");
            out.print(this.mLastSurfaceWidth);
            out.print(" mLastSurfaceHeight=");
            out.println(this.mLastSurfaceHeight);
            out.print(prefix);
            out.print(" mXOffset=");
            out.print(this.mXOffset);
            out.print(" mYOffset=");
            out.println(this.mYOffset);
            out.print(prefix);
            out.print(" mVisible=");
            out.print(this.mVisible);
            out.print(" mOffsetsChanged=");
            out.println(this.mOffsetsChanged);
            out.print(prefix);
            out.print(" mLastXTranslation=");
            out.print(this.mLastXTranslation);
            out.print(" mLastYTranslation=");
            out.print(this.mLastYTranslation);
            out.print(" mScale=");
            out.println(this.mScale);
            out.print(prefix);
            out.print(" mLastRequestedWidth=");
            out.print(this.mLastRequestedWidth);
            out.print(" mLastRequestedHeight=");
            out.println(this.mLastRequestedHeight);
            out.print(prefix);
            out.println(" DisplayInfo at last updateSurfaceSize:");
            out.print(prefix);
            out.print("  rotation=");
            out.print(this.mRotationAtLastSurfaceSizeUpdate);
            out.print("  width=");
            out.print(this.mDisplayWidthAtLastSurfaceSizeUpdate);
            out.print("  height=");
            out.println(this.mDisplayHeightAtLastSurfaceSizeUpdate);
        }

        private void drawWallpaperWithCanvas(SurfaceHolder sh, int w, int h, int left, int top) {
            Canvas c = sh.lockHardwareCanvas();
            if (c != null) {
                try {
                    float right = left + (this.mBackground.getWidth() * this.mScale);
                    float bottom = top + (this.mBackground.getHeight() * this.mScale);
                    if (w < 0 || h < 0) {
                        c.save(2);
                        c.clipRect(left, top, right, bottom, Region.Op.DIFFERENCE);
                        c.drawColor(-16777216);
                        c.restore();
                    }
                    if (this.mBackground != null) {
                        RectF dest = new RectF(left, top, right, bottom);
                        Log.i("ImageWallpaper", "Redrawing in rect: " + dest + " with surface size: " + this.mLastRequestedWidth + "x" + this.mLastRequestedHeight);
                        c.drawBitmap(this.mBackground, (Rect) null, dest, (Paint) null);
                    }
                } finally {
                    sh.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
