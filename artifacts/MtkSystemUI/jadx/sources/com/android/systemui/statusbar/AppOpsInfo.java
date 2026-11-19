package com.android.systemui.statusbar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class AppOpsInfo extends LinearLayout implements NotificationGuts.GutsContent {
    private String mAppName;
    private ArraySet<Integer> mAppOps;
    private int mAppUid;
    private NotificationGuts mGutsContainer;
    private MetricsLogger mMetricsLogger;
    private View.OnClickListener mOnOk;
    private OnSettingsClickListener mOnSettingsClickListener;
    private String mPkg;
    private PackageManager mPm;
    private StatusBarNotification mSbn;

    public interface OnSettingsClickListener {
        void onClick(View view, String str, int i, ArraySet<Integer> arraySet);
    }

    public AppOpsInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOnOk = new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$AppOpsInfo$68oqrp8uVHC6FuhanxBqfZBzjSI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.closeControls(view);
            }
        };
    }

    public void bindGuts(PackageManager pm, OnSettingsClickListener onSettingsClick, StatusBarNotification sbn, ArraySet<Integer> activeOps) {
        this.mPkg = sbn.getPackageName();
        this.mSbn = sbn;
        this.mPm = pm;
        this.mAppName = this.mPkg;
        this.mOnSettingsClickListener = onSettingsClick;
        this.mAppOps = activeOps;
        bindHeader();
        bindPrompt();
        bindButtons();
        this.mMetricsLogger = new MetricsLogger();
        this.mMetricsLogger.visibility(1345, true);
    }

    private void bindHeader() {
        Drawable pkgicon = null;
        try {
            ApplicationInfo info = this.mPm.getApplicationInfo(this.mPkg, 795136);
            if (info != null) {
                this.mAppUid = this.mSbn.getUid();
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(info));
                pkgicon = this.mPm.getApplicationIcon(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            pkgicon = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(R.id.pkgicon)).setImageDrawable(pkgicon);
        ((TextView) findViewById(R.id.pkgname)).setText(this.mAppName);
    }

    private void bindPrompt() {
        TextView prompt = (TextView) findViewById(R.id.prompt);
        prompt.setText(getPrompt());
    }

    private void bindButtons() {
        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$AppOpsInfo$t2ZvU1jOw3dfO--nkIVBlcF8VwE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppOpsInfo appOpsInfo = this.f$0;
                appOpsInfo.mOnSettingsClickListener.onClick(view, appOpsInfo.mPkg, appOpsInfo.mAppUid, appOpsInfo.mAppOps);
            }
        });
        TextView ok = (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(this.mOnOk);
    }

    private String getPrompt() {
        if (this.mAppOps == null || this.mAppOps.size() == 0) {
            return "";
        }
        if (this.mAppOps.size() == 1) {
            if (this.mAppOps.contains(26)) {
                return this.mContext.getString(R.string.appops_camera);
            }
            if (this.mAppOps.contains(27)) {
                return this.mContext.getString(R.string.appops_microphone);
            }
            return this.mContext.getString(R.string.appops_overlay);
        }
        if (this.mAppOps.size() == 2) {
            if (this.mAppOps.contains(26)) {
                if (this.mAppOps.contains(27)) {
                    return this.mContext.getString(R.string.appops_camera_mic);
                }
                return this.mContext.getString(R.string.appops_camera_overlay);
            }
            return this.mContext.getString(R.string.appops_mic_overlay);
        }
        return this.mContext.getString(R.string.appops_camera_mic_overlay);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (this.mGutsContainer != null && event.getEventType() == 32) {
            if (this.mGutsContainer.isExposed()) {
                event.getText().add(this.mContext.getString(R.string.notification_channel_controls_opened_accessibility, this.mAppName));
            } else {
                event.getText().add(this.mContext.getString(R.string.notification_channel_controls_closed_accessibility, this.mAppName));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeControls(View v) {
        this.mMetricsLogger.visibility(1345, false);
        int[] parentLoc = new int[2];
        int[] targetLoc = new int[2];
        this.mGutsContainer.getLocationOnScreen(parentLoc);
        v.getLocationOnScreen(targetLoc);
        int centerX = v.getWidth() / 2;
        int centerY = v.getHeight() / 2;
        int x = (targetLoc[0] - parentLoc[0]) + centerX;
        int y = (targetLoc[1] - parentLoc[1]) + centerY;
        this.mGutsContainer.closeControls(x, y, false, false);
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public void setGutsParent(NotificationGuts guts) {
        this.mGutsContainer = guts;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean willBeRemoved() {
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean shouldBeSaved() {
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public View getContentView() {
        return this;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public boolean handleCloseControls(boolean save, boolean force) {
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationGuts.GutsContent
    public int getActualHeight() {
        return getHeight();
    }
}
