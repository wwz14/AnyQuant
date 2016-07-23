package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
* @Description: MAVO类

* @version V1.0  
 */
public class MAVO extends GraphDataVO<Date> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3762539749865591599L;
	private BigDecimal MA5;
	private BigDecimal MA20;
	private BigDecimal MA30;
	private BigDecimal MA60;
	private BigDecimal MAVOL5;
	private BigDecimal MAVOL10;
	//为什么要日期
	private Date date;

	public MAVO(BigDecimal mA5, BigDecimal mA20, BigDecimal mA30, BigDecimal mA60, BigDecimal mAVOL5,
			BigDecimal mAVOL10, Date date) {
		super();
		MA5 = mA5;
		MA20 = mA20;
		MA30 = mA30;
		MA60 = mA60;
		MAVOL5 = mAVOL5;
		MAVOL10 = mAVOL10;
		this.date = date;
	}
	
	@Deprecated
	public MAVO(double x, Date date) {
		MA5 = new BigDecimal(x);
		MA20 = new BigDecimal(9);
		MA30 = new BigDecimal(7);
		MA60 = new BigDecimal(8);
		MAVOL10 = new BigDecimal(2825702);
		MAVOL5 = new BigDecimal(3225702);
		this.date = date;
	}

	public BigDecimal getMA5() {
		return MA5;
	}

	public void setMA5(BigDecimal mA5) {
		MA5 = mA5;
	}

	public BigDecimal getMA20() {
		return MA20;
	}

	public void setMA20(BigDecimal mA20) {
		MA20 = mA20;
	}

	public BigDecimal getMA30() {
		return MA30;
	}

	public void setMA30(BigDecimal mA30) {
		MA30 = mA30;
	}

	public BigDecimal getMA60() {
		return MA60;
	}

	public void setMA60(BigDecimal mA60) {
		MA60 = mA60;
	}

	public BigDecimal getMAVOL5() {
		return MAVOL5;
	}

	public void setMAVOL5(BigDecimal mAVOL5) {
		MAVOL5 = mAVOL5;
	}

	public BigDecimal getMAVOL10() {
		return MAVOL10;
	}

	public void setMAVOL10(BigDecimal mAVOL10) {
		MAVOL10 = mAVOL10;
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
		dataItems.add(new DataItem("MA5", this.MA5.doubleValue()));
		dataItems.add(new DataItem("MA20", this.MA20.doubleValue()));
		dataItems.add(new DataItem("MA30", this.MA30.doubleValue()));
		dataItems.add(new DataItem("MA60", this.MA60.doubleValue()));
		dataItems.add(new DataItem("MAVOL5", this.MAVOL5.doubleValue()));
		dataItems.add(new DataItem("MAVOL10", this.MAVOL10.doubleValue()));
		return dataItems.iterator();
	}

	@Override
	public Date getVar() {
		return getDate();
	}
	
	

}
