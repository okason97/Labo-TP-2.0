package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
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
import android.graphics.Bitmap
import java.io.IOException


class Game: AppCompatActivity(), DialogExit.ExitDialogListener, DialogVictory.VictoryDialogListener, DialogDefeat.DefeatDialogListener {
    override fun onDefeatDialogPositiveClick(dialog: DialogFragment) {
        count = 0
        victories = 1
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
    private var victories = 1
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
        setGameMode()
        gameMode.newGame()
        count++
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
                            playNextLevel()
                            editor = sharedPref.edit()
                            editor.putInt(getString(R.string.minijuego),
                                    R.id.razasYPelajesJuntos)
                            editor.putBoolean(getString(R.string.level), false)
                            if (currentLevel < resources.getInteger(R.integer.firstRyPJLevel)) {
                                editor.putInt(getString(R.string.lastLevel), currentLevel + 1)
                            }
                            editor.apply()
                        }
                        R.id.razasYPelajesJuntos -> {
                            playNextLevel()
                            editor = sharedPref.edit()
                            editor.putInt(getString(R.string.minijuego),
                                    R.id.cruzas)
                            editor.putBoolean(getString(R.string.level), false)
                            if (currentLevel < resources.getInteger(R.integer.firstCruzasLevel)) {
                                editor.putInt(getString(R.string.lastLevel), currentLevel + 1)
                            }
                            editor.apply()
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
                }
            }else{
                // Create an instance of the dialog fragment and show it
                val dialog : DialogFragment = DialogDefeat()
                dialog.show(supportFragmentManager, "Fin del juego")
            }
        }
        count++
    }

    private fun playNextLevel() {
        val img = findViewById<ImageView>(R.id.confeti_anim)
        img.visibility = View.VISIBLE

        val animation = object : MyAnimationDrawable(getString(R.string.confetiDrawableName),this, img.width, img.height, 8) {

            override fun onAnimationFinish() {
                img.visibility = View.GONE
                setGameMode()
                gameMode.newGame()
            }

        }
        animation.isOneShot = true

        try {
            //Always load same bitmap, anyway you load the right one in draw() method in MyAnimationDrawable
            val id = resources.getIdentifier("${getString(R.string.confetiDrawableName)}00.jpg", "drawable", packageName)
            val bmp = BitmapFactory.decodeResource(resources, id);
            for (i in 0..resources.getInteger(R.integer.confettiFramesSize)) {
                animation.addFrame(BitmapDrawable(resources, bmp), resources.getInteger(R.integer.confettiFrameDuration))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Set AnimationDrawable to ImageView
        if (Build.VERSION.SDK_INT < 16) {
            img.setBackgroundDrawable(animation);
        } else {
            img.background = animation;
        }

        img.post { animation.start() }
    }

    private fun victoryDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogVictory()
        dialog.show(supportFragmentManager, "Victoria")
    }

    private fun playVictory() {
        val img = findViewById<ImageView>(R.id.cup_anim)
        img.visibility = View.VISIBLE

        val animation = object : MyAnimationDrawable(getString(R.string.cupDrawableName),this, img.width, img.height, 1) {

            override fun onAnimationFinish() {
                img.visibility = View.GONE
                victoryDialog()
            }

        }
        animation.isOneShot = true

        try {
            //Always load same bitmap, anyway you load the right one in draw() method in MyAnimationDrawable
            val id = resources.getIdentifier("${getString(R.string.cupDrawableName)}00.jpg", "drawable", packageName)
            val bmp = BitmapFactory.decodeResource(resources, id);
            for (i in 0..resources.getInteger(R.integer.cupFramesSize)) {
                animation.addFrame(BitmapDrawable(resources, bmp), resources.getInteger(R.integer.cupFrameDuration))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Set AnimationDrawable to ImageView
        if (Build.VERSION.SDK_INT < 16) {
            img.setBackgroundDrawable(animation);
        } else {
            img.background = animation;
        }

        img.post { animation.start() }

    }

    private fun restartError() {
        val sound : MediaPlayer ?= sounds[getString(R.string.error_sound_key)]
        if (sound != null) {
            sound.stop()
            sound.release()
        }
        sounds[getString(R.string.error_sound_key)] = MediaPlayer.create(this, R.raw.sound_resoplido)
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