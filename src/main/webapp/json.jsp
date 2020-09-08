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
