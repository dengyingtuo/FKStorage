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
package com.joker.storage.protocol.mysql.packet.client2server;

import java.nio.ByteBuffer;

import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.constant.Capabilities;
import com.joker.storage.protocol.mysql.packet.BasePacket;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

/**
 * From client to server during initial handshake.
 * 
 * <pre>
 * Bytes                        Name
 * -----                        ----
 * 4                            client_flags
 * 4                            max_packet_size
 * 1                            charset_number
 * 23                           (filler) always 0x00...
 * n (Null-Terminated String)   user
 * n (Length Coded Binary)      scramble_buff (1 + x bytes)
 * n (Null-Terminated String)   databasename (optional)
 * 
 * @see http://forge.mysql.com/wiki/MySQL_Internals_ClientServer_Protocol#Client_Authentication_Packet
 * </pre>
 * 
 * @author xianmao.hexm 2010-7-15 下午04:35:34
 * 客户端向后端发送的auth包
 */
public class AuthPacket extends BasePacket {
    private static final byte[] FILLER = new byte[23];

    public long clientFlags;
    public long maxPacketSize;
    public int charsetIndex;
    public byte[] extra;// from FILLER(23)
    public String user;
    public byte[] password;
    public String database;


    public void read(ByteBuffer buffer) {
        super.read(buffer);

        clientFlags = BufferUtil.readUB4(buffer);
        maxPacketSize = BufferUtil.readUB4(buffer);
        charsetIndex = (int) BufferUtil.readUB1(buffer);
        // read extra
        int current = buffer.position();
        int len = (int) BufferUtil.readLength(buffer);	/* 可能填充的地方用作它用  */
        if (len > 0 && len < FILLER.length) {
            byte[] ab = new byte[len];
            System.arraycopy(buffer.array(), buffer.position(), ab, 0, len);
            this.extra = ab;
        }
        buffer.position(current + FILLER.length);
        user = BufferUtil.readStringWithNull(buffer);
        password = BufferUtil.readBytesWithLength(buffer);
        if (((clientFlags & Capabilities.CLIENT_CONNECT_WITH_DB) != 0) && buffer.hasRemaining()) {
            database = BufferUtil.readStringWithNull(buffer);
        }
    }


		/* 向后端发送一个连接请求 */
    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);

        BufferUtil.writeUB4(buffer, clientFlags);
        BufferUtil.writeUB4(buffer, maxPacketSize);
        buffer.put((byte) charsetIndex);
        BufferUtil.writeBinaryByte(buffer, FILLER);
        if (user == null) {
            buffer.put((byte) 0);
        } else {
            byte[] userData = user.getBytes();
            BufferUtil.writeWithNull(buffer, userData);
        }
        if (password == null) {
            buffer.put((byte) 0);
        } else {
            BufferUtil.writeWithLength(buffer, password);
        }
        if (database == null) {
            buffer.put((byte) 0);
        } else {
            byte[] databaseData = database.getBytes();
            BufferUtil.writeWithNull(buffer, databaseData);
        }

        return buffer;
    }

    @Override
    public int calcPacketSize() {
        int size = super.calcPacketSize();

        size += 32;// 4+4+1+23;
        size += (user == null) ? 1 : user.length() + 1;
        size += (password == null) ? 1 : BufferUtil.getLength(password);
        size += (database == null) ? 1 : database.length() + 1;
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Authentication Packet";
    }

}
