package dataservice.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import dataservice.BenchmarkDataservice;
import exception.StatusNotOKException;
import po.NBenchMarkPO;

public class BenchmarkDataservice_stub implements BenchmarkDataservice{

	@Override
	public ArrayList<NBenchMarkPO> getByName(String name) throws StatusNotOKException {
		
		ArrayList<NBenchMarkPO> po=new ArrayList<NBenchMarkPO>();
		if(name.equals("sz600")){
		BigDecimal temp= new BigDecimal(1);
		NBenchMarkPO p=new NBenchMarkPO ("sz600",  temp, temp,
				temp,temp, new Date(2011-11-11),temp,
				temp);
		po.add(p);
		System.out.println(po.size());
		}
		return po;
	}

	@Override
	public ArrayList<NBenchMarkPO> getByTime(String name, String startTime, String endTime)
			throws StatusNotOKException {
		ArrayList<NBenchMarkPO> po=new ArrayList<NBenchMarkPO>();
		if(name.equals("hs300")){
		BigDecimal temp= new BigDecimal(1);
		NBenchMarkPO p=new NBenchMarkPO ("hs300",  temp, temp,
				temp,temp, new Date(2011-11-11),temp,
				temp);
		po.add(p);
		}
		return po;
	}

}
