/**
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 微店(口袋购物) </p>
 *
 * @author zhonghua@weidian.com
 */
package com.joker.storage.core.processor.select;

import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.vdian.vdds.engine.jdbc.DistributedConnection;
import com.vdian.vdds.engine.model.Parameter;

import java.sql.SQLException;
import java.util.Map;

/**
 * <p>功能描述：处理select命令</p>
 */
public class SelectProcessor extends BaseProcessor{

    public SelectProcessor(SQLStatement sqlStatement, Map<Integer, Parameter> parameterMap, DistributedConnection conn) {
        super(sqlStatement, parameterMap, conn);
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
