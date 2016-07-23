package logicservice.Analysisblservice;

import enums.Stockfield;
import utils.ResultMsg;

public interface PolyFitLogicservice {
/**
 * 
 * @param name 股票名称
 * @return 一个PolyFitvo的list还有一个String文本分析
 */
	ResultMsg polyFit(String name) throws Exception;
	
	/**
	 * 各个属性的多项式拟合,属性可以是下列之一：开盘价、收盘价、市盈率、市净率、换手率、adj_price，最高价、最低价
	 * @param name
	 * @param field
	 * @return
	 * @throws Exception
	 */
	ResultMsg polyFit(String name ,Stockfield field) throws Exception;
}
