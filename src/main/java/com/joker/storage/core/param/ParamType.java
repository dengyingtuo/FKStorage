package com.joker.storage.core.param;

public enum ParamType {
    BOOLEAN(0, "boolean"), BYTE(1, "byte"), SHORT(2, "short"), INT(3, "int"), LONG(4, "long"), FLOAT(5, "float"),
    DOUBLE(6, "double"), STRING(7, "string"), BYTES(8, "bytes"), DATE(9, "date"), TIME(10, "time"), TIMESTAMP(11, "timestamp"),
    OBJECT(12, "object");

    private int i;
    private String s;


    ParamType(int i, String s) {
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
