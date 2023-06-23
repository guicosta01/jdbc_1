package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
		Connection conn = null; 
		PreparedStatement st = null; 
		
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement("INSERT INTO seller " + 
					"(Name, Email, BirthDate, BaseSalary, DepartmentId)" 
					+"VALUES"
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Guilherme"); //replace nos "?"
			st.setString(2, "guilherme@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("09/11/2001").getTime()));
			st.setDouble(4, 25000.0);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected>0) {
				ResultSet rs = st.getGeneratedKeys(); //pode ter 1 ou mais valores 
				while(rs.next()) {
					int id = rs.getInt(1); //1 coluna
					System.out.println("Done! Id: "+ id);
				}
			}
			else {
				System.out.println("No rows affected");
			}
		}
		catch (SQLException e ) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}
	
	public static void get_data() {
		//Connection conn = DB.getConnection();
				//DB.closeConnection();
				Connection conn = null;
				Statement st = null; // comando SQL
				ResultSet rs = null;
				
				try {
					conn = DB.getConnection();
					
					st = conn.createStatement(); //instacia o obj statement
					
					rs = st.executeQuery("select * from department");
					
					while (rs.next()) {
						System.out.println(rs.getInt("Id") + "-" + rs.getString("Name"));	
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					DB.closeResultSet(rs);
					DB.closeStatement(st);
					DB.closeConnection();
				}
	}

}
