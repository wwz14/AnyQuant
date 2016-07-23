package logicservice.showInfoblservice;

import java.util.ArrayList;
import java.util.Date;

import exception.StatusNotOKException;
import vo.MacdVO;

public interface MacdcalculateLogicservice {
	
	/**
	 * 计算从start到end时间段，macd图所需要的数据
	 * macd图是两柱一线，macd值是柱状图高度，dea和dif是两条曲线
	 * @param stockName
	 * @param start
	 * @param end
	 * @return
	 * @throws StatusNotOKException
	 */
	public  ArrayList<MacdVO> calculateMacd(String stockName,Date start,Date end) throws StatusNotOKException;

}
