<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/add-student-style.css"/>
</head>

<body>
<div id="wrapper">
		<div id="header">
		
		
		</div>
	</div>	
	    <h2>Add New Task</h2>
    <form action="AddTaskServlet" method="post">
    	<input type="hidden" name="command" value="MYTASK"/>
    
        <label for="name">Task Name:</label>
        <input type="text" name="name"><br>

        <label for="description">Task Description:</label>
        <input type="text" name="description"><br>

        <input type="submit" value="Create Task">
    </form>
</body>
</html>