<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" /></title>
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
<div class="content">
	<h2>
		<tiles:insertAttribute name='title' />
		<s:text name="task" />
	</h2>

	<tiles:insertAttribute name="body" />
</div>
<%@ include file="/views/footer.jsp"%>