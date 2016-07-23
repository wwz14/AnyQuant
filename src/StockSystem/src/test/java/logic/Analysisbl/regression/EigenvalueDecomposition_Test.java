package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class EigenvalueDecomposition_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testgetV() {
		EigenvalueDecomposition e = new EigenvalueDecomposition(new Matrix(4,4));
		assertNotNull(e.getV());	
	}
	@Test
	public void testgetD() {
		EigenvalueDecomposition e = new EigenvalueDecomposition(new Matrix(4,4));
		assertNotNull(e.getD());	
	}
	@Test
	public void testgetImag() {
		EigenvalueDecomposition e = new EigenvalueDecomposition(new Matrix(4,4));
		assertNotNull(e.getImagEigenvalues());	
	}
	@Test
	public void testgetReal() {
		EigenvalueDecomposition e = new EigenvalueDecomposition(new Matrix(4,4));
		assertNotNull(e.getRealEigenvalues());	
	}
	

}
