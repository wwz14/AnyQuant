package vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BenchSortVO extends SortVO implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1093597886926925715L;
private NBenchMarkVO benchmarkVO;
 private BigDecimal sortData;
 
public BenchSortVO(NBenchMarkVO benchmarkVO, BigDecimal sortData) {
	super(benchmarkVO, sortData);
	this.benchmarkVO = benchmarkVO;
	this.sortData = sortData;
}

public NBenchMarkVO getBenchmarkVO() {
	return benchmarkVO;
}

public BigDecimal getSortData() {
	return sortData;
}
 

}
