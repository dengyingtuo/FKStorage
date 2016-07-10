/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.joker.storage.core.processor.desc;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.vdian.vdds.engine.executor.Session;
import com.vdian.vdds.engine.executor.SqlExecResult;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;
import com.vdian.vdds.engine.model.SqlObject;
import com.vdian.vdds.engine.router.IRouter;
import com.vdian.vdds.engine.router.model.RouteResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * <p>功能描述：处理desc tableName命令</p>
 */
public class DescTableProcessor extends BaseProcessor {

    public DescTableProcessor(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) {
        super(sqlStatement, parameterMap, conn);
    }

    @Override
    public SqlExecResult execute(Session session, IRouter route) throws Exception {
        getRouteResult(route);

        SqlExecResult sqlExecResult = session.execute(routeResult, false);

        sqlExecResult.setProcessor(this);
        return sqlExecResult;
    }

    @Override
    public RouteResult getRouteResult(IRouter route) {
        routeResult = route.getOnePhyTable(sqlObject);
        return routeResult;
    }

    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return -1;
    }

    @Override
    public boolean getMoreResults() {
        return true;
    }
}
