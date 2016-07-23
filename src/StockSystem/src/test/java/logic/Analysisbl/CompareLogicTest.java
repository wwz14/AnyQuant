package logic.Analysisbl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.Stockfield;
import junit.framework.TestCase;

public class CompareLogicTest extends TestCase {
	CompareLogic a;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		a=new CompareLogic();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testChangeRateLine() {
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.adj_price).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.open).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.changeString).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.close).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.high).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.low).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.volume).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.pb).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.pe_ttm).size());
		assertEquals(22,a.changeRateLine("sh600000", Stockfield.turnover).size());
		assertEquals(22,a.changeRateLine("hs300", Stockfield.turnover).size());
	
	}

	@Test
	public void testGetcorrelationCoeffcient() {
		try {
			System.out.println(a.getcorrelationCoeffcient("sh600000", "sh600015"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testConfiInterval() {
		try {
			System.out.println(a.confiInterval("sh600000", "sh600015", Stockfield.adj_price));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
