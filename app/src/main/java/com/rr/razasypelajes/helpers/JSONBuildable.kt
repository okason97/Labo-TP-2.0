package com.rr.razasypelajes.helpers

import org.json.JSONObject

abstract class JSONBuildable(json: JSONObject) {
    init {
        fromJSON(json)
    }

    protected abstract fun fromJSON(json: JSONObject)
}
