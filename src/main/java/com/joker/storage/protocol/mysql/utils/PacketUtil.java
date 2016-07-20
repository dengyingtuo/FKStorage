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
package com.joker.storage.protocol.mysql.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import com.joker.storage.protocol.mysql.packet.meta.FieldPacket;
import com.joker.storage.protocol.mysql.packet.server2client.ResultSetHeaderPacket;

/**
 * @author xianmao.hexm
 */
public class PacketUtil {
    private static final String CODE_PAGE_1252 = "Cp1252";

    public static byte[] encode(String src, String charset) {
        if (src == null) {
            return null;
        }
        try {
            return src.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return src.getBytes();
        }
    }

    public static final FieldPacket getField(String name, String orgName, int type) {
        FieldPacket packet = new FieldPacket();
        packet.charsetIndex = CharsetUtil.getIndex(CODE_PAGE_1252);
        packet.name = encode(name, CODE_PAGE_1252);
        packet.orgName = encode(orgName, CODE_PAGE_1252);
        packet.type = (byte) type;
        return packet;
    }

    public static final FieldPacket getField(String name, int type) {
        FieldPacket packet = new FieldPacket();
        packet.charsetIndex = CharsetUtil.getIndex(CODE_PAGE_1252);
        packet.name = encode(name, CODE_PAGE_1252);
        packet.type = (byte) type;
        return packet;
    }

    public static final FieldPacket getField(ByteBuffer src, String fieldName) {
        FieldPacket field = new FieldPacket();
        field.read(src);
        field.name = encode(fieldName, CODE_PAGE_1252);
        field.packetLength = field.calcPacketSize();
        return field;
    }
}
