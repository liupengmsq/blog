<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="header.jspf" %>

<form action="<c:url value="/management/tag/update.html"/>" method="post">
    <table border="1px"  width="100%">
        <tr>
            <td width="20%">旧的标签名</td>
            <td width="80%">${tag.name}</td>
        </tr>
        <tr>
            <td width="20%">新的标签名</td>
            <td width="80%"><input  type="text" name="newName"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="保存">
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${tag.id}"/>
</form>

<%@ include file="footer.jspf" %>
