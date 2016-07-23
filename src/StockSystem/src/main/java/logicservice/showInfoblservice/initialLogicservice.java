package logicservice.showInfoblservice;

import java.io.IOException;

import exception.StatusNotOKException;

public interface initialLogicservice {
	/**
	 * 存数据,不使用数据库
	 */
	void save() throws StatusNotOKException, IOException;

	/**
	 * 更新数据库数据
	 */
	void updateDatabese() throws StatusNotOKException;
	
	
}
