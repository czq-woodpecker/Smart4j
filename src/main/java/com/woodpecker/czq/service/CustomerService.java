package com.woodpecker.czq.service;

import com.woodpecker.czq.helper.DatabaseHelper;
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
 * @author : woodpecker
 * @date: 2018/7/2 20:55
 *
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    /*
    获取客户列表 version:3
     */
    public List<Customer> getCustomerList(){
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class,sql);
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
        String sql =  "SELECT * FROM customer WHERE id = ? ";
        return DatabaseHelper.queryEntity(Customer.class,sql,id);
    }

    /*
    创建客户
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /*
    更新客户
     */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    /*
    删除客户
     */
    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }

}
