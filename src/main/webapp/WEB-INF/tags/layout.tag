<%@tag description="Základní šablona stránky" pageEncoding="UTF-8"%>
<%@ attribute name="title" rtexprvalue="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title} | ${applicationName}</title>
   <link rel='stylesheet' href='<c:url value="resources/css/style.css"/>' />
</head>
<body>
<jsp:doBody/>
</body>
</html>