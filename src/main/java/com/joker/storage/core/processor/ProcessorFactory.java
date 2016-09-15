package com.joker.storage.core.processor;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.alibaba.cobar.parser.ast.stmt.dal.ShowTables;
import com.joker.storage.core.model.SqlNode;
import com.joker.storage.core.model.SqlType;
import com.joker.storage.core.processor.desc.DescTableProcessor;
import com.joker.storage.core.processor.multi.BatchProcessor;
import com.joker.storage.core.processor.multi.MultiSqlProcessor;
import com.joker.storage.core.processor.other.*;
import com.joker.storage.core.processor.select.SelectProcessor;
import com.joker.storage.core.processor.select.UnionSelectProcessor;
import com.joker.storage.core.processor.show.ShowTablesProcessor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProcessorFactory {
    public static Processor makeProcessor(List<SqlNode> nodes) throws SQLException {
        if(nodes==null || nodes.size()==0) {
            throw new SQLException("sql node list is empty");
        }

        if(nodes.size()>1) {
            return new BatchProcessor();
        }

        return makeProcess(nodes.get(0));
    }

    private static Processor makeProcess(SqlNode node) throws SQLException {
        if(node==null || node.getSqlStatements()==null || node.getSqlStatements().size()<=0) {
            throw new SQLException("sql node is null");
        }

        if(node.getSqlStatements().size()>1) {
            MultiSqlProcessor processor = new MultiSqlProcessor();

            for(SQLStatement sqlStatement : node.getSqlStatements()) {
                processor.addProcessor(makeProcess(sqlStatement));
            }

            return processor;
        }

        return makeProcess(node.getSqlStatements().get(0));
    }

    private static Processor makeProcess(SQLStatement sqlStatement) throws SQLException {
        SqlType sqlType = SqlType.valueOfSQLStatement(sqlStatement);
        switch (sqlType) {
			case INSERT:
                return new InsertProcessor();

			case SELECT:
                return new SelectProcessor();

			case UPDATE:
                return new UpdateProcessor();

			case DELETE:
                return new DeleteProcessor();

			case REPLACE:
                return new ReplaceProcessor();

			case SHOW:
                return makeShowProcess(sqlStatement);

            case DESC:
                return new DescTableProcessor();

            case UNION_SELECT:
                return new UnionSelectProcessor();

            case OTHER:
                return new OtherProcessor();

            default:
                throw new SQLException("Unknonw sql type:" + sqlType.toString());

        }
    }

    private static Processor makeShowProcess(SQLStatement sqlStatement) throws SQLException {
        if(sqlStatement instanceof ShowTables) {
            return new ShowTablesProcessor();
        }

        throw new SQLException("unSupport show sql:" + sqlStatement.getClass().getName());
    }
}
