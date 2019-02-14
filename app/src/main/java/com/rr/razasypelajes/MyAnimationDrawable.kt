package com.rr.razasypelajes

import android.content.Context
import android.graphics.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Matrix.ScaleToFit
import android.support.v4.os.HandlerCompat.postDelayed
import android.graphics.drawable.Drawable
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import java.io.IOException


abstract class MyAnimationDrawable(private val name : String, private val context: Context, private val reqWidth: Int, private val reqHeight: Int) : AnimationDrawable() {
    private var current: Int = 0
    private var totalTime: Int = 0

    override fun addFrame(frame: Drawable, duration: Int) {
        super.addFrame(frame, duration)
        totalTime += duration
    }

    override fun start() {
        super.start()
        Handler().postDelayed({ onAnimationFinish() }, totalTime.toLong())
    }

    override fun draw(canvas: Canvas) {
        try {
            val number = if (current < 10) {
                "0$current"
            }else{
                "$current"
            }
            //Loading image from assets, you could make it from resources
            val id = context.resources.getIdentifier("$name$number.jpg", "drawable", context.packageName)
            var bmp = BitmapFactory.decodeResource(context.resources, id);
            //Scaling image to fitCenter
            val m = Matrix()
            m.setRectToRect(RectF(0f, 0f, bmp.width.toFloat(), bmp.height.toFloat()), RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat()), Matrix.ScaleToFit.CENTER)
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, m, true)
            //Calculating the start 'x' and 'y' to paint the Bitmap
            val x = (reqWidth - bmp.width) / 2
            val y = (reqHeight - bmp.height) / 2
            //Painting Bitmap in canvas
            canvas.drawBitmap(bmp, x.toFloat(), y.toFloat(), null)
            //Jump to next item
            current++
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    internal abstract fun onAnimationFinish()

}