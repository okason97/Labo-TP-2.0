package com.rr.razasypelajes.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    private static JSONArray getJSON(InputStream is) throws IOException, JSONException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Reader reader = new BufferedReader(new InputStreamReader(is));
        int n;
        while ((n = reader.read(buffer)) != -1) writer.write(buffer, 0, n);
        is.close();
        return new JSONArray(writer.toString());
    }

    public static <T extends JSONBuildable> List<T> fromJSON(Class<T> tClass, InputStream is) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray arr = getJSON(is);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject current = arr.getJSONObject(i);
                Constructor<T> constructor = tClass.getDeclaredConstructor(JSONObject.class);
                list.add(constructor.newInstance(current));
            }
        } catch (IOException | JSONException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }
}
