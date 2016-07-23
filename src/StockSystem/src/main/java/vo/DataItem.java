package vo;

import java.io.Serializable;

public class DataItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 846434039829471859L;

	private String name;
	
	private double data;
	
	private double lowerBound = 0;
	
	private double upperBound = 100;

	public DataItem(String name, double data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public DataItem setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
		return this;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public DataItem setUpperBound(double upperBound) {
		this.upperBound = upperBound;
		return this;
	}
	
	
}
