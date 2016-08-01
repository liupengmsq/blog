<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ include file="header.jspf" %>--%>

<%--<h2>${blog.title}</h2>--%>
<%--<small>${blog.updateTime} by ${blog.userName} ${blog.showCount}</small>--%>
<%--<div class="entry">--%>
<%--<p>${blog.content}</p>--%>
<%--</div>--%>
<%--<ul class="postmetadata">--%>
<%--<li class="icon_cat">--%>
<%--<strong>Categories: </strong><a href="<c:url value="/category/${blog.categoryVo.id}.html"/>">${blog.categoryVo.name}</a>--%>
<%--</li>--%>
<%--<li class="icon_bullet">--%>
<%--<strong>Tags: </strong>--%>
<%--<c:forEach var="tag" items="${blog.tagVos}">--%>
<%--<a href="<c:url value="/tag/${tag.id}.html"/>">${tag.name}</a>&nbsp;--%>
<%--</c:forEach>--%>
<%--</li>--%>
<%--</ul>--%>
<%--<%@ include file="footer.jspf" %>--%>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jspf" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/lightbox.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/shCore.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/shThemeDefault.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shCore.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushPlain.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushJScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushCSharp.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushBash.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushJava.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushPython.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushSql.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushXml.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shBrushCpp.js"></script>
<script type="text/javascript">
    SyntaxHighlighter.all()
</script>

<div id="wrapper">
    <div id="content">
        <c:if test="${!empty category}">
            <h1>分类： ${category.name}</h1>
        </c:if>
        <c:if test="${!empty tag}">
            <h1>标签： ${tag.name}</h1>
        </c:if>
        <div class="post">
            <h2><a href="<c:url value="/blog/show/${blog.id}.html"/>">${blog.title}</a></h2>
            <small>${blog.updateTime} by ${blog.userName} ${blog.showCount}</small>
            <div class="entry">
                ${blog.content}
            </div>
            <ul class="postmetadata">
                <li class="icon_cat">
                    <strong>Categories: </strong><a href="<c:url value="/category/${blog.categoryVo.id}.html"/>">${blog.categoryVo.name}</a>
                </li>
                <li class="icon_bullet">
                    <strong>Tags: </strong>
                    <c:forEach var="tag" items="${blog.tagVos}">
                        <a href="<c:url value="/tag/${tag.id}.html"/>">${tag.name}</a>&nbsp;
                    </c:forEach>
                </li>
            </ul>
        </div>
        <a name="comments"></a>
        <a class="addcomment" href="#add-comment">Add your comment</a>
        <h3 id="commentCount">
            ${commentsCount} 条回复
        </h3>
        <div class="clear"></div>
        <ol class="commentList" id="commentList">
            <c:forEach var="comment" items="${comments}">
                <li class="commentItem" style="display:list-item;">
                    <div class="userInfo">
                        <a class="avatar" href="" target="_blank" rel="nofollow">
                            <img src="${pageContext.request.contextPath}/resources/profile.gif" alt="${comment.userName}">
                        </a>
                        <br>
                        <span class="ipaddress">${comment.userremoteIp}</span>
                    </div>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <a class="commentControl" href="<c:url value="/comment/delete/${blog.id}/${comment.id}.html"/>">删除</a>&nbsp;
                        <a class="commentControl" href="">回复</a>
                    </c:if>
                    <h4>
                        <a href="" target="_blank" rel="nofollow">${comment.userName}</a>
                        <span class="time">${comment.createTime}</span>
                    </h4>
                    <div class="commentContent"><p>${comment.content}</p></div>
                    <div class="clear" title="post-610"></div>
                </li>
                <script></script>
            </c:forEach>
        </ol>
        <a name="add-comment"></a>
        <h3 id="respond">发表评论</h3>
        <script>
            function validate()
            {
                var errorMsgEle = document.getElementById('errorMsg');
                var valueOfNickName = document.getElementById('nickName').value;
                if(valueOfNickName.length <= 0)
                {
                    errorMsgEle.innerHTML = '昵称为必填项';
                    return false;
                }
                else if(valueOfNickName.length > 10)
                {
                    errorMsgEle.innerHTML = '昵称太长啦！！';
                    return false;
                }

                var valueOfEmail = document.getElementById('email').value;
                if(valueOfEmail.length <= 0)
                {
                    errorMsgEle.innerHTML = '邮箱为必填项';
                    return false;
                }
                else if(valueOfEmail.length > 50)
                {
                    errorMsgEle.innerHTML = '邮箱地址太长啦！！';
                    return false;
                }
                else
                {
                    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                    console.log('Email is: ' + valueOfEmail);
                    if(!re.test(valueOfEmail))
                    {
                        errorMsgEle.innerHTML = '邮箱格式不正确，请重新输入';
                        return false;
                    }
                }

                var valueOfBlogURL = document.getElementById('blogUrl').value;
                if(valueOfBlogURL.length > 0)
                {
                    if(valueOfBlogURL.length > 50)
                    {
                        errorMsgEle.innerHTML = '主页地址太长啦！！';
                        return false;
                    }
                    else
                    {
                        var urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
                        console.log("BlogURL is: " + valueOfBlogURL);
                        if(!urlregex.test(valueOfBlogURL))
                        {
                            errorMsgEle.innerHTML = '主页地址格式不正确，请重新输入';
                            return false;
                        }
                    }
                }

                var valueOfCommentContent = document.getElementById('commentContent').value;
                if(valueOfCommentContent.length <= 5)
                {
                    errorMsgEle.innerHTML = '评论内容需要大于5个字符';
                    return false;
                }
                else if(valueOfCommentContent.length > 500)
                {
                    errorMsgEle.innerHTML = '评论内容过多了，哥们少些点吧 ^_^';
                    return false;
                }
            }
        </script>
        <p id="errorMsg" style="color:red">
            <c:if test="${!empty errorMsg}"> ${errorMsg} </c:if>
        </p>
        <form:form class="commentform" action="/comment/create.html" method="post" id="commentForm" modelAttribute="commentForm" onsubmit="return validate()">
            <p>
                昵称：<form:input id="nickName" path="userName" type="text" class="author" value="${commentForm.userName}" maxlength="20"/>（必填）
            </p>
            <p>
                邮箱：<form:input id="email" path="userEmail" type="text" class="email" value="${commentForm.userEmail}" maxlength="50"/>（必填）
            </p>
            <p>
                主页：<form:input id="blogUrl" path="userUrl" class="website" type="text" value="${commentForm.userUrl}" maxlength="50"/>（可选）
            </p>
            <p>
                <label for="rememberMe">记住我</label>
                <form:checkbox id="rememberMe" path="rememberMe" checked="checked" value="True"/>
            </p>
            <p class="content">评论内容（大于5个字符）：</p>
            <form:textarea id="commentContent" path="content" rows="10" cols="10" style="width:100%;"></form:textarea><br/>
            <form:input path="blogId" type="hidden" value="${blog.id}"/>
            <br/>
            <input type="submit" value="提交" />
        </form:form>
    </div>
    <div id="sidebar">
        <myblog:rightBanner
                allCats="${allCategories}"
                allTags="${allTags}"
                topTenBlogs="${topTenBlogs}"
                topTenViewCountBlogs="${topTenViewCountBlogs}"
                allFriendlyLinks="${allFriendlyLinks}"
                allBookRecommendations="${allBookRecommendations}"
                allPostRecommendations="${allPostRecommendations}"
        />
    </div>
</div>
<%@ include file="footer.jspf" %>
