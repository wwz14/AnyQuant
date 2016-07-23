package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.DateTool;

public class DateAndAverage_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		DateAndAverage  d = new DateAndAverage(new Date(),new BigDecimal(0.1));
		assertEquals(DateTool.getStringByDate(new Date()),DateTool.getStringByDate(d.getDate()));
		BigDecimal b = new BigDecimal(0.1);
		assertEquals(b.doubleValue(),d.getAveraage().doubleValue(),0.1);
	}

}
