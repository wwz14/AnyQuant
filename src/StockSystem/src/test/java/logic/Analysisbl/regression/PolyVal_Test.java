package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.DateTool;
import vo.MacdVO;

public class PolyVal_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ArrayList<MacdVO> macdList = new ArrayList<>();
		double[] x = new double[20];
		double[] y = new double[20];
		for(int i = 0;i<20;i++) {
			x[i] = i + 1;
			y[i] = Math.random()*10;		
		}
		try {
			Polyfit polyfit = new Polyfit(x, y,3);
			Polyval v = new Polyval(x,polyfit);
			assertNotSame(null,v.getErrorBounds());
			assertNotSame(null,v.getErrorBoundsMatrix());
			assertNotSame(null,v.getYoutMatrix());
			assertNotSame(null,v.getErrorBoundsMatrix());
			assertNotSame(null,v.getYout());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
