package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
* @Description: 行情VO

* @version V1.0  
 */
public class MarketVO extends GraphDataVO<Date>implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2777856142745751619L;
	private Date date;
	/**
	 * 行情涨跌幅，基于收盘价
	 */
	private BigDecimal marketChangeRate;
	/**
	 * 大盘涨跌幅，基于收盘价
	 */
	private BigDecimal benchmarkChangeRate;
	
	private boolean isInTime;
	
	
	public boolean isInTime() {
		return isInTime;
	}

	public void setInTime(boolean isInTime) {
		this.isInTime = isInTime;
	}

	public MarketVO(Date date, BigDecimal marketChangeRate,
			BigDecimal benchmarkChangeRate) {
		super();
		this.date = date;
		this.marketChangeRate = marketChangeRate;
		this.benchmarkChangeRate = benchmarkChangeRate;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getMarketChangeRate() {
		return marketChangeRate;
	}
	public void setMarketChangeRate(BigDecimal marketChangeRate) {
		this.marketChangeRate = marketChangeRate;
	}
	public BigDecimal getBenchmarkChangeRate() {
		return benchmarkChangeRate;
	}
	public void setBenchmarkChangeRate(BigDecimal benchmarkChangeRate) {
		this.benchmarkChangeRate = benchmarkChangeRate;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("行情涨跌幅", this.marketChangeRate.doubleValue()));
		dataItems.add(new DataItem("大盘涨跌幅", this.benchmarkChangeRate.doubleValue()));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getDate();
	}
	
	
	
}
