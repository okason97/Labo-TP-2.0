package com.rr.razasypelajes.Horses;

import android.support.annotation.NonNull;

import com.rr.razasypelajes.Helpers.JSONBuildable;

import org.json.JSONException;
import org.json.JSONObject;

public class Padres extends JSONBuildable {
    private String img;
    private String cruza;

    public Padres(JSONObject json) {
        super(json);
    }

    public String getImg() {
        return img;
    }

    public String getCruza() {
        return cruza;
    }

    @NonNull
    @Override
    public String toString() {
        return "Padres{" +
                "img='" + img + '\'' +
                ", cruza='" + cruza + '\'' +
                '}';
    }

    @Override
    protected void fromJSON(JSONObject json) throws JSONException {
        this.img = json.getString("image");
        this.cruza = json.getString("cruza");
    }
}
