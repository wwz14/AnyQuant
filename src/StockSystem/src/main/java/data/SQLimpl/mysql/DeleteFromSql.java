package data.sqlImpl.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.utils.SqlDataBase;

public class DeleteFromSql {
//	private static MySQL  mysqlconn = new MySQL();
	private static Connection conn ;
	
	public static void deleteTable(String tablename) {
		String sql = "DELETE FROM "+tablename+";";
		System.out.println(sql);
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void deleteOnePiece(String name) throws SQLException {
		String sql = "DELETE FROM stock_on_today WHERE name = '"+name+"';";
		System.out.println(sql);
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		};
	}
	
	public static void deletename(String name) throws SQLException {
		String sql = "DELETE FROM stockname WHERE name = '"+name+"';";
		System.out.println(sql);
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
