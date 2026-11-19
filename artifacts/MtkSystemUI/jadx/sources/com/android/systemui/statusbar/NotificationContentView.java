package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Rect;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.HybridGroupManager;
import com.android.systemui.statusbar.notification.HybridNotificationView;
import com.android.systemui.statusbar.notification.NotificationCustomViewWrapper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationViewWrapper;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationContentView extends FrameLayout {
    private View mAmbientChild;
    private HybridNotificationView mAmbientSingleLineChild;
    private NotificationViewWrapper mAmbientWrapper;
    private boolean mAnimate;
    private int mAnimationStartVisibleType;
    private boolean mBeforeN;
    private RemoteInputView mCachedExpandedRemoteInput;
    private RemoteInputView mCachedHeadsUpRemoteInput;
    private int mClipBottomAmount;
    private final Rect mClipBounds;
    private boolean mClipToActualHeight;
    private int mClipTopAmount;
    private ExpandableNotificationRow mContainingNotification;
    private int mContentHeight;
    private int mContentHeightAtAnimationStart;
    private View mContractedChild;
    private NotificationViewWrapper mContractedWrapper;
    private boolean mDark;
    private final ViewTreeObserver.OnPreDrawListener mEnableAnimationPredrawListener;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandable;
    private View mExpandedChild;
    private RemoteInputView mExpandedRemoteInput;
    private SmartReplyView mExpandedSmartReplyView;
    private Runnable mExpandedVisibleListener;
    private NotificationViewWrapper mExpandedWrapper;
    private boolean mFocusOnVisibilityChange;
    private boolean mForceSelectNextLayout;
    private NotificationGroupManager mGroupManager;
    private boolean mHeadsUpAnimatingAway;
    private View mHeadsUpChild;
    private int mHeadsUpHeight;
    private RemoteInputView mHeadsUpRemoteInput;
    private NotificationViewWrapper mHeadsUpWrapper;
    private HybridGroupManager mHybridGroupManager;
    private boolean mIconsVisible;
    private boolean mIsChildInGroup;
    private boolean mIsContentExpandable;
    private boolean mIsHeadsUp;
    private boolean mIsLowPriority;
    private boolean mLegacy;
    private int mMinContractedHeight;
    private int mNotificationAmbientHeight;
    private int mNotificationContentMarginEnd;
    private int mNotificationMaxHeight;
    private PendingIntent mPreviousExpandedRemoteInputIntent;
    private PendingIntent mPreviousHeadsUpRemoteInputIntent;
    private RemoteInputController mRemoteInputController;
    private boolean mRemoteInputVisible;
    private HybridNotificationView mSingleLineView;
    private int mSingleLineWidthIndention;
    private int mSmallHeight;
    private SmartReplyConstants mSmartReplyConstants;
    private SmartReplyController mSmartReplyController;
    private StatusBarNotification mStatusBarNotification;
    private int mTransformationStartVisibleType;
    private int mUnrestrictedContentHeight;
    private boolean mUserExpanding;
    private int mVisibleType;

    public NotificationContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mClipBounds = new Rect();
        this.mVisibleType = 0;
        this.mEnableAnimationPredrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.NotificationContentView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationContentView.this.post(new Runnable() { // from class: com.android.systemui.statusbar.NotificationContentView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationContentView.this.mAnimate = true;
                    }
                });
                NotificationContentView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        this.mClipToActualHeight = true;
        this.mAnimationStartVisibleType = -1;
        this.mForceSelectNextLayout = true;
        this.mContentHeightAtAnimationStart = -1;
        this.mHybridGroupManager = new HybridGroupManager(getContext(), this);
        this.mSmartReplyConstants = (SmartReplyConstants) Dependency.get(SmartReplyConstants.class);
        this.mSmartReplyController = (SmartReplyController) Dependency.get(SmartReplyController.class);
        initView();
    }

    public void initView() {
        this.mMinContractedHeight = getResources().getDimensionPixelSize(R.dimen.min_notification_layout_height);
        this.mNotificationContentMarginEnd = getResources().getDimensionPixelSize(android.R.dimen.conversation_compact_face_pile_protection_width);
    }

    public void setHeights(int smallHeight, int headsUpMaxHeight, int maxHeight, int ambientHeight) {
        this.mSmallHeight = smallHeight;
        this.mHeadsUpHeight = headsUpMaxHeight;
        this.mNotificationMaxHeight = maxHeight;
        this.mNotificationAmbientHeight = ambientHeight;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int iMakeMeasureSpec;
        int heightSpec;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        boolean hasFixedHeight = heightMode == 1073741824;
        boolean isHeightLimited = heightMode == Integer.MIN_VALUE;
        int maxSize = 1073741823;
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        if (hasFixedHeight || isHeightLimited) {
            maxSize = View.MeasureSpec.getSize(heightMeasureSpec);
        }
        int maxSize2 = maxSize;
        int maxChildHeight = 0;
        if (this.mExpandedChild != null) {
            int notificationMaxHeight = this.mNotificationMaxHeight;
            if (this.mExpandedSmartReplyView != null) {
                notificationMaxHeight += this.mExpandedSmartReplyView.getHeightUpperLimit();
            }
            int size = notificationMaxHeight + this.mExpandedWrapper.getExtraMeasureHeight();
            ViewGroup.LayoutParams layoutParams = this.mExpandedChild.getLayoutParams();
            boolean useExactly = false;
            if (layoutParams.height >= 0) {
                size = Math.min(size, layoutParams.height);
                useExactly = true;
            }
            int spec = View.MeasureSpec.makeMeasureSpec(size, useExactly ? 1073741824 : Integer.MIN_VALUE);
            measureChildWithMargins(this.mExpandedChild, widthMeasureSpec, 0, spec, 0);
            maxChildHeight = Math.max(0, this.mExpandedChild.getMeasuredHeight());
        }
        if (this.mContractedChild != null) {
            int size2 = this.mSmallHeight;
            ViewGroup.LayoutParams layoutParams2 = this.mContractedChild.getLayoutParams();
            boolean useExactly2 = false;
            if (layoutParams2.height >= 0) {
                size2 = Math.min(size2, layoutParams2.height);
                useExactly2 = true;
            }
            int size3 = size2;
            boolean useExactly3 = useExactly2;
            if (shouldContractedBeFixedSize() || useExactly3) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size3, 1073741824);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size3, Integer.MIN_VALUE);
            }
            int heightSpec2 = iMakeMeasureSpec;
            measureChildWithMargins(this.mContractedChild, widthMeasureSpec, 0, heightSpec2, 0);
            int measuredHeight = this.mContractedChild.getMeasuredHeight();
            if (measuredHeight < this.mMinContractedHeight) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(this.mMinContractedHeight, 1073741824);
                measureChildWithMargins(this.mContractedChild, widthMeasureSpec, 0, heightSpec, 0);
            } else {
                heightSpec = heightSpec2;
            }
            maxChildHeight = Math.max(maxChildHeight, measuredHeight);
            if (updateContractedHeaderWidth()) {
                measureChildWithMargins(this.mContractedChild, widthMeasureSpec, 0, heightSpec, 0);
            }
            if (this.mExpandedChild != null && this.mContractedChild.getMeasuredHeight() > this.mExpandedChild.getMeasuredHeight()) {
                measureChildWithMargins(this.mExpandedChild, widthMeasureSpec, 0, View.MeasureSpec.makeMeasureSpec(this.mContractedChild.getMeasuredHeight(), 1073741824), 0);
            }
        }
        if (this.mHeadsUpChild != null) {
            int maxHeight = this.mHeadsUpHeight;
            int size4 = maxHeight + this.mHeadsUpWrapper.getExtraMeasureHeight();
            ViewGroup.LayoutParams layoutParams3 = this.mHeadsUpChild.getLayoutParams();
            boolean useExactly4 = false;
            if (layoutParams3.height >= 0) {
                size4 = Math.min(size4, layoutParams3.height);
                useExactly4 = true;
            }
            measureChildWithMargins(this.mHeadsUpChild, widthMeasureSpec, 0, View.MeasureSpec.makeMeasureSpec(size4, useExactly4 ? 1073741824 : Integer.MIN_VALUE), 0);
            maxChildHeight = Math.max(maxChildHeight, this.mHeadsUpChild.getMeasuredHeight());
        }
        if (this.mSingleLineView != null) {
            int singleLineWidthSpec = widthMeasureSpec;
            if (this.mSingleLineWidthIndention != 0 && View.MeasureSpec.getMode(widthMeasureSpec) != 0) {
                singleLineWidthSpec = View.MeasureSpec.makeMeasureSpec((width - this.mSingleLineWidthIndention) + this.mSingleLineView.getPaddingEnd(), 1073741824);
            }
            i = Integer.MIN_VALUE;
            this.mSingleLineView.measure(singleLineWidthSpec, View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, Integer.MIN_VALUE));
            maxChildHeight = Math.max(maxChildHeight, this.mSingleLineView.getMeasuredHeight());
        } else {
            i = Integer.MIN_VALUE;
        }
        if (this.mAmbientChild != null) {
            int size5 = this.mNotificationAmbientHeight;
            ViewGroup.LayoutParams layoutParams4 = this.mAmbientChild.getLayoutParams();
            boolean useExactly5 = false;
            if (layoutParams4.height >= 0) {
                size5 = Math.min(size5, layoutParams4.height);
                useExactly5 = true;
            }
            i2 = widthMeasureSpec;
            this.mAmbientChild.measure(i2, View.MeasureSpec.makeMeasureSpec(size5, useExactly5 ? 1073741824 : i));
            maxChildHeight = Math.max(maxChildHeight, this.mAmbientChild.getMeasuredHeight());
        } else {
            i2 = widthMeasureSpec;
        }
        if (this.mAmbientSingleLineChild != null) {
            int size6 = this.mNotificationAmbientHeight;
            ViewGroup.LayoutParams layoutParams5 = this.mAmbientSingleLineChild.getLayoutParams();
            boolean useExactly6 = false;
            if (layoutParams5.height >= 0) {
                size6 = Math.min(size6, layoutParams5.height);
                useExactly6 = true;
            }
            int ambientSingleLineWidthSpec = i2;
            if (this.mSingleLineWidthIndention != 0 && View.MeasureSpec.getMode(widthMeasureSpec) != 0) {
                ambientSingleLineWidthSpec = View.MeasureSpec.makeMeasureSpec((width - this.mSingleLineWidthIndention) + this.mAmbientSingleLineChild.getPaddingEnd(), 1073741824);
            }
            HybridNotificationView hybridNotificationView = this.mAmbientSingleLineChild;
            if (useExactly6) {
                i = 1073741824;
            }
            hybridNotificationView.measure(ambientSingleLineWidthSpec, View.MeasureSpec.makeMeasureSpec(size6, i));
            maxChildHeight = Math.max(maxChildHeight, this.mAmbientSingleLineChild.getMeasuredHeight());
        }
        int ownHeight = Math.min(maxChildHeight, maxSize2);
        setMeasuredDimension(width, ownHeight);
    }

    private int getExtraRemoteInputHeight(RemoteInputView remoteInput) {
        if (remoteInput == null) {
            return 0;
        }
        if (remoteInput.isActive() || remoteInput.isSending()) {
            return getResources().getDimensionPixelSize(android.R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        }
        return 0;
    }

    private boolean updateContractedHeaderWidth() {
        int paddingLeft;
        int paddingLeft2;
        int paddingLeft3;
        int paddingLeft4;
        NotificationHeaderView contractedHeader = this.mContractedWrapper.getNotificationHeader();
        if (contractedHeader != null) {
            if (this.mExpandedChild != null && this.mExpandedWrapper.getNotificationHeader() != null) {
                NotificationHeaderView expandedHeader = this.mExpandedWrapper.getNotificationHeader();
                int expandedSize = expandedHeader.getMeasuredWidth() - expandedHeader.getPaddingEnd();
                int collapsedSize = contractedHeader.getMeasuredWidth() - expandedHeader.getPaddingEnd();
                if (expandedSize != collapsedSize) {
                    int paddingEnd = contractedHeader.getMeasuredWidth() - expandedSize;
                    if (!contractedHeader.isLayoutRtl()) {
                        paddingLeft3 = contractedHeader.getPaddingLeft();
                    } else {
                        paddingLeft3 = paddingEnd;
                    }
                    int paddingTop = contractedHeader.getPaddingTop();
                    if (contractedHeader.isLayoutRtl()) {
                        paddingLeft4 = contractedHeader.getPaddingLeft();
                    } else {
                        paddingLeft4 = paddingEnd;
                    }
                    contractedHeader.setPadding(paddingLeft3, paddingTop, paddingLeft4, contractedHeader.getPaddingBottom());
                    contractedHeader.setShowWorkBadgeAtEnd(true);
                    return true;
                }
            } else {
                int paddingEnd2 = this.mNotificationContentMarginEnd;
                if (contractedHeader.getPaddingEnd() != paddingEnd2) {
                    if (!contractedHeader.isLayoutRtl()) {
                        paddingLeft = contractedHeader.getPaddingLeft();
                    } else {
                        paddingLeft = paddingEnd2;
                    }
                    int paddingTop2 = contractedHeader.getPaddingTop();
                    if (contractedHeader.isLayoutRtl()) {
                        paddingLeft2 = contractedHeader.getPaddingLeft();
                    } else {
                        paddingLeft2 = paddingEnd2;
                    }
                    contractedHeader.setPadding(paddingLeft, paddingTop2, paddingLeft2, contractedHeader.getPaddingBottom());
                    contractedHeader.setShowWorkBadgeAtEnd(false);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean shouldContractedBeFixedSize() {
        return this.mBeforeN && (this.mContractedWrapper instanceof NotificationCustomViewWrapper);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int previousHeight = 0;
        if (this.mExpandedChild != null) {
            previousHeight = this.mExpandedChild.getHeight();
        }
        super.onLayout(changed, left, top, right, bottom);
        if (previousHeight != 0 && this.mExpandedChild.getHeight() != previousHeight) {
            this.mContentHeightAtAnimationStart = previousHeight;
        }
        updateClipping();
        invalidateOutline();
        selectLayout(false, this.mForceSelectNextLayout);
        this.mForceSelectNextLayout = false;
        updateExpandButtons(this.mExpandable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateVisibility();
    }

    public View getContractedChild() {
        return this.mContractedChild;
    }

    public View getExpandedChild() {
        return this.mExpandedChild;
    }

    public View getHeadsUpChild() {
        return this.mHeadsUpChild;
    }

    public View getAmbientChild() {
        return this.mAmbientChild;
    }

    public HybridNotificationView getAmbientSingleLineChild() {
        return this.mAmbientSingleLineChild;
    }

    public void setContractedChild(View child) {
        if (this.mContractedChild != null) {
            this.mContractedChild.animate().cancel();
            removeView(this.mContractedChild);
        }
        addView(child);
        this.mContractedChild = child;
        this.mContractedWrapper = NotificationViewWrapper.wrap(getContext(), child, this.mContainingNotification);
    }

    private NotificationViewWrapper getWrapperForView(View child) {
        if (child == this.mContractedChild) {
            return this.mContractedWrapper;
        }
        if (child == this.mExpandedChild) {
            return this.mExpandedWrapper;
        }
        if (child == this.mHeadsUpChild) {
            return this.mHeadsUpWrapper;
        }
        if (child == this.mAmbientChild) {
            return this.mAmbientWrapper;
        }
        return null;
    }

    public void setExpandedChild(View child) {
        if (this.mExpandedChild != null) {
            this.mPreviousExpandedRemoteInputIntent = null;
            if (this.mExpandedRemoteInput != null) {
                this.mExpandedRemoteInput.onNotificationUpdateOrReset();
                if (this.mExpandedRemoteInput.isActive()) {
                    this.mPreviousExpandedRemoteInputIntent = this.mExpandedRemoteInput.getPendingIntent();
                    this.mCachedExpandedRemoteInput = this.mExpandedRemoteInput;
                    this.mExpandedRemoteInput.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mExpandedRemoteInput.getParent()).removeView(this.mExpandedRemoteInput);
                }
            }
            this.mExpandedChild.animate().cancel();
            removeView(this.mExpandedChild);
            this.mExpandedRemoteInput = null;
        }
        if (child == null) {
            this.mExpandedChild = null;
            this.mExpandedWrapper = null;
            if (this.mVisibleType == 1) {
                this.mVisibleType = 0;
            }
            if (this.mTransformationStartVisibleType == 1) {
                this.mTransformationStartVisibleType = -1;
                return;
            }
            return;
        }
        addView(child);
        this.mExpandedChild = child;
        this.mExpandedWrapper = NotificationViewWrapper.wrap(getContext(), child, this.mContainingNotification);
    }

    public void setHeadsUpChild(View child) {
        if (this.mHeadsUpChild != null) {
            this.mPreviousHeadsUpRemoteInputIntent = null;
            if (this.mHeadsUpRemoteInput != null) {
                this.mHeadsUpRemoteInput.onNotificationUpdateOrReset();
                if (this.mHeadsUpRemoteInput.isActive()) {
                    this.mPreviousHeadsUpRemoteInputIntent = this.mHeadsUpRemoteInput.getPendingIntent();
                    this.mCachedHeadsUpRemoteInput = this.mHeadsUpRemoteInput;
                    this.mHeadsUpRemoteInput.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mHeadsUpRemoteInput.getParent()).removeView(this.mHeadsUpRemoteInput);
                }
            }
            this.mHeadsUpChild.animate().cancel();
            removeView(this.mHeadsUpChild);
            this.mHeadsUpRemoteInput = null;
        }
        if (child == null) {
            this.mHeadsUpChild = null;
            this.mHeadsUpWrapper = null;
            if (this.mVisibleType == 2) {
                this.mVisibleType = 0;
            }
            if (this.mTransformationStartVisibleType == 2) {
                this.mTransformationStartVisibleType = -1;
                return;
            }
            return;
        }
        addView(child);
        this.mHeadsUpChild = child;
        this.mHeadsUpWrapper = NotificationViewWrapper.wrap(getContext(), child, this.mContainingNotification);
    }

    public void setAmbientChild(View child) {
        if (this.mAmbientChild != null) {
            this.mAmbientChild.animate().cancel();
            removeView(this.mAmbientChild);
        }
        if (child == null) {
            return;
        }
        addView(child);
        this.mAmbientChild = child;
        this.mAmbientWrapper = NotificationViewWrapper.wrap(getContext(), child, this.mContainingNotification);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        updateVisibility();
    }

    private void updateVisibility() {
        setVisible(isShown());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
    }

    private void setVisible(boolean isVisible) {
        if (isVisible) {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            getViewTreeObserver().addOnPreDrawListener(this.mEnableAnimationPredrawListener);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            this.mAnimate = false;
        }
    }

    private void focusExpandButtonIfNecessary() {
        ImageView expandButton;
        if (this.mFocusOnVisibilityChange) {
            NotificationHeaderView header = getVisibleNotificationHeader();
            if (header != null && (expandButton = header.getExpandButton()) != null) {
                expandButton.requestAccessibilityFocus();
            }
            this.mFocusOnVisibilityChange = false;
        }
    }

    public void setContentHeight(int contentHeight) {
        this.mUnrestrictedContentHeight = Math.max(contentHeight, getMinHeight());
        int maxContentHeight = (this.mContainingNotification.getIntrinsicHeight() - getExtraRemoteInputHeight(this.mExpandedRemoteInput)) - getExtraRemoteInputHeight(this.mHeadsUpRemoteInput);
        this.mContentHeight = Math.min(this.mUnrestrictedContentHeight, maxContentHeight);
        selectLayout(this.mAnimate, false);
        int minHeightHint = getMinContentHeightHint();
        NotificationViewWrapper wrapper = getVisibleWrapper(this.mVisibleType);
        if (wrapper != null) {
            wrapper.setContentHeight(this.mUnrestrictedContentHeight, minHeightHint);
        }
        NotificationViewWrapper wrapper2 = getVisibleWrapper(this.mTransformationStartVisibleType);
        if (wrapper2 != null) {
            wrapper2.setContentHeight(this.mUnrestrictedContentHeight, minHeightHint);
        }
        updateClipping();
        invalidateOutline();
    }

    private int getMinContentHeightHint() {
        int hint;
        if (this.mIsChildInGroup && isVisibleOrTransitioning(3)) {
            return this.mContext.getResources().getDimensionPixelSize(android.R.dimen.control_corner_material);
        }
        if (this.mHeadsUpChild != null && this.mExpandedChild != null) {
            boolean transitioningBetweenHunAndExpanded = isTransitioningFromTo(2, 1) || isTransitioningFromTo(1, 2);
            boolean pinned = !isVisibleOrTransitioning(0) && (this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && !this.mContainingNotification.isOnKeyguard();
            if (transitioningBetweenHunAndExpanded || pinned) {
                return Math.min(getViewHeight(2), getViewHeight(1));
            }
        }
        if (this.mVisibleType == 1 && this.mContentHeightAtAnimationStart >= 0 && this.mExpandedChild != null) {
            return Math.min(this.mContentHeightAtAnimationStart, getViewHeight(1));
        }
        if (this.mAmbientChild != null && isVisibleOrTransitioning(4)) {
            hint = this.mAmbientChild.getHeight();
        } else if (this.mAmbientSingleLineChild != null && isVisibleOrTransitioning(5)) {
            hint = this.mAmbientSingleLineChild.getHeight();
        } else if (this.mHeadsUpChild != null && isVisibleOrTransitioning(2)) {
            hint = getViewHeight(2);
        } else if (this.mExpandedChild != null) {
            hint = getViewHeight(1);
        } else {
            hint = getViewHeight(0) + this.mContext.getResources().getDimensionPixelSize(android.R.dimen.control_corner_material);
        }
        if (this.mExpandedChild != null && isVisibleOrTransitioning(1)) {
            return Math.min(hint, getViewHeight(1));
        }
        return hint;
    }

    private boolean isTransitioningFromTo(int from, int to) {
        return (this.mTransformationStartVisibleType == from || this.mAnimationStartVisibleType == from) && this.mVisibleType == to;
    }

    private boolean isVisibleOrTransitioning(int type) {
        return this.mVisibleType == type || this.mTransformationStartVisibleType == type || this.mAnimationStartVisibleType == type;
    }

    private void updateContentTransformation() {
        int visibleType = calculateVisibleType();
        if (visibleType != this.mVisibleType) {
            this.mTransformationStartVisibleType = this.mVisibleType;
            TransformableView shownView = getTransformableViewForVisibleType(visibleType);
            TransformableView hiddenView = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
            shownView.transformFrom(hiddenView, 0.0f);
            getViewForVisibleType(visibleType).setVisibility(0);
            hiddenView.transformTo(shownView, 0.0f);
            this.mVisibleType = visibleType;
            updateBackgroundColor(true);
        }
        if (this.mForceSelectNextLayout) {
            forceUpdateVisibilities();
        }
        if (this.mTransformationStartVisibleType != -1 && this.mVisibleType != this.mTransformationStartVisibleType && getViewForVisibleType(this.mTransformationStartVisibleType) != null) {
            TransformableView shownView2 = getTransformableViewForVisibleType(this.mVisibleType);
            TransformableView hiddenView2 = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
            float transformationAmount = calculateTransformationAmount();
            shownView2.transformFrom(hiddenView2, transformationAmount);
            hiddenView2.transformTo(shownView2, transformationAmount);
            updateBackgroundTransformation(transformationAmount);
            return;
        }
        updateViewVisibilities(visibleType);
        updateBackgroundColor(false);
    }

    private void updateBackgroundTransformation(float transformationAmount) {
        int endColor = getBackgroundColor(this.mVisibleType);
        int startColor = getBackgroundColor(this.mTransformationStartVisibleType);
        if (endColor != startColor) {
            if (startColor == 0) {
                startColor = this.mContainingNotification.getBackgroundColorWithoutTint();
            }
            if (endColor == 0) {
                endColor = this.mContainingNotification.getBackgroundColorWithoutTint();
            }
            endColor = NotificationUtils.interpolateColors(startColor, endColor, transformationAmount);
        }
        this.mContainingNotification.updateBackgroundAlpha(transformationAmount);
        this.mContainingNotification.setContentBackground(endColor, false, this);
    }

    private float calculateTransformationAmount() {
        int startHeight = getViewHeight(this.mTransformationStartVisibleType);
        int endHeight = getViewHeight(this.mVisibleType);
        int progress = Math.abs(this.mContentHeight - startHeight);
        int totalDistance = Math.abs(endHeight - startHeight);
        if (totalDistance == 0) {
            Log.wtf("NotificationContentView", "the total transformation distance is 0\n StartType: " + this.mTransformationStartVisibleType + " height: " + startHeight + "\n VisibleType: " + this.mVisibleType + " height: " + endHeight + "\n mContentHeight: " + this.mContentHeight);
            return 1.0f;
        }
        float amount = progress / totalDistance;
        return Math.min(1.0f, amount);
    }

    public int getMaxHeight() {
        if (this.mContainingNotification.isShowingAmbient()) {
            return getShowingAmbientView().getHeight();
        }
        if (this.mExpandedChild != null) {
            return getViewHeight(1) + getExtraRemoteInputHeight(this.mExpandedRemoteInput);
        }
        if (this.mIsHeadsUp && this.mHeadsUpChild != null && !this.mContainingNotification.isOnKeyguard()) {
            return getViewHeight(2) + getExtraRemoteInputHeight(this.mHeadsUpRemoteInput);
        }
        return getViewHeight(0);
    }

    private int getViewHeight(int visibleType) {
        View view = getViewForVisibleType(visibleType);
        int height = view.getHeight();
        NotificationViewWrapper viewWrapper = getWrapperForView(view);
        if (viewWrapper != null) {
            return height + viewWrapper.getHeaderTranslation();
        }
        return height;
    }

    public int getMinHeight() {
        return getMinHeight(false);
    }

    public int getMinHeight(boolean likeGroupExpanded) {
        if (this.mContainingNotification.isShowingAmbient()) {
            return getShowingAmbientView().getHeight();
        }
        if (likeGroupExpanded || !this.mIsChildInGroup || isGroupExpanded()) {
            return getViewHeight(0);
        }
        return this.mSingleLineView.getHeight();
    }

    public View getShowingAmbientView() {
        View v = this.mIsChildInGroup ? this.mAmbientSingleLineChild : this.mAmbientChild;
        if (v != null) {
            return v;
        }
        return this.mContractedChild;
    }

    private boolean isGroupExpanded() {
        return this.mGroupManager.isGroupExpanded(this.mStatusBarNotification);
    }

    public void setClipTopAmount(int clipTopAmount) {
        this.mClipTopAmount = clipTopAmount;
        updateClipping();
    }

    public void setClipBottomAmount(int clipBottomAmount) {
        this.mClipBottomAmount = clipBottomAmount;
        updateClipping();
    }

    @Override // android.view.View
    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY);
        updateClipping();
    }

    private void updateClipping() {
        if (this.mClipToActualHeight) {
            int top = (int) (this.mClipTopAmount - getTranslationY());
            int bottom = (int) ((this.mUnrestrictedContentHeight - this.mClipBottomAmount) - getTranslationY());
            this.mClipBounds.set(0, top, getWidth(), Math.max(top, bottom));
            setClipBounds(this.mClipBounds);
            return;
        }
        setClipBounds(null);
    }

    public void setClipToActualHeight(boolean clipToActualHeight) {
        this.mClipToActualHeight = clipToActualHeight;
        updateClipping();
    }

    private void selectLayout(boolean animate, boolean force) {
        if (this.mContractedChild == null) {
            return;
        }
        if (this.mUserExpanding) {
            updateContentTransformation();
            return;
        }
        int visibleType = calculateVisibleType();
        boolean changedType = visibleType != this.mVisibleType;
        if (changedType || force) {
            View visibleView = getViewForVisibleType(visibleType);
            if (visibleView != null) {
                visibleView.setVisibility(0);
                transferRemoteInputFocus(visibleType);
            }
            if (animate && ((visibleType == 1 && this.mExpandedChild != null) || ((visibleType == 2 && this.mHeadsUpChild != null) || ((visibleType == 3 && this.mSingleLineView != null) || visibleType == 0)))) {
                animateToVisibleType(visibleType);
            } else {
                updateViewVisibilities(visibleType);
            }
            this.mVisibleType = visibleType;
            if (changedType) {
                focusExpandButtonIfNecessary();
            }
            NotificationViewWrapper visibleWrapper = getVisibleWrapper(visibleType);
            if (visibleWrapper != null) {
                visibleWrapper.setContentHeight(this.mUnrestrictedContentHeight, getMinContentHeightHint());
            }
            updateBackgroundColor(animate);
        }
    }

    private void forceUpdateVisibilities() {
        forceUpdateVisibility(0, this.mContractedChild, this.mContractedWrapper);
        forceUpdateVisibility(1, this.mExpandedChild, this.mExpandedWrapper);
        forceUpdateVisibility(2, this.mHeadsUpChild, this.mHeadsUpWrapper);
        forceUpdateVisibility(3, this.mSingleLineView, this.mSingleLineView);
        forceUpdateVisibility(4, this.mAmbientChild, this.mAmbientWrapper);
        forceUpdateVisibility(5, this.mAmbientSingleLineChild, this.mAmbientSingleLineChild);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
    }

    private void fireExpandedVisibleListenerIfVisible() {
        if (this.mExpandedVisibleListener != null && this.mExpandedChild != null && isShown() && this.mExpandedChild.getVisibility() == 0) {
            Runnable listener = this.mExpandedVisibleListener;
            this.mExpandedVisibleListener = null;
            listener.run();
        }
    }

    private void forceUpdateVisibility(int type, View view, TransformableView wrapper) {
        if (view == null) {
            return;
        }
        boolean visible = this.mVisibleType == type || this.mTransformationStartVisibleType == type;
        if (!visible) {
            view.setVisibility(4);
        } else {
            wrapper.setVisible(true);
        }
    }

    public void updateBackgroundColor(boolean animate) {
        int customBackgroundColor = getBackgroundColor(this.mVisibleType);
        this.mContainingNotification.resetBackgroundAlpha();
        this.mContainingNotification.setContentBackground(customBackgroundColor, animate, this);
    }

    public void setBackgroundTintColor(int color) {
        if (this.mExpandedSmartReplyView != null) {
            this.mExpandedSmartReplyView.setBackgroundTintColor(color);
        }
    }

    public int getVisibleType() {
        return this.mVisibleType;
    }

    public int getBackgroundColorForExpansionState() {
        int visibleType;
        if (this.mContainingNotification.isGroupExpanded() || this.mContainingNotification.isUserLocked()) {
            visibleType = calculateVisibleType();
        } else {
            visibleType = getVisibleType();
        }
        return getBackgroundColor(visibleType);
    }

    public int getBackgroundColor(int visibleType) {
        NotificationViewWrapper currentVisibleWrapper = getVisibleWrapper(visibleType);
        if (currentVisibleWrapper == null) {
            return 0;
        }
        int customBackgroundColor = currentVisibleWrapper.getCustomBackgroundColor();
        return customBackgroundColor;
    }

    private void updateViewVisibilities(int visibleType) {
        updateViewVisibility(visibleType, 0, this.mContractedChild, this.mContractedWrapper);
        updateViewVisibility(visibleType, 1, this.mExpandedChild, this.mExpandedWrapper);
        updateViewVisibility(visibleType, 2, this.mHeadsUpChild, this.mHeadsUpWrapper);
        updateViewVisibility(visibleType, 3, this.mSingleLineView, this.mSingleLineView);
        updateViewVisibility(visibleType, 4, this.mAmbientChild, this.mAmbientWrapper);
        updateViewVisibility(visibleType, 5, this.mAmbientSingleLineChild, this.mAmbientSingleLineChild);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
    }

    private void updateViewVisibility(int visibleType, int type, View view, TransformableView wrapper) {
        if (view != null) {
            wrapper.setVisible(visibleType == type);
        }
    }

    private void animateToVisibleType(int visibleType) {
        TransformableView shownView = getTransformableViewForVisibleType(visibleType);
        final TransformableView hiddenView = getTransformableViewForVisibleType(this.mVisibleType);
        if (shownView == hiddenView || hiddenView == null) {
            shownView.setVisible(true);
            return;
        }
        this.mAnimationStartVisibleType = this.mVisibleType;
        shownView.transformFrom(hiddenView);
        getViewForVisibleType(visibleType).setVisibility(0);
        hiddenView.transformTo(shownView, new Runnable() { // from class: com.android.systemui.statusbar.NotificationContentView.2
            @Override // java.lang.Runnable
            public void run() {
                if (hiddenView != NotificationContentView.this.getTransformableViewForVisibleType(NotificationContentView.this.mVisibleType)) {
                    hiddenView.setVisible(false);
                }
                NotificationContentView.this.mAnimationStartVisibleType = -1;
            }
        });
        fireExpandedVisibleListenerIfVisible();
    }

    private void transferRemoteInputFocus(int visibleType) {
        if (visibleType == 2 && this.mHeadsUpRemoteInput != null && this.mExpandedRemoteInput != null && this.mExpandedRemoteInput.isActive()) {
            this.mHeadsUpRemoteInput.stealFocusFrom(this.mExpandedRemoteInput);
        }
        if (visibleType == 1 && this.mExpandedRemoteInput != null && this.mHeadsUpRemoteInput != null && this.mHeadsUpRemoteInput.isActive()) {
            this.mExpandedRemoteInput.stealFocusFrom(this.mHeadsUpRemoteInput);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TransformableView getTransformableViewForVisibleType(int visibleType) {
        switch (visibleType) {
            case 1:
                return this.mExpandedWrapper;
            case 2:
                return this.mHeadsUpWrapper;
            case 3:
                return this.mSingleLineView;
            case 4:
                return this.mAmbientWrapper;
            case 5:
                return this.mAmbientSingleLineChild;
            default:
                return this.mContractedWrapper;
        }
    }

    private View getViewForVisibleType(int visibleType) {
        switch (visibleType) {
            case 1:
                return this.mExpandedChild;
            case 2:
                return this.mHeadsUpChild;
            case 3:
                return this.mSingleLineView;
            case 4:
                return this.mAmbientChild;
            case 5:
                return this.mAmbientSingleLineChild;
            default:
                return this.mContractedChild;
        }
    }

    public NotificationViewWrapper getVisibleWrapper(int visibleType) {
        if (visibleType != 4) {
            switch (visibleType) {
                case 0:
                    return this.mContractedWrapper;
                case 1:
                    return this.mExpandedWrapper;
                case 2:
                    return this.mHeadsUpWrapper;
                default:
                    return null;
            }
        }
        return this.mAmbientWrapper;
    }

    public int calculateVisibleType() {
        int height;
        int collapsedVisualType;
        if (this.mContainingNotification.isShowingAmbient()) {
            if (this.mIsChildInGroup && this.mAmbientSingleLineChild != null) {
                return 5;
            }
            if (this.mAmbientChild != null) {
                return 4;
            }
            return 0;
        }
        if (this.mUserExpanding) {
            if (!this.mIsChildInGroup || isGroupExpanded() || this.mContainingNotification.isExpanded(true)) {
                height = this.mContainingNotification.getMaxContentHeight();
            } else {
                height = this.mContainingNotification.getShowingLayout().getMinHeight();
            }
            if (height == 0) {
                height = this.mContentHeight;
            }
            int expandedVisualType = getVisualTypeForHeight(height);
            if (this.mIsChildInGroup && !isGroupExpanded()) {
                collapsedVisualType = 3;
            } else {
                collapsedVisualType = getVisualTypeForHeight(this.mContainingNotification.getCollapsedHeight());
            }
            return this.mTransformationStartVisibleType == collapsedVisualType ? expandedVisualType : collapsedVisualType;
        }
        int intrinsicHeight = this.mContainingNotification.getIntrinsicHeight();
        int viewHeight = this.mContentHeight;
        if (intrinsicHeight != 0) {
            viewHeight = Math.min(this.mContentHeight, intrinsicHeight);
        }
        return getVisualTypeForHeight(viewHeight);
    }

    private int getVisualTypeForHeight(float viewHeight) {
        boolean noExpandedChild = this.mExpandedChild == null;
        if (!noExpandedChild && viewHeight == getViewHeight(1)) {
            return 1;
        }
        if (!this.mUserExpanding && this.mIsChildInGroup && !isGroupExpanded()) {
            return 3;
        }
        if ((!this.mIsHeadsUp && !this.mHeadsUpAnimatingAway) || this.mHeadsUpChild == null || this.mContainingNotification.isOnKeyguard()) {
            return (noExpandedChild || (viewHeight <= ((float) getViewHeight(0)) && !(this.mIsChildInGroup && !isGroupExpanded() && this.mContainingNotification.isExpanded(true)))) ? 0 : 1;
        }
        return (viewHeight <= ((float) getViewHeight(2)) || noExpandedChild) ? 2 : 1;
    }

    public boolean isContentExpandable() {
        return this.mIsContentExpandable;
    }

    public void setDark(boolean dark, boolean fade, long delay) {
        if (this.mContractedChild == null) {
            return;
        }
        this.mDark = dark;
        selectLayout(!dark && fade, false);
    }

    public void setHeadsUp(boolean headsUp) {
        this.mIsHeadsUp = headsUp;
        selectLayout(false, true);
        updateExpandButtons(this.mExpandable);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setLegacy(boolean legacy) {
        this.mLegacy = legacy;
        updateLegacy();
    }

    private void updateLegacy() {
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setLegacy(this.mLegacy);
        }
    }

    public void setIsChildInGroup(boolean isChildInGroup) {
        this.mIsChildInGroup = isChildInGroup;
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        if (this.mAmbientChild != null) {
            this.mAmbientWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        updateAllSingleLineViews();
    }

    public void onNotificationUpdated(NotificationData.Entry entry) {
        this.mStatusBarNotification = entry.notification;
        this.mBeforeN = entry.targetSdk < 24;
        updateAllSingleLineViews();
        if (this.mContractedChild != null) {
            this.mContractedWrapper.onContentUpdated(entry.row);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.onContentUpdated(entry.row);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.onContentUpdated(entry.row);
        }
        if (this.mAmbientChild != null) {
            this.mAmbientWrapper.onContentUpdated(entry.row);
        }
        applyRemoteInputAndSmartReply(entry);
        updateLegacy();
        this.mForceSelectNextLayout = true;
        setDark(this.mDark, false, 0L);
        this.mPreviousExpandedRemoteInputIntent = null;
        this.mPreviousHeadsUpRemoteInputIntent = null;
    }

    private void updateAllSingleLineViews() {
        updateSingleLineView();
        updateAmbientSingleLineView();
    }

    private void updateSingleLineView() {
        if (!this.mIsChildInGroup) {
            if (this.mSingleLineView != null) {
                removeView(this.mSingleLineView);
                this.mSingleLineView = null;
                return;
            }
            return;
        }
        boolean isNewView = this.mSingleLineView == null;
        this.mSingleLineView = this.mHybridGroupManager.bindFromNotification(this.mSingleLineView, this.mStatusBarNotification.getNotification());
        if (isNewView) {
            updateViewVisibility(this.mVisibleType, 3, this.mSingleLineView, this.mSingleLineView);
        }
    }

    private void updateAmbientSingleLineView() {
        if (!this.mIsChildInGroup) {
            if (this.mAmbientSingleLineChild != null) {
                removeView(this.mAmbientSingleLineChild);
                this.mAmbientSingleLineChild = null;
                return;
            }
            return;
        }
        boolean isNewView = this.mAmbientSingleLineChild == null;
        this.mAmbientSingleLineChild = this.mHybridGroupManager.bindAmbientFromNotification(this.mAmbientSingleLineChild, this.mStatusBarNotification.getNotification());
        if (isNewView) {
            updateViewVisibility(this.mVisibleType, 5, this.mAmbientSingleLineChild, this.mAmbientSingleLineChild);
        }
    }

    private void applyRemoteInputAndSmartReply(NotificationData.Entry entry) {
        if (this.mRemoteInputController == null) {
            return;
        }
        boolean enableSmartReplies = this.mSmartReplyConstants.isEnabled() && (!this.mSmartReplyConstants.requiresTargetingP() || entry.targetSdk >= 28);
        boolean hasRemoteInput = false;
        RemoteInput remoteInputWithChoices = null;
        PendingIntent pendingIntentWithChoices = null;
        Notification.Action[] actions = entry.notification.getNotification().actions;
        if (actions != null) {
            PendingIntent pendingIntentWithChoices2 = null;
            RemoteInput remoteInputWithChoices2 = null;
            boolean hasRemoteInput2 = false;
            for (Notification.Action a : actions) {
                if (a.getRemoteInputs() != null) {
                    PendingIntent pendingIntentWithChoices3 = pendingIntentWithChoices2;
                    RemoteInput remoteInputWithChoices3 = remoteInputWithChoices2;
                    boolean hasRemoteInput3 = hasRemoteInput2;
                    for (RemoteInput ri : a.getRemoteInputs()) {
                        boolean showRemoteInputView = ri.getAllowFreeFormInput();
                        boolean showSmartReplyView = enableSmartReplies && ri.getChoices() != null && ri.getChoices().length > 0;
                        if (showRemoteInputView) {
                            hasRemoteInput3 = true;
                        }
                        if (showSmartReplyView) {
                            remoteInputWithChoices3 = ri;
                            pendingIntentWithChoices3 = a.actionIntent;
                        }
                        if (showRemoteInputView || showSmartReplyView) {
                            break;
                        }
                    }
                    hasRemoteInput2 = hasRemoteInput3;
                    remoteInputWithChoices2 = remoteInputWithChoices3;
                    pendingIntentWithChoices2 = pendingIntentWithChoices3;
                }
            }
            hasRemoteInput = hasRemoteInput2;
            remoteInputWithChoices = remoteInputWithChoices2;
            pendingIntentWithChoices = pendingIntentWithChoices2;
        }
        applyRemoteInput(entry, hasRemoteInput);
        applySmartReplyView(remoteInputWithChoices, pendingIntentWithChoices, entry);
    }

    private void applyRemoteInput(NotificationData.Entry entry, boolean hasRemoteInput) {
        View bigContentView = this.mExpandedChild;
        if (bigContentView != null) {
            this.mExpandedRemoteInput = applyRemoteInput(bigContentView, entry, hasRemoteInput, this.mPreviousExpandedRemoteInputIntent, this.mCachedExpandedRemoteInput, this.mExpandedWrapper);
        } else {
            this.mExpandedRemoteInput = null;
        }
        if (this.mCachedExpandedRemoteInput != null && this.mCachedExpandedRemoteInput != this.mExpandedRemoteInput) {
            this.mCachedExpandedRemoteInput.dispatchFinishTemporaryDetach();
        }
        this.mCachedExpandedRemoteInput = null;
        View headsUpContentView = this.mHeadsUpChild;
        if (headsUpContentView != null) {
            this.mHeadsUpRemoteInput = applyRemoteInput(headsUpContentView, entry, hasRemoteInput, this.mPreviousHeadsUpRemoteInputIntent, this.mCachedHeadsUpRemoteInput, this.mHeadsUpWrapper);
        } else {
            this.mHeadsUpRemoteInput = null;
        }
        if (this.mCachedHeadsUpRemoteInput != null && this.mCachedHeadsUpRemoteInput != this.mHeadsUpRemoteInput) {
            this.mCachedHeadsUpRemoteInput.dispatchFinishTemporaryDetach();
        }
        this.mCachedHeadsUpRemoteInput = null;
    }

    private RemoteInputView applyRemoteInput(View view, NotificationData.Entry entry, boolean hasRemoteInput, PendingIntent existingPendingIntent, RemoteInputView cachedView, NotificationViewWrapper wrapper) {
        View actionContainerCandidate = view.findViewById(android.R.id.KEYCODE_VIDEO_APP_1);
        if (actionContainerCandidate instanceof FrameLayout) {
            RemoteInputView existing = (RemoteInputView) view.findViewWithTag(RemoteInputView.VIEW_TAG);
            if (existing != null) {
                existing.onNotificationUpdateOrReset();
            }
            if (existing == null && hasRemoteInput) {
                ViewGroup actionContainer = (FrameLayout) actionContainerCandidate;
                if (cachedView == null) {
                    RemoteInputView riv = RemoteInputView.inflate(this.mContext, actionContainer, entry, this.mRemoteInputController);
                    riv.setVisibility(4);
                    actionContainer.addView(riv, new FrameLayout.LayoutParams(-1, -1));
                    existing = riv;
                } else {
                    actionContainer.addView(cachedView);
                    cachedView.dispatchFinishTemporaryDetach();
                    cachedView.requestFocus();
                    existing = cachedView;
                }
            }
            if (hasRemoteInput) {
                int color = entry.notification.getNotification().color;
                if (color == 0) {
                    color = this.mContext.getColor(R.color.default_remote_input_background);
                }
                existing.setBackgroundColor(NotificationColorUtil.ensureTextBackgroundColor(color, this.mContext.getColor(R.color.remote_input_text_enabled), this.mContext.getColor(R.color.remote_input_hint)));
                existing.setWrapper(wrapper);
                existing.setOnVisibilityChangedListener(new Consumer() { // from class: com.android.systemui.statusbar.-$$Lambda$ajnJ2IkdZIqGVFWc6Wtl4AFbcm4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.setRemoteInputVisible(((Boolean) obj).booleanValue());
                    }
                });
                if (existingPendingIntent != null || existing.isActive()) {
                    Notification.Action[] actions = entry.notification.getNotification().actions;
                    if (existingPendingIntent != null) {
                        existing.setPendingIntent(existingPendingIntent);
                    }
                    if (existing.updatePendingIntentFromActions(actions)) {
                        if (!existing.isActive()) {
                            existing.focus();
                        }
                    } else if (existing.isActive()) {
                        existing.close();
                    }
                }
            }
            return existing;
        }
        return null;
    }

    private void applySmartReplyView(RemoteInput remoteInput, PendingIntent pendingIntent, NotificationData.Entry entry) {
        if (this.mExpandedChild != null) {
            this.mExpandedSmartReplyView = applySmartReplyView(this.mExpandedChild, remoteInput, pendingIntent, entry);
            if (this.mExpandedSmartReplyView != null && remoteInput != null && remoteInput.getChoices() != null && remoteInput.getChoices().length > 0) {
                this.mSmartReplyController.smartRepliesAdded(entry, remoteInput.getChoices().length);
            }
        }
    }

    private SmartReplyView applySmartReplyView(View view, RemoteInput remoteInput, PendingIntent pendingIntent, NotificationData.Entry entry) {
        View smartReplyContainerCandidate = view.findViewById(android.R.id.month_name);
        if (!(smartReplyContainerCandidate instanceof LinearLayout)) {
            return null;
        }
        LinearLayout smartReplyContainer = (LinearLayout) smartReplyContainerCandidate;
        if (remoteInput == null || pendingIntent == null) {
            smartReplyContainer.setVisibility(8);
            return null;
        }
        boolean showingSpinner = entry.notification.getNotification().extras.getBoolean("android.remoteInputSpinner", false);
        if (showingSpinner) {
            smartReplyContainer.setVisibility(8);
            return null;
        }
        boolean hideSmartReplies = entry.notification.getNotification().extras.getBoolean("android.hideSmartReplies", false);
        if (hideSmartReplies) {
            smartReplyContainer.setVisibility(8);
            return null;
        }
        SmartReplyView smartReplyView = null;
        if (smartReplyContainer.getChildCount() == 0) {
            smartReplyView = SmartReplyView.inflate(this.mContext, smartReplyContainer);
            smartReplyContainer.addView(smartReplyView);
        } else if (smartReplyContainer.getChildCount() == 1) {
            View child = smartReplyContainer.getChildAt(0);
            if (child instanceof SmartReplyView) {
                smartReplyView = (SmartReplyView) child;
            }
        }
        SmartReplyView smartReplyView2 = smartReplyView;
        if (smartReplyView2 != null) {
            smartReplyView2.setRepliesFromRemoteInput(remoteInput, pendingIntent, this.mSmartReplyController, entry, smartReplyContainer);
            smartReplyContainer.setVisibility(0);
        }
        return smartReplyView2;
    }

    public void closeRemoteInput() {
        if (this.mHeadsUpRemoteInput != null) {
            this.mHeadsUpRemoteInput.close();
        }
        if (this.mExpandedRemoteInput != null) {
            this.mExpandedRemoteInput.close();
        }
    }

    public void setGroupManager(NotificationGroupManager groupManager) {
        this.mGroupManager = groupManager;
    }

    public void setRemoteInputController(RemoteInputController r) {
        this.mRemoteInputController = r;
    }

    public void setExpandClickListener(View.OnClickListener expandClickListener) {
        this.mExpandClickListener = expandClickListener;
    }

    public void updateExpandButtons(boolean expandable) {
        this.mExpandable = expandable;
        if (this.mExpandedChild != null && this.mExpandedChild.getHeight() != 0) {
            if ((!this.mIsHeadsUp && !this.mHeadsUpAnimatingAway) || this.mHeadsUpChild == null || this.mContainingNotification.isOnKeyguard()) {
                if (this.mExpandedChild.getHeight() <= this.mContractedChild.getHeight()) {
                    expandable = false;
                }
            } else if (this.mExpandedChild.getHeight() <= this.mHeadsUpChild.getHeight()) {
                expandable = false;
            }
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.updateExpandability(expandable, this.mExpandClickListener);
        }
        if (this.mContractedChild != null) {
            this.mContractedWrapper.updateExpandability(expandable, this.mExpandClickListener);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.updateExpandability(expandable, this.mExpandClickListener);
        }
        this.mIsContentExpandable = expandable;
    }

    public NotificationHeaderView getNotificationHeader() {
        NotificationHeaderView header = null;
        if (this.mContractedChild != null) {
            header = this.mContractedWrapper.getNotificationHeader();
        }
        if (header == null && this.mExpandedChild != null) {
            header = this.mExpandedWrapper.getNotificationHeader();
        }
        if (header == null && this.mHeadsUpChild != null) {
            header = this.mHeadsUpWrapper.getNotificationHeader();
        }
        if (header == null && this.mAmbientChild != null) {
            NotificationHeaderView header2 = this.mAmbientWrapper.getNotificationHeader();
            return header2;
        }
        return header;
    }

    public void showAppOpsIcons(ArraySet<Integer> activeOps) {
        if (this.mContractedChild != null && this.mContractedWrapper.getNotificationHeader() != null) {
            this.mContractedWrapper.getNotificationHeader().showAppOpsIcons(activeOps);
        }
        if (this.mExpandedChild != null && this.mExpandedWrapper.getNotificationHeader() != null) {
            this.mExpandedWrapper.getNotificationHeader().showAppOpsIcons(activeOps);
        }
        if (this.mHeadsUpChild != null && this.mHeadsUpWrapper.getNotificationHeader() != null) {
            this.mHeadsUpWrapper.getNotificationHeader().showAppOpsIcons(activeOps);
        }
    }

    public NotificationHeaderView getContractedNotificationHeader() {
        if (this.mContractedChild != null) {
            return this.mContractedWrapper.getNotificationHeader();
        }
        return null;
    }

    public NotificationHeaderView getVisibleNotificationHeader() {
        NotificationViewWrapper wrapper = getVisibleWrapper(this.mVisibleType);
        if (wrapper == null) {
            return null;
        }
        return wrapper.getNotificationHeader();
    }

    public void setContainingNotification(ExpandableNotificationRow containingNotification) {
        this.mContainingNotification = containingNotification;
    }

    public void requestSelectLayout(boolean needsAnimation) {
        selectLayout(needsAnimation, false);
    }

    public void reInflateViews() {
        if (this.mIsChildInGroup && this.mSingleLineView != null) {
            removeView(this.mSingleLineView);
            this.mSingleLineView = null;
            updateAllSingleLineViews();
        }
    }

    public void setUserExpanding(boolean userExpanding) {
        this.mUserExpanding = userExpanding;
        if (userExpanding) {
            this.mTransformationStartVisibleType = this.mVisibleType;
            return;
        }
        this.mTransformationStartVisibleType = -1;
        this.mVisibleType = calculateVisibleType();
        updateViewVisibilities(this.mVisibleType);
        updateBackgroundColor(false);
    }

    public void setSingleLineWidthIndention(int singleLineWidthIndention) {
        if (singleLineWidthIndention != this.mSingleLineWidthIndention) {
            this.mSingleLineWidthIndention = singleLineWidthIndention;
            this.mContainingNotification.forceLayout();
            forceLayout();
        }
    }

    public HybridNotificationView getSingleLineView() {
        return this.mSingleLineView;
    }

    public void setRemoved() {
        if (this.mExpandedRemoteInput != null) {
            this.mExpandedRemoteInput.setRemoved();
        }
        if (this.mHeadsUpRemoteInput != null) {
            this.mHeadsUpRemoteInput.setRemoved();
        }
    }

    public void setContentHeightAnimating(boolean animating) {
        if (!animating) {
            this.mContentHeightAtAnimationStart = -1;
        }
    }

    @VisibleForTesting
    boolean isAnimatingVisibleType() {
        return this.mAnimationStartVisibleType != -1;
    }

    public void setHeadsUpAnimatingAway(boolean headsUpAnimatingAway) {
        this.mHeadsUpAnimatingAway = headsUpAnimatingAway;
        selectLayout(false, true);
    }

    public void setFocusOnVisibilityChange() {
        this.mFocusOnVisibilityChange = true;
    }

    public void setIconsVisible(boolean iconsVisible) {
        this.mIconsVisible = iconsVisible;
        updateIconVisibilities();
    }

    private void updateIconVisibilities() {
        NotificationHeaderView header;
        NotificationHeaderView header2;
        NotificationHeaderView header3;
        if (this.mContractedWrapper != null && (header3 = this.mContractedWrapper.getNotificationHeader()) != null) {
            header3.getIcon().setForceHidden(!this.mIconsVisible);
        }
        if (this.mHeadsUpWrapper != null && (header2 = this.mHeadsUpWrapper.getNotificationHeader()) != null) {
            header2.getIcon().setForceHidden(!this.mIconsVisible);
        }
        if (this.mExpandedWrapper != null && (header = this.mExpandedWrapper.getNotificationHeader()) != null) {
            header.getIcon().setForceHidden(!this.mIconsVisible);
        }
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible) {
            fireExpandedVisibleListenerIfVisible();
        }
    }

    public void setOnExpandedVisibleListener(Runnable r) {
        this.mExpandedVisibleListener = r;
        fireExpandedVisibleListenerIfVisible();
    }

    public void setIsLowPriority(boolean isLowPriority) {
        this.mIsLowPriority = isLowPriority;
    }

    public boolean isDimmable() {
        if (!this.mContractedWrapper.isDimmable()) {
            return false;
        }
        return true;
    }

    public boolean disallowSingleClick(float x, float y) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(getVisibleType());
        if (visibleWrapper != null) {
            return visibleWrapper.disallowSingleClick(x, y);
        }
        return false;
    }

    public boolean shouldClipToRounding(boolean topRounded, boolean bottomRounded) {
        boolean needsPaddings = shouldClipToRounding(getVisibleType(), topRounded, bottomRounded);
        if (this.mUserExpanding) {
            return needsPaddings | shouldClipToRounding(this.mTransformationStartVisibleType, topRounded, bottomRounded);
        }
        return needsPaddings;
    }

    private boolean shouldClipToRounding(int visibleType, boolean topRounded, boolean bottomRounded) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(visibleType);
        if (visibleWrapper == null) {
            return false;
        }
        return visibleWrapper.shouldClipToRounding(topRounded, bottomRounded);
    }

    public CharSequence getActiveRemoteInputText() {
        if (this.mExpandedRemoteInput != null && this.mExpandedRemoteInput.isActive()) {
            return this.mExpandedRemoteInput.getText();
        }
        if (this.mHeadsUpRemoteInput != null && this.mHeadsUpRemoteInput.isActive()) {
            return this.mHeadsUpRemoteInput.getText();
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        RemoteInputView riv = getRemoteInputForView(getViewForVisibleType(this.mVisibleType));
        if (riv != null && riv.getVisibility() == 0) {
            int inputStart = this.mUnrestrictedContentHeight - riv.getHeight();
            if (y <= this.mUnrestrictedContentHeight && y >= inputStart) {
                ev.offsetLocation(0.0f, -inputStart);
                return riv.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean pointInView(float localX, float localY, float slop) {
        float top = this.mClipTopAmount;
        float bottom = this.mUnrestrictedContentHeight;
        return localX >= (-slop) && localY >= top - slop && localX < ((float) (this.mRight - this.mLeft)) + slop && localY < bottom + slop;
    }

    private RemoteInputView getRemoteInputForView(View child) {
        if (child == this.mExpandedChild) {
            return this.mExpandedRemoteInput;
        }
        if (child == this.mHeadsUpChild) {
            return this.mHeadsUpRemoteInput;
        }
        return null;
    }

    public int getExpandHeight() {
        int viewType = 1;
        if (this.mExpandedChild == null) {
            viewType = 0;
        }
        return getViewHeight(viewType) + getExtraRemoteInputHeight(this.mExpandedRemoteInput);
    }

    public int getHeadsUpHeight() {
        int viewType = 2;
        if (this.mHeadsUpChild == null) {
            viewType = 0;
        }
        return getViewHeight(viewType) + getExtraRemoteInputHeight(this.mHeadsUpRemoteInput) + getExtraRemoteInputHeight(this.mExpandedRemoteInput);
    }

    public void setRemoteInputVisible(boolean remoteInputVisible) {
        this.mRemoteInputVisible = remoteInputVisible;
        setClipChildren(!remoteInputVisible);
    }

    @Override // android.view.ViewGroup
    public void setClipChildren(boolean clipChildren) {
        boolean clipChildren2 = clipChildren && !this.mRemoteInputVisible;
        super.setClipChildren(clipChildren2);
    }

    public void setHeaderVisibleAmount(float headerVisibleAmount) {
        if (this.mContractedWrapper != null) {
            this.mContractedWrapper.setHeaderVisibleAmount(headerVisibleAmount);
        }
        if (this.mHeadsUpWrapper != null) {
            this.mHeadsUpWrapper.setHeaderVisibleAmount(headerVisibleAmount);
        }
        if (this.mExpandedWrapper != null) {
            this.mExpandedWrapper.setHeaderVisibleAmount(headerVisibleAmount);
        }
    }
}
