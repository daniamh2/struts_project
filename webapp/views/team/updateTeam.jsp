<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="/views/mainHeader.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
<style>
h2 {
	color: white;
}

body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

#wrapper {
	background-color: #539ce9;
	padding: 10px;
	text-align: center;
	color: white;
}

#container {
	max-width: 800px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h3 {
	margin-top: 0;
}

table {
	width: 100%;
}

td {
	padding: 5px;
}

.save {
	padding: 10px 20px;
	background-color: #539ce9;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.save:hover {
	background-color: #338beb;
}

select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>
	<div class="content">

		<div id="wrapper">
			<div id="header">

				<h2>
					<tiles:getAsString name="title" />
				</h2>

			</div>
		</div>
		<tiles:insertAttribute name="body" />
		<p>
			<a href="<s:url action='teamsList' />">Back to <s:text
					name="list" /></a>
		</p>
	</div>
	<%@ include file="/views/footer.jsp"%>

	<script>
	document.getElementById("updateForm").addEventListener("submit", function(event) {
		
    event.preventDefault(); 
    
    var formData = new FormData(this);
    
    var developer = Array.from(document.getElementById("developer").selectedOptions).map(option => option.value);
    formData.append("developer", developer);
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", this.action, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log("DONE");
        }
    };
    xhr.send(formData);
});
</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
	window.onpageshow = function(event) {
		if (event.persisted) {
			window.location.reload();
		}
	};
</script>

</body>
</html>