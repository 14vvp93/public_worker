package com.autochips.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.autochips.bluetooth.adapter.EnableScrollViewPager;
import com.autochips.bluetooth.fragment.DialFragment;
import com.autochips.bluetooth.fragment.PBFragment;
import com.autochips.bluetooth.fragment.PairedFragment;
import com.autochips.bluetooth.fragment.PairedFragmentEx;
import com.autochips.bluetooth.fragment.RecordFragment;
import com.autochips.bluetooth.fragment.SetupFragment;
import com.autochips.bluetooth.fragment.SetupFragmentEx;
import com.autochips.bluetooth.skin.FastBlurUtils;
import com.autochips.bluetooth.skin.SkinUtils;
import com.autochips.bluetooth.viewpager.FragmentViewPaperAdapter;
import com.autochips.bluetooth.viewpager.transforms.DepthPageTransformer;
import com.hcn.auto.theme.utils.ThemeUtils;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.skin.support.app.SkinCompatActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainBluetoothActivity extends SkinCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, IFragmentCallback {
    private static final int MSG_UPDATE_CTRL = 0;
    public static final int PAGE_FIVE = 4;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_TWO = 1;
    private static final String TAG = "MainBluetoothActivity";
    public static final String VOICE_EVENT_BT_EXIT = "com.bt.extra.exit";
    private Fragment dialFragment;
    private LocalBluetoothAdapterManager mAdapterManager;
    private View mBtnContract;
    private View mBtnDial;
    private View mBtnPaired;
    private View mBtnRecord;
    private View mBtnSetting;
    private FragmentViewPaperAdapter mFragmentViewPaperAdapter;
    private View mSelectedView;
    private EnableScrollViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList();
    private Handler mHandler = null;
    private boolean isConnected = false;
    private ViewGroup mainLayout = null;
    private Drawable layoutDrawable = null;
    private ViewGroup llBottomMenu = null;
    private boolean isUseSkinLayout = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.MainBluetoothActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (intExtra == 2) {
                    MainBluetoothActivity.this.isConnected = true;
                    MainBluetoothActivity.this.updateView();
                    return;
                } else {
                    if (intExtra == 0) {
                        MainBluetoothActivity.this.isConnected = false;
                        MainBluetoothActivity.this.updateView();
                        return;
                    }
                    return;
                }
            }
            if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10) == 10) {
                    MainBluetoothActivity.this.isConnected = false;
                    MainBluetoothActivity.this.updateView();
                    return;
                }
                return;
            }
            if (action.equals(MainBluetoothActivity.VOICE_EVENT_BT_EXIT)) {
                MainBluetoothActivity.this.finish();
            }
        }
    };
    private ConnectionListener mAdapterListener = new ConnectionListener() { // from class: com.autochips.bluetooth.MainBluetoothActivity.2
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            Log.d(MainBluetoothActivity.TAG, "onServiceConnected");
            MainBluetoothActivity mainBluetoothActivity = MainBluetoothActivity.this;
            mainBluetoothActivity.isConnected = mainBluetoothActivity.mAdapterManager.isBluetoothConnected();
            MainBluetoothActivity.this.mHandler.removeMessages(0);
            MainBluetoothActivity.this.mHandler.sendEmptyMessage(0);
        }
    };

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(67108864);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        this.mHandler = new MyHandler(this);
        setContentView(C0414R.layout.activity_main);
        if (SkinUtils.useSkinPackage()) {
            this.isUseSkinLayout = SkinUtils.isResourcesExist(C0414R.layout.activity_main) > 0;
        }
        init();
        initView();
        selectPage(3);
    }

    private static class MyHandler extends Handler {
        private WeakReference<MainBluetoothActivity> mWeakReference;

        public MyHandler(MainBluetoothActivity mainBluetoothActivity) {
            this.mWeakReference = new WeakReference<>(mainBluetoothActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            MainBluetoothActivity mainBluetoothActivity;
            if (message.what == 0 && (mainBluetoothActivity = this.mWeakReference.get()) != null) {
                mainBluetoothActivity.updateView();
            }
            super.handleMessage(message);
        }
    }

    protected void init() {
        LocalBluetoothAdapterManager localBluetoothAdapterManagerInit = LocalBluetoothAdapterManager.getInstance().init(this);
        this.mAdapterManager = localBluetoothAdapterManagerInit;
        localBluetoothAdapterManagerInit.addConnectListener(this.mAdapterListener);
        this.dialFragment = new DialFragment();
        RecordFragment recordFragment = new RecordFragment();
        PBFragment pBFragment = new PBFragment();
        Fragment setupFragment = getSetupFragment();
        Fragment pairedFragment = getPairedFragment();
        this.fragments.add(this.dialFragment);
        this.fragments.add(pBFragment);
        this.fragments.add(recordFragment);
        this.fragments.add(pairedFragment);
        if (SkinUtils.getInteger(C0414R.integer.setup_fragment_type) != -1) {
            this.fragments.add(setupFragment);
        }
    }

    private Fragment getPairedFragment() {
        if (SkinUtils.getInteger(C0414R.integer.paired_fragment_type) == 1) {
            PairedFragmentEx pairedFragmentEx = new PairedFragmentEx();
            pairedFragmentEx.setFragmentCallback(this);
            return pairedFragmentEx;
        }
        return new PairedFragment();
    }

    private Fragment getSetupFragment() {
        if (SkinUtils.getInteger(C0414R.integer.setup_fragment_type) == 1) {
            SetupFragmentEx setupFragmentEx = new SetupFragmentEx();
            setupFragmentEx.setFragmentCallback(this);
            return setupFragmentEx;
        }
        return new SetupFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateView() {
        int currentItem;
        EnableScrollViewPager enableScrollViewPager = this.mViewPager;
        if (enableScrollViewPager == null) {
            return;
        }
        if (!this.isConnected && (currentItem = enableScrollViewPager.getCurrentItem()) >= 0 && currentItem <= 2) {
            selectPage(3);
        }
        this.mViewPager.setScanScroll(this.isConnected);
    }

    private void initView() {
        this.mainLayout = (ViewGroup) findViewById(SkinUtils.getId(C0414R.id.layout_main));
        Drawable appWallpaper = ThemeUtils.getInstance().getAppWallpaper();
        ViewGroup viewGroup = this.mainLayout;
        if (viewGroup != null && appWallpaper != null) {
            viewGroup.setBackground(appWallpaper);
        }
        ViewGroup viewGroup2 = this.mainLayout;
        if (viewGroup2 != null) {
            this.layoutDrawable = viewGroup2.getBackground();
        }
        this.llBottomMenu = (ViewGroup) findViewById(SkinUtils.getId(C0414R.id.bottom_menu));
        this.mBtnDial = findViewById(SkinUtils.getId(C0414R.id.btn_telephone));
        this.mBtnContract = findViewById(SkinUtils.getId(C0414R.id.btn_phonebook));
        this.mBtnRecord = findViewById(SkinUtils.getId(C0414R.id.btn_record));
        this.mBtnSetting = findViewById(SkinUtils.getId(C0414R.id.btn_setup));
        this.mBtnPaired = findViewById(SkinUtils.getId(C0414R.id.btn_pared));
        if (SkinUtils.getInteger(C0414R.integer.setup_tab_btn_type) != 0) {
            this.mBtnSetting.setVisibility(8);
        }
        EnableScrollViewPager enableScrollViewPager = (EnableScrollViewPager) findViewById(SkinUtils.getId(C0414R.id.viewPager));
        this.mViewPager = enableScrollViewPager;
        enableScrollViewPager.setOffscreenPageLimit(5);
        this.mBtnDial.setOnClickListener(this);
        this.mBtnContract.setOnClickListener(this);
        this.mBtnRecord.setOnClickListener(this);
        this.mBtnPaired.setOnClickListener(this);
        this.mBtnSetting.setOnClickListener(this);
        FragmentViewPaperAdapter fragmentViewPaperAdapter = new FragmentViewPaperAdapter(getSupportFragmentManager(), this.fragments);
        this.mFragmentViewPaperAdapter = fragmentViewPaperAdapter;
        this.mViewPager.setAdapter(fragmentViewPaperAdapter);
        this.mViewPager.setPageTransformer(true, new DepthPageTransformer());
        this.mViewPager.addOnPageChangeListener(this);
    }

    private void selectPage(int i) {
        this.mViewPager.setCurrentItem(i, false);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        if (i == 0) {
            setViewSelected(this.mBtnDial);
            return;
        }
        if (i == 1) {
            setViewSelected(this.mBtnContract);
            return;
        }
        if (i == 2) {
            setViewSelected(this.mBtnRecord);
        } else if (i == 3) {
            setViewSelected(this.mBtnPaired);
        } else {
            if (i != 4) {
                return;
            }
            setViewSelected(this.mBtnSetting);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (this.isUseSkinLayout) {
            id = SkinUtils.getViewId(view);
        }
        switch (id) {
            case C0414R.id.btn_pared /* 2131230862 */:
                selectPage(3);
                setViewSelected(view);
                break;
            case C0414R.id.btn_phonebook /* 2131230863 */:
                if (this.isConnected) {
                    selectPage(1);
                    setViewSelected(view);
                } else {
                    Utils.showToast(this, SkinUtils.getString(C0414R.string.str_connect_hf));
                }
                break;
            case C0414R.id.btn_record /* 2131230866 */:
                if (this.isConnected) {
                    selectPage(2);
                    setViewSelected(view);
                } else {
                    Utils.showToast(this, SkinUtils.getString(C0414R.string.str_connect_hf));
                }
                break;
            case C0414R.id.btn_setup /* 2131230868 */:
                selectPage(4);
                setViewSelected(view);
                break;
            case C0414R.id.btn_telephone /* 2131230871 */:
                if (this.isConnected) {
                    selectPage(0);
                    setViewSelected(view);
                } else {
                    Utils.showToast(this, SkinUtils.getString(C0414R.string.str_connect_hf));
                }
                break;
        }
    }

    public void setViewSelected(View view) {
        View view2 = this.mSelectedView;
        if (view2 != null) {
            view2.setSelected(false);
        }
        view.setSelected(true);
        this.mSelectedView = view;
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.e(TAG, "onUserLeaveHint");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction(VOICE_EVENT_BT_EXIT);
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        Log.e(TAG, "onResume()");
        super.onResume();
        this.isConnected = this.mAdapterManager.isBluetoothConnected();
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        Log.e(TAG, "onPause()");
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        BroadcastReceiver broadcastReceiver = this.mReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EnableScrollViewPager enableScrollViewPager = this.mViewPager;
        if (enableScrollViewPager != null) {
            enableScrollViewPager.removeOnPageChangeListener(this);
        }
        this.mAdapterManager.removeConnectListener(this.mAdapterListener);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                if (this.mViewPager.getCurrentItem() == 0) {
                    return ((DialFragment) this.dialFragment).onKeyDown(i, keyEvent);
                }
                break;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                if (this.mViewPager.getCurrentItem() == 0) {
                    return ((DialFragment) this.dialFragment).onKeyUp(i, keyEvent);
                }
                break;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.autochips.bluetooth.IFragmentCallback
    public boolean updateBackground(boolean z) {
        Log.d(TAG, "updateBackground: " + z);
        if (z) {
            ViewGroup viewGroup = this.mainLayout;
            if (viewGroup != null) {
                viewGroup.setBackground(new BitmapDrawable(getResources(), FastBlurUtils.getBlurBackgroundDrawer(this)));
            }
            EnableScrollViewPager enableScrollViewPager = this.mViewPager;
            if (enableScrollViewPager != null) {
                enableScrollViewPager.setVisibility(4);
            }
            ViewGroup viewGroup2 = this.llBottomMenu;
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(4);
            }
        } else {
            ViewGroup viewGroup3 = this.mainLayout;
            if (viewGroup3 != null) {
                viewGroup3.setBackground(this.layoutDrawable);
            }
            EnableScrollViewPager enableScrollViewPager2 = this.mViewPager;
            if (enableScrollViewPager2 != null) {
                enableScrollViewPager2.setVisibility(0);
            }
            ViewGroup viewGroup4 = this.llBottomMenu;
            if (viewGroup4 != null) {
                viewGroup4.setVisibility(0);
            }
        }
        return false;
    }
}
