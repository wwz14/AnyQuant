package vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SortVO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -6943906284992498719L;
private NStockVO stockVO;
private NBenchMarkVO benchMarkVO;
private Date date;
private BigDecimal data;

public SortVO(NStockVO stockVO, BigDecimal data) {
	super();
	this.stockVO = stockVO;
	this.data = data;
}

public SortVO(NStockVO stockVO,Date date){
	super();
	this.stockVO = stockVO;
	this.date = date;
	
}
public SortVO(NBenchMarkVO benchMarkVO, BigDecimal data) {
	super();
	this.benchMarkVO = benchMarkVO;
	this.data = data;
}

public SortVO(NBenchMarkVO benchMarkVO,Date date) {
	super();
	this.benchMarkVO = benchMarkVO;
	this.date = date;
}

public NStockVO getStockVO() {
	return stockVO;
}

public NBenchMarkVO getBenchMarkVO() {
	return benchMarkVO;
}

public BigDecimal getData() {
	return data;
}

public Date getDate() {
	return date;
}

}
