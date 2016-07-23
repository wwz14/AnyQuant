package data.sqlImpl;

import static org.junit.Assert.*;
import logic.Initializebl.InitializeInDatabeseLogic;
import logicservice.showInfoblservice.initialLogicservice;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import utils.NetStatus;
import dataservice.InitiailzeDataservice;
import exception.StatusNotOKException;

public class InitializeDataSQL_Test {

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
		InitialzeDataSQL initial = new InitialzeDataSQL();
		if (NetStatus.isConnected()) {
		try {
			assertEquals(0,0);
			initial.resaveBenchmark();;
		//	initial.resaveStockInfo();;
			initial.initialzeBenchmarkNearly();
		//	initial.initailzeTodayStockInfo();
		} catch (StatusNotOKException e) {
			assertEquals(0,0);
			e.printStackTrace();
		}
		}
	}
	
	@Test
	public void testB() {
		InitialzeDataSQL initial = new InitialzeDataSQL();
		if (NetStatus.isConnected()) {
		try {
			assertEquals(0,0);
			//initial.resaveBenchmark();;
			initial.resaveStockInfo();;
			//initial.initialzeBenchmarkNearly();
		initial.initailzeTodayStockInfo();
		} catch (StatusNotOKException e) {
			assertEquals(0,0);
			e.printStackTrace();
		}
		}
	}
	
	

}
