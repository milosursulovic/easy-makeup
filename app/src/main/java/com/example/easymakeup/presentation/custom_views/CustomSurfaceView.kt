package com.example.easymakeup.presentation.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceView

class CustomSurfaceView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs) {
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawOval(width / 6f, height / 6f, 5 * width / 6f, 5 * height / 6f, paint)
    }
}