<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<m:adminLayout title="${title}">
    Jméno: ${conference.name}
</m:adminLayout>