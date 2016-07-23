package dataservice.stub;

import java.math.BigDecimal;
import java.util.ArrayList;

import dataservice.BenchmarkDataservice;
import exception.StatusNotOKException;
import po.NBenchMarkPO;
import utils.DateTool;

public class BenchmarkData_Stub implements BenchmarkDataservice {

	@Override
	public ArrayList<NBenchMarkPO> getByName(String name)
			throws StatusNotOKException {
		// TODO Auto-generated method stub
		return null;
	}
//	public NBenchMarkPO(String name, BigDecimal volume, BigDecimal high,
//			BigDecimal adj_price, BigDecimal low, Date date,
//			BigDecimal close, BigDecimal open) 
	@Override
	public ArrayList<NBenchMarkPO> getByTime(String name, String startTime,
			String endTime) throws StatusNotOKException {
		ArrayList<NBenchMarkPO> poList = new ArrayList<NBenchMarkPO>();
		NBenchMarkPO po = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-01"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po1 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-02"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po2 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-03"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po3 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-04"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po4 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-05"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po5 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-06"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po6 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-07"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po7 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-08"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po8 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-09"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po9 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-10"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po10 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-11"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		NBenchMarkPO po11 = new NBenchMarkPO("hs300",new BigDecimal(1.1111),new BigDecimal(1.1112),new BigDecimal(2.1113),
				new BigDecimal(1.0000),DateTool.getDateByString("2016-03-13"),
				new BigDecimal(2.1111),new BigDecimal(1.1111));
		poList.add(po);
		poList.add(po1);
		poList.add(po2);
		poList.add(po3);
		poList.add(po4);
		poList.add(po5);
		poList.add(po6);
		poList.add(po7);
		poList.add(po8);
		poList.add(po9);
		poList.add(po10);
		poList.add(po11);
		return poList;
	}

}
