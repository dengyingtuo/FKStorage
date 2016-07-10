package com.joker.storage.net;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.joker.storage.net.connection.NetConnection;
import com.joker.storage.net.connection.NetConnectionFactory;
import com.joker.storage.protocol.mysql.constant.ErrorCode;

/**
 * 网络事件反应器
 * 负责在网络ok的时候调用对应的读写函数 
 */
public final class NIOProcessor extends Thread {
    private final BlockingQueue<NetConnection> connQueue;
    private final Selector selector;

    public NIOProcessor() throws SQLException {
        try {
            connQueue = new LinkedBlockingQueue<NetConnection>();
            selector = Selector.open();
        } catch (Exception e) {
            throw new SQLException("NIOProcessor init error", e);
        }
    }

    public void addConn(NetConnection conn) throws SQLException {
        connQueue.add(conn);
        selector.wakeup();
    }

    public int getConnQueueList() {
        return connQueue.size();
    }

    @Override
    public void run() {
        while(true) {
            try {
                selector.select(1000L);
                register(selector);
                Set<SelectionKey> keys = selector.selectedKeys();
                try {
                    for (SelectionKey key : keys) {
                        Object att = key.attachment();

                        if (att != null && key.isValid()) {
                            int readyOps = key.readyOps();
                            if ((readyOps & SelectionKey.OP_READ) != 0) {
                                read((NetConnection)att);

                            } else if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                                write((NetConnection)att);

                            } else if((readyOps & SelectionKey.OP_CONNECT) != 0) {
                                connect((NetConnection) att);

                            } else if((readyOps & SelectionKey.OP_ACCEPT) !=0) {
                                accept((NetConnectionFactory) att);

                            } else {
                                key.cancel();
                            }
                        } else {
                            key.cancel();
                        }
                    }
                } finally {
                    keys.clear();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /* 将connQueue中的数组加入到select中 */
    private void register(Selector selector) {
        NetConnection c;
        while ((c = connQueue.poll()) != null) {
            try {
                c.register(selector);
            } catch (Throwable e) {
                c.error(ErrorCode.ERR_REGISTER, e);
            }
        }
    }

    private void read(NetConnection c) {
        try {
            c.read4Net();
        } catch (Throwable e) {
            c.error(ErrorCode.ERR_READ, e);
        }
    }

    private void write(NetConnection c) {
        try {
            c.write2Net();
        } catch (Throwable e) {
            c.error(ErrorCode.ERR_WRITE_BY_EVENT, e);
        }
    }

    private void connect(NetConnection c) {
        try {
            c.connect();
        } catch (Throwable e) {
            c.error(ErrorCode.ERR_WRITE_BY_EVENT, e);
        }
    }

    private void accept(NetConnectionFactory f) {
         try {
             f.accept(this);
        } catch (Throwable e) {
             e.printStackTrace();
        }
    }

}
