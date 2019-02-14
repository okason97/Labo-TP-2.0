package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import android.widget.ImageView
import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory
import java.io.IOException
import android.graphics.drawable.AnimationDrawable
import android.support.v4.content.res.ResourcesCompat


class Game: AppCompatActivity(), DialogExit.ExitDialogListener, DialogVictory.VictoryDialogListener, DialogDefeat.DefeatDialogListener {
    override fun onDefeatDialogPositiveClick(dialog: DialogFragment) {
        count = 0
        victories = 0
        gameMode.newGame()
    }

    override fun onDefeatDialogNegativeClick(dialog: DialogFragment) {
        finish()
    }

    private val sounds : HashMap<String, MediaPlayer> = HashMap()
    var answerViews : ArrayList<View> = ArrayList()
    var answer : Int by Delegates.notNull()
    private lateinit var gameMode : GameMode
    private var count = 0
    private var victories = 0
    private lateinit var sharedPref : SharedPreferences

    override fun onVictoryDialogNegativeClick(dialog: DialogFragment) {
        val editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(getString(R.string.minijuego),
                R.id.razasYPelajes)
        editor.putBoolean(getString(R.string.level), false)
        editor.apply()
        setGameMode()
        gameMode.newGame()
    }

    override fun onVictoryDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    private fun initializeSounds() {
        sounds[getString(R.string.horse_sound_key)] = MediaPlayer.create(this, R.raw.horse_sound)
        sounds[getString(R.string.error_sound_key)] = MediaPlayer.create(this, R.raw.sound_resoplido)
        sounds[getString(R.string.correct_sound_key)] = MediaPlayer.create(this, R.raw.sound_relincho)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)

        initializeSounds()
        newLevel()
    }

    private fun newLevel() {
        count = 0
        victories = 0
        setGameMode()
        gameMode.newGame()
    }

    private fun setGameMode(){
        if (sharedPref.getInt(getString(R.string.minijuego), R.id.razasYPelajes) != R.id.cruzas){
            when(sharedPref.getInt(getString(R.string.interaccion), R.id.imagenPalabra)){
                R.id.imagenPalabra -> {
                    if (!sharedPref.getBoolean(getString(R.string.level), false)){
                        setContentView(R.layout.interaccion_imagen_palabra_level1)
                        addLevelOneAnswers()
                    }else{
                        setContentView(R.layout.interaccion_imagen_palabra_level2)
                        addLevelTwoAnswers()
                    }
                    gameMode = InteraccionA(this)
                }
                R.id.palabraImagen -> {
                    if (!sharedPref.getBoolean(getString(R.string.level), false)){
                        setContentView(R.layout.interaccion_palabra_imagen_level1)
                        addLevelOneAnswers()
                    }else{
                        setContentView(R.layout.interaccion_palabra_imagen_level2)
                        addLevelTwoAnswers()
                    }
                    gameMode = InteraccionB(this)
                }
            }
        }else{
            if (!sharedPref.getBoolean(getString(R.string.level), false)){
                setContentView(R.layout.interaccion_imagen_imagen_level1)
                addLevelOneAnswers()
            }else{
                setContentView(R.layout.interaccion_imagen_imagen_level2)
                addLevelTwoAnswers()
            }
            gameMode = InteraccionC(this)
        }
    }

    private fun addLevelTwoAnswers() {
        answerViews.add(findViewById(R.id.answer1))
        answerViews.add(findViewById(R.id.answer2))
        answerViews.add(findViewById(R.id.answer3))
        answerViews.add(findViewById(R.id.answer4))
    }

    private fun addLevelOneAnswers() {
        answerViews.add(findViewById(R.id.answer1))
        answerViews.add(findViewById(R.id.answer2))
    }

    private fun playSound(str : String) {
        sounds[str]?.start()
    }

    fun selectedAnswer(view: View) {
        println(answer)
        println(view.id)
        println(victories)
        if (view.id == answer) {
            victories++
            playCorrect()
        }else{
            playError()
        }
        count++
        if (count < 5) {
            if (view.id == answer) {
                Handler().postDelayed({
                    gameMode.newGame()
                }, duration(R.string.correct_sound_key))
            }else{
                Handler().postDelayed({
                    gameMode.newGame()
                }, duration(R.string.error_sound_key))
            }
        }else{
            if (victories >= 3) {
                val currentLevel = sharedPref.getInt(getString(R.string.lastLevel), 1)
                val editor: SharedPreferences.Editor
                if (sharedPref.getBoolean(getString(R.string.level), false)) {
                    when (sharedPref.getInt(getString(R.string.minijuego),
                            R.id.razasYPelajes)) {
                        R.id.razasYPelajes -> {
                            editor = sharedPref.edit()
                            editor.putInt(getString(R.string.minijuego),
                                    R.id.razasYPelajesJuntos)
                            editor.putBoolean(getString(R.string.level), false)
                            if (currentLevel < resources.getInteger(R.integer.firstRyPJLevel)) {
                                editor.putInt(getString(R.string.lastLevel), currentLevel + 1)
                            }
                            editor.apply()
                            playNextLevel()
                        }
                        R.id.razasYPelajesJuntos -> {
                            editor = sharedPref.edit()
                            editor.putInt(getString(R.string.minijuego),
                                    R.id.cruzas)
                            editor.putBoolean(getString(R.string.level), false)
                            if (currentLevel < resources.getInteger(R.integer.firstCruzasLevel)) {
                                editor.putInt(getString(R.string.lastLevel), currentLevel + 1)
                            }
                            editor.apply()
                            playNextLevel()
                        }
                        R.id.cruzas -> {
                            playVictory()
                        }
                    }
                } else {
                    editor = sharedPref.edit()
                    editor.putBoolean(getString(R.string.level), true)
                    editor.putInt(getString(R.string.lastLevel), currentLevel + 1)
                    editor.apply()
                    newLevel()
                }
            }else{
                // Create an instance of the dialog fragment and show it
                val dialog : DialogFragment = DialogDefeat()
                dialog.show(supportFragmentManager, "Fin del juego")
            }
        }
    }

    private fun playNextLevel() {
        val img = findViewById<ImageView>(R.id.confeti_anim)
        img.visibility = View.VISIBLE

        // Pass our animation drawable to our custom drawable class
        val anim = object : MyAnimationDrawable(ResourcesCompat.getDrawable(resources, R.drawable.anim_confeti, null)
                as AnimationDrawable) {
            override fun onAnimationFinish() {
                img.visibility = View.GONE
                newLevel()
            }
        }

        //Set AnimationDrawable to ImageView
        if (Build.VERSION.SDK_INT < 16) {
            img.setBackgroundDrawable(anim)
        } else {
            img.background = anim
        }

        img.post { anim.start() }
    }

    private fun victoryDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogVictory()
        dialog.show(supportFragmentManager, "Victoria")
    }

    private fun playVictory() {
        val img = findViewById<ImageView>(R.id.cup_anim)
        img.visibility = View.VISIBLE

        // Pass our animation drawable to our custom drawable class
        val anim = object : MyAnimationDrawable(ResourcesCompat.getDrawable(resources, R.drawable.anim_cup, null)
                as AnimationDrawable) {
            override fun onAnimationFinish() {
                img.visibility = View.GONE
                victoryDialog()
            }
        }

        //Set AnimationDrawable to ImageView
        if (Build.VERSION.SDK_INT < 16) {
            img.setBackgroundDrawable(anim)
        } else {
            img.background = anim
        }

        img.post { anim.start() }
    }

    private fun duration(key : Int): Long {
        val sound : MediaPlayer? = sounds[getString(key)]
        return if (sound != null) {
            System.out.println("sound exists")
            sound.duration.toLong()
        } else
            0
    }

    private fun playError() = playSound(getString(R.string.error_sound_key))

    private fun playCorrect() = playSound(getString(R.string.correct_sound_key))

    fun goBack(view: View?){
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogExit()
        dialog.show(supportFragmentManager, "Salir")
    }
}