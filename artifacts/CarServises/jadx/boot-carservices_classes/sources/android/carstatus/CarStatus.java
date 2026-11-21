package android.carstatus;

import android.carsource.McuConstant;
import android.graphics.Color;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.utilities.Util;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class CarStatus {
    public static final String ACTION_ACC = "android.mcu.device.action.ACC";
    public static final String ACTION_GSENSOR = "android.mcu.device.action.GSENSOR";
    public static final String ACTION_PARKING = "android.mcu.device.action.PARKING";
    public static final String ACTION_REVSTATUS = "android.mcu.device.action.REVSTATUS";
    public static final String ACTION_SILENCE_BUTTON = "android.mcu.device.action.SILENCE_BUTTON";
    public static final String ACTION_TELMUTE = "android.mcu.device.action.TELMUTE";
    public static final String EXTRA_ACC = "AccStatus";
    public static final String EXTRA_GSENSOR = "Counts";
    public static final String EXTRA_PARKING = "Parking";
    public static final String EXTRA_REVSTATUS = "REVSTATUS";
    public static final String EXTRA_TELMUTE = "state";
    private static final String TAG = "CarStatus";
    private ICarStatus mService = ICarStatus.Stub.asInterface(ServiceManager.getService("carstatus"));

    public int getParkingStatus() {
        try {
            int status = this.mService.getParkingStatus();
            return status;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return -1;
        }
    }

    public void setParkingStatus(int park) {
        try {
            this.mService.setParkingStatus(park);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public boolean getHeadLightStatus() {
        try {
            boolean status = this.mService.getHeadLightStatus();
            return status;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return false;
        }
    }

    public void setDashboardBrightness(int value) {
        try {
            this.mService.setDashboardBrightness(value);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public int getReversing() {
        try {
            int status = this.mService.getReversing();
            return status;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return -1;
        }
    }

    public void setReversing(int revsersing) {
        try {
            this.mService.setReversing(revsersing);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    public int getAccStatus() {
        try {
            int status = this.mService.getAccStatus();
            return status;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return -1;
        }
    }

    public int getTelMuteSatus() {
        try {
            int status = this.mService.getTelMuteSatus();
            return status;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return -1;
        }
    }

    public void setLed(int index, Color color) {
        int r = Math.min(McuConstant.K_SWCSETTING, (int) ((color.red() * 255.0f) + 0.5f));
        int g = Math.min(McuConstant.K_SWCSETTING, (int) ((color.green() * 255.0f) + 0.5f));
        int b = Math.min(McuConstant.K_SWCSETTING, (int) ((color.blue() * 255.0f) + 0.5f));
        int a = (int) ((color.alpha() * 255.0f) + 0.5f);
        try {
            this.mService.setLed(index, a < 2 ? -1 : r, g, b);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }
}
