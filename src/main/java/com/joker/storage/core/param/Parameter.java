package com.joker.storage.core.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Parameter {
    public String getValue();

    public ParamType getType();

    public void add(PreparedStatement ps, int index) throws SQLException;
}
