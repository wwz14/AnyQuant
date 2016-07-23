package logicservice.Analysisblservice;

import exception.StatusNotOKException;
import utils.ResultMsg;

public interface MarkovLogicservice {
	
	/**
	 * 马尔可夫模型预测
	 * @param name
	 * @param price输入用户买入的价格
	 * @throws StatusNotOKException 
	 * @return返回用户的持股最佳天数和用户最佳平均收益
	 */
	ResultMsg MaxProfit(String name) throws StatusNotOKException ;

}
