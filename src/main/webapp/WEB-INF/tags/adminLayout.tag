<%@tag description="Základní šablona stránky" pageEncoding="UTF-8"%>
<%@ attribute name="title" rtexprvalue="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title} | ${applicationName}</title>
    <link rel='stylesheet' href='<c:url value="resources/css/style.css"/>' />
</head>
<body>
<div class="wrapper">
    <div class="menu">
        <div class="items">
            <a href="">Domů</a>
            <a href="">Vytvořit konferenci</a>
            <a href="">Nahrávání článků</a>
            <a href="">Vyhledávání</a>
        </div>
        <div class="right">
            <span class="text">Uživatel: admin</span>
            <a href="">Odhlásit se</a>
        </div>
    </div>
    <div class="content">
        <h1>${title}</h1>
        <jsp:doBody/>
    </div>
    <div class="footer">
        &copy; 2015
    </div>
</div>

</body>
</html>