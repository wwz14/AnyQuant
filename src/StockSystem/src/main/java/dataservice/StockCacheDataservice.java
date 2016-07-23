package dataservice;

import java.util.ArrayList;

import exception.StatusNotOKException;
import po.NStockPO;

public interface StockCacheDataservice {
	/**
	 * 返回某股票的历史信息
	 * @param name 股票代码
	 * @param startTime 起始时间，如果其为null，则返回默认（最近一个月）的历史信息
	 * @param endTime 终止时间，如果其为null,则返回起始时间到现在的历史信息
	 * @return
	 * @throws StatusNotOKException 可能status不是ok
	 */
	ArrayList<NStockPO> getByName(String name,String startTime , String endTime) throws StatusNotOKException;
	
	ArrayList<NStockPO> getAllByTime(String date);
}
