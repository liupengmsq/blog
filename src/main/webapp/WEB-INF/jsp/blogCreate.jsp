<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<%@ include file="header.jspf" %>
<script>
    function copyToClipboard(text)
    {
        window.prompt("Copy to clipboard: Ctrl+C, Enter", text);
    }

    function getSelectValues(select, imgHeight) {
        var result = '';
        var options = select && select.options;
        var opt;

        for (var i=0, iLen=options.length; i<iLen; i++) {
            opt = options[i];

            if (opt.selected) {
                result = result + "<a href=\"${pageContext.request.contextPath}/resources/" + opt.text + "\" data-lightbox=\"image-1\"    data-title=\"\"><img src=\"${pageContext.request.contextPath}/resources/" + opt.text + "\" height=\"" + imgHeight + "%\" /></a>" + "<br/>";
            }
        }
        return result;
    }

    function searchImg()
    {
        searchBox = document.getElementById("searchBox");
        countries = document.getElementById("opts");

        var text = searchBox.value;
        var options = countries.options;

        //clear select first
        for (var i = 0; i < options.length; i++) {
            options[i].selected = false;
        }

        //search and select then
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var optionText = option.text;
            var lowerOptionText = optionText.toLowerCase();
            var lowerText = text.toLowerCase();
            var regex = new RegExp("^" + text, "i");
            var match = optionText.match(regex);
            var contains = lowerOptionText.indexOf(lowerText) != -1;
            if (match || contains) {
                option.selected = true;
            }
            searchBox.selectedIndex = 0;
        }
    }

    function insertAtCursor(myField, startTag, endTag)
    {
        if (myField.selectionStart || myField.selectionStart == '0')
        {
            var startPos = myField.selectionStart;
            var endPos = myField.selectionEnd;

            console.log("StartPos: " + startPos);
            console.log("EndPos: " + endPos);

            var mySubString = myField.value.substring(startPos, endPos);
            console.log("subString: " + mySubString);

            myField.value = myField.value.substring(0, startPos)
                    + startTag
                    + mySubString
                    + endTag
                    + myField.value.substring(endPos, myField.value.length);
        }
    }

    function insertParagraphTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<p>', '</p>');
    }

    function insertScriptTag()
    {
        var lanOptions = document.getElementById('languageSelect');
        var lan = lanOptions.options[lanOptions.selectedIndex].value;
        insertAtCursor(document.getElementById('blogContent'), "<script type=\"syntaxhighlighter\" class=\"brush: " + lan +  "\">", "<\/script>");
    }

    function insertBrTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<br/>', '');
    }

    function insertStrongTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<strong>', '</strong>');
    }

    function insertOlTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<ol>', '</ol>');
    }

    function insertUlTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<ul>', '</ul>');
    }

    function insertLiTag()
    {
        insertAtCursor(document.getElementById('blogContent'), '<li>', '</li>');
    }

    function insertTitleLevelTag()
    {
        var titleLevelOptions = document.getElementById('titleLevel');
        var title = titleLevelOptions.options[titleLevelOptions.selectedIndex].value;
        insertAtCursor(document.getElementById('blogContent'), '<' + title + '>', '</' + title + '>');
    }
</script>

<table width="100%">
    <tr>
        <td valign="top">
            <c:if test="${!empty errorMsg}">
                <div style="color:red">${errorMsg}</div>
            </c:if>
            <form action="<c:url value="/blog/create.html"/>" method="post">
                标题: &nbsp; <input  type="text" name="title" style="width:100%"/><br/>
                <br/>

                摘要: <br/>
                <textarea rows="4" cols="20" name="summary" style="width:100%;"></textarea><br/><br/>

                内容: <br/>
                <input type="button" value="<p>...</p>" onclick="insertParagraphTag()" />&nbsp;
                <select id="languageSelect">
                    <option>c</option>
                    <option>python</option>
                    <option>java</option>
                    <option>csharp</option>
                    <option>plain</option>
                    <option>bash</option>
                    <option>xml</option>
                </select>
                <input type="button" value="<script>...</script>" onclick="insertScriptTag()" />&nbsp;
                <input type="button" value="<br/>" onclick="insertBrTag()" />&nbsp;
                <input type="button" value="<strong>...</strong>" onclick="insertStrongTag()" />&nbsp;
                <select id="titleLevel">
                    <option>h1</option>
                    <option>h2</option>
                    <option>h3</option>
                </select>
                <input type="button" value="<h..>...</h..>" onclick="insertTitleLevelTag()" />&nbsp;
                <input type="button" value="<ol>...</ol>" onclick="insertOlTag()" />&nbsp;
                <input type="button" value="<ul>...</ul>" onclick="insertUlTag()" />&nbsp;
                <input type="button" value="<li>...</li>" onclick="insertLiTag()" />&nbsp;
                <br/>
                <textarea id="blogContent" rows="30" cols="20" name="content" style="width:100%;"></textarea><br/><br/>
                <br/>
                <input type="button" value="<p>...</p>" onclick="insertParagraphTag()" />&nbsp;
                <select id="languageSelect">
                    <option>c</option>
                    <option>python</option>
                    <option>java</option>
                    <option>csharp</option>
                    <option>plain</option>
                    <option>bash</option>
                    <option>xml</option>
                </select>
                <input type="button" value="<script>...</script>" onclick="insertScriptTag()" />&nbsp;
                <input type="button" value="<br/>" onclick="insertBrTag()" />&nbsp;
                <input type="button" value="<strong>...</strong>" onclick="insertStrongTag()" />&nbsp;
                <select id="titleLevel">
                    <option>h1</option>
                    <option>h2</option>
                    <option>h3</option>
                </select>
                <input type="button" value="<h..>...</h..>" onclick="insertTitleLevelTag()" />&nbsp;
                <input type="button" value="<ol>...</ol>" onclick="insertOlTag()" />&nbsp;
                <input type="button" value="<ul>...</ul>" onclick="insertUlTag()" />&nbsp;
                <input type="button" value="<li>...</li>" onclick="insertLiTag()" />&nbsp;
                <br/>

                分类：&nbsp;
                <c:forEach var="category" items="${allCategories}">
                    <input type="radio" name="categoryVo" value="${category.id}"/>${category.name} &nbsp;
                </c:forEach>
                <br/><br/>

                标签：&nbsp;
                <c:forEach var="tag" items="${allTags}">
                    <input type="checkbox" name="tagVos" value="${tag.id}"/>${tag.name} &nbsp;
                </c:forEach>
                <br/><br/>

                状态: &nbsp;
                <input type="radio" name="status" value="PUBLISHED" checked="checked"/>发布 &nbsp;
                <input type="radio" name="status" value="DRAFT"/>草稿 &nbsp;
                <br/><br/>

                格式: &nbsp;
                <input type="radio" name="blogFormat" value="HTML" checked="checked"/>HTML &nbsp;
                <input type="radio" name="blogFormat" value="MarkDown"/>MarkDown &nbsp;
                <br/><br/>

                <input type="submit" value="保存">&nbsp;
                <input type="reset" value="重置"><br/>
            </form>
        </td>
        <td valign="top" width="30%">
            <form action="<c:url value="/blog/uploadImage.html"/>" method="post" enctype="multipart/form-data">
                文件名：<input type="text" name="name">
                <br>
                <input type="file" name="file" value="选择文件">
                <br>
                <input type="submit" value="上传"/>
            </form>
            <br/>
            <br/>
            <input type="search" id="searchBox">
            <br>
            <button id="searchButton" onclick="searchImg()">Search</button>
            <br>
            <select id="opts" multiple draggable="true" size="50">
                <c:forEach var="name" items="${fileNames}">
                <option>${name}
                    </c:forEach>
            </select>
            <input type="text" id="imgHeight" width="10px" value="100"/>%
            <button id="copyImagePathButton" onclick="
                  var el = document.getElementById('opts');
                  var imgHeight = document.getElementById('imgHeight').value;
                  copyToClipboard(getSelectValues(el, imgHeight));
            ">Show selected values</button>
        </td>
    </tr>
</table>

<script>
    document.getElementById("searchBox")
            .addEventListener("keyup", function(event) {
                console.log("start to run");
                event.preventDefault();
                console.log("keycode: " + event.keyCode);
                if (event.keyCode == 13) {
                    console.log("start to click searchButton button");
                    document.getElementById("searchButton").click();
                    document.getElementById("copyImagePathButton").click();
                }
            });
</script>

<%@ include file="footer.jspf" %>
