package utils;

import java.math.BigDecimal;

import vo.NStockVO;

public class StopDeal {
/**
 * 判断是否停盘
 * 依据：收盘价或开盘价或最高价或最低价位0
 * true为停牌
 * @param vo 
 * @return
 */
public static  boolean isStopDeal(NStockVO vo){
	BigDecimal zero=BigDecimal.ZERO;
	if(vo.getHigh().compareTo(zero)==0){
		return true;
	}
	if(vo.getLow().compareTo(zero)==0){
		return true;
	}
	if(vo.getClose().compareTo(zero)==0){
		return true;
	}
	if(vo.getOpen().compareTo(zero)==0){
		return true;
	}
	return false;
	
}
}
