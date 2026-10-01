<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng ký</title>
</head>
<body>
    <h3>Đăng ký</h3>
    <p style="color:red">${mess}</p>
    <form action="<c:url value='/register'/>" method="post">
        Username: <input type="text" name="username" required/><br/><br/>
        Email: <input type="email" name="email" required/><br/><br/>
        Password: <input type="password" name="password" required/><br/><br/>
        <button type="submit">Register</button>
    </form>
</body>
</html>
