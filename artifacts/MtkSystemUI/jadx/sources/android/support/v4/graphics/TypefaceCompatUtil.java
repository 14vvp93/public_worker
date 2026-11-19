package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes.dex */
public class TypefaceCompatUtil {
    public static File getTempFile(Context context) {
        String prefix = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002f A[Catch: IOException -> 0x0033, TryCatch #3 {IOException -> 0x0033, blocks: (B:3:0x0001, B:5:0x0017, B:14:0x0026, B:19:0x0032, B:18:0x002f, B:17:0x002b), top: B:26:0x0001, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0026 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static ByteBuffer mmap(File file) throws Throwable {
        Throwable th;
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                FileChannel channel = fis.getChannel();
                long size = channel.size();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                fis.close();
                return map;
            } catch (Throwable th2) {
                th = th2;
                th = null;
                if (th == null) {
                    fis.close();
                    throw th;
                }
                fis.close();
                throw th;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x004b A[Catch: all -> 0x004f, Throwable -> 0x0052, TryCatch #5 {Throwable -> 0x0052, blocks: (B:8:0x0014, B:10:0x002e, B:26:0x004e, B:25:0x004b, B:24:0x0047), top: B:48:0x0014 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x005a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x0065 A[Catch: IOException -> 0x0069, TryCatch #0 {IOException -> 0x0069, blocks: (B:3:0x0005, B:6:0x0010, B:12:0x0033, B:35:0x005c, B:39:0x0065, B:38:0x0061, B:40:0x0068), top: B:43:0x0005, inners: #3 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:0x0042 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:? A[Catch: IOException -> 0x0069, SYNTHETIC, TRY_LEAVE, TryCatch #0 {IOException -> 0x0069, blocks: (B:3:0x0005, B:6:0x0010, B:12:0x0033, B:35:0x005c, B:39:0x0065, B:38:0x0061, B:40:0x0068), top: B:43:0x0005, inners: #3 }] */
    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) throws Throwable {
        Throwable th;
        Throwable th2;
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r", cancellationSignal);
            if (pfd == null) {
                if (pfd != null) {
                    pfd.close();
                }
                return null;
            }
            try {
                try {
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    try {
                        FileChannel channel = fis.getChannel();
                        long size = channel.size();
                        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                        fis.close();
                        if (pfd != null) {
                            pfd.close();
                        }
                        return map;
                    } catch (Throwable th3) {
                        th = th3;
                        th2 = null;
                        if (th2 == null) {
                            fis.close();
                            throw th;
                        }
                        fis.close();
                        throw th;
                    }
                } catch (Throwable th4) {
                    try {
                        throw th4;
                    } catch (Throwable th5) {
                        th = th4;
                        th = th5;
                        if (pfd != null) {
                            throw th;
                        }
                        if (th == null) {
                            pfd.close();
                            throw th;
                        }
                        try {
                            pfd.close();
                            throw th;
                        } catch (Throwable th6) {
                            th.addSuppressed(th6);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th7) {
                th = th7;
                th = null;
                if (pfd != null) {
                    throw th;
                }
                if (th == null) {
                    pfd.close();
                    throw th;
                }
                pfd.close();
                throw th;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBufferMmap = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBufferMmap = mmap(tmpFile);
            }
            return byteBufferMmap;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        boolean z = false;
        try {
            os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int readLen = is.read(buffer);
                if (readLen == -1) {
                    break;
                }
                os.write(buffer, 0, readLen);
            }
            z = true;
        } catch (IOException e) {
            Log.e("TypefaceCompatUtil", "Error copying resource contents to temp file: " + e.getMessage());
        } finally {
            closeQuietly(os);
        }
        return z;
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
