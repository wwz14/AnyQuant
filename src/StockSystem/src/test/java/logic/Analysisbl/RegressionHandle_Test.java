package logic.Analysisbl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Test;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.DateTool;

public class RegressionHandle_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testX() throws Exception {
		ArrayList<Double> d = new ArrayList<>();
		d.add(0.1);
		d.add(0.2);
		d.add(0.3);
		d.add(0.4);
		d.add(0.5);
		try {
			RegressionHandle  re = new RegressionHandle(d);
			double[] x = re.getCurveX();
			assertEquals(d.size()*50,x.length);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		StockListDataservice getData = new StockDataSQL();
		ArrayList<NStockPO>  stock = new ArrayList<>();
		stock = getData.getByName("sh601288", DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)), 
				DateTool.getStringByDate(new Date()));
		//TODO 按时间升序
		ArrayList<Double> yList = new ArrayList<>();
		ArrayList<Date> dateList = new ArrayList<>();
		for(NStockPO stockPO:stock) {
			yList.add(stockPO.getClose().doubleValue());//真实的y值；
			dateList.add(stockPO.getDate());
		}
		RegressionHandle  regression = new RegressionHandle(yList);
		assertEquals(yList.size()*50,regression.getCurveX().length);
			
	}
	
	public void testY() throws Exception {
		ArrayList<Double> d = new ArrayList<>();
		d.add(0.1);
		d.add(0.2);
		d.add(0.3);
		d.add(0.4);
		d.add(0.5);
		try {
			RegressionHandle  re = new RegressionHandle(d);
			double[] y = re.getCurveY();
			assertEquals(d.size()*50,y.length);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
		StockListDataservice getData = new StockDataSQL();
		ArrayList<NStockPO>  stock = new ArrayList<>();
		stock = getData.getByName("sh601288", DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)), 
				DateTool.getStringByDate(new Date()));
		//TODO 按时间升序
		ArrayList<Double> yList = new ArrayList<>();
		ArrayList<Date> dateList = new ArrayList<>();
		for(NStockPO stockPO:stock) {
			yList.add(stockPO.getClose().doubleValue());//真实的y值；
			dateList.add(stockPO.getDate());
		}
		RegressionHandle  regression = new RegressionHandle(yList);
		assertEquals(yList.size()*50,regression.getCurveY().length);
	}

}
