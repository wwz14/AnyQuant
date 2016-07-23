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
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NStockPO;


public class StockDataSQL implements StockListDataservice{
	private static Connection conn = SqlDataBase.getConnection();
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 

	private NStockPO getNStockPO(ResultSet rs) {
		NStockPO po = new NStockPO();
		try {
			po.setName(rs.getString(1));
			po.setVolume(rs.getBigDecimal(2));
			po.setHigh(rs.getBigDecimal(3));
			po.setAdj_price(rs.getBigDecimal(4));
			po.setLow(rs.getBigDecimal(5));
			po.setDate(rs.getDate(6));
			po.setClose(rs.getBigDecimal(7));
			po.setOpen(rs.getBigDecimal(8));
			po.setTurnover(rs.getBigDecimal(9));
			po.setPe_ttm(rs.getBigDecimal(10));
			po.setPb(rs.getBigDecimal(11));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return po;
	}
	/**
	 * 返回某股票的历史信息
	 * @param name 股票代码
	 * @param startTime 起始时间，如果其为null，则返回默认（最近一个月）的历史信息
	 * @param endTime 终止时间，如果其为null,则返回起始时间到现在的历史信息
	 * @return
	 * @throws StatusNotOKException 可能status不是ok
	 */
	@Override
	public ArrayList<NStockPO> getByName(String name, String startTime, String endTime)  {
//		System.out.println("start"+startTime);
//		System.out.println("end"+endTime);
		ArrayList<NStockPO> result = new ArrayList<NStockPO>();
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from stock where name=? and date>=? and date<=?");
			ps.setString(1, name);
			//判断是不是null
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
			java.sql.Date eDate=java.sql.Date.valueOf(endTime);
			ps.setDate(3, eDate);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(getNStockPO(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
     * 返回这一天16支股票的信息
     * @param startTime
     * @param endTime
     * @return
     * @throws StatusNotOKException
     */
	@Override
	public ArrayList<NStockPO> getAllByTime(String date)  {
		ArrayList<NStockPO> result = new ArrayList<NStockPO>();
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from stock where date = ?");
			java.sql.Date sDate=java.sql.Date.valueOf(date);
			ps.setDate(1, sDate);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(getNStockPO(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<NStockPO> getResultListAll(String exchange) {
		ArrayList<NStockPO> result = new ArrayList<NStockPO>();
		String sql = "select * from stock_on_today " ;
		ResultSet rs;
		try {
			conn = SqlDataBase.getConnection();
			rs = conn.createStatement().executeQuery(sql);
			while(rs.next()) {
				result.add(getNStockPO(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
