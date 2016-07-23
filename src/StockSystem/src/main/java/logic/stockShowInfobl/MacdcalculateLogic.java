package logic.stockShowInfobl;

import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import logicservice.showInfoblservice.MacdcalculateLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;
import vo.MacdVO;
import vo.NStockVO;

public class MacdcalculateLogic implements MacdcalculateLogicservice {
    private StockListDataservice stockListDataservice ;
    private ArrayList<NStockPO> stockList;
    @SuppressWarnings("unused")
	private double firstDif = 0;
    private double firstDea = 0;
   public MacdcalculateLogic(){
	   if(WHICHIMP.isSQL){
			//sql实现
		   stockListDataservice = new StockDataSQL();
	   }
   }
	@Override
	public ArrayList<MacdVO> calculateMacd(String stockName, Date start,
			Date end) throws StatusNotOKException {
	    ArrayList<MacdVO> macdList = new ArrayList<MacdVO>();
		stockList = stockListDataservice.getByName(stockName, DateTool.getStringByDate(start), 
				DateTool.getStringByDate(end));
		MacdVO first = new MacdVO(stockList.get(0).getDate(),stockList.get(0).getClose().doubleValue(),
				stockList.get(0).getClose().doubleValue(),firstDea);
		macdList.add(first);
        for(int i = 1;i<stockList.size();i++) {
        	double ema12 = macdList.get(i - 1).getEma12()*11/13 + stockList.get(i).getClose().doubleValue()*2/13;
        	double ema26 = macdList.get(i - 1).getEma26()*25/27 + stockList.get(i).getClose().doubleValue()*2/27;
        	double dif = ema12 - ema26;
        	double dea = macdList.get(i - 1).getDea()*8/10 + dif*2/10;
        	//double macd = (dif - dea)*2;
        	//public MacdVO(Date date,double ema12, double ema26, double dea) 
        	MacdVO macdData = new MacdVO(stockList.get(i).getDate(),ema12,ema26,dea);
        	macdList.add(macdData);
        	
        }
   
		return macdList;
	}
	
	@SuppressWarnings("unused")
	private NStockVO poTOvo(NStockPO po) {		
		return new NStockVO(po.getName(), po.getDate(), po.getOpen(), po.getHigh(), po.getLow(), po.getClose(),
				po.getAdj_price(), po.getVolume(), po.getTurnover(), po.getPe_ttm(), po.getPb());
	}

}
