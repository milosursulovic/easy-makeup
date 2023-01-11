package com.example.easymakeup.util

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface BitmapParser {
    fun parse(): Flow<Resource<Bitmap>>
}