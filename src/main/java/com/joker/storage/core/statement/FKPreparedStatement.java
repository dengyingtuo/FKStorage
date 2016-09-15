package com.joker.storage.core.statement;

import com.joker.storage.core.Session;
import com.joker.storage.core.model.SqlNode;
import com.joker.storage.core.param.ParamMap;
import com.joker.storage.core.param.type.*;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class FKPreparedStatement extends FKStatement implements PreparedStatement {
    private String sql;
    private ParamMap paramMap = new ParamMap();

    public FKPreparedStatement(String sql, Session session) {
        super(session);
        this.sql = sql;
    }

    @Override
    public ResultSet executeQuery() throws SQLException { execute(); return preProcessor.getResultSet(); }
    @Override
    public int executeUpdate() throws SQLException { execute(); return preProcessor.getUpdateCount(); }
    @Override
    public boolean execute() throws SQLException {
        checkClosed();
        sqlNodes.add(new SqlNode(sql, paramMap));
        preProcessor = session.execute(sqlNodes);
        sqlNodes.clear();
        paramMap.clear();
        return preProcessor.isUpdate();
    }


    @Override
    public void addBatch() throws SQLException {
        checkClosed();
        sqlNodes.add(new SqlNode(sql, paramMap));
        paramMap = new ParamMap();
    }

    @Override
    public void clearParameters() throws SQLException {checkClosed(); paramMap.clear();}
    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamBoolean(x));}
    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamByte(x)); }
    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamShort(x)); }
    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamInt(x)); }
    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamLong(x));}
    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamFloat(x));}
    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamDouble(x));}
    @Override
    public void setString(int parameterIndex, String x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamString(x));}
    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamBytes(x));}
    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamDate(x));}
    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamTime(x));}
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamTimeStamp(x));}
    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {checkClosed(); paramMap.add(parameterIndex, new ParamObject(x));}


        /******* not support *******/
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {throw new SQLException("not support");}
    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {throw new SQLException("not support");}
    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {throw new SQLException("not support");}
    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("not support");}
}
