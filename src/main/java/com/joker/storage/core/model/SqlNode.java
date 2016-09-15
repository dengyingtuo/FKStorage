package com.joker.storage.core.model;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.joker.storage.core.execute.ExecuteNode;
import com.joker.storage.core.param.ParamMap;

import java.util.ArrayList;
import java.util.List;

public class SqlNode {
    private String sql;
    private ParamMap paramMap;
    private List<SQLStatement> sqlStatements;

    private List<ExecuteNode> children = new ArrayList<ExecuteNode>();

    public SqlNode(String sql, ParamMap paramMap) {
        this.sql = sql;
        this.paramMap = paramMap;
    }

    public void addChild(ExecuteNode e) { children.add(e); }
    public List<ExecuteNode> getChildren() { return children; }
    public List<SQLStatement> getSqlStatements() { return sqlStatements; }
}
