<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<style>
/* Content container */
.content {
	margin-left: 320px; /* Adjust margin to accommodate sidebar width */
	padding: 20px;
}

body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 800px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.team {
	margin-bottom: 20px;
	padding: 15px;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.team-info {
	margin-bottom: 10px;
}

.employee {
	margin-left: 20px;
	padding-left: 20px;
	border-left: 2px solid #ccc;
}

.employee-info {
	margin-bottom: 10px;
}

/* Sidebar container */
.sidebar {
	width: 250px;
	background-color: #f4f4f4;
	padding: 20px;
	border-right: 1px solid #ddd;
	float: left; /* Float sidebar to the left */
}
/* Header container */
.header {
	margin-left: 300px; /* Adjust margin to accommodate sidebar width */
	padding: 20px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		$('.status-dropdown').on('change', function() {
			var taskId = $(this).data('taskid'); // Changed 'data-taskid' to 'taskid'
			var newStatusId = $(this).val();

			var url = '/strutsTask1/updateTaskStatus'; // Define the URL here

			console.log('URL:', url); // Log the URL

			$.ajax({
				url : url,
				method : 'POST',
				data : {
					taskId : taskId,
					statusId : newStatusId
				},
				success : function(response) {
					console.log("Ajax success:", response);
					alert("Selected option value: " + taskId + newStatusId)
					$('#result-container').html(response); // Display the result in #result-container
				}
			});
		});
	});
</script>
<body>
	<div class="header">
		<h2>
			<s:property value="#session.user.firstName" />
			's Profile Page
		</h2>
		<div class="task-container">
			<div class="task-details">
				<h2>
					<strong><s:text name="employee" /> <s:text
							name="  Information" /></strong>
				</h2>
				<p>
					<strong><s:text name="first_name" />:</strong>
					<s:property value="#session.user.firstName" />
				</p>
				<p>
					<strong><s:text name="last_name" />:</strong>
					<s:property value="#session.user.lastName" />
				</p>
				<p>
					<strong><s:text name="Email" />:</strong>
					<s:property value="#session.user.email" />
				</p>

				<p>
					<strong><s:text name="address" />:</strong>
					<s:property value="#session.user.address" />
				</p>
				<strong><s:text name="role" />:</strong>
				<td><s:property value="#request.role" /></td>
				<p>
					<s:if test="%{#session.user.isManager==false}">

						<strong><s:text name="team" />:</strong>

						<s:iterator value="#session.teams" var="team">
							<s:if test="%{#team.teamId == #session.user.teamId}">
								<s:property value="#team.teamName" />
							</s:if>
						</s:iterator>
					</s:if>
				</p>
			</div>
		</div>
	</div>
</body>
</html>
