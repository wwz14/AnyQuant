package logicservice.Analysisblservice;

import java.util.ArrayList;
import java.util.Date;

import enums.Stockfield;
import vo.AnalysisVO;

/**
 * 统计分析，主要是股票间的对比（包括大盘）
 * 涨幅折线图，和相关系数
 * @author alice
 *
 */
public interface CompareLogicservice {
	/**
	 * 股票涨幅折线图
	 * @param name 股票名字
	 * Stockfield field 参数
	 * 默认给一个月的数据，不提供时间选择
	 * 大盘只能比涨幅，股票都能比
	 * @return
	 */
	ArrayList<AnalysisVO<Date>> changeRateLine (String name,Stockfield field);
	
	/**
	 * 计算两支股票的协方差
	 * 只能股票的
	 * 默认给一个月的
	 * @param name1
	 * @param name2
	 * @return
	 */
	String getcorrelationCoeffcient(String name1, String name2) throws Exception;
	/**
	 * 计算两支股票的置信区间
	 * 默认给一个月的
	 * @param name1
	 * @param name2
	 * @return
	 * @throws Exception 
	 */
	String confiInterval(String name1,String name2,Stockfield field) throws Exception;
	
	/**
	 * 计算一支股票和大盘均值差的置信区间
	 * 默认给一个月的
	 * 只有涨幅有意义
	 * @param name1 股票名
	 * @return
	 * @throws Exception 
	 */
	String confiIntervalBench(String name1) throws Exception;
	/**
	 * 计算行情和大盘均值差的置信区间
	 * 默认给一个月的
	 * 只有涨幅有意义
	 * @param name1 股票名
	 * @return
	 * @throws Exception 
	 */
	String confiIntervalHang(Date start,Date end) throws Exception;
}
