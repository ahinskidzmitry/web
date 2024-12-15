<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="article.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<jsp:include page="/jsp/fragments/header.jsp"/>
<div class="container-lg" style="margin-top: 6%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="row">
        <div class="col-md-2 d-flex align-items-center justify-content-center">
            <c:choose>
                <c:when test="${current_entity.photoName.equals(\"\")}">
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                         width="150" height="150" alt="" style="margin-bottom: 1rem;">
                </c:when>
                <c:otherwise>
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${current_entity.photoName}" width="150" height="150" alt="">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-10 d-flex align-items-center text-start">
            <div class="w-100" style="margin: auto 0;">
                <h6 style="color: #DE5E60;"><fmt:message key="article.topic" /> ${current_entity.topic}</h6>
                <h2 class="fs-1">${current_entity.author}</h2>
                <h3 class="text-break fs-4">${current_entity.text}</h3>
                <div class="w-100 d-flex justify-content-between align-items-center">
                    <h6 style="color: #DE5E60;">${current_entity.date.toLocalDate()} <fmt:message key="article.date_separator" /> ${current_entity.date.toLocalTime()}</h6>
                    <div class="d-flex">
                        <a class="fs-2 me-1"><i class="fa fa-cloud" aria-hidden="true"></i> <span class="fs-4 me-4">${current_entity.commentsNumber}</span></a>
                        <c:choose>
                            <c:when test="${current_entity.ratingByUser.ordinal() == 1}">
                                <a href="like_article.do?article_id=${current_entity.id}" class="link-success fs-2" style="background: none;"><i class="fa fa-thumbs-up" aria-hidden="true"></i> <span class="fs-4 me-4" style="color: #fff;">${current_entity.positiveRating}</span></a>
                            </c:when>
                            <c:otherwise>
                                <a href="like_article.do?article_id=${current_entity.id}" class="link-secondary fs-2" style="background: none;"><i class="fa fa-thumbs-up" aria-hidden="true"></i> <span class="fs-4 me-4" style="color: #fff;">${current_entity.positiveRating}</span></a>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${current_entity.ratingByUser.ordinal() == 2}">
                                <a href="dislike_article.do?article_id=${current_entity.id}" class="link-danger fs-2 me-1" style="background: none;"><i class="fa fa-thumbs-down" aria-hidden="true"></i> <span class="fs-4 me-4" style="color: #fff;">${current_entity.negativeRating}</span></a>
                            </c:when>
                            <c:otherwise>
                                <a href="dislike_article.do?article_id=${current_entity.id}" class="link-secondary fs-2 me-1" style="background: none;"><i class="fa fa-thumbs-down" aria-hidden="true"></i> <span class="fs-4 me-4" style="color: #fff;">${current_entity.negativeRating}</span></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-lg" style="margin-top: 1%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="row">
        <div class="col-md-2 d-flex align-items-center justify-content-center">
            <c:choose>
                <c:when test="${sessionScope.user.photoName.equals(\"\")}">
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                         width="150" height="150" alt="" style="margin-bottom: 1rem;">
                </c:when>
                <c:otherwise>
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${sessionScope.user.photoName}" width="150" height="150" alt="">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-10 d-flex align-items-center justify-content-start">
            <form action="create_comment.do" method="post" class="article-creation" onsubmit="return validateComment(this.comment_text.value);" style="width: 100%;">
                <div class="form-floating mb-3">
                    <textarea class="form-control" name="comment_text" placeholder="<fmt:message key="comment.comment" />" title="<fmt:message key="pattern.comment" />" id="floatingTextarea2" style="height: 250px; background-color: #2F3238; color: white; resize: none;"></textarea>
                    <label for="floatingTextarea2" style="font-size: 16px;"><fmt:message key="comment.comment" /></label>
                </div>
                <div class="d-flex justify-content-between align-items-center">
                    <c:if test="${invalid_data eq true}">
                        <h6 style="color: #DE5E60;"><fmt:message key="validation.invalid_data" /></h6>
                    </c:if>
                    <h6 class="error" style="color: #DE5E60;"></h6>
                    <button type="submit" class="btn btn-primary" style="background-color: #DE5E60; border: none;"><fmt:message key="comment.publish" /></button>
                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${entity_number ne 0}">
    <div class="container-lg mt-1 mx-auto mb-4" style="background-color: #26292E; padding: 25px; border-radius: 10px;">
        <c:forEach var="comment" items="${entity_list}">
            <div class="row py-5">
                <div class="col-md-2 d-flex align-items-center justify-content-center">
                    <c:choose>
                        <c:when test="${comment.photoName.equals(\"\")}">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                                 width="150" height="150" alt="" style="margin-bottom: 1rem;">
                        </c:when>
                        <c:otherwise>
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${comment.photoName}" width="150" height="150" alt="">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-10 d-flex align-items-center text-start">
                    <div class="w-100" style="margin: auto 0;">
                        <h2 class="fs-1">${comment.author}</h2>
                        <h2 class="text-break fs-4">${comment.text}</h2>
                        <div class="w-100 d-flex justify-content-between align-items-center">
                            <h6 style="color: #DE5E60; margin-bottom: 0">${comment.date.toLocalDate()} <fmt:message key="article.date_separator" /> ${comment.date.toLocalTime()}</h6>
                            <form action="like_comment.do" method="post" class="d-flex justify-content-end align-items-center">
                                <input type="hidden" name="comment_id" value="${comment.id}">
                                <c:choose>
                                    <c:when test="${comment.likedByUser eq true}">
                                        <button type="submit" class="link-success fs-4 mt-3" style="background: none;">
                                            <i class="fa fa-heart-o" aria-hidden="true"></i><span class="fs-4" style="color: #fff;"> ${comment.likesNumber}</span></button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="link-secondary fs-4 mt-3" style="background: none;">
                                            <i class="fa fa-heart-o" aria-hidden="true"></i><span class="fs-4" style="color: #fff;"> ${comment.likesNumber}</span></button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
<ctg:pagination command="VIEW_ARTICLE" />
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
