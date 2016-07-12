package com.joker.storage.core.processor.select;

import com.joker.storage.core.processor.BaseProcessor;

import java.sql.SQLException;

public class SelectProcessor extends BaseProcessor {


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
