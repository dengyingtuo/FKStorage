package com.joker.storage.core.processor.multi;


import com.joker.storage.core.processor.Processor;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BatchProcessor implements Processor {

    @Override
    public void execute() throws SQLException {

    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public boolean isUpdate() throws SQLException {
        return false;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int[] getBatchUpdateCounts() throws SQLException {
        return new int[0];
    }
}
