package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.Stockfield;
import po.NStockPO;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import vo.NBenchMarkVO;
import vo.NStockVO;

public class FilterUtilsTest {
	ArrayList<NStockVO> po;
	Filter filter;
	@Before
	public void setUp() throws Exception {
		po=new ArrayList<NStockVO>();
		BigDecimal temp= new BigDecimal(2);
		NStockVO p=new NStockVO ("sh600000", DateTool.getDateByString("2016-3-2"), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p);
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testFilter() {
//		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
//		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.volume,new BigDecimal(1),new BigDecimal(3)));
//		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-5"));
//		assertEquals(1,FilterUtils.filter(po, filter).size());
//	}
//
//	@Test
//	public void testFilterDetailArrayListOfNStockVOFilter() {
//		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
//		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(3)));
//		filterItems.add(new FilterItem(Stockfield.volume,new BigDecimal(1),new BigDecimal(3)));
//		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-5"));
//		assertEquals(1,FilterUtils.filterDetail(po, filter).size());
//		filter=new Filter (filterItems,null,null);
//		assertEquals(1,FilterUtils.filterDetail(po, filter).size());
//		filter=new Filter (filterItems,null,DateTool.getDateByString("2016-3-5"));
//		assertEquals(1,FilterUtils.filterDetail(po, filter).size());
//		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),null);
//		assertEquals(1,FilterUtils.filterDetail(po, filter).size());
//		
//	}

	@Test
	public void testFilterDetailStringArrayListOfNStockVOFilter() {
		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(100)));
		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(100)));
		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(100)));
		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(100)));
		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(100)));
		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-10"));
		assertEquals(0,FilterUtils.filterDetail("sh600000", po, filter).size());
	}

}
