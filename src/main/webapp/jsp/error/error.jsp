<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="error.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.role.ordinal() == 1}">
        <%@include file="../admin/admin_header.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="../fragments/header.jsp"%>
    </c:otherwise>
</c:choose>
<main class="content">
    <div class="container d-flex h-50 justify-content-center align-items-center">
        <div class="error-icon d-flex justify-content-center">
            <h2 class="error__icon-item me-5" style="font-size: 100px;"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i></h2>
        </div>
        <div class="error-text">
            <p class="error-text-item"><b><fmt:message key="error.code" /> ${pageContext.errorData.statusCode}</b>.</p>
            <p class="error-text-item"><b><fmt:message key="error.message_one" /></b></p>
            <p class="error-text-item"><b><fmt:message key="error.message_two" /></b></p>
        </div>
    </div>
</main>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
