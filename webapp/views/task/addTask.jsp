<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

#wrapper {
	background-color: #4CAF50;
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
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.save:hover {
	background-color: #45a049;
}

select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}
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

select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}
h2{
color:white;}
</style>
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update task</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />

<meta charset="UTF-8">
<title>Update Task</title>
</head>
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
			<form action="saveNewTask" method="POST" id="uppdate">
				<table>
					<tbody>
						<tr>
							<td><s:text name="name" />:</td>
							<td><s:if
									test="%{#request.task.pending==1||#request.task.pending==2}">
									<s:property value="%{#request.task.taskName}" />
								</s:if> <s:else>
									<td><s:textfield name="taskName"
											value="%{#request.task.taskName}" /></s:else>
						</tr>

						<tr>
							<td><s:text name="description" />:</td>
							<td><s:if
									test="%{#request.task.pending==1||#request.task.pending==2}">
									<s:property value="#request.task.taskDescription" />
								</s:if> <s:else>
									<s:textfield name="taskDescription"
										value="%{#request.task.taskDescription}" />
								</s:else></td>

						</tr>

						<tr>
							<td><label><s:text name="pending" />:</label></td>
							<td><s:select name="pending"
									list="#{'1':'Pending', '2':'Rejected', '0':'Approved'}"
									value="%{#request.task.pending}"
									disabled="%{not #request.role.equals('manager')}" /></td>
						</tr>


						<tr>
							<td><s:text name="status" />:</td>

							<td><s:select name="statusId" list="#request.statusIds"
									listKey="statusId" listValue="status"
									value="%{#request.task.statusId} " disabled="true" /></td>
						</tr>
						<tr>

							<td><s:text name="employees" /> :</td>
							<td><select id="employees" name="employees" multiple
								<s:if test="%{#request.role.equals('developer')}">disabled</s:if>>
									<s:if test="%{#request.role.equals('developer')}">
										<option value="<s:property value="#employee.employeeId" />"
											selected="true">
											<s:property value="#session.user.firstName" />
											<s:property value="#employee.lastName" />
										</option>
									</s:if>
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
					</tr>
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