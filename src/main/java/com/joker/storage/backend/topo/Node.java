package com.joker.storage.backend.topo;

import com.joker.storage.backend.database.ConnPool;
import com.joker.storage.backend.database.Connection;
import com.joker.storage.config.backend.NodeConfig;

public class Node {
    private String name;
    private ConnPool connPool;

    private NodeConfig config;
    public Node(NodeConfig config) {
        this.config = config;

        this.name = config.nodeName;
        this.connPool = null;
    }

    public void init() {
        connPool.init();
    }


    public Connection getConn() {
        return connPool.getConnection();
    }
}
