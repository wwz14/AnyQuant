package logic.Analysisbl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class RegressionAnTest extends TestCase {
	RegressionAn aaaa;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		aaaa=new RegressionAn();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testRegression() {
		try {
			aaaa.regression("sh601169");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSPT1() {
		/**    * 一元回归    */
		int i;
		double[] dt=new double[6];
		double[] a=new double[2];
		double[] x={ 0.0,0.1,0.2,0.3,0.4,0.5,
				0.6,0.7,0.8,0.9,1.0};
		double[] y={ 2.75,2.84,2.965,3.01,3.20,
				3.25,3.38,3.43,3.55,3.66,3.74};
		aaaa.SPT1(x,y,11,a,dt);
		assertEquals(1f,a[1],0.1f);
		assertEquals(2.752f,a[0],0.1f);
		// System.out.println("a="+a[1]+" b="+a[0]);
		assertEquals(0.5f,dt[0],0.1f);
		assertEquals(0.213f,dt[1],0.1f);
		assertEquals(1.1f,dt[2],0.1f);
		// System.out.println("q="+dt[0]+" s="+dt[1]+" p="+dt[2]);
		assertEquals(0.0477f,dt[3],0.1f);
		assertEquals(0.012f,dt[4],0.1f);
		assertEquals(0,dt[5],0.1f);
		// System.out.println(" umax="+dt[3]+" umin="+dt[4]+"  u="+dt[5]);
	}

	@Test
	public void testSqt2() {
		/**    * 多元回归    */
		int i;
		double[] a = new double[2];
		double[] v = new double[1];
		double[] dt = new double[4];
		//double[][] x = { { 1.1, 1.0, 1.2, 1.1, 0.9 },
		//	{ 2.0, 2.0, 1.8, 1.9, 2.1 },
		//{ 3.2, 3.2, 3.0, 2.9, 2.9 } };
		double[][]x={
				{255.7,263.3,275.4,278.3,296.7,309.3,315.8,318.8
					,330.0,340.2,350.7,367.3,381.3,406.5,430.8,451.5}
		};
		//double[] y = { 10.1, 10.2, 10.0, 10.1, 10.0 };
		double[] y={
				116.5,120.8,124.4,125.5,131.7,136.2,138.7,140.2,
				146.8,149.6,153.0,158.2,163.2,170.5,178.2,185.9
		};
		RegressionAn.sqt2(x, y, 1, 16, a, dt, v);
		assertEquals(27.91f,a[1],0.1f);
		assertEquals(0.35f,a[0],0.1f);
		assertEquals(22.05f,dt[0],0.1f);
		assertEquals(1.17f,dt[1],0.1f);
		assertEquals(0.998f,dt[2],0.1f);
	}

}
