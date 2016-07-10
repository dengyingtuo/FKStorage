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
 * From Server To Client, part of Result Set Packets. One for each column in the
 * result set. Thus, if the value of field_columns in the Result Set Header
 * Packet is 3, then the Field Packet occurs 3 times.
 * 
 * <pre>
 * Bytes                      Name
 * -----                      ----
 * n (Length Coded String)    catalog
 * n (Length Coded String)    db
 * n (Length Coded String)    table
 * n (Length Coded String)    org_table
 * n (Length Coded String)    name
 * n (Length Coded String)    org_name
 * 1                          (filler)
 * 2                          charsetNumber
 * 4                          length
 * 1                          type
 * 2                          flags
 * 1                          decimals
 * 2                          (filler), always 0x00
 * n (Length Coded Binary)    default
 * 
 * @see http://forge.mysql.com/wiki/MySQL_Internals_ClientServer_Protocol#Field_Packet
 * </pre>
 * 
 * @author xianmao.hexm 2010-7-22 下午05:43:34
 */
public class FieldPacket extends BasePacket {
    private static final byte[] DEFAULT_CATALOG = "def".getBytes();
    private static final byte[] FILLER = new byte[2];

    public byte[] catalog = DEFAULT_CATALOG;
    public byte[] db;			/* dbName */
    public byte[] table;		/* 表名	*/
    public byte[] orgTable;		/* 原先的表名 */
    public byte[] name;			/* 字段名 */
    public byte[] orgName;		/* 原先的字段名 */
    public int charsetIndex;	/* 字符集的下标 */
    public long length;			/* 字段长度 */
    public int type;			/* 字段类型 */
    public int flags;			/* 字段标志 */
    public byte decimals;		/* 整型值精度 */
    public byte[] definition;	/* 默认值的 */

    /* 把字节数组转变成FieldPacket */
    public void read(ByteBuffer buffer) {
        super.read(buffer);

        this.catalog = BufferUtil.readBytesWithLength(buffer);
        this.db = BufferUtil.readBytesWithLength(buffer);
        this.table = BufferUtil.readBytesWithLength(buffer);
        this.orgTable = BufferUtil.readBytesWithLength(buffer);
        this.name = BufferUtil.readBytesWithLength(buffer);
        this.orgName = BufferUtil.readBytesWithLength(buffer);
        buffer.get();
        this.charsetIndex = (int) BufferUtil.readUB2(buffer);
        this.length = BufferUtil.readUB4(buffer);
        this.type = (int) BufferUtil.readUB1(buffer);
        this.flags = (int) BufferUtil.readUB2(buffer);
        this.decimals = BufferUtil.readByte(buffer);
        buffer.position(buffer.position() + FILLER.length);
        if (buffer.hasRemaining()) {
            this.definition = BufferUtil.readBytesWithLength(buffer);
        }
    }

    @Override
    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        buffer = BufferUtil.checkWriteBuffer(buffer, calcPacketSize(), c);
        buffer = super.write(buffer, c);

        BufferUtil.writeWithLength(buffer, catalog);
        BufferUtil.writeWithLength(buffer, db);
        BufferUtil.writeWithLength(buffer, table);
        BufferUtil.writeWithLength(buffer, orgTable);
        BufferUtil.writeWithLength(buffer, name);
        BufferUtil.writeWithLength(buffer, orgName);
        buffer.put((byte) 0x0C);
        BufferUtil.writeUB2(buffer, charsetIndex);
        BufferUtil.writeUB4(buffer, length);
        buffer.put((byte) (type & 0xff));
        BufferUtil.writeUB2(buffer, flags);
        buffer.put(decimals);
        buffer.position(buffer.position() + FILLER.length);
        if (definition != null) {
            BufferUtil.writeWithLength(buffer, definition);
        }

        return buffer;
    }


    @Override
    protected String getPacketInfo() {
        return "MySQL Field Packet";
    }

    @Override
    public int calcPacketSize() {
        int size = super.calcPacketSize();
        size += (catalog == null ? 1 : BufferUtil.getLength(catalog));
        size += (db == null ? 1 : BufferUtil.getLength(db));
        size += (table == null ? 1 : BufferUtil.getLength(table));
        size += (orgTable == null ? 1 : BufferUtil.getLength(orgTable));
        size += (name == null ? 1 : BufferUtil.getLength(name));
        size += (orgName == null ? 1 : BufferUtil.getLength(orgName));
        size += 13;// 1+2+4+1+2+1+2
        if (definition != null) {
            size += BufferUtil.getLength(definition);
        }
        return size;
    }

}
