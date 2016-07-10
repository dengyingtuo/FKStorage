package com.joker.storage.backend.database;

import java.util.Map;

public interface ConnPool {
    public void init();
    public Connection getConnection();
}
