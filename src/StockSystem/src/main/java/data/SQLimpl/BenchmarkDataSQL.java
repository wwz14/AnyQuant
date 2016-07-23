package data.sqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import data.utils.SqlDataBase;
import dataservice.BenchmarkDataservice;
import po.NBenchMarkPO;



public class BenchmarkDataSQL implements BenchmarkDataservice{
	
	
	
	private static Connection conn = SqlDataBase.getConnection();
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 

	
	
	
	private NBenchMarkPO getNBenchMarkPO(ResultSet rs) {
		NBenchMarkPO po = new NBenchMarkPO();
		try {
			po.setName(rs.getString(1));
			po.setVolume(rs.getBigDecimal(2));
			po.setHigh(rs.getBigDecimal(3));
			po.setAdj_price(rs.getBigDecimal(4));
			po.setLow(rs.getBigDecimal(5));
			po.setDate(rs.getDate(6));
			po.setClose(rs.getBigDecimal(7));
			po.setOpen(rs.getBigDecimal(8));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return po;
	}
	/**
	 * 返回一开始显示在大盘界面上
	 * @param name：大盘名称，现在只有hs300
	 * @return ArrayList<NBenchMarkPO> 一年的数据
	 * */
	@Override
	public ArrayList<NBenchMarkPO> getByName(String name)  {
		ArrayList<NBenchMarkPO> result = new ArrayList<NBenchMarkPO>();
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from benchmark_of_oneyear where name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(getNBenchMarkPO(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<NBenchMarkPO> getByTime(String name, String startTime, String endTime)
			 {
		ArrayList<NBenchMarkPO> result = new ArrayList<NBenchMarkPO>();
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from benchmark where name=? and date>=? and date<=?");
			if (startTime == null) {
				Calendar date = Calendar.getInstance(); 
				date.add(Calendar.MONTH, -1);
				startTime = df.format(date.getTime());
			} 
			java.sql.Date sDate=java.sql.Date.valueOf(startTime);
			ps.setDate(2, sDate);
			if(endTime == null) {
				Calendar date = Calendar.getInstance(); 
				date.add(Calendar.DATE, 1);
				endTime = df.format(date.getTime());
			}
			ps.setString(1, name);
			java.sql.Date eDate=java.sql.Date.valueOf(endTime);
			ps.setDate(3, eDate);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(getNBenchMarkPO(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
