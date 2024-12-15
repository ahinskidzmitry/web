<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="admin_user.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="admin_header.jsp"%>
<div class="container-lg" style="margin-top: 6%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="mx-auto w-75">
        <div class="row d-flex justify-content-center">
            <div class="col-md-4 d-flex align-items-center justify-content-center">
                <form class="d-flex flex-column" action="file.upload" enctype="multipart/form-data" method="post">
                    <c:choose>
                        <c:when test="${current_entity.photoName.equals(\"\")}">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                                 width="150" height="150" alt="" style="margin-bottom: 1rem;">
                        </c:when>
                        <c:otherwise>
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${current_entity.photoName}" width="150" height="150" alt="">
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
            <div class="col-md-6">
                <div class="d-flex flex-column">
                    <p class="fs-1"> ${current_entity.name} </p>
                    <p class="text-break"><fmt:message key="info.login" /> ${current_entity.login}</p>
                    <p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><fmt:message key="info.email" /> ${current_entity.email}</p>
                    <c:choose>
                        <c:when test="${current_entity.blocked eq true}">
                            <a href="admin_unblock_user.do?user_id=${current_entity.id}" class="btn btn-primary border-0" style="background-color: #DE5E60;"><fmt:message key="admin_user.unblock" /></a>
                        </c:when>
                        <c:otherwise>
                            <a href="admin_block_user.do?user_id=${current_entity.id}" class="btn btn-primary border-0" style="background-color: #DE5E60;"><fmt:message key="admin_user.block" /></a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-lg mt-1 mx-auto mb-4" style="background-color: #26292E; padding: 25px; border-radius: 10px;">
    <c:choose>
        <c:when test="${entity_number ne 0}">
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
                                <div class="d-flex w-25">
                                    <a href="admin_view_article.do?article_id=${article.id}" class="btn btn-primary w-100 border-0 me-1" style="background-color: #DE5E60;"><fmt:message key="info.view" /></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2 class="d-flex justify-content-center mt-5 mb-5 fs-5" style="color: #DE5E60;"><fmt:message key="admin_user.question_info" /></h2>
        </c:otherwise>
    </c:choose>
</div>
<ctg:pagination command="ADMIN_TO_USER_PROFILE" />
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
