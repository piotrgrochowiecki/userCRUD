<%--
  Created by IntelliJ IDEA Ultimate.
  User: piotr
  Date: 26.04.2022
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${user.userName} details</title>
</head>
<body>
<div>
    ID: ${user.id}<br>
    Name: ${user.userName}<br>
    E-mail: ${user.email}<br>
</div>
</body>
</html>
