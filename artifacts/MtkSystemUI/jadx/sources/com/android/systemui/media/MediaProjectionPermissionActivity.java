package com.android.systemui.media;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.BidiFormatter;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class MediaProjectionPermissionActivity extends Activity implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private AlertDialog mDialog;
    private String mPackageName;
    private boolean mPermanentGrant;
    private IMediaProjectionManager mService;
    private int mUid;

    @Override // android.app.Activity
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mPackageName = getCallingPackage();
        IBinder b = ServiceManager.getService("media_projection");
        this.mService = IMediaProjectionManager.Stub.asInterface(b);
        if (this.mPackageName == null) {
            finish();
            return;
        }
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo aInfo = packageManager.getApplicationInfo(this.mPackageName, 0);
            this.mUid = aInfo.uid;
            try {
                if (this.mService.hasProjectionPermission(this.mUid, this.mPackageName)) {
                    setResult(-1, getMediaProjectionIntent(this.mUid, this.mPackageName, false));
                    finish();
                    return;
                }
                TextPaint paint = new TextPaint();
                paint.setTextSize(42.0f);
                String label = aInfo.loadLabel(packageManager).toString();
                int labelLength = label.length();
                int offset = 0;
                while (offset < labelLength) {
                    int codePoint = label.codePointAt(offset);
                    int type = Character.getType(codePoint);
                    if (type == 13 || type == 15 || type == 14) {
                        label = label.substring(0, offset) + "…";
                        break;
                    }
                    offset += Character.charCount(codePoint);
                }
                if (label.isEmpty()) {
                    label = this.mPackageName;
                }
                String unsanitizedAppName = TextUtils.ellipsize(label, paint, 500.0f, TextUtils.TruncateAt.END).toString();
                String appName = BidiFormatter.getInstance().unicodeWrap(unsanitizedAppName);
                String actionText = getString(R.string.media_projection_dialog_text, new Object[]{appName});
                SpannableString message = new SpannableString(actionText);
                int appNameIndex = actionText.indexOf(appName);
                if (appNameIndex >= 0) {
                    message.setSpan(new StyleSpan(1), appNameIndex, appNameIndex + appName.length(), 0);
                }
                this.mDialog = new AlertDialog.Builder(this).setIcon(aInfo.loadIcon(packageManager)).setMessage(message).setPositiveButton(R.string.media_projection_action_text, this).setNegativeButton(android.R.string.cancel, this).setView(R.layout.remember_permission_checkbox).setOnCancelListener(this).create();
                this.mDialog.create();
                this.mDialog.getButton(-1).setFilterTouchesWhenObscured(true);
                ((CheckBox) this.mDialog.findViewById(R.id.remember)).setOnCheckedChangeListener(this);
                Window w = this.mDialog.getWindow();
                w.setType(2003);
                w.addPrivateFlags(524288);
                this.mDialog.show();
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error checking projection permissions", e);
                finish();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("MediaProjectionPermissionActivity", "unable to look up package name", e2);
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0035 A[DONT_GENERATE] */
    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        try {
            if (which == -1) {
                setResult(-1, getMediaProjectionIntent(this.mUid, this.mPackageName, this.mPermanentGrant));
            }
        } catch (RemoteException e) {
            Log.e("MediaProjectionPermissionActivity", "Error granting projection permission", e);
            setResult(0);
        } finally {
            if (this.mDialog != null) {
                this.mDialog.dismiss();
            }
            finish();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.mPermanentGrant = isChecked;
    }

    private Intent getMediaProjectionIntent(int uid, String packageName, boolean permanentGrant) throws RemoteException {
        IMediaProjection projection = this.mService.createProjection(uid, packageName, 0, permanentGrant);
        Intent intent = new Intent();
        intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection.asBinder());
        return intent;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        finish();
    }
}
