package android.carserver;

import android.Configures.HConfig;
import android.carsource.McuService;
import android.carstatus.CarStatusService;
import android.content.Context;
import android.os.ServiceManager;
import android.radio.RadioService;
import android.sourceservice.SourceService;
import android.util.Slog;
import com.android.car.CarServiceManager;
import com.android.car.settings.CarSettingsService;
import com.android.server.LocalServices;
import com.mediatek.galleryfeature.pq.filter.Filter;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class carservices {
    private static final String TAG = "carservices";
    private static CarStatusService sCarStatusService;
    private static HConfig sConfigs;
    private static McuService sMcuService;
    private static SourceService srcService;

    public static void startup(Context context) {
        Filter.loadPQSettings();
        sConfigs = HConfig.instance(context);
        try {
            Slog.i(TAG, "MCU Service");
            sMcuService = new McuService(context);
            ServiceManager.addService("mcu_service", sMcuService);
        } catch (Throwable e) {
            Slog.e(TAG, "Failure starting MCU Service", e);
        }
        try {
            Slog.i(TAG, "Source Service");
            srcService = new SourceService(context);
            ServiceManager.addService("sourceinfo", srcService);
        } catch (Throwable e2) {
            Slog.e(TAG, "Failure starting Source Service", e2);
        }
        try {
            Slog.i(TAG, "CarStatusService Service");
            sCarStatusService = new CarStatusService(context);
            LocalServices.addService(CarStatusService.class, sCarStatusService);
            ServiceManager.addService("carstatus", sCarStatusService);
        } catch (Throwable e3) {
            Slog.e(TAG, "Failure starting CarStatusService", e3);
        }
        try {
            Slog.i(TAG, "Analog fmradio Service");
            ServiceManager.addService("fmradio", new RadioService(context));
        } catch (Throwable e4) {
            Slog.e(TAG, "Failure starting fmradio Service", e4);
        }
        CarServiceManager carServiceManager = new CarServiceManager(context);
        LocalServices.addService(CarServiceManager.class, carServiceManager);
        carServiceManager.startService(CarSettingsService.Lifecycle.class);
    }

    public static void startBootPhase(int phase) {
        CarServiceManager carServiceManager = (CarServiceManager) LocalServices.getService(CarServiceManager.class);
        if (carServiceManager != null) {
            carServiceManager.startBootPhase(phase);
        }
    }

    public static void systemReady() {
        sMcuService.systemReady();
        sCarStatusService.systemReady();
        sConfigs.systemReady();
    }
}
