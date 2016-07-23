package po;

import java.io.Serializable;
import java.util.ArrayList;

import vo.LinkVO;
import vo.ResultListVO;
import vo.ResultVO;

public class ResultListPO implements Serializable{
	private static final long serialVersionUID=-2436435067738040835l;
	private String status;
	private ArrayList<LinkPO> data;
	private ArrayList<ResultPO> resultPOs;

	public ResultListPO(String status, ArrayList<LinkPO> data,
			ArrayList<ResultPO> resultPOs) {
		super();
		this.status = status;
		this.data = data;
		this.resultPOs = resultPOs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<LinkPO> getData() {
		return data;
	}

	public void setData(ArrayList<LinkPO> data) {
		this.data = data;
	}

	public ArrayList<ResultPO> getResultPOs() {
		return resultPOs;
	}

	public void setResultPOs(ArrayList<ResultPO> resultPOs) {
		this.resultPOs = resultPOs;
	}
	//public ResultListVO(String status, ArrayList<LinkVO> data,
	//ArrayList<ResultVO> resultVOs)
	public ResultListVO toVO() {
		//ResultListVO vo = new ResultListVO(this.status,this.data, this.resultPOs);
		 ArrayList<LinkVO> linkVoList = new ArrayList<LinkVO>();
		 for(int i = 0;i<this.data.size();i++) {
			 linkVoList.add(this.data.get(i).toVO());
		 }
		 
		 ArrayList<ResultVO> resultListVO = new ArrayList<ResultVO>();
		 for(int j = 0;j<this.resultPOs.size();j++) {
			 resultListVO.add(this.resultPOs.get(j).toVO());
		 }
		
		return new ResultListVO(this.status,linkVoList,resultListVO);
	}

}
