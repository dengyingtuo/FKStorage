package com.joker.storage.backend.topo;

import com.joker.storage.config.backend.SliceConfig;
import com.joker.storage.config.backend.SliceConfig.*;

import java.util.ArrayList;
import java.util.List;

public class Slice {
    public SliceNode writer;
    public List<SliceNode> readers = new ArrayList<SliceNode>();

    private SliceConfig config;
    public Slice(SliceConfig c) {
        config = c;
        writer = new SliceNode(c.writer);

        for(SliceNodeConfig snc : c.reader) {
            readers.add(new SliceNode(snc));
        }
    }


    public class SliceNode {
        public String nodeName;
        public Integer weight;

        private SliceNodeConfig config;

        public static final String NODENAME_STR = "nodeName";
        public static final String WEIGHT_STR = "weight";
        public SliceNode(SliceNodeConfig c) {
            this.config = c;

            this.nodeName = c.config.get(NODENAME_STR);
            this.weight = Integer.valueOf(c.config.get(WEIGHT_STR));
        }
    }
}
