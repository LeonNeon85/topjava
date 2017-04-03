
<%--
  Created by IntelliJ IDEA.
  User: Kpks
  Date: 29.03.2017
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
    <title>Edit data</title>
</head>
<body>

<form method="POST" action='meals' name="addMeal" >

    Дата и время: <input type="datetime" name="dateTime" value="<javatime:format value="${Meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" />"
                         pattern="[0-9]{4}-[0-1][0-9]-[0-3][0-9]\s[0-2][0-9]:[0-5][0-9]"
                         placeholder="yyyy-MM-dd HH:mm"
                         title="yyyy-MM-dd HH:mm"
                         required />   <br />
    Описание: <input  type="text" name="description" value="${Meal.getDescription()}"
                       pattern="[А-Яа-яЁё\s]+$"
                       required />    <br />
    Калории : <input  type="number" name="calories" value="${Meal.getCalories()}" max="5000" required/>   <br />

    <input  type="hidden" name="mealId" value="${param.get("mealId")}"/>

    <input type="submit" value="Submit" />
</form>

<form action="meals" >
    <button type="submit" > Back </button>
</form>

</body>
</html>

