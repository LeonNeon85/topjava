<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <% System.out.println(request.getRequestURL());
        String url = request.getRequestURL().toString();
        System.out.println(url.substring(0,(url.length() - request.getRequestURI().length())) + request.getContextPath() + "/"+" - this is getRequestURL");

    %>
    <form method="post" action="meals/createOrUpdate">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>

        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>

</body>
</html>

