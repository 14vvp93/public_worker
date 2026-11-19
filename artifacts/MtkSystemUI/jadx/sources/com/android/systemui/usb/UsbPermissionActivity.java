package com.android.systemui.usb;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.hardware.usb.AccessoryFilter;
import android.hardware.usb.DeviceFilter;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.util.XmlUtils;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class UsbPermissionActivity extends AlertActivity implements DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private UsbAccessory mAccessory;
    private CheckBox mAlwaysUse;
    private TextView mClearDefaultHint;
    private UsbDevice mDevice;
    private UsbDisconnectedReceiver mDisconnectedReceiver;
    private String mPackageName;
    private PendingIntent mPendingIntent;
    private boolean mPermissionGranted;
    private int mUid;

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        this.mDevice = (UsbDevice) intent.getParcelableExtra("device");
        this.mAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
        this.mPendingIntent = (PendingIntent) intent.getParcelableExtra("android.intent.extra.INTENT");
        this.mUid = intent.getIntExtra("android.intent.extra.UID", -1);
        this.mPackageName = intent.getStringExtra("package");
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo aInfo = packageManager.getApplicationInfo(this.mPackageName, 0);
            String appName = aInfo.loadLabel(packageManager).toString();
            AlertController.AlertParams ap = this.mAlertParams;
            ap.mTitle = appName;
            if (this.mDevice == null) {
                ap.mMessage = getString(R.string.usb_accessory_permission_prompt, new Object[]{appName, this.mAccessory.getDescription()});
                this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mAccessory);
            } else {
                ap.mMessage = getString(R.string.usb_device_permission_prompt, new Object[]{appName, this.mDevice.getProductName()});
                this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mDevice);
            }
            ap.mPositiveButtonText = getString(android.R.string.ok);
            ap.mNegativeButtonText = getString(android.R.string.cancel);
            ap.mPositiveButtonListener = this;
            ap.mNegativeButtonListener = this;
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(this.mPackageName, 129);
                if ((this.mDevice != null && canBeDefault(this.mDevice, packageInfo)) || (this.mAccessory != null && canBeDefault(this.mAccessory, packageInfo))) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
                    ap.mView = inflater.inflate(android.R.layout.alert_dialog_leanback, (ViewGroup) null);
                    this.mAlwaysUse = (CheckBox) ap.mView.findViewById(android.R.id.aboveThumb);
                    if (this.mDevice == null) {
                        this.mAlwaysUse.setText(getString(R.string.always_use_accessory, new Object[]{appName, this.mAccessory.getDescription()}));
                    } else {
                        this.mAlwaysUse.setText(getString(R.string.always_use_device, new Object[]{appName, this.mDevice.getProductName()}));
                    }
                    this.mAlwaysUse.setOnCheckedChangeListener(this);
                    this.mClearDefaultHint = (TextView) ap.mView.findViewById(android.R.id.aerr_report);
                    this.mClearDefaultHint.setVisibility(8);
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
            if (aInfo.packageName.equals("it.eds.dashboard")) {
                this.mPermissionGranted = true;
                finish();
            } else {
                setupAlert();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("UsbPermissionActivity", "unable to look up package name", e2);
            finish();
        }
    }

    private boolean canBeDefault(UsbDevice device, PackageInfo packageInfo) {
        ActivityInfo[] activities = packageInfo.activities;
        if (activities != null) {
            for (ActivityInfo activityInfo : activities) {
                try {
                    XmlResourceParser parser = activityInfo.loadXmlMetaData(getPackageManager(), "android.hardware.usb.action.USB_DEVICE_ATTACHED");
                    Throwable th = null;
                    if (parser != null) {
                        try {
                            try {
                                XmlUtils.nextElement(parser);
                                while (parser.getEventType() != 1) {
                                    if ("usb-device".equals(parser.getName())) {
                                        DeviceFilter filter = DeviceFilter.read(parser);
                                        if (filter.matches(device)) {
                                            if (parser != null) {
                                                $closeResource(null, parser);
                                            }
                                            return true;
                                        }
                                    }
                                    XmlUtils.nextElement(parser);
                                }
                                if (parser != null) {
                                    $closeResource(null, parser);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        } catch (Throwable th3) {
                            if (parser != null) {
                                $closeResource(th, parser);
                            }
                            throw th3;
                        }
                    } else if (parser != null) {
                        $closeResource(null, parser);
                    }
                } catch (Exception e) {
                    Log.w("UsbPermissionActivity", "Unable to load component info " + activityInfo.toString(), e);
                }
            }
        }
        return false;
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) throws Exception {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    private boolean canBeDefault(UsbAccessory accessory, PackageInfo packageInfo) {
        ActivityInfo[] activities = packageInfo.activities;
        if (activities != null) {
            for (ActivityInfo activityInfo : activities) {
                try {
                    XmlResourceParser parser = activityInfo.loadXmlMetaData(getPackageManager(), "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
                    Throwable th = null;
                    if (parser != null) {
                        try {
                            try {
                                XmlUtils.nextElement(parser);
                                while (parser.getEventType() != 1) {
                                    if ("usb-accessory".equals(parser.getName())) {
                                        AccessoryFilter filter = AccessoryFilter.read(parser);
                                        if (filter.matches(accessory)) {
                                            if (parser != null) {
                                                $closeResource(null, parser);
                                            }
                                            return true;
                                        }
                                    }
                                    XmlUtils.nextElement(parser);
                                }
                                if (parser != null) {
                                    $closeResource(null, parser);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        } catch (Throwable th3) {
                            if (parser != null) {
                                $closeResource(th, parser);
                            }
                            throw th3;
                        }
                    } else if (parser != null) {
                        $closeResource(null, parser);
                    }
                } catch (Exception e) {
                    Log.w("UsbPermissionActivity", "Unable to load component info " + activityInfo.toString(), e);
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onDestroy() {
        IBinder b = ServiceManager.getService("usb");
        IUsbManager service = IUsbManager.Stub.asInterface(b);
        Intent intent = new Intent();
        try {
            if (this.mDevice != null) {
                intent.putExtra("device", this.mDevice);
                if (this.mPermissionGranted) {
                    service.grantDevicePermission(this.mDevice, this.mUid);
                    if (this.mAlwaysUse != null && this.mAlwaysUse.isChecked()) {
                        int userId = UserHandle.getUserId(this.mUid);
                        service.setDevicePackage(this.mDevice, this.mPackageName, userId);
                    }
                }
            }
            if (this.mAccessory != null) {
                intent.putExtra("accessory", this.mAccessory);
                if (this.mPermissionGranted) {
                    service.grantAccessoryPermission(this.mAccessory, this.mUid);
                    if (this.mAlwaysUse != null && this.mAlwaysUse.isChecked()) {
                        int userId2 = UserHandle.getUserId(this.mUid);
                        service.setAccessoryPackage(this.mAccessory, this.mPackageName, userId2);
                    }
                }
            }
            intent.putExtra("permission", this.mPermissionGranted);
            this.mPendingIntent.send((Context) this, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            Log.w("UsbPermissionActivity", "PendingIntent was cancelled");
        } catch (RemoteException e2) {
            Log.e("UsbPermissionActivity", "IUsbService connection failed", e2);
        }
        if (this.mDisconnectedReceiver != null) {
            unregisterReceiver(this.mDisconnectedReceiver);
        }
        super.onDestroy();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            this.mPermissionGranted = true;
        }
        finish();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (this.mClearDefaultHint == null) {
            return;
        }
        if (isChecked) {
            this.mClearDefaultHint.setVisibility(0);
        } else {
            this.mClearDefaultHint.setVisibility(8);
        }
    }
}
