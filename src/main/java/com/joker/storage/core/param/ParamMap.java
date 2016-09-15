package com.joker.storage.core.param;

import java.util.HashMap;
import java.util.Map;

public class ParamMap {
    public Map<Integer, Parameter> m = new HashMap<Integer, Parameter>();

    public void add(int index, Parameter parameter) {
        m.put(index, parameter);
    }

    public void clear() { m.clear(); }
}
