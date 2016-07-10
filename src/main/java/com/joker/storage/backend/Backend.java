package com.joker.storage.backend;

import com.joker.storage.backend.database.Connection;
import com.joker.storage.backend.topo.Node;
import com.joker.storage.backend.topo.Slice;
import com.joker.storage.config.backend.BackendConfig;

import java.util.HashMap;
import java.util.Map;

public class Backend {
    private Map<String, Node> nodeMap = new HashMap<String, Node>();
    private Map<String, Slice> sliceMap = new HashMap<String, Slice>();

    private BackendConfig config;
    public Backend(BackendConfig c) {
        this.config = c;

        for(String pdbName : c.nodeConfigMap.keySet()) {
            nodeMap.put(pdbName, new Node(c.nodeConfigMap.get(pdbName)));
        }

        for(String sliceName : c.sliceConfigMap.keySet()) {
            sliceMap.put(sliceName, new Slice(c.sliceConfigMap.get(sliceName)));
        }
    }


    public Connection getConn(String sliceName, boolean isUpdate, boolean noWriter) {
        String nodeName = sliceMap.get(sliceName).writer.nodeName;
        return nodeMap.get(nodeName).getConn();
    }
}
