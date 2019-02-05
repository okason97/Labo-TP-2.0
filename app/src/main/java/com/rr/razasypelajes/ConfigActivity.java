package com.rr.razasypelajes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigActivity extends AppCompatActivity {
    Switch levelSwitch;
    Switch sexSwitch;
    RadioGroup minijuegoRadio;
    RadioGroup viewModeRadio;
    RadioGroup modoInteraccionRadio;
    HashMap<String, CheckBox> gameModeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameModeCheckBox = new HashMap<>();
        setContentView(R.layout.settings);

        setValues();
    }

    private void setValues(){

        modoInteraccionRadio = (RadioGroup) findViewById(R.id.ModoInteraccion);
        levelSwitch = findViewById(R.id.Level);
        sexSwitch = findViewById(R.id.Sex);
        minijuegoRadio = findViewById(R.id.Minijuego);
        viewModeRadio = findViewById(R.id.ViewMode);
        gameModeCheckBox.put(getString(R.string.razas), (CheckBox)findViewById(R.id.RazasCheckbox));
        gameModeCheckBox.put(getString(R.string.pelajes), (CheckBox)findViewById(R.id.PelajesCheckbox));
        gameModeCheckBox.put(getString(R.string.cruzas), (CheckBox)findViewById(R.id.CruzasCheckbox));

        SharedPreferences preferences = getSharedPreferences(getString(R.string.config),Context.MODE_PRIVATE);
        levelSwitch.setChecked(preferences
                .getBoolean(getString(R.string.level), false));
        sexSwitch.setChecked(preferences
                .getBoolean(getString(R.string.sex), false));
        minijuegoRadio.check(preferences
                .getInt(getString(R.string.minijuego), R.id.razasYPelajes));
        viewModeRadio.check(preferences
                .getInt(getString(R.string.viewMode), R.id.lista));
        modoInteraccionRadio.check(preferences
                .getInt(getString(R.string.modo_interaccion), R.id.interaccionB));

        for (CheckBox checkBox : gameModeCheckBox.values()) checkBox.setChecked(false);

        Set<String> gameModes = preferences
                .getStringSet(getString(R.string.gameMode), new HashSet<String>());

        if (gameModes != null) for (String checkBoxKey : gameModes) {
            CheckBox ck = gameModeCheckBox.get(checkBoxKey);
            if (ck != null) ck.setChecked(true);
        }
    }

    public void onAccept(View view) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.config),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.sex), sexSwitch.isChecked());
        editor.putBoolean(getString(R.string.level), levelSwitch.isChecked());
        editor.putInt(getString(R.string.minijuego), minijuegoRadio.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.viewMode), viewModeRadio.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.modo_interaccion), modoInteraccionRadio.getCheckedRadioButtonId());
        HashSet<String> selectedGameModes = new HashSet<>();
        for (Map.Entry<String, CheckBox> entry : gameModeCheckBox.entrySet())
            if (entry.getValue().isChecked()) selectedGameModes.add(entry.getKey());
        editor.putStringSet(getString(R.string.gameMode), selectedGameModes);
        editor.apply();
        finish();
    }

}
