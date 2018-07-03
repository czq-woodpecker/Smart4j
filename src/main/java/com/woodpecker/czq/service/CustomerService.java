package com.woodpecker.czq.service;

import com.woodpecker.czq.model.Customer;
import com.woodpecker.czq.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: woodpecker
 * @Date: 2018/7/2 20:55
 *
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropertiesUtil.loadProperties("config.properties");//类路径下
        /////////////////为什么下面这段他不用自己封装的获取
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        //注册驱动
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }
    }



    /*
    获取客户列表
     */
    public List<Customer> getCustomerList(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from customer";
        List<Customer> customerList = new ArrayList<Customer>();
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                customerList.add(customer);
            }

        } catch (SQLException e) {
            LOGGER.error("excute sql failure",e);
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("close connection failure",e);
                }
            }
        }
        return customerList;
    }

    /*
    获取客户列表
     */
    public List<Customer> getCustomerList(String keyword){
        //TODO
        return null;
    }

    /*
    获取客户
     */
    public Customer getCustomer(long id){
        //TODO
        return null;
    }

    /*
    创建客户
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    /*
    更新客户
     */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    /*
    删除客户
     */
    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }

}
