package com.android.systemui.pip.phone;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityManager;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.plugins.R;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.component.HidePipMenuEvent;
import com.android.systemui.recents.misc.ReferenceCountedTrigger;
import com.android.systemui.shared.system.InputConsumerController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class PipMenuActivityController {
    private IActivityManager mActivityManager;
    private ParceledListSlice mAppActions;
    private Context mContext;
    private InputConsumerController mInputConsumerController;
    private ParceledListSlice mMediaActions;
    private PipMediaController mMediaController;
    private int mMenuState;
    private ReferenceCountedTrigger mOnAttachDecrementTrigger;
    private boolean mStartActivityRequested;
    private long mStartActivityRequestedTime;
    private Messenger mToActivityMessenger;
    private ArrayList<Listener> mListeners = new ArrayList<>();
    private Bundle mTmpDismissFractionData = new Bundle();
    private Handler mHandler = new AnonymousClass1();
    private Messenger mMessenger = new Messenger(this.mHandler);
    private Runnable mStartActivityRequestedTimeoutRunnable = new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$46Yr3xVHMZsGyZiGhSKF_IPBnzk
        @Override // java.lang.Runnable
        public final void run() {
            PipMenuActivityController.lambda$new$0(this.f$0);
        }
    };
    private PipMediaController.ActionListener mMediaActionListener = new PipMediaController.ActionListener() { // from class: com.android.systemui.pip.phone.PipMenuActivityController.2
        @Override // com.android.systemui.pip.phone.PipMediaController.ActionListener
        public void onMediaActionsChanged(List<RemoteAction> mediaActions) {
            PipMenuActivityController.this.mMediaActions = new ParceledListSlice(mediaActions);
            PipMenuActivityController.this.updateMenuActions();
        }
    };

    public interface Listener {
        void onPipBigger();

        void onPipDismiss();

        void onPipExpand();

        void onPipMenuStateChanged(int i, boolean z);

        void onPipMinimize();

        void onPipShowMenu();

        void onPipSmaller();
    }

    /* JADX INFO: renamed from: com.android.systemui.pip.phone.PipMenuActivityController$1, reason: invalid class name */
    class AnonymousClass1 extends Handler {
        AnonymousClass1() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /* 100 */:
                    int menuState = msg.arg1;
                    PipMenuActivityController.this.onMenuStateChanged(menuState, true);
                    break;
                case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 101 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$8btqC3E6FFjbjLWUhiNmbnKUlfI
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipExpand();
                        }
                    });
                    break;
                case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 102 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$o9fLqvuiKIYwdsSexRT0X4Ty0V4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipMinimize();
                        }
                    });
                    break;
                case R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu /* 103 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$rDXDKqpw1CLC0fwevwYEng68Bps
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipDismiss();
                        }
                    });
                    break;
                case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 104 */:
                    PipMenuActivityController.this.mToActivityMessenger = msg.replyTo;
                    PipMenuActivityController.this.setStartActivityRequested(false);
                    if (PipMenuActivityController.this.mOnAttachDecrementTrigger != null) {
                        PipMenuActivityController.this.mOnAttachDecrementTrigger.decrement();
                        PipMenuActivityController.this.mOnAttachDecrementTrigger = null;
                    }
                    if (PipMenuActivityController.this.mToActivityMessenger == null) {
                        PipMenuActivityController.this.onMenuStateChanged(0, true);
                    }
                    break;
                case R.styleable.AppCompatTheme_textColorSearchUrl /* 105 */:
                    PipMenuActivityController.this.mInputConsumerController.registerInputConsumer();
                    break;
                case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 106 */:
                    PipMenuActivityController.this.mInputConsumerController.unregisterInputConsumer();
                    break;
                case R.styleable.AppCompatTheme_toolbarStyle /* 107 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$nEDJFK5X-9H1WAx_9S8qUwV6KLY
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipShowMenu();
                        }
                    });
                    break;
                case R.styleable.AppCompatTheme_tooltipForegroundColor /* 108 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$EFFMZe7ZROR7cDC2pBd_2_B9lck
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipBigger();
                        }
                    });
                    break;
                case R.styleable.AppCompatTheme_tooltipFrameBackground /* 109 */:
                    PipMenuActivityController.this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$1$RTGjBGc6RXbvqBV-gB1oy2VEpZY
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PipMenuActivityController.Listener) obj).onPipSmaller();
                        }
                    });
                    break;
            }
        }
    }

    public static /* synthetic */ void lambda$new$0(PipMenuActivityController pipMenuActivityController) {
        pipMenuActivityController.setStartActivityRequested(false);
        if (pipMenuActivityController.mOnAttachDecrementTrigger != null) {
            pipMenuActivityController.mOnAttachDecrementTrigger.decrement();
            pipMenuActivityController.mOnAttachDecrementTrigger = null;
        }
        Log.e("PipMenuActController", "Expected start menu activity request timed out");
    }

    public PipMenuActivityController(Context context, IActivityManager activityManager, PipMediaController mediaController, InputConsumerController inputConsumerController) {
        this.mContext = context;
        this.mActivityManager = activityManager;
        this.mMediaController = mediaController;
        this.mInputConsumerController = inputConsumerController;
        EventBus.getDefault().register(this);
    }

    public boolean isMenuActivityVisible() {
        return this.mToActivityMessenger != null;
    }

    public void onActivityPinned() {
        if (this.mMenuState == 0) {
            this.mInputConsumerController.registerInputConsumer();
        }
    }

    public void onActivityUnpinned() {
        hideMenu();
        setStartActivityRequested(false);
    }

    public void onPinnedStackAnimationEnded() {
        if (this.mToActivityMessenger != null) {
            Message m = Message.obtain();
            m.what = 6;
            try {
                this.mToActivityMessenger.send(m);
            } catch (RemoteException e) {
                Log.e("PipMenuActController", "Could not notify menu pinned animation ended", e);
            }
        }
    }

    public void addListener(Listener listener) {
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
    }

    public void setDismissFraction(float fraction) {
        if (this.mToActivityMessenger == null) {
            if (!this.mStartActivityRequested || isStartActivityRequestedElapsed()) {
                startMenuActivity(0, null, null, false, false);
                return;
            }
            return;
        }
        this.mTmpDismissFractionData.clear();
        this.mTmpDismissFractionData.putFloat("dismiss_fraction", fraction);
        Message m = Message.obtain();
        m.what = 5;
        m.obj = this.mTmpDismissFractionData;
        try {
            this.mToActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e("PipMenuActController", "Could not notify menu to update dismiss fraction", e);
        }
    }

    public void showMenu(int menuState, Rect stackBounds, Rect movementBounds, boolean allowMenuTimeout, boolean willResizeMenu) {
        if (this.mToActivityMessenger == null) {
            if (!this.mStartActivityRequested || isStartActivityRequestedElapsed()) {
                startMenuActivity(menuState, stackBounds, movementBounds, allowMenuTimeout, willResizeMenu);
                return;
            }
            return;
        }
        Bundle data = new Bundle();
        data.putInt("menu_state", menuState);
        data.putParcelable("stack_bounds", stackBounds);
        data.putParcelable("movement_bounds", movementBounds);
        data.putBoolean("allow_timeout", allowMenuTimeout);
        data.putBoolean("resize_menu_on_show", willResizeMenu);
        Message m = Message.obtain();
        m.what = 1;
        m.obj = data;
        try {
            this.mToActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e("PipMenuActController", "Could not notify menu to show", e);
        }
    }

    public void pokeMenu() {
        if (this.mToActivityMessenger != null) {
            Message m = Message.obtain();
            m.what = 2;
            try {
                this.mToActivityMessenger.send(m);
            } catch (RemoteException e) {
                Log.e("PipMenuActController", "Could not notify poke menu", e);
            }
        }
    }

    public void hideMenu() {
        if (this.mToActivityMessenger != null) {
            Message m = Message.obtain();
            m.what = 3;
            try {
                this.mToActivityMessenger.send(m);
            } catch (RemoteException e) {
                Log.e("PipMenuActController", "Could not notify menu to hide", e);
            }
        }
    }

    public void hideMenuWithoutResize() {
        onMenuStateChanged(0, false);
    }

    public void setAppActions(ParceledListSlice appActions) {
        this.mAppActions = appActions;
        updateMenuActions();
    }

    private ParceledListSlice resolveMenuActions() {
        if (isValidActions(this.mAppActions)) {
            return this.mAppActions;
        }
        return this.mMediaActions;
    }

    private void startMenuActivity(int menuState, Rect stackBounds, Rect movementBounds, boolean allowMenuTimeout, boolean willResizeMenu) {
        try {
            ActivityManager.StackInfo pinnedStackInfo = this.mActivityManager.getStackInfo(2, 0);
            if (pinnedStackInfo != null && pinnedStackInfo.taskIds != null && pinnedStackInfo.taskIds.length > 0) {
                Intent intent = new Intent(this.mContext, (Class<?>) PipMenuActivity.class);
                intent.putExtra("messenger", this.mMessenger);
                intent.putExtra("actions", (Parcelable) resolveMenuActions());
                if (stackBounds != null) {
                    intent.putExtra("stack_bounds", stackBounds);
                }
                if (movementBounds != null) {
                    intent.putExtra("movement_bounds", movementBounds);
                }
                intent.putExtra("menu_state", menuState);
                intent.putExtra("allow_timeout", allowMenuTimeout);
                intent.putExtra("resize_menu_on_show", willResizeMenu);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(this.mContext, 0, 0);
                options.setLaunchTaskId(pinnedStackInfo.taskIds[pinnedStackInfo.taskIds.length - 1]);
                options.setTaskOverlay(true, true);
                this.mContext.startActivityAsUser(intent, options.toBundle(), UserHandle.CURRENT);
                setStartActivityRequested(true);
                return;
            }
            Log.e("PipMenuActController", "No PIP tasks found");
        } catch (RemoteException e) {
            setStartActivityRequested(false);
            Log.e("PipMenuActController", "Error showing PIP menu activity", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMenuActions() {
        if (this.mToActivityMessenger != null) {
            Rect stackBounds = null;
            try {
                ActivityManager.StackInfo pinnedStackInfo = this.mActivityManager.getStackInfo(2, 0);
                if (pinnedStackInfo != null) {
                    stackBounds = pinnedStackInfo.bounds;
                }
            } catch (RemoteException e) {
                Log.e("PipMenuActController", "Error showing PIP menu activity", e);
            }
            Bundle data = new Bundle();
            data.putParcelable("stack_bounds", stackBounds);
            data.putParcelable("actions", resolveMenuActions());
            Message m = Message.obtain();
            m.what = 4;
            m.obj = data;
            try {
                this.mToActivityMessenger.send(m);
            } catch (RemoteException e2) {
                Log.e("PipMenuActController", "Could not notify menu activity to update actions", e2);
            }
        }
    }

    private boolean isValidActions(ParceledListSlice actions) {
        return actions != null && actions.getList().size() > 0;
    }

    private boolean isStartActivityRequestedElapsed() {
        return SystemClock.uptimeMillis() - this.mStartActivityRequestedTime >= 300;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMenuStateChanged(final int menuState, final boolean resize) {
        if (menuState == 0) {
            this.mInputConsumerController.registerInputConsumer();
        } else {
            this.mInputConsumerController.unregisterInputConsumer();
        }
        if (menuState != this.mMenuState) {
            this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipMenuActivityController$oZuzXTzYX29YiUgUX8-q8QZcGtw
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PipMenuActivityController.Listener) obj).onPipMenuStateChanged(menuState, resize);
                }
            });
            if (menuState == 2) {
                this.mMediaController.addListener(this.mMediaActionListener);
            } else {
                this.mMediaController.removeListener(this.mMediaActionListener);
            }
        }
        this.mMenuState = menuState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStartActivityRequested(boolean requested) {
        this.mHandler.removeCallbacks(this.mStartActivityRequestedTimeoutRunnable);
        this.mStartActivityRequested = requested;
        this.mStartActivityRequestedTime = requested ? SystemClock.uptimeMillis() : 0L;
    }

    public final void onBusEvent(HidePipMenuEvent event) {
        if (this.mStartActivityRequested) {
            this.mOnAttachDecrementTrigger = event.getAnimationTrigger();
            this.mOnAttachDecrementTrigger.increment();
            this.mHandler.removeCallbacks(this.mStartActivityRequestedTimeoutRunnable);
            this.mHandler.postDelayed(this.mStartActivityRequestedTimeoutRunnable, 300L);
        }
    }

    public void dump(PrintWriter pw, String prefix) {
        String innerPrefix = prefix + "  ";
        pw.println(prefix + "PipMenuActController");
        pw.println(innerPrefix + "mMenuState=" + this.mMenuState);
        pw.println(innerPrefix + "mToActivityMessenger=" + this.mToActivityMessenger);
        pw.println(innerPrefix + "mListeners=" + this.mListeners.size());
        pw.println(innerPrefix + "mStartActivityRequested=" + this.mStartActivityRequested);
        pw.println(innerPrefix + "mStartActivityRequestedTime=" + this.mStartActivityRequestedTime);
    }
}
