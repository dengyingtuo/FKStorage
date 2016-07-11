package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ParamTimeStamp implements Parameter {
    public Timestamp value;

    public ParamTimeStamp(Timestamp value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public ParamType getType() {
        return ParamType.TIMESTAMP;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setTimestamp(index, value);
    }
}
