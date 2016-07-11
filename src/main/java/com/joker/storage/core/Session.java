package com.joker.storage.core;

import com.joker.storage.core.model.SqlNode;
import com.joker.storage.core.processor.Processor;
import com.joker.storage.core.statement.FKStatement;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Session {
    private FKConnection connection;
    private Set<FKStatement> statements = new HashSet<FKStatement>();

    private boolean autoCommit = true;
    private boolean closed = false;

    public Processor execute(List<SqlNode> sqlNodes) throws SQLException {
        throw new SQLException("not support");
    }


    public void commit() throws SQLException {

    }

    public void rollback() throws SQLException {

    }

    public void release() throws SQLException {
        throw new SQLException("not support");
    }

    public void close() throws SQLException {
        closed = true;
        throw new SQLException();
    }

    public void setAutoCommit(boolean autoCommit) { this.autoCommit = autoCommit; }
    public boolean autoCommit() { return autoCommit; }
    public void setConnection(FKConnection conn) { this.connection = conn; }
    public FKConnection getConnection() { return connection; }
    public boolean isClosed() { return closed; }
    public void delStatement(FKStatement statement) { statements.remove(statement); }
    public void addStatement(FKStatement statement) { statements.add(statement); }
}
