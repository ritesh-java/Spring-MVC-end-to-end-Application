<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
<p>
${message} <br/>

<a href="${pageContext.request.contextPath}/team/add.html">Add New Team</a> <br/>

<!-- Here we can see we are using /team/list. So it will search for controller with /team mapping. Hence, it goes to TeamController page.
"/list" is mapped to listOfTeams() method and hence it will be executed which is calling database using teamService.allMethod() and getting
all the teams -->
<a href="${pageContext.request.contextPath}/team/list.html">Teams List</a><br/>

</p>
</body>
</html>