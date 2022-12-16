package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class StepView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var radius = 30f
    private val strokePaint = Paint()
    private val linePaint = Paint()
    private val fillPaint = Paint()

    private val paintMap = mutableMapOf<Int, Paint>(
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

    init {
        strokePaint.run {
            color = Color.WHITE
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
        fillPaint.run {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        linePaint.run {
            color = Color.WHITE
            strokeWidth = 10f
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 200;
        val desiredHeight = 200;

        val widthMode = MeasureSpec.getMode(widthMeasureSpec);
        val widthSize = MeasureSpec.getSize(widthMeasureSpec);
        val heightMode = MeasureSpec.getMode(heightMeasureSpec);
        val heightSize = MeasureSpec.getSize(heightMeasureSpec);

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize;
            }
            MeasureSpec.AT_MOST -> {
                desiredWidth.coerceAtMost(widthSize);
            }
            else -> {
                desiredWidth;
            }
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize;
            }
            MeasureSpec.AT_MOST -> {
                desiredHeight.coerceAtMost(heightSize);
            }
            else -> {
                desiredHeight;
            }
        }

        setMeasuredDimension(width, height);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawCircle(
                (width / 6).toFloat(),
                (height / 2).toFloat(),
                radius,
                paintMap[1] ?: strokePaint
            )
            drawLine(
                width / 6 + radius,
                (height / 2).toFloat(),
                width / 2 - radius,
                (height / 2).toFloat(),
                linePaint
            )
            drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                radius,
                paintMap[2] ?: strokePaint
            )
            drawLine(
                width / 2 + radius,
                (height / 2).toFloat(),
                5 * width / 6 - radius,
                (height / 2).toFloat(),
                linePaint
            )
            drawCircle(
                (5 * width / 6).toFloat(),
                (height / 2).toFloat(),
                radius,
                paintMap[3] ?: strokePaint
            )
        }
    }
}