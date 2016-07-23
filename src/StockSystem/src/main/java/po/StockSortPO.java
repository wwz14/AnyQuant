package po;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockSortPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7897489693245369996L;
	@SuppressWarnings("unused")
	private StockPO stockPO;
	private BigDecimal sortData;
	
	public StockSortPO(StockPO stockPO,BigDecimal sortData) {
		this.stockPO = stockPO;
		this.sortData = sortData;
	}
	
	public BigDecimal getSortData() {
		return sortData;
	}
}
