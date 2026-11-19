package com.android.systemui.statusbar.policy;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.NotificationColorUtil;
import com.android.keyguard.KeyguardHostView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.text.BreakIterator;
import java.util.Comparator;
import java.util.PriorityQueue;

/* JADX INFO: loaded from: classes.dex */
public class SmartReplyView extends ViewGroup {
    private final BreakIterator mBreakIterator;
    private PriorityQueue<Button> mCandidateButtonQueueForSqueezing;
    private final SmartReplyConstants mConstants;
    private int mCurrentBackgroundColor;
    private final int mDefaultBackgroundColor;
    private final int mDefaultStrokeColor;
    private final int mDefaultTextColor;
    private final int mDefaultTextColorDarkBg;
    private final int mDoubleLineButtonPaddingHorizontal;
    private final int mHeightUpperLimit;
    private final KeyguardDismissUtil mKeyguardDismissUtil;
    private final double mMinStrokeContrast;
    private final int mRippleColor;
    private final int mRippleColorDarkBg;
    private final int mSingleLineButtonPaddingHorizontal;
    private final int mSingleToDoubleLineButtonWidthIncrease;
    private View mSmartReplyContainer;
    private final int mSpacing;
    private final int mStrokeWidth;
    private static final int MEASURE_SPEC_ANY_WIDTH = View.MeasureSpec.makeMeasureSpec(0, 0);
    private static final Comparator<View> DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR = new Comparator() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$SmartReplyView$UA3QkbRzztEFRlbb86djKcGIV5E
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return SmartReplyView.lambda$static$0((View) obj, (View) obj2);
        }
    };

    static /* synthetic */ int lambda$static$0(View v1, View v2) {
        return ((v2.getMeasuredWidth() - v2.getPaddingLeft()) - v2.getPaddingRight()) - ((v1.getMeasuredWidth() - v1.getPaddingLeft()) - v1.getPaddingRight());
    }

    public SmartReplyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mConstants = (SmartReplyConstants) Dependency.get(SmartReplyConstants.class);
        this.mKeyguardDismissUtil = (KeyguardDismissUtil) Dependency.get(KeyguardDismissUtil.class);
        this.mHeightUpperLimit = NotificationUtils.getFontScaledHeight(this.mContext, R.dimen.smart_reply_button_max_height);
        this.mCurrentBackgroundColor = context.getColor(R.color.smart_reply_button_background);
        this.mDefaultBackgroundColor = this.mCurrentBackgroundColor;
        this.mDefaultTextColor = this.mContext.getColor(R.color.smart_reply_button_text);
        this.mDefaultTextColorDarkBg = this.mContext.getColor(R.color.smart_reply_button_text_dark_bg);
        this.mDefaultStrokeColor = this.mContext.getColor(R.color.smart_reply_button_stroke);
        this.mRippleColor = this.mContext.getColor(R.color.notification_ripple_untinted_color);
        this.mRippleColorDarkBg = Color.argb(Color.alpha(this.mRippleColor), 255, 255, 255);
        this.mMinStrokeContrast = NotificationColorUtil.calculateContrast(this.mDefaultStrokeColor, this.mDefaultBackgroundColor);
        int strokeWidth = 0;
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SmartReplyView, 0, 0);
        int length = arr.getIndexCount();
        int doubleLineButtonPaddingHorizontal = 0;
        int doubleLineButtonPaddingHorizontal2 = 0;
        int spacing = 0;
        for (int spacing2 = 0; spacing2 < length; spacing2++) {
            int attr = arr.getIndex(spacing2);
            switch (attr) {
                case 0:
                    strokeWidth = arr.getDimensionPixelSize(spacing2, 0);
                    break;
                case 1:
                    doubleLineButtonPaddingHorizontal = arr.getDimensionPixelSize(spacing2, 0);
                    break;
                case 2:
                    doubleLineButtonPaddingHorizontal2 = arr.getDimensionPixelSize(spacing2, 0);
                    break;
                case 3:
                    spacing = arr.getDimensionPixelSize(spacing2, 0);
                    break;
            }
        }
        arr.recycle();
        this.mStrokeWidth = strokeWidth;
        this.mSpacing = spacing;
        this.mSingleLineButtonPaddingHorizontal = doubleLineButtonPaddingHorizontal2;
        this.mDoubleLineButtonPaddingHorizontal = doubleLineButtonPaddingHorizontal;
        this.mSingleToDoubleLineButtonWidthIncrease = 2 * (doubleLineButtonPaddingHorizontal - doubleLineButtonPaddingHorizontal2);
        this.mBreakIterator = BreakIterator.getLineInstance();
        reallocateCandidateButtonQueueForSqueezing();
    }

    public int getHeightUpperLimit() {
        return this.mHeightUpperLimit;
    }

    private void reallocateCandidateButtonQueueForSqueezing() {
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue<>(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    public void setRepliesFromRemoteInput(RemoteInput remoteInput, PendingIntent pendingIntent, SmartReplyController smartReplyController, NotificationData.Entry entry, View smartReplyContainer) {
        CharSequence[] choices;
        this.mSmartReplyContainer = smartReplyContainer;
        removeAllViews();
        this.mCurrentBackgroundColor = this.mDefaultBackgroundColor;
        if (remoteInput != null && pendingIntent != null && (choices = remoteInput.getChoices()) != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                int i3 = choices.length;
                if (i2 >= i3) {
                    break;
                }
                Button replyButton = inflateReplyButton(getContext(), this, i2, choices[i2], remoteInput, pendingIntent, smartReplyController, entry);
                addView(replyButton);
                i = i2 + 1;
            }
        }
        reallocateCandidateButtonQueueForSqueezing();
    }

    public static SmartReplyView inflate(Context context, ViewGroup root) {
        return (SmartReplyView) LayoutInflater.from(context).inflate(R.layout.smart_reply_view, root, false);
    }

    @VisibleForTesting
    Button inflateReplyButton(final Context context, ViewGroup root, final int replyIndex, final CharSequence choice, final RemoteInput remoteInput, final PendingIntent pendingIntent, final SmartReplyController smartReplyController, final NotificationData.Entry entry) {
        final Button b = (Button) LayoutInflater.from(context).inflate(R.layout.smart_reply_button, root, false);
        b.setText(choice);
        final KeyguardHostView.OnDismissAction action = new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$SmartReplyView$UwLdHmeZSqfnZQeDlBO5lJ6m-GM
            @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
            public final boolean onDismiss() {
                return SmartReplyView.lambda$inflateReplyButton$1(this.f$0, smartReplyController, entry, replyIndex, b, remoteInput, choice, pendingIntent, context);
            }
        };
        b.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$SmartReplyView$oc5xsdTBKPaigeImxuPKm7py0NE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.mKeyguardDismissUtil.executeWhenUnlocked(action);
            }
        });
        b.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.policy.SmartReplyView.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                String label = SmartReplyView.this.getResources().getString(R.string.accessibility_send_smart_reply);
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, label));
            }
        });
        setColors(b, this.mCurrentBackgroundColor, this.mDefaultStrokeColor, this.mDefaultTextColor, this.mRippleColor);
        return b;
    }

    public static /* synthetic */ boolean lambda$inflateReplyButton$1(SmartReplyView smartReplyView, SmartReplyController smartReplyController, NotificationData.Entry entry, int replyIndex, Button b, RemoteInput remoteInput, CharSequence choice, PendingIntent pendingIntent, Context context) {
        smartReplyController.smartReplySent(entry, replyIndex, b.getText());
        Bundle results = new Bundle();
        results.putString(remoteInput.getResultKey(), choice.toString());
        Intent intent = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(new RemoteInput[]{remoteInput}, intent, results);
        RemoteInput.setResultsSource(intent, 1);
        entry.setHasSentReply();
        try {
            pendingIntent.send(context, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            Log.w("SmartReplyView", "Unable to send smart reply", e);
        }
        smartReplyView.mSmartReplyContainer.setVisibility(8);
        return false;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.mContext, attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = -2;
        return new LayoutParams(i, i);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams params) {
        return new LayoutParams(params.width, params.height);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0050  */
    /* JADX WARN: Code duplicated, block: B:53:0x0115  */
    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int buttonPaddingHorizontal;
        int targetWidth = View.MeasureSpec.getMode(widthMeasureSpec) == 0 ? Integer.MAX_VALUE : View.MeasureSpec.getSize(widthMeasureSpec);
        resetButtonsLayoutParams();
        if (!this.mCandidateButtonQueueForSqueezing.isEmpty()) {
            Log.wtf("SmartReplyView", "Single line button queue leaked between onMeasure calls");
            this.mCandidateButtonQueueForSqueezing.clear();
        }
        int measuredWidth = this.mPaddingLeft + this.mPaddingRight;
        int maxChildHeight = 0;
        int buttonPaddingHorizontal2 = this.mSingleLineButtonPaddingHorizontal;
        int childCount = getChildCount();
        int displayedChildCount = 0;
        int measuredWidth2 = measuredWidth;
        int measuredWidth3 = 0;
        while (measuredWidth3 < childCount) {
            View child = getChildAt(measuredWidth3);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() != 0) {
                childCount = childCount;
            } else if (!(child instanceof Button)) {
                childCount = childCount;
            } else {
                child.setPadding(buttonPaddingHorizontal2, child.getPaddingTop(), buttonPaddingHorizontal2, child.getPaddingBottom());
                child.measure(MEASURE_SPEC_ANY_WIDTH, heightMeasureSpec);
                int lineCount = ((Button) child).getLineCount();
                if (lineCount < 1) {
                    childCount = childCount;
                } else if (lineCount > 2) {
                    childCount = childCount;
                } else {
                    if (lineCount == 1) {
                        this.mCandidateButtonQueueForSqueezing.add((Button) child);
                    }
                    int originalMaxChildHeight = maxChildHeight;
                    int originalMeasuredWidth = measuredWidth2;
                    int originalButtonPaddingHorizontal = buttonPaddingHorizontal2;
                    int spacing = displayedChildCount == 0 ? 0 : this.mSpacing;
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    measuredWidth2 += spacing + childWidth;
                    int maxChildHeight2 = Math.max(maxChildHeight, childHeight);
                    int maxChildHeight3 = this.mSingleLineButtonPaddingHorizontal;
                    boolean increaseToTwoLines = buttonPaddingHorizontal2 == maxChildHeight3 && (lineCount == 2 || measuredWidth2 > targetWidth);
                    if (increaseToTwoLines) {
                        measuredWidth2 += (displayedChildCount + 1) * this.mSingleToDoubleLineButtonWidthIncrease;
                        buttonPaddingHorizontal2 = this.mDoubleLineButtonPaddingHorizontal;
                    }
                    if (measuredWidth2 > targetWidth) {
                        while (true) {
                            if (measuredWidth2 <= targetWidth) {
                                maxChildHeight = maxChildHeight2;
                                buttonPaddingHorizontal = buttonPaddingHorizontal2;
                                break;
                            }
                            buttonPaddingHorizontal = buttonPaddingHorizontal2;
                            if (this.mCandidateButtonQueueForSqueezing.isEmpty()) {
                                maxChildHeight = maxChildHeight2;
                                break;
                            }
                            Button candidate = this.mCandidateButtonQueueForSqueezing.poll();
                            int childCount2 = childCount;
                            int childCount3 = squeezeButton(candidate, heightMeasureSpec);
                            View child2 = child;
                            if (childCount3 != -1) {
                                maxChildHeight = maxChildHeight2;
                                maxChildHeight = Math.max(maxChildHeight, candidate.getMeasuredHeight());
                                measuredWidth2 -= childCount3;
                            } else {
                                maxChildHeight = maxChildHeight2;
                            }
                            buttonPaddingHorizontal2 = buttonPaddingHorizontal;
                            childCount = childCount2;
                            child = child2;
                        }
                        if (measuredWidth2 > targetWidth) {
                            measuredWidth2 = originalMeasuredWidth;
                            maxChildHeight = originalMaxChildHeight;
                            buttonPaddingHorizontal2 = originalButtonPaddingHorizontal;
                            markButtonsWithPendingSqueezeStatusAs(3, measuredWidth3);
                            break;
                        }
                        markButtonsWithPendingSqueezeStatusAs(2, measuredWidth3);
                    } else {
                        buttonPaddingHorizontal = buttonPaddingHorizontal2;
                        childCount = childCount;
                        maxChildHeight = maxChildHeight2;
                    }
                    lp.show = true;
                    displayedChildCount++;
                    buttonPaddingHorizontal2 = buttonPaddingHorizontal;
                }
            }
            measuredWidth3++;
            childCount = childCount;
        }
        this.mCandidateButtonQueueForSqueezing.clear();
        remeasureButtonsIfNecessary(buttonPaddingHorizontal2, maxChildHeight);
        setMeasuredDimension(resolveSize(Math.max(getSuggestedMinimumWidth(), measuredWidth2), widthMeasureSpec), resolveSize(Math.max(getSuggestedMinimumHeight(), this.mPaddingTop + maxChildHeight + this.mPaddingBottom), heightMeasureSpec));
    }

    private void resetButtonsLayoutParams() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.show = false;
            lp.squeezeStatus = 0;
        }
    }

    private int squeezeButton(Button button, int heightMeasureSpec) {
        int estimatedOptimalTextWidth = estimateOptimalSqueezedButtonTextWidth(button);
        if (estimatedOptimalTextWidth == -1) {
            return -1;
        }
        return squeezeButtonToTextWidth(button, heightMeasureSpec, estimatedOptimalTextWidth);
    }

    private int estimateOptimalSqueezedButtonTextWidth(Button button) {
        String rawText = button.getText().toString();
        TransformationMethod transformation = button.getTransformationMethod();
        String text = transformation == null ? rawText : transformation.getTransformation(rawText, button).toString();
        int length = text.length();
        this.mBreakIterator.setText(text);
        int i = -1;
        if (this.mBreakIterator.preceding(length / 2) == -1 && this.mBreakIterator.next() == -1) {
            return -1;
        }
        TextPaint paint = button.getPaint();
        int initialPosition = this.mBreakIterator.current();
        int i2 = 0;
        float initialLeftTextWidth = Layout.getDesiredWidth(text, 0, initialPosition, paint);
        float initialRightTextWidth = Layout.getDesiredWidth(text, initialPosition, length, paint);
        float optimalTextWidth = Math.max(initialLeftTextWidth, initialRightTextWidth);
        if (initialLeftTextWidth != initialRightTextWidth) {
            boolean moveLeft = initialLeftTextWidth > initialRightTextWidth;
            int maxSqueezeRemeasureAttempts = this.mConstants.getMaxSqueezeRemeasureAttempts();
            float optimalTextWidth2 = optimalTextWidth;
            int i3 = 0;
            while (i3 < maxSqueezeRemeasureAttempts) {
                int newPosition = moveLeft ? this.mBreakIterator.previous() : this.mBreakIterator.next();
                if (newPosition != i) {
                    float newLeftTextWidth = Layout.getDesiredWidth(text, i2, newPosition, paint);
                    float newRightTextWidth = Layout.getDesiredWidth(text, newPosition, length, paint);
                    float newOptimalTextWidth = Math.max(newLeftTextWidth, newRightTextWidth);
                    if (newOptimalTextWidth >= optimalTextWidth2) {
                        break;
                    }
                    optimalTextWidth2 = newOptimalTextWidth;
                    boolean tooFar = !moveLeft ? newLeftTextWidth < newRightTextWidth : newLeftTextWidth > newRightTextWidth;
                    if (tooFar) {
                        break;
                    }
                    i3++;
                    i = -1;
                    i2 = 0;
                } else {
                    break;
                }
            }
            optimalTextWidth = optimalTextWidth2;
        }
        return (int) Math.ceil(optimalTextWidth);
    }

    private int squeezeButtonToTextWidth(Button button, int heightMeasureSpec, int textWidth) {
        int oldWidth = button.getMeasuredWidth();
        if (button.getPaddingLeft() != this.mDoubleLineButtonPaddingHorizontal) {
            oldWidth += this.mSingleToDoubleLineButtonWidthIncrease;
        }
        button.setPadding(this.mDoubleLineButtonPaddingHorizontal, button.getPaddingTop(), this.mDoubleLineButtonPaddingHorizontal, button.getPaddingBottom());
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((this.mDoubleLineButtonPaddingHorizontal * 2) + textWidth, Integer.MIN_VALUE);
        button.measure(widthMeasureSpec, heightMeasureSpec);
        int newWidth = button.getMeasuredWidth();
        LayoutParams lp = (LayoutParams) button.getLayoutParams();
        if (button.getLineCount() > 2 || newWidth >= oldWidth) {
            lp.squeezeStatus = 3;
            return -1;
        }
        lp.squeezeStatus = 1;
        return oldWidth - newWidth;
    }

    private void remeasureButtonsIfNecessary(int buttonPaddingHorizontal, int maxChildHeight) {
        int maxChildHeightMeasure = View.MeasureSpec.makeMeasureSpec(maxChildHeight, 1073741824);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.show) {
                boolean requiresNewMeasure = false;
                int newWidth = child.getMeasuredWidth();
                if (lp.squeezeStatus == 3) {
                    requiresNewMeasure = true;
                    newWidth = Integer.MAX_VALUE;
                }
                if (child.getPaddingLeft() != buttonPaddingHorizontal) {
                    requiresNewMeasure = true;
                    if (newWidth != Integer.MAX_VALUE) {
                        if (buttonPaddingHorizontal == this.mSingleLineButtonPaddingHorizontal) {
                            newWidth -= this.mSingleToDoubleLineButtonWidthIncrease;
                        } else {
                            newWidth += this.mSingleToDoubleLineButtonWidthIncrease;
                        }
                    }
                    child.setPadding(buttonPaddingHorizontal, child.getPaddingTop(), buttonPaddingHorizontal, child.getPaddingBottom());
                }
                if (child.getMeasuredHeight() != maxChildHeight) {
                    requiresNewMeasure = true;
                }
                if (requiresNewMeasure) {
                    child.measure(View.MeasureSpec.makeMeasureSpec(newWidth, Integer.MIN_VALUE), maxChildHeightMeasure);
                }
            }
        }
    }

    private void markButtonsWithPendingSqueezeStatusAs(int squeezeStatus, int maxChildIndex) {
        for (int i = 0; i <= maxChildIndex; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.squeezeStatus == 1) {
                lp.squeezeStatus = squeezeStatus;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        boolean isRtl = getLayoutDirection() == 1;
        int width = right - left;
        int position = isRtl ? width - this.mPaddingRight : this.mPaddingLeft;
        int childCount = getChildCount();
        int position2 = position;
        for (int position3 = 0; position3 < childCount; position3++) {
            View child = getChildAt(position3);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.show) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int childLeft = isRtl ? position2 - childWidth : position2;
                child.layout(childLeft, 0, childLeft + childWidth, childHeight);
                int childWidthWithSpacing = this.mSpacing + childWidth;
                if (isRtl) {
                    position2 -= childWidthWithSpacing;
                } else {
                    position2 += childWidthWithSpacing;
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return lp.show && super.drawChild(canvas, child, drawingTime);
    }

    public void setBackgroundTintColor(int backgroundColor) {
        if (backgroundColor == this.mCurrentBackgroundColor) {
            return;
        }
        this.mCurrentBackgroundColor = backgroundColor;
        boolean dark = !NotificationColorUtil.isColorLight(backgroundColor);
        int textColor = NotificationColorUtil.ensureTextContrast(dark ? this.mDefaultTextColorDarkBg : this.mDefaultTextColor, backgroundColor | (-16777216), dark);
        int strokeColor = NotificationColorUtil.ensureContrast(this.mDefaultStrokeColor, (-16777216) | backgroundColor, dark, this.mMinStrokeContrast);
        int rippleColor = dark ? this.mRippleColorDarkBg : this.mRippleColor;
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < childCount) {
                Button child = (Button) getChildAt(i2);
                setColors(child, backgroundColor, strokeColor, textColor, rippleColor);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private void setColors(Button button, int backgroundColor, int strokeColor, int textColor, int rippleColor) {
        Drawable drawable = button.getBackground();
        if (drawable instanceof RippleDrawable) {
            Drawable drawable2 = drawable.mutate();
            RippleDrawable ripple = (RippleDrawable) drawable2;
            ripple.setColor(ColorStateList.valueOf(rippleColor));
            Drawable inset = ripple.getDrawable(0);
            if (inset instanceof InsetDrawable) {
                Drawable background = ((InsetDrawable) inset).getDrawable();
                if (background instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) background;
                    gradientDrawable.setColor(backgroundColor);
                    gradientDrawable.setStroke(this.mStrokeWidth, strokeColor);
                }
            }
            button.setBackground(drawable2);
        }
        button.setTextColor(textColor);
    }

    @VisibleForTesting
    static class LayoutParams extends ViewGroup.LayoutParams {
        private boolean show;
        private int squeezeStatus;

        private LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.show = false;
            this.squeezeStatus = 0;
        }

        private LayoutParams(int width, int height) {
            super(width, height);
            this.show = false;
            this.squeezeStatus = 0;
        }

        @VisibleForTesting
        boolean isShown() {
            return this.show;
        }
    }
}
