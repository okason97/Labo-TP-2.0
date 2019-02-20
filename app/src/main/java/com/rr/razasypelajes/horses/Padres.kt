package com.rr.razasypelajes.horses

import com.rr.razasypelajes.helpers.JSONBuildable
import com.rr.razasypelajes.helpers.JSONHelper
import org.json.JSONArray

import org.json.JSONException
import org.json.JSONObject

class Padres(json: JSONObject) : JSONBuildable(json) {
    var name: String? = null
        private set
    var img: String? = null
        private set
    var parents: List<String>? = null
        private set

    @Throws(JSONException::class)
    override fun fromJSON(json: JSONObject) {
        this.name = json.getString("name")
        this.img = json.getString("img")
        this.parents = JSONHelper.convertJsonArray(json.getJSONArray("parents"))
    }

    override fun toString(): String {
        return "Padres(name=$name, img=$img, parents=$parents)"
    }
}
