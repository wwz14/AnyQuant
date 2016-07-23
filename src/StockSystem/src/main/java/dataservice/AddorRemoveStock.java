package dataservice;

import java.sql.SQLException;


import exception.StatusNotOKException;
import utils.ResultMsg;

public interface AddorRemoveStock {
	
	public void addStock(String stockname) throws StatusNotOKException;
	
	public void removeStock(String stockname)  throws SQLException;
	
	

}
