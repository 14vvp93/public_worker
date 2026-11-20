package com.hcn.skin2.runtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.OverlayInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import com.hcn.auto_compat.app.CompatUtils;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RRO {
    public static final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    private static final String DESCRIPTOR = "android.content.om.IOverlayManager";
    private static final boolean QUERY_MEDIA_ALL_RRO_INFO = true;
    private static final String TAG = "RRO";
    private static final int TRANSACTION_getOverlayInfo = 3;
    private static final int TRANSACTION_getOverlayInfosForTarget = 2;
    private static int TRANSACTION_setEnabled = 4;
    private static RRO sInstance;
    private static IBinder sOverlayManager;
    private Reference<Context> mContextRef;
    private OverlayInfo2 mOverlayInfo = null;
    private BroadcastReceiver mRROChangedReceiver = null;

    public static RRO instance() {
        if (sInstance == null) {
            sInstance = new RRO();
        }
        return sInstance;
    }

    private RRO() {
        if (Build.VERSION.SDK_INT >= 33) {
            TRANSACTION_setEnabled = 5;
        }
    }

    public static void initOverlayManager(Context context) {
        if (sOverlayManager != null) {
            LogUtils.wTag(TAG, "Repeated call, initialized.");
            return;
        }
        IBinder systemService = HUtilsEx.getSystemService("overlay");
        sOverlayManager = systemService;
        if (Objects.isNull(systemService)) {
            LogUtils.wTag(TAG, "Get system service 'overlay' failed.");
        }
        instance().initMediaOverlayInfo(context);
    }

    public static void uninitOverlayManager() {
        instance().unregisterMediaRROReceiver();
    }

    private void initMediaOverlayInfo(Context context) {
        this.mContextRef = new WeakReference(context);
        if (Objects.isNull(sOverlayManager)) {
            return;
        }
        updateMediaOverlayInfo(context);
        registerMediaRROReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMediaOverlayInfo(Context context) {
        String str = context.getPackageName() + ".overlay";
        for (Object obj : new ArrayList(getOverlayInfosForTarget(context.getPackageName(), CompatUtils.getCurrentUserID()))) {
            if (obj instanceof OverlayInfo) {
                OverlayInfo overlayInfo = (OverlayInfo) obj;
                if (overlayInfo.isEnabled()) {
                    str = overlayInfo.packageName;
                }
            }
        }
        this.mOverlayInfo = getOverlayInfo(str, CompatUtils.getCurrentUserID());
        LogUtils.vTag(TAG, "initMediaOverlayInfo: " + this.mOverlayInfo);
    }

    public boolean isEnabled() {
        if (Objects.isNull(this.mOverlayInfo)) {
            return false;
        }
        return this.mOverlayInfo.isEnabled();
    }

    public String overlayPackageName() {
        if (Objects.isNull(this.mOverlayInfo)) {
            return null;
        }
        return this.mOverlayInfo.packageName;
    }

    private void registerMediaRROReceiver() {
        Context context = this.mContextRef.get();
        if (!Objects.isNull(context) && Objects.isNull(this.mRROChangedReceiver)) {
            this.mRROChangedReceiver = new MediaRROReceiver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_OVERLAY_CHANGED);
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart(context.getPackageName(), 0);
            context.registerReceiver(this.mRROChangedReceiver, intentFilter);
            return;
        }
        LogUtils.wTag(TAG, "Function registerMediaRROReceiver called repeatedly!");
    }

    private void unregisterMediaRROReceiver() {
        Context context = this.mContextRef.get();
        if (Objects.isNull(context) || Objects.isNull(this.mRROChangedReceiver)) {
            return;
        }
        context.unregisterReceiver(this.mRROChangedReceiver);
        this.mRROChangedReceiver = null;
    }

    private static final class MediaRROReceiver extends BroadcastReceiver {
        private final Reference<RRO> mOwnerRef;

        public MediaRROReceiver(RRO rro) {
            this.mOwnerRef = new WeakReference(rro);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            RRO rro = this.mOwnerRef.get();
            if (!Objects.isNull(rro) && action.equals(RRO.ACTION_OVERLAY_CHANGED)) {
                Context context2 = (Context) rro.mContextRef.get();
                if (Objects.isNull(context2)) {
                    return;
                }
                rro.updateMediaOverlayInfo(context2);
            }
        }
    }

    public ArrayList<?> getOverlayInfosForTarget(String str, int i) {
        if (Objects.isNull(sOverlayManager)) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeString(str);
                parcelObtain.writeInt(i);
                sOverlayManager.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                ArrayList<?> arrayList = parcelObtain2.readArrayList(getClass().getClassLoader());
                parcelObtain2.recycle();
                parcelObtain.recycle();
                return arrayList;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            parcelObtain2.recycle();
            parcelObtain.recycle();
            throw th;
        }
    }

    public OverlayInfo2 getOverlayInfo(String str, int i) {
        if (Objects.isNull(sOverlayManager)) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeString(str);
                parcelObtain.writeInt(i);
                sOverlayManager.transact(3, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                OverlayInfo2 overlayInfo2 = parcelObtain2.readInt() != 0 ? new OverlayInfo2(parcelObtain2) : null;
                parcelObtain2.recycle();
                parcelObtain.recycle();
                return overlayInfo2;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            parcelObtain2.recycle();
            parcelObtain.recycle();
            throw th;
        }
    }

    public boolean setEnabled(String str, boolean z, int i) {
        if (Objects.isNull(sOverlayManager)) {
            return false;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeString(str);
                parcelObtain.writeInt(z ? 1 : 0);
                parcelObtain.writeInt(i);
                sOverlayManager.transact(TRANSACTION_setEnabled, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                boolean z2 = parcelObtain2.readInt() != 0;
                parcelObtain2.recycle();
                parcelObtain.recycle();
                return z2;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            parcelObtain2.recycle();
            parcelObtain.recycle();
            throw th;
        }
    }
}
