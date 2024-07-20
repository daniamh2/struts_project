<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
 "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	document
			.addEventListener(
					"DOMContentLoaded",
					function() {
						document
								.getElementById("addTaskButton")
								.addEventListener(
										"click",
										function() {
											var table = document
													.getElementById("sortableTable");
											var newRow = table
													.insertRow(table.rows.length);
											var cell1 = newRow.insertCell(0);
											var cell2 = newRow.insertCell(1);
											var cell3 = newRow.insertCell(2);
											var cell4 = newRow.insertCell(3);

											// Set up the new task ID input field
											cell1.innerHTML = '<td>Id</td>';

											// Set up the new task name input field
											cell2.innerHTML = '<td><input type="text" id="newTaskName"></td>';

											// Set up the new task pending status dropdown
											cell3.innerHTML = '<td><select id="newTaskPending" disabled="%{#request.role.equals("manager")}">'
													+ '<option value="1">Pending</option>'
													+ '<option value="2">Rejected</option>'
													+ '<option value="0">Approved</option>'
													+ '</select></td>';

											// Set up the new task status dropdown using a placeholder
											cell4.innerHTML = '<td><select id="newTaskStatus" name="taskStatus" disabled="true">'
													+ '<option value="">Assigned</option>' // Add a placeholder option
													+ '</select></td>';

											// Add the "Save" button after the new row
											var saveButtonCell = newRow
													.insertCell(4);
											saveButtonCell.innerHTML = '<button onclick="saveNewTask()">Save</button>';
										});
					});

	function saveNewTask() {
		var taskName = document.getElementById("newTaskName").value;
		var pending = document.getElementById("newTaskPending").value;
		var url = '/struts-task/views/saveNewTaskData'; // Define the URL here
		$
				.ajax({
					url : url,
					method : 'POST',
					data : {
						taskName : taskName,
						pending : pending
					},
					success : function(response) {
						var newTask = response.newTask; 
						var newTaskObject = JSON.parse(newTask); // Parse the JSON string into an object
						var taskId = newTaskObject.taskId; // Access the taskId property
						var table = document.getElementById("sortableTable");
						table.deleteRow(table.rows.length - 1); // Remove the new row
						var newRow = table.insertRow(table.rows.length);
						var cell1 = newRow.insertCell(0);
						var cell2 = newRow.insertCell(1);
						var cell3 = newRow.insertCell(2);
						var cell4 = newRow.insertCell(3);
						cell1.innerHTML = '<td>' +
						  '<s:url var="updateTaskUrl" action="updateTask">' +
						  '<s:param name="taskId" value="' + taskId + '" />' +
						  '</s:url>' +
						  '<a href="<s:property value="#updateTaskUrl"/>" />' + taskId + '</a>' +
						  '</td>';  
						cell2.innerHTML = '<td>' + newTaskObject.taskName + '</td>';

						cell3.innerHTML = '<td>' + (newTaskObject.pending == 1) ? 'pending'
								: ((newTaskObject.pending == 2) ? 'rejected'
										: 'approved');
						+'</td>';

						// Set up the new task status dropdown using a placeholder
						cell4.innerHTML = '<td><select id="newTaskStatus" name="taskStatus" disabled="true">'
								+ '<option value="">Assigned</option>' // Add a placeholder option
								+ '</select></td>';
						// Remove the form elements
						document.getElementById("newTaskName").value = ""; // Clear the task name input
						document.getElementById("newTaskPending").value = ""; // Reset the pending status dropdown
						document.getElementById("newTaskId").value = ""; // Reset the pending status dropdown

						// Hide the form or remove the new row, depending on your preference
						table.deleteRow(table.rows.length - 2); // Remove the new row

						$('#result-container').html(response);
					},
					error : function(xhr, status, error) {
						console.error("Error:", error);
						alert("Failed to add task. Please try again.");
					}
				});
	}
</script>

<script>
	$(document).ready(function() {
		$('.status-dropdown').on('change', function() {
			var taskId = $(this).data('taskid'); // Changed 'data-taskid' to 'taskid'
			var newStatusId = $(this).val();

			var url = '/struts-task/updateTaskStatus'; // Define the URL here

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
<script>
	// Object to store the current order for each column
	var columnOrders = {};

	function sortTasks(sortBy) {
		var currentOrder = columnOrders[sortBy] || ''; // Get the current order for the column
		// Set the sortBy parameter value
		if (currentOrder === 'asc') {
			currentOrder = 'desc';
		} else if (currentOrder === 'desc') {
			currentOrder = ''; // Reset to no specific order on third click
		} else if (currentOrder === '') {
			currentOrder = 'asc'; // Reset to no specific order on third click
		}

		// Set the current order for the column
		columnOrders[sortBy] = currentOrder;
		if (currentOrder === '') {
			delete columnOrders[sortBy];
		}
		// Get the URL
		var url = '/struts-task/views/sort';
		// Make AJAX call
		$.ajax({
			url : url,
			method : 'POST',
			data : {
				sortBy : sortBy,
				order : currentOrder,
				orders : $('#orders').val()
			}, // Pass sortBy and currentOrder as data
			success : function(response) {
				console.log("Ajax success:", response);
				$('.newContent').html(response);
				// Update the content of the sortableTable div with the response data
				// Update sort indicators for all columns
				for ( var column in columnOrders) {
					var indicator = '';
					if (columnOrders[column] === 'asc') {
						indicator = '▲';
					} else if (columnOrders[column] === 'desc') {
						indicator = '▼';
					}
					$(
							'th[onClick="sortTasks(\'' + column
									+ '\')"] .sort-indicator').html(indicator);
				}
			},
			error : function(xhr, status, error) {
				// Handle errors if any
				console.error(error);
			}
		});
	}
</script>
<style>
.sort-indicator {
	color: #007bff;
	font-size: 10px; /* Adjust size as needed */
	visibility: visible;
}
/* Table styles */
table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
	border-radius: 5px;
	overflow: hidden;
}

th, td {
	padding: 12px 16px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #f8f9fa;
	font-weight: bold;
}

tr:hover {
	background-color: #e9ecef;
}

/* Team card styles */
.team {
	border: 1px solid #ddd;
	border-radius: 8px;
	padding: 20px;
	margin-bottom: 20px;
	background-color: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.team-info {
	margin-bottom: 20px;
}

.team h3 {
	margin-bottom: 10px;
	color: #333;
}

.team table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 15px;
}

.team th, .team td {
	padding: 12px 16px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

.team th {
	background-color: #f8f9fa;
	font-weight: bold;
	cursor: pointer;
}

.team td {
	color: #555;
}

.team-actions {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.team-actions form {
	display: flex;
	gap: 10px;
}

.team-actions form button {
	padding: 10px 20px;
	font-size: 14px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.team-actions form .update-btn {
	background-color: #007bff;
	color: #fff;
}

.team-actions form .delete-btn {
	background-color: #dc3545;
	color: #fff;
}

.team-actions form button:hover {
	opacity: 0.9;
}

/* Responsive styles */
@media ( max-width : 768px) {
	.team {
		padding: 15px;
	}
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
</style>
</head>
<body>

	<div class="newContent">
		<div class="content">

			<s:if test="%{#request.tasks != null and #request.tasks.size() > 0}">
				<div class="team">
					<div class="team-info">

						<h3>
							<s:text name="tasks" />
						</h3>
						<button id="addTaskButton">+</button>
						<input type="hidden" id="sortBy" name="sortBy" /> <input
							type="hidden" id="order" name="order"
							value="<s:property value='%{#request.order}' />" /> <input
							type="hidden" id="orders" name="orders"
							value="<s:property value='%{#request.orders}' />" />
						<table border="1" id="sortableTable">
							<tr>

								<th onClick="sortTasks('taskId')"><s:text name="Id" /> <span
									class="sort-indicator" id="taskIdIndicator"></span></th>
								<th onClick="sortTasks('taskName')"><strong><s:text
											name="name" /> <span class="sort-indicator"
										id="taskNameIndicator"></span></th>
								<th onClick="sortTasks('pending')"><s:text name="pending" />
									<span class="sort-indicator" id="pendingIndicator"></span></th>
								<th onClick="sortTasks('statusId')"><s:text name="status" />
									<span class="sort-indicator" id="statusIndicator"></span></th>

							</tr>
							<s:iterator value="#request.tasks" var="task">
								<tr>
									<td><s:url var="updateTaskUrl" action="updateTask">
											<s:param name="taskId" value='#task.taskId' />

										</s:url> <a href="<s:property value='#updateTaskUrl'/>"><s:property
												value='#task.taskId' /></td>

									<td><s:property value='#task.taskName' /></td>
									<td><s:property
											value="%{#task.pending == 1 ? 'pending' : (#task.pending == 2 ? 'rejected' : 'approved')}" />
									</td>
									<td><select class="status-dropdown"
										data-taskid="<s:property value="#task.taskId"/>"
										name="statusId" <s:if test="#task.pending!=0">disabled</s:if>>
											<s:iterator value="#request.statusIds" var="status">
												<option value="<s:property value='%{#status.statusId}' />"
													<s:if test="#status.statusId==#task.statusId">selected</s:if>>
													<s:property value="#status.status" />
												</option>
											</s:iterator>
									</select></td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</s:if>
		</div>
		<s:if test="#request.role.equals('manager')">
			<div class="content">
				<div class="container">
					<div id="header">
						<h2>
							<s:text name="team" />
							<s:text name="Information" />
						</h2>
					</div>
					<div id="content">
						<div class="team">
							<div class="team-info">
								<s:iterator value="#request.teams" var="team">
									<s:if
										test="#request.teamEmployees.containsKey(#team.teamId) && #request.teamEmployees[#team.teamId].size() > 0">

										<div class="team">
											<div class="team-info">


												<a
													href="<s:url action='updateTeam'><s:param name='teamId' value='%{#team.teamId}'/></s:url>"><s:property
														value="%{#team.teamId}" /></a>
												<form id="updateTeam" action="updateTeam" method="GET">
													<s:hidden id="taskIdInput" name="teamId"
														value="<s:property value='%{#team.teamId}' />" />
												</form>

												<h3>
													<s:property value="#team.teamName" />
												</h3>
												<table>
													<tr>
														<th><s:text name="employee" /></th>
														<th><s:text name="role" /></th>
													</tr>
													<s:iterator value="#request.teamEmployees[#team.teamId]"
														var="employee">
														<tr>
															<td><s:property value="#employee.firstName" /> <s:property
																	value="#employee.lastName" /></td>
															<td><s:iterator value="#request.roles" var="role">
																	<s:if test="%{#role.roleId == #employee.roleId}">
																		<s:property value="#role.roleName" />
																	</s:if>
																</s:iterator></td>
														</tr>
													</s:iterator>
												</table>
											</div>
											<form
												action="updateTeam?teamId=<s:property value="%{#team.teamId}"/>"
												method="POST">
												<s:submit value="%{getText('update')}" cssClass="save" />
											</form>
											<form
												action="deleteTeam?teamId=<s:property value="%{#team.teamId}"/>"
												method="POST">
												<s:submit value="%{getText('delete')}" cssClass="save" />

											</form>
										</div>
									</s:if>
								</s:iterator>
							</div>
						</div>
					</div>
				</div>
			</div>
		</s:if>

		<s:if test="empty #request.tasks">
			<p>
				No
				<s:text name="tasks" />
			</p>
		</s:if>

	</div>
</body>

</html>
