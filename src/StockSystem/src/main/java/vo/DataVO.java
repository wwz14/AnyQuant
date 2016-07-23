package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class DataVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1132171491358646852L;
	private ArrayList<StockVO> trading_info;
	private String name;
	
	
	public DataVO(ArrayList<StockVO> trading_info, String name) {
		super();
		this.trading_info = trading_info;
		this.name = name;
	}
	public ArrayList<StockVO> getTrading_info() {
		return trading_info;
	}
	public void setTrading_info(ArrayList<StockVO> trading_info) {
		this.trading_info = trading_info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
