package com.android.systemui.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.component.HidePipMenuEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PipMenuActivity extends Activity {
    private LinearLayout mActionsGroup;
    private Drawable mBackgroundDrawable;
    private int mBetweenActionPaddingLand;
    private View mBigButton;
    private View mDismissButton;
    private ImageView mExpandButton;
    private View mMenuContainer;
    private AnimatorSet mMenuContainerAnimator;
    private int mMenuState;
    private View mSettingsButton;
    private View mSmallButton;
    private Messenger mToControllerMessenger;
    private PipTouchState mTouchState;
    private ViewConfiguration mViewConfig;
    private View mViewRoot;
    private boolean mAllowMenuTimeout = true;
    private boolean mAllowTouches = true;
    private final List<RemoteAction> mActions = new ArrayList();
    private ValueAnimator.AnimatorUpdateListener mMenuBgUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.pip.phone.PipMenuActivity.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            float alpha = ((Float) animation.getAnimatedValue()).floatValue();
            PipMenuActivity.this.mBackgroundDrawable.setAlpha((int) (0.3f * alpha * 255.0f));
        }
    };
    private PointF mDownPosition = new PointF();
    private PointF mDownDelta = new PointF();
    private Handler mHandler = new Handler();

    @SuppressLint({"HandlerLeak"})
    private final Messenger mMessenger = new Messenger(new Handler() { // from class: com.android.systemui.pip.phone.PipMenuActivity.2
        @Override // android.os.Handler
        @SuppressLint({"HandlerLeak"})
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle data = (Bundle) msg.obj;
                    PipMenuActivity.this.showMenu(data.getInt("menu_state"), (Rect) data.getParcelable("stack_bounds"), (Rect) data.getParcelable("movement_bounds"), data.getBoolean("allow_timeout"), data.getBoolean("resize_menu_on_show"));
                    break;
                case 2:
                    PipMenuActivity.this.cancelDelayedFinish();
                    break;
                case 3:
                    PipMenuActivity.this.hideMenu();
                    break;
                case 4:
                    Bundle data2 = (Bundle) msg.obj;
                    ParceledListSlice actions = data2.getParcelable("actions");
                    PipMenuActivity.this.setActions((Rect) data2.getParcelable("stack_bounds"), actions != null ? actions.getList() : Collections.EMPTY_LIST);
                    break;
                case 5:
                    PipMenuActivity.this.updateDismissFraction(((Bundle) msg.obj).getFloat("dismiss_fraction"));
                    break;
                case 6:
                    PipMenuActivity.this.mAllowTouches = true;
                    break;
            }
        }
    });
    private final Runnable mFinishRunnable = new Runnable() { // from class: com.android.systemui.pip.phone.PipMenuActivity.3
        @Override // java.lang.Runnable
        public void run() {
            PipMenuActivity.this.hideMenu();
        }
    };

    @Override // android.app.Activity
    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        this.mViewConfig = ViewConfiguration.get(this);
        this.mTouchState = new PipTouchState(this.mViewConfig, this.mHandler, new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$sJi0SxOuGngGF8xURDQ1Bnt0G_E
            @Override // java.lang.Runnable
            public final void run() {
                PipMenuActivity.lambda$onCreate$0(this.f$0);
            }
        });
        getWindow().addFlags(537133056);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pip_menu_activity);
        this.mBackgroundDrawable = new ColorDrawable(-16777216);
        this.mBackgroundDrawable.setAlpha(0);
        this.mViewRoot = findViewById(R.id.background);
        this.mViewRoot.setBackground(this.mBackgroundDrawable);
        this.mMenuContainer = findViewById(R.id.menu_container);
        this.mMenuContainer.setAlpha(0.0f);
        this.mMenuContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$WvtwNwFY4S4VeIJ5ZxsSTL51DAs
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return PipMenuActivity.lambda$onCreate$1(this.f$0, view, motionEvent);
            }
        });
        this.mSettingsButton = findViewById(R.id.settings);
        this.mSettingsButton.setAlpha(0.0f);
        this.mSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$70yHDyzrwE1GNEVEQrmSEL7H6fY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.showSettings();
            }
        });
        this.mDismissButton = findViewById(R.id.dismiss);
        this.mDismissButton.setAlpha(0.0f);
        this.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$XrbqAt128TykA2bnzcA2djOz8lo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.dismissPip();
            }
        });
        this.mActionsGroup = (LinearLayout) findViewById(R.id.actions_group);
        this.mBetweenActionPaddingLand = getResources().getDimensionPixelSize(R.dimen.pip_between_action_padding_land);
        this.mExpandButton = (ImageView) findViewById(R.id.expand_button);
        this.mExpandButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$YOeS_llXN6jKZLtC4kPfnX-YD-8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.expandPip();
            }
        });
        this.mBigButton = findViewById(R.id.big_button);
        this.mBigButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$5W6TLaLmockUREshiuCHSoxznms
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.biggerPip();
            }
        });
        this.mSmallButton = findViewById(R.id.small_button);
        this.mSmallButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$4SE8x1fqOq14OC9Lj2kGrj52RcQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.smallerPip();
            }
        });
        updateFromIntent(getIntent());
        setTitle(R.string.pip_menu_title);
        setDisablePreviewScreenshots(true);
    }

    public static /* synthetic */ void lambda$onCreate$0(PipMenuActivity pipMenuActivity) {
        if (pipMenuActivity.mMenuState == 1) {
            pipMenuActivity.showPipMenu();
        }
    }

    public static /* synthetic */ boolean lambda$onCreate$1(PipMenuActivity pipMenuActivity, View v, MotionEvent event) {
        pipMenuActivity.mTouchState.onTouchEvent(event);
        if (event.getAction() == 1 && !pipMenuActivity.mTouchState.isDoubleTap() && pipMenuActivity.mMenuState != 2) {
            if (!pipMenuActivity.mTouchState.isWaitingForDoubleTap()) {
                if (pipMenuActivity.mMenuState == 1) {
                    pipMenuActivity.showPipMenu();
                }
            } else {
                pipMenuActivity.mTouchState.scheduleDoubleTapTimeoutCallback();
            }
        }
        return true;
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateFromIntent(intent);
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        if (this.mAllowMenuTimeout) {
            repostDelayedFinish(2000L);
        }
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        hideMenu();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        cancelDelayedFinish();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        notifyActivityCallback(null);
    }

    @Override // android.app.Activity
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        if (!isInPictureInPictureMode) {
            finish();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    @SuppressLint({"NewApi"})
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!this.mAllowTouches) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        if (action == 0) {
            this.mDownPosition.set(ev.getX(), ev.getY());
            this.mDownDelta.set(0.0f, 0.0f);
        } else if (action == 2) {
            this.mDownDelta.set(ev.getX() - this.mDownPosition.x, ev.getY() - this.mDownPosition.y);
            if (this.mDownDelta.length() > this.mViewConfig.getScaledTouchSlop() && this.mMenuState != 0) {
                notifyRegisterInputConsumer();
                cancelDelayedFinish();
            }
        } else if (action == 4) {
            hideMenu();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.app.Activity
    @SuppressLint({"NewApi"})
    public void finish() {
        notifyActivityCallback(null);
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // android.app.Activity
    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    }

    public final void onBusEvent(final HidePipMenuEvent event) {
        if (this.mMenuState != 0) {
            event.getAnimationTrigger().increment();
            hideMenu(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$kb9R6rlFtKhY2Ro6rF3tc5MQM7A
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$rpIqZGszmSoREP0oSWTR7VAmbpo
                        @Override // java.lang.Runnable
                        public final void run() {
                            hidePipMenuEvent.getAnimationTrigger().decrement();
                        }
                    });
                }
            }, true, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void showMenu(int menuState, Rect stackBounds, Rect movementBounds, boolean allowMenuTimeout, boolean resizeMenuOnShow) {
        this.mAllowMenuTimeout = allowMenuTimeout;
        if (this.mMenuState != menuState) {
            boolean disallowTouchesUntilAnimationEnd = resizeMenuOnShow && (this.mMenuState == 2 || menuState == 2);
            this.mAllowTouches = !disallowTouchesUntilAnimationEnd;
            cancelDelayedFinish();
            updateActionViews(stackBounds);
            if (this.mMenuContainerAnimator != null) {
                this.mMenuContainerAnimator.cancel();
            }
            notifyMenuStateChange(menuState);
            this.mMenuContainerAnimator = new AnimatorSet();
            ObjectAnimator menuAnim = ObjectAnimator.ofFloat(this.mMenuContainer, (Property<View, Float>) View.ALPHA, this.mMenuContainer.getAlpha(), 1.0f);
            menuAnim.addUpdateListener(this.mMenuBgUpdateListener);
            ObjectAnimator settingsAnim = ObjectAnimator.ofFloat(this.mSettingsButton, (Property<View, Float>) View.ALPHA, this.mSettingsButton.getAlpha(), 1.0f);
            ObjectAnimator dismissAnim = ObjectAnimator.ofFloat(this.mDismissButton, (Property<View, Float>) View.ALPHA, this.mDismissButton.getAlpha(), 1.0f);
            if (menuState == 2) {
                this.mMenuContainerAnimator.playTogether(menuAnim, settingsAnim, dismissAnim);
            } else {
                this.mMenuContainerAnimator.playTogether(dismissAnim);
            }
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
            this.mMenuContainerAnimator.setDuration(125L);
            if (allowMenuTimeout) {
                this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.pip.phone.PipMenuActivity.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        PipMenuActivity.this.repostDelayedFinish(3500L);
                    }
                });
            }
            this.mMenuContainerAnimator.start();
            return;
        }
        if (allowMenuTimeout) {
            repostDelayedFinish(2000L);
        }
        notifyUnregisterInputConsumer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMenu() {
        hideMenu(null, true, false);
    }

    @SuppressLint({"NewApi"})
    private void hideMenu(final Runnable animationFinishedRunnable, boolean notifyMenuVisibility, final boolean isDismissing) {
        if (this.mMenuState != 0) {
            cancelDelayedFinish();
            if (notifyMenuVisibility) {
                notifyMenuStateChange(0);
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            ObjectAnimator menuAnim = ObjectAnimator.ofFloat(this.mMenuContainer, (Property<View, Float>) View.ALPHA, this.mMenuContainer.getAlpha(), 0.0f);
            menuAnim.addUpdateListener(this.mMenuBgUpdateListener);
            ObjectAnimator settingsAnim = ObjectAnimator.ofFloat(this.mSettingsButton, (Property<View, Float>) View.ALPHA, this.mSettingsButton.getAlpha(), 0.0f);
            ObjectAnimator dismissAnim = ObjectAnimator.ofFloat(this.mDismissButton, (Property<View, Float>) View.ALPHA, this.mDismissButton.getAlpha(), 0.0f);
            this.mMenuContainerAnimator.playTogether(menuAnim, settingsAnim, dismissAnim);
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            this.mMenuContainerAnimator.setDuration(125L);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.pip.phone.PipMenuActivity.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (animationFinishedRunnable != null) {
                        animationFinishedRunnable.run();
                    }
                    if (!isDismissing) {
                        PipMenuActivity.this.finish();
                    }
                }
            });
            this.mMenuContainerAnimator.start();
            return;
        }
        finish();
    }

    private void updateFromIntent(Intent intent) {
        this.mToControllerMessenger = (Messenger) intent.getParcelableExtra("messenger");
        if (this.mToControllerMessenger == null) {
            Log.w("PipMenuActivity", "Controller messenger is null. Stopping.");
            finish();
            return;
        }
        notifyActivityCallback(this.mMessenger);
        EventBus.getDefault().register(this);
        ParceledListSlice actions = intent.getParcelableExtra("actions");
        if (actions != null) {
            this.mActions.clear();
            this.mActions.addAll(actions.getList());
        }
        int menuState = intent.getIntExtra("menu_state", 0);
        if (menuState != 0) {
            Rect stackBounds = (Rect) intent.getParcelableExtra("stack_bounds");
            Rect movementBounds = (Rect) intent.getParcelableExtra("movement_bounds");
            boolean allowMenuTimeout = intent.getBooleanExtra("allow_timeout", true);
            boolean willResizeMenu = intent.getBooleanExtra("resize_menu_on_show", false);
            showMenu(menuState, stackBounds, movementBounds, allowMenuTimeout, willResizeMenu);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActions(Rect stackBounds, List<RemoteAction> actions) {
        this.mActions.clear();
        this.mActions.addAll(actions);
        updateActionViews(stackBounds);
    }

    private void updateActionViews(Rect stackBounds) {
        int i;
        ViewGroup expandContainer = (ViewGroup) findViewById(R.id.expand_container);
        ViewGroup actionsContainer = (ViewGroup) findViewById(R.id.actions_container);
        actionsContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$GWYyIX_QCjCm2cUJTP8pzkpDtts
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return PipMenuActivity.lambda$updateActionViews$9(view, motionEvent);
            }
        });
        if (!this.mActions.isEmpty()) {
            if (this.mMenuState != 1) {
                actionsContainer.setVisibility(0);
                if (this.mActionsGroup != null) {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    while (this.mActionsGroup.getChildCount() < this.mActions.size()) {
                        this.mActionsGroup.addView((ImageView) inflater.inflate(R.layout.pip_menu_action, (ViewGroup) this.mActionsGroup, false));
                    }
                    for (int i2 = 0; i2 < this.mActionsGroup.getChildCount(); i2++) {
                        View childAt = this.mActionsGroup.getChildAt(i2);
                        if (i2 < this.mActions.size()) {
                            i = 0;
                        } else {
                            i = 8;
                        }
                        childAt.setVisibility(i);
                    }
                    boolean isLandscapePip = stackBounds != null && stackBounds.width() > stackBounds.height();
                    int i3 = 0;
                    while (i3 < this.mActions.size()) {
                        final RemoteAction action = this.mActions.get(i3);
                        final ImageView actionView = (ImageView) this.mActionsGroup.getChildAt(i3);
                        action.getIcon().loadDrawableAsync(this, new Icon.OnDrawableLoadedListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$ysyPKPSs0U1DriZ_qKZBwdQ036k
                            @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                            public final void onDrawableLoaded(Drawable drawable) {
                                PipMenuActivity.lambda$updateActionViews$10(actionView, drawable);
                            }
                        }, this.mHandler);
                        actionView.setContentDescription(action.getContentDescription());
                        if (action.isEnabled()) {
                            actionView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$Sczihu7jZBclG35OELhufmjF-8k
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    PipMenuActivity.lambda$updateActionViews$11(action, view);
                                }
                            });
                        }
                        actionView.setEnabled(action.isEnabled());
                        actionView.setAlpha(action.isEnabled() ? 1.0f : 0.54f);
                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) actionView.getLayoutParams();
                        lp.leftMargin = (!isLandscapePip || i3 <= 0) ? 0 : this.mBetweenActionPaddingLand;
                        i3++;
                    }
                }
                FrameLayout.LayoutParams expandedLp = (FrameLayout.LayoutParams) expandContainer.getLayoutParams();
                expandedLp.topMargin = getResources().getDimensionPixelSize(R.dimen.pip_action_padding);
                expandedLp.bottomMargin = getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin);
                expandContainer.requestLayout();
                return;
            }
        }
        actionsContainer.setVisibility(4);
    }

    static /* synthetic */ boolean lambda$updateActionViews$9(View v, MotionEvent ev) {
        return true;
    }

    static /* synthetic */ void lambda$updateActionViews$10(ImageView actionView, Drawable d) {
        if (d != null) {
            d.setTint(-1);
        }
        actionView.setImageDrawable(d);
    }

    static /* synthetic */ void lambda$updateActionViews$11(RemoteAction action, View v) {
        try {
            action.getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            Log.w("PipMenuActivity", "Failed to send action", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void updateDismissFraction(float fraction) {
        int alpha;
        float menuAlpha = 1.0f - fraction;
        if (this.mMenuState == 2) {
            this.mMenuContainer.setAlpha(menuAlpha);
            this.mSettingsButton.setAlpha(menuAlpha);
            this.mDismissButton.setAlpha(menuAlpha);
            float interpolatedAlpha = (0.3f * menuAlpha) + (0.6f * fraction);
            alpha = (int) (255.0f * interpolatedAlpha);
        } else {
            int alpha2 = this.mMenuState;
            if (alpha2 == 1) {
                this.mDismissButton.setAlpha(menuAlpha);
            }
            alpha = (int) (0.6f * fraction * 255.0f);
        }
        this.mBackgroundDrawable.setAlpha(alpha);
    }

    private void notifyRegisterInputConsumer() {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_textColorSearchUrl;
        sendMessage(m, "Could not notify controller to register input consumer");
    }

    private void notifyUnregisterInputConsumer() {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
        sendMessage(m, "Could not notify controller to unregister input consumer");
    }

    private void notifyMenuStateChange(int menuState) {
        this.mMenuState = menuState;
        Message m = Message.obtain();
        m.what = 100;
        m.arg1 = menuState;
        sendMessage(m, "Could not notify controller of PIP menu visibility");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expandPip() {
        hideMenu(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$ccbkw6vD_Uh8ctb0UeW7-URHSto
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.sendEmptyMessage(com.android.systemui.plugins.R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle, "Could not notify controller to expand PIP");
            }
        }, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissPip() {
        hideMenu(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivity$3RAwgU8lGmb8UVr005uyNmIiIQA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.sendEmptyMessage(com.android.systemui.plugins.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, "Could not notify controller to dismiss PIP");
            }
        }, false, true);
    }

    private void showPipMenu() {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_toolbarStyle;
        sendMessage(m, "Could not notify controller to show PIP menu");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void biggerPip() {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_tooltipForegroundColor;
        sendMessage(m, "Could not notify controller to bigger PIP");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smallerPip() {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_tooltipFrameBackground;
        sendMessage(m, "Could not notify controller to smaller PIP");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void showSettings() {
        Pair<ComponentName, Integer> topPipActivityInfo = PipUtils.getTopPinnedActivity(this, ActivityManager.getService());
        if (topPipActivityInfo.first != null) {
            UserHandle user = UserHandle.of(((Integer) topPipActivityInfo.second).intValue());
            Intent settingsIntent = new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.fromParts("package", ((ComponentName) topPipActivityInfo.first).getPackageName(), null));
            settingsIntent.putExtra("android.intent.extra.user_handle", user);
            settingsIntent.setFlags(268468224);
            startActivity(settingsIntent);
        }
    }

    private void notifyActivityCallback(Messenger callback) {
        Message m = Message.obtain();
        m.what = com.android.systemui.plugins.R.styleable.AppCompatTheme_textColorAlertDialogListItem;
        m.replyTo = callback;
        sendMessage(m, "Could not notify controller of activity finished");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEmptyMessage(int what, String errorMsg) {
        Message m = Message.obtain();
        m.what = what;
        sendMessage(m, errorMsg);
    }

    private void sendMessage(Message m, String errorMsg) {
        if (this.mToControllerMessenger == null) {
            return;
        }
        try {
            this.mToControllerMessenger.send(m);
        } catch (RemoteException e) {
            Log.e("PipMenuActivity", errorMsg, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDelayedFinish() {
        this.mHandler.removeCallbacks(this.mFinishRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void repostDelayedFinish(long delay) {
        this.mHandler.removeCallbacks(this.mFinishRunnable);
        this.mHandler.postDelayed(this.mFinishRunnable, delay);
    }
}
