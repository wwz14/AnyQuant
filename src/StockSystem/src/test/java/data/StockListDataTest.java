package data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import po.NStockPO;

public class StockListDataTest {
	StockListData a;
	@Before
	public void setUp() throws Exception {
		a=new StockListData();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetResultListAll() {
		ArrayList<NStockPO> aa=new ArrayList<NStockPO>();
		try {
			aa=a.getResultListAll("hs300");
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetByName() {
//		ArrayList<NStockPO> aa=new ArrayList<NStockPO>();
//		try {
//			aa=a.getByName("sh600000", "2016-1-1", "2016-2-2");
//		} catch (StatusNotOKException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//assertNotNull(aa);
	}

	

	@Test
	public void testGetAllByTime() {
		ArrayList<NStockPO> aa=new ArrayList<NStockPO>();
		try {
			aa=a.getAllByTime("2016-6-1");
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(aa);
	}

}
