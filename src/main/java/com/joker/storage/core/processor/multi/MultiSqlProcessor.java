/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.joker.storage.core.processor.multi;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.vdian.vdds.engine.executor.Session;
import com.vdian.vdds.engine.executor.SqlExecResult;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;
import com.vdian.vdds.engine.router.IRouter;
import com.vdian.vdds.engine.router.model.RouteResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>功能描述：处理一次传入一个sql字符串,其中有多个以分号分开的sql语句
 *      eg select * from tableA; select * from tableB;
 * </p>
 */
public class MultiSqlProcessor implements Processor{
    private DistributedConnection conn;
    private List<Processor> processors;

    private RouteResult routeResult;     /* 路由结果 */

    private int index = 0;

    public MultiSqlProcessor(List<Processor> processors, DistributedConnection conn) {
        this.conn = conn;
        this.processors = processors;
    }

    @Override
    public SqlExecResult execute(Session session, IRouter route) throws Exception {
        getRouteResult(route);

        SqlExecResult sqlExecResult = session.execute(routeResult, true);
        sqlExecResult.setProcessor(this);

        return sqlExecResult;
    }


    @Override
    public RouteResult getRouteResult(IRouter route) throws SQLException {
        routeResult = new RouteResult();
        for(Processor processor : processors) {
            routeResult.addRouteResult(processor.getRouteResult(route));
        }
        return routeResult;
    }

    @Override
    public boolean isUpdate() {
        return processors.get(0).isUpdate();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        if(index>=processors.size()) {
            return null;
        } else {
            return processors.get(index).getResultSet();
        }
    }

    @Override
    public int getUpdateCount() throws SQLException {
        if(index>=processors.size()) {
            return -1;
        } else {
            return processors.get(index).getUpdateCount();
        }
    }

    @Override
    public boolean getMoreResults() throws Exception {
        index++;
        if(index>=processors.size()) {
            return false;
        }

        return processors.get(index).getMoreResults();
    }
}
