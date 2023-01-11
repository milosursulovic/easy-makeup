package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.easymakeup.presentation.custom_views.utils.Measures

class StepView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var radius = 30f
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 10f
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 10f
    }

    private val paintMap = mutableMapOf(
        1 to strokePaint,
        2 to strokePaint,
        3 to strokePaint
    )

    fun setStep(step: Int) {
        if (step in 1..3) {
            for (i in 1..step) {
                paintMap[i] = fillPaint
            }
        } else {
            for (i in 1..3) {
                paintMap[i] = strokePaint
            }
        }
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
            drawCircle(
                width / 6f,
                height / 2f,
                radius,
                paintMap[1] ?: strokePaint
            )
            drawLine(
                width / 6 + radius,
                height / 2f,
                width / 2 - radius,
                height / 2f,
                linePaint
            )
            drawCircle(
                width / 2f,
                height / 2f,
                radius,
                paintMap[2] ?: strokePaint
            )
            drawLine(
                width / 2 + radius,
                height / 2f,
                5 * width / 6 - radius,
                height / 2f,
                linePaint
            )
            drawCircle(
                5 * width / 6f,
                height / 2f,
                radius,
                paintMap[3] ?: strokePaint
            )
        }
    }
}