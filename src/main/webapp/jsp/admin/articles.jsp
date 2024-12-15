<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <title><fmt:message key="admin_articles.title" /></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <%@include file="../fragments/import_styles.jsp"%>
</head>
<body>
<%@include file="admin_header.jsp"%>
<div class="container-lg" style="margin-top: 6%;">
    <div class="row">
        <form class="d-flex mb-3" action="admin_search_articles.do" method="post">
            <input class="form-control me-2" name="criteria" type="search" placeholder="<fmt:message key="admin_articles.search" />" value="${criteria}" aria-label="<fmt:message key="admin_articles.search" />" style="background-color: #2F3238; color: white;">
            <button class="btn btn-outline-secondary" type="submit"><fmt:message key="admin_articles.search" /></button>
        </form>
        <div class="table-responsive">
            <table class="table table-dark table-striped" style="border-collapse: separate;">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="admin_articles.topic" /></th>
                    <th scope="col"><fmt:message key="admin_articles.author" /></th>
                    <th scope="col"><fmt:message key="admin_articles.rating" /></th>
                    <th scope="col"><fmt:message key="admin_articles.active" /></th>
                    <th scope="col"><fmt:message key="admin_articles.blocked" /></th>
                    <th scope="col" class="d-flex justify-content-center"><fmt:message key="admin_articles.action" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="article" items="${entity_list}">
                    <tr>
                        <td scope="row" class="align-middle">${article.topic}</td>
                        <td class="align-middle">${article.author}</td>
                        <td class="align-middle">${article.positiveRating + article.negativeRating}</td>
                        <td class="align-middle">${article.active}</td>
                        <td class="align-middle">${article.blocked}</td>
                        <td class="border-0">
                            <a href="admin_view_article.do?article_id=${article.id}" class="btn btn-danger w-100"><fmt:message key="admin_articles.view" /></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${criteria ne null}">
        <ctg:pagination command="${command}" hidden="${criteria}" />
    </c:when>
    <c:otherwise>
        <ctg:pagination command="${command}" />
    </c:otherwise>
</c:choose>
<%@include file="../fragments/import_scripts.jsp"%>
</body>
</html>
