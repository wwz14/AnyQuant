package logic.stockShowInfobl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.SortType;
import enums.Stockfield;
import junit.framework.TestCase;
import logic.utils.FilterUtils;
import logicservice.showInfoblservice.StockListLogicservice;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import vo.NStockVO;

public class StockListLogic_Test extends TestCase{
	@SuppressWarnings("unused")
	private FilterUtils fu=null;
	private ArrayList<NStockVO> polistlist=new ArrayList<NStockVO>();
	private ArrayList<NStockVO> polistlistErr=new ArrayList<NStockVO>();
	private Filter filter=null;
	private Filter filternull=null;
	@Before
	public void setUp() throws Exception {
		fu = new FilterUtils();
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
		filter=new Filter(filterItems, DateTool.getDateByString("2011-11-11"), DateTool.getDateByString("2011-11-12"));
	//
		ArrayList<FilterItem> filterItemsnull=new ArrayList<FilterItem> ();
		filternull=new Filter(filterItemsnull, DateTool.getDateByString("2011-11-11"), DateTool.getDateByString("2011-11-12"));
	//构造polistlistErr
		BigDecimal temperr= new BigDecimal(2);
		NStockVO Voerr=new NStockVO ("sh600000",DateTool.getDateByString("2011-11-11"), temperr, temperr,
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
	public void testGetResultList() {
		assertEquals(polistlist,FilterUtils.filter(polistlist, filter));
		assertEquals(0,FilterUtils.filter(polistlistErr, filter).size());
		assertEquals(polistlist,FilterUtils.filter(polistlist, filternull));
	}

	@Test
	public void testSort() {
		//public ArrayList<NStockVO> sortStockList(ArrayList<NStockVO> vo, SortType sortType)
		 StockListLogicservice service = new StockListLogic();
		 BigDecimal temp= new BigDecimal(1);
		 NStockVO Vo1=new NStockVO ("sh600000", DateTool.getDateByString("2011-11-11"), temp, temp,
					temp,temp, temp,
					temp, temp, temp,
					temp); 
		    polistlist.clear();
			polistlist.add(Vo1);
			 BigDecimal temp1= new BigDecimal(1);
			 NStockVO Vo2=new NStockVO ("sh600000", DateTool.getDateByString("2011-11-11"), temp, temp,
						temp1,temp1, temp1,
						temp, temp, temp,
						temp); 	
			 polistlist.add(Vo2);
			 
			 ArrayList<NStockVO> list1 = service.sortStockList(polistlist, SortType.adj_priceAscent);
			 assertEquals(2,list1.size());
			 
			 ArrayList<NStockVO> list2 = service.sortStockList(polistlist, SortType.adj_priceDown);
			 assertEquals(2,list2.size());
			 
			 ArrayList<NStockVO> list3 = service.sortStockList(polistlist, SortType.openAscend);
			 assertEquals(2,list3.size());
			 
			 ArrayList<NStockVO> list4 = service.sortStockList(polistlist, SortType.openDown);
			 assertEquals(2,list4.size());
			 
			 ArrayList<NStockVO> list5 = service.sortStockList(polistlist, SortType.closeDown);
			 assertEquals(2,list5.size());
			 
			 ArrayList<NStockVO> list6 = service.sortStockList(polistlist, SortType.closeAscend);
			 assertEquals(2,list6.size());
			 
			 ArrayList<NStockVO> list7 = service.sortStockList(polistlist, SortType.highAscend);
			 assertEquals(2,list7.size());
			 
			 ArrayList<NStockVO> list8 = service.sortStockList(polistlist, SortType.highDown);
			 assertEquals(2,list8.size());
			 
			 ArrayList<NStockVO> list9 = service.sortStockList(polistlist, SortType.lowAscend);
			 assertEquals(2,list9.size());
			 
			 ArrayList<NStockVO> list10 = service.sortStockList(polistlist, SortType.lowDown);
			 assertEquals(2,list10.size());
			 
			 ArrayList<NStockVO> list11 = service.sortStockList(polistlist, SortType.pbAscent);
			 assertEquals(2,list11.size());
			 
			 ArrayList<NStockVO> list12 = service.sortStockList(polistlist, SortType.pbDown);
			 assertEquals(2,list12.size());
			 
			 ArrayList<NStockVO> list13 = service.sortStockList(polistlist, SortType.volumeAscent);
			 assertEquals(2,list13.size());
			 
			 ArrayList<NStockVO> list14 = service.sortStockList(polistlist, SortType.volumeDown);
			 assertEquals(2,list14.size());
			 
			 ArrayList<NStockVO> list15 = service.sortStockList(polistlist, SortType.volumeDown);
			 assertEquals(2,list15.size());
			 
			 ArrayList<NStockVO> list16 = service.sortStockList(polistlist, SortType.dateDown);
			 assertEquals(2,list16.size());
			 ArrayList<NStockVO> list17 = service.sortStockList(polistlist, SortType.dateAscent);
			 assertEquals(2,list17.size());
			 
			 ArrayList<NStockVO> list18 = service.sortStockList(polistlist, SortType.peAscent);
			 assertEquals(2,list18.size());
			 
			 ArrayList<NStockVO> list19 = service.sortStockList(polistlist, SortType.peDown);
			 assertEquals(2,list19.size());
			 
			 ArrayList<NStockVO> list20 = service.sortStockList(polistlist, SortType.turnoverAscent);
			 assertEquals(2,list20.size());
			 
			 ArrayList<NStockVO> list21 = service.sortStockList(polistlist, SortType.turnoverDown);
			 assertEquals(2,list21.size());
	}

	@Test
	public void testGetResultDetail() {
		//TODO
				assertEquals(polistlist,FilterUtils.filterDetail(polistlist, filter));
				assertEquals(polistlist,FilterUtils.filterDetail(polistlist, filternull));
				assertEquals(0,FilterUtils.filter(polistlistErr, filter).size());
	}

}
