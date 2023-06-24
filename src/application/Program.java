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
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		transaction_data();
	}
	
	public static void transaction_data() {
		Connection conn = null;
		Statement st = null; 
		
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false); //nao 'salva' -> transacao
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId=1");
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId=2");
			
			conn.commit(); // caso nao ocorra erros
			
			System.out.println("row1: " + rows1);
			System.out.println("row2: " + rows2);

			
			}	
			catch (SQLException e) {
				try {
					conn.rollback();
					e.getMessage();
					//throw new DbException("Error by: " + e.getMessage());
				} catch (SQLException e1) {
					//throw new DbException("Error rollback by: " + e1.getMessage());
					e1.getMessage();
				} 
			}
			finally {
			DB.closeStatement(st);
			DB.closeConnection();
			}
	}
	
	public static void delete_data() {
		Connection conn = null;
		PreparedStatement st = null; 
		
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

			st.setInt(1, 5); //deparmento 2 vai aumentar 200
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows: "+ rowsAffected);
			
			}	
			catch (SQLException e) {
				throw new DbIntegrityException(e.getMessage());
			}
			finally {
			DB.closeStatement(st);
			DB.closeConnection();
			}
		
	}
	
	public static void update_data() {
		Connection conn = null;
		PreparedStatement st = null; 
		
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ? WHERE DepartmentId = ?");

			st.setDouble(1, 200.0); //soma 200 no salario
			st.setInt(2, 2); //deparmento 2 vai aumentar 200
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows: "+ rowsAffected);
			
			}	
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
			DB.closeStatement(st);
			DB.closeConnection();
			}
	}
	
	public static void write_data() {
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
			
			st.setString(1, "Bruna"); //replace nos "?"
			st.setString(2, "bruna@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("09/11/2002").getTime()));
			st.setDouble(4, 25000.0);
			st.setInt(5, 3);
			
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
