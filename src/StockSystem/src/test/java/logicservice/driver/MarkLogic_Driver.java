package logicservice.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import exception.StatusNotOKException;
import logic.Analysisbl.MarketLogic;
import logicservice.Analysisblservice.MarketLogicservice;
import utils.DateTool;
import vo.MarketVO;
import vo.NStockVO;

public class MarkLogic_Driver {
	private static MarketLogicservice  marketLogicService = new MarketLogic();
	
	public static void main(String [] args) throws StatusNotOKException, IOException {
		//testGetAll();
		testGetMaretVO(); 
		
	}
    
	public static  void testGetAll () {
		try {
			ArrayList<NStockVO> voList = marketLogicService.getResultListAll(new Date()) ;
			for(int i = 0;i<voList.size();i++) {
				NStockVO vo = voList.get(i);
				System.out.println(DateTool.getStringByDate(vo.getDate())+" "+vo.getName() +" "+vo.getHigh()
						+" "+vo.getLow()+" "+vo.getOpen()+" "+
						vo.getClose()+" "+
						vo.getVolume()+" "+vo.getAdj_price()+" "+vo.getDealprice()+" "+vo.getChangeRate());
				
			}
		} catch (StatusNotOKException e) {
			System.out.println("网站不给数据的异常");
			e.printStackTrace();
		}	
		System.out.println("_________________________________________________/n");
	}
	
	public static void testGetMaretVO()  {
		try {
			ArrayList<MarketVO> marketList = marketLogicService.getMarketVOs(DateTool.getDateByString("2016-03-12"), 
					new Date());
			for(int i = 0;i<marketList.size();i++) {
				MarketVO vo = marketList.get(i);
				System.out.println("日期："+DateTool.getStringByDate(vo.getDate())+" "+
				"大盘涨跌："+vo.getBenchmarkChangeRate()+" "+
						"银行涨跌："+vo.getMarketChangeRate());
			}
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
	}
}
