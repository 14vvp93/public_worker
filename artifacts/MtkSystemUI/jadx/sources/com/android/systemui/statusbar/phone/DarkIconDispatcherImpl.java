package com.android.systemui.statusbar.phone;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public class DarkIconDispatcherImpl implements DarkIconDispatcher {
    private float mDarkIntensity;
    private int mDarkModeIconColorSingleTone;
    private int mLightModeIconColorSingleTone;
    private final LightBarTransitionsController mTransitionsController;
    private final Rect mTintArea = new Rect();
    private final ArrayMap<Object, DarkIconDispatcher.DarkReceiver> mReceivers = new ArrayMap<>();
    private int mIconTint = -1;

    public DarkIconDispatcherImpl(Context context) {
        this.mDarkModeIconColorSingleTone = context.getColor(R.color.dark_mode_icon_color_single_tone);
        this.mLightModeIconColorSingleTone = context.getColor(R.color.light_mode_icon_color_single_tone);
        this.mTransitionsController = new LightBarTransitionsController(context, new LightBarTransitionsController.DarkIntensityApplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$DarkIconDispatcherImpl$oA-3cfsaKFqoqAQl8JID377YBJY
            @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
            public final void applyDarkIntensity(float f) {
                this.f$0.setIconTintInternal(f);
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public LightBarTransitionsController getTransitionsController() {
        return this.mTransitionsController;
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void addDarkReceiver(DarkIconDispatcher.DarkReceiver receiver) {
        this.mReceivers.put(receiver, receiver);
        receiver.onDarkChanged(this.mTintArea, this.mDarkIntensity, this.mIconTint);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void addDarkReceiver(final ImageView imageView) {
        DarkIconDispatcher.DarkReceiver receiver = new DarkIconDispatcher.DarkReceiver() { // from class: com.android.systemui.statusbar.phone.DarkIconDispatcherImpl.1
            @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
            public void onDarkChanged(Rect area, float darkIntensity, int tint) {
                if (imageView != null) {
                    imageView.setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(DarkIconDispatcherImpl.this.mTintArea, imageView, DarkIconDispatcherImpl.this.mIconTint)));
                    imageView.setColorFilter(DarkIconDispatcherImpl.this.mIconTint);
                }
            }
        };
        this.mReceivers.put(imageView, receiver);
        receiver.onDarkChanged(this.mTintArea, this.mDarkIntensity, this.mIconTint);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void removeDarkReceiver(DarkIconDispatcher.DarkReceiver object) {
        this.mReceivers.remove(object);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void removeDarkReceiver(ImageView object) {
        this.mReceivers.remove(object);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void applyDark(DarkIconDispatcher.DarkReceiver object) {
        this.mReceivers.get(object).onDarkChanged(this.mTintArea, this.mDarkIntensity, this.mIconTint);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher
    public void setIconsDarkArea(Rect darkArea) {
        if (darkArea == null && this.mTintArea.isEmpty()) {
            return;
        }
        if (darkArea == null) {
            this.mTintArea.setEmpty();
        } else {
            this.mTintArea.set(darkArea);
        }
        applyIconTint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIconTintInternal(float darkIntensity) {
        this.mDarkIntensity = darkIntensity;
        this.mIconTint = ((Integer) ArgbEvaluator.getInstance().evaluate(darkIntensity, Integer.valueOf(this.mLightModeIconColorSingleTone), Integer.valueOf(this.mDarkModeIconColorSingleTone))).intValue();
        applyIconTint();
    }

    private void applyIconTint() {
        for (int i = 0; i < this.mReceivers.size(); i++) {
            this.mReceivers.valueAt(i).onDarkChanged(this.mTintArea, this.mDarkIntensity, this.mIconTint);
        }
    }
}
