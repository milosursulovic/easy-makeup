package com.example.easymakeup.presentation.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.easymakeup.presentation.custom_views.utils.Measures

class ColorChangeView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    private val minusRect = RectF()
    private val sliderRect = RectF()
    private val overlayRect = RectF()
    private val plusRect1 = RectF()
    private val plusRect2 = RectF()

    private var color = Color.TRANSPARENT
    private var currentBrightness = 0f

    fun setColor(color: Int) {
        this.color = color
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        currentBrightness = String.format("%.1f", hsv[2]).toFloat()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            Measures.getWidth(1000, widthMeasureSpec),
            Measures.getHeight(200, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            minusRect.run {
                left = 0f
                top = height / 3f
                right = width / 6f
                bottom = 2 * height / 3f
            }

            sliderRect.run {
                left = width / 4f
                top = height / 3f
                right = 3 * width / 4f
                bottom = 2 * height / 3f
            }

            plusRect1.run {
                left = 5 * width / 6f
                top = height / 3f
                right = width.toFloat()
                bottom = 2 * height / 3f
            }

            plusRect2.run {
                left = 21 * width / 24f
                top = height / 4f
                right = 23 * width / 24f
                bottom = 3 * height / 4f
            }

            drawRect(minusRect, paint)
            drawRect(sliderRect, paint)
            drawRect(plusRect1, paint)
            drawRect(plusRect2, paint)
            if (currentBrightness > 0f) {
                overlayRect.run {
                    left = width / 4f
                    top = height / 3f
                    right = width / 4f + (3 * width / 4f - width / 4f) * currentBrightness
                    bottom = 2 * height / 3f
                }
                drawRect(overlayRect, overlayPaint)
            }
        }
    }

    private var minusClickListener: ((Int) -> Unit)? = null
    private var plusClickListener: ((Int) -> Unit)? = null

    fun setMinusClickListener(minusClickListener: (Int) -> Unit) {
        this.minusClickListener = minusClickListener
    }

    fun setPlusClickListener(plusClickListener: (Int) -> Unit) {
        this.plusClickListener = plusClickListener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y

            if (y > height / 3 && y < 2 * height / 3 && x > 0 && x < width / 6) {
                minusClickListener?.let {
                    if (currentBrightness > 0f) {
                        currentBrightness -= 0.1f
                        currentBrightness = String.format("%.1f", currentBrightness).toFloat()
                        val tempColor = getBrightenColor()
                        it(tempColor)
                    }
                }
            }

            if (x > 5 * width / 6f && x < width && y > height / 4 && y < 3 * height / 4) {
                plusClickListener?.let {
                    if (currentBrightness < 1f) {
                        currentBrightness += 0.1f
                        currentBrightness = String.format("%.1f", currentBrightness).toFloat()
                        val tempColor = getBrightenColor()
                        it(tempColor)
                    }
                }
            }

            invalidate()
        }

        return true
    }

    private fun getBrightenColor(): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = currentBrightness
        return Color.HSVToColor(hsv)
    }
}