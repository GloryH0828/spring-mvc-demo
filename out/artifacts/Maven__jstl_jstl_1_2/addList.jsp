<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/9/1
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
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
