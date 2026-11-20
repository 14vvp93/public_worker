package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.SystemProperties;
import android.util.Log;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class PanProfile implements LocalBluetoothProfile {
    private static final String EXTRA_STATE = "extra_state";
    private static final String KEY_AUTO_CONNECT_NET = "persist.sys.AutoConnectBtNet";
    public static final String NAME = "PAN";
    private static final int ORDINAL = 4;
    private static final String PAN_STATE_CHANGED_ACTION = "com.hcn.bluetooth.pan.state.changed";
    private static final String TAG = "PanProfile";
    private static boolean V = true;
    private ConnectivityManager cm;
    private Context mContext;
    private boolean mIsProfileReady;
    private BluetoothPan mService;
    private final HashMap<BluetoothDevice, Integer> mDeviceRoleMap = new HashMap<>();
    private boolean mAutoConnectable = SystemProperties.getBoolean(KEY_AUTO_CONNECT_NET, false);

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 4;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        return -1;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 5;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isConnectable() {
        return true;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        return true;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
    }

    public String toString() {
        return NAME;
    }

    private final class PanServiceListener implements BluetoothProfile.ServiceListener {
        private PanServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (PanProfile.V) {
                Log.d(PanProfile.TAG, "Bluetooth service connected");
            }
            PanProfile.this.mService = (BluetoothPan) bluetoothProfile;
            PanProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (PanProfile.V) {
                Log.d(PanProfile.TAG, "Bluetooth service disconnected");
            }
            PanProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    PanProfile(Context context) {
        this.mContext = null;
        this.cm = null;
        this.mContext = context;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PanServiceListener(), 5);
        this.cm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return this.mAutoConnectable;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return false;
        }
        disconnect();
        return this.mService.connect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return false;
        }
        return bluetoothPan.disconnect(bluetoothDevice);
    }

    public boolean disconnect() {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return false;
        }
        List connectedDevices = bluetoothPan.getConnectedDevices();
        if (connectedDevices == null) {
            return true;
        }
        Iterator it = connectedDevices.iterator();
        while (it.hasNext()) {
            disconnect((BluetoothDevice) it.next());
        }
        return true;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return 0;
        }
        return bluetoothPan.getConnectionState(bluetoothDevice);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return new ArrayList(0);
        }
        return bluetoothPan.getConnectedDevices();
    }

    void setLocalRole(BluetoothDevice bluetoothDevice, int i) {
        this.mDeviceRoleMap.put(bluetoothDevice, Integer.valueOf(i));
    }

    boolean isLocalRoleNap(BluetoothDevice bluetoothDevice) {
        if (!this.mDeviceRoleMap.containsKey(bluetoothDevice)) {
            return false;
        }
        Log.d(TAG, "isLocalRoleMap is " + this.mDeviceRoleMap.get(bluetoothDevice));
        return this.mDeviceRoleMap.get(bluetoothDevice).intValue() == 1;
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(5, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up PAN proxy", th);
            }
        }
    }

    public void notifySystemUINetworkState(boolean z) {
        Intent intent = new Intent(PAN_STATE_CHANGED_ACTION);
        intent.putExtra(EXTRA_STATE, z);
        Context context = this.mContext;
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void saveAutoConnectState(boolean z) {
        if (this.mAutoConnectable != z) {
            this.mAutoConnectable = z;
            SystemProperties.set(KEY_AUTO_CONNECT_NET, String.valueOf(z));
        }
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) {
        BluetoothPan bluetoothPan = this.mService;
        return bluetoothPan == null ? new ArrayList(0) : bluetoothPan.getDevicesMatchingConnectionStates(iArr);
    }

    public boolean isBluetoothNetworkError() {
        if (this.mContext == null) {
            return false;
        }
        ConnectivityManager connectivityManager = this.cm;
        if (connectivityManager == null) {
            Log.d(TAG, "connectivityManager is null!");
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork != null) {
            NetworkCapabilities networkCapabilities = this.cm.getNetworkCapabilities(activeNetwork);
            if (networkCapabilities.hasTransport(2)) {
                boolean zIsValidateHostAddress = isValidateHostAddress();
                boolean zHasCapability = networkCapabilities.hasCapability(16);
                if (!zHasCapability || !zIsValidateHostAddress) {
                    Log.e(TAG, "bt_pan Address=" + zIsValidateHostAddress + " Capability=" + zHasCapability);
                    return true;
                }
            }
            return false;
        }
        Log.e(TAG, "bt_pan network null!!");
        return false;
    }

    private static boolean isValidateHostAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.getName().equals("bt-pan")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && ((inetAddressNextElement instanceof Inet4Address) || (inetAddressNextElement instanceof Inet6Address))) {
                            String hostAddress = inetAddressNextElement.getHostAddress();
                            if ((inetAddressNextElement instanceof Inet4Address) && hostAddress != null && !"".equals(hostAddress) && !hostAddress.equals("00.00.00.00")) {
                                return true;
                            }
                            if ((inetAddressNextElement instanceof Inet6Address) && hostAddress != null && !"".equals(hostAddress) && !hostAddress.toLowerCase().startsWith("fe80")) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SocketException unused) {
            Log.e(TAG, "SocketException");
        }
        Log.e(TAG, "Host Address error!");
        return false;
    }
}
