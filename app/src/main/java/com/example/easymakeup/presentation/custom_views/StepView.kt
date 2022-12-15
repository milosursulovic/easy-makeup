package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.easymakeup.R

class StepView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private lateinit var ta: TypedArray
    var currentStep: Int = 0
    private var strokePaint = Paint()
    private var fillPaint = Paint()

    init {
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.StepView, 0, 0)
            currentStep = ta.getString(R.styleable.StepView_current_step)?.toInt() ?: 0
        } finally {
            ta.recycle()
        }

        fillPaint.style = Paint.Style.FILL
        fillPaint.color = Color.BLACK

        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(50f, 50f, 50f, if (currentStep == 1) fillPaint else strokePaint)
        canvas?.drawLine(100f, 50f, 200f, 50f, fillPaint)
        canvas?.drawCircle(250f, 50f, 50f, if (currentStep == 2) fillPaint else strokePaint)
        canvas?.drawLine(300f, 50f, 400f, 50f, fillPaint)
        canvas?.drawCircle(450f, 50f, 50f, if (currentStep == 3) fillPaint else strokePaint)
    }
}