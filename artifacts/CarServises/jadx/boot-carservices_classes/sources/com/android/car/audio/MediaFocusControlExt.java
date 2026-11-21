package com.android.car.audio;

import android.Configures.HConfig;
import android.content.Context;
import android.media.AudioFocusInfo;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.sourceservice.SourceService;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;
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

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class MediaFocusControlExt {
    private static final String AUDIO_FOCUS_CONTROL_FIEL = "/apd/audio_focus_control.xml";
    private static final String HOOK_REQUEST_FOCUS_NAVI_APP_LIST = "com.nng.igo,";
    private static final String TAG = "MediaFocusControlExt";
    private final Reference<Context> mContextRef;
    private static final boolean DEBUG = Util.isDebugVersion();
    private final ArrayList<String> mAllowMixingNaviAppList = new ArrayList<>();

    public MediaFocusControlExt(Context context) {
        this.mContextRef = new WeakReference(context);
        String[] packageNames = HOOK_REQUEST_FOCUS_NAVI_APP_LIST.split(",");
        for (String pkgName : packageNames) {
            this.mAllowMixingNaviAppList.add(pkgName);
        }
        readAudioFocusControlInfo(new File(AUDIO_FOCUS_CONTROL_FIEL));
    }

    private void readAudioFocusControlInfo(File configFile) {
        FileInputStream fis;
        String str;
        StringBuilder sb;
        try {
            try {
                fis = new FileInputStream(configFile);
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, null);
                    XmlUtils.beginDocument(parser, "audio-focus");
                    int outerDepth = parser.getDepth();
                    while (XmlUtils.nextElementWithin(parser, outerDepth)) {
                        String tagName = parser.getName();
                        if ("group".equals(tagName)) {
                            String group = parser.getAttributeValue(null, "id");
                            if ("force-mixing-navi".equals(group)) {
                                parseForceMixingNaviGroup(parser);
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
                    Log.e(TAG, "Error reading audio focus config from " + configFile, ex);
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
                    Log.e(TAG, "Error reading audio focus config from " + configFile, ex2);
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

    private void parseForceMixingNaviGroup(XmlPullParser parser) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (XmlUtils.nextElementWithin(parser, outerDepth)) {
            String tagName = parser.getName();
            if (SourceService.EXTRA_TOP_ACTIVITY_PACKAGE.equals(tagName)) {
                parser.require(2, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
                String pkg = parser.nextText();
                if (!TextUtils.isEmpty(pkg) && !this.mAllowMixingNaviAppList.contains(pkg)) {
                    this.mAllowMixingNaviAppList.add(pkg);
                    if (DEBUG) {
                        Log.v(TAG, "audio-focus/force-mixing-navi: " + pkg);
                    }
                }
                parser.require(3, null, SourceService.EXTRA_TOP_ACTIVITY_PACKAGE);
            }
        }
    }

    public int requestAudioFocus_Hook(String callingPackageName, int focusChangeHint) {
        if (DEBUG) {
            Log.v(TAG, "requestAudioFocus_Hook, " + callingPackageName + " / " + focusChangeHint);
        }
        if (!TextUtils.isEmpty(callingPackageName)) {
            boolean hitPackage = false;
            for (String pkgName : this.mAllowMixingNaviAppList) {
                if (callingPackageName.contains(pkgName)) {
                    hitPackage = true;
                    break;
                }
            }
            if (hitPackage && focusChangeHint == 2) {
                return 3;
            }
        }
        return focusChangeHint;
    }

    public void recordRequestAudioFocus(String callingPackageName, int focusChangeHint) {
        if (focusChangeHint != 1) {
            return;
        }
        String[] t = callingPackageName.split("\\.");
        String app = callingPackageName;
        if (t.length > 4) {
            int pos = callingPackageName.lastIndexOf(".");
            app = callingPackageName.substring(0, pos);
        }
        File f = new File("/data/readonly/media_packages.txt");
        String v = "";
        try {
            v = FileUtils.readTextFile(f, 0, null);
        } catch (IOException e) {
        }
        try {
            if (!v.contains(app)) {
                FileUtils.stringToFile(f, app + "," + v);
            }
            Os.chmod("/data/readonly/media_packages.txt", 420);
            SELinux.restorecon("/data/readonly/media_packages.txt");
        } catch (Exception e2) {
        }
    }

    public void notifySourceInfoAudioFocusState(AudioFocusInfo afi, int requestResult, int grant) {
        try {
            int type = afi.getAttributes().getContentType();
            IBinder binder = ServiceManager.getService("sourceinfo");
            if (binder != null) {
                Parcel _data = Parcel.obtain();
                try {
                    try {
                        _data.writeInterfaceToken("android.sourceservice.ISourceInfo");
                        _data.writeString(afi.getPackageName());
                        _data.writeInt(requestResult);
                        _data.writeInt(type);
                        _data.writeInt(afi.getGainRequest());
                        _data.writeInt(grant);
                        binder.transact(601, _data, null, 1);
                        Log.i(TAG, "notifySourceInfoAudioFocusState  " + afi.getPackageName() + " type = " + type + " requestResult = " + requestResult + " GainRequest()" + afi.getGainRequest());
                    } finally {
                        _data.recycle();
                    }
                } catch (RemoteException e) {
                    _data.recycle();
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "notifySourceInfoAudioFocusState" + e2);
        }
    }

    public boolean canReassignAudioFocus(String callingPackageName) {
        int callstate = SystemProperties.getInt("sys.nonsystem.callstate", 0);
        String packageName = SystemProperties.get(HConfig.exclusive_audio_focus);
        if (callstate > 0 && !callingPackageName.equals(SourceService.ACTIVE_ZJ_CARPLAY)) {
            Log.i(TAG, "canReassignAudioFocus callstate:" + callstate + " return false");
            return false;
        }
        if (packageName != null && !packageName.isEmpty() && !callingPackageName.equals(packageName)) {
            Log.i(TAG, "canReassignAudioFocus packageName:" + packageName + " return false");
            return false;
        }
        return true;
    }
}
