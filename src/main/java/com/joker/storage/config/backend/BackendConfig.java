package com.joker.storage.config.backend;

import com.google.gson.JsonObject;
import com.joker.storage.config.BaseConfig;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BackendConfig extends BaseConfig {
    public Map<String, NodeConfig> nodeConfigMap = new HashMap<String, NodeConfig>();
    public Map<String, SliceConfig> sliceConfigMap = new HashMap<String, SliceConfig>();

    public static final String NODE_STR = "node";
    public static final String SLICE_STR = "slice";
    public BackendConfig(String jsonStr) throws SQLException {
        super(jsonStr);

        JsonObject obj = JsonUtils.parseJsonStr(jsonStr).getAsJsonObject();

        Map<String, String> m;

        m = JsonUtils.getMap(obj.getAsJsonObject(NODE_STR));
        for(String nodeName : m.keySet()) {
            nodeConfigMap.put(nodeName, new NodeConfig(nodeName, m.get(nodeName)));
        }

        m = JsonUtils.getMap(obj.getAsJsonObject(SLICE_STR));
        for(String sliceName : m.keySet()) {
            sliceConfigMap.put(sliceName, new SliceConfig(sliceName, m.get(sliceName)));
        }
    }
}
