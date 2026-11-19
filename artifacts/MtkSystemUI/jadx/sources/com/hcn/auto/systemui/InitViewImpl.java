package com.hcn.auto.systemui;

import android.carsource.McuManager;
import android.carstatus.CarStatus;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class InitViewImpl {
    RelativeLayout bottomBg;
    private RecyclerViewDialog dialog;
    private ImageButton mBtnAllApp;
    private ImageButton mBtnBt;
    private ImageButton mBtnBtMusic;
    private ImageButton mBtnEq;
    private ImageButton mBtnHome;
    private ImageButton mBtnMusic;
    private ImageButton mBtnMute;
    private ImageButton mBtnNavi;
    private ImageButton mBtnOffScreen;
    private ImageButton mBtnRadio;
    private ImageButton mBtnSetting;
    private Context mContext;
    private String mCurrentSkin;
    private TextView mDate;
    private DynamicAppRecyclerView mDynamicAppRecyclerView;
    private ImageView mMuteImg;
    private ImageView mPlayImg;
    private TextView mTime;
    private TextView mTvSoundVolume;
    private int mUiMode;
    private View mView;
    private TextView mWeek;
    private BroadcastReceiver timeReceiver;
    Utils utils;
    private int mIsReversing = 0;
    protected Resources mResources = SkinCompatResources.getInstance().getSkinResources();
    protected String mPackageName = SkinCompatResources.getInstance().getSkinPkgName();

    public InitViewImpl(View view, Context context) {
        this.mView = view;
        this.mContext = context;
        initView();
    }

    public void initView() {
        if ("1".equals(Utils.getSystemProperty("independent_systemui_style", "0"))) {
            this.mCurrentSkin = Utils.getSystemProperty("persist.sys.systemui.skin", "");
        } else {
            this.mCurrentSkin = Utils.getSystemProperty("persist.sys.skin", "");
        }
        this.utils = new Utils(this.mContext);
        this.utils.init();
        this.mUiMode = this.mContext.getResources().getConfiguration().uiMode;
        boolean isNight = (this.mUiMode & 48) == 32;
        initBtnView(this.mCurrentSkin);
        this.mUiMode = this.mContext.getResources().getConfiguration().uiMode;
        if (this.mBtnAllApp != null) {
            this.mBtnAllApp.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$dME3wslnbpTaukINnWlzPldQ-9E
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(335, 50);
                }
            });
        }
        if (this.mBtnHome != null) {
            this.mBtnHome.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$6avzLCZeoTBLphbT3RW3AJpYgNk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(3, 50);
                }
            });
        }
        if (this.mBtnEq != null) {
            this.mBtnEq.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$WHoCQdtv7g87_vl58uLJLbING0w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(308, 50);
                }
            });
        }
        if (this.mBtnOffScreen != null) {
            this.mBtnOffScreen.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$BDU1y7MspRk9gTmILJ8whvcxyL4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(310, 50);
                }
            });
        }
        if (this.mBtnBtMusic != null) {
            this.mBtnBtMusic.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$ahOO5KN-4drqFtbD-pRLHMBk7mk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(331, 50);
                }
            });
        }
        if (this.mBtnNavi != null) {
            this.mBtnNavi.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$AiB7JWELCdhbhPPsQh3bzdDIc7o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(298, 50);
                }
            });
        }
        if (this.mBtnMusic != null) {
            this.mBtnMusic.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$gLH1U_fMoZ9_Vw1jFdz8jSYl4hw
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(209, 50);
                }
            });
        }
        if (this.mBtnMute != null) {
            this.mBtnMute.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$n3Lwea6kVCp8AdcQkcleJxA5lY0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(164, 50);
                }
            });
        }
        if (this.mBtnSetting != null) {
            this.mBtnSetting.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$NJPmxLoXrudd1jW5JoJwKoDtRYo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    com.android.systemui.util.Utils.startActivity(this.f$0.mContext, "com.android.settings", "com.android.settings.Settings");
                }
            });
        }
        if (this.mBtnBt != null) {
            this.mBtnBt.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$AYAfwqp1j2KuTyjQjbZmvUAco3I
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    McuManager.getsInstance().injectKeyEventTimeout(302, 50);
                }
            });
        }
        if (this.mBtnRadio != null) {
            this.mBtnRadio.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$rOVlinZeUekh1NmiLBid3CfkEw4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    com.android.systemui.util.Utils.startActivity(this.f$0.mContext, "com.hcn.autoradio", "com.hcn.autoradio.RadioMain");
                }
            });
        }
        if (this.mTime != null) {
            this.mTime.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$kybrSNNyIjQa0N4bWP9hGVA-QlU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InitViewImpl.startTimeSetting(this.f$0.mContext);
                }
            });
        }
        if (this.mDate != null) {
            this.mDate.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$CNKIsYhRRbQNvSFmgflvzokNnzw
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InitViewImpl.startCalendar(this.f$0.mContext);
                }
            });
        }
        if (this.mWeek != null) {
            this.mWeek.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$0l96YvVH8LDpqSxmoNrW7AksGiY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InitViewImpl.startCalendar(this.f$0.mContext);
                }
            });
        }
        if (this.bottomBg != null && SystemUIApplication.hasBottomBar()) {
            this.bottomBg.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$IWnMhJ3tTET3ib_HnDLmkg7PQoQ
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return InitViewImpl.lambda$initView$14(this.f$0, view);
                }
            });
        }
        onTimeChanged();
        registerTimeReceiver();
        if (this.mTvSoundVolume != null && this.mPlayImg != null) {
            Log.e("InitViewImpl", "mTvSoundVolume-not null");
            ViewGroup.MarginLayoutParams tvSoundVolumeParams = new RelativeLayout.LayoutParams(-2, -2);
            ViewGroup.MarginLayoutParams tvPlayImgParams = new RelativeLayout.LayoutParams(-2, -2);
            int tvSoundVolumeMarginStart = Integer.parseInt(Utils.getSystemProperty("tvSoundVolume_layout_marginStart", "0"));
            int tvSoundVolumeMarginBottom = Integer.parseInt(Utils.getSystemProperty("tvSoundVolume_layout_marginBottom", "0"));
            int playImgHeight = Integer.parseInt(Utils.getSystemProperty("playImg_layout_height", "0"));
            int playImgWidth = Integer.parseInt(Utils.getSystemProperty("playImg_layout_width", "0"));
            int playImgMarginEnd = Integer.parseInt(Utils.getSystemProperty("playImg_layout_marginEnd", "0"));
            int playImgMarginBottom = Integer.parseInt(Utils.getSystemProperty("playImg_layout_marginBottom", "0"));
            int bottomBgHeight = Integer.parseInt(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""));
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            int bottomBgWidth = wm.getDefaultDisplay().getWidth();
            int textSize = (int) this.mTvSoundVolume.getTextSize();
            Log.e("InitViewImpl", "mTvSoundVolume-not null--bottombgWidth-" + bottomBgWidth + "--bottombgHtight--" + bottomBgHeight);
            Log.e("InitViewImpl", "mTvSoundVolume-not null--textSize-" + textSize + "--screenWidth--" + bottomBgWidth);
            if (tvSoundVolumeMarginStart != 0 && tvSoundVolumeMarginBottom != 0) {
                if (this.mContext.getResources().getConfiguration().getLayoutDirection() == 1) {
                    tvSoundVolumeParams.setMargins(0, (bottomBgHeight - textSize) - tvSoundVolumeMarginBottom, (bottomBgWidth - tvSoundVolumeMarginStart) - textSize, 0);
                    Log.e("InitViewImpl", "mTvSoundVolume-not null right");
                } else {
                    tvSoundVolumeParams.setMargins(tvSoundVolumeMarginStart, (bottomBgHeight - textSize) - tvSoundVolumeMarginBottom, 0, 0);
                    Log.e("InitViewImpl", "mTvSoundVolume-not null left");
                }
                this.mTvSoundVolume.setLayoutParams(tvSoundVolumeParams);
            }
            if (playImgMarginEnd != 0 && playImgMarginBottom != 0) {
                tvPlayImgParams.setMargins((bottomBgWidth - playImgMarginEnd) - playImgWidth, (bottomBgHeight - playImgHeight) - playImgMarginBottom, 0, 0);
                this.mPlayImg.setLayoutParams(tvPlayImgParams);
            }
            Log.e("InitViewImpl", "mTvSoundVolume-not null---" + tvSoundVolumeMarginStart + "----" + tvSoundVolumeMarginBottom);
            Log.e("InitViewImpl", "mTvSoundVolume-not null---" + playImgMarginEnd + "----" + playImgMarginBottom);
            Log.e("InitViewImpl", "mTvSoundVolume-not null--playImgHeight--" + playImgHeight + "--playImgWidth--" + playImgWidth);
        }
        if (isNewBottomUI(this.mCurrentSkin)) {
            Log.e("InitViewImpl", "initView---" + this.mPackageName + "----" + this.mUiMode + "---" + isNight);
            int configChangeTheme = SkinCompatResources.getInstance().getInteger("config_change_theme");
            if (configChangeTheme == 1) {
                updateBottomBgUIStyle((this.mUiMode & 48) == 32 ? "night" : "");
            }
        }
        try {
            this.mDynamicAppRecyclerView = (DynamicAppRecyclerView) findViewByName("darv_apps");
            if (this.mDynamicAppRecyclerView == null) {
                this.mDynamicAppRecyclerView = (DynamicAppRecyclerView) this.mView.findViewById(R.id.darv_apps);
            }
            if (this.mDynamicAppRecyclerView == null) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.xt617_box_bg);
            int newWidth = (int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_bg_width);
            int newHeight = (int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_bg_height);
            Drawable dialogBackground = resizeBitmapDrawable(bitmap, newWidth, newHeight);
            this.dialog = new RecyclerViewDialog(this.mContext);
            this.dialog.setLayoutType(1).setRowCount(3).setColumnCount(5).setTitleColor(this.mContext.getResources().getColor(R.color.xt617_text_translate_color, this.mContext.getTheme())).setItemIconSize((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_item_icon_size)).setItemTextSize((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_item_text_size)).setDialogWidth((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_width)).setDialogHeight((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_height)).setItemHeight((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_item_height)).setItemWidth((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_item_width)).setItemTextDrawablePadding((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_item_text_padding)).setItemTextColor(this.mContext.getResources().getColor(R.color.xt617_text_color, this.mContext.getTheme())).setDialogBackground(dialogBackground).setRecyclerViewTopMargin((int) this.mContext.getResources().getDimension(R.dimen.xt617_dialog_top_margin_top)).create();
            this.dialog.getRootView().setOnClickListener(new View.OnClickListener() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$1Pzt69nQyruZilZ-eLll1aq0kwQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.hideDialog();
                }
            });
            this.mDynamicAppRecyclerView.setDialog(this.dialog).setAppIconFilter(new DynamicAppRecyclerView.AppIconFilter() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$mxqmUY0EJCYHlMf2SsOquMgRUhM
                @Override // com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.AppIconFilter
                public final boolean accept(LauncherActivityInfo launcherActivityInfo) {
                    return InitViewImpl.lambda$initView$16(launcherActivityInfo);
                }
            });
            this.mDynamicAppRecyclerView.setAppIconLoader(new DynamicAppRecyclerView.AppIconLoader() { // from class: com.hcn.auto.systemui.-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA
                @Override // com.hcn.widgets.dynamicrecyclerview.DynamicAppRecyclerView.AppIconLoader
                public final Drawable loadIcon(LauncherActivityInfo launcherActivityInfo) {
                    return InitViewImpl.lambda$initView$17(this.f$0, launcherActivityInfo);
                }
            });
            int layoutId = SkinCompatResources.getInstance().getSkinResId("app_icon", "layout");
            Drawable addDrawable = SkinCompatResources.getInstance().getDrawable("icon_add_selector");
            if (isNewBottomUI(this.mCurrentSkin) && layoutId != 0 && addDrawable != null) {
                DynamicAppRecyclerView dynamicAppRecyclerView = this.mDynamicAppRecyclerView;
                IconAdapter iconAdapter = new IconAdapter(layoutId);
                DynamicAppRecyclerView dynamicAppRecyclerView2 = this.mDynamicAppRecyclerView;
                dynamicAppRecyclerView.initAppIconRecycleView(iconAdapter, addDrawable, 0, "launcher_dynamic_app");
            } else {
                String configSkin = Utils.getSystemProperty("persist.sys.skin", "");
                if ("xt617".equals(configSkin) || "xt618".equals(configSkin)) {
                    DynamicAppRecyclerView dynamicAppRecyclerView3 = this.mDynamicAppRecyclerView;
                    IconAdapter iconAdapter2 = new IconAdapter(R.layout.xt617_item_app);
                    Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.xt617_icon_add_selector, this.mContext.getTheme());
                    DynamicAppRecyclerView dynamicAppRecyclerView4 = this.mDynamicAppRecyclerView;
                    dynamicAppRecyclerView3.initAppIconRecycleView(iconAdapter2, drawable, 0, "launcher_dynamic_app");
                }
            }
            this.mDynamicAppRecyclerView.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ boolean lambda$initView$14(InitViewImpl initViewImpl, View v) {
        if (initViewImpl.changeBottomBgThemeDialog()) {
            try {
                Intent intent = new Intent();
                intent.addFlags(268468224);
                intent.setClassName("com.hcn.themepicker", "com.hcn.themepicker.BottomBgThemeActivity");
                initViewImpl.mContext.startActivity(intent);
                return true;
            } catch (Exception e) {
                Log.e("InitViewImpl", "", e);
                return true;
            }
        }
        return true;
    }

    static /* synthetic */ boolean lambda$initView$16(LauncherActivityInfo launcherActivityInfo) {
        boolean bRet = !com.hcn.auto.theme.utils.IconThemeUtils.isHidden(launcherActivityInfo.getComponentName().getPackageName(), launcherActivityInfo.getComponentName().getClassName());
        return bRet;
    }

    public static /* synthetic */ Drawable lambda$initView$17(InitViewImpl initViewImpl, LauncherActivityInfo launcherActivityInfo) {
        Utils utils = initViewImpl.utils;
        Drawable drawable = Utils.getCarHomeIconFromTheme(initViewImpl.mContext, launcherActivityInfo.getComponentName(), "car_home_", SkinCompatResources.getInstance().getInteger("icon_size"), 0.65f, "car_home_icon_background");
        if (drawable == null) {
            Utils utils2 = initViewImpl.utils;
            Drawable drawable2 = Utils.getActivityIconFromTheme(initViewImpl.mContext, launcherActivityInfo.getComponentName());
            if ("xt617".equals(initViewImpl.mCurrentSkin) || "xt618".equals(initViewImpl.mCurrentSkin)) {
                Utils utils3 = initViewImpl.utils;
                drawable2 = Utils.getActivityIconFromTheme(initViewImpl.mContext, launcherActivityInfo.getComponentName(), "xt617_icon_themes", "xt617_bottom_icon_background");
            }
            if ("sa266".equals(initViewImpl.mCurrentSkin)) {
                Utils utils4 = initViewImpl.utils;
                return Utils.getActivityIconFromTheme(initViewImpl.mContext, launcherActivityInfo.getComponentName(), "sa266_icon_themes", "xt617_bottom_icon_background");
            }
            return drawable2;
        }
        return drawable;
    }

    public void initBtnView(String skinName) {
        if (isNewBottomUI(skinName)) {
            Log.e("InitViewImpl", "initBtnView----isNewBottomUI---" + skinName);
            this.mBtnAllApp = (ImageButton) findViewByName("btn_all_app");
            this.mBtnEq = (ImageButton) findViewByName("btn_eq");
            this.mBtnOffScreen = (ImageButton) findViewByName("btn_off_screen");
            this.mBtnBtMusic = (ImageButton) findViewByName("btn_bt_music");
            this.mBtnNavi = (ImageButton) findViewByName("btn_navi");
            this.mBtnMusic = (ImageButton) findViewByName("btn_music");
            this.mBtnRadio = (ImageButton) findViewByName("btn_radio");
            this.mTime = (TextView) findViewByName("tv_time");
            this.mDate = (TextView) findViewByName("tv_date");
            this.mWeek = (TextView) findViewByName("tv_week");
            this.mTvSoundVolume = (TextView) findViewByName("tv_sound_volume");
            this.mBtnMute = (ImageButton) findViewByName("btn_mute");
            this.mBtnSetting = (ImageButton) findViewByName("btn_settings");
            this.mBtnBt = (ImageButton) findViewByName("btn_bt");
            this.bottomBg = (RelativeLayout) findViewByName("navi_bottom");
            this.mBtnHome = (ImageButton) findViewByName("btn_home");
            this.mPlayImg = (ImageView) findViewByName("play_img");
            this.mMuteImg = (ImageView) findViewByName("mute_img");
            return;
        }
        Log.e("InitViewImpl", "initBtnView----notNewBottomUI---" + skinName);
        this.mBtnAllApp = (ImageButton) this.mView.findViewById(R.id.btn_all_app);
        this.mBtnEq = (ImageButton) this.mView.findViewById(R.id.btn_eq);
        this.mBtnOffScreen = (ImageButton) this.mView.findViewById(R.id.btn_off_screen);
        this.mBtnBtMusic = (ImageButton) this.mView.findViewById(R.id.btn_bt_music);
        this.mBtnNavi = (ImageButton) this.mView.findViewById(R.id.btn_navi);
        this.mBtnMusic = (ImageButton) this.mView.findViewById(R.id.btn_music);
        this.mBtnRadio = (ImageButton) this.mView.findViewById(R.id.btn_radio);
        this.mTime = (TextView) this.mView.findViewById(R.id.tv_time);
        this.mDate = (TextView) this.mView.findViewById(R.id.tv_date);
        this.mWeek = (TextView) this.mView.findViewById(R.id.tv_week);
        this.mTvSoundVolume = (TextView) this.mView.findViewById(R.id.tv_sound_volume);
        this.mBtnMute = (ImageButton) this.mView.findViewById(R.id.btn_mute);
        this.mBtnSetting = (ImageButton) this.mView.findViewById(R.id.btn_settings);
        this.mBtnBt = (ImageButton) this.mView.findViewById(R.id.btn_bt);
        this.bottomBg = (RelativeLayout) this.mView.findViewById(R.id.navi_bottom);
        this.mBtnHome = (ImageButton) this.mView.findViewById(R.id.btn_home);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDialog() {
        if (this.dialog != null) {
            this.dialog.hide();
        }
    }

    private Drawable resizeBitmapDrawable(Bitmap originalBitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        return new BitmapDrawable(resizedBitmap);
    }

    public ImageButton getBtnAllApp() {
        return this.mBtnAllApp;
    }

    public ImageButton getBtnNavi() {
        return this.mBtnNavi;
    }

    public ImageButton getBtnMusic() {
        return this.mBtnMusic;
    }

    public ImageButton getBtnRadio() {
        return this.mBtnRadio;
    }

    public TextView getTime() {
        return this.mTime;
    }

    public static void startCalendar(Context context) {
        if (context == null) {
            return;
        }
        if (Tools.isAppInstalled("com.google.android.calendar", context)) {
            Tools.startActivity(context, "com.google.android.calendar", "com.google.android.calendar.AllInOneCalendarActivity");
        } else if (Tools.isAppInstalled("com.calendar.hcncalendar", context)) {
            Tools.startActivity(context, "com.calendar.hcncalendar", "com.calendar.hcncalendar.CalendarActivity");
        } else {
            startTimeSetting(context);
        }
    }

    private void registerTimeReceiver() {
        if (this.timeReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.DATE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            this.timeReceiver = new BroadcastReceiver() { // from class: com.hcn.auto.systemui.InitViewImpl.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    InitViewImpl.this.onTimeChanged();
                }
            };
            this.mContext.registerReceiver(this.timeReceiver, intentFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        if (this.mTime != null) {
            String formatTime = null;
            if (Build.VERSION.SDK_INT >= 28) {
                formatTime = (String) this.mTime.getAccessibilityPaneTitle();
            }
            if (formatTime == null) {
                formatTime = (String) this.mTime.getContentDescription();
            }
            if (formatTime != null && DateFormat.is24HourFormat(this.mContext)) {
                formatTime = "HH:mm";
            }
            CharSequence value = formatTime != null ? DateFormat.format(formatTime, Calendar.getInstance().getTime()) : DateFormat.getTimeFormat(this.mContext).format(Calendar.getInstance().getTime());
            if (this.mTime instanceof TextView) {
                this.mTime.setText(value);
            } else if (this.mTime instanceof Button) {
                ((Button) this.mTime).setText(value);
            }
        }
        if (this.mDate != null) {
            String formatDate = null;
            if (Build.VERSION.SDK_INT >= 28) {
                formatDate = (String) this.mDate.getAccessibilityPaneTitle();
            }
            if (formatDate == null) {
                formatDate = (String) this.mDate.getContentDescription();
            }
            CharSequence value2 = formatDate != null ? DateFormat.format(formatDate, Calendar.getInstance().getTime()) : DateFormat.getMediumDateFormat(this.mContext).format(Calendar.getInstance().getTime());
            if (this.mDate instanceof TextView) {
                this.mDate.setText(value2);
            } else if (this.mDate instanceof Button) {
                ((Button) this.mDate).setText(value2);
            }
        }
        if (this.mWeek != null) {
            String formatWeek = null;
            if (Build.VERSION.SDK_INT >= 28) {
                formatWeek = (String) this.mWeek.getAccessibilityPaneTitle();
            }
            if (formatWeek == null) {
                formatWeek = (String) this.mWeek.getContentDescription();
            }
            CharSequence value3 = formatWeek != null ? DateFormat.format(formatWeek, Calendar.getInstance().getTime()) : DateFormat.format("E", Calendar.getInstance().getTime());
            if (this.mWeek instanceof TextView) {
                this.mWeek.setText(value3);
            } else if (this.mWeek instanceof Button) {
                ((Button) this.mWeek).setText(value3);
            }
        }
    }

    public static void startTimeSetting(Context context) {
        if (context == null) {
            return;
        }
        try {
            Intent it = new Intent();
            it.setAction("android.settings.DATE_SETTINGS");
            it.setFlags(268566528);
            context.startActivity(it);
        } catch (Exception e) {
            Log.e("InitViewImpl", "", e);
        }
    }

    public void unregisterTimeReceiver() {
        Log.d("InitViewImpl", "unregisterTimeReceiver");
        if (this.mContext != null && this.timeReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.timeReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onVolumeChanged(int stream, int volume, boolean mute) {
        int vol = volume;
        if (stream == 3 && mute) {
            vol = 0;
        }
        SkinCompatResources.getInstance().setupSkin(this.mResources, this.mPackageName);
        String value = String.format(Locale.ENGLISH, "%02d", Integer.valueOf(vol));
        if (this.mBtnMute != null) {
            if (volume != 0) {
                if (isNewBottomUI(this.mCurrentSkin) && findViewByName("btn_mute") != null) {
                    String resVolumeName = this.mCurrentSkin + "_icon_volume_selector";
                    Drawable resVolumeDraw = SkinCompatResources.getInstance().getDrawable(resVolumeName);
                    this.mBtnMute.setBackground(resVolumeDraw);
                } else {
                    this.mBtnMute.setBackgroundResource(R.drawable.xt646_btn_volume_selector);
                }
            } else if (isNewBottomUI(this.mCurrentSkin) && findViewByName("btn_mute") != null) {
                String resMuteName = this.mCurrentSkin + "_icon_mute_selector";
                Drawable resMuteDraw = SkinCompatResources.getInstance().getDrawable(resMuteName);
                this.mBtnMute.setBackground(resMuteDraw);
            } else {
                this.mBtnMute.setBackgroundResource(R.drawable.xt646_btn_mute_selector);
            }
        }
        if (this.mTvSoundVolume != null) {
            if (isBackCar()) {
                if (this.mIsReversing < 2) {
                    this.mIsReversing++;
                }
            } else if (!isBackCar() && this.mIsReversing > 0) {
                this.mIsReversing--;
            } else {
                this.mTvSoundVolume.setText(value);
            }
        }
        if (this.mMuteImg == null || this.mTvSoundVolume == null) {
            return;
        }
        if (volume != 0) {
            this.mMuteImg.setVisibility(8);
            this.mTvSoundVolume.setVisibility(0);
        } else {
            this.mMuteImg.setVisibility(0);
            this.mTvSoundVolume.setVisibility(8);
        }
    }

    public boolean isNewBottomUI(String skinName) {
        if (!SystemUIApplication.hasBottomBar()) {
            Log.e("InitViewImpl", "notNewBottomUI---false");
            return false;
        }
        String apkPath = Utils.getApkPath(this.mContext, "com.hcn.systemui.theme." + skinName);
        if (!TextUtils.isEmpty(apkPath)) {
            Log.e("InitViewImpl", "isNewBottomUI---true" + apkPath);
            return true;
        }
        Log.e("InitViewImpl", "notNewBottomUI---false");
        return false;
    }

    public View findViewByName(String name) {
        int id = 0;
        try {
            id = this.mResources.getIdentifier(name, "id", this.mPackageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id != 0) {
            return this.mView.findViewById(id);
        }
        return null;
    }

    private boolean changeBottomBgThemeDialog() {
        return "1".equals(Utils.getSystemProperty("change_bottomBg_theme", "0"));
    }

    public void updateBottomBgUIStyle(String uiStyle) {
        String resName;
        String uiStyle2;
        if (uiStyle.equals("night")) {
            resName = this.mCurrentSkin + "_bottom_bg_" + uiStyle;
        } else {
            resName = this.mCurrentSkin + "_bottom_bg";
        }
        Drawable drawable = SkinCompatResources.getInstance().getDrawable(resName);
        if (drawable != null) {
            this.bottomBg.setBackground(drawable);
            if (uiStyle.equals("night")) {
                uiStyle2 = "_night";
            } else {
                uiStyle2 = "";
            }
            if (this.mBtnAllApp != null) {
                String resAllAppsName = this.mCurrentSkin + "_icon_all_apps_selector" + uiStyle2;
                Drawable resAllAppsDraw = SkinCompatResources.getInstance().getDrawable(resAllAppsName);
                this.mBtnAllApp.setBackground(resAllAppsDraw);
            }
            if (this.mBtnNavi != null) {
                String resNaviName = this.mCurrentSkin + "_icon_navi_selector" + uiStyle2;
                Drawable resNaviDraw = SkinCompatResources.getInstance().getDrawable(resNaviName);
                this.mBtnNavi.setBackground(resNaviDraw);
            }
            if (this.mBtnMusic != null) {
                String resMusicName = this.mCurrentSkin + "_icon_music_selector" + uiStyle2;
                Drawable resMusicDraw = SkinCompatResources.getInstance().getDrawable(resMusicName);
                this.mBtnMusic.setBackground(resMusicDraw);
            }
            if (this.mBtnRadio != null) {
                String resRadioName = this.mCurrentSkin + "_icon_radio_selector" + uiStyle2;
                Drawable resRadioDraw = SkinCompatResources.getInstance().getDrawable(resRadioName);
                this.mBtnRadio.setBackground(resRadioDraw);
            }
            if (this.mTime != null) {
                if (uiStyle2.equals("_night")) {
                    this.mTime.setTextColor(SkinCompatResources.getInstance().getColor(this.mCurrentSkin + "_volume_text_color" + uiStyle2));
                    return;
                }
                this.mTime.setTextColor(SkinCompatResources.getInstance().getColor(this.mCurrentSkin + "_volume_text_color"));
            }
        }
    }

    private boolean isBackCar() {
        if (new CarStatus().getReversing() > 0) {
            return true;
        }
        return false;
    }
}
