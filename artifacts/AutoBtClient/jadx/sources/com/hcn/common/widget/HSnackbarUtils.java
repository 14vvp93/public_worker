package com.hcn.common.widget;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.autochips.bluetooth.skin.SkinID;
import com.google.android.material.snackbar.Snackbar;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class HSnackbarUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final int COLOR_ERROR = -65536;
    private static final int COLOR_MESSAGE = -1;
    private static final int COLOR_SUCCESS = -13912576;
    private static final int COLOR_WARNING = -16128;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    private static WeakReference<Snackbar> sWeakSnackbar;
    private View.OnClickListener actionListener;
    private CharSequence actionText;
    private int actionTextColor;
    private float alpha;
    private int bgColor;
    private int bgResource;
    private int bottomMargin;
    private int duration;
    private Drawable leftDrawable;
    private CharSequence message;
    private int messageColor;
    private int messageSize;
    private float radius;
    private View view;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private HSnackbarUtils(View view) {
        setDefault();
        this.view = view;
    }

    private void setDefault() {
        this.message = SkinID.SKIN_NONE;
        this.messageColor = COLOR_DEFAULT;
        this.messageSize = 0;
        this.bgColor = COLOR_DEFAULT;
        this.bgResource = -1;
        this.duration = -1;
        this.actionText = SkinID.SKIN_NONE;
        this.actionTextColor = COLOR_DEFAULT;
        this.bottomMargin = 0;
        this.radius = 0.0f;
        this.alpha = 1.0f;
        this.leftDrawable = null;
    }

    public static HSnackbarUtils with(View view) {
        return new HSnackbarUtils(view);
    }

    public HSnackbarUtils setMessage(CharSequence charSequence) {
        this.message = charSequence;
        return this;
    }

    public HSnackbarUtils setMessageColor(int i) {
        this.messageColor = i;
        return this;
    }

    public HSnackbarUtils setMessageSize(int i) {
        this.messageSize = i;
        return this;
    }

    public HSnackbarUtils setBgColor(int i) {
        this.bgColor = i;
        return this;
    }

    public HSnackbarUtils setBgResource(int i) {
        this.bgResource = i;
        return this;
    }

    private GradientDrawable getRadiusDrawable(Drawable drawable) {
        if (drawable instanceof GradientDrawable) {
            return (GradientDrawable) drawable;
        }
        if (!(drawable instanceof ColorDrawable)) {
            return null;
        }
        int color = ((ColorDrawable) drawable).getColor();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    public HSnackbarUtils setRadius(float f) {
        this.radius = f;
        return this;
    }

    public HSnackbarUtils setAlpha(float f) {
        this.alpha = f < 1.0f ? Math.max(f, 0.0f) : 1.0f;
        return this;
    }

    public HSnackbarUtils setDuration(int i) {
        this.duration = i;
        return this;
    }

    public HSnackbarUtils setAction(CharSequence charSequence, View.OnClickListener onClickListener) {
        return setAction(charSequence, COLOR_DEFAULT, onClickListener);
    }

    public HSnackbarUtils setAction(CharSequence charSequence, int i, View.OnClickListener onClickListener) {
        this.actionText = charSequence;
        this.actionTextColor = i;
        this.actionListener = onClickListener;
        return this;
    }

    public HSnackbarUtils setLeftDrawable(Drawable drawable) {
        this.leftDrawable = drawable;
        return this;
    }

    public HSnackbarUtils setBottomMargin(int i) {
        this.bottomMargin = i;
        return this;
    }

    public Snackbar show() {
        return show(false);
    }

    public Snackbar show(boolean z) {
        CoordinatorLayout coordinatorLayout = this.view;
        if (coordinatorLayout == null) {
            return null;
        }
        if (z) {
            ViewGroup viewGroupFindSuitableParentCopyFromSnackbar = findSuitableParentCopyFromSnackbar(coordinatorLayout);
            CoordinatorLayout coordinatorLayoutFindViewWithTag = viewGroupFindSuitableParentCopyFromSnackbar.findViewWithTag("topSnackBarCoordinatorLayout");
            if (coordinatorLayoutFindViewWithTag == null) {
                coordinatorLayoutFindViewWithTag = new CoordinatorLayout(coordinatorLayout.getContext());
                coordinatorLayoutFindViewWithTag.setTag("topSnackBarCoordinatorLayout");
                coordinatorLayoutFindViewWithTag.setRotation(180.0f);
                if (Build.VERSION.SDK_INT >= 21) {
                    coordinatorLayoutFindViewWithTag.setElevation(100.0f);
                }
                viewGroupFindSuitableParentCopyFromSnackbar.addView((View) coordinatorLayoutFindViewWithTag, -1, -1);
            }
            coordinatorLayout = coordinatorLayoutFindViewWithTag;
        }
        if (this.messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(this.message);
            spannableString.setSpan(new ForegroundColorSpan(this.messageColor), 0, spannableString.length(), 33);
            sWeakSnackbar = new WeakReference<>(Snackbar.make(coordinatorLayout, spannableString, this.duration));
        } else {
            sWeakSnackbar = new WeakReference<>(Snackbar.make(coordinatorLayout, this.message, this.duration));
        }
        Snackbar snackbar = sWeakSnackbar.get();
        Snackbar.SnackbarLayout view = snackbar.getView();
        if (z) {
            for (int i = 0; i < view.getChildCount(); i++) {
                view.getChildAt(i).setRotation(180.0f);
            }
        }
        int i2 = this.bgResource;
        if (i2 != -1) {
            view.setBackgroundResource(i2);
        } else {
            int i3 = this.bgColor;
            if (i3 != COLOR_DEFAULT) {
                view.setBackgroundColor(i3);
            }
        }
        GradientDrawable radiusDrawable = getRadiusDrawable(view.getBackground());
        if (radiusDrawable != null) {
            float f = this.radius;
            if (f < 0.0f) {
                f = 12.0f;
            }
            this.radius = f;
            radiusDrawable.setCornerRadius(f);
            view.setBackgroundDrawable(radiusDrawable);
        }
        view.setAlpha(this.alpha);
        if (this.bottomMargin != 0) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.bottomMargin = this.bottomMargin;
            view.setLayoutParams(marginLayoutParams);
        }
        if (this.actionText.length() > 0 && this.actionListener != null) {
            int i4 = this.actionTextColor;
            if (i4 != COLOR_DEFAULT) {
                snackbar.setActionTextColor(i4);
            }
            snackbar.setAction(this.actionText, this.actionListener);
        }
        TextView messageView = view.getChildAt(0).getMessageView();
        if (messageView != null) {
            int i5 = this.messageSize;
            if (i5 != 0) {
                messageView.setTextSize(i5);
            }
            if (this.leftDrawable != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageView.getLayoutParams();
                messageView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height, 0.0f));
                messageView.setCompoundDrawablePadding(messageView.getPaddingLeft());
                int textSize = ((int) messageView.getTextSize()) + 3;
                this.leftDrawable.setBounds(0, 0, textSize, textSize);
                messageView.setCompoundDrawables(this.leftDrawable, null, null, null);
                view.addView(new Space(view.getContext()), 1, new LinearLayout.LayoutParams(-2, -2, 1.0f));
            }
        }
        snackbar.show();
        return snackbar;
    }

    public void showSuccess() {
        showSuccess(false);
    }

    public void showSuccess(boolean z) {
        this.bgColor = COLOR_SUCCESS;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z);
    }

    public void showWarning() {
        showWarning(false);
    }

    public void showWarning(boolean z) {
        this.bgColor = COLOR_WARNING;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z);
    }

    public void showError() {
        showError(false);
    }

    public void showError(boolean z) {
        this.bgColor = -65536;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z);
    }

    public static void dismiss() {
        WeakReference<Snackbar> weakReference = sWeakSnackbar;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        sWeakSnackbar.get().dismiss();
        sWeakSnackbar = null;
    }

    public static View getView() {
        Snackbar snackbar = sWeakSnackbar.get();
        if (snackbar == null) {
            return null;
        }
        return snackbar.getView();
    }

    public static void addView(int i, ViewGroup.LayoutParams layoutParams) {
        Snackbar.SnackbarLayout view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            view.addView(LayoutInflater.from(view.getContext()).inflate(i, (ViewGroup) null), -1, layoutParams);
        }
    }

    public static void addView(View view, ViewGroup.LayoutParams layoutParams) {
        Snackbar.SnackbarLayout view2 = getView();
        if (view2 != null) {
            view2.setPadding(0, 0, 0, 0);
            view2.addView(view, layoutParams);
        }
    }

    private static ViewGroup findSuitableParentCopyFromSnackbar(View view) {
        ViewGroup viewGroup = null;
        while (!(view instanceof CoordinatorLayout)) {
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }
                viewGroup = (ViewGroup) view;
            }
            if (view != null) {
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            if (view == null) {
                return viewGroup;
            }
        }
        return (ViewGroup) view;
    }
}
