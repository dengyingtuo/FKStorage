package com.joker.storage.protocol.mysql.packet.client2server.command;

import com.joker.storage.protocol.mysql.connection.MysqlConnection;
import com.joker.storage.protocol.mysql.packet.client2server.CommandPacket;
import com.joker.storage.protocol.mysql.utils.BufferUtil;

import java.nio.ByteBuffer;

/**
 * Command packet
 *
 * command id - 1
 */
public class QuitPacket {
    private static final byte[] arg = {1};
    public void read (CommandPacket command) {}

    public ByteBuffer write(ByteBuffer buffer, MysqlConnection c) {
        CommandPacket commandPacket = new CommandPacket();
        commandPacket.arg = arg;

        commandPacket.packetLength = commandPacket.calcPacketSize();
        commandPacket.packetId = 0;
        buffer = BufferUtil.checkWriteBuffer(buffer, commandPacket.calcPacketSize(), c);
        return commandPacket.write(buffer, c);
    }

    protected String getPacketInfo() {
        return "MySQL Quit Packet";
    }
}
