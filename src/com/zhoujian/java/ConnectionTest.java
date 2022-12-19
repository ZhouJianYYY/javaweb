package com.zhoujian.java;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    //方式1
    @Test
    public  void  testConnection1() throws SQLException {

        Driver driver = new com.mysql.cj.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/zhoujian";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }
    //方式2
    @Test
    public void testConnection2() throws Exception {

        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver =(Driver) clazz.newInstance();
        String url = "jdbc:mysql://localhost:3306/zhoujian";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }
    //方式三
    @Test
    public void testConnection3() throws Exception {
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver =(Driver) clazz.newInstance();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/zhoujian";
        String user = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    //方式四
    public void testConnection4() throws Exception {
        String url = "jdbc:mysql://localhost:3306/zhoujian";
        String user = "root";
        String password = "123456";
        //2 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //省略注册的操作。在mysql Driver的静态代码块中注册
//        Driver driver =(Driver) clazz.newInstance();
//        DriverManager.registerDriver(driver);
        // 3获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    //方式5
    @Test
    public void testConnection5() throws Exception {
        /*
        将配置信息保存在配置文件中
         */
        //1 读取配置文件信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String driver = properties.getProperty("driver");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);

    }

}
