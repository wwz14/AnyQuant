package Util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import utils.DataStatus;

public class DataStatus_Test {

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
		assertEquals(false,DataStatus.isNeedRefresh());
		DataStatus.setNeedRefresh(true);
		assertEquals(true,DataStatus.isNeedRefresh());
	}

}
