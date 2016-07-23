package logic.Initializebl;

import java.io.IOException;

import data.sqlImpl.InitialzeDataSQL;
import dataservice.InitiailzeDataservice;
import exception.StatusNotOKException;
import logicservice.showInfoblservice.initialLogicservice;

public class InitializeInDatabeseLogic implements initialLogicservice{
    private InitiailzeDataservice service = new InitialzeDataSQL();
	@Override
	public void save() throws StatusNotOKException, IOException {
		
		
	}

	@Override
	public void updateDatabese() throws StatusNotOKException {
		service.initailzeTodayStockInfo();
		service.initialzeBenchmarkNearly();	
	}

}
