package com.mediatek.galleryfeature.pq.filter;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class Filter {
    private static final String TAG = "MtkPQFilter";
    private static final File CFG_File1 = new File("/apd/pqconfig.cfg");

    public static native int nativeGetBrightnessAdjIndex();

    public static native int nativeGetBrightnessAdjRange();

    public static native int nativeGetContrastAdjIndex();

    public static native int nativeGetContrastAdjRange();

    public static native int nativeGetGrassToneHIndex();

    public static native int nativeGetGrassToneHRange();

    public static native int nativeGetGrassToneSIndex();

    public static native int nativeGetGrassToneSRange();

    public static native int nativeGetHueAdjIndex();

    public static native int nativeGetHueAdjRange();

    public static native int nativeGetSatAdjIndex();

    public static native int nativeGetSatAdjRange();

    public static native int nativeGetSharpAdjIndex();

    public static native int nativeGetSharpAdjRange();

    public static native int nativeGetSkinToneHIndex();

    public static native int nativeGetSkinToneHRange();

    public static native int nativeGetSkinToneSIndex();

    public static native int nativeGetSkinToneSRange();

    public static native int nativeGetSkyToneHIndex();

    public static native int nativeGetSkyToneHRange();

    public static native int nativeGetSkyToneSIndex();

    public static native int nativeGetSkyToneSRange();

    public static native int nativeGetXAxisIndex();

    public static native int nativeGetXAxisRange();

    public static native int nativeGetYAxisIndex();

    public static native int nativeGetYAxisRange();

    public static native boolean nativeSetBrightnessAdjIndex(int i);

    public static native boolean nativeSetContrastAdjIndex(int i);

    public static native boolean nativeSetGrassToneHIndex(int i);

    public static native boolean nativeSetGrassToneSIndex(int i);

    public static native boolean nativeSetHueAdjIndex(int i);

    public static native boolean nativeSetSatAdjIndex(int i);

    public static native boolean nativeSetSharpAdjIndex(int i);

    public static native boolean nativeSetSkinToneHIndex(int i);

    public static native boolean nativeSetSkinToneSIndex(int i);

    public static native boolean nativeSetSkyToneHIndex(int i);

    public static native boolean nativeSetSkyToneSIndex(int i);

    public static native boolean nativeSetXAxisIndex(int i);

    public static native boolean nativeSetYAxisIndex(int i);

    static {
        System.loadLibrary("PQjni");
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00ce  */
    public static void loadPQSettings() {
        File file = CFG_File1;
        if (!file.exists()) {
            Log.i(TAG, "load pq default settings");
            defaultSettings();
            return;
        }
        Log.i(TAG, "load pq settings from " + file.getAbsolutePath());
        try {
            FileReader input = new FileReader(file.getAbsolutePath());
            BufferedReader in = new BufferedReader(input);
            while (true) {
                String line = in.readLine();
                if (line != null) {
                    String line2 = line.trim();
                    if (!line2.startsWith("#")) {
                        String[] kv = line2.split("=");
                        if (kv.length == 2) {
                            String key = kv[0].trim();
                            int value = Integer.parseInt(kv[1].trim());
                            switch (key) {
                                case "saturation":
                                    nativeSetSatAdjIndex(value);
                                    break;
                                case "contrast":
                                    nativeSetContrastAdjIndex(value);
                                    break;
                                case "brightness":
                                    nativeSetBrightnessAdjIndex(value);
                                    break;
                                case "skinhue":
                                    nativeSetSkinToneHIndex(value);
                                    break;
                                case "skinsat":
                                    nativeSetSkinToneSIndex(value);
                                    break;
                                case "skyhue":
                                    nativeSetSkyToneHIndex(value);
                                    break;
                                case "skysat":
                                    nativeSetSkyToneSIndex(value);
                                    break;
                                case "grasshue":
                                    nativeSetGrassToneHIndex(value);
                                    break;
                                case "grasssat":
                                    nativeSetGrassToneSIndex(value);
                                    break;
                            }
                        }
                    }
                } else {
                    in.close();
                    input.close();
                    return;
                }
            }
        } catch (IOException e) {
            Log.w(TAG, "load pq settings faled. " + e.toString());
        }
    }

    public static void defaultSettings() {
        nativeSetSatAdjIndex(8);
        nativeSetContrastAdjIndex(6);
        nativeSetBrightnessAdjIndex(2);
        nativeSetSkinToneHIndex(4);
        nativeSetSkinToneSIndex(16);
        nativeSetSkyToneHIndex(9);
        nativeSetSkyToneSIndex(18);
        nativeSetGrassToneHIndex(2);
        nativeSetGrassToneSIndex(18);
    }
}
