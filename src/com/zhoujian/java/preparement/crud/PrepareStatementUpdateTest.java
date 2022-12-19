package com.zhoujian.java.preparement.crud;

import com.zhoujian.util.JDBCUtil;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @auther: zhoujian
 * @create: 2022-12-17 16:04
 **/

public class PrepareStatementUpdateTest {

    @Test
    public void testInsert() {
        /**
         * 插入数据
         */
        //1
        Connection conn = null;
        PreparedStatement ps = null;
        try {
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
            conn = DriverManager.getConnection(url, user, password);
//            System.out.println(conn);
            //4
            String sql = "INSERT customers(NAME,email,birth) VALUES(?,?,?);";
            ps = conn.prepareStatement(sql);
            // 5
            ps.setObject(1, "马化腾");
            ps.setObject(2, "mahuateng@163.com");
//        ps.setObject(3,"1965-01-21");
            //时间转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1965-12-01");
            ps.setDate(3, new Date(date.getTime()));
            //6执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 资源关闭
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
    }
    //修改数据
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "update customers set name=? where id= ?;";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"雷军");
            ps.setObject(2,2);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps);
        }
    }
    //通过的增删改
    public void update(String sql, Object ...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps);
        }
    }

    @Test
    public void testCommonUpdate() throws ParseException {
//        String sql = "delete from customers where id = ?;";
//        update(sql,2);
        String sql = "insert customers(name,email,birth) values(?,?,?);";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse("1965-12-01");

        update(sql,"刘强东","jiuqiongdong@qq.com",new Date(date.getTime()));
    }

}
