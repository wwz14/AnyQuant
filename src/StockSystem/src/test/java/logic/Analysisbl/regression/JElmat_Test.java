package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class JElmat_Test {

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
		JElmat j1 = new JElmat(10,10);
		assertNotNull(j1.copy());
		assertNotNull(j1.diag());
		//assertNotNull(j1.diag(1));
	///	assertNotNull(j1.solve(new Matrix(10,10)));
		assertNotNull(j1.rot90());
		assertNotNull(j1.rank());
		j1.flipLR();
		j1.norm1();
		j1.norm2();
		j1.normF();
		j1.normInf();
		j1.getArrayCopy();
		j1.times(1);
		j1.diag(0);
		j1.chol();
		j1.arrayLeftDivide(new Matrix(10,10));
		j1.arrayLeftDivideEquals(j1);
		j1.arrayRightDivide(j1);
		j1.arrayRightDivideEquals(j1);
		j1.times(j1);
		j1.arrayTimes(j1);
		j1.arrayTimesEquals(j1);
		j1.clone();
	//	j1.cond();
		j1.rot90(j1);
		j1.toMatrixRowVector();
		j1.toMatrixColumnVector();
		
		
	}

}
