package com.joker.storage.protocol.mysql.packet.model;

import com.joker.storage.protocol.mysql.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.sql.Time;
import java.util.Calendar;

/**
 * @Author joker
 * @Date 16/7/10.
 */
public class TimeValue {
    private Time time = null;


    public TimeValue(ByteBuffer buffer) {
        buffer.position(buffer.position() + 6);
        int hour = (int) BufferUtil.readUB1(buffer);
        int minute = (int) BufferUtil.readUB1(buffer);
        int second = (int) BufferUtil.readUB1(buffer);

        Calendar cal = BufferUtil.getLocalCalendar();
        cal.set(0, 0, 0, hour, minute, second);
        time = new Time(cal.getTimeInMillis());
    }


    public void write(ByteBuffer buffer) {
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();

        buffer.position(buffer.position() + 6);
        BufferUtil.writeUB1(buffer, hour);
        BufferUtil.writeUB1(buffer, minute);
        BufferUtil.writeUB1(buffer, second);
    }


    public int calcPacketSize() {
        return 6 + 1 + 1 + 1;
    }
}
