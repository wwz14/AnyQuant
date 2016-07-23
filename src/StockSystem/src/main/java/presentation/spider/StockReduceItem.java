package presentation.spider;

/**
 * 连续下跌个股
 * 
 * @author xyf
 *
 */
public class StockReduceItem {
	/**
	 * {symbol:"sh600432",name:"*ST吉恩",close:"7.2700",volume:"24680376",turnover
	 * :"0.0304",changes:"-0.0497",changes_con:"-0.2347",day_con:"9",day:
	 * "2016-05-06",flag1:"0",flag_con:"0"}
	 */

	String symbol;
	String name;

	String day_con; // 下跌天数
	String changes_con; // 阶段跌幅
	String close; // 收盘价
	String changes; // 涨跌幅
	String volume; // 成交量/股
	String turnover; // 换手率
	String day;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDay_con() {
		return day_con;
	}
	public void setDay_con(String day_con) {
		this.day_con = day_con;
	}
	public String getChanges_con() {
		return changes_con;
	}
	public void setChanges_con(String changes_con) {
		this.changes_con = changes_con;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
}
