<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<m:userLayout title="${title}">

    <div class="search-results">
        <h2>Vyhledávání článků</h2>
        <form:form method="GET" commandName="searchAttributes" action="/search/results/">
            <form:input path="text" cssClass="input-long" maxlength="150" required="required" htmlEscape="true"/>
            <form:select path="type" required="required" >
                <form:options items="${searchTypes}"/>
                <%--<form:option value="title">Název článku</form:option>--%>
                <%--<form:option value="abstract">Abstrakt</form:option>--%>
                <%--<form:option value="authors">Autoři</form:option>--%>
                <%--<form:option value="konference">Konference</form:option>--%>
            </form:select>
            <c:if test="${param.account != null}">
                <input type="hidden" name="account" value="${param.account}" />
            </c:if>
            <input type="submit" value="Vyhledat">
        </form:form>
        <c:forEach var="result" items="${results}">
            <div class="item">
                ${fn:escapeXml(result.authors())}. <i>
                    ${fn:escapeXml(result.name)}
                . </i>Konference: ${fn:escapeXml(result.conferenceObj)} <i>${result.conferenceObj.theme}</i>, ${result.conferenceObj.address},
                ${result.conferenceObj.city}, ${result.conferenceObj.state}. ${result.conferenceObj.monthString()} ${result.conferenceObj.year}.
                    <b><a href="/search/download/${result.conferenceObj.id}/${result.fileName}">Download PDF</a></b>
                    <br><br>
                <b>Abstrakt: </b><br>
                ${result.abstrct}
            </div>

            <c:set var="prev_id" scope="session" value="${result.id}"/>
        </c:forEach>
    </div>
</m:userLayout>