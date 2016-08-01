<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="header.jspf" %>

<c:if test="${!empty errorMsg}">
    <div style="color:red">${errorMsg}</div>
</c:if>
<a href="<c:url value="/blog/create.html" />" >创建博客</a> &nbsp;
<a href="<c:url value="/management/category/listAll.html" />" >分类管理</a> &nbsp;
<a href="<c:url value="/management/tag/listAll.html" />" >标签管理</a> &nbsp;
<a href="<c:url value="/management/friendlyLink/listAll.html" />" >友链管理</a> &nbsp;
<a href="<c:url value="/management/bookRecommendation/listAll.html" />" >书籍推荐管理</a> &nbsp;
<a href="<c:url value="/management/postRecommendation/listAll.html" />" >文章推荐管理</a> &nbsp;

<table border="1px" width="100%">
    <tr>
        <td>id</td>
        <td>标题</td>
        <td>创建时间</td>
        <td>更改时间</td>
        <td>创建人</td>
        <td>展现数</td>
        <td>状态</td>
        <td>操作列</td>
    </tr>
    <c:forEach var="blog" items="${allBlogs}">
        <tr>
            <td>${blog.id}</td>
            <td>${blog.title}</td>
            <td>${blog.createTime}</td>
            <td>${blog.updateTime}</td>
            <td>${blog.userName}</td>
            <td>${blog.showCount}</td>
            <td>${blog.status}</td>
            <td><a href="<c:url value="/blog/delete/${blog.id}.html"/>">物理删除</a> &nbsp;
                <a href="<c:url value="/blog/update/${blog.id}.html"/>">更新</a></td>
        </tr>
    </c:forEach>
</table>

<%@ include file="footer.jspf" %>
