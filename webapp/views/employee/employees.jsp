<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Employees</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<style>
/* CSS for table */
table {
	width: 100%;
	border-collapse: collapse;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Add box-shadow */
}

th, td {
color:#539ce9;
	border: 1px solid #dddddd;
	text-align: left;
	padding: 12px; /* Increase padding */
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

/* CSS for buttons */
input[type="button"] {
	background-color: #539ce9;
	color: white;
	padding: 12px 24px;
	margin: 25px 0;
	border: none;
	cursor: pointer;
	border-radius: 6px; /* Add border radius */
	transition: background-color 0.3s; /* Add transition */
}

input[type="button"]:hover {
	background-color: #539ce9;
}

/* CSS for anchor tags */
a {
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
}

a:hover {
	color: #ff0000; /* Change hover color */
}
</style>

</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
S

<body>
	<div class="content">

		<div id="wrapper">
			<div id="header">
				<h2>Employees List</h2>
			</div>
		</div>

		<div id="container">
			<div id="content">
				<s:url var="addEmployeeUrl" action="addEmployee" />
				<s:url var="logoutUrl" action="logout" />
				<s:url var="addTaskUrl" action="addTask" />
				<s:url var="profileUrl" action="view" />
				<s:url var="teamsUrl" action="teamsList" />
				<s:url var="pendingTasksUrl" action="pending" />


				<table border="1">
					<tr>
						<th>Id</th>
						<th><s:text name="first_name" /></th>
						<th><s:text name="last_name" /></th>
						<!-- 	<th><s:text name="email" /></th>
					 	<th><s:text name="address" /></th>-->
						<th><s:text name="role" /></th>
						<th><s:text name="team" /></th>
					</tr>
					<s:iterator value="#request.employeeList" var="employee">

						<tr>
							<td><a
								href="<s:url action='updateEmployee'>
						<s:param name='employeeId' value='%{#employee.employeeId}' />
					</s:url>"><s:property
										value="%{#employee.employeeId}" /> </a> <a
								href="deleteEmployee?employeeId=<s:property value="#employee.employeeId"/>"
								onclick="return confirm('Are you sure you want to delete the record?')">Delete</a></td>
							<td><s:property value="#employee.firstName" /></td>
							<td><s:property value="#employee.lastName" /></td>
							<!-- 	<td><s:property value="#employee.email" /></td>
							<td><s:property value="#employee.address" /></td>-->
							<td><s:iterator value="#request.roles" var="role">
									<s:if test="#role.roleId == #employee.roleId">
										<s:property value="#role.roleName" />
									</s:if>
								</s:iterator></td>
							<td><s:iterator value="#request.teams" var="team">
									<s:if test="#team.teamId == #employee.teamId">
										<s:property value="#team.teamName" />
									</s:if>
								</s:iterator></td>

						</tr>
					</s:iterator>
				</table>
				<div>
					<form>
						<input type="hidden" name="teams" value=requeston.teams" /> <input
							type="hidden" name="roles" value="#request.roles" /> <input
							type="button" value="Add EMPLOYEE"
							onclick="window.location.href='${addEmployeeUrl}'" class="emp" />
					</form>
				</div>
				<%@ include file="/views/footer.jsp"%>

			</div>
		</div>
	</div>

</body>
</html>
