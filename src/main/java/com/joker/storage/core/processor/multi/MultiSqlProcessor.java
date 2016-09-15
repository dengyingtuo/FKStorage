package com.joker.storage.core.processor.multi;


import com.joker.storage.core.processor.Processor;
import com.joker.storage.core.resultset.MultiResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MultiSqlProcessor implements Processor {
    private List<Processor> processors = new ArrayList<Processor>();
    public void  addProcessor(Processor processor) {
        processors.add(processor);
    }

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
