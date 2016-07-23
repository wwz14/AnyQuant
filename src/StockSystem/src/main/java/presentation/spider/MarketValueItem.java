package presentation.spider;

/**
 * 流通市值排行
 * @author xyf
 *
 */
public class MarketValueItem {
	/*
	 * {symbol:"sh601857",code:"601857",name:"中国石油",trade:"7.340",pricechange:
	 * "-0.120",changepercent:"-1.609",buy:"7.340",sell:"7.350",settlement:
	 * "7.460",open:"7.460",high:"7.460",low:"7.340",volume:"32726175",amount:
	 * "241868241",ticktime:"15:00:00",per:38.632,per_d:85.548,nta:"6.3894",pb:1
	 * .149,mktcap:134337397.71841,nmc:118850805.11841,turnoverratio:0.02021}
	 * 
	 */
	String symbol;
	String name;
	
	String trade; //收盘价
	String changepercent; //涨跌幅 ，百分比
	String volume; //成交量/股
	String turnoverratio; //换手率
	String nmc; // 流通市值/万元
	String mktcap; //总市值/万元
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
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getChangepercent() {
		return changepercent;
	}
	public void setChangepercent(String changepercent) {
		this.changepercent = changepercent;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getNmc() {
		return nmc;
	}
	public void setNmc(String nmc) {
		this.nmc = nmc;
	}
	public String getMktcap() {
		return mktcap;
	}
	public void setMktcap(String mktcap) {
		this.mktcap = mktcap;
	}
	public String getTurnoverratio() {
		return turnoverratio;
	}
	public void setTurnoverratio(String turnoverratio) {
		this.turnoverratio = turnoverratio;
	}
	
	
	
	
}
