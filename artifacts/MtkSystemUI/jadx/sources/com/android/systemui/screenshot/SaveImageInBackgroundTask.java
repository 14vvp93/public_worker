package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.util.NotificationChannels;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: compiled from: GlobalScreenshot.java */
/* JADX INFO: loaded from: classes.dex */
class SaveImageInBackgroundTask extends AsyncTask<Void, Void, Void> {
    private final String mImageFileName;
    private final String mImageFilePath;
    private final int mImageHeight;
    private final long mImageTime;
    private final int mImageWidth;
    private final Notification.Builder mNotificationBuilder;
    private final NotificationManager mNotificationManager;
    private final Notification.BigPictureStyle mNotificationStyle;
    private final SaveImageInBackgroundData mParams;
    private final Notification.Builder mPublicNotificationBuilder;
    private final File mScreenshotDir;

    SaveImageInBackgroundTask(Context context, SaveImageInBackgroundData data, NotificationManager nManager) {
        Resources r = context.getResources();
        this.mParams = data;
        this.mImageTime = System.currentTimeMillis();
        String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(this.mImageTime));
        this.mImageFileName = String.format("Screenshot_%s.png", imageDate);
        this.mScreenshotDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Screenshots");
        this.mImageFilePath = new File(this.mScreenshotDir, this.mImageFileName).getAbsolutePath();
        this.mImageWidth = data.image.getWidth();
        this.mImageHeight = data.image.getHeight();
        int iconSize = data.iconSize;
        int previewWidth = data.previewWidth;
        int previewHeight = data.previewheight;
        Paint paint = new Paint();
        ColorMatrix desat = new ColorMatrix();
        desat.setSaturation(0.25f);
        paint.setColorFilter(new ColorMatrixColorFilter(desat));
        Matrix matrix = new Matrix();
        matrix.setTranslate((previewWidth - this.mImageWidth) / 2, (previewHeight - this.mImageHeight) / 2);
        Bitmap picture = generateAdjustedHwBitmap(data.image, previewWidth, previewHeight, matrix, paint, 1090519039);
        float scale = iconSize / Math.min(this.mImageWidth, this.mImageHeight);
        matrix.setScale(scale, scale);
        matrix.postTranslate((iconSize - (this.mImageWidth * scale)) / 2.0f, (iconSize - (this.mImageHeight * scale)) / 2.0f);
        Bitmap icon = generateAdjustedHwBitmap(data.image, iconSize, iconSize, matrix, paint, 1090519039);
        this.mNotificationManager = nManager;
        long now = System.currentTimeMillis();
        this.mNotificationStyle = new Notification.BigPictureStyle().bigPicture(picture.createAshmemBitmap());
        this.mPublicNotificationBuilder = new Notification.Builder(context, NotificationChannels.SCREENSHOTS_HEADSUP).setContentTitle(r.getString(R.string.screenshot_saving_title)).setSmallIcon(R.drawable.stat_notify_image).setCategory("progress").setWhen(now).setShowWhen(true).setColor(r.getColor(android.R.color.car_colorPrimary));
        SystemUI.overrideNotificationAppName(context, this.mPublicNotificationBuilder, true);
        this.mNotificationBuilder = new Notification.Builder(context, NotificationChannels.SCREENSHOTS_HEADSUP).setContentTitle(r.getString(R.string.screenshot_saving_title)).setSmallIcon(R.drawable.stat_notify_image).setWhen(now).setShowWhen(true).setColor(r.getColor(android.R.color.car_colorPrimary)).setStyle(this.mNotificationStyle).setPublicVersion(this.mPublicNotificationBuilder.build());
        this.mNotificationBuilder.setFlag(32, true);
        SystemUI.overrideNotificationAppName(context, this.mNotificationBuilder, true);
        this.mNotificationManager.notify(1, this.mNotificationBuilder.build());
        this.mNotificationBuilder.setLargeIcon(icon.createAshmemBitmap());
        this.mNotificationStyle.bigLargeIcon((Bitmap) null);
    }

    private Bitmap generateAdjustedHwBitmap(Bitmap bitmap, int width, int height, Matrix matrix, Paint paint, int color) {
        Picture picture = new Picture();
        Canvas canvas = picture.beginRecording(width, height);
        canvas.drawColor(color);
        canvas.drawBitmap(bitmap, matrix, paint);
        picture.endRecording();
        return Bitmap.createBitmap(picture);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... params) {
        if (isCancelled()) {
            return null;
        }
        Process.setThreadPriority(-2);
        Context context = this.mParams.context;
        Bitmap image = this.mParams.image;
        Resources r = context.getResources();
        try {
            this.mScreenshotDir.mkdirs();
            long dateSeconds = this.mImageTime / 1000;
            OutputStream out = new FileOutputStream(this.mImageFilePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            ContentValues values = new ContentValues();
            ContentResolver resolver = context.getContentResolver();
            values.put("_data", this.mImageFilePath);
            values.put("title", this.mImageFileName);
            values.put("_display_name", this.mImageFileName);
            values.put("datetaken", Long.valueOf(this.mImageTime));
            values.put("date_added", Long.valueOf(dateSeconds));
            values.put("date_modified", Long.valueOf(dateSeconds));
            values.put("mime_type", "image/png");
            values.put("width", Integer.valueOf(this.mImageWidth));
            values.put("height", Integer.valueOf(this.mImageHeight));
            values.put("_size", Long.valueOf(new File(this.mImageFilePath).length()));
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            PendingIntent deleteAction = PendingIntent.getBroadcast(context, 0, new Intent(context, (Class<?>) GlobalScreenshot.DeleteScreenshotReceiver.class).putExtra("android:screenshot_uri_id", uri.toString()), 1342177280);
            Notification.Action.Builder deleteActionBuilder = new Notification.Action.Builder(R.drawable.ic_screenshot_delete, r.getString(android.R.string.biometric_dangling_notification_action_not_now), deleteAction);
            this.mNotificationBuilder.addAction(deleteActionBuilder.build());
            this.mParams.imageUri = uri;
            this.mParams.image = null;
            this.mParams.errorMsgResId = 0;
        } catch (Exception e) {
            Slog.e("SaveImageInBackgroundTask", "unable to save screenshot", e);
            this.mParams.clearImage();
            this.mParams.errorMsgResId = R.string.screenshot_failed_to_save_text;
        }
        if (image != null) {
            image.recycle();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Void params) {
        if (this.mParams.errorMsgResId != 0) {
            GlobalScreenshot.notifyScreenshotError(this.mParams.context, this.mNotificationManager, this.mParams.errorMsgResId);
        } else {
            Context context = this.mParams.context;
            Resources r = context.getResources();
            Intent launchIntent = new Intent("android.intent.action.VIEW");
            launchIntent.setDataAndType(this.mParams.imageUri, "image/png");
            launchIntent.setFlags(268435457);
            long now = System.currentTimeMillis();
            this.mPublicNotificationBuilder.setContentTitle(r.getString(R.string.screenshot_saved_title)).setContentText(r.getString(R.string.screenshot_saved_text)).setContentIntent(PendingIntent.getActivity(this.mParams.context, 0, launchIntent, 67108864)).setWhen(now).setAutoCancel(true).setColor(context.getColor(android.R.color.car_colorPrimary));
            this.mNotificationBuilder.setContentTitle(r.getString(R.string.screenshot_saved_title)).setContentText(r.getString(R.string.screenshot_saved_text)).setContentIntent(PendingIntent.getActivity(this.mParams.context, 0, launchIntent, 67108864)).setWhen(now).setAutoCancel(true).setColor(context.getColor(android.R.color.car_colorPrimary)).setPublicVersion(this.mPublicNotificationBuilder.build()).setFlag(32, false);
            this.mNotificationManager.notify(1, this.mNotificationBuilder.build());
        }
        this.mParams.finisher.run();
        this.mParams.clearContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onCancelled(Void params) {
        this.mParams.finisher.run();
        this.mParams.clearImage();
        this.mParams.clearContext();
        this.mNotificationManager.cancel(1);
    }
}
