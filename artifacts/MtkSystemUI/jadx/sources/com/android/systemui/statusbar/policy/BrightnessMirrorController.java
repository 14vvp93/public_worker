package com.android.systemui.statusbar.policy;

import android.content.res.Resources;
import android.util.ArraySet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.StatusBarWindowView;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class BrightnessMirrorController implements CallbackController<BrightnessMirrorListener> {
    private View mBrightnessMirror;
    private final ArraySet<BrightnessMirrorListener> mBrightnessMirrorListeners = new ArraySet<>();
    private final int[] mInt2Cache = new int[2];
    private final NotificationPanelView mNotificationPanel;
    private final StatusBarWindowView mStatusBarWindow;
    private final Consumer<Boolean> mVisibilityCallback;

    public interface BrightnessMirrorListener {
        void onBrightnessMirrorReinflated(View view);
    }

    public BrightnessMirrorController(StatusBarWindowView statusBarWindow, Consumer<Boolean> visibilityCallback) {
        this.mStatusBarWindow = statusBarWindow;
        this.mBrightnessMirror = statusBarWindow.findViewById(R.id.brightness_mirror);
        this.mNotificationPanel = (NotificationPanelView) statusBarWindow.findViewById(R.id.notification_panel);
        this.mNotificationPanel.setPanelAlphaEndAction(new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$BrightnessMirrorController$6Ez050oVQOhwQ3Mf-NjJAvUx4_k
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mBrightnessMirror.setVisibility(4);
            }
        });
        this.mVisibilityCallback = visibilityCallback;
    }

    public void showMirror() {
        this.mBrightnessMirror.setVisibility(0);
        this.mVisibilityCallback.accept(true);
        this.mNotificationPanel.setPanelAlpha(0, true);
    }

    public void hideMirror() {
        this.mVisibilityCallback.accept(false);
        this.mNotificationPanel.setPanelAlpha(255, true);
    }

    public void setLocation(View original) {
        original.getLocationInWindow(this.mInt2Cache);
        int originalX = this.mInt2Cache[0] + (original.getWidth() / 2);
        int originalY = this.mInt2Cache[1] + (original.getHeight() / 2);
        this.mBrightnessMirror.setTranslationX(0.0f);
        this.mBrightnessMirror.setTranslationY(0.0f);
        this.mBrightnessMirror.getLocationInWindow(this.mInt2Cache);
        int mirrorX = this.mInt2Cache[0] + (this.mBrightnessMirror.getWidth() / 2);
        int mirrorY = this.mInt2Cache[1] + (this.mBrightnessMirror.getHeight() / 2);
        this.mBrightnessMirror.setTranslationX(originalX - mirrorX);
        this.mBrightnessMirror.setTranslationY(originalY - mirrorY);
    }

    public View getMirror() {
        return this.mBrightnessMirror;
    }

    public void updateResources() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mBrightnessMirror.getLayoutParams();
        Resources r = this.mBrightnessMirror.getResources();
        lp.width = r.getDimensionPixelSize(R.dimen.qs_panel_width);
        lp.height = r.getDimensionPixelSize(R.dimen.brightness_mirror_height);
        lp.gravity = r.getInteger(R.integer.notification_panel_layout_gravity);
        this.mBrightnessMirror.setLayoutParams(lp);
    }

    public void onOverlayChanged() {
        reinflate();
    }

    public void onDensityOrFontScaleChanged() {
        reinflate();
    }

    private void reinflate() {
        ContextThemeWrapper qsThemeContext = new ContextThemeWrapper(this.mBrightnessMirror.getContext(), R.style.qs_theme);
        int index = this.mStatusBarWindow.indexOfChild(this.mBrightnessMirror);
        this.mStatusBarWindow.removeView(this.mBrightnessMirror);
        int i = 0;
        this.mBrightnessMirror = LayoutInflater.from(qsThemeContext).inflate(R.layout.brightness_mirror, (ViewGroup) this.mStatusBarWindow, false);
        this.mStatusBarWindow.addView(this.mBrightnessMirror, index);
        while (true) {
            int i2 = i;
            if (i2 < this.mBrightnessMirrorListeners.size()) {
                this.mBrightnessMirrorListeners.valueAt(i2).onBrightnessMirrorReinflated(this.mBrightnessMirror);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(BrightnessMirrorListener listener) {
        Preconditions.checkNotNull(listener);
        this.mBrightnessMirrorListeners.add(listener);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(BrightnessMirrorListener listener) {
        this.mBrightnessMirrorListeners.remove(listener);
    }
}
