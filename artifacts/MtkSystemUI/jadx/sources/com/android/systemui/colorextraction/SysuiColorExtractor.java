package com.android.systemui.colorextraction;

import android.app.WallpaperColors;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dumpable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class SysuiColorExtractor extends ColorExtractor implements Dumpable {
    private boolean mMediaBackdropVisible;
    private boolean mWallpaperVisible;
    private final ColorExtractor.GradientColors mWpHiddenColors;

    public SysuiColorExtractor(Context context) {
        this(context, new Tonal(context), true);
    }

    @VisibleForTesting
    public SysuiColorExtractor(Context context, ExtractionType type, boolean registerVisibility) {
        super(context, type);
        this.mWpHiddenColors = new ColorExtractor.GradientColors();
    }

    private void updateDefaultGradients(WallpaperColors colors) {
        Tonal.applyFallback(colors, this.mWpHiddenColors);
    }

    public void onColorsChanged(WallpaperColors colors, int which, int userId) {
        if (userId != KeyguardUpdateMonitor.getCurrentUser()) {
            return;
        }
        super.onColorsChanged(colors, which);
        if ((which & 1) != 0) {
            updateDefaultGradients(colors);
        }
    }

    @VisibleForTesting
    ColorExtractor.GradientColors getFallbackColors() {
        return this.mWpHiddenColors;
    }

    public ColorExtractor.GradientColors getColors(int which) {
        return getColors(which, 1);
    }

    public ColorExtractor.GradientColors getColors(int which, int type) {
        return getColors(which, type, false);
    }

    public ColorExtractor.GradientColors getColors(int which, boolean ignoreWallpaperVisibility) {
        return getColors(which, 0, ignoreWallpaperVisibility);
    }

    public ColorExtractor.GradientColors getColors(int which, int type, boolean ignoreWallpaperVisibility) {
        if (which == 1) {
            if (this.mWallpaperVisible || ignoreWallpaperVisibility) {
                return super.getColors(which, type);
            }
            return this.mWpHiddenColors;
        }
        if (this.mMediaBackdropVisible) {
            return this.mWpHiddenColors;
        }
        return super.getColors(which, type);
    }

    @VisibleForTesting
    void setWallpaperVisible(boolean visible) {
        if (this.mWallpaperVisible != visible) {
            this.mWallpaperVisible = visible;
            triggerColorsChanged(1);
        }
    }

    public void setMediaBackdropVisible(boolean visible) {
        if (this.mMediaBackdropVisible != visible) {
            this.mMediaBackdropVisible = visible;
            triggerColorsChanged(2);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("SysuiColorExtractor:");
        pw.println("  Current wallpaper colors:");
        pw.println("    system: " + this.mSystemColors);
        pw.println("    lock: " + this.mLockColors);
        ColorExtractor.GradientColors[] system = (ColorExtractor.GradientColors[]) this.mGradientColors.get(1);
        ColorExtractor.GradientColors[] lock = (ColorExtractor.GradientColors[]) this.mGradientColors.get(2);
        pw.println("  Gradients:");
        pw.println("    system: " + Arrays.toString(system));
        pw.println("    lock: " + Arrays.toString(lock));
        pw.println("  Default scrim: " + this.mWpHiddenColors);
    }
}
