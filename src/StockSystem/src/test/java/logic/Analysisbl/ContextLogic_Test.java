package logic.Analysisbl;

import static org.junit.Assert.*;
import logicservice.Analysisblservice.ContextLogicService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ContextLogic_Test {

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
	public void test() throws Exception {
		ContextLogicService s = new ContextLogic();
		assertNotNull(s.bollText("sh600000"));
		assertNotNull(s.bollText("sh601288"));
		assertNotNull(s.bollText("sh601328"));
		assertNotNull(s.macdText("sh600000"));
		assertNotNull(s.macdText("sh601288"));
		assertNotNull(s.macdText("sh601328"));
		assertNotNull(s.kdjText("sh600000"));
		assertNotNull(s.kdjText("sh601288"));
		assertNotNull(s.kdjText("sh601328"));
		assertNotNull(s.getRSIText("sh600000"));
		assertNotNull(s.getRSIText("sh601288"));
		assertNotNull(s.getRSIText("sh601328"));
		
		
	}

}
