package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.FontsContractCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (!OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return null;
            }
            return new File(path);
        } catch (ErrnoException e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x005b A[Catch: all -> 0x005f, Throwable -> 0x0062, TryCatch #5 {Throwable -> 0x0062, blocks: (B:7:0x001a, B:9:0x0020, B:12:0x0027, B:16:0x0031, B:18:0x003e, B:34:0x005e, B:33:0x005b, B:32:0x0057), top: B:56:0x001a }] */
    /* JADX WARN: Code duplicated, block: B:42:0x006a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x0075 A[Catch: IOException -> 0x0079, TryCatch #0 {IOException -> 0x0079, blocks: (B:6:0x000f, B:14:0x002d, B:20:0x0043, B:43:0x006c, B:47:0x0075, B:46:0x0071, B:48:0x0078), top: B:51:0x000f, inners: #3 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:59:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:? A[Catch: IOException -> 0x0079, SYNTHETIC, TRY_LEAVE, TryCatch #0 {IOException -> 0x0079, blocks: (B:6:0x000f, B:14:0x002d, B:20:0x0043, B:43:0x006c, B:47:0x0075, B:46:0x0071, B:48:0x0078), top: B:51:0x000f, inners: #3 }] */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) throws Throwable {
        Throwable th;
        Throwable th2;
        if (fonts.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo bestFont = findBestInfo(fonts, style);
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            try {
                try {
                    File file = getFile(pfd);
                    if (file != null && file.canRead()) {
                        Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                        if (pfd != null) {
                            pfd.close();
                        }
                        return typefaceCreateFromFile;
                    }
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    try {
                        Typeface typefaceCreateFromInputStream = super.createFromInputStream(context, fis);
                        fis.close();
                        if (pfd != null) {
                            pfd.close();
                        }
                        return typefaceCreateFromInputStream;
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
}
