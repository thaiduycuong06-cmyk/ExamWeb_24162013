<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Xác thực OTP</title>
</head>
<body>
    <h3>Nhập mã OTP</h3>
    <p style="color:red">${mess}</p>
    <form action="<c:url value='/verify'/>" method="post">
        OTP: <input type="text" name="otp" required/><br/><br/>
        <button type="submit">Xác thực</button>
    </form>
</body>
</html>
