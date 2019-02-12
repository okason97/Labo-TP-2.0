package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Switch

class ConfigActivity : AppCompatActivity(), ExitDialog.ExitDialogListener {
    private val preferences : SharedPreferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)

    // Recon
    private val reconRadio : RadioGroup = findViewById(R.id.settingsReconGroup)
    private val reconRyP : CheckBox = findViewById(R.id.settingsReconRyP)
    private val reconCruza: CheckBox = findViewById(R.id.settingsReconCruza)

    // Minigame
    private val minigameRadio : RadioGroup = findViewById(R.id.settingsMinigameGroup)

    // Dificulty
    private val dificultySwitch: Switch = findViewById(R.id.settingsDificultySwitch)

    // Audio
    private val audioSwitch : Switch = findViewById(R.id.settingsAudioSwitch)

    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    fun goBack(view: View?){
        // Save preferences
        saveValues()

        // Create an instance of the dialog fragment and show it
        val dialog = ExitDialog() as DialogFragment
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Load from preferences
        loadValues()
    }

    private fun loadValues() {
        // Recon
        reconRadio.check(preferences.getInt(getString(R.string.reconRadio),
                R.id.lista))
        reconRyP.isChecked = preferences.getBoolean(getString(R.string.reconRyP),
                false)
        reconCruza.isChecked = preferences.getBoolean(getString(R.string.reconRyP),
                false)
        if (!(reconRyP.isChecked && reconCruza.isChecked)) reconRyP.isChecked = true

        // Minigame
        minigameRadio.check(preferences.getInt(getString(R.string.minijuego),
                R.id.rypImagenPalabra))

        // Dificulty
        dificultySwitch.isChecked = preferences.getBoolean(
                getString(R.string.dificultySwitch), false)

        // Audio
        audioSwitch.isChecked = preferences.getBoolean(getString(R.string.audioSwitch),
                false)

        val currentMaxLevel = preferences.getInt(getString(R.string.lastLevel), 1)
        when(preferences.getInt(getString(R.string.minijuego), R.id.razasYPelajes)){
            R.id.rypImagenPalabra -> {
                if(currentMaxLevel > resources.getInteger(
                                R.integer.firstRyPImagenPalabraLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }
            }
            R.id.rypPalabraImagen -> {
                if(currentMaxLevel > resources.getInteger(
                                R.integer.firstRyPPalabraImagenLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }
            }
            R.id.cruzas -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstCruzasLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }
            }
        }
    }

    private fun saveValues() {
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.apply()
    }

    fun onChangeMinigame(view : View){
        val currentMaxLevel = preferences.getInt(getString(R.string.lastLevel), 1)
        when(view.id){
            R.id.rypImagenPalabra -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstRyPImagenPalabraLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                }
            }
            R.id.rypPalabraImagen -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstRyPPalabraImagenLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                }
            }
            R.id.cruzas -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstCruzasLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                }
            }
        }
    }


}