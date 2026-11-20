package com.hcn.bluetooth.view;

import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.hcn.bluetooth.bean.CallInfo;
import com.hcn.skin2.Skin2;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class PhoneCallViewBase extends LinearLayout implements View.OnTouchListener {
    protected boolean isAddView;
    protected int lastX;
    protected int lastY;
    protected AudioManager mAudioManager;
    protected Context mContext;
    protected boolean mEnable;
    protected WindowManager.LayoutParams mLayoutParams;
    protected boolean mMoveFlag;
    protected View mViewRoot;
    protected WindowManager mWindowManager;
    protected int paramX;
    protected int paramY;
    protected Rect rect;

    protected abstract int getLayoutId();

    protected abstract void initView();

    public abstract void onActionCallStateChanged(List<BluetoothHeadsetClientCall> list);

    public abstract void updateAudioState(int i);

    public abstract void updateCallTime(Map<String, CallInfo> map);

    public PhoneCallViewBase(Context context, WindowManager windowManager, AudioManager audioManager) {
        super(context);
        this.isAddView = false;
        this.mEnable = true;
        this.mMoveFlag = false;
        this.rect = new Rect();
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mAudioManager = audioManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        layoutParams.type = 2016;
        this.mLayoutParams.format = 1;
        this.mLayoutParams.gravity = 17;
        this.mLayoutParams.width = -2;
        this.mLayoutParams.height = -2;
        this.mLayoutParams.flags = 8;
        if (getLayoutId() > 0) {
            View viewInflate = Skin2.inflate(getLayoutId(), this, true);
            this.mViewRoot = viewInflate;
            viewInflate.setOnTouchListener(this);
            initView();
        }
    }

    public void showView() {
        if (this.mEnable && !this.isAddView) {
            this.isAddView = true;
            try {
                this.mWindowManager.addView(this, this.mLayoutParams);
            } catch (Exception unused) {
            }
        }
    }

    public void hideView() {
        if (this.isAddView) {
            try {
                this.mWindowManager.removeView(this);
                this.isAddView = false;
            } catch (Exception unused) {
            }
        }
    }

    public void updateView() {
        if (this.isAddView) {
            try {
                this.mWindowManager.updateViewLayout(this, this.mLayoutParams);
            } catch (Exception unused) {
            }
        }
    }

    public boolean isAddView() {
        return this.isAddView;
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
        if (z) {
            return;
        }
        hideView();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        if (action == 0) {
            this.lastX = (int) rawX;
            this.lastY = (int) rawY;
            this.paramX = this.mLayoutParams.x;
            this.paramY = this.mLayoutParams.y;
            this.mMoveFlag = false;
        } else if (action != 1) {
            if (action == 2) {
                int i = (int) (rawX - this.lastX);
                int i2 = (int) (rawY - this.lastY);
                if ((this.mLayoutParams.gravity & 80) == 80) {
                    i2 = -i2;
                }
                calculateLayoutParamXY(this.paramX + i, this.paramY + i2);
                if (Math.abs(i) > 10 || Math.abs(i2) > 10) {
                    this.mMoveFlag = true;
                    updateView();
                }
            }
        } else if (this.mMoveFlag) {
            calculateLayoutParamXY(this.mLayoutParams.x, this.mLayoutParams.y);
            updateView();
        }
        return true;
    }

    public void calculateLayoutParamXY(int i, int i2) {
        getWindowVisibleDisplayFrame(this.rect);
        int width = (int) (((this.rect.right - this.rect.left) - getWidth()) * 0.5f);
        if (Math.abs(i) <= width) {
            this.mLayoutParams.x = i;
        } else {
            this.mLayoutParams.x = Integer.signum(i) * width;
        }
        if ((this.mLayoutParams.gravity & 80) == 80 || (this.mLayoutParams.gravity & 48) == 48) {
            this.mLayoutParams.y = Math.min(Math.max(0, i2), (this.rect.bottom - this.rect.top) - getHeight());
        } else if ((this.mLayoutParams.gravity & 16) == 16) {
            int height = (int) (((this.rect.bottom - this.rect.top) - getHeight()) * 0.5f);
            if (Math.abs(i2) <= height) {
                this.mLayoutParams.y = i2;
            } else {
                this.mLayoutParams.y = Integer.signum(i2) * height;
            }
        }
    }
}
