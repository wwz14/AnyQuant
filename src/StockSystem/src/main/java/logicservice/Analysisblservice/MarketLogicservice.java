package logicservice.Analysisblservice;

import java.util.ArrayList;
import java.util.Date;

import exception.StatusNotOKException;
import vo.MarketVO;
import vo.NStockVO;

public interface MarketLogicservice {
	
	/**
	 * 返回16支股票的最新信息列表，数据内容包含涨幅，日期为当日日期
	 * @return
	 */
	ArrayList<NStockVO> getResultListAll(Date today) throws StatusNotOKException;
	
	/**
	 * 计算start至end时间段内的行情，行情的涨幅数值计算依据是16支股票收盘价按成交金额的加权平均数的涨跌
	 * @param start
	 * @param end
	 * @return
	 * @throws StatusNotOKException
	 */
	ArrayList<MarketVO> getMarketVOs(Date start,Date end) throws StatusNotOKException;
}
