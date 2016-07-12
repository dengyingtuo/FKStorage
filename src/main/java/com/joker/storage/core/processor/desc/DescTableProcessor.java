package com.joker.storage.core.processor.desc;

import com.joker.storage.core.processor.BaseProcessor;
import java.sql.SQLException;

/**
 * <p>功能描述：处理desc tableName命令</p>
 */
public class DescTableProcessor extends BaseProcessor {

    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return -1;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return true;
    }
}
