package logic.utils;

import java.util.ArrayList;

import vo.NStockVO;
import vo.SortVO;
/**
 * 为排序构造数据结构
 * @author alice
 *
 */
public class CreatSortDataStructure {
	//private static ArrayList<StockSortVO> stockSortList = new ArrayList<StockSortVO>();
	private static ArrayList<SortVO> stockSortList = new ArrayList<SortVO>();
	/**
	 * 开盘价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByOpen(ArrayList<NStockVO> vo) {	
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getOpen()));
		}
		return  stockSortList;	
	}
	/**
	 * 收盘价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByClose(ArrayList<NStockVO> vo) {

		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getClose()));
		}
		return stockSortList;
	}
	/**
	 * 以做高价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByHigh(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getHigh()));
		}
		return stockSortList;
	}
	/**
	 * 最低价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByLow(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getLow()));
		}
		return stockSortList;
	}
	/**
	 * 后复权价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByAdjPrice(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getAdj_price()));
		}
		return stockSortList;
	}
	/**
	 * 市盈率作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByPb(ArrayList<NStockVO> vo) {
		
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getPb()));
		}
		return stockSortList;
	}
	/**
	 * 市净率作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByPe(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getPe_ttm()));
		}
		return stockSortList;
	}
	/**
	 * 换手率作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByTurnOver(ArrayList<NStockVO> vo) {
	
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getTurnover()));
		}
		return stockSortList;
	}
	/**
	 * 成交量作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByVolume(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getVolume()));
		}
		return stockSortList;
	}
	
	public static ArrayList<SortVO> sortByDate(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getDate()));
		}
		return stockSortList;
	}
	
	/**
	 * 涨幅作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByChangeRate(ArrayList<NStockVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getChangeRate()));
		}
		return stockSortList;
	}

}
