<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.current_language}" />
<fmt:setBundle basename="locale.pagecontent" />
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<header class="fixed-top">
    <nav class="navbar navbar-expand-lg">
        <div class="container-lg">
            <a href="global_article_list.do?requested_page=1" class="navbar-brand"><span class="a-letter">A</span>Questions</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fa fa-bars" aria-hidden="true"></i>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
                <ul class="navbar-nav mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a href="user_article_list.do?requested_page=1" class="nav-link"><i class="fa fa-user" aria-hidden="true"></i> <fmt:message key="header.profile" /></a>
                    </li>
                    <li class="nav-item">
                        <a href="global_article_list.do?requested_page=1" class="nav-link"><i class="fa fa-newspaper-o" aria-hidden="true"></i> <fmt:message key="header.feed" /></a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa fa-globe" aria-hidden="true"></i> <fmt:message key="header.language" />
                        </a>
                        <form action="locale.do" method="post" class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><button type="submit" class="dropdown-item" name="language" value="ru_RU">Русский</button></li>
                            <li><button type="submit" class="dropdown-item" name="language" value="en_US">English</button></li>
                        </form>
                    </li>
                    <c:choose>
                        <c:when test="${user eq null}">
                            <li class="nav-item">
                                <a class="nav-link" href="to_login.do"><i class="fa fa-sign-in" aria-hidden="true"></i> <fmt:message key="header.sign_in" /></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="sign_out.do"><i class="fa fa-sign-in" aria-hidden="true"></i> <fmt:message key="header.log_out" /></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</header>
</body>
</html>
