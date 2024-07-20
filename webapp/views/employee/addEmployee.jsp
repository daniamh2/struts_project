<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>

<div id="container">
	<form
		<s:if test='%{#request.form == "update"}'>		
			action="saveUpdEmployee?employeeId=<s:property value="%{#request.updatedEmployee.employeeId}"/>"</s:if>
		<s:else >			action="saveEmployee"</s:else> method="POST">


		<table>
			<tbody>
				<tr>
					<td><label><s:text name="first_name" />:</label></td>
					<td><s:textfield name="firstName"
							value="%{#request.updatedEmployee.firstName}" /></td>
				</tr>
				<tr>
					<td><label><s:text name="last_name" />:</label></td>
					<td><s:textfield name="lastName"
							value="%{#request.updatedEmployee.lastName}" /></td>
				</tr>
				<tr>
					<td><label><s:text name="email" />:</label></td>
					<td><s:textfield name="email"
							value="%{#request.updatedEmployee.email}" /></td>
				</tr>
				<tr>
					<td><label><s:text name="address" />:</label></td>
					<td><s:textfield name="address"
							value="%{#request.updatedEmployee.address}" /></td>
				</tr>
				<tr>
					<td><label><s:text name="role" />:</label></td>
					<td><s:select name="roleId" list="#request.roles"
							listKey="roleId" listValue="roleName"
							value="%{#request.updatedEmployee.roleId}" /></td>
				</tr>
				<tr>
					<td><label><s:text name="team" />:</label></td>
					<td><s:select name="teamId" list="#request.teams"
							listKey="teamId" listValue="teamName"
							value="%{#request.updatedEmployee.teamId}" /></td>
				</tr>
				<s:if test='%{#request.form != "update"}'>
					<tr>
						<td><label><s:text name="password" />:</label></td>
						<td><s:textfield name="password"
								value="%{#request.updatedEmployee.password}" /></td>
					</tr>

				</s:if>
				<tr>
					<td></td>
					<td><s:submit value="%{getText('save')}" cssClass="save" /></td>
				</tr>
			</tbody>
		</table>

	</form>
</div>
</html>