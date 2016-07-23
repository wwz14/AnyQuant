package logicservice.Analysisblservice;

import utils.ResultMsg;

public interface ContextLogicService {
	/**
	 * 
	 * @param name 股票名称
	 * rsi适合短线，5天数据就够了
	 * resultmsg中的string是文本分析,噢bject是画图用的数据
	 * @return
	 */
	ResultMsg getRSIText(String name) throws Exception;
	/**
	 * 
	 * @param name 股票名称
	 * 综合分析
	 * @return
	 */
	String getComp(String name) throws Exception;
	/**
	 * 
	 * @param name 股票名称
	 * @return
	 */
	String macdText(String name) throws Exception;
	/**
	 * 
	 * @param name 股票名称
	 * @return
	 */
	String bollText(String name) throws Exception;
	/**
	 * 
	 * @param name 股票名称
	 * @return
	 */
	String kdjText(String name) throws Exception;
}
