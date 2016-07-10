package com.joker.storage.utils;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class JsonUtils {


    public static Map<String, String> getMap(JsonObject obj) throws SQLException {
        if(obj==null) {
            throw new SQLException("obj is null");
        }

        try {
            Map<String, String> ret = new HashMap<String, String>();
            for(Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                ret.put(entry.getKey(), entry.getValue().toString());
            }

            return ret;
        } catch (Exception e) {
            throw new SQLException("getMap error", e);
        }
    }

    public static JsonElement parseJsonStr(String str) throws SQLException {
        if(str == null) {
            throw new SQLException("param is null");
        }

        try {
            JsonParser parser = new JsonParser();
            JsonReader reader = new JsonReader(new StringReader(str));
            reader.setLenient(true);

            JsonElement root = parser.parse(reader);
            if(root == null) {
                throw new SQLException("json parse get null");
            }

            return root;
        } catch (Exception e) {
            throw new SQLException("json parse error", e);
        }
    }

    public static void processObject(JsonObject obj, String matchKey, ProcessJsonObject pjo) throws SQLException {
        if(obj == null) {
            throw new SQLException("param is null");
        }

        try {
            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                String key = entry.getKey();
                JsonElement element = entry.getValue();

                if (element.isJsonObject()) {
                    processObject(element.getAsJsonObject(), matchKey, pjo);
                }

                if (element.isJsonArray()) {
                    processArray(element.getAsJsonArray(), matchKey, pjo);
                }

                if (element.isJsonPrimitive() && key.equals(matchKey)) {
                    try {
                        pjo.process(obj);
                    } catch (Exception e) {
                        throw new SQLException("process json object error, obj:" + obj.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new SQLException("encodeAllPassword error", e);
        }
    }

    private static void processArray(JsonArray arr, String matchKey, ProcessJsonObject pjo) throws SQLException {
        if(arr == null) {
            throw new SQLException("param is null");
        }

        for (JsonElement a : arr) {
            if (a.isJsonObject()) {
                processObject(a.getAsJsonObject(), matchKey, pjo);
            }

            if (a.isJsonArray()) {
                processArray(a.getAsJsonArray(), matchKey, pjo);
            }
        }
    }


    interface ProcessJsonObject {
        public void process(JsonObject jsonObject);
    }
}
