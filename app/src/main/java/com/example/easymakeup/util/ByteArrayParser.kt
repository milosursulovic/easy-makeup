package com.example.easymakeup.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.easymakeup.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ByteArrayParser(private val context: Context, private val byteArray: ByteArray) :
    BitmapParser {
    override fun parse(): Flow<Resource<Bitmap>> = flow {
        emit(Resource.Loading())
        try {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val rawBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            bitmap.recycle()

            val rotatedBitmap: Bitmap = if (rawBitmap.width > rawBitmap.height) {
                val matrix = Matrix()
                matrix.postRotate(90f)
                Bitmap.createBitmap(
                    rawBitmap,
                    0,
                    0,
                    rawBitmap.width,
                    rawBitmap.height,
                    matrix,
                    true
                )
            } else Bitmap.createBitmap(rawBitmap)

            rawBitmap.recycle()
            emit(Resource.Success(rotatedBitmap))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: context.resources.getString(R.string.unknown_error)
                )
            )
        }
    }
}