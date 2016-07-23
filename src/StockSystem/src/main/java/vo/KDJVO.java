/*
 * KDJ指标（随机指标）
 * */
package vo;
import java.io.Serializable;
import java.util.ArrayList;
/*
 * KDJ的计算比较复杂，首先要计算周期（n日、n周等）的RSV值，即未成熟随机指标值，然后再计算K值、D值、J值等。以n日KDJ数值的计算为例，其计算公式为
n日RSV=（Cn－Ln）/（Hn－Ln）×100
公式中，Cn为第n日收盘价；Ln为n日内的最低价；Hn为n日内的最高价。
其次，计算K值与D值：
当日K值=2/3×前一日K值+1/3×当日RSV
当日D值=2/3×前一日D值+1/3×当日K值
若无前一日K 值与D值，则可分别用50来代替。
J值=3*当日K值-2*当日D值
以9日为周期的KD线为例，即未成熟随机值，计算公式为
9日RSV=（C－L9）÷（H9－L9）×100
公式中，C为第9日的收盘价；L9为9日内的最低价；H9为9日内的最高价。
K值=2/3×第8日K值+1/3×第9日RSV
D值=2/3×第8日D值+1/3×第9日K值
J值=3*第9日K值-2*第9日D值
若无前一日K值与D值，则可以分别用50代替。
 * */
import java.util.Date;
import java.util.Iterator;

public class KDJVO extends GraphDataVO<Date>implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4546169556090800113L;
	/*
	 * 白线
	 * K值
	 * */
	private double K;
	/*
	 * 黄线
	 * D值
	 * */
	private double D;
	/*
	 * 紫线
	 * J值
	 * */
	private double J;
	private Date date;
	public KDJVO(double k, double d, double j,Date date) {
		super();
		K = k;
		D = d;
		J = j;
		this.date=date;
	}
	public double getK() {
		return K;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setK(double k) {
		K = k;
	}
	public double getD() {
		return D;
	}
	public void setD(double d) {
		D = d;
	}
	public double getJ() {
		return J;
	}
	public void setJ(double j) {
		J = j;
	}
	@Override
	public Iterator<DataItem> getDataItemIterator() {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		dataItems.add(new DataItem("K", this.K));
		dataItems.add(new DataItem("D", this.D));
		dataItems.add(new DataItem("J", this.J));
		return dataItems.iterator();
	}
	@Override
	public Date getVar() {
		return getDate();
	}
	
}
