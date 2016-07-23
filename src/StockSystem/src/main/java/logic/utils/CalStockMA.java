/**
 * k线图需要收盘价开盘价最高价最低价
 * 收盘价的均线
 * 成交量
 * 成交量的均线
 * 指标量图,目前木有看懂
 * */
package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import enums.MaType;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;
import vo.MAVO;

public class CalStockMA {
	StockListDataservice stockListdata;
	
	
	public CalStockMA(){
		if(WHICHIMP.isSQL){
			//sql实现
			stockListdata = new StockDataSQL();
		}
	}
	//cov,是ma还是mavo
		//0是ma，1是mavo
		//type,是天数
	private ArrayList<BigDecimal> calma(ArrayList<NStockPO> stock,int type,int cOv){
		ArrayList<BigDecimal> ma=new ArrayList<BigDecimal>();
		BigDecimal temp=new BigDecimal(0);
		for(int j=0;j<(stock.size()-(type-1));j++){//总共有size-（type-1）个ma
			for(int i=j;i<(j+type);i++){
				if(cOv==0){
					temp=temp.add(stock.get(i).getClose());
				}
				else{
					temp=temp.add(stock.get(i).getVolume());
				}
			}
			BigDecimal ty=new BigDecimal(type);
			//分母为0的情况
			if(ty.compareTo(BigDecimal.ZERO)==0){
				temp=new BigDecimal(0);
				System.out.println("ma0天");
			}else{
				temp=temp.divide(ty,4,BigDecimal.ROUND_HALF_UP);
			}
			ma.add(temp);
			temp=new BigDecimal(0);
		}

		return ma;

	}
	public ArrayList<MAVO> getMAVOs(String name, Date start, Date end) throws StatusNotOKException {

		ArrayList<MAVO> malist=new ArrayList<MAVO>();

		//获取制定的股票数据

		//这段数据的数量最多，只需这一次调用数据层，其它的数据在这个数据上截

		//先获取前120天的数据,保证一定包含前59天的数据,
		ArrayList<NStockPO> stock120=stockListdata.getByName(name, DateTool.getStringByDate(DateTool.beforeDate(start, -120)), DateTool.getStringByDate(end));
		ArrayList<NStockPO> gonggongend=stockListdata.getByName(name, DateTool.getStringByDate(start), DateTool.getStringByDate(end));
		ArrayList<NStockPO> stock60=GetPreStock.jiequ(stock120,gonggongend.size()+59 );
		//System.out.println("stock6"+stock6.size());
		//然后截取
		//ArrayList<NStockPO> stock60=GetPreStock.getPreS(name,start,end,MaType.MA60.getVal()-1);
		//System.out.println(stock60.size()+"stock60");
		//ma5,mavo5
		//System.out.println(stock60.size());
		ArrayList<NStockPO> stock5=GetPreStock.getPreStock(stock60, 55);
		//System.out.println(stock5.size());
		//mavo10
		ArrayList<NStockPO> stockv10=GetPreStock.getPreStock(stock60, 50);
		//	System.out.println(stockv10.size());
		ArrayList<NStockPO> stock20=GetPreStock.getPreStock(stock60, 40);
		//	System.out.println(stock20.size());
		ArrayList<NStockPO> stock30=GetPreStock.getPreStock(stock60, 30);
		//	System.out.println(stock30.size());


		ArrayList<BigDecimal> ma5=calma(stock5, MaType.MA5.getVal(), 0);
		ArrayList<BigDecimal> mav5=calma(stock5, MaType.MA5.getVal(), 1);
		ArrayList<BigDecimal> mav10=calma(stockv10, MaType.MAVOL10.getVal(), 1);
		ArrayList<BigDecimal> ma20=calma(stock20, MaType.MA20.getVal(), 0);
		ArrayList<BigDecimal> ma30=calma(stock30, MaType.MA30.getVal(), 0);
		ArrayList<BigDecimal> ma60=calma(stock60, MaType.MA60.getVal(), 0);

		for(int i=0;i<ma5.size();i++){

			MAVO mm=new MAVO(ma5.get(i), ma20.get(i), ma30.get(i), ma60.get(i),mav5.get(i), mav10.get(i),gonggongend.get(i).getDate() );
			malist.add(mm);
		}

		//		for(int i=0;i<malist.size();i++){
		//			System.out.println("ma"+malist.get(i).getDate());
		//		}

		return malist;
	}
	//得到n天的ma
	//	private ArrayList<BigDecimal> getMaByDay(String name, Date start, Date end,int n) throws StatusNotOKException{
	//		ArrayList<NStockPO> stock=GetPreStock.getPreS(name,start,end,n-1);
	//		ArrayList<BigDecimal> ma=calma(stock, n, 0);
	//		return ma;
	//
	//	}
}
