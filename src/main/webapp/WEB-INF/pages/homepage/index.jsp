<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>

<m:layout title="${title}">
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <div class="login_form">
        <h1>Přihlásit se</h1>
        <form name='loginForm' method="POST" action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />">
            <table>
                <tr>
                    <td><label for="email">Email: </label></td>
                    <td><input type="text" name="email" id="email"></td>
                </tr>
                <tr>
                    <td><label for="password">Heslo: </label></td>
                    <td><input type="password" name="password" id="password"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Přihlásit se"></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />
        </form>
    </div>
</m:layout>