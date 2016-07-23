package po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import vo.StockVO;

public class StockPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3514894220649291910L;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;	
	private BigDecimal close;
	private BigDecimal adj_price;	
	private BigDecimal volume;
	private BigDecimal turnover;
	private BigDecimal pe_ttm;
	private BigDecimal pb;
	private String date;
	
		
	public StockPO(BigDecimal open, BigDecimal high, BigDecimal low,
			BigDecimal close, BigDecimal adj_price, BigDecimal volume,
			BigDecimal turnover, BigDecimal pe_ttm, BigDecimal pb, String date) {
		super();
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adj_price = adj_price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe_ttm = pe_ttm;
		this.pb = pb;
		this.date = date;
	}


	//10个筛选项呀，555
			/* 假设
		    open("开盘价"), 
		    high("最高价"),   
		    low("最低价"),  
		    close("收盘价")    
		    adj_price("后复权价"),   
		    volume("成交量"),   
		   turnover("换手率"),  
		   pe("市盈率"), 
		   pb("市净率"),
		   date("时间");
		   */
	//得到筛选项
	public ArrayList<BigDecimal> getAllItem(){
		ArrayList<BigDecimal> a=new ArrayList<BigDecimal>();
		a.add(open);
		a.add(high);
		a.add(low);
		a.add(close);
		a.add(adj_price);
		a.add(volume);
		a.add(turnover);
		a.add(pe_ttm);
		a.add(pb);
		
		return a;
		
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getAdj_price() {
		return adj_price;
	}
	public void setAdj_price(BigDecimal adj_price) {
		this.adj_price = adj_price;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getPb() {
		return pb;
	}
	public void setPb(BigDecimal pb) {
		this.pb = pb;
	}
	public BigDecimal getPe_ttm() {
		return pe_ttm;
	}
	public void setPe_ttm(BigDecimal pe_ttm) {
		this.pe_ttm = pe_ttm;
	}
	public BigDecimal getTurnover() {
		return turnover;
	}
	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}
	
	
//	public StockVO(BigDecimal open, BigDecimal high, BigDecimal low,
//			BigDecimal close, BigDecimal adj_price, BigDecimal volume,
//			BigDecimal turnover, BigDecimal pe_ttm, BigDecimal pb, String date)
	public StockVO toVO() {
		return new StockVO(this.open,this.high,this.low,this.close,this.adj_price,
				this.volume,this.turnover,this.pe_ttm,this.pb,this.date);
	}
	
}
