package data.sqlImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.NBenchMarkPO;

public class BenchmarkDataSQLTest {
	BenchmarkDataSQL bench=new BenchmarkDataSQL();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetByName() {
	
			ArrayList<NBenchMarkPO> b=bench.getByName("hs300");
			assertEquals(282,b.size());
			
		
	}

	@Test
	public void testGetByTime() {

			ArrayList<NBenchMarkPO> b=bench.getByTime("hs300", "2015-5-11", "2016-6-4");
			assertEquals(278,b.size());
			
		
	}

}
