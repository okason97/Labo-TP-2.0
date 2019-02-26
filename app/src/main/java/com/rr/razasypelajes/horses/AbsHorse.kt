package com.rr.razasypelajes.horses

import android.content.Context
import com.rr.razasypelajes.helpers.JSONBuildable
import org.json.JSONObject

abstract class AbsHorse(json: JSONObject) : JSONBuildable(json) {
    var name: String? = null
        protected set
    var img: String? = null
        protected set
    var text: String? = null
        protected set

    fun prettyName() : String? {
        return name?.replace("_", " ")?.capitalize()
    }

    fun getImageId(context: Context): Int {
        return context.resources.getIdentifier(img, "drawable", context.packageName)
    }

    abstract fun getReconSound(): String?
    abstract fun getReconText(): String?
}