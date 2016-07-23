package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class QRDecomposition_Test {

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
		QRDecomposition o = new QRDecomposition(new Matrix(4,4));
		assertNotNull(o.getH());
		assertNotNull(o.getQ());
		assertNotNull(o.getR());
		//o.solve(new Matrix(4,4));
		//assertNotNull(o.solve(new Matrix(4,4)));
		assertNotNull(o.isFullRank());
	}

}
