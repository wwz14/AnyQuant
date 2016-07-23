package vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockSortVO extends SortVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8095505417489345238L;
	private NStockVO stockVO;
	private BigDecimal sortData;
	
	public StockSortVO(NStockVO stockVO,BigDecimal sortData) {
		super(stockVO, sortData);
		this.stockVO = stockVO;
		this.sortData = sortData;
	}
	
	public BigDecimal getSortData() {
		return sortData;
	}
	
	public NStockVO getStockVO() {
		return stockVO;
	}
}
