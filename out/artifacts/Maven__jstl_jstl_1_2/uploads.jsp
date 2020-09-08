<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/9/4
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
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
