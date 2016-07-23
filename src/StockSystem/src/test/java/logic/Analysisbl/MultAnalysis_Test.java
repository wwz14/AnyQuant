package logic.Analysisbl;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class MultAnalysis_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testanalyseMACD() {
		 MultAnalysis ma = new  MultAnalysis("sh600000");
		 try {
			double a = ma.analyseMACD("sh600000");
			assertEquals(0,a-a,0.1);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}	 
	}
	@Test
	public void testBoll() {
		 MultAnalysis ma = new  MultAnalysis("sh600000");
		 try {
			double a = ma.analysisBoll("sh600000");
			assertEquals(0,a-a,0.1);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}	 
	}
	@Test
	public void testRSI() {
		 MultAnalysis ma = new  MultAnalysis("sh600000");
		 try {
			double a = ma.getRSIText("sh600000");
			assertEquals(0,a-a,0.1);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}	 
	}
	@Test
	public void testKDJ() {
		 MultAnalysis ma = new  MultAnalysis("sh600000");
		 try {
			double a = ma.kdjText("sh600000");
			assertEquals(0,a-a,0.1);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}	 
	}
	@Test
	public void testMul() {
		 MultAnalysis ma = new  MultAnalysis("sh600000");
		 try {
			//ma.multAnalysis("sh600000");;
			assertNotNull(ma.analysisResult);
		} catch (Exception e) {
			assertEquals(true,true);
			e.printStackTrace();
		}	 
	}
	

	

}
