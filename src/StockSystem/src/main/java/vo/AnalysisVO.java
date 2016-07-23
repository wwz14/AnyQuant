package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AnalysisVO<Item> extends GraphDataVO<Item> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654137239506455440L;
	private String name;
	private double statistic;
	private Date date;
	private Item item;
	private boolean isInTime;
	
	public boolean isInTime() {
		return isInTime;
	}
	public void setInTime(boolean isInTime) {
		this.isInTime = isInTime;
	}
	public String getName() {
		return name;
	}
	public double getStatistic() {
		return statistic;
	}
	public Date getDate() {
		return date;
	}
	public AnalysisVO(String name, double statistic, Date date, Item item) {
		super();
		this.name = name;
		this.statistic = statistic;
		this.date = date;
		this.item = item;
	}
	
	public Iterator<DataItem> getDataItemIterator() {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem(name, this.statistic));
		return dataItems.iterator();
	}
	@Override
	public Item getVar() {
		return item;
	}

}
