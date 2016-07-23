package vo;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MAVOTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		RadarDataVO rd=new RadarDataVO(0, 0, 0, 0, 0, "a");
		rd.getAverageGains();rd.getDataItemIterator();rd.getPb();rd.getRisingProbability();rd.getRiskIndex();rd.getVar();rd.getVol();
		BigDecimal kkk=new BigDecimal(0.1);
		MAVO ma=new MAVO(0, null);
		MAVO ma2=new MAVO(kkk, kkk, kkk, kkk, kkk, kkk, null);
		ma2.getDataItemIterator();ma2.getDate();ma2.getMA20();ma2.getMA30();ma2.getMA5();ma2.getMA60();ma2.getMAVOL10();ma2.getMAVOL5();
	}

}
