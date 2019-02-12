package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*

class ConfigActivity : AppCompatActivity(), DialogExitConfig.ExitConfigDialogListener {
    override fun onExitConfigDialogPositiveClick(dialog: DialogFragment) {
        // Save preferences
        saveValues()

        finish()
    }

    override fun onExitConfigDialogNeutralClick(dialog: DialogFragment) {
        finish()
    }

    private val preferences : SharedPreferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)

    // Recon
    private val reconRadio : RadioGroup = findViewById(R.id.settingsReconGroup)
    private val reconRyP : CheckBox = findViewById(R.id.settingsReconRyP)
    private val reconCruza: CheckBox = findViewById(R.id.settingsReconCruza)

    // Minigame
    private val minigameRadio : RadioGroup = findViewById(R.id.settingsMinigameGroup)

    // Dificulty
    private val dificultySwitch: Switch = findViewById(R.id.settingsDificultySwitch)
    private val dificultyText : TextView = findViewById(R.id.settingsDificultyText)

    // Audio
    private val audioSwitch : Switch = findViewById(R.id.settingsAudioSwitch)

    fun goBack(view: View?){
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogExit()
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
        reconCruza.isChecked = preferences.getBoolean(getString(R.string.reconCruza),
                false)

        if (!(reconRyP.isChecked && reconCruza.isChecked)) reconRyP.isChecked = true

        // Minigame
        minigameRadio.check(preferences.getInt(getString(R.string.minijuego),
                R.id.rypImagenPalabra))

        // Dificulty
        dificultySwitch.isChecked = preferences.getBoolean(
                getString(R.string.level), false)

        // Audio
        audioSwitch.isChecked = preferences.getBoolean(getString(R.string.audioSwitch),
                false)

        val currentMaxLevel = preferences.getInt(getString(R.string.lastLevel), 1)
        when(preferences.getInt(getString(R.string.minijuego), R.id.rypImagenPalabra)){
            R.id.rypImagenPalabra -> {
                if(currentMaxLevel > resources.getInteger(
                                R.integer.firstRyPImagenPalabraLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }
            }
            R.id.rypPalabraImagen -> {
                if(currentMaxLevel > resources.getInteger(
                                R.integer.firstRyPPalabraImagenLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }
            }
            R.id.cruzas -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstCruzasLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }
            }
        }
        if(currentMaxLevel > resources.getInteger(R.integer.firstCruzasLevel)){
            minigameRadio.findViewById<RadioButton>(R.id.cruzas).isEnabled = true
            minigameRadio.findViewById<RadioButton>(R.id.cruzas).isClickable = true
        }else if(currentMaxLevel > resources.getInteger(
                    R.integer.firstRyPPalabraImagenLevel)){
            minigameRadio.findViewById<RadioButton>(R.id.rypPalabraImagen).isEnabled = true
            minigameRadio.findViewById<RadioButton>(R.id.rypPalabraImagen).isClickable = true
        }
    }

    private fun saveValues() {
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(getString(R.string.audioSwitch), audioSwitch.isChecked)
        editor.putBoolean(getString(R.string.level), dificultySwitch.isChecked)
        editor.putInt(getString(R.string.minijuego), minigameRadio.checkedRadioButtonId)
        editor.putInt(getString(R.string.reconRadio), reconRadio.checkedRadioButtonId)
        editor.putBoolean(getString(R.string.reconRyP), reconRyP.isChecked)
        editor.putBoolean(getString(R.string.reconCruza), reconCruza.isChecked)
        editor.apply()
    }

    fun onChangeMinigame(view : View){
        val currentMaxLevel = preferences.getInt(getString(R.string.lastLevel), 1)
        when(view.id){
            R.id.rypImagenPalabra -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstRyPImagenPalabraLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                    dificultyText.isEnabled = false
                }
            }
            R.id.rypPalabraImagen -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstRyPPalabraImagenLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                    dificultyText.isEnabled = false
                }
            }
            R.id.cruzas -> {
                if(currentMaxLevel > resources.getInteger(R.integer.firstCruzasLevel)){
                    dificultySwitch.isEnabled = true
                    dificultySwitch.isClickable = true
                    dificultyText.isEnabled = true
                }else{
                    dificultySwitch.isEnabled = false
                    dificultySwitch.isClickable = false
                    dificultyText.isEnabled = false
                }
            }
        }
    }
}