package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NStockVO extends FatherVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9074672553287718546L;
	/**
	 * 股票代码
	 * */
	private String name;
	/**
	 *   时间
	 * */
	private Date date;
	/**
	 *   开盘价
	 * */
	private BigDecimal open;
	/**
	 *   最高价
	 * */
	private BigDecimal high;
	/**
	 *   最低价
	 * */
	private BigDecimal low;	
	/**
	 *   收盘价
	 * */
	private BigDecimal close;
	/**
	 *   后复权价
	 * */
	private BigDecimal adj_price;	
	/**
	 *   成交量
	 * */
	private BigDecimal volume;
	/**
	 *   换手率
	 * */
	private BigDecimal turnover;
	/**
	 *   市盈率
	 * */
	private BigDecimal pe_ttm;
	/**
	 *   市净率
	 * */
	private BigDecimal pb;
	/**
	 *  涨跌幅
	 */
	private BigDecimal changeRate;
	/**
	 * 成交金额
	 */
	private BigDecimal dealprice;
	
	private String rate;
	//与前一日差价
	public double diff;
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public BigDecimal getDealprice() {
		return dealprice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getAdj_price() {
		return adj_price;
	}
	public void setAdj_price(BigDecimal adj_price) {
		this.adj_price = adj_price;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getTurnover() {
		return turnover;
	}
	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}
	public BigDecimal getPe_ttm() {
		return pe_ttm;
	}
	public void setPe_ttm(BigDecimal pe_ttm) {
		this.pe_ttm = pe_ttm;
	}
	public BigDecimal getPb() {
		return pb;
	}
	public void setPb(BigDecimal pb) {
		this.pb = pb;
	}
	public NStockVO(String name, Date date, BigDecimal open, BigDecimal high,
			BigDecimal low, BigDecimal close, BigDecimal adj_price,
			BigDecimal volume, BigDecimal turnover, BigDecimal pe_ttm,
			BigDecimal pb,BigDecimal changeRate) {
		super();
		this.name = name;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adj_price = adj_price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe_ttm = pe_ttm;
		this.pb = pb;
		this.changeRate=changeRate;
		this.dealprice = volume.multiply(adj_price).setScale(4, BigDecimal.ROUND_HALF_UP);
		
				
	}
	public NStockVO(String name, Date date, BigDecimal open, BigDecimal high,
			BigDecimal low, BigDecimal close, BigDecimal adj_price,
			BigDecimal volume, BigDecimal turnover, BigDecimal pe_ttm,
			BigDecimal pb) {
		super();
		this.name = name;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adj_price = adj_price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe_ttm = pe_ttm;
		this.pb = pb;
		this.dealprice = volume.multiply(adj_price).setScale(4, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

}
