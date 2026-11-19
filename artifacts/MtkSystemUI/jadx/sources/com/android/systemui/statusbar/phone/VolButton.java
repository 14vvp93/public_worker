package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class VolButton extends ImageView {
    private MyHandler handler;
    private long intervalTime;
    private LongClickRepeatListener repeatListener;

    public interface LongClickRepeatListener {
        void repeatAction();
    }

    public VolButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.handler = new MyHandler(this);
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.phone.VolButton.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                new Thread(new LongClickThread()).start();
                return true;
            }
        });
    }

    private class LongClickThread implements Runnable {
        private int num;

        private LongClickThread() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (VolButton.this.isPressed()) {
                this.num++;
                if (this.num % 5 == 0) {
                    VolButton.this.handler.sendEmptyMessage(1);
                }
                SystemClock.sleep(VolButton.this.intervalTime / 5);
            }
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<VolButton> ref;

        MyHandler(VolButton button) {
            this.ref = new WeakReference<>(button);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VolButton button = this.ref.get();
            if (button != null && button.repeatListener != null) {
                button.repeatListener.repeatAction();
            }
        }
    }

    public void setLongClickRepeatListener(LongClickRepeatListener listener, long intervalTime) {
        this.repeatListener = listener;
        this.intervalTime = intervalTime;
    }

    public void setLongClickRepeatListener(LongClickRepeatListener listener) {
        setLongClickRepeatListener(listener, 100L);
    }
}
