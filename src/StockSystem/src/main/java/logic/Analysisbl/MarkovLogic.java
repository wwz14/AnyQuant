package logic.Analysisbl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import enums.SortType;
import exception.StatusNotOKException;
import logic.stockShowInfobl.StockListLogic;
import logicservice.Analysisblservice.MarkovLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.ResultMsg;
import vo.MResult;
import vo.NStockVO;

//返回未来30天的股票价格
public class MarkovLogic implements MarkovLogicservice {
	private StockListDataservice stockService = new StockDataSQL();
	private StockListLogicservice sortService = new StockListLogic();
	private int state = 4;
	private int preiod = 15;

	public static void main(String[] args) {
		MarkovLogic l = new MarkovLogic();
		try {
			//l.MaxProfit("sh600000");
			ResultMsg m = l.MaxProfit("sh601818");
			ArrayList<MResult> result = (ArrayList<MResult>)m.getResult();
			for(MResult re: result) {
				//System.out.println("M   "+re.MaxException);
				//System.out.println("M  "+re.MidException);
				System.out.println("M  "+re.FirstChance);
				System.out.println("M  "+re.ForthChance);
				System.out.println("M  "+re.SecondChance);
				System.out.println("M  "+re.ThirdChance);
				//System.out.println("M  "+re.interval);
			}
		} catch (StatusNotOKException e) {
			System.out.println("error in maxProfit");
			e.printStackTrace();
		}

	}

	public ResultMsg MaxProfit(String name) throws StatusNotOKException {
		//获得指定股票最近一个月的信息
		ArrayList<NStockPO> poList = stockService.getByName(name,DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)) ,DateTool.getStringByDate(new Date()));
		ArrayList<NStockVO> voList = new ArrayList<>();
		ArrayList<MResult> result = new ArrayList<>();

		//对取出来的数据进行状态分组，分4种状态
		MaxAndMin maxAndMin = maxAndMinClose(poList);
		double max = maxAndMin.max;
		System.out.println("max" +max);
		double min = maxAndMin.min;
		System.out.println("min" +min);
		double length = (max - min)/state;
		System.out.println("length" + length);
		double[] stateSet = {min, min + length, min+2*length, min+3*length, max};


		String interal = keepTherePoint(min)+"~"+ keepTherePoint(min+length)+" "+keepTherePoint(min+length)+"~"+keepTherePoint(min+2*length)+" "
				+keepTherePoint(min+2*length)+"~"+keepTherePoint(min+3*length)+" "+
				keepTherePoint(min+3*length)+"~"+keepTherePoint(max);

		//poList按日期升序排序
		for(NStockPO po : poList) {
			NStockVO vo = poTOvo(po);
			voList.add(vo);
		}
		voList = sortService.sortStockList(voList, SortType.dateAscent);


		//创建状态转移矩阵并初始化
		double[][] stateTransfer = new double[state][state];
		for(int i = 0;i<state;i++) {
			for(int j = 0;j<state;j++)
				stateTransfer[i][j] = 0;
		}
		//计算状态转移矩阵
		int firstPlace = 0;
		int secondPlace = 0;
		for(int i = 0;i<voList.size() - 1;i++) {
			double todayClose = voList.get(i).getClose().doubleValue();
			if(todayClose <= stateSet[1])
				firstPlace = 0;
			else if(todayClose > stateSet[1] && todayClose <= stateSet[2])
				firstPlace = 1;
			else  if(todayClose > stateSet[2] && todayClose <= stateSet[3])
				firstPlace = 2;
			else
				firstPlace = 3;

			double  nextClose = voList.get(i+1).getClose().doubleValue();
			if(nextClose <= stateSet[1])
				secondPlace = 0;
			else if(nextClose > stateSet[1] && nextClose <= stateSet[2])
				secondPlace = 1;
			else  if(nextClose > stateSet[2] && nextClose <= stateSet[3])
				secondPlace = 2;
			else
				secondPlace = 3;
			System.out.println(firstPlace+" and"+secondPlace);

			stateTransfer[firstPlace][secondPlace] = stateTransfer[firstPlace][secondPlace]+1;
		}

//		for(int m = 0;m<state;m++) {
//			for(int n = 0;n<state;n++) {
//				System.out.print(m+""+n +":"+stateTransfer[m][n]+"    ");
//			}
//			System.out.println("");
//		}

		//创建并计算状态转移概率矩阵
		double[][] chance  = new double[state][state];
		for(int i = 0;i<state;i++) {
			for(int j = 0;j<state;j++)
				if((stateTransfer[i][0]+stateTransfer[i][1]+stateTransfer[i][2]+stateTransfer[i][3] )!= 0)
					chance[i][j] =
							stateTransfer[i][j]/(stateTransfer[i][0]+stateTransfer[i][1]+stateTransfer[i][2]+stateTransfer[i][3]);
		}
		for(int m = 0;m<state;m++) {
			for(int n = 0;n<state;n++) {
				System.out.print("概率"+m+""+n +":"+chance[m][n]+"    ");
			}
			System.out.println("");
		}

		//设置初始化向量
		double[] initial = new double[4];
		int place = 0;
		double close = voList.get(voList.size() - 1).getClose().doubleValue();
		if(close <= stateSet[1])
			place = 0;
		else if(close > stateSet[1] && close <= stateSet[2])
			place = 1;
		else  if(close > stateSet[2] && close <= stateSet[3])
			place = 2;
		else
			place = 3;
//		for(int j = 0;j<4;j++) {
//			if(j == place)
//				initial[j] = 1;
//			else
//				initial[j] = 0;
//		}
		for(int j = 0;j<4;j++) {
			if(j%2 == 0)
				initial[j] = 1;
			else
				initial[j] = 0;
		}

		for(int i = 0;i<4;i++) {
			System.out.println("initial:" + initial[i]);
		}

		//计算接下来30天的价格
		double[] matrix = new double[4];
		matrix = matrix (initial, chance);
		double[] ex = exception(stateSet,matrix);
		MResult mresult = new MResult(ex[0],ex[1],ex[2],interal,matrix[0]/2.0,matrix[1]/2.0,matrix[2]/2.0,matrix[3]/2.0);
		result.add(mresult);
		for(int m = 1;m < preiod;m++) {
			matrix = matrix(matrix,chance);
			ex = exception(stateSet,matrix);
			MResult theResult = new MResult(ex[0],ex[1],ex[2],interal,matrix[0]/2.0,matrix[1]/2.0,matrix[2]/2.0,matrix[3]/2.0);
			result.add(theResult);
		}

		ResultMsg resultMsg = new ResultMsg(true,"哦耶",result);

		return resultMsg;
	}

	private NStockVO poTOvo(NStockPO po) {
		return new NStockVO(po.getName(), po.getDate(), po.getOpen(), po.getHigh(), po.getLow(), po.getClose(),
				po.getAdj_price(), po.getVolume(), po.getTurnover(), po.getPe_ttm(), po.getPb());
	}

	//计算最小值和最大值
	private MaxAndMin maxAndMinClose(ArrayList<NStockPO> poList) {
		double min = poList.get(0).getClose().doubleValue();
		double max = min;
		for(int i = 1;i<poList.size();i++) {
			if(poList.get(i).getClose().doubleValue() < min)
				min = poList.get(i).getClose().doubleValue();
			if(poList.get(i).getClose().doubleValue() > max)
				max = poList.get(i).getClose().doubleValue();
		}
		MaxAndMin maxAndMin = new MaxAndMin();
		maxAndMin.min = min;
		maxAndMin.max = max;
		return maxAndMin;
	}

	private double[] matrix (double[] p0, double[][] p1) {
		double[] result = new double[4];
		for(int m = 0;m<4;m++) {
			result[m] = 0;
		}
		for(int i = 0;i<4;i++) {
			for(int j = 0;j<4;j++){
				result[i] = result[i] + p1[j][i]*p0[j];
			}
		}
		return result;
	}


	private class MaxAndMin {
		double min;
		double max;
	}

	private double[] exception(double[] shuzi,double[] gailv) {
		double[] result = new double[3];
		result[0] = shuzi[0]*gailv[0]+shuzi[1]*gailv[1]+shuzi[2]*gailv[2]+shuzi[3]*gailv[3];//min
		result[1] = shuzi[1]*gailv[0]+shuzi[2]*gailv[1]+shuzi[3]*gailv[2]+shuzi[4]*gailv[3];//max
		result[2] = ((shuzi[0]+shuzi[1])/2)*gailv[0] + ((shuzi[1]+shuzi[2])/2)*gailv[1] +//mid
				((shuzi[2]+shuzi[3])/2)*gailv[2] + ((shuzi[3]+shuzi[4])/2)*gailv[3];
		return result;
	}

	private double keepTherePoint(double a) {

		BigDecimal   b   =   new   BigDecimal(a);
		double r =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return r;
	}


}
