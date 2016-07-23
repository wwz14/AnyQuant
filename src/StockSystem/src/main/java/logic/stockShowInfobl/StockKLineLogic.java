package logic.stockShowInfobl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import enums.KLineType;
import exception.StatusNotOKException;
import logic.utils.CalStockKLine;
import logic.utils.CalStockMA;
import logic.utils.GetPreStock;
import logic.utils.Statistics;
import logicservice.showInfoblservice.StockKLineLogicService;
import po.NStockPO;
import vo.BollVO;
import vo.KDJVO;
import vo.KLineVO;
import vo.MAVO;
import vo.RSIVO;
import vo.StockStatisticVO;

public class StockKLineLogic implements StockKLineLogicService{
	@Override
	public ArrayList<KLineVO> getKLineVOs(String name, KLineType kLineType, Date start, Date end) throws StatusNotOKException  {
		CalStockKLine a=new CalStockKLine();
		ArrayList<KLineVO> result=a.getKLineVOs(name, kLineType, start, end);
		//System.out.println("result"+result.size());
		return result;
	}

	@Override
	public ArrayList<MAVO> getMAVOs(String name, Date start, Date end) throws StatusNotOKException {
		CalStockMA a=new CalStockMA();
		return a.getMAVOs(name, start, end);
	}
	/*
	 * （1）计算MA
	MA=N日内的收盘价之和÷N
	（2）计算标准差MD
	（3）计算MB、UP、DN线
	MB=（N－1）日的MA
	UP=MB+k×MD
	DN=MB－k×MD
	（K为参数，可根据股票的特性来做相应的调整，一般默认为2）
	N一般为20
	 * */
	@Override
	public ArrayList<BollVO> getBoll(String name, Date start, Date end) throws StatusNotOKException {
		ArrayList<BollVO> result=new ArrayList<BollVO>();
		//得到前18天的数据
		ArrayList<NStockPO> stock=GetPreStock.getPreS(name,start,end,18);

		//计算ma19
		for(int i=18;i<stock.size();i++){
			ArrayList<Double> close=new ArrayList<Double>();
			for(int j=i-18;j<i+1;j++){
				close.add(stock.get(j).getClose().doubleValue());
			}
			Statistics dsd = new Statistics(close);
			//得到收盘价的ma,即平均值
			double mb=dsd.getMeanValue();
			//标准差MD
			double md=dsd.getSdValue();
			//UP=MB+k×MD
			double up=mb+2*md;
			double dn=mb-2*md;
			result.add(new BollVO(mb, up, dn, stock.get(i).getDate()));
		}
		//System.out.println("boll"+result.size());
		return result;
	}
	/*
	 * 
K值=2/3×第8日K值+1/3×第9日RSV
D值=2/3×第8日D值+1/3×第9日K值
J值=3*第9日K值-2*第9日D值
若无前一日K值与D值,j值，则可以分别用50代替。
	 * */
	@Override
	public ArrayList<KDJVO> getKDJ(String name, Date start, Date end) throws StatusNotOKException  {
		//目前取前八天的数据用50代替
		ArrayList<NStockPO> stock=GetPreStock.getPreS(name,start,end,8);
		//System.out.println("size"+stock.size());
		ArrayList<KDJVO> result=new ArrayList<KDJVO>();
		//第8天的kdj
		KDJVO temp=new KDJVO(50,50,50,stock.get(7).getDate());
		result.add(temp);
		//开始算rsv，
		//9日RSV=（C－L9）÷（H9－L9）×100
		//公式中，C为第9日的收盘价；L9为9日内的最低价；H9为9日内的最高价。
		ArrayList<Double> rsv=new ArrayList<Double>();
		for(int i=8;i<stock.size();i++){
			ArrayList<NStockPO> te=new ArrayList<NStockPO>();
			for(int j=0;j<=i;j++){
				te.add(stock.get(j));
			}
			double fenzi=stock.get(i).getClose().doubleValue()-getLowest(te).doubleValue();
			double fenmu=getHighest(te).subtract(getLowest(te)).doubleValue();
			//分母为0
			if(fenmu!=0){
				fenzi=fenzi/fenmu*100;
			}else{
				fenzi=0;
				System.out.println("rsv分母为0");
			}
			rsv.add(fenzi);		
		}
		//算k值，K值=2/3×第8日K值+1/3×第9日RSV
		ArrayList<Double> k=new ArrayList<Double>();
		k.add(result.get(0).getK());
		for(int i=8;i<stock.size();i++){
			double kk=k.get(i-8);
			double tempkk=kk*2/3;
			double temprsv=rsv.get(i-8)/3;
			tempkk=tempkk+temprsv;
			k.add(tempkk);
		}

		//D值=2/3×第8日D值+1/3×第9日K值
		ArrayList<Double> d=new ArrayList<Double>();
		d.add(result.get(0).getD());
		for(int i=8;i<stock.size();i++){
			double dd=d.get(i-8);
			double tempdd=dd*2/3+k.get(i-7)/3;
			d.add(tempdd);
		}
		//J值=3*第9日K值-2*第9日D值
		ArrayList<Double> j=new ArrayList<Double>();
		j.add(result.get(0).getJ());
		for(int i=8;i<stock.size();i++){
			double tempj=k.get(i-7)*3-d.get(i-7)*2;
			j.add(tempj);
		}
		//starttime前一天的不需要；
		result.remove(0);
		//已经取了前8天的数据，肯定大于1，多取了前八天
		for(int i=1;i<stock.size()-7;i++){
			result.add(new KDJVO(k.get(i),d.get(i),j.get(i),stock.get(i+7).getDate()));
		}
		return result;
	}
	public ArrayList<RSIVO> getRSI(String name, Date start, Date end) throws Exception  {
		ArrayList<RSIVO> result=new ArrayList<RSIVO>();
		//要算6，12，24的
		ArrayList<NStockPO> stock24=GetPreStock.getPreS(name,start,end,23);
		ArrayList<NStockPO> stock12=GetPreStock.getPreStock(stock24, 12);
		ArrayList<NStockPO> stock6=GetPreStock.getPreStock(stock12, 6);
		//算
		ArrayList<Double> rsi6=calRSI(stock6,6);
		ArrayList<Double> rsi12=calRSI(stock12,12);
		ArrayList<Double> rsi24=calRSI(stock24,24);
		for(int i=5;i<stock6.size();i++){

			RSIVO temp=new RSIVO(rsi6.get(i-5),rsi12.get(i-5),rsi24.get(i-5),stock6.get(i).getDate());
			result.add(temp);
		}
		return result;


	}
	public ArrayList<Double> calRSI(ArrayList<NStockPO> stock, int type) throws Exception  {
		/**
		 * 公式化简：RSI=100×14天内收市价上涨数之和的平均值÷(14天内收市价上涨数之和的平均值+14天内收市价下跌数之和的平均值）=
		 * 100×14天内收市价上涨数之和÷(14天内收市价上涨数之和+14天内收市价下跌数之和）。
		 */
		if(type<6){
			throw new Exception("数据太少,无法计算RSI");

		}
		ArrayList<Double> result=new ArrayList<Double>();
		//第一个可计算得
		for(int j=type-1;j<stock.size();j++){
			double increase=0;
			double decrease=0;
			for(int i=0;i<type;i++){
				double diff=stock.get(j-i).diff;
				if(diff>0){
					increase+=diff;
				}else{
					decrease-=diff;
				}
			}
			//求出相对强度RS
			double rs = increase / decrease;
			double rsi = rs / (1 + rs) * 100;
			result.add(rsi);
		}

		return result;


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

	@Override
	public ArrayList<StockStatisticVO> getStockATR(String stockCode, Date start, Date end) throws StatusNotOKException {
		// 均幅指标

		CalStockKLine a = new CalStockKLine();
		return a.getStockStatistic(stockCode, start, end);
	}

}
