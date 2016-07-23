package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RSIVO extends GraphDataVO<Date>implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5459328358368578336L;
	/*
	 * 白线
	 * 6日相对强弱指标
	 * */
	private double rsi6;
	/*
	 * 黄线
	 * 12日相对强弱指标
	 * */
	private double rsi12;
	/*
	 * 紫线
	 * 24日相对强弱指标
	 * */
	private double rsi24;
	private Date date;
	public RSIVO(double k, double d, double j,Date date) {
		super();
		rsi6 = k;
		rsi12 = d;
		rsi24 = j;
		this.date=date;
	}
	
	public double getRsi6() {
		return rsi6;
	}

	public void setRsi6(double rsi6) {
		this.rsi6 = rsi6;
	}

	public double getRsi12() {
		return rsi12;
	}

	public void setRsi12(double rsi12) {
		this.rsi12 = rsi12;
	}

	public double getRsi24() {
		return rsi24;
	}

	public void setRsi24(double rsi24) {
		this.rsi24 = rsi24;
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
		dataItems.add(new DataItem("rsi6", this.rsi6));
		dataItems.add(new DataItem("rsi12", this.rsi12));
		dataItems.add(new DataItem("rsi24", this.rsi24));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getDate();
	}

	
}
