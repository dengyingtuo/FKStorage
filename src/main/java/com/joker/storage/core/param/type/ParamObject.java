package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParamObject implements Parameter {
    public Object value;

    public ParamObject(Object value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public ParamType getType() {
        return ParamType.OBJECT;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setObject(index, value);
    }
}
