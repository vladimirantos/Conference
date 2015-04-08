<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<m:adminLayout title="${title}">
    <div class="left">
        <table rules="all" border="1" class="table">
            <tr>
                <th>Název</th>
                <th>Téma</th>
                <th>Adresa</th>
                <th>Datum konání</th>
                <th>Počet článků</th>
                <th>Přidáno dne</th>
            </tr>
            <c:forEach var="conference" items="${conferences}">
                <tr>
                    <td class="longtd">${conference.name}</td>
                    <td class="longtd">${conference.theme}</td>
                    <td class="longtd">${conference.address}, ${conference.city} ${conference.state}</td>
                    <td class="numbertd"><c:choose>
                        <c:when test="${conference.month <= 9}">
                            0${ conference.month}/${conference.year} <br>
                        </c:when>
                        <c:otherwise>
                            ${conference.month}/${conference.year} <br>
                        </c:otherwise>
                    </c:choose></td>
                    <td class="numbertd">${conference.countArticles}</td>
                    <td class="numbertd"><fmt:formatDate value="${conference.creationDate}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
                   <!-- <td class="icontd">
                        <a href=""><img alt="Editovat" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAJTSURBVDhPjdFNaBNBGAbgd2bX2PzUJtLWQ3vQaBOLINqKCAVBsfSm9mpPihcVUfHQQwuVEKEHBRVBPFSv4kFUPAlStFiiHqoVWtOI2DSV2vw1yWaT7Ca7ftONUTCRvjDsYef59p1Zhg3mZY98zpErD9k4+kwTYcZw72AYtzc0IHXX9+Bdmp3uiCTh7zwC1uHF4qMJpFcTl6XqnoYxXvSO2FvdV1u6B+DqBORsFDzJsEVTsLKS2PHfAQIzbg9i1yAcbXthk+NIaBmkwrNwz39HXEdrwwHG8/0jTHIE0TUI07kTLPUKkhqBJjGEsxLCaxo1KU7VHWA8JSw7g/D9xpNA7jNQycMRVVEpGkjZZHjiyrV/LnEdS4R3E3YQThPOzNCLErBAS6VNLg4lqjxsHls4wy1mxXhCmBP2nyDsJfwayM7+wXnaRNhUjTGBhakN0B/3jDPJZWFROz0FKHNUu0BYI2wCTgvzCx8DVWYNiNzpGZflpmF0H4fpEviNhctK9csG4JAIV0b/xiJ8+uYhe6bEz0NqhmnfTvgtXRhhPQuE6cAC2wkXCF/8dL3qauHFtfThFt9RW8zdDxaaABJfAVMnTNVF7c2idn0swgt6aWBr14FNbb59iLUfo8pRq3aBfpCNiTOP8kv1sQgvmsZZp2cbz3+5jyblGWL+IToCVZcZ1Jx+g19pjEUkX29/wOtJcj0+Yyz/yOmL8x+099HV4lJGubUn8G24uq9h2KmTfWU1tWS22+LLasmY/pkxQ5NzWqhcBl0C6Cy1Rf8SdDnrzwqtMgD8AjhKE9XQzde3AAAAAElFTkSuQmCC" /></a>
                        <a href="">
                            <img alt="Smazat" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsTAAALEwEAmpwYAAAABGdBTUEAALGeYUxB9wAAACBjSFJNAAB6JQAAgIMAAPn/AACA6AAAUggAARVYAAA6lwAAF2/XWh+QAAADFklEQVR42qSRS2icVRxHz//e+30z40zSNI1NJ5M0aWpj0hCToEiRqAhWS5G2ii7ERVdioSC4E7pyI5SutIsufCJaFIoQRLJQKVpKwaRYA1XTF01MWpukdJLM8/vuw0VF3HvgtzmrHxw5uWPHrvGDBz7u6u3uvvDRJ58uXr3xjktTnEBOQQZIAOcDOE+kVfuD/f3vbhvY9dyN8xdOm7GXDn0zvm/vw/HaXdSzE8fOrZWL1/+8fURpxX+xzpPRes/A2OiHW4eHB27PL8xvNGqXTDYTt6ryKmZ9hd5NJiru6Xvj+2b5qemVxhNBSTkA1ntVatt0Zmxs7FBZqdr01NTRtNH8IADyXrFILjJvPTby0LGhgt2il25SWy7z81+N1Ys1P5QTBoeiaLJUKrVfrtWn55ZuTeTiOMm3tlKrrCOndvZTW1nFJE3aI94fKegjHd5FSdMzV3eVnCNXMEbPeP/ZPesOiyiiOCaXz1OrrKNEBBMZspmYlVr65tnlxs5za+7XtbqjZEMh57z+yboTd7w/jNYEQAARQQCTpgn1pEnTWoxSVIW7SRoi5z0uQFYLmxXPLHuFDQBCYi3NahV8QD/eqNNMU3wIaOgZ9H5ut/O9VRFmjf62JSP94wXVvZBKx5plSgSch6a1JB5UCAFECNA54Nxsv/NbrBYuRerLRSUv/JjKy+tZ7V7cHh9VwiMuQOD+kwAouZ9ZDTg/0xdCm4mFP4y6eQt5NQaanskzd+xrsRZ5ssNMhuAxEv6dkgBF5z4fCr47m9WsRNr+7uV5HcAFMEDDhq++nm8e3503fdti9br84w2ggndbh9rbXikUYsgJM5YTqedKCGA9BAJZBUsN//Yv99Iro3lzXAhoQANqc6FwoO/pCdM22Iu0xCFj5FRPTtGVVXRmFaLAC0QaZjfsfnHhgU6tDuZFKIigqkm6mB0doWP/Xih1+Yb19dQHEh+wIRADERADAa7/VncnuxTDPRp6NOhHrb2WeLevZXtP8YezF08vlCtf1FNPNfVUUo8SRaQEEUGL0IDvanC+AmEDkBAC/4e/BwACT2zMWyQBIAAAAABJRU5ErkJggg==">
                        </a>
                    </td> -->
                </tr>
            </c:forEach>
        </table>

    </div>
</m:adminLayout>