<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1256">
<title>	<s:text name="login" />
</title><style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Larger form */
        .form-container {
            width: 400px; /* Adjust width as needed */
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        /* Form input and button styles */
        .form-label {
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 12px;
            margin-top: 6px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px; /* Larger font size */
        }
        .btn {
            width: 100%;
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px; /* Larger font size */
        }
        
        </style>
</head>
<body>

	<s:property value="#request.msg" />

	<form action="loginS" method="post">
		<div class="mb-3">
			<label class="form-label"><s:text name="Email" /></label> <input
				name="email" type="email" placeholder="Enter Email"
				class="form-control">
			<div id="emailHelp" class="form-text"></div>
		</div>
		<div class="mb-3">
			<label class="form-label"><s:text name="password" /></label> <input
				name="password" type="password" placeholder="Enter password"
				class="form-control">
		</div>

		<button type="submit" class="btn my-bg-color text-white col-md-12">
			<s:text name="submit" />
		</button>
	</form>
</body>
</html>