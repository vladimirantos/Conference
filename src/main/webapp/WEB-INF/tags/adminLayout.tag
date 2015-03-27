<%@tag description="Základní šablona stránky" pageEncoding="utf-8"%>
<%@ attribute name="title" rtexprvalue="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel='stylesheet' href='<c:url value="/resources/css/style.css"/>' />
</head>
<body>
<div class="wrapper">
    <div class="menu">
        <div class="items">
            <a href="/admin/">Domů</a>
            <a href="/admin/conference/add">Přidat konferenci</a>
            <a href="/404">Nahrávání článků</a>
            <a href="/404">Vyhledávání</a>
        </div>
        <div class="message-box">
            <span class="${mtype}">${messages}</span>
        </div>
        <div class="right">
            <span class="text">Uživatel: admin</span>
            <a href="/404">Odhlásit se</a>
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

<div style="color: red; font-size: 10px; float: right">Debug mode: ${debugMode}</div>
</body>
</html>