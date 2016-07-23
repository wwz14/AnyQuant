package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FatherVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8162413537033229864L;
	/**
	 * 大盘名称，目前只有hs300
	 * */
	private String name;
	/**
	 *   成交量
	 * */
	private BigDecimal volume;
	/**
	 *   最高价
	 * */
	private BigDecimal high;
	/**
	 *   后复权价
	 * */
	private BigDecimal adj_price;
	/**
	 *   最低价
	 * */
	private BigDecimal low;	
	/**
	 *   时间
	 * */
	private Date date;
	/**
	 *   收盘价
	 * */
	private BigDecimal close;
	/**
	 *   开盘价
	 * */
	private BigDecimal open;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getAdj_price() {
		return adj_price;
	}
	public void setAdj_price(BigDecimal adj_price) {
		this.adj_price = adj_price;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}


}
