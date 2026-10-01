<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Chi tiết: ${video.title}</title>
    <style>
        .detail-box { display: flex; border: 1px solid #dee2e6; border-radius: 8px; padding: 20px; background: #fff; gap: 25px; flex-wrap: wrap; }
        .poster { width: 35%; min-width: 250px; height: 220px; background: #e9ecef; display: flex; align-items: center; justify-content: center; border-radius: 6px; font-size: 16px; color: #6c757d; }
        .info { flex: 1; min-width: 280px; }
        .price { font-size: 24px; font-weight: bold; color: #dc3545; margin: 12px 0; }
        .stock { font-size: 14px; color: #198754; font-weight: 600; margin-bottom: 15px; }
        .desc-box { border: 1px solid #dee2e6; border-radius: 8px; padding: 15px; margin-top: 20px; background: #fafafa; }
        .btn-add { background-color: #198754; color: white; border: none; padding: 10px 20px; border-radius: 4px; font-weight: bold; font-size: 15px; cursor: pointer; }
        .btn-add:hover { background-color: #157347; }
    </style>
</head>
<body>
    <a href="<c:url value='/home'/>" style="text-decoration: none; color: #0d6efd; font-weight: 500; display: inline-block; margin-bottom: 15px;">← Quay lại danh sách sản phẩm</a>

    <div class="detail-box">
        <div class="poster">
            [${video.poster}]
        </div>
        <div class="info">
            <h2 style="margin-top: 0;">${video.title}</h2>
            <div class="price"><fmt:formatNumber value="${video.price != null ? video.price : 50000}" type="number"/> VNĐ</div>
            <div class="stock">📦 Tồn kho hiện có: ${video.quantity != null ? video.quantity : 50} sản phẩm</div>
            <p><strong>Mã video:</strong> ${video.videoId}</p>
            <p><strong>Danh mục:</strong> ${video.category.categoryname}</p>
            <p><strong>Lượt xem:</strong> ${video.views}</p>
            <p><strong>Chia sẻ:</strong> ${shareCount} | <strong>Yêu thích:</strong> ${likeCount}</p>

            <form action="<c:url value='/cart/add'/>" method="post" style="margin-top: 20px; display: flex; align-items: center; gap: 10px;">
                <input type="hidden" name="videoId" value="${video.videoId}"/>
                <input type="hidden" name="redirect" value="detail"/>
                <label for="qtyInput"><strong>Số lượng:</strong></label>
                <input type="number" id="qtyInput" name="quantity" value="1" min="1" max="${video.quantity != null ? video.quantity : 50}" style="width: 60px; padding: 8px; text-align: center; border: 1px solid #ced4da; border-radius: 4px;" required/>
                <button type="submit" class="btn-add">🛒 Thêm Vào Giỏ Hàng</button>
            </form>
        </div>
    </div>

    <div class="desc-box">
        <h4 style="margin-top: 0;">Mô tả chi tiết:</h4>
        <p>${empty video.description ? 'Không có mô tả chi tiết cho sản phẩm/video này.' : video.description}</p>
    </div>

</body>
</html>
