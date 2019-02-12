package com.rr.razasypelajes;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.rr.razasypelajes.Helpers.JSONHelper;
import com.rr.razasypelajes.horses.Horse;
import com.rr.razasypelajes.horses.Padres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cruzas extends Activity {
    private Map<String, MediaPlayer> sounds;
    private ArrayList<ImageView> horsesViews;
    ImageView question;
    int answer;
    List<Horse> horses;
    List<Padres> parents;

    private void initializeSounds() {
        sounds = new HashMap<>();
        sounds.put(getString(R.string.horse_sound_key), MediaPlayer.create(this, R.raw.horse_sound));
        sounds.put(getString(R.string.error_sound_key), MediaPlayer.create(this, R.raw.error_sound));
        sounds.put(getString(R.string.correct_sound_key), MediaPlayer.create(this, R.raw.success_sound));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSounds();
        setContentView(R.layout.interaccion_c);
        horsesViews = new ArrayList<>();
        horsesViews.add((ImageView) findViewById(R.id.horseImg1));
        horsesViews.add((ImageView) findViewById(R.id.horseImg2));
        horsesViews.add((ImageView) findViewById(R.id.horseImg3));
        horsesViews.add((ImageView) findViewById(R.id.horseImg4));
        question = findViewById(R.id.mainHorseImg);

        Resources resources = getResources();

        horses = JSONHelper.fromJSON(Horse.class, resources.openRawResource(R.raw.horses));
        parents = JSONHelper.fromJSON(Padres.class, resources.openRawResource(R.raw.padres));

        newGame();
    }

    private void newGame(){
        ArrayList<String> parentsOfAnswer = new ArrayList<>();
        int answerIndex = 0;
        Random r = new Random();
        int id;
        while (parentsOfAnswer.size() == 0){
            Collections.shuffle(horses);

            answerIndex = r.nextInt(horsesViews.size());
            for (int i = 0; i < parents.size(); i++) {
                if (parents.get(i).getCruza().equals(horses.get(answerIndex).getRaza()))
                    parentsOfAnswer.add(parents.get(i).getImg());
            }
        }
        for (int i = 0; i < horsesViews.size(); i++) {
            id = getResources().getIdentifier(horses.get(i).getImg(), "drawable", getPackageName());
            horsesViews.get(i).setImageResource(id);
        }
        answer = horsesViews.get(answerIndex).getId();
        int parentAnswerIndex = r.nextInt(parentsOfAnswer.size());
        id = getResources().getIdentifier(parentsOfAnswer.get(parentAnswerIndex), "drawable", getPackageName());
        question.setImageResource(id);
    }

    private void playSound(String str) {
        MediaPlayer sound = sounds.get(str);
        if (sound != null) sound.start();
    }

    public void selectedAnswer(View view){
        if (view.getId() == answer){
            playCorrect();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newGame();
                }
            }, 1000);
        }else{
            playError();
        }
    }

    public void playError() {
        playSound(getString(R.string.error_sound_key));
    }

    public void playHorse(View view) {
        playSound(getString(R.string.horse_sound_key));
    }

    public void playCorrect() {
        playSound(getString(R.string.correct_sound_key));
    }

    public void goBack(View view){
        finish();
    }
}
