<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<m:adminLayout title="${title}">
    <div class="left">
        <h2>Vlož konfigurační soubor</h2>
        <form:form method="get" action="/admin/article/upload?${_csrf.parameterName}=${_csrf.token}" commandName="article" modelAttribute="article" enctype="multipart/form-data">
            <table>
                <tr>
                        <td><form:label for="configFile" path="configFile">Konfigurační soubor: </form:label></td>
                        <td><input id="configFile" name="configFile" type="file" value=""/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table>
        </form:form>

    </div>
</m:adminLayout>