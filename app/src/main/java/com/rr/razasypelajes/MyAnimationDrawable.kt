package com.rr.razasypelajes

import android.graphics.drawable.Drawable
import android.graphics.drawable.AnimationDrawable
import android.os.Handler


abstract class MyAnimationDrawable(aniDrawable: AnimationDrawable): AnimationDrawable() {
    private var totalTime: Int = 0

    init {
        /* Add each frame to our animation drawable */
        for (i in 0 until aniDrawable.numberOfFrames) {
            this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i))
        }
    }

    override fun addFrame(frame: Drawable, duration: Int) {
        super.addFrame(frame, duration)
        totalTime += duration
    }

    override fun start() {
        super.start()
        Handler().postDelayed({ onAnimationFinish() }, totalTime.toLong())
    }

    internal abstract fun onAnimationFinish()

}