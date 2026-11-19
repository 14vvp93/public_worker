package com.android.systemui.statusbar;

import android.R;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class NotificationRemoteInputManager implements Dumpable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static final boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", true);
    protected Callback mCallback;
    protected final Context mContext;
    protected NotificationEntryManager mEntryManager;
    protected NotificationPresenter mPresenter;
    protected RemoteInputController mRemoteInputController;
    private final UserManager mUserManager;
    protected final ArraySet<NotificationData.Entry> mRemoteInputEntriesToRemoveOnCollapse = new ArraySet<>();
    protected final NotificationLockscreenUserManager mLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);
    private final RemoteViews.OnClickHandler mOnClickHandler = new AnonymousClass1();
    protected IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    public interface Callback {
        boolean handleRemoteViewClick(View view, PendingIntent pendingIntent, Intent intent, ClickHandler clickHandler);

        void onLockedRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view);

        void onLockedWorkRemoteInput(int i, ExpandableNotificationRow expandableNotificationRow, View view);

        void onMakeExpandedVisibleForRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view);

        boolean shouldHandleRemoteInput(View view, PendingIntent pendingIntent);
    }

    public interface ClickHandler {
        boolean handleClick();
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$1, reason: invalid class name */
    class AnonymousClass1 extends RemoteViews.OnClickHandler {
        AnonymousClass1() {
        }

        public boolean onClickHandler(final View view, final PendingIntent pendingIntent, final Intent fillInIntent) {
            NotificationRemoteInputManager.this.mPresenter.wakeUpIfDozing(SystemClock.uptimeMillis(), view);
            if (handleRemoteInput(view, pendingIntent)) {
                return true;
            }
            logActionClick(view);
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException e) {
            }
            return NotificationRemoteInputManager.this.mCallback.handleRemoteViewClick(view, pendingIntent, fillInIntent, new ClickHandler() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationRemoteInputManager$1$dzxY8UkafLAnu6kZIoh3HbriWqA
                @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.ClickHandler
                public final boolean handleClick() {
                    return this.f$0.superOnClickHandler(view, pendingIntent, fillInIntent);
                }
            });
        }

        private void logActionClick(View view) {
            ViewParent parent = view.getParent();
            String key = getNotificationKeyForParent(parent);
            if (key == null) {
                Log.w("NotificationRemoteInputManager", "Couldn't determine notification for click.");
                return;
            }
            int index = -1;
            if (view.getId() == 16908661 && parent != null && (parent instanceof ViewGroup)) {
                ViewGroup actionGroup = (ViewGroup) parent;
                index = actionGroup.indexOfChild(view);
            }
            int count = NotificationRemoteInputManager.this.mEntryManager.getNotificationData().getActiveNotifications().size();
            int rank = NotificationRemoteInputManager.this.mEntryManager.getNotificationData().getRank(key);
            NotificationVisibility nv = NotificationVisibility.obtain(key, rank, count, true);
            try {
                NotificationRemoteInputManager.this.mBarService.onNotificationActionClick(key, index, nv);
            } catch (RemoteException e) {
            }
        }

        private String getNotificationKeyForParent(ViewParent parent) {
            while (parent != null) {
                if (parent instanceof ExpandableNotificationRow) {
                    return ((ExpandableNotificationRow) parent).getStatusBarNotification().getKey();
                }
                parent = parent.getParent();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean superOnClickHandler(View view, PendingIntent pendingIntent, Intent fillInIntent) {
            return super.onClickHandler(view, pendingIntent, fillInIntent, 4);
        }

        private boolean handleRemoteInput(View view, PendingIntent pendingIntent) {
            if (NotificationRemoteInputManager.this.mCallback.shouldHandleRemoteInput(view, pendingIntent)) {
                return true;
            }
            Object tag = view.getTag(R.id.keyboardHidden);
            RemoteInput[] inputs = null;
            if (tag instanceof RemoteInput[]) {
                inputs = (RemoteInput[]) tag;
            }
            if (inputs == null) {
                return false;
            }
            RemoteInput input = null;
            for (RemoteInput i : inputs) {
                if (i.getAllowFreeFormInput()) {
                    input = i;
                }
            }
            if (input == null) {
                return false;
            }
            ViewParent p = view.getParent();
            ExpandableNotificationRow row = null;
            ViewParent p2 = p;
            RemoteInputView riv = null;
            while (p2 != null) {
                if (p2 instanceof View) {
                    View pv = (View) p2;
                    if (pv.isRootNamespace()) {
                        riv = findRemoteInputView(pv);
                        break;
                    }
                }
                p2 = p2.getParent();
            }
            while (p2 != null) {
                if (p2 instanceof ExpandableNotificationRow) {
                    row = (ExpandableNotificationRow) p2;
                    break;
                }
                p2 = p2.getParent();
            }
            if (row == null) {
                return false;
            }
            row.setUserExpanded(true);
            if (!NotificationRemoteInputManager.this.mLockscreenUserManager.shouldAllowLockscreenRemoteInput()) {
                int userId = pendingIntent.getCreatorUserHandle().getIdentifier();
                if (!NotificationRemoteInputManager.this.mLockscreenUserManager.isLockscreenPublicMode(userId)) {
                    if (NotificationRemoteInputManager.this.mUserManager.getUserInfo(userId).isManagedProfile() && NotificationRemoteInputManager.this.mPresenter.isDeviceLocked(userId)) {
                        NotificationRemoteInputManager.this.mCallback.onLockedWorkRemoteInput(userId, row, view);
                        return true;
                    }
                } else {
                    NotificationRemoteInputManager.this.mCallback.onLockedRemoteInput(row, view);
                    return true;
                }
            }
            if (riv == null) {
                riv = findRemoteInputView(row.getPrivateLayout().getExpandedChild());
                if (riv == null) {
                    return false;
                }
                if (!row.getPrivateLayout().getExpandedChild().isShown()) {
                    NotificationRemoteInputManager.this.mCallback.onMakeExpandedVisibleForRemoteInput(row, view);
                    return true;
                }
            }
            int width = view.getWidth();
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getLayout() != null) {
                    int innerWidth = (int) tv.getLayout().getLineWidth(0);
                    width = Math.min(width, innerWidth + tv.getCompoundPaddingLeft() + tv.getCompoundPaddingRight());
                }
            }
            int innerWidth2 = view.getLeft();
            int cx = innerWidth2 + (width / 2);
            int cy = view.getTop() + (view.getHeight() / 2);
            int w = riv.getWidth();
            int h = riv.getHeight();
            int r = Math.max(Math.max(cx + cy, cx + (h - cy)), Math.max((w - cx) + cy, (w - cx) + (h - cy)));
            riv.setRevealParameters(cx, cy, r);
            riv.setPendingIntent(pendingIntent);
            riv.setRemoteInput(inputs, input);
            riv.focusAnimated();
            return true;
        }

        private RemoteInputView findRemoteInputView(View v) {
            if (v == null) {
                return null;
            }
            return (RemoteInputView) v.findViewWithTag(RemoteInputView.VIEW_TAG);
        }
    }

    public NotificationRemoteInputManager(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager, Callback callback, RemoteInputController.Delegate delegate) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        this.mCallback = callback;
        this.mRemoteInputController = new RemoteInputController(delegate);
        this.mRemoteInputController.addCallback(new AnonymousClass2());
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$2, reason: invalid class name */
    class AnonymousClass2 implements RemoteInputController.Callback {
        AnonymousClass2() {
        }

        @Override // com.android.systemui.statusbar.RemoteInputController.Callback
        public void onRemoteInputSent(final NotificationData.Entry entry) {
            if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY && NotificationRemoteInputManager.this.mEntryManager.isNotificationKeptForRemoteInput(entry.key)) {
                NotificationRemoteInputManager.this.mEntryManager.removeNotification(entry.key, null);
            } else if (NotificationRemoteInputManager.this.mRemoteInputEntriesToRemoveOnCollapse.contains(entry)) {
                NotificationRemoteInputManager.this.mPresenter.getHandler().postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationRemoteInputManager$2$upPlaSspsvwkYecy75h02dyePMo
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationRemoteInputManager.AnonymousClass2.lambda$onRemoteInputSent$0(this.f$0, entry);
                    }
                }, 200L);
            }
            try {
                NotificationRemoteInputManager.this.mBarService.onNotificationDirectReplied(entry.notification.getKey());
            } catch (RemoteException e) {
            }
        }

        public static /* synthetic */ void lambda$onRemoteInputSent$0(AnonymousClass2 anonymousClass2, NotificationData.Entry entry) {
            if (NotificationRemoteInputManager.this.mRemoteInputEntriesToRemoveOnCollapse.remove(entry)) {
                NotificationRemoteInputManager.this.mEntryManager.removeNotification(entry.key, null);
            }
        }
    }

    public RemoteInputController getController() {
        return this.mRemoteInputController;
    }

    public void onUpdateNotification(NotificationData.Entry entry) {
        this.mRemoteInputEntriesToRemoveOnCollapse.remove(entry);
    }

    public boolean onRemoveNotification(NotificationData.Entry entry) {
        if (entry != null && this.mRemoteInputController.isRemoteInputActive(entry) && entry.row != null && !entry.row.isDismissed()) {
            this.mRemoteInputEntriesToRemoveOnCollapse.add(entry);
            return true;
        }
        return false;
    }

    public void onPerformRemoveNotification(StatusBarNotification n, NotificationData.Entry entry) {
        if (this.mRemoteInputController.isRemoteInputActive(entry)) {
            this.mRemoteInputController.removeRemoteInput(entry, null);
        }
    }

    public void removeRemoteInputEntriesKeptUntilCollapsed() {
        for (int i = 0; i < this.mRemoteInputEntriesToRemoveOnCollapse.size(); i++) {
            NotificationData.Entry entry = this.mRemoteInputEntriesToRemoveOnCollapse.valueAt(i);
            this.mRemoteInputController.removeRemoteInput(entry, null);
            this.mEntryManager.removeNotification(entry.key, this.mEntryManager.getLatestRankingMap());
        }
        this.mRemoteInputEntriesToRemoveOnCollapse.clear();
    }

    public void checkRemoteInputOutside(MotionEvent event) {
        if (event.getAction() == 4 && event.getX() == 0.0f && event.getY() == 0.0f && this.mRemoteInputController.isRemoteInputActive()) {
            this.mRemoteInputController.closeRemoteInputs();
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NotificationRemoteInputManager state:");
        pw.print("  mRemoteInputEntriesToRemoveOnCollapse: ");
        pw.println(this.mRemoteInputEntriesToRemoveOnCollapse);
    }

    public void bindRow(ExpandableNotificationRow row) {
        row.setRemoteInputController(this.mRemoteInputController);
        row.setRemoteViewClickHandler(this.mOnClickHandler);
    }

    @VisibleForTesting
    public Set<NotificationData.Entry> getRemoteInputEntriesToRemoveOnCollapse() {
        return this.mRemoteInputEntriesToRemoveOnCollapse;
    }
}
