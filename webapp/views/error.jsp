<%@ page isErrorPage="true"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<title>Error Page</title>
</head>
<body>
	<s:if test="hasErrors()">
		<s:actionerror />
	</s:if>
	<s:if test="%{#request.msg != null}">
		<script>
			var sessionMsg = "<s:property value='%{#request.msg}'/>";
			alert(sessionMsg);
		</script>
	</s:if>

</body>
</html>