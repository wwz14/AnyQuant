package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import data.utils.DataManager;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NStockPO;
import po.ResultListPO;
import po.ResultPO;
import po.StockPO;
import utils.DateTool;
import utils.NamesFactory;

public class StockListData implements StockListDataservice {
	
	//缓存数据
	//单个股票历史记录
	ArrayList<NStockPO> singleHistory=new ArrayList<NStockPO>();
	ArrayList<ArrayList<NStockPO>> stocks = new ArrayList<>();
	String initial = "2014-01-01";
	
	
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 

	@Override
	public ArrayList<NStockPO> getResultListAll(String exchange) throws StatusNotOKException {
		ResultListPO resultListPO = DataManager.getNewestData();
		ArrayList<NStockPO> nStockPOs = new ArrayList<>();
		String[] strings = {exchange,};
		if(exchange.contains("+"))
			strings = exchange.split("\\+");
		for (String string : strings) {
			for (ResultPO resultPO : resultListPO.getResultPOs()) {
				StockPO stockPO = resultPO.getData().getTrading_info().get(0);
				String name = resultPO.getData().getName();
				if(!name.contains(string))
					continue;
				Date date = null;
				try {
					date = df.parse(stockPO.getDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nStockPOs.add(new NStockPO(name, date, stockPO.getOpen(), stockPO.getHigh(), stockPO.getLow(), stockPO.getClose(), stockPO.getAdj_price(), stockPO.getVolume(), stockPO.getTurnover(), stockPO.getPe_ttm(), stockPO.getPb()));
			}
		}
		
		return nStockPOs;
	}



	@Override
	public ArrayList<NStockPO> getByName(String name, String startTime, String endTime) throws StatusNotOKException  {
		//已经有缓存，判断日期是否一样
		if(singleHistory.size()!=0){
			if(singleHistory.get(0).getName().equals(name)){
				//这边不早是不是这样比
				if(DateTool.getStringByDate(singleHistory.get(0).getDate())==startTime){
					//目前endtime应该都是最新一天，以后不早会怎样
					return singleHistory;
					
				}
			}
			
		}
		//否则从网上取
		if (startTime == null) {
			Calendar date = Calendar.getInstance(); 
			date.add(Calendar.DATE, 1);
			endTime = df.format(date.getTime());
			date.add(Calendar.MONTH, -1);
			startTime = df.format(date.getTime());
		}else if(endTime == null) {
			Calendar date = Calendar.getInstance(); 
			date.add(Calendar.DATE, 1);
			endTime = df.format(date.getTime());
		}
		//由于api，是左闭右开，所以endTime向后取一天
		if(endTime != null) {
			Date endDate = DateTool.getDateByString(endTime);
			endDate = DateTool.beforeDate(endDate, 1);
			endTime = DateTool.getStringByDate(endDate);
		}
		
		ResultPO resultPO = DataManager.getHistoryData(name, startTime, endTime);
		if(!resultPO.getStatus().equals("ok"))
			throw new StatusNotOKException();
		ArrayList<NStockPO> nStockPOs = new ArrayList<>();
		for (StockPO stockPO : resultPO.getData().getTrading_info()) {
			Date date = null;
			try {
				date = df.parse(stockPO.getDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nStockPOs.add(new NStockPO(name, date, stockPO.getOpen(), stockPO.getHigh(), stockPO.getLow(), stockPO.getClose(), stockPO.getAdj_price(), stockPO.getVolume(), stockPO.getTurnover(), stockPO.getPe_ttm(), stockPO.getPb()));
		}
		//缓存
		singleHistory=nStockPOs;
		return nStockPOs;
	}
	
	


//    public ArrayList<NStockPO> getAllByTime(String date)
//    throws StatusNotOKException {
//        List<String> nameList = NamesFactory.getAllNames();
//        ArrayList<NStockPO> thisDayPO = new ArrayList<NStockPO>();
//        for(int i = 0;i<nameList.size();i++) {
//        	 ArrayList<NStockPO> poList = getByName(nameList.get(i), date, null);
//        	 if(!poList.isEmpty()) {
//        	thisDayPO.add(poList.get(0));
//        	 }
//        }
//        return thisDayPO;
//    }
	
//    
    public ArrayList<NStockPO> getAllByTime(String date) throws StatusNotOKException {
		List<String> nameList = NamesFactory.getAllNames();
		ArrayList<NStockPO> result = new ArrayList<NStockPO>();
		ArrayList<NStockPO> stock = new ArrayList<> ();
		if(stocks.size() != nameList.size()) {
		for(int i = 0;i<nameList.size();i++) {
			stock = getByName(nameList.get(i),null,null);
			stocks.add(stock);
		}
		}
		//System.out.println("stocks length :"+stocks.size());
			
		for(int j = 0;j<nameList.size();j++) {
				for(int m = 0;m<stocks.get(j).size();m++) {
					stock = stocks.get(j);
					if(stock.get(m).getDate().getTime() == DateTool.getDateByString(date).getTime()) {
						result.add(stock.get(m));
						break;
					}
				}
			}
		
        return result;
	}
	
	
	
//
//	
//	public ResultListPO getResultList(String url) {
//		Gson gson = new Gson();
//		ResultListPO resultListPO = gson.fromJson(GetJSON.getJSON(url), ResultListPO.class);
//		System.out.println(resultListPO.getStatus());
//		ArrayList<ResultPO> resultPOs = new ArrayList<ResultPO>();
//		StockData stockData = new StockData();
//		ResultPO resultPO;
//		System.out.println(resultListPO.getData().size());
//		// TODO 2000个链接，读取太慢，需要分开读
//		for (LinkPO linkPO : resultListPO.getData()) {
//			resultPO = stockData.getResult(linkPO.getLink());
//			resultPOs.add(resultPO);
//		}
//		System.out.println("加载完毕");
//		resultListPO.setResultPOs(resultPOs);
//		return resultListPO;
//	}
//

}
