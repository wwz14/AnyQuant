package data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import po.NBenchMarkPO;

public class BenchmarkDataTest {
	BenchmarkData a;
	@Before
	public void setUp() throws Exception {
		a=new BenchmarkData();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetByName() {
		try {
			ArrayList<NBenchMarkPO> aa=	a.getByName("hs300");
			assertNotNull(aa);
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetByTime() {
		try {
		a.getByTime("hs300", null, null);
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
