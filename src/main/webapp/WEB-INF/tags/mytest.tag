<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="myattr" required="true" rtexprvalue="true" type="java.util.ArrayList" %>

<c:forEach var="blog" items="${myattr}">
    ${blog.id}, ${blog.userName}
</c:forEach>
