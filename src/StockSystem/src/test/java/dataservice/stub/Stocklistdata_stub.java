package dataservice.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import po.NStockPO;

public class Stocklistdata_stub implements StockListDataservice{

	@Override
	public ArrayList<NStockPO> getResultListAll(String exchange) throws StatusNotOKException {
		ArrayList<NStockPO> po=new ArrayList<NStockPO>();
		BigDecimal temp= new BigDecimal(1);
		NStockPO p=new NStockPO ("sh600", new Date(2011-11-11), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p);
		return po;
	}

	@Override
	public ArrayList<NStockPO> getByName(String name, String startTime, String endTime) throws StatusNotOKException {
		ArrayList<NStockPO> po=new ArrayList<NStockPO>();
		if(name.equals("sz600")){
		
		BigDecimal temp= new BigDecimal(1);
		NStockPO p=new NStockPO ("sz600", new Date(2011-11-11), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p);
		NStockPO p1=new NStockPO ("sz600", new Date(2011-11-12), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p1);
		NStockPO p2=new NStockPO ("sz600", new Date(2011-11-13), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p2);
		NStockPO p3=new NStockPO ("sz600", new Date(2011-11-14), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p3);
		NStockPO p4=new NStockPO ("sz600", new Date(2011-11-15), temp, temp,
				temp,temp, temp,
				temp, temp, temp,
				temp);
		po.add(p4);
		}
		return po;
	}

    @Override
    public ArrayList<NStockPO> getAllByTime(String date)
    throws StatusNotOKException {
        // TODO Auto-generated method stub
        return null;
    }

}
