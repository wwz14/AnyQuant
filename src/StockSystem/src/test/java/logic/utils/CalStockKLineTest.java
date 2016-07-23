package logic.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.KLineType;
import exception.StatusNotOKException;
import utils.DateTool;

public class CalStockKLineTest {
	CalStockKLine a=new CalStockKLine();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetStockStatistic()  {
try {
	assertEquals(23,a.getStockStatistic("sh600000", DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-2-2")).size());
} catch (StatusNotOKException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}

	@Test
	public void testGetKLineVOs() {
		try {
			assertEquals(23,a.getKLineVOs("sh600000", KLineType.day,DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-2-2")).size());
			assertEquals(2,a.getKLineVOs("sh600000", KLineType.month,DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-2-2")).size());
			assertEquals(6,a.getKLineVOs("sh600000", KLineType.week,DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-2-2")).size());
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
