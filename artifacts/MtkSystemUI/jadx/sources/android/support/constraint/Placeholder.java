package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class Placeholder extends View {
    private View mContent;
    private int mContentId;
    private int mDefaultVisibility;

    public Placeholder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContentId = -1;
        this.mContent = null;
        this.mDefaultVisibility = 4;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        super.setVisibility(this.mDefaultVisibility);
        this.mContentId = -1;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.ConstraintLayout_Layout_content) {
                    this.mContentId = a.getResourceId(attr, this.mContentId);
                }
            }
        }
    }

    public View getContent() {
        return this.mContent;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
            Rect r = new Rect();
            canvas.getClipBounds(r);
            paint.setTextSize(r.height());
            int cHeight = r.height();
            int cWidth = r.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, "?".length(), r);
            float x = ((cWidth / 2.0f) - (r.width() / 2.0f)) - r.left;
            float y = ((cHeight / 2.0f) + (r.height() / 2.0f)) - r.bottom;
            canvas.drawText("?", x, y, paint);
        }
    }

    public void updatePreLayout(ConstraintLayout container) {
        if (this.mContentId == -1) {
            getLayoutParams();
            if (!isInEditMode()) {
                setVisibility(this.mDefaultVisibility);
            }
        }
        this.mContent = container.findViewById(this.mContentId);
        if (this.mContent != null) {
            ConstraintLayout.LayoutParams layoutParamsContent = (ConstraintLayout.LayoutParams) this.mContent.getLayoutParams();
            layoutParamsContent.isInPlaceholder = true;
            this.mContent.setVisibility(0);
            setVisibility(0);
        }
    }

    public void updatePostMeasure(ConstraintLayout container) {
        if (this.mContent == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsContent = (ConstraintLayout.LayoutParams) this.mContent.getLayoutParams();
        layoutParamsContent.widget.setVisibility(0);
        layoutParams.widget.setWidth(layoutParamsContent.widget.getWidth());
        layoutParams.widget.setHeight(layoutParamsContent.widget.getHeight());
        layoutParamsContent.widget.setVisibility(8);
    }
}
