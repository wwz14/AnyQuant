package logic.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStatisticsDoubleArray() {
		//double[] data = new double[]{15.23,12.11,7,88,17,89,6.578,13.456,9.1235,20.5678};  
        // Arrays.sort(data);  
          ArrayList<Double> data=new ArrayList<Double>();
          data.add(6.22);
          data.add(6.19);
          data.add(6.19);
          data.add(5.84);
          data.add(5.86);
          data.add(5.69);
          data.add(5.84);
          data.add(5.83);
          data.add(5.77);
          data.add(5.96);
          data.add(5.91);
          data.add(6.04);
          data.add(5.76);
        Statistics dsd = new Statistics(data);  
        DescriptiveStatistics ds = new DescriptiveStatistics();  
        for(int i=0; i<data.size(); i++)   
        {  
            ds.addValue(data.get(i));  
        }  
        System.out.println("Demo sum = " + dsd.getSumValue());  
        System.out.println("Demo mean = " + dsd.getMeanValue());  
        System.out.println("Demo median = " + dsd.getMedianValue());  
        System.out.println("Demo standard deviation = " + dsd.getSdValue());  
        System.out.println("DS sum = " + ds.getSum());  
        System.out.println("DS mean = " + ds.getMean());  
        System.out.println("DS median = " + ds.getPercentile(50));  
        System.out.println("DS standard deviation = " + ds.getStandardDeviation());  
	}

	

	

}
