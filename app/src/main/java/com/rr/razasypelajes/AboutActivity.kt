package com.rr.razasypelajes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class AboutActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.about)
    }

    fun goBack(view: View) {
        finish()
    }
}