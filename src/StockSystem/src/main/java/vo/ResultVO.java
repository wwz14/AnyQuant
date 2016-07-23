package vo;

import java.io.Serializable;

public class ResultVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5458974663431636409L;
	private String status;
	private DataVO data;
	
	public ResultVO(String status, DataVO data) {
		super();
		this.status = status;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public DataVO getData() {
		return data;
	}
	public void setData(DataVO data) {
		this.data = data;
	}
}
