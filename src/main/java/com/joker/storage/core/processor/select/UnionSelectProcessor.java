package com.joker.storage.core.processor.select;

import com.joker.storage.core.processor.BaseProcessor;

import java.sql.SQLException;

public class UnionSelectProcessor extends BaseProcessor {

    @Override
    public boolean isUpdate() throws SQLException {
        return false;
    }
}
