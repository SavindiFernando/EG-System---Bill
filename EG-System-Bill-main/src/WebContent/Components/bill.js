$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 //New
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateBillForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidbiil_idSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "BillService",   
			type : type,  
			data : $("#formBill").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onBillSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onBillSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divBillGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidbiil_idSave").val("");  
	$("#formSupplier")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidbiil_idSave").val($(this).closest("tr").find('#hidbiil_idUpdate').val());     
	$("#user_id").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#reading_date").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#billing_date").val($(this).closest("tr").find('td:eq(2)').text());
	$("#no_of_units").val($(this).closest("tr").find('td:eq(3)').text());   
	$("#total_amount").val($(this).closest("tr").find('td:eq(4)').text()); 
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "BillService",   
		type : "DELETE",   
		data : "biil_id=" + $(this).data("biil_id"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onBillDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onBillDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divBillGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateBillForm() 
{  
	// BILLID-----------------------
	 var tmpAcc = $("#biil_id").val().trim();
	if (!$.isNumeric(tmpAcc)) 
		{
		return "Insert Bill ID";
		} 
	
	// USERID---------------------------  
	var tmpUsage = $("#user_id").val().trim();
	if (!$.isNumeric(tmpUsage)) 
	 {
	 return "Insert User ID";
	 }
	 
	 // READINGDATE-------------------------------
	 var tmpUsage = $("#reading_date").val().trim();
	if (!$.isNumeric(tmpUsage)) 
	 {
	 return "Insert Reading Date";
	 }
	
	// BILLINGDATE-------------------------------
	 var tmpUsage = $("#billing_date").val().trim();
	if (!$.isNumeric(tmpUsage)) 
	 {
	 return "Insert Billing Date";
	 }
	
	// NOOFUNITS-------------------------------
	if ($("#no_of_units").val().trim() == "")  
	{   
		return "Insert No of Units";  
	}
	
	// TOTALAMOUNT---------------------------  
	if ($("#total_amount").val().trim() == "")  
	{   
		return "Insert Total Amount";  
	}

	return true; 
}