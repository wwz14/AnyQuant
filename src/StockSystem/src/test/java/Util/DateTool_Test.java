package Util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import utils.DateTool;
import utils.PeriodType;

public class DateTool_Test {

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
		Date d0 = new Date();
		Date d1 = DateTool.beforeDate(d0, -1);
		Date d2 = DateTool.beforeDate(d1, -1);
		Date d3 = DateTool.beforeDate(d2, -1);
		Date d4 = DateTool.beforeDate(d3, -3);
		List<Date> d = new ArrayList<>();
		d.add(d4);d.add(d3);d.add(d2);d.add(d1);d.add(d0);
		 assertNotNull(DateTool.beforeDate(new Date(), -1));
		 assertNotNull(DateTool.getDateByString("2014-01-01"));
		 assertNotNull(DateTool.getNewestDay());
		 assertNotNull(DateTool.betweenDays(DateTool.beforeDate(new Date(), -1), new Date()));
		 assertNotNull(DateTool.beforeDays("2015-01-01", -1));
		 assertNotNull(DateTool.beforeDate(new Date(), 2));
		 assertNotNull(DateTool.beforeDays("2015-01-01", 1));
		 assertNotNull(DateTool.getNewestTime());
		 assertNotNull(DateTool.getStringByDate(new Date()));
		 assertNotNull(DateTool.currentDay());
		 assertNotNull(DateTool.getTheFirstDay(new Date()));
		 assertNotNull(DateTool.getTheLastDay(new Date()));
		 assertNotNull(DateTool.getTheLastDayOfWeek(new Date()));
		 assertNotNull(DateTool.getTheLastDayOfWeek(DateTool.beforeDate(new Date(), -3)));
		 assertNotNull(DateTool.getTheSunDayOfWeek(new Date()));
		 assertNotNull(DateTool.today());
		 DateTool.getMaxDate(d);
		 DateTool.getMinDate(d);
		 DateTool.isSamePeriod(d,  PeriodType.DAY);
		 DateTool.isSamePeriod(d,  PeriodType.WEEK);
		 DateTool.isSamePeriod(d,  PeriodType.MONTH);
	}

}
