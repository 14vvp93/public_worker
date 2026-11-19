package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationEntryManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import java.util.ArrayList;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public class NotificationIconAreaController implements DarkIconDispatcher.DarkReceiver {
    private Context mContext;
    private int mIconHPadding;
    private int mIconSize;
    private final NotificationColorUtil mNotificationColorUtil;
    protected View mNotificationIconArea;
    private NotificationIconContainer mNotificationIcons;
    private NotificationStackScrollLayout mNotificationScrollLayout;
    private NotificationIconContainer mShelfIcons;
    private StatusBar mStatusBar;
    private final Runnable mUpdateStatusBarIcons = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NWCrb8vzuopzf5kAygkNeXndtBo
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.updateStatusBarIcons();
        }
    };
    private int mIconTint = -1;
    private final Rect mTintArea = new Rect();
    private final NotificationEntryManager mEntryManager = (NotificationEntryManager) Dependency.get(NotificationEntryManager.class);

    public NotificationIconAreaController(Context context, StatusBar statusBar) {
        this.mStatusBar = statusBar;
        this.mNotificationColorUtil = NotificationColorUtil.getInstance(context);
        this.mContext = context;
        initializeNotificationAreaViews(context);
    }

    protected View inflateIconArea(LayoutInflater inflater) {
        return inflater.inflate(R.layout.notification_icon_area, (ViewGroup) null);
    }

    protected void initializeNotificationAreaViews(Context context) {
        reloadDimens(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        this.mNotificationIconArea = inflateIconArea(layoutInflater);
        this.mNotificationIcons = (NotificationIconContainer) this.mNotificationIconArea.findViewById(R.id.notificationIcons);
        this.mNotificationScrollLayout = this.mStatusBar.getNotificationScrollLayout();
    }

    public void setupShelf(NotificationShelf shelf) {
        this.mShelfIcons = shelf.getShelfIcons();
        shelf.setCollapsedIcons(this.mNotificationIcons);
    }

    public void onDensityOrFontScaleChanged(Context context) {
        reloadDimens(context);
        FrameLayout.LayoutParams params = generateIconLayoutParams();
        for (int i = 0; i < this.mNotificationIcons.getChildCount(); i++) {
            View child = this.mNotificationIcons.getChildAt(i);
            child.setLayoutParams(params);
        }
        for (int i2 = 0; i2 < this.mShelfIcons.getChildCount(); i2++) {
            View child2 = this.mShelfIcons.getChildAt(i2);
            child2.setLayoutParams(params);
        }
    }

    private FrameLayout.LayoutParams generateIconLayoutParams() {
        return new FrameLayout.LayoutParams(this.mIconSize + (2 * this.mIconHPadding), getHeight());
    }

    private void reloadDimens(Context context) {
        Resources res = context.getResources();
        this.mIconSize = res.getDimensionPixelSize(R.dimen.status_bar_icon_size);
        this.mIconHPadding = res.getDimensionPixelSize(R.dimen.status_bar_icon_padding);
    }

    public View getNotificationInnerAreaView() {
        return this.mNotificationIconArea;
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect tintArea, float darkIntensity, int iconTint) {
        if (tintArea == null) {
            this.mTintArea.setEmpty();
        } else {
            this.mTintArea.set(tintArea);
        }
        if (this.mNotificationIconArea == null || DarkIconDispatcher.isInArea(tintArea, this.mNotificationIconArea)) {
            this.mIconTint = iconTint;
        }
        applyNotificationIconsTint();
    }

    protected int getHeight() {
        return this.mStatusBar.getStatusBarHeight();
    }

    protected boolean shouldShowNotificationIcon(NotificationData.Entry entry, boolean showAmbient, boolean hideDismissed, boolean hideRepliedMessages) {
        if ((this.mEntryManager.getNotificationData().isAmbient(entry.key) && !showAmbient) || !StatusBar.isTopLevelChild(entry) || entry.row.getVisibility() == 8) {
            return false;
        }
        if (entry.row.isDismissed() && hideDismissed) {
            return false;
        }
        if (hideRepliedMessages && entry.isLastMessageFromReply()) {
            return false;
        }
        return showAmbient || !this.mEntryManager.getNotificationData().shouldSuppressStatusBar(entry);
    }

    public void updateNotificationIcons() {
        updateStatusBarIcons();
        updateIconsForLayout(new Function() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationIconAreaController$UqZBoYLzFV9iQ2ZKXh5_vFY0A6w
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((NotificationData.Entry) obj).expandedIcon;
            }
        }, this.mShelfIcons, true, false, false);
        applyNotificationIconsTint();
    }

    public void updateStatusBarIcons() {
        updateIconsForLayout(new Function() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationIconAreaController$GwJ5PCGqDowcNUA9PbNBkuNSG7c
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((NotificationData.Entry) obj).icon;
            }
        }, this.mNotificationIcons, false, true, true);
    }

    private void updateIconsForLayout(Function<NotificationData.Entry, StatusBarIconView> function, NotificationIconContainer hostLayout, boolean showAmbient, boolean hideDismissed, boolean hideRepliedMessages) {
        ArrayList<StatusBarIconView> toShow = new ArrayList<>(this.mNotificationScrollLayout.getChildCount());
        for (int i = 0; i < this.mNotificationScrollLayout.getChildCount(); i++) {
            View view = this.mNotificationScrollLayout.getChildAt(i);
            if (view instanceof ExpandableNotificationRow) {
                NotificationData.Entry ent = ((ExpandableNotificationRow) view).getEntry();
                if (shouldShowNotificationIcon(ent, showAmbient, hideDismissed, hideRepliedMessages)) {
                    toShow.add(function.apply(ent));
                }
            }
        }
        ArrayMap<String, ArrayList<StatusBarIcon>> replacingIcons = new ArrayMap<>();
        ArrayList<View> toRemove = new ArrayList<>();
        for (int i2 = 0; i2 < hostLayout.getChildCount(); i2++) {
            View child = hostLayout.getChildAt(i2);
            if ((child instanceof StatusBarIconView) && !toShow.contains(child)) {
                StatusBarIconView removedIcon = (StatusBarIconView) child;
                String removedGroupKey = removedIcon.getNotification().getGroupKey();
                boolean iconWasReplaced = false;
                for (int j = 0; j < toShow.size(); j++) {
                    StatusBarIconView candidate = toShow.get(j);
                    if (candidate.getSourceIcon().sameAs(removedIcon.getSourceIcon()) && candidate.getNotification().getGroupKey().equals(removedGroupKey)) {
                        if (!iconWasReplaced) {
                            iconWasReplaced = true;
                        } else {
                            iconWasReplaced = false;
                            break;
                        }
                    }
                }
                if (iconWasReplaced) {
                    ArrayList<StatusBarIcon> statusBarIcons = replacingIcons.get(removedGroupKey);
                    if (statusBarIcons == null) {
                        statusBarIcons = new ArrayList<>();
                        replacingIcons.put(removedGroupKey, statusBarIcons);
                    }
                    statusBarIcons.add(removedIcon.getStatusBarIcon());
                }
                toRemove.add(removedIcon);
            }
        }
        ArrayList<String> duplicates = new ArrayList<>();
        for (String key : replacingIcons.keySet()) {
            if (replacingIcons.get(key).size() != 1) {
                duplicates.add(key);
            }
        }
        replacingIcons.removeAll(duplicates);
        hostLayout.setReplacingIcons(replacingIcons);
        int toRemoveCount = toRemove.size();
        for (int i3 = 0; i3 < toRemoveCount; i3++) {
            hostLayout.removeView(toRemove.get(i3));
        }
        FrameLayout.LayoutParams params = generateIconLayoutParams();
        for (int i4 = 0; i4 < toShow.size(); i4++) {
            StatusBarIconView v = toShow.get(i4);
            hostLayout.removeTransientView(v);
            if (v.getParent() == null) {
                if (hideDismissed) {
                    v.setOnDismissListener(this.mUpdateStatusBarIcons);
                }
                hostLayout.addView(v, i4, params);
            }
        }
        hostLayout.setChangingViewPositions(true);
        int childCount = hostLayout.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View actual = hostLayout.getChildAt(i5);
            StatusBarIconView expected = toShow.get(i5);
            if (actual != expected) {
                hostLayout.removeView(expected);
                hostLayout.addView(expected, i5);
            }
        }
        hostLayout.setChangingViewPositions(false);
        hostLayout.setReplacingIcons(null);
    }

    private void applyNotificationIconsTint() {
        for (int i = 0; i < this.mNotificationIcons.getChildCount(); i++) {
            final StatusBarIconView iv = (StatusBarIconView) this.mNotificationIcons.getChildAt(i);
            if (iv.getWidth() != 0) {
                updateTintForIcon(iv);
            } else {
                iv.executeOnLayout(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationIconAreaController$hxZzEn_IxyrKECdSreC0o-qUafc
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.updateTintForIcon(iv);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTintForIcon(StatusBarIconView v) {
        Boolean.TRUE.equals(v.getTag(R.id.icon_is_pre_L));
        int color = 0;
        boolean colorize = NotificationUtils.isGrayscale(v, this.mNotificationColorUtil);
        if (colorize) {
            color = DarkIconDispatcher.getTint(this.mTintArea, v, this.mIconTint);
        }
        v.setStaticDrawableColor(color);
        v.setDecorColor(this.mIconTint);
    }

    public void showIconIsolated(StatusBarIconView icon, boolean animated) {
        this.mNotificationIcons.showIconIsolated(icon, animated);
    }

    public void setIsolatedIconLocation(Rect iconDrawingRect, boolean requireStateUpdate) {
        this.mNotificationIcons.setIsolatedIconLocation(iconDrawingRect, requireStateUpdate);
    }
}
