<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/8/31
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
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
