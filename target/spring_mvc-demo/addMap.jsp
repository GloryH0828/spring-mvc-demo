<%@ page contentType="text/html;charset=UTF-8" %>
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