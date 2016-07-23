package logic.Analysisbl;
import java.util.ArrayList;

import logic.Analysisbl.regression.Polyfit;
/**
 * Created by alice on 16/6/3.
 */
public class RegressionHandle {
private double[]coes;
	
	private int size = 0;
	
	private double [] curveX;
	
	private double [] curveY;
	
	private double [] newcurveY ;
	
  // public double[] xishu;
	
	private static final double X_INTER = 0.02;	//曲线上两点之间间隔
	private static final int DOTS_BETWEEN = 50;
	
	public RegressionHandle(ArrayList<Double> obs) throws Exception{
		size =  obs.size();
		double [] x = new double[size];
		double [] y = new double[size];
		newcurveY = new double[size];
		for (int i=0; i<obs.size(); i++) {
			x[i] = i + 1;
			y[i] = obs.get(i);
		}
	    Polyfit polyfit = null;
	   
	    //创建多项式拟合对象，其中的4表示是4次多项式拟合
	    polyfit = new Polyfit(x, y, Math.min(4, obs.size() - 1));
	    coes = polyfit.getPolynomialCoefficients();
	    
	    curveX = new double[obs.size() * DOTS_BETWEEN];
	    curveY = new double[curveX.length];
	    
	    for (int i=0;i<curveX.length;i++) {
	    	curveX[i] = 1+ X_INTER * i;
	    	curveY[i] = getY(curveX[i]);
	    }
	    
	    for(int j = 0;j<obs.size();j++) {
	    	newcurveY[j] = getY(x[j]);
	    }
	}
	
	public double getNextValueByRegression() {
		double next = size + 1;
	    return getY(next);
	}
	
	public double [] getCurveX() {
		return curveX;
	}
	
	public double [] getCurveY() {
		return curveY;
	}
	
	private double getY(double x) {
		double result = 0;
		for (int i = 0;i<coes.length;i++) {
        	result += Math.pow(x, i) * coes[coes.length - 1 - i];	//计算出下一个值
        }
		return result;
	}
	
	
	public double[] getnewy() {
		return  newcurveY ;
	}
	
//	public double[] getCoes()  {
//		return coes;
//	}


}
