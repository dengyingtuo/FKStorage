package com.joker.storage.core.model;

import com.joker.storage.core.param.ParamMap;

public class SqlNode {
    private String sql;
    private ParamMap paramMap;

    public SqlNode(String sql, ParamMap paramMap) {
        this.sql = sql;
        this.paramMap = paramMap;
    }
}
