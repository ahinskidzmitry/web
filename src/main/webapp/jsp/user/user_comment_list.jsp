<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="comments.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<jsp:include page="/jsp/fragments/header.jsp"/>
<div class="container-lg" style="margin-top: 6%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="mx-auto w-75">
        <div class="row d-flex justify-content-center">
            <div class="col-md-4 d-flex align-items-center justify-content-center">
                <form class="d-flex flex-column" action="file.upload" enctype="multipart/form-data" method="post">
                    <c:choose>
                        <c:when test="${sessionScope.user.photoName.equals(\"\")}">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                                 width="150" height="150" alt="" style="margin-bottom: 1rem;">
                        </c:when>
                        <c:otherwise>
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${sessionScope.user.photoName}" width="150" height="150" alt="">
                        </c:otherwise>
                    </c:choose>
                    <input class="form-control mt-1" type="file" accept="image/*" aria-valuetext="" name="user_photo" id="formFile">
                    <button type="submit" class="btn btn-primary mt-1 py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="info.change" /></button>
                </form>
            </div>
            <div class="col-md-6">
                <div class="d-flex flex-column">
                    <p class="fs-1"> ${user.name} </p>
                    <p class="text-break"><fmt:message key="info.login" /> ${user.login}</p>
                    <p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><fmt:message key="info.email" /> ${user.email}</p>
                    <a href="to_edit_profile.do" class="btn btn-primary mt-2 mb-2 py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="info.edit_button" /></a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-lg mt-1 mx-auto mb-4" style="background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="d-flex justify-content-end">
        <a href="user_comment_list.do?requested_page=1" class="nav-link" style="border-right: 1px solid white;"><i class="fa fa-comments" aria-hidden="true"></i> <fmt:message key="info.comments" /></a>
        <a href="user_article_list.do?requested_page=1" class="nav-link ms-1"><i class="fa fa-newspaper-o" aria-hidden="true"></i> <fmt:message key="info.questions" /></a>
    </div>
    <c:choose>
        <c:when test="${entity_number ne 0}">
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
                                <div class="d-flex w-50">
                                    <c:choose>
                                        <c:when test="${comment.blocked eq true}">
                                            <h2 class="fs-2 mx-auto" style="color: #DE5E60;"><fmt:message key="info.blocked" /></h2>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="view_comment_article.do?comment_id=${comment.id}" class="btn btn-primary me-1" style="background-color: #DE5E60; border: none; width: 33%;"><fmt:message key="info.to_a_question" /></a>
                                            <c:choose>
                                                <c:when test="${comment.active eq true}">
                                                    <a href="hide_comment.do?comment_id=${comment.id}" class="btn btn-primary me-1" style="background-color: #DE5E60; border: none; width: 33%;"><fmt:message key="info.hide" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="recover_comment.do?comment_id=${comment.id}" class="btn btn-primary me-1" style="background-color: #DE5E60; border: none; width: 33%;"><fmt:message key="info.show" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="to_edit_comment.do?comment_id=${comment.id}" class="btn btn-primary me-1" style="background-color: #DE5E60; border: none; width: 33%;"><fmt:message key="info.edit_button" /></a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2 class="d-flex justify-content-center mt-3 mb-5 fs-5" style="color: #DE5E60;"><fmt:message key="info.comments_list" /></h2>
        </c:otherwise>
    </c:choose>
</div>
<ctg:pagination command="USER_COMMENT_LIST" />
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
