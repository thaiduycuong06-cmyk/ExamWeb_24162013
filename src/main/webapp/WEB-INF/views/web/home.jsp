<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Trang chủ Sản phẩm & Video</title>
    <style>
        .category-section { margin-bottom: 30px; border: 1px solid #dee2e6; border-radius: 8px; padding: 18px; background: #fff; }
        .category-title { color: #0d6efd; margin-top: 0; border-bottom: 2px solid #0d6efd; padding-bottom: 8px; }
        .video-grid { display: flex; gap: 20px; flex-wrap: wrap; }
        .video-card { width: calc(33.333% - 14px); border: 1px solid #e9ecef; border-radius: 6px; padding: 15px; box-sizing: border-box; background: #fafafa; display: flex; flex-direction: column; justify-content: space-between; transition: transform 0.2s, box-shadow 0.2s; }
        .video-card:hover { transform: translateY(-3px); box-shadow: 0 4px 10px rgba(0,0,0,0.08); }
        .video-poster { height: 140px; background: #e9ecef; display: flex; align-items: center; justify-content: center; border-radius: 4px; color: #6c757d; font-weight: bold; }
        .video-card h4 { margin: 10px 0 5px 0; }
        .video-card h4 a { color: #212529; text-decoration: none; }
        .video-card h4 a:hover { color: #0d6efd; }
        .price-tag { font-size: 17px; font-weight: bold; color: #dc3545; margin: 6px 0; }
        .stock-tag { font-size: 13px; color: #198754; font-weight: 500; }
        .meta-info { font-size: 13px; color: #6c757d; margin: 3px 0; }
        .btn-add-cart { background-color: #198754; color: white; border: none; padding: 7px 12px; cursor: pointer; border-radius: 4px; font-weight: bold; flex: 1; transition: background-color 0.2s; }
        .btn-add-cart:hover { background-color: #157347; }
        .pagination { margin-top: 15px; display: flex; gap: 6px; }
        .pagination a { padding: 4px 10px; border: 1px solid #ced4da; text-decoration: none; border-radius: 4px; color: #0d6efd; }
        .pagination a.active { background-color: #0d6efd; color: white; border-color: #0d6efd; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Danh Sách Sản Phẩm / Video Theo Danh Mục</h2>

    <c:forEach var="cat" items="${categories}">
        <div class="category-section">
            <h3 class="category-title">${cat.categoryname} (${countMap[cat.categoryId]})</h3> <!-- Câu 5: Đếm số lượng video theo từng Category -->
            
            <div class="video-grid">
                <c:forEach var="v" items="${videoMap[cat.categoryId]}">
                    <div class="video-card">
                        <div>
                            <div class="video-poster">[${v.poster}]</div>
                            <h4><a href="<c:url value='/video/detail?id=${v.videoId}'/>">${v.title}</a></h4>
                            <div class="price-tag"><fmt:formatNumber value="${v.price != null ? v.price : 50000}" type="number"/> VNĐ</div>
                            <div class="stock-tag">Tồn kho: ${v.quantity != null ? v.quantity : 50} sản phẩm</div>
                            <div class="meta-info">Mã video: ${v.videoId} | View: ${v.views}</div>
                            <div class="meta-info">Share(10) | Like(10)</div>
                        </div>

                        <!-- Form thêm vào giỏ hàng có chọn số lượng trong giới hạn kho -->
                        <form action="<c:url value='/cart/add'/>" method="post" style="margin-top: 12px; display: flex; gap: 6px; align-items: center;">
                            <input type="hidden" name="videoId" value="${v.videoId}"/>
                            <input type="number" name="quantity" value="1" min="1" max="${v.quantity != null ? v.quantity : 50}" style="width: 50px; padding: 6px; text-align: center; border: 1px solid #ced4da; border-radius: 4px;" title="Số lượng mua trong giới hạn kho" required/>
                            <button type="submit" class="btn-add-cart">🛒 Thêm vào giỏ</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
            
            <c:if test="${totalPageMap[cat.categoryId] > 1}">
                <div class="pagination">
                    <span>&lt;&lt;</span>
                    <c:forEach begin="1" end="${totalPageMap[cat.categoryId]}" var="i">
                        <c:set var="cp" value="currentPage_${cat.categoryId}" />
                        <a href="<c:url value='/home?page_${cat.categoryId}=${i}'/>" class="${i == requestScope[cp] ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    <span>&gt;&gt;</span>
                </div>
            </c:if>
        </div>
    </c:forEach>

</body>
</html>
