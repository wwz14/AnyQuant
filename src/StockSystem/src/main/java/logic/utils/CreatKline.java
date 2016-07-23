package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import vo.KLineVO;
import vo.NBenchMarkVO;

public class CreatKline {
//	public KLineVO(BigDecimal open, BigDecimal close, BigDecimal high, BigDecimal low, BigDecimal volumn,
//			BigDecimal dealPrice,Date date)
	/**
	 * 返回一天的k线数据
	 * 前值条件：datepointer是工作日
	 * @param vo
	 * @param datePointer
	 * @return
	 */
	public static KLineVO kLineForDay(NBenchMarkVO vo,Date datePointer) {	
		return new KLineVO(vo.getOpen(),vo.getClose(),vo.getHigh(),vo.getLow(),
				vo.getVolume(),vo.getVolume().multiply(vo.getAdj_price()),datePointer);
	}
	
	public static KLineVO kLineForWeek(ArrayList<NBenchMarkVO> voList,Date datePointer) {
	
		return kLineForPeriod(voList, datePointer);
	}
	
	public static KLineVO kLineForMonth(ArrayList<NBenchMarkVO> voList,Date datePointer) {
		return kLineForPeriod(voList, datePointer);
	}
	
	private static KLineVO kLineForPeriod(ArrayList<NBenchMarkVO> voList,Date datePointer) {
		BigDecimal open = voList.get(0).getOpen();
		BigDecimal close = voList.get(voList.size() - 1).getClose();
		BigDecimal high = getHighest(voList);
		BigDecimal low = getLowest(voList);
		BigDecimal vol = getVol(voList);
		BigDecimal dealMoney = getDealMon(voList);	
		return new KLineVO(open,close,high,low,vol,dealMoney,datePointer);
	}
/**
 * 获得最高价
 * @param voList
 * @return
 */
	private static BigDecimal getHighest(ArrayList<NBenchMarkVO> voList) {
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
	private static BigDecimal getLowest(ArrayList<NBenchMarkVO> voList) {
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
	private static BigDecimal getVol(ArrayList<NBenchMarkVO> voList) {
		BigDecimal vol = new BigDecimal(0);
		for(int i = 0;i<voList.size();i++) {
			vol = vol.add(voList.get(i).getVolume());
		}
		return vol;
	}
	/**
	 * 获得一段时间内的交易金额
	 * @param voList
	 * @return
	 */
	private static BigDecimal getDealMon(ArrayList<NBenchMarkVO> voList) {
		BigDecimal dealMoney = new BigDecimal(0);
		for(int i = 0;i<voList.size();i++) {
			BigDecimal eachDayDealMoney = voList.get(i).getVolume().multiply(voList.get(i).getAdj_price());
			dealMoney = dealMoney.add(eachDayDealMoney);
		}
		return dealMoney;
	}
}
