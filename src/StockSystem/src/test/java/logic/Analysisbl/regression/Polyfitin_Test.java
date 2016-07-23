package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class Polyfitin_Test {

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
		double[] x = new double[4];
		double[] y = new double[4];
		for(int i = 0;i<4;i++) {
			x[i] =i+1;
			y[i] = Math.random()*10+1;
		}
		try {
			Polyfit p = new Polyfit(x,y,2);
			assertNotNull(p.getDegreeOfFreedom());
			assertNotNull(p.gradient());
			assertNotNull(p.getPolynomialCoefficients());
			assertNotNull(p.getPolyCoeffMatrix());
			assertNotNull(p.getR());
			assertNotNull(p.getYIntercept());
			assertNotNull(p.getNorm());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
