package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.easymakeup.presentation.custom_views.utils.Measures

class SliderView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val path = Path()

    private val paint = Paint().apply {
        color = Color.WHITE
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
}