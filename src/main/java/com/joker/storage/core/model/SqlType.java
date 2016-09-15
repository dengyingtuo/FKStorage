package com.joker.storage.core.model;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.alibaba.cobar.parser.ast.stmt.dal.DALShowStatement;
import com.alibaba.cobar.parser.ast.stmt.ddl.DescTableStatement;
import com.alibaba.cobar.parser.ast.stmt.dml.*;

public enum SqlType {
    INSERT(0, "insert"), SELECT(1, "select"), UNION_SELECT(2, "union_select"), UPDATE(3, "update"), DELETE(4, "delete"),
    REPLACE(5, "replace"), SHOW(6, "show"), DESC(7, "desc"), OTHER(8, "other");

    private int i;
    private String s;

    SqlType(int i, String s) {
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }

    public static SqlType valueOfSQLStatement(SQLStatement sqlStatement) {
        if(sqlStatement instanceof DMLInsertReplaceStatement) {
            return INSERT;
        }

        if(sqlStatement instanceof DMLSelectStatement) {
            return SELECT;
        }

        if(sqlStatement instanceof DMLSelectUnionStatement) {
            return UNION_SELECT;
        }

        if(sqlStatement instanceof DMLUpdateStatement) {
            return UPDATE;
        }

        if(sqlStatement instanceof DMLDeleteStatement) {
            return DELETE;
        }

        if(sqlStatement instanceof DMLReplaceStatement) {
            return REPLACE;
        }

        if(sqlStatement instanceof DescTableStatement) {
            return DESC;
        }

        if(sqlStatement instanceof DALShowStatement) {
            return SHOW;
        }

        return OTHER;
    }
}
