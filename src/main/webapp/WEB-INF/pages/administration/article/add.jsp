<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<m:adminLayout title="${title}">
    <div class="left">
        <h2>1. Vlož konfigurační soubor</h2>
        <form:form method="POST" action="/admin/article/upload?${_csrf.parameterName}=${_csrf.token}"
                   commandName="article" modelAttribute="article" enctype="multipart/form-data">
            <table>
                <tr>
                    <td><form:label path="conference">Konference: </form:label></td>
                    <td>
                        <form:select path="conference">
                            <form:option value=""></form:option>
                            <form:options items="${conf}"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td><form:label for="configFile" path="configFile">Konfigurační soubor: </form:label></td>
                    <td><input id="configFile" name="configFile" type="file" value=""/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table>
        </form:form>
    <h2>2. Nahraj články</h2>
        <form:form method="POST" action="/admin/article/uploadArticles?${_csrf.parameterName}=${_csrf.token}"
                   commandName="article" modelAttribute="article" enctype="multipart/form-data">
            <table>
                <tr>
                    <td><form:label path="conference">Konference: </form:label></td>
                    <td>
                        <form:select path="conference">
                            <form:option value=""></form:option>
                            <form:options items="${conf}"/>
                        </form:select>
                        <div class="error">
                            <form:errors path="conference" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="articles"> Články v pdf: </form:label></td>
                    <td><input id="articles" name="articles" type="file" value="" multiple="multiple" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table>
        </form:form>
    </div>
</m:adminLayout>