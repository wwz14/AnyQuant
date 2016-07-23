package logic.utils;

import java.util.ArrayList;

import vo.NBenchMarkVO;
import vo.SortVO;

public class CreatBenchMarkSort {
	private static ArrayList<SortVO> stockSortList = new ArrayList<SortVO>();
	/**
	 * 开盘价作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByOpen(ArrayList<NBenchMarkVO> vo) {	
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
	public static ArrayList<SortVO> sortByClose(ArrayList<NBenchMarkVO> vo) {

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
	public static ArrayList<SortVO> sortByHigh(ArrayList<NBenchMarkVO> vo) {
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
	public static ArrayList<SortVO> sortByLow(ArrayList<NBenchMarkVO> vo) {
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
	public static ArrayList<SortVO> sortByAdjPrice(ArrayList<NBenchMarkVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getAdj_price()));
		}
		return stockSortList;
	}

	/**
	 * 成交量作为排序依据
	 * @param vo
	 * @return
	 */
	public static ArrayList<SortVO> sortByVolume(ArrayList<NBenchMarkVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getVolume()));
		}
		return stockSortList;
	}
	
	public static ArrayList<SortVO> sortByDate(ArrayList<NBenchMarkVO> vo) {
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
	public static ArrayList<SortVO> sortByChangeRate(ArrayList<NBenchMarkVO> vo) {
		stockSortList.clear();
		for(int i = 0;i<vo.size();i++) {
			stockSortList.add(new SortVO(vo.get(i),vo.get(i).getChangeRate()));
		}
		return stockSortList;
	}

}
