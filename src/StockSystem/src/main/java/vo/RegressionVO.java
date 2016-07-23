package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 回归预测结果
 * @author alice
 *
 */
public class RegressionVO extends GraphDataVO<Date> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8862315764295473114L;
	/**
	 * 日期
	 */
    private Date date;
    /**
     * 实际价格
     */
	private double actual;
	/**
	 * 预测值
	 */
	private double forecast;
	/**
	 * 误差值
	 */
	private double deviation;
	
	public RegressionVO(Date date,double actual, double forecast, double deviation) {
		super();
		this.date = date;
		this.actual = actual;
		this.forecast = forecast;
		this.deviation = deviation;
	}

	public Date getDate() {
		return date;
	}

	public double getActual() {
		return actual;
	}

	public double getForecast() {
		return forecast;
	}

	public double getDeviation() {
		return deviation;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		List<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("实际价格", actual));
		dataItems.add(new DataItem("预测值", forecast));
		dataItems.add(new DataItem("误差值", deviation));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		// TODO Auto-generated method stub
		return date;
	}
	

}
