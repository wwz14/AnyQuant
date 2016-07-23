package logic.benchmarkShowInfobl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataservice.BenchmarkDataservice;
import dataservice.stub.BenchmarkDataservice_stub;
import enums.KLineType;
import enums.SortType;
import enums.Stockfield;
import exception.StatusNotOKException;
import junit.framework.TestCase;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logic.stockShowInfobl.StockListLogic;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import po.NBenchMarkPO;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import vo.KLineVO;
import vo.MAVO;
import vo.NBenchMarkVO;
import vo.NStockVO;

public class BenchmarkLogicTest extends TestCase {
	BenchmarkLogic bm = null;
	ArrayList<NBenchMarkPO> po = new ArrayList<NBenchMarkPO>();
	BenchmarkDataservice bd = new BenchmarkDataservice_stub();

	@Before
	public void setUp() throws Exception {
		bm = new BenchmarkLogic();

		BigDecimal temp = new BigDecimal(1);
		NBenchMarkPO p = new NBenchMarkPO("sz600", temp, temp, temp, temp, new Date(2011 - 11 - 11), temp, temp);
		po.add(p);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetByName() {
		bm.setService(bd);
		assertEquals(0, bm.getByName("sz500").size());
	}

	@Test
	public void testGetResultList() {
		bm.setService(bd);
		BigDecimal temp = new BigDecimal(1);
		ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
		FilterItem op = new FilterItem(Stockfield.open, temp, temp);
		FilterItem hi = new FilterItem(Stockfield.high, temp, temp);
		FilterItem lo = new FilterItem(Stockfield.low, temp, temp);
		FilterItem cl = new FilterItem(Stockfield.close, temp, temp);
		FilterItem ad = new FilterItem(Stockfield.adj_price, temp, temp);
		FilterItem vo = new FilterItem(Stockfield.volume, temp, temp);
		FilterItem tu = new FilterItem(Stockfield.turnover, temp, temp);
		FilterItem pe = new FilterItem(Stockfield.pe_ttm, temp, temp);
		FilterItem pb = new FilterItem(Stockfield.pb, temp, temp);
		filterItems.add(op);
		filterItems.add(hi);
		filterItems.add(lo);
		filterItems.add(cl);
		filterItems.add(ad); 
		filterItems.add(vo);
		filterItems.add(tu);
		filterItems.add(pe);
		filterItems.add(pb);

		// 构造filter
		Filter filter = new Filter(filterItems,  DateTool.getDateByString("2015-11-11"), DateTool.getDateByString("2015-11-11"));
		//assertEquals(1, bm.getResultList(null, filter).size());
		Filter filter2 = new Filter(filterItems,  DateTool.getDateByString("2015-11-11"), DateTool.getDateByString("2015-11-11"));
	//	assertEquals(0, bm.getResultList(null, filter2).size());
	}
	@Test
	public void testgetMAVOs() {
		 try {
			ArrayList<MAVO> list = bm.getMAVOs(DateTool.beforeDate(new Date(), -20), new Date());
			assertNotNull(list);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
	}
	@Test
	public void testgetKLineVOs() {
		try {
			ArrayList<KLineVO> list = bm.getKLineVOs(KLineType.day,DateTool.beforeDate(new Date(), -20), new Date());
			assertNotNull(list);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
		try {
			ArrayList<KLineVO> list = bm.getKLineVOs(KLineType.month,DateTool.beforeDate(new Date(), -90), new Date());
			assertNotNull(list);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
		try {
			ArrayList<KLineVO> list = bm.getKLineVOs(KLineType.week,DateTool.beforeDate(new Date(), -30), new Date());
			assertNotNull(list);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
	}
	@Test
	public void testSort() {
		BenchmarkLogicservice service = new BenchmarkLogic();
		 BigDecimal temp= new BigDecimal(1);
//		 NBenchMarkVO(String name, BigDecimal volume, BigDecimal high,
//					BigDecimal adj_price, BigDecimal low, Date date,
//					BigDecimal close, BigDecimal open)
		 NBenchMarkVO Vo1=new NBenchMarkVO("sh600000",temp, temp,
					temp,temp, DateTool.getDateByString("2011-11-11"),temp,
					temp); 
		 ArrayList<NBenchMarkVO>  polistlist = new ArrayList<>();
		    polistlist.clear();
			polistlist.add(Vo1);
			 BigDecimal temp1= new BigDecimal(1);
			 NBenchMarkVO Vo2=new NBenchMarkVO("sh600000",temp1, temp1,
						temp1,temp1, DateTool.getDateByString("2011-11-11"),temp,
						temp1); 	
			 polistlist.add(Vo2);
			 
			 ArrayList<NBenchMarkVO> list1 = service.sort(polistlist, SortType.adj_priceAscent);
			 assertEquals(2,list1.size());
			 
			 ArrayList<NBenchMarkVO> list2 = service.sort(polistlist, SortType.adj_priceDown);
			 assertEquals(2,list2.size());
			 
			 ArrayList<NBenchMarkVO> list3 = service.sort(polistlist, SortType.openAscend);
			 assertEquals(2,list3.size());
			 
			 ArrayList<NBenchMarkVO> list4 = service.sort(polistlist, SortType.openDown);
			 assertEquals(2,list4.size());
			 
			 ArrayList<NBenchMarkVO> list5 = service.sort(polistlist, SortType.closeDown);
			 assertEquals(2,list5.size());
			 
			 ArrayList<NBenchMarkVO> list6 = service.sort(polistlist, SortType.closeAscend);
			 assertEquals(2,list6.size());
			 
			 ArrayList<NBenchMarkVO> list7 = service.sort(polistlist, SortType.highAscend);
			 assertEquals(2,list7.size());
			 
			 ArrayList<NBenchMarkVO> list8 = service.sort(polistlist, SortType.highDown);
			 assertEquals(2,list8.size());
			 
			 ArrayList<NBenchMarkVO> list9 = service.sort(polistlist, SortType.lowAscend);
			 assertEquals(2,list9.size());
			 
			 ArrayList<NBenchMarkVO> list10 = service.sort(polistlist, SortType.lowDown);
			 assertEquals(2,list10.size());
			 
			 
			 ArrayList<NBenchMarkVO> list13 = service.sort(polistlist, SortType.volumeAscent);
			 assertEquals(2,list13.size());
			 
			 ArrayList<NBenchMarkVO> list14 = service.sort(polistlist, SortType.volumeDown);
			 assertEquals(2,list14.size());
			 
			 ArrayList<NBenchMarkVO> list15 = service.sort(polistlist, SortType.volumeDown);
			 assertEquals(2,list15.size());
			 
			 ArrayList<NBenchMarkVO> list16 = service.sort(polistlist, SortType.dateDown);
			 assertEquals(2,list16.size());
			 ArrayList<NBenchMarkVO> list17 = service.sort(polistlist, SortType.dateAscent);
			 assertEquals(2,list17.size());
	}
	@Test
	public void testgetByTime() {
		BenchmarkLogicservice service = new BenchmarkLogic();
		try {
			ArrayList<NBenchMarkVO> list = new ArrayList<NBenchMarkVO>();
			list = service.getByTime("sh600000", DateTool.beforeDate(new Date(),-10), new Date());
			assertEquals(0,0);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
		try {
			ArrayList<NBenchMarkVO> list = new ArrayList<NBenchMarkVO>();
			list = service.getByTime("sh600000", null, null);
			assertEquals(0,0);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
		try {
			ArrayList<NBenchMarkVO> list = new ArrayList<NBenchMarkVO>();
			list = service.getByTime("sh600000", DateTool.beforeDate(new Date(),-30), null);
			assertEquals(0,0);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
		
	}

}
