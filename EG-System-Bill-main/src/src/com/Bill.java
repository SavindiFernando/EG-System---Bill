package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Bill {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bill?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertBill(String user_id, String reading_date, String billing_date, String no_of_units, String total_amount)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into Bill(`biil_id`,`user_id`,`reading_date`,`billing_date`,`no_of_units`,`total_amount`)" + " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, user_id);
			 preparedStmt.setString(3, reading_date);
			 preparedStmt.setString(4, billing_date);
			 preparedStmt.setString(5, no_of_units);
			 preparedStmt.setString(6, total_amount);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newBill = readBill(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Bill.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readBill()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>User ID</th><th>Reading Date</th><th>Billing Date</th><th>No of Units</th><th>Total Amount</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from Bill";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String biil_id = Integer.toString(rs.getInt("biil_id"));
				 String user_id = rs.getString("user_id");
				 String reading_date = rs.getString("reading_date");
				 String billing_date = rs.getString("billing_date");
				 String no_of_units = rs.getString("no_of_units");
				 String total_amount = rs.getString("total_amount");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidbiil_idUpdate\' name=\'hidbiil_idUpdate\' type=\'hidden\' value=\'" + biil_id + "'>" 
							+ user_id + "</td>"; 
				output += "<td>" + reading_date + "</td>";
				output += "<td>" + billing_date + "</td>";
				output += "<td>" + no_of_units + "</td>";
				output += "<td>" + total_amount + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-Billid='" + biil_id + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Bill.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateBill(String biil_id, String user_id, String reading_date, String billing_date, String no_of_units, String total_amount)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE Bill SET user_id=?,reading_date=?,billing_date=?,no_of_units=?,total_amount=?"  + "WHERE biil_id=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, biil_id);
			 preparedStmt.setString(2, user_id);
			 preparedStmt.setString(3, reading_date);
			 preparedStmt.setString(4, billing_date);
			 preparedStmt.setString(5, no_of_units);
			 preparedStmt.setInt(6, Integer.parseInt(total_amount)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newBill = readBill();    
			output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Bill.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteBill(String biil_id)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from Bill where biil_id=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(biil_id)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newBill = readBill();    
			output = "{\"status\":\"success\", \"data\": \"" +  newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Bill.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
