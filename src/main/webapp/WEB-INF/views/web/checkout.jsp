<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Thanh toán đơn hàng (COD)</title>
    <style>
        .checkout-container { display: flex; gap: 30px; margin-top: 20px; flex-wrap: wrap; }
        .checkout-form { flex: 1.2; min-width: 320px; }
        .order-summary { flex: 0.8; min-width: 300px; background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 8px; padding: 20px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; font-weight: 600; color: #333; }
        .form-control { width: 100%; padding: 10px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; }
        .payment-method-box { background: #e7f1ff; border: 1px solid #b6d4fe; border-radius: 6px; padding: 15px; margin-top: 15px; }
        .btn-submit { background-color: #198754; color: white; padding: 14px 20px; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; width: 100%; cursor: pointer; transition: background-color 0.2s; }
        .btn-submit:hover { background-color: #157347; }
        .item-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #dee2e6; font-size: 14px; }
        .alert-danger { background-color: #f8d7da; color: #842029; padding: 12px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #f5c2c7; }
    </style>
</head>
<body>

    <h2>💳 Thanh Toán Đơn Hàng (COD)</h2>

    <c:if test="${not empty error}">
        <div class="alert-danger">${error}</div>
    </c:if>

    <form action="<c:url value='/checkout'/>" method="post">
        <div class="checkout-container">
            
            <!-- Cột thông tin nhận hàng -->
            <div class="checkout-form">
                <h3 style="border-bottom: 2px solid #0d6efd; padding-bottom: 8px; margin-top: 0;">Thông tin người nhận hàng</h3>
                
                <div class="form-group">
                    <label for="receiverName">Họ và tên người nhận (*):</label>
                    <input type="text" id="receiverName" name="receiverName" class="form-control" value="${sessionScope.account.fullname}" required placeholder="Ví dụ: Thái Duy Cường" />
                </div>

                <div class="form-group">
                    <label for="receiverPhone">Số điện thoại (*):</label>
                    <input type="text" id="receiverPhone" name="receiverPhone" class="form-control" value="${sessionScope.account.phone}" required placeholder="Ví dụ: 0912345678" />
                </div>

                <div class="form-group">
                    <label for="receiverAddress">Địa chỉ nhận hàng chi tiết (*):</label>
                    <textarea id="receiverAddress" name="receiverAddress" class="form-control" rows="3" required placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố"></textarea>
                </div>

                <div class="form-group">
                    <label for="note">Ghi chú cho shipper:</label>
                    <textarea id="note" name="note" class="form-control" rows="2" placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi đến..."></textarea>
                </div>

                <div class="payment-method-box">
                    <h4 style="margin: 0 0 10px 0; color: #0d6efd;">Phương thức thanh toán</h4>
                    <label style="display: flex; align-items: center; gap: 10px; cursor: pointer; font-weight: 500;">
                        <input type="radio" name="paymentMethod" value="COD" checked readonly />
                        <span><strong>COD</strong> - Thanh toán tiền mặt khi nhận hàng (Cash On Delivery)</span>
                    </label>
                    <div style="font-size: 13px; color: #6c757d; margin-top: 6px; margin-left: 24px;">
                        Bạn chỉ cần thanh toán tiền mặt cho shipper sau khi đã kiểm tra kiện hàng.
                    </div>
                </div>
            </div>

            <!-- Cột tóm tắt đơn hàng -->
            <div class="order-summary">
                <h3 style="border-bottom: 2px solid #198754; padding-bottom: 8px; margin-top: 0;">Tóm tắt đơn hàng</h3>
                
                <div style="margin-bottom: 15px;">
                    <c:forEach var="item" items="${sessionScope.cart.items}">
                        <div class="item-row">
                            <div>
                                <strong>${item.video.title}</strong>
                                <div style="font-size: 12px; color: #6c757d;">SL: ${item.quantity} x <fmt:formatNumber value="${item.video.price != null ? item.video.price : 50000}" type="number"/> đ</div>
                            </div>
                            <div style="font-weight: 600;">
                                <fmt:formatNumber value="${item.subTotal}" type="number"/> đ
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div style="border-top: 2px solid #dee2e6; padding-top: 15px; margin-top: 15px;">
                    <div style="display: flex; justify-content: space-between; font-size: 15px; margin-bottom: 8px;">
                        <span>Số lượng sản phẩm:</span>
                        <strong>${sessionScope.cart.totalQuantity}</strong>
                    </div>
                    <div style="display: flex; justify-content: space-between; font-size: 15px; margin-bottom: 8px;">
                        <span>Phí vận chuyển:</span>
                        <span style="color: #198754; font-weight: bold;">Miễn phí (0 đ)</span>
                    </div>
                    <div style="display: flex; justify-content: space-between; font-size: 18px; margin-top: 12px; font-weight: bold; color: #dc3545;">
                        <span>Tổng thanh toán:</span>
                        <span><fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number"/> VNĐ</span>
                    </div>
                </div>

                <div style="margin-top: 25px;">
                    <button type="submit" class="btn-submit">✅ Xác Nhận Đặt Hàng COD</button>
                    <a href="<c:url value='/cart'/>" style="display: block; text-align: center; margin-top: 12px; color: #6c757d; text-decoration: none;">← Quay lại giỏ hàng</a>
                </div>
            </div>

        </div>
    </form>

</body>
</html>
