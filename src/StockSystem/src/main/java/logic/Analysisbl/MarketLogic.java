package logic.Analysisbl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import enums.SortType;
import exception.StatusNotOKException;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logic.stockShowInfobl.StockListLogic;
import logic.utils.DateAndAverage;
import logicservice.Analysisblservice.MarketLogicservice;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;
import utils.changeRateFormate;
import vo.MarketVO;
import vo.NBenchMarkVO;
import vo.NStockVO;


public class MarketLogic implements MarketLogicservice{
	private StockListDataservice stockdataService ;
	private BenchmarkLogicservice benchMarkService ;
	
	
	private StockListLogicservice sortService ;
	//private StockCacheDataservice catchService ;
	private long betweenDays;
	public MarketLogic(){
		if(WHICHIMP.isSQL){
			stockdataService = new StockDataSQL();
		}
		benchMarkService = new BenchmarkLogic();
		sortService = new StockListLogic();
		//catchService = new StockCacheTxt();
	}
	@Override
	public ArrayList<NStockVO> getResultListAll(Date today) throws StatusNotOKException {
	    Date datePointer = today;
	    ArrayList<NStockPO> todayStockList = new ArrayList<NStockPO>();
	    //如果当日不是工作日，逻辑层返回空的ArrayList，然后日期向前移动一天，继续取数据，直到是工作日，ArrayList不为空位置
	    while(todayStockList.isEmpty()) {
	    //	if(NetStatus.isConnected()){
	    	todayStockList = stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
	    	
//	    	}else {
//	    		todayStockList = SaveDataToTXT.load16Stocks("today");		
//	    	}
	    	datePointer = DateTool.beforeDate(datePointer, -1);
	    }
	   // SaveDataToTXT.saveToday(todayStockList,FilePath.getThepath());;
	    //取前一天的数据
	    ArrayList<NStockPO> yesterdayStockList = new ArrayList<NStockPO>();
	    while(yesterdayStockList.isEmpty()) {
	    	//if(NetStatus.isConnected()){
	    	yesterdayStockList =  stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
	    	
//	    	}else{
//	    		yesterdayStockList = SaveDataToTXT.load16Stocks("yesterday");
//	    	}
	    	datePointer = DateTool.beforeDate(datePointer, -1);
	    }
	  //  SaveDataToTXT.saveToday(yesterdayStockList, FilePath.getyesterdaypath());
	    //计算涨幅
	    ArrayList<NStockVO> todayStock = poListTOvoList(todayStockList);
	    
	    ArrayList<NStockVO> yesterdayStock = poListTOvoList(yesterdayStockList);
	    for(int i = 0;i<todayStock.size();i++) {
	    	for(int j = 0;j<yesterdayStock.size();j++) {
	    		if(todayStock.get(i).getName().equals(yesterdayStock.get(j).getName())){
	    			BigDecimal thisClose = todayStock.get(i).getClose();
					BigDecimal preClose = yesterdayStock.get(j).getClose();
					BigDecimal changeRate = new BigDecimal(0);
					if(preClose.compareTo(BigDecimal.ZERO) != 0){
					changeRate = thisClose.subtract(preClose).divide(preClose,5,BigDecimal.ROUND_HALF_UP);
					}
					else
						   todayStock.get(i).setRate("null");
					   todayStock.get(i).setChangeRate(changeRate);
					   todayStock.get(i).setRate(changeRateFormate.formate(changeRate));
	    			break;
	    		}
	    	}
	    }
	    //按涨幅降序排列
	    todayStock =  sortService .sortStockList(todayStock, SortType.changeRateDown);
		return todayStock;
	}
	@Override
	public ArrayList<MarketVO> getMarketVOs(Date start, Date end) throws StatusNotOKException {
		betweenDays = betweenDays(start,end);
		ArrayList<MarketVO> market = new ArrayList<MarketVO>();
		
		Date datePointer = start;
		ArrayList<DateAndAverage> dateAndAverageList = new ArrayList<DateAndAverage>();
		ArrayList<NBenchMarkVO> benchMarkVo = new ArrayList<> ();
		//if(NetStatus.isConnected()){
		 benchMarkVo = benchMarkService.getByTime("hs300", start, end);
		//SaveDataToTXT.saveBenchmark("hs300", DateTool.getStringByDate(start), DateTool.getStringByDate(end));	
//		}else {
//		 benchMarkVo = SaveDataToTXT.loadBenchmarkday(DateTool.getStringByDate(start), DateTool.getStringByDate(end));
//		}
		for(int i = 0;i<betweenDays;i++) {
			//计算某一日期，16支股票的加权平均数，
			ArrayList<NStockPO> poList = stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
			if(!poList.isEmpty()){
				DateAndAverage thisdayAverage = average(poList,datePointer);
				dateAndAverageList.add(thisdayAverage);
			}				
				datePointer = DateTool.beforeDate(datePointer, 1);				
		}
//		MarketVO(Date date, BigDecimal marketChangeRate,
//				BigDecimal benchmarkChangeRate) 
		for(int j = 1;j<dateAndAverageList.size();j++) {
			BigDecimal thisCloseAvergae = dateAndAverageList.get(j).getAveraage();
			BigDecimal preCloseAverage = dateAndAverageList.get(j-1).getAveraage();
			@SuppressWarnings("unused")
			Date thisDate = dateAndAverageList.get(j).getDate();
			BigDecimal changeRate = new BigDecimal(0);
			if(preCloseAverage.doubleValue() != 0){
			 changeRate = thisCloseAvergae.subtract(preCloseAverage).divide(preCloseAverage,5,BigDecimal.ROUND_HALF_UP);
			 if(changeRate.compareTo(new BigDecimal(0.10000)) == 1)
				 changeRate = new BigDecimal(0.10000);
			 if(changeRate.compareTo(new BigDecimal(-0.10000)) == -1)
				 changeRate = new BigDecimal(-0.10000); 
			 for(int p = 0;p<benchMarkVo.size();p++) {
					if(benchMarkVo.get(p).getDate().getTime() == dateAndAverageList.get(j).getDate().getTime()){
					MarketVO marketVo = new MarketVO(dateAndAverageList.get(j).getDate(),
							changeRate,benchMarkVo.get(p).getChangeRate());
					 marketVo.setInTime(true);
					market.add(marketVo);
					}
				}
			}
			
			
			
		}	
		
		return market;
	}
	/**
	 * 日期之间的时间间隔
	 * @param start
	 * @param end
	 * @return
	 */
	private long betweenDays(Date start,Date end) {
		long betweenDay = (long)((end.getTime()-start.getTime()) / (1000 * 60 * 60 *24) + 0.5);
		return betweenDay + 1;
	}
	
	
	private NStockVO poTOvo(NStockPO po) {	
		return new NStockVO(po.getName(), po.getDate(), po.getOpen(), po.getHigh(), po.getLow(), po.getClose(),
				po.getAdj_price(), po.getVolume(), po.getTurnover(), po.getPe_ttm(), po.getPb());
	}
	
	private ArrayList<NStockVO> poListTOvoList(ArrayList<NStockPO> poList) {
		ArrayList<NStockVO> voList = new ArrayList<NStockVO> ();
		for(int i = 0;i<poList.size();i++) {
			voList.add(poTOvo(poList.get(i)));
		}
		return voList;
	}
	/**
	 * 计算收盘价加权平均数，权重为成交金额，成交金额在构造NStock时计算
	 * @param po
	 * @param date
	 * @return
	 */
    private DateAndAverage average(ArrayList<NStockPO> po, Date date) {
	   ArrayList<NStockVO> voList = new ArrayList<NStockVO> ();
	   BigDecimal totalDeal = new BigDecimal(0);
	   BigDecimal totalWeight = new BigDecimal(0);
	   BigDecimal average = new BigDecimal(0);
	   for(int i = 0;i<po.size();i++) {
		   voList.add(poTOvo(po.get(i)));   
	   }
	   
	   for(int j = 0;j<voList.size();j++) {
		   totalDeal = totalDeal.add(voList.get(j).getDealprice());
		   BigDecimal weight = voList.get(j).getDealprice().multiply(voList.get(j).getClose());
		   totalWeight = totalWeight.add(weight);   
	   }
	   
	   if(totalDeal.compareTo(BigDecimal.ZERO)!= 0)
	   average = totalWeight.divide(totalDeal, 5, BigDecimal.ROUND_HALF_UP);	
	   else
	   average = totalDeal;
	   return new DateAndAverage(date,average);
   }
   
	
}
