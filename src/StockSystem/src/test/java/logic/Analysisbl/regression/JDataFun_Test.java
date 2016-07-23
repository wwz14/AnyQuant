package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class JDataFun_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		JDataFun j = new JDataFun();
	    assertNotNull(j.min(new Matrix(4,4),new Matrix(4,4)));
	    assertNotNull(j.sum(new Matrix(4,4)));
	    assertNotNull(j.sum(new Matrix(4,4),0));
	}
   
}
