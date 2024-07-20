<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/views/mainHeader.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teams</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="style.css">
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="content">

		<div id="wrapper">
			<div id="header">
				<h2>
					<s:text name="team" />
					Information
				</h2>
			</div>
		</div>
		<div class="container">
			<div id="header">
				<h2>
					<s:text name="team" />
					<s:text name="Information" />
				</h2>
			</div>
			<div id="content">
				<s:iterator value="#request.teams" var="team">

					<div class="team">
						<div class="team-info">
							<a
								href="<s:url action='updateTeam'><s:param name='teamId' value='%{#team.teamId}'/></s:url>"><s:property
									value="%{#team.teamId}" /></a>


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
					</div>
				</s:iterator>
			</div>
		</div>
	</div>


	<%@ include file="/views/footer.jsp"%>


</body>
</html>
