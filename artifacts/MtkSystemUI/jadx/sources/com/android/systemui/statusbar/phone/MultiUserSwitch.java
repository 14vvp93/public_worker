package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcher;
import com.android.systemui.statusbar.policy.UserSwitcherController;

/* JADX INFO: loaded from: classes.dex */
public class MultiUserSwitch extends FrameLayout implements View.OnClickListener {
    private boolean mKeyguardMode;
    private KeyguardUserSwitcher mKeyguardUserSwitcher;
    protected QSPanel mQsPanel;
    private final int[] mTmpInt2;
    private UserSwitcherController.BaseUserAdapter mUserListener;
    final UserManager mUserManager;
    protected UserSwitcherController mUserSwitcherController;

    public MultiUserSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTmpInt2 = new int[2];
        this.mUserManager = UserManager.get(getContext());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
        refreshContentDescription();
    }

    public void setQsPanel(QSPanel qsPanel) {
        this.mQsPanel = qsPanel;
        setUserSwitcherController((UserSwitcherController) Dependency.get(UserSwitcherController.class));
    }

    public void setUserSwitcherController(UserSwitcherController userSwitcherController) {
        this.mUserSwitcherController = userSwitcherController;
        registerListener();
        refreshContentDescription();
    }

    public void setKeyguardUserSwitcher(KeyguardUserSwitcher keyguardUserSwitcher) {
        this.mKeyguardUserSwitcher = keyguardUserSwitcher;
    }

    public void setKeyguardMode(boolean keyguardShowing) {
        this.mKeyguardMode = keyguardShowing;
        registerListener();
    }

    private void registerListener() {
        UserSwitcherController controller;
        if (this.mUserManager.isUserSwitcherEnabled() && this.mUserListener == null && (controller = this.mUserSwitcherController) != null) {
            this.mUserListener = new UserSwitcherController.BaseUserAdapter(controller) { // from class: com.android.systemui.statusbar.phone.MultiUserSwitch.1
                @Override // android.widget.BaseAdapter
                public void notifyDataSetChanged() {
                    MultiUserSwitch.this.refreshContentDescription();
                }

                @Override // android.widget.Adapter
                public View getView(int position, View convertView, ViewGroup parent) {
                    return null;
                }
            };
            refreshContentDescription();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (this.mUserManager.isUserSwitcherEnabled()) {
            if (this.mKeyguardMode) {
                if (this.mKeyguardUserSwitcher != null) {
                    this.mKeyguardUserSwitcher.show(true);
                    return;
                }
                return;
            } else {
                if (this.mQsPanel != null && this.mUserSwitcherController != null) {
                    View center = getChildCount() > 0 ? getChildAt(0) : this;
                    center.getLocationInWindow(this.mTmpInt2);
                    int[] iArr = this.mTmpInt2;
                    iArr[0] = iArr[0] + (center.getWidth() / 2);
                    int[] iArr2 = this.mTmpInt2;
                    iArr2[1] = iArr2[1] + (center.getHeight() / 2);
                    this.mQsPanel.showDetailAdapter(true, getUserDetailAdapter(), this.mTmpInt2);
                    return;
                }
                return;
            }
        }
        if (this.mQsPanel != null) {
            Intent intent = ContactsContract.QuickContact.composeQuickContactsIntent(getContext(), v, ContactsContract.Profile.CONTENT_URI, 3, null);
            ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(intent, 0);
        }
    }

    @Override // android.view.View
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        refreshContentDescription();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshContentDescription() {
        String currentUser = null;
        if (this.mUserManager.isUserSwitcherEnabled() && this.mUserSwitcherController != null) {
            currentUser = this.mUserSwitcherController.getCurrentUserName(this.mContext);
        }
        String text = null;
        if (!TextUtils.isEmpty(currentUser)) {
            text = this.mContext.getString(R.string.accessibility_quick_settings_user, currentUser);
        }
        if (!TextUtils.equals(getContentDescription(), text)) {
            setContentDescription(text);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    protected DetailAdapter getUserDetailAdapter() {
        return this.mUserSwitcherController.userDetailAdapter;
    }
}
