package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.UserInfoController;

/* JADX INFO: loaded from: classes.dex */
public class LockIcon extends KeyguardAffordanceView implements UserInfoController.OnUserInfoChangedListener {
    private AccessibilityController mAccessibilityController;
    private int mDensity;
    private boolean mDeviceInteractive;
    private final Runnable mDrawOffTimeout;
    private boolean mHasFaceUnlockIcon;
    private boolean mHasFingerPrintIcon;
    private boolean mLastDeviceInteractive;
    private boolean mLastScreenOn;
    private int mLastState;
    private boolean mScreenOn;
    private boolean mTransientFpError;
    private TrustDrawable mTrustDrawable;
    private final UnlockMethodCache mUnlockMethodCache;
    private Drawable mUserAvatarIcon;

    public LockIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLastState = 0;
        this.mDrawOffTimeout = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$LockIcon$0dMARpPtuLHOj252cR_FbaJx3Kc
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.update(true);
            }
        };
        this.mTrustDrawable = new TrustDrawable(context);
        setBackground(this.mTrustDrawable);
        this.mUnlockMethodCache = UnlockMethodCache.getInstance(context);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (isShown()) {
            this.mTrustDrawable.start();
        } else {
            this.mTrustDrawable.stop();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTrustDrawable.stop();
    }

    @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
    public void onUserInfoChanged(String name, Drawable picture, String userAccount) {
        this.mUserAvatarIcon = picture;
        update();
    }

    public void setTransientFpError(boolean transientFpError) {
        this.mTransientFpError = transientFpError;
        update();
    }

    public void setDeviceInteractive(boolean deviceInteractive) {
        this.mDeviceInteractive = deviceInteractive;
        update();
    }

    public void setScreenOn(boolean screenOn) {
        this.mScreenOn = screenOn;
        update();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int density = newConfig.densityDpi;
        if (density != this.mDensity) {
            this.mDensity = density;
            this.mTrustDrawable.stop();
            this.mTrustDrawable = new TrustDrawable(getContext());
            setBackground(this.mTrustDrawable);
            update();
        }
    }

    public void update() {
        update(false);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x008a  */
    /* JADX WARN: Code duplicated, block: B:48:0x0091  */
    /* JADX WARN: Code duplicated, block: B:51:0x009d  */
    /* JADX WARN: Code duplicated, block: B:52:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:61:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:62:0x00da  */
    /* JADX WARN: Code duplicated, block: B:65:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:66:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:69:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:76:0x0110  */
    /* JADX WARN: Code duplicated, block: B:77:0x011f  */
    public void update(boolean force) {
        boolean trustHidden;
        Drawable icon;
        AnimatedVectorDrawable animation;
        int iconHeight;
        int iconWidth;
        int dimensionPixelSize;
        float f;
        boolean visible = isShown() && KeyguardUpdateMonitor.getInstance(this.mContext).isDeviceInteractive();
        if (visible) {
            this.mTrustDrawable.start();
        } else {
            this.mTrustDrawable.stop();
        }
        int state = getState();
        boolean anyFingerprintIcon = state == 3 || state == 4;
        this.mHasFaceUnlockIcon = state == 2;
        boolean useAdditionalPadding = anyFingerprintIcon;
        boolean trustHidden2 = anyFingerprintIcon;
        if (state != this.mLastState || this.mDeviceInteractive != this.mLastDeviceInteractive || this.mScreenOn != this.mLastScreenOn || force) {
            int iconAnimRes = getAnimationResForTransition(this.mLastState, state, this.mLastDeviceInteractive, this.mDeviceInteractive, this.mLastScreenOn, this.mScreenOn);
            boolean isAnim = iconAnimRes != -1;
            if (iconAnimRes == R.drawable.lockscreen_fingerprint_draw_off_animation) {
                anyFingerprintIcon = true;
                useAdditionalPadding = true;
                trustHidden = true;
            } else if (iconAnimRes == R.drawable.trusted_state_to_error_animation) {
                anyFingerprintIcon = true;
                useAdditionalPadding = false;
                trustHidden = true;
            } else {
                if (iconAnimRes == R.drawable.error_to_trustedstate_animation) {
                    anyFingerprintIcon = true;
                    useAdditionalPadding = false;
                    trustHidden = false;
                }
                if (isAnim) {
                    icon = this.mContext.getDrawable(iconAnimRes);
                } else {
                    icon = getIconForState(state, this.mScreenOn, this.mDeviceInteractive);
                }
                if (icon instanceof AnimatedVectorDrawable) {
                    animation = (AnimatedVectorDrawable) icon;
                } else {
                    animation = null;
                }
                iconHeight = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_icon_height);
                iconWidth = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_icon_width);
                if (!anyFingerprintIcon && (icon.getIntrinsicHeight() != iconHeight || icon.getIntrinsicWidth() != iconWidth)) {
                    icon = new IntrinsicSizeDrawable(icon, iconWidth, iconHeight);
                }
                if (useAdditionalPadding) {
                    dimensionPixelSize = 0;
                } else {
                    dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.fingerprint_icon_additional_padding);
                }
                setPaddingRelative(0, 0, 0, dimensionPixelSize);
                if (anyFingerprintIcon) {
                    f = 1.0f;
                } else {
                    f = 0.5f;
                }
                setRestingAlpha(f);
                setImageDrawable(icon, false);
                if (this.mHasFaceUnlockIcon) {
                    announceForAccessibility(getContext().getString(R.string.accessibility_scanning_face));
                }
                this.mHasFingerPrintIcon = anyFingerprintIcon;
                if (animation != null && isAnim) {
                    animation.forceAnimationOnUI();
                    animation.start();
                }
                if (iconAnimRes == R.drawable.lockscreen_fingerprint_draw_off_animation) {
                    removeCallbacks(this.mDrawOffTimeout);
                    postDelayed(this.mDrawOffTimeout, 800L);
                } else {
                    removeCallbacks(this.mDrawOffTimeout);
                }
                this.mLastState = state;
                this.mLastDeviceInteractive = this.mDeviceInteractive;
                this.mLastScreenOn = this.mScreenOn;
            }
            trustHidden2 = trustHidden;
            if (isAnim) {
                icon = this.mContext.getDrawable(iconAnimRes);
            } else {
                icon = getIconForState(state, this.mScreenOn, this.mDeviceInteractive);
            }
            if (icon instanceof AnimatedVectorDrawable) {
                animation = (AnimatedVectorDrawable) icon;
            } else {
                animation = null;
            }
            iconHeight = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_icon_height);
            iconWidth = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_icon_width);
            if (!anyFingerprintIcon) {
                icon = new IntrinsicSizeDrawable(icon, iconWidth, iconHeight);
            }
            if (useAdditionalPadding) {
                dimensionPixelSize = 0;
            } else {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.fingerprint_icon_additional_padding);
            }
            setPaddingRelative(0, 0, 0, dimensionPixelSize);
            if (anyFingerprintIcon) {
                f = 1.0f;
            } else {
                f = 0.5f;
            }
            setRestingAlpha(f);
            setImageDrawable(icon, false);
            if (this.mHasFaceUnlockIcon) {
                announceForAccessibility(getContext().getString(R.string.accessibility_scanning_face));
            }
            this.mHasFingerPrintIcon = anyFingerprintIcon;
            if (animation != null) {
                animation.forceAnimationOnUI();
                animation.start();
            }
            if (iconAnimRes == R.drawable.lockscreen_fingerprint_draw_off_animation) {
                removeCallbacks(this.mDrawOffTimeout);
                postDelayed(this.mDrawOffTimeout, 800L);
            } else {
                removeCallbacks(this.mDrawOffTimeout);
            }
            this.mLastState = state;
            this.mLastDeviceInteractive = this.mDeviceInteractive;
            this.mLastScreenOn = this.mScreenOn;
        }
        boolean trustManaged = this.mUnlockMethodCache.isTrustManaged() && !trustHidden2;
        this.mTrustDrawable.setTrustManaged(trustManaged);
        updateClickability();
    }

    private void updateClickability() {
        if (this.mAccessibilityController == null) {
            return;
        }
        boolean clickToUnlock = this.mAccessibilityController.isAccessibilityEnabled();
        boolean clickToForceLock = this.mUnlockMethodCache.isTrustManaged() && !clickToUnlock;
        boolean longClickToForceLock = this.mUnlockMethodCache.isTrustManaged() && !clickToForceLock;
        setClickable(clickToForceLock || clickToUnlock);
        setLongClickable(longClickToForceLock);
        setFocusable(this.mAccessibilityController.isAccessibilityEnabled());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.mHasFingerPrintIcon) {
            AccessibilityNodeInfo.AccessibilityAction unlock = new AccessibilityNodeInfo.AccessibilityAction(16, getContext().getString(R.string.accessibility_unlock_without_fingerprint));
            info.addAction(unlock);
            info.setHintText(getContext().getString(R.string.accessibility_waiting_for_fingerprint));
        } else if (this.mHasFaceUnlockIcon) {
            info.setClassName(LockIcon.class.getName());
            info.setContentDescription(getContext().getString(R.string.accessibility_scanning_face));
        }
    }

    public void setAccessibilityController(AccessibilityController accessibilityController) {
        this.mAccessibilityController = accessibilityController;
    }

    private Drawable getIconForState(int state, boolean screenOn, boolean deviceInteractive) {
        int iconRes;
        switch (state) {
            case 0:
                iconRes = R.drawable.ic_lock_24dp;
                break;
            case 1:
                if (this.mUnlockMethodCache.isTrustManaged() && this.mUnlockMethodCache.isTrusted() && this.mUserAvatarIcon != null) {
                    return this.mUserAvatarIcon;
                }
                iconRes = R.drawable.ic_lock_open_24dp;
                break;
                break;
            case 2:
                iconRes = R.drawable.ic_face_unlock;
                break;
            case 3:
                if (screenOn && deviceInteractive) {
                    iconRes = R.drawable.ic_fingerprint;
                } else {
                    iconRes = R.drawable.lockscreen_fingerprint_draw_on_animation;
                }
                break;
            case 4:
                iconRes = R.drawable.ic_fingerprint_error;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this.mContext.getDrawable(iconRes);
    }

    private int getAnimationResForTransition(int oldState, int newState, boolean oldDeviceInteractive, boolean deviceInteractive, boolean oldScreenOn, boolean screenOn) {
        if (oldState == 3 && newState == 4) {
            return R.drawable.lockscreen_fingerprint_fp_to_error_state_animation;
        }
        if (oldState == 1 && newState == 4) {
            return R.drawable.trusted_state_to_error_animation;
        }
        if (oldState == 4 && newState == 1) {
            return R.drawable.error_to_trustedstate_animation;
        }
        if (oldState == 4 && newState == 3) {
            return R.drawable.lockscreen_fingerprint_error_state_to_fp_animation;
        }
        if (oldState == 3 && newState == 1 && !this.mUnlockMethodCache.isTrusted()) {
            return R.drawable.lockscreen_fingerprint_draw_off_animation;
        }
        if (newState != 3) {
            return -1;
        }
        if (!oldScreenOn && screenOn && deviceInteractive) {
            return R.drawable.lockscreen_fingerprint_draw_on_animation;
        }
        if (screenOn && !oldDeviceInteractive && deviceInteractive) {
            return R.drawable.lockscreen_fingerprint_draw_on_animation;
        }
        return -1;
    }

    private int getState() {
        KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        boolean fingerprintRunning = updateMonitor.isFingerprintDetectionRunning();
        boolean unlockingAllowed = updateMonitor.isUnlockingWithFingerprintAllowed();
        if (this.mTransientFpError) {
            return 4;
        }
        if (this.mUnlockMethodCache.canSkipBouncer()) {
            return 1;
        }
        if (this.mUnlockMethodCache.isFaceUnlockRunning()) {
            return 2;
        }
        if (fingerprintRunning && unlockingAllowed) {
            return 3;
        }
        return 0;
    }

    private static class IntrinsicSizeDrawable extends InsetDrawable {
        private final int mIntrinsicHeight;
        private final int mIntrinsicWidth;

        public IntrinsicSizeDrawable(Drawable drawable, int intrinsicWidth, int intrinsicHeight) {
            super(drawable, 0);
            this.mIntrinsicWidth = intrinsicWidth;
            this.mIntrinsicHeight = intrinsicHeight;
        }

        @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mIntrinsicWidth;
        }

        @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mIntrinsicHeight;
        }
    }
}
