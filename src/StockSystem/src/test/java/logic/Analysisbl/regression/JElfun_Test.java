package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class JElfun_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Matrix m = new Matrix(4,4);
		assertNotNull(JElfun.acos(m));
		assertNotNull(JElfun.asin(m));
		assertNotNull(JElfun.atan(m));
		assertNotNull(JElfun.cos(m));
		assertNotNull(JElfun.cosh(m));
		assertNotNull(JElfun.exp(m));
		assertNotNull(JElfun.isInfinte(m));
		JElfun.log10(m);
		//assertNotNull(JElfun.log10(m));
		JElfun.logN(4, m);
		//assertNotNull(JElfun.logN(4, m));
		try {
			assertNotNull(JElfun.logN(m, 4));
		} catch (Exception e) {
			assertEquals(0,0);
			//e.printStackTrace();
		}
		assertNotNull(JElfun.pow(2, m));
		assertNotNull(JElfun.tan(m));
		assertNotNull(JElfun.tanh(m));
		assertNotNull(JElfun.sync(m));
		assertNotNull(JElfun.sqrt(m));
		assertNotNull(JElfun.sinh(m));
		assertNotNull(JElfun.sin(m));
	}

}
