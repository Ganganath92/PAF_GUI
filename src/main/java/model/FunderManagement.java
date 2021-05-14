package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FunderManagement {
	
	private static Connection connect() {

		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget?serverTimezone=UTC", "root","");

			System.out.println("succsess");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}
		return con;

	}
	
	
	public String createPost(String title, String content, String pdate, String ptime) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";

			}

			// create a prepared statement
			//LocalDate date = LocalDate.now();
			//LocalTime time = LocalTime.now();
			String query = "insert into funder(title,content,pdate,ptime) values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, title);
			preparedStmt.setString(2, content);
			preparedStmt.setString(3, pdate);
			preparedStmt.setString(4, ptime);

			// execute the statement

			preparedStmt.execute();
			con.close();
			
			String newPost = readPost(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPost + "\"}"; 

			
		}

		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the post.\"}";
		
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}

		return output;
	}
	public String readPost() {
		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Post ID</th><th>Post Title</th>" +
					 "<th>Content</th>" + 
					 "<th>Publish Date</th>" +
					 "<th>Publish Time</th>" +
					 "<th>Update</th><th>Remove</th></tr>"; 
			

			String query = "select * from funder";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set

			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String title = rs.getString("title");
				String content = rs.getString("content");
				String pdate = rs.getString("pdate");
				String ptime = rs.getString("ptime");

				// Add into the html table

				output += "<tr><td>" + id + "</td>";
				output += "<td>" + title + "</td>";
				output += "<td>" + content + "</td>";
				output += "<td>" + pdate + "</td>";
				output += "<td>" + ptime + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						 + "<td><button class='btnRemove btn btn-danger' name='btnRemove' id ='btnRemove' value='"+ id +"' >Remove</button></td></tr>";

			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "reading the posts.";
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}
		return output;
	}
	
	public String updateItem(String id, String title, String content, String pdate, String ptime) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = "UPDATE funder SET title=?,content=?,pdate=?,ptime=? WHERE id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, title);
			preparedStmt.setString(2, content);
			preparedStmt.setString(3, pdate);
			preparedStmt.setString(4, ptime);
			preparedStmt.setInt(5, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPost = readPost(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPost + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the post.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteItem(String id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from funder where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
