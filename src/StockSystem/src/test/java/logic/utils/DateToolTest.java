package logic.utils;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;

import utils.DateTool;

public class DateToolTest {

	@Test
	public void testIsWeekend(){
		Date date = new Date();
		assertFalse(DateTool.isWeekend(date));
	}
	
}
