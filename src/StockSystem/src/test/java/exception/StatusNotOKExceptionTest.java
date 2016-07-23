package exception;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatusNotOKExceptionTest {
	StatusNotOKException a;
	@Before
	public void setUp() throws Exception {
		a=new StatusNotOKException();
		a.setMessage("ss");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMessage() {
		assertNotNull(a.getMessage());
	}

	@Test
	public void testSetMessage() {
		a.setMessage("ss");
	}



}
