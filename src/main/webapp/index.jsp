<%--
  Created by IntelliJ IDEA.
  User: Timur
  Date: 16.01.2024
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<a href="/time">Узнать текущее время сервера</a>
Поддерживает ли он русский язык?
<%
    //А тут можно писать Java-код

    String s = "Вся власть роботам!";
    for(int i=0; i<10; i++)
    {
        out.println(s);
        out.println("<br>");
    }

%>
</body>
</html>
