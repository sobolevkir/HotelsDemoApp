package com.sobolevkir.hotels.presentation.screen.hotel_details.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import coil.size.Size
import coil.transform.Transformation

class CropBordersTransform : Transformation {

    override val cacheKey: String = "CropBordersTransform"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {

        val width = input.width
        val height = input.height

        val config = input.config ?: Bitmap.Config.ARGB_8888
        val croppedBitmap = Bitmap.createBitmap(width - 2, height - 2, config)
        val paint = Paint().apply { isAntiAlias = true }
        val srcRect = Rect(1, 1, width - 1, height - 1)
        val destRect = Rect(0, 0, width - 2, height - 2)
        Canvas(croppedBitmap).drawBitmap(input, srcRect, destRect, paint)

        return croppedBitmap
    }
}