package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.AbsHorse
import com.rr.razasypelajes.horses.Horse
import com.rr.razasypelajes.horses.Padres

class Reconocimiento : AppCompatActivity(), DialogExit.ExitDialogListener {
    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    private lateinit var mode : ReconMode

    val horses : ArrayList<AbsHorse> = ArrayList()
    val sounds : ArrayList<MediaPlayer> = ArrayList()
    lateinit var gender : String

    private fun loadRazasYPelajes() {
        val hCol = JSONHelper.fromJSON(Horse::class.java, resources.openRawResource(R.raw.horses))
        horses.addAll(hCol)
        for (h in hCol) {
            try {
                val id = resources.getIdentifier("sound_" + gender + h.name?.toLowerCase() , "raw", packageName)
                sounds.add(MediaPlayer.create(this, id))
            } catch (e: Exception) {
                sounds.add(MediaPlayer.create(this, R.raw.sound_resoplido))
            }
        }
    }

    private fun loadCruzas() {
        val pCol = JSONHelper.fromJSON(Padres::class.java, resources.openRawResource(R.raw.padres))
        horses.addAll(pCol)
        for (p in pCol) {
            try {
                val id = resources.getIdentifier("sound_" + gender + p.name?.toLowerCase(), "raw", packageName)
                sounds.add(MediaPlayer.create(this, id))
            } catch (e: Exception) {
                sounds.add(MediaPlayer.create(this, R.raw.sound_resoplido))
            }
        }
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

    private fun initGender(sharedPref: SharedPreferences) {
        val gender = sharedPref.getBoolean(getString(R.string.audioSwitch), false)
        this.gender = if (gender) "fem_" else "mas_"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref : SharedPreferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)

        initGender(sharedPref)
        initializeHorses(sharedPref)
        setReconMode(sharedPref)

        mode.runRecon()
    }

    fun goBack(view: View) {
        finish()
    }

    fun enlarge(view: View) {
        mode.enlarge(view as ImageView)
    }

    fun unEnlarge(view: View) {
        view.visibility = View.GONE
    }

}