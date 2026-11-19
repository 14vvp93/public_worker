package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarWifiView extends FrameLayout implements StatusIconDisplayable, DarkIconDispatcher.DarkReceiver {
    private View mAirplaneSpacer;
    private ContextThemeWrapper mDarkContext;
    private float mDarkIntensity;
    private StatusBarIconView mDotView;
    private ImageView mIn;
    private View mInoutContainer;
    private ContextThemeWrapper mLightContext;
    private ImageView mOut;
    private View mSignalSpacer;
    private String mSlot;
    private StatusBarSignalPolicy.WifiIconState mState;
    private int mVisibleState;
    private LinearLayout mWifiGroup;
    private ImageView mWifiIcon;

    public static StatusBarWifiView fromContext(Context context, String slot) {
        LayoutInflater inflater = LayoutInflater.from(context);
        StatusBarWifiView v = (StatusBarWifiView) inflater.inflate(R.layout.status_bar_wifi_group, (ViewGroup) null);
        v.setSlot(slot);
        v.init();
        v.setVisibleState(0);
        return v;
    }

    public StatusBarWifiView(Context context) {
        super(context);
        this.mDarkIntensity = 0.0f;
        this.mVisibleState = -1;
    }

    public StatusBarWifiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDarkIntensity = 0.0f;
        this.mVisibleState = -1;
    }

    public StatusBarWifiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDarkIntensity = 0.0f;
        this.mVisibleState = -1;
    }

    public StatusBarWifiView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDarkIntensity = 0.0f;
        this.mVisibleState = -1;
    }

    public void setSlot(String slot) {
        this.mSlot = slot;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setStaticDrawableColor(int color) {
        ColorStateList list = ColorStateList.valueOf(color);
        this.mWifiIcon.setImageTintList(list);
        this.mIn.setImageTintList(list);
        this.mOut.setImageTintList(list);
        this.mDotView.setDecorColor(color);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setDecorColor(int color) {
        this.mDotView.setDecorColor(color);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public String getSlot() {
        return this.mSlot;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public boolean isIconVisible() {
        return this.mState != null && this.mState.visible;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setVisibleState(int state) {
        if (state == this.mVisibleState) {
        }
        this.mVisibleState = state;
        switch (state) {
            case 0:
                this.mWifiGroup.setVisibility(0);
                this.mDotView.setVisibility(8);
                break;
            case 1:
                this.mWifiGroup.setVisibility(8);
                this.mDotView.setVisibility(0);
                break;
            default:
                this.mWifiGroup.setVisibility(8);
                this.mDotView.setVisibility(8);
                break;
        }
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public int getVisibleState() {
        return this.mVisibleState;
    }

    @Override // android.view.View
    public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        outRect.left = (int) (outRect.left + translationX);
        outRect.right = (int) (outRect.right + translationX);
        outRect.top = (int) (outRect.top + translationY);
        outRect.bottom = (int) (outRect.bottom + translationY);
    }

    private void init() {
        int dualToneLightTheme = Utils.getThemeAttr(this.mContext, R.attr.lightIconTheme);
        int dualToneDarkTheme = Utils.getThemeAttr(this.mContext, R.attr.darkIconTheme);
        this.mLightContext = new ContextThemeWrapper(this.mContext, dualToneLightTheme);
        this.mDarkContext = new ContextThemeWrapper(this.mContext, dualToneDarkTheme);
        this.mWifiGroup = (LinearLayout) findViewById(R.id.wifi_group);
        this.mWifiIcon = (ImageView) findViewById(R.id.wifi_signal);
        this.mIn = (ImageView) findViewById(R.id.wifi_in);
        this.mOut = (ImageView) findViewById(R.id.wifi_out);
        this.mSignalSpacer = findViewById(R.id.wifi_signal_spacer);
        this.mAirplaneSpacer = findViewById(R.id.wifi_airplane_spacer);
        this.mInoutContainer = findViewById(R.id.inout_container);
        initDotView();
    }

    private void initDotView() {
        this.mDotView = new StatusBarIconView(this.mContext, this.mSlot, null);
        this.mDotView.setVisibleState(1);
        int width = this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, width);
        lp.gravity = 8388627;
        addView(this.mDotView, lp);
    }

    public void applyWifiState(StatusBarSignalPolicy.WifiIconState state) {
        if (state == null) {
            setVisibility(8);
            this.mState = null;
            return;
        }
        if (this.mState == null) {
            this.mState = state.copy();
            initViewState();
        }
        if (!this.mState.equals(state)) {
            updateState(state.copy());
        }
    }

    private void updateState(StatusBarSignalPolicy.WifiIconState state) {
        setContentDescription(state.contentDescription);
        if (this.mState.resId != state.resId && state.resId >= 0) {
            NeutralGoodDrawable drawable = NeutralGoodDrawable.create(this.mLightContext, this.mDarkContext, state.resId);
            drawable.setDarkIntensity(this.mDarkIntensity);
            this.mWifiIcon.setImageDrawable(drawable);
        }
        this.mIn.setVisibility(state.activityIn ? 0 : 8);
        this.mOut.setVisibility(state.activityOut ? 0 : 8);
        this.mInoutContainer.setVisibility((state.activityIn || state.activityOut) ? 0 : 8);
        this.mAirplaneSpacer.setVisibility(state.airplaneSpacerVisible ? 0 : 8);
        this.mSignalSpacer.setVisibility(state.signalSpacerVisible ? 0 : 8);
        if (this.mState.visible != state.visible) {
            setVisibility(state.visible ? 0 : 8);
        }
        this.mState = state;
    }

    private void initViewState() {
        setContentDescription(this.mState.contentDescription);
        if (this.mState.resId >= 0) {
            NeutralGoodDrawable drawable = NeutralGoodDrawable.create(this.mLightContext, this.mDarkContext, this.mState.resId);
            drawable.setDarkIntensity(this.mDarkIntensity);
            this.mWifiIcon.setImageDrawable(drawable);
        }
        this.mIn.setVisibility(this.mState.activityIn ? 0 : 8);
        this.mOut.setVisibility(this.mState.activityOut ? 0 : 8);
        this.mInoutContainer.setVisibility((this.mState.activityIn || this.mState.activityOut) ? 0 : 8);
        this.mAirplaneSpacer.setVisibility(this.mState.airplaneSpacerVisible ? 0 : 8);
        this.mSignalSpacer.setVisibility(this.mState.signalSpacerVisible ? 0 : 8);
        setVisibility(this.mState.visible ? 0 : 8);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        if (!DarkIconDispatcher.isInArea(area, this)) {
            darkIntensity = 0.0f;
        }
        this.mDarkIntensity = darkIntensity;
        Drawable d = this.mWifiIcon.getDrawable();
        if (d instanceof NeutralGoodDrawable) {
            ((NeutralGoodDrawable) d).setDarkIntensity(darkIntensity);
        }
        this.mIn.setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(area, this, tint)));
        this.mOut.setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(area, this, tint)));
        this.mDotView.setDecorColor(tint);
        this.mDotView.setIconColor(tint, false);
    }

    @Override // android.view.View
    public String toString() {
        return "StatusBarWifiView(slot=" + this.mSlot + " state=" + this.mState + ")";
    }
}
