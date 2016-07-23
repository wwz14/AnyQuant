package vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3480141550283378046L;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;

	private BigDecimal close;
	private BigDecimal adj_price;

	private BigDecimal volume;


	private BigDecimal turnover;
	private BigDecimal pe_ttm;
	private BigDecimal pb;
	private String date;
	//与前一日的差价
	public double change;
	public StockVO(BigDecimal open, BigDecimal high, BigDecimal low,
			BigDecimal close, BigDecimal adj_price, BigDecimal volume,
			BigDecimal turnover, BigDecimal pe_ttm, BigDecimal pb, String date) {
		super();
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adj_price = adj_price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe_ttm = pe_ttm;
		this.pb = pb;
		this.date = date;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
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
	public BigDecimal getPb() {
		return pb;
	}
	public void setPb(BigDecimal pb) {
		this.pb = pb;
	}
	public BigDecimal getPe_ttm() {
		return pe_ttm;
	}
	public void setPe_ttm(BigDecimal pe_ttm) {
		this.pe_ttm = pe_ttm;
	}
	public BigDecimal getTurnover() {
		return turnover;
	}
	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}
}
