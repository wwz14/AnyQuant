package logic.stockShowInfobl;


import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataservice.StockListDataservice;
import dataservice.stub.Stocklistdata_stub;
import enums.Stockfield;
import exception.StatusNotOKException;
import junit.framework.TestCase;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import vo.NStockVO;

public class StockListLogicTest extends TestCase{
	private StockListLogic fu=null;
	private ArrayList<NStockVO> polistlist=new ArrayList<NStockVO>();
	private ArrayList<NStockVO> polistlistErr=new ArrayList<NStockVO>();
	private Filter filter=null;
	private Filter filternull=null;
	@Before
	public void setUp() throws Exception {
		fu = new StockListLogic();
		//一个各种字段都为1的stockpo
		BigDecimal temp= new BigDecimal(1);
		NStockVO Vo=new NStockVO ("sh600000", DateTool.getDateByString("2011-11-11"), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		polistlist.add(Vo);
		//构造ArrayList<FilterItem>
		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem> ();
		FilterItem op=new FilterItem(Stockfield.open, temp, temp);
		FilterItem hi=new FilterItem(Stockfield.high, temp, temp);
		FilterItem lo=new FilterItem(Stockfield.low, temp, temp);
		FilterItem cl=new FilterItem(Stockfield.close, temp, temp);
		FilterItem ad=new FilterItem(Stockfield.adj_price, temp, temp);
		FilterItem vo=new FilterItem(Stockfield.volume, temp, temp);
		FilterItem tu=new FilterItem(Stockfield.turnover, temp, temp);
		FilterItem pe=new FilterItem(Stockfield.pe_ttm, temp, temp);
		FilterItem pb=new FilterItem(Stockfield.pb, temp, temp);
		filterItems.add(op);
		filterItems.add(hi);
		filterItems.add(lo);
		filterItems.add(cl);
		filterItems.add(ad);
		filterItems.add(vo);
		filterItems.add(tu);
		filterItems.add(pe);
		filterItems.add(pb);


		//构造filter
		filter=new Filter(filterItems,  DateTool.getDateByString("2015-11-11"),  DateTool.getDateByString("2015-11-12"));
	//
		ArrayList<FilterItem> filterItemsnull=new ArrayList<FilterItem> ();
		filternull=new Filter(filterItemsnull,  DateTool.getDateByString("2015-11-11"),  DateTool.getDateByString("2015-11-12"));
	//构造polistlistErr
		BigDecimal temperr= new BigDecimal(2);
		NStockVO Voerr=new NStockVO ("sh600000",  DateTool.getDateByString("2011-11-11"), temperr, temperr,
				temperr,temperr, temperr,
				temperr, temperr, temperr,
				temperr);
		polistlistErr.add(Voerr);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

	}

	

	@Test
	public void testGetResultListAll() {
		StockListDataservice socklistService =new Stocklistdata_stub();
		fu.setService(socklistService);
		assertEquals(1,fu.getResultListAll("2012", "sz").size());
	}

	@Test
	public void testSearch() {
		StockListDataservice socklistService =new Stocklistdata_stub();
		fu.setService(socklistService);
		ArrayList<NStockVO> po=new ArrayList<NStockVO>();
		BigDecimal temp= new BigDecimal(1);
		NStockVO p=new NStockVO ("sh600000", DateTool.getDateByString("2011-11-11"), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p);
		try {
			assertEquals(0,fu.search("sh600000").size());
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(0,fu.search("as300").size());
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetResultList() {
		assertEquals(polistlist,fu.getResultList(polistlist, filter));
		assertEquals(0,fu.getResultList(polistlistErr, filter).size());
		assertEquals(polistlist,fu.getResultList(polistlist, filternull));
	}

	
	@Test
	public void testClick() {
		StockListDataservice socklistService =new Stocklistdata_stub();
		fu.setService(socklistService);
		assertEquals(4,fu.click("sz600").size());
		assertEquals(0,fu.click("sh500").size());
	}

	@Test
	public void testGetResultDetail() {
	//	assertEquals(polistlist,fu.getResultDetail(polistlist, filter));
	//	assertEquals(polistlist,fu.getResultDetail(polistlist, filternull));
	//	assertEquals(0,fu.getResultDetail(polistlistErr, filter).size());
	}

}
