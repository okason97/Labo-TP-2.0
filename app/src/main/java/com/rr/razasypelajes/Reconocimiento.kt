package com.rr.razasypelajes

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.rr.razasypelajes.Horses.Horse

class Reconocimiento : AppCompatActivity() {
    private lateinit var mode : ReconMode

    fun newRecon(context: ReconMode) {
    }

    private fun loadRazasYPelajes(horses : List<Horse>) {
        TODO()
    }

    private fun loadCruzas(horses : List<Horse>) {
        TODO()
    }

    private fun setReconMode(sharedPref: SharedPreferences){
        val reconHorses : List<Horse> = ArrayList()

        val defaultRecon : HashSet<String> = HashSet()
        defaultRecon.add(getString(R.string.razas))

        val gamemodes  = sharedPref.getStringSet(getString(R.string.gameMode), defaultRecon)

        if (gamemodes.contains(getString(R.string.razas))) loadRazasYPelajes(reconHorses)
        if (gamemodes.contains(getString(R.string.cruzas))) loadCruzas(reconHorses)

        mode = if (sharedPref.getInt(getString(R.string.viewMode), R.id.lista) == R.id.lista) ReconLista(this) else ReconGrilla(this)
    }
}