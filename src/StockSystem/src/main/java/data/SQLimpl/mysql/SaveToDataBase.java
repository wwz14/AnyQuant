package data.sqlImpl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import data.BenchmarkData;
import data.GetJSON;
import data.StockListData;
import data.utils.SqlDataBase;
import dataservice.BenchmarkDataservice;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NBenchMarkPO;
import po.NStockPO;
import po.ResultPO;
import po.StockPO;
import utils.DateTool;
import utils.NamesFactory;
import utils.NetStatus;

public class SaveToDataBase {
	private static  MySQL  mysqlconn = new MySQL();
	private static  Connection conn ;
	private  String[] years = {"2010","2011","2012","2013","2014","2015","2016"};
	private static  StockListDataservice service = new StockListData();
	private static   BenchmarkDataservice bservice = new BenchmarkData();
	private static String startTime = "2002-01-01";
	
//	public static void main(String[] args) throws StatusNotOKException {
//		//saveBenchmark();
//		
//		//saveStock();
//		SaveToDataBase s = new SaveToDataBase();
//		//DeleteFromSql.deletFromBenchmark();
//		//s.saveHistoryOfBenchmark();
//		s.updateBenchmark();
//		System.out.println(new Date(0));
//	}
	
	public static void updateBenchmark() throws StatusNotOKException  {
		if(NetStatus.isConnected()) {
		String url = "http://121.41.106.89:8010/api/benchmark/hs300";
		Gson gson = new Gson(); 
		ResultPO resultPO = gson.fromJson(GetJSON.getJSON(url), ResultPO.class);
		if(!resultPO.getStatus().equals("ok"))
				throw new StatusNotOKException();
			
		System.out.println("Download success");
		for (StockPO stockPO : resultPO.getData().getTrading_info()) {
			instertToBenchmarkOnePiece(stockPO, resultPO.getData().getName(),"benchmark_of_oneyear");
		}
		}else {
			System.out.println("网络错误");
		}
		
	}
	
//	StockPO(BigDecimal open, BigDecimal high, BigDecimal low,
//			BigDecimal close, BigDecimal adj_price, BigDecimal volume,
//			BigDecimal turnover, BigDecimal pe_ttm, BigDecimal pb, String date) 
//	
	/**
	 * 存储大盘所有可用信息
	 * @throws StatusNotOKException 
	 */
	public static void saveHistoryOfBenchmark() throws StatusNotOKException {
		ArrayList<NBenchMarkPO> poList = new ArrayList<>();  
		if(NetStatus.isConnected()) {
		
			poList = bservice.getByTime("hs300", "2002-01-01", DateTool.today());
			for(NBenchMarkPO po : poList) {
			StockPO stockPO = new StockPO(po.getOpen(),po.getHigh(),po.getLow(),po.getClose(),po.getAdj_price(),
					po.getVolume(),null,null,null,DateTool.getStringByDate(po.getDate()));
			instertToBenchmarkOnePiece(stockPO, po.getName(),"benchmark");
			}
			
		
		}else {
			System.out.println("网络错误");
		}
	}
	
	/**
	 * 存储16支股票当前可用信息
	 * 用户要求重新初始化系统数据时调用
	 * @throws StatusNotOKException 
	 */
	public static void saveStock() throws StatusNotOKException  {
		if(NetStatus.isConnected()) {
		  ArrayList<NStockPO> poList = new ArrayList<NStockPO>();
		  DeleteFromSql.deleteTable("stock");;//清空数据库表
		
			List<String> names= NamesFactory.getAllNames();
			for(String name: names) {
				//TODO 去除冗余
				poList = service.getByName(name, startTime, DateTool.today());
				for(NStockPO stockPO :poList) {
					  insterToStockOnePiece(stockPO,"stock");
				  }
			}	
		}else {
			System.out.println("网络错误");
		}
		 
			
	}
	/**
	 * 存储一段时间的数据到stock表
	 * @param startTime
	 * @param endTime
	 */
  public static void updateStock(String start,String end) throws StatusNotOKException{
		if(NetStatus.isConnected()) {
			  ArrayList<NStockPO> poList = new ArrayList<NStockPO>();		
				List<String> names= NamesFactory.getAllNames();
				for(String name: names) {
					poList = service.getByName(name, start, end);
					for(NStockPO stockPO :poList) {
						  insterToStockOnePiece(stockPO,"stock");
						  System.out.println("正在更逊stock");
					  }
				}	
			}else {
				System.out.println("网络错误");
			}
  }
	
	
/**
 * 将最新的16支股票的数据存在stock_on_today表中，用于更新表数据
 * @throws StatusNotOKException 
 */
	public static void updateStockOnToday() throws StatusNotOKException {
		if(NetStatus.isConnected()) {
			  ArrayList<NStockPO> poList = new ArrayList<NStockPO>();
			 
			
				List<String> names= NamesFactory.getAllNames();
				for(String name: names) {
					poList = service.getByName(name,DateTool.getNewestDay(),null );
					for(NStockPO stockPO :poList) {
						  insterToStockOnePiece(stockPO,"stock_on_today");
					  }
				}
				
				
		}else {
			System.out.println("网络错误");
		}
	}
	
	
	/**
	 * 存储一条大盘信息
	 * @param po
	 * @param name
//	 */
	public static  void instertToBenchmarkOnePiece(StockPO po , String name,String tableName) {
		//TODO 检查是否需要联网更新
		Date date = Date.valueOf(po.getDate());
		String sql = "INSERT INTO "+tableName+" VALUES ("+"'"+ name +"'"+","+ "'"+po.getVolume().doubleValue()+"'"+"," +"'" 
				+po.getHigh().doubleValue()+"'"+","+"'"+ po.getAdj_price().doubleValue()+"'"+
				","+ "'"+po.getLow().doubleValue()+"'"+","+"'"+ date+"'"+","+"'"+
				po.getClose().doubleValue()+"'"+","+"'"+ po.getOpen().doubleValue()+"'"+")"+";";
		System.out.println(sql);
		
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 存一条股票信息
	 */
	public static  void insterToStockOnePiece(NStockPO po,String tablename) {
		Date date = Date.valueOf(DateTool.getStringByDate(po.getDate()));
		String sql = "INSERT INTO "+tablename+" VALUES ("+"'"+ po.getName() +"'"+","+ "'"+po.getVolume().doubleValue()+"'"+"," +"'" 
				+po.getHigh().doubleValue()+"'"+","+"'"+ po.getAdj_price().doubleValue()+"'"+
				","+ "'"+po.getLow().doubleValue()+"'"+","+"'"+ date+"'"+","+"'"+
				po.getClose().doubleValue()+"'"+","+"'"+ po.getOpen().doubleValue()+"'"+","+"'"+po.getTurnover().doubleValue()+"'"+","+"'"+
				po.getPe_ttm().doubleValue()+"'"+","+"'"+po.getPb().doubleValue()+"'"+")"+";";
		System.out.println(sql);
		
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void insertName(String name) {
		String sql = "INSERT INTO "+"stockname"+" VALUES ('"+name+"')"+";";
		System.out.println(sql);
		try {
			conn = SqlDataBase.getConnection();
			
			Statement statement = conn.createStatement() ;
			statement.execute(sql);
		} catch (SQLException e) {
			
		}}	
		
	

}
