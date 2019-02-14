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

    private lateinit var preferences : SharedPreferences

    // Recon
    private lateinit var reconRadio : RadioGroup
    private lateinit var reconRyP : CheckBox
    private lateinit var reconCruza: CheckBox

    // Minigame
    private lateinit var interactionRadio : RadioGroup

    // Dificulty
    private lateinit var dificultySwitch: Switch
    private lateinit var dificultyText : TextView

    // Audio
    private lateinit var audioSwitch : Switch

    fun goBack(view: View?){
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogExitConfig()
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    private fun initValues() {
        preferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)

        reconRadio = findViewById(R.id.settingsReconGroup)
        reconRyP = findViewById(R.id.settingsReconRyP)
        reconCruza = findViewById(R.id.settingsReconCruza)

        // Minigame
        interactionRadio = findViewById(R.id.settingsInteractionGroup)

        // Dificulty
        dificultySwitch = findViewById(R.id.settingsDificultySwitch)
        dificultyText = findViewById(R.id.settingsDificultyText)

        // Audio
        audioSwitch = findViewById(R.id.settingsAudioSwitch)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings3)

        initValues()

        // Load from preferences
        loadValues()
    }

    private fun loadValues() {
        // Recon
        reconRadio.check(preferences.getInt(getString(R.string.reconRadio), R.id.lista))

        reconRyP.isChecked = preferences.getBoolean(getString(R.string.reconRyP), false)
        reconCruza.isChecked = preferences.getBoolean(getString(R.string.reconCruza), false)

        if (!reconRyP.isChecked && !reconCruza.isChecked) reconRyP.isChecked = true

        // Interaction
        if (preferences.getInt(getString(R.string.minijuego), R.id.razasYPelajes) != R.id.cruzas){
            interactionRadio.check(preferences.getInt(getString(R.string.interaccion), R.id.imagenPalabra))
        }else{
            interactionRadio.check(R.id.imagenImagen)
        }

        // Dificulty
        dificultySwitch.isChecked = preferences.getBoolean(getString(R.string.level), false)

        // Audio
        audioSwitch.isChecked = preferences.getBoolean(getString(R.string.audioSwitch), false)

        val currentMaxLevel = preferences.getInt(getString(R.string.lastLevel), 1)
        if(currentMaxLevel.rem(2) == 0){
            dificultySwitch.isEnabled = true
            dificultySwitch.isClickable = true
            dificultyText.isEnabled = true
        }else{
            dificultySwitch.isEnabled = false
            dificultySwitch.isClickable = false
            dificultyText.isEnabled = false
        }
    }

    private fun saveValues() {
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(getString(R.string.audioSwitch), audioSwitch.isChecked)
        editor.putBoolean(getString(R.string.level), dificultySwitch.isChecked)

        if (preferences.getInt(getString(R.string.minijuego), R.id.razasYPelajes) != R.id.cruzas){
            editor.putInt(getString(R.string.interaccion), interactionRadio.checkedRadioButtonId)
        }

        editor.putInt(getString(R.string.reconRadio), reconRadio.checkedRadioButtonId)

        if (!reconRyP.isChecked && !reconCruza.isChecked) reconRyP.isChecked = true

        editor.putBoolean(getString(R.string.reconRyP), reconRyP.isChecked)
        editor.putBoolean(getString(R.string.reconCruza), reconCruza.isChecked)
        editor.apply()
    }

}