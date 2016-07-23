package data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetJSONTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetJSON() {
		System.out.println(GetJSON.getJSON("http://121.41.106.89:8010/api/stock/sh600216/?start=2016-03-03&end=2016-03-05"));
	}

}
