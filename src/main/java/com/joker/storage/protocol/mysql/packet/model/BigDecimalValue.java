package com.joker.storage.protocol.mysql.packet.model;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class BigDecimalValue {
    private boolean isNull;
    private BigDecimal bigDecimal = null;

    public BigDecimalValue(ByteBuffer buffer) {
//        String src = new String(BufferUtil.readBytesWithLength(buffer));
//        bigDecimal = (src == null ? null : new BigDecimal(src));
//        throw new SQLException("big decimal not support");
    }


    public void write(ByteBuffer buffer) {
        /* TODO */
//        throw new SQLException("big decimal not support");
    }

    public int calcPacketSize() {
        /* TODO */
        return 0;
    }
}
