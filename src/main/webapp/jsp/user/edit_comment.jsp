<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="edit_comment.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<jsp:include page="/jsp/fragments/header.jsp"/>
<div class="container-lg" style="margin-top: 6%; background-color: #26292E; padding: 25px; border-radius: 10px;">
    <div class="row">
        <div class="col-md-2 d-flex align-items-center justify-content-center">
            <c:choose>
                <c:when test="${user.photoName.equals(\"\")}">
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/img/nophoto.png"
                         width="150" height="150" alt="" style="margin-bottom: 1rem;">
                </c:when>
                <c:otherwise>
                    <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/provide_image.do?file_name=${sessionScope.user.photoName}" width="150" height="150" alt="">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-10 d-flex align-items-center justify-content-start mx-auto">
            <form action="edit_comment.do" method="post" class="article-creation mb-0" onsubmit="return validateComment(this.comment_text.value);" style="width: 100%;">
                <input type="hidden" name="comment_id" value="${current_entity.id}">
                <div class="form-floating mb-3">
                    <textarea class="form-control" name="comment_text" placeholder="<fmt:message key="comment.comment" />" title="<fmt:message key="pattern.textarea" />" id="floatingTextarea2" style="height: 250px; background-color: #2F3238; color: white; resize: none;" required>${current_entity.text}</textarea>
                    <label for="floatingTextarea2" style="font-size: 16px;"><fmt:message key="comment.comment" /></label>
                </div>
                <div class="d-flex align-items-center justify-content-between">
                    <c:if test="${invalid_data eq true}">
                        <h6 style="color: #DE5E60;"><fmt:message key="validation.invalid_data" /></h6>
                    </c:if>
                    <h6 class="error" style="color: #DE5E60;"></h6>
                    <button type="submit" class="btn btn-primary w-25" style="background-color: #DE5E60; border: none;"><fmt:message key="edit_article.save" /></button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
