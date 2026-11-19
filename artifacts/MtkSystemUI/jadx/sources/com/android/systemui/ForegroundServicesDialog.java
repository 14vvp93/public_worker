package com.android.systemui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.logging.MetricsLogger;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class ForegroundServicesDialog extends AlertActivity implements DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener, AlertController.AlertParams.OnPrepareListViewListener {
    private PackageItemAdapter mAdapter;
    private DialogInterface.OnClickListener mAppClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.ForegroundServicesDialog.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            String pkg = ForegroundServicesDialog.this.mAdapter.getItem(which).packageName;
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", pkg, null));
            ForegroundServicesDialog.this.startActivity(intent);
            ForegroundServicesDialog.this.finish();
        }
    };
    LayoutInflater mInflater;
    private MetricsLogger mMetricsLogger;
    private String[] mPackages;

    /* JADX WARN: Multi-variable type inference failed */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependency.initDependencies(getApplicationContext());
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mInflater = LayoutInflater.from(this);
        this.mAdapter = new PackageItemAdapter(this);
        AlertController.AlertParams p = this.mAlertParams;
        p.mAdapter = this.mAdapter;
        p.mOnClickListener = this.mAppClickListener;
        p.mCustomTitleView = this.mInflater.inflate(R.layout.foreground_service_title, (ViewGroup) null);
        p.mIsSingleChoice = true;
        p.mOnItemSelectedListener = this;
        p.mPositiveButtonText = getString(android.R.string.bugreport_screenshot_failure_toast);
        p.mPositiveButtonListener = this;
        p.mOnPrepareListViewListener = this;
        updateApps(getIntent());
        if (this.mPackages == null) {
            Log.w("ForegroundServicesDialog", "No packages supplied");
            finish();
        } else {
            setupAlert();
        }
    }

    protected void onResume() {
        super.onResume();
        this.mMetricsLogger.visible(944);
    }

    protected void onPause() {
        super.onPause();
        this.mMetricsLogger.hidden(944);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateApps(intent);
    }

    protected void onStop() {
        super.onStop();
        if (!isChangingConfigurations()) {
            finish();
        }
    }

    void updateApps(Intent intent) {
        this.mPackages = intent.getStringArrayExtra("packages");
        if (this.mPackages != null) {
            this.mAdapter.setPackages(this.mPackages);
        }
    }

    public void onPrepareListView(ListView listView) {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        finish();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView parent) {
    }

    private static class PackageItemAdapter extends ArrayAdapter<ApplicationInfo> {
        final IconDrawableFactory mIconDrawableFactory;
        final LayoutInflater mInflater;
        final PackageManager mPm;

        public PackageItemAdapter(Context context) {
            super(context, R.layout.foreground_service_item);
            this.mPm = context.getPackageManager();
            this.mInflater = LayoutInflater.from(context);
            this.mIconDrawableFactory = IconDrawableFactory.newInstance(context, true);
        }

        public void setPackages(String[] packages) {
            clear();
            ArrayList<ApplicationInfo> apps = new ArrayList<>();
            for (String str : packages) {
                try {
                    apps.add(this.mPm.getApplicationInfo(str, 4202496));
                } catch (PackageManager.NameNotFoundException e) {
                }
            }
            apps.sort(new ApplicationInfo.DisplayNameComparator<>(this.mPm));
            addAll(apps);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = this.mInflater.inflate(R.layout.foreground_service_item, parent, false);
            } else {
                view = convertView;
            }
            ImageView icon = (ImageView) view.findViewById(R.id.app_icon);
            icon.setImageDrawable(this.mIconDrawableFactory.getBadgedIcon(getItem(position)));
            TextView label = (TextView) view.findViewById(R.id.app_name);
            label.setText(getItem(position).loadLabel(this.mPm));
            return view;
        }
    }
}
