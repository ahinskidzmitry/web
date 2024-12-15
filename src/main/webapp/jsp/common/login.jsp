<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="login.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="../fragments/header.jsp"%>
<div class="container-lg p-5" style="margin-top: 3%;">
    <div class="row">
        <div class="col-md-6 mx-auto" style="background-color: #26292E; border-radius: 10px;">
            <p class="fs-2 mb-5 mt-5 text-center"><fmt:message key="login.info" /> </p>
            <form action="login.do" method="post">
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" name="login" class="form-control" id="floatingInput" pattern="[a-zA-Z0-9]{5,30}" title="<fmt:message key="pattern.login" />" placeholder="<fmt:message key="login.username" />" title="<fmt:message key="pattern.login" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="login.username" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="password" class="form-control" id="floatingInput" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" placeholder="<fmt:message key="login.password" />" title="<fmt:message key="pattern.password" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="login.password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <c:if test="${invalid_data ne null}">
                    <p class="d-flex justify-content-center"><fmt:message key="validation.invalid_data" /></p>
                </c:if>
                <c:if test="${user_blocked ne null}">
                    <p class="d-flex justify-content-center"><fmt:message key="login.user_blocked" /></p>
                </c:if>
                <div class="d-flex w-100">
                    <button type="submit" class="btn btn-primary mt-2 mb-3 mx-auto py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="login.enter" /></button>
                </div>
            </form>
            <div class="d-flex">
                <a href="to_sign_up.do" class="mx-auto fs-5 mb-5 "><fmt:message key="login.create_account" /></a>
            </div>
        </div>
    </div>
</div>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
