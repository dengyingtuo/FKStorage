package com.joker.storage.protocol.mysql.connection;

import com.joker.storage.net.NIOProcessor;
import com.joker.storage.protocol.mysql.packet.BinaryPacket;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;


/* 能区分一个mysql包 */
public abstract class MysqlConnection extends AsyncConnection {
//    private BlockingQueue<BinaryPacket>


    public MysqlConnection(NIOProcessor processor, long id, SocketChannel channel) {
        super(processor, id, channel);
    }

    @Override
    protected void doRead4Net(ByteBuffer byteBuffer) {

    }


    protected abstract void handleOnePacket(BinaryPacket packet);
}
