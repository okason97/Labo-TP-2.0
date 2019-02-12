package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import android.widget.ImageView



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
    var answerViews : ArrayList<ConstraintLayout> = ArrayList()
    var answer : Int by Delegates.notNull()
    private lateinit var gameMode : GameMode
    private var count = 0
    private var victories = 0
    private lateinit var sharedPref : SharedPreferences

    override fun onVictoryDialogNegativeClick(dialog: DialogFragment) {
        val editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(getString(R.string.minijuego),
                R.id.rypImagenPalabra)
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
        sounds[getString(R.string.error_sound_key)] = MediaPlayer.create(this, R.raw.error_sound)
        sounds[getString(R.string.correct_sound_key)] = MediaPlayer.create(this, R.raw.success_sound)
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
        when(sharedPref.getInt(getString(R.string.modo_interaccion), R.id.rypImagenPalabra)){
            R.id.rypImagenPalabra -> {
                if (sharedPref.getBoolean(getString(R.string.level), false)){
                    addLevelOneAnswers()
                    setContentView(R.layout.interaccion_imagen_palabra_level1)
                }else{
                    addLevelTwoAnswers()
                    setContentView(R.layout.interaccion_imagen_palabra_level2)
                }
                gameMode = InteraccionA(this)
            }
            R.id.rypPalabraImagen -> {
                if (sharedPref.getBoolean(getString(R.string.level), false)){
                    addLevelOneAnswers()
                    setContentView(R.layout.interaccion_palabra_imagen_level1)
                }else{
                    addLevelTwoAnswers()
                    setContentView(R.layout.interaccion_palabra_imagen_level2)
                }
                gameMode = InteraccionB(this)
            }
            R.id.cruzas -> {
                if (sharedPref.getBoolean(getString(R.string.level), false)){
                    addLevelOneAnswers()
                    setContentView(R.layout.interaccion_imagen_imagen_level1)
                }else{
                    addLevelTwoAnswers()
                    setContentView(R.layout.interaccion_imagen_imagen_level2)
                }
                gameMode = InteraccionC(this)
            }
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
        if (view.id == answer) {
            victories++
            playCorrect()
            if (count < 5) {
                Handler().postDelayed({
                    gameMode.newGame()
                }, correctDuration())
            }else{
                val currentLevel = sharedPref.getInt(getString(R.string.lastLevel),1)
                if (victories >= 3){
                    val editor : SharedPreferences.Editor
                    if (sharedPref.getBoolean(getString(R.string.level), false)){
                        when (sharedPref.getInt(getString(R.string.minijuego),
                                R.id.rypImagenPalabra)){
                            R.id.rypImagenPalabra -> {
                                playNextLevel()
                                Handler().postDelayed({
                                    findViewById<ImageView>(R.id.confeti_anim)
                                            .visibility = View.GONE
                                    setGameMode()
                                    gameMode.newGame()
                                }, nextLevelDuration())
                                editor = sharedPref.edit()
                                editor.putInt(getString(R.string.minijuego),
                                        R.id.rypPalabraImagen)
                                editor.putBoolean(getString(R.string.level), false)
                                if (currentLevel < resources.getInteger(R.integer.firstRyPJLevel)){
                                    editor.putInt(getString(R.string.lastLevel), currentLevel+1)
                                }
                                editor.apply()
                            }
                            R.id.rypPalabraImagen -> {
                                playNextLevel()
                                Handler().postDelayed({
                                    findViewById<ImageView>(R.id.confeti_anim)
                                            .visibility = View.GONE
                                    setGameMode()
                                    gameMode.newGame()
                                }, nextLevelDuration())
                                editor = sharedPref.edit()
                                editor.putInt(getString(R.string.minijuego),
                                        R.id.cruzas)
                                editor.putBoolean(getString(R.string.level), false)
                                if (currentLevel < resources.getInteger(R.integer.firstCruzasLevel)){
                                    editor.putInt(getString(R.string.lastLevel), currentLevel+1)
                                }
                                editor.apply()
                            }
                            R.id.cruzas -> {
                                playVictory()
                                Handler().postDelayed({
                                    findViewById<ImageView>(R.id.cup_anim)
                                            .visibility = View.GONE
                                    victoryDialog()
                                }, victoryDuration())
                            }
                        }
                    }else{
                        editor = sharedPref.edit()
                        editor.putBoolean(getString(R.string.level), true)
                        editor.putInt(getString(R.string.lastLevel), currentLevel+1)
                        editor.apply()
                    }
                }
            }
        } else {
            restartError()
            playError()
            if (count < 5) {
                Handler().postDelayed({
                    gameMode.newGame()
                }, correctDuration())
            }else{
                // Create an instance of the dialog fragment and show it
                val dialog : DialogFragment = DialogDefeat()
                dialog.show(supportFragmentManager, "Fin del juego")
            }
        }
        count++
    }

    private fun nextLevelDuration(): Long {
        val frameDuration : Long = resources.getInteger(R.integer.confettiFrameDuration).toLong()
        val framesSize : Long = resources.getInteger(R.integer.confettiFramesSize).toLong()
        return frameDuration*framesSize
    }

    private fun playNextLevel() {
        val img = findViewById<ImageView>(R.id.confeti_anim)
        val anim : AnimationDrawable = img.drawable as AnimationDrawable
        img.visibility = View.VISIBLE
        img.post { anim.start() }
    }

    private fun victoryDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog : DialogFragment = DialogVictory()
        dialog.show(supportFragmentManager, "Victoria")
    }

    private fun victoryDuration(): Long {
        val frameDuration : Long = resources.getInteger(R.integer.cupFrameDuration).toLong()
        val framesSize : Long = resources.getInteger(R.integer.cupFramesSize).toLong()
        return frameDuration*framesSize
    }

    private fun playVictory() {
        val img = findViewById<ImageView>(R.id.cup_anim)
        val anim = img.drawable as AnimationDrawable
        img.visibility = View.VISIBLE
        img.post { anim.start() }
    }

    private fun restartError() {
        val sound : MediaPlayer ?= sounds[getString(R.string.error_sound_key)]
        if (sound != null) {
            sound.stop()
            sound.release()
        }
        sounds[getString(R.string.error_sound_key)] = MediaPlayer.create(this, R.raw.error_sound)
    }

    private fun correctDuration(): Long {
        val sound : MediaPlayer? = sounds[getString(R.string.correct_sound_key)]
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