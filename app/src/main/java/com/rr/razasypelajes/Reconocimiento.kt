package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.Horse

class Reconocimiento : AppCompatActivity(), DialogExit.ExitDialogListener {
    private lateinit var mode : ReconMode

    val horses : ArrayList<Horse> = ArrayList()
    val sounds : ArrayList<MediaPlayer> = ArrayList()

    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    private fun loadRazasYPelajes() {
        val hCol = JSONHelper.fromJSON(Horse::class.java, resources.openRawResource(R.raw.horses))
        horses.addAll(hCol)
        for (h in hCol) {
            try {
                val id = resources.getIdentifier("sound_" + h.raza, "raw", packageName)
                sounds.add(MediaPlayer.create(this, id))
            } catch (e: Exception) { println("Horse: $h. E: $e") }
        }
    }

    private fun loadCruzas() {
        loadRazasYPelajes()
        // lo mismo pero con .cruza
    }

    private fun initializeHorses(sharedPref: SharedPreferences) {
        val defaultRecon : HashSet<String> = HashSet()
        defaultRecon.add(getString(R.string.reconRyP))

        val gamemodes  = sharedPref.getStringSet(getString(R.string.gameMode), defaultRecon)

        if (gamemodes.contains(getString(R.string.reconRyP))) loadRazasYPelajes()
        if (gamemodes.contains(getString(R.string.reconCruza))) loadCruzas()
    }

    private fun setReconMode(sharedPref: SharedPreferences){
        val viewMode = sharedPref.getInt(getString(R.string.reconRadio), R.id.lista)
        mode = if (viewMode == R.id.lista) ReconLista(this) else ReconGrilla(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)
        initializeHorses(sharedPref)
        setReconMode(sharedPref)
        mode.runRecon()
    }

    fun goBack(view: View?){
        // Create an instance of the dialog fragment and show it
        val dialog = DialogExit() as DialogFragment
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

}