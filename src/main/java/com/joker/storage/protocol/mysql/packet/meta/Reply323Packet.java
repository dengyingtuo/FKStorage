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
package com.joker.storage.protocol.mysql.packet.meta;

import java.nio.ByteBuffer;

import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.packet.BasePacket;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

/**
 * @author xianmao.hexm
 */
public class Reply323Packet extends BasePacket {

    public byte[] seed;

    public void read(ByteBuffer buffer) {
        super.read(buffer);
        seed = BufferUtil.readBytesWithNull(buffer);
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);
        if (seed == null) {
            buffer.put((byte) 0);
        } else {
            BufferUtil.writeWithNull(buffer, seed);
        }

        return buffer;
    }

    @Override
    public int calcPacketSize() {
        int size = super.calcPacketSize();
        size += (seed == null ? 1 : seed.length + 1);
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Auth323 Packet";
    }

}
