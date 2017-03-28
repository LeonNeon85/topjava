<%--
  Created by IntelliJ IDEA.
  User: Kpks
  Date: 27.03.2017
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
    <title>Meals list</title>

    <style>
        .exceed{
        color: red;
     }
        .notExeed {
            color: green;
        }
    </style>

</head>
<body>
<h1>Meals list:</h1>

<table border="1" bordercolor="black"  cellpadding="4" cellspacing="2" >
    <thead>
    <tr>
        <th> Дата и время </th>
        <th> Описание </th>
        <th> Калории </th>
    </tr>
    </thead>

    <tbody>
        <c:forEach var="meal" items="${mealsListExeeded}">
            <!-- Определение стиля текста -->
            <%--<c:out value="${meal.isExceed() ? 'exceed' : 'notExeed'}" />--%>
            <c:if test="${meal.isExceed()}">
                <c:set var="textStyle" value="exceed"/>
            </c:if>
            <c:if test="${!meal.isExceed()}">
                <c:set var="textStyle" value="notExeed"/>
            </c:if>

            <tr class="${textStyle}">
                <td> <javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /> </td>
                <td> ${meal.getDescription()} </td>
                <td> ${meal.getCalories()} </td>
            </tr>
        </c:forEach>
    </tbody>


</table>

</body>
</html>

