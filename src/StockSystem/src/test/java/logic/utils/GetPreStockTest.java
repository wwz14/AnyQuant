

package logic.utils;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import po.NBenchMarkPO;
import po.NStockPO;
import utils.DateTool;

public class GetPreStockTest extends TestCase {
	ArrayList<NStockPO> get;
	ArrayList<NBenchMarkPO> getBench;
	@Before
	protected void setUp() throws Exception {

		super.setUp();
		get=new ArrayList<NStockPO>();
		get=GetPreStock.getPreS("sh600015",DateTool.getDateByString("2016-3-29"),DateTool.getDateByString("2016-4-10"),10);
		getBench=GetPreStock.getPreB("hs300", DateTool.getDateByString("2016-3-29"),DateTool.getDateByString("2016-4-10"),10);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetPreStock() {
		ArrayList<NStockPO> get2=	GetPreStock.getPreStock(get, 5);
		//		System.out.println(get2.size());
		//		for(int i=0;i<get2.size();i++){
		//			System.out.println(get2.get(i).getDate());
		//		}
		assertEquals(14,get2.size());
		GetPreStock.getPreStock(get,100);
	}

	@Test
	public void testJiequ() {
		ArrayList<NStockPO> get2=	GetPreStock.jiequ(get, 5);
		//		System.out.println(get2.size());
		//		for(int i=0;i<get2.size();i++){
		//			System.out.println(get2.get(i).getDate());
		//		}
		assertEquals(5,get2.size());
		ArrayList<NStockPO> get3=GetPreStock.jiequ(get,100);
		assertEquals(0,get3.size());
	}

	@Test
	public void testGetPreS() {

		assertEquals(19,get.size());


	}

	@Test
	public void testGetByStartEnd() {

		ArrayList<NStockPO> get2=	GetPreStock.getByStartEnd(get,DateTool.getDateByString("2016-4-1") , DateTool.getDateByString("2016-4-5"));
		//		System.out.println(get2.size());
		//		for(int i=0;i<get2.size();i++){
		//			System.out.println(get2.get(i).getDate());
		//		}
		assertEquals(3,get2.size());
	}

	@Test
	public void testJiequB() {
		ArrayList<NBenchMarkPO> get2=	GetPreStock.jiequB(getBench, 5);
		//		System.out.println(get2.size());
		//		for(int i=0;i<get2.size();i++){
		//			System.out.println(get2.get(i).getDate());
		//		}
		assertEquals(5,get2.size());
	}

	@Test
	public void testGetPreB() {
		assertEquals(19,getBench.size());
	}

}

