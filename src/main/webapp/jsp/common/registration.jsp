<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="registration.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="../fragments/header.jsp"%>
<div class="container-lg p-5" style="margin-top: 3%;">
    <div class="row">
        <div class="col-md-6 mx-auto" style="background-color: #26292E; border-radius: 10px;">
            <p class="fs-2 mb-5 mt-5 text-center"><fmt:message key="registration.info" /></p>
            <form action="register.do" method="post">
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" name="name" class="form-control" id="floatingInput" placeholder="<fmt:message key="registration.name" />" style="background-color: #2F3238; color: white;"
                    pattern="[a-zA-Zа-яА-Я0-9]{5,25}" title="<fmt:message key="pattern.name" />" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="registration.name" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" name="email" class="form-control" id="floatingInput" placeholder="<fmt:message key="registration.email" />" style="background-color: #2F3238; color: white;"
                    pattern="^(([^&lt;&gt;()[\]\\.,;:\s@&quot;]+(\.[^&lt;&gt;()[\]\\.,;:\s@&quot;]+)*)|(&quot;.+&quot;))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))" title="<fmt:message key="pattern.email" />" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="registration.email" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" name="login" class="form-control" id="floatingInput" placeholder="<fmt:message key="registration.username" />" style="background-color: #2F3238; color: white;"
                    pattern="[a-zA-Z0-9]{5,30}" title="<fmt:message key="pattern.login" />" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="registration.username" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="password" class="form-control" id="floatingInput" placeholder="<fmt:message key="registration.password" />" style="background-color: #2F3238; color: white;"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="registration.password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <c:choose>
                    <c:when test = "${invalid_data eq true}">
                        <p class="d-flex justify-content-center"><fmt:message key="validation.invalid_data" /></p>
                    </c:when>
                    <c:when test = "${invalid_data eq false}">
                        <p class="d-flex justify-content-center"><fmt:message key="registration.valid_data" /> (<a class="text-primary"
                                href="to_login.do"><fmt:message key="registration.log_in_transfer" /></a>)</p>
                    </c:when>
                </c:choose>
                <div class="d-flex w-100">
                    <button type="submit" class="btn btn-primary mt-2 mb-3 mx-auto py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="registration.button_create" /></button>
                </div>
            </form>
            <div class="d-flex">
                <a href="to_login.do" class="mx-auto fs-5 mb-5 "><fmt:message key="registration.account_exists" /></a>
            </div>
        </div>
    </div>
</div>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
