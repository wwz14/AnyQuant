package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.utils.DataBase;
import data.utils.SqlDataBase;
import po.NBenchMarkPO;

public class NamesFactory {
	private NamesFactory() {}
	
	private static final String NAMES_PATH = "data/names.dat";
	
	private static final String NO_AND_NAMES_PATH = "data/NumAndNames.dat";
	private static Connection conn = SqlDataBase.getConnection();
	//数据库实现
	@SuppressWarnings("unchecked")
	public static List<String> getAllNames() {
		List<String> result = new ArrayList<String>();
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from stockname");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getMapOfNames() {
		return (HashMap<String, String>) DataBase.load(NO_AND_NAMES_PATH);
	}
	
}
