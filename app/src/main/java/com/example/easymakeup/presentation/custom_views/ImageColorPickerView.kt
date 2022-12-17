package com.example.easymakeup.presentation.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class ImageColorPickerView(context: Context, attrs: AttributeSet) :
    AppCompatImageView(context, attrs) {

    private var imageX: Float? = 0f
    private var imageY: Float? = 0f

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.TRANSPARENT
    }

    private var chosenColorListener: ((Int) -> Unit)? = null

    fun setChosenColorListener(chosenColorListener: (Int) -> Unit) {
        this.chosenColorListener = chosenColorListener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val viewX = event?.x
        val viewY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                circlePaint.color = Color.WHITE
                postInvalidate()
            }
            MotionEvent.ACTION_UP -> {
                circlePaint.color = Color.TRANSPARENT
                postInvalidate()
            }
        }

        val viewWidth = width
        val viewHeight = height
        val bitmap = (drawable as? BitmapDrawable)?.bitmap
        val bitmapWidth = bitmap?.width
        val bitmapHeight = bitmap?.height
        imageX = viewX!! * bitmapWidth!! / viewWidth
        imageY = viewY!! * bitmapHeight!! / viewHeight
        val croppedBitmap =
            Bitmap.createBitmap(
                bitmap,
                imageX!!.toInt() - 25,
                imageY!!.toInt() - 25,
                50,
                50,
                null,
                true
            )
        val outputBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.TRANSPARENT
        }
        canvas.drawCircle(25f, 25f, 50f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(croppedBitmap, 0f, 0f, paint)
        val pixels = IntArray(2500)
        croppedBitmap.getPixels(pixels, 0, 50, 0, 0, 50, 50)
        val dominantPixel = mostCommonPixel(pixels)

        chosenColorListener?.let {
            it(dominantPixel)

            outputBitmap.recycle()
            croppedBitmap.recycle()
        }

        return true
    }

    private fun mostCommonPixel(array: IntArray): Int {
        var maxCount = 0
        var mostCommon = 0
        for (i in array.indices) {
            var count = 0
            for (j in array.indices) {
                if (array[i] == array[j]) count++
            }
            if (count > maxCount) {
                maxCount = count
                mostCommon = array[i]
            }
        }

        return mostCommon
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            imageX?.let {
                drawCircle(it, imageY!!, 50f, circlePaint)
            }
        }
    }
}