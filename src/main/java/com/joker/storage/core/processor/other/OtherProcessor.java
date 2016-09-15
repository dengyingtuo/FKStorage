package com.joker.storage.core.processor.other;

import com.joker.storage.core.processor.BaseProcessor;

import java.sql.SQLException;


public class OtherProcessor extends BaseProcessor {
    @Override
    public boolean isUpdate() throws SQLException {
        return false;
    }
}
