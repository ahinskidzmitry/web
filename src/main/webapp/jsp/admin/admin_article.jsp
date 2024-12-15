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
<%@include file="admin_header.jsp"%>
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
                <c:choose>
                    <c:when test="${current_entity.text.length() > 300}">
                        <h2 class="text-break" style="font-size: 20px;">${current_entity.text.substring(0, 300)}...</h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-break" style="font-size: 20px;">${current_entity.text}</h2>
                    </c:otherwise>
                </c:choose>
                <h6 style="color: #DE5E60;">${current_entity.author}, ${current_entity.date.toLocalDate()} <fmt:message key="article.date_separator" /> ${current_entity.date.toLocalTime()}, ${current_entity.topic}</h6>
                <form action="admin_block_article.do" method="post" class="w-100 d-flex justify-content-between align-items-center">
                    <input type="hidden" name="article_id" value="${current_entity.id}">
                    <div class="d-flex justify-content-between align-items-center">
                        <c:choose>
                            <c:when test="${current_entity.negativeRating + current_entity.positiveRating < 0}">
                                <span class="text-danger fs-2 me-1"><i class="fa fa-frown-o" aria-hidden="true"></i></span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-success fs-2 me-1"><i class="fa fa-smile-o" aria-hidden="true"></i></span>
                            </c:otherwise>
                        </c:choose>
                        <span class="fs-4 me-4" style="color: #fff;">${current_entity.positiveRating + current_entity.negativeRating}</span>
                        <a class="fs-2 me-1"><i class="fa fa-cloud" aria-hidden="true"></i></a>
                        <span class="fs-4">${current_entity.commentsNumber}</span>
                    </div>
                    <c:choose>
                        <c:when test="${current_entity.blocked eq true}">
                            <button type="submit" class="btn btn-primary w-25 border-0" style="background-color: #DE5E60;"><fmt:message key="admin_article.unblock" /> </button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary w-25 border-0" style="background-color: #DE5E60;"><fmt:message key="admin_article.block" /></button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
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
                        <div class="w-100 d-flex justify-content-between align-items-center">
                            <h6 style="color: #DE5E60; margin-bottom: 0">${comment.date.toLocalDate()} <fmt:message key="article.date_separator" /> ${comment.date.toLocalTime()}</h6>
                        </div>
                        <h2 class="fs-1">${comment.author}</h2>
                        <h2 class="text-break fs-4">${comment.text}</h2>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="text-success fs-2"><i class="fa fa-heart-o" aria-hidden="true"></i><span class="fs-2" style="color: #fff;"> ${comment.likesNumber}</span></span>
                            <div class="d-flex w-25">
                                <c:choose>
                                    <c:when test="${comment.blocked eq true}">
                                        <a href="admin_unblock_comment.do?comment_id=${comment.id}" class="btn btn-primary me-1 w-100" style="background-color: #DE5E60; border: none;"><fmt:message key="admin_article.unblock" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="admin_block_comment.do?comment_id=${comment.id}" class="btn btn-primary me-1 w-100" style="background-color: #DE5E60; border: none;"><fmt:message key="admin_article.block" /></a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
<ctg:pagination command="ADMIN_VIEW_ARTICLE" />
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
