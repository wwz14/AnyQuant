package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.DateTool;
import vo.NStockVO;
import vo.SortVO;

public class sortByTime_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BigDecimal a = new BigDecimal(1);
		NStockVO vo = new NStockVO("sh60000",new Date(),a,a,a,a,a,a,a,a,a);
		SortVO sort1  = new SortVO(vo,DateTool.beforeDate(new Date(), -1));
		SortVO sort2  = new SortVO(vo,DateTool.beforeDate(new Date(), -2));
		SortVO sort3 = new SortVO(vo,DateTool.beforeDate(new Date(), -3));
		SortVO sort4 = new SortVO(vo,DateTool.beforeDate(new Date(), -4));
		
		ArrayList<SortVO> beforeSort = new ArrayList<>();
		beforeSort.add(sort3);
		beforeSort.add(sort4);
		beforeSort.add(sort2);
		beforeSort.add(sort1);
		
		ArrayList<SortVO> result1 = SortByTime.sortByTime(beforeSort);
		
		ArrayList<SortVO> result2 = SortByTime.sortByTimeup(beforeSort);
		assertEquals(4,result1.size());
		assertEquals(4,result2.size());
	}

}
