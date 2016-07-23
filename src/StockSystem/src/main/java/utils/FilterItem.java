package utils;

import java.math.BigDecimal;

import enums.Stockfield;

/**
 * @Description: (具体的筛选条目)
 * 
 * @version V1.0
 */
public class FilterItem {
	Stockfield field;
	BigDecimal start;
	BigDecimal end;

	public FilterItem(Stockfield field, BigDecimal start, BigDecimal end) {
		this.field = field;
		this.start = start;
		this.end = end;
	}

	public Stockfield getField() {
		return field;
	}

	public void setField(Stockfield field) {
		this.field = field;
	}

	public BigDecimal getStart() {
		return start;
	}

	public void setStart(BigDecimal start) {
		this.start = start;
	}

	public BigDecimal getEnd() {
		return end;
	}

	public void setEnd(BigDecimal end) {
		this.end = end;
	}

}
