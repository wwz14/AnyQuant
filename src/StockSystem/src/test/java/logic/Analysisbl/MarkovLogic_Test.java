package logic.Analysisbl;

import static org.junit.Assert.*;
import logicservice.Analysisblservice.MarkovLogicservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;
import utils.ResultMsg;

public class MarkovLogic_Test {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		MarkovLogicservice service = new MarkovLogic();
		try {
			ResultMsg m = service.MaxProfit("sh600000");
			assertEquals(true,m.isIsok());
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
		
	}

}
