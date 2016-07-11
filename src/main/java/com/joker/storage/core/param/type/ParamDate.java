package com.joker.storage.core.param.type;

import com.joker.storage.core.param.ParamType;
import com.joker.storage.core.param.Parameter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParamDate implements Parameter {
    public Date value;

    public ParamDate(Date value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public ParamType getType() {
        return ParamType.DATE;
    }

    @Override
    public void add(PreparedStatement ps, int index) throws SQLException {
        ps.setDate(index, value);
    }
}
