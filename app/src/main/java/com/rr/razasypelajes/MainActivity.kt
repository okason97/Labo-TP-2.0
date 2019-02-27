package com.rr.razasypelajes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity(), DialogAbout.Listener {
    override fun onAboutDialogPositiveClick(dialog: DialogFragment) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio);
    }

    fun toConfig(view: View) {
        val intent = Intent(this, ConfigActivity::class.java)
        startActivity(intent)
    }

    fun onJugar(view: View) {
        val intent = Intent(this, Game::class.java)
        startActivity(intent)
    }

    fun onRecon(view: View) {
        val intent = Intent(this, Reconocimiento::class.java)
        startActivity(intent)
    }

    fun onAbout(view: View?){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

}