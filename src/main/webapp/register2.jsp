<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/9/8
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
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
