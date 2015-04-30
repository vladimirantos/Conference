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
    <%--<script type="text/javascript" src="<c:url value="/resources/js/jquery.highlight-5.closure.js"/>" />--%>
</head>
<body>
<div class="wrapper">
    <div class="menu">

        <div class="items">
            <c:choose>
                <c:when test="${param.account == 'admin'}">
                    <a href="/admin/">Domů</a>
                    <a href="/admin/conference/add">Správa konferencí</a>
                    <a href="/admin/article/add">Nahrávání článků</a>
                    <a href="/search?account=admin">Vyhledávání</a>
                </c:when>
                <c:otherwise>
                    <a href="/user/">Domů</a>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="message-box">
            <span class="${mtype}">${messages}</span>
        </div>
        <div class="right">
            <span class="text">Uživatel: ${param.account == 'admin' ? 'admin' : 'user'}</span>
            <c:url var="logoutUrl" value="/j_spring_security_logout"/>

            <form action="${logoutUrl}" method="post">

                <input type="image" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAKgSURBVDiNbZLNa5RXFMZ/555735mJpiqGmkhJrYUqtDtroMFqEeLQlU1X7SIFKxRxmW2L/SfqRtyKmNIUGmhcG7rzgzapEErSWGMiakbHTHyTed97TxeZxFB64eEHh4fnuV8y807v56m59o2CdymBGS6ZkAwxw8wok1GaSdwk0Yy2WdE0fpA71ezhG+vttzIgAP+lAW2g+B8+hnlHSlXrGA1IHUbgj9FR7o2MkHbMd0qFmtwO+rS7iD2h07glDYHuPOfl0hL09xM7rTu1IrLskrHdkIB155g9cYJcFaeKZhmNvj5mL16kpUrc4RUBt7XdCGyI0Lp6lY+mpmj39bG1fE8Pg5cvU46NsVapbPsRwWFG6gxenj/P++fOMXflCrK8vB3A9DTzFy5wZHiYtdFRYucOBJBbQRf2FPFtAXru3sWHwIuBAXRjgzcXF3n16BGrx4+TgDA+zv7BQeZ7ewFoVsKsaznXWAJawN7Dh1mcnKTMc4qUWD52jGa9TgGUQGtuju4DB2hkGYtAkfmmT2YUwDrQXl2l6+hRHnhHEEGfPQGgDJ4Co//QIV41GjRjBKDmHM6SbR/14cQE7w4NUXx8EnZVsd01bHcXblcVqdd57+xZ5n8cQ9JmgBPBpZQACEF5/P13rPw5w+nxnymG6pS1KtJVIX02zKnrN3g6M82TS9+SZWHzCZ3gzQxVRwgeX7RZGPmSI79McmbsJ/Lnz2nnOXsOHqS58DcPvv4KH0tS8KRkiAjeDFQV75UQFLf6gn8+/YSVDwdwp07j9u3n2c0J8t+mCBttUlBSSsSYUO/wzklLVXDqXgepg/u/o3/NICK4MhKCkpISY6LUiKpQrWQt+bUSPjCzLwANmSfLAlnmNxUC3isinX/7GohI2VWrXPsXwSwtxdofVVYAAAAASUVORK5CYII="
                       value="Odhlásit se" class="logout" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
    <div class="content">
        <%--<h1>${title}</h1>--%>
        <div class="submenu">
            <c:forEach var="item" items="${submenu}" end="2">
                <a href="${item.key}">${item.value}</a>
            </c:forEach>
        </div>
        <jsp:doBody/>
    </div>
    <div class="footer">
        &copy; 2015
    </div>
</div>

<div style="color: red; font-size: 10px; float: right">Debug mode: ${debugMode}</div>
</body>
</html>