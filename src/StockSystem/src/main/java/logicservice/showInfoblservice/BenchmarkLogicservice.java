/**
 * 大盘
 *
 */
package logicservice.showInfoblservice;

import java.util.ArrayList;
import java.util.Date;

import enums.KLineType;
import enums.SortType;
import exception.StatusNotOKException;
import utils.Filter;
import vo.KLineVO;
import vo.MAVO;
import vo.NBenchMarkVO;

public interface BenchmarkLogicservice {
	/**
	 * 返回一开始显示在大盘界面上
	 * 
	 * @param name：大盘名称，现在只有hs300
	 * @return ArrayList<NBenchMarkVO> 该大盘所有时间数据
	 */
	ArrayList<NBenchMarkVO> getByName(String name);

	/**
	 * 根据筛选项返回
	 * 
	 * @param filter
	 *            筛选项
	 * @return ArrayList<NBenchMarkVO>>某些时间的某些股票
	 */
	ArrayList<NBenchMarkVO> getResultList(ArrayList<NBenchMarkVO> vo, Filter filter);

	/**
	 * 排序 需要考虑之前界面上的数据是什么 可能是某些时间的某些股票 所以界面传vo来 还要考虑根据哪个字段进行排序
	 * 
	 * @param ArrayList<NBenchMarkVO>
	 *            vo 要排序的大盘
	 * @param sortType排序方法
	 * @return ArrayList<NBenchMarkVO> 排号序的大盘
	 */
	ArrayList<NBenchMarkVO> sort(ArrayList<NBenchMarkVO> vo, SortType sortType);

	/**
	 * @Description: 传入开始日期、结束日期、K线图类型，获取KLineVo类的ArrayList
	 * @param @param start 开始时间
	 * @param @param end  结束时间
	 * @param @param kLineType K线图类型
	 * @param @return    设定文件    
	 * @return ArrayList<KLineVO>    KLineVO类的ArrayList
	 * @throws StatusNotOKException 
	 */
	ArrayList<KLineVO> getKLineVOs(KLineType kLineType,Date start, Date end) throws StatusNotOKException;
	
	/**
	 * @Description: 传入开始日期、结束日期，获取MAVO类的ArrayList
	 * @param @param start 开始时间
	 * @param @param end 结束时间
	 * @param @return    设定文件    
	 * @return ArrayList<MAVO>    返回类型   MAVO类的ArrayList
	 * @throws StatusNotOKException 
	 */
	ArrayList<MAVO> getMAVOs(Date start, Date end) throws StatusNotOKException;
	/**
	 * 获得指定时间段某大盘的数据信息
	 * @param name 大盘名称，现在只有hs300
	 * @param startTime 起始时间
	 * @param endTime 最终时间
	 * start，end不能为null
	 * @return 该大盘指定时间段数据
	 * @throws StatusNotOKException
	 */
	ArrayList<NBenchMarkVO> getByTime(String name, Date startTime, Date endTime) throws StatusNotOKException;

	
}
