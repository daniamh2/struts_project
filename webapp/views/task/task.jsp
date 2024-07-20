<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tasks</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
		var url = '/struts-task/views/task';
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
					} else {
						var indicator = '';

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
	<div class="newContent">
		<%@ include file="/views/mainHeader.jsp"%>


		<s:if test="%{#request.tasks != null and #request.tasks.size() > 0 }">
			<div class="content">
				<div class="team">
					<div class="team-info">
						<h2>
							<s:text name="my" />
							<s:text name="tasks" />
						</h2>
						<input type="hidden" id="sortBy" name="sortBy" /> <input
							type="hidden" id="order" name="order"
							value="<s:property value='%{#request.order}' />" /> <input
							type="hidden" id="orders" name="orders"
							value="<s:property value='%{#request.orders}' />" />
						<table border="1" id="sortableTable">
							<div class="tasks-container">
								<table border="1" id="sortableTable">
									<thead>
										<tr>
											<th onClick="sortTasks('taskId')"><strong><s:text
														name="task" /> <s:text name="Id" /></strong><span
												class="sort-indicator" id="statusIndicator"></span></th>
											<th onClick="sortTasks('taskName')"><strong><s:text
														name="name" /></strong><span class="sort-indicator"
												id="statusIndicator"></span></th>
											<th onClick="sortTasks('taskDescription')"><strong><s:text
														name="description" /></strong><span class="sort-indicator"
												id="statusIndicator"></span></th>
											<th onClick="sortTasks('pending')"><strong><s:text
														name="pending" /></strong><span class="sort-indicator"
												id="statusIndicator"></span></th>
											<th onClick="sortTasks('creationDate')"><strong><s:text
														name="creation_date" /></strong><span class="sort-indicator"
												id="statusIndicator"></span></th>
											<th onClick="sortTasks('statusId')"><strong><s:text
														name="status" /></strong><span class="sort-indicator"
												id="statusIndicator"></span></th>

										</tr>
									</thead>
									<tbody>
										<s:iterator value="#request.tasks" var="task">
											<tr>
												<s:if test="#task.taskId!=0">
													<s:param name="taskId" value="#task.taskId" />
													<td><s:if test="#task.pending==0">
															<s:url var="updateTaskUrl" action="updateTask">
																<s:param name="taskId" value="#task.taskId" />
															</s:url>

															<a href="<s:property value='#updateTaskUrl'/>"><s:property
																	value="#task.taskId" /></a>
														</s:if> <s:elseif
															test="#task.pending==1||#request.task.pending==2">
															<s:property value="#task.taskId" />
														</s:elseif></td>
													<td><s:property value='#task.taskName' /></td>
													<td><s:property value='#task.taskDescription' /></td>
													<td><s:property
															value="%{#task.pending == 1 ? 'pending' : (#task.pending == 2 ? 'rejected' : 'approved')}" />
													</td>
													<td><fmt:setLocale value="en_US" /> <fmt:formatDate
															value="${task.creationDate}" pattern="MM/dd/yyyy" /></td>
													<td><select class="status-dropdown"
														data-taskid="<s:property value="#task.taskId"/>"
														name="statusId"
														<s:if test="#task.pending==1||#task.pending==2">disabled</s:if>>
															<s:iterator value="#request.statusIds" var="status">
																<option
																	value="<s:property value='%{#status.statusId}' />"
																	<s:if test="#status.statusId==#task.statusId">selected</s:if>>
																	<s:property value="#status.status" />
																</option>
															</s:iterator>
													</select></td>
												</s:if>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							</div>
							</div>
							</div>
		</s:if>


		<s:if test="#request.taskMap != null && !#request.taskMap.isEmpty()">
			<div class="content">
				<div class="team">
					<div class="team-info">
						<h2>
							<s:text name="employees" />
							<s:text name="tasks" />
						</h2>
						<input type="hidden" id="sortBy" name="sortBy" /> <input
							type="hidden" id="order" name="order"
							value="<s:property value='%{#request.order}' />" /> <input
							type="hidden" id="orders" name="orders"
							value="<s:property value='%{#request.orders}' />" />
						<table border="1" id="sortableTable">
							<thead>
								<tr>
									<th onClick="sortTasks('taskId')"><strong><s:text
												name="task" /> <s:text name="Id" /></strong><span
										class="sort-indicator" id="statusIndicator"></span></th>
									<th onClick="sortTasks('taskName')"><strong><s:text
												name="name" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>
									<th onClick="sortTasks('creationDate')"><strong><s:text
												name="creation_date" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>
									<th onClick="sortTasks('statusId')"><strong><s:text
												name="status" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>
									<th onClick="sortTasks('pending')"><strong><s:text
												name="pending" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>

									<th onClick="sortTasks('taskDescription')"><strong><s:text
												name="description" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>

									<th onClick="sortTasks('firstName')"><strong><s:text
												name="employee" /> <s:text name="name" /></strong><span
										class="sort-indicator" id="statusIndicator"></span></th>
									<th onClick="sortTasks('teamName')"><strong><s:text
												name="team" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>
									<th onClick="sortTasks('roleName')"><strong><s:text
												name="role" /></strong><span class="sort-indicator"
										id="statusIndicator"></span></th>

								</tr>
							</thead>
							<tbody>
								<s:iterator value="#request.taskMap" var="entry">
									<tr>
										<s:hidden name="taskId"
											value="<s:property value='#entry.key.taskId' />" />

										<s:url var="updateTaskUrl" action="updateTask">
											<s:param name="taskId" value="#entry.key.taskId" />
										</s:url>
										<td><strong> <s:url var="udeleteTaskUrl"
													action="deleteTask">
													<s:param name="taskId" value="#entry.key.taskId" />
												</s:url> <a href="<s:property value='#updateTaskUrl'/>"><s:property
														value="#entry.key.taskId" /></a> <a
												href="<s:property value='#udeleteTaskUrl'/>"><s:text
														name="delete" /></a> <br></strong> <strong> </strong></td>
										<td><s:property value="#entry.key.taskName" /></td>
										<td><fmt:setLocale value="en_US" /> <fmt:formatDate
												value="${entry.key.creationDate}" pattern="MM/dd/yyyy" /></td>

										<td><s:iterator value="#request.statusIds" var="sId">
												<s:if test="%{#sId.statusId == #entry.key.statusId}">
													<s:property value="#sId.status" />
												</s:if>
											</s:iterator></td>
										<td><s:property
												value="%{#entry.key.pending == 1 ? 'pending' : (#entry.key.pending == 2 ? 'rejected' : 'approved')}" />
										</td>
										<td><s:property value="#entry.key.taskDescription" /></td>

										<s:iterator value="#entry.value" var="employee">
											<td><s:property value="#employee.firstName" /> <s:property
													value="#employee.lastName" /></td>
											<td><s:iterator value="#request.teams" var="team">
													<s:if test="%{#team.teamId == #employee.teamId}">
														<s:property value="#team.teamName" />
													</s:if>
												</s:iterator></td>
											<td><s:iterator value="#request.roles" var="role">
													<s:if test="%{#role.roleId == #employee.roleId}">
														<s:property value="#role.roleName" />
													</s:if>
												</s:iterator></td>
										</s:iterator>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</s:if>
	</div>

	<p>
		<%@ include file="/views/footer.jsp"%>
	</p>
</body>
</html>