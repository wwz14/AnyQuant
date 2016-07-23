package logic.Analysisbl;

import static org.junit.Assert.*;


import org.junit.Test;

import utils.ResultMsg;


public class ContextLogicTest {
	ContextLogic a=new ContextLogic();
	

	@Test
	public void testMacdText() {
		String s="";
		try {
			s=a.macdText("sh600000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(s);
	}

	@Test
	public void testBollText() {
		String s="";
		try {
			s=a.bollText("sh600000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(s);
	}

	@Test
	public void testKdjText() {
		String s="";
		try {
			s=a.kdjText("sh600000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(s);
	}

	

	@Test
	public void testGetRSIText() {
		ResultMsg s=new ResultMsg();
		try {
			s=a.getRSIText("sh600000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(s);
	}

	@Test
	public void testGetComp() {
		String s="";
		try {
			s=a.getComp("sh600000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(s);
	}

}
