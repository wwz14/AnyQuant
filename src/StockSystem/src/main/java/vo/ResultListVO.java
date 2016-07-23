package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultListVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2701018588947711081L;
	private String status;
	private ArrayList<LinkVO> data;
	private ArrayList<ResultVO> resultVOs;
	



	public ResultListVO(String status, ArrayList<LinkVO> data,
			ArrayList<ResultVO> resultVOs) {
		super();
		this.status = status;
		this.data = data;
		this.resultVOs = resultVOs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<LinkVO> getData() {
		return data;
	}

	public void setData(ArrayList<LinkVO> data) {
		this.data = data;
	}

	public ArrayList<ResultVO> getResultVOs() {
		return resultVOs;
	}

	public void setResultVOs(ArrayList<ResultVO> resultVOs) {
		this.resultVOs = resultVOs;
	}
}
