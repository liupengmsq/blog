<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="header.jspf" %>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form name='loginForm'
          action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />"
          method='POST'>
        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='username'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                                       value="submit" /></td>
            </tr>
        </table>
    </form>

<%@ include file="footer.jspf" %>
