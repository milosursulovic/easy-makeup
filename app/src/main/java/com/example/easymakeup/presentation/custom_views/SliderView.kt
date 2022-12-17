package com.example.easymakeup.presentation.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.easymakeup.presentation.custom_views.utils.Measures

class SliderView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val path = Path()

    private val paint = Paint().apply {
        color = Color.WHITE
    }

    private var color = Color.TRANSPARENT

    fun setColor(color: Int) {
        this.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            Measures.getWidth(1000, widthMeasureSpec),
            Measures.getHeight(200, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            path.run {
                moveTo(0f, height / 3f)
                lineTo(width / 6f, height / 3f)
                lineTo(width / 6f, 2 * height / 3f)
                lineTo(0f, 2 * height / 3f)
                lineTo(0f, height / 3f)

                moveTo(width / 4f, height / 3f)
                lineTo(3 * width / 4f, height / 3f)
                lineTo(3 * width / 4f, 2 * height / 3f)
                lineTo(width / 4f, 2 * height / 3f)
                lineTo(width / 4f, height / 3f)

                moveTo(5 * width / 6f, height / 3f)
                lineTo(width.toFloat(), height / 3f)
                lineTo(width.toFloat(), 2 * height / 3f)
                lineTo(5 * width / 6f, 2 * height / 3f)
                lineTo(5 * width / 6f, height / 3f)

                moveTo(21 * width / 24f, height / 4f)
                lineTo(23 * width / 24f, height / 4f)
                lineTo(23 * width / 24f, 3 * height / 4f)
                lineTo(21 * width / 24f, 3 * height / 4f)
                lineTo(21 * width / 24f, height / 4f)
            }
            drawPath(path, paint)
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
        val x = event?.x
        val y = event?.y

        if (y!! > height / 3 && y < 2 * height / 3 && x!! > 0 && x < width / 6) {
            minusClickListener?.let {
                it(color)
            }
        }

        if (x!! > 5 * width / 6f && x < width && y > height / 4 && y < 3 * height / 4) {
            plusClickListener?.let {
                it(color)
            }
        }

        return true
    }
}