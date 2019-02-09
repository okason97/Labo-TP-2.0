package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rr.razasypelajes.Horses.Horse
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class Game: AppCompatActivity() {
    private val sounds : HashMap<String, MediaPlayer> = HashMap()
    var answerViews : ArrayList<ConstraintLayout> = ArrayList()
    var answer : Int by Delegates.notNull()
    private lateinit var gameMode : GameMode
    private var count = 0
    private var victories = 0

    private fun initializeSounds() {
        sounds[getString(R.string.horse_sound_key)] = MediaPlayer.create(this, R.raw.horse_sound)
        sounds[getString(R.string.error_sound_key)] = MediaPlayer.create(this, R.raw.error_sound)
        sounds[getString(R.string.correct_sound_key)] = MediaPlayer.create(this, R.raw.success_sound)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeSounds()
        val sharedPref = getSharedPreferences(getString(R.string.config), Context.MODE_PRIVATE)
        setGameMode(sharedPref)
        gameMode.newGame()
        count++
    }

    private fun setGameMode(sharedPref : SharedPreferences){
        when(sharedPref.getInt(getString(R.string.modo_interaccion), R.id.interaccionA)){
            R.id.interaccionA -> {
                if (sharedPref.getBoolean(getString(R.string.level), false)){
                    addLevelOneAnswers()
                    setContentView(R.layout.interaccion_imagen_palabra_level1)
                }else{
                    addLevelTwoAnswers()
                    setContentView(R.layout.interaccion_imagen_palabra_level2)
                }
                gameMode = InteraccionA(this)
            }
            R.id.interaccionB -> {
                if (sharedPref.getBoolean(getString(R.string.level), false)){
                    addLevelOneAnswers()
                    setContentView(R.layout.interaccion_palabra_imagen_level1)
                }else{
                    addLevelTwoAnswers()
                    setContentView(R.layout.interaccion_palabra_imagen_level2)
                }
                gameMode = InteraccionB(this)
            }
            R.id.interaccionC -> {
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
        if (count < 5){
            if (view.id == answer) {
                victories++
                playCorrect()
                Handler().postDelayed({
                    gameMode.newGame()
                }, correctDuration())
            } else {
                restartError()
                playError()
            }
            count++
        }else{

            goBack(null)
        }
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

    private fun playHorse(view: View) = playSound(getString(R.string.horse_sound_key))

    private fun playCorrect() = playSound(getString(R.string.correct_sound_key))

    private fun goBack(view: View?) = finish()
}