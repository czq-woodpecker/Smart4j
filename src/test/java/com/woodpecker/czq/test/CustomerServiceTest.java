package com.woodpecker.czq.test;

import com.woodpecker.czq.helper.DatabaseHelper;
import com.woodpecker.czq.model.Customer;
import com.woodpecker.czq.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author: woodpecker
 * @Date: 2018/7/2 21:04
 *
 * CustomerService单元测试
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest(){
        customerService = new CustomerService();
    }

    @Before
    public void init() throws IOException {
        // 初始化数据库
        DatabaseHelper.excuteSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void getCustomerTest(){
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest(){
        Map<String,Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name","customer100");
        fieldMap.put("contact","John");
        fieldMap.put("telephone","13620247788");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest(){
        long id = 1;
        Map<String,Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("contact","Eric");
        boolean result = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(result);
    }

    public void deleteCustomerTest(){
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }



}
