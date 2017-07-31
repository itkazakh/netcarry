/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.business;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DBUtils.java 数据
 * 
 * @author yinyayun
 */
public class DBUtils {
    enum DRIVER_TYPE {
        MYSQL, ORACLE
    }

    /**
     * 获取连接
     * 
     * @return
     */
    public static Connection getConnection(DRIVER_TYPE driver, String url, String user, String passwd) {
        Connection conn = null;
        try {
            if (driver == DRIVER_TYPE.MYSQL) {
                Class.forName("com.mysql.jdbc.Driver");
            }
            else {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }
            conn = DriverManager.getConnection(url, user, passwd);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(AutoCloseable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
