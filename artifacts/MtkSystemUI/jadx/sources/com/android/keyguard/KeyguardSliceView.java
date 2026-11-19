package com.android.keyguard;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Trace;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceViewManager;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceLiveData;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.statusbar.AlphaOptimizedTextView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.wakelock.KeepAwakeAnimationListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardSliceView extends LinearLayout implements Observer<Slice>, View.OnClickListener, ConfigurationController.ConfigurationListener, TunerService.Tunable {
    private final HashMap<View, PendingIntent> mClickActions;
    private Runnable mContentChangeListener;
    private float mDarkAmount;
    private boolean mHasHeader;
    private int mIconSize;
    private Uri mKeyguardSliceUri;
    private LiveData<Slice> mLiveData;
    private boolean mPulsing;
    private Row mRow;
    private Slice mSlice;
    private int mTextColor;

    @VisibleForTesting
    TextView mTitle;

    public KeyguardSliceView(Context context) {
        this(context, null, 0);
    }

    public KeyguardSliceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyguardSliceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDarkAmount = 0.0f;
        TunerService tunerService = (TunerService) Dependency.get(TunerService.class);
        tunerService.addTunable(this, "keyguard_slice_uri");
        this.mClickActions = new HashMap<>();
        LayoutTransition transition = new LayoutTransition();
        transition.setStagger(0, 275L);
        transition.setDuration(2, 550L);
        transition.setDuration(3, 275L);
        transition.disableTransitionType(0);
        transition.disableTransitionType(1);
        transition.setInterpolator(2, Interpolators.FAST_OUT_SLOW_IN);
        transition.setInterpolator(3, Interpolators.ALPHA_OUT);
        transition.setAnimateParentHierarchy(false);
        transition.addTransitionListener(new SliceViewTransitionListener());
        setLayoutTransition(transition);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(com.android.systemui.R.id.title);
        this.mRow = (Row) findViewById(com.android.systemui.R.id.row);
        this.mTextColor = Utils.getColorAttr(this.mContext, com.android.systemui.R.attr.wallpaperTextColor);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLiveData.observeForever(this);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mLiveData.removeObserver(this);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v3, types: [com.android.keyguard.KeyguardSliceView$KeyguardSliceButton, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r18v0 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [int] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private void showSlice() throws FileNotFoundException {
        ?? r13;
        int i;
        Trace.beginSection("KeyguardSliceView#showSlice");
        if (this.mPulsing || this.mSlice == null) {
            this.mTitle.setVisibility(8);
            this.mRow.setVisibility(8);
            if (this.mContentChangeListener != null) {
                this.mContentChangeListener.run();
                return;
            }
            return;
        }
        ListContent listContent = new ListContent(getContext(), this.mSlice);
        this.mHasHeader = listContent.hasHeader();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < listContent.getRowItems().size(); i3++) {
            SliceItem sliceItem = listContent.getRowItems().get(i3);
            if (!"content://com.android.systemui.keyguard/action".equals(sliceItem.getSlice().getUri().toString())) {
                arrayList.add(sliceItem);
            }
        }
        boolean z = true;
        if (this.mHasHeader) {
            this.mTitle.setVisibility(0);
            SliceItem titleItem = new RowContent(getContext(), (SliceItem) arrayList.get(0), true).getTitleItem();
            this.mTitle.setText(titleItem != null ? titleItem.getText() : null);
        } else {
            this.mTitle.setVisibility(8);
        }
        this.mClickActions.clear();
        int size = arrayList.size();
        int textColor = getTextColor();
        boolean z2 = this.mHasHeader;
        this.mRow.setVisibility(size > 0 ? 0 : 8);
        ?? r3 = arrayList;
        for (?? r2 = z2; r2 < size; r2++) {
            SliceItem sliceItem2 = (SliceItem) r3.get(r2);
            RowContent rowContent = new RowContent(getContext(), sliceItem2, z);
            Uri uri = sliceItem2.getSlice().getUri();
            KeyguardSliceButton keyguardSliceButton = (KeyguardSliceButton) this.mRow.findViewWithTag(uri);
            if (keyguardSliceButton == null) {
                r13 = keyguardSliceButton;
                KeyguardSliceButton keyguardSliceButton2 = new KeyguardSliceButton(this.mContext);
                keyguardSliceButton2.setTextColor(textColor);
                keyguardSliceButton2.setTag(uri);
                this.mRow.addView(keyguardSliceButton2, r2 - (this.mHasHeader ? 1 : 0));
                r13 = keyguardSliceButton2;
            }
            r13 = keyguardSliceButton;
            PendingIntent action = null;
            if (rowContent.getPrimaryAction() != null) {
                action = rowContent.getPrimaryAction().getAction();
            }
            this.mClickActions.put((View) r13, action);
            SliceItem titleItem2 = rowContent.getTitleItem();
            r13.setText(titleItem2 == null ? null : titleItem2.getText());
            r13.setContentDescription(rowContent.getContentDescription());
            Drawable drawableLoadDrawable = null;
            SliceItem sliceItemFind = SliceQuery.find(sliceItem2.getSlice(), "image");
            if (sliceItemFind != null) {
                drawableLoadDrawable = sliceItemFind.getIcon().loadDrawable(this.mContext);
                i = 0;
                drawableLoadDrawable.setBounds(0, 0, Math.max((int) ((drawableLoadDrawable.getIntrinsicWidth() / drawableLoadDrawable.getIntrinsicHeight()) * this.mIconSize), 1), this.mIconSize);
            } else {
                i = 0;
            }
            r13.setCompoundDrawables(drawableLoadDrawable, null, null, null);
            r13.setOnClickListener(this);
            r13.setClickable(action != null ? 1 : i);
            i2 = i;
            listContent = listContent;
            r3 = r3;
            z = true;
        }
        int i4 = i2;
        while (i4 < this.mRow.getChildCount()) {
            View childAt = this.mRow.getChildAt(i4);
            if (!this.mClickActions.containsKey(childAt)) {
                this.mRow.removeView(childAt);
                i4--;
            }
            i4++;
        }
        if (this.mContentChangeListener != null) {
            this.mContentChangeListener.run();
        }
        Trace.endSection();
    }

    public void setPulsing(boolean pulsing, boolean animate) throws FileNotFoundException {
        this.mPulsing = pulsing;
        LayoutTransition transition = getLayoutTransition();
        if (!animate) {
            setLayoutTransition(null);
        }
        showSlice();
        if (!animate) {
            setLayoutTransition(transition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence findBestLineBreak(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        String source = charSequence.toString();
        if (source.contains("\n") || !source.contains(" ")) {
            return source;
        }
        String[] words = source.split(" ");
        StringBuilder optimalString = new StringBuilder(source.length());
        int current = 0;
        while (optimalString.length() < source.length() - optimalString.length()) {
            optimalString.append(words[current]);
            if (current < words.length - 1) {
                optimalString.append(" ");
            }
            current++;
        }
        optimalString.append("\n");
        for (int i = current; i < words.length; i++) {
            optimalString.append(words[i]);
            if (current < words.length - 1) {
                optimalString.append(" ");
            }
        }
        return optimalString.toString();
    }

    public void setDarkAmount(float darkAmount) {
        this.mDarkAmount = darkAmount;
        this.mRow.setDarkAmount(darkAmount);
        updateTextColors();
    }

    private void updateTextColors() {
        int blendedColor = getTextColor();
        this.mTitle.setTextColor(blendedColor);
        int childCount = this.mRow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = this.mRow.getChildAt(i);
            if (v instanceof Button) {
                ((Button) v).setTextColor(blendedColor);
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        PendingIntent action = this.mClickActions.get(v);
        if (action != null) {
            try {
                action.send();
            } catch (PendingIntent.CanceledException e) {
                Log.i("KeyguardSliceView", "Pending intent cancelled, nothing to launch", e);
            }
        }
    }

    public void setContentChangeListener(Runnable contentChangeListener) {
        this.mContentChangeListener = contentChangeListener;
    }

    public boolean hasHeader() {
        return this.mHasHeader;
    }

    @Override // android.arch.lifecycle.Observer
    public void onChanged(Slice slice) {
        this.mSlice = slice;
        showSlice();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        setupUri(newValue);
    }

    public void setupUri(String uriString) {
        if (uriString == null) {
            uriString = "content://com.android.systemui.keyguard/main";
        }
        boolean wasObserving = false;
        if (this.mLiveData != null && this.mLiveData.hasActiveObservers()) {
            wasObserving = true;
            this.mLiveData.removeObserver(this);
        }
        this.mKeyguardSliceUri = Uri.parse(uriString);
        this.mLiveData = SliceLiveData.fromUri(this.mContext, this.mKeyguardSliceUri);
        if (wasObserving) {
            this.mLiveData.observeForever(this);
        }
    }

    @VisibleForTesting
    int getTextColor() {
        return ColorUtils.blendARGB(this.mTextColor, -1, this.mDarkAmount);
    }

    @VisibleForTesting
    void setTextColor(int textColor) {
        this.mTextColor = textColor;
        updateTextColors();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.widget_icon_size);
    }

    public void refresh() {
        Slice slice;
        Slice slice2;
        Trace.beginSection("KeyguardSliceView#refresh");
        if ("content://com.android.systemui.keyguard/main".equals(this.mKeyguardSliceUri.toString())) {
            KeyguardSliceProvider instance = KeyguardSliceProvider.getAttachedInstance();
            if (instance != null) {
                slice2 = instance.onBindSlice(this.mKeyguardSliceUri);
            } else {
                Log.w("KeyguardSliceView", "Keyguard slice not bound yet?");
                slice2 = null;
            }
            slice = slice2;
        } else {
            slice = SliceViewManager.getInstance(getContext()).bindSlice(this.mKeyguardSliceUri);
        }
        onChanged(slice);
        Trace.endSection();
    }

    public static class Row extends LinearLayout {
        private float mDarkAmount;
        private final Animation.AnimationListener mKeepAwakeListener;

        public Row(Context context) {
            this(context, null);
        }

        public Row(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public Row(Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr, 0);
        }

        public Row(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            this.mKeepAwakeListener = new KeepAwakeAnimationListener(this.mContext);
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            LayoutTransition transition = new LayoutTransition();
            transition.setDuration(550L);
            PropertyValuesHolder left = PropertyValuesHolder.ofInt("left", 0, 1);
            PropertyValuesHolder right = PropertyValuesHolder.ofInt("right", 0, 1);
            ObjectAnimator changeAnimator = ObjectAnimator.ofPropertyValuesHolder(null, left, right);
            transition.setAnimator(0, changeAnimator);
            transition.setAnimator(1, changeAnimator);
            transition.setInterpolator(0, Interpolators.ACCELERATE_DECELERATE);
            transition.setInterpolator(1, Interpolators.ACCELERATE_DECELERATE);
            transition.setStartDelay(0, 550L);
            transition.setStartDelay(1, 550L);
            ObjectAnimator appearAnimator = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            transition.setAnimator(2, appearAnimator);
            transition.setInterpolator(2, Interpolators.ALPHA_IN);
            ObjectAnimator disappearAnimator = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            transition.setInterpolator(3, Interpolators.ALPHA_OUT);
            transition.setDuration(3, 137L);
            transition.setAnimator(3, disappearAnimator);
            transition.setAnimateParentHierarchy(false);
            setLayoutTransition(transition);
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = View.MeasureSpec.getSize(widthMeasureSpec);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child instanceof KeyguardSliceButton) {
                    ((KeyguardSliceButton) child).setMaxWidth(width / childCount);
                }
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        public void setDarkAmount(float darkAmount) {
            boolean isAwake = darkAmount != 0.0f;
            boolean wasAwake = this.mDarkAmount != 0.0f;
            if (isAwake == wasAwake) {
                return;
            }
            this.mDarkAmount = darkAmount;
            setLayoutAnimationListener(isAwake ? null : this.mKeepAwakeListener);
        }

        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }
    }

    @VisibleForTesting
    static class KeyguardSliceButton extends Button implements ConfigurationController.ConfigurationListener {
        public KeyguardSliceButton(Context context) {
            super(context, null, 0, 2131886478);
            onDensityOrFontScaleChanged();
            setEllipsize(TextUtils.TruncateAt.END);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onDensityOrFontScaleChanged() {
            updatePadding();
        }

        @Override // android.widget.TextView
        public void setText(CharSequence text, TextView.BufferType type) {
            super.setText(text, type);
            updatePadding();
        }

        private void updatePadding() {
            boolean hasText = !TextUtils.isEmpty(getText());
            int horizontalPadding = ((int) getContext().getResources().getDimension(com.android.systemui.R.dimen.widget_horizontal_padding)) / 2;
            setPadding(horizontalPadding, 0, (hasText ? 1 : -1) * horizontalPadding, 0);
            setCompoundDrawablePadding((int) this.mContext.getResources().getDimension(com.android.systemui.R.dimen.widget_icon_padding));
        }

        @Override // android.widget.TextView
        public void setTextColor(int color) {
            super.setTextColor(color);
            updateDrawableColors();
        }

        @Override // android.widget.TextView
        public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
            super.setCompoundDrawables(left, top, right, bottom);
            updateDrawableColors();
            updatePadding();
        }

        private void updateDrawableColors() {
            int color = getCurrentTextColor();
            for (Drawable drawable : getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setTint(color);
                }
            }
        }
    }

    static class TitleView extends AlphaOptimizedTextView {
        public TitleView(Context context) {
            super(context);
        }

        public TitleView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            Layout layout = getLayout();
            int lineCount = layout.getLineCount();
            boolean ellipsizing = layout.getEllipsisCount(lineCount + (-1)) != 0;
            if (lineCount > 0 && !ellipsizing) {
                CharSequence title = getText();
                CharSequence bestLineBreak = KeyguardSliceView.findBestLineBreak(title);
                if (!TextUtils.equals(title, bestLineBreak)) {
                    setText(bestLineBreak);
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }

    private class SliceViewTransitionListener implements LayoutTransition.TransitionListener {
        private SliceViewTransitionListener() {
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            switch (transitionType) {
                case 2:
                    int translation = KeyguardSliceView.this.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.pulsing_notification_appear_translation);
                    view.setTranslationY(translation);
                    view.animate().translationY(0.0f).setDuration(550L).setInterpolator(Interpolators.ALPHA_IN).start();
                    break;
                case 3:
                    if (view == KeyguardSliceView.this.mTitle) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) KeyguardSliceView.this.mTitle.getLayoutParams();
                        int margin = params.topMargin + params.bottomMargin;
                        KeyguardSliceView.this.mTitle.setTranslationY((-KeyguardSliceView.this.mTitle.getHeight()) - margin);
                    }
                    break;
            }
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
        }
    }
}
