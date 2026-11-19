package com.android.systemui.qs.tileimpl;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTile.State;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.QSHost;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class QSTileImpl<TState extends QSTile.State> implements QSTile {
    protected static final Object ARG_SHOW_TRANSIENT_ENABLING;
    protected static final boolean DEBUG;
    private boolean mAnnounceNextStateChange;
    protected final Context mContext;
    private RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    protected final QSHost mHost;
    private int mIsFullQs;
    private boolean mShowingDetail;
    private String mTileSpec;
    protected final String TAG = "Tile." + getClass().getSimpleName();
    protected QSTileImpl<TState>.H mHandler = new H((Looper) Dependency.get(Dependency.BG_LOOPER));
    protected final Handler mUiHandler = new Handler(Looper.getMainLooper());
    private final ArraySet<Object> mListeners = new ArraySet<>();
    private final MetricsLogger mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
    private final ArrayList<QSTile.Callback> mCallbacks = new ArrayList<>();
    private final Object mStaleListener = new Object();
    protected TState mState = (TState) newTileState();
    private TState mTmpState = (TState) newTileState();

    public abstract Intent getLongClickIntent();

    @Override // com.android.systemui.plugins.qs.QSTile
    public abstract int getMetricsCategory();

    protected abstract void handleClick();

    protected abstract void handleSetListening(boolean z);

    protected abstract void handleUpdateState(TState tstate, Object obj);

    public abstract TState newTileState();

    static {
        DEBUG = Log.isLoggable("Tile", 3) || FeatureOptions.LOG_ENABLE;
        ARG_SHOW_TRANSIENT_ENABLING = new Object();
    }

    protected QSTileImpl(QSHost qSHost) {
        this.mHost = qSHost;
        this.mContext = qSHost.getContext();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setListening(Object obj, boolean z) {
        this.mHandler.obtainMessage(14, z ? 1 : 0, 0, obj).sendToTarget();
    }

    protected long getStaleTimeout() {
        return 600000L;
    }

    @VisibleForTesting
    protected void handleStale() {
        setListening(this.mStaleListener, true);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public String getTileSpec() {
        return this.mTileSpec;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setTileSpec(String tileSpec) {
        this.mTileSpec = tileSpec;
    }

    public QSHost getHost() {
        return this.mHost;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public QSIconView createTileView(Context context) {
        return new QSIconViewImpl(context);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return null;
    }

    protected DetailAdapter createDetailAdapter() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void addCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(1, callback).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void removeCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(13, callback).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void removeCallbacks() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void click() {
        this.mMetricsLogger.write(populate(new LogMaker(925).setType(4)));
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void secondaryClick() {
        this.mMetricsLogger.write(populate(new LogMaker(926).setType(4)));
        this.mHandler.sendEmptyMessage(3);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void longClick() {
        this.mMetricsLogger.write(populate(new LogMaker(366).setType(4)));
        this.mHandler.sendEmptyMessage(4);
        Prefs.putInt(this.mContext, "QsLongPressTooltipShownCount", 2);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        if (this.mState instanceof QSTile.BooleanState) {
            logMaker.addTaggedData(928, Integer.valueOf(((QSTile.BooleanState) this.mState).value ? 1 : 0));
        }
        return logMaker.setSubtype(getMetricsCategory()).addTaggedData(833, Integer.valueOf(this.mIsFullQs)).addTaggedData(927, Integer.valueOf(this.mHost.indexOf(this.mTileSpec)));
    }

    public void showDetail(boolean z) {
        this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void refreshState() {
        refreshState(null);
    }

    protected final void refreshState(Object arg) {
        this.mHandler.obtainMessage(5, arg).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void clearState() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void userSwitch(int newUserId) {
        this.mHandler.obtainMessage(7, newUserId, 0).sendToTarget();
    }

    public void fireToggleStateChanged(boolean z) {
        this.mHandler.obtainMessage(8, z ? 1 : 0, 0).sendToTarget();
    }

    public void fireScanStateChanged(boolean z) {
        this.mHandler.obtainMessage(9, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void destroy() {
        this.mHandler.sendEmptyMessage(10);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public TState getState() {
        return this.mState;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setDetailListening(boolean listening) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddCallback(QSTile.Callback callback) {
        this.mCallbacks.add(callback);
        callback.onStateChanged(this.mState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveCallback(QSTile.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveCallbacks() {
        this.mCallbacks.clear();
    }

    protected void handleSecondaryClick() {
        handleClick();
    }

    protected void handleLongClick() {
        Intent intent = getLongClickIntent();
        if (intent == null) {
            return;
        }
        ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(getLongClickIntent(), 0);
    }

    protected void handleClearState() {
        this.mTmpState = (TState) newTileState();
        this.mState = (TState) newTileState();
    }

    protected void handleRefreshState(Object arg) {
        handleUpdateState(this.mTmpState, arg);
        boolean changed = this.mTmpState.copyTo(this.mState);
        if (changed) {
            handleStateChanged();
        }
        this.mHandler.removeMessages(15);
        this.mHandler.sendEmptyMessageDelayed(15, getStaleTimeout());
        setListening(this.mStaleListener, false);
    }

    private void handleStateChanged() {
        String announcement;
        boolean delayAnnouncement = shouldAnnouncementBeDelayed();
        boolean z = false;
        if (this.mCallbacks.size() != 0) {
            QSTile.State stateNewTileState = newTileState();
            this.mState.copyTo(stateNewTileState);
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                this.mCallbacks.get(i).onStateChanged(stateNewTileState);
            }
            if (this.mAnnounceNextStateChange && !delayAnnouncement && (announcement = composeChangeAnnouncement()) != null) {
                this.mCallbacks.get(0).onAnnouncementRequested(announcement);
            }
        }
        if (this.mAnnounceNextStateChange && delayAnnouncement) {
            z = true;
        }
        this.mAnnounceNextStateChange = z;
    }

    protected boolean shouldAnnouncementBeDelayed() {
        return false;
    }

    protected String composeChangeAnnouncement() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowDetail(boolean show) {
        this.mShowingDetail = show;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onShowDetail(show);
        }
    }

    protected boolean isShowingDetail() {
        return this.mShowingDetail;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleToggleStateChanged(boolean state) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onToggleStateChanged(state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScanStateChanged(boolean state) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onScanStateChanged(state);
        }
    }

    protected void handleUserSwitch(int newUserId) {
        handleRefreshState(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetListeningInternal(Object listener, boolean listening) {
        if (listening) {
            if (this.mListeners.add(listener) && this.mListeners.size() == 1) {
                if (DEBUG) {
                    Log.d(this.TAG, "handleSetListening true");
                }
                handleSetListening(listening);
                refreshState();
            }
        } else if (this.mListeners.remove(listener) && this.mListeners.size() == 0) {
            if (DEBUG) {
                Log.d(this.TAG, "handleSetListening false");
            }
            handleSetListening(listening);
        }
        updateIsFullQs();
    }

    private void updateIsFullQs() {
        for (Object listener : this.mListeners) {
            if (PagedTileLayout.TilePage.class.equals(listener.getClass())) {
                this.mIsFullQs = 1;
                return;
            }
        }
        this.mIsFullQs = 0;
    }

    protected void handleDestroy() {
        if (this.mListeners.size() != 0) {
            handleSetListening(false);
        }
        this.mCallbacks.clear();
    }

    protected void checkIfRestrictionEnforcedByAdminOnly(QSTile.State state, String userRestriction) {
        RestrictedLockUtils.EnforcedAdmin admin = RestrictedLockUtils.checkIfRestrictionEnforced(this.mContext, userRestriction, ActivityManager.getCurrentUser());
        if (admin != null && !RestrictedLockUtils.hasBaseUserRestriction(this.mContext, userRestriction, ActivityManager.getCurrentUser())) {
            state.disabledByPolicy = true;
            this.mEnforcedAdmin = admin;
        } else {
            state.disabledByPolicy = false;
            this.mEnforcedAdmin = null;
        }
    }

    public static int getColorForState(Context context, int state) {
        switch (state) {
            case 0:
                return Utils.getDisabled(context, Utils.getColorAttr(context, R.attr.textColorSecondary));
            case 1:
                return Utils.getColorAttr(context, R.attr.textColorSecondary);
            case 2:
                return Utils.getColorAttr(context, R.attr.colorPrimary);
            default:
                Log.e("QSTile", "Invalid state " + state);
                return 0;
        }
    }

    protected final class H extends Handler {
        @VisibleForTesting
        protected H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            try {
                boolean z = true;
                if (msg.what == 1) {
                    QSTileImpl.this.handleAddCallback((QSTile.Callback) msg.obj);
                } else if (msg.what == 12) {
                    QSTileImpl.this.handleRemoveCallbacks();
                } else if (msg.what == 13) {
                    QSTileImpl.this.handleRemoveCallback((QSTile.Callback) msg.obj);
                } else if (msg.what == 2) {
                    if (QSTileImpl.this.mState.disabledByPolicy) {
                        Intent intent = RestrictedLockUtils.getShowAdminSupportDetailsIntent(QSTileImpl.this.mContext, QSTileImpl.this.mEnforcedAdmin);
                        ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(intent, 0);
                    } else {
                        QSTileImpl.this.handleClick();
                    }
                } else if (msg.what == 3) {
                    QSTileImpl.this.handleSecondaryClick();
                } else if (msg.what == 4) {
                    QSTileImpl.this.handleLongClick();
                } else if (msg.what == 5) {
                    QSTileImpl.this.handleRefreshState(msg.obj);
                } else if (msg.what == 6) {
                    QSTileImpl qSTileImpl = QSTileImpl.this;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl.handleShowDetail(z);
                } else if (msg.what == 7) {
                    QSTileImpl.this.handleUserSwitch(msg.arg1);
                } else if (msg.what == 8) {
                    QSTileImpl qSTileImpl2 = QSTileImpl.this;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl2.handleToggleStateChanged(z);
                } else if (msg.what == 9) {
                    QSTileImpl qSTileImpl3 = QSTileImpl.this;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl3.handleScanStateChanged(z);
                } else if (msg.what == 10) {
                    QSTileImpl.this.handleDestroy();
                } else if (msg.what == 11) {
                    QSTileImpl.this.handleClearState();
                } else if (msg.what == 14) {
                    QSTileImpl qSTileImpl4 = QSTileImpl.this;
                    Object obj = msg.obj;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl4.handleSetListeningInternal(obj, z);
                } else if (msg.what == 15) {
                    QSTileImpl.this.handleStale();
                } else {
                    throw new IllegalArgumentException("Unknown msg: " + msg.what);
                }
            } catch (Throwable t) {
                String error = "Error in " + ((String) null);
                Log.w(QSTileImpl.this.TAG, error, t);
                QSTileImpl.this.mHost.warn(error, t);
            }
        }
    }

    public static class DrawableIcon extends QSTile.Icon {
        protected final Drawable mDrawable;
        protected final Drawable mInvisibleDrawable;

        public DrawableIcon(Drawable drawable) {
            this.mDrawable = drawable;
            this.mInvisibleDrawable = drawable.getConstantState().newDrawable();
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return this.mDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getInvisibleDrawable(Context context) {
            return this.mInvisibleDrawable;
        }
    }

    public static class ResourceIcon extends QSTile.Icon {
        private static final SparseArray<QSTile.Icon> ICONS = new SparseArray<>();
        protected final int mResId;

        private ResourceIcon(int resId) {
            this.mResId = resId;
        }

        public static QSTile.Icon get(int resId) {
            QSTile.Icon icon = ICONS.get(resId);
            if (icon == null) {
                QSTile.Icon icon2 = new ResourceIcon(resId);
                ICONS.put(resId, icon2);
                return icon2;
            }
            return icon;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getInvisibleDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        public boolean equals(Object o) {
            return (o instanceof ResourceIcon) && ((ResourceIcon) o).mResId == this.mResId;
        }

        public String toString() {
            return String.format("ResourceIcon[resId=0x%08x]", Integer.valueOf(this.mResId));
        }
    }
}
