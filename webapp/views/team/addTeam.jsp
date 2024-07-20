
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>

<div id="container">
	<h3>
		<s:text name="teams" />
	</h3>


	<form
		<s:if test='%{#request.form == "update"}'>		
			action="saveTeam?teamId=<s:property value="%{#request.team.teamId}"/>"</s:if>
		<s:else >			action="saveNewTeam"</s:else> method="POST">


		<table>
			<tbody>

				<tr>
					<td><label> <s:text name="name" />:
					</label></td>

					<td><s:textfield name="teamName"
							value="%{#request.team.teamName}" /></td>

				</tr>
				<tr>
					<td><label><s:text name="leader" />:</label></td>
					<td><s:select name="leader" list="#request.leaders"
							listKey="employeeId" listValue="firstName + ' ' + lastName"
							value="%{#request.team.leaderId}"
							selected="%{#leader.employeeId == #team.leaderId ? 'selected' : ''}"
							disabled="not #request.role.equals('manager')" /></td>
				</tr>

				<tr>
					<td><label><s:text name="developer" />s:</label></td>
					<td><select name="developer" multiple
						<s:if test="not #request.role.equals('manager')">disabled</s:if>>
							<s:iterator value="#request.developers" var="developer">
								<option value="<s:property value='#developer.employeeId'/>"
									<s:if test="#request.teamDev.containsKey(#developer.employeeId)">selected</s:if>>
									<s:property value="#developer.firstName" />
									<s:property value="#developer.lastName" />
								</option>
							</s:iterator>
					</select></td>



				</tr>

				<tr>
					<td><s:submit value="%{getText('save')}" cssClass="save" /></td>
				</tr>

			</tbody>
		</table>
	</form>
</div>
</html>