package com.joker.storage.backend.database.mysql;

import com.joker.storage.net.connection.NetConnection;

import java.nio.ByteBuffer;

public class MysqlConnection extends NetConnection {
    private ByteBuffer currentReadPacket;

    @Override
    protected void doRead4Net(ByteBuffer byteBuffer) {

    }

    @Override
    protected void doAccept() {

    }

    @Override
    protected void doConnection() {

    }

    @Override
    protected void doClose() {

    }

    @Override
    protected void error(int errCode, Throwable e) {

    }

    public boolean readOnePacket(ByteBuffer buffer) {
        int bufferRemaining = buffer.remaining();
        int needLength = currentReadPacket.remaining();

        if(needLength <= bufferRemaining) {
            System.arraycopy(currentReadPacket.array(), currentReadPacket.limit(),
                             buffer.array(), buffer.position(), needLength);

            buffer.position(buffer.position()+needLength);
            currentReadPacket.position(currentReadPacket.capacity());

            return true;
        } else {
            System.arraycopy(currentReadPacket.array(), currentReadPacket.limit(),
                             buffer.array(), buffer.position(), buffer.remaining());
            currentReadPacket.position(currentReadPacket.position()+buffer.remaining());
            buffer.position(buffer.capacity());

            return false;
        }
    }

}
