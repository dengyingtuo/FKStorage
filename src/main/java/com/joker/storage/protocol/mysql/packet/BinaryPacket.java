package com.joker.storage.protocol.mysql.packet;

import com.joker.storage.protocol.mysql.utils.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class BinaryPacket {
    private static Logger log = LoggerFactory.getLogger(BinaryPacket.class);

    public ByteBuffer data = null;
    private boolean isReady = false;

    public void read(ByteBuffer buffer) {
        if(data == null) {
            if(buffer.remaining()<4) {
                log.error("buffer is small");
                return;
            } else {
                int length = (int) BufferUtil.readUB3(buffer);
                BufferUtil.move(buffer, -3);

                data = ByteBuffer.allocate(length);
            }
        }


                System.arraycopy(buffer.array(), buffer.position(), data.array(), data.position(), length+4);
                BufferUtil.move(buffer, length + 4);
                BufferUtil.move(data, length+4);
        data = BufferUtil.readRemainBytes(buffer);
    }


    public boolean isReady() { return this.isReady; }

}
