package com.rr.razasypelajes.horses

import com.rr.razasypelajes.helpers.JSONBuildable

import org.json.JSONException
import org.json.JSONObject

class Padres(json: JSONObject) : JSONBuildable(json) {
    var img: String? = null
        private set
    var cruza: String? = null
        private set

    override fun toString(): String {
        return "Padres{" +
                "img='" + img + '\''.toString() +
                ", cruza='" + cruza + '\''.toString() +
                '}'.toString()
    }

    @Throws(JSONException::class)
    override fun fromJSON(json: JSONObject) {
        this.img = json.getString("image")
        this.cruza = json.getString("cruza")
    }
}
