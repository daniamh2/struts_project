<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/views/mainHeader.jsp"%>

<!DOCTYPE html>
<html>
<head>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update Task</title>
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css"/>
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>Employees</h2>
        </div>
    </div>


	<c:forEach var="e" items="${emp}">
       ${e.firstName }
        ${e.lastName }<br>
		<br>

	</c:forEach>



	</div>
	</div>

</body>
 
</html>