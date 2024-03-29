package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ColorView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val rect = RectF()
    private val rectPaint = Paint().apply {
        color = Color.TRANSPARENT
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
    }
    private val borderCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rect.apply {
            top = 0f
            left = 0f
            bottom = width.toFloat()
            right = height.toFloat()
        }
    }

    fun setColor(color: Int) {
        circlePaint.color = color
        borderCirclePaint.color = Color.WHITE
        invalidate()
    }

    fun getColor(): Int = circlePaint.color

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            drawRect(rect, rectPaint)
            drawCircle(width / 2f, height / 2f, 100f, circlePaint)
            drawCircle(width / 2f, height / 2f, 100f, borderCirclePaint)
        }
    }
}