package presentation.spider;

import java.io.Serializable;

public class SecurityRisk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9211170236809861107L;
	String closePrice;// : "14.320",
	String frcEndDate;// : "2016-05-11",
	String frcForecastDate;// : "2016-05-10",
	String frcGainProb;// : "0.585725",
	String position;// : "0.4",
	String frcVolatility;// : "0.045593",
	String highPrice;// : "14.973",
	String lowPrice;// : "13.667",
	String riskMeasure;// : "12.57"

	@Override
	public String toString() {
		return "SecurityRisk [closePrice=" + closePrice + ", frcEndDate=" + frcEndDate + ", frcForecastDate="
				+ frcForecastDate + ", frcGainProb=" + frcGainProb + ", position=" + position + ", frcVolatility="
				+ frcVolatility + ", highPrice=" + highPrice + ", lowPrice=" + lowPrice + ", riskMeasure=" + riskMeasure
				+ "]";
	}

	public String getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	public String getFrcEndDate() {
		return frcEndDate;
	}

	public void setFrcEndDate(String frcEndDate) {
		this.frcEndDate = frcEndDate;
	}

	public String getFrcForecastDate() {
		return frcForecastDate;
	}

	public void setFrcForecastDate(String frcForecastDate) {
		this.frcForecastDate = frcForecastDate;
	}

	public String getFrcGainProb() {
		return frcGainProb;
	}

	public void setFrcGainProb(String frcGainProb) {
		this.frcGainProb = frcGainProb;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFrcVolatility() {
		return frcVolatility;
	}

	public void setFrcVolatility(String frcVolatility) {
		this.frcVolatility = frcVolatility;
	}

	public String getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getRiskMeasure() {
		return riskMeasure;
	}

	public void setRiskMeasure(String riskMeasure) {
		this.riskMeasure = riskMeasure;
	}
	
	

}
