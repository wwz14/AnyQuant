package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThermometerDataVO extends GraphDataVO<String> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1582207949021052390L;

	/**
	 * 预计买入价
	 */
	private double expectedBuyPrice;
	
	/**
	 * 预计卖出价
	 */
	private double expectedSellPrice;
	
	/**
	 * 现价
	 */
	private double currentPrice;
	
	/**
	 * 昨日收盘价
	 */
	private double HQZRSP;
	
	/**
	 * 股票名称
	 */
	private String name;
	
	public ThermometerDataVO(double expectedBuyPrice, double expectedSellPrice, double currentPrice, double hQZRSP, String name) {
		super();
		this.expectedBuyPrice = expectedBuyPrice;
		this.expectedSellPrice = expectedSellPrice;
		this.currentPrice = currentPrice;
		this.name = name;
		HQZRSP = hQZRSP;
	}
	
	public double getExpectedBuyPrice() {
		return expectedBuyPrice;
	}



	public void setExpectedBuyPrice(double expectedBuyPrice) {
		this.expectedBuyPrice = expectedBuyPrice;
	}



	public double getExpectedSellPrice() {
		return expectedSellPrice;
	}



	public void setExpectedSellPrice(double expectedSellPrice) {
		this.expectedSellPrice = expectedSellPrice;
	}



	public double getCurrentPrice() {
		return currentPrice;
	}



	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}



	public double getHQZRSP() {
		return HQZRSP;
	}



	public void setHQZRSP(double hQZRSP) {
		HQZRSP = hQZRSP;
	}



	@Override
	public Iterator<DataItem> getDataItemIterator() {
		List<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("预计买入价", expectedBuyPrice));
		dataItems.add(new DataItem("预计卖出价", expectedSellPrice));
		dataItems.add(new DataItem("现价", currentPrice));
		dataItems.add(new DataItem("昨日收盘价", HQZRSP));
		return dataItems.iterator();
	}

	@Override
	public String getVar() {
		return name;
	}

}
