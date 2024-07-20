<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	// Function to fade out the message div after 10 seconds
	function fadeOutMessage() {
		var messageDivs = document.querySelectorAll('.message');
		messageDivs.forEach(function(div) {
			setTimeout(function() {
				div.style.opacity = '0';
				setTimeout(function() {
					div.style.display = 'none';
				}, 1000); // Fade out transition time
			}, 10000); // 10 seconds delay
		});
	}

	// Call the fadeOutMessage function
	fadeOutMessage();
</script>

<style>

/* CSS for message divs */
.message {
	position: fixed;
	top: 20px;
	right: 20px;
	padding: 10px;
	background-color: rgba(0, 0, 0, 0.8);
	color: #fff;
	border-radius: 5px;
	transition: opacity 1s ease-in-out; /* CSS transition for fading out */
	opacity: 1;
	z-index: 9999;
}
</style>
<script>
	function toggleUserInfo() {
		var userInfo = document.querySelector('.user-info');
		userInfo.style.display = (userInfo.style.display === 'block') ? 'none'
				: 'block';

	}
</script>
<style>
h2, h2, h3 {
	color: #71a6df;
}

li a {
	display: block;
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
}

a {
	padding: 10px 0;
	color: #007bff;
	text-decoration: none;
}

.content {
	position: relative;
	padding: 20px;
}

.user-info {
	position: absolute;
	top: 30px;
	right: 15px;
	background-color: #f4f4f4;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 5px;
	display: none;
	z-index: 9;
	color: #539ce9;
}

.toggle-icon {
	cursor: pointer;
	position: absolute;
	top: 10px;
	right: 10px;
	color: #539ce9;
}

ul {
	list-style: none;
}
</style>
</head>
<body>

	<div class="content">
		<div class="toggle-icon" onclick="toggleUserInfo()">User Info
			&#9660;</div>
		<div class="user-info">
			<ul>
				<li><s:url var="logouturl" action="logout" /> <a
					href="<s:property value='logouturl' />"><s:text name="logout" /></a></li>
				<li><s:url var="profile" action="view" /> <a
					href="<s:property value='profile' />"> <strong> <s:property
								value="#session.user.firstName" /></strong> <strong> <s:property
								value="#session.user.lastName" /></strong>
				</a></li>
			</ul>
		</div>
	</div>

</body>
</html>
<s:if test="%{#request.msg != null}">
	<div id="sessionMsg" class="message">
		<s:property value="%{#request.msg}" />
	</div>
</s:if>

<s:if test="%{#request.message != null}">
	<div id="requestMsg" class="message">#request.message</div>
</s:if>

<s:if test="hasActionMessages()">
	<div class="success message">
		<s:actionmessage />
	</div>
</s:if>
<s:if test="hasActionErrors()">
	<div class="error message">
		Error:
		<s:actionerror />
	</div>
</s:if>


