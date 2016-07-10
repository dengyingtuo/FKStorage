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
import com.joker.storage.protocol.mysql.packet.BasePacket;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

/**
 * From client to server when the client do heartbeat between cobar cluster.
 * 
 * <pre>
 * Bytes         Name
 * -----         ----
 * 1             command
 * n             id
 * 
 * @author haiqing.zhuhq 2012-07-06
 */
public class HeartbeatPacket extends BasePacket {

    public byte command;
    public long id;

    @Override
    public void read(ByteBuffer buffer) {
        super.read(buffer);
        command = BufferUtil.readByte(buffer);
        id = BufferUtil.readLength(buffer);
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);

        BufferUtil.writeByte(buffer, command);
        BufferUtil.writeLength(buffer, id);
        return buffer;
    }

    @Override
    public int calcPacketSize() {
        return super.calcPacketSize() + 1 + BufferUtil.getLength(id);
    }

    @Override
    protected String getPacketInfo() {
        return "Cobar Heartbeat Packet";
    }

}
