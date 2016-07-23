package enums;

import java.util.HashMap;
import java.util.Map;

public enum SortType {
	/**
	 * 开盘价
	 */
	openAscend("开盘价升序"),

	openDown("开盘价降序"), /**
						 * 最高价
						 */
	highAscend("最高价升序"),

	highDown("最高价降序"), /**
						 * 最低价
						 */
	lowAscend("最低价升序"),

	lowDown("最低价降序"), /**
						 * 收盘价
						 */
	closeAscend("收盘价升序"),

	closeDown("收盘价降序"), /**
						 * 后复权价
						 */
	adj_priceAscent("后复权价升序"),

	adj_priceDown("后复权价降序"), /**
								 * 成交量
								 */
	volumeAscent("成交量升序"),

	volumeDown("成交量降序"), /**
							 * 换手率
							 */
	turnoverAscent("换手率升序"),

	turnoverDown("换手率降序"), /**
							 * 市盈率
							 */
	peAscent("市盈率升序"),

	peDown("市盈率降序"), /**
						 * 市净率
						 */
	pbAscent("市净率升序"),

	pbDown("市净率降序"), /**
						 * 时间
						 */
	dateAscent("时间升序"),

	dateDown("时间降序"), /**
						 * 涨幅
						 */
	changeRateAscent("涨跌幅升序"),

	changeRateDown("涨跌幅降序"),

	// 用于market bar chart panel
	changeRate("涨跌幅"),

	pe("市盈率"),

	pb("市净率"),

	dealPrice("成交金额");

	private String type;

	SortType(String s) {
		type = s;
	}

	@Override
	public String toString() {
		return this.type;
	}

	private static final Map<String, SortType> stringToEnum = new HashMap<String, SortType>();

	static {
		// Initialize map from constant name to enum constant
		for (SortType sortType : values()) {
			stringToEnum.put(sortType.toString(), sortType);
		}
	}

	// Returns SortType for string, or null if string is invalid
	public static SortType fromString(String symbol) {
		return stringToEnum.get(symbol);
	}

}
