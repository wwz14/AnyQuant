package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PolyFitVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3912850513230563396L;
	/**
	 * 日期（横坐标）
	 */
	public ArrayList<Date> date;
	/**
	 * 真实值是散点图
	 */
	public ArrayList<Double> act;
	/**
	 * 预测值是连起来的折线图
	 */
	public ArrayList<Double> curveX;
	public ArrayList<Double> pre;
	public PolyFitVO(ArrayList<Date> date, ArrayList<Double> act, ArrayList<Double> pre) {
		super();
		this.date = date;
		this.act = act;
		this.pre = pre;
	}
	public PolyFitVO(ArrayList<Date> date, ArrayList<Double> act,
			ArrayList<Double> curveX, ArrayList<Double> pre) {
		super();
		this.date = date;
		this.act = act;
		this.curveX = curveX;
		this.pre = pre;
	}
	
	
	
//	public DataItem actItem() {
//		return new DataItem("真实值", act);
//	}
//	
//	public DataItem preItem() {
//		return new DataItem("预测值", pre);
//	}

}
