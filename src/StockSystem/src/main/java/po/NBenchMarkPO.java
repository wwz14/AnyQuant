/**最新的,信息全，某一时刻一只大盘的全部信息，
 * 
 */
package po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NBenchMarkPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7133137214644762299L;
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
	public NBenchMarkPO(){
		super();
	}
	public NBenchMarkPO(String name, BigDecimal volume, BigDecimal high,
			BigDecimal adj_price, BigDecimal low, Date date,
			BigDecimal close, BigDecimal open) {
		super();
		this.name = name;
		this.volume = volume;
		this.high = high;
		this.adj_price = adj_price;
		this.low = low;
		this.date = date;
		this.close = close;
		this.open = open;
	}







}
