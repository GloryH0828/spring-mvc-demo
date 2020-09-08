Spring MVC  学习记录总结

## 一、Spring MVC 概述

### 1、什么是 Spring MVC？

Spring MVC 是目前主流的实现MVC设计模式的框架，是Spring 框架的一个分支产品，以 Spring IoC 容器为基础，并利用容器的特性来简化他的配置。

Spring MVC 相当于Spring 的一个子模块，可以很好的和Spring 结合起来进行开发，是**Java Web 开发者必须掌握的框架**。

Spring MVC 把程序分成了 Controller 、View 、Model 三层。

![image-20200831100954473](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831100954473.png)

用户可以看到和操作 View ，Controller 接收到 View 的操作之后就可以 调用 业务模型 Model ，Model 生成业务处理后 返回给 View ，呈现给用户。

Spring MVC 就是对 这套流程的封装，它屏蔽了很多底层代码，开放出接口，让开发者可以更加轻松、便捷的完成基于MVC 模式的 Web 开发。

### 2、Spring MVC 的核心组件

* DispatcherServlet：前置控制器，是整个流程控制的核心，控制器其他组件的执行并将进行统一调度，可以降低组件之间的耦合性，相当于总指挥。
* Handler：处理器，完成具体的业务逻辑，相当于Servlet 或 Action。
* HandlerMapping：DispatcherServlet接收到请求后，通过HandlerMapping将不同的请求映射到不同的Handler。
* HandlerInterceptor：处理器拦截器。这是一个接口，如果需要完成一些拦截处理，可以实现该接口。
* HandlerExecutionChain：处理器执行链。它包括Handler 和HandlerInterceptor 两部分内容组成。系统会有一个默认的HandlerInterceptor ，如果需要额外设置拦截，可以添加拦截器。
* HandlerAdapter：处理器适配器。Handler 在执行业务方法之前，需要进行一系列的操作，包括表单数据的验证、数据类型转换、将表单数据封装到 JavaBean 等，这些操作均由 HandlerAdapter 完成，开发者只需要完成业务逻辑。（注：DispatcherServlet 通过 HandlerAdapter 执行不同的 Handler）
* ModelAndView：装载了模型数据和视图信息，作为 Handler 的处理结果返回给DIspatcherServlet。
* ViewResolver：视图解析器。DIspatcherServlet 通过 ViewResolver 将逻辑视图解析为物理视图，最终将渲染的结果呈现给用户。

### 3、Spring MVC 的工作流程

* 客户端请求被DispatcherServlet 接收

* DispatcherServlet 根据 HandlerMapping 映射到 Handler

* HandlerMapping 生成 Handler 和 HandlerInterceptor。

* Handler 和 HandlerInterptor 会以 HandlerExecutionChain 的形式一边给返回给 DispatcherServlet。

* DispatcherServlet 通过 HandlerAdapter 调用 Handler 的方法完成业务逻辑处理。

* Handler 返回一个ModelAndView 给 DispatcherServlet。

* DispatcherServlet 将获取的 ModelAndView 对象传给 ViewResolver 视图解析器，将逻辑视图解析为物理视图View。

* ViewResolver 返回一个 View 给 DispatcherServlet。

* DispatcherServlet 根据 View 进行试图渲染（将模型数据 Model 填充的视图 View 中）。

* DispatcherServlet 将渲染后的结果响应给客户端。

![image-20200831104114934](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831104114934.png)

### 4、Spring MVC 的特点

* 清晰地角色划分
* 灵活的配置功能
* 提供了大量的控制器和接口实现类
* 分离 View 层的实现
* 国际化支持
* 面向接口编程

Spring MVC 的流程非常复杂，但在实际开发中很简单，因为大部分的组件不需要开发者创建和管理，只需要通过配置稳健的方式完成配置即可。真正需要开发者进行处理的只有 **Handler**  、**View** 。

### 5、如何使用 Spring MVC？

* 创建Maven工程，pom.xml 添加相关依赖。

  ```xml
  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.2.8.RELEASE</version>
    </dependency>
  </dependencies>
  ```

* 在web.xml中配置DispatcherServlet。

  ```xml
  <!DOCTYPE web-app PUBLIC
   "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
   "http://java.sun.com/dtd/web-app_2_3.dtd" >
  
  <web-app>
    <display-name>Archetype Created Web Application</display-name>
  
    <!-- 配置DispatcherServlet拦截所有请求，并初始化读取 springmvc.xml 文件 -->
    <servlet>
      <servlet-name>dispatcherServlet</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
      </init-param>
    </servlet>
  
    <!-- 拦截所有请求 用 <url-pattern>/</url-pattern> 字段 -->
    <servlet-mapping>
      <servlet-name>dispatcherServlet</servlet-name>
      <url-pattern>/</url-pattern>
    </servlet-mapping>
  </web-app>
  ```

* springmvc.xml 文件中配置 Spring MVC 的相关配置。

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/context
                             http://www.springframework.org/schema/context/spring-context.xsd
                             http://www.springframework.org/schema/mvc
                             http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd">
      <!-- 自动扫描 -->
      <context:component-scan base-package="com.gloryh"></context:component-scan>
  
      <!-- 配置视图解析器 -->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/"></property>
          <property name="suffix" value=".jsp"></property>
      </bean>
  
  </beans>
  ```

* 创建 Handler

  ```java
  package com.gloryh.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  /**
   * MVC 对应业务层
   *
   * @author 黄光辉
   * @since 2020/8/31
   **/
  @Controller
  public class HelloHandler {
      @RequestMapping("/index")
      public String index() {
          System.out.println("执行了index");
          return "index";
      }
  }
  ```

  * RequestMapping

    Spring MVC 通过 `@RequestMapping` 注解将 URL 请求与业务方法进行映射，在 Handler 的类定义处以及方法定义处都可以添加。在类定义处添加就相当于客户端多了一层访问路径，在方法处添加即完成请求与方法的绑定。  

  * Controller

    `@controller` 注解在类定义处添加，即将该类交给 IoC 容器管理，使其成为一个控制器（相当于`@Component` 的一个子类），结合  springmvc.xml 文件中的自动扫描配置，可以接收客户端请求。

  ````java
  package com.gloryh.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  /**
   * MVC 对应业务层
   *
   * @author 黄光辉
   * @since 2020/8/31
   **/
  @Controller
  @RequestMapping("/hello")
  public class HelloHandler {
      @RequestMapping("/index")
      public String index() {
          System.out.println("执行了index");
          return "index";
      }
  }
  ````
  
  * RestController
  
    `@RestController` 表示控制器会直接将业务方法的返回值响应给客户端，不进行视图解析。即只加载Model。而`@Controller`表示该控制器的每一个业务方法的返回值都会交给视图解析器进行解析，如果只需要将数据响应给客户端，而不需要进行视图解析，则需要在对应的业务方法定义处添加`@ResponseBody`注解。

### 6、Spring MVC 注解

* RequestMapping 的参数

  > value：指定URL请求的实际地址，是 `@RequestMapping` 的默认值。`@RequestMapping("/hello")`相当于`@RequestMapping(value = "/hello")`.

  > method：指定请求的method 的类型（常用的有GET、POST、PUT、DELETE）。
  >
  > ```java
  > @RequestMapping(value = "/index",method = RequestMethod.GET)
  > @RequestMapping(value = "/index",method = RequestMethod.POST)
  > @RequestMapping(value = "/index",method = RequestMethod.PUT)
  > @RequestMapping(value = "/index",method = RequestMethod.DELETE)
  > ```
  >
  > 添加这个参数后，表示只能接收对应类型的HTTP请求。
  >
  > 测试：
  >
  > Handler设置为GET请求：
  >
  > ```java
  > package com.gloryh.controller;
  > 
  > import org.springframework.stereotype.Controller;
  > import org.springframework.web.bind.annotation.RequestMapping;
  > import org.springframework.web.bind.annotation.RequestMethod;
  > 
  > /**
  >  * MVC 对应业务层
  >  *
  >  * @author 黄光辉
  >  * @since 2020/8/31
  >  **/
  > @Controller
  > @RequestMapping("/hello")
  > public class HelloHandler {
  >     @RequestMapping(value = "/index",method = RequestMethod.GET)
  >     public String index() {
  >         System.out.println("执行了index");
  >         return "index";
  >     }
  > }
  > ```
  >
  > 使用GET请求页面，测试通过：
  >
  > ![image-20200831150219547](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831150219547.png)
  >
  > 使用POST请求，405错误，提示方法不允许：
  >
  > ![image-20200831150259801](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831150259801.png)

  > params：指定请求中必须包含某些参数，否则无法调用该方法。
  >
  > 测试（指定了必须包含name和id两个参数）：
  >
  > ```java
  > @RequestMapping(value = "/index",method = RequestMethod.GET,params = {"name","id" })
  > public String index() {
  >     System.out.println("执行了index");
  >     return "index";
  > }
  > ```
  >
  > URL中无指定参数，报错400：
  >
  > ![image-20200831150903593](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831150903593.png)
  >
  > URL中有指定参数，访问正常：
  >
  > ![image-20200831151023068](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831151023068.png)
  >
  > 另外，也可以指定某个值必须为多少，例如：`params = {"name","id=10" }`，这就代表URL里面不仅要有name和id两个参数，其中id参数的值还必须是10.
  >
  > 另外，如果我们需要得到某个params里面的参数，我们可以直接给方法添加一个同名的参数传递就可以获取到，比如我需要params里面的name，对应的参数，我就可以这样写：
  >
  > ```java
  > @RequestMapping(value = "/index",method = RequestMethod.GET,params = {"name","id=10" })
  > public String index(String name){
  >     System.out.println(name);
  >     System.out.println("执行了index");
  >     return "index";
  > }
  > ```
  >
  > 运行后就能打印出来（URL 为 ：http://localhost:8080/hello/index?name=zhangsan&id=10）：
  >
  > ![image-20200831152109574](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831152109574.png)
  >
  > 如过需要多个params内的指定参数，直接定义多个方法的同名参数就行，例如同时取出name和id：
  >
  > ```java
  > @RequestMapping(value = "/index",method = RequestMethod.GET,params = {"name","id=10" })
  > public String index(String name，int id){
  >     System.out.println(name);
  >     System.out.println(id);
  >     System.out.println("执行了index");
  >     return "index";
  > }
  > ```

* RequestParam：当我们的方法内参数和params不对应时，就无法完成传值，此时我们可以通过`@RequestParam`注解完成映射，即参数绑定。

  比如：

  ```java
  @RequestMapping(value = "/index",method = RequestMethod.GET,params = {"name","id=10" })
  public String index(String str，int age){
      System.out.println(str);
      System.out.println(age);
      System.out.println("执行了index");
      return "index";
  }
  ```

  由于基本数据类型 int 不能为null，就会报错：

  ![image-20200831153356065](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831153356065.png)

  使用Integer 接收的话也会返回null：

  ![image-20200831153457236](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831153457236.png)

  此时就可以采用`@RequestParam`来完成映射，他还会自动完成数据类型的转换：

  ```java
  @RequestMapping(value = "/index",method = RequestMethod.GET,params = {"name","id=10" })
  public String index(@RequestParam("name") String str ,@RequestParam("id") int age){
      System.out.println(str);
      System.out.println(age);
      System.out.println("执行了index");
      return "index";
  }
  ```

  运行结果：

  ![image-20200831153712321](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831153712321.png)

### 7.RESTful 风格

Spring MVC 设计模式也支持RESTful 风格的URL。

* 传统格式：http://localhost:8080/hello/index?name=zhangsan&id=10
* RESTful：http://localhost:8080/hello/rest/zhangsan/10

解析REStful风格的URL其实也很简单，只需要在使用`@RequestMapping`时注意将URL的风格改为RESTful，并按照其参数传递风格来获取参数即可，此时数据映射也并不是使用`@RequestParam`注解，而是使用`@PathVariable`注解，且**无论命名是否一致，都要添加此注解**：

```java
@RequestMapping("/rest/{name}/{id}")
    public String rest(@PathVariable("name") String str , @PathVariable("id") int age){
        System.out.println(str);
        System.out.println(age);
        System.out.println("执行了index");
        return "index";
    }
```

执行结果：

![image-20200831160013571](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831160013571.png)

### 8、映射Cookie

Spring MVC 通过映射可以直接在业务方法中获取 Cookie 的值。例如：获取Cookie中的sessionID：

```java
@RequestMapping("/cookie")
public String cookie(@CookieValue(value = "JSESSIONID") String sessionId) {
    System.out.println(sessionId);
    return "index";
}
```

运行结果：

![image-20200831161927248](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831161927248.png)

### 9、使用JavaBean来绑定参数

Spring MVC 会根据请求参数名和 JavaBean 属性名进行自动匹配，自动为对象填充属性值，同时支持级联属性。

1.新建实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 用户实体类，用于测试JavaBean数据绑定
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Data
public class User {
    private int id;
    private String name;
}
```

2.前段页面内属性名与实体类属性名对应：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="/hello/save" method="post">
    用户id：<input type="text" name="id" /><br/>
    用户名：<input type="text" name="name" /><br/>
    <input type="submit" value="注册"/>
</form>
</body>
</html>
```

3.方法内直接传参实体类：

```java
@RequestMapping(value = "/save",method = RequestMethod.POST)
public String save(User user){
    System.out.println(user);
    return "index";
}
```

测试：

前端输入：

![image-20200831165657056](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831165657056.png)

后端接收：

![image-20200831165801736](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831165801736.png)

可以看到已经得到了值，只不过出现了乱码，我们只需要在 web.xml 中新增乱码处理方法即可：

```xml
<!-- 字符过滤器 -->
<filter>
  <filter-name>encodingFilter</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>encodingFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

处理之后：

![image-20200831170333168](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831170333168.png)

4.支持级联。

主实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 用户实体类，用于测试JavaBean数据绑定
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Data
public class User {
    private int id;
    private String name;
    private Address address;
}
```

级联实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 地址实体类，测试级联
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Data
public class Address {
    private String value;
}
```

前端地址的 input name属性 采用级联式命名：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="/hello/save" method="post">
    用户id：<input type="text" name="id" /><br/>
    用户名：<input type="text" name="name" /><br/>
    地址：<input type="text" name="address.value"/><br/>
    <input type="submit" value="注册"/>
</form>
</body>
</html>
```

测试：

前端输入：

![image-20200831171134977](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831171134977.png)

后端打印：

![image-20200831171158932](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831171158932.png)

### 10、JSP 页面的转发和重定向

Spring MVC 默认是以转发的形式来响应JSP。

* 转发

  ```java
  @RequestMapping("/forward")
  public String forward(){
      return "forward:/index.jsp";
  }
  ```

  其中`return "forward:/index.jsp";`就相当于`return "index";`。

  输入地址：

  ![image-20200831172421022](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831172421022.png)

  回车进入后地址：

  ![image-20200831172439799](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831172439799.png)

* 重定向

  ```java
  @RequestMapping("/redirect")
  public String redirect(){
      return "redirect:/index.jsp";
  }
  ```

  输入地址：

  ![image-20200831172525252](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831172525252.png)

  回车进入后地址：

  ![image-20200831172558819](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831172558819.png)

  



## 二、Spring MVC 的数据绑定

数据绑定：在后端的业务方法中直接获取 客户端 HTTP 请求中的参数，将请求参数映射到业务方法的形参中。

Spring MVC 中的数据绑定是由 HandlerAdapter 来完成的。

### 1、基本数据类型

```java
@RequestMapping("/baseType")
@ResponseBody
public String baseType(int id){
    return id+"";
}
```

添加`@ResponseBody`注解后，Spring MVC 会直接将业务方法的返回值响应给客户端。

如果不加的话，Spring MVC 会将业务方法的返回值传递给 DispatcherServlet ，再由 DispatcherServlet 调用 ViewResolver 对返回值进行解析，映射到一个 JSP 资源。

不加`@ResponseBody`注解：

![image-20200831174432814](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831174432814.png)

加`@ResponseBody`注解：

![image-20200831174507442](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831174507442.png)

当没有进行传值时，会自动传递一个 null 值，但是由于是基本数据类型，不能够接收 null 值，就会报错：

![image-20200831174752108](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831174752108.png)

### 2、包装类

解决这个问题需要用到包装类，因为包装类可以接收 null 值 ：

```java
@RequestMapping("/packageType")
@ResponseBody
public String packageType(Integer id){
    return id+"";
}
```

运行结果：

![image-20200831175128964](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831175128964.png)

![image-20200831175215071](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831175215071.png)

以下为基本数据类型对应的包装类：

![image-20200831174921965](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200831174921965.png)

但是我们在使用时要注意类型的匹配，如果传递过来的参数不为对应的基本数据类型。仍然会报错：

![image-20200901092022263](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901092022263.png)

如果我们的传递参数和我们的方法参数命名不一致。

例如：

URL中：http://localhost:8080/data/packageType?num=1

方法中：Integer id

此时可以借助`@RequestParam`字段进行数据绑定：

```java
@RequestMapping("/packageType")
@ResponseBody
public String packageType(@RequestParam(value = "num") Integer id){
    return id+"";
}
```

![image-20200901092506070](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901092506070.png)

我们还可以加注 `required = true` ，让这个字段必须传递：

```java
@RequestMapping("/packageType")
@ResponseBody
public String packageType(@RequestParam(value = "num" ,required = true ) Integer id){
    return id+"";
}
```

不写num：

![image-20200901092835095](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901092835095.png)

也可以使用加注`required = false ,defaultValue = "0"`,让他可以不传num也可以，但此时不为 null值 而是我们设置的 defaultValue 即为0：

```java
@RequestMapping("/packageType")
@ResponseBody
public String packageType(@RequestParam(value = "num" ,required = false ,defaultValue = "0") Integer id){
    return id+"";
}
```

![image-20200901093130577](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901093130577.png)

### 3、Array数组

当我们在URL传参时，如果同一个参数被赋予了不同的值，例如：http://localhost:8080/data/packageType?num=1&num=2，就会报错：

![image-20200901093853652](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901093853652.png)

此时如果我们需要用到这种方式传参时需要将 num 扩充为数组。

```java
@RequestMapping("/array")
@ResponseBody
public String array( Integer[] num){
    String str = Arrays.toString(num);
    return str;
}
```

输出结果：

![image-20200901094357844](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901094357844.png)

### 4、List集合

Spring MVC 并不支持 List 类型的直接转换，需要我们利用实体类进行包装。

利用实体类封装List：

```java
package com.gloryh.entity;

import lombok.Data;
import java.util.List;

/**
 * 对List进行包装的实体类
 *
 * @author 黄光辉
 * @since 2020/9/1
 **/
@Data
public class UserList {
    private List<User> users;
}
```

User实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 用户实体类，用于测试JavaBean数据绑定
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Data
public class User {
    private int id;
    private String name;
    private Address address;
}
```

然后前端的值命名要与UserList 的属性 users 的 对应的 List<User> 的User 相对应：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/data/list" method="post">
    用户1id：<input type="text" name="users[0].id"/><br/>
    用户1name：<input type="text" name="users[0].name"/><br/>
    用户2id：<input type="text" name="users[1].id"/><br/>
    用户2name：<input type="text" name="users[1].name"/><br/>
    用户3id：<input type="text" name="users[2].id"/><br/>
    用户3id：<input type="text" name="users[2].name"/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
```

业务代码：

```java
@RequestMapping("/list")
@ResponseBody
public String list(UserList userList){
    String str ="";
    for(User user:userList.getUsers()){
        str+=user;
    }
    return str;
}
```

测试：

输入内容：

![image-20200901120432082](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901120432082.png)

跳转后结果：

![image-20200901142334965](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901142334965.png)

此时显示的说明我们对于集合的操作已经成功，中文乱码是因为我们在返回网页的时候出现的问题。即没有完成UTF-8的转码，我们需要在返回之前将其完成转码，即在转发之前调用消息转换器对消息进行处理。

在springmvc.xml中设置消息转换器：

```xml
<!-- 配置消息转换器，解决网页中文乱码 -->
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <property name="supportedMediaTypes" value="text/html;charset=UTF-8"></property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

重新测试跳转后结果：

![image-20200901143215648](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901143215648.png)

### 5、Map集合

与 List 集合一样，Spring MVC 并不支持 Map 类型的直接转换，需要我们利用实体类进行包装。

封装实体类：

```java
package com.gloryh.entity;

import lombok.Data;
import java.util.Map;

/**
 * 对 Map 进行包装的实体类
 *
 * @author 黄光辉
 * @since 2020/9/1
 **/
@Data
public class UserMap {
    private Map<String ,User> users;
}

```

业务实现方法：

```java
@RequestMapping("/map")
@ResponseBody
public String map(UserMap userMap) {
    String str = "";
    for (String key : userMap.getUsers().keySet()) {
        User user = userMap.getUsers().get(key);
        str += user;
    }
    return str;
}
```

前端页面：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/data/map" method="post">
    用户1id：<input type="text" name="users['value1'].id"/><br/>
    用户1name：<input type="text" name="users['value1'].name"/><br/>
    用户2id：<input type="text" name="users['value2'].id"/><br/>
    用户2name：<input type="text" name="users['value2'].name"/><br/>
    用户3id：<input type="text" name="users['value3'].id"/><br/>
    用户3id：<input type="text" name="users['value3'].name"/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
```

测试：

前端输入内容：

![image-20200901144944231](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901144944231.png)

跳转后结果：

![image-20200901144958164](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901144958164.png)

### 6、JSON格式数据

客户端发送JSON格式的数据，直接通过 Spring MVC 绑定到业务方法的形参中。

首先，要将JQuery 文件导入（我用的jQuery.1.8.2.min.js）：

![image-20200901150221525](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901150221525.png)

接下来我们要放行 JavaScript 格式的文件，因为之前我们设置的拦截器会将 所有格式的文件 的请求 都拦截。

Spring MVC 设置放行 JavaScript 文件（web.xml）：

```xml
 <!-- 放行其他静态资源 -->
  <servlet-mapping>
  <servlet-name>default</servlet-name>
  <url-pattern>*.js</url-pattern>
</servlet-mapping>
```

注：这里只放行了JavaScript 格式的静态资源，需要放行其他格式的静态资源可以自己添加。

放行之后，我们通过 Ajax 异步发送JSON文件：

```jsp
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/9/1
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jQuery.1.8.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var user={
                "id":1,
                "name":"张三"
            };
            $.ajax({
                url:"/data/json",
                data:JSON.stringify(user),
                type:"POST",
                contentType:"application/json;charset=UTF-8",
                dataType:"JSON",
                success:function (data) {
                    alert(data.id+"---"+data.name);
                }
            });
        });
    </script>
</head>
<body>
</body>
</html>
```

业务方法：

```java
@RequestMapping("/json")
@ResponseBody
public User json(@RequestBody User user) {
    System.out.println(user);
    user.setId(2);
    user.setName("李四");
    return user;
}
```

在业务方法中我们在参数前添加了`@RequestBody`来将传送过来的 JSON 数据转换成User 实体类，并且在方法内我们打印接受到的数据后，对数据进行了修改，最后返回给前端网页。

但是，我们还需要一个工具包来进行 JSON 数据的转换，我使用的时阿里的 fastjson 工具包，除此之外还有谷歌的 Gson 工具包等，可以自行选择（pom.xml 中添加依赖）：

```xml
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.70</version>
</dependency>
```

之后在springmvc.xml文件中配置 fastjson：

```xml
<mvc:annotation-driven>
    <mvc:message-converters>
        <!-- 配置消息转换器，解决网页中文乱码 -->
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <property name="supportedMediaTypes" value="text/html;charset=UTF-8"></property>
        </bean>
        <!-- 配置fastjson 解析JSON数据-->
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter"></bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

测试：

控制台打印：

![image-20200901153125388](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901153125388.png)

前端显示：

![image-20200901153142239](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901153142239.png)

因为我们在业务方法内对User进行了修改，所以打印结果为图中所示。

注：如果觉得这种json的解析用起来很麻烦，可以参考我之前用过的另一种ajax的写法。

```javascript
$.ajax({
            url:'/user/login',
            data:JSON.stringify(j),
            type:'POST',
            dataType:'json',
            /*
           声明参数位json类型，便于后台接收时可以识别
            */
            headers:{
                Accept:"application/json",
                "Content-Type":"application/json"
            },
            processData:false,
            cache:false
        }).done(function (data) {
            var status=JSON.parse(data).status;
            var name=JSON.parse(data).name;
            //alert(status);
            if (status==0){
                alert("您好"+name +",恭喜您登陆成功，即将跳转到主页!");
            }else if(status==1){
                alert("该用户不存在，请重新输入！");
            }else if (status==2){
                alert("密码输入有误，请重新输入！");
            }
        });
    });
```

链接如下：

gitee：https://gitee.com/GloryH0828/ssm_manager/tree/master

github：https://github.com/GloryH0828/ssm_manager

## 三 、Spring MVC 模型数据解析

JSP的四大作用域（范围从小到大）：page（页面作用域）、request（请求作用域）、session（会话作用域）、application（应用程序作用域）。

JSP四大作用域对应的内置对象依次为：pageContext < request < session < application。

模型数据的绑定是由 ViewResolver 来完成的，实际开发中，我们需要**先添加模型数据**，在交给ViewResolver 来绑定。

Spring MVC 提供了以下几种方式添加模型数据：

* Map
* Model
* ModelAndView
* @SessionAttribute
* @ModelAttributes

### 1、request

#### Map

业务方法：

```java
@RequestMapping("/map")
public String map(Map<String,User> map){
    User user =new User();
    user.setId(1);
    user.setName("张三");
    map.put("user", user);
    return "view";
}
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${requestScope.user}
</body>
</html>
```

前端展示：

![image-20200901164546476](C:\Users\admin\Desktop\image-20200901164546476.png)

#### Model

业务方法：

```java
@RequestMapping("/model")
public String model(Model model){
    User user =new User();
    user.setId(1);
    user.setName("张三");
    model.addAttribute("user",user);
    return "view";
}
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${requestScope.user}
</body>
</html>
```

前端展示：

![image-20200901164905146](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901164905146.png)

#### ModelAndView

##### 第一种写法

业务方法：

```java
@RequestMapping("/modelAndView")
public ModelAndView modelAndView() {
    ModelAndView modelAndView=new ModelAndView();
    User user = new User();
    user.setId(1);
    user.setName("张三");
    modelAndView.addObject("user",user);
    modelAndView.setViewName("view");
    return modelAndView;
}
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${requestScope.user}
</body>
</html>
```

前端展示：

<img src="C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901165523419.png" alt="image-20200901165523419" style="zoom:150%;" />

##### 第二种写法

业务方法：

```java
@RequestMapping("/modelAndView2")
public ModelAndView modelAndView2() {
    ModelAndView modelAndView=new ModelAndView();
    User user = new User();
    user.setId(1);
    user.setName("张三");
    modelAndView.addObject("user",user);
    View view = new InternalResourceView("/view.jsp");
    modelAndView.setView(view);
    return modelAndView;
}
```

前端展示：

![image-20200901170351761](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901170351761.png)

##### 第三种写法

业务方法：

```java
@RequestMapping("/modelAndView3")
public ModelAndView modelAndView3() {
    ModelAndView modelAndView=new ModelAndView("view");
    User user = new User();
    user.setId(1);
    user.setName("张三");
    modelAndView.addObject("user",user);
    return modelAndView;
}
```

前端展示：

![image-20200901170620894](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901170620894.png)

##### 第四种写法

业务方法：

```java
@RequestMapping("/modelAndView4")
public ModelAndView modelAndView4() {
    View view =new InternalResourceView("/view.jsp");
    ModelAndView modelAndView=new ModelAndView(view);
    User user = new User();
    user.setId(1);
    user.setName("张三");
    modelAndView.addObject("user",user);
    return modelAndView;
}
```

前端展示：

![image-20200901171229251](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901171229251.png)

##### 第五种写法

业务方法：

```java
@RequestMapping("/modelAndView5")
public ModelAndView modelAndView5() {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    Map<String, User> map = new HashMap<>();
    map.put("user", user);
    ModelAndView modelAndView = new ModelAndView("view", map);
    return modelAndView;
}
```

前端展示：

![image-20200901172049700](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901172049700.png)

##### 第六种写法

```java
@RequestMapping("/modelAndView6")
public ModelAndView modelAndView6() {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    ModelAndView modelAndView = new ModelAndView("view","user",user);
    return modelAndView;
}
```

#### HTTPServletRequest

添加依赖：

```xml
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>servlet-api</artifactId>
  <version>2.5</version>
</dependency>
```

业务方法：

```java
@RequestMapping("/request")
public String request(HttpServletRequest request) {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    request.setAttribute("user",user);
    return "view";
}
```

#### @ModelAttribute

定义一个方法，该方法专门用来返回要填充到数据模型中的对象（以下写法均可）：

```java
@ModelAttribute
public User getUser() {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    return user ;
}
```

```java
@ModelAttribute
public void  getUser(Map<String,User> map) {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    map.put("user",user);
}
```

```java
@ModelAttribute
public void  getUser(Model model) {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    model.addAttribute("user",user);
}
```

业务方法中无需再处理模型数据，只需返回视图即可：

```java
@RequestMapping("/modelAttribute")
public String modelAttribute(){
    return "view";
}
```

### 2、session

#### HTTPServletRequest

##### 第一种写法

业务方法：

```java
@RequestMapping("/session")
public String session(HttpServletRequest request){
    HttpSession session =request.getSession();
    User user = new User();
    user.setId(1);
    user.setName("张三");
    session.setAttribute("user",user);
    return "view";
}
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${sessionScope.user}
</body>
</html>
```

前端展示：

![image-20200901181104668](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901181104668.png)

##### 第二种写法

业务方法：

```java
@RequestMapping("/session2")
public String session2(HttpSession session) {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    session.setAttribute("user", user);
    return "view";
}
```

#### @SessionAttribute/@SessionAttributes

该注解添加在业务方法类上（与`@Controller`添加位置相同），会导致全局设置为识别存储session域中。他有两个属性;

* value ,会把所有以 value对应字段 命名的 方法内字段命名相同的 参数添加到session。
* type ，会把所有方法内 对应类的 参数添加到session。

以上两个属性均支持定义多个参数（单个参数使用 `@SessionAttribute` ,多个参数使用`@SessionAttributes`）。

### 3、application

#### HTTPServletRequest

业务方法：

```java
 @RequestMapping("/application")
    public String application(HttpServletRequest request) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        ServletContext application=request.getServletContext();
        application.setAttribute("user",user);
        return "view";
    }
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${applicationScope.user}
</body>
</html>
```

前端展示：

![image-20200901184246414](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200901184246414.png)

### 4、Spring MVC 自定义数据转换器

数据转换器是指将客户端HTTP请求中的参数转换为业务方法中定义的形参。

自定义表示开发者可以自主设计转换的方式。

HandlerAdapter 已经提供了通用的转换方式（比如 String 转 int ，String 转 double 等）、表单数据的封装等，但是在特殊的业务场景下，HandlerAdapter 无法进行转换，这时候就需要开发者自定义转换器。

#### 1.时间类型数据转换器

客户端输入 String 类型的数据“2020-09-02” ，自定义转换器将该数据转换为Date类型的对象。

自定义日期类型数据转换器：

```java
package com.gloryh.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器实现
 *
 * @author 黄光辉
 * @since 2020/9/2
 **/
public class DateConverter implements Converter<String, Date> {
    private String pattern;

    public DateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date convert(String s) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
```

在springmvc.xml 文件中配置自定义转换器：

```xml
<!-- 配置自定义数据转换器 并在 <mvc:annotation-driven>中 注册 -->
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <list>
            <bean class="com.gloryh.converter.DateConverter">
                <constructor-arg type="java.lang.String" value="yyyy-MM-dd"></constructor-arg>
            </bean>
        </list>
    </property>
</bean>

<mvc:annotation-driven conversion-service="conversionService">
    <mvc:message-converters>
        <!-- 配置消息转换器，解决网页中文乱码 -->
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <property name="supportedMediaTypes" value="text/html;charset=UTF-8"></property>
        </bean>
        <!-- 配置fastjson 解析JSON数据-->
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter"></bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

测试：

业务代码实现接收客户端数据后返回：

```java
package com.gloryh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 数据转换器实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@RestController
@RequestMapping("/converter")
public class ConverterHandler {

    @RequestMapping("/date")
    public String date(Date date) {
        return date.toString();
    }

}
```

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/converter/date" method="post">
    请输入日期：<input type="text" name="date"/>(yyyy-MM-dd)
    <input type="submit" name="提交"/>
</form>
</body>
</html>
```

前端数据输入：

![image-20200904095745003](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904095745003.png)

提交后返回前端：

![image-20200904095810598](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904095810598.png)

#### 2.自定义类型数据转换器

自定义 Student 实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 自定义学生类型实体类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Data
public class Student {
    private long id;
    private String name;
    private int age;
}
```

业务实现方法：

```java
@RequestMapping("/student")
public String student(Student student) {
    return student.toString();
}
```

前端代码：

```java
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/converter/student" method="post">
    输入学生信息：<input type="text" name="student" /><br/>（id-name-age）
    <input type="submit" value="注册"/>
</form>
</body>
</html>
```

前端信息输入：

![image-20200904101311016](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904101311016.png)

提交后显示（未加自定义数据转换器）：

![image-20200904101358548](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904101358548.png)

显示各项数据均为空，即无法识别我们输入的内容。

编写和配置String 到 Student 类型的数据转换器。

```java
package com.gloryh.converter;

import com.gloryh.entity.Student;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义学生实体类数据转换器
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
public class StudentConverter implements Converter<String, Student> {

    @Override
    public Student convert(String s) {
        //拆分String字符串。例如：38-黄光辉-20
        String[] args=s.split("-");
        Student student=new Student();
        student.setId(Long.parseLong(args[0]));
        student.setName(args[1]);
        student.setAge(Integer.parseInt(args[2]));
        return student;
    }
}
```

在 spring.xml中配置：

```xml
<!-- 配置自定义数据转换器 并在 <mvc:annotation-driven>中 注册 -->
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <list>
            <bean class="com.gloryh.converter.DateConverter">
                <constructor-arg type="java.lang.String" value="yyyy-MM-dd"></constructor-arg>
            </bean>
            <bean class="com.gloryh.converter.StudentConverter">
            </bean>
        </list>
    </property>
</bean>

<mvc:annotation-driven conversion-service="conversionService">
    <mvc:message-converters>
        <!-- 配置消息转换器，解决网页中文乱码 -->
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <property name="supportedMediaTypes" value="text/html;charset=UTF-8"></property>
        </bean>
        <!-- 配置fastjson 解析JSON数据-->
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter"></bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

再次运行前端代码后显示：

![image-20200904102215828](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904102215828.png)



## 四、Spring MVC 的 RESTful 架构

REST（Representational State Transfer）：资源表现层状态转换，是目前比较主流的一种互联网软件架构。

RESTful 架构 的特点：

* 结构清晰

* 标准规范

* 易于理解

* 便于扩展

  即：URL 更加简、有利于不同系统之间的资源共享。

RESTful 结构 的 核心概念：

* 资源（Resource）

  网络上的一个实体（网络中存在的一个具体的信息，比如，一段文本、一张图片、一段音频或者视频等等）。我们可以用一个 **URI**（统一资源定位符/统一资源标识符）指向它。**每个资源都有一个特定的URI**，我们需要获取该资源时，只需要访问对应的URI即可。

* 表现层（Representation）

  资源具体呈现出来的形式。比如文本可以用 txt 格式表示，也可以使用 HTML 、XML 、JSON 等格式来表示。

* 状态转换（State Transfer）

  客户端如果希望操作服务器中的某个资源，就需要通过某种方式让服务端发生状态转换，而这种转换建立在表现层之上，故称之为 **“表现层状态转换”**。

RESTful 架构的使用：

REST 具体操作就是 HTTP 协议中四个表示操作方式的动词分别对应CRUD基本操作：

* GET：获取资源。
* POST：新建资源。
* PUT：修改资源。
* DELETE：删除资源。

用到的实体类：

```java
package com.gloryh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义学生类型实体类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
}
```

CRUD操作实现的接口类：

```java
package com.gloryh.repository;

import com.gloryh.entity.Student;

import java.util.Collection;

/**
 * REST 相关业务方法的接口类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
public interface StudentRepository {
    /**
     * 查询所有信息
     * @return Collection<Student>
     */
    public Collection<Student> findAll();

    /**
     * 按照id查询
     * @param id
     * @return Student
     */
    public Student findById(long id);

    /**
     * 更新或者添加
     * @param student
     */
    public void saveOrUpdate(Student student);

    /**
     * 按id删除
     * @param id
     */
    public void deleteById(long id);
}
```

CRUD相关操作接口的实现类(添加`@Repository`注解交给IoC容器管理，方便调用):

```java
package com.gloryh.repository.impl;

import com.gloryh.entity.Student;
import com.gloryh.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 * REST 相关业务方法的接口的实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Repository
public class StudentRepositoryImpl implements StudentRepository {
    /**
     * 定义一个静态集合模拟数据库
     */
    private static Map<Long,Student> studentMap;
    static{
        studentMap.put(1L,new Student(1L,"张三",22));
        studentMap.put(1L,new Student(2L,"李四",23));
        studentMap.put(1L,new Student(3L,"王五",24));
    }
    /**
     * 查询所有信息
     *
     * @return Collection<Student>
     */
    @Override
    public Collection<Student> findAll() {
        return studentMap.values();
    }

    /**
     * 按照id查询
     *
     * @param id
     * @return Student
     */
    @Override
    public Student findById(long id) {
        return studentMap.get(id);
    }

    /**
     * 更新或者添加
     *
     * @param student
     */
    @Override
    public void saveOrUpdate(Student student) {
        studentMap.put(student.getId(),student);
    }

    /**
     * 按id删除
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        studentMap.remove(id);
    }
}
```

业务方法实现类（`@Autowired`注解注入实现类）：

```java
package com.gloryh.controller;

import com.gloryh.entity.Student;
import com.gloryh.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * REST 相关操作业务实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@RestController
@RequestMapping("/rest")
public class RestHandler {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 实现查询所有Student ,使用 produces 处理中文乱码
     * @return Collection<Student>
     */
    @GetMapping(value = "/findAll",produces ="text/json;charset=UTF-8" )
    public Collection<Student> findAll(){
        return studentRepository.findAll();
    }

    /**
     * 按id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/find/{id}",produces ="text/json;charset=UTF-8")
    public Student findById(@PathVariable("id") long id){
        return studentRepository.findById(id);
    }

    /**
     * 添加
     * @param student
     */
    @PostMapping(value = "/save")
    public void save(@RequestBody Student student){
        studentRepository.saveOrUpdate(student);
    }

    /**
     * 更新
     * @param student
     */
    public void update(@RequestBody Student student){
        studentRepository.saveOrUpdate(student);
    }

    /**
     * 按id删除
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") long id){
        studentRepository.deleteById(id);
    }
}
```

查询操作使用GET请求 :

`@RequestMapping(value = "/findAll",produces = "text/json;charset=UTF-8",method = RequestMethod.GET)`

`@RequestMapping(value = "/find/{id}",produces = "text/json;charset=UTF-8",method = RequestMethod.GET)`

可以简写为：

`@GetMapping(value = "/findAll",produces ="text/json;charset=UTF-8" )`

`@GetMapping(value = "/find/{id}",produces ="text/json;charset=UTF-8")`

因为使用了RESTful 风格，所以参数传递也是用`@PathVariable`注解。

测试结果：

1.查询所有信息：

![image-20200904121208853](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121208853.png)

2.按id查询：

![image-20200904121238986](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121238986.png)



添加请求：

`@RequestMapping(value = "save",method = RequestMethod.POST)`

可以简写为：

` @PostMapping(value = "/save")`

测试（添加赵六）：

![image-20200904121742484](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121742484.png)

添加后结果：

![image-20200904121830546](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121830546.png)

更新请求：

`@RequestMapping(value = "save",method = RequestMethod.PUT)`

可以简写为：

`@PutMapping("/update")`

测试（更改赵六的年龄为26）：

![image-20200904121928291](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121928291.png)

更新后结果：

<img src="C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904121952307.png" alt="image-20200904121952307" style="zoom:150%;" />

删除请求：

`@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)`

可以简写为：

` @DeleteMapping("/delete/{id}")`

测试（删除赵六）：

![image-20200904122126140](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904122126140.png)

删除后结果：

![image-20200904122158189](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904122158189.png)

## 五 、Spring MVC 文件上传/下载

### 1、单文件上传

底层采用 Apache FileUpload 组件完成上传，Spring MVC 对这种方式进行了封装。

* pom.xml导入相关依赖：

  ```xml
  <dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
  </dependency>
  <dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
  </dependency>
  ```

* 在 springmvc.xml 文件中配置相应组件：

  ```xml
  <!-- 配置上传组件 -->
  <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
  </bean>
  ```

* 在服务器发布的项目路径中新建一个名为 file 的文件夹存储上传的文件

  ![image-20200904145619178](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904145619178.png)

* 前端添加上传组件 且图片上传后进行预览：

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page isELIgnored="false" %>
  <html>
  <head>
      <title>Title</title>
  </head>
  <body>
  <form action="/file/upload" method="post" enctype="multipart/form-data">
      <input type="file" name="img"/>
      <input type="submit" value="上传"/>
      <img src="${path}"/>
  </form>
  </body>
  </html>
  ```

  注：form 标签中 必须添加属性：`method="post" enctype="multipart/form-data"`,否则只能将文件名传给服务器。

* 业务方法实现类

  ```java
  package com.gloryh.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.multipart.MultipartFile;
  
  import javax.servlet.http.HttpServletRequest;
  import java.io.File;
  import java.io.IOException;
  
  /**
   * 文件上传下载业务方法
   *
   * @author 黄光辉
   * @since 2020/9/4
   **/
  @Controller
  @RequestMapping("/file")
  public class FileHandler {
  
      @PostMapping("/upload")
      public String upload(MultipartFile img, HttpServletRequest request){
          //文件不为空，保存文件
          if (img.getSize()>0){
              //获取文件保存的绝对路径file
              String path=request.getServletContext().getRealPath("file");
              //获取上传的文件名
              String name=img.getOriginalFilename();
              //在要保存的路径内创建一个空的文件，文件名为字段 name
              File file=new File(path,name);
              //将 img 的 数据 赋到该空文件中
              try {
                  img.transferTo(file);
              } catch (IOException e) {
                  e.printStackTrace();
              }
              //保存上传之后的文件路径,传回前端进行预览
              request.setAttribute("path","/file/"+name);
  
          }
          return "upload";
      }
  }
  ```

  前端上传：

  ![image-20200904150650769](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904150650769.png)

  上传后预览：

  ![image-20200904150825870](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904150825870.png)

  文件加载失败，因为 dispatcherServlet 未放行 图片格式的静态资源文件。

  设置放行（web.xml中）：

  ```xml
   <!-- 放行其他静态资源 -->
    <servlet-mapping>
    <servlet-name>default</servlet-name>
      <url-pattern>*.js</url-pattern>
      <url-pattern>*.jpg</url-pattern>
      <url-pattern>*.png</url-pattern>
  </servlet-mapping>
  ```

  重新上传预览成功：

  ![image-20200904152405639](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904152405639.png)

### 2、多文件上传

多文件上传只要控制循环输入文件就行，相当于为每个文件执行单文件上传操作，预览使用集合存储绝对文件路径。业务方法实现如下：

```java
@PostMapping("/uploads")
public String uploads(MultipartFile[] imgs, HttpServletRequest request){
    //定义一个集合存储文件预览时的路径
    List<String> files=new ArrayList<>();
    for (MultipartFile img:imgs){
        //文件不为空，保存文件
        if (img.getSize()>0){
            //获取文件保存的绝对路径file
            String path=request.getServletContext().getRealPath("file");
            //获取上传的文件名
            String name=img.getOriginalFilename();
            //在要保存的路径内创建一个空的文件，文件名为字段 name
            File folder=new File(path);
            if (!folder.exists()){
                folder.mkdir();
            }
            File file=new File(path,name);
            //将 img 的 数据 赋到该空文件中
            try {
                img.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //保存上传之后的文件路径到list集合
            files.add("/file/"+name);
        }
        //将list集合传回前端
        request.setAttribute("files", files);
    }
    return "uploads";
}
```

前端代码，预览采用循环读取list中的文件（jstl 标签和standard标签）：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/file/uploads" method="post" enctype="multipart/form-data">
    文件1：<input type="file" name="imgs"/><br/>
    文件2： <input type="file" name="imgs"/><br/>
    文件3：<input type="file" name="imgs"/><br/>
    <input type="submit" value="上传"/>
</form>
<c:forEach items="${files}" var="file">
    <img src="${file}" width="300px"/><br/><hr/><br/>
</c:forEach>
</body>
</html>
```

添加 jstl 标签 和 standard 标签 的相关依赖(pom.xml)：

```xml
<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
</dependency>
<dependency>
  <groupId>taglibs</groupId>
  <artifactId>standard</artifactId>
  <version>1.1.2</version>
</dependency>
```

上传前预览:

![image-20200904161818268](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904161818268.png)

上传后预览：

![image-20200904161831702](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904161831702.png)

### 3、文件下载

下载相当于反向的上传，可以在前端定义一个超链接，点击超链接即可实现下载。

后台已有的三张图片：

![image-20200904165825429](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904165825429.png)

前端代码：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/file/download/1">下载图片1</a>
<a href="/file/download/2">下载图片2</a>
<a href="/file/download/3">下载图片3</a>
</body>
</html>
```

业务方法实现：

```java
 @GetMapping("/download/{name}")
public void download(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response){
        //判断参数是否为null
        if(name!=null){
            //获取文件绝对路径
            String path=request.getServletContext().getRealPath("file");
            //获取要下载的文件
            File file =new File(path,name+".png");
            //创建输出流用于下载文件
            OutputStream stream=null;
            //判断文件是否存在
            if(file.exists()){
                //设置下载需要的属性
                response.setContentType("application/force-download");
                response.setHeader( "Content-Disposition","attachment;filename="+name);
                try {
                    //对接设置的属性
                    stream=response.getOutputStream();
                    //文件写入，完成下载
                    stream.write(FileUtils.readFileToByteArray(file));
                    stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    //关闭输出流
                    if(stream != null){
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
}
```

前端页面：

![image-20200904165034820](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904165034820.png)

点击下载：

![image-20200904170455341](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904170455341.png)

可以看到已经下载到了本地。

## 六、Spring MVC 表单标签库

form 标签库通过`<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>`的导入方式为jsp页面加入 form 标签库，再通过 `modelAttribute`属性绑定具体的模型数据。

### input 标签

* 使用标签库前：

  业务方法：

  ```java
  @GetMapping("/get")
  public ModelAndView get(){
      ModelAndView modelAndView=new ModelAndView("show");
      Student student =new Student(1L,"张三",20);
      modelAndView.addObject("student",student);
      return modelAndView;
  }
  ```

  前端代码：

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page isELIgnored="false" %>
  <html>
  <head>
      <title>Title</title>
  </head>
  <body>
  <h1>学生信息</h1>
  <form>
      学生ID：<input type="text" name="id" value="${student.id}"/><br/>
      学生姓名：<input type="text" name="name" value="${student.name}"/><br/>
      学生年龄：<input type="text" name="age" value="${student.age}"/><br/>
      <input type="submit" value="提交"/>
  </form>
  </body>
  </html>
  ```

  前端显示：

  ![image-20200904171948070](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904171948070.png)

* 使用标签库后

  业务方法不变。

  前端代码：

  JSP 中
  
  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page isELIgnored="false" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <html>
  <head>
      <title>Title</title>
  </head>
  <body>
  <h1>学生信息</h1>
  <form:form modelAttribute="student">
      学生ID：<form:input path="id"></form:input><br/>
      学生姓名：<form:input path="name"></form:input><br/>
      学生年龄：<form:input path="age"></form:input><br/>
      <input type="submit" value="提交"/>
  </form:form>
  </body>
</html>
  ```

  前端显示：![image-20200904172550518](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200904172550518.png)

  input 标签绑定的是模型数据中的属性值。通过 path 属性 可以与模型数据中的属性名对应，且支持级联操作（直接追加即可，类似于 javaBean 中的级联操作）。

### password 标签

​	`<form:password path="password"/>` 

相当于 `<input type="password"/>` ，通过  path 属性与模型数据的属性值进行数据绑定，password 标签的值不会在页面显示。

### checkbox 标签

```jsp
<form:checkbox path="hobby" value="读书" />
```

相当于：

```
<input type="checkbox" value="读书" name="hobby"/>
```

checkbox 标签通过 path 属性 与模型数据中的属性值进行绑定，可以绑定的类型有：Boolean 、数组 和集合。

如果绑定 Boolean 类型 ，变量值 为 true ，则复选框选择，否则不选中。

成员变量：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
}
```

业务方法层（male 为 true）：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student(1L,"张三",20,true);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form>
    学生ID：<input type="text" name="id" value="${student.id}"/><br/>
    学生姓名：<input type="text" name="name" value="${student.name}"/><br/>
    学生年龄：<input type="text" name="age" value="${student.age}"/><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
```

显示（已选中）：

![image-20200908112157239](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908112157239.png)

如果绑定的是集合或者数组，集合/数组中的元素等于checkbox 的value值 ，则选中，否则不选中。

成员变量：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private String[] hobby;
}
```

业务方法：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    String[] hobby ={"吃","睡","玩"};
    student.setHobby(hobby);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkbox path="hobby" value="摄影"/>摄影<br/>
    <form:checkbox path="hobby" value="读书"/>读书<br/>
    <form:checkbox path="hobby" value="吃"/>吃<br/>
    <form:checkbox path="hobby" value="睡"/>睡<br/>
    <form:checkbox path="hobby" value="玩"/>玩<br/>
    <input type="submit" value="提交"/>
</form:form>
</body>
</html>
```

前端显示（value 值 对应 的 选中）：

![image-20200908113209686](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908113209686.png)

### checkboxes 标签

```jsp
<form:checkboxes item="${student.hobby}" path="selecthobbies" />
```

相当于对一组 chexkbox 进行处理， 一次解决多个checkbox标签。items遍历创建所有的checkbox，path绑定小集合，二者相交的部分会被选中。

实体类（以 List集合举例）：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private List<String> hobby;
    private List<String> selectHobbies;
}
```

业务方法：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
    student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：
    <form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
</form:form>
</body>
</html>
```

显示（交集被选中）：

![image-20200908114448654](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908114448654.png)

### radiobutton 标签

```jsp
<form:radiobutton /> 
```

相当于

```jsp
<input type="radio" />
```

若绑定的数据与标签内的 value 值相等则为选中，否则不选中。

实体类中：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private List<String> hobby;
    private List<String> selectHobbies;
    private int radioId;
}
```

业务方法中（radioId 值为 2）：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
    student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
    student.setRadioId(2);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
    radioButton：<form:radiobutton path="radioId" value="2"/><br/>
</form:form>
</body>
</html>
```

显示（值相等，所以选中）：

![image-20200908115335924](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908115335924.png)

### radiobuttons 标签

```jsp
<form:radiobuttons items="${student.grade}" path="selectgrade"/>
```

类似于checkbox 和checkboxs 的关系，他渲染的是一组 radiobutton 标签。

业务方法中（grade 为 2）：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
    student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
    student.setRadioId(2);
    Map<Integer,String> grade=new HashMap<>();
    grade.put(1,"一年级");
    grade.put(2,"二年级");
    grade.put(3,"三年级");
    modelAndView.addObject("grade",grade);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
    年级：<form:radiobuttons path="radioId" items="${grade}"/><br/>
</form:form>
</body>
</html>
```

显示（对应的二年级选中）：

![image-20200908120200561](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908120200561.png)

### select 标签

```jsp
<form:select items="${citys}" path="student.city"/>
```

对下拉框的处理。用法和 checkboxes 和radiobuttons 一致。

实体类：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private List<String> hobby;
    private List<String> selectHobbies;
    private int radioId;
    private String city;
}
```

业务方法（city 为郑州）：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
    student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
    student.setRadioId(2);
    student.setCity("郑州");
    Map<Integer,String> grade=new HashMap<>();
    grade.put(1,"一年级");
    grade.put(2,"二年级");
    grade.put(3,"三年级");
    modelAndView.addObject("grade",grade);
    String[] city={"洛阳","开封","郑州"};
    modelAndView.addObject("city",city);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
    年级：<form:radiobuttons path="radioId" items="${grade}"/><br/>
    城市：<form:select path="city" items="${city}"/><br/>
</form:form>
</body>
</html>
```

显示（默认选中郑州）：

![image-20200908121020405](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908121020405.png)

### option/options 标签

`form:select`结合`form:options`的使用。`form:select`之定义path属性，可以在其内部添加一个子标签`form:options`，设置 items属性，获取被遍历的集合。option 和 options 的关系与前面 radiobutton 和radiobuttons 标签、checkbox 和checkboxes 标签用法一致。

所以上述 select 标签前端可以改写为：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
    年级：<form:radiobuttons path="radioId" items="${grade}"/><br/>
    城市：<form:select path="city"><form:options items="${city}"/></form:select>
    <br/>
</form:form>
</body>
</html>
```

显示与原来一致：

![image-20200908141144145](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908141144145.png)

### textarea 标签

```jsp
<form:textarea path=""/>
```

相当于：

```jsp
<textarea></textarea>
```

方法类：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private List<String> hobby;
    private List<String> selectHobbies;
    private int radioId;
    private String city;
    private String introduce;
}
```

业务方法（给 introduce 赋值）：

```java
@GetMapping("/get")
public ModelAndView get(){
    ModelAndView modelAndView=new ModelAndView("tag");
    Student student =new Student();
    student.setId(1L);
    student.setName("张三");
    student.setAge(20);
    student.isMale();
    student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
    student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
    student.setRadioId(2);
    student.setCity("郑州");
    student.setIntroduce("活泼开朗，学习认真");
    Map<Integer,String> grade=new HashMap<>();
    grade.put(1,"一年级");
    grade.put(2,"二年级");
    grade.put(3,"三年级");
    modelAndView.addObject("grade",grade);
    String[] city={"洛阳","开封","郑州"};
    modelAndView.addObject("city",city);
    modelAndView.addObject("student",student);
    return modelAndView;
}
```

前端：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>学生信息</h1>
<form:form modelAttribute="student">
    学生ID：<form:input path="id"></form:input><br/>
    学生姓名：<form:input path="name"></form:input><br/>
    学生年龄：<form:input path="age"></form:input><br/>
    是否男生：<form:checkbox path="male" value="male"></form:checkbox><br/>
    爱好：<form:checkboxes path="selectHobbies" items="${student.hobby}"/><br/>
    年级：<form:radiobuttons path="radioId" items="${grade}"/><br/>
    城市：<form:select path="city"><form:options items="${city}"/></form:select><br/>
    简介：<form:textarea path="introduce"/><br/>
</form:form>
</body>
</html>
```

显示：

![image-20200908141958856](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908141958856.png)

### errors 标签

处理错误信息，一般用于数据校验。该标签不能单独使用，需要结合 Spring MVC 的验证器使用。

## 七、Spring MVC 数据校验

Spring MVC 提供了两种数据校验的方式：

* 基于 Validator 接口 的数据校验
* 使用 Annotation JSR - 303 标准进行数据校验

第一种种方式需要自定义 Validator 验证器 ，每一条数据的校验规则需要开发者手动完成。

第二种则不需要自定义验证器，通过注解的方式可以直接在实体类中添加每个属性的验证规则。

第二种方法更加方便，所以在实际开发中推荐使用。

### 1、基于Validator 接口的方式

定义实体类：

```java
package com.gloryh.entity;

import lombok.Data;

/**
 * 用于数据校验的实体类
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Data
public class Account {
    private String name;
    private String password;
}
```

自定义验证器 AccountValidator ，实现 Validator 接口：

```java
package com.gloryh.validator;

import com.gloryh.entity.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 自定义用于Account实体类的数据校验 验证器
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
public class AccountValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        //判断是否为 Account 实体类
        return Account.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //如果是 Account 实体类，首先进行非空验证
        ValidationUtils.rejectIfEmpty(errors,"name",null,"姓名不能为空！");
        ValidationUtils.rejectIfEmpty(errors,"password",null,"密码不能为空！");
        
    }
}
```

业务方法：

```java
package com.gloryh.controller;

import com.gloryh.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于数据校验的业务方法
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Controller
@RequestMapping("/validator")
public class ValidatorHandler {

    @GetMapping("/login")
    public String login(Model model){
        //使用方法初始化 前端中的对应实体类并完成绑定
        model.addAttribute("account",new Account());
        return "login";
    }
}
```

前端(用 error 标签显示错误信息):

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form modelAttribute="account" action="/validator/login" method="post">
    姓名：<form:input path="name"/><br/><form:errors path="name"></form:errors><br/>
    密码：<form:input path="password"/><br/><form:errors path="password"/><br/>
    <input type="submit" value="登录"/>
</form:form>
</body>
</html>
```

对数据进行校验：

```java
@PostMapping("/login")
public String login(@Validated Account account, BindingResult bindingResult){
    //使用 @Validated 对调用数据校验器数据进行验证，并返回绑定结果集，结果集中出现错误则返回原网页打印错误
    if(bindingResult.hasErrors()){
        return "login";
    }
    return "index";
}
```

要使数据验证器生效，要在 springmvc.xml 文件中进行相关配置：

```xml
<!-- 基于 Validator 的数据校验期配置 -->
<bean id="accountValidator" class="com.gloryh.validator.AccountValidator"></bean>
<mvc:annotation-driven validator="accountValidator"></mvc:annotation-driven>
```

前端进入页面,完成初始化和数据绑定（不能直接访问jsp页面，注意url）：

![image-20200908150211218](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908150211218.png)

不输入信息直接点击登录观察变化：

![image-20200908150244510](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908150244510.png)

### 2、基于 Annotation JSR - 303 标准的数据校验

使用 Annotation JSR -303 标准 进行数据验证，需要导入支持这种标准的依赖（这里使用 Hibernate 的Validator 依赖包）。

pom.xml 中添加依赖：

```xml
<!--JSR 303 -->
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>6.0.14.Final</version>
</dependency>
<dependency>
  <groupId>javax.validation</groupId>
  <artifactId>validation-api</artifactId>
  <version>2.0.1.Final</version>
</dependency>
<dependency>
  <groupId>org.jboss.logging</groupId>
  <artifactId>jboss-logging</artifactId>
  <version>3.3.2.Final</version>
</dependency>
```

通过注解的方式直接在实体类中添加相关的验证规则：

```java
package com.gloryh.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用于 Annotation JSR -303 的数据校验实体类
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Data
public class Person {
    @NotEmpty(message = "用户名不能为空")
    private String name;

    @Size(min = 6,max =12,message = "密码长度应该为6-12位")
    private String password;
    @Email(regexp = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$",message = "请输入正确的邮箱格式")
    private String email;
    @Pattern(regexp = "/^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}$/",message = "请输入正确的手机号")
    private String phone;
}
```

业务方法（也需要两个方法，一个用于绑定数据，一个用于数据校验）：

```java
@GetMapping("/register")
public String register(Model model){
    //使用方法初始化 前端中的对应实体类并完成绑定
    model.addAttribute("person",new Person());
    return "register2";
}
@PostMapping("/register")
public String register(@Valid Person person, BindingResult bindingResult ){
    //使用 @Valid 对调用数据校验器数据进行验证，并返回绑定结果集，结果集中出现错误则返回原网页打印错误
    if (bindingResult.hasErrors()){
        return "register2";
    }
    return "login";
}
```

要使数据验证器生效，要在 springmvc.xml 文件中进行相关配置，使用 Annotation JSR -303 标准只需要启用

`<mvc:annotation-driven></mvc:annotation-driven>`即可，而不需要多余的其他配置。

```xml
<!-- 配置 Annotation JSR -303 -->
<mvc:annotation-driven></mvc:annotation-driven>
```

前端页面：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form modelAttribute="person" action="/validator/register" method="post">
    姓名：<form:input path="name"/><br/><form:errors path="name"></form:errors><br/>
    密码：<form:input path="password"/><br/><form:errors path="password"/><br/>
    邮箱：<form:input path="email"/><br/><form:errors path="email"/><br/>
    电话：<form:input path="phone"/><br/><form:errors path="phone"/><br/>
    <input type="submit" value="注册"/>
</form:form>
</body>
</html>
```

前端进入页面,完成初始化和数据绑定（不能直接访问jsp页面，注意url）：

![image-20200908154709798](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908154709798.png)

不输入信息直接点击注册按钮观察变化：

![image-20200908155300797](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200908155300797.png)

注解解释：

* @NUll              被注解的元素必须为null
* @NotNull        被注解的元素不能为 null     
* @Min                被注解的元素必须为数字，且必须大于等于指定的值
* @Max               被注解的元素必须为数字，且必须小于于等于指定的值
* @Email             被注解的元素必须是电子邮箱地址
* @Size                被注解的元素必须为数字，且符合规定的标准
* @Pattern           被注解的元素必须符合对应的表达式
* @Length           被注解的元素的大小必须在指定的范围内
* @NotEmpty     被注解的元素被注解的字符串必须为非空字符串 （`String str =null` 和`String str=“”` 有本质的区别）