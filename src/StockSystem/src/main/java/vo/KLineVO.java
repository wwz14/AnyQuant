package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @Description:K线图vo类
 * 
 * @version V1.0
 */
public class KLineVO extends GraphDataVO<Date>implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2643704057334485480L;
	private Date date;
	private BigDecimal Open;
	private BigDecimal Close;
	private BigDecimal High;
	private BigDecimal Low;
	//成交量图使用，可选择成交手数或金额
	private BigDecimal volumn;
	/**
	 * 成交金额=数量乘价格，价格是adj_price
	 */
	private BigDecimal dealPrice;
	public KLineVO(BigDecimal open, BigDecimal close, BigDecimal high, BigDecimal low, BigDecimal volumn,
			BigDecimal dealPrice,Date date) {
		super();
		Open = open;
		Close = close;
		High = high;
		Low = low;
		this.volumn = volumn;
		this.dealPrice = dealPrice;
        this.date = date;
	}
	
	//用于测试的构造方法，以后会删除
	@Deprecated
	public KLineVO(double d, double e, double f, double g, int i, int j, Date dateByString) {
		Open = new BigDecimal(d);
		High = new BigDecimal(e);
		Low = new BigDecimal(f);
		Close = new BigDecimal(g);
		this.volumn = new BigDecimal(i);
		this.dealPrice = new BigDecimal(j);
		this.date = dateByString;
	}

	public BigDecimal getOpen() {
		return Open;
	}
	public void setOpen(BigDecimal open) {
		Open = open;
	}
	public BigDecimal getClose() {
		return Close;
	}
	public void setClose(BigDecimal close) {
		Close = close;
	}
	public BigDecimal getHigh() {
		return High;
	}
	public void setHigh(BigDecimal high) {
		High = high;
	}
	public BigDecimal getLow() {
		return Low;
	}
	public void setLow(BigDecimal low) {
		Low = low;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public BigDecimal getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
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
		dataItems.add(new DataItem("开盘价", this.Open.doubleValue()));
		dataItems.add(new DataItem("收盘价", this.Close.doubleValue()));
		dataItems.add(new DataItem("最高价", this.High.doubleValue()));
		dataItems.add(new DataItem("最低价", this.Low.doubleValue()));
		dataItems.add(new DataItem("成交量", this.volumn.doubleValue()));
		dataItems.add(new DataItem("成交额", this.dealPrice.doubleValue()));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getVar();
	}

}
