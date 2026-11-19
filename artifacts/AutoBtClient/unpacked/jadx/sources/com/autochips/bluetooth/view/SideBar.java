package com.autochips.bluetooth.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.skinx_night.NightX;

/* JADX INFO: loaded from: classes.dex */
public class SideBar extends View {

    /* JADX INFO: renamed from: b */
    public static String[] f71b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", NightX.DARK_TAG_SEGMENTS_SEPARATOR};
    private int choose;
    private int direction;
    private int mBackgroundColor;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint;
    private int txt_color;
    private int txt_color_choose;
    private int txt_size;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String str);
    }

    public void setChoose(int i) {
        if (i >= 0 && i < f71b.length) {
            this.choose = i;
        } else {
            this.choose = -1;
        }
        invalidate();
    }

    public void setTextView(TextView textView) {
        this.mTextDialog = textView;
    }

    public void setDirection(int i) {
        this.direction = i;
    }

    public void setShowBackground(int i) {
        this.mBackgroundColor = i;
    }

    public void setTextSize(int i) {
        this.txt_size = i;
    }

    public SideBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.choose = -1;
        this.paint = new Paint();
        this.txt_color = ViewCompat.MEASURED_SIZE_MASK;
        this.txt_color_choose = 12976128;
        this.txt_size = 22;
        this.direction = 0;
        this.mBackgroundColor = -1;
    }

    public SideBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.choose = -1;
        this.paint = new Paint();
        this.txt_color = ViewCompat.MEASURED_SIZE_MASK;
        this.txt_color_choose = 12976128;
        this.txt_size = 22;
        this.direction = 0;
        this.mBackgroundColor = -1;
        this.txt_color = SkinUtils.getColor(C0414R.color.tel_search_txt_color_uix);
        this.txt_color_choose = SkinUtils.getColor(C0414R.color.txt_color_choose);
        this.txt_size = SkinUtils.getInteger(C0414R.integer.side_bar_text_size);
        this.direction = SkinUtils.getInteger(C0414R.integer.side_bar_direction);
    }

    public SideBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
        this.txt_color = ViewCompat.MEASURED_SIZE_MASK;
        this.txt_color_choose = 12976128;
        this.txt_size = 22;
        this.direction = 0;
        this.mBackgroundColor = -1;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float length;
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        float length2 = 0.0f;
        if (this.direction == 0) {
            float f = width * 1.0f;
            String[] strArr = f71b;
            length = (f - ((f / strArr.length) / 2.0f)) / strArr.length;
        } else {
            float f2 = height * 1.0f;
            String[] strArr2 = f71b;
            length2 = (f2 - ((f2 / strArr2.length) / 2.0f)) / strArr2.length;
            length = 0.0f;
        }
        int i = this.mBackgroundColor;
        if (i != -1) {
            canvas.drawColor(i);
        }
        for (int i2 = 0; i2 < f71b.length; i2++) {
            this.paint.setColor(this.txt_color);
            this.paint.setTypeface(Typeface.DEFAULT_BOLD);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(this.txt_size);
            if (i2 == this.choose) {
                this.paint.setColor(this.txt_color_choose);
            }
            if (this.direction == 0) {
                int i3 = height / 2;
                this.paint.measureText(f71b[i2]);
                canvas.drawText(f71b[i2], ((i2 * length) + length) - 8.0f, 24.0f, this.paint);
            } else {
                canvas.drawText(f71b[i2], (width / 2) - (this.paint.measureText(f71b[i2]) / 2.0f), (i2 * length2) + length2, this.paint);
            }
            this.paint.reset();
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int height;
        getParent().requestDisallowInterceptTouchEvent(true);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int i = this.choose;
        OnTouchingLetterChangedListener onTouchingLetterChangedListener = this.onTouchingLetterChangedListener;
        if (this.direction == 0) {
            height = (int) ((x / getWidth()) * f71b.length);
        } else {
            height = (int) ((y / getHeight()) * f71b.length);
        }
        if (action == 1) {
            invalidate();
            TextView textView = this.mTextDialog;
            if (textView != null) {
                textView.setVisibility(4);
            }
        } else if (i != height && height >= 0) {
            String[] strArr = f71b;
            if (height < strArr.length) {
                if (onTouchingLetterChangedListener != null) {
                    onTouchingLetterChangedListener.onTouchingLetterChanged(strArr[height]);
                }
                TextView textView2 = this.mTextDialog;
                if (textView2 != null) {
                    textView2.setText(f71b[height]);
                    this.mTextDialog.setVisibility(0);
                }
                this.choose = height;
                invalidate();
            }
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
}
