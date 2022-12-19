package com.zhoujian.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @auther: zhoujian
 * @create: 2022-12-17 21:53
 **/

public class JDBCUtil {
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String driver = properties.getProperty("driver");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        //2
        Class.forName(driver);
        //3
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     *
     * @param conn
     * @param ps
     */
    public static  void closeResource(Connection conn, Statement ps){

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static  void closeResource(Connection conn, Statement ps, ResultSet rs){

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
