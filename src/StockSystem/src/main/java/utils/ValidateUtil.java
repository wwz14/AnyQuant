package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import logic.stockShowInfobl.StockListLogic;
import logicservice.showInfoblservice.StockListLogicservice;
import vo.NStockVO;

public class ValidateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static StockListLogicservice stockListLogicservice = new StockListLogic();

	public static boolean isNumber(String string) {

		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDatetime(String string) {
		try {
			dateFormat.parse(string);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public static boolean isStockCode(String string) {
		ArrayList<NStockVO> nStockVOs = stockListLogicservice.click(string);
		if (nStockVOs.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检测Date start ，Date end 是否合理，start至少比end提早一天,end早于等于今天
	 * start和end必须是经过DateTool.getDateByString后的
	 */
	public static boolean isDateRangeValidated(Date start, Date end) {
		Date now = DateTool.getDateByString(DateTool.getStringByDate(new Date()));
		if (now.before(end)) {
			return false;
		}

		if (start.before(end)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(ValidateUtil.isStockCode("sh600036"));
		System.out.println(ValidateUtil.isStockCode("sz"));
	}

}
