<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="header.jspf" %>

<c:if test="${!empty errorMsg}">
    <div style="color:red">${errorMsg}</div>
</c:if>
<a href="<c:url value="/management/bookRecommendation/create.html"/>">创建</a><br/>
<table border="1px" width="100%">
    <tr>
        <td>id</td>
        <td>Link img src</td>
        <td>Link url</td>
        <td>Title</td>
        <td>操作列</td>
    </tr>
    <c:forEach var="fl" items="${allBookRecommendations}">
        <tr>
            <td>${fl.id}</td>
            <td>${fl.imgSrc}</td>
            <td>${fl.url}</td>
            <td>${fl.title}</td>
            <td><a href="<c:url value="/management/bookRecommendation/delete/${fl.id}.html"/>">删除</a> &nbsp;
        </tr>
    </c:forEach>
</table>

<%@ include file="footer.jspf" %>
