package com.android.systemui.statusbar.phone;

import android.graphics.Color;
import android.os.Trace;
import android.util.MathUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.ScrimView;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'KEYGUARD' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: classes.dex */
public class ScrimState {
    private static final /* synthetic */ ScrimState[] $VALUES;
    public static final ScrimState AOD;
    public static final ScrimState BOUNCER;
    public static final ScrimState BOUNCER_SCRIMMED;
    public static final ScrimState BRIGHTNESS_MIRROR;
    public static final ScrimState KEYGUARD;
    public static final ScrimState PULSING;
    public static final ScrimState UNINITIALIZED = new ScrimState("UNINITIALIZED", 0, -1);
    public static final ScrimState UNLOCKED;
    boolean mAnimateChange;
    long mAnimationDuration;
    float mAodFrontScrimAlpha;
    boolean mBlankScreen;
    float mCurrentBehindAlpha;
    int mCurrentBehindTint;
    float mCurrentInFrontAlpha;
    int mCurrentInFrontTint;
    boolean mDisplayRequiresBlanking;
    DozeParameters mDozeParameters;
    int mIndex;
    KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    ScrimView mScrimBehind;
    float mScrimBehindAlphaKeyguard;
    ScrimView mScrimInFront;
    boolean mWallpaperSupportsAmbientMode;

    public static ScrimState valueOf(String name) {
        return (ScrimState) Enum.valueOf(ScrimState.class, name);
    }

    public static ScrimState[] values() {
        return (ScrimState[]) $VALUES.clone();
    }

    static {
        int i = 1;
        KEYGUARD = new ScrimState("KEYGUARD", i, 0) { // from class: com.android.systemui.statusbar.phone.ScrimState.1
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mBlankScreen = false;
                if (previousState == ScrimState.AOD) {
                    this.mAnimationDuration = 500L;
                    if (this.mDisplayRequiresBlanking) {
                        this.mBlankScreen = true;
                    }
                } else {
                    this.mAnimationDuration = 220L;
                }
                this.mCurrentBehindAlpha = this.mScrimBehindAlphaKeyguard;
                this.mCurrentInFrontAlpha = 0.0f;
            }

            @Override // com.android.systemui.statusbar.phone.ScrimState
            public float getBehindAlpha(float busynessFactor) {
                return MathUtils.map(0.0f, 1.0f, this.mScrimBehindAlphaKeyguard, 0.7f, busynessFactor);
            }
        };
        int i2 = 2;
        BOUNCER = new ScrimState("BOUNCER", i2, i) { // from class: com.android.systemui.statusbar.phone.ScrimState.2
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mCurrentBehindAlpha = 0.7f;
                this.mCurrentInFrontAlpha = 0.0f;
            }
        };
        int i3 = 3;
        BOUNCER_SCRIMMED = new ScrimState("BOUNCER_SCRIMMED", i3, i2) { // from class: com.android.systemui.statusbar.phone.ScrimState.3
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mCurrentBehindAlpha = 0.0f;
                this.mCurrentInFrontAlpha = 0.7f;
            }
        };
        int i4 = 4;
        BRIGHTNESS_MIRROR = new ScrimState("BRIGHTNESS_MIRROR", i4, i3) { // from class: com.android.systemui.statusbar.phone.ScrimState.4
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mCurrentBehindAlpha = 0.0f;
                this.mCurrentInFrontAlpha = 0.0f;
            }
        };
        int i5 = 5;
        AOD = new ScrimState("AOD", i5, i4) { // from class: com.android.systemui.statusbar.phone.ScrimState.5
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                boolean alwaysOnEnabled = this.mDozeParameters.getAlwaysOn();
                this.mBlankScreen = this.mDisplayRequiresBlanking;
                this.mCurrentBehindAlpha = (!this.mWallpaperSupportsAmbientMode || this.mKeyguardUpdateMonitor.hasLockscreenWallpaper()) ? 1.0f : 0.0f;
                this.mCurrentInFrontAlpha = alwaysOnEnabled ? this.mAodFrontScrimAlpha : 1.0f;
                this.mCurrentInFrontTint = -16777216;
                this.mCurrentBehindTint = -16777216;
                this.mAnimationDuration = 1000L;
                this.mAnimateChange = this.mDozeParameters.shouldControlScreenOff();
            }

            @Override // com.android.systemui.statusbar.phone.ScrimState
            public boolean isLowPowerState() {
                return true;
            }
        };
        int i6 = 6;
        PULSING = new ScrimState("PULSING", i6, i5) { // from class: com.android.systemui.statusbar.phone.ScrimState.6
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mCurrentInFrontAlpha = 0.0f;
                this.mCurrentInFrontTint = -16777216;
                this.mCurrentBehindAlpha = (!this.mWallpaperSupportsAmbientMode || this.mKeyguardUpdateMonitor.hasLockscreenWallpaper()) ? 1.0f : 0.0f;
                this.mCurrentBehindTint = -16777216;
                this.mBlankScreen = this.mDisplayRequiresBlanking;
            }
        };
        UNLOCKED = new ScrimState("UNLOCKED", 7, i6) { // from class: com.android.systemui.statusbar.phone.ScrimState.7
            @Override // com.android.systemui.statusbar.phone.ScrimState
            public void prepare(ScrimState previousState) {
                this.mCurrentBehindAlpha = 0.0f;
                this.mCurrentInFrontAlpha = 0.0f;
                this.mAnimationDuration = 300L;
                if (previousState == ScrimState.AOD || previousState == ScrimState.PULSING) {
                    updateScrimColor(this.mScrimInFront, 1.0f, -16777216);
                    updateScrimColor(this.mScrimBehind, 1.0f, -16777216);
                    this.mCurrentInFrontTint = -16777216;
                    this.mCurrentBehindTint = -16777216;
                    this.mBlankScreen = true;
                    return;
                }
                this.mCurrentInFrontTint = 0;
                this.mCurrentBehindTint = 0;
                this.mBlankScreen = false;
            }
        };
        $VALUES = new ScrimState[]{UNINITIALIZED, KEYGUARD, BOUNCER, BOUNCER_SCRIMMED, BRIGHTNESS_MIRROR, AOD, PULSING, UNLOCKED};
    }

    private ScrimState(String str, int i, int index) {
        super(str, i);
        this.mBlankScreen = false;
        this.mAnimationDuration = 220L;
        this.mCurrentInFrontTint = 0;
        this.mCurrentBehindTint = 0;
        this.mAnimateChange = true;
        this.mIndex = index;
    }

    public void init(ScrimView scrimInFront, ScrimView scrimBehind, DozeParameters dozeParameters) {
        this.mScrimInFront = scrimInFront;
        this.mScrimBehind = scrimBehind;
        this.mDozeParameters = dozeParameters;
        this.mDisplayRequiresBlanking = dozeParameters.getDisplayNeedsBlanking();
        this.mKeyguardUpdateMonitor = KeyguardUpdateMonitor.getInstance(scrimInFront.getContext());
    }

    public void prepare(ScrimState previousState) {
    }

    public int getIndex() {
        return this.mIndex;
    }

    public float getFrontAlpha() {
        return this.mCurrentInFrontAlpha;
    }

    public float getBehindAlpha(float busyness) {
        return this.mCurrentBehindAlpha;
    }

    public int getFrontTint() {
        return this.mCurrentInFrontTint;
    }

    public int getBehindTint() {
        return this.mCurrentBehindTint;
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public boolean getBlanksScreen() {
        return this.mBlankScreen;
    }

    public void updateScrimColor(ScrimView scrim, float alpha, int tint) {
        Trace.traceCounter(4096L, scrim == this.mScrimInFront ? "front_scrim_alpha" : "back_scrim_alpha", (int) (255.0f * alpha));
        Trace.traceCounter(4096L, scrim == this.mScrimInFront ? "front_scrim_tint" : "back_scrim_tint", Color.alpha(tint));
        scrim.setTint(tint);
        scrim.setViewAlpha(alpha);
    }

    public boolean getAnimateChange() {
        return this.mAnimateChange;
    }

    public void setAodFrontScrimAlpha(float aodFrontScrimAlpha) {
        this.mAodFrontScrimAlpha = aodFrontScrimAlpha;
    }

    public void setScrimBehindAlphaKeyguard(float scrimBehindAlphaKeyguard) {
        this.mScrimBehindAlphaKeyguard = scrimBehindAlphaKeyguard;
    }

    public void setWallpaperSupportsAmbientMode(boolean wallpaperSupportsAmbientMode) {
        this.mWallpaperSupportsAmbientMode = wallpaperSupportsAmbientMode;
    }

    public boolean isLowPowerState() {
        return false;
    }
}
