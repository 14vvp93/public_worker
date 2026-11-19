package com.hcn.auto.systemui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import com.android.launcher3.icons.ColorExtractor;
import com.android.launcher3.icons.FixedScaleDrawable;
import com.android.systemui.R;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class BaseIconFactory implements AutoCloseable {
    static final boolean ATLEAST_OREO;
    static final boolean ATLEAST_P;
    private final Canvas mCanvas;
    private final ColorExtractor mColorExtractor;
    protected final Context mContext;
    private boolean mDisableColorExtractor;
    protected final int mFillResIconDpi;
    protected final int mIconBitmapSize;
    private IconNormalizer mNormalizer;
    private final Rect mOldBounds;
    private final PackageManager mPm;
    private final boolean mShapeDetection;
    private int mWrapperBackgroundColor;
    private Drawable mWrapperIcon;

    protected BaseIconFactory(Context context, int fillResIconDpi, int iconBitmapSize, boolean shapeDetection) {
        this.mOldBounds = new Rect();
        this.mWrapperBackgroundColor = -1;
        this.mContext = context.getApplicationContext();
        this.mShapeDetection = shapeDetection;
        this.mFillResIconDpi = fillResIconDpi;
        this.mIconBitmapSize = iconBitmapSize;
        this.mPm = this.mContext.getPackageManager();
        this.mColorExtractor = new ColorExtractor();
        this.mCanvas = new Canvas();
        this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        clear();
    }

    protected BaseIconFactory(Context context, int fillResIconDpi, int iconBitmapSize) {
        this(context, fillResIconDpi, iconBitmapSize, false);
    }

    protected void clear() {
        this.mWrapperBackgroundColor = -1;
        this.mDisableColorExtractor = false;
    }

    public IconNormalizer getNormalizer() {
        if (this.mNormalizer == null) {
            this.mNormalizer = new IconNormalizer(this.mContext, this.mIconBitmapSize, this.mShapeDetection);
        }
        return this.mNormalizer;
    }

    public Bitmap createScaledBitmapWithoutShadow(Drawable icon, boolean shrinkNonAdaptiveIcons) {
        RectF iconBounds = new RectF();
        float[] scale = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(icon, shrinkNonAdaptiveIcons, iconBounds, scale), 1.0f);
    }

    public Bitmap createScaledBitmapWithoutShadow(Drawable icon, boolean shrinkNonAdaptiveIcons, String iconName) {
        RectF iconBounds = new RectF();
        float[] scale = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(icon, shrinkNonAdaptiveIcons, iconBounds, scale, iconName), 1.0f);
    }

    public Bitmap createScaledBitmapWithoutShadow(Drawable icon, Drawable iconBackground, boolean shrinkNonAdaptiveIcons, float scale) {
        RectF iconBounds = new RectF();
        float[] outScale = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(icon, shrinkNonAdaptiveIcons, iconBounds, outScale, scale, iconBackground), 1.0f);
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, boolean shrinkNonAdaptiveIcons, RectF outIconBounds, float[] outScale) {
        Drawable icon2 = icon;
        float scale = 0.78f;
        int iconSize = this.mIconBitmapSize;
        int iconSizeTheme = SkinCompatResources.getInstance().getInteger("icon_size");
        if (iconSizeTheme != 0) {
            iconSize = iconSizeTheme;
        }
        if (iconSize >= 120) {
            scale = 1.0f;
        }
        if (shrinkNonAdaptiveIcons && ATLEAST_OREO) {
            if (this.mWrapperIcon == null) {
                this.mWrapperIcon = this.mContext.getDrawable(R.drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable dr = (AdaptiveIconDrawable) this.mWrapperIcon;
            FixedScaleDrawable fsd = new FixedScaleDrawable();
            int ignoreIconBackground = SkinCompatResources.getInstance().getInteger("ignore_icon_background");
            Drawable iconBackground = ignoreIconBackground == 0 ? SkinCompatResources.getInstance().getDrawable("icon_background") : null;
            if (iconBackground != null) {
                dr = new AdaptiveIconDrawable(iconBackground, fsd);
            } else if (ignoreIconBackground == 1) {
                dr.getBackground().setAlpha(0);
            }
            FixedScaleDrawable fsd2 = (FixedScaleDrawable) dr.getForeground();
            fsd2.setDrawable(icon2);
            if (iconSize == 120 || iconSize == 126 || iconSize == 128 || iconSize == 155) {
                fsd2.setScale(0.7f * scale * 0.6667f);
            } else if (iconSize == 160) {
                fsd2.setScale(0.5f * scale * 0.6667f);
            } else if (iconSize == 165) {
                fsd2.setScale(0.6f * scale * 0.6667f);
            } else if (iconSize == 178) {
                fsd2.setScale(0.4f * scale * 0.6667f);
            } else if (iconSize == 188) {
                fsd2.setScale(0.5f * scale * 0.6667f);
            } else {
                fsd2.setScale(0.92f * scale);
            }
            SkinCompatResources.getInstance().getInteger("icon_size_scale");
            if (100 != 0) {
                fsd2.setScale(100 * scale * 0.01f * 0.6667f);
            }
            Drawable icon_bg = SkinCompatResources.getInstance().getDrawable(IconThemeUtils.getIconBackgroundName("za37_icon_background_n"));
            if (icon_bg != null) {
                scale = 1.0f;
                Drawable[] layers = {icon_bg, null};
                if (100 != 0) {
                    fsd2.setScale(100 * 1.0f * 0.01f);
                }
                layers[1] = fsd2;
                icon2 = new LayerDrawable(layers);
            } else {
                icon2 = dr;
            }
        } else {
            scale = getNormalizer().getScale(icon2, outIconBounds, (Path) null, (boolean[]) null);
        }
        outScale[0] = scale;
        return icon2;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, boolean shrinkNonAdaptiveIcons, RectF outIconBounds, float[] outScale, String iconName) {
        Drawable icon2 = icon;
        float scale = 0.78f;
        int iconSize = this.mIconBitmapSize;
        int iconSizeTheme = SkinCompatResources.getInstance().getInteger("icon_size");
        if (iconSizeTheme != 0) {
            iconSize = iconSizeTheme;
        }
        if (iconSize >= 120) {
            scale = 1.0f;
        }
        if (shrinkNonAdaptiveIcons && ATLEAST_OREO) {
            if (this.mWrapperIcon == null) {
                this.mWrapperIcon = this.mContext.getDrawable(R.drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable dr = (AdaptiveIconDrawable) this.mWrapperIcon;
            FixedScaleDrawable fsd = new FixedScaleDrawable();
            int ignoreIconBackground = SkinCompatResources.getInstance().getInteger("ignore_icon_background");
            Drawable iconBackground = ignoreIconBackground == 0 ? SkinCompatResources.getInstance().getDrawable("icon_background") : null;
            if (iconBackground != null) {
                dr = new AdaptiveIconDrawable(iconBackground, fsd);
            } else if (ignoreIconBackground == 1) {
                dr.getBackground().setAlpha(0);
            }
            FixedScaleDrawable fsd2 = (FixedScaleDrawable) dr.getForeground();
            fsd2.setDrawable(icon2);
            if (iconSize == 120 || iconSize == 126 || iconSize == 128 || iconSize == 155) {
                fsd2.setScale(0.7f * scale * 0.6667f);
            } else if (iconSize == 160) {
                fsd2.setScale(0.5f * scale * 0.6667f);
            } else if (iconSize == 165) {
                fsd2.setScale(0.6f * scale * 0.6667f);
            } else if (iconSize == 178) {
                fsd2.setScale(0.4f * scale * 0.6667f);
            } else if (iconSize == 188) {
                fsd2.setScale(0.5f * scale * 0.6667f);
            } else {
                fsd2.setScale(0.92f * scale);
            }
            SkinCompatResources.getInstance().getInteger("icon_size_scale");
            if (60 != 0) {
                fsd2.setScale(60 * scale * 0.01f * 0.6667f);
            }
            Drawable icon_bg = SkinCompatResources.getInstance().getDrawable(IconThemeUtils.getIconBackgroundName(iconName));
            if (icon_bg != null) {
                scale = 1.0f;
                Drawable[] layers = {icon_bg, null};
                if (60 != 0) {
                    fsd2.setScale(60 * 1.0f * 0.01f);
                }
                layers[1] = fsd2;
                icon2 = new LayerDrawable(layers);
            } else {
                icon2 = dr;
            }
        } else {
            scale = getNormalizer().getScale(icon2, outIconBounds, (Path) null, (boolean[]) null);
        }
        outScale[0] = scale;
        return icon2;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, boolean shrinkNonAdaptiveIcons, RectF outIconBounds, float[] outScale, float scale, Drawable background) {
        if (shrinkNonAdaptiveIcons && ATLEAST_OREO) {
            if (this.mWrapperIcon == null) {
                this.mWrapperIcon = this.mContext.getDrawable(R.drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable dr = (AdaptiveIconDrawable) this.mWrapperIcon;
            FixedScaleDrawable fsd = new FixedScaleDrawable();
            SkinCompatResources.getInstance().getInteger("icon_translate_x");
            int ignoreIconBackground = SkinCompatResources.getInstance().getInteger("ignore_icon_background");
            Drawable iconBackground = ignoreIconBackground == 0 ? background : null;
            if (iconBackground != null) {
                dr = new AdaptiveIconDrawable(iconBackground, fsd);
            } else if (ignoreIconBackground == 1) {
                dr.getBackground().setAlpha(0);
            }
            FixedScaleDrawable fsd2 = (FixedScaleDrawable) dr.getForeground();
            fsd2.setDrawable(icon);
            if (iconBackground != null) {
                Drawable[] layers = {iconBackground, null};
                fsd2.setScale(scale);
                layers[1] = fsd2;
                icon = new LayerDrawable(layers);
            } else {
                icon = dr;
            }
        } else {
            scale = getNormalizer().getScale(icon, outIconBounds, (Path) null, (boolean[]) null);
        }
        outScale[0] = scale;
        return icon;
    }

    private Bitmap createIconBitmap(Drawable icon, float scale) {
        return createIconBitmap(icon, scale, this.mIconBitmapSize);
    }

    public Bitmap createIconBitmap(Drawable icon, float scale, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        if (icon == null) {
            return bitmap;
        }
        this.mCanvas.setBitmap(bitmap);
        this.mOldBounds.set(icon.getBounds());
        if (ATLEAST_OREO && (icon instanceof AdaptiveIconDrawable)) {
            int width = Math.max((int) Math.ceil(0.010416667f * size), Math.round((size * (1.0f - scale)) / 2.0f));
            icon.setBounds(width, width, size - width, size - width);
            icon.draw(this.mCanvas);
        } else {
            if (icon instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
                Bitmap b = bitmapDrawable.getBitmap();
                if (bitmap != null && b.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int width2 = size;
            int height = size;
            int intrinsicWidth = icon.getIntrinsicWidth();
            int intrinsicHeight = icon.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float ratio = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    height = (int) (size / ratio);
                } else if (intrinsicHeight > intrinsicWidth) {
                    width2 = (int) (size * ratio);
                }
            }
            int left = (size - width2) / 2;
            int top = (size - height) / 2;
            icon.setBounds(left, top, left + width2, top + height);
            this.mCanvas.save();
            this.mCanvas.scale(scale, scale, size / 2, size / 2);
            icon.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        icon.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap((Bitmap) null);
        return bitmap;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        clear();
    }

    static {
        ATLEAST_OREO = Build.VERSION.SDK_INT >= 26;
        ATLEAST_P = Build.VERSION.SDK_INT >= 28;
    }
}
