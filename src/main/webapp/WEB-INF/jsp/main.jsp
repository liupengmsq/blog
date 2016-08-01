<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jspf" %>
<div id="wrapper">
    <div id="content">
        <c:if test="${!empty category}">
            <h1>分类： ${category.name}</h1>
        </c:if>
        <c:if test="${!empty tag}">
            <h1>标签： ${tag.name}</h1>
        </c:if>
        <c:if test="${!empty searchContent}">
            <h1>搜索内容： ${searchContent}</h1>
        </c:if>
        <c:forEach var="blog" items="${pageResult.currentPageData}">
            <div class="post">
                <h2><a href="<c:url value="/blog/show/${blog.id}.html"/>">${blog.title}</a></h2>
                <small>${blog.updateTime} by ${blog.userName} ${blog.showCount}</small>
                <div class="entry">
                    <strong>摘要： </strong> ${blog.summary}&nbsp;&nbsp;<a href="<c:url value="/blog/show/${blog.id}.html"/>">阅读全文</a>
                </div>
                <ul class="postmetadata">
                    <li class="icon_comment icon_r">
                        <a href="<c:url value="/blog/show/${blog.id}.html#comments"/>">
                            ${blog.commentCount} Comments
                        </a>
                    </li>
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
        </c:forEach>
        <div id="pager">
            <p align="center">
                <c:if test="${!empty category}">
                    <myblog:PageBar pageUrl="/category/${category.id}.html" pageAttrKey="pageResult"/>
                </c:if>
                <c:if test="${!empty tag}">
                    <myblog:PageBar pageUrl="/tag/${tag.id}.html" pageAttrKey="pageResult"/>
                </c:if>
                <c:if test="${!empty searchContent}">
                    <myblog:PageBar pageUrl="/blog/search.html?search=${searchContent}" pageAttrKey="pageResult"/>
                </c:if>
                <c:if test="${empty tag and empty category and empty searchContent}">
                    <myblog:PageBar pageUrl="/blog.html" pageAttrKey="pageResult"/>
                </c:if>
            </p>
        </div>
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
