package vo;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BollVO_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BollVO v0 = new BollVO(0.1,0.2,0.3,new Date());
		assertEquals(0.1, v0.getMb(),0.01);
		assertEquals(0.2,v0.getUp(),0.01);
		v0.getDate();
		assertEquals(0.3,v0.getDn(),0.01);
		v0.getDataItemIterator();
		KDJVO a=new KDJVO(0.1,0.2,0.3,new Date());
		assertEquals(0.1,a.getK(),0.01);
		assertEquals(0.2,a.getD(),0.01);
		v0.getDate();
		assertEquals(0.3,a.getJ(),0.01);
		a.getDataItemIterator();
		BigDecimal kkk=new BigDecimal(0.1);
		KLineVO k=new KLineVO(kkk, kkk, kkk,kkk, kkk, kkk,new Date());
k.getClose();k.getDataItemIterator();k.getDate();k.getDealPrice();k.getHigh();k.getLow();k.getOpen();k.getVolumn();
StockVO  sv=new StockVO(kkk, kkk, kkk, kkk, kkk, kkk, kkk, kkk, kkk,"2016-1-1");
sv.getAdj_price();sv.getClose();sv.getDate();sv.getHigh();sv.getLow();sv.getOpen();sv.getPb();sv.getTurnover();sv.getPe_ttm();sv.getTurnover();

	}

}