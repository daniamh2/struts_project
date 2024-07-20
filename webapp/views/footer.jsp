
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<STYLE>/* Sidebar container */

/* Sidebar heading */
.sidebar h2 {
	margin-bottom: 20px;
	color: #333;
}

/* Sidebar links */
.sidebar a {
	display: block;
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
}

.sidebar a:hover {
	background-color: #ddd;
}

/* Active link */
.sidebar .active {
	font-weight: bold;
}

.sidebar ul {
	padding: 0;
	list-style-type: none;
}

.content {
	margin-left: 170px; /* Adjust margin to accommodate sidebar width */
	padding: 20px;
}

.sidebar {
	width: 150;
	height: 100%;
	background-color: #f4f4f4;
	padding: 20px;
	border-right: 1px solid #ddd;
	position: fixed;
	top: 0;
	left: 0;
}

.header {
	margin-left: 300px;
	padding: 20px;
}

.content {
	margin-left: 170px;
	padding: 20px;
}
</STYLE>
</head>
<body>
	<div class="sidebar">
		<ul>

			<li><s:url var="addTasksurl" action="addTask" /> <a
				href="<s:property value='addTasksurl' />"><s:text name="add" />
					<s:text name="tasks" /> </a></li>


			<s:if test="#request.canViewTeams != false">
				<li><s:url var="teamsurl" action="teamsList" /> <a
					href="<s:property value='teamsurl' />"> <s:text name="teams" />
				</a></li>
				<li><s:url var="addTeamUrl" action="addTeam" /> <a
					href="<s:property value='addTeamUrl' />"> <s:text name="add" />
						<s:text name="teams" />
				</a></li>
			</s:if>


			<s:if test="#request.canViewTeams!= false">

				<li><s:url var="emp" action="employeesList" /> <a
					href="<s:property value='emp' />"> <s:text name="employees" />
				</a></li>
				<li><s:url var="add" action="addEmployee" /> <a
					href="<s:property value='add' />"> <s:text name="add" /> <s:text
							name="employees" />
				</a></li>
			</s:if>
			<s:if test="#request.canApproveTasks==true">

				<li><s:url var="pendingurl" action="pending">
						<s:param name="title" value="pending" />
					</s:url> <a href="<s:property value='pendingurl' />"><s:text
							name="pending" /> <s:text name="tasks" /> </a></li>
				<!--  <li><s:url var="rejectedUrl" action="rejected">
						<s:param name="title" value="rejected" />
					</s:url> <a href="<s:property value='rejectedUrl' />"><s:text
							name="rejected" /> <s:text name="tasks" /> </a></li>-->

			</s:if>
			<s:if test=" #request.role.equals('leader')">
				<li><s:url var="myTeamurl" action="myTeamEmp" /> <a
					href="<s:property value='myTeamurl' />"> <s:text name="my" />
						<s:text name="team" /> <s:text name="employees" />
				</a></li>
			</s:if>

			<li><s:url var="tasksurl" action="task" /> <a
				href="<s:property value='tasksurl' />"><s:text name="all" /> <s:text
						name="tasks" /></a></li>
		</ul>
	</div>
</body>