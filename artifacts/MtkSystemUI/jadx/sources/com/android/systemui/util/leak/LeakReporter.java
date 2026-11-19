package com.android.systemui.util.leak;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.google.android.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class LeakReporter {
    private final Context mContext;
    private final LeakDetector mLeakDetector;
    private final String mLeakReportEmail;

    public LeakReporter(Context context, LeakDetector leakDetector, String leakReportEmail) {
        this.mContext = context;
        this.mLeakDetector = leakDetector;
        this.mLeakReportEmail = leakReportEmail;
    }

    public void dumpLeak(int garbageCount) {
        try {
            File leakDir = new File(this.mContext.getCacheDir(), "leak");
            leakDir.mkdir();
            File hprofFile = new File(leakDir, "leak.hprof");
            Debug.dumpHprofData(hprofFile.getAbsolutePath());
            File dumpFile = new File(leakDir, "leak.dump");
            FileOutputStream fos = new FileOutputStream(dumpFile);
            Throwable th = null;
            try {
                try {
                    PrintWriter w = new PrintWriter(fos);
                    w.print("Build: ");
                    w.println(SystemProperties.get("ro.build.description"));
                    w.println();
                    w.flush();
                    this.mLeakDetector.dump(fos.getFD(), w, new String[0]);
                    w.close();
                    fos.close();
                    NotificationManager notiMan = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
                    NotificationChannel channel = new NotificationChannel("leak", "Leak Alerts", 4);
                    channel.enableVibration(true);
                    notiMan.createNotificationChannel(channel);
                    Notification.Builder builder = new Notification.Builder(this.mContext, channel.getId()).setAutoCancel(true).setShowWhen(true).setContentTitle("Memory Leak Detected").setContentText(String.format("SystemUI has detected %d leaked objects. Tap to send", Integer.valueOf(garbageCount))).setSmallIcon(R.drawable.pointer_help_large).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, getIntent(hprofFile, dumpFile), 201326592, null, UserHandle.CURRENT));
                    notiMan.notify("LeakReporter", 0, builder.build());
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th;
                if (th4 == null) {
                    fos.close();
                    throw th3;
                }
                try {
                    fos.close();
                    throw th3;
                } catch (Throwable th5) {
                    th4.addSuppressed(th5);
                    throw th3;
                }
            }
        } catch (IOException e) {
            Log.e("LeakReporter", "Couldn't dump heap for leak", e);
        }
    }

    private Intent getIntent(File hprofFile, File dumpFile) throws XmlPullParserException, IOException {
        Uri dumpUri = FileProvider.getUriForFile(this.mContext, "com.android.systemui.fileprovider", dumpFile);
        Uri hprofUri = FileProvider.getUriForFile(this.mContext, "com.android.systemui.fileprovider", hprofFile);
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(1);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.leakreport");
        intent.putExtra("android.intent.extra.SUBJECT", "SystemUI leak report");
        StringBuilder messageBody = new StringBuilder("Build info: ").append(SystemProperties.get("ro.build.description"));
        intent.putExtra("android.intent.extra.TEXT", messageBody.toString());
        ClipData clipData = new ClipData(null, new String[]{"application/vnd.android.leakreport"}, new ClipData.Item(null, null, null, dumpUri));
        ArrayList<Uri> attachments = Lists.newArrayList(new Uri[]{dumpUri});
        clipData.addItem(new ClipData.Item(null, null, null, hprofUri));
        attachments.add(hprofUri);
        intent.setClipData(clipData);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", attachments);
        String leakReportEmail = this.mLeakReportEmail;
        if (leakReportEmail != null) {
            intent.putExtra("android.intent.extra.EMAIL", new String[]{leakReportEmail});
        }
        return intent;
    }
}
