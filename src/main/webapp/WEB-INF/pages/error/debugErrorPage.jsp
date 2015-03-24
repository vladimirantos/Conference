<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${exception}</title>
    <style>
        body{
            font-family: Segoe UI;
            font-size: 13px;
            background-color: rgba(133, 176, 254, 0.86);
        }

        h1{
            padding-left: 20px;
            padding-top: 10px;
            width: 1300px;
            height: 50px;
            background-color: #328ADC;
            color: #fff;
        }
        h2{
            font-size: 18px;
            color: royalblue;
        }

        .stack{
            width: 1300px;
            margin: auto;
            overflow: hidden;
            background-color: #e4e4e4;
            padding-left: 20px;
        }

        table td{
            font-family: Consolas,monospace;
            font-size: 12px;
            height: 25px;
        }

        p{
            font-size: 14px;
        }
    </style>
</head>
<body>
<h1>${exception}</h1>

<h2>Message: </h2>
<p> <b>${exception.message}</b> </p>

<h2>Call stack</h2>
<div class="stack">
    <c:forEach items="${exception.stackTrace}" var="element">
        <table>
            <tr>
                <td><c:out value="${element}" /></td>
            </tr>
        </table>
    </c:forEach>
</div>
</body>
</html>
