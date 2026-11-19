package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v7.graphics.Palette;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediaNotificationProcessor {
    private Palette.Filter mBlackWhiteFilter;
    private final ImageGradientColorizer mColorizer;
    private final Context mContext;
    private float[] mFilteredBackgroundHsl;
    private final Context mPackageContext;

    public static /* synthetic */ boolean lambda$new$0(MediaNotificationProcessor mediaNotificationProcessor, int rgb, float[] hsl) {
        return !mediaNotificationProcessor.isWhiteOrBlack(hsl);
    }

    public MediaNotificationProcessor(Context context, Context packageContext) {
        this(context, packageContext, new ImageGradientColorizer());
    }

    MediaNotificationProcessor(Context context, Context packageContext, ImageGradientColorizer colorizer) {
        this.mFilteredBackgroundHsl = null;
        this.mBlackWhiteFilter = new Palette.Filter() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$MediaNotificationProcessor$FQ2jqCN-nkK3wF0AhHJdzMEAIb4
            @Override // android.support.v7.graphics.Palette.Filter
            public final boolean isAllowed(int i, float[] fArr) {
                return MediaNotificationProcessor.lambda$new$0(this.f$0, i, fArr);
            }
        };
        this.mContext = context;
        this.mPackageContext = packageContext;
        this.mColorizer = colorizer;
    }

    public void processNotification(Notification notification, Notification.Builder builder) {
        int backgroundColor;
        Icon largeIcon = notification.getLargeIcon();
        if (largeIcon != null) {
            builder.setRebuildStyledRemoteViews(true);
            Drawable drawable = largeIcon.loadDrawable(this.mPackageContext);
            if (notification.isColorizedMedia()) {
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                int area = width * height;
                if (area > 22500) {
                    double factor = Math.sqrt(22500.0f / area);
                    width = (int) (((double) width) * factor);
                    height = (int) (((double) height) * factor);
                }
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, width, height);
                drawable.draw(canvas);
                Palette.Builder paletteBuilder = Palette.from(bitmap).setRegion(0, 0, bitmap.getWidth() / 2, bitmap.getHeight()).clearFilters().resizeBitmapArea(22500);
                Palette palette = paletteBuilder.generate();
                backgroundColor = findBackgroundColorAndFilter(palette);
                paletteBuilder.setRegion((int) (bitmap.getWidth() * 0.4f), 0, bitmap.getWidth(), bitmap.getHeight());
                if (this.mFilteredBackgroundHsl != null) {
                    paletteBuilder.addFilter(new Palette.Filter() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$MediaNotificationProcessor$G1QdSAb4RmghAdo10Gv3JWJ8lmw
                        @Override // android.support.v7.graphics.Palette.Filter
                        public final boolean isAllowed(int i, float[] fArr) {
                            return MediaNotificationProcessor.lambda$processNotification$1(this.f$0, i, fArr);
                        }
                    });
                }
                paletteBuilder.addFilter(this.mBlackWhiteFilter);
                Palette palette2 = paletteBuilder.generate();
                int foregroundColor = selectForegroundColor(backgroundColor, palette2);
                builder.setColorPalette(backgroundColor, foregroundColor);
            } else {
                backgroundColor = this.mContext.getColor(R.color.notification_material_background_color);
            }
            Bitmap colorized = this.mColorizer.colorize(drawable, backgroundColor, this.mContext.getResources().getConfiguration().getLayoutDirection() == 1);
            builder.setLargeIcon(Icon.createWithBitmap(colorized));
        }
    }

    public static /* synthetic */ boolean lambda$processNotification$1(MediaNotificationProcessor mediaNotificationProcessor, int rgb, float[] hsl) {
        float diff = Math.abs(hsl[0] - mediaNotificationProcessor.mFilteredBackgroundHsl[0]);
        return diff > 10.0f && diff < 350.0f;
    }

    private int selectForegroundColor(int backgroundColor, Palette palette) {
        if (NotificationColorUtil.isColorLight(backgroundColor)) {
            return selectForegroundColorForSwatches(palette.getDarkVibrantSwatch(), palette.getVibrantSwatch(), palette.getDarkMutedSwatch(), palette.getMutedSwatch(), palette.getDominantSwatch(), -16777216);
        }
        return selectForegroundColorForSwatches(palette.getLightVibrantSwatch(), palette.getVibrantSwatch(), palette.getLightMutedSwatch(), palette.getMutedSwatch(), palette.getDominantSwatch(), -1);
    }

    private int selectForegroundColorForSwatches(Palette.Swatch moreVibrant, Palette.Swatch vibrant, Palette.Swatch moreMutedSwatch, Palette.Swatch mutedSwatch, Palette.Swatch dominantSwatch, int fallbackColor) {
        Palette.Swatch coloredCandidate = selectVibrantCandidate(moreVibrant, vibrant);
        if (coloredCandidate == null) {
            coloredCandidate = selectMutedCandidate(mutedSwatch, moreMutedSwatch);
        }
        if (coloredCandidate != null) {
            if (dominantSwatch == coloredCandidate) {
                return coloredCandidate.getRgb();
            }
            if (coloredCandidate.getPopulation() / dominantSwatch.getPopulation() < 0.01f && dominantSwatch.getHsl()[1] > 0.19f) {
                return dominantSwatch.getRgb();
            }
            return coloredCandidate.getRgb();
        }
        if (hasEnoughPopulation(dominantSwatch)) {
            return dominantSwatch.getRgb();
        }
        return fallbackColor;
    }

    private Palette.Swatch selectMutedCandidate(Palette.Swatch first, Palette.Swatch second) {
        boolean firstValid = hasEnoughPopulation(first);
        boolean secondValid = hasEnoughPopulation(second);
        if (firstValid && secondValid) {
            float firstSaturation = first.getHsl()[1];
            float secondSaturation = second.getHsl()[1];
            float populationFraction = first.getPopulation() / second.getPopulation();
            if (firstSaturation * populationFraction > secondSaturation) {
                return first;
            }
            return second;
        }
        if (firstValid) {
            return first;
        }
        if (secondValid) {
            return second;
        }
        return null;
    }

    private Palette.Swatch selectVibrantCandidate(Palette.Swatch first, Palette.Swatch second) {
        boolean firstValid = hasEnoughPopulation(first);
        boolean secondValid = hasEnoughPopulation(second);
        if (firstValid && secondValid) {
            int firstPopulation = first.getPopulation();
            int secondPopulation = second.getPopulation();
            if (firstPopulation / secondPopulation < 1.0f) {
                return second;
            }
            return first;
        }
        if (firstValid) {
            return first;
        }
        if (secondValid) {
            return second;
        }
        return null;
    }

    private boolean hasEnoughPopulation(Palette.Swatch swatch) {
        return swatch != null && ((double) (((float) swatch.getPopulation()) / 22500.0f)) > 0.002d;
    }

    private int findBackgroundColorAndFilter(Palette palette) {
        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
        if (dominantSwatch == null) {
            this.mFilteredBackgroundHsl = null;
            return -1;
        }
        if (!isWhiteOrBlack(dominantSwatch.getHsl())) {
            this.mFilteredBackgroundHsl = dominantSwatch.getHsl();
            return dominantSwatch.getRgb();
        }
        List<Palette.Swatch> swatches = palette.getSwatches();
        float highestNonWhitePopulation = -1.0f;
        Palette.Swatch second = null;
        for (Palette.Swatch swatch : swatches) {
            if (swatch != dominantSwatch && swatch.getPopulation() > highestNonWhitePopulation && !isWhiteOrBlack(swatch.getHsl())) {
                second = swatch;
                highestNonWhitePopulation = swatch.getPopulation();
            }
        }
        if (second == null) {
            this.mFilteredBackgroundHsl = null;
            return dominantSwatch.getRgb();
        }
        if (dominantSwatch.getPopulation() / highestNonWhitePopulation > 2.5f) {
            this.mFilteredBackgroundHsl = null;
            return dominantSwatch.getRgb();
        }
        this.mFilteredBackgroundHsl = second.getHsl();
        return second.getRgb();
    }

    private boolean isWhiteOrBlack(float[] hsl) {
        return isBlack(hsl) || isWhite(hsl);
    }

    private boolean isBlack(float[] hslColor) {
        return hslColor[2] <= 0.08f;
    }

    private boolean isWhite(float[] hslColor) {
        return hslColor[2] >= 0.9f;
    }
}
