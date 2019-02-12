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
            for (i in 0..arr.length()) {
                val current = arr.getJSONObject(i)
                val constructor = tClass.getDeclaredConstructor(JSONObject::class.java) as Constructor<T>
                list.add(constructor.newInstance(current))
            }
            return list
        }
    }
}