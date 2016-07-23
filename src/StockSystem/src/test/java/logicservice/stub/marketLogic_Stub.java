package logicservice.stub;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import exception.StatusNotOKException;
import logicservice.Analysisblservice.MarketLogicservice;
import vo.MarketVO;
import vo.NStockVO;

public class marketLogic_Stub implements MarketLogicservice {

	@Override
	public ArrayList<NStockVO> getResultListAll(Date today)
			throws StatusNotOKException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MarketVO> getMarketVOs(Date start, Date end)
			throws StatusNotOKException {
		MarketVO vo = new MarketVO(new Date(),new BigDecimal(0),new BigDecimal(0));
                ArrayList<MarketVO> result = new ArrayList<MarketVO>();
                result.add(vo);
		return result;
	}

}
