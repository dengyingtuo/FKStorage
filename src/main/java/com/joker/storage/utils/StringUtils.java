package com.joker.storage.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class StringUtils {
	public static int getPositiveInt(String str) throws SQLException  {
		if(str==null) {
            throw new SQLException("str is null");
		}
		
		str = removeBlank(str);
		
		if(str.matches("[0-9]+")) {
			return Integer.valueOf(str);
		} else {
            throw new SQLException("str is not positive int:" + str);
		}
	}
	
	public static void isBoolean(String str) throws SQLException {
		if(str==null) {
            throw new SQLException("str is null");
		}
		
		str = removeBlank(str);
		
		if(!str.toLowerCase().matches("true|false")) {
            throw new SQLException("str is not boolean " + str);
		}
	}

	public static void isString(String str) throws SQLException {
		if(str == null) {
			throw new SQLException("str is null");
		}
	}

	/* 0000-0009 */
	public static List<String>  getNumRange(String str, int numLen) throws SQLException {
		if(str == null) {
			throw new SQLException("str is null");
		}

		str = removeBlank(str);
		List<String> ret = new ArrayList<String>();

		if(str.matches("[0-9]+-[0-9]+")) {
			String[] splitArray = str.split("-");
			int low = Integer.valueOf(splitArray[0]);
			int high = Integer.valueOf(splitArray[1]);

			if(low>high) {
				throw new SQLException("getNumRange str is worng, str:" + str);
			}

			for(int i=low; i<=high; i++) {
				Formatter fmt = new Formatter();
				if(numLen<=0) {
					fmt.format("%d", i);
				} else {
					fmt.format("%0" + numLen + "d", i);
				}
				ret.add(fmt.toString());
			}
		} else {
			ret.add(str);
		}

		return ret;
	}
	
	public static String removeBlank(String str) throws SQLException {
		if(str==null) {
            throw new SQLException("str is null");
		}
		
		/* 删除空格 */
		str = str.replace(" ", "");
		
		/* 删除回车 */
		str = str.replace("\r", "");
		
		/* 删除tab */
		str = str.replace("\t", "");
		
		/* 删除换行 */
		str = str.replace("\n", "");


		return str;
	}

	/* 去除引号 */
	public static String removeQuotation(String str) throws SQLException {
		try {
			if (str == null) {
				throw new SQLException("str is null");
			}

			/* 删除单引号 */
			str = str.replace("'", "");

			/* 删除双引号 */
			str = str.replace("\"", "");

			/* 删除` */
			str = str.replace("`", "");


			return str;
		} catch (Exception e) {
			throw new SQLException("removeQuotation error, str:" + str, e);
		}
	}



    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {   //一个字节的数，
        // 转成16进制字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            //整数转成十六进制表示
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();   //转成大写
    }

    public static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }

        return b2;
    }

	public static boolean isEquals(Object o1, Object o2) {
		if(o1==null) {
			if(o2!=null) {
				return false;
			} else {
				return true;
			}
		} else {
			return o1.equals(o2);
		}
	}

	public static String getStringFromNum(long numlen, long val) {

        Formatter fmt = new Formatter();
        if (numlen <= 0) {
            fmt.format("%d", val);
        } else {
            fmt.format("%0" + numlen + "d", val);
        }

        return fmt.toString();
    }

	public static String getNameForRule(long numlen, long val, String pattern, String tag) {
		String numStr = StringUtils.getStringFromNum(numlen, val);
        return pattern.replace(tag, numStr);
	}

	/**
	 * <p>功能描述：根据表名获得表的下标 </p>
	 *
	 * @param tableName 物理表名
	 * @param tableNamePattern 物理表名模板
	 * @param tag 模板中用于替换数字的部分
	 * @param numLen 物理表名中数字的长度
	 *
	 * @return 物理表的下标
	 * @throws
	 */
    public static long getTableNameIndex(String tableName, String tableNamePattern, String tag, long numLen) {
		int startIndex = tableNamePattern.indexOf(tag);
		int endIndex = startIndex + (int) numLen;

		while(endIndex<tableName.length()) {
			char c = tableName.charAt(endIndex);
			if(c<'0' || c>'9') {
				break;
			} else {
				endIndex++;
			}
		}

		String numStr = tableName.substring(startIndex, endIndex);
		return Long.valueOf(numStr);
	}

	public static void main(String[] args) {
		System.out.println(getTableNameIndex("hello_110_10", "hello_{?}", "{?}", 3));
//		System.out.println(getStringFromNum(1, 11));
	}

}