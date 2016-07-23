/*
 * 布林线指标
 * */
package vo;
import java.io.Serializable;
import java.util.ArrayList;
/*
 * （1）计算MA
MA=N日内的收盘价之和÷N
（2）计算标准差MD
MD=平方根N日的（C－MA）的两次方之和除以N;c收盘价
（3）计算MB、UP、DN线
MB=（N－1）日的MA
UP=MB+k×MD
DN=MB－k×MD
（K为参数，可根据股票的特性来做相应的调整，一般默认为2）
N一般为20
 * */
import java.util.Date;
import java.util.Iterator;

public class BollVO extends GraphDataVO<Date>implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3399712498505403558L;
	/*
	 * 白线
	 * 中轨线=N日的移动平均线
	 * */
	private double mb;
	/*
	 * 黄线
	 * 上轨线=中轨线+两倍的标准差
	 * */
	private double up;
	/*
	 * 紫线
	 * 下轨线下轨线=中轨线－两倍的标准差
	 * */
	private double dn;
	/*
	 * 日期
	 * */
	Date date;
	public BollVO(double mb, double up, double dn, Date date) {
		super();
		this.mb = mb;
		this.up = up;
		this.dn = dn;
		this.date = date;
	}
	public double getMb() {
		return mb;
	}
	public void setMb(double mb) {
		this.mb = mb;
	}
	public double getUp() {
		return up;
	}
	public void setUp(double up) {
		this.up = up;
	}
	public double getDn() {
		return dn;
	}
	public void setDn(double dn) {
		this.dn = dn;
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
		dataItems.add(new DataItem("MB", this.mb));
		dataItems.add(new DataItem("UP", this.up));
		dataItems.add(new DataItem("DN", this.dn));
		return dataItems.iterator();
	}
	@Override
	public Date getVar() {
		return getDate();
	}

	
	
}
