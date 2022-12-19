package com.zhoujian.java.preparement.crud;

import com.zhoujian.bean.Customers;
import com.zhoujian.util.JDBCUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @desc: 针对Customerc通用查询
 * @auther: zhoujian
 * @create: 2022-12-18 16:26
 **/

public class CustomerForQuery {

    @Test
    public void testQuery1() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select id,name,email,birth from customers where id =?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 3);
            resultSet = ps.executeQuery();
            if(resultSet.next()){
                //next 判断结果集是否有下一条数据，有则返回并下移
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                System.out.println(id);
                //封装
    //            Object[] obj = new Object[]{id,name,email,birth};
    //            System.out.println(obj);
                // 类封装
                Customers customers = new Customers(id, name, email, birth);
                System.out.println(customers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtil.closeResource(conn,ps,resultSet);
        }

    }

    public Customers queryForCustomers(String sql,Object ...args) {
        /**
         * 针对customers表的通用查询
         */
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的源数据
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if(rs.next()){
                Customers cust = new Customers();

                for(int i=0;i<columnCount;i++){
                    Object columnValue = rs.getObject(i + 1);
                    //给指定的属性复制 反射
                    // 获取列名
//                    String columnName = metaData.getColumnName(i+1);
                    String columnName = metaData.getColumnLabel(i + 1);
                    //
                    Field field = Customers.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(cust,columnValue);
                }
                return cust;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return null;
    }
    @Test
    public void testQueryForCustomers(){
        String sql = "select id,name,email,birth from customers where id = ?;";
        Customers cust = queryForCustomers(sql, 5);
        System.out.println(cust);
    }

}
