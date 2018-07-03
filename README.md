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