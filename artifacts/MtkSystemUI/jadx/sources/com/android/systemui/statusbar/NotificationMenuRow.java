package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NotificationMenuRow implements View.OnClickListener, NotificationMenuRowPlugin, ExpandableNotificationRow.LayoutListener {
    private boolean mAnimating;
    private NotificationMenuRowPlugin.MenuItem mAppOpsItem;
    private CheckForDrag mCheckForDrag;
    private Context mContext;
    private boolean mDismissing;
    private ValueAnimator mFadeAnimator;
    private boolean mIconsPlaced;
    private NotificationMenuRowPlugin.MenuItem mInfoItem;
    private boolean mIsUserTouching;
    private FrameLayout mMenuContainer;
    private boolean mMenuFadedIn;
    private NotificationMenuRowPlugin.OnMenuEventListener mMenuListener;
    private boolean mMenuSnappedOnLeft;
    private boolean mMenuSnappedTo;
    private boolean mOnLeft;
    private ExpandableNotificationRow mParent;
    private float mPrevX;
    private boolean mShouldShowMenu;
    private boolean mSnapping;
    private NotificationMenuRowPlugin.MenuItem mSnoozeItem;
    private NotificationSwipeActionHelper mSwipeHelper;
    private float mTranslation;
    private int[] mIconLocation = new int[2];
    private int[] mParentLocation = new int[2];
    private float mHorizSpaceForIcon = -1.0f;
    private int mVertSpaceForIcons = -1;
    private int mIconPadding = -1;
    private float mAlpha = 0.0f;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ArrayList<NotificationMenuRowPlugin.MenuItem> mMenuItems = new ArrayList<>();

    public NotificationMenuRow(Context context) {
        this.mContext = context;
        this.mShouldShowMenu = context.getResources().getBoolean(R.bool.config_showNotificationGear);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public ArrayList<NotificationMenuRowPlugin.MenuItem> getMenuItems(Context context) {
        return this.mMenuItems;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public NotificationMenuRowPlugin.MenuItem getLongpressMenuItem(Context context) {
        return this.mInfoItem;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public NotificationMenuRowPlugin.MenuItem getAppOpsMenuItem(Context context) {
        return this.mAppOpsItem;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public NotificationMenuRowPlugin.MenuItem getSnoozeMenuItem(Context context) {
        return this.mSnoozeItem;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void setSwipeActionHelper(NotificationSwipeActionHelper helper) {
        this.mSwipeHelper = helper;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void setMenuClickListener(NotificationMenuRowPlugin.OnMenuEventListener listener) {
        this.mMenuListener = listener;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void createMenu(ViewGroup parent, StatusBarNotification sbn) {
        this.mParent = (ExpandableNotificationRow) parent;
        createMenuViews(true);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public boolean isMenuVisible() {
        return this.mAlpha > 0.0f;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public View getMenuView() {
        return this.mMenuContainer;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void resetMenu() {
        resetState(true);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void onNotificationUpdated(StatusBarNotification sbn) {
        if (this.mMenuContainer == null) {
            return;
        }
        createMenuViews(!isMenuVisible());
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void onConfigurationChanged() {
        this.mParent.setLayoutListener(this);
    }

    @Override // com.android.systemui.statusbar.ExpandableNotificationRow.LayoutListener
    public void onLayout() {
        this.mIconsPlaced = false;
        setMenuLocation();
        this.mParent.removeListener();
    }

    private void createMenuViews(boolean resetState) {
        Resources res = this.mContext.getResources();
        this.mHorizSpaceForIcon = res.getDimensionPixelSize(R.dimen.notification_menu_icon_size);
        this.mVertSpaceForIcons = res.getDimensionPixelSize(R.dimen.notification_min_height);
        this.mMenuItems.clear();
        if (this.mParent != null && this.mParent.getStatusBarNotification() != null) {
            int flags = this.mParent.getStatusBarNotification().getNotification().flags;
            boolean isForeground = (flags & 64) != 0;
            if (!isForeground) {
                this.mSnoozeItem = createSnoozeItem(this.mContext);
                this.mMenuItems.add(this.mSnoozeItem);
            }
        }
        this.mInfoItem = createInfoItem(this.mContext);
        this.mMenuItems.add(this.mInfoItem);
        this.mAppOpsItem = createAppOpsItem(this.mContext);
        this.mMenuItems.add(this.mAppOpsItem);
        if (this.mMenuContainer != null) {
            this.mMenuContainer.removeAllViews();
        } else {
            this.mMenuContainer = new FrameLayout(this.mContext);
        }
        for (int i = 0; i < this.mMenuItems.size(); i++) {
            addMenuView(this.mMenuItems.get(i), this.mMenuContainer);
        }
        if (resetState) {
            resetState(false);
            return;
        }
        this.mIconsPlaced = false;
        setMenuLocation();
        if (!this.mIsUserTouching) {
            showMenu(this.mParent, this.mOnLeft ? getSpaceForMenu() : -getSpaceForMenu(), 0.0f);
        }
    }

    private void resetState(boolean notify) {
        setMenuAlpha(0.0f);
        this.mIconsPlaced = false;
        this.mMenuFadedIn = false;
        this.mAnimating = false;
        this.mSnapping = false;
        this.mDismissing = false;
        this.mMenuSnappedTo = false;
        setMenuLocation();
        if (this.mMenuListener != null && notify) {
            this.mMenuListener.onMenuReset(this.mParent);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public boolean onTouchEvent(View view, MotionEvent ev, float velocity) {
        int action = ev.getActionMasked();
        switch (action) {
            case 0:
                this.mSnapping = false;
                if (this.mFadeAnimator != null) {
                    this.mFadeAnimator.cancel();
                }
                this.mHandler.removeCallbacks(this.mCheckForDrag);
                this.mCheckForDrag = null;
                this.mPrevX = ev.getRawX();
                this.mIsUserTouching = true;
                return false;
            case 1:
                this.mIsUserTouching = false;
                return handleUpEvent(ev, view, velocity);
            case 2:
                this.mSnapping = false;
                float diffX = ev.getRawX() - this.mPrevX;
                this.mPrevX = ev.getRawX();
                if (!isTowardsMenu(diffX) && isMenuLocationChange()) {
                    this.mMenuSnappedTo = false;
                    if (!this.mHandler.hasCallbacks(this.mCheckForDrag)) {
                        this.mCheckForDrag = null;
                    } else {
                        setMenuAlpha(0.0f);
                        setMenuLocation();
                    }
                }
                if (this.mShouldShowMenu && !NotificationStackScrollLayout.isPinnedHeadsUp(view) && !this.mParent.areGutsExposed() && !this.mParent.isDark() && (this.mCheckForDrag == null || !this.mHandler.hasCallbacks(this.mCheckForDrag))) {
                    this.mCheckForDrag = new CheckForDrag();
                    this.mHandler.postDelayed(this.mCheckForDrag, 60L);
                }
                return false;
            case 3:
                this.mIsUserTouching = false;
                cancelDrag();
                return false;
            default:
                return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00a2  */
    private boolean handleUpEvent(MotionEvent ev, View animView, float velocity) {
        boolean z;
        boolean withinSnapMenuThreshold = true;
        if (!this.mShouldShowMenu) {
            if (this.mSwipeHelper.isDismissGesture(ev)) {
                dismiss(animView, velocity);
            } else {
                snapBack(animView, velocity);
            }
            return true;
        }
        boolean gestureTowardsMenu = isTowardsMenu(velocity);
        boolean gestureFastEnough = this.mSwipeHelper.getMinDismissVelocity() <= Math.abs(velocity);
        this.mSwipeHelper.swipedFarEnough(this.mTranslation, this.mParent.getWidth());
        double timeForGesture = ev.getEventTime() - ev.getDownTime();
        boolean showMenuForSlowOnGoing = !this.mParent.canViewBeDismissed() && timeForGesture >= 200.0d;
        float menuSnapTarget = this.mOnLeft ? getSpaceForMenu() : -getSpaceForMenu();
        if (this.mMenuSnappedTo && isMenuVisible() && this.mMenuSnappedOnLeft == this.mOnLeft) {
            float maximumSwipeDistance = this.mHorizSpaceForIcon * 0.2f;
            float targetLeft = getSpaceForMenu() - maximumSwipeDistance;
            float targetRight = this.mParent.getWidth() * 0.6f;
            if (this.mOnLeft) {
                if (this.mTranslation <= targetLeft || this.mTranslation >= targetRight) {
                    withinSnapMenuThreshold = false;
                }
            } else if (this.mTranslation >= (-targetLeft) || this.mTranslation <= (-targetRight)) {
                withinSnapMenuThreshold = false;
            } else {
                withinSnapMenuThreshold = true;
            }
            if (this.mOnLeft) {
                z = this.mTranslation < targetLeft;
            } else if (this.mTranslation > (-targetLeft)) {
            }
            boolean shouldSnapTo = z;
            if (withinSnapMenuThreshold && !this.mSwipeHelper.isDismissGesture(ev)) {
                showMenu(animView, menuSnapTarget, velocity);
                return true;
            }
            if (this.mSwipeHelper.isDismissGesture(ev) && !shouldSnapTo) {
                dismiss(animView, velocity);
                return true;
            }
            snapBack(animView, velocity);
            return true;
        }
        if ((!this.mSwipeHelper.isFalseGesture(ev) && swipedEnoughToShowMenu() && (!gestureFastEnough || showMenuForSlowOnGoing)) || (gestureTowardsMenu && !this.mSwipeHelper.isDismissGesture(ev))) {
            showMenu(animView, menuSnapTarget, velocity);
            return true;
        }
        if (this.mSwipeHelper.isDismissGesture(ev) && !gestureTowardsMenu) {
            dismiss(animView, velocity);
            return true;
        }
        snapBack(animView, velocity);
        return true;
    }

    private void showMenu(View animView, float targetLeft, float velocity) {
        this.mMenuSnappedTo = true;
        this.mMenuSnappedOnLeft = this.mOnLeft;
        this.mMenuListener.onMenuShown(animView);
        this.mSwipeHelper.snap(animView, targetLeft, velocity);
    }

    private void snapBack(View animView, float velocity) {
        cancelDrag();
        this.mMenuSnappedTo = false;
        this.mSnapping = true;
        this.mSwipeHelper.snap(animView, 0.0f, velocity);
    }

    private void dismiss(View animView, float velocity) {
        cancelDrag();
        this.mMenuSnappedTo = false;
        this.mDismissing = true;
        this.mSwipeHelper.dismiss(animView, velocity);
    }

    private void cancelDrag() {
        if (this.mFadeAnimator != null) {
            this.mFadeAnimator.cancel();
        }
        this.mHandler.removeCallbacks(this.mCheckForDrag);
    }

    private boolean swipedEnoughToShowMenu() {
        float multiplier;
        if (this.mParent.canViewBeDismissed()) {
            multiplier = 0.25f;
        } else {
            multiplier = 0.15f;
        }
        float minimumSwipeDistance = this.mHorizSpaceForIcon * multiplier;
        return !this.mSwipeHelper.swipedFarEnough(0.0f, 0.0f) && isMenuVisible() && (!this.mOnLeft ? this.mTranslation >= (-minimumSwipeDistance) : this.mTranslation <= minimumSwipeDistance);
    }

    private boolean isTowardsMenu(float movement) {
        return isMenuVisible() && ((this.mOnLeft && movement <= 0.0f) || (!this.mOnLeft && movement >= 0.0f));
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void setAppName(String appName) {
        if (appName == null) {
            return;
        }
        Resources res = this.mContext.getResources();
        int count = this.mMenuItems.size();
        for (int i = 0; i < count; i++) {
            NotificationMenuRowPlugin.MenuItem item = this.mMenuItems.get(i);
            String description = String.format(res.getString(R.string.notification_menu_accessibility), appName, item.getContentDescription());
            View menuView = item.getMenuView();
            if (menuView != null) {
                menuView.setContentDescription(description);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void onHeightUpdate() {
        float translationY;
        if (this.mParent == null || this.mMenuItems.size() == 0 || this.mMenuContainer == null) {
            return;
        }
        int parentHeight = this.mParent.getActualHeight();
        if (parentHeight < this.mVertSpaceForIcons) {
            translationY = (parentHeight / 2) - (this.mHorizSpaceForIcon / 2.0f);
        } else {
            translationY = (this.mVertSpaceForIcons - this.mHorizSpaceForIcon) / 2.0f;
        }
        this.mMenuContainer.setTranslationY(translationY);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void onTranslationUpdate(float translation) {
        float desiredAlpha;
        this.mTranslation = translation;
        if (this.mAnimating || !this.mMenuFadedIn) {
            return;
        }
        float fadeThreshold = this.mParent.getWidth() * 0.3f;
        float absTrans = Math.abs(translation);
        if (absTrans == 0.0f) {
            desiredAlpha = 0.0f;
        } else if (absTrans <= fadeThreshold) {
            desiredAlpha = 1.0f;
        } else {
            desiredAlpha = 1.0f - ((absTrans - fadeThreshold) / (this.mParent.getWidth() - fadeThreshold));
        }
        setMenuAlpha(desiredAlpha);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (this.mMenuListener == null) {
            return;
        }
        v.getLocationOnScreen(this.mIconLocation);
        this.mParent.getLocationOnScreen(this.mParentLocation);
        int centerX = (int) (this.mHorizSpaceForIcon / 2.0f);
        int centerY = v.getHeight() / 2;
        int x = (this.mIconLocation[0] - this.mParentLocation[0]) + centerX;
        int y = (this.mIconLocation[1] - this.mParentLocation[1]) + centerY;
        int index = this.mMenuContainer.indexOfChild(v);
        this.mMenuListener.onMenuClicked(this.mParent, x, y, this.mMenuItems.get(index));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMenuLocationChange() {
        boolean onLeft = this.mTranslation > ((float) this.mIconPadding);
        boolean onRight = this.mTranslation < ((float) (-this.mIconPadding));
        return (this.mOnLeft && onRight) || (!this.mOnLeft && onLeft);
    }

    private void setMenuLocation() {
        boolean showOnLeft = this.mTranslation > 0.0f;
        if ((this.mIconsPlaced && showOnLeft == this.mOnLeft) || this.mSnapping || this.mMenuContainer == null || !this.mMenuContainer.isAttachedToWindow()) {
            return;
        }
        int count = this.mMenuContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = this.mMenuContainer.getChildAt(i);
            float left = i * this.mHorizSpaceForIcon;
            float right = this.mParent.getWidth() - (this.mHorizSpaceForIcon * (i + 1));
            v.setX(showOnLeft ? left : right);
        }
        this.mOnLeft = showOnLeft;
        this.mIconsPlaced = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMenuAlpha(float alpha) {
        this.mAlpha = alpha;
        if (this.mMenuContainer == null) {
            return;
        }
        if (alpha == 0.0f) {
            this.mMenuFadedIn = false;
            this.mMenuContainer.setVisibility(4);
        } else {
            this.mMenuContainer.setVisibility(0);
        }
        int count = this.mMenuContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            this.mMenuContainer.getChildAt(i).setAlpha(this.mAlpha);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getSpaceForMenu() {
        return this.mHorizSpaceForIcon * this.mMenuContainer.getChildCount();
    }

    private final class CheckForDrag implements Runnable {
        private CheckForDrag() {
        }

        @Override // java.lang.Runnable
        public void run() {
            float absTransX = Math.abs(NotificationMenuRow.this.mTranslation);
            float bounceBackToMenuWidth = NotificationMenuRow.this.getSpaceForMenu();
            float notiThreshold = NotificationMenuRow.this.mParent.getWidth() * 0.4f;
            if ((!NotificationMenuRow.this.isMenuVisible() || NotificationMenuRow.this.isMenuLocationChange()) && absTransX >= ((double) bounceBackToMenuWidth) * 0.4d && absTransX < notiThreshold) {
                NotificationMenuRow.this.fadeInMenu(notiThreshold);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeInMenu(final float notiThreshold) {
        if (this.mDismissing || this.mAnimating) {
            return;
        }
        if (isMenuLocationChange()) {
            setMenuAlpha(0.0f);
        }
        final float transX = this.mTranslation;
        final boolean fromLeft = this.mTranslation > 0.0f;
        setMenuLocation();
        this.mFadeAnimator = ValueAnimator.ofFloat(this.mAlpha, 1.0f);
        this.mFadeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.NotificationMenuRow.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float absTrans = Math.abs(transX);
                boolean pastMenu = (fromLeft && transX <= notiThreshold) || (!fromLeft && absTrans <= notiThreshold);
                if (pastMenu && !NotificationMenuRow.this.mMenuFadedIn) {
                    NotificationMenuRow.this.setMenuAlpha(((Float) animation.getAnimatedValue()).floatValue());
                }
            }
        });
        this.mFadeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.NotificationMenuRow.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                NotificationMenuRow.this.mAnimating = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                NotificationMenuRow.this.setMenuAlpha(0.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationMenuRow.this.mAnimating = false;
                NotificationMenuRow.this.mMenuFadedIn = NotificationMenuRow.this.mAlpha == 1.0f;
            }
        });
        this.mFadeAnimator.setInterpolator(Interpolators.ALPHA_IN);
        this.mFadeAnimator.setDuration(200L);
        this.mFadeAnimator.start();
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public void setMenuItems(ArrayList<NotificationMenuRowPlugin.MenuItem> items) {
    }

    public static NotificationMenuRowPlugin.MenuItem createSnoozeItem(Context context) {
        Resources res = context.getResources();
        NotificationSnooze content = (NotificationSnooze) LayoutInflater.from(context).inflate(R.layout.notification_snooze, (ViewGroup) null, false);
        String snoozeDescription = res.getString(R.string.notification_menu_snooze_description);
        NotificationMenuRowPlugin.MenuItem snooze = new NotificationMenuItem(context, snoozeDescription, content, R.drawable.ic_snooze);
        return snooze;
    }

    public static NotificationMenuRowPlugin.MenuItem createInfoItem(Context context) {
        Resources res = context.getResources();
        String infoDescription = res.getString(R.string.notification_menu_gear_description);
        NotificationInfo infoContent = (NotificationInfo) LayoutInflater.from(context).inflate(R.layout.notification_info, (ViewGroup) null, false);
        NotificationMenuRowPlugin.MenuItem info = new NotificationMenuItem(context, infoDescription, infoContent, R.drawable.ic_settings);
        return info;
    }

    public static NotificationMenuRowPlugin.MenuItem createAppOpsItem(Context context) {
        AppOpsInfo appOpsContent = (AppOpsInfo) LayoutInflater.from(context).inflate(R.layout.app_ops_info, (ViewGroup) null, false);
        NotificationMenuRowPlugin.MenuItem info = new NotificationMenuItem(context, null, appOpsContent, -1);
        return info;
    }

    private void addMenuView(NotificationMenuRowPlugin.MenuItem item, ViewGroup parent) {
        View menuView = item.getMenuView();
        if (menuView != null) {
            parent.addView(menuView);
            menuView.setOnClickListener(this);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) menuView.getLayoutParams();
            lp.width = (int) this.mHorizSpaceForIcon;
            lp.height = (int) this.mHorizSpaceForIcon;
            menuView.setLayoutParams(lp);
        }
    }

    public static class NotificationMenuItem implements NotificationMenuRowPlugin.MenuItem {
        String mContentDescription;
        NotificationGuts.GutsContent mGutsContent;
        View mMenuView;

        public NotificationMenuItem(Context context, String s, NotificationGuts.GutsContent content, int iconResId) {
            Resources res = context.getResources();
            int padding = res.getDimensionPixelSize(R.dimen.notification_menu_icon_padding);
            int tint = res.getColor(R.color.notification_gear_color);
            if (iconResId >= 0) {
                AlphaOptimizedImageView iv = new AlphaOptimizedImageView(context);
                iv.setPadding(padding, padding, padding, padding);
                Drawable icon = context.getResources().getDrawable(iconResId);
                iv.setImageDrawable(icon);
                iv.setColorFilter(tint);
                iv.setAlpha(1.0f);
                this.mMenuView = iv;
            }
            this.mContentDescription = s;
            this.mGutsContent = content;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public View getMenuView() {
            return this.mMenuView;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public View getGutsView() {
            return this.mGutsContent.getContentView();
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public String getContentDescription() {
            return this.mContentDescription;
        }
    }
}
