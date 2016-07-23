package po;

import java.io.Serializable;
import java.util.ArrayList;

import vo.DataVO;
import vo.StockVO;

public class DataPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5064995928473309521L;
	private ArrayList<StockPO> trading_info;
	private String name;

	public DataPO(ArrayList<StockPO> trading_info, String name) {
		super();
		this.trading_info = trading_info;
		this.name = name;
	}

	public ArrayList<StockPO> getTrading_info() {
		return trading_info;
	}

	public void setTrading_info(ArrayList<StockPO> trading_info) {
		this.trading_info = trading_info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public DataVO toVO() {
    	//public DataVO(ArrayList<StockVO> trading_info, String name)
    	ArrayList<StockVO> stockVoList = new ArrayList<StockVO>();
    	for(int i = 0;i<trading_info.size();i++){
    		stockVoList.add(trading_info.get(i).toVO());	
    	}
    	return new DataVO(stockVoList,this.name);
    }
}
