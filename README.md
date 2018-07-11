# Smart4j

## 一、介绍



## 二、各个提交版本

### 版本1：

介绍：实现了基本功能

存在的问题：

1. 在CustomerService中读取config.properties文件不合理，因为其他Service也需要做同样的事情，最好抽取出来。
2. 执行一条select语句需要编写一大堆代码，还需要使用try-catch-finally，开发效率不高。





## X、遇到的问题：

1. 为什么无法使用@WebServlet()注解？

   高版本的Servlet-api才支持

2. 如何理解下面的泛型？

   ```java
   public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
       ////////////////////////////
   }
   ```

3. 为什么加上下面这句注释掉的代码可以解决entity的初始化问题？

   ```java
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
   //            throw new RuntimeException(e);
           } finally {
               closeConnection();
           }
           return entity;
   
       }
   ```

4. throw、throws和系统自动抛出异常的异同？

5. Exception、RuntimeException、Error的区别？

6. ？和T的区别？

   ```java
       /*
       根据实体类名获取表名
        */
       private static  String getTableName(Class<?> entityClass) {
           return  entityClass.getSimpleName();
       }
   ```

   



