<%@ tag import="pengliu.me.domain.Blog" %>
<%@ tag import="pengliu.me.domain.BookRecommendation" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="allCats" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有分类" %>
<%@ attribute name="allTags" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>
<%@ attribute name="topTenBlogs" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>
<%@ attribute name="topTenViewCountBlogs" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>
<%@ attribute name="allFriendlyLinks" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>
<%@ attribute name="allBookRecommendations" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>
<%@ attribute name="allPostRecommendations" required="true" rtexprvalue="true" type="java.util.ArrayList" description="所有的标签" %>

<div id="about">
    <div>
        <h2 id="about_title">About</h2>
        <div id="about_body">
            <img src="/resources/peng.jpg" width="160px" height="175px" id="peng" />
            <p><strong>刘鹏</strong>，从事多年软件测试开发工作，包括自动化测试任务管理平台、Windows与Mac OSX平台的UI自动化、Web UI自动化、服务端接口业务逻辑的自动化测试。</p>
            <p>享受测试开发工作的相对自由，不用按照PRD文档完成每个功能，不用因为多出一个弹窗或少了一个提示信息而更改代码。只需满足公司内部的使用需求，达到测试目的即可，可以选择自己喜欢的语言、框架去实现功能。</p>
            <p>联系博主：
                <ul>
                    <li>
                        邮箱：<a href="mailto:liupengmsq@hotmail.com" target="_top">liupengmsq@hotmail.com</a>
                    </li>
                    <li>
                        豆瓣主页: <a href="https://www.douban.com/people/42861656/" target="_top">https://www.douban.com/people/42861656/</a>
                    </li>
                </ul>
            </p>
        </div>
    </div>
</div>
<div id="mySearchWrapper">
    <form action="<c:url value="/blog/search.html"/>" method="post">
        <div id="mySearch">
            <input type="image" src="${pageContext.request.contextPath}/resources/css/images/btnsearch.gif" id="btnZzk" class="submit">
            <label class="lb_search">
                <input type="text" id="q" name="search" class="keyword">
            </label>
        </div>
    </form>
</div>
<div id="sideLeft">
    <div class="side-recent-posts">
        <h2>最近的博客</h2>
        <ul class="bullet">
            <% int blogIndex = 0; %>
            <c:forEach var="blog" items="${topTenBlogs}">
                <li>
                    <a href="<c:url value="/blog/show/${blog.id}.html"/>">${blog.title}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <br/>
    <div class="side-categories">
        <h2>我的分类</h2>
        <ul class="folder">
            <c:forEach var="cat" items="${allCats}">
                <li>
                    <a href="<c:url value="/category/${cat.id}.html"/>">${cat.name}</a>(${cat.blogCount})
                </li>
            </c:forEach>
        </ul>
    </div>
    <div>
        <h2>推荐文章</h2>
        <ul class="chain">
            <c:forEach var="fl" items="${allPostRecommendations}">
                <li>
                    <a href="${fl.url}" target="_blank">${fl.displayName}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<div id="sideRight">
    <div class="side-tags">
        <h2>我的标签</h2>
        <ul class="chain">
            <c:forEach var="tag" items="${allTags}">
                <li>
                    <a href="<c:url value="/tag/${tag.id}.html"/>">${tag.name}</a>(${tag.blogCount})
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="latestBlogs">
        <h2>阅读排行榜</h2>
        <ul class="bullet">
            <% int blogIndexForViewCount = 0; %>
            <c:forEach var="blog" items="${topTenViewCountBlogs}">
                <li>
                    <a href="<c:url value="/blog/show/${blog.id}.html"/>"><%= ++blogIndexForViewCount %>. ${blog.title}(${blog.showCount})</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div>
        <h2>推荐博客</h2>
        <ul class="chain">
            <c:forEach var="fl" items="${allFriendlyLinks}">
                <li>
                    <a href="${fl.url}" target="_blank">${fl.displayName}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div id="side-book-recommendation">
        <h2>书籍推荐</h2>
        <div style="">
            <table cellspacing="0" cellpadding="4" style="">
                <%
                    Object[] bookRecommendations = allBookRecommendations.toArray();
                    for(int i=0; i<bookRecommendations.length; i++)
                    {
                        if(i%2 == 0)
                        {
                            out.println("<tr align=\"center\">");
                        }
                        BookRecommendation br = (BookRecommendation)bookRecommendations[i];
                        out.println("<td>");
                        out.println("<a href=\"" + br.getUrl() + "\" title=\"" + br.getTitle() + "\" target=\"_blank\">" +
                                "<img src=\"" + br.getImgSrc() + "\" border=\"0\">" +
                                "</a>");
                        out.println("</td>");

                        if(i%2 != 0 || i == bookRecommendations.length - 1)
                        {
                            out.println("</tr>");
                        }
                    }
                %>
            </table>
        </div>
    </div>
</div>
