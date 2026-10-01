<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><decorator:title default='Trang Web'/></title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }
        header { background-color: #0d6efd; color: white; padding: 15px 30px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        header h2 { margin: 0 0 10px 0; font-size: 24px; }
        nav { display: flex; align-items: center; flex-wrap: wrap; gap: 15px; }
        header a { color: white; text-decoration: none; font-weight: 500; padding: 6px 12px; border-radius: 4px; transition: background-color 0.2s; }
        header a:hover { background-color: rgba(255,255,255,0.2); }
        .cart-badge { background-color: #ffc107; color: #000; font-weight: bold; border-radius: 50%; padding: 2px 7px; font-size: 12px; margin-left: 5px; }
        footer { background-color: #e9ecef; padding: 15px; text-align: center; margin-top: 40px; border-top: 1px solid #dee2e6; color: #495057; }
        .container { min-height: 480px; max-width: 1200px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .user-greeting { margin-left: auto; color: #e0f2fe; }
    </style>
    <decorator:head/>
</head>
<body>
    <header>
        <h2>Trang Web - Bài Kiểm Tra Quá Trình</h2>
        <nav>
            <a href="<c:url value='/home'/>">🏠 Trang Chủ</a>
            <a href="<c:url value='/home'/>">📦 Sản phẩm</a>
            <a href="<c:url value='/cart'/>">🛒 Giỏ hàng <span class="cart-badge">${sessionScope.cart != null ? sessionScope.cart.totalItems : 0}</span></a>
            
            <c:choose>
                <c:when test="${empty sessionScope.account}">
                    <a href="<c:url value='/login'/>" style="margin-left: auto;">Đăng nhập</a>
                    <a href="<c:url value='/register'/>">Đăng ký</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/order/history'/>">📋 Đơn hàng của tôi</a>
                    <span class="user-greeting">Xin chào, <strong>${sessionScope.account.fullname}</strong></span>
                    <c:if test="${sessionScope.account.admin == true}">
                        <a href="<c:url value='/admin/home'/>" style="background-color: #dc3545;">Trang quản trị</a>
                    </c:if>
                    <a href="<c:url value='/logout'/>" style="background-color: rgba(0,0,0,0.2);">Đăng xuất</a>
                </c:otherwise>
            </c:choose>
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
