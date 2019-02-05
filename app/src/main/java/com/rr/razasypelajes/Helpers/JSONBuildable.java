package com.rr.razasypelajes.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONBuildable {
    public JSONBuildable(JSONObject json) {
        try {
            fromJSON(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected abstract void fromJSON(JSONObject json) throws JSONException;
}
