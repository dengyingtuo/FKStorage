package com.joker.storage.protocol.mysql.packet.model;

import com.joker.storage.protocol.mysql.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateValue {
    private Date date = null;

    public DateValue(ByteBuffer buffer) {
        short length = (short) BufferUtil.readUB1(buffer);
        int year = (int) BufferUtil.readUB2(buffer);
        short month = (short) BufferUtil.readUB1(buffer);
        short date = (short) BufferUtil.readUB1(buffer);
        short hour = (short) BufferUtil.readUB1(buffer);
        short minute = (short) BufferUtil.readUB1(buffer);
        short second = (short) BufferUtil.readUB1(buffer);

        if (length == 11) {
            long nanos = BufferUtil.readUB4(buffer);
            Calendar cal = Calendar.getInstance();
            cal.set(year, --month, date, hour, minute, second);
            Timestamp time = new Timestamp(cal.getTimeInMillis());
            time.setNanos((int) nanos);

            this.date = time;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(year, --month, date, hour, minute, second);
            this.date = new Date(cal.getTimeInMillis());
        }
    }

    private void writeDate(ByteBuffer buffer, java.util.Date date) {
        if(date instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) date;

            short length = 11;
            int year = timestamp.getYear();
            int month = timestamp.getMonth();
            int day = timestamp.getDay();
            int hour = timestamp.getHours();
            int minute = timestamp.getMinutes();
            int second = timestamp.getSeconds();
            int nanos = timestamp.getNanos();

            BufferUtil.writeUB1(buffer, length);
            BufferUtil.writeUB2(buffer, year);
            BufferUtil.writeUB1(buffer, month);
            BufferUtil.writeUB1(buffer, day);
            BufferUtil.writeUB1(buffer, hour);
            BufferUtil.writeUB1(buffer, minute);
            BufferUtil.writeUB1(buffer, second);
            BufferUtil.writeUB4(buffer, nanos);

        } else {

            short length = 7;
            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();
            int hour = date.getHours();
            int minute = date.getMinutes();
            int second = date.getSeconds();

            BufferUtil.writeUB1(buffer, length);
            BufferUtil.writeUB2(buffer, year);
            BufferUtil.writeUB1(buffer, month);
            BufferUtil.writeUB1(buffer, day);
            BufferUtil.writeUB1(buffer, hour);
            BufferUtil.writeUB1(buffer, minute);
            BufferUtil.writeUB1(buffer, second);
        }
    }


    public int calcPacketSize() {
        if(date instanceof Timestamp) {
            return 1 + 2 + 1 + 1 + 1 + 1 + 1 + 4;
        } else {
            return 1 + 2 + 1 + 1 + 1 + 1 + 1;
        }
    }
}
