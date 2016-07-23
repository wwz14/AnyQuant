
package dataservice;

import java.util.ArrayList;

import exception.StatusNotOKException;
import po.NStockPO;

public interface StockListDataservice {
	
	/**
	 * 返回一开始显示在股票列表界面上
	 * 助教写得说明上是事先选取几只感兴趣的股票展示
	 * 所有股票一个时刻的信息
	 * @param exchange 交易所，目前可以为 sz或sh，代表深交所和上交所
	 * @return
	 * @throws StatusNotOKException 可能status不是ok
	 */
	ArrayList<NStockPO> getResultListAll(String exchange) throws StatusNotOKException;
	
	/**
	 * 返回某股票的历史信息
	 * @param name 股票代码
	 * @param startTime 起始时间，如果其为null，则返回默认（最近一个月）的历史信息
	 * @param endTime 终止时间，如果其为null,则返回起始时间到现在的历史信息
	 * @return
	 * @throws StatusNotOKException 可能status不是ok
	 */
	ArrayList<NStockPO> getByName(String name,String startTime , String endTime) throws StatusNotOKException;
	
    /**
     * 返回这一天16支股票的信息
     * @param startTime
     * @param endTime
     * @return
     */
    ArrayList<NStockPO> getAllByTime(String date) throws StatusNotOKException;
	

}
