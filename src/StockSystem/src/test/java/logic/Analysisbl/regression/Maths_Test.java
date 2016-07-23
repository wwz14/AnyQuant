package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Maths_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		double result1 = Maths.hypot(2, 3);
		double result2 = Maths.hypot(5, 6);
		double result3 = Maths.hypot(3, 0);
		double result4 = Maths.hypot(-3, 0);
		assertEquals(3.0,result3,0.01);
		assertEquals(3.0,result4,0.01);
		assertEquals(3.60,result1,0.01);
		assertEquals(7.81,result2,0.01);
		
	}

	

}
