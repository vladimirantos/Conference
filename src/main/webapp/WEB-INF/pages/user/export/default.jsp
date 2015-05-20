<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<m:userLayout title="${title}">
    <div class="left">
        <h2>Vytvoření formátu</h2>
        <form:form method="POST" commandName="export">
            <table>
                <tr>
                    <td>Název:</td>
                    <td><form:input path="name"/></td>
                </tr>
                <tr>
                    <td>Formát:</td>
                    <td><form:input path="pattern" class="input-long" required="required"/></td>
                </tr>
                <tr>
                    <td colspan="4" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table>
        </form:form>

        <h2>Exportovat</h2>
        <c:set var="adminurl" value="/search/export/file?account=admin"/>
        <c:set var="userurl" value="/search/export/file"/>
        <c:choose>
            <c:when test="${patterns.size() > 0}">
                <form:form method="POST" commandName="export" action="${param.account == 'admin' ? adminurl : userurl}">
                    <b>Vyber formát výstupu: </b>
                    <ul>
                        <c:forEach items="${patterns}" var="pattern" varStatus="status">
                            <li>
                                <form:radiobutton path="idPattern" value="${pattern.idPattern}"/>
                                <label for="idPattern${status.index+1}">${pattern.name}: ${pattern.pattern}</label>
                            </li>
                        </c:forEach>
                        <form:hidden path="article" />
                    </ul>
                    <input type="submit" value="Export">
                </form:form>
            </c:when>
            <c:otherwise>
                Nebyl nalezen žádný formát. Zkus nejprve vytvořit.
            </c:otherwise>
        </c:choose>
        <c:if test="${result != null}">
            <h2>Výsledek</h2>
            <p>${result}</p>
        </c:if>
    </div>
</m:userLayout>