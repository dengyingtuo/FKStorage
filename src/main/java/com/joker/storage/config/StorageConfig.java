package com.joker.storage.config;

import com.google.gson.JsonObject;
import com.joker.storage.config.backend.BackendConfig;
import com.joker.storage.config.router.RouterConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.Map;

public class StorageConfig extends BaseConfig {
    public BackendConfig backendConfig;
    public RouterConfig routerConfig;

    public static final String BACKEND_STR = "backend";
    public static final String ROUTER_STR = "router";
    public StorageConfig(String jsonStr) throws SQLException {
        super(jsonStr);

        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();
        Map<String, String> m = JsonUtils.getMap(obj);
        backendConfig = new BackendConfig(m.get(BACKEND_STR));
        routerConfig  = new RouterConfig(m.get(ROUTER_STR));
    }
}
