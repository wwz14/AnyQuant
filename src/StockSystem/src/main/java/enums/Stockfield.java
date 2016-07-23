package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 具体的某股票详细信息应该包括以下10个字段
 * 
 * */
public enum Stockfield {
	/**
     * 开盘价
     */
    open("开盘价"),
    /**
     * 最高价
     */
    high("最高价"),
    /**
     * 最低价
     */
    low("最低价"),
    /**
     * 收盘价
     */
    close("收盘价"),
    /*
     * 涨跌幅
     */
    changeString("涨跌幅"),
    /**
     * 后复权价
     */
    adj_price("后复权价"),
    /**
     * 成交量
     */
    volume("成交量"),
    /**
     * 换手率
     */
   turnover("换手率"),
   /**
    * 市盈率
    */
   pe_ttm("市盈率"),
   /**
    * 市净率
    */
   pb("市净率"),
   /**
    * 时间
    */
   date("时间"),
   /**
    * 名称
    */
   name("名称");
	
    private String type;

    Stockfield(String s) {
        type = s;
    }

    @Override
    public String toString() {
        return this.type;
    }
    
    private static final Map<String, Stockfield> stringToEnum = new HashMap<String, Stockfield>();

	static {
		// Initialize map from constant name to enum constant
		for (Stockfield stockfield : values()) {
			stringToEnum.put(stockfield.toString(), stockfield);
		}
	}

	// Returns SortType for string, or null if string is invalid
	public static Stockfield fromString(String symbol) {
		return stringToEnum.get(symbol);
	}

   
}
