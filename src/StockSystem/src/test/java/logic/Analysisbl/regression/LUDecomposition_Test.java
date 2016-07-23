package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class LUDecomposition_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testgetDouble() {
		Matrix m = new Matrix(4,4);
		LUDecomposition l = new LUDecomposition(m);
		assertNotNull(l.getDoublePivot());
	}
	
	@Test
	public void testgetL() {
		Matrix m = new Matrix(4,4);
		LUDecomposition ll = new LUDecomposition(m);
		assertNotNull(ll.getL());
	}
	
	@Test
	public void testgetU() {
		Matrix m = new Matrix(4,4);
		LUDecomposition l3 = new LUDecomposition(m);
		assertNotNull(l3.getU());
	}
	
	@Test
	public void testgetPivot() {
		Matrix m = new Matrix(4,4);
		LUDecomposition l4 = new LUDecomposition(m);
		assertNotNull(l4.getPivot());
	}
	
	@Test
	public void testIsNonsingular() {
		Matrix m = new Matrix(4,4);
		LUDecomposition l5 = new LUDecomposition(m);
		assertEquals(false,l5.isNonsingular());
	}
	
}
