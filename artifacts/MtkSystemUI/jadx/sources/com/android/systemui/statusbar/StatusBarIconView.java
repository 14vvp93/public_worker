package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.ViewDebug;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationIconDozeHelper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarIconView extends AnimatedImageView implements StatusIconDisplayable {
    private final int ANIMATION_DURATION_FAST;
    private boolean mAlwaysScaleIcon;
    private int mAnimationStartColor;
    private final boolean mBlocked;
    private int mCachedContrastBackgroundColor;
    private ValueAnimator mColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mColorUpdater;
    private int mContrastedDrawableColor;
    private int mCurrentSetColor;
    private float mDarkAmount;
    private int mDecorColor;
    private int mDensity;
    private boolean mDismissed;
    private ObjectAnimator mDotAnimator;
    private float mDotAppearAmount;
    private final Paint mDotPaint;
    private float mDotRadius;
    private final NotificationIconDozeHelper mDozer;
    private int mDrawableColor;
    private StatusBarIcon mIcon;
    private float mIconAppearAmount;
    private ObjectAnimator mIconAppearAnimator;
    private int mIconColor;
    private float mIconScale;
    private boolean mIsInShelf;
    private Runnable mLayoutRunnable;
    private float[] mMatrix;
    private ColorMatrixColorFilter mMatrixColorFilter;
    private StatusBarNotification mNotification;
    private Drawable mNumberBackground;
    private Paint mNumberPain;
    private String mNumberText;
    private int mNumberX;
    private int mNumberY;
    private Runnable mOnDismissListener;
    private OnVisibilityChangedListener mOnVisibilityChangedListener;

    @ViewDebug.ExportedProperty
    private String mSlot;
    private int mStaticDotRadius;
    private int mStatusBarIconDrawingSize;
    private int mStatusBarIconDrawingSizeDark;
    private int mStatusBarIconSize;
    private int mVisibleState;
    private static final Property<StatusBarIconView, Float> ICON_APPEAR_AMOUNT = new FloatProperty<StatusBarIconView>("iconAppearAmount") { // from class: com.android.systemui.statusbar.StatusBarIconView.1
        @Override // android.util.FloatProperty
        public void setValue(StatusBarIconView object, float value) {
            object.setIconAppearAmount(value);
        }

        @Override // android.util.Property
        public Float get(StatusBarIconView object) {
            return Float.valueOf(object.getIconAppearAmount());
        }
    };
    private static final Property<StatusBarIconView, Float> DOT_APPEAR_AMOUNT = new FloatProperty<StatusBarIconView>("dot_appear_amount") { // from class: com.android.systemui.statusbar.StatusBarIconView.2
        @Override // android.util.FloatProperty
        public void setValue(StatusBarIconView object, float value) {
            object.setDotAppearAmount(value);
        }

        @Override // android.util.Property
        public Float get(StatusBarIconView object) {
            return Float.valueOf(object.getDotAppearAmount());
        }
    };

    public interface OnVisibilityChangedListener {
        void onVisibilityChanged(int i);
    }

    public static /* synthetic */ void lambda$new$0(StatusBarIconView statusBarIconView, ValueAnimator animation) {
        int newColor = NotificationUtils.interpolateColors(statusBarIconView.mAnimationStartColor, statusBarIconView.mIconColor, animation.getAnimatedFraction());
        statusBarIconView.setColorInternal(newColor);
    }

    public StatusBarIconView(Context context, String slot, StatusBarNotification sbn) {
        this(context, slot, sbn, false);
    }

    public StatusBarIconView(Context context, String slot, StatusBarNotification sbn, boolean blocked) {
        super(context);
        this.ANIMATION_DURATION_FAST = 100;
        this.mStatusBarIconDrawingSizeDark = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mStatusBarIconSize = 1;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.-$$Lambda$StatusBarIconView$nRA4PFzS-KIqshXSve3PBqKMX7Q
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                StatusBarIconView.lambda$new$0(this.f$0, valueAnimator);
            }
        };
        this.mCachedContrastBackgroundColor = 0;
        this.mDozer = new NotificationIconDozeHelper(context);
        this.mBlocked = blocked;
        this.mSlot = slot;
        this.mNumberPain = new Paint();
        this.mNumberPain.setTextAlign(Paint.Align.CENTER);
        this.mNumberPain.setColor(context.getColor(R.drawable.notification_number_text_color));
        this.mNumberPain.setAntiAlias(true);
        setNotification(sbn);
        setScaleType(ImageView.ScaleType.CENTER);
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        if (this.mNotification != null) {
            setDecorColor(getContext().getColor(android.R.color.accessibility_magnification_background));
        }
        reloadDimens();
        maybeUpdateIconScaleDimens();
    }

    private void maybeUpdateIconScaleDimens() {
        if (this.mNotification != null || this.mAlwaysScaleIcon) {
            updateIconScaleForNotifications();
        } else {
            updateIconScaleForSystemIcons();
        }
    }

    private void updateIconScaleForNotifications() {
        float imageBounds = NotificationUtils.interpolate(this.mStatusBarIconDrawingSize, this.mStatusBarIconDrawingSizeDark, this.mDarkAmount);
        int outerBounds = this.mStatusBarIconSize;
        this.mIconScale = imageBounds / outerBounds;
        updatePivot();
    }

    private void updateIconScaleForSystemIcons() {
        this.mIconScale = 0.88235295f;
    }

    public float getIconScaleFullyDark() {
        return this.mStatusBarIconDrawingSizeDark / this.mStatusBarIconDrawingSize;
    }

    public float getIconScale() {
        return this.mIconScale;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int density = newConfig.densityDpi;
        if (density != this.mDensity) {
            this.mDensity = density;
            reloadDimens();
            maybeUpdateIconScaleDimens();
            updateDrawable();
        }
    }

    private void reloadDimens() {
        boolean applyRadius = this.mDotRadius == ((float) this.mStaticDotRadius);
        Resources res = getResources();
        this.mStaticDotRadius = res.getDimensionPixelSize(R.dimen.overflow_dot_radius);
        this.mStatusBarIconSize = res.getDimensionPixelSize(R.dimen.status_bar_icon_size);
        this.mStatusBarIconDrawingSizeDark = res.getDimensionPixelSize(R.dimen.status_bar_icon_drawing_size_dark);
        this.mStatusBarIconDrawingSize = res.getDimensionPixelSize(R.dimen.status_bar_icon_drawing_size);
        if (applyRadius) {
            this.mDotRadius = this.mStaticDotRadius;
        }
    }

    public void setNotification(StatusBarNotification notification) {
        this.mNotification = notification;
        if (notification != null) {
            setContentDescription(notification.getNotification());
        }
    }

    public StatusBarIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ANIMATION_DURATION_FAST = 100;
        this.mStatusBarIconDrawingSizeDark = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mStatusBarIconSize = 1;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.-$$Lambda$StatusBarIconView$nRA4PFzS-KIqshXSve3PBqKMX7Q
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                StatusBarIconView.lambda$new$0(this.f$0, valueAnimator);
            }
        };
        this.mCachedContrastBackgroundColor = 0;
        this.mDozer = new NotificationIconDozeHelper(context);
        this.mBlocked = false;
        this.mAlwaysScaleIcon = true;
        reloadDimens();
        updateIconScaleForNotifications();
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
    }

    public boolean equalIcons(Icon a, Icon b) {
        if (a == b) {
            return true;
        }
        if (a.getType() != b.getType()) {
            return false;
        }
        int type = a.getType();
        if (type == 2) {
            return a.getResPackage().equals(b.getResPackage()) && a.getResId() == b.getResId();
        }
        if (type != 4) {
            return false;
        }
        return a.getUriString().equals(b.getUriString());
    }

    public boolean set(StatusBarIcon icon) {
        boolean iconEquals = this.mIcon != null && equalIcons(this.mIcon.icon, icon.icon);
        boolean levelEquals = iconEquals && this.mIcon.iconLevel == icon.iconLevel;
        boolean visibilityEquals = this.mIcon != null && this.mIcon.visible == icon.visible;
        boolean numberEquals = this.mIcon != null && this.mIcon.number == icon.number;
        this.mIcon = icon.clone();
        setContentDescription(icon.contentDescription);
        if (!iconEquals) {
            if (!updateDrawable(false)) {
                return false;
            }
            setTag(R.id.icon_is_grayscale, null);
        }
        if (!levelEquals) {
            setImageLevel(icon.iconLevel);
        }
        if (!numberEquals) {
            if (icon.number > 0 && getContext().getResources().getBoolean(R.bool.config_statusBarShowNumber)) {
                if (this.mNumberBackground == null) {
                    this.mNumberBackground = getContext().getResources().getDrawable(R.drawable.ic_notification_overlay);
                }
                placeNumber();
            } else {
                this.mNumberBackground = null;
                this.mNumberText = null;
            }
            invalidate();
        }
        if (!visibilityEquals) {
            setVisibility((!icon.visible || this.mBlocked) ? 8 : 0);
        }
        return true;
    }

    public void updateDrawable() {
        updateDrawable(true);
    }

    private boolean updateDrawable(boolean withClear) {
        if (this.mIcon == null) {
            return false;
        }
        try {
            Drawable drawable = getIcon(this.mIcon);
            if (drawable == null) {
                Log.w("StatusBarIconView", "No icon for slot " + this.mSlot + "; " + this.mIcon.icon);
                return false;
            }
            if ((drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap() != null) {
                int byteCount = ((BitmapDrawable) drawable).getBitmap().getByteCount();
                if (byteCount > 104857600) {
                    Log.w("StatusBarIconView", "Drawable is too large (" + byteCount + " bytes) " + this.mIcon);
                    return false;
                }
            } else if (drawable.getIntrinsicWidth() > 5000 || drawable.getIntrinsicHeight() > 5000) {
                Log.w("StatusBarIconView", "Drawable is too large (" + drawable.getIntrinsicWidth() + "x" + drawable.getIntrinsicHeight() + ") " + this.mIcon);
                return false;
            }
            if (withClear) {
                setImageDrawable(null);
            }
            setImageDrawable(drawable);
            return true;
        } catch (OutOfMemoryError e) {
            Log.w("StatusBarIconView", "OOM while inflating " + this.mIcon.icon + " for slot " + this.mSlot);
            return false;
        }
    }

    public Icon getSourceIcon() {
        return this.mIcon.icon;
    }

    private Drawable getIcon(StatusBarIcon icon) {
        return getIcon(getContext(), icon);
    }

    public static Drawable getIcon(Context context, StatusBarIcon statusBarIcon) {
        int userId = statusBarIcon.user.getIdentifier();
        if (userId == -1) {
            userId = 0;
        }
        Drawable icon = statusBarIcon.icon.loadDrawableAsUser(context, userId);
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        float scaleFactor = typedValue.getFloat();
        if (scaleFactor == 1.0f) {
            return icon;
        }
        return new ScalingDrawableWrapper(icon, scaleFactor);
    }

    public StatusBarIcon getStatusBarIcon() {
        return this.mIcon;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (this.mNotification != null) {
            event.setParcelableData(this.mNotification.getNotification());
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mNumberBackground != null) {
            placeNumber();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        updateDrawable();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        float fadeOutAmount;
        if (this.mIconAppearAmount > 0.0f) {
            canvas.save();
            canvas.scale(this.mIconScale * this.mIconAppearAmount, this.mIconScale * this.mIconAppearAmount, getWidth() / 2, getHeight() / 2);
            super.onDraw(canvas);
            canvas.restore();
        }
        if (this.mNumberBackground != null) {
            this.mNumberBackground.draw(canvas);
            canvas.drawText(this.mNumberText, this.mNumberX, this.mNumberY, this.mNumberPain);
        }
        if (this.mDotAppearAmount != 0.0f) {
            float alpha = Color.alpha(this.mDecorColor) / 255.0f;
            if (this.mDotAppearAmount <= 1.0f) {
                fadeOutAmount = this.mDotRadius * this.mDotAppearAmount;
            } else {
                float radius = this.mDotAppearAmount;
                float fadeOutAmount2 = radius - 1.0f;
                alpha *= 1.0f - fadeOutAmount2;
                fadeOutAmount = NotificationUtils.interpolate(this.mDotRadius, getWidth() / 4, fadeOutAmount2);
            }
            this.mDotPaint.setAlpha((int) (255.0f * alpha));
            canvas.drawCircle(this.mStatusBarIconSize / 2, getHeight() / 2, fadeOutAmount, this.mDotPaint);
        }
    }

    protected void debug(int depth) {
        super.debug(depth);
        Log.d("View", debugIndent(depth) + "slot=" + this.mSlot);
        Log.d("View", debugIndent(depth) + "icon=" + this.mIcon);
    }

    void placeNumber() {
        String str;
        int tooBig = getContext().getResources().getInteger(android.R.integer.status_bar_notification_info_maxnum);
        if (this.mIcon.number > tooBig) {
            str = getContext().getResources().getString(android.R.string.status_bar_notification_info_overflow);
        } else {
            NumberFormat f = NumberFormat.getIntegerInstance();
            str = f.format(this.mIcon.number);
        }
        this.mNumberText = str;
        int w = getWidth();
        int h = getHeight();
        Rect r = new Rect();
        this.mNumberPain.getTextBounds(str, 0, str.length(), r);
        int tw = r.right - r.left;
        int th = r.bottom - r.top;
        this.mNumberBackground.getPadding(r);
        int dw = r.left + tw + r.right;
        if (dw < this.mNumberBackground.getMinimumWidth()) {
            dw = this.mNumberBackground.getMinimumWidth();
        }
        this.mNumberX = (w - r.right) - (((dw - r.right) - r.left) / 2);
        int dh = r.top + th + r.bottom;
        if (dh < this.mNumberBackground.getMinimumWidth()) {
            dh = this.mNumberBackground.getMinimumWidth();
        }
        this.mNumberY = (h - r.bottom) - ((((dh - r.top) - th) - r.bottom) / 2);
        this.mNumberBackground.setBounds(w - dw, h - dh, w, h);
    }

    private void setContentDescription(Notification notification) {
        if (notification != null) {
            String d = contentDescForNotification(this.mContext, notification);
            if (!TextUtils.isEmpty(d)) {
                setContentDescription(d);
            }
        }
    }

    @Override // android.view.View
    public String toString() {
        return "StatusBarIconView(slot=" + this.mSlot + " icon=" + this.mIcon + " notification=" + this.mNotification + ")";
    }

    public StatusBarNotification getNotification() {
        return this.mNotification;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public String getSlot() {
        return this.mSlot;
    }

    public static String contentDescForNotification(Context c, Notification n) {
        CharSequence desc;
        String appName = "";
        try {
            Notification.Builder builder = Notification.Builder.recoverBuilder(c, n);
            appName = builder.loadHeaderAppName();
        } catch (RuntimeException e) {
            Log.e("StatusBarIconView", "Unable to recover builder", e);
            Parcelable appInfo = n.extras.getParcelable("android.appInfo");
            if (appInfo instanceof ApplicationInfo) {
                appName = String.valueOf(((ApplicationInfo) appInfo).loadLabel(c.getPackageManager()));
            }
        }
        CharSequence title = n.extras.getCharSequence("android.title");
        CharSequence text = n.extras.getCharSequence("android.text");
        CharSequence ticker = n.tickerText;
        CharSequence titleOrText = TextUtils.equals(title, appName) ? text : title;
        if (TextUtils.isEmpty(titleOrText)) {
            desc = !TextUtils.isEmpty(ticker) ? ticker : "";
        } else {
            desc = titleOrText;
        }
        return c.getString(R.string.accessibility_desc_notification_icon, appName, desc);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setDecorColor(int iconTint) {
        this.mDecorColor = iconTint;
        updateDecorColor();
    }

    private void updateDecorColor() {
        int color = NotificationUtils.interpolateColors(this.mDecorColor, -1, this.mDarkAmount);
        if (this.mDotPaint.getColor() != color) {
            this.mDotPaint.setColor(color);
            if (this.mDotAppearAmount != 0.0f) {
                invalidate();
            }
        }
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setStaticDrawableColor(int color) {
        this.mDrawableColor = color;
        setColorInternal(color);
        updateContrastedStaticColor();
        this.mIconColor = color;
        this.mDozer.setColor(color);
    }

    private void setColorInternal(int color) {
        this.mCurrentSetColor = color;
        updateIconColor();
    }

    private void updateIconColor() {
        if (this.mCurrentSetColor != 0) {
            if (this.mMatrixColorFilter == null) {
                this.mMatrix = new float[20];
                this.mMatrixColorFilter = new ColorMatrixColorFilter(this.mMatrix);
            }
            int color = NotificationUtils.interpolateColors(this.mCurrentSetColor, -1, this.mDarkAmount);
            updateTintMatrix(this.mMatrix, color, 0.67f * this.mDarkAmount);
            this.mMatrixColorFilter.setColorMatrixArray(this.mMatrix);
            setColorFilter(this.mMatrixColorFilter);
            invalidate();
            return;
        }
        this.mDozer.updateGrayscale(this, this.mDarkAmount);
    }

    private static void updateTintMatrix(float[] array, int color, float alphaBoost) {
        Arrays.fill(array, 0.0f);
        array[4] = Color.red(color);
        array[9] = Color.green(color);
        array[14] = Color.blue(color);
        array[18] = (Color.alpha(color) / 255.0f) + alphaBoost;
    }

    public void setIconColor(int iconColor, boolean animate) {
        if (this.mIconColor != iconColor) {
            this.mIconColor = iconColor;
            if (this.mColorAnimator != null) {
                this.mColorAnimator.cancel();
            }
            if (this.mCurrentSetColor == iconColor) {
                return;
            }
            if (animate && this.mCurrentSetColor != 0) {
                this.mAnimationStartColor = this.mCurrentSetColor;
                this.mColorAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mColorAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mColorAnimator.setDuration(100L);
                this.mColorAnimator.addUpdateListener(this.mColorUpdater);
                this.mColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        StatusBarIconView.this.mColorAnimator = null;
                        StatusBarIconView.this.mAnimationStartColor = 0;
                    }
                });
                this.mColorAnimator.start();
                return;
            }
            setColorInternal(iconColor);
        }
    }

    public int getStaticDrawableColor() {
        return this.mDrawableColor;
    }

    int getContrastedStaticDrawableColor(int backgroundColor) {
        if (this.mCachedContrastBackgroundColor != backgroundColor) {
            this.mCachedContrastBackgroundColor = backgroundColor;
            updateContrastedStaticColor();
        }
        return this.mContrastedDrawableColor;
    }

    private void updateContrastedStaticColor() {
        if (Color.alpha(this.mCachedContrastBackgroundColor) != 255) {
            this.mContrastedDrawableColor = this.mDrawableColor;
            return;
        }
        int contrastedColor = this.mDrawableColor;
        if (!NotificationColorUtil.satisfiesTextContrast(this.mCachedContrastBackgroundColor, contrastedColor)) {
            float[] hsl = new float[3];
            ColorUtils.colorToHSL(this.mDrawableColor, hsl);
            if (hsl[1] < 0.2f) {
                contrastedColor = 0;
            }
            contrastedColor = NotificationColorUtil.resolveContrastColor(this.mContext, contrastedColor, this.mCachedContrastBackgroundColor);
        }
        this.mContrastedDrawableColor = contrastedColor;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public void setVisibleState(int state) {
        setVisibleState(state, true, null);
    }

    public void setVisibleState(int state, boolean animate) {
        setVisibleState(state, animate, null);
    }

    @Override // com.android.systemui.statusbar.AnimatedImageView, android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setVisibleState(int visibleState, boolean animate, Runnable endRunnable) {
        setVisibleState(visibleState, animate, endRunnable, 0L);
    }

    public void setVisibleState(int visibleState, boolean animate, final Runnable endRunnable, long duration) {
        boolean runnableAdded = false;
        if (visibleState != this.mVisibleState) {
            this.mVisibleState = visibleState;
            if (this.mIconAppearAnimator != null) {
                this.mIconAppearAnimator.cancel();
            }
            if (this.mDotAnimator != null) {
                this.mDotAnimator.cancel();
            }
            if (animate) {
                float targetAmount = 0.0f;
                Interpolator interpolator = Interpolators.FAST_OUT_LINEAR_IN;
                if (visibleState == 0) {
                    targetAmount = 1.0f;
                    interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
                }
                float currentAmount = getIconAppearAmount();
                if (targetAmount != currentAmount) {
                    this.mIconAppearAnimator = ObjectAnimator.ofFloat(this, ICON_APPEAR_AMOUNT, currentAmount, targetAmount);
                    this.mIconAppearAnimator.setInterpolator(interpolator);
                    this.mIconAppearAnimator.setDuration(duration == 0 ? 100L : duration);
                    this.mIconAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            StatusBarIconView.this.mIconAppearAnimator = null;
                            StatusBarIconView.this.runRunnable(endRunnable);
                        }
                    });
                    this.mIconAppearAnimator.start();
                    runnableAdded = true;
                }
                float targetAmount2 = visibleState == 0 ? 2.0f : 0.0f;
                Interpolator interpolator2 = Interpolators.FAST_OUT_LINEAR_IN;
                if (visibleState == 1) {
                    targetAmount2 = 1.0f;
                    interpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
                }
                float currentAmount2 = getDotAppearAmount();
                if (targetAmount2 != currentAmount2) {
                    this.mDotAnimator = ObjectAnimator.ofFloat(this, DOT_APPEAR_AMOUNT, currentAmount2, targetAmount2);
                    this.mDotAnimator.setInterpolator(interpolator2);
                    this.mDotAnimator.setDuration(duration == 0 ? 100L : duration);
                    final boolean runRunnable = runnableAdded ? false : true;
                    this.mDotAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.5
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            StatusBarIconView.this.mDotAnimator = null;
                            if (runRunnable) {
                                StatusBarIconView.this.runRunnable(endRunnable);
                            }
                        }
                    });
                    this.mDotAnimator.start();
                    runnableAdded = true;
                }
            } else {
                float f = 1.0f;
                setIconAppearAmount(visibleState == 0 ? 1.0f : 0.0f);
                if (visibleState != 1) {
                    f = visibleState == 0 ? 2.0f : 0.0f;
                }
                setDotAppearAmount(f);
            }
        }
        if (!runnableAdded) {
            runRunnable(endRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runRunnable(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setIconAppearAmount(float iconAppearAmount) {
        if (this.mIconAppearAmount != iconAppearAmount) {
            this.mIconAppearAmount = iconAppearAmount;
            invalidate();
        }
    }

    public float getIconAppearAmount() {
        return this.mIconAppearAmount;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public int getVisibleState() {
        return this.mVisibleState;
    }

    public void setDotAppearAmount(float dotAppearAmount) {
        if (this.mDotAppearAmount != dotAppearAmount) {
            this.mDotAppearAmount = dotAppearAmount;
            invalidate();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.mOnVisibilityChangedListener != null) {
            this.mOnVisibilityChangedListener.onVisibilityChanged(visibility);
        }
    }

    public float getDotAppearAmount() {
        return this.mDotAppearAmount;
    }

    public void setOnVisibilityChangedListener(OnVisibilityChangedListener listener) {
        this.mOnVisibilityChangedListener = listener;
    }

    public void setDark(boolean dark, boolean fade, long delay) {
        this.mDozer.setIntensityDark(new Consumer() { // from class: com.android.systemui.statusbar.-$$Lambda$StatusBarIconView$IedzBslpRTF95Z-E8YfkBh77Pu0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBarIconView.lambda$setDark$1(this.f$0, (Float) obj);
            }
        }, dark, fade, delay, this);
    }

    public static /* synthetic */ void lambda$setDark$1(StatusBarIconView statusBarIconView, Float f) {
        statusBarIconView.mDarkAmount = f.floatValue();
        statusBarIconView.updateIconScaleForNotifications();
        statusBarIconView.updateDecorColor();
        statusBarIconView.updateIconColor();
        statusBarIconView.updateAllowAnimation();
    }

    private void updateAllowAnimation() {
        if (this.mDarkAmount == 0.0f || this.mDarkAmount == 1.0f) {
            setAllowAnimation(this.mDarkAmount == 0.0f);
        }
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

    public void setIsInShelf(boolean isInShelf) {
        this.mIsInShelf = isInShelf;
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mLayoutRunnable != null) {
            this.mLayoutRunnable.run();
            this.mLayoutRunnable = null;
        }
        updatePivot();
    }

    private void updatePivot() {
        setPivotX(((1.0f - this.mIconScale) / 2.0f) * getWidth());
        setPivotY((getHeight() - (this.mIconScale * getWidth())) / 2.0f);
    }

    public void executeOnLayout(Runnable runnable) {
        this.mLayoutRunnable = runnable;
    }

    public void setDismissed() {
        this.mDismissed = true;
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.run();
        }
    }

    public void setOnDismissListener(Runnable onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        int areaTint = DarkIconDispatcher.getTint(area, this, tint);
        ColorStateList color = ColorStateList.valueOf(areaTint);
        setImageTintList(color);
        setDecorColor(areaTint);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public boolean isIconVisible() {
        return this.mIcon != null && this.mIcon.visible;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public boolean isIconBlocked() {
        return this.mBlocked;
    }
}
