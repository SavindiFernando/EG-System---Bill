<%@ page import="com.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/bill.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Bill Service</h1>
				<form id="formBill" name="formBill" method="post" action="BillUI.jsp">  
					Bill ID:  
 	 				<input id="biil_id" name="biil_id" type="text"  class="form-control form-control-sm">
					<br>User ID:   
  					<input id="user_id" name="user_id" type="text" class="form-control form-control-sm">   
  					<br>Reading Date:   
  					<input id="reading_date" name="reading_date" type="date"  class="form-control form-control-sm">
  					<br>Billing Date:   
  					<input id="billing_date" name="billing_date" type="date"  class="form-control form-control-sm">
  					<br>No of Units:   
  					<input id="no_of_units" name="no_of_units" type="text"  class="form-control form-control-sm">
					<br>  
					<br>Total Amount:   
  					<input id="total_amount" name="total_amount" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidbiil_idSave" name="hidbiil_idSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divBillGrid">
					<%
					    Bill BillObj = new Bill();
						out.print(BillObj.readBill());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>