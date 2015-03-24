<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>

<m:layout title="${exception.code}: Unauthorized">
    <h1>${exception.code}: Unauthorized</h1>
    <p> ${not empty exception.message ? exception.message : 
      'Neoprávněný přístup. Nebyla provedena autentifikace.'} </p>
    <p><small>error ${exception.code}</small></p>
</m:layout>