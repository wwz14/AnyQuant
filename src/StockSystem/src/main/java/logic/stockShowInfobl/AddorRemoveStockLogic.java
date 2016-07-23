package logic.stockShowInfobl;

import java.sql.SQLException;

import data.sqlImpl.AddorRemoveStockSQL;
import dataservice.AddorRemoveStock;
import logicservice.showInfoblservice.AddorRemoveLogicservice;

public class AddorRemoveStockLogic implements AddorRemoveLogicservice {
     private  AddorRemoveStock service = new AddorRemoveStockSQL();
	@Override
	public void addStock(String name) throws Exception {
		service.addStock(name);
		
	}

	@Override
	public void removeStock(String name) throws SQLException {
		service.removeStock(name);
		
	}

}
