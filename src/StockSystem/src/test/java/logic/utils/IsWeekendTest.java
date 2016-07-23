package logic.utils;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IsWeekendTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		if((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
			   assertEquals(true,IsWeekend.isWeekend(today));
		   else 
			   assertEquals(false,IsWeekend.isWeekend(today));		
	}

}
