package logic.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import vo.NStockVO;
import vo.SortVO;

public class Heapsort_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {	
//		public NStockVO(String name, Date date, BigDecimal open, BigDecimal high,
//				BigDecimal low, BigDecimal close, BigDecimal adj_price,
//				BigDecimal volume, BigDecimal turnover, BigDecimal pe_ttm,
//				BigDecimal pb) 
		BigDecimal a = new BigDecimal(1);
		NStockVO vo = new NStockVO("sh60000",new Date(),a,a,a,a,a,a,a,a,a);
		SortVO sort1  = new SortVO(vo,new BigDecimal(1));
		SortVO sort2  = new SortVO(vo,new BigDecimal(2));
		SortVO sort3 = new SortVO(vo,new BigDecimal(3));
		SortVO sort4 = new SortVO(vo,new BigDecimal(4));
		
		ArrayList<SortVO> beforeSort = new ArrayList<>();
		beforeSort.add(sort3);
		beforeSort.add(sort4);
		beforeSort.add(sort2);
		beforeSort.add(sort1);
		
		ArrayList<SortVO> result1 = HeapSort.heapSort(beforeSort);
		ArrayList<SortVO> result2 = HeapSort.AntiHeapSort(beforeSort);	
		assertEquals(4,result1.size());
		assertEquals(4,result2.size());
		HeapSort.swap(result1, 0, 1);
		assertEquals(4,result1.size());
	}

}
