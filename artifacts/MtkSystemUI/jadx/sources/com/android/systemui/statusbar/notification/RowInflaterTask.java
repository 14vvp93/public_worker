package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationData;

/* JADX INFO: loaded from: classes.dex */
public class RowInflaterTask implements AsyncLayoutInflater.OnInflateFinishedListener, InflationTask {
    private boolean mCancelled;
    private NotificationData.Entry mEntry;
    private Throwable mInflateOrigin;
    private RowInflationFinishedListener mListener;

    public interface RowInflationFinishedListener {
        void onInflationFinished(ExpandableNotificationRow expandableNotificationRow);
    }

    public void inflate(Context context, ViewGroup parent, NotificationData.Entry entry, RowInflationFinishedListener listener) {
        this.mInflateOrigin = new Throwable("inflate requested here");
        this.mListener = listener;
        AsyncLayoutInflater inflater = new AsyncLayoutInflater(context);
        this.mEntry = entry;
        entry.setInflationTask(this);
        inflater.inflate(R.layout.status_bar_notification_row, parent, this);
    }

    @Override // com.android.systemui.statusbar.InflationTask
    public void abort() {
        this.mCancelled = true;
    }

    @Override // android.support.v4.view.AsyncLayoutInflater.OnInflateFinishedListener
    public void onInflateFinished(View view, int resid, ViewGroup parent) {
        if (!this.mCancelled) {
            try {
                this.mEntry.onInflationTaskFinished();
                this.mListener.onInflationFinished((ExpandableNotificationRow) view);
            } catch (Throwable t) {
                if (this.mInflateOrigin != null) {
                    Log.e("RowInflaterTask", "Error in inflation finished listener: " + t, this.mInflateOrigin);
                    t.addSuppressed(this.mInflateOrigin);
                }
                throw t;
            }
        }
    }
}
