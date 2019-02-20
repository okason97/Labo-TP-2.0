package com.rr.razasypelajes.helpers

import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.reflect.Constructor

class JSONHelper {
    companion object {
        private fun getJSON(inputStream: InputStream): JSONArray {
            val writer = StringWriter() as Writer
            val buffer = CharArray(1024)
            val reader = BufferedReader(InputStreamReader(inputStream)) as Reader
            var n = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
            inputStream.close()
            return JSONArray(writer.toString())
        }

        fun <T : JSONBuildable> fromJSON(tClass: Class<T>, inputStream: InputStream): List<T> {
            val list = ArrayList<T>()
            val arr = getJSON(inputStream)
            for (i in 0 until arr.length()) {
                val current = arr.getJSONObject(i)
                val constructor = tClass.getDeclaredConstructor(JSONObject::class.java) as Constructor<T>
                list.add(constructor.newInstance(current))
            }
            return list
        }

        fun convertJsonArray(jsonArray: JSONArray) : List<String> {
            val list = ArrayList<String>()
            for (i in 0 until jsonArray.length()) list.add(jsonArray[i].toString())
            return list
        }

        fun stringFromJsonArray(jsonArray: JSONArray) : String {
            val sb = StringBuilder()
            for (i in 0 until jsonArray.length() - 1) sb.append(jsonArray[i]).append("\n")
            sb.append(jsonArray[jsonArray.length() - 1])
            return sb.toString()
        }
    }
}
