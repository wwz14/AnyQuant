package logic.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import utils.DateTool;
import vo.MAVO;

public class CalStockMATest {
	CalStockMA cm=new CalStockMA();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	

	@Test
	public void testGetMAVOs() {
		ArrayList<MAVO>result=new ArrayList<MAVO>();
		try {
		result=cm.getMAVOs("sh600000", DateTool.getDateByString("2016-1-1"), DateTool.getDateByString("2016-2-2"));
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(23,result.size());
	}

}
