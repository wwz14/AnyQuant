package logic.Analysisbl.regression;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.DateTool;

public class CholeskyDecomposition_test {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() { 
		
		StockListDataservice getData = new StockDataSQL();
		ArrayList<NStockPO>  stock = new ArrayList<>();
		try {
			stock = getData.getByName("sh600000", DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)), 
					DateTool.getStringByDate(new Date()));
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO 按时间升序
		ArrayList<Double> yList = new ArrayList<>();
		ArrayList<Date> dateList = new ArrayList<>();
		for(NStockPO stockPO:stock) {
			yList.add(stockPO.getClose().doubleValue());//真实的y值；
			dateList.add(stockPO.getDate());
		}
		
		int size = yList.size();
		double [] x = new double[size];
		double [] y = new double[size];
		//newcurveY = new double[size];
		for (int i=0; i<yList.size(); i++) {
			x[i] = i + 1;
			y[i] = yList.get(i);
		}
		
		 Matrix y2D = new Matrix(JElmat.convertTo2D(y));
		    Matrix yTranspose = y2D.transpose();
		CholeskyDecomposition c = new CholeskyDecomposition(y2D);
		//System.out.println(c.isSPD());
		assertEquals(false,c.isSPD());
		CholeskyDecomposition c1 = new CholeskyDecomposition(y2D);
		//System.out.println(c1.isSPD());
		assertEquals(false,c1.isSPD());
		CholeskyDecomposition c2 = new CholeskyDecomposition(y2D);
		assertNotNull(c2.getL());
		CholeskyDecomposition c3 = new CholeskyDecomposition(new Matrix(4,4));
		//assertNotNull(c3.solve(new Matrix(4,4)));
	}


}
