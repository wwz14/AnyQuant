package logic.stockShowInfobl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import logicservice.showInfoblservice.MacdcalculateLogicservice;
import po.NStockPO;
import utils.DateTool;
import vo.MacdVO;

public class MacdLogicTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testcalculateMacd() {
		StockListDataservice  stockListDataservice = new StockDataSQL();
		 MacdcalculateLogicservice service = new MacdcalculateLogic();
		 ArrayList<NStockPO> stockList = new ArrayList<>();
		 try {
			stockList = stockListDataservice.getByName("sh600000", DateTool.getStringByDate( DateTool.beforeDate(new Date(), -30)), 
						DateTool.getStringByDate(new Date()));
		} catch (StatusNotOKException e1) {
			e1.printStackTrace();
		}
		ArrayList<MacdVO> result = new ArrayList<>();
		try {
			result = service.calculateMacd("sh600000", DateTool.beforeDate(new Date(), -30), new Date());
			boolean isZero = false;
			if(result.size() >= 0)
				isZero = true;
			assertEquals(stockList.size(),result.size());
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
		
	}

}
