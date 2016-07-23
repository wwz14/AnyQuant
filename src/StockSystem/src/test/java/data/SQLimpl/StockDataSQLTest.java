package data.sqlImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import po.NStockPO;

public class StockDataSQLTest {
	StockDataSQL stock=new StockDataSQL();
	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetByName() {
			ArrayList<NStockPO> b=stock.getByName("sh60021", "2016-5-1", "2016-6-4");
			assertEquals(0,b.size());
			
	}

	@Test
	public void testGetAllByTime() {
		
			ArrayList<NStockPO> b=stock.getAllByTime("2016-4-6");
			System.out.println(b.size());
	//TODO 2016-5-5/5-6 数据库重复日期
				
			assertEquals(16,b.size());
		
	}

	@Test
	public void testGetResultListAll() {
		
			ArrayList<NStockPO> b=stock.getResultListAll("sz");
			assertEquals(16,b.size());
	}

}
