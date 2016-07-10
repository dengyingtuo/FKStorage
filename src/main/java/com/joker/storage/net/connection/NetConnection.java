package com.joker.storage.net.connection;

import com.joker.storage.net.NIOProcessor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class NetConnection {
    private long id;

    private NIOProcessor processor;
    private SocketChannel channel;

    private ByteBuffer readBuffer = ByteBuffer.allocate(10*1024*1024);
    private ByteBuffer writeBuffer = null;
    private BlockingQueue<ByteBuffer>  writeBufferQueue = new LinkedBlockingQueue<ByteBuffer>();
    private int netOp = -1;

    public NetConnection(NIOProcessor processor, long id, SocketChannel channel) {
        this.processor = processor;
        this.channel = channel;
        this.id = id;
    }

    public long getId() { return id; }


    public void register(Selector selector) throws SQLException {
        if(netOp<0) {
            throw new SQLException("NetConnection have no op");
        }

        try {
            channel.register(selector, netOp, this);
        } catch (Exception e) {
            throw new SQLException("register channel error, op:" + netOp, e);
        }
    }

    public void accept() throws SQLException {
        doAccept();
    }

    public void connect() throws SQLException {
        try {
            if (channel.finishConnect()) {
                doConnection();
            } else {
                throw new SQLException("connection error");
            }
        } catch (Exception e) {
            throw new  SQLException("finish connect error", e);
        }
    }

    public void read4Net() throws SQLException {
        try {
            int readCount = channel.read(readBuffer);
            if(readCount==0) {
                close();
                return;
            }

            while(readCount>0) {
                doRead4Net(readBuffer);
                readCount = channel.read(readBuffer);
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void writer2Queue(ByteBuffer buffer) {
        writeBufferQueue.add(buffer);
    }

    public void write2Net() throws SQLException {
        try {
            do {
                if(writeBuffer==null || writeBuffer.hasRemaining()) {
                    int remain = writeBuffer.remaining();
                    int writeCount = channel.write(writeBuffer);

                    if(writeCount<remain) {
                        triggerOP(SelectionKey.OP_WRITE);
                        return;
                    }
                }

                writeBuffer = writeBufferQueue.poll();
            } while (writeBuffer != null);

        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void triggerOP(int op) throws SQLException {
        netOp = op;
        processor.addConn(this);
    }

    public void close() throws SQLException {
        try {
            doClose();
            channel.close();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    protected abstract void doRead4Net(ByteBuffer byteBuffer);

    protected abstract void doAccept();

    protected abstract void doConnection();

    protected abstract void doClose();

    protected abstract void error(int errCode, Throwable e);
}
