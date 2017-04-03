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
        <th> Id </th>
        <th> Дата и время </th>
        <th> Описание </th>
        <th> Калории </th>
        <th colspan=2>Action</th>
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
                <td> <c:out value="${meal.getId()}" /> </td>
                <td> <javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /> </td>
                <td> <c:out value="${meal.getDescription()}" /> </td>
                <td> <c:out value="${meal.getCalories()}" />  </td>
                <td><a href="meals?action=update&mealId=<c:out value="${meal.getId()}"/> ">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/> ">Delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>



<form action="edit.jsp" method="post">
    <button type="submit" formtarget="_self" > Add meal </button>
</form>

<form action="index.html" >
    <button type="submit" > Back </button>
</form>

</body>
</html>

