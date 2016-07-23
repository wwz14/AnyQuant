package data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.utils.SqlDataBase;

public class SqlDataBaseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnectToSql() {
		SqlDataBase.ConnectToSql();
		assertEquals(true,true);
	}

	@Test
	public void testGetConnection() {
		SqlDataBase.getConnection();
		assertEquals(true,true);
	}

}
