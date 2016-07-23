package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class RowColumnIndex_Test {

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
		RowColumnIndex r = new RowColumnIndex();
		assertEquals(null, r.getColumnIndex());
		assertEquals(null, r.getRowIndex());
		//assertEquals(null, r.getTotalElements());
		assertEquals(null, r.getElementValues());
		assertEquals(null, r.getElementValuesInMatrix());
		int[] a = {1,2,3,4,5};
		int[] b = {1,2,3,4,5};
		double[] c = {1,1,1,1,1};
		RowColumnIndex r1 = new RowColumnIndex(a,b,c);
		assertNotNull(r1.getColumnIndex());
		assertNotNull(r1.getElementValues());
		assertNotNull(r1.getRowIndex());
		assertNotNull(r1.getTotalElements());
		assertNotNull(r1.getElementValuesInMatrix());
	}

}
