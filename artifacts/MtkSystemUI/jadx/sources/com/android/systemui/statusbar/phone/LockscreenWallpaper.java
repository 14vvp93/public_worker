package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import libcore.io.IoUtils;

/* JADX INFO: loaded from: classes.dex */
public class LockscreenWallpaper extends IWallpaperManagerCallback.Stub implements Runnable {
    private final StatusBar mBar;
    private Bitmap mCache;
    private boolean mCached;
    private int mCurrentUserId = ActivityManager.getCurrentUser();
    private final Handler mH;
    private AsyncTask<Void, Void, LoaderResult> mLoader;
    private UserHandle mSelectedUser;
    private final KeyguardUpdateMonitor mUpdateMonitor;
    private final WallpaperManager mWallpaperManager;

    public LockscreenWallpaper(Context ctx, StatusBar bar, Handler h) {
        this.mBar = bar;
        this.mH = h;
        this.mWallpaperManager = (WallpaperManager) ctx.getSystemService("wallpaper");
        this.mUpdateMonitor = KeyguardUpdateMonitor.getInstance(ctx);
        IWallpaperManager service = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        try {
            service.setLockWallpaperCallback(this);
        } catch (RemoteException e) {
            Log.e("LockscreenWallpaper", "System dead?" + e);
        }
    }

    public Bitmap getBitmap() {
        if (this.mCached) {
            return this.mCache;
        }
        if (!this.mWallpaperManager.isWallpaperSupported()) {
            this.mCached = true;
            this.mCache = null;
            return null;
        }
        LoaderResult result = loadBitmap(this.mCurrentUserId, this.mSelectedUser);
        if (result.success) {
            this.mCached = true;
            this.mUpdateMonitor.setHasLockscreenWallpaper(result.bitmap != null);
            this.mCache = result.bitmap;
        }
        return this.mCache;
    }

    public LoaderResult loadBitmap(int currentUserId, UserHandle selectedUser) {
        int lockWallpaperUserId = selectedUser != null ? selectedUser.getIdentifier() : currentUserId;
        ParcelFileDescriptor fd = this.mWallpaperManager.getWallpaperFile(2, lockWallpaperUserId);
        if (fd == null) {
            return selectedUser != null ? LoaderResult.success(this.mWallpaperManager.getBitmapAsUser(selectedUser.getIdentifier(), true)) : LoaderResult.success(null);
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            return LoaderResult.success(BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, options));
        } catch (OutOfMemoryError e) {
            Log.w("LockscreenWallpaper", "Can't decode file", e);
            return LoaderResult.fail();
        } finally {
            IoUtils.closeQuietly(fd);
        }
    }

    public void setCurrentUser(int user) {
        if (user != this.mCurrentUserId) {
            if (this.mSelectedUser == null || user != this.mSelectedUser.getIdentifier()) {
                this.mCached = false;
            }
            this.mCurrentUserId = user;
        }
    }

    public void onWallpaperChanged() {
        postUpdateWallpaper();
    }

    public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) {
    }

    private void postUpdateWallpaper() {
        this.mH.removeCallbacks(this);
        this.mH.post(this);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.phone.LockscreenWallpaper$1] */
    @Override // java.lang.Runnable
    public void run() {
        if (this.mLoader != null) {
            this.mLoader.cancel(false);
        }
        final int currentUser = this.mCurrentUserId;
        final UserHandle selectedUser = this.mSelectedUser;
        this.mLoader = new AsyncTask<Void, Void, LoaderResult>() { // from class: com.android.systemui.statusbar.phone.LockscreenWallpaper.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public LoaderResult doInBackground(Void... params) {
                return LockscreenWallpaper.this.loadBitmap(currentUser, selectedUser);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(LoaderResult result) {
                super.onPostExecute(result);
                if (isCancelled()) {
                    return;
                }
                if (result.success) {
                    LockscreenWallpaper.this.mCached = true;
                    LockscreenWallpaper.this.mCache = result.bitmap;
                    LockscreenWallpaper.this.mUpdateMonitor.setHasLockscreenWallpaper(result.bitmap != null);
                    LockscreenWallpaper.this.mBar.updateMediaMetaData(true, true);
                }
                LockscreenWallpaper.this.mLoader = null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class LoaderResult {
        public final Bitmap bitmap;
        public final boolean success;

        LoaderResult(boolean success, Bitmap bitmap) {
            this.success = success;
            this.bitmap = bitmap;
        }

        static LoaderResult success(Bitmap b) {
            return new LoaderResult(true, b);
        }

        static LoaderResult fail() {
            return new LoaderResult(false, null);
        }
    }

    public static class WallpaperDrawable extends DrawableWrapper {
        private final ConstantState mState;
        private final Rect mTmpRect;

        public WallpaperDrawable(Resources r, Bitmap b) {
            this(r, new ConstantState(b));
        }

        private WallpaperDrawable(Resources r, ConstantState state) {
            super(new BitmapDrawable(r, state.mBackground));
            this.mTmpRect = new Rect();
            this.mState = state;
        }

        public void setXfermode(Xfermode mode) {
            getDrawable().setXfermode(mode);
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return -1;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return -1;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect bounds) {
            float scale;
            int vwidth = getBounds().width();
            int vheight = getBounds().height();
            int dwidth = this.mState.mBackground.getWidth();
            int dheight = this.mState.mBackground.getHeight();
            if (dwidth * vheight > vwidth * dheight) {
                scale = vheight / dheight;
            } else {
                float scale2 = vwidth;
                scale = scale2 / dwidth;
            }
            if (scale <= 1.0f) {
                scale = 1.0f;
            }
            float dy = (vheight - (dheight * scale)) * 0.5f;
            this.mTmpRect.set(bounds.left, bounds.top + Math.round(dy), bounds.left + Math.round(dwidth * scale), bounds.top + Math.round((dheight * scale) + dy));
            super.onBoundsChange(this.mTmpRect);
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public ConstantState getConstantState() {
            return this.mState;
        }

        static class ConstantState extends Drawable.ConstantState {
            private final Bitmap mBackground;

            ConstantState(Bitmap background) {
                this.mBackground = background;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return newDrawable(null);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable(Resources res) {
                return new WallpaperDrawable(res, this);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }
        }
    }
}
