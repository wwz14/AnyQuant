package logic.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CorrelationCoefficientTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrelationCoefficient() {
		double[]arg1={1,2,3,4,5,6,7,8,9};
		double []arg2={5,6,4,7,3,6,5,5,5};
		assertEquals(-0.07,CorrelationCoefficient.correlationCoefficient(arg1, arg2),0.1);
	}

}
