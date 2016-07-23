package presentation.spider;

import java.io.Serializable;

/**
 * 股票机构分析
 * 
 * @author xyf
 *
 */
public class StockOrgAnalysis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7439558080552705258L;
	ObjectList object;
	
	/*
	 * 
	 * String closePrice;// : "14.320",
	String frcEndDate;// : "2016-05-11",
	String frcForecastDate;// : "2016-05-10",
	String frcGainProb;// : "0.585725",
	String position;// : "0.4",
	String frcVolatility;// : "0.045593",
	String highPrice;// : "14.973",
	String lowPrice;// : "13.667",
	String riskMeasure;// : "12.57"
	 */
	
	@Override
	public String toString() {
		return object.toString();
	}



	public ObjectList getObject() {
		return object;
	}



	public void setObject(ObjectList object) {
		this.object = object;
	}
	
	public String getClosePrice() {
		return object.securityRisk.closePrice;
	}

	

	public String getFrcEndDate() {
		return object.securityRisk.frcEndDate;
	}

	

	public String getFrcForecastDate() {
		return object.securityRisk.frcForecastDate;
	}

	

	public String getFrcGainProb() {
		return object.securityRisk.frcGainProb;
	}

	

	public String getPosition() {
		return object.securityRisk.position;
	}

	

	public String getFrcVolatility() {
		return object.securityRisk.frcVolatility;
	}

	

	public String getHighPrice() {
		return object.securityRisk.highPrice;
	}

	

	public String getLowPrice() {
		return object.securityRisk.lowPrice;
	}

	

	public String getRiskMeasure() {
		return object.securityRisk.riskMeasure;
	}

	
	
	
	
}
