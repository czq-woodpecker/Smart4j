package com.woodpecker.czq.helper;

import com.woodpecker.czq.util.CollectionUtil;
import com.woodpecker.czq.util.PropertiesUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: woodpecker
 * @Date: 2018/7/3 15:04
 *
 * 数据库操作助手类
 */
public final class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER;

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final BasicDataSource DATA_SOURCE;







    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();


        Properties properties = PropertiesUtil.loadProperties("config.properties");//类路径下
        /////////////////为什么下面这段他不用自己封装的获取
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);//加载驱动
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);

    }



    /*
        获取数据库连接  version:2
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection == null){
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }



    /*
    查询实体
     */
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;
        try {
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }
        return entity;

    }


    /*
    查询实体列表
     */
    //使用QUERY_RUNNER对象可以面向实体（Entity）进行查询，DBUtil执行SQL语句并返回一个ResultSet，随后通过反射去创建并初始化实体对象
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList;
        try {
            Connection connection = getConnection();
            ////////////////////////////未知正确性
            entityList = QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /*
    执行查询语句
     */
    public static List<Map<String,Object>> executeQuery(String sql,Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection connection = getConnection();
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /*
    执行更新语句（包括update、insert、delete）
     */
    public static int  excuteUpdate(String sql,Object... params){
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection,sql,params);
        } catch (SQLException e) {
            LOGGER.error("excute update failure",e);
        }
        return rows;
    }

    /*
        插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity,fieldMap is empty");
            return false;
        }
        String sql = "INSERT INTO "+getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(",  ");
            values.append("?, ");
        }
        //替换最后的","为")"
        columns.replace(columns.lastIndexOf(", "),columns.length(),")");
        values.replace(values.lastIndexOf(", "),values.length(),")");
        sql += columns +" VALUES "+ values;

        Object[] params = fieldMap.values().toArray();
        return excuteUpdate(sql,params) == 1;
    }

    /*
    更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity,fieldMap is empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for(String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append("=?,");
        }
        sql += columns.substring(0,columns.lastIndexOf(",")) + " WHERE id = ?";
        List<Object> paramsList = new ArrayList<Object>();
        paramsList.addAll(fieldMap.values());
        paramsList.add(id);
        Object[] params = paramsList.toArray();

        return excuteUpdate(sql,params) == 1;
    }

    /*
    删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + "WHERE id = ?";
        return excuteUpdate(sql,id) == 1;
    }


    /*
    根据实体类名获取表名
     */
    private static  String getTableName(Class<?> entityClass) {
        return  entityClass.getSimpleName();
    }

    /*
    执行SQL文件
     */
    public static void excuteSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null){
                excuteUpdate(sql);
            }
        } catch (Exception e) {
            LOGGER.error("excute sql file failure",e);
            throw new RuntimeException(e);
        }

    }

}
