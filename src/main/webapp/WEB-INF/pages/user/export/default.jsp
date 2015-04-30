<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<m:userLayout title="${title}">
  <div class="left">
    <form:form method="POST" commandName="export">
        <table>
          <tr>
            <td>Název: </td>
            <td><form:input path="name" /></td>
          </tr>
        </table>
    </form:form>
  </div>
</m:userLayout>