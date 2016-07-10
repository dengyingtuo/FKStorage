package com.joker.storage.utils;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;

public class FileUtils {
    public static String readFile(String fileName) throws SQLException {
        try {
            File file = getFile(fileName);
            if(file==null) {
                throw new SQLException("file is not exist, file:" + fileName);
            }

            byte[] data = new byte[10 * 1024 * 1024];
            InputStream in = new FileInputStream(file);
            int n = in.read(data);
            in.close();

            return new String(data, 0, n);
        } catch (Exception e) {
            throw new SQLException("read file execption, fileName:" + fileName, e);
        }
    }

    public static File getFile(String fileName) throws SQLException {
        if(fileName==null || fileName.length()==0)  {
            throw new SQLException("file name is null");
        }

        try {
            URL url = FileUtils.class.getClassLoader().getResource(fileName);
            File file = null;

            if(url!=null) {
                file = new File(url.getFile());
                if(!file.exists()) {
                    file = null;
                }
            }

            if(file==null) {
                file = new File(fileName);
                if(!file.exists()) {
                    file = null;
                }
            }
            return file;

        } catch (Exception e) {
            throw new SQLException("read file execption, fileName:" + fileName, e);
        }
    }


    public static void writeFile(String fileName, byte[] content) throws SQLException {

        try {
            File file = getFile(fileName);
            if(file!=null) {
                file.delete();
            }

            file =new File(fileName);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();

        } catch (Exception e) {
            throw new SQLException("write file error, fileName:" + fileName, e);
        }
    }
}
