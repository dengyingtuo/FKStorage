package com.joker.storage.config;

import com.google.gson.JsonElement;
import com.joker.storage.utils.JsonUtils;

import java.sql.SQLException;

public class BaseConfig {
    public String jsonStr;

    public BaseConfig(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getJsonStr() { return this.jsonStr; }

    @Override
    public String toString() {
        return jsonStr;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof BaseConfig)) {
            return false;
        }

        try {
            JsonElement e1 = JsonUtils.parseJsonStr(jsonStr);
            JsonElement e2 = JsonUtils.parseJsonStr(((BaseConfig) obj).jsonStr);

            return e1.equals(e2);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
