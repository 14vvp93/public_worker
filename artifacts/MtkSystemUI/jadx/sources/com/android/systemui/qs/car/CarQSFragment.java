package com.android.systemui.qs.car;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSFooter;
import com.android.systemui.statusbar.car.UserGridRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CarQSFragment extends Fragment implements QS {
    private AnimatorSet mAnimatorSet;
    private CarQSFooter mFooter;
    private View mFooterExpandIcon;
    private View mFooterUserName;
    private View mHeader;
    private UserGridRecyclerView mUserGridView;
    private UserSwitchCallback mUserSwitchCallback;
    private View mUserSwitcherContainer;

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.car_qs_panel, container, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mHeader = view.findViewById(R.id.header);
        this.mFooter = (CarQSFooter) view.findViewById(R.id.qs_footer);
        this.mFooterUserName = this.mFooter.findViewById(R.id.user_name);
        this.mFooterExpandIcon = this.mFooter.findViewById(R.id.user_switch_expand_icon);
        this.mUserSwitcherContainer = view.findViewById(R.id.user_switcher_container);
        updateUserSwitcherHeight(0);
        Context context = getContext();
        this.mUserGridView = (UserGridRecyclerView) this.mUserSwitcherContainer.findViewById(R.id.user_grid);
        GridLayoutManager layoutManager = new GridLayoutManager(context, context.getResources().getInteger(R.integer.user_fullscreen_switcher_num_col));
        this.mUserGridView.getRecyclerView().setLayoutManager(layoutManager);
        this.mUserGridView.buildAdapter();
        this.mUserSwitchCallback = new UserSwitchCallback();
        this.mFooter.setUserSwitchCallback(this.mUserSwitchCallback);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void hideImmediately() {
        getView().setVisibility(4);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setQsExpansion(float qsExpansionFraction, float headerTranslation) {
        getView().setVisibility(headerTranslation == 0.0f ? 0 : 4);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public View getHeader() {
        return this.mHeader;
    }

    QSFooter getFooter() {
        return this.mFooter;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderListening(boolean listening) {
        this.mFooter.setListening(listening);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setListening(boolean listening) {
        this.mFooter.setListening(listening);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getQsMinExpansionHeight() {
        return getView().getHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getDesiredHeight() {
        return getView().getHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setPanelView(QS.HeightListener notificationPanelView) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeightOverride(int desiredHeight) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderClickable(boolean qsExpansionEnabled) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isCustomizing() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setOverscrolling(boolean overscrolling) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setExpanded(boolean qsExpanded) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isShowingDetail() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void closeDetail() {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setKeyguardShowing(boolean keyguardShowing) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void animateHeaderSlidingIn(long delay) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void animateHeaderSlidingOut() {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void notifyCustomizeChanged() {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setContainer(ViewGroup container) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setExpandClickListener(View.OnClickListener onClickListener) {
    }

    public class UserSwitchCallback {
        private boolean mShowing;

        public UserSwitchCallback() {
        }

        public boolean isShowing() {
            return this.mShowing;
        }

        public void show() {
            this.mShowing = true;
            CarQSFragment.this.animateHeightChange(true);
        }

        public void hide() {
            this.mShowing = false;
            CarQSFragment.this.animateHeightChange(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserSwitcherHeight(int height) {
        ViewGroup.LayoutParams layoutParams = this.mUserSwitcherContainer.getLayoutParams();
        layoutParams.height = height;
        this.mUserSwitcherContainer.requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateHeightChange(boolean opening) {
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.cancel();
        }
        List<Animator> allAnimators = new ArrayList<>();
        ValueAnimator heightAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(getContext(), opening ? R.anim.car_user_switcher_open_animation : R.anim.car_user_switcher_close_animation);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.car.-$$Lambda$CarQSFragment$F8Wi9wqWb5vO5dq5W60LmH4nDcg
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.updateUserSwitcherHeight(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        allAnimators.add(heightAnimator);
        Animator nameAnimator = AnimatorInflater.loadAnimator(getContext(), opening ? R.anim.car_user_switcher_open_name_animation : R.anim.car_user_switcher_close_name_animation);
        nameAnimator.setTarget(this.mFooterUserName);
        allAnimators.add(nameAnimator);
        Animator iconAnimator = AnimatorInflater.loadAnimator(getContext(), opening ? R.anim.car_user_switcher_open_icon_animation : R.anim.car_user_switcher_close_icon_animation);
        iconAnimator.setTarget(this.mFooterExpandIcon);
        allAnimators.add(iconAnimator);
        this.mAnimatorSet = new AnimatorSet();
        this.mAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.car.CarQSFragment.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CarQSFragment.this.mAnimatorSet = null;
            }
        });
        this.mAnimatorSet.playTogether((Animator[]) allAnimators.toArray(new Animator[0]));
        setupInitialValues(this.mAnimatorSet);
        this.mAnimatorSet.start();
    }

    private void setupInitialValues(Animator anim) {
        if (anim instanceof AnimatorSet) {
            for (Animator a : ((AnimatorSet) anim).getChildAnimations()) {
                setupInitialValues(a);
            }
            return;
        }
        if (anim instanceof ObjectAnimator) {
            ((ObjectAnimator) anim).setCurrentFraction(0.0f);
        }
    }
}
