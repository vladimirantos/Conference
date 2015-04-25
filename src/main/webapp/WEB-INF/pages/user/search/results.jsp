<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<m:userLayout title="${title}">

    <div class="search-results">
        <c:set var="prev_id" scope="session" value="0"/>
        <c:forEach var="result" items="${results}">
            <div class="item">
                ${result.authors()}. <i>${result.name}. </i>${result.conferenceObj}

            </div>

            <c:set var="prev_id" scope="session" value="${result.id}"/>
        </c:forEach>
    </div>
</m:userLayout>