<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<c:choose>
    <c:when test="${debugMode}">
        <m:layout title="${exception}">
            <h1>${exception}</h1>
            <p> ${exception.message} </p>
            <c:forEach items="${exception.stackTrace}" var="element">
                <c:out value="${element}" />
            </c:forEach>
        </m:layout>
    </c:when>

    <c:otherwise>
        <m:layout title="Neočekávaná chyba">
            <h1>Neočekávaná chyba</h1>
            <p>${not empty exception.message ? exception.message : 
                 'Došlo k neočekávané chybě. Opakujte akci později.'}</p>
        </m:layout>
    </c:otherwise>
</c:choose>