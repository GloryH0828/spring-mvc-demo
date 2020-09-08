<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/9/4
  Time: 17:20
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
