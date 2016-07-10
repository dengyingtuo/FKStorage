package com.joker.storage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClassUtils {
    private static Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    private static Map<String, Class> classMap = new HashMap<String, Class>();

    public static Class getObjectByClassName(String className) throws Exception {
        try {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.info("class is not found local, try vitamin, className:" + className);
            } catch (Exception e) {
                throw e;
            }


            return getClassFromFile(className);

        } catch (Exception e) {
            throw new SQLException("getObjectByClassName error, className:" + className, e);
        }
    }

    private static synchronized Class getClassFromFile(String className) throws Exception {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        }


        // 加载
        File file = FileUtils.getFile(className);
        if (file == null) {
            throw new Exception("file is not exist, file:" + className);
        }


        URL url = file.toURI().toURL();
        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
        Class<?> myClass = (Class<?>) myClassLoader.loadClass(className);

        if (myClass == null) {
            throw new SQLException("getClassFromFile get null class fileName:" + className);
        }

        classMap.put(className, myClass);
        return myClass;
    }
}
