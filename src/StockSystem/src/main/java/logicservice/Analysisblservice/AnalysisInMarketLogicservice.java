package logicservice.Analysisblservice;

import java.util.ArrayList;
import java.util.Date;

import vo.AnalysisVO;

/**
 * 行情分析数据
 * @author alice
 *
 */
public interface AnalysisInMarketLogicservice {
	/**
	 *行业均价折线图
	 * @param name 行业名
	 * @param start 开始时间
	 * @param end  结束时间
	 * @return
	 */
	ArrayList<AnalysisVO<Date>> averageMarketPrice (Date start ,Date end);
	
	/**
	 * 行业内市盈率柱状图
	 * @param marketname 行业名
	 * @return
	 */
	ArrayList<AnalysisVO<String>> shiYingLuBar ();
	
	
	/**
	 * 行业内市净率柱状图
	 * @param marketname 行业名
	 * @return
	 */
	ArrayList<AnalysisVO<String>> shiJingLuBar ();
	
	
	/**
	 * 行业内市涨幅状图
	 * @param marketname 行业名
	 * @return
	 */
	ArrayList<AnalysisVO<String>> changeRateBar ();
	

	/**
	 * 行业内成交金额状图和饼图
	 * @param marketname 行业名
	 * @return
	 */
	ArrayList<AnalysisVO<String>> dealpriceBarandPie ();
	
	/**
	 * 均价分析
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	String averageMarket( Date start,
			Date end) throws Exception;
	

	
	
		
	

}
