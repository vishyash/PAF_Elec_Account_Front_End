<%@page import="com.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
        <script src="Components/accounts.js"></script>
		<title>Electricity Account Management</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>Electricity Account</h1>
					<form id="formItem" name="formItem" method="POST" action="account.jsp">
						Account Number: 
						<input 
							id="accountNumber" 
							name="accountNumber" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Account Name: 
						<input 
							id="accountName"
							name="accountName" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Premises ID: 
						<input 
							id="premisesID" 
							name="premisesID" 
							type="text" 
							class="form-control form-control-sm"
						><br>


						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidItemIDSave" id="hidItemIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success">
						
					</div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divItemGrid">
					<%
					Account item = new Account(); 
									out.print(item.readItems());
					%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>