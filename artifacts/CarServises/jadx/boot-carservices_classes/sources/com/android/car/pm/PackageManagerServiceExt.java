package com.android.car.pm;

import android.content.Context;
import android.content.pm.PackageParser;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.sourceservice.SourceService;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
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

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class PackageManagerServiceExt {
    private static final String PM_NOT_ALLOW_INSTALL_FILE = "/apd/pm_not_allow_install_list.xml";
    private static final String PM_PERMITTED_FIEL = "/apd/pm_permitted_list.xml";
    private static final String PM_UNINSTALL_FIEL = "/apd/pm_uninstall_list.xml";
    private static final String TAG = "PackageManagerServiceExt";
    private final AsyncHandler mAsyncHandler;
    private Reference<Context> mContextRef;
    private static final boolean mDowngradeUpdateEnable = SystemProperties.getBoolean("ro.sys.downgrade.enable", false);
    private static final String ALLOW_RECEIVE_BOOT_BROADCAST_PACKAGE_NAME = SystemProperties.get("sys.allow.boot.packages", "");
    private static PackageManagerServiceExt sInstance = null;
    private Reference<Handler> mHandlerRef = null;
    private final ArrayList<String> mAllowDowngradeAppWhiteList = new ArrayList<>();
    private final ArrayList<String> mUninstallFileList = new ArrayList<>();
    private final ArrayList<String> mNotAllowInstallFileList = new ArrayList<>();
    private final OvarlayLanguageImpl mOlImpl = new OvarlayLanguageImpl();
    private final HandlerThread mHandlerThread = new HandlerThread("PMS-Ext");

    public static PackageManagerServiceExt instance() {
        if (sInstance == null) {
            synchronized (PackageManagerServiceExt.class) {
                if (sInstance == null) {
                    sInstance = new PackageManagerServiceExt();
                }
            }
        }
        return sInstance;
    }

    private PackageManagerServiceExt() {
        this.mHandlerThread.start();
        this.mAsyncHandler = new AsyncHandler(this.mHandlerThread.getLooper());
    }

    public void init(Context content, Handler handler) {
        this.mContextRef = new WeakReference(content);
        this.mHandlerRef = new WeakReference(handler);
        this.mOlImpl.init(content);
        readPermittedAppInfoWhiteList(new File(PM_PERMITTED_FIEL));
        readUninstallAppList(new File(PM_UNINSTALL_FIEL));
        readNotAllowInstallAppList(new File(PM_NOT_ALLOW_INSTALL_FILE));
    }

    public void release() {
    }

    public void systemReady() {
        this.mOlImpl.systemReady();
    }

    public void appDataPrepared() {
        this.mAsyncHandler.post(new Runnable() { // from class: com.android.car.pm.PackageManagerServiceExt.1
            @Override // java.lang.Runnable
            public void run() {
                PackageManagerServiceExt.this.mOlImpl.enableOverlayLanguage();
            }
        });
    }

    private void readPermittedAppInfoWhiteList(File configFile) {
        FileInputStream fis;
        String str;
        StringBuilder sb;
        try {
            try {
                fis = new FileInputStream(configFile);
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, null);
                    XmlUtils.beginDocument(parser, "app-permitted");
                    int outerDepth = parser.getDepth();
                    while (XmlUtils.nextElementWithin(parser, outerDepth)) {
                        String tagName = parser.getName();
                        if ("group".equals(tagName)) {
                            String group = parser.getAttributeValue(null, "id");
                            if ("downgrade".equals(group)) {
                                parsePermittedDowngradeGroup(parser);
                            } else {
                                continue;
                            }
                        }
                    }
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
                } catch (IOException ex) {
                    Log.e(TAG, "Error reading permitted config from " + configFile, ex);
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
                } catch (XmlPullParserException ex2) {
                    Log.e(TAG, "Error reading permitted config from " + configFile, ex2);
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
            } catch (FileNotFoundException e4) {
                Log.e(TAG, "FileNotFoundException: " + configFile);
            }
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (IOException ex3) {
                Log.e(TAG, "Error while closing " + configFile, ex3);
            }
            throw th;
        }
    }

    private void parsePermittedDowngradeGroup(XmlPullParser parser) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (XmlUtils.nextElementWithin(parser, outerDepth)) {
            String tagName = parser.getName();
            if (SourceService.EXTRA_TOP_ACTIVITY_PACKAGE.equals(tagName)) {
                parser.require(2, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
                String pkg = parser.nextText();
                if (!TextUtils.isEmpty(pkg)) {
                    this.mAllowDowngradeAppWhiteList.add(pkg);
                    if (mDowngradeUpdateEnable) {
                        Log.v(TAG, "app-permitted/downgrade-group: " + pkg);
                    }
                }
                parser.require(3, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
            }
        }
    }

    public boolean allowDowngradeUpdate(String pacakgeName) {
        if (!mDowngradeUpdateEnable || TextUtils.isEmpty(pacakgeName)) {
            return false;
        }
        return this.mAllowDowngradeAppWhiteList.contains(pacakgeName);
    }

    private class AsyncHandler extends Handler {
        public AsyncHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
        }
    }

    public static boolean isPreInstallApp(PackageParser.Package pkg) {
        String codePath = pkg.codePath;
        if (codePath == null || !codePath.contains("data/config/app")) {
            String filename = codePath.substring(codePath.lastIndexOf("/") + 1);
            File file = new File("/data/PreInstall");
            String[] filelist = file.list();
            if (filename != null && filelist != null && filelist.length > 0) {
                for (String str : filelist) {
                    if (str.contains(filename)) {
                        Log.i(TAG, "isPreInstall:" + pkg.codePath);
                        return true;
                    }
                }
            }
            return false;
        }
        Log.i(TAG, "isPreInstall:" + pkg.codePath);
        return true;
    }

    private void readUninstallAppList(File configFile) {
        FileInputStream fis;
        String str;
        StringBuilder sb;
        try {
            try {
                fis = new FileInputStream(configFile);
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, null);
                    XmlUtils.beginDocument(parser, "uninstall");
                    int outerDepth = parser.getDepth();
                    while (XmlUtils.nextElementWithin(parser, outerDepth)) {
                        String tagName = parser.getName();
                        if ("filename".equals(tagName)) {
                            String filename = parser.getAttributeValue(null, "name");
                            if (TextUtils.isEmpty(filename)) {
                                continue;
                            } else {
                                this.mUninstallFileList.add(filename);
                                Log.i(TAG, "mUninstallFileList add " + filename);
                            }
                        }
                    }
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
                } catch (IOException ex) {
                    Log.e(TAG, "Error reading permitted config from " + configFile, ex);
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
                } catch (XmlPullParserException ex2) {
                    Log.e(TAG, "Error reading permitted config from " + configFile, ex2);
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
            } catch (FileNotFoundException e4) {
                Log.e(TAG, "FileNotFoundException: " + configFile);
            }
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (IOException ex3) {
                Log.e(TAG, "Error while closing " + configFile, ex3);
            }
            throw th;
        }
    }

    public boolean allowInstall(String filename) {
        if (this.mUninstallFileList.size() <= 0 && TextUtils.isEmpty(filename)) {
            return true;
        }
        boolean find = this.mUninstallFileList.contains(filename);
        StringBuilder sb = new StringBuilder();
        sb.append("allowInstall filename: ");
        sb.append(filename);
        sb.append(", isAllowed: ");
        sb.append(!find);
        Log.i(TAG, sb.toString());
        return !find;
    }

    public boolean allowReceiveBootBroadcast(String packageName) {
        if (packageName == null || packageName.equals("")) {
            return false;
        }
        return ALLOW_RECEIVE_BOOT_BROADCAST_PACKAGE_NAME.contains(packageName);
    }

    private void readNotAllowInstallAppList(File configFile) {
        FileInputStream fis;
        String str;
        StringBuilder sb;
        try {
            try {
                fis = new FileInputStream(configFile);
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, null);
                    XmlUtils.beginDocument(parser, "not_allow_install");
                    int outerDepth = parser.getDepth();
                    while (XmlUtils.nextElementWithin(parser, outerDepth)) {
                        String tagName = parser.getName();
                        if ("package_name".equals(tagName)) {
                            String pacakgeName = parser.getAttributeValue(null, "name");
                            if (TextUtils.isEmpty(pacakgeName)) {
                                continue;
                            } else {
                                this.mNotAllowInstallFileList.add(pacakgeName);
                                Log.i(TAG, "mNotAllowInstallFileList add " + pacakgeName);
                            }
                        }
                    }
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
                } catch (IOException ex) {
                    Log.e(TAG, "Error reading pm_not_allow_install_list config from " + configFile, ex);
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
                } catch (XmlPullParserException ex2) {
                    Log.e(TAG, "Error reading pm_not_allow_install_list config from " + configFile, ex2);
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
            } catch (FileNotFoundException e4) {
                Log.e(TAG, "FileNotFoundException: " + configFile);
            }
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (IOException ex3) {
                Log.e(TAG, "Error while closing " + configFile, ex3);
            }
            throw th;
        }
    }

    public boolean notAllowInstall(String pacakgeName) {
        if (this.mNotAllowInstallFileList.size() <= 0 || TextUtils.isEmpty(pacakgeName)) {
            return false;
        }
        boolean find = this.mNotAllowInstallFileList.contains(pacakgeName);
        StringBuilder sb = new StringBuilder();
        sb.append("notAllowInstall pacakge (");
        sb.append(pacakgeName);
        sb.append(")");
        sb.append(find ? " is not allow install " : " is allow install");
        Log.i(TAG, sb.toString());
        return find;
    }
}
