package com.joker.storage.backend.database;

public interface Connection {
    public void execute(String sql);

    public void close();
}
