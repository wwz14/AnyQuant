package vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
/**
 * MACD指标是由两线一柱组合起来形成，快速线为DIF，慢速线为DEA，柱状图为MACD，
 * @author alice
 *
 */
public class MacdVO extends GraphDataVO<Date>implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4586480661070320996L;
	//第一日为ema收盘价
	/**
	 * 时间周期为12的ema指数
	 */
	private double ema12;
	/**
	 * 时间周期为26的ema指数
	 */
	private double ema26;
	/**
	 * 差离值 ，为 DIF = EMA12 - EMA26 第一日为0
	 */
	private double dif;
	/**
	 * 离差平均值 今日DEA = （前一日DEA X 8/10 + 今日DIF X 2/10），第一日为0
	 */
	private double dea;
	/**
	 * 
	 */
	private double macd;
	/**
	 * 
	 * @param ema12
	 * @param ema26
	 * @param dea
	 */
	private Date date;
	
	public MacdVO(Date date,double ema12, double ema26, double dea) {
		super();
		this.date = date;
		this.ema12 = ema12;
		this.ema26 = ema26;
		this.dif = ema12 - ema26;
		this.dea = dea;
		this.macd = (dif-dea)*2;

	}

	@Deprecated
	public MacdVO(double macd, double dif, double dea, Date dateByString) {
		this.macd = macd;
		this.dea = dea;
		this.dif = dif;
		this.date = dateByString;
	}

	public double getEma12() {
		return ema12;
	}

	public double getEma26() {
		return ema26;
	}

	public double getDif() {
		DecimalFormat    df   = new DecimalFormat("######0.0000"); 
	    dif = Double.parseDouble(df.format(this.dif));
		return dif;
	}

	public double getDea() {
		DecimalFormat    df   = new DecimalFormat("######0.0000"); 
		dea = Double.parseDouble(df.format(this.dea));
		return dea;
	}

	public double getMacd() {
		DecimalFormat    df   = new DecimalFormat("######0.0000"); 
		macd = Double.parseDouble(df.format(this.macd));
		return macd;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("DIF", this.dif));
		dataItems.add(new DataItem("DEA", this.dea));
		dataItems.add(new DataItem("MACD", this.macd));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getDate();
	}
}
