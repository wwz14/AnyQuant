package vo;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import po.NBenchMarkPO;

public class BenchSortVO_Test {

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
		NBenchMarkVO p = new NBenchMarkVO("sz600", temp, temp, temp, temp, new Date(2011 - 11 - 11), temp, temp);
		BenchSortVO v = new BenchSortVO(p,temp);
		assertEquals(p,v.getBenchmarkVO());
		assertEquals(temp,v.getSortData());
	}

}
