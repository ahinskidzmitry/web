<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="change_password.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="../fragments/header.jsp"%>
<div class="container-lg p-5" style="margin-top: 5%;">
    <div class="row">
        <div class="col-md-6 mx-auto" style="background-color: #26292E; border-radius: 10px;">
            <form action="change_password.do" method="post">
                <div class="form-floating mt-5 mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="password" class="form-control" id="floatingInput" placeholder="<fmt:message key="change_password.password" />" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" style="background-color: #2F3238; color: white;"
                           required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="change_password.password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="confirmed_password" class="form-control" id="floatingInput" placeholder="<fmt:message key="change_password.confirm_password" />" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="change_password.confirm_password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="new_password" class="form-control" id="floatingInput" placeholder="<fmt:message key="change_password.new_password" />" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="change_password.new_password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="password" name="confirmed_new_password" class="form-control" id="floatingInput" placeholder="<fmt:message key="change_password.confirm_new_password" />" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="<fmt:message key="pattern.password" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="change_password.confirm_new_password" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <c:choose>
                    <c:when test = "${invalid_data eq true}">
                        <h6 class="d-flex justify-content-center" style="color: #DE5E60;"><fmt:message key="validation.invalid_data" /></h6>
                    </c:when>
                    <c:when test = "${invalid_data eq false}">
                        <h6 class="d-flex justify-content-center" style="color: #DE5E60;"><fmt:message key="validation.valid_data" /></h6>
                    </c:when>
                </c:choose>
                <div class="d-flex w-100">
                    <button type="submit" class="btn btn-primary mt-3 mb-3 mx-auto py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="change_password.save" /></button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
