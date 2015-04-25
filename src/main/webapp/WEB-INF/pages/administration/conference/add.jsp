<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<m:adminLayout title="${title}">
    <div class="left">
        <form:form method="POST" commandName="conference">
            <table>
                <tr>
                    <td>Název: </td>
                    <td colspan="4"><form:input path="name" cssClass="input-long" maxlength="100" required="required" htmlEscape="true" /></td>
                    <td><span class="error"><form:errors path="name" /></span></td>
                </tr>
                <tr>
                    <td><form:label path="theme">Téma: </form:label></td>
                    <td colspan="4"><form:input path="theme" cssClass="input-long" maxlength="150" required="required"/></td>
                    <td><span class="error"><form:errors path="theme"/></span></td>
                </tr>
                <tr>
                    <td><form:label path="address">Adresa: </form:label></td>
                    <td><form:input path="address" maxlength="40"/></td>

                    <td><form:label path="city">Město: </form:label></td>
                    <td><form:input path="city" maxlength="30" required="required"/></td>

                    <td><form:label path="state">Stát: </form:label></td>
                    <td><form:input path="state" maxlength="30" required="required"/></td>

                    <td><div class="error"><form:errors path="city"/> <form:errors path="state"/></div></td>
                </tr>

                <tr>
                    <td>Datum konání: </td>
                    <td colspan="5">
                        <form:select path="month" required="required">
                            <form:option value="0">Měsíc</form:option>
                            <form:options items="${months}" />
                        </form:select>
                        <form:input path="year" placeholder="Rok" required="required"/>
                        <div class="error">
                            <form:errors path="month" />
                            <form:errors path="year" />
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table>
        </form:form>
    </div>
    <div class="right">
        <h2>Nejnovější konference</h2>
        <c:forEach var="conference" items="${conferences}" end="2">
                <div class="item">
                    <h3>${conference.name}</h3>
                    <b>Téma: </b>${conference.theme}<br>
                    <b>Adresa konání: </b>${conference.address}, ${conference.city} ${conference.state}<br>
                    <b>Datum konání: </b>
                    <c:choose>
                        <c:when test="${conference.month <= 9}">
                            0${ conference.month}/${conference.year} <br>
                        </c:when>
                        <c:otherwise>
                            ${conference.month}/${conference.year} <br>
                        </c:otherwise>
                    </c:choose>
                    <span style="font-size: 12px">Přidáno dne: <fmt:formatDate value="${conference.creationDate}" pattern="dd.MM.yyyy HH:mm:ss" /></span><br>
                    <span style="font-size: 12px">Počet článků: </span>${conference.countArticles}
                </div>
        </c:forEach>
    </div>
</m:adminLayout>