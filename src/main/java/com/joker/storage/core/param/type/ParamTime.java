package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class ParamTime implements Parameter {
    public Time value;

    public ParamTime(Time value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public ParamType getType() {
        return ParamType.TIME;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setTime(index, value);
    }
}
