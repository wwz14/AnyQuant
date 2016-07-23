package presentation.spider;

import java.io.Serializable;

/**
 * 强势股
 *
 */
public class StrongStockItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5041382614632890202L;
	String symbol; //代码
	String name;//名称	
	String changes; //月涨跌幅
	String open;//月开盘价 
	String close;//月收盘价
	String high;//月最高价
	String low;//月最低价
	String volume;//月成交量/股
	String amount;//月成交金额
	String turnover;//换手率
	String index_changes;//沪深300涨幅
	String day;//时间
	
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
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getIndex_changes() {
		return index_changes;
	}
	public void setIndex_changes(String index_changes) {
		this.index_changes = index_changes;
	}
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}

}
