/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.joker.storage.core.processor.show;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.vdian.vdds.engine.executor.Session;
import com.vdian.vdds.engine.executor.SqlExecResult;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;
import com.vdian.vdds.engine.resultset.InternalResultSet;
import com.vdian.vdds.engine.router.IRouter;
import com.vdian.vdds.engine.router.model.RouteResult;
import com.vdian.vdds.rule.model.LogicTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>功能描述：处理desc tableName命令</p>
 */
public class ShowTablesProcessor extends BaseProcessor {

    public ShowTablesProcessor(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) {
        super(sqlStatement, parameterMap, conn);
    }

    private static final String COLUMNE_NAME = "TABLE_NAME";

    @Override
    public RouteResult getRouteResult(IRouter route) {
        routeResult = route.getDefaultDB(sqlObject);
        return routeResult;
    }

    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
		Map<String, LogicTable> logicTableMap = sqlObject.getConn().getRule().getTableMap();

        List<String> columnList = new ArrayList<String>();
        columnList.add(COLUMNE_NAME);

        //将逻辑表中的表名加入
        InternalResultSet irs = new InternalResultSet(columnList);
        for(String ltName : logicTableMap.keySet()) {
            irs.addRow();
            irs.addRowData(ltName);
        }

        //将默认db中的物理表名加上
        ResultSet defaultDBRs = routeResult.getLastAddRouteItem().getLast_insert_actual_sql().getResultSet();
        while(defaultDBRs.next()) {
            String ptName = defaultDBRs.getString(1);
            for(String ltName : logicTableMap.keySet()) {
                if(!isInLogicTable(logicTableMap.get(ltName), routeResult.getLastAddSliceName(), ptName)) {
                    irs.addRowData(ptName);
                }
            }
        }

		return irs;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return -1;
    }

    @Override
    public boolean getMoreResults() {
        return true;
    }

    private boolean isInLogicTable(LogicTable logicTable, String defaultSliceName, String ptName) {
        if(logicTable.isSharding()) {
            if(defaultSliceName.equals(logicTable.getSingleDbSliceID()) &&
                    ptName.equals(logicTable.getLogicTableName())) {
                return true;
            } else {
                return false;
            }
        } else {
            if(logicTable.getTopo().containsKey(defaultSliceName) &&
                    logicTable.getTopo().get(defaultSliceName).contains(ptName)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
