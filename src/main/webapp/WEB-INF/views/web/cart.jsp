<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Giỏ hàng của bạn</title>
    <style>
        .cart-table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .cart-table th, .cart-table td { border: 1px solid #dee2e6; padding: 12px; text-align: center; }
        .cart-table th { background-color: #f1f3f5; color: #333; }
        .product-info { display: flex; align-items: center; gap: 15px; text-align: left; }
        .product-poster { width: 70px; height: 50px; background: #e9ecef; display: flex; align-items: center; justify-content: center; border-radius: 4px; font-size: 11px; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 20px; font-weight: 500; }
        .alert-success { background-color: #d1e7dd; color: #0f5132; border: 1px solid #badbcc; }
        .alert-warning { background-color: #fff3cd; color: #664d03; border: 1px solid #ffecb5; }
        .btn { display: inline-block; padding: 8px 16px; border-radius: 4px; text-decoration: none; font-weight: bold; cursor: pointer; border: none; }
        .btn-primary { background-color: #0d6efd; color: white; }
        .btn-success { background-color: #198754; color: white; font-size: 16px; }
        .btn-danger { background-color: #dc3545; color: white; }
        .btn-secondary { background-color: #6c757d; color: white; }
        .qty-input { width: 60px; padding: 6px; text-align: center; border: 1px solid #ced4da; border-radius: 4px; }
        .cart-actions { display: flex; justify-content: space-between; align-items: center; margin-top: 25px; flex-wrap: wrap; gap: 15px; }
        .total-box { font-size: 20px; font-weight: bold; color: #d63384; }
    </style>
</head>
<body>

    <h2>🛒 Giỏ Hàng Của Bạn</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty warning}">
        <div class="alert alert-warning">${warning}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty sessionScope.cart || empty sessionScope.cart.items}">
            <div style="text-align: center; padding: 50px 20px;">
                <p style="font-size: 18px; color: #6c757d;">Giỏ hàng của bạn đang trống!</p>
                <a href="<c:url value='/home'/>" class="btn btn-primary" style="margin-top: 15px;">👉 Tiếp tục mua sắm</a>
            </div>
        </c:when>
        <c:otherwise>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th style="width: 35%;">Sản phẩm / Video</th>
                        <th>Đơn giá</th>
                        <th>Tồn kho</th>
                        <th style="width: 20%;">Số lượng (Giới hạn kho)</th>
                        <th>Thành tiền</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${sessionScope.cart.items}">
                        <tr>
                            <td>
                                <div class="product-info">
                                    <div class="product-poster">[${item.video.poster}]</div>
                                    <div>
                                        <strong><a href="<c:url value='/video/detail?id=${item.video.videoId}'/>" style="color: #0d6efd; text-decoration: none;">${item.video.title}</a></strong>
                                        <div style="font-size: 12px; color: #6c757d;">Mã: ${item.video.videoId}</div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.video.price != null ? item.video.price : 50000}" type="number"/> đ
                            </td>
                            <td>
                                <span style="background: #e2e3e5; padding: 2px 8px; border-radius: 4px; font-weight: bold;">
                                    ${item.video.quantity != null ? item.video.quantity : 50}
                                </span>
                            </td>
                            <td>
                                <form action="<c:url value='/cart/update'/>" method="post" style="display: flex; justify-content: center; align-items: center; gap: 6px; margin: 0;">
                                    <input type="hidden" name="videoId" value="${item.video.videoId}"/>
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.video.quantity != null ? item.video.quantity : 50}" class="qty-input" required/>
                                    <button type="submit" class="btn btn-secondary" style="padding: 6px 10px; font-size: 13px;" title="Cập nhật số lượng trong giới hạn">Cập nhật</button>
                                </form>
                            </td>
                            <td style="font-weight: bold; color: #0d6efd;">
                                <fmt:formatNumber value="${item.subTotal}" type="number"/> đ
                            </td>
                            <td>
                                <a href="<c:url value='/cart/delete?id=${item.video.videoId}'/>" class="btn btn-danger" style="padding: 6px 12px; font-size: 13px;" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ?');">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="cart-actions">
                <div>
                    <a href="<c:url value='/home'/>" class="btn btn-secondary">← Tiếp tục xem sản phẩm</a>
                    <a href="<c:url value='/cart/clear'/>" class="btn btn-danger" style="margin-left: 10px;" onclick="return confirm('Bạn có muốn xóa toàn bộ giỏ hàng?');">Xóa toàn bộ giỏ</a>
                </div>
                <div style="text-align: right;">
                    <div class="total-box">
                        Tổng tiền: <fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number"/> VNĐ
                    </div>
                    <div style="margin-top: 10px;">
                        <a href="<c:url value='/checkout'/>" class="btn btn-success" style="padding: 12px 24px;">🚀 Tiến hành thanh toán (COD)</a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</body>
</html>
