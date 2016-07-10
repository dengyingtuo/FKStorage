package com.joker.storage.config.router;

import com.google.gson.JsonObject;
import com.joker.storage.config.BaseConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.Map;

public class LTableConfig extends BaseConfig {
    public String tableName;
    public Map<String, String> mapConfig;

    public LTableConfig(String tablename, String jsonStr) throws SQLException {
        super(jsonStr);

        this.tableName = tablename;

        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
        mapConfig = JsonUtils.getMap(obj);
    }
}
