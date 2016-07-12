package com.joker.storage.core.processor;

import com.alibaba.cobar.parser.model.SyntaxResult;
import com.joker.storage.core.execute.ExecuteNode;
import com.joker.storage.core.execute.ExecuteSet;
import com.joker.storage.core.model.SqlNode;
import com.joker.storage.core.resultset.MultiResultSet;
import com.joker.storage.route.RouteResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseProcessor implements Processor{
    protected SqlNode sqlNode;
    protected SyntaxResult syntaxResult = null;
    protected RouteResult routeResult = null;
    protected ExecuteSet executeSet = null;

    private boolean isfinished = false;

    public BaseProcessor() {}
    public BaseProcessor(SqlNode sqlNode) {
        this.sqlNode = sqlNode;
    }

    @Override
    public void execute() throws SQLException {
//        getRouteResult(route);
//
//        ExecuteNode sqlExecResult = session.execute(routeResult, true);
//        sqlExecResult.setProcessor(this);
//
//        return sqlExecResult;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        checkFinished();
        return new MultiResultSet(sqlNode);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        checkFinished();

        int updateCount = 0;
        for(ExecuteNode node : sqlNode.getChildren()) {
            updateCount+=node.getUpdateCount();
        }
        return updateCount;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        checkFinished();

        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        checkFinished();

        return null;
    }

    @Override
    public int[] getBatchUpdateCounts() throws SQLException {
        checkFinished();

        List<ExecuteNode> children = sqlNode.getChildren();

        int[] updateCounts = new int[children.size()];
        for(int i=0; i<children.size(); i++) {
            updateCounts[i] = children.get(i).getUpdateCount();
        }
        return updateCounts;
    }

    protected void checkFinished() throws SQLException {
        if (!isfinished) {
            throw new SQLException("processor - have not finished");
        }
    }

    @Override
    abstract public boolean isUpdate() throws SQLException;
}
