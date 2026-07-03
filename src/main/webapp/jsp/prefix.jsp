<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prefixes</title>
    <style>table, th, td { border: 1px solid #ccc; border-collapse: collapse; padding: 6px; }</style>
</head>
<body>
<h2>Manage Prefixes</h2>

<form action="${pageContext.request.contextPath}/prefix/save" method="post">
    <input type="hidden" name="id" value="${prefix.id}" />
    <label>Prefix: <input type="text" name="prefix" value="${prefix.prefix}" required/></label>
    <button type="submit">Save</button>
</form>

<hr/>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Prefix</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${prefixList}" var="p">
        <tr>
            <td>${p.id}</td>
            <td>${p.prefix}</td>
            <td><a href="${pageContext.request.contextPath}/prefix/delete/${p.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>