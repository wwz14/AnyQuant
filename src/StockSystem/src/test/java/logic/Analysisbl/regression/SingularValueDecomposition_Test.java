package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SingularValueDecomposition_Test {

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
		SingularValueDecomposition s = new SingularValueDecomposition(new Matrix(4,4));
	assertNotNull(s.getS());
	assertNotNull(s.getSingularValues());
	assertNotNull(s.getU());
	assertNotNull(s.getV());
	assertNotNull(s.norm2());
	assertNotNull(s.cond());
	assertNotNull(s.rank());
	}

}
