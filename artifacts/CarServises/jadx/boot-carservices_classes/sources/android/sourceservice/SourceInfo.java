package android.sourceservice;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class SourceInfo {
    private static final int EVENT_MUSIC = 1;
    private static final int EVENT_SOURCE = 2;
    private OnEventListener mOnEventListener;
    private static SourceInfo sSourceInfo;
    private final ISourceService mService = ISourceService.Stub.asInterface(ServiceManager.getService("sourceinfo"));
    private final ISourceCallback mToken = new ISourceCallback.Stub() { // from class: android.sourceservice.SourceInfo.1
        @Override // android.sourceservice.ISourceCallback
        public void onMusicEvent(MusicInfo info) {
            SourceInfo.this.mHandler.obtainMessage(1, info).sendToTarget();
        }

        @Override // android.sourceservice.ISourceCallback
        public void onSourceChanged(String pkg) {
            SourceInfo.this.mHandler.obtainMessage(2, pkg).sendToTarget();
        }
    };
    private Handler mHandler = new Handler() { // from class: android.sourceservice.SourceInfo.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            OnEventListener l = SourceInfo.this.mOnEventListener;
            if (l != null) {
                switch (msg.what) {
                    case 1:
                        l.onMusicEvent((MusicInfo) msg.obj);
                        break;
                    case 2:
                        l.onSourceChanged((String) msg.obj);
                        break;
                }
            }
        }
    };

    public interface OnEventListener {
        void onMusicEvent(MusicInfo musicInfo);

        void onSourceChanged(String str);
    }

    private SourceInfo() {
        if (this.mService == null) {
            throw new RuntimeException("can not find sourceinfo serivce");
        }
    }

    public static SourceInfo getInstance() {
        if (sSourceInfo == null) {
            sSourceInfo = new SourceInfo();
        }
        return sSourceInfo;
    }

    public void onRequestPlayAudio(Context context) {
        try {
            String pkg = context.getPackageName();
            Log.i("SourceService", String.format("onRequestPlayAudio from [%s]", pkg));
            this.mService.onRequestPlayAudio(pkg + "/background");
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void onRequestPlayAudio(String pkg) {
        try {
            Log.i("SourceService", String.format("onRequestPlayAudio(pkg) from [%s]", pkg));
            this.mService.onRequestPlayAudio(pkg + "/background");
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void onRequestPlayAudio(ComponentName component) {
        try {
            String cls = component.flattenToShortString();
            Log.i("SourceService", String.format("onRequestPlayAudio from [%s]", cls));
            this.mService.onRequestPlayAudio(cls);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void onRequestAuxAudio(String packageName, boolean active, boolean exclusiveMode) {
        try {
            Log.i("SourceService", "onRequestAuxAudio packageName:" + packageName + " ,active:" + active + " ,exclusiveMode:" + exclusiveMode);
            this.mService.onRequestAuxAudio(packageName, active, exclusiveMode);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setOnEventListener(OnEventListener listener) {
        this.mOnEventListener = listener;
        try {
            if (listener != null) {
                this.mService.setcallback(this.mToken);
            } else {
                this.mService.removecallback(this.mToken);
            }
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public String getSourcePackage() {
        return SystemProperties.get("sys.sourcing.package");
    }

    public MusicInfo getMusicInfo() {
        try {
            MusicInfo info = this.mService.getMusicInfo();
            return info;
        } catch (RemoteException | NullPointerException e) {
            return null;
        }
    }

    protected void finalize() {
        try {
            this.mService.removecallback(this.mToken);
        } catch (RemoteException | NullPointerException e) {
        }
        try {
            super.finalize();
        } catch (Throwable th) {
        }
    }

    public void setInputPathMute(int path, boolean mute) {
        try {
            this.mService.setInputPathMute(path, mute);
        } catch (RemoteException e) {
        }
    }
}
