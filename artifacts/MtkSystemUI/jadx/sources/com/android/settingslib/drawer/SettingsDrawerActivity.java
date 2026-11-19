package com.android.settingslib.drawer;

import android.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SettingsDrawerActivity extends Activity {
    private static final boolean DEBUG = Log.isLoggable("SettingsDrawerActivity", 3);
    private static ArraySet<ComponentName> sTileBlacklist = new ArraySet<>();
    private FrameLayout mContentHeaderContainer;
    private final PackageReceiver mPackageReceiver = new PackageReceiver();
    private final List<CategoryListener> mCategoryListeners = new ArrayList();

    public interface CategoryListener {
        void onCategoriesChanged();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.currentTimeMillis();
        TypedArray theme = getTheme().obtainStyledAttributes(R.styleable.Theme);
        if (!theme.getBoolean(38, false)) {
            getWindow().addFlags(Integer.MIN_VALUE);
            requestWindowFeature(1);
        }
        super.setContentView(com.android.settingslib.R.layout.settings_with_drawer);
        this.mContentHeaderContainer = (FrameLayout) findViewById(com.android.settingslib.R.id.content_header_container);
        Toolbar toolbar = (Toolbar) findViewById(com.android.settingslib.R.id.action_bar);
        if (theme.getBoolean(38, false)) {
            toolbar.setVisibility(8);
        } else {
            setActionBar(toolbar);
        }
    }

    @Override // android.app.Activity
    public boolean onNavigateUp() {
        if (!super.onNavigateUp()) {
            finish();
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction("android.intent.action.PACKAGE_CHANGED");
        filter.addAction("android.intent.action.PACKAGE_REPLACED");
        filter.addDataScheme("package");
        registerReceiver(this.mPackageReceiver, filter);
        new CategoriesUpdateTask().execute(new Void[0]);
    }

    @Override // android.app.Activity
    protected void onPause() {
        unregisterReceiver(this.mPackageReceiver);
        super.onPause();
    }

    @Override // android.app.Activity
    public void setContentView(int layoutResID) {
        ViewGroup parent = (ViewGroup) findViewById(com.android.settingslib.R.id.content_frame);
        if (parent != null) {
            parent.removeAllViews();
        }
        LayoutInflater.from(this).inflate(layoutResID, parent);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        ((ViewGroup) findViewById(com.android.settingslib.R.id.content_frame)).addView(view);
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        ((ViewGroup) findViewById(com.android.settingslib.R.id.content_frame)).addView(view, params);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCategoriesChanged() {
        int N = this.mCategoryListeners.size();
        for (int i = 0; i < N; i++) {
            this.mCategoryListeners.get(i).onCategoriesChanged();
        }
    }

    public String getSettingPkg() {
        return "com.android.settings";
    }

    private class CategoriesUpdateTask extends AsyncTask<Void, Void, Void> {
        private final CategoryManager mCategoryManager;

        public CategoriesUpdateTask() {
            this.mCategoryManager = CategoryManager.get(SettingsDrawerActivity.this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... params) {
            this.mCategoryManager.reloadAllCategories(SettingsDrawerActivity.this, SettingsDrawerActivity.this.getSettingPkg());
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void result) {
            this.mCategoryManager.updateCategoryFromBlacklist(SettingsDrawerActivity.sTileBlacklist);
            SettingsDrawerActivity.this.onCategoriesChanged();
        }
    }

    private class PackageReceiver extends BroadcastReceiver {
        private PackageReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SettingsDrawerActivity.this.new CategoriesUpdateTask().execute(new Void[0]);
        }
    }
}
