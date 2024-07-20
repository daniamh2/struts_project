

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

#wrapper {
	background-color: #539ce9;
	padding: 10px;
	text-align: center;
	color: white;
}

#container {
	max-width: 800px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h3 {
	margin-top: 0;
}

table {
	width: 100%;
}

td {
	padding: 5px;
}

.save {
	padding: 10px 20px;
	background-color: #539ce9;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.save:hover {
	background-color: #338beb;
}
h2{
color:white;}
select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}a {
	display: block;
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update task</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<meta charset="UTF-8">
<title>Update Task</title>
<body>
	<div class="content">

		<div id="wrapper">
			<div id="header">
				<h2>
					<s:text name="task" />
					<s:text name="Information" />
				</h2>
			</div>
		</div>

		<div id="container">
			<h3>
				<s:text name="tasks" />
			</h3>
			<form
				action="saveTask?taskId=<s:property value='#request.task.taskId'/>"
				method="POST" id="uppdate">

				<table>
					<tbody>
						<tr>
							<td><s:text name="name" />:</td>
							<td>
							<td><s:textfield name="taskName"
									value="%{#request.task.taskName}" /></td>
						</tr>

						<tr>
							<td><s:text name="description" />:</td>
							<td><s:textfield name="taskDescription"
									value="%{#request.task.taskDescription}" /></td>

						</tr>
						<tr>
							<td><label><s:text name="pending" />:</label></td>
							<td><s:select name="pending"
									list="#{'1':'Pending', '2':'Rejected', '0':'Approved'}"
									value="%{#request.task.pending}"
									disabled="%{#request.empIds[0]==#session.user.employeeId}" /></td>
						</tr>


						<tr>
							<td><s:text name="status" />:</td>

							<td><select name="statusId"
								<s:if test="#request.task.pending!=0">disabled</s:if>>
									<s:iterator value="#request.statusIds" var="status">
										<option value="<s:property value='%{#status.statusId}' />"
											<s:if test="#status.statusId==#request.task.statusId">selected</s:if>>
											<s:property value="#status.status" />
										</option>
									</s:iterator>
							</select></td>
						</tr>
						<tr>

							<td><label><s:text name="employees" /> :</label></td>
							<td><select id="employees" name="employees" multiple
								<s:if test="#request.role.equals('developer')">disabled</s:if>>

									<s:iterator value="#request.employees" var="employee">
										<option value="<s:property value='#employee.employeeId'/>"
											<s:if test="%{#request.empIds.contains(#employee.employeeId)}">selected="true"
										</s:if>>
											<s:property value="#employee.firstName" />
											<s:property value="#employee.lastName" />
										</option>
									</s:iterator>
							</select></td>
						</tr>
						<tr>

							<td><s:submit value="%{getText('save')}" cssClass="save" /></td>
						</tr>
						<tr>
					</tbody>
				</table>
			</form>
		</div>
		<p>
			<s:url var="list" action="task" />
			<a href="<s:property value='list' />">Back to tasks <s:text
					name="list" /></a>
		</p>
	</div>

	<%@ include file="/views/footer.jsp"%>
	<script>
	document.getElementById("update").addEventListener("submit", function(event) {
		
    event.preventDefault(); 
    
    var formData = new FormData(this);
    
    var employees = Array.from(document.getElementById("employees").selectedOptions).map(option => option.value);
    formData.append("employees", employees);
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", this.action, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log("DONE");
        }
    };
    xhr.send(formData);
});
</script>

</body>
</html>