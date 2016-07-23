package logicservice.showInfoblservice;

import java.sql.SQLException;

public interface AddorRemoveLogicservice {
	/**
	 * 增加新股
	 * @param name
	 * @throws Exception 
	 */
	void addStock(String name)  throws  Exception ;
	/**
	 * 删除股票
	 * @param name
	 */
	void removeStock(String name) throws SQLException ;

}
