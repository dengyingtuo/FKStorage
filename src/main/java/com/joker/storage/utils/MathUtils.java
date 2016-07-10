package com.joker.storage.utils;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MathUtils {
    public static Set<Long> getIndexFromRange(long left, long right, long low, long high, long count, boolean isExceptionExceed) throws SQLException {
        if(left > right || low>high || high/low<count || count<=0) {
            throw new SQLException("MathUtils getIndexFromRange , param error, " +
                    "left:" + left + ",right:" + right + ",low:" + low + ",high:" + high + ",count:" + count);
        }

        long highLeft = left/low;
        long highRight = right/low;
        long possCount = high/low;
        long paramCount = highRight -highLeft + 1;
        if(paramCount>=count) {
            if(paramCount==count || possCount==count || !isExceptionExceed) {
                Set<Long> retSet = new HashSet<Long>();
                for(long i=0;i<count;i++) {
                    retSet.add(i);
                }
                return retSet;
            } else {
                throw new SQLException("MathUtils getIndexFromRange - value exceed count, count:" + count + ", paramCount:" + paramCount);
            }
        }

        Set<Long> retSet = new HashSet<Long>();
        for(; highLeft<=highRight; ) {
            retSet.add(MathUtils.getIndexFromSingleValue(highLeft*low, low, high, count, isExceptionExceed));
            if(retSet.size()>=count) {
                break;
            }

            if(highLeft>=highRight) {
                break;
            } else {
                highLeft++;
            }
        }

        return retSet;
    }

    public static long getIndexFromSingleValue(long value, long low, long high, long count, boolean isExceptionExceed) throws SQLException {
        long index = (value%high)/low;
        if(index>=count) {
            if(isExceptionExceed) {
                throw new SQLException("MathUtils getIndexFromSingleValue - value exceed count, count:" + count + ", paramCount:" + index);
            } else {
                index = index%count;
            }
        }

        return index;
    }

}
