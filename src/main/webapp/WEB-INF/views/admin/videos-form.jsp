<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Form Video</title>
</head>
<body>
    <h3>${video != null ? 'Cập nhật' : 'Thêm mới'} Video</h3>
    <form action="<c:url value='/admin/videos'/>" method="post">
        Video ID: <input type="text" name="videoId" value="${video.videoId}" ${video != null ? 'readonly' : ''} required/><br/><br/>
        Title: <input type="text" name="title" value="${video.title}" required/><br/><br/>
        Poster: <input type="text" name="poster" value="${video.poster}"/><br/><br/>
        Description: <textarea name="description">${video.description}</textarea><br/><br/>
        Views: <input type="number" name="views" value="${video != null ? video.views : 0}"/><br/><br/>
        Active: 
        <input type="radio" name="active" value="true" ${video == null || video.active == true ? 'checked' : ''}/> True
        <input type="radio" name="active" value="false" ${video != null && video.active == false ? 'checked' : ''}/> False
        <br/><br/>
        Category: 
        <select name="categoryId">
            <c:forEach var="c" items="${categories}">
                <option value="${c.categoryId}" ${video != null && video.category.categoryId == c.categoryId ? 'selected' : ''}>${c.categoryname}</option>
            </c:forEach>
        </select>
        <br/><br/>
        <button type="submit">Lưu</button>
    </form>
</body>
</html>
