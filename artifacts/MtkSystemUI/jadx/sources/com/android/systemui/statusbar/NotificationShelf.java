package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.stack.AmbientState;
import com.android.systemui.statusbar.stack.AnimationProperties;
import com.android.systemui.statusbar.stack.ExpandableViewState;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.stack.StackScrollState;
import com.android.systemui.statusbar.stack.ViewState;

/* JADX INFO: loaded from: classes.dex */
public class NotificationShelf extends ActivatableNotificationView implements View.OnLayoutChangeListener {
    private AmbientState mAmbientState;
    private boolean mAnimationsEnabled;
    private Rect mClipRect;
    private NotificationIconContainer mCollapsedIcons;
    private boolean mDark;
    private float mFirstElementRoundness;
    private boolean mHasItemsInStableShelf;
    private boolean mHideBackground;
    private NotificationStackScrollLayout mHostLayout;
    private int mIconAppearTopPadding;
    private int mIconSize;
    private boolean mInteractive;
    private int mMaxLayoutHeight;
    private float mMaxShelfEnd;
    private boolean mNoAnimationsInThisFrame;
    private int mNotGoneIndex;
    private float mOpenedAmount;
    private int mPaddingBetweenElements;
    private int mRelativeOffset;
    private int mScrollFastThreshold;
    private int mShelfAppearTranslation;
    private NotificationIconContainer mShelfIcons;
    private ShelfState mShelfState;
    private boolean mShowNotificationShelf;
    private int mStatusBarHeight;
    private int mStatusBarPaddingStart;
    private int mStatusBarState;
    private int[] mTmp;
    private static final boolean USE_ANIMATIONS_WHEN_OPENING = SystemProperties.getBoolean("debug.icon_opening_animations", true);
    private static final boolean ICON_ANMATIONS_WHILE_SCROLLING = SystemProperties.getBoolean("debug.icon_scroll_animations", true);

    public NotificationShelf(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTmp = new int[2];
        this.mAnimationsEnabled = true;
        this.mClipRect = new Rect();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mShelfIcons = (NotificationIconContainer) findViewById(R.id.content);
        this.mShelfIcons.setClipChildren(false);
        this.mShelfIcons.setClipToPadding(false);
        setClipToActualHeight(false);
        setClipChildren(false);
        setClipToPadding(false);
        this.mShelfIcons.setIsStaticLayout(false);
        this.mShelfState = new ShelfState();
        setBottomRoundness(1.0f, false);
        initDimens();
    }

    public void bind(AmbientState ambientState, NotificationStackScrollLayout hostLayout) {
        this.mAmbientState = ambientState;
        this.mHostLayout = hostLayout;
    }

    private void initDimens() {
        Resources res = getResources();
        this.mIconAppearTopPadding = res.getDimensionPixelSize(R.dimen.notification_icon_appear_padding);
        this.mStatusBarHeight = res.getDimensionPixelOffset(R.dimen.status_bar_height);
        this.mStatusBarPaddingStart = res.getDimensionPixelOffset(R.dimen.status_bar_padding_start);
        this.mPaddingBetweenElements = res.getDimensionPixelSize(R.dimen.notification_divider_height);
        this.mShelfAppearTranslation = res.getDimensionPixelSize(R.dimen.shelf_appear_translation);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = res.getDimensionPixelOffset(R.dimen.notification_shelf_height);
        setLayoutParams(layoutParams);
        int padding = res.getDimensionPixelOffset(R.dimen.shelf_icon_container_padding);
        this.mShelfIcons.setPadding(padding, 0, padding, 0);
        this.mScrollFastThreshold = res.getDimensionPixelOffset(R.dimen.scroll_fast_threshold);
        this.mShowNotificationShelf = res.getBoolean(R.bool.config_showNotificationShelf);
        this.mIconSize = res.getDimensionPixelSize(R.dimen.status_bar_icon_size);
        if (!this.mShowNotificationShelf) {
            setVisibility(8);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initDimens();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableView
    public void setDark(boolean dark, boolean fade, long delay) {
        super.setDark(dark, fade, delay);
        if (this.mDark == dark) {
            return;
        }
        this.mDark = dark;
        this.mShelfIcons.setDark(dark, fade, delay);
        updateInteractiveness();
    }

    public void fadeInTranslating() {
        float translation = this.mShelfIcons.getTranslationY();
        this.mShelfIcons.setTranslationY(translation - this.mShelfAppearTranslation);
        this.mShelfIcons.setAlpha(0.0f);
        this.mShelfIcons.animate().setInterpolator(Interpolators.DECELERATE_QUINT).translationY(translation).setDuration(200L).start();
        this.mShelfIcons.animate().alpha(1.0f).setInterpolator(Interpolators.LINEAR).setDuration(200L).start();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected View getContentView() {
        return this.mShelfIcons;
    }

    public NotificationIconContainer getShelfIcons() {
        return this.mShelfIcons;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public ExpandableViewState createNewViewState(StackScrollState stackScrollState) {
        return this.mShelfState;
    }

    public void updateState(StackScrollState resultState, AmbientState ambientState) {
        View lastView = ambientState.getLastVisibleBackgroundChild();
        boolean z = true;
        if (this.mShowNotificationShelf && lastView != null) {
            float maxShelfEnd = ambientState.getInnerHeight() + ambientState.getTopPadding() + ambientState.getStackTranslation();
            ExpandableViewState lastViewState = resultState.getViewStateForView(lastView);
            if (lastViewState == null) {
                Log.e("NotificationShelf", "NullPointerException: lastViewState is null! return.");
                return;
            }
            float viewEnd = lastViewState.yTranslation + lastViewState.height;
            this.mShelfState.copyFrom(lastViewState);
            this.mShelfState.height = getIntrinsicHeight();
            float awakenTranslation = Math.max(Math.min(viewEnd, maxShelfEnd) - this.mShelfState.height, getFullyClosedTranslation());
            float darkTranslation = this.mAmbientState.getDarkTopPadding();
            float yRatio = this.mAmbientState.hasPulsingNotifications() ? 0.0f : this.mAmbientState.getDarkAmount();
            this.mShelfState.yTranslation = MathUtils.lerp(awakenTranslation, darkTranslation, yRatio);
            this.mShelfState.zTranslation = ambientState.getBaseZHeight();
            float openedAmount = (this.mShelfState.yTranslation - getFullyClosedTranslation()) / (getIntrinsicHeight() * 2);
            this.mShelfState.openedAmount = Math.min(1.0f, openedAmount);
            this.mShelfState.clipTopAmount = 0;
            this.mShelfState.alpha = this.mAmbientState.hasPulsingNotifications() ? 0.0f : 1.0f;
            this.mShelfState.belowSpeedBump = this.mAmbientState.getSpeedBumpIndex() == 0;
            this.mShelfState.shadowAlpha = 1.0f;
            this.mShelfState.hideSensitive = false;
            this.mShelfState.xTranslation = getTranslationX();
            if (this.mNotGoneIndex != -1) {
                this.mShelfState.notGoneIndex = Math.min(this.mShelfState.notGoneIndex, this.mNotGoneIndex);
            }
            this.mShelfState.hasItemsInStableShelf = lastViewState.inShelf;
            ShelfState shelfState = this.mShelfState;
            if (this.mAmbientState.isShadeExpanded() && !this.mAmbientState.isQsCustomizerShowing()) {
                z = false;
            }
            shelfState.hidden = z;
            this.mShelfState.maxShelfEnd = maxShelfEnd;
            return;
        }
        this.mShelfState.hidden = true;
        this.mShelfState.location = 64;
        this.mShelfState.hasItemsInStableShelf = false;
    }

    /* JADX WARN: Code duplicated, block: B:69:0x0174  */
    /* JADX WARN: Code duplicated, block: B:71:0x017b  */
    /* JADX WARN: Code duplicated, block: B:72:0x018c  */
    /* JADX WARN: Code duplicated, block: B:73:0x0191  */
    /* JADX WARN: Code duplicated, block: B:76:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:77:0x01a6 A[PHI: r3 r6 r34 r35 r36
      0x01a6: PHI (r3v21 'previousColor' int) = (r21v1 'previousColor' int), (r3v24 'previousColor' int) binds: [B:75:0x019e, B:71:0x017b] A[DONT_GENERATE, DONT_INLINE]
      0x01a6: PHI (r6v15 'transitionAmount' float) = (r22v1 'transitionAmount' float), (r6v18 'transitionAmount' float) binds: [B:75:0x019e, B:71:0x017b] A[DONT_GENERATE, DONT_INLINE]
      0x01a6: PHI (r34v7 'scrolling' boolean) = (r34v5 'scrolling' boolean), (r34v8 'scrolling' boolean) binds: [B:75:0x019e, B:71:0x017b] A[DONT_GENERATE, DONT_INLINE]
      0x01a6: PHI (r35v6 'scrollingFast' boolean) = (r35v4 'scrollingFast' boolean), (r35v7 'scrollingFast' boolean) binds: [B:75:0x019e, B:71:0x017b] A[DONT_GENERATE, DONT_INLINE]
      0x01a6: PHI (r36v6 'expandingAnimated' boolean) = (r36v4 'expandingAnimated' boolean), (r36v7 'expandingAnimated' boolean) binds: [B:75:0x019e, B:71:0x017b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:79:0x01a9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:80:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:82:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:84:0x01c1 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:85:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:87:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:89:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:92:0x01f4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:94:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:96:0x0219  */
    /* JADX WARN: Code duplicated, block: B:97:0x0237  */
    /* JADX WARN: Code duplicated, block: B:99:0x0254  */
    /* JADX WARN: Instruction removed from duplicated block: B:96:0x0219, please report this as an issue */
    public void updateAppearance() {
        int i;
        float notificationClipEnd;
        float fMin;
        float interpolationStart;
        float inShelfAmount;
        int ownColorUntinted;
        int colorTwoBefore;
        int colorOfViewBeforeLast;
        boolean z;
        float firstElementRoundness;
        NotificationIconContainer.IconState iconState;
        String str;
        if (!this.mShowNotificationShelf) {
            return;
        }
        this.mShelfIcons.resetViewStates();
        float shelfStart = getTranslationY();
        View lastChild = this.mAmbientState.getLastVisibleBackgroundChild();
        this.mNotGoneIndex = -1;
        float interpolationStart2 = this.mMaxLayoutHeight - (getIntrinsicHeight() * 2);
        float expandAmount = 0.0f;
        if (shelfStart >= interpolationStart2) {
            float expandAmount2 = (shelfStart - interpolationStart2) / getIntrinsicHeight();
            expandAmount = Math.min(1.0f, expandAmount2);
        }
        float expandAmount3 = expandAmount;
        boolean backgroundForceHidden = false;
        if (this.mHideBackground && !this.mShelfState.hasItemsInStableShelf) {
            backgroundForceHidden = true;
        }
        boolean backgroundForceHidden2 = backgroundForceHidden;
        float currentScrollVelocity = this.mAmbientState.getCurrentScrollVelocity();
        boolean scrollingFast = currentScrollVelocity > ((float) this.mScrollFastThreshold) || (this.mAmbientState.isExpansionChanging() && Math.abs(this.mAmbientState.getExpandingVelocity()) > ((float) this.mScrollFastThreshold));
        boolean scrolling = currentScrollVelocity > 0.0f;
        boolean expandingAnimated = this.mAmbientState.isExpansionChanging() && !this.mAmbientState.isPanelTracking();
        int baseZHeight = this.mAmbientState.getBaseZHeight();
        int previousColor = 0;
        float transitionAmount = 0.0f;
        int backgroundTop = 0;
        float numViewsInShelf = 0.0f;
        int colorOfViewBeforeLast2 = 0;
        int i2 = 0;
        int notGoneIndex = 0;
        int previousColor2 = 0;
        float transitionAmount2 = 0.0f;
        while (true) {
            int colorTwoBefore2 = previousColor2;
            if (i2 >= this.mHostLayout.getChildCount()) {
                break;
            }
            ExpandableView child = (ExpandableView) this.mHostLayout.getChildAt(i2);
            if (child instanceof ExpandableNotificationRow) {
                i = i2;
                if (child.getVisibility() != 8) {
                    ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                    int notGoneIndex2 = notGoneIndex;
                    boolean aboveShelf = ViewState.getFinalTranslationZ(row) > ((float) baseZHeight) || row.isPinned();
                    boolean isLastChild = child == lastChild;
                    float rowTranslationY = row.getTranslationY();
                    if ((isLastChild && !child.isInShelf()) || aboveShelf || backgroundForceHidden2) {
                        fMin = getIntrinsicHeight() + shelfStart;
                    } else {
                        float notificationClipEnd2 = shelfStart - this.mPaddingBetweenElements;
                        float height = notificationClipEnd2 - rowTranslationY;
                        if (row.isBelowSpeedBump() || height > getNotificationMergeSize()) {
                            notificationClipEnd = notificationClipEnd2;
                        } else {
                            fMin = Math.min(shelfStart, rowTranslationY + getNotificationMergeSize());
                        }
                        updateNotificationClipHeight(row, notificationClipEnd);
                        lastChild = lastChild;
                        interpolationStart2 = interpolationStart2;
                        interpolationStart = transitionAmount2;
                        inShelfAmount = updateIconAppearance(row, expandAmount3, scrolling, scrollingFast, expandingAnimated, isLastChild);
                        numViewsInShelf += inShelfAmount;
                        ownColorUntinted = row.getBackgroundColorWithoutTint();
                        if (rowTranslationY >= shelfStart) {
                            scrolling = scrolling;
                            if (this.mNotGoneIndex == -1) {
                                this.mNotGoneIndex = notGoneIndex2;
                                previousColor = previousColor;
                                setTintColor(previousColor);
                                transitionAmount = transitionAmount;
                                setOverrideTintColor(colorTwoBefore2, transitionAmount);
                                scrollingFast = scrollingFast;
                                expandingAnimated = expandingAnimated;
                            }
                            colorTwoBefore = colorTwoBefore2;
                            if (isLastChild) {
                                if (colorOfViewBeforeLast2 == 0) {
                                    colorOfViewBeforeLast2 = ownColorUntinted;
                                }
                                colorOfViewBeforeLast = colorOfViewBeforeLast2;
                                row.setOverrideTintColor(colorOfViewBeforeLast, inShelfAmount);
                                z = false;
                            } else {
                                colorOfViewBeforeLast = ownColorUntinted;
                                z = false;
                                row.setOverrideTintColor(0, 0.0f);
                            }
                            if (notGoneIndex2 == 0 || !aboveShelf) {
                                row.setAboveShelf(z);
                            }
                            if (notGoneIndex2 == 0) {
                                StatusBarIconView icon = row.getEntry().expandedIcon;
                                iconState = getIconState(icon);
                                if (iconState == null && iconState.clampedAppearAmount == 1.0f) {
                                    backgroundTop = (int) (row.getTranslationY() - getTranslationY());
                                    firstElementRoundness = row.getCurrentTopRoundness();
                                    colorTwoBefore = colorTwoBefore;
                                    colorOfViewBeforeLast = colorOfViewBeforeLast;
                                } else {
                                    if (iconState == null) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("iconState is null. ExpandedIcon: ");
                                        sb.append(row.getEntry().expandedIcon);
                                        if (row.getEntry().expandedIcon != null) {
                                            str = "\n icon parent: " + row.getEntry().expandedIcon.getParent();
                                        } else {
                                            str = "";
                                        }
                                        sb.append(str);
                                        sb.append(" \n number of notifications: ");
                                        sb.append(this.mHostLayout.getChildCount());
                                        Log.wtf("NotificationShelf", sb.toString());
                                    } else {
                                        colorOfViewBeforeLast = colorOfViewBeforeLast;
                                    }
                                    firstElementRoundness = interpolationStart;
                                }
                            } else {
                                colorOfViewBeforeLast = colorOfViewBeforeLast;
                                firstElementRoundness = interpolationStart;
                            }
                            previousColor = ownColorUntinted;
                            notGoneIndex = notGoneIndex2 + 1;
                            transitionAmount = transitionAmount;
                            previousColor2 = colorTwoBefore;
                            colorOfViewBeforeLast2 = colorOfViewBeforeLast;
                            transitionAmount2 = firstElementRoundness;
                        } else {
                            scrolling = scrolling;
                        }
                        scrollingFast = scrollingFast;
                        expandingAnimated = expandingAnimated;
                        if (this.mNotGoneIndex == -1) {
                            colorTwoBefore = previousColor;
                            transitionAmount = inShelfAmount;
                        } else {
                            colorTwoBefore = colorTwoBefore2;
                        }
                        if (isLastChild) {
                            if (colorOfViewBeforeLast2 == 0) {
                                colorOfViewBeforeLast2 = ownColorUntinted;
                            }
                            colorOfViewBeforeLast = colorOfViewBeforeLast2;
                            row.setOverrideTintColor(colorOfViewBeforeLast, inShelfAmount);
                            z = false;
                        } else {
                            colorOfViewBeforeLast = ownColorUntinted;
                            z = false;
                            row.setOverrideTintColor(0, 0.0f);
                        }
                        if (notGoneIndex2 == 0) {
                            row.setAboveShelf(z);
                        } else {
                            row.setAboveShelf(z);
                        }
                        if (notGoneIndex2 == 0) {
                            StatusBarIconView icon2 = row.getEntry().expandedIcon;
                            iconState = getIconState(icon2);
                            if (iconState == null) {
                            }
                            if (iconState == null) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("iconState is null. ExpandedIcon: ");
                                sb2.append(row.getEntry().expandedIcon);
                                if (row.getEntry().expandedIcon != null) {
                                    str = "\n icon parent: " + row.getEntry().expandedIcon.getParent();
                                } else {
                                    str = "";
                                }
                                sb2.append(str);
                                sb2.append(" \n number of notifications: ");
                                sb2.append(this.mHostLayout.getChildCount());
                                Log.wtf("NotificationShelf", sb2.toString());
                            } else {
                                colorOfViewBeforeLast = colorOfViewBeforeLast;
                            }
                            firstElementRoundness = interpolationStart;
                        } else {
                            colorOfViewBeforeLast = colorOfViewBeforeLast;
                            firstElementRoundness = interpolationStart;
                        }
                        previousColor = ownColorUntinted;
                        notGoneIndex = notGoneIndex2 + 1;
                        transitionAmount = transitionAmount;
                        previousColor2 = colorTwoBefore;
                        colorOfViewBeforeLast2 = colorOfViewBeforeLast;
                        transitionAmount2 = firstElementRoundness;
                    }
                    notificationClipEnd = fMin;
                    updateNotificationClipHeight(row, notificationClipEnd);
                    lastChild = lastChild;
                    interpolationStart2 = interpolationStart2;
                    interpolationStart = transitionAmount2;
                    inShelfAmount = updateIconAppearance(row, expandAmount3, scrolling, scrollingFast, expandingAnimated, isLastChild);
                    numViewsInShelf += inShelfAmount;
                    ownColorUntinted = row.getBackgroundColorWithoutTint();
                    if (rowTranslationY >= shelfStart) {
                        scrolling = scrolling;
                        if (this.mNotGoneIndex == -1) {
                            this.mNotGoneIndex = notGoneIndex2;
                            previousColor = previousColor;
                            setTintColor(previousColor);
                            transitionAmount = transitionAmount;
                            setOverrideTintColor(colorTwoBefore2, transitionAmount);
                            scrollingFast = scrollingFast;
                            expandingAnimated = expandingAnimated;
                        }
                        colorTwoBefore = colorTwoBefore2;
                        if (isLastChild) {
                            if (colorOfViewBeforeLast2 == 0) {
                                colorOfViewBeforeLast2 = ownColorUntinted;
                            }
                            colorOfViewBeforeLast = colorOfViewBeforeLast2;
                            row.setOverrideTintColor(colorOfViewBeforeLast, inShelfAmount);
                            z = false;
                        } else {
                            colorOfViewBeforeLast = ownColorUntinted;
                            z = false;
                            row.setOverrideTintColor(0, 0.0f);
                        }
                        if (notGoneIndex2 == 0) {
                            row.setAboveShelf(z);
                        } else {
                            row.setAboveShelf(z);
                        }
                        if (notGoneIndex2 == 0) {
                            StatusBarIconView icon3 = row.getEntry().expandedIcon;
                            iconState = getIconState(icon3);
                            if (iconState == null) {
                            }
                            if (iconState == null) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("iconState is null. ExpandedIcon: ");
                                sb3.append(row.getEntry().expandedIcon);
                                if (row.getEntry().expandedIcon != null) {
                                    str = "\n icon parent: " + row.getEntry().expandedIcon.getParent();
                                } else {
                                    str = "";
                                }
                                sb3.append(str);
                                sb3.append(" \n number of notifications: ");
                                sb3.append(this.mHostLayout.getChildCount());
                                Log.wtf("NotificationShelf", sb3.toString());
                            } else {
                                colorOfViewBeforeLast = colorOfViewBeforeLast;
                            }
                            firstElementRoundness = interpolationStart;
                        } else {
                            colorOfViewBeforeLast = colorOfViewBeforeLast;
                            firstElementRoundness = interpolationStart;
                        }
                        previousColor = ownColorUntinted;
                        notGoneIndex = notGoneIndex2 + 1;
                        transitionAmount = transitionAmount;
                        previousColor2 = colorTwoBefore;
                        colorOfViewBeforeLast2 = colorOfViewBeforeLast;
                        transitionAmount2 = firstElementRoundness;
                    } else {
                        scrolling = scrolling;
                    }
                    scrollingFast = scrollingFast;
                    expandingAnimated = expandingAnimated;
                    if (this.mNotGoneIndex == -1) {
                        colorTwoBefore = previousColor;
                        transitionAmount = inShelfAmount;
                    } else {
                        colorTwoBefore = colorTwoBefore2;
                    }
                    if (isLastChild) {
                        if (colorOfViewBeforeLast2 == 0) {
                            colorOfViewBeforeLast2 = ownColorUntinted;
                        }
                        colorOfViewBeforeLast = colorOfViewBeforeLast2;
                        row.setOverrideTintColor(colorOfViewBeforeLast, inShelfAmount);
                        z = false;
                    } else {
                        colorOfViewBeforeLast = ownColorUntinted;
                        z = false;
                        row.setOverrideTintColor(0, 0.0f);
                    }
                    if (notGoneIndex2 == 0) {
                        row.setAboveShelf(z);
                    } else {
                        row.setAboveShelf(z);
                    }
                    if (notGoneIndex2 == 0) {
                        StatusBarIconView icon4 = row.getEntry().expandedIcon;
                        iconState = getIconState(icon4);
                        if (iconState == null) {
                        }
                        if (iconState == null) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("iconState is null. ExpandedIcon: ");
                            sb4.append(row.getEntry().expandedIcon);
                            if (row.getEntry().expandedIcon != null) {
                                str = "\n icon parent: " + row.getEntry().expandedIcon.getParent();
                            } else {
                                str = "";
                            }
                            sb4.append(str);
                            sb4.append(" \n number of notifications: ");
                            sb4.append(this.mHostLayout.getChildCount());
                            Log.wtf("NotificationShelf", sb4.toString());
                        } else {
                            colorOfViewBeforeLast = colorOfViewBeforeLast;
                        }
                        firstElementRoundness = interpolationStart;
                    } else {
                        colorOfViewBeforeLast = colorOfViewBeforeLast;
                        firstElementRoundness = interpolationStart;
                    }
                    previousColor = ownColorUntinted;
                    notGoneIndex = notGoneIndex2 + 1;
                    transitionAmount = transitionAmount;
                    previousColor2 = colorTwoBefore;
                    colorOfViewBeforeLast2 = colorOfViewBeforeLast;
                    transitionAmount2 = firstElementRoundness;
                }
                i2 = i + 1;
                lastChild = lastChild;
                interpolationStart2 = interpolationStart2;
                scrolling = scrolling;
                scrollingFast = scrollingFast;
                expandingAnimated = expandingAnimated;
            } else {
                i = i2;
            }
            transitionAmount = transitionAmount;
            previousColor2 = colorTwoBefore2;
            notGoneIndex = notGoneIndex;
            transitionAmount2 = transitionAmount2;
            previousColor = previousColor;
            i2 = i + 1;
            lastChild = lastChild;
            interpolationStart2 = interpolationStart2;
            scrolling = scrolling;
            scrollingFast = scrollingFast;
            expandingAnimated = expandingAnimated;
        }
        int notGoneIndex3 = notGoneIndex;
        clipTransientViews();
        setBackgroundTop(backgroundTop);
        setFirstElementRoundness(transitionAmount2);
        this.mShelfIcons.setSpeedBumpIndex(this.mAmbientState.getSpeedBumpIndex());
        this.mShelfIcons.calculateIconTranslations();
        this.mShelfIcons.applyIconStates();
        for (int i3 = 0; i3 < this.mHostLayout.getChildCount(); i3++) {
            View child2 = this.mHostLayout.getChildAt(i3);
            if ((child2 instanceof ExpandableNotificationRow) && child2.getVisibility() != 8) {
                ExpandableNotificationRow row2 = (ExpandableNotificationRow) child2;
                updateIconClipAmount(row2);
                updateContinuousClipping(row2);
            }
        }
        boolean hideBackground = numViewsInShelf < 1.0f;
        setHideBackground(hideBackground || backgroundForceHidden2);
        if (this.mNotGoneIndex == -1) {
            this.mNotGoneIndex = notGoneIndex3;
        }
    }

    private void clipTransientViews() {
        for (int i = 0; i < this.mHostLayout.getTransientViewCount(); i++) {
            View transientView = this.mHostLayout.getTransientView(i);
            if (transientView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow transientRow = (ExpandableNotificationRow) transientView;
                updateNotificationClipHeight(transientRow, getTranslationY());
            } else {
                Log.e("NotificationShelf", "NotificationShelf.clipTransientViews(): Trying to clip non-row transient view");
            }
        }
    }

    private void setFirstElementRoundness(float firstElementRoundness) {
        if (this.mFirstElementRoundness != firstElementRoundness) {
            this.mFirstElementRoundness = firstElementRoundness;
            setTopRoundness(firstElementRoundness, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIconClipAmount(ExpandableNotificationRow row) {
        float maxTop = row.getTranslationY();
        StatusBarIconView icon = row.getEntry().expandedIcon;
        float shelfIconPosition = getTranslationY() + icon.getTop() + icon.getTranslationY();
        if (shelfIconPosition < maxTop && !this.mAmbientState.isDark()) {
            int top = (int) (maxTop - shelfIconPosition);
            Rect clipRect = new Rect(0, top, icon.getWidth(), Math.max(top, icon.getHeight()));
            icon.setClipBounds(clipRect);
            return;
        }
        icon.setClipBounds(null);
    }

    private void updateContinuousClipping(final ExpandableNotificationRow row) {
        final StatusBarIconView icon = row.getEntry().expandedIcon;
        boolean needsContinuousClipping = ViewState.isAnimatingY(icon) && !this.mAmbientState.isDark();
        boolean isContinuousClipping = icon.getTag(R.id.continuous_clipping_tag) != null;
        if (needsContinuousClipping && !isContinuousClipping) {
            final ViewTreeObserver observer = icon.getViewTreeObserver();
            final ViewTreeObserver.OnPreDrawListener predrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.NotificationShelf.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    boolean animatingY = ViewState.isAnimatingY(icon);
                    if (animatingY) {
                        NotificationShelf.this.updateIconClipAmount(row);
                        return true;
                    }
                    observer.removeOnPreDrawListener(this);
                    icon.setTag(R.id.continuous_clipping_tag, null);
                    return true;
                }
            };
            observer.addOnPreDrawListener(predrawListener);
            icon.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.NotificationShelf.2
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                    if (v == icon) {
                        observer.removeOnPreDrawListener(predrawListener);
                        icon.setTag(R.id.continuous_clipping_tag, null);
                    }
                }
            });
            icon.setTag(R.id.continuous_clipping_tag, predrawListener);
        }
    }

    private void updateNotificationClipHeight(ExpandableNotificationRow row, float notificationClipEnd) {
        float viewEnd = row.getTranslationY() + row.getActualHeight();
        boolean isPinned = (row.isPinned() || row.isHeadsUpAnimatingAway()) && !this.mAmbientState.isDozingAndNotPulsing(row);
        if (viewEnd > notificationClipEnd && (this.mAmbientState.isShadeExpanded() || !isPinned)) {
            int clipBottomAmount = (int) (viewEnd - notificationClipEnd);
            if (isPinned) {
                clipBottomAmount = Math.min(row.getIntrinsicHeight() - row.getCollapsedHeight(), clipBottomAmount);
            }
            row.setClipBottomAmount(clipBottomAmount);
            return;
        }
        row.setClipBottomAmount(0);
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableView
    public void setFakeShadowIntensity(float shadowIntensity, float outlineAlpha, int shadowYEnd, int outlineTranslation) {
        if (!this.mHasItemsInStableShelf) {
            shadowIntensity = 0.0f;
        }
        super.setFakeShadowIntensity(shadowIntensity, outlineAlpha, shadowYEnd, outlineTranslation);
    }

    private float updateIconAppearance(ExpandableNotificationRow row, float expandAmount, boolean scrolling, boolean scrollingFast, boolean expandingAnimated, boolean isLastChild) {
        ExpandableNotificationRow expandableNotificationRow;
        int fullHeight;
        float iconTransformDistance;
        boolean fullyInOrOut;
        float fullTransitionAmount;
        float iconTransitionAmount;
        StatusBarIconView icon = row.getEntry().expandedIcon;
        NotificationIconContainer.IconState iconState = getIconState(icon);
        if (iconState == null) {
            return 0.0f;
        }
        float viewStart = row.getTranslationY();
        int fullHeight2 = row.getActualHeight() + this.mPaddingBetweenElements;
        float iconTransformDistance2 = Math.min(getIntrinsicHeight() * 1.5f * NotificationUtils.interpolate(1.0f, 1.5f, expandAmount), fullHeight2);
        if (isLastChild) {
            fullHeight2 = Math.min(fullHeight2, row.getMinHeight() - getIntrinsicHeight());
            iconTransformDistance2 = Math.min(iconTransformDistance2, row.getMinHeight() - getIntrinsicHeight());
        }
        float viewEnd = viewStart + fullHeight2;
        if (expandingAnimated && this.mAmbientState.getScrollY() == 0 && !this.mAmbientState.isOnKeyguard() && !iconState.isLastExpandIcon) {
            expandableNotificationRow = row;
            float position = this.mAmbientState.getIntrinsicPadding() + this.mHostLayout.getPositionInLinearLayout(expandableNotificationRow);
            int maxShelfStart = this.mMaxLayoutHeight - getIntrinsicHeight();
            if (position < maxShelfStart && row.getIntrinsicHeight() + position >= maxShelfStart && row.getTranslationY() < position) {
                iconState.isLastExpandIcon = true;
                iconState.customTransformHeight = Integer.MIN_VALUE;
                boolean forceInShelf = ((float) (this.mMaxLayoutHeight - getIntrinsicHeight())) - position < ((float) getIntrinsicHeight());
                if (!forceInShelf) {
                    iconState.customTransformHeight = (int) ((this.mMaxLayoutHeight - getIntrinsicHeight()) - position);
                }
            }
        } else {
            expandableNotificationRow = row;
        }
        float shelfStart = getTranslationY();
        if (iconState.hasCustomTransformHeight()) {
            int fullHeight3 = iconState.customTransformHeight;
            iconTransformDistance = iconState.customTransformHeight;
            fullHeight = fullHeight3;
        } else {
            fullHeight = fullHeight2;
            iconTransformDistance = iconTransformDistance2;
        }
        if (viewEnd < shelfStart || ((this.mAmbientState.isUnlockHintRunning() && !row.isInShelf()) || (!this.mAmbientState.isShadeExpanded() && (row.isPinned() || row.isHeadsUpAnimatingAway())))) {
            fullyInOrOut = true;
            fullTransitionAmount = 0.0f;
            iconTransitionAmount = 0.0f;
        } else if (viewStart < shelfStart) {
            float fullAmount = Math.min(1.0f, (shelfStart - viewStart) / fullHeight);
            float interpolatedAmount = Interpolators.ACCELERATE_DECELERATE.getInterpolation(fullAmount);
            float fullTransitionAmount2 = 1.0f - NotificationUtils.interpolate(interpolatedAmount, fullAmount, expandAmount);
            float iconTransitionAmount2 = (shelfStart - viewStart) / iconTransformDistance;
            fullyInOrOut = false;
            fullTransitionAmount = fullTransitionAmount2;
            iconTransitionAmount = 1.0f - Math.min(1.0f, iconTransitionAmount2);
        } else {
            iconTransitionAmount = 1.0f;
            fullyInOrOut = true;
            fullTransitionAmount = 1.0f;
        }
        if (fullyInOrOut && !expandingAnimated && iconState.isLastExpandIcon) {
            iconState.isLastExpandIcon = false;
            iconState.customTransformHeight = Integer.MIN_VALUE;
        }
        updateIconPositioning(expandableNotificationRow, iconTransitionAmount, fullTransitionAmount, iconTransformDistance, scrolling, scrollingFast, expandingAnimated, isLastChild);
        return fullTransitionAmount;
    }

    private void updateIconPositioning(ExpandableNotificationRow row, float iconTransitionAmount, float fullTransitionAmount, float iconTransformDistance, boolean scrolling, boolean scrollingFast, boolean expandingAnimated, boolean isLastChild) {
        float transitionAmount;
        StatusBarIconView icon = row.getEntry().expandedIcon;
        NotificationIconContainer.IconState iconState = getIconState(icon);
        if (iconState == null) {
            return;
        }
        boolean forceInShelf = iconState.isLastExpandIcon && !iconState.hasCustomTransformHeight();
        float contentTransformationAmount = 0.0f;
        float clampedAmount = iconTransitionAmount > 0.5f ? 1.0f : 0.0f;
        if (clampedAmount == fullTransitionAmount) {
            iconState.noAnimations = (scrollingFast || expandingAnimated) && !forceInShelf;
            iconState.useFullTransitionAmount = iconState.noAnimations || (!ICON_ANMATIONS_WHILE_SCROLLING && fullTransitionAmount == 0.0f && scrolling);
            iconState.useLinearTransitionAmount = (ICON_ANMATIONS_WHILE_SCROLLING || fullTransitionAmount != 0.0f || this.mAmbientState.isExpansionChanging()) ? false : true;
            iconState.translateContent = (((float) this.mMaxLayoutHeight) - getTranslationY()) - ((float) getIntrinsicHeight()) > 0.0f;
        }
        if (!forceInShelf && (scrollingFast || (expandingAnimated && iconState.useFullTransitionAmount && !ViewState.isAnimatingY(icon)))) {
            iconState.cancelAnimations(icon);
            iconState.useFullTransitionAmount = true;
            iconState.noAnimations = true;
        }
        if (iconState.hasCustomTransformHeight()) {
            iconState.useFullTransitionAmount = true;
        }
        if (iconState.isLastExpandIcon) {
            iconState.translateContent = false;
        }
        if (this.mAmbientState.getDarkAmount() > 0.0f && !row.isInShelf()) {
            transitionAmount = this.mAmbientState.isFullyDark() ? 1.0f : 0.0f;
        } else if (isLastChild || !USE_ANIMATIONS_WHEN_OPENING || iconState.useFullTransitionAmount || iconState.useLinearTransitionAmount) {
            transitionAmount = iconTransitionAmount;
        } else {
            transitionAmount = clampedAmount;
            iconState.needsCannedAnimation = (iconState.clampedAppearAmount == clampedAmount || this.mNoAnimationsInThisFrame) ? false : true;
        }
        float transitionAmount2 = transitionAmount;
        iconState.iconAppearAmount = (!USE_ANIMATIONS_WHEN_OPENING || iconState.useFullTransitionAmount) ? fullTransitionAmount : transitionAmount2;
        iconState.clampedAppearAmount = clampedAmount;
        if (!this.mAmbientState.isAboveShelf(row) && (isLastChild || iconState.translateContent)) {
            contentTransformationAmount = iconTransitionAmount;
        }
        row.setContentTransformationAmount(contentTransformationAmount, isLastChild);
        setIconTransformationAmount(row, transitionAmount2, iconTransformDistance, clampedAmount != transitionAmount2, isLastChild);
    }

    private void setIconTransformationAmount(ExpandableNotificationRow row, float transitionAmount, float iconTransformDistance, boolean usingLinearInterpolation, boolean isLastChild) {
        int iconTopPadding;
        StatusBarIconView icon = row.getEntry().expandedIcon;
        NotificationIconContainer.IconState iconState = getIconState(icon);
        View rowIcon = row.getNotificationIcon();
        float notificationIconPosition = row.getTranslationY() + row.getContentTranslation();
        boolean stayingInShelf = row.isInShelf() && !row.isTransformingIntoShelf();
        if (usingLinearInterpolation && !stayingInShelf) {
            notificationIconPosition = getTranslationY() - iconTransformDistance;
        }
        float notificationIconSize = 0.0f;
        if (rowIcon != null) {
            iconTopPadding = row.getRelativeTopPadding(rowIcon);
            notificationIconSize = rowIcon.getHeight();
        } else {
            iconTopPadding = this.mIconAppearTopPadding;
        }
        float shelfIconPosition = getTranslationY() + icon.getTop();
        float iconYTranslation = NotificationUtils.interpolate((notificationIconPosition + iconTopPadding) - (shelfIconPosition + ((icon.getHeight() - (icon.getIconScale() * this.mIconSize)) / 2.0f)), 0.0f, transitionAmount);
        float shelfIconSize = this.mIconSize * icon.getIconScale();
        float alpha = 1.0f;
        boolean noIcon = !row.isShowingIcon();
        if (noIcon) {
            alpha = transitionAmount;
            notificationIconSize = shelfIconSize / 2.0f;
        }
        float alpha2 = alpha;
        float newSize = NotificationUtils.interpolate(notificationIconSize, shelfIconSize, transitionAmount);
        if (iconState != null) {
            iconState.scaleX = newSize / shelfIconSize;
            iconState.scaleY = iconState.scaleX;
            iconState.hidden = transitionAmount == 0.0f && !iconState.isAnimating(icon);
            boolean isAppearing = row.isDrawingAppearAnimation() && !row.isInShelf();
            if (isAppearing) {
                iconState.hidden = true;
                iconState.iconAppearAmount = 0.0f;
            }
            iconState.alpha = alpha2;
            iconState.yTranslation = iconYTranslation;
            if (stayingInShelf) {
                iconState.iconAppearAmount = 1.0f;
                iconState.alpha = 1.0f;
                iconState.scaleX = 1.0f;
                iconState.scaleY = 1.0f;
                iconState.hidden = false;
            }
            if (this.mAmbientState.isAboveShelf(row) || (!row.isInShelf() && ((isLastChild && row.areGutsExposed()) || row.getTranslationZ() > this.mAmbientState.getBaseZHeight()))) {
                iconState.hidden = true;
            }
            int backgroundColor = getBackgroundColorWithoutTint();
            int shelfColor = icon.getContrastedStaticDrawableColor(backgroundColor);
            if (!noIcon && shelfColor != 0) {
                int iconColor = row.getVisibleNotificationHeader().getOriginalIconColor();
                shelfColor = NotificationUtils.interpolateColors(iconColor, shelfColor, iconState.iconAppearAmount);
            }
            iconState.iconColor = shelfColor;
        }
    }

    private NotificationIconContainer.IconState getIconState(StatusBarIconView icon) {
        return this.mShelfIcons.getIconState(icon);
    }

    private float getFullyClosedTranslation() {
        return (-(getIntrinsicHeight() - this.mStatusBarHeight)) / 2;
    }

    public int getNotificationMergeSize() {
        return getIntrinsicHeight();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean hasNoContentHeight() {
        return true;
    }

    private void setHideBackground(boolean hideBackground) {
        if (this.mHideBackground != hideBackground) {
            this.mHideBackground = hideBackground;
            updateBackground();
            updateOutline();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    protected boolean needsOutline() {
        return !this.mHideBackground && super.needsOutline();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected boolean shouldHideBackground() {
        return super.shouldHideBackground() || this.mHideBackground;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateRelativeOffset();
        int height = getResources().getDisplayMetrics().heightPixels;
        this.mClipRect.set(0, -height, getWidth(), height);
        this.mShelfIcons.setClipBounds(this.mClipRect);
    }

    private void updateRelativeOffset() {
        this.mCollapsedIcons.getLocationOnScreen(this.mTmp);
        this.mRelativeOffset = this.mTmp[0];
        getLocationOnScreen(this.mTmp);
        this.mRelativeOffset -= this.mTmp[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOpenedAmount(float openedAmount) {
        int collapsedPadding;
        this.mNoAnimationsInThisFrame = openedAmount == 1.0f && this.mOpenedAmount == 0.0f;
        this.mOpenedAmount = openedAmount;
        if (!this.mAmbientState.isPanelFullWidth()) {
            openedAmount = 1.0f;
        }
        int start = this.mRelativeOffset;
        if (isLayoutRtl()) {
            start = (getWidth() - start) - this.mCollapsedIcons.getWidth();
        }
        int width = (int) NotificationUtils.interpolate(this.mCollapsedIcons.getFinalTranslationX() + start, this.mShelfIcons.getWidth(), openedAmount);
        this.mShelfIcons.setActualLayoutWidth(width);
        boolean hasOverflow = this.mCollapsedIcons.hasOverflow();
        int collapsedPadding2 = this.mCollapsedIcons.getPaddingEnd();
        if (!hasOverflow) {
            collapsedPadding = collapsedPadding2 - this.mCollapsedIcons.getNoOverflowExtraPadding();
        } else {
            collapsedPadding = collapsedPadding2 - this.mCollapsedIcons.getPartialOverflowExtraPadding();
        }
        float padding = NotificationUtils.interpolate(collapsedPadding, this.mShelfIcons.getPaddingEnd(), openedAmount);
        this.mShelfIcons.setActualPaddingEnd(padding);
        float paddingStart = NotificationUtils.interpolate(start, this.mShelfIcons.getPaddingStart(), openedAmount);
        this.mShelfIcons.setActualPaddingStart(paddingStart);
        this.mShelfIcons.setOpenedAmount(openedAmount);
    }

    public void setMaxLayoutHeight(int maxLayoutHeight) {
        this.mMaxLayoutHeight = maxLayoutHeight;
    }

    public int getNotGoneIndex() {
        return this.mNotGoneIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHasItemsInStableShelf(boolean hasItemsInStableShelf) {
        if (this.mHasItemsInStableShelf != hasItemsInStableShelf) {
            this.mHasItemsInStableShelf = hasItemsInStableShelf;
            updateInteractiveness();
        }
    }

    public boolean hasItemsInStableShelf() {
        return this.mHasItemsInStableShelf;
    }

    public void setCollapsedIcons(NotificationIconContainer collapsedIcons) {
        this.mCollapsedIcons = collapsedIcons;
        this.mCollapsedIcons.addOnLayoutChangeListener(this);
    }

    public void setStatusBarState(int statusBarState) {
        if (this.mStatusBarState != statusBarState) {
            this.mStatusBarState = statusBarState;
            updateInteractiveness();
        }
    }

    private void updateInteractiveness() {
        this.mInteractive = this.mStatusBarState == 1 && this.mHasItemsInStableShelf && !this.mDark;
        setClickable(this.mInteractive);
        setFocusable(this.mInteractive);
        setImportantForAccessibility(this.mInteractive ? 1 : 4);
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected boolean isInteractive() {
        return this.mInteractive;
    }

    public void setMaxShelfEnd(float maxShelfEnd) {
        this.mMaxShelfEnd = maxShelfEnd;
    }

    public void setAnimationsEnabled(boolean enabled) {
        this.mAnimationsEnabled = enabled;
        this.mCollapsedIcons.setAnimationsEnabled(enabled);
        if (!enabled) {
            this.mShelfIcons.setAnimationsEnabled(false);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.mInteractive) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            AccessibilityNodeInfo.AccessibilityAction unlock = new AccessibilityNodeInfo.AccessibilityAction(16, getContext().getString(R.string.accessibility_overflow_action));
            info.addAction(unlock);
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        updateRelativeOffset();
    }

    private class ShelfState extends ExpandableViewState {
        private boolean hasItemsInStableShelf;
        private float maxShelfEnd;
        private float openedAmount;

        private ShelfState() {
        }

        @Override // com.android.systemui.statusbar.stack.ExpandableViewState, com.android.systemui.statusbar.stack.ViewState
        public void applyToView(View view) {
            if (!NotificationShelf.this.mShowNotificationShelf) {
                return;
            }
            super.applyToView(view);
            NotificationShelf.this.setMaxShelfEnd(this.maxShelfEnd);
            NotificationShelf.this.setOpenedAmount(this.openedAmount);
            NotificationShelf.this.updateAppearance();
            NotificationShelf.this.setHasItemsInStableShelf(this.hasItemsInStableShelf);
            NotificationShelf.this.mShelfIcons.setAnimationsEnabled(NotificationShelf.this.mAnimationsEnabled);
        }

        @Override // com.android.systemui.statusbar.stack.ExpandableViewState, com.android.systemui.statusbar.stack.ViewState
        public void animateTo(View child, AnimationProperties properties) {
            if (!NotificationShelf.this.mShowNotificationShelf) {
                return;
            }
            super.animateTo(child, properties);
            NotificationShelf.this.setMaxShelfEnd(this.maxShelfEnd);
            NotificationShelf.this.setOpenedAmount(this.openedAmount);
            NotificationShelf.this.updateAppearance();
            NotificationShelf.this.setHasItemsInStableShelf(this.hasItemsInStableShelf);
            NotificationShelf.this.mShelfIcons.setAnimationsEnabled(NotificationShelf.this.mAnimationsEnabled);
        }
    }
}
