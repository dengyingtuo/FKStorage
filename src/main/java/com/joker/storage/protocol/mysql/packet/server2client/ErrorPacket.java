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
package com.joker.storage.protocol.mysql.packet.server2client;

import java.nio.ByteBuffer;

import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.packet.BasePacket;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

/**
 * From server to client in response to command, if error.
 * 
 * <pre>
 * Bytes                       Name
 * -----                       ----
 * 1                           field_count, always = 0xff
 * 2                           errno
 * 1                           (sqlstate marker), always '#'
 * 5                           sqlstate (5 characters)
 * n                           message
 * 
 * @see http://forge.mysql.com/wiki/MySQL_Internals_ClientServer_Protocol#Error_Packet
 * </pre>
 * 
 * @author xianmao.hexm 2010-7-16 上午10:45:01
 */
public class ErrorPacket extends BasePacket {
    public static final byte FIELD_COUNT = (byte) 0xff;
    private static final byte SQLSTATE_MARKER = (byte) '#';
    private static final byte[] DEFAULT_SQLSTATE = "HY000".getBytes();

    public byte fieldCount = FIELD_COUNT;
    public int errno;
    public byte mark = SQLSTATE_MARKER;
    public byte[] sqlState = DEFAULT_SQLSTATE;
    public byte[] message;

    @Override
    public void read(ByteBuffer buffer) {
        super.read(buffer);

        fieldCount = BufferUtil.readByte(buffer);
        errno = (int) BufferUtil.readUB2(buffer);

        if (buffer.hasRemaining() && (buffer.get(buffer.position())==SQLSTATE_MARKER)) {
            buffer.get();
            sqlState = BufferUtil.readBytes(buffer, 5);
        }
        message = BufferUtil.readRemainBytes(buffer);
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);

        BufferUtil.writeByte(buffer, fieldCount);
        BufferUtil.writeUB2(buffer, errno);
        BufferUtil.writeByte(buffer, mark);
        BufferUtil.writeBinaryByte(buffer, sqlState);

        if (message != null) {
            BufferUtil.writeBinaryByte(buffer, message);
        }
        return buffer;
    }

    @Override
    public int calcPacketSize() {
        int size = super.calcPacketSize() + 9;  // 1 + 2 + 1 + 5
        if (message != null) {
            size += message.length;
        }
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Error Packet";
    }

}
