/**
 * 股票列表表格所需
 *
 */
package logicservice.showInfoblservice;

import java.util.ArrayList;

import enums.SortType;
import exception.StatusNotOKException;
import utils.Filter;
import vo.NStockVO;

public interface StockListLogicservice {
	/**
	 * 返回一开始显示在股票列表界面上 助教写得说明上是事先选取几只感兴趣的股票展示
	 * 
	 * @param year:
	 *            年份
	 * @param exchange：交易所，目前可以为
	 *            sz或sh，代表深交所和上交所
	 * @return ArrayList<NStockVO> 所有股票一个时刻的信息
	 */
	ArrayList<NStockVO> getResultListAll(String year, String exchange);


	/**
	 * 根据股票代码搜索
	 * 
	 * @param searchstr:
	 *            股票代码
	 * @return ArrayList<NStockVO> 那些股票一天时间的信息
	 */
	ArrayList<NStockVO> search(String searchstr) throws StatusNotOKException;

	/**
	 * 股票列表 没有时间筛选
	 * 
	 * @param filter
	 *            筛选项
	 * @param vo
	 *            界面传回的vo
	 * @return ArrayList<NStockVO>一天的某些股票
	 */
	ArrayList<NStockVO> getResultList(ArrayList<NStockVO> vo, Filter filter);

	/**
	 * 根据筛选项返回 具体股票 多一个时间的筛选 filter里面有三tarttime和endtime
	 * 
	 * @param filter
	 *            筛选项
	 * @param vo
	 *            界面传回的vo
	 * @param startdate
	 *            开始时间
	 * @param enddate
	 *            结束时间
	 * @return ArrayList<NStockVO>一只股票的某些信息
	 */
	ArrayList<NStockVO> getResultDetail(ArrayList<NStockVO> vo, Filter filter);

	/**
	 * 排序 需要考虑之前界面上的数据是什么 可能是某些时间的某些股票 所以界面传vo来 还要考虑根据哪个字段进行排序
	 * 
	 * @param ArrayList<NStockVO>
	 *            vo 要排序的股票
	 * @param sortType排序方法
	 * @return ArrayList<NStockVO> 排号序的股票
	 */
	ArrayList<NStockVO> sortStockList(ArrayList<NStockVO> vo, SortType sortType);

	/**
	 * 点击某只股票显示
	 * 
	 * @param name股票代码
	 * @return ArrayList<NStockVO> 该股票历史信息
	 */
	ArrayList<NStockVO> click(String name);

	

}
