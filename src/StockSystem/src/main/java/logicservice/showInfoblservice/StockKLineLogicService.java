//单个股票的k线图所需的数据
//包括k线,ma,成交量,mavo,各项指标
package logicservice.showInfoblservice;

import java.util.ArrayList;
import java.util.Date;

import enums.KLineType;
import exception.StatusNotOKException;
import vo.BollVO;
import vo.KDJVO;
import vo.KLineVO;
import vo.MAVO;
import vo.StockStatisticVO;

public interface StockKLineLogicService {
	/**
	 * @Description: 得到k线盒方和成交量图需要的数据
	      如果是周k，则传入的时间为这周的第一个交易日   
	       如果是月k，则传入的时间为这月的第一个交易日
	 * @param name 股票名称
	 * @param start 开始时间
	 * @param end  结束时间
	 * @param kLineType K线图类型
	 * @return ArrayList<KLineVO>  
	 * @throws StatusNotOKException 
	 */
	ArrayList<KLineVO> getKLineVOs(String name,KLineType kLineType,Date start, Date end) throws StatusNotOKException;
	/**
	 * @Description: 得到ma图需要的数据
	 * @param name 股票名称
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return ArrayList<MAVO>   
	 * @throws StatusNotOKException 
	 */
	ArrayList<MAVO> getMAVOs(String name,Date start, Date end) throws StatusNotOKException;
	/**
	 * @Description: 得到boll图需要的数据
	 * @param name 股票名称
	 * @param start 开始时间
	 * @param end 结束时间
	 * @throws StatusNotOKException 
	 */
	ArrayList<BollVO> getBoll(String name,Date start, Date end) throws StatusNotOKException;
	/**
	 * 
	 * 9日RSV=（C－L9）÷（H9－L9）×100
	 *公式中，C为第9日的收盘价；L9为9日内的最低价；H9为9日内的最高价。
	 *K值=2/3×第8日K值+1/3×第9日RSV
	 *D值=2/3×第8日D值+1/3×第9日K值
	 *J值=3*第9日K值-2*第9日D值
	 *(若无前一日K值与D值，则可以分别用50代替。
	 *要么取到所有数据一次算好,要么每次改变starttime,数据都会变,因为第一天的数据用
	 *50代替,但是目前木有办法取道所有数据,因为默认返回一个月的数据
	 * 
	 * @Description: 得到kdj图需要的数据,周期为9
	 * @param name 股票名称
	 * @param start 开始时间
	 * @param end 结束时间
	 * @throws StatusNotOKException 
	 */
	ArrayList<KDJVO> getKDJ(String name,Date start, Date end) throws StatusNotOKException;

	/**
	 * @Description: 获得一段时间内的股票统计数据
	 * @param @param
	 *            start 开始时间
	 * @param @param
	 *            end
	 * @param @return
	 *            设定文件
	 * @return StockStatisticVO 返回类型
	 * @throws StatusNotOKException
	 */
	ArrayList<StockStatisticVO> getStockATR(String stockCode, Date start, Date end) throws StatusNotOKException;
}
