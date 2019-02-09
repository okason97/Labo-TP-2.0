package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rr.razasypelajes.Horses.Horse

class Reconocimiento : AppCompatActivity() {
    private val sounds : HashMap<String, MediaPlayer> = HashMap()
    private lateinit var mode : ReconMode

    var horses : List<Horse> = ArrayList()

    private fun loadRazasYPelajes(horses : List<Horse>) {
        TODO()
    }

    private fun loadCruzas(horses : List<Horse>) {
        TODO()
    }

    private fun initializeSounds() {
        TODO()
    }

    private fun initializeHorses(sharedPref: SharedPreferences) {
        val defaultRecon : HashSet<String> = HashSet()
        defaultRecon.add(getString(R.string.razas))

        val gamemodes  = sharedPref.getStringSet(getString(R.string.gameMode), defaultRecon)

        if (gamemodes.contains(getString(R.string.razas))) loadRazasYPelajes(horses)
        if (gamemodes.contains(getString(R.string.cruzas))) loadCruzas(horses)
    }

    private fun setReconMode(sharedPref: SharedPreferences){
        val viewMode = sharedPref.getInt(getString(R.string.viewMode), R.id.lista)
        mode = if (viewMode == R.id.lista) ReconLista(this) else ReconGrilla(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)
        initializeSounds()
        initializeHorses(sharedPref)
        setReconMode(sharedPref)
        mode.runRecon()
    }
}