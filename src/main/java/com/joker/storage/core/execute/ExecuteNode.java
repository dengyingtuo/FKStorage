package com.joker.storage.core.execute;

import java.sql.ResultSet;

public class ExecuteNode {


    private ResultSet rs;
    private int updateCount;
    private long insertId;


    public ResultSet getRs() { return rs; }
    public int getUpdateCount() { return updateCount; }
    public long getInsertId() { return insertId; }
}
