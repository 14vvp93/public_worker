package com.hcn.auto.systemui.netspeed;

import android.content.Context;
import android.provider.Settings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes.dex */
public class NetSpeed {
    private static final String TAG = NetSpeed.class.getSimpleName();
    private static long lastTotalRxBytes = 0;
    private static long lastTimeStamp = 0;

    public static boolean showNetSpeed(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "real_time_network", 0) == 1;
    }

    public static void reset() {
        lastTotalRxBytes = 0L;
        lastTimeStamp = 0L;
    }

    public static String getNetSpeed(int uid) throws IOException {
        String mSpeed;
        long nowTotalRxBytes = 0;
        long nowTimeStamp = System.currentTimeMillis();
        long difTime = nowTimeStamp - lastTimeStamp;
        if (difTime <= 0) {
            mSpeed = "0B/s";
        } else {
            FileReader stream = null;
            BufferedReader in = null;
            try {
                try {
                    try {
                        stream = new FileReader("/proc/net/xt_qtaguid/stats");
                        in = new BufferedReader(stream, 500);
                        while (true) {
                            String line = in.readLine();
                            if (line != null) {
                                String[] dataStrings = line.split(" ");
                                String iface = dataStrings[1].trim();
                                if (iface.startsWith("wlan") || iface.startsWith("bt-pan") || iface.startsWith("usb") || iface.startsWith("ccmni") || iface.startsWith("iph") || iface.startsWith("and0")) {
                                    String rx_bytes = dataStrings[5].trim();
                                    String rx_tcp_bytes = dataStrings[9].trim();
                                    nowTotalRxBytes += Long.parseLong(rx_bytes) + Long.parseLong(rx_tcp_bytes);
                                }
                            } else {
                                try {
                                    break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        in.close();
                        stream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        try {
                            in.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        stream.close();
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                long speedTotal = ((nowTotalRxBytes - lastTotalRxBytes) * 1000) / difTime;
                mSpeed = getNetFileSizeDescription(speedTotal);
            } catch (Throwable e5) {
                try {
                    in.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                try {
                    stream.close();
                    throw e5;
                } catch (IOException e7) {
                    e7.printStackTrace();
                    throw e5;
                }
            }
        }
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return mSpeed;
    }

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###");
        if (size >= 1000000000) {
            double i = size / 1.0E9d;
            bytes.append(format.format(i));
            bytes.append("GB/s");
        } else if (size >= 1000000) {
            double i2 = size / 1000000.0d;
            bytes.append(format.format(i2));
            bytes.append("MB/s");
        } else if (size >= 1000) {
            double i3 = size / 1000.0d;
            bytes.append(format.format(i3));
            bytes.append("KB/s");
        } else if (size < 1000.0d) {
            if (size <= 0) {
                bytes.append("0B/s");
            } else {
                bytes.append((int) size);
                bytes.append("B/s");
            }
        }
        return bytes.toString();
    }
}
