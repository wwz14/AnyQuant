package logicservice.Analysisblservice;

import utils.ResultMsg;

public interface RegressionLogicservice {
	/**
	 * 回归分析
	 * @param name
	 * @throws Exception 
	 * @return返回分析的文本结果包括结论和回归方程以及下一个交易日的价格，还有默认一年的误差分析数据regressionVO
	 * resultmsg中的object为ArrayList<RegressionVO>
	 */
ResultMsg regression(String name) throws Exception ;


}
