package data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

import dataservice.BenchmarkDataservice;
import exception.StatusNotOKException;
import po.NBenchMarkPO;
import po.ResultPO;
import po.StockPO;
import utils.DateTool;

public class BenchmarkData implements BenchmarkDataservice {

	
	@Override
	public ArrayList<NBenchMarkPO> getByName(String name) throws StatusNotOKException {
		String url = "http://121.41.106.89:8010/api/benchmark/" + name;
		Gson gson = new Gson(); 
		ResultPO resultPO = gson.fromJson(GetJSON.getJSON(url), ResultPO.class);
		if(!resultPO.getStatus().equals("ok"))
			throw new StatusNotOKException();
		ArrayList<NBenchMarkPO> nBenchMarkPOs = new ArrayList<>();
		for (StockPO stockPO : resultPO.getData().getTrading_info()) {
			Date date = DateTool.getDateByString(stockPO.getDate());
			nBenchMarkPOs.add(new NBenchMarkPO(name, stockPO.getVolume(), stockPO.getHigh(), stockPO.getAdj_price(), stockPO.getLow(), date, stockPO.getClose(), stockPO.getOpen()));
		}
		
		return nBenchMarkPOs;
		
	}

	@Override
	public ArrayList<NBenchMarkPO> getByTime(String name, String startTime, String endTime)
			throws StatusNotOKException {
		if (startTime == null) {
			Calendar date = Calendar.getInstance();
			date.add(Calendar.DATE, 1);
			endTime = DateTool.getStringByDate(date.getTime());
			date.add(Calendar.MONTH, -1);
			startTime = DateTool.getStringByDate(date.getTime());
		} else if (endTime == null) {
			Calendar date = Calendar.getInstance();
			date.add(Calendar.DATE, 1);
			endTime = DateTool.getStringByDate(date.getTime());
		}
		
		//由于api，是左闭右开，所以endTime向后取一天
		if(endTime != null) {
			Date endDate = DateTool.getDateByString(endTime);
			endDate = DateTool.beforeDate(endDate, 1);
			endTime = DateTool.getStringByDate(endDate);
		}		
				
		Gson gson = new Gson();
		String url = "http://121.41.106.89:8010/api/benchmark/" + name + "?start=" + startTime + "&end=" + endTime;
		ResultPO resultPO = gson.fromJson(GetJSON.getJSON(url), ResultPO.class);
		if (!resultPO.getStatus().equals("ok"))
			throw new StatusNotOKException();
		ArrayList<NBenchMarkPO> nBenchMarkPOs = new ArrayList<>();
		for (StockPO stockPO : resultPO.getData().getTrading_info()) {
			Date date = DateTool.getDateByString(stockPO.getDate());
			nBenchMarkPOs.add(new NBenchMarkPO(name, stockPO.getVolume(), stockPO.getHigh(), stockPO.getAdj_price(),
					stockPO.getLow(), date, stockPO.getClose(), stockPO.getOpen()));
			
		}
		return nBenchMarkPOs;
	}


		
}

	


