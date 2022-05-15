package com;

import java.sql.*;

public class Account {

	public Connection connect() 
	{ 
	 Connection conn = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/account", "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return conn; 
	}
    
    
    //method to insert data
    public String insertItem(String accountNumber, String accountName, String premisesID) {
    	
    	
    	String Output = "";
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO electricity (accountNumber,accountName,premisesID) VALUES (?,?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	//preparedStatement.setInt(1, 0); 
        	preparedStatement.setString(1, accountNumber);
        	preparedStatement.setString(2, accountName);
        	preparedStatement.setString(3, premisesID);
        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newItems = readItems(); 
        	 Output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}"; 
        	
        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to update data
    public String updateItem(String accountID, String accountNumber, String accountName, String premisesID) {
    	
    	
    	String Output = "";
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE electricity SET accountNumber = ?,accountName = ?, premisesID = ?  WHERE accountID = ?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, accountNumber);
        	preparedStatement.setString(2, accountName);
        	preparedStatement.setString(3, premisesID);
        	//preparedStatement.setString(5, itemID);
        	preparedStatement.setInt(4, Integer.parseInt(accountID)); 
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	String newItems = readItems(); 
        	
        	 Output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 

        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //method to read data
    public String readItems() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM electricity";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table border=\"1\" class=\"table\"><tr><th>Account Number</th>"
    		 		+ "<th>Account Name</th><th>Premises ID</th>"
    		 		+ "<th>Update</th>"
    		 		+ "<th>Remove</th></tr>"; 
        	
        	while(resultSet.next()) {
        		String accountID = Integer.toString(resultSet.getInt("accountID"));
        		String accountNumber = resultSet.getString("accountNumber");
        		String accountName = resultSet.getString("accountName");
        		String premisesID = resultSet.getString("premisesID");
        		
        		// Add a row into the HTML table
        		 
        		Output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='"+accountID+"'>"+accountNumber+"</td>";  
        		Output += "<td>" + accountName + "</td>"; 
        		Output += "<td>" + premisesID + "</td>"; 
        	
        		
        		// buttons
        		Output += "<td><input name='btnUpdate' type='button' value='Update' "
       				 + "class='btnUpdate btn btn-secondary' data-accountid='" + accountID + "'></td>"
       				 + "<td><input name='btnRemove' type='button' value='Remove' "
       				 + "class='btnRemove btn btn-danger' data-accountid='" + accountID + "'></td></tr>";
        				
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the items";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to delete data
    public String deleteItem(String accountID) {
    	String Output = "";
    	
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM electricity WHERE accountID = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(accountID));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newItems = readItems(); 
        	 Output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}"; 
        	
        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 

    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}
