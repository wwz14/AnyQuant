package presentation.spider;

/**
 * 盈利能力
 * @author xyf
 *
 */
public class Profit {
	String symbol;
	String name;
	
	String roe; //净资产收益率(%)
	String netprofitmargin; //净利率(%)
	String profitmargin; //毛利率(%)
	String netprofit; //净利润(百万元)
	String eps; //每股收益(元)
	String income; //营业收入(百万元)
	String mips; //每股主营业务收入(元)
	
	
	public Profit(String symbol, String name, String roe, String netprofitmargin, String profitmargin, String netprofit,
			String eps, String income, String mips) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.roe = roe;
		this.netprofitmargin = netprofitmargin;
		this.profitmargin = profitmargin;
		this.netprofit = netprofit;
		this.eps = eps;
		this.income = income;
		this.mips = mips;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoe() {
		return roe;
	}
	public void setRoe(String roe) {
		this.roe = roe;
	}
	public String getNetprofitmargin() {
		return netprofitmargin;
	}
	public void setNetprofitmargin(String netprofitmargin) {
		this.netprofitmargin = netprofitmargin;
	}
	public String getProfitmargin() {
		return profitmargin;
	}
	public void setProfitmargin(String profitmargin) {
		this.profitmargin = profitmargin;
	}
	public String getNetprofit() {
		return netprofit;
	}
	public void setNetprofit(String netprofit) {
		this.netprofit = netprofit;
	}
	public String getEps() {
		return eps;
	}
	public void setEps(String eps) {
		this.eps = eps;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getMips() {
		return mips;
	}
	public void setMips(String mips) {
		this.mips = mips;
	}
	@Override
	public String toString() {
		return "Profit [symbol=" + symbol + ", name=" + name + ", roe=" + roe + ", netprofitmargin=" + netprofitmargin
				+ ", profitmargin=" + profitmargin + ", netprofit=" + netprofit + ", eps=" + eps + ", income=" + income
				+ ", mips=" + mips + "]";
	}
	
	
	
}
