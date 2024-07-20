<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
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
		var url = '/struts-task/views/pendingSort';
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
<!DOCTYPE html>
<html>
<head>
<style>
/* Background styles */
body {
	background-color: #0000;
}

/* Table styles */
table {
	width: 100%;
	border-collapse: collapse;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: white; /* White background for table */
}

th, td {
	color: #539ce9;
	border: 1px solid #dddddd;
	text-align: left;
	padding: 12px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

/* Button styles */
input[type="button"] {
	background-color: #539ce9;
	color: white;
	padding: 12px 24px;
	margin: 25px 0;
	border: none;
	cursor: pointer;
	border-radius: 6px;
	transition: background-color 0.3s;
}

input[type="button"]:hover {
	background-color: #539ce9;
}

/* Link styles */
a {
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
}

a:hover {
	color: #ff0000;
}
</style>
</head>
<body>
	<div class="newContent">
		<s:if test="%{#request.tasks != null and #request.tasks.size() > 0}">
			<div class="tasks-container">
				<input type="hidden" id="sortBy" name="sortBy" /> <input
					type="hidden" id="order" name="order"
					value="<s:property value='%{#request.order}' />" /> <input
					type="hidden" id="orders" name="orders"
					value="<s:property value='%{#request.orders}' />" />
				<table>
					<thead>
						<tr>
							<th><strong> <s:text name="Action" /></strong></th>
							<th onClick="sortTasks('taskId')"><s:text name="Id" /> <span
								class="sort-indicator" id="taskIdIndicator"></span></th>
							<th onClick="sortTasks('taskName')"><strong><s:text
										name="name" /> <span class="sort-indicator"
									id="taskNameIndicator"></span></th>
							<th onClick="sortTasks('pending')"><s:text name="pending" />
								<span class="sort-indicator" id="pendingIndicator"></span></th>
							<th onClick="sortTasks('creationDate')"><strong><s:text
										name="creation_date" /></strong><span class="sort-indicator"
								id="statusIndicator"></span></th>
							<th onClick="sortTasks('statusId')"><s:text name="status" />
								<span class="sort-indicator" id="statusIndicator"></span></th>

						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.tasks" var="task">
							<tr>
								<td><s:if test="#task.pending!=0">
										<s:if
											test='%{#task.pending == 1||(#task.pending==2 && (#request.title == "Rejected"))}'>
											<s:url var="approveUrl" action="approveTask">
												<s:param name="taskId" value="#task.taskId" />
											</s:url>


											<a href=" #" class="up-link"
												data-url="<s:property value='approveUrl' />"><s:text
													name="approve" /></a>
										</s:if>
										<s:if test='%{#task.pending!=2}'>

											<s:if test='%{#request.title == "Pending"}'>
												<s:url var="rejectUrl" action="reject">
													<s:param name="taskId" value="#task.taskId" />
												</s:url>

												<a href="#" class="up-link"
													data-url="<s:property value='rejectUrl' />"> <s:text
														name="reject" />
												</a>
											</s:if>
										</s:if>
									</s:if> <s:if test="#task.pending==0">

										<s:text name="task" />
										<s:text name="approved" />
										<br>
										<s:url var="rejectUrl" action="reject">
											<s:param name="taskId" value="#task.taskId" />
										</s:url>

										<a href="#" class="up-link"
											data-url="<s:property value='rejectUrl' />"> <s:text
												name="reject" />
										</a>

									</s:if> <s:if test="#task.pending==2">
										<s:if test='%{#request.title == "Pending"}'>
											<s:text name="task" />
											<s:text name="rejected" />

											<s:url var="approveUrl" action="approveTask">
												<s:param name="taskId" value="#task.taskId" />
											</s:url>

											<a href=" #" class="up-link"
												data-url="<s:property value='approveUrl' />"><s:text
													name="approve" /></a>
										</s:if>
									</s:if></td>
								<td><s:url var="updateTaskUrl" action="updateTask">
										<s:param name="taskId" value="#task.taskId" />
									</s:url> <a href="<s:property value='updateTaskUrl'/>"><s:property
											value="#task.taskId" /> </a></td>
								<td><s:property value='#task.taskName' /></td>
								<td><s:property
										value="%{#task.pending == 1 ? 'pending' : (#task.pending == 2 ? 'rejected' : 'approved')}" />

								</td>
								<td><fmt:setLocale value="en_US" /> <fmt:formatDate
										value="${task.creationDate}" pattern="MM/dd/yyyy" /></td>
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
					</tbody>
				</table>
			</div>
		</s:if>
	</div>
</body>
</html>