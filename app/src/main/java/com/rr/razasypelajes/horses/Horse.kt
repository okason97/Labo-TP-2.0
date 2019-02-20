package com.rr.razasypelajes.horses

import android.content.Context
import com.rr.razasypelajes.helpers.JSONBuildable
import com.rr.razasypelajes.helpers.JSONHelper

import org.json.JSONException
import org.json.JSONObject

class Horse(json: JSONObject) : JSONBuildable(json) {
    var raza: String? = null
        private set
    var pelaje: String? = null
        private set
    var img: String? = null
        private set
    var name: String? = null
        private set
    var text: String? = null
        private set


    @Throws(JSONException::class)
    override fun fromJSON(json: JSONObject) {
        this.raza = json.getString("raza")
        this.pelaje = json.getString("pelaje")
        this.img = json.getString("image")
        this.name = json.getString("name")
        this.text = JSONHelper.stringFromJsonArray(json.getJSONArray("text"))
    }

    fun prettyRaza() : String? {
        return raza?.replace("_", " ")?.capitalize()
    }

    fun prettyPelaje() : String? {
        return pelaje?.replace("_", " ")?.capitalize()
    }

    fun prettyBoth() : String? {
        return prettyRaza() + " - " + prettyPelaje()
    }

    fun getImageId(context: Context): Int {
        return context.resources.getIdentifier(img, "drawable", context.packageName)
    }

    override fun toString(): String {
        return "Horse(raza=$raza, pelaje=$pelaje, img=$img, name=$name, text=$text)"
    }
}
