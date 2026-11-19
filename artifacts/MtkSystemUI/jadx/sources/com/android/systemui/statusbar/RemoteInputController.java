package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RemoteInputController {
    private static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    private final Delegate mDelegate;
    private final ArrayList<Pair<WeakReference<NotificationData.Entry>, Object>> mOpen = new ArrayList<>();
    private final ArrayMap<String, Object> mSpinning = new ArrayMap<>();
    private final ArrayList<Callback> mCallbacks = new ArrayList<>(3);

    public interface Delegate {
        void lockScrollTo(NotificationData.Entry entry);

        void requestDisallowLongPressAndDismiss();

        void setRemoteInputActive(NotificationData.Entry entry, boolean z);
    }

    public RemoteInputController(Delegate delegate) {
        this.mDelegate = delegate;
    }

    public static void processForRemoteInput(Notification n, Context context) {
        RemoteInput[] remoteInputs;
        if (ENABLE_REMOTE_INPUT && n.extras != null && n.extras.containsKey("android.wearable.EXTENSIONS")) {
            if (n.actions == null || n.actions.length == 0) {
                Notification.WearableExtender we = new Notification.WearableExtender(n);
                List<Notification.Action> actions = we.getActions();
                int numActions = actions.size();
                Notification.Action viableAction = null;
                for (int i = 0; i < numActions; i++) {
                    Notification.Action action = actions.get(i);
                    if (action != null && (remoteInputs = action.getRemoteInputs()) != null) {
                        for (RemoteInput ri : remoteInputs) {
                            if (ri.getAllowFreeFormInput()) {
                                viableAction = action;
                                break;
                            }
                        }
                        if (viableAction != null) {
                            break;
                        }
                    }
                }
                if (viableAction != null) {
                    Notification.Builder rebuilder = Notification.Builder.recoverBuilder(context, n);
                    rebuilder.setActions(viableAction);
                    rebuilder.build();
                }
            }
        }
    }

    public void addRemoteInput(NotificationData.Entry entry, Object token) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(token);
        boolean found = pruneWeakThenRemoveAndContains(entry, null, token);
        if (!found) {
            this.mOpen.add(new Pair<>(new WeakReference(entry), token));
        }
        apply(entry);
    }

    public void removeRemoteInput(NotificationData.Entry entry, Object token) {
        Preconditions.checkNotNull(entry);
        pruneWeakThenRemoveAndContains(null, entry, token);
        apply(entry);
    }

    public void addSpinning(String key, Object token) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(token);
        this.mSpinning.put(key, token);
    }

    public void removeSpinning(String key, Object token) {
        Preconditions.checkNotNull(key);
        if (token == null || this.mSpinning.get(key) == token) {
            this.mSpinning.remove(key);
        }
    }

    public boolean isSpinning(String key) {
        return this.mSpinning.containsKey(key);
    }

    public boolean isSpinning(String key, Object token) {
        return this.mSpinning.get(key) == token;
    }

    private void apply(NotificationData.Entry entry) {
        this.mDelegate.setRemoteInputActive(entry, isRemoteInputActive(entry));
        boolean remoteInputActive = isRemoteInputActive();
        int N = this.mCallbacks.size();
        for (int i = 0; i < N; i++) {
            this.mCallbacks.get(i).onRemoteInputActive(remoteInputActive);
        }
    }

    public boolean isRemoteInputActive(NotificationData.Entry entry) {
        return pruneWeakThenRemoveAndContains(entry, null, null);
    }

    public boolean isRemoteInputActive() {
        pruneWeakThenRemoveAndContains(null, null, null);
        return !this.mOpen.isEmpty();
    }

    private boolean pruneWeakThenRemoveAndContains(NotificationData.Entry contains, NotificationData.Entry remove, Object removeToken) {
        boolean found = false;
        for (int i = this.mOpen.size() - 1; i >= 0; i--) {
            NotificationData.Entry item = (NotificationData.Entry) ((WeakReference) this.mOpen.get(i).first).get();
            Object itemToken = this.mOpen.get(i).second;
            boolean removeTokenMatches = removeToken == null || itemToken == removeToken;
            if (item == null || (item == remove && removeTokenMatches)) {
                this.mOpen.remove(i);
            } else if (item == contains) {
                if (removeToken != null && removeToken != itemToken) {
                    this.mOpen.remove(i);
                } else {
                    found = true;
                }
            }
        }
        return found;
    }

    public void addCallback(Callback callback) {
        Preconditions.checkNotNull(callback);
        this.mCallbacks.add(callback);
    }

    public void remoteInputSent(NotificationData.Entry entry) {
        int N = this.mCallbacks.size();
        for (int i = 0; i < N; i++) {
            this.mCallbacks.get(i).onRemoteInputSent(entry);
        }
    }

    public void closeRemoteInputs() {
        if (this.mOpen.size() == 0) {
            return;
        }
        ArrayList<NotificationData.Entry> list = new ArrayList<>(this.mOpen.size());
        for (int i = this.mOpen.size() - 1; i >= 0; i--) {
            NotificationData.Entry item = (NotificationData.Entry) ((WeakReference) this.mOpen.get(i).first).get();
            if (item != null && item.row != null) {
                list.add(item);
            }
        }
        int i2 = list.size();
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            NotificationData.Entry item2 = list.get(i3);
            if (item2.row != null) {
                item2.row.closeRemoteInput();
            }
        }
    }

    public void requestDisallowLongPressAndDismiss() {
        this.mDelegate.requestDisallowLongPressAndDismiss();
    }

    public void lockScrollTo(NotificationData.Entry entry) {
        this.mDelegate.lockScrollTo(entry);
    }

    public interface Callback {
        default void onRemoteInputActive(boolean active) {
        }

        default void onRemoteInputSent(NotificationData.Entry entry) {
        }
    }
}
