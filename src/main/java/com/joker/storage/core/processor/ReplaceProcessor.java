/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.vdian.vdds.engine.processor;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.vdian.vdds.engine.executor.Session;
import com.vdian.vdds.engine.executor.SqlExecResult;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;
import com.vdian.vdds.engine.model.SqlObject;
import com.vdian.vdds.engine.router.IRouter;
import com.vdian.vdds.engine.router.model.ActualSql;
import com.vdian.vdds.engine.router.model.RouteResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>功能描述：处理Replace命令</p>
 */
public class ReplaceProcessor extends BaseProcessor{
    public ReplaceProcessor(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) {
        super(sqlStatement, parameterMap, conn);
    }

    @Override
    public boolean isUpdate() {
        return true;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults() {
        return false;
    }
}
