package logic.Analysisbl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import logicservice.Analysisblservice.AnalysisInMarketLogicservice;

import org.junit.AfterClass;
import org.junit.Test;

import utils.DateTool;
import vo.AnalysisVO;

public class AnalysisLogic_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testaverageMarketPrice() {
		AnalysisInMarketLogicservice s = new AnalysisLogic();
		ArrayList<AnalysisVO<Date>> l = s.averageMarketPrice(DateTool.beforeDate(new Date(), -20),new Date());
		assertNotNull(l);
	}
	@Test
	public void testshiYingLuBar() {
		AnalysisInMarketLogicservice s = new AnalysisLogic();
		ArrayList<AnalysisVO<String>> l = s.shiJingLuBar();
		assertNotNull(l);
	}
	@Test
	public void testchangeRateBar() {
		AnalysisInMarketLogicservice s = new AnalysisLogic();
		ArrayList<AnalysisVO<String>> l = s.changeRateBar();
		assertNotNull(l);
	}
	@Test
	public void testdealpriceBarandPie() {
		AnalysisInMarketLogicservice s = new AnalysisLogic();
		ArrayList<AnalysisVO<String>> l = s.dealpriceBarandPie();
		assertNotNull(l);
	}
	@Test
	public void testshiJingLuBar() {
		AnalysisInMarketLogicservice s = new AnalysisLogic();
		ArrayList<AnalysisVO<String>> l = s.shiJingLuBar();
		assertNotNull(l);
	}
	


}
