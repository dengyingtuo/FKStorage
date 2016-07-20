package com.joker.storage.protocol.mysql.utils;

import com.joker.storage.protocol.mysql.connection.MysqlConnection;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class BufferUtil {
    private static long NULL_LENGTH = -1;
    private static byte NULL_MARK = (byte) 251;

    public static void writeUB1(ByteBuffer buffer, long i) {
        buffer.put((byte) (i & 0xff));
    }
    public static long readUB1(ByteBuffer buffer) {
        return buffer.get() & 0xff;
    }

    public static void writeUB2(ByteBuffer buffer, long i) {
        buffer.put((byte) (i & 0xff));
        buffer.put((byte) (i >> 8));
    }
    public static long readUB2(ByteBuffer buffer) {
        long i = 0;
        i |= (buffer.get() & 0xff) << 0;
        i |= (buffer.get() & 0xff) << 8;
        return i;
    }

    public static void writeUB3(ByteBuffer buffer, long i) {
        buffer.put((byte) (i & 0xff));
        buffer.put((byte) (i >> 8));
        buffer.put((byte) (i >> 16));
    }
    public static long readUB3(ByteBuffer buffer) {
        long i = 0;
        i |= (buffer.get() & 0xff) << 0;
        i |= (buffer.get() & 0xff) << 8;
        i |= (buffer.get() & 0xff) << 16;
        return i;
    }

    public static void writeUB4(ByteBuffer buffer, long l) {
        buffer.put((byte) (l & 0xff));
        buffer.put((byte) (l >> 8));
        buffer.put((byte) (l >> 16));
        buffer.put((byte) (l >> 24));
    }
    public static long readUB4(ByteBuffer buffer) {
        long l = 0;
        l |= (buffer.get() & 0xff) << 0;
        l |= (buffer.get() & 0xff) << 8;
        l |= (buffer.get() & 0xff) << 16;
        l |= (buffer.get() & 0xff) << 24;
        return l;
    }


    public static void writeByte(ByteBuffer buffer, byte i) {
        buffer.put(i);
    }
    public static byte readByte(ByteBuffer buffer) {
        return buffer.get();
    }

    public static void writeShort(ByteBuffer buffer, short i) {
        buffer.put((byte) (i & 0xff));
        buffer.put((byte) (i >> 8));
    }
    public static short readShort(ByteBuffer buffer) {
         short i = 0;
         i |= (buffer.get() & 0xff) << 0;
         i |= (buffer.get() & 0xff) << 8;
         return i;
    }

    public static void writeInt(ByteBuffer buffer, int i) {
        buffer.put((byte) (i & 0xff));
        buffer.put((byte) (i >> 8));
        buffer.put((byte) (i >> 16));
        buffer.put((byte) (i >> 24));
    }
    public static int readInt(ByteBuffer buffer) {
        int i = 0;
        i |= (buffer.get() & 0xff) << 0;
        i |= (buffer.get() & 0xff) << 8;
        i |= (buffer.get() & 0xff) << 16;
        i |= (buffer.get() & 0xff) << 24;
        return i;
    }

    public static void writeLong(ByteBuffer buffer, long l) {
        buffer.put((byte) (l & 0xff));
        buffer.put((byte) (l >> 8));
        buffer.put((byte) (l >> 16));
        buffer.put((byte) (l >> 24));
        buffer.put((byte) (l >> 32));
        buffer.put((byte) (l >> 40));
        buffer.put((byte) (l >> 48));
        buffer.put((byte) (l >> 56));
    }
    public static long readLong(ByteBuffer buffer) {
        long l = 0;
        l |= (long) (buffer.get() & 0xff) << 0;
        l |= (long) (buffer.get() & 0xff) << 8;
        l |= (long) (buffer.get() & 0xff) << 16;
        l |= (long) (buffer.get() & 0xff) << 24;
        l |= (long) (buffer.get() & 0xff) << 32;
        l |= (long) (buffer.get() & 0xff) << 40;
        l |= (long) (buffer.get() & 0xff) << 48;
        l |= (long) (buffer.get() & 0xff) << 56;
        return l;
    }

    public static void writeFloat(ByteBuffer buffer, float f) { writeInt(buffer, Float.floatToIntBits(f)); }
    public static float readFloat(ByteBuffer buffer) { return Float.intBitsToFloat(readInt(buffer)); }
    public static void writeDouble(ByteBuffer buffer, double d) { writeLong(buffer, Double.doubleToLongBits(d)); }
    public static double readDouble(ByteBuffer buffer) { return Double.longBitsToDouble(readLong(buffer)); }


    public static void writeBinaryByte(ByteBuffer buffer, byte[] src)   { buffer.put(src); }
    public static void writeWithNull(ByteBuffer buffer, byte[] src)     { buffer.put(src); buffer.put((byte) 0); }
    public static void writeWithLength(ByteBuffer buffer, byte[] src) {
        if(src==null) {
            buffer.put(NULL_MARK);
        }

        writeLength(buffer, src.length);
        buffer.put(src);
    }

    public static void writeLength(ByteBuffer buffer, long l) {
        if (l < 251) {
            buffer.put((byte) l);
        } else if (l < 0x10000L) {
            buffer.put((byte) 252);
            writeUB2(buffer, (int) l);
        } else if (l < 0x1000000L) {
            buffer.put((byte) 253);
            writeUB3(buffer, (int) l);
        } else {
            buffer.put((byte) 254);
            writeLong(buffer, l);
        }
    }

    public static int getLength(byte[] src) {
        if(src == null) {
            return 1;
        }

        return getLength(src.length) + src.length;
    }

    public static int getLength(long length) {
        if (length < 251) {
            return 1;
        } else if (length < 0x10000L) {
            return 3;
        } else if (length < 0x1000000L) {
            return 4;
        } else {
            return 9;
        }
    }

    public static long readLength(ByteBuffer buffer) {
        int length = buffer.get() & 0xff;
        switch (length) {
        case 251:
            return NULL_LENGTH;
        case 252:
            return readUB2(buffer);
        case 253:
            return readUB3(buffer);
        case 254:
            return readLong(buffer);
        default:
            return length;
        }
    }

    public static byte[] readRemainBytes(ByteBuffer buffer) {
        return readBytes(buffer, buffer.remaining());
    }

    public static byte[] readBytesWithLength(ByteBuffer buffer) {
        int length = (int) readLength(buffer);

        if(length==NULL_LENGTH) {
            return null;
        }

        return readBytes(buffer, length);
    }

    public static byte[] readBytesWithNull(ByteBuffer buffer) {
        if (buffer.remaining()<=0) {
            return null;
        }

        byte[] b = buffer.array();
        int offset = -1;
        for (int i = buffer.position(); i < buffer.limit(); i++) {
            if (b[i] == 0) {
                offset = i;
                break;
            }
        }
        switch (offset) {
        case -1:
            // 整个Buffer都没有null
            byte[] ab1 = new byte[buffer.remaining()];
            System.arraycopy(b, buffer.position(), ab1, 0, ab1.length);
            buffer.position(buffer.limit());
            return ab1;

        default:
            byte[] ab2 = new byte[offset - buffer.position()];
            System.arraycopy(b, buffer.position(), ab2, 0, ab2.length);
            buffer.position(offset + 1);
            return ab2;
        }
    }


    public static String readStringWithNull(ByteBuffer buffer) {
        byte[] bytes = readBytesWithNull(buffer);
        return bytes==null ? null : new String(bytes);
    }

    public static String readStringWithNull(ByteBuffer buffer, String charset) throws UnsupportedEncodingException {
        byte[] bytes = readBytesWithNull(buffer);
        return bytes==null ? null : new String(bytes, charset);
    }

    public static String readStringWithLength(ByteBuffer buffer) {
        byte[] bytes = readBytesWithLength(buffer);
        return bytes==null ? null : new String(bytes);
    }

    public static String readStringWithLength(ByteBuffer buffer, String charset) throws UnsupportedEncodingException {
        byte[] bytes = readBytesWithLength(buffer);
        return bytes==null ? null : new String(bytes, charset);
    }

    public static byte[] readBytes(ByteBuffer buffer, int length) {
        byte[] ab = new byte[length];
        System.arraycopy(buffer.array(), buffer.position(), ab, 0, length);
        buffer.position(buffer.position()+length);
        return ab;
    }

    /* 检查WriteBuffer容量，不够则写出当前缓存块并申请新的缓存块 */
    public static int BUFFER_CAPACITY = 10*1024;
    public static ByteBuffer checkWriteBuffer(ByteBuffer buffer, int capacity, MysqlConnection c) {
        if (capacity > buffer.remaining()) {
            c.writer2Queue(buffer);
            return ByteBuffer.allocate(BUFFER_CAPACITY);
        } else {
            return buffer;
        }
    }


    public static void move(ByteBuffer buffer, int step) { buffer.position(buffer.position() + step); }
}
