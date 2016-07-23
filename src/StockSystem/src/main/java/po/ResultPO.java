package po;

import java.io.Serializable;

import vo.ResultVO;

public class ResultPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7331845987613766742L;
	private String status;
	private DataPO data;
	
	public ResultPO(String status, DataPO data) {
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
	public DataPO getData() {
		return data;
	}
	public void setData(DataPO data) {
		this.data = data;
	}
//public ResultVO(String status, DataVO data)
	public ResultVO toVO() {
		return new ResultVO(this.status,this.data.toVO());
	}
}
