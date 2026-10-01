<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
    <h3>Đăng nhập</h3>
    <p style="color:red">${mess}</p>
    <form action="<c:url value='/login'/>" method="post">
        Username: <input type="text" name="username" required/><br/><br/>
        Password: <input type="password" name="password" required/><br/><br/>
        <button type="submit">Login</button>
    </form>
</body>
</html>
