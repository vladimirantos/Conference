<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<m:adminLayout title="${title}">
    <div class="left">
        <form:form method="POST" commandName="conference">
            <table>
                <tr>
                    <td>Název: </td>
                    <td><form:input path="name" /></td>

                </tr>
                <tr>
                    <td><input type="submit"></td>
                </tr>
            </table>
            <!-- <table>
                <tr>
                    <td><label for="name">Název</label></td>
                    <td colspan="4"><input type="text" name="name" id="name" maxlength="40" class="input-long" required></td>
                </tr>
                <tr>
                    <td><label for="theme">Téma</label></td>
                    <td colspan="4"><input type="text" name="theme" id="theme" maxlength="80" class="input-long" required></td>
                </tr>
                <tr>
                    <td><label for="building">Adresa</label></td>
                    <td><input type="text" name="building" id="building" maxlength="40"></td>

                    <td><label for="city">Město</label></td>
                    <td><input type="text" name="city" id="city" maxlength="30" required></td>

                    <td><label for="state">Stát</label></td>
                    <td><input type="text" name="state" id="state" maxlength="30" required></td>
                </tr>
                <tr>
                    <td>Datum konání</td>
                    <td colspan="4">
                        <select name="month" required>
                                <option disabled selected>Měsíc</option>
                                <option value="1">Leden</option>
                                <option value="2">Únor</option>
                                <option value="3">Březen</option>
                                <option value="4">Duben</option>
                                <option value="5">Květen</option>
                                <option value="6">Červen</option>
                                <option value="7">Červenec</option>
                                <option value="8">Srpen</option>
                                <option value="9">Září</option>
                                <option value="10">Říjen</option>
                                <option value="11">Listopad</option>
                                <option value="12">Prosinec</option>
                        </select><input type="number" name="year" placeholder="Rok" required></td>
                </tr>
                <tr>
                    <td colspan="4" align="center"><input type="submit" value="Uložit"></td>
                </tr>
            </table> -->
        </form:form>
    </div>
    <div class="right">
        <h2>Seznam konferencí</h2>
    </div>
</m:adminLayout>