package dataservice;

import exception.StatusNotOKException;

public interface InitiailzeDataservice {
	
	/**
	 * 初始化Stock_on_today数据库表，存如最新工作日信息
	 */
	public void initailzeTodayStockInfo() throws StatusNotOKException;
	/**
	 * 初始化benchmark_of_onyear数据库表，存入最近一年的信息
	 */
	public void initialzeBenchmarkNearly() throws StatusNotOKException;
	/**
	 * 清空stock表，重新存储从可用数据至最新工作日的数据到stock数据库表
	 */
	public void resaveStockInfo() throws StatusNotOKException;
	
	/**
	 * 清空benchmark表，重新存库从可用数据至最新工作日的数据到benchmark数据库表
	 */
	public void resaveBenchmark() throws StatusNotOKException;

}
