package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParamFloat implements Parameter {
    public float value;

    public ParamFloat(float value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public ParamType getType() {
        return ParamType.FLOAT;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setFloat(index, value);
    }
}
