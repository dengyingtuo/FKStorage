package com.joker.storage.core.processor;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface Processor {
    void execute( ) throws SQLException;

    ResultSet getResultSet() throws SQLException;

    boolean isUpdate() throws SQLException;

    int getUpdateCount() throws SQLException;

    boolean getMoreResults(int current) throws SQLException;

    ResultSet getGeneratedKeys() throws SQLException;

    int[] getBatchUpdateCounts() throws SQLException;
}
