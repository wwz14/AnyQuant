package data.sqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.sqlImpl.mysql.DeleteFromSql;
import data.sqlImpl.mysql.MySQL;
import data.sqlImpl.mysql.SaveToDataBase;
import data.utils.SqlDataBase;
import dataservice.InitiailzeDataservice;
import exception.StatusNotOKException;
import utils.DateTool;
import utils.NetStatus;

public class InitialzeDataSQL implements InitiailzeDataservice {
	private  MySQL  mysqlconn = new MySQL();
	private  Connection conn ;
	
//	public static void main(String[] args) throws StatusNotOKException {
//		//DeleteFromSql.deleteTable("stock_on_today");
//		 InitialzeDataSQL i = new  InitialzeDataSQL();
//		 i.initailzeTodayStockInfo();
//		 i.initialzeBenchmarkNearly();
//	//	 i.resaveBenchmark();
//	//	 i.resaveStockInfo();
//	}

	@Override
	public void initailzeTodayStockInfo() throws StatusNotOKException {
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from stock_on_today where date=?");
			java.sql.Date sDate=java.sql.Date.valueOf(DateTool.getNewestDay());
			System.out.println(DateTool.getNewestDay());
			ps.setDate(1, sDate);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("not necessary to update");
			}else {
				if(NetStatus.isConnected()){
					//判断网络是否链接
					String sql = "select * from stock_on_today " ;
					System.out.println(sql);
			
					try {
						ResultSet nrs = conn.createStatement().executeQuery(sql);
						while(nrs.next()) {
							String date = DateTool.getStringByDate(nrs.getDate(6));
							SaveToDataBase.updateStock(DateTool.getStringByDate(DateTool.beforeDate(DateTool.getDateByString(date), 1)), DateTool.getNewestDay());
							break;
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					DeleteFromSql.deleteTable("stock_on_today");
					SaveToDataBase.updateStockOnToday();
					
					//SaveToDataBase.saveStock();
				}
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialzeBenchmarkNearly() {
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from benchmark_of_oneyear where date=?");
			java.sql.Date sDate=java.sql.Date.valueOf(DateTool.getNewestDay());
			ps.setDate(1, sDate);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("not necessary to update");
			}else {
				try {
					if(NetStatus.isConnected()) {
					SaveToDataBase.updateBenchmark();
					SaveToDataBase.saveHistoryOfBenchmark();
					}
				} catch (StatusNotOKException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void resaveStockInfo() throws StatusNotOKException {
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from stock where date=?");
			java.sql.Date sDate=java.sql.Date.valueOf(DateTool.getNewestDay());
			ps.setDate(1, sDate);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("not necessary to update");
			}else {	
				if(NetStatus.isConnected()) 
					SaveToDataBase.saveStock();;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void resaveBenchmark() throws StatusNotOKException {
		try {
			conn = SqlDataBase.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("select * from benchmark where date=?");
			java.sql.Date sDate=java.sql.Date.valueOf(DateTool.getNewestDay());
			ps.setDate(1, sDate);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("not necessary to update");
			}else {
				if(NetStatus.isConnected()) 
					SaveToDataBase.saveHistoryOfBenchmark();;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}