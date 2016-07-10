/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joker.storage.net.connection;

import com.joker.storage.net.NIOProcessor;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public abstract class NetConnectionFactory {
    public static int socketRecvBuffer  = 16 * 1024;		/* tcp接收缓存大小 */
    public static int socketSendBuffer  = 8 * 1024;		    /* tcp发送缓存大小 */

    private ServerSocketChannel serverSocketChannel;
    public NetConnectionFactory(NIOProcessor nioProcessor, ServerSocketChannel channel) {
        this.serverSocketChannel = channel;
    }

	/* 初始化socketchannel, 用于发起连接 */
    public NetConnection getNetConn(NIOProcessor processor) throws SQLException {
        SocketChannel channel = null;

        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);

            return getNetConn(channel, processor);
        } catch (Exception e) {
            closeChannel(channel);
            throw new SQLException("create net connection error", e);
        }
    }

    /* 初始化accept的连接 */
    public void accept(NIOProcessor processor) throws SQLException {
        SocketChannel channel = null;
        try {
            while ((channel = serverSocketChannel.accept()) != null) {
                NetConnection conn = getNetConn(channel, processor);
                conn.accept();
            }
        } catch (Exception e) {
            throw new SQLException("net connection factory error", e);
        }
    }


    private NetConnection getNetConn(SocketChannel channel, NIOProcessor processor) throws SQLException {
        try {
            Socket socket = channel.socket();
            socket.setReceiveBufferSize(socketRecvBuffer);
            socket.setSendBufferSize(socketSendBuffer);
            socket.setTcpNoDelay(true);
            socket.setKeepAlive(true);

            return newConn(processor, channel);
        } catch (Exception e) {
            closeChannel(channel);
            throw new SQLException("create net connection error", e);
        }
    }

    protected abstract NetConnection newConn(NIOProcessor processor, SocketChannel channel);


    private static void closeChannel(SocketChannel channel) {
        if (channel == null) {
            return;
        }
        Socket socket = channel.socket();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
