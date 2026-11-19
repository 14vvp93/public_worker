package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.SystemClock;
import android.util.Slog;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;

/* JADX INFO: loaded from: classes.dex */
public class ScreenPinningNotify {
    private final Context mContext;
    private long mLastShowToastTime;
    private Toast mLastToast;

    public ScreenPinningNotify(Context context) {
        this.mContext = context;
    }

    public void showPinningStartToast() {
        makeAllUserToastAndShow(R.string.screen_pinning_start);
    }

    public void showPinningExitToast() {
        makeAllUserToastAndShow(R.string.screen_pinning_exit);
    }

    public void showEscapeToast(boolean isRecentsButtonVisible) {
        int i;
        long showToastTime = SystemClock.elapsedRealtime();
        if (showToastTime - this.mLastShowToastTime < 1000) {
            Slog.i("ScreenPinningNotify", "Ignore toast since it is requested in very short interval.");
            return;
        }
        if (this.mLastToast != null) {
            this.mLastToast.cancel();
        }
        if (isRecentsButtonVisible) {
            i = R.string.screen_pinning_toast;
        } else {
            i = R.string.screen_pinning_toast_recents_invisible;
        }
        this.mLastToast = makeAllUserToastAndShow(i);
        this.mLastShowToastTime = showToastTime;
    }

    private Toast makeAllUserToastAndShow(int resId) {
        Toast toast = SysUIToast.makeText(this.mContext, resId, 1);
        toast.show();
        return toast;
    }
}
