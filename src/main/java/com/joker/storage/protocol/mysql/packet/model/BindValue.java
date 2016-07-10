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
package com.joker.storage.protocol.mysql.packet.model;

import com.joker.storage.protocol.mysql.constant.Fields;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author xianmao.hexm 2012-8-28
 * preparedstatement中一个值
 * 到这里，一般是有值的 已经被null bitmap过滤了
 */
public class BindValue {
    private static final byte NULL_MARK = (byte) 251;

    public boolean isNull;      /* NULL indicator */
    public boolean isLongData;  /* long data indicator */
    public boolean isSet;       /* 是否已经赋值 */

    public long length;     /* Default length of data */
    public int type = -1;   /* data type */
    public byte scale;

    public Object value;    /* value to store */

    public void reset() {
        this.isNull = false;
        this.isLongData = false;
        this.isSet = false;

        this.length = 0;
        this.type = 0;
        this.scale = 0;

        this.value = null;
    }



    public void read(ByteBuffer buffer, String charset) throws UnsupportedEncodingException {
        switch (type & 0xff) {
        case Fields.FIELD_TYPE_BIT:
            value = BufferUtil.readBytesWithLength(buffer);
            break;

        case Fields.FIELD_TYPE_TINY:
            value = BufferUtil.readByte(buffer);
            break;

        case Fields.FIELD_TYPE_SHORT:
            value = BufferUtil.readShort(buffer);
            break;

        case Fields.FIELD_TYPE_LONG:
            value = BufferUtil.readInt(buffer);
            break;

        case Fields.FIELD_TYPE_LONGLONG:
            value = BufferUtil.readLong(buffer);
            break;

        case Fields.FIELD_TYPE_FLOAT:
            value = BufferUtil.readFloat(buffer);
            break;

        case Fields.FIELD_TYPE_DOUBLE:
            value = BufferUtil.readDouble(buffer);
            break;

        case Fields.FIELD_TYPE_TIME:
            value = new TimeValue(buffer);
            break;

        case Fields.FIELD_TYPE_DATE:
        case Fields.FIELD_TYPE_DATETIME:
        case Fields.FIELD_TYPE_TIMESTAMP:
            value = new DateValue(buffer);
            break;

        case Fields.FIELD_TYPE_VAR_STRING:
        case Fields.FIELD_TYPE_STRING:
        case Fields.FIELD_TYPE_VARCHAR:
            value = BufferUtil.readStringWithLength(buffer, charset);
            if (value == null) {
                isNull = true;
            }
            break;

        case Fields.FIELD_TYPE_DECIMAL:
        case Fields.FIELD_TYPE_NEW_DECIMAL:
            value = new BigDecimalValue(buffer);
            if (value == null) {
                isNull = true;
            }
            break;

        default:
            throw new IllegalArgumentException("bindValue error,unsupported type:" + type);
        }
        isSet = true;
    }

    public ByteBuffer write(ByteBuffer buffer) {
        switch (type & 0xff) {
        case Fields.FIELD_TYPE_BIT:
            BufferUtil.writeWithLength(buffer, (byte[]) value);
            break;

        case Fields.FIELD_TYPE_TINY:
            BufferUtil.writeByte(buffer, (Byte) value);
            break;

        case Fields.FIELD_TYPE_SHORT:
            BufferUtil.writeShort(buffer, (Short) value);
            break;

        case Fields.FIELD_TYPE_LONG:
            BufferUtil.writeInt(buffer, (Integer) value);
            break;

        case Fields.FIELD_TYPE_LONGLONG:
            BufferUtil.writeLong(buffer, (Long) value);
            break;

        case Fields.FIELD_TYPE_FLOAT:
            BufferUtil.writeFloat(buffer, (Float)value);
            break;

        case Fields.FIELD_TYPE_DOUBLE:
            BufferUtil.writeDouble(buffer, (Double) value);
            break;

        case Fields.FIELD_TYPE_TIME:
            ((TimeValue)value).write(buffer);
            break;

        case Fields.FIELD_TYPE_DATE:
        case Fields.FIELD_TYPE_DATETIME:
        case Fields.FIELD_TYPE_TIMESTAMP:
            ((TimeValue)value).write(buffer);
            break;

        case Fields.FIELD_TYPE_VAR_STRING:
        case Fields.FIELD_TYPE_STRING:
        case Fields.FIELD_TYPE_VARCHAR:
            byte[] data;
            if(isNull) {
                data = null;
            } else {
                data = ((String)value).getBytes();
            }

            BufferUtil.writeWithLength(buffer, data);
            break;

        case Fields.FIELD_TYPE_DECIMAL:
        case Fields.FIELD_TYPE_NEW_DECIMAL:
            ((BigDecimalValue)value).write(buffer);
            break;

        default:
            throw new IllegalArgumentException("bindValue error,unsupported type:" + type);
        }

        return buffer;
    }



    public int calcPacketSize() {
            switch (type & 0xff) {
        case Fields.FIELD_TYPE_BIT:
            return BufferUtil.getLength((byte[]) value);

        case Fields.FIELD_TYPE_TINY:
            return 1;

        case Fields.FIELD_TYPE_SHORT:
            return 2;

        case Fields.FIELD_TYPE_LONG:
        case Fields.FIELD_TYPE_FLOAT:
            return 4;

        case Fields.FIELD_TYPE_LONGLONG:
        case Fields.FIELD_TYPE_DOUBLE:
            return 8;

        case Fields.FIELD_TYPE_TIME:
            return ((TimeValue)value).calcPacketSize();

        case Fields.FIELD_TYPE_DATE:
        case Fields.FIELD_TYPE_DATETIME:
        case Fields.FIELD_TYPE_TIMESTAMP:
            return ((DateValue)value).calcPacketSize();

        case Fields.FIELD_TYPE_VAR_STRING:
        case Fields.FIELD_TYPE_STRING:
        case Fields.FIELD_TYPE_VARCHAR:
            if(isNull) {
                return BufferUtil.getLength(null);
            } else {
                return BufferUtil.getLength(((String)value).getBytes());
            }

        case Fields.FIELD_TYPE_DECIMAL:
        case Fields.FIELD_TYPE_NEW_DECIMAL:
            return ((BigDecimalValue)value).calcPacketSize();

        default:
            throw new IllegalArgumentException("bindValue error,unsupported type:" + type);
        }
    }
}
