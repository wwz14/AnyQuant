package vo;

import java.io.Serializable;

public class MResult implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910258888776558873L;
	/**
	 * 最小
	 */
	public double MinException;
	/**
	 * 最大
	 */
	public double MaxException;
    /**
     * 中间期望
     */
	public double MidException;
    /**
     * 状态区间
     */
	public String interval;
	/**
	 * 
	 */
	public double FirstChance;
	/**
	 * 
	 */
	public double SecondChance;
	/**
	 * 
	 */
	public double ThirdChance;
	/**
	 * 
	 */
	public double ForthChance;
	
	public MResult(double minException, double maxException, double midException, String interval, double firstChance,
			double secondChance, double thirdChance, double forthChance) {
		super();
		MinException = minException;
		MaxException = maxException;
		MidException = midException;
		this.interval = interval;
		FirstChance = firstChance;
		SecondChance = secondChance;
		ThirdChance = thirdChance;
		ForthChance = forthChance;
	}

}
