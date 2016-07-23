package logic.Analysisbl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import logicservice.Analysisblservice.MarketLogicservice;
import po.NStockPO;
import utils.DateTool;
import vo.MarketVO;
import vo.NStockVO;

public class MarketLogicTest {

	

	
	@Test
	public void testgetResultListAll() {
		 MarketLogicservice service = new MarketLogic();
		 StockListDataservice stockdataService = new StockDataSQL();
		 ArrayList<NStockVO> result = new ArrayList<>();
		try {
			result = service.getResultListAll(new Date());
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 Date datePointer = new Date();
		    ArrayList<NStockPO> todayStockList = new ArrayList<NStockPO>();
		    //如果当日不是工作日，逻辑层返回空的ArrayList，然后日期向前移动一天，继续取数据，直到是工作日，ArrayList不为空位置
		    while(todayStockList.isEmpty()) {
		    //	if(NetStatus.isConnected()){
		    	try {
					todayStockList = stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
				} catch (StatusNotOKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
//		    	}else {
//		    		todayStockList = SaveDataToTXT.load16Stocks("today");		
//		    	}
		    	datePointer = DateTool.beforeDate(datePointer, -1);
		    }
		    assertEquals(todayStockList.size(),result.size());
	}
	@Test
	public void testgetvo() {
		 MarketLogicservice service = new MarketLogic();
		 try {
			ArrayList<MarketVO>aa=service.getMarketVOs(DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-3-1"));
		assertNotNull(aa);
		 } catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
