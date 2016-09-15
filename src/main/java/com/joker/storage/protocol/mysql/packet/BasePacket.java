package com.joker.storage.protocol.mysql.packet;

import java.nio.ByteBuffer;
import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

public abstract class BasePacket {
    public int packetLength;
    public byte packetId;

    public void read(ByteBuffer buffer) {
        packetLength = (int) BufferUtil.readUB3(buffer);
        packetId = BufferUtil.readByte(buffer);
    }

    /* 把数据包写到buffer中 */
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        BufferUtil.writeUB3(buffer, packetLength);
        BufferUtil.writeByte(buffer, packetId);
        return buffer;
    }

    /* 计算数据包大小，不包含包头长度 */
    public int calcPacketSize() {
        return 4;
    }

    /* 取得数据包信息
     */
    protected abstract String getPacketInfo();

    @Override
    public String toString() {
        return new StringBuilder().append(getPacketInfo())
                .append("{length=").append(packetLength)
                .append(",id=").append(packetId)
                .append('}').toString();
    }
}
