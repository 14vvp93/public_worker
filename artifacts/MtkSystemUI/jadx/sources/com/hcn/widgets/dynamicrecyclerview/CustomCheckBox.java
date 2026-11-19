package com.hcn.widgets.dynamicrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class CustomCheckBox extends View {
    private static final String TAG = CustomCheckBox.class.getCanonicalName();
    private Paint mBitmapPaint;
    private Drawable mCheckIcon;
    private RectF mCheckableScope;
    private boolean mChecked;
    private Context mContext;
    private Drawable mDrawable;
    private float mDrawableSize;
    private float mDrawableX;
    private float mDrawableY;
    private int mIconGravity;
    private float mIconSize;
    private float mIconX;
    private float mIconY;
    private View.OnClickListener mOnClickListener;
    private float mPadding;
    private String mText;
    private int mTextColor;
    private float mTextMarginTop;
    private TextPaint mTextPaint;
    private float mTextSize;
    private float mTextWidth;
    private float mTextX;
    private float mTextY;
    private Drawable mUncheckIcon;
    private float mViewWidth;

    public CustomCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mChecked = false;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox);
        this.mPadding = a.getDimension(R.styleable.CustomCheckBox_padding, getResources().getDimension(R.dimen.CustomCheckBox_padding));
        this.mDrawableSize = a.getDimension(R.styleable.CustomCheckBox_drawableSize, getResources().getDimension(R.dimen.CustomCheckBox_drawableSize));
        this.mDrawable = a.getDrawable(R.styleable.CustomCheckBox_drawable);
        this.mDrawable = this.mDrawable == null ? getResources().getDrawable(R.drawable.customcheckbox_drawable_default) : this.mDrawable;
        this.mCheckIcon = a.getDrawable(R.styleable.CustomCheckBox_checkIcon);
        this.mCheckIcon = this.mCheckIcon == null ? getResources().getDrawable(R.drawable.icon_checked) : this.mCheckIcon;
        this.mUncheckIcon = a.getDrawable(R.styleable.CustomCheckBox_uncheckIcon);
        this.mIconGravity = a.getInteger(R.styleable.CustomCheckBox_icon_Gravity, 2);
        this.mIconSize = a.getDimension(R.styleable.CustomCheckBox_iconSize, this.mCheckIcon.getIntrinsicWidth());
        this.mText = a.getString(R.styleable.CustomCheckBox_text);
        this.mTextSize = a.getDimension(R.styleable.CustomCheckBox_textSize, getResources().getDimension(R.dimen.CustomCheckBox_textSize));
        this.mTextColor = a.getColor(R.styleable.CustomCheckBox_textColor, getResources().getColor(R.color.black));
        this.mTextMarginTop = a.getDimension(R.styleable.CustomCheckBox_textMarginTop, getResources().getDimension(R.dimen.CustomCheckBox_textMarginTop));
        a.recycle();
        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setAntiAlias(true);
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mViewWidth = this.mDrawableSize + (this.mPadding * 2.0f);
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mDrawableX = this.mPadding;
        calculateIconXY();
        this.mTextX = this.mViewWidth / 2.0f;
        this.mTextY = this.mDrawableY + this.mDrawableSize + this.mTextMarginTop + this.mTextSize;
        this.mCheckIcon.setBounds((int) this.mIconX, (int) this.mIconY, (int) (this.mIconX + this.mIconSize), (int) (this.mIconY + this.mIconSize));
        if (this.mUncheckIcon != null) {
            this.mUncheckIcon.setBounds((int) this.mIconX, (int) this.mIconY, (int) (this.mIconX + this.mIconSize), (int) (this.mIconY + this.mIconSize));
        }
        this.mCheckableScope = new RectF(this.mPadding, 0.0f, this.mPadding + this.mDrawableSize, this.mDrawableSize);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mDrawable.setBounds((int) this.mDrawableX, (int) this.mDrawableY, (int) (this.mDrawableX + this.mDrawableSize), (int) (this.mDrawableY + this.mDrawableSize));
        this.mDrawable.draw(canvas);
        if (this.mChecked) {
            this.mCheckIcon.draw(canvas);
        } else if (this.mUncheckIcon != null) {
            this.mUncheckIcon.draw(canvas);
        }
        this.mTextWidth = this.mTextPaint.measureText(this.mText);
        if (this.mTextWidth >= this.mViewWidth) {
            this.mText = TextUtils.ellipsize(this.mText, this.mTextPaint, this.mViewWidth, TextUtils.TruncateAt.END).toString();
        }
        canvas.drawText(this.mText, this.mTextX, this.mTextY, this.mTextPaint);
    }

    private void calculateIconXY() {
        if (this.mIconGravity == 2 || this.mIconGravity == 1) {
            this.mDrawableY = this.mIconSize / 2.0f;
        } else {
            this.mDrawableY = 0.0f;
        }
        switch (this.mIconGravity) {
            case 1:
                this.mIconX = this.mPadding - (this.mIconSize / 2.0f);
                this.mIconY = 0.0f;
                break;
            case 2:
                this.mIconX = (this.mViewWidth - this.mPadding) - (this.mIconSize / 2.0f);
                this.mIconY = 0.0f;
                break;
            case 3:
                this.mIconX = this.mPadding - (this.mIconSize / 2.0f);
                this.mIconY = (this.mDrawableY + this.mDrawableSize) - (this.mIconSize / 2.0f);
                break;
            case 4:
                this.mIconX = (this.mViewWidth - this.mPadding) - (this.mIconSize / 2.0f);
                this.mIconY = (this.mDrawableY + this.mDrawableSize) - (this.mIconSize / 2.0f);
                break;
            default:
                this.mIconX = (this.mViewWidth - this.mIconSize) / 2.0f;
                this.mIconY = ((this.mDrawableSize - this.mIconSize) / 2.0f) + this.mDrawableY;
                break;
        }
    }

    public void redrawCheckIcon() {
        postInvalidate((int) Math.floor(this.mIconX), (int) Math.floor(this.mIconY), (int) Math.ceil(this.mIconX + this.mIconSize), (int) Math.ceil(this.mIconY + this.mIconSize));
    }

    public void setChecked(boolean checked) {
        this.mChecked = checked;
    }

    public void setDrawableSize(int size) {
        if (size <= 0) {
            return;
        }
        this.mDrawableSize = size;
        this.mViewWidth = this.mDrawableSize + (this.mPadding * 2.0f);
    }

    public void setDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        this.mDrawable = drawable;
    }

    public void setCheckIcon(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        this.mCheckIcon = drawable;
        this.mIconSize = this.mCheckIcon.getIntrinsicWidth();
    }

    public void setIconGravity(int gravity) {
        if (gravity >= 0 && gravity <= 4) {
            this.mIconGravity = gravity;
        } else {
            this.mIconGravity = 0;
        }
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setTextSize(int textSize) {
        if (textSize <= 0) {
            return;
        }
        this.mTextSize = textSize;
        this.mTextPaint.setTextSize(this.mTextSize);
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        this.mTextPaint.setColor(this.mTextColor);
    }

    public void setTextMarginTop(float textMarginTop) {
        this.mTextMarginTop = textMarginTop;
    }

    public void setPadding(float padding) {
        this.mPadding = padding;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1 && event.getX() >= this.mCheckableScope.left && event.getX() <= this.mCheckableScope.right && event.getY() >= this.mCheckableScope.top && event.getY() <= this.mCheckableScope.bottom) {
            setChecked(!this.mChecked);
            redrawCheckIcon();
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onClick(this);
            }
        }
        return true;
    }
}
