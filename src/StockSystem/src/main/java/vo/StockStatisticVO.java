package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class StockStatisticVO extends GraphDataVO<Date> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8304947532884930212L;

	/**
	 * 均幅指数ATR
	 * 真是波幅TR=max{|今最高价-今最低价|,|昨收盘-今最高价|,|昨收盘-今最低价|}
	 * ATR平均的atr，N一般为14
	 * 
	 */
	
	private BigDecimal ATR;
	
	private Date date;

	public BigDecimal getATR() {
		return ATR;
	}

	public void setATR(BigDecimal aTR) {
		ATR = aTR;
	}

	public StockStatisticVO(BigDecimal aTR, Date date) {
		super();
		ATR = aTR;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("ATR", this.ATR.doubleValue()));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getDate();
	}
	
	
	
}
