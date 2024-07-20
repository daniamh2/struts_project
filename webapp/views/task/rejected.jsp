<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Tasks and Employees</title>
<style>
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

.task {
	margin-bottom: 20px;
	padding: 15px;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.task-info {
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

<script>
	$(document).ready(
			function() {
				$(".up-link").click(
						function(e) {
							e.preventDefault(); // Prevent default link behavior

							var url = $(this).data("url"); // Get the URL from data attribute
							var taskId = $(this).closest(".task-info").find(
									"[name='taskId']").val();

							$.ajax({
								url : url,
								type : "GET", // or "POST" depending on your server-side implementation
								data : {
									taskId : taskId
								}, // Pass additional data if needed
								success : function(response) {
									// Handle success response if needed
									alert("Task action succeeded!");
									// Reload the tasks section or update it accordingly
									// Example: $("#tasks-container").load("tasks.jsp");
									window.location.reload();

								},
								error : function(xhr, status, error) {
									// Handle error response if needed
									alert("Task action failed: " + error);
								}
							});
						});
			});
</script>

<body>
	<div class="container">
		<s:if test="#request.msg != null">
			<script>
				alert("#request.msg");
			</script>
		</s:if>
		<s:if test="%{#request.message != null}">
			<script>
				alert("#request.message");
			</script>
		</s:if>

		<s:if test="hasActionMessages()">
			<div class="success">
				<s:actionmessage />
			</div>
		</s:if>
		<h1>Tasks</h1>
		<s:if test="%{#request.tasks != null and #request.tasks.size() > 0}">
			<h2><s:text name="rejected" /> <s:text name="tasks" /></h2>
			<div class="tasks-container">


				<s:iterator value="#request.tasks" var="task">
					<s:if test="#task.pending==0"><s:text name="task" /> <s:text name="approved" />
							<s:url var="rejectUrl" action="reject">
							<s:param name="taskId" value="#task.taskId" />
						</s:url>
						<s:url var="rejectUrl" action="reject">
							<s:param name="taskId" value="#task.taskId" />
						</s:url>
						<a href="#" class="up-link"
							data-url="<s:property value='rejectUrl' />"> <s:text name="reject" />  </a>
					</s:if>
					<div class="task">
						<div class="task-info">
							<strong>  ID:</strong>

							<s:url var="updateTaskUrl" action="updateTask">
								<s:param name="taskId" value="#task.taskId" />
							</s:url>
							<a href="<s:property value='updateTaskUrl'/>"><s:property
									value="#task.taskId" /> </a>

							<div>
								<strong><s:text name="name" />:</strong>
								<s:property value='#task.taskName' />
								<s:if test="#task.pending==2">

									<s:url var="approveUrl" action="approveTask">
										<s:param name="taskId" value="#task.taskId" />
									</s:url>

									<a href=" #" class="up-link"
										data-url="<s:property value='approveUrl' />"><s:text name="approve" /></a>
								</s:if>
							</div>
							<div>
								<strong><s:text name="description" />:</strong>
								<s:property value='#task.taskDescription' />
							</div>
							<div>
								<strong><s:text name="pending" />:</strong>
								<s:property
									value="%{#task.pending == 1 ? 'pending' : (#task.pending == 2 ? 'rejected' : 'approved')}" />

							</div>
							<div>
								<strong><s:text name="creation_date" /> :</strong>
								<s:property value='#task.creationDate' />
							</div>
							<div>
								<strong><s:text name="status" />:</strong> <select class="status-dropdown"
									data-taskid="<s:property value="#task.taskId"/>"
									name="statusId" <s:if test="#task.pending!=0">disabled</s:if>>
									<s:iterator value="#session.statusIds" var="status">
										<option value="<s:property value='%{#status.statusId}' />"
											<s:if test="#status.statusId==#task.statusId">selected</s:if>>
											<s:property value="#status.status" />
										</option>
									</s:iterator>
								</select>
							</div>
						</div>
					</div>
				</s:iterator>
			</div>
		</s:if>

		<p>
			<%@ include file="/views/footer.jsp"%>

		</p>
	</div>
</body>
</html>
