package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RadarDataVO extends GraphDataVO<String>implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823189454634170398L;
	/**
	 * 市净率
	 */
	private double pb;
	/**
	 * 成交量
	 */
	private int vol;
	/**
	 * 风险指数
	 */
	private double riskIndex;
	/**
	 * 上涨概率
	 */
	private double risingProbability;
	/**
	 * 1个月涨幅平均数
	 */
	private double averageGains;

	public static double maxpb = 0;
	public static int maxvol = 0;
	public static double maxriskIndex = 0;
	public static double maxrisingProbability = 0;
	public static double maxaverageGains = 0;

	private String name;

	public RadarDataVO(double pb, int vol, double riskIndex, double risingProbability, double averageGains,
			String name) {
		super();
		this.pb = pb;
		this.vol = vol;
		this.riskIndex = riskIndex;
		this.risingProbability = risingProbability;
		this.averageGains = averageGains;
		this.name = name;

		maxpb = max(maxpb, pb);
		maxvol = max(maxvol, vol);
		maxriskIndex = max(maxriskIndex, riskIndex);
		maxrisingProbability = max(maxrisingProbability, risingProbability);
		maxaverageGains = max(maxaverageGains, averageGains);
	}

	public double getPb() {
		return pb;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public int getVol() {
		return vol;
	}

	public void setVol(int vol) {
		this.vol = vol;
	}

	public double getRiskIndex() {
		return riskIndex;
	}

	public void setRiskIndex(double riskIndex) {
		this.riskIndex = riskIndex;
	}

	public double getRisingProbability() {
		return risingProbability;
	}

	public void setRisingProbability(double risingProbability) {
		this.risingProbability = risingProbability;
	}

	public double getAverageGains() {
		return averageGains;
	}

	public void setAverageGains(double averageGains) {
		this.averageGains = averageGains;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		List<DataItem> dataItems = new ArrayList<>();

		if (maxpb == pb) {
			dataItems.add(new DataItem("市净率(%)", pb * 100).setUpperBound(pb * getRandom()));
		} else {
			dataItems.add(new DataItem("市净率(%)", pb * 100).setUpperBound(maxpb * getRandom()));
		}

		if (maxvol == vol) {
			dataItems.add(new DataItem("成交量(×10^6)", vol/Math.pow(10, 6)).setUpperBound(vol * getRandom()));
		} else {
			dataItems.add(new DataItem("成交量(×10^6)", vol/Math.pow(10, 6)).setUpperBound(maxvol * getRandom()));
		}

		if (maxriskIndex == riskIndex) {
			dataItems.add(new DataItem("风险指数", riskIndex).setUpperBound(riskIndex * getRandom()));
		} else {
			dataItems.add(new DataItem("风险指数", riskIndex).setUpperBound(maxriskIndex * getRandom()));
		}
		if(maxrisingProbability==risingProbability){
				dataItems.add(new DataItem("上涨概率(%)", risingProbability * 100).setUpperBound(risingProbability* getRandom()));
		}else {
			dataItems.add(new DataItem("上涨概率(%)", risingProbability * 100).setUpperBound(maxrisingProbability* getRandom()));
		}
		
		if(maxaverageGains ==averageGains){
			dataItems.add(new DataItem("平均涨跌幅(‰)", averageGains * 1000).setUpperBound(averageGains * getRandom()));
		}else{
			dataItems.add(new DataItem("平均涨跌幅(‰)", averageGains * 1000).setUpperBound(maxaverageGains * getRandom()));
		}
		

		return dataItems.iterator();
	}

	/**
	 * @return random double 1.0~1.9
	 */
	private static double getRandom() {
		Random random = new Random();
		return random.nextInt(10) / 10.0 + 1;
	}

	@Override
	public String getVar() {
		return "股票"+name ;
	}

	@Override
	public String toString() {
		return "RadarDataVO [pb=" + pb + ", vol=" + vol + ", riskIndex=" + riskIndex + ", risingProbability="
				+ risingProbability + ", averageGains=" + averageGains + ", name=" + name + "]";
	}

	private double max(double d1, double d2) {
		if (d2 > d1) {
			return d2;
		}
		return d1;
	}

	private int max(int d1, int d2) {
		if (d2 > d1) {
			return d2;
		}
		return d1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
