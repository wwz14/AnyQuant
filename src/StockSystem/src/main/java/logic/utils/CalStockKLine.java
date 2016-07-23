package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import enums.KLineType;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;
import vo.KLineVO;
import vo.StockStatisticVO;

public class CalStockKLine {

		StockListDataservice stockListdata;
	
	//test
	//StockListDataservice stockListdata=new Stocklistdata_stub();
public CalStockKLine(){
	if(WHICHIMP.isSQL){
		//sql实现
		stockListdata = new StockDataSQL();
	}
}
	public ArrayList<StockStatisticVO> getStockStatistic(String stockCode, Date start, Date end) throws StatusNotOKException {
		ArrayList<StockStatisticVO> result=new ArrayList<StockStatisticVO>();
		//为了算start的数据,必须得到start前13天的数据，因为n是14
		ArrayList<NStockPO> stock=GetPreStock.getPreS(stockCode,start,end,14);
		//TR=max{|今最高价-今最低价|,|昨收盘-今最高价|,|昨收盘-今最低价|}
		if(stock.size()<14){
			System.out.println("数据少于14算不出来呀");
			return result;
		}
		BigDecimal tr=new BigDecimal(0);
		for(int j=14;j<stock.size();j++){
			for(int i=j-13;i<j+1;i++){
				//前13-前14,
				BigDecimal toHi=stock.get(i).getHigh();
				BigDecimal toLo=stock.get(i).getLow();
				BigDecimal yeCl=stock.get(i-1).getClose();
				BigDecimal toHiMitoLo=toHi.subtract(toLo);
				BigDecimal tYECLMioHI=yeCl.subtract(toHi);
				BigDecimal yeclMitoLo=yeCl.subtract(toLo);
				BigDecimal max=toHiMitoLo;
				if(max.compareTo(tYECLMioHI)<0){
					max=tYECLMioHI;
				}
				if(max.compareTo(yeclMitoLo)<0){
					max=yeclMitoLo;
				}
				tr=tr.add(max);
			}
			tr=tr.divide(new BigDecimal(14), 5, BigDecimal.ROUND_HALF_UP);
			result.add(new StockStatisticVO(tr,stock.get(j).getDate()));
			tr=new BigDecimal(0);

		}
		return result;
	}
	public ArrayList<KLineVO> getKLineVOs(String name, KLineType kLineType, Date start, Date end) throws StatusNotOKException {
		ArrayList<KLineVO> klinelist=new ArrayList<KLineVO>();
		//获取制定的股票数据



		//这边如果是周k，需要往前取7，月k，往前取31还是30
		ArrayList<NStockPO> stock;
		//需要的数据


		BigDecimal dealPrice=new BigDecimal(0);
		BigDecimal open;
		BigDecimal close;
		BigDecimal high;
		BigDecimal low;
		BigDecimal vol = new BigDecimal(0);
		//根据不同的类型
		if(kLineType==KLineType.day){
			stock=stockListdata.getByName(name, DateTool.getStringByDate(start), DateTool.getStringByDate(end));
			//日k，则取出每天的收盘开盘最低价成交金额直接给
			for(int i=0;i<stock.size();i++){
				NStockPO t=stock.get(i);
				dealPrice=t.getVolume().multiply(t.getAdj_price());
				KLineVO temp=new KLineVO(t.getOpen(), t.getClose(), t.getHigh(), t.getLow(), t.getVolume(), dealPrice,t.getDate());
				klinelist.add(temp);
			}
		}
		//改成，一次取道所有数据，然后截取，提高速度
		else if(kLineType==KLineType.week){
			//周k，则取出一周第一个交易日的开盘，最后一个交易日的收盘，周最高，周最低，周成交量和周交易金额打包成一个vo
			//一周的交易日只有5天
			//本周一
			Date trueStart=DateTool.getTheFirstdayOfWeek(start);
			//System.out.println(trueStart);
			//本周日
			Date trueEnd=DateTool.getTheSunDayOfWeek(start);
			//System.out.println(trueEnd);
			//月k，先多取一点数据
			ArrayList<NStockPO>	longstock=stockListdata.getByName(name,DateTool.getStringByDate(DateTool.beforeDate(start, -60)), DateTool.getStringByDate(end));
			while(trueEnd.getTime()<end.getTime()){
				//这表明是完整的一周
				//这周的klinevo
				//	stock=stockListdata.getByName(name, DateTool.getStringByDate(trueStart), DateTool.getStringByDate(trueEnd));
				//				System.out.println("stock"+stock.size());
				//				System.out.println(stock.get(0).getDate());
				//				System.out.println(stock.get(stock.size()-1).getDate());
				stock=GetPreStock.getByStartEnd(longstock, trueStart, trueEnd);
				//				System.out.println("stockte"+stockte.size());
				//				System.out.println(stockte.get(0).getDate());
				//				System.out.println(stockte.get(stockte.size()-1).getDate());
				if(stock.size()!=0){
					open=stock.get(0).getOpen();
					close=stock.get(stock.size()-1).getClose();
					high=getHighest(stock);
					low=getLowest(stock);
					vol=getVol(stock);
					dealPrice=getDealMon(stock);
					//虽然是周日，但是取道的最后一个数据依然是周五
					KLineVO temp=new KLineVO(open, close, high, low, vol, dealPrice,stock.get(stock.size()-1).getDate());
					klinelist.add(temp);
				}
				//下周一
				trueStart=DateTool.beforeDate(trueEnd, 1);
				//下周日
				trueEnd=DateTool.getTheSunDayOfWeek(trueStart);
			}
			//否则表明是残缺的一周
			stock=GetPreStock.getByStartEnd(longstock, trueStart, end);
			if(stock.size()!=0){
				open=stock.get(0).getOpen();
				close=stock.get(stock.size()-1).getClose();
				high=getHighest(stock);
				low=getLowest(stock);
				vol=getVol(stock);
				dealPrice=getDealMon(stock);
				KLineVO temp=new KLineVO(open, close, high, low, vol, dealPrice,stock.get(stock.size()-1).getDate());
				klinelist.add(temp);
			}
		}
		else if(kLineType==KLineType.month){
			//月k，则取出一月第一个交易日的开盘，最后一个交易日的收盘，月最高，月最低，月成交量和月交易金额打包成一个vo
			Date trueStart=DateTool.getTheFirstDay(start);
			Date trueEnd=DateTool.getTheLastDay(start);
			//完整的一月
			//月k，先多取一点数据
			ArrayList<NStockPO>	longstock=stockListdata.getByName(name,DateTool.getStringByDate(DateTool.beforeDate(start, -60)), DateTool.getStringByDate(end));
			while(trueEnd.getTime()<end.getTime()){
				stock=GetPreStock.getByStartEnd(longstock, trueStart, trueEnd);
				if(stock.size()!=0){
					open=stock.get(0).getOpen();
					close=stock.get(stock.size()-1).getClose();
					high=getHighest(stock);
					low=getLowest(stock);
					vol=getVol(stock);
					dealPrice=getDealMon(stock);
					KLineVO temp=new KLineVO(open, close, high, low, vol, dealPrice,stock.get(stock.size()-1).getDate());
					klinelist.add(temp);
				}
				trueStart=DateTool.beforeDate(trueEnd, 1);
				trueEnd=DateTool.getTheLastDay(trueStart);
			}
			//不完整的一月
			stock=GetPreStock.getByStartEnd(longstock, trueStart, end);
			if(stock.size()!=0){
				open=stock.get(0).getOpen();
				close=stock.get(stock.size()-1).getClose();
				high=getHighest(stock);
				low=getLowest(stock);
				vol=getVol(stock);
				dealPrice=getDealMon(stock);
				KLineVO temp=new KLineVO(open, close, high, low, vol, dealPrice,stock.get(stock.size()-1).getDate());
				klinelist.add(temp);
			}	
		}

		//test
		//		for(int i=0;i<klinelist.size();i++){
		//			System.out.println(klinelist.get(i).getDate());
		//			System.out.println(klinelist.get(i).getVolumn());
		//		}

		return klinelist;
	}





	/**
	 * 获得最高价
	 * @param voList
	 * @return
	 */
	private static BigDecimal getHighest(ArrayList<NStockPO> voList) {
		BigDecimal theHigh = voList.get(0).getHigh();
		for(int i = 1;i<voList.size();i++) {
			if(theHigh.compareTo(voList.get(i).getHigh()) == -1)
				theHigh = voList.get(i).getHigh();
		}

		return theHigh;
	}
	/**
	 * 获得最低价
	 * @param voList
	 * @return
	 */
	private static BigDecimal getLowest(ArrayList<NStockPO> voList) {
		BigDecimal theLow = voList.get(0).getLow();
		for(int i = 1;i<voList.size();i++) {
			if(theLow.compareTo(voList.get(i).getLow()) == 1)
				theLow = voList.get(i).getLow();
		}
		return theLow;
	}
	/**
	 * 获得一段时间内的成交数
	 */
	private static BigDecimal getVol(ArrayList<NStockPO> voList) {
		BigDecimal vol = new BigDecimal(0);
		for(int i = 0;i<voList.size();i++) {

			vol=vol.add(voList.get(i).getVolume());
		}
		return vol;
	}
	/**
	 * 获得一段时间内的交易金额
	 * @param voList
	 * @return
	 */
	private static BigDecimal getDealMon(ArrayList<NStockPO> voList) {
		BigDecimal dealMoney = new BigDecimal(0);
		for(int i = 0;i<voList.size();i++) {
			BigDecimal eachDayDealMoney = voList.get(i).getVolume().multiply(voList.get(i).getAdj_price());
			dealMoney=dealMoney.add(eachDayDealMoney);
		}
		return dealMoney;
	}

}
