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
 * <pre>
 * From server to client, in response to prepared statement initialization packet. 
 * It is made up of: 
 *   1.a PREPARE_OK packet
 *   2.if "number of parameters" > 0 
 *       (field packets) as in a Result Set Header Packet 
 *       (EOF packet)
 *   3.if "number of columns" > 0 
 *       (field packets) as in a Result Set Header Packet 
 *       (EOF packet)
 *   
 * -----------------------------------------------------------------------------------------
 * 
 *  Bytes              Name
 *  -----              ----
 *  1                  0 - marker for OK packet
 *  4                  statement_handler_id
 *  2                  number of columns in result set
 *  2                  number of parameters in query
 *  1                  filler (always 0)
 *  2                  warning count
 *  
 *  @see http://dev.mysql.com/doc/internals/en/prepared-statement-initialization-packet.html
 * </pre>
 * 
 * @author xianmao.hexm 2012-8-28
 */
public class PreparedOkPacket extends BasePacket {

    public byte flag;
    public long statementId;
    public int columnsNumber;
    public int parametersNumber;
    public byte filler;
    public int warningCount;

    public PreparedOkPacket() {
        this.flag = 0;
        this.filler = 0;
    }

    @Override
    public void read(ByteBuffer buffer) {
        super.read(buffer);

        this.flag = BufferUtil.readByte(buffer);
        this.statementId = BufferUtil.readUB4(buffer);
        this.columnsNumber = (int) BufferUtil.readUB2(buffer);
        this.parametersNumber = (int) BufferUtil.readUB2(buffer);
        this.filler = BufferUtil.readByte(buffer);
        this.warningCount = (int) BufferUtil.readUB2(buffer);
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);


        BufferUtil.writeByte(buffer, flag);
        BufferUtil.writeUB4(buffer, statementId);
        BufferUtil.writeUB2(buffer, columnsNumber);
        BufferUtil.writeUB2(buffer, parametersNumber);
        BufferUtil.writeByte(buffer, filler);
        BufferUtil.writeUB2(buffer, warningCount);
        return buffer;
    }

    @Override
    public int calcPacketSize() {
        return super.calcPacketSize() + 12;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL PreparedOk Packet";
    }

}
