package com.example.easymakeup.presentation.custom_views.utils

import android.view.View

object Measures {
    private fun getMode(value: Int): Int = View.MeasureSpec.getMode(value)

    private fun getSize(value: Int): Int = View.MeasureSpec.getSize(value);

    fun getWidth(desiredWidth: Int, widthMeasureSpec: Int): Int {
        val widthMode = getMode(widthMeasureSpec)
        val widthSize = getSize(widthMeasureSpec)

        return when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
            else -> desiredWidth
        }
    }

    fun getHeight(desiredHeight: Int, heightMeasureSpec: Int): Int {
        val heightMode = getMode(heightMeasureSpec)
        val heightSize = getSize(heightMeasureSpec)

        return when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }
    }
}