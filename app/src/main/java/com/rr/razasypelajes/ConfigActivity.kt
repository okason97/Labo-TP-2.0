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
import java.util.HashSet

class ConfigActivity : AppCompatActivity(), ExitDialog.ExitDialogListener {
    var preferences = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE) as SharedPreferences
    var editor = preferences.edit() as SharedPreferences.Editor

    // Recon
    var reconRadio = findViewById<RadioGroup>(R.id.settingsReconGroup)
    var reconRyP = findViewById<CheckBox>(R.id.settingsReconRyP)
    var reconcruza = findViewById<CheckBox>(R.id.settingsReconCruza)

    // Minigame
    var minigameRadio = findViewById<RadioGroup>(R.id.settingsMinigameGroup)

    // Dificulty
    var dificultySwitch = findViewById<Switch>(R.id.settingsDificultySwitch)

    // Audio
    var audioSwitch = findViewById<Switch>(R.id.settingsAudioSwitch)

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
        reconRadio.check(preferences.getInt("reconRadio", R.id.lista))
        reconRyP.isChecked = preferences.getBoolean("reconRyP", false)
        reconcruza.isChecked = preferences.getBoolean("reconRyP", false)
        if (!(reconRyP.isChecked && reconcruza.isChecked)) reconRyP.isChecked = true

        // Minigame
        minigameRadio.check(preferences.getInt("minigameRadio", R.id.rypImagenPalabra))

        // Dificulty
        dificultySwitch.isChecked = preferences.getBoolean("dificultySwitch", false)

        // Audio
        audioSwitch.isChecked = preferences.getBoolean("audioSwitch", false)
    }

    private fun saveValues() {

    }
}