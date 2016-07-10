/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.vdian.vdds.engine.processor;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.alibaba.cobar.parser.ast.stmt.dal.ShowTables;
import com.vdian.vdds.common.exception.DdasException;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;
import com.vdian.vdds.engine.model.SqlObject;
import com.vdian.vdds.engine.model.SqlType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>功能描述：根据SqlStatement返回对应的处理器</p>
 */
public class ProcessorFactory {
    public static Processor makeProcessor(List<SqlObject> sqlObjectList) throws SQLException {
        if(sqlObjectList==null || sqlObjectList.size()==0) {
            throw new DdasException("sqlObjectList is empty");
        }

        if(sqlObjectList.size()>1) {
            //batch TODO
            return null;
        }

        return makeProcess(sqlObjectList.get(0));
    }

    private static Processor makeProcess(SqlObject sqlObject) throws SQLException {
        if(sqlObject==null) {
            throw new DdasException("sqlObject is null");
        }

        if(sqlObject.getSqlStatements().size()>1) {
            List<Processor> processors = new ArrayList<Processor>();
            for(SQLStatement sqlStatement : sqlObject.getSqlStatements()) {
                processors.add(makeProcess(sqlStatement, sqlObject.getParameterMap(), sqlObject.getConn()));
            }

            return new MultiSqlProcessor(processors, sqlObject.getConn());
        }

        return makeProcess(sqlObject.getSqlStatement(), sqlObject.getParameterMap(), sqlObject.getConn());
    }

    private static Processor makeProcess(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) throws SQLException {
        SqlType sqlType = SqlType.valueOfSQLStatement(sqlStatement);
        switch (sqlType) {
			case INSERT:
                return new InsertProcessor(sqlStatement, parameterMap, conn);

			case SELECT:
                return new SelectProcessor(sqlStatement, parameterMap, conn);

			case UPDATE:
                return new UpdateProcessor(sqlStatement, parameterMap, conn);

			case DELETE:
                return new DeleteProcessor(sqlStatement, parameterMap, conn);

			case REPLACE:
                return new ReplaceProcessor(sqlStatement, parameterMap, conn);

			case SHOW:
                return makeShowProcess(sqlStatement, parameterMap, conn);

            case DESC:
                return new DescTableProcessor(sqlStatement, parameterMap, conn);

            default:
                throw new SQLException("Unknonw sql type:" + sqlType.toString());

        }
    }

    private static Processor makeShowProcess(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) throws SQLException {
        if(sqlStatement instanceof ShowTables) {
            return new ShowTablesProcessor(sqlStatement, parameterMap, conn);
        }

        throw new SQLException("unSupport show sql:" + sqlStatement.getClass().getName());
    }
}
