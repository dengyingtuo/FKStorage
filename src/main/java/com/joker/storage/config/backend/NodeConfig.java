package com.joker.storage.config.backend;

import com.google.gson.JsonObject;
import com.joker.storage.config.BaseConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.Map;

public class NodeConfig extends BaseConfig {
    public String nodeName;
    public Map<String, String> config;

    public NodeConfig(String jsonStr, String nodeName) throws SQLException {
        super(jsonStr);

        this.nodeName = nodeName;
        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
        this.config = JsonUtils.getMap(obj);
    }
}
