<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="header.jspf" %>

<form action="<c:url value="/management/category/update.html"/>" method="post">
    <table border="1px"  width="100%">
        <tr>
            <td width="20%">旧的分类名</td>
            <td width="80%">${category.name}</td>
        </tr>
        <tr>
            <td width="20%">新的分类名</td>
            <td width="80%"><input  type="text" name="newName"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="保存">
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${category.id}"/>
</form>

<%@ include file="footer.jspf" %>
