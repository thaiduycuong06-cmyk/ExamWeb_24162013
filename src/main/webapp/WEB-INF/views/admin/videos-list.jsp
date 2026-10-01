<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý Video</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        .pagination a { padding: 5px 10px; border: 1px solid #ccc; margin-right: 5px; text-decoration: none; }
    </style>
</head>
<body>
    <h3>Danh sách Video</h3>
    <a href="<c:url value='/admin/videos/add'/>" style="display:inline-block; margin-bottom: 10px;">Thêm mới</a>
    
    <table>
        <tr>
            <th>Video ID</th>
            <th>Title</th>
            <th>Poster</th>
            <th>Views</th>
            <th>Active</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        <c:forEach var="v" items="${videos}">
            <tr>
                <td>${v.videoId}</td>
                <td>${v.title}</td>
                <td>${v.poster}</td>
                <td>${v.views}</td>
                <td>${v.active}</td>
                <td>${v.category.categoryname}</td>
                <td>
                    <a href="<c:url value='/admin/videos/edit?id=${v.videoId}'/>">Edit</a> |
                    <a href="<c:url value='/admin/videos/delete?id=${v.videoId}'/>" onclick="return confirm('Bạn có chắc xóa?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <div class="pagination" style="margin-top: 15px;">
        <c:forEach begin="1" end="${totalPage}" var="i">
            <a href="<c:url value='/admin/videos?page=${i}'/>" style="${i == currentPage ? 'font-weight:bold; background-color:#ddd' : ''}">${i}</a>
        </c:forEach>
    </div>
</body>
</html>
