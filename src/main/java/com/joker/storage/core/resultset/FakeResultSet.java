package com.joker.storage.core.resultset;

import com.joker.storage.core.execute.ExecuteNode;
import com.joker.storage.core.model.SqlNode;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class FakeResultSet implements ResultSet {


    private SqlNode sqlNode;
    private int index = 0;
    private boolean closed = false;


    private ResultSet currentRs() throws SQLException {
        List<ExecuteNode> executeNodes = sqlNode.getChildren();
        if(index>=executeNodes.size() || index<0) {
            throw new SQLException("out of array size, index:" + index);
        }

        ResultSet rs = executeNodes.get(index).getRs();
        if(rs==null) {
            throw new SQLException("result rs is null, index:" + index);
        }

        return rs;
    }

    @Override
    public boolean next() throws SQLException {
        while(!currentRs().next()) {
            index++;
            if(index>=sqlNode.getChildren().size()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void close() throws SQLException { closed = true; }
    @Override
    public boolean isClosed() throws SQLException { return closed; }
    @Override
    public boolean wasNull() throws SQLException { return currentRs().wasNull(); }
    @Override
    public String getString(int columnIndex) throws SQLException { return currentRs().getString(columnIndex);}
    @Override
    public boolean getBoolean(int columnIndex) throws SQLException { return currentRs().getBoolean(columnIndex);}
    @Override
    public byte getByte(int columnIndex) throws SQLException { return currentRs().getByte(columnIndex);}
    @Override
    public short getShort(int columnIndex) throws SQLException { return currentRs().getShort(columnIndex);}
    @Override
    public int getInt(int columnIndex) throws SQLException { return currentRs().getInt(columnIndex);}
    @Override
    public long getLong(int columnIndex) throws SQLException { return currentRs().getLong(columnIndex);}
    @Override
    public float getFloat(int columnIndex) throws SQLException { return currentRs().getFloat(columnIndex);}
    @Override
    public double getDouble(int columnIndex) throws SQLException { return currentRs().getDouble(columnIndex);}
    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException { return currentRs().getBigDecimal(columnIndex);}
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException { return currentRs().getBytes(columnIndex);}
    @Override
    public Date getDate(int columnIndex) throws SQLException { return currentRs().getDate(columnIndex);}
    @Override
    public Time getTime(int columnIndex) throws SQLException { return currentRs().getTime(columnIndex);}
    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException { return currentRs().getTimestamp(columnIndex);}
    @Override
    public String getString(String columnLabel) throws SQLException { return currentRs().getString(columnLabel);}
    @Override
    public boolean getBoolean(String columnLabel) throws SQLException { return currentRs().getBoolean(columnLabel);}
    @Override
    public byte getByte(String columnLabel) throws SQLException { return currentRs().getByte(columnLabel);}
    @Override
    public short getShort(String columnLabel) throws SQLException { return currentRs().getShort(columnLabel);}
    @Override
    public int getInt(String columnLabel) throws SQLException { return currentRs().getInt(columnLabel);}
    @Override
    public long getLong(String columnLabel) throws SQLException { return currentRs().getLong(columnLabel);}
    @Override
    public float getFloat(String columnLabel) throws SQLException { return currentRs().getFloat(columnLabel);}
    @Override
    public double getDouble(String columnLabel) throws SQLException { return currentRs().getDouble(columnLabel);}
    @Override
    public byte[] getBytes(String columnLabel) throws SQLException { return currentRs().getBytes(columnLabel);}
    @Override
    public Date getDate(String columnLabel) throws SQLException { return currentRs().getDate(columnLabel);}
    @Override
    public Time getTime(String columnLabel) throws SQLException { return currentRs().getTime(columnLabel);}
    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException { return currentRs().getTimestamp(columnLabel);}
    @Override
    public Object getObject(int columnLabel) throws SQLException { return currentRs().getObject(columnLabel);}
    @Override
    public Object getObject(String columnLabel) throws SQLException { return currentRs().getObject(columnLabel);}
    @Override
    public BigDecimal getBigDecimal(int columnLabel) throws SQLException { return currentRs().getBigDecimal(columnLabel);}
    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException { return currentRs().getBigDecimal(columnLabel);}



    /****** not support ******/
    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public SQLWarning getWarnings() throws SQLException { throw new SQLException("not support");}
    @Override
    public void clearWarnings() throws SQLException { throw new SQLException("not support");}
    @Override
    public String getCursorName() throws SQLException { throw new SQLException("not support");}
    @Override
    public ResultSetMetaData getMetaData() throws SQLException { throw new SQLException("not support");}
    @Override
    public int findColumn(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean isBeforeFirst() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean isAfterLast() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean isFirst() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean isLast() throws SQLException { throw new SQLException("not support");}
    @Override
    public void beforeFirst() throws SQLException { throw new SQLException("not support");}
    @Override
    public void afterLast() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean first() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean last() throws SQLException { throw new SQLException("not support");}
    @Override
    public int getRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean absolute(int row) throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean relative(int rows) throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean previous() throws SQLException { throw new SQLException("not support");}
    @Override
    public void setFetchDirection(int direction) throws SQLException { throw new SQLException("not support");}
    @Override
    public int getFetchDirection() throws SQLException { throw new SQLException("not support");}
    @Override
    public void setFetchSize(int rows) throws SQLException { throw new SQLException("not support");}
    @Override
    public int getFetchSize() throws SQLException { throw new SQLException("not support");}
    @Override
    public int getType() throws SQLException { throw new SQLException("not support");}
    @Override
    public int getConcurrency() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean rowUpdated() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean rowInserted() throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean rowDeleted() throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNull(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateShort(int columnIndex, short x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateInt(int columnIndex, int x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateLong(int columnIndex, long x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateString(int columnIndex, String x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNull(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateShort(String columnLabel, short x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateInt(String columnLabel, int x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateLong(String columnLabel, long x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateString(String columnLabel, String x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void insertRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public void deleteRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public void refreshRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public void cancelRowUpdates() throws SQLException { throw new SQLException("not support");}
    @Override
    public void moveToInsertRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public void moveToCurrentRow() throws SQLException { throw new SQLException("not support");}
    @Override
    public Statement getStatement() throws SQLException { throw new SQLException("not support");}
    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException { throw new SQLException("not support");}
    @Override
    public Ref getRef(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Blob getBlob(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Clob getClob(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Array getArray(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException { throw new SQLException("not support");}
    @Override
    public Ref getRef(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Blob getBlob(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Clob getClob(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Array getArray(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException { throw new SQLException("not support");}
    @Override
    public URL getURL(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public URL getURL(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException { throw new SQLException("not support");}
    @Override
    public RowId getRowId(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public RowId getRowId(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException { throw new SQLException("not support");}
    @Override
    public int getHoldability() throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException { throw new SQLException("not support");}
    @Override
    public NClob getNClob(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public NClob getNClob(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException { throw new SQLException("not support");}
    @Override
    public String getNString(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public String getNString(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException { throw new SQLException("not support");}
    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException { throw new SQLException("not support");}
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException { throw new SQLException("not support");}
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException { throw new SQLException("not support");}
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException { throw new SQLException("not support");}
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException { throw new SQLException("not support");}
}
