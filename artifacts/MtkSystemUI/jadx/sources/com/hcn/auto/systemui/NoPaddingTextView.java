package com.hcn.auto.systemui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class NoPaddingTextView extends TextView {
    private Rect mRect;

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mRect == null) {
            this.mRect = new Rect();
        }
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), this.mRect);
        int width = this.mRect.width();
        int height = this.mRect.height();
        setMeasuredDimension(width, height);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        int left = this.mRect.left;
        int top = this.mRect.top;
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        canvas.drawText(text, -left, -top, paint);
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        this.mRect = null;
        requestLayout();
    }
}
