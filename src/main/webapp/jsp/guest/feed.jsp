<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <meta charset ="UTF-8">
    <title><fmt:message key="feed.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="../fragments/header.jsp"%>
<div class="container-lg" style="margin-top: 6%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <c:if test="${sessionScope.user ne null}">
        <div class="row">
            <div class="col-md-2 d-flex align-items-center justify-content-center">
                <c:choose>
                    <c:when test="${sessionScope.user.photoName.equals(\"\")}">
                        <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                             width="150" height="150" alt="" style="margin-bottom: 1rem;">
                    </c:when>
                    <c:otherwise>
                        <img class="mx-auto rounded-circle"
                             src="${pageContext.request.contextPath}/provide_image.do?file_name=${sessionScope.user.photoName}"
                             width="150" height="150" alt="">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-10 d-flex align-items-center justify-content-start">
                <form action="create_article.do" method="post" class="article-creation" onsubmit="return validateArticleFields(this.topic.value, this.text.value)" style="width: 100%;">
                    <div class="form-floating mb-3">
                        <input type="text" name="topic" class="form-control" id="floatingInput" placeholder="<fmt:message key="feed.topic" />" title="<fmt:message key="pattern.topic" />" style="background-color: #2F3238; color: white;" maxlength="100" required>
                        <label for="floatingInput" style="font-size: 16px;"><fmt:message key="feed.topic" /></label>
                    </div>
                    <div class="form-floating mb-3">
                        <textarea class="form-control" name="text" placeholder="<fmt:message key="feed.question" />" id="floatingTextarea2" title="<fmt:message key="pattern.textarea" />" style="height: 250px; background-color: #2F3238; color: white; resize: none;" required></textarea>
                        <label for="floatingTextarea2" style="font-size: 16px;"><fmt:message key="feed.question" /></label>
                    </div>
                    <div class="d-flex align-items-center justify-content-between">
                        <c:if test="${invalid_data eq true}">
                            <h6 style="color: #DE5E60;"><fmt:message key="validation.invalid_data" /></h6>
                        </c:if>
                        <h6 class="error" style="color: #DE5E60;"></h6>
                        <button type="submit" class="btn btn-primary w-25" style="background-color: #DE5E60; border: none;"><fmt:message key="feed.publish" /></button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
    <c:forEach var="article" items="${entity_list}">
        <div class="row py-5">
            <div class="col-md-2 d-flex align-items-center justify-content-center">
                <c:choose>
                    <c:when test="${article.photoName.equals(\"\")}">
                        <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                             width="150" height="150" alt="" style="margin-bottom: 1rem;">
                    </c:when>
                    <c:otherwise>
                        <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${article.photoName}" width="150" height="150" alt="">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-10 d-flex align-items-center text-start">
                <div class="w-100" style="margin: auto 0;">
                    <c:choose>
                        <c:when test="${article.text.length() > 300}">
                            <h2 class="text-break" style="font-size: 20px;">${article.text.substring(0, 300)}...</h2>
                        </c:when>
                        <c:otherwise>
                            <h2 class="text-break" style="font-size: 20px;">${article.text}</h2>
                        </c:otherwise>
                    </c:choose>
                    <h6 style="color: #DE5E60;">${article.author}, ${article.date.toLocalDate()} <fmt:message key="article.date_separator" /> ${article.date.toLocalTime()}, ${article.topic}</h6>
                    <div class="w-100 d-flex justify-content-between align-items-center">
                        <div class="d-flex justify-content-between align-items-center">
                            <c:choose>
                                <c:when test="${article.negativeRating + article.positiveRating < 0}">
                                    <span class="text-danger fs-2 me-1"><i class="fa fa-frown-o" aria-hidden="true"></i></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-success fs-2 me-1"><i class="fa fa-smile-o" aria-hidden="true"></i></span>
                                </c:otherwise>
                            </c:choose>
                            <span class="fs-4 me-4" style="color: #fff;">${article.positiveRating + article.negativeRating}</span>
                            <a class="fs-2 me-1"><i class="fa fa-cloud" aria-hidden="true"></i></a>
                            <span class="fs-4">${article.commentsNumber}</span>
                        </div>
                        <a href="view_article.do?article_id=${article.id}" class="btn btn-primary w-25" style="background-color: #DE5E60; border: none;"><fmt:message key="feed.answer" /></a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<ctg:pagination command="GLOBAL_ARTICLE_LIST" />
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
