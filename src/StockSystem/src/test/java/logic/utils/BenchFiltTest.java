package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.Stockfield;
import logic.utils.*;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import vo.NBenchMarkVO;


public class BenchFiltTest {
	ArrayList<NBenchMarkVO> poList = new ArrayList<NBenchMarkVO>();
	Filter filter;
	@Before
	public void setUp() throws Exception {

		NBenchMarkVO po = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-01"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po1 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-02"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po2 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-03"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po3 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-04"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po4 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-05"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po5 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-06"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po6 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-07"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po7 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-08"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po8 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-09"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po9 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-10"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po10 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-11"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkVO po11 = new NBenchMarkVO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-13"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		poList.add(po);
		poList.add(po1);
		poList.add(po2);
		poList.add(po3);
		poList.add(po4);
		poList.add(po5);
		poList.add(po6);
		poList.add(po7);
		poList.add(po8);
		poList.add(po9);
		poList.add(po10);
		poList.add(po11);
	}


	/* 假设
    open("开盘价"), 
    high("最高价"),   
    low("最低价"),  
    close("收盘价")    
    adj_price("后复权价"),   
    volume("成交量"),   
   turnover("换手率"),  
   pe("市盈率"), 
   pb("市净率"),
   date("时间");
	 */
	@Test
	public void testFilter() {
		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.volume,new BigDecimal(1),new BigDecimal(3)));
		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-5"));
		ArrayList<NBenchMarkVO>result=BenchFilt.filter(poList, filter);
		assertEquals(12,result.size());
	}

	@Test
	public void testFilterDetail() {
		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.volume,new BigDecimal(1),new BigDecimal(3)));
		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-5"));
		ArrayList<NBenchMarkVO>result=BenchFilt.filterDetail(poList, filter);
		assertEquals(5,result.size());
		filter=new Filter (filterItems,null,DateTool.getDateByString("2016-3-5"));
		ArrayList<NBenchMarkVO>result1=BenchFilt.filterDetail(poList, filter);
		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-5"),null);
		ArrayList<NBenchMarkVO>result2=BenchFilt.filterDetail(poList, filter);
		filter=new Filter (filterItems,null,null);
		ArrayList<NBenchMarkVO>result3=BenchFilt.filterDetail(poList, filter);
	}

	@Test
	public void testFilterDetailStringArrayListOfNBenchMarkVOFilter() {
		ArrayList<FilterItem> filterItems=new ArrayList<FilterItem>();
		filterItems.add(new FilterItem(Stockfield.open,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.high,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.low,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.close,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.adj_price,new BigDecimal(1),new BigDecimal(3)));
		filterItems.add(new FilterItem(Stockfield.volume,new BigDecimal(1),new BigDecimal(3)));
		filter=new Filter (filterItems,DateTool.getDateByString("2016-3-1"),DateTool.getDateByString("2016-3-5"));
		ArrayList<NBenchMarkVO>result=BenchFilt.filterDetail("sh600000",poList, filter);
		assertEquals(0,result.size());
	}

}
