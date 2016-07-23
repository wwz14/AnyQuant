package logic.utils;

import java.util.ArrayList;
import java.util.Date;

import data.BenchmarkData;
import data.sqlImpl.BenchmarkDataSQL;
import data.sqlImpl.StockDataSQL;
import dataservice.BenchmarkDataservice;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NBenchMarkPO;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;

public class GetPreStock {
	static StockListDataservice stockListdata;
	static BenchmarkDataservice benchmarkDataservice;
	
	static{
		if(WHICHIMP.isSQL){//sql实现
			benchmarkDataservice = new BenchmarkDataSQL();
			stockListdata = new StockDataSQL();
		}
	}
	
	
	
	//for test
	//static StockListDataservice stockListdata=new Stocklistdata_stub();
	//得到start前days的数据
	//如果历史数据都不够start前days的数据,就会陷入死循环;
	//假设历史数据够
	//	public static  ArrayList<NStockPO> getPreSlow(String name,Date start,Date end,int days) throws StatusNotOKException{
	//		ArrayList<NStockPO> stock;
	//		int length=days;
	//		while(true){
	//			//System.out.println(start);
	//			Date truestart=DateTool.beforeDate(start, -length);
	//			//这边还应该是end
	//			stock=stockListdata.getByName(name, DateTool.getStringByDate(truestart), DateTool.getStringByDate(end));
	//			//System.out.println(DateTool.getStringByDate(truestart));
	//			//System.out.println(stock.size());
	//			//start 在Array里的位置
	//			int position=0;
	//			for(int i=0;i<stock.size();i++){
	//				NStockPO temp=stock.get(i);
	//				if(temp.getDate().compareTo(start)==0){
	//					break;
	//				}
	//				position++;
	//			}	
	//			if(position==days){
	//				break;
	//			}
	//			length++;
	//		}
	//		return stock;
	//
	//	}
	/**stock,长数据
	 *days,期望截取这段长数据中的正数第几天,
	 *size为size-days
	 */
	public static  ArrayList<NStockPO> getPreStock(ArrayList<NStockPO> stock,int days){
		ArrayList<NStockPO> result=new ArrayList<NStockPO>();
		if(days>stock.size()){
			System.out.println("数据太少无法计算");
			return result;
		}
		for(int i=days;i<stock.size();i++){
			result.add(stock.get(i));
		}
		return result;

	}
	/*stock,长数据
	 *days,期望截取这段长数据中的倒数数第几天,
	 *days为size
	 * */
	public static ArrayList<NStockPO> jiequ(ArrayList<NStockPO> stock,int days){
		ArrayList<NStockPO> result=new ArrayList<NStockPO>();
		if(days>stock.size()){
			System.out.println("数据太少无法计算");
			return result;
		}
		
		int begin=stock.size()-days;//开始
		for(int i=begin;i<stock.size();i++){
			result.add(stock.get(i));
		}
		return result;

	}
	/*
	 * 从数据层取的数据截取的方法，比getPreSLOW快
	 */
	public static  ArrayList<NStockPO> getPreS(String name,Date start,Date end,int days) throws StatusNotOKException{
//		System.out.println(name);
//		System.out.println(start);
//		System.out.println(end);
//		System.out.println(days);
		
		int between=2*days+20;
		String startstr=DateTool.getStringByDate(DateTool.beforeDate(start, -4*between));
		//System.out.println(startstr);
		//从这个长的中截取
		ArrayList<NStockPO> longStock=stockListdata.getByName(name, startstr, DateTool.getStringByDate(end));
		ArrayList<NStockPO> gonggongend=stockListdata.getByName(name, DateTool.getStringByDate(start), DateTool.getStringByDate(end));
		//多一天
		ArrayList<NStockPO> stock=GetPreStock.jiequ(longStock,gonggongend.size()+days+1 );
		//算一下与前日的差价
		for(int i=1;i<stock.size();i++){
			stock.get(i).diff=stock.get(i).getClose().doubleValue()-stock.get(i-1).getClose().doubleValue();
		
		}
		stock.remove(0);
		return stock;

	}
	/*
	 * 从长数据中根据start，end截取
	 * */
	public static  ArrayList<NStockPO> getByStartEnd(ArrayList<NStockPO> stock,Date start,Date end) {
		//从这个长的中截取
		ArrayList<NStockPO> result=new ArrayList<NStockPO>();
		for(int i=0;i<stock.size();i++){
			NStockPO temp=stock.get(i);
			//start，第一个不小于的
			if(start.getTime()<=temp.getDate().getTime()){
				//end,小于等于
				if(end.getTime()>=temp.getDate().getTime()){
					result.add(temp);
				}
			}
		}
		return result;

	}


	/*stock,长数据
	 *days,期望截取这段长数据中的倒数数第几天,
	 *days为size
	 * */

	public static ArrayList<NBenchMarkPO> jiequB(ArrayList<NBenchMarkPO> stock,int days){
		ArrayList<NBenchMarkPO> result=new ArrayList<NBenchMarkPO>();
		if(days>stock.size()){
			System.out.println("数据太少无法计算");
			return result;
		}
		
		int begin=stock.size()-days;//开始
		for(int i=begin;i<stock.size();i++){
			result.add(stock.get(i));
		}
		return result;

	}
	/*
	 * 从数据层取的数据截取的方法，比getPreSLOW快
	 */
	public static  ArrayList<NBenchMarkPO> getPreB(String name,Date start,Date end,int days) throws StatusNotOKException{
		int between=2*days+20;
		//从这个长的中截取
		ArrayList<NBenchMarkPO> longStock= benchmarkDataservice.getByTime(name, DateTool.getStringByDate(DateTool.beforeDate(start, -4*between)), DateTool.getStringByDate(end));
		ArrayList<NBenchMarkPO> gonggongend= benchmarkDataservice.getByTime(name, DateTool.getStringByDate(start), DateTool.getStringByDate(end));
		ArrayList<NBenchMarkPO> stock=GetPreStock.jiequB(longStock,gonggongend.size()+days );
		return stock;

	}



}
