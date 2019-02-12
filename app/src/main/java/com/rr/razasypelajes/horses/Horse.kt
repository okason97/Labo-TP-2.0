package com.rr.razasypelajes.horses

import com.rr.razasypelajes.helpers.JSONBuildable

import org.json.JSONException
import org.json.JSONObject

class Horse(json: JSONObject) : JSONBuildable(json) {
    var raza: String? = null
        private set
    var pelaje: String? = null
        private set
    var img: String? = null
        private set

    override fun toString(): String {
        return "Horse{" +
                "raza='" + raza + '\''.toString() +
                ", pelaje='" + pelaje + '\''.toString() +
                ", img='" + img + '\''.toString() +
                '}'.toString()
    }

    @Throws(JSONException::class)
    override fun fromJSON(json: JSONObject) {
        this.raza = json.getString("raza")
        this.pelaje = json.getString("pelaje")
        this.img = json.getString("image")
    }
}
