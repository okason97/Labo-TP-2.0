package com.rr.razasypelajes.Horses;

import android.support.annotation.NonNull;

import com.rr.razasypelajes.Helpers.JSONBuildable;

import org.json.JSONException;
import org.json.JSONObject;

public class Horse extends JSONBuildable {
    private String raza;
    private String pelaje;
    private String img;

    public Horse(JSONObject json) {
        super(json);
    }

    public String getRaza() {
        return raza;
    }

    public String getPelaje() {
        return pelaje;
    }

    public String getImg() {
        return img;
    }

    @NonNull
    @Override
    public String toString() {
        return "Horse{" +
                "raza='" + raza + '\'' +
                ", pelaje='" + pelaje + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    protected void fromJSON(JSONObject json) throws JSONException {
        this.raza = json.getString("raza");
        this.pelaje = json.getString("pelaje");
        this.img = json.getString("image");
    }
}
