package com.android.car.pm;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.sourceservice.SourceService;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import android.utilities.Util;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: OverlayLanguageImpl.java */
/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
final class OvarlayLanguageImpl {
    private static final String OVERLAY_PACKAGE_CONFIG_FILE = "/apd/overlay_package_config.xml";
    private static final String TAG = "OvarlayLanguageImpl";
    private Reference<Context> mContextRef;
    private IOverlayManager mOverlayManager;
    private final ArrayList<String> mAllowApplyOverlayList = new ArrayList<>();

    OvarlayLanguageImpl() {
    }

    public void init(Context context) {
        this.mContextRef = new WeakReference(context);
        readOverlayPackageInfoList(new File(OVERLAY_PACKAGE_CONFIG_FILE));
    }

    public void systemReady() {
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
    }

    public boolean isAppInstalled(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        IPackageManager pm = AppGlobals.getPackageManager();
        try {
            int userId = ActivityManager.getCurrentUser();
            Context context = this.mContextRef.get();
            if (context != null) {
                userId = context.getUserId();
            }
            ApplicationInfo info = pm.getApplicationInfo(pkgName, 0, userId);
            if (info != null) {
                return info.enabled;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Package NameNotFoundException: " + e);
        }
        return false;
    }

    /* JADX WARN: Undo finally extract visitor
    java.lang.NullPointerException: Cannot invoke "Object.hashCode()" because "this.second" is null
    	at jadx.core.utils.Pair.hashCode(Pair.java:35)
    	at java.base/java.util.HashMap.hash(HashMap.java:338)
    	at java.base/java.util.HashMap.getNode(HashMap.java:576)
    	at java.base/java.util.HashMap.containsKey(HashMap.java:602)
    	at jadx.core.dex.visitors.finaly.traverser.state.TraverserGlobalCommonState.hasBlocksBeenCached(TraverserGlobalCommonState.java:35)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.MergePathActivePathTraverserHandler.handle(MergePathActivePathTraverserHandler.java:174)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.AbstractActivePathTraverserHandler.process(AbstractActivePathTraverserHandler.java:19)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.processHandlerImplementations(TraverserController.java:43)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.advance(TraverserController.java:156)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.process(TraverserController.java:79)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.findCommonInsns(MarkFinallyVisitor.java:404)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.extractFinally(MarkFinallyVisitor.java:284)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.processTryBlock(MarkFinallyVisitor.java:202)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.visit(MarkFinallyVisitor.java:135)
     */
    private void readOverlayPackageInfoList(File configFile) {
        String str;
        StringBuilder sb;
        try {
            FileInputStream fis = new FileInputStream(configFile);
            try {
                try {
                    try {
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(fis, null);
                        XmlUtils.beginDocument(parser, "overlay-package");
                        this.mAllowApplyOverlayList.addAll(parseOverlayPackageInfo(parser));
                        try {
                            fis.close();
                        } catch (IOException e) {
                            ex = e;
                            str = TAG;
                            sb = new StringBuilder();
                            sb.append("Error while closing ");
                            sb.append(configFile);
                            Log.e(str, sb.toString(), ex);
                        }
                    } catch (XmlPullParserException ex) {
                        this.mAllowApplyOverlayList.clear();
                        Log.e(TAG, "Error reading overlay config from " + configFile, ex);
                        try {
                            fis.close();
                        } catch (IOException e2) {
                            ex = e2;
                            str = TAG;
                            sb = new StringBuilder();
                            sb.append("Error while closing ");
                            sb.append(configFile);
                            Log.e(str, sb.toString(), ex);
                        }
                    }
                } catch (IOException ex2) {
                    this.mAllowApplyOverlayList.clear();
                    Log.e(TAG, "Error reading overlay config from " + configFile, ex2);
                    try {
                        fis.close();
                    } catch (IOException e3) {
                        ex = e3;
                        str = TAG;
                        sb = new StringBuilder();
                        sb.append("Error while closing ");
                        sb.append(configFile);
                        Log.e(str, sb.toString(), ex);
                    }
                }
            } catch (Throwable th) {
                try {
                    fis.close();
                } catch (IOException ex3) {
                    Log.e(TAG, "Error while closing " + configFile, ex3);
                }
                throw th;
            }
        } catch (FileNotFoundException e4) {
            Log.e(TAG, "FileNotFoundException: " + configFile);
        }
    }

    private ArrayList<String> parseOverlayPackageInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        ArrayList<String> overlayList = new ArrayList<>();
        while (XmlUtils.nextElementWithin(parser, outerDepth)) {
            String tagName = parser.getName();
            if (SourceService.EXTRA_TOP_ACTIVITY_PACKAGE.equals(tagName)) {
                parser.require(2, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
                String pkg = parser.nextText();
                if (!TextUtils.isEmpty(pkg)) {
                    overlayList.add(pkg);
                    if (Util.isDebugVersion()) {
                        Log.v(TAG, "overlay-package: " + pkg);
                    }
                }
                parser.require(3, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
            }
        }
        return overlayList;
    }

    public void enableOverlayLanguage() {
        if (this.mOverlayManager == null) {
            this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        }
        Slog.d(TAG, "+ enable autoapps overlay language.");
        long start = SystemClock.elapsedRealtime();
        try {
            int userId = ActivityManager.getCurrentUser();
            for (String pacakgeName : this.mAllowApplyOverlayList) {
                if (isAppInstalled(pacakgeName)) {
                    this.mOverlayManager.setEnabled(pacakgeName, true, userId);
                }
            }
        } catch (RemoteException e) {
            Slog.e(TAG, "Can't enable overlay language", e);
        }
        long current = SystemClock.elapsedRealtime();
        long totalSeconds = (current - start) / 1000;
        Slog.d(TAG, "- enable autoapps overlay language, takes " + totalSeconds + " second.");
    }
}
