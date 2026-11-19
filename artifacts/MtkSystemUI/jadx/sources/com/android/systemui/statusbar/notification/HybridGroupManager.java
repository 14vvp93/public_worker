package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class HybridGroupManager {
    private final Context mContext;
    private float mDarkAmount = 0.0f;
    private final NotificationDozeHelper mDozer = new NotificationDozeHelper();
    private int mOverflowNumberColor;
    private int mOverflowNumberColorDark;
    private int mOverflowNumberPadding;
    private int mOverflowNumberPaddingDark;
    private float mOverflowNumberSize;
    private float mOverflowNumberSizeDark;
    private final ViewGroup mParent;

    public HybridGroupManager(Context ctx, ViewGroup parent) {
        this.mContext = ctx;
        this.mParent = parent;
        initDimens();
    }

    public void initDimens() {
        Resources res = this.mContext.getResources();
        this.mOverflowNumberSize = res.getDimensionPixelSize(R.dimen.group_overflow_number_size);
        this.mOverflowNumberSizeDark = res.getDimensionPixelSize(R.dimen.group_overflow_number_size_dark);
        this.mOverflowNumberPadding = res.getDimensionPixelSize(R.dimen.group_overflow_number_padding);
        this.mOverflowNumberPaddingDark = this.mOverflowNumberPadding + res.getDimensionPixelSize(R.dimen.group_overflow_number_extra_padding_dark);
    }

    private HybridNotificationView inflateHybridViewWithStyle(int style) {
        LayoutInflater inflater = (LayoutInflater) new ContextThemeWrapper(this.mContext, style).getSystemService(LayoutInflater.class);
        HybridNotificationView hybrid = (HybridNotificationView) inflater.inflate(R.layout.hybrid_notification, this.mParent, false);
        this.mParent.addView(hybrid);
        return hybrid;
    }

    private TextView inflateOverflowNumber() {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(LayoutInflater.class);
        TextView numberView = (TextView) inflater.inflate(R.layout.hybrid_overflow_number, this.mParent, false);
        this.mParent.addView(numberView);
        updateOverFlowNumberColor(numberView);
        return numberView;
    }

    private void updateOverFlowNumberColor(TextView numberView) {
        numberView.setTextColor(NotificationUtils.interpolateColors(this.mOverflowNumberColor, this.mOverflowNumberColorDark, this.mDarkAmount));
    }

    public void setOverflowNumberColor(TextView numberView, int colorRegular, int colorDark) {
        this.mOverflowNumberColor = colorRegular;
        this.mOverflowNumberColorDark = colorDark;
        if (numberView != null) {
            updateOverFlowNumberColor(numberView);
        }
    }

    public HybridNotificationView bindFromNotification(HybridNotificationView reusableView, Notification notification) {
        return bindFromNotificationWithStyle(reusableView, notification, R.style.HybridNotification);
    }

    public HybridNotificationView bindAmbientFromNotification(HybridNotificationView reusableView, Notification notification) {
        return bindFromNotificationWithStyle(reusableView, notification, 2131886289);
    }

    private HybridNotificationView bindFromNotificationWithStyle(HybridNotificationView reusableView, Notification notification, int style) {
        if (reusableView == null) {
            reusableView = inflateHybridViewWithStyle(style);
        }
        CharSequence titleText = resolveTitle(notification);
        CharSequence contentText = resolveText(notification);
        reusableView.bind(titleText, contentText);
        return reusableView;
    }

    private CharSequence resolveText(Notification notification) {
        CharSequence contentText = notification.extras.getCharSequence("android.text");
        if (contentText == null) {
            return notification.extras.getCharSequence("android.bigText");
        }
        return contentText;
    }

    private CharSequence resolveTitle(Notification notification) {
        CharSequence titleText = notification.extras.getCharSequence("android.title");
        if (titleText == null) {
            return notification.extras.getCharSequence("android.title.big");
        }
        return titleText;
    }

    public TextView bindOverflowNumber(TextView reusableView, int number) {
        if (reusableView == null) {
            reusableView = inflateOverflowNumber();
        }
        String text = this.mContext.getResources().getString(R.string.notification_group_overflow_indicator, Integer.valueOf(number));
        if (!text.equals(reusableView.getText())) {
            reusableView.setText(text);
        }
        String contentDescription = String.format(this.mContext.getResources().getQuantityString(R.plurals.notification_group_overflow_description, number), Integer.valueOf(number));
        reusableView.setContentDescription(contentDescription);
        return reusableView;
    }

    public TextView bindOverflowNumberAmbient(TextView titleView, Notification notification, int number) {
        String text = this.mContext.getResources().getString(R.string.notification_group_overflow_indicator_ambient, resolveTitle(notification), Integer.valueOf(number));
        if (!text.equals(titleView.getText())) {
            titleView.setText(text);
        }
        return titleView;
    }

    public void setOverflowNumberDark(final TextView view, boolean dark, boolean fade, long delay) {
        this.mDozer.setIntensityDark(new Consumer() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$HybridGroupManager$F4-Ues96OqS7pldtinZIwLPoyPg
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                HybridGroupManager.lambda$setOverflowNumberDark$0(this.f$0, view, (Float) obj);
            }
        }, dark, fade, delay, view);
        view.setTextSize(0, dark ? this.mOverflowNumberSizeDark : this.mOverflowNumberSize);
        int paddingEnd = dark ? this.mOverflowNumberPaddingDark : this.mOverflowNumberPadding;
        view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(), paddingEnd, view.getPaddingBottom());
    }

    public static /* synthetic */ void lambda$setOverflowNumberDark$0(HybridGroupManager hybridGroupManager, TextView view, Float f) {
        hybridGroupManager.mDarkAmount = f.floatValue();
        hybridGroupManager.updateOverFlowNumberColor(view);
    }
}
