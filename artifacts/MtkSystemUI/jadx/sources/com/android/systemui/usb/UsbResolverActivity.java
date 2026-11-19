package com.android.systemui.usb;

import android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.widget.CheckBox;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.app.ResolverActivity;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class UsbResolverActivity extends ResolverActivity {
    private UsbAccessory mAccessory;
    private UsbDevice mDevice;
    private UsbDisconnectedReceiver mDisconnectedReceiver;
    private ResolveInfo mForwardResolveInfo;
    private Intent mOtherProfileIntent;

    /* JADX WARN: Multi-variable type inference failed */
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Parcelable targetParcelable = intent.getParcelableExtra("android.intent.extra.INTENT");
        if (!(targetParcelable instanceof Intent)) {
            Log.w("UsbResolverActivity", "Target is not an intent: " + targetParcelable);
            finish();
            return;
        }
        Intent target = (Intent) targetParcelable;
        ArrayList<ResolveInfo> rList = new ArrayList<>(intent.getParcelableArrayListExtra("rlist"));
        ArrayList<ResolveInfo> rListOtherProfile = new ArrayList<>();
        this.mForwardResolveInfo = null;
        Iterator<ResolveInfo> iterator = rList.iterator();
        while (iterator.hasNext()) {
            ResolveInfo ri = iterator.next();
            if (ri.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
                this.mForwardResolveInfo = ri;
            } else if (UserHandle.getUserId(ri.activityInfo.applicationInfo.uid) != UserHandle.myUserId()) {
                iterator.remove();
                rListOtherProfile.add(ri);
            }
        }
        this.mDevice = (UsbDevice) target.getParcelableExtra("device");
        if (this.mDevice != null) {
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mDevice);
        } else {
            this.mAccessory = (UsbAccessory) target.getParcelableExtra("accessory");
            if (this.mAccessory == null) {
                Log.e("UsbResolverActivity", "no device or accessory");
                finish();
                return;
            }
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mAccessory);
        }
        if (this.mForwardResolveInfo != null) {
            if (rListOtherProfile.size() > 1) {
                this.mOtherProfileIntent = new Intent(intent);
                this.mOtherProfileIntent.putParcelableArrayListExtra("rlist", rListOtherProfile);
            } else {
                this.mOtherProfileIntent = new Intent((Context) this, (Class<?>) UsbConfirmActivity.class);
                this.mOtherProfileIntent.putExtra("rinfo", rListOtherProfile.get(0));
                if (this.mDevice != null) {
                    this.mOtherProfileIntent.putExtra("device", this.mDevice);
                }
                if (this.mAccessory != null) {
                    this.mOtherProfileIntent.putExtra("accessory", this.mAccessory);
                }
            }
        }
        CharSequence title = getResources().getText(R.string.accessibility_shortcut_enabling_service);
        super.onCreate(savedInstanceState, target, title, (Intent[]) null, rList, true);
        CheckBox alwaysUse = (CheckBox) findViewById(R.id.aboveThumb);
        if (alwaysUse != null) {
            if (this.mDevice == null) {
                alwaysUse.setText(com.android.systemui.R.string.always_use_accessory);
            } else {
                alwaysUse.setText(com.android.systemui.R.string.always_use_device);
            }
        }
    }

    protected void onDestroy() {
        if (this.mDisconnectedReceiver != null) {
            unregisterReceiver(this.mDisconnectedReceiver);
        }
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected boolean onTargetSelected(ResolverActivity.TargetInfo target, boolean alwaysCheck) {
        ResolveInfo ri = target.getResolveInfo();
        if (ri == this.mForwardResolveInfo) {
            startActivityAsUser(this.mOtherProfileIntent, null, UserHandle.of(this.mForwardResolveInfo.targetUserId));
            return true;
        }
        try {
            IBinder b = ServiceManager.getService("usb");
            IUsbManager service = IUsbManager.Stub.asInterface(b);
            int uid = ri.activityInfo.applicationInfo.uid;
            int userId = UserHandle.myUserId();
            if (this.mDevice != null) {
                service.grantDevicePermission(this.mDevice, uid);
                if (alwaysCheck) {
                    service.setDevicePackage(this.mDevice, ri.activityInfo.packageName, userId);
                } else {
                    service.setDevicePackage(this.mDevice, (String) null, userId);
                }
            } else if (this.mAccessory != null) {
                service.grantAccessoryPermission(this.mAccessory, uid);
                if (alwaysCheck) {
                    service.setAccessoryPackage(this.mAccessory, ri.activityInfo.packageName, userId);
                } else {
                    service.setAccessoryPackage(this.mAccessory, (String) null, userId);
                }
            }
            try {
                target.startAsUser(this, (Bundle) null, UserHandle.of(userId));
            } catch (ActivityNotFoundException e) {
                Log.e("UsbResolverActivity", "startActivity failed", e);
            }
        } catch (RemoteException e2) {
            Log.e("UsbResolverActivity", "onIntentSelected failed", e2);
        }
        return true;
    }
}
