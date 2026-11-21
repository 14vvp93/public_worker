package android.carsource;

import android.app.ActivityManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.utilities.Util;
import java.util.List;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class McuManager {
    public static final int CNACEL_KEY = 268435456;
    public static final int EVENT_MCU_IAP = 8;
    public static final int FACTORYTEST_KEY = 2;
    public static final int PANEL_KEY = 16;
    public static final int REMOTE_KEY = 32;
    public static final int SWC_KEY = 1;
    private static final String TAG = "McuManager";
    public static final int TOUCH_KEY = 4;
    public static final int VIDEOPATH_AUX = 0;
    public static final int VIDEOPATH_CMMB = 2;
    public static final int VIDEOPATH_FCAMERA = 3;
    public static final int VIDEOPATH_REV = 1;
    private OnEventListener mOnEventListener;
    private static McuManager sInstance;
    private IMcuManager mService = IMcuManager.Stub.asInterface(ServiceManager.getService("mcu_service"));
    private final IMcuListener mToken = new IMcuListener.Stub() { // from class: android.carsource.McuManager.1
        @Override // android.carsource.IMcuListener
        public void onEvent(int cmd, int event) {
            OnEventListener l = McuManager.this.mOnEventListener;
            if (l != null) {
                l.onEvent(cmd, event);
            }
        }

        @Override // android.carsource.IMcuListener
        public void onByteEvent(int cmd, byte[] event) {
            OnEventListener l = McuManager.this.mOnEventListener;
            if (l != null) {
                l.onEvent(cmd, event);
            }
        }
    };

    public interface OnEventListener {
        void onEvent(int i, int i2);

        void onEvent(int i, byte[] bArr);
    }

    public static McuManager getsInstance() {
        if (sInstance == null) {
            sInstance = new McuManager();
        }
        return sInstance;
    }

    private McuManager() {
    }

    public void setOnEventListener(OnEventListener listener, int event) {
        this.mOnEventListener = listener;
        try {
            if (listener != null) {
                this.mService.setcallback(this.mToken, event);
            } else {
                this.mService.removecallback(this.mToken, event);
            }
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void requestBeep(int type) {
        try {
            this.mService.requestBeep(type);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public float getBattery() {
        try {
            float ret = this.mService.getBattery();
            return ret;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return 0.0f;
        }
    }

    public void getKeySettings(int type, List<KeyMap> keys) {
        try {
            this.mService.getKeySettings(type, keys);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void setKeyMaps(KeyMap key) {
        try {
            this.mService.setKeyMaps(key);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void setTouchKeyMaps(KeyMap key) {
        try {
            this.mService.setTouchKeyMaps(key);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void setTouchIrqType(int type) {
        try {
            this.mService.setTouchIrqType(type);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void requestTouchdown(int x, int y) {
        try {
            this.mService.requestTouchdown(x, y);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void applyVideoPath(int path) {
        Log.i(TAG, "applyVideoPath to " + path);
        try {
            this.mService.applyVideoPath(path);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void injectKeyEvent(int keycode, int state) {
        if (state != 0 && state != 1) {
            new Exception("state must be KeyEvent.ACTION_DOWN or KeyEvent.ACTION_UP");
            return;
        }
        if (310 == keycode && ActivityManager.isUserAMonkey()) {
            return;
        }
        try {
            this.mService.injectKeyEvent(keycode, state);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void injectKeyEventTimeout(int keycode, int timeout) {
        if (310 == keycode && ActivityManager.isUserAMonkey()) {
            return;
        }
        try {
            this.mService.injectKeyEventTimeout(keycode, timeout);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void requestSendIR(byte[] data) {
        try {
            this.mService.requestSendIR(data);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public void requestMcuIAP(byte subcmd, byte[] data) {
        try {
            this.mService.requestMcuIAP(subcmd, data);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public int setLogo(String filename) {
        if (filename == null || filename.length() <= 5) {
            return -1;
        }
        try {
            int ret = this.mService.setLogo(filename);
            return ret;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return -1;
        }
    }
}
