<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Lịch sử đặt hàng</title>
    <style>
        .status-tabs { display: flex; gap: 8px; flex-wrap: wrap; margin-bottom: 25px; border-bottom: 2px solid #dee2e6; padding-bottom: 12px; }
        .tab-item { padding: 8px 16px; border-radius: 20px; text-decoration: none; color: #495057; background: #e9ecef; font-weight: 500; font-size: 14px; transition: all 0.2s; display: inline-flex; align-items: center; gap: 6px; }
        .tab-item:hover { background: #ced4da; }
        .tab-item.active { background: #0d6efd; color: white; }
        .tab-count { background: rgba(0,0,0,0.15); padding: 1px 7px; border-radius: 10px; font-size: 12px; font-weight: bold; }
        .tab-item.active .tab-count { background: rgba(255,255,255,0.3); }

        .order-card { border: 1px solid #dee2e6; border-radius: 8px; margin-bottom: 20px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.03); }
        .order-header { background: #f8f9fa; padding: 12px 18px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #dee2e6; flex-wrap: wrap; gap: 10px; }
        .order-body { padding: 18px; }
        .order-footer { background: #fafafa; padding: 12px 18px; display: flex; justify-content: space-between; align-items: center; border-top: 1px solid #dee2e6; }

        .status-badge { padding: 5px 12px; border-radius: 15px; font-weight: bold; font-size: 13px; color: white; display: inline-block; }
        .badge-new { background-color: #0d6efd; }          /* Đơn hàng mới */
        .badge-confirmed { background-color: #0dcaf0; color: #000; }    /* Đã xác nhận */
        .badge-preparing { background-color: #6f42c1; }    /* Chuẩn bị hàng */
        .badge-shipping { background-color: #fd7e14; }     /* Vận chuyển */
        .badge-delivering { background-color: #ffc107; color: #000; }   /* Giao hàng */
        .badge-delivered { background-color: #198754; }    /* Đã giao */
        .badge-cancelled { background-color: #dc3545; }    /* Đơn hàng hủy */
        .badge-returned { background-color: #6c757d; }     /* Đơn hàng hoàn */

        .order-items-table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        .order-items-table th, .order-items-table td { border-bottom: 1px solid #f1f1f1; padding: 8px 10px; text-align: left; }
        .order-items-table th { color: #6c757d; font-size: 13px; font-weight: 600; }
        
        .tip-box { background: #e8f4fd; border-left: 4px solid #0d6efd; padding: 12px 16px; margin-bottom: 20px; border-radius: 0 4px 4px 0; font-size: 14px; color: #084298; }
        .alert-success { background-color: #d1e7dd; color: #0f5132; padding: 12px 16px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #badbcc; }
    </style>
</head>
<body>

    <h2>📋 Lịch Sử Đặt Hàng & Quản Lý Trạng Thái</h2>

    <c:if test="${not empty successMessage}">
        <div class="alert-success">🎉 ${successMessage}</div>
    </c:if>

    <div class="tip-box">
        💡 <strong>Hướng dẫn quan sát trạng thái theo yêu cầu đề bài:</strong><br/>
        Bạn có thể mở MySQL Workbench, sửa giá trị cột <code>status</code> trong bảng <code>Orders</code> thành một trong các trạng thái:
        <em>"Đơn hàng mới", "Đã xác nhận", "Chuẩn bị hàng", "Vận chuyển", "Giao hàng", "Đã giao", "Đơn hàng hủy", "Đơn hàng hoàn"</em>, sau đó bấm <strong>Apply</strong> và <strong>F5</strong> lại trang này để kiểm chứng đơn hàng tự động lọc tương ứng!
    </div>

    <!-- Thanh Tabs lọc theo 8 trạng thái -->
    <div class="status-tabs">
        <a href="<c:url value='/order/history?status=all'/>" class="tab-item ${currentStatus == 'all' ? 'active' : ''}">
            Tất cả <span class="tab-count">${countMap['all']}</span>
        </a>
        <c:forEach var="st" items="${statusList}">
            <a href="<c:url value='/order/history?status=${st}'/>" class="tab-item ${currentStatus == st ? 'active' : ''}">
                ${st} <span class="tab-count">${countMap[st]}</span>
            </a>
        </c:forEach>
    </div>

    <!-- Danh sách đơn hàng -->
    <c:choose>
        <c:when test="${empty orders}">
            <div style="text-align: center; padding: 50px 20px; color: #6c757d;">
                <p style="font-size: 18px;">Không có đơn hàng nào trong trạng thái "<strong>${currentStatus == 'all' ? 'Tất cả' : currentStatus}</strong>"!</p>
                <a href="<c:url value='/home'/>" style="color: #0d6efd; font-weight: bold; text-decoration: none;">👉 Mua sắm sản phẩm ngay</a>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="ord" items="${orders}">
                <div class="order-card">
                    <div class="order-header">
                        <div>
                            <strong>Mã đơn hàng: #${ord.id}</strong> 
                            <span style="color: #6c757d; font-size: 13px; margin-left: 10px;">
                                Ngày đặt: <fmt:formatDate value="${ord.orderDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </span>
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${ord.status == 'Đơn hàng mới'}"><span class="status-badge badge-new">🆕 ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Đã xác nhận'}"><span class="status-badge badge-confirmed">✔️ ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Chuẩn bị hàng'}"><span class="status-badge badge-preparing">📦 ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Vận chuyển'}"><span class="status-badge badge-shipping">🚚 ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Giao hàng'}"><span class="status-badge badge-delivering">🛵 ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Đã giao'}"><span class="status-badge badge-delivered">✅ ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Đơn hàng hủy'}"><span class="status-badge badge-cancelled">❌ ${ord.status}</span></c:when>
                                <c:when test="${ord.status == 'Đơn hàng hoàn'}"><span class="status-badge badge-returned">↩️ ${ord.status}</span></c:when>
                                <c:otherwise><span class="status-badge badge-returned">${ord.status}</span></c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="order-body">
                        <div style="font-size: 14px; margin-bottom: 12px; line-height: 1.6;">
                            <div>👤 <strong>Người nhận:</strong> ${ord.receiverName} - 📞 ${ord.receiverPhone}</div>
                            <div>📍 <strong>Địa chỉ giao:</strong> ${ord.receiverAddress}</div>
                            <c:if test="${not empty ord.note}">
                                <div>📝 <strong>Ghi chú:</strong> <em>${ord.note}</em></div>
                            </c:if>
                            <div>💳 <strong>Thanh toán:</strong> <span style="background: #e7f1ff; color: #0d6efd; padding: 2px 6px; border-radius: 4px; font-weight: bold;">${ord.paymentMethod}</span></div>
                        </div>

                        <!-- Chi tiết sản phẩm trong đơn -->
                        <table class="order-items-table">
                            <thead>
                                <tr>
                                    <th>Sản phẩm / Video</th>
                                    <th>Đơn giá</th>
                                    <th>Số lượng</th>
                                    <th style="text-align: right;">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dt" items="${ord.orderDetails}">
                                    <tr>
                                        <td>
                                            <strong>${dt.video.title}</strong>
                                            <span style="font-size: 12px; color: #6c757d;">(Mã: ${dt.video.videoId})</span>
                                        </td>
                                        <td><fmt:formatNumber value="${dt.price}" type="number"/> đ</td>
                                        <td>${dt.quantity}</td>
                                        <td style="text-align: right; font-weight: 500;">
                                            <fmt:formatNumber value="${dt.subTotal}" type="number"/> đ
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="order-footer">
                        <span style="font-size: 14px; color: #6c757d;">Phương thức: COD (Thanh toán khi nhận hàng)</span>
                        <div style="font-size: 17px;">
                            Tổng thanh toán: <strong style="color: #dc3545; font-size: 19px;"><fmt:formatNumber value="${ord.totalAmount}" type="number"/> VNĐ</strong>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</body>
</html>
