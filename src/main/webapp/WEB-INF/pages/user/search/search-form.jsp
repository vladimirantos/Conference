<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<m:userLayout title="${title}">
  <div class="searchPanel">
    <h2>Vyhledávání článků</h2>
      <form:form method="GET" commandName="searchAttributes" action="/search/results/">
        <form:input path="text" cssClass="input-long" maxlength="150" required="required"/>
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
  </div>
</m:userLayout>