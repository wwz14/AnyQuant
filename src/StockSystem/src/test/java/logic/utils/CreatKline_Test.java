package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import utils.DateTool;
import vo.NBenchMarkVO;

public class CreatKline_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BigDecimal temp = new BigDecimal(1);
		ArrayList<NBenchMarkVO> list = new ArrayList<>();
		NBenchMarkVO p = new NBenchMarkVO("sz600", temp, temp, temp, temp,new Date() , temp, temp);
		NBenchMarkVO p1 = new NBenchMarkVO("sz600", temp, temp, temp, temp,DateTool.beforeDate(new Date(), -1), temp, temp);
		NBenchMarkVO p2 = new NBenchMarkVO("sz600", temp, temp, temp, temp,DateTool.beforeDate(new Date(), -2), temp, temp);
		NBenchMarkVO p3 = new NBenchMarkVO("sz600", temp, temp, temp, temp,DateTool.beforeDate(new Date(), -3), temp, temp);
		NBenchMarkVO p4 = new NBenchMarkVO("sz600", temp, temp, temp, temp,DateTool.beforeDate(new Date(), -4), temp, temp);
		NBenchMarkVO p5 = new NBenchMarkVO("sz600", temp, temp, temp, temp,DateTool.beforeDate(new Date(), -5), temp, temp);	
		list.add(p5);
		list.add(p4);
		list.add(p3);
		list.add(p2);
		list.add(p1);
		list.add(p);
		assertNotNull(CreatKline.kLineForDay(p,new Date()));
		assertNotNull(CreatKline.kLineForMonth(list, new Date()));
		//assertNotNull(CreatKline.kLineForMonth(list, new Date()));
		assertNotNull(CreatKline.kLineForWeek(list, new Date()));
		
	}

}
