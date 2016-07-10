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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.packet.BasePacket;
import com.joker.storage.protocol.mysql.packet.model.BindValue;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

/**
 * <pre>
 *  Bytes                      Name
 *  -----                      ----
 *  1                          code
 *  4                          statement_id
 *  1                          flags
 *  4                          iteration_count 
 *  (param_count+7)/8          null_bit_map
 *  1                          new_parameter_bound_flag (if new_params_bound == 1:)
 *  n*2                        type of parameters
 *  n                          values for the parameters   
 *  --------------------------------------------------------------------------------
 *  code:                      always COM_EXECUTE
 *  
 *  statement_id:              statement identifier
 *  
 *  flags:                     reserved for future use. In MySQL 4.0, always 0.
 *                             In MySQL 5.0: 
 *                               0: CURSOR_TYPE_NO_CURSOR
 *                               1: CURSOR_TYPE_READ_ONLY
 *                               2: CURSOR_TYPE_FOR_UPDATE
 *                               4: CURSOR_TYPE_SCROLLABLE
 *  
 *  iteration_count:           reserved for future use. Currently always 1.
 *  
 *  null_bit_map:              A bitmap indicating parameters that are NULL.
 *                             Bits are counted from LSB, using as many bytes
 *                             as necessary ((param_count+7)/8)
 *                             i.e. if the first parameter (parameter 0) is NULL, then
 *                             the least significant bit in the first byte will be 1.
 *														 表示对应的param是否为null
 *  
 *  new_parameter_bound_flag:  Contains 1 if this is the first time
 *                             that "execute" has been called, or if
 *                             the parameters have been rebound. 决定后面是否有type数据
 *  
 *  type:                      Occurs once for each parameter; 
 *                             The highest significant bit of this 16-bit value
 *                             encodes the unsigned property. The other 15 bits
 *                             are reserved for the type (only 8 currently used).
 *                             This block is sent when parameters have been rebound
 *                             or when a prepared statement is executed for the 
 *                             first time.
 * 
 *  values:                    for all non-NULL values, each parameters appends its value
 *                             as described in Row Data Packet: Binary (column values)
 * @see http://dev.mysql.com/doc/internals/en/execute-packet.html
 * </pre>
 * 
 * @author xianmao.hexm 2012-8-28
 */
public class ExecutePacket extends BasePacket {

    public byte code;
    public long statementId;
    public byte flags;
    public long iterationCount;
    public byte[] nullBitMap;
    public short[] typeMap;
    public byte newParameterBoundFlag;
    public BindValue[] values;

    private final int paramCount;   /* 参数的个数 */

    public ExecutePacket(int paramCount) {
        this.paramCount= paramCount;
        this.values = new BindValue[paramCount];
    }

	/* 读取一个preparedstatement的数据包 */
    public void read(ByteBuffer buffer, String charset) throws UnsupportedEncodingException {
        super.read(buffer);

        code = BufferUtil.readByte(buffer);
        statementId = BufferUtil.readUB4(buffer);
        flags = BufferUtil.readByte(buffer);
        iterationCount = BufferUtil.readUB4(buffer);

        // 读取NULL指示器数据
        nullBitMap = new byte[(+ 7) / 8];
        for (int i = 0; i < nullBitMap.length; i++) {
            nullBitMap[i] = BufferUtil.readByte(buffer);
        }

        // 当newParameterBoundFlag==1时，更新参数类型。
        newParameterBoundFlag = BufferUtil.readByte(buffer);
        if (newParameterBoundFlag == (byte) 1) {
            typeMap = new short[paramCount];
            for (int i = 0; i < paramCount; i++) {
                typeMap[i] = BufferUtil.readShort(buffer);
            }
        }

        // 设置参数类型和读取参数值
        byte[] nullBitMap = this.nullBitMap;
        for (int i = 0; i < paramCount; i++) {
            BindValue bv = new BindValue();
            bv.type = typeMap[i];
            if ((nullBitMap[i / 8] & (1 << (i & 7))) != 0) {
                bv.isNull = true;
            } else {
                bv.read(buffer, charset);
            }
            values[i] = bv;
        }
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        super.write(buffer, c);

        BufferUtil.writeByte(buffer, code);
        BufferUtil.writeUB4(buffer, statementId);
        BufferUtil.writeByte(buffer, flags);
        BufferUtil.writeUB4(buffer, iterationCount);

        for(byte b : nullBitMap) {
            BufferUtil.writeByte(buffer, b);
        }

        BufferUtil.writeByte(buffer, newParameterBoundFlag);
        if(newParameterBoundFlag == (byte)1) {
            for(short t : typeMap) {
                BufferUtil.writeShort(buffer, t);
            }
        }

        for(BindValue bv : values) {
            bv.write(buffer);
        }

        return buffer;
    }


    @Override
    public int calcPacketSize() {
        int size = super.calcPacketSize();
        size += 1 + 4 + 1 + 4;
        size += (paramCount + 7)/8;
        size += 1;
        if(newParameterBoundFlag == (byte)1) {
            size += 2*paramCount;
        }
        for(BindValue bv : values) {
            size += bv.calcPacketSize();
        }

        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Execute Packet";
    }

}
