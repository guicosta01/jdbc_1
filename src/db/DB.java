package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//metodos estaticos para conectar e desc com banco de dados
public class DB {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties(); //pega as prop de conection
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props); // conecta no banco = instancia um obj connection 
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn; // obj da connection
	}
	
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs); //le e guarda no props
			return props;
		}
		catch (IOException e) {  //tratar erros
			throw new DbException(e.getMessage()); //joga na exception personalizada
		}
	}
}
