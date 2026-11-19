package com.android.systemui.statusbar;

import android.content.ComponentName;
import android.graphics.Rect;
import android.hardware.biometrics.IBiometricPromptReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.SystemUI;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CommandQueue extends IStatusBar.Stub {
    private int mDisable1;
    private int mDisable2;
    private final Object mLock = new Object();
    private ArrayList<Callbacks> mCallbacks = new ArrayList<>();
    private Handler mHandler = new H(Looper.getMainLooper());

    public interface Callbacks {
        default void setIcon(String slot, StatusBarIcon icon) {
        }

        default void removeIcon(String slot) {
        }

        default void disable(int state1, int state2, boolean animate) {
        }

        default void animateExpandNotificationsPanel() {
        }

        default void animateCollapsePanels(int flags) {
        }

        default void togglePanel() {
        }

        default void animateExpandSettingsPanel(String obj) {
        }

        default void setSystemUiVisibility(int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenStackBounds, Rect dockedStackBounds) {
        }

        default void topAppWindowChanged(boolean visible) {
        }

        default void setImeWindowStatus(IBinder token, int vis, int backDisposition, boolean showImeSwitcher) {
        }

        default void showRecentApps(boolean triggeredFromAltTab) {
        }

        default void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) {
        }

        default void toggleRecentApps() {
        }

        default void toggleSplitScreen() {
        }

        default void preloadRecentApps() {
        }

        default void dismissKeyboardShortcutsMenu() {
        }

        default void toggleKeyboardShortcutsMenu(int deviceId) {
        }

        default void cancelPreloadRecentApps() {
        }

        default void setWindowState(int window, int state) {
        }

        default void showScreenPinningRequest(int taskId) {
        }

        default void appTransitionPending(boolean forced) {
        }

        default void appTransitionCancelled() {
        }

        default void appTransitionStarting(long startTime, long duration, boolean forced) {
        }

        default void appTransitionFinished() {
        }

        default void showAssistDisclosure() {
        }

        default void startAssist(Bundle args) {
        }

        default void onCameraLaunchGestureDetected(int source) {
        }

        default void showPictureInPictureMenu() {
        }

        default void setTopAppHidesStatusBar(boolean topAppHidesStatusBar) {
        }

        default void addQsTile(ComponentName tile) {
        }

        default void remQsTile(ComponentName tile) {
        }

        default void clickTile(ComponentName tile) {
        }

        default void handleSystemKey(int arg1) {
        }

        default void showPinningEnterExitToast(boolean entering) {
        }

        default void showPinningEscapeToast() {
        }

        default void handleShowGlobalActionsMenu() {
        }

        default void handleShowShutdownUi(boolean isReboot, String reason) {
        }

        default void showWirelessChargingAnimation(int batteryLevel) {
        }

        default void onRotationProposal(int rotation, boolean isValid) {
        }

        default void showFingerprintDialog(Bundle bundle, IBiometricPromptReceiver receiver) {
        }

        default void onFingerprintAuthenticated() {
        }

        default void onFingerprintHelp(String message) {
        }

        default void onFingerprintError(String error) {
        }

        default void hideFingerprintDialog() {
        }
    }

    protected CommandQueue() {
    }

    public void addCallbacks(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
        callbacks.disable(this.mDisable1, this.mDisable2, false);
    }

    public void removeCallbacks(Callbacks callbacks) {
        this.mCallbacks.remove(callbacks);
    }

    public void setIcon(String slot, StatusBarIcon icon) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 1, 0, new Pair(slot, icon)).sendToTarget();
        }
    }

    public void removeIcon(String slot) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 2, 0, slot).sendToTarget();
        }
    }

    public void disable(int state1, int state2, boolean animate) {
        synchronized (this.mLock) {
            this.mDisable1 = state1;
            this.mDisable2 = state2;
            this.mHandler.removeMessages(131072);
            Message msg = this.mHandler.obtainMessage(131072, state1, state2, Boolean.valueOf(animate));
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                this.mHandler.handleMessage(msg);
                msg.recycle();
            } else {
                msg.sendToTarget();
            }
        }
    }

    public void disable(int state1, int state2) {
        disable(state1, state2, true);
    }

    public void recomputeDisableFlags(boolean animate) {
        disable(this.mDisable1, this.mDisable2, animate);
    }

    public void animateExpandNotificationsPanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(196608);
            this.mHandler.sendEmptyMessage(196608);
        }
    }

    public void animateCollapsePanels() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, 0, 0).sendToTarget();
        }
    }

    public void animateCollapsePanels(int flags) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, flags, 0).sendToTarget();
        }
    }

    public void togglePanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2293760);
            this.mHandler.obtainMessage(2293760, 0, 0).sendToTarget();
        }
    }

    public void animateExpandSettingsPanel(String subPanel) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(327680);
            this.mHandler.obtainMessage(327680, subPanel).sendToTarget();
        }
    }

    public void setSystemUiVisibility(int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenStackBounds, Rect dockedStackBounds) {
        synchronized (this.mLock) {
            SomeArgs args = SomeArgs.obtain();
            args.argi1 = vis;
            args.argi2 = fullscreenStackVis;
            args.argi3 = dockedStackVis;
            args.argi4 = mask;
            args.arg1 = fullscreenStackBounds;
            args.arg2 = dockedStackBounds;
            this.mHandler.obtainMessage(393216, args).sendToTarget();
        }
    }

    public void topAppWindowChanged(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(458752);
            this.mHandler.obtainMessage(458752, z ? 1 : 0, 0, null).sendToTarget();
        }
    }

    public void setImeWindowStatus(IBinder token, int vis, int backDisposition, boolean showImeSwitcher) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(524288);
            Message m = this.mHandler.obtainMessage(524288, vis, backDisposition, token);
            m.getData().putBoolean("showImeSwitcherKey", showImeSwitcher);
            m.sendToTarget();
        }
    }

    public void showRecentApps(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(851968);
            this.mHandler.obtainMessage(851968, z ? 1 : 0, 0, null).sendToTarget();
        }
    }

    public void hideRecentApps(boolean z, boolean z2) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(917504);
            this.mHandler.obtainMessage(917504, z ? 1 : 0, z2 ? 1 : 0, null).sendToTarget();
        }
    }

    public void toggleSplitScreen() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1966080);
            this.mHandler.obtainMessage(1966080, 0, 0, null).sendToTarget();
        }
    }

    public void toggleRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(589824);
            Message msg = this.mHandler.obtainMessage(589824, 0, 0, null);
            msg.setAsynchronous(true);
            msg.sendToTarget();
        }
    }

    public void preloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(655360);
            this.mHandler.obtainMessage(655360, 0, 0, null).sendToTarget();
        }
    }

    public void cancelPreloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(720896);
            this.mHandler.obtainMessage(720896, 0, 0, null).sendToTarget();
        }
    }

    public void dismissKeyboardShortcutsMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2097152);
            this.mHandler.obtainMessage(2097152).sendToTarget();
        }
    }

    public void toggleKeyboardShortcutsMenu(int deviceId) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1638400);
            this.mHandler.obtainMessage(1638400, deviceId, 0).sendToTarget();
        }
    }

    public void showPictureInPictureMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1703936);
            this.mHandler.obtainMessage(1703936).sendToTarget();
        }
    }

    public void setWindowState(int window, int state) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(786432, window, state, null).sendToTarget();
        }
    }

    public void showScreenPinningRequest(int taskId) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1179648, taskId, 0, null).sendToTarget();
        }
    }

    public void appTransitionPending() {
        appTransitionPending(false);
    }

    public void appTransitionPending(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1245184, z ? 1 : 0, 0).sendToTarget();
        }
    }

    public void appTransitionCancelled() {
        synchronized (this.mLock) {
            this.mHandler.sendEmptyMessage(1310720);
        }
    }

    public void appTransitionStarting(long startTime, long duration) {
        appTransitionStarting(startTime, duration, false);
    }

    public void appTransitionStarting(long j, long j2, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1376256, z ? 1 : 0, 0, Pair.create(Long.valueOf(j), Long.valueOf(j2))).sendToTarget();
        }
    }

    public void appTransitionFinished() {
        synchronized (this.mLock) {
            this.mHandler.sendEmptyMessage(2031616);
        }
    }

    public void showAssistDisclosure() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1441792);
            this.mHandler.obtainMessage(1441792).sendToTarget();
        }
    }

    public void startAssist(Bundle args) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1507328);
            this.mHandler.obtainMessage(1507328, args).sendToTarget();
        }
    }

    public void onCameraLaunchGestureDetected(int source) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1572864);
            this.mHandler.obtainMessage(1572864, source, 0).sendToTarget();
        }
    }

    public void addQsTile(ComponentName tile) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1769472, tile).sendToTarget();
        }
    }

    public void remQsTile(ComponentName tile) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1835008, tile).sendToTarget();
        }
    }

    public void clickQsTile(ComponentName tile) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1900544, tile).sendToTarget();
        }
    }

    public void handleSystemKey(int key) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2162688, key, 0).sendToTarget();
        }
    }

    public void showPinningEnterExitToast(boolean entering) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2949120, Boolean.valueOf(entering)).sendToTarget();
        }
    }

    public void showPinningEscapeToast() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3014656).sendToTarget();
        }
    }

    public void showGlobalActionsMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2228224);
            this.mHandler.obtainMessage(2228224).sendToTarget();
        }
    }

    public void setTopAppHidesStatusBar(boolean z) {
        this.mHandler.removeMessages(2424832);
        this.mHandler.obtainMessage(2424832, z ? 1 : 0, 0).sendToTarget();
    }

    public void showShutdownUi(boolean z, String str) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2359296);
            this.mHandler.obtainMessage(2359296, z ? 1 : 0, 0, str).sendToTarget();
        }
    }

    public void showWirelessChargingAnimation(int batteryLevel) {
        this.mHandler.removeMessages(2883584);
        this.mHandler.obtainMessage(2883584, batteryLevel, 0).sendToTarget();
    }

    public void onProposedRotationChanged(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2490368);
            this.mHandler.obtainMessage(2490368, i, z ? 1 : 0, null).sendToTarget();
        }
    }

    public void showFingerprintDialog(Bundle bundle, IBiometricPromptReceiver receiver) {
        synchronized (this.mLock) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = bundle;
            args.arg2 = receiver;
            this.mHandler.obtainMessage(2555904, args).sendToTarget();
        }
    }

    public void onFingerprintAuthenticated() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2621440).sendToTarget();
        }
    }

    public void onFingerprintHelp(String message) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2686976, message).sendToTarget();
        }
    }

    public void onFingerprintError(String error) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2752512, error).sendToTarget();
        }
    }

    public void hideFingerprintDialog() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2818048).sendToTarget();
        }
    }

    private final class H extends Handler {
        private H(Looper l) {
            super(l);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int what = msg.what & (-65536);
            int i = 0;
            switch (what) {
                case 65536:
                    switch (msg.arg1) {
                        case 1:
                            Pair<String, StatusBarIcon> p = (Pair) msg.obj;
                            while (i < CommandQueue.this.mCallbacks.size()) {
                                ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setIcon((String) p.first, (StatusBarIcon) p.second);
                                i++;
                            }
                            break;
                    }
                    while (true) {
                        int i2 = i;
                        if (i2 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i2)).removeIcon((String) msg.obj);
                            i = i2 + 1;
                        }
                        break;
                    }
                    break;
                case 131072:
                    while (true) {
                        int i3 = i;
                        if (i3 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i3)).disable(msg.arg1, msg.arg2, ((Boolean) msg.obj).booleanValue());
                            i = i3 + 1;
                        }
                        break;
                    }
                    break;
                case 196608:
                    while (true) {
                        int i4 = i;
                        if (i4 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i4)).animateExpandNotificationsPanel();
                            i = i4 + 1;
                        }
                        break;
                    }
                    break;
                case 262144:
                    while (true) {
                        int i5 = i;
                        if (i5 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i5)).animateCollapsePanels(msg.arg1);
                            i = i5 + 1;
                        }
                        break;
                    }
                    break;
                case 327680:
                    while (true) {
                        int i6 = i;
                        if (i6 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i6)).animateExpandSettingsPanel((String) msg.obj);
                            i = i6 + 1;
                        }
                        break;
                    }
                    break;
                case 393216:
                    SomeArgs args = (SomeArgs) msg.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setSystemUiVisibility(args.argi1, args.argi2, args.argi3, args.argi4, (Rect) args.arg1, (Rect) args.arg2);
                        i++;
                    }
                    args.recycle();
                    break;
                case 458752:
                    for (int i7 = 0; i7 < CommandQueue.this.mCallbacks.size(); i7++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i7)).topAppWindowChanged(msg.arg1 != 0);
                    }
                    break;
                case 524288:
                    for (int i8 = 0; i8 < CommandQueue.this.mCallbacks.size(); i8++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i8)).setImeWindowStatus((IBinder) msg.obj, msg.arg1, msg.arg2, msg.getData().getBoolean("showImeSwitcherKey", false));
                    }
                    break;
                case 589824:
                    while (true) {
                        int i9 = i;
                        if (i9 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i9)).toggleRecentApps();
                            i = i9 + 1;
                        }
                        break;
                    }
                    break;
                case 655360:
                    while (true) {
                        int i10 = i;
                        if (i10 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i10)).preloadRecentApps();
                            i = i10 + 1;
                        }
                        break;
                    }
                    break;
                case 720896:
                    while (true) {
                        int i11 = i;
                        if (i11 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i11)).cancelPreloadRecentApps();
                            i = i11 + 1;
                        }
                        break;
                    }
                    break;
                case 786432:
                    while (true) {
                        int i12 = i;
                        if (i12 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i12)).setWindowState(msg.arg1, msg.arg2);
                            i = i12 + 1;
                        }
                        break;
                    }
                    break;
                case 851968:
                    for (int i13 = 0; i13 < CommandQueue.this.mCallbacks.size(); i13++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i13)).showRecentApps(msg.arg1 != 0);
                    }
                    break;
                case 917504:
                    for (int i14 = 0; i14 < CommandQueue.this.mCallbacks.size(); i14++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i14)).hideRecentApps(msg.arg1 != 0, msg.arg2 != 0);
                    }
                    break;
                case 1179648:
                    while (true) {
                        int i15 = i;
                        if (i15 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i15)).showScreenPinningRequest(msg.arg1);
                            i = i15 + 1;
                        }
                        break;
                    }
                    break;
                case 1245184:
                    for (int i16 = 0; i16 < CommandQueue.this.mCallbacks.size(); i16++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i16)).appTransitionPending(msg.arg1 != 0);
                    }
                    break;
                case 1310720:
                    while (true) {
                        int i17 = i;
                        if (i17 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i17)).appTransitionCancelled();
                            i = i17 + 1;
                        }
                        break;
                    }
                    break;
                case 1376256:
                    for (int i18 = 0; i18 < CommandQueue.this.mCallbacks.size(); i18++) {
                        Pair<Long, Long> data = (Pair) msg.obj;
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i18)).appTransitionStarting(((Long) data.first).longValue(), ((Long) data.second).longValue(), msg.arg1 != 0);
                    }
                    break;
                case 1441792:
                    while (true) {
                        int i19 = i;
                        if (i19 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i19)).showAssistDisclosure();
                            i = i19 + 1;
                        }
                        break;
                    }
                    break;
                case 1507328:
                    while (true) {
                        int i20 = i;
                        if (i20 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i20)).startAssist((Bundle) msg.obj);
                            i = i20 + 1;
                        }
                        break;
                    }
                    break;
                case 1572864:
                    while (true) {
                        int i21 = i;
                        if (i21 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i21)).onCameraLaunchGestureDetected(msg.arg1);
                            i = i21 + 1;
                        }
                        break;
                    }
                    break;
                case 1638400:
                    while (true) {
                        int i22 = i;
                        if (i22 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i22)).toggleKeyboardShortcutsMenu(msg.arg1);
                            i = i22 + 1;
                        }
                        break;
                    }
                    break;
                case 1703936:
                    while (true) {
                        int i23 = i;
                        if (i23 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i23)).showPictureInPictureMenu();
                            i = i23 + 1;
                        }
                        break;
                    }
                    break;
                case 1769472:
                    while (true) {
                        int i24 = i;
                        if (i24 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i24)).addQsTile((ComponentName) msg.obj);
                            i = i24 + 1;
                        }
                        break;
                    }
                    break;
                case 1835008:
                    while (true) {
                        int i25 = i;
                        if (i25 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i25)).remQsTile((ComponentName) msg.obj);
                            i = i25 + 1;
                        }
                        break;
                    }
                    break;
                case 1900544:
                    while (true) {
                        int i26 = i;
                        if (i26 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i26)).clickTile((ComponentName) msg.obj);
                            i = i26 + 1;
                        }
                        break;
                    }
                    break;
                case 1966080:
                    while (true) {
                        int i27 = i;
                        if (i27 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i27)).toggleSplitScreen();
                            i = i27 + 1;
                        }
                        break;
                    }
                    break;
                case 2031616:
                    while (true) {
                        int i28 = i;
                        if (i28 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i28)).appTransitionFinished();
                            i = i28 + 1;
                        }
                        break;
                    }
                    break;
                case 2097152:
                    while (true) {
                        int i29 = i;
                        if (i29 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i29)).dismissKeyboardShortcutsMenu();
                            i = i29 + 1;
                        }
                        break;
                    }
                    break;
                case 2162688:
                    while (true) {
                        int i30 = i;
                        if (i30 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i30)).handleSystemKey(msg.arg1);
                            i = i30 + 1;
                        }
                        break;
                    }
                    break;
                case 2228224:
                    while (true) {
                        int i31 = i;
                        if (i31 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i31)).handleShowGlobalActionsMenu();
                            i = i31 + 1;
                        }
                        break;
                    }
                    break;
                case 2293760:
                    while (true) {
                        int i32 = i;
                        if (i32 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i32)).togglePanel();
                            i = i32 + 1;
                        }
                        break;
                    }
                    break;
                case 2359296:
                    for (int i33 = 0; i33 < CommandQueue.this.mCallbacks.size(); i33++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i33)).handleShowShutdownUi(msg.arg1 != 0, (String) msg.obj);
                    }
                    break;
                case 2424832:
                    for (int i34 = 0; i34 < CommandQueue.this.mCallbacks.size(); i34++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i34)).setTopAppHidesStatusBar(msg.arg1 != 0);
                    }
                    break;
                case 2490368:
                    for (int i35 = 0; i35 < CommandQueue.this.mCallbacks.size(); i35++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i35)).onRotationProposal(msg.arg1, msg.arg2 != 0);
                    }
                    break;
                case 2555904:
                    CommandQueue.this.mHandler.removeMessages(2752512);
                    CommandQueue.this.mHandler.removeMessages(2686976);
                    CommandQueue.this.mHandler.removeMessages(2621440);
                    while (true) {
                        int i36 = i;
                        if (i36 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i36)).showFingerprintDialog((Bundle) ((SomeArgs) msg.obj).arg1, (IBiometricPromptReceiver) ((SomeArgs) msg.obj).arg2);
                            i = i36 + 1;
                        }
                        break;
                    }
                    break;
                case 2621440:
                    while (true) {
                        int i37 = i;
                        if (i37 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i37)).onFingerprintAuthenticated();
                            i = i37 + 1;
                        }
                        break;
                    }
                    break;
                case 2686976:
                    while (true) {
                        int i38 = i;
                        if (i38 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i38)).onFingerprintHelp((String) msg.obj);
                            i = i38 + 1;
                        }
                        break;
                    }
                    break;
                case 2752512:
                    while (true) {
                        int i39 = i;
                        if (i39 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i39)).onFingerprintError((String) msg.obj);
                            i = i39 + 1;
                        }
                        break;
                    }
                    break;
                case 2818048:
                    while (true) {
                        int i40 = i;
                        if (i40 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i40)).hideFingerprintDialog();
                            i = i40 + 1;
                        }
                        break;
                    }
                    break;
                case 2883584:
                    while (true) {
                        int i41 = i;
                        if (i41 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i41)).showWirelessChargingAnimation(msg.arg1);
                            i = i41 + 1;
                        }
                        break;
                    }
                    break;
                case 2949120:
                    while (true) {
                        int i42 = i;
                        if (i42 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i42)).showPinningEnterExitToast(((Boolean) msg.obj).booleanValue());
                            i = i42 + 1;
                        }
                        break;
                    }
                    break;
                case 3014656:
                    while (true) {
                        int i43 = i;
                        if (i43 < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i43)).showPinningEscapeToast();
                            i = i43 + 1;
                        }
                        break;
                    }
                    break;
            }
        }
    }

    public static class CommandQueueStart extends SystemUI {
        @Override // com.android.systemui.SystemUI
        public void start() {
            putComponent(CommandQueue.class, new CommandQueue());
        }
    }
}
