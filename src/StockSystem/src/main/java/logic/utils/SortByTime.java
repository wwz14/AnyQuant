package logic.utils;

import java.util.ArrayList;
import java.util.Date;

import vo.SortVO;

public class SortByTime {
	/**
	 * 按时间排序降序
	 * @param sortList
	 * @return
	 */
	public static ArrayList<SortVO> sortByTime(ArrayList<SortVO> sortList) {
		int j;
		for(int p = 1;p<sortList.size();p++) {
			SortVO temp = sortList.get(p);
			for(j = p;j>0&&before(temp.getDate(),sortList.get(j-1).getDate());j--){
				sortList.set(j, sortList.get(j-1));
				
			}
				
			sortList.set(j, temp);
		}
		return sortList;
	}
	
	/**
	 * 按时间排序升序
	 * @param sortList
	 * @return
	 */
	public static ArrayList<SortVO> sortByTimeup(ArrayList<SortVO> sortList) {
		int j;
		for(int p = 1;p<sortList.size();p++) {
			SortVO temp = sortList.get(p);
			for(j = p;j>0&&(!(before(temp.getDate(),sortList.get(j-1).getDate())));j--)
				sortList.set(j, sortList.get(j-1));
			sortList.set(j, temp);
		}
		return sortList;
	}
	
	//如果第一个日期在第二个日期之前则返回true
	public static boolean before(Date date1,Date date2) {
		if(date1.getTime()<date2.getTime())
			return false;
		else 
			return true;
	}
	

}
