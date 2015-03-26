<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>

<c:choose>
    <c:when test="${module.equals('administration')}">
        <m:adminLayout title="${exception.code}: Not found">
            <h1>${exception.code}: Not found</h1>
            <p> ${not empty exception.message ? exception.message :
                    'Požadovaná stránka nebyla nalezena. Zkontrolujte URL adresu nebo opakujte požadavek později.'} </p>
            <p><small>error ${exception.code}</small></p>
        </m:adminLayout>
    </c:when>

    <c:otherwise>
        <m:layout title="${exception.code}: Not found">
            <h1>${exception.code}: Not found</h1>
            <p> ${not empty exception.message ? exception.message :
                    'Požadovaná stránka nebyla nalezena. Zkontrolujte URL adresu nebo opakujte požadavek později.'} </p>
            <p><small>error ${exception.code}</small></p>
        </m:layout>
    </c:otherwise>
</c:choose>


