<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="edit_profile.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<jsp:include page="/jsp/fragments/header.jsp"/>
<div class="container-lg p-5" style="margin-top: 5%;">
    <div class="row">
        <div class="col-md-6 mx-auto" style="background-color: #26292E; border-radius: 10px;">
            <form action="edit_profile.do" method="post">
                <div class="form-floating mt-5 mb-3 mx-auto" style="width: 80%;">
                    <input type="text" class="form-control" id="floatingInput" name="name" pattern="[a-zA-Zа-яА-Я0-9]{5,25}" placeholder="<fmt:message key="edit_profile.name" />" value="${user.name}" title="<fmt:message key="pattern.name" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="edit_profile.name" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" class="form-control" id="floatingInput" name="email" pattern="^(([^&lt;&gt;()[\]\\.,;:\s@&quot;]+(\.[^&lt;&gt;()[\]\\.,;:\s@&quot;]+)*)|(&quot;.+&quot;))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))" placeholder="<fmt:message key="edit_profile.email" />" value="${user.email}" title="<fmt:message key="pattern.email" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="edit_profile.email" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <input type="text" class="form-control" id="floatingInput" name="login" pattern="[a-zA-Z0-9]{5,30}" placeholder="<fmt:message key="edit_profile.login" />" value="${user.login}" title="<fmt:message key="pattern.login" />" style="background-color: #2F3238; color: white;" required />
                    <label for="floatingInput" style="font-size: 16px;"><fmt:message key="edit_profile.login" /> <span style="color: #DE5E60;">*</span></label>
                </div>
                <div class="form-floating mb-3 mx-auto" style="width: 80%;">
                    <a href="to_change_password.do" class="btn btn-primary w-100 mx-auto py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="edit_profile.change_password" /></a>
                </div>
                <c:if test="${invalid_data ne null}">
                    <p class="d-flex justify-content-center"><fmt:message key="validation.invalid_data" /></p>
                </c:if>
                <div class="d-flex w-75 mx-auto mb-5">
                    <button type="submit" class="btn btn-primary mt-5 mx-auto py-2 px-5 border-0" style="background-color: #DE5E60;"><fmt:message key="edit_profile.save" /></button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
