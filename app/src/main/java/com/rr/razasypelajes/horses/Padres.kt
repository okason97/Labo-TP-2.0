package com.rr.razasypelajes.horses

import android.content.Context
import com.rr.razasypelajes.helpers.JSONBuildable
import com.rr.razasypelajes.helpers.JSONHelper
import org.json.JSONArray

import org.json.JSONException
import org.json.JSONObject

class Padres(json: JSONObject) : AbsHorse(json) {
    var parents: List<String>? = null
        private set

    @Throws(JSONException::class)
    override fun fromJSON(json: JSONObject) {
        this.name = json.getString("name")
        this.img = json.getString("img")
        this.parents = JSONHelper.convertJsonArray(json.getJSONArray("parents"))
        this.text = JSONHelper.stringFromJsonArray(json.getJSONArray("text"))
    }

    override fun toString(): String {
        return "Padres(name=$name, img=$img, parents=$parents)"
    }

    fun getParentsImagesIds(context: Context): List<Int> {
        var parentsImagesIds : List<Int> = ArrayList()
        for (parent in parents as List<String>){
            parentsImagesIds += context.resources.getIdentifier(parent, "drawable", context.packageName)
        }
        return parentsImagesIds
    }
}
