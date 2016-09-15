package com.joker.storage.core.processor.other;

import com.joker.storage.core.processor.BaseProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteProcessor extends BaseProcessor {

    @Override
    public boolean isUpdate() {
        return true;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

}
