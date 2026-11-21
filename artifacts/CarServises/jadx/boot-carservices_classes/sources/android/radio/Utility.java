package android.radio;

import android.os.SystemProperties;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class Utility {
    public static final int[][] REGION_DATA = {new int[]{200, 87500, 107900}, new int[]{10, 530, 1710}, new int[]{50, 87500, 108000}, new int[]{9, 522, 1620}, new int[]{100, 87500, 108000}, new int[]{10, 520, 1710}, new int[]{100, 65000, 74000}, new int[]{9, 531, 1620}, new int[]{100, 87500, 108000}, new int[]{9, 531, 1629}, new int[]{100, 76000, 90000}, new int[]{9, 522, 1629}, new int[]{100, 87500, 108000}, new int[]{9, 531, 1602}, new int[]{100, 76000, 108000}, new int[]{10, 520, 1710}, new int[]{100, 76000, 108000}, new int[]{9, 522, 1629}, new int[]{100, 76000, 95000}, new int[]{9, 522, 1629}};

    public static boolean isSupportRDS() {
        return SystemProperties.getBoolean("hw.sys.radio.rds", false);
    }

    public static final class RadioParameters {
        public final int AmMax;
        public final int AmMin;
        public final int AmStep;
        public final int FmMax;
        public final int FmMin;
        public final int FmStep;

        public RadioParameters() {
            int region = SystemProperties.getInt("hw.sys.radio.region", 4) * 2;
            this.FmStep = Utility.REGION_DATA[region][0];
            this.FmMin = Utility.REGION_DATA[region][1];
            this.FmMax = Utility.REGION_DATA[region][2];
            this.AmStep = Utility.REGION_DATA[region + 1][0];
            this.AmMin = Utility.REGION_DATA[region + 1][1];
            this.AmMax = Utility.REGION_DATA[region + 1][2];
        }
    }
}
