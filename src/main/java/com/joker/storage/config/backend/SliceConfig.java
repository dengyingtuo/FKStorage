package com.joker.storage.config.backend;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.joker.storage.config.BaseConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SliceConfig extends BaseConfig {
    public String sliceName;
    public SliceNodeConfig writer;
    public List<SliceNodeConfig> reader = new ArrayList<SliceNodeConfig>();

    public static final String WRITER_STR = "writer";
    public static final String READER_STR = "reader";
    public SliceConfig(String sliceName, String jsonStr) throws SQLException {
        super(jsonStr);

        this.sliceName = sliceName;
        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
        this.writer = new SliceNodeConfig(obj.get(WRITER_STR).toString());

        JsonArray array = obj.get(READER_STR).getAsJsonArray();
        for(JsonElement element : array) {
            reader.add(new SliceNodeConfig(element.toString()));
        }
    }


    public class SliceNodeConfig extends BaseConfig {
        public Map<String, String> config;

        public SliceNodeConfig(String jsonStr) throws SQLException {
            super(jsonStr);

            JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
            config = JsonUtils.getMap(obj);
        }
    }
}
