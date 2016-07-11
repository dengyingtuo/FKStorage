package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParamString implements Parameter {
    public String value;

    public ParamString(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ParamType getType() {
        return ParamType.STRING;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setString(index, value);
    }
}
