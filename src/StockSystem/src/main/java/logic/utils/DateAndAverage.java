package logic.utils;

import java.math.BigDecimal;
import java.util.Date;
/**
 * average 为本日期，所有股票收盘价的加权平均数，加权的因素是成交金额
 * @author alice
 *
 */
public class DateAndAverage {
	
	private Date date;
	private BigDecimal averaage;
	
	public DateAndAverage(Date date, BigDecimal averaage) {
		super();
		this.date = date;
		this.averaage = averaage;
	}

	public Date getDate() {
		return date;
	}

	public BigDecimal getAveraage() {
		return averaage;
	}
	
}
