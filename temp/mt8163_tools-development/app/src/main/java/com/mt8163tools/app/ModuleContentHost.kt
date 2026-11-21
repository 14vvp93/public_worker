package com.mt8163tools.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class ModuleContentHost @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val defaultImportantForAccessibility = importantForAccessibility

    private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xB3000000.toInt()
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFFFFFF.toInt()
        textSize = 18f * resources.displayMetrics.scaledDensity
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    var contentEnabled: Boolean = true
        private set

    init {
        setWillNotDraw(false)
    }

    fun setContentEnabled(enabled: Boolean) {
        if (contentEnabled == enabled) return
        contentEnabled = enabled
        descendantFocusability = if (enabled) {
            ViewGroup.FOCUS_AFTER_DESCENDANTS
        } else {
            ViewGroup.FOCUS_BLOCK_DESCENDANTS
        }
        importantForAccessibility = if (enabled) {
            defaultImportantForAccessibility
        } else {
            View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        }
        if (!enabled) clearFocus()
        invalidate()
    }

    override fun dispatchTouchEvent(event: android.view.MotionEvent): Boolean {
        if (!contentEnabled) return true
        return super.dispatchTouchEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (!contentEnabled) return true
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (!contentEnabled) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)
            val baseline = height / 2f - (textPaint.ascent() + textPaint.descent()) / 2f
            canvas.drawText("MODULE DISABLED", width / 2f, baseline, textPaint)
        }
    }
}
