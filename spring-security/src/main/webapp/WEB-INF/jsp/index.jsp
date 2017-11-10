<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>iScream</title>
    <meta charset="utf-8">
    <meta content="author" description="me">
</head>
<body>
    <h1>Hello world</h1>

    <security:authorize access="hasRole('ROLE_ADMIN')">
        <security:authentication var="user" property="principal" />
        <c:out value="Authorizes as: ${user}"/>
    </security:authorize>

</body>
</html>
