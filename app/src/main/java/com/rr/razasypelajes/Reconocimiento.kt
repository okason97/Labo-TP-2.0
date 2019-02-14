package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CheckBox
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.Horse

class Reconocimiento : AppCompatActivity(), DialogExit.ExitDialogListener {
    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    private lateinit var mode : ReconMode

    val horses : ArrayList<Horse> = ArrayList()
    val sounds : ArrayList<MediaPlayer> = ArrayList()

    private fun loadRazasYPelajes() {
        val hCol = JSONHelper.fromJSON(Horse::class.java, resources.openRawResource(R.raw.horses))
        horses.addAll(hCol)
        for (h in hCol) {
            try {
                val id = resources.getIdentifier("sound_" + h.raza + "_" + h.pelaje, "raw", packageName)
                sounds.add(MediaPlayer.create(this, id))
            } catch (e: Exception) {
                sounds.add(MediaPlayer.create(this, R.raw.sound_relincho))
            }
        }
    }

    private fun loadCruzas() {
        loadRazasYPelajes()
        // lo mismo pero con .cruza
    }

    private fun initializeHorses(sharedPref: SharedPreferences) {
        var ryp = sharedPref.getBoolean(getString(R.string.reconRyP), false)
        val cruzas = sharedPref.getBoolean(getString(R.string.reconCruza), false)

        if (!ryp && !cruzas) ryp = true

        if (ryp) loadRazasYPelajes()
        if (cruzas) loadCruzas()
    }

    private fun setReconMode(sharedPref: SharedPreferences){
        val viewMode = sharedPref.getInt(getString(R.string.reconRadio), R.id.lista)
        mode = if (viewMode == R.id.lista) ReconLista(this) else ReconGrilla(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref : SharedPreferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)
        initializeHorses(sharedPref)
        setReconMode(sharedPref)
        mode.runRecon()
    }

    fun goBack(view: View) {
        finish()
    }

}