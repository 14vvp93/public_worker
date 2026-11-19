package com.android.systemui.util.leak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.android.systemui.Dependency;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes.dex */
public class DumpTruck {
    final StringBuilder body = new StringBuilder();
    private final Context context;
    private Uri hprofUri;

    public DumpTruck(Context context) {
        this.context = context;
    }

    public DumpTruck captureHeaps(int[] pids) {
        GarbageMonitor.ProcessMemInfo info;
        GarbageMonitor gm = (GarbageMonitor) Dependency.get(GarbageMonitor.class);
        File dumpDir = new File(this.context.getCacheDir(), "leak");
        dumpDir.mkdirs();
        this.hprofUri = null;
        this.body.setLength(0);
        StringBuilder sb = this.body;
        sb.append("Build: ");
        sb.append(Build.DISPLAY);
        sb.append("\n\nProcesses:\n");
        ArrayList<String> paths = new ArrayList<>();
        int myPid = Process.myPid();
        int[] pids_copy = Arrays.copyOf(pids, pids.length);
        for (int pid : pids_copy) {
            StringBuilder sb2 = this.body;
            sb2.append("  pid ");
            sb2.append(pid);
            if (gm != null && (info = gm.getMemInfo(pid)) != null) {
                StringBuilder sb3 = this.body;
                sb3.append(":");
                sb3.append(" up=");
                sb3.append(info.getUptime());
                sb3.append(" pss=");
                sb3.append(info.currentPss);
                sb3.append(" uss=");
                sb3.append(info.currentUss);
            }
            if (pid == myPid) {
                String path = new File(dumpDir, String.format("heap-%d.ahprof", Integer.valueOf(pid))).getPath();
                Log.v("DumpTruck", "Dumping memory info for process " + pid + " to " + path);
                try {
                    Debug.dumpHprofData(path);
                    paths.add(path);
                    this.body.append(" (hprof attached)");
                } catch (IOException e) {
                    Log.e("DumpTruck", "error dumping memory:", e);
                    StringBuilder sb4 = this.body;
                    sb4.append("\n** Could not dump heap: \n");
                    sb4.append(e.toString());
                    sb4.append("\n");
                }
            }
            this.body.append("\n");
        }
        try {
            String zipfile = new File(dumpDir, String.format("hprof-%d.zip", Long.valueOf(System.currentTimeMillis()))).getCanonicalPath();
            if (zipUp(zipfile, paths)) {
                File pathFile = new File(zipfile);
                this.hprofUri = FileProvider.getUriForFile(this.context, "com.android.systemui.fileprovider", pathFile);
            }
        } catch (IOException e2) {
            Log.e("DumpTruck", "unable to zip up heapdumps", e2);
            StringBuilder sb5 = this.body;
            sb5.append("\n** Could not zip up files: \n");
            sb5.append(e2.toString());
            sb5.append("\n");
        }
        return this;
    }

    public Intent createShareIntent() {
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.addFlags(268435456);
        shareIntent.addFlags(1);
        shareIntent.putExtra("android.intent.extra.SUBJECT", "SystemUI memory dump");
        shareIntent.putExtra("android.intent.extra.TEXT", this.body.toString());
        if (this.hprofUri != null) {
            shareIntent.setType("application/zip");
            shareIntent.putExtra("android.intent.extra.STREAM", this.hprofUri);
        }
        return shareIntent;
    }

    private static boolean zipUp(String zipfilePath, ArrayList<String> paths) throws Exception {
        Throwable th;
        Throwable th2;
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipfilePath));
            try {
                byte[] buf = new byte[524288];
                for (String filename : paths) {
                    InputStream is = new BufferedInputStream(new FileInputStream(filename));
                    try {
                        ZipEntry entry = new ZipEntry(filename);
                        zos.putNextEntry(entry);
                        while (true) {
                            int len = is.read(buf, 0, 524288);
                            if (len <= 0) {
                                break;
                            }
                            zos.write(buf, 0, len);
                        }
                        zos.closeEntry();
                        $closeResource(null, is);
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            th = th3;
                            th2 = th4;
                            $closeResource(th, is);
                            throw th2;
                        }
                    }
                }
                $closeResource(null, zos);
                return true;
            } catch (Throwable th5) {
                $closeResource(null, zos);
                throw th5;
            }
        } catch (IOException e) {
            Log.e("DumpTruck", "error zipping up profile data", e);
            return false;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) throws Exception {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }
}
