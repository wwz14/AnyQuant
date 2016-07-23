package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class Matrix_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testconstructWithCopy() {
		Matrix m = new Matrix(4,4);
		double[][] A = new double[4][4];
		for(int i = 0;i<4;i++) {
			for(int j = 0;j<4;j++) {
				A[i][j] = 0;
			}
		}
		assertNotNull(m.constructWithCopy(A));
	}
	@Test
	public void testCopy() {
		Matrix m = new Matrix(4,4);
		assertNotNull(m.copy());
		assertNotNull(m.getArray());
		assertNotNull(m.clone());
		assertNotNull(m.getColumnDimension());
		assertNotNull(m.getColumnPackedCopy());
		assertNotNull(m.plus(new Matrix(4,4)));
		assertNotNull(m.arrayTimes(new Matrix(4,4)));
		assertNotNull(m.arrayTimesEquals(new Matrix(4,4)));
		assertNotNull(m.minus(m));
		assertNotNull(m.minusEquals(m));
		assertNotNull(m.arrayRightDivideEquals(new Matrix(4,4)));
		assertNotNull(m.arrayRightDivide(new Matrix(4,4)));
		assertNotNull(m.arrayLeftDivide(new Matrix(4,4)));
		assertNotNull(m.arrayLeftDivideEquals(new Matrix(4,4)));
	}

}
