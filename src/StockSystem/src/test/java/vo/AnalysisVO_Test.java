package vo;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class AnalysisVO_Test {

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
		 AnalysisVO v = new  AnalysisVO("123",2,new Date(),null);
		 assertEquals("123",v.getName());
		 assertEquals(2,v.getStatistic(),0.01);
		 v.getDate();
		 
	}

}
