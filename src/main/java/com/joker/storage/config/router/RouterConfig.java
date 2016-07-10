package com.joker.storage.config.router;

import com.google.gson.JsonObject;
import com.joker.storage.config.BaseConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RouterConfig extends BaseConfig {
    public Map<String, LTableConfig> lTableConfigMap = new HashMap<String, LTableConfig>();

    public RouterConfig(String jsonStr) throws SQLException {
        super(jsonStr);

        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
        Map<String, String> m = JsonUtils.getMap(obj);

        for(String ltName : m.keySet()) {
            lTableConfigMap.put(ltName, new LTableConfig(ltName, m.get(ltName)));
        }
    }
}
