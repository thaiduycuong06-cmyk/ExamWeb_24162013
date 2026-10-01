<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><decorator:title default='Admin Panel'/></title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }
        header { background-color: #212529; color: white; padding: 15px 30px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        header h2 { margin: 0 0 10px 0; font-size: 22px; }
        nav { display: flex; align-items: center; gap: 15px; }
        header a { color: white; text-decoration: none; padding: 6px 12px; border-radius: 4px; }
        header a:hover { background-color: rgba(255,255,255,0.2); }
        footer { background-color: #e9ecef; padding: 15px; text-align: center; margin-top: 40px; border-top: 1px solid #dee2e6; color: #495057; }
        .container { min-height: 480px; max-width: 1200px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
    </style>
    <decorator:head/>
</head>
<body>
    <header>
        <h2>Trang Quản Trị Hệ Thống (Admin)</h2>
        <nav>
            <a href="<c:url value='/home'/>">🏠 Về Trang Chủ</a>
            <a href="<c:url value='/admin/home'/>">📊 Admin Dashboard</a>
            <a href="<c:url value='/admin/videos'/>">🎬 Quản lý Video</a>
            <c:if test="${not empty sessionScope.account}">
                <span style="margin-left: auto;">Xin chào Admin: <strong>${sessionScope.account.fullname}</strong> | </span>
                <a href="<c:url value='/logout'/>" style="background-color: #dc3545;">Đăng xuất</a>
            </c:if>
        </nav>
    </header>

    <div class="container">
        <decorator:body/>
    </div>

    <footer>
        <p>Họ tên: Thái Duy Cường | MSSV: 24162013 | Mã đề: 02</p>
    </footer>
</body>
</html>
