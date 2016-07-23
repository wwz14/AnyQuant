package data.sqlImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import data.StockListData;
import data.sqlImpl.mysql.DeleteFromSql;
import data.sqlImpl.mysql.SaveToDataBase;
import dataservice.AddorRemoveStock;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.DateTool;

public class AddorRemoveStockSQL implements AddorRemoveStock {
	 StockListData source = new  StockListData();

	@Override
	public void addStock(String name) throws StatusNotOKException {
		ArrayList<NStockPO> poList = new ArrayList<>();
		poList = source.getByName(name, DateTool.getStringByDate(DateTool.beforeDate(new Date(), -300)), DateTool.getStringByDate(new Date()));
		SaveToDataBase.insterToStockOnePiece(poList.get(poList.size() - 1), "stock_on_today");
		
		for(NStockPO po : poList) {
			SaveToDataBase.insterToStockOnePiece(po, "stock");
		}
		SaveToDataBase.insertName(name);
		

		
	}

	@Override
	public void removeStock(String stockname) throws SQLException {
		// TODO Auto-generated method stub
		//要从name.dat文件中删除
		DeleteFromSql.deleteOnePiece(stockname);
		DeleteFromSql.deletename(stockname);
	}
	
//	public static void main(String[] args) {
//	List<String> n = NamesFactory.getAllNames();
//	for(int i=0;i<n.size();i++) {
//		SaveToDataBase.insertName(n.get(i));
//	}
////	try {
////		DeleteFromSql.deletename("sh600000");
////	} catch (SQLException e) {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	}
//	}

	

}
