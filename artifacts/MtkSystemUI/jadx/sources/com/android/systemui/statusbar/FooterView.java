package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.stack.ExpandableViewState;
import com.android.systemui.statusbar.stack.StackScrollState;

/* JADX INFO: loaded from: classes.dex */
public class FooterView extends StackScrollerDecorView {
    private final int mClearAllTopPadding;
    private FooterViewButton mDismissButton;
    private FooterViewButton mManageButton;

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mClearAllTopPadding = context.getResources().getDimensionPixelSize(R.dimen.clear_all_padding_top);
    }

    @Override // com.android.systemui.statusbar.StackScrollerDecorView
    protected View findContentView() {
        return findViewById(R.id.content);
    }

    @Override // com.android.systemui.statusbar.StackScrollerDecorView
    protected View findSecondaryView() {
        return findViewById(R.id.dismiss_text);
    }

    @Override // com.android.systemui.statusbar.StackScrollerDecorView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDismissButton = (FooterViewButton) findSecondaryView();
        this.mManageButton = (FooterViewButton) findViewById(R.id.manage_text);
    }

    public void setTextColor(int color) {
        this.mManageButton.setTextColor(color);
        this.mDismissButton.setTextColor(color);
    }

    public void setManageButtonClickListener(View.OnClickListener listener) {
        this.mManageButton.setOnClickListener(listener);
    }

    public void setDismissButtonClickListener(View.OnClickListener listener) {
        this.mDismissButton.setOnClickListener(listener);
    }

    public boolean isOnEmptySpace(float touchX, float touchY) {
        return touchX < this.mContent.getX() || touchX > this.mContent.getX() + ((float) this.mContent.getWidth()) || touchY < this.mContent.getY() || touchY > this.mContent.getY() + ((float) this.mContent.getHeight());
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDismissButton.setText(R.string.clear_all_notifications_text);
        this.mDismissButton.setContentDescription(this.mContext.getString(R.string.accessibility_clear_all));
        this.mManageButton.setText(R.string.manage_notifications_text);
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public ExpandableViewState createNewViewState(StackScrollState stackScrollState) {
        return new FooterViewState();
    }

    public class FooterViewState extends ExpandableViewState {
        public FooterViewState() {
        }

        @Override // com.android.systemui.statusbar.stack.ExpandableViewState, com.android.systemui.statusbar.stack.ViewState
        public void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof FooterView) {
                FooterView footerView = (FooterView) view;
                boolean z = false;
                boolean visible = this.clipTopAmount < FooterView.this.mClearAllTopPadding;
                if (visible && footerView.isVisible()) {
                    z = true;
                }
                footerView.setContentVisible(z);
            }
        }
    }
}
