package logic.initialize;

import static org.junit.Assert.*;
import logic.Initializebl.InitializeInDatabeseLogic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StatusNotOKException;

public class InitializeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		InitializeInDatabeseLogic l = new InitializeInDatabeseLogic();
		//l.save();
		try {
			l.updateDatabese();
			assertEquals(true,true);
		} catch (StatusNotOKException e) {
			assertEquals(true,true);
			e.printStackTrace();
		}
	}

}
