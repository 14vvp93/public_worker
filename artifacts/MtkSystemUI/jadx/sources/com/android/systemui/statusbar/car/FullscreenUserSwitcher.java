package com.android.systemui.statusbar.car;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import com.android.settingslib.users.UserManagerHelper;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBar;

/* JADX INFO: loaded from: classes.dex */
public class FullscreenUserSwitcher {
    private final View mContainer;
    private int mCurrentForegroundUserId;
    private final View mParent;
    private final int mShortAnimDuration;
    private boolean mShowing;
    private final StatusBar mStatusBar;
    private final UserGridRecyclerView mUserGridView;
    private final UserManagerHelper mUserManagerHelper;

    public FullscreenUserSwitcher(StatusBar statusBar, ViewStub containerStub, Context context) {
        this.mStatusBar = statusBar;
        this.mParent = containerStub.inflate();
        this.mContainer = this.mParent.findViewById(R.id.container);
        this.mUserGridView = (UserGridRecyclerView) this.mContainer.findViewById(R.id.user_grid);
        GridLayoutManager layoutManager = new GridLayoutManager(context, context.getResources().getInteger(R.integer.user_fullscreen_switcher_num_col));
        this.mUserGridView.getRecyclerView().setLayoutManager(layoutManager);
        this.mUserGridView.buildAdapter();
        this.mUserGridView.setUserSelectionListener(new UserGridRecyclerView.UserSelectionListener() { // from class: com.android.systemui.statusbar.car.-$$Lambda$FullscreenUserSwitcher$aJmHs-UVhjESZPP4fORPpYI740g
            @Override // com.android.systemui.statusbar.car.UserGridRecyclerView.UserSelectionListener
            public final void onUserSelected(UserGridRecyclerView.UserRecord userRecord) {
                this.f$0.onUserSelected(userRecord);
            }
        });
        this.mUserManagerHelper = new UserManagerHelper(context);
        updateCurrentForegroundUser();
        this.mShortAnimDuration = this.mContainer.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void show() {
        if ((this.mUserManagerHelper.isHeadlessSystemUser() && this.mUserManagerHelper.userIsSystemUser(this.mUserManagerHelper.getForegroundUserInfo())) || this.mShowing) {
            return;
        }
        this.mShowing = true;
        this.mParent.setVisibility(0);
    }

    public void hide() {
        this.mShowing = false;
        this.mParent.setVisibility(8);
    }

    public void onUserSwitched(int newUserId) {
        if (foregroundUserChanged()) {
            toggleSwitchInProgress(false);
            updateCurrentForegroundUser();
            this.mParent.post(new Runnable() { // from class: com.android.systemui.statusbar.car.-$$Lambda$FullscreenUserSwitcher$IK7lNRNVhlYLd9PajOKix9WDNFg
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dismissKeyguard();
                }
            });
        }
    }

    private boolean foregroundUserChanged() {
        return this.mCurrentForegroundUserId != this.mUserManagerHelper.getForegroundUserId();
    }

    private void updateCurrentForegroundUser() {
        this.mCurrentForegroundUserId = this.mUserManagerHelper.getForegroundUserId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserSelected(UserGridRecyclerView.UserRecord record) {
        if (record.mIsForeground) {
            dismissKeyguard();
        } else {
            toggleSwitchInProgress(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissKeyguard() {
        this.mStatusBar.executeRunnableDismissingKeyguard(null, null, true, true, true);
    }

    private void toggleSwitchInProgress(boolean inProgress) {
        if (inProgress) {
            fadeOut(this.mContainer);
        } else {
            fadeIn(this.mContainer);
        }
    }

    private void fadeOut(final View view) {
        view.animate().alpha(0.0f).setDuration(this.mShortAnimDuration).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.car.FullscreenUserSwitcher.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(8);
            }
        });
    }

    private void fadeIn(final View view) {
        view.animate().alpha(1.0f).setDuration(this.mShortAnimDuration).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.car.FullscreenUserSwitcher.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                view.setAlpha(0.0f);
                view.setVisibility(0);
            }
        });
    }
}
