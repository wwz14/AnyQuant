package logic.stockShowInfobl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import junit.framework.TestCase;

import logic.stockShowInfobl.StockKLineLogic;
import po.NStockPO;
import utils.DateTool;
import vo.BollVO;
import vo.KDJVO;
import vo.RSIVO;
import vo.StockStatisticVO;

public class StockKLineLogicTest extends TestCase{
	StockKLineLogic skl;
	@Before
	public void setUp() throws Exception {
		skl=new StockKLineLogic();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetRSI() throws StatusNotOKException {
		ArrayList<RSIVO> ma=new ArrayList<RSIVO>();
		try {
			ma=skl.getRSI("sh600000", DateTool.getDateByString("2016-4-5"), DateTool.getDateByString("2016-4-6"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(2,ma.size());
	}
	@Test
	public void testCalGetRSI() throws StatusNotOKException {
		ArrayList<Double> ma=new ArrayList<Double>();
		ArrayList<NStockPO> po=new ArrayList<NStockPO>();
		BigDecimal temp= new BigDecimal(1);
		NStockPO p=new NStockPO ("sh600", new Date(2011-11-11), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		po.add(p);
		try {
			ma=skl.calRSI(po, 6);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(4,ma.size());
	}
	
	@Test
	public void testGetBoll() throws StatusNotOKException {
		ArrayList<BollVO> ma=skl.getBoll("sh600000", DateTool.getDateByString("2016-4-5"), DateTool.getDateByString("2016-4-6"));
//		System.out.println(ma.size());
//		for(int i=0;i<ma.size();i++){
//			System.out.println("dn"+ma.get(i).getDn());
//			System.out.println("mb"+ma.get(i).getMb());
//			System.out.println("up"+ma.get(i).getUp());
//			System.out.println("date"+ma.get(i).getDate());
//			
//		}
		assertEquals(2,ma.size());
	}

	@Test
	public void testGetKDJ() throws StatusNotOKException {
		ArrayList<KDJVO> ma=skl.getKDJ("sh600000", DateTool.getDateByString("2016-4-5"), DateTool.getDateByString("2016-4-6"));
//		System.out.println(ma.size());
//		for(int i=0;i<ma.size();i++){
//			System.out.println("k"+ma.get(i).getK());
//			System.out.println("d"+ma.get(i).getD());
//			System.out.println("j"+ma.get(i).getJ());
//			System.out.println("date"+ma.get(i).getDate());
//			
//		}
		assertEquals(2,ma.size());
		//防御式编程，分母为0时没办法测试
	}

	@Test
	public void testGetStockATR() throws StatusNotOKException {
		ArrayList<StockStatisticVO> ma=skl.getStockATR("sh600000", DateTool.getDateByString("2016-4-5"), DateTool.getDateByString("2016-4-6"));
//		System.out.println(ma.size());
//		for(int i=0;i<ma.size();i++){
//			System.out.println("atr"+ma.get(i).getATR());
//			
//		}
		assertEquals(2,ma.size());
	}

}
