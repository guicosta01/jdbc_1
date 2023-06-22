package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
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
