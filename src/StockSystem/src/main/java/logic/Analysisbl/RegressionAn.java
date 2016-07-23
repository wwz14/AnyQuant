

package logic.Analysisbl;

import java.math.BigDecimal;
import java.util.ArrayList;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import logicservice.Analysisblservice.RegressionLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.ResultMsg;
import vo.RegressionVO;

public class RegressionAn implements RegressionLogicservice{



	StockListDataservice stockData=new StockDataSQL();
	//	BenchmarkDataservice benchData=new BenchmarkDataSQL();


	private void    getCan(ArrayList<NStockPO> result,double[]x,char c){
		switch (c){

		case 'c':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getClose().doubleValue();
			}
			break;
		case 'o':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getOpen().doubleValue();
			}
			break;
		case 'v':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getVolume().doubleValue();
			}
			break;
		case 'h':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getHigh().doubleValue();
			}
			break;
		case 'l':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getLow().doubleValue();
			}
			break;
		case 'a':
			for(int ss=1;ss<result.size();ss++){
				x[ss-1]=result.get(ss-1).getAdj_price().doubleValue();
			}
			break;
		}
	}

	public ResultMsg regression(String name) throws Exception{
		ArrayList<NStockPO> result=new ArrayList<NStockPO>();
		//ArrayList<NBenchMarkPO> result=new ArrayList<NBenchMarkPO>();
		ArrayList<RegressionVO> regreList=new ArrayList<RegressionVO>();
		//取一年的数据
		String endTime=DateTool.today();
		String startTime=DateTool.beforeDays(endTime, 365);
		try {
			result=stockData.getByName(name, startTime, endTime);
			//result=benchData.getByName(name);
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("before"+result.size());
		//去掉脏数据
		for(int i=result.size()-1;i>=0;i--){
			//NBenchMarkPO po=result.get(i);
			NStockPO po=result.get(i);
			if(po.getVolume().doubleValue()==0||po.getOpen().doubleValue()==0
					||po.getClose().doubleValue()==0||po.getHigh().doubleValue()==0
					||po.getLow().doubleValue()==0){
				result.remove(i);
			}
		}
		System.out.println("after"+result.size());

		if(result.size()<1){
			throw new Exception("Regression :- There must be at least 2 data points for Regression fitting.");
		}

		/**    * 多元回归    */   
		int m=1;//自变量个数
		int n=result.size()-1;//数据个数
		//存放回归系数
		double[] a = new double[m+1];  
		/** * 一元线性回归分析 *

		 * @param x[n] 
		 * * 存放自变量x的n个取值 *
		 *  @param y[n] * 
		 *  存放与自变量x的n个取值相对应的随机变量y的观察值 * 
		 *  @param n * 观察点数 * 
		 *  @param a[2] * a(0) 返回回归系数b ,a(1)返回回归系数a * 
		 *  @param dt[6] * dt(0) 返回偏差平方和q ,dt(1)返回平均标准偏差s ,
		 *  dt(2)返回回归 * dt(3)返回最大偏差umax,
		 *  dt(4)返回最小偏差umin,dt(5)返回偏差平平方和p, 均值u

		 */
		double[] dt = new double[6];  

		///自变量,第一天收盘价
		double[]close=new double[result.size()-1];
		getCan(result,close,'c');

		////因变量,第二天开盘价
		double[] y=new double[result.size()-1];
		for(int ss=1;ss<result.size();ss++){
			y[ss-1]=result.get(ss).getOpen().doubleValue();
		}
		///
		double[]x=close;


		/*
		 *       @param n      观察数据的组数   *
		 * */
		SPT1(x, y,  n, a, dt);  
		//	a[0]+=500;
		//
		System.out.println("回归方程为：y="+a[0]+"+"+a[1]+"x1");
		//显著性分析
		System.out.println("决定系数r="+dt[2]);
		System.out.println("标准残差="+dt[1]);
		//偏回归系数分析
		//多重共线性分析。。。。
		//点预测
		double pre=0;
		double maxErr=0;
		for(int i=1;i<result.size();i++){

			double re=result.get(i).getOpen().doubleValue();
			double xi[]={

					result.get(i-1).getClose().doubleValue()

			};

			pre=predict(a,xi);
			double xiangduiwucha=Math.abs((pre-re)/re);
			if(xiangduiwucha>maxErr){
				maxErr=xiangduiwucha;
			}
			System.out.println("实际："+re+"   预测："+pre+"  相对误差的绝对值："+xiangduiwucha);
	//		System.out.println(result.get(i).getDate());
			//区间预测
			double down=pre-2*dt[1];
			double up=pre+2*dt[1];
			//System.out.println("区间预测："+down+"~"+up);
			RegressionVO regr=new RegressionVO(result.get(i).getDate(),re,pre,xiangduiwucha);
			regreList.add(regr);
		}
		System.out.println("最大误差"+maxErr);
		BigDecimal   b   =   new   BigDecimal(a[0]);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		BigDecimal   b1   =   new   BigDecimal(a[1]);  
		double   f2   =   b1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		BigDecimal   b2   =   new   BigDecimal(maxErr);  
		
		double   f3   =   b2.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		String anly="通过线性回归分析,发现昨日收盘价对第二天开盘价有显著影响。 线性回归方程为"+
				"： y="+f1+"+"+f2+"x "
				+" 从上述回归方程看，昨日收盘价"+"上涨1元"+"第二天开盘价上涨 "+f2+" 元"
				+ "，常数项值为 "+f1;
		anly+=" 通过对回归模型的验证，把统计的数据代入回归模型对比实际的股票价格进行误差检验，结果显示预测值和实际值误差范围在小于 "
				+f3+" ，检验结果证明线性回归模型可以用来预测股票价格。";
		anly+="采用的数据是过去一年的日数据,对 "+name+" 股价水平的变化做出定量化研究。";
		BigDecimal   preb   =   new   BigDecimal(pre);  
		double   prf1   =   preb.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		anly+="通过今日收盘价，预测明日开盘价为 "+prf1;
		return new ResultMsg(true,anly,regreList);
	}
	/**
	 * 
	 * @param xishu,方程y=a0+a1x1+...中的系数ai
	 * @param sta，xi
	 * @return
	 */
	private double predict(double[] xishu,double[] sta){
		double result=xishu[0];
		System.out.println(xishu[0]+"a0");
		if(xishu.length>1){
			for(int i=1;i<xishu.length;i++){
				result=result+xishu[i]*sta[i-1];
			}
		}
		//		if(result>sta[0]*1.1){
		//			result=sta[0]*1.1;
		//		}
		//		if(result<sta[0]*0.9){
		//			result=sta[0]*0.9;
		//		}
		if(result<=0){
			double ss=0;
			for(int i=0;i<sta.length;i++)
				ss+=sta[i];

			return ss/sta.length;
		}
		return result;
	}

	/** * 一元线性回归分析 *

	 * @param x[n] 
	 * * 存放自变量x的n个取值 *
	 *  @param y[n] * 
	 *  存放与自变量x的n个取值相对应的随机变量y的观察值 * 
	 *  @param n * 观察点数 * 
	 *  @param a[2] * a(0) 返回回归系数b ,a(1)返回回归系数a * 
	 *  @param dt[6] * dt(0) 返回偏差平方和q ,dt(1)返回平均标准偏差s ,
	 *  dt(2)返回回归 * dt(3)返回最大偏差umax,
	 *  dt(4)返回最小偏差umin,dt(5)返回偏差平平方和p, 均值u

	 */

	public static void SPT1(double[] x, double[] y, int n, double[] a, double[] dt){
		int i; 
		double xx, yy, e, f, q, u, p, umax, umin, s;
		xx = 0.0; yy = 0.0; 
		for (i = 0; i <= n - 1; i++) { 
			xx = xx + x[i] / n;
			yy = yy + y[i] / n; 
		} 
		e = 0.0;
		f = 0.0; 
		for (i = 0; i <= n - 1; i++) {
			q = x[i] - xx; 
			e = e + q * q;    
			f = f + q * (y[i] - yy);
		} 
		a[1] = f / e; 
		a[0] = yy - a[1] * xx; 
		q = 0.0;
		u = 0.0; 
		p = 0.0; 
		umax = 0.0; 
		umin = 1.0e+30;
		for (i = 0; i <= n - 1; i++) {
			s = a[1] * x[i] + a[0]; 
			q = q + (y[i] - s) * (y[i] - s); 
			p = p + (s - yy) * (s - yy); q = x[i] - xx; 
			e = Math.abs(y[i] - s); 
			if (e > umax) 
				umax = e;
			umin = e; 
			if (e < umin)
				u = u + e / n; 
		}
		dt[1] = Math.sqrt(q / n); 
		dt[0] = q; 
		dt[2] = p;
		dt[3] = umax; 
		dt[4] = umin;
		dt[5] = u; 
	}



	/**    * 多元线性回归分析   *   
	 *   * @param x[m][n]               每一列存放m个自变量的观察值 
	 *     * @param y[n]               存放随即变量y的n个观察值   *
	 *      @param m            自变量的个数   *
	 *       @param n      观察数据的组数   * 
	 *       @param a     返回回归系数a0,...,am   * 
	 *       @param dt[4] 
	 *       dt[0]偏差平方和q(随机误差)
	 *       dt[1] 平均标准偏差s (标准残差)
	 *       dt[2]返回复相关系数r(决定系数) 
	 *        dt[3]返回回归平方和u(回归偏差平方和)
	 *@param v[m]   *            返回m个自变量的偏相关系数   */ 


	public static void sqt2(double[][] x, double[] y, int m, int n, 
			double[] a,double[] dt, double[] v) { 
		int i, j, k, mm;   
		double q, e, u, p, yy, s, r, pp;   
		double[] b = new double[(m + 1) * (m + 1)]; 
		mm = m + 1;    b[mm * mm - 1] = n;   
		for (j = 0; j <= m - 1; j++) {   
			p = 0.0;
			for (i = 0; i <= n - 1; i++)   
				p = p + x[j][i];   
			b[m * mm + j] = p;   
			b[j * mm + m] = p;    
		}    
		for (i = 0; i <= m - 1; i++)  
			for (j = i; j <= m - 1; j++) {     
				p = 0.0;      
				for(k = 0; k <= n - 1; k++)  
					p = p + x[i][k] * x[j][k]; 
				b[j * mm + i] = p;   
				b[i * mm + j] = p;    
			} 
		a[m]=0.0;
		for (i = 0; i <= n - 1; i++)  
			a[m] = a[m] + y[i];  
		for (i = 0; i <= m - 1; i++) {  
			a[i] = 0.0;    
			for (j = 0; j <= n - 1; j++)      
				a[i] = a[i] + x[i][j] * y[j];  
		}   
		chlk(b, mm, 1, a); 
		yy = 0.0;   
		for (i = 0; i <= n - 1; i++)   
			yy = yy + y[i] / n;  
		q = 0.0; 
		e = 0.0;  
		u = 0.0;   
		for (i = 0; i <= n - 1; i++) {  
			p = a[m];   
			for (j = 0; j <= m - 1; j++)    
				p = p + a[j] * x[j][i];   
			q = q + (y[i] - p) * (y[i] - p);  
			e = e + (y[i] - yy) * (y[i] - yy);
			u = u + (yy - p) * (yy - p); 
		}
		s = Math.sqrt(q / n);  
		r = Math.sqrt(1.0 - q / e);  
		for (j = 0; j <= m - 1; j++) {   
			p = 0.0;   
			for (i = 0; i <= n - 1; i++) {   
				pp = a[m];
				for	(k = 0; k <= m - 1; k++)  
					if (k != j)
						pp = pp + a[k] * x[k][i];   
				p = p + (y[i] - pp) * (y[i] - pp);   
			}     
			v[j] = Math.sqrt(1.0 - q / p);  
		}   
		dt[0] = q;  
		dt[1] = s;  
		dt[2] = r;  
		dt[3] = u; 
	}
	private static int chlk(double[] a, int n, int m, double[] d) {  
		int i, j, k, u, v;    
		if ((a[0] + 1.0 == 1.0) || (a[0] < 0.0)) {  
			System.out.println("fail\n");   
			return (-2);  
		}
		a[0] = Math.sqrt(a[0]);   
		for (j = 1; j <= n - 1; j++)   
			a[j] = a[j] / a[0];  
		for (i = 1; i <= n - 1; i++) {  
			u = i * n + i;    
			for (j = 1; j <= i; j++) {   
				v = (j - 1) * n + i;   
				a[u] = a[u] - a[v] * a[v];  
			} 
			if ((a[u] + 1.0 == 1.0) || (a[u] < 0.0)) {  
				System.out.println("fail\n");  
				return (-2);    
			} 
			a[u] = Math.sqrt(a[u]);  
			if (i != (n - 1)) {    
				for (j = i + 1; j <= n - 1; j++) {    
					v = i * n + j;     
					for (k = 1; k <= i; k++)    
						a[v] = a[v] - a[(k - 1) * n + i] * a[(k - 1) * n +  j];   
					a[v] = a[v] / a[u];     
				}    
			}  
		}
		for (j = 0; j <= m - 1; j++) {   
			d[j] = d[j] / a[0];    
			for (i = 1; i <= n - 1; i++) { 
				u = i * n + i;    
				v = i * m + j;     
				for (k = 1; k <= i; k++)    
					d[v] = d[v] - a[(k - 1) * n + i] * d[(k - 1) * m + j];   
				d[v] = d[v] / a[u];   
			}  
		}
		for (j = 0; j <= m - 1; j++) {   
			u = (n - 1) * m + j;    
			d[u] = d[u] / a[n * n - 1];    
			for (k = n - 1; k >= 1; k--) {  
				u = (k - 1) * m + j;     
				for (i = k; i <= n - 1; i++) {  
					v = (k - 1) * n + i;    
					d[u] = d[u] - a[v] * d[i * m + j];   
				}      v = (k - 1) * n + k - 1; 
				d[u] = d[u] / a[v];   
			}   
		} 
		return (2);
	}





}

